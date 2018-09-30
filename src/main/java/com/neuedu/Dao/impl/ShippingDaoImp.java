package com.neuedu.Dao.impl;

import com.neuedu.Dao.IShippingDao;
import com.neuedu.pojo.Cart;
import com.neuedu.pojo.Shipping;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ShippingDaoImp implements IShippingDao{
    @Autowired
    SqlSession sqlSession;


    @Override
    public Integer addShipping(Shipping shipping) {


        return sqlSession.insert("com.neuedu.Dao.IShippingDao.addShipping", shipping);
    }

    @Override
    public Integer deleteShipping(Integer user_id, Integer shippingId) {
          Map<String,Integer>map=new HashMap<>();
          map.put("user_id", user_id);
          map.put("shippingId",shippingId );

          return sqlSession.delete("com.neuedu.Dao.IShippingDao.deleteShipping",map );

    }

    @Override
    public Integer updateShipping(Shipping shipping) {

        return sqlSession.update("com.neuedu.Dao.IShippingDao.updateShipping",shipping);
    }

    @Override
    public Shipping findShippingByShippingId(Integer user_id, Integer shippingId) {

        Map<String,Integer>map=new HashMap<>();
        map.put("user_id",user_id );
        map.put("shippingId",shippingId );


        return    sqlSession.selectOne("com.neuedu.Dao.IShippingDao.findShippingByShippingId",map );


    }

    @Override
    public List<Shipping> findAllShippings(Integer user_id, Integer pageSize, Integer pageNum) {
       Map<String,Object>map=new HashMap<>();
       map.put("user_id",user_id );
       map.put("pageSize",pageSize );
       map.put("offSize",(pageNum-1)*pageSize );

        return sqlSession.selectList("com.neuedu.Dao.IShippingDao.findAllShippings", map);
    }

    @Override
    public Integer countShippings(Integer user_id) {


        return sqlSession.selectOne("com.neuedu.Dao.IShippingDao.countShippings",user_id );
    }


}
