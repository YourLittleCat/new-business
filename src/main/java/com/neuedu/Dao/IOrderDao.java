package com.neuedu.Dao;

import com.neuedu.pojo.Cart;
import com.neuedu.pojo.Order;
import com.neuedu.pojo.OrderItem;
import com.neuedu.pojo.PayInfo;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.util.List;

public interface IOrderDao {

    public Order findOrderByUserIdAndOrderNo(Integer user_id, Long order_no);


    //


    public List<OrderItem> findOrderItemsByOrderNo(Long order_no);



    //根据订单号查询订单

    public  Order findOrderByOrderNo(Long order_no);


    //更新订单的支付状态

    public  Integer updateOrderStatus(Long order_no,Integer status);



    //添加支付信息

    public  Integer addPayInfo(PayInfo payInfo);



    //添加订单

    public  Integer addOrder(Order order);


    //批量插入orderitem

    public Integer addOrderItem(List<OrderItem>orderItemList);


    //查询订单状态

    public Integer orderStatus(Integer user_id,Long order_no);


    //发货后订单状态和send_time的修改

    public  Integer updaetOrderStatusAndSendTime(Integer user_id,Integer status,Long order_no);

    //前台根据user_id查询所有的order集合

    public  List<Order> findOrdersByUserId(Integer user_id,Integer pageNum,Integer pageSize);


    //查订单总数

    public  Integer addAllOrders(Integer user_id);


    //后台查询所有的订单

    public  List<Order> findOrders(Integer pageNum,Integer pageSize);

    //后台统计所有的订单总数

    public  Integer findAllOrders();

//
//    //后台模糊查询所有订单
//
//    public  List<Order> findOrdersVague(String order_no,Integer pageNum,Integer pageSize);
//
//
//    //后台模糊查询所有订单数量
//    public  Integer findAllOrdersVague(String order_no);


    //后台根据order_no查询订单数

    public  Integer orderNum(Long order_no);

}
