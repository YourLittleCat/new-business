package com.neuedu.Dao;

import com.neuedu.pojo.Cart;
import com.neuedu.pojo.Shipping;

import java.util.List;

public interface IShippingDao {


    //增加收货地址

    public Integer addShipping(Shipping shipping);

    //删除指定的收货地址

    public  Integer deleteShipping(Integer user_id,Integer shippngId);

    //修改收货地址

    public Integer updateShipping(Shipping shipping);


    //根据id查找shipping

    public Shipping findShippingByShippingId (Integer user_id,Integer shippingId);


    //根据用户id查找账号下所有的shipping

    public List<Shipping> findAllShippings(Integer user_id,Integer pageSize,Integer pageNum);


   //统计shipping总数

    public  Integer countShippings(Integer user_id);

}
