package com.neuedu.Dao;

import com.neuedu.pojo.Product;

import java.util.List;
import java.util.Set;

public interface IProduct {

    //根据id查询商品信息
      public Product findProductById(Integer id);


      //增加商品

    public  int addProduct(Product product);

    public  int updateProduct(Product product);



    public List<Product> findProductByPageNo(Integer pageNo,Integer pageSize);


    //查询product表中所有的记录数量

    public long findTotalRecord();


    //根据id或者name查询商品

    public List<Product> findProductByIdOrName(Integer productId,String productName,Integer pageNo,Integer pageSize);


    //根据id或者name计算总数
    public long findTotalRecord(Integer productId,String productName);


    //根据categoryId和productName前台动态查询


    public List<Product> findProductByCategoryIdProductName(Set<Integer> categoryIds, String productName, Integer pageNo, Integer pageSize,Integer orderBy);

    //根据categoryIds或者productName获取product的总数

    public long findTotalRecord(Set<Integer> categoryIds, String productName);





}
