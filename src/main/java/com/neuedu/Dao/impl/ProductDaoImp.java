package com.neuedu.Dao.impl;

import com.neuedu.Dao.IProduct;
import com.neuedu.comment.MyBatisUtil;
import com.neuedu.pojo.Product;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ProductDaoImp implements IProduct {
    @Autowired
    private SqlSession sqlSession;


    //根据id查找商品
    @Override
    public Product findProductById(Integer id) {


        Product product = sqlSession.selectOne("com.neuedu.Dao.IProduct.findProductById", id);
        return product;
    }


    @Override
    public int addProduct(Product product) {

        return sqlSession.insert("com.neuedu.Dao.IProduct.addProduct", product);
    }

    @Override
    public int updateProduct(Product product) {


        return sqlSession.update("com.neuedu.Dao.IProduct.updateProduct", product);
    }


    //分页查询
    @Override
    public List<Product> findProductByPageNo(Integer pageNo, Integer pageSize) {
        Map<String, Integer> map = new HashMap<>();
        map.put("offSet", (pageNo-1)*pageSize);
        map.put("pageSize", pageSize);
        return  sqlSession.selectList("com.neuedu.Dao.IProduct.findProductByPageNo", map);
    }

    @Override
    public long findTotalRecord() {


        return sqlSession.selectOne("com.neuedu.Dao.IProduct.findTotalRecord");
    }

    @Override
    public List<Product> findProductByIdOrName(Integer productId, String productName,Integer pageNo,Integer pageSize) {
        System.out.println(productName+"====3=====productname===========");
        System.out.println(productId+"======3===productid===========");
        System.out.println(pageSize+"======3====pageSize===========");
        System.out.println(pageNo+"=======3===pageNO===========");

        Map<String,Object> map=new HashMap<>();
        map.put("productId", productId);
        map.put("productName", productName);
        map.put("offSet", (pageNo-1)*pageSize);
        map.put("pageSize", pageSize);

        return  sqlSession.selectList("com.neuedu.Dao.IProduct.findProductByIdOrName",map );
    }

    @Override
    public long findTotalRecord(Integer productId, String productName) {
        Map<String,Object> map=new HashMap<>();
        map.put("productId", productId);
        map.put("productName", productName);

        return    sqlSession.selectOne("com.neuedu.Dao.IProduct.findTotalRecordByIdOrName",map);

    }

    @Override
    public List<Product> findProductByCategoryIdProductName(Set<Integer> categoryIds, String productName, Integer pageNo, Integer pageSize,Integer  orderBy) {
          Map<String,Object>map = new HashMap<>() ;

          map.put("categoryIds", categoryIds);
          map.put("productName", productName);
          map.put("offSet", (pageNo-1)*pageSize);
          map.put("pageSize", pageSize);
          map.put("orderby", orderBy);

     //   System.out.println(orderBy+"============orderBy==============");

        return sqlSession.selectList("com.neuedu.Dao.IProduct.findProductByCategoryIdProductName",map );
    }

    @Override
    public long findTotalRecord(Set<Integer> categoryIds, String productName) {
        Map<String,Object>map = new HashMap<>() ;
        map.put("categoryIds", categoryIds);
        map.put("productName", productName);


        return sqlSession.selectOne("com.neuedu.Dao.IProduct.findTotalRecordByCategoryIdProductName", map);
    }


}
