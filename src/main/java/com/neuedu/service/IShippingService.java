package com.neuedu.service;

import com.neuedu.comment.ServerResponse;
import com.neuedu.pojo.Shipping;
import com.neuedu.vo.ShippingVo;

import javax.servlet.http.HttpSession;

public interface IShippingService {

    //增加收获地址

    public ServerResponse<Shipping> addShipping(Shipping shipping);

    //删除地址
    public ServerResponse<Integer> deleteShipping(Integer user_id, Integer shippingId);

    //修改地址

    public  ServerResponse<Integer> updateShipping(Integer id,Integer userId, String receiverName, String receiverPhone, String receiverMobile, String receiverProvince, String receiverCity, String receiverAdress, String receiverZip);

    //根据shippingId查看地址

    public ServerResponse<Shipping> findShippingByShippingId(Integer shippingId,Integer user_id);


    //根据user_id查找shipping  并返回一个shipping的VO
     public ServerResponse<ShippingVo> findAllShippings(Integer user_id,Integer pageNum,Integer pageSize);



}
