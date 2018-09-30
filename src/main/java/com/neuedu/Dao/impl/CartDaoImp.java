package com.neuedu.Dao.impl;

import com.neuedu.Dao.ICartDao;
import com.neuedu.pojo.Cart;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CartDaoImp implements ICartDao {

    @Autowired
    private SqlSession sqlSession;


    @Override
    public Cart findCartByUserIdAndProductId(Integer user_id, Integer product_id) {
        Map<String, Integer> map = new HashMap<>();
        map.put("user_id", user_id);
        map.put("product_id", product_id);

        return sqlSession.selectOne("com.neuedu.Dao.ICartDao.findCartByUserIdAndProductId", map);
    }


    //将商品添加到购物车

    @Override
    public Integer addProductToCart(Cart cart) {

        return sqlSession.insert("com.neuedu.Dao.ICartDao.addProductToCart", cart);
    }

    //更新商品
    @Override
    public Integer updateCart(Cart cart) {

        return sqlSession.update("com.neuedu.Dao.ICartDao.updateCart", cart);
    }

    @Override
    public List<Cart> findCartsByUserId(Integer user_id) {

        return sqlSession.selectList("com.neuedu.Dao.ICartDao.findCartsByUserId", user_id);
    }

    @Override
    public Integer isCheckAll(Integer user_id) {

        return sqlSession.selectOne("com.neuedu.Dao.ICartDao.isCheckAll", user_id);
    }


    //从购物车中移除商品
    @Override
    public Integer deleteProduct(Cart cart) {

        return sqlSession.delete("com.neuedu.Dao.ICartDao.deleteProduct",cart );
    }

    @Override
    public Integer checkedProduct(Integer user_id,Integer product_id) {
        Map<String ,Integer> map = new HashMap<>() ;
         map.put("user_id",user_id );
         map.put("product_id",product_id );

        return sqlSession.update("com.neuedu.Dao.ICartDao.checkedProduct",map);
    }


    @Override
    public Integer unCheckedProduct(Integer user_id,Integer product_id) {
        Map<String ,Integer> map = new HashMap<>() ;
        map.put("user_id",user_id );
        map.put("product_id",product_id );

        return sqlSession.update("com.neuedu.Dao.ICartDao.unCheckedProduct",map);
    }

    @Override
    public List<Cart> findCartsCheckedByUserId(Integer user_id) {

        return sqlSession.selectList("com.neuedu.Dao.ICartDao.findCartsCheckedByUserId",user_id);
    }

    @Override
    public Integer deleteCart(List<Cart> list,Integer user_id) {
          Map<String,Object>map=new HashMap<>();
          map.put("user_id",user_id );
          map.put("list",list );

        return sqlSession.delete("com.neuedu.Dao.ICartDao.deleteCart",map );
    }
}
