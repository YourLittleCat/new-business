package com.neuedu.service;

import com.neuedu.comment.ServerResponse;
import com.neuedu.vo.OrderItemVo;
import com.neuedu.vo.OrderPageModel;
import com.neuedu.vo.OrderProductVo;
import com.neuedu.vo.OrderVo;

import java.util.Map;

public interface IOrderService {

    //
    public ServerResponse findOrderByUserIdAndOrderNo(Long order_no, Integer user_id);


    //
    public  String alipaycallBack(Map<String,String> map);

    //创建订单

    public  ServerResponse creatOrder(Integer user_id,Integer shippingId);


    //查看订单状态
    public  ServerResponse orderStatus(Integer user_id,Long order_no);


    //取消订单
    public  ServerResponse cancleOrder(Integer user_id,Long order_no);

    //订单中商品的详细信息
    public  ServerResponse<OrderProductVo> getOrderProductDetail(Integer user_id);


    //查看订单的详情

    public  ServerResponse<OrderVo> detail(Integer user_id,Long order_no);


    //发货

    public ServerResponse sendGoods(Integer user_id,Long order_no);


    //前台list

    public  ServerResponse<OrderPageModel> orderlist(Integer user_id,Integer pageSize,Integer pageNum);

    //后台查询所有list

    public  ServerResponse<OrderPageModel> allorderlist(Integer user_id,Integer pageSize,Integer pageNum);


    //后台根据订单号查询订单

    public ServerResponse<OrderPageModel> findListByOrderNo(Integer user_id,Long order_no,Integer pageSize,Integer pageNum);


    //前台根据订单号和用户id查询order返回orderVo

    public  ServerResponse<OrderVo>frontDetail(Integer user_id,Long order_no);
}
