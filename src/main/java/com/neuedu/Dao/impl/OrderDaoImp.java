package com.neuedu.Dao.impl;

import com.neuedu.Dao.IOrderDao;
import com.neuedu.pojo.Cart;
import com.neuedu.pojo.Order;
import com.neuedu.pojo.OrderItem;
import com.neuedu.pojo.PayInfo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderDaoImp implements IOrderDao{

    @Autowired
   private SqlSession sqlSession;


    @Override
    public Order findOrderByUserIdAndOrderNo(Integer user_id, Long order_no) {
        Map<String,Object>map=new HashMap<>();
        map.put("user_id",user_id );
        map.put("order_no" , order_no );

        return sqlSession.selectOne("com.neuedu.Dao.IOrderDao.findOrderByUserIdAndOrderNo",map );
    }

    @Override
    public List<OrderItem> findOrderItemsByOrderNo(Long order_no) {


        return sqlSession.selectList("com.neuedu.Dao.IOrderDao.findOrderItemsByOrderNo", order_no);



    }

    @Override
    public Order findOrderByOrderNo(Long order_no) {

        return sqlSession.selectOne("com.neuedu.Dao.IOrderDao.findOderByOrderNo",order_no );
    }


    //更新支付状态
    @Override
    public Integer updateOrderStatus(Long order_no, Integer status) {
        Map<String,Object>map=new HashMap<>();
        map.put("order_no",order_no );
        map.put("status",status );

        return sqlSession.update("com.neuedu.Dao.IOrderDao.updateOrderStatus",map );
    }


    //添加支付信息
    @Override
    public Integer addPayInfo(PayInfo payInfo) {

        return sqlSession.insert("com.neuedu.Dao.IOrderDao.addPayInfo",payInfo );
    }

    @Override
    public Integer addOrder(Order order) {


        return sqlSession.insert("com.neuedu.Dao.IOrderDao.addOrder",order );
    }

    @Override
    public Integer addOrderItem(List<OrderItem> list) {


        return sqlSession.insert("com.neuedu.Dao.IOrderDao.addOrderItem",list);
    }

    //查询订单状态

    @Override
    public Integer orderStatus(Integer user_id, Long order_no) {
          Map<String,Object> map=new HashMap<>() ;
          map.put("user_id", user_id);
          map.put("order_no",order_no );

        return sqlSession.selectOne("com.neuedu.Dao.IOrderDao.orderStatus", map);
    }

    @Override
    public Integer updaetOrderStatusAndSendTime(Integer user_id, Integer status, Long order_no) {
        Map<String,Object>map=new HashMap<>();
        map.put("user_id", user_id);
        map.put("status",status );
        map.put("order_no",order_no );
        return sqlSession.update("com.neuedu.Dao.IOrderDao.updaetOrderStatusAndSendTime", map);
    }


    //根据user_id查询orders
    @Override
    public List<Order> findOrdersByUserId(Integer user_id,Integer pageNum,Integer pageSize) {
             Map<String,Integer>map = new HashMap<>();
             map.put("user_id",user_id );
             map.put("offSet",(pageNum-1)*pageSize );
             map.put("pageSize",pageSize );


        return sqlSession.selectList("com.neuedu.Dao.IOrderDao.findOrdersByUserId" , map);
    }



    //查询订单总数

    @Override
    public Integer addAllOrders(Integer user_id) {


        return sqlSession.selectOne("com.neuedu.Dao.IOrderDao.addAllOrders",user_id );
    }


    //后台查询所有的订单

    @Override
    public List<Order> findOrders(Integer pageNum, Integer pageSize) {
        Map<String,Integer>map = new HashMap<>();
        map.put("offSet",(pageNum-1)*pageSize );
        map.put("pageSize",pageSize );
        return sqlSession.selectList("com.neuedu.Dao.IOrderDao.findOrders" ,map );
    }
    //后台统计订单总数
    @Override
    public Integer findAllOrders() {


        return sqlSession.selectOne("com.neuedu.Dao.IOrderDao.findAllOrders" );
    }

    @Override
    public Integer orderNum(Long order_no) {
        return null;
    }

//    //后台模糊查询订单
//    @Override
//    public List<Order> findOrdersVague(String order_no, Integer pageNum, Integer pageSize) {
//        Map<String,Object>map = new HashMap<>();
//        map.put("order_no",order_no );
//        map.put("offSet",(pageNum-1)*pageSize );
//        map.put("pageSize",pageSize );
//
//        return sqlSession.selectList("com.neuedu.Dao.IOrderDao.findOrdersVague",map );
//    }
//
//    //后台模糊查询订单数量
//    @Override
//    public Integer findAllOrdersVague(String order_no) {
//
//
//        return sqlSession.selectOne("com.neuedu.Dao.IOrderDao.findAllOrdersVague", order_no);
//    }


}
