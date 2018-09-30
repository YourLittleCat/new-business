package com.neuedu.service.impl;

import com.alipay.api.AlipayResponse;
import com.alipay.api.domain.TradeFundBill;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.MonitorHeartbeatSynResponse;
import com.alipay.demo.trade.DemoHbRunner;
import com.alipay.demo.trade.Main;
import com.alipay.demo.trade.config.Configs;
import com.alipay.demo.trade.model.ExtendParams;
import com.alipay.demo.trade.model.GoodsDetail;
import com.alipay.demo.trade.model.builder.*;
import com.alipay.demo.trade.model.hb.*;
import com.alipay.demo.trade.model.hb.Product;
import com.alipay.demo.trade.model.result.AlipayF2FPayResult;
import com.alipay.demo.trade.model.result.AlipayF2FPrecreateResult;
import com.alipay.demo.trade.model.result.AlipayF2FQueryResult;
import com.alipay.demo.trade.model.result.AlipayF2FRefundResult;
import com.alipay.demo.trade.service.AlipayMonitorService;
import com.alipay.demo.trade.service.AlipayTradeService;
import com.alipay.demo.trade.service.impl.AlipayMonitorServiceImpl;
import com.alipay.demo.trade.service.impl.AlipayTradeServiceImpl;
import com.alipay.demo.trade.service.impl.AlipayTradeWithHBServiceImpl;
import com.alipay.demo.trade.utils.Utils;
import com.alipay.demo.trade.utils.ZxingUtils;
import com.neuedu.Dao.ICartDao;
import com.neuedu.Dao.IOrderDao;
import com.neuedu.Dao.IProduct;
import com.neuedu.Dao.IShippingDao;
import com.neuedu.comment.*;

import com.neuedu.pojo.*;
import com.neuedu.service.IOrderService;
import com.neuedu.vo.*;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.apache.commons.io.filefilter.OrFileFilter;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class OrderServiceImp implements IOrderService {

    @Autowired
    IOrderDao iOrderDao;

    @Autowired
    ICartDao iCartDao;

    @Autowired
    IProduct iProduct;


    @Autowired
    IShippingDao iShippingDao;

    @Override
    public ServerResponse creatOrder(Integer user_id, Integer shippingId) {

        //先查找到购物车中已经勾选的商品  返回一个cartList
        List<Cart> cartList = iCartDao.findCartsCheckedByUserId(user_id);

        //将cartList转化为orderItemList
        ServerResponse serverResponse = getListOrderItem(user_id, cartList);


        //如果状态码对不上  说明转化失败了
        if (serverResponse.getStatus() != ResponseCode.CHANGE_SUCCESS.getCode()) {

            return serverResponse;

        }
        List<OrderItem> orderItemList = (List<OrderItem>) serverResponse.getData();

        //创建order
        Order order = getOrder(user_id, shippingId);
        order.setPayment(getPayMent(orderItemList));
        iOrderDao.addOrder(order);

        //把刚才没的订单号加进去

        for (OrderItem orderItem : orderItemList) {
            orderItem.setOrder_no(order.getOrder_no());

        }

        //批量插入list<orderItem>
        iOrderDao.addOrderItem(orderItemList);


        //扣除库存
        reduceStock(cartList);

        //清除购物车
        iCartDao.deleteCart(cartList, user_id);

        //返回数据orderVo
        OrderVo orderVo = accembleOrderVo(order);


        return ServerResponse.createServerResponse(ResponseCode.UPDATE_SUCCESS.getCode(), orderVo, ResponseCode.UPDATE_SUCCESS.getMsg());
    }


    //查询订单状态

    @Override
    public ServerResponse orderStatus(Integer user_id, Long order_no) {
        int result = iOrderDao.orderStatus(user_id, order_no);

        if (result <= 0) {
            return ServerResponse.createServerResponse(ResponseCode.FIND_FAILD.getCode(), result, ResponseCode.FIND_FAILD.getMsg());

        } else {

            return ServerResponse.createServerResponse(ResponseCode.FIND_SUCCESS.getCode(), ResponseCode.FIND_SUCCESS.getMsg());
        }

    }


    //取消订单---》更改订单状态
    @Override
    public ServerResponse cancleOrder(Integer user_id, Long order_no) {
        Order order = iOrderDao.findOrderByUserIdAndOrderNo(user_id, order_no);

        if (order == null) {
            return ServerResponse.createServerResponse(ResponseCode.ORDER_NOT_EXIT.getCode(), ResponseCode.ORDER_NOT_EXIT.getMsg());

        }
        if (order.getStatus() != 10) {
            return ServerResponse.createServerResponse(ResponseCode.ORDER_COUND_NOT_CANCLE.getCode(), ResponseCode.ORDER_COUND_NOT_CANCLE.getMsg());

        }


        int result = iOrderDao.updateOrderStatus(order_no, Const.ORDERSTSTUS.ORDER_CANCELLED.getCode());
        if (result <= 0) {

            return ServerResponse.createServerResponse(ResponseCode.DO_FAILD.getCode(), ResponseCode.DO_FAILD.getMsg());

        } else {

            return ServerResponse.createServerResponse(ResponseCode.DO_SUCCESS.getCode(), Const.ORDERSTSTUS.ORDER_CANCELLED.getCode(), ResponseCode.DO_SUCCESS.getMsg());

        }
    }


    //订单中商品的详细信息
    @Override
    public ServerResponse<OrderProductVo> getOrderProductDetail(Integer user_id) {
        //1，获取订单中商品列表---》也就是购物车中选中的商品

        List<Cart> cartList = iCartDao.findCartsCheckedByUserId(user_id);
        if (cartList == null || cartList.size() <= 0) {
            return ServerResponse.createServerResponse(ResponseCode.CART_WITHOUT_PRODUCT.getCode(), ResponseCode.CART_WITHOUT_PRODUCT.getMsg());
        }

        //2.将商品列表cartList转成orderItemList
        ServerResponse serverResponse = getListOrderItem(user_id, cartList);

        //如果状态码对不上  说明转化失败了
        if (serverResponse.getStatus() != ResponseCode.CHANGE_SUCCESS.getCode()) {
            return serverResponse;
        }
        List<OrderItem> orderItemList = (List<OrderItem>) serverResponse.getData();

        //3.将orderItemList转化为orderItemVoList

        List<OrderItemVo> orderItemVoList = new ArrayList<>();
        for (OrderItem orderItem : orderItemList) {

            OrderItemVo orderItemVo = accembleOrderItemVo(orderItem);
            orderItemVoList.add(orderItemVo);


        }
        //4.返回数据
        OrderProductVo orderProductVo = new OrderProductVo();
        orderProductVo.setImageHost(PropertiesUtil.getProperties("imageHost"));
        orderProductVo.setOrderItemVoList(orderItemVoList);
        orderProductVo.setProductTotalPrice(getPayMent(orderItemList));


        return ServerResponse.createServerResponse(ResponseCode.FIND_SUCCESS.getCode(), orderProductVo, ResponseCode.FIND_SUCCESS.getMsg());
    }

    @Override
    public ServerResponse<OrderVo> detail(Integer user_id, Long order_no) {
        //根据order_no查询到订单 order
        Order order = iOrderDao.findOrderByOrderNo(order_no);
        if (order == null) {
            return ServerResponse.createServerResponse(ResponseCode.ORDER_NOT_EXIT.getCode(), ResponseCode.ORDER_NOT_EXIT.getMsg());
        }
        //将order转化为orderVo
        OrderVo orderVo = accembleOrderVo(order);

        return ServerResponse.createServerResponse(ResponseCode.FIND_SUCCESS.getCode(), orderVo, ResponseCode.FIND_SUCCESS.getMsg());
    }


    //发货后  更新订单的状态和发货时间

    @Override
    public ServerResponse sendGoods(Integer user_id, Long order_no) {
        //根据order_no查询到订单
        Order order = iOrderDao.findOrderByUserIdAndOrderNo(user_id, order_no);
        if (order == null) {
            return ServerResponse.createServerResponse(ResponseCode.ORDER_NOT_EXIT.getCode(), ResponseCode.ORDER_NOT_EXIT.getMsg());
        }


        //修改状态和发货时间
        int result = iOrderDao.updaetOrderStatusAndSendTime(user_id, Const.ORDERSTSTUS.ORDER_SEND.getCode(), order_no);
        if (result > 0) {

            return ServerResponse.createServerResponse(ResponseCode.DO_SUCCESS.getCode(), order.getStatus(), ResponseCode.DO_SUCCESS.getMsg());

        } else {

            return ServerResponse.createServerResponse(ResponseCode.DO_FAILD.getCode(), ResponseCode.DO_FAILD.getMsg());


        }


    }


    //前台查询orderlist
    @Override
    public ServerResponse<OrderPageModel> orderlist(Integer user_id, Integer pageSize, Integer pageNum) {
        OrderPageModel orderPageModel = new OrderPageModel();
        /*
         * 根据user_id 获得orderlist
         */
        /*
         * 最外面的是List<OrderVo>--->里面有orderVo
         * ---》List<OrderItemVo>----》orderItemVo
         */



        /*---》orderItem---》order
         * **/
        List<Order> orderList = iOrderDao.findOrdersByUserId(user_id, pageNum, pageSize);
        if (orderList.size() <= 0 || orderList == null) {
            return ServerResponse.createServerResponse(ResponseCode.ORDER_ENMPTY.getCode(), ResponseCode.ORDER_ENMPTY.getMsg());

        }

        //orderList转化为orderVoList
        List<OrderVo> orderVoList = new ArrayList<>();


        for (Order order : orderList) {
            OrderVo orderVo = accembleOrderVo(order);
            orderVoList.add(orderVo);
        }

        //设置list
        orderPageModel.setOrderVoList(orderVoList);
        orderPageModel.setPageNum(pageNum);
        orderPageModel.setPageSize(pageSize);
        //获取订单总数
        int count = iOrderDao.addAllOrders(user_id);
        orderPageModel.setTotal(count);
        orderPageModel.setStartRow(1);
        //计算总的页数
        int pages = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
        orderPageModel.setPages(pages);
        //如果当前页+1 》总页数  则没有下一页 当前页就是下一页 否则   下一页就是当前页+1
        int nextPage = pageNum + 1 > pages ? pageNum : pageNum + 1;
        orderPageModel.setNextPage(nextPage);
        int prePage = pageNum - 1 >= 1 ? pageNum - 1 : pageNum;
        orderPageModel.setPrePage(prePage);
        orderPageModel.setOrderBy(false);
        //
        boolean isFirstPage = pageNum == 1 ? true : false;
        orderPageModel.setFirstPage(isFirstPage);
        orderPageModel.setFirstPage(1);

        //
        boolean isLastPage = pageNum == pages ? true : false;
        orderPageModel.setLastPage(isLastPage);
        orderPageModel.setLastPage(pages);
        //
        boolean hasNextPage = pageNum == pages ? false : true;
        orderPageModel.setHasNextPage(hasNextPage);


        return ServerResponse.createServerResponse(ResponseCode.FIND_SUCCESS.getCode(), orderPageModel, ResponseCode.FIND_SUCCESS.getMsg());
    }


    //后台查询所有orderList
    @Override
    public ServerResponse<OrderPageModel> allorderlist(Integer user_id, Integer pageSize, Integer pageNum) {
        OrderPageModel orderPageModel = new OrderPageModel();
        /*
         * 根据user_id 获得orderlist
         */
        /*
         * 最外面的是List<OrderVo>--->里面有orderVo
         * ---》List<OrderItemVo>----》orderItemVo
         */



        /*---》orderItem---》order
         * **/
        List<Order> orderList = iOrderDao.findOrders(pageNum, pageSize);
        if (orderList.size() <= 0 || orderList == null) {
            return ServerResponse.createServerResponse(ResponseCode.ORDER_ENMPTY.getCode(), ResponseCode.ORDER_ENMPTY.getMsg());

        }

        //orderList转化为orderVoList
        List<OrderVo> orderVoList = new ArrayList<>();


        for (Order order : orderList) {
            OrderVo orderVo = accembleOrderVo(order);
            orderVoList.add(orderVo);
        }

        //设置list
        orderPageModel.setOrderVoList(orderVoList);
        orderPageModel.setPageNum(pageNum);
        orderPageModel.setPageSize(pageSize);
        //获取订单总数
        int count = iOrderDao.findAllOrders();
        orderPageModel.setTotal(count);
        orderPageModel.setStartRow(1);
        //计算总的页数
        int pages = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
        orderPageModel.setPages(pages);
        //如果当前页+1 》总页数  则没有下一页 当前页就是下一页 否则   下一页就是当前页+1
        int nextPage = pageNum + 1 > pages ? pageNum : pageNum + 1;
        orderPageModel.setNextPage(nextPage);
        int prePage = pageNum - 1 >= 1 ? pageNum - 1 : pageNum;
        orderPageModel.setPrePage(prePage);
        orderPageModel.setOrderBy(false);
        //
        boolean isFirstPage = pageNum == 1 ? true : false;
        orderPageModel.setFirstPage(isFirstPage);
        orderPageModel.setFirstPage(1);

        //
        boolean isLastPage = pageNum == pages ? true : false;
        orderPageModel.setLastPage(isLastPage);
        orderPageModel.setLastPage(pages);
        //
        boolean hasNextPage = pageNum == pages ? false : true;
        orderPageModel.setHasNextPage(hasNextPage);


        return ServerResponse.createServerResponse(ResponseCode.FIND_SUCCESS.getCode(), orderPageModel, ResponseCode.FIND_SUCCESS.getMsg());


    }

    //后台根据订单号查询订单
    @Override
    public ServerResponse<OrderPageModel> findListByOrderNo(Integer user_id, Long order_no, Integer pageSize, Integer pageNum) {
        OrderPageModel orderPageModel = new OrderPageModel();
        /*
         * 根据user_id 获得orderlist
         */
        /*
         * 最外面的是List<OrderVo>--->里面有orderVo
         * ---》List<OrderItemVo>----》orderItemVo
         */



        /*---》orderItem---》order
         * **/
        if (order_no==null||order_no.equals("")){
            return ServerResponse.createServerResponse(ResponseCode.ORDER_NUMBER_NOT_NULL.getCode(),ResponseCode.ORDER_NUMBER_NOT_NULL.getMsg());

        }


           Order order1=     iOrderDao.findOrderByOrderNo(order_no);
        List<Order> orderList =new ArrayList<>();
        orderList.add(order1);
        if (orderList.size() <= 0 || orderList == null) {
            return ServerResponse.createServerResponse(ResponseCode.ORDER_ENMPTY.getCode(), ResponseCode.ORDER_ENMPTY.getMsg());

        }

        //orderList转化为orderVoList
        List<OrderVo> orderVoList = new ArrayList<>();


        for (Order order : orderList) {
            OrderVo orderVo = accembleOrderVo(order);
            orderVoList.add(orderVo);
        }

        //设置list
        orderPageModel.setOrderVoList(orderVoList);
        orderPageModel.setPageNum(pageNum);
        orderPageModel.setPageSize(pageSize);
        //获取订单总数
        int count = 1;
        orderPageModel.setTotal(count);
        orderPageModel.setStartRow(1);
        //计算总的页数
        int pages = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
        orderPageModel.setPages(pages);
        //如果当前页+1 》总页数  则没有下一页 当前页就是下一页 否则   下一页就是当前页+1
        int nextPage = pageNum + 1 > pages ? pageNum : pageNum + 1;
        orderPageModel.setNextPage(nextPage);
        int prePage = pageNum - 1 >= 1 ? pageNum - 1 : pageNum;
        orderPageModel.setPrePage(prePage);
        orderPageModel.setOrderBy(false);
        //
        boolean isFirstPage = pageNum == 1 ? true : false;
        orderPageModel.setFirstPage(isFirstPage);
        orderPageModel.setFirstPage(1);

        //
        boolean isLastPage = pageNum == pages ? true : false;
        orderPageModel.setLastPage(isLastPage);
        orderPageModel.setLastPage(pages);
        //
        boolean hasNextPage = pageNum == pages ? false : true;
        orderPageModel.setHasNextPage(hasNextPage);


        return ServerResponse.createServerResponse(ResponseCode.FIND_SUCCESS.getCode(), orderPageModel, ResponseCode.FIND_SUCCESS.getMsg());


    }

    //前台根据用户id和订单号查询订单Vo
    @Override
    public ServerResponse<OrderVo> frontDetail(Integer user_id, Long order_no) {
        //根据order_no查询到订单 order
        Order order = iOrderDao.findOrderByUserIdAndOrderNo(user_id, order_no);
        if (order == null) {
            return ServerResponse.createServerResponse(ResponseCode.ORDER_NOT_EXIT.getCode(), ResponseCode.ORDER_NOT_EXIT.getMsg());
        }
        //将order转化为orderVo
        OrderVo orderVo = accembleOrderVo(order);

        return ServerResponse.createServerResponse(ResponseCode.FIND_SUCCESS.getCode(), orderVo, ResponseCode.FIND_SUCCESS.getMsg());

    }


    //获得orderVo

    private OrderVo accembleOrderVo(Order order) {
        OrderVo orderVo = new OrderVo();
        orderVo.setOrder_no(order.getOrder_no());
        orderVo.setPayment(order.getPayment());
        orderVo.setPayment_type(Const.PAYONLINEOROFFLINE.PAY_ONLINE.getCode());
        orderVo.setPaymentTypeDesc(Const.PAYONLINEOROFFLINE.PAY_ONLINE.getMsg());
        orderVo.setPostage(0);
        orderVo.setStatus(order.getStatus());
        orderVo.setStatusDesc(Const.ORDERSTSTUS.codeOf(order.getStatus()) != null ? Const.ORDERSTSTUS.codeOf(order.getStatus()).getMsg() : "");
        orderVo.setSend_time(DateUtil.dateChange(order.getSend_time()));
        orderVo.setPayment_time(DateUtil.dateChange(order.getPayment_time()));
        orderVo.setClose_time(DateUtil.dateChange(order.getClose_time()));
        orderVo.setCreat_time(DateUtil.dateChange(order.getCreat_time()));
        orderVo.setImageHost(PropertiesUtil.getProperties("imageHost"));
        orderVo.setShipping_id(order.getShipping_id());
        /**
         * 根据shippingId查询shipping  并转换成shippingVo的还没转
         * */
        Shipping shipping = iShippingDao.findShippingByShippingId(order.getUser_id(), order.getShipping_id());
        VOShipping voShipping = accessbleShippingVo(shipping);

        orderVo.setVoShipping(voShipping);
//        shippingVo.setEndRow(0);
//        shippingVo.setFirstPage(true);
//        shippingVo.setFirstPage(1);
//        shippingVo.setHasNextPage(false);
//        shippingVo.setHasPreviousPage(false);
//        shippingVo.setLastPage(false);
//        shippingVo.setLastPage(1);
//        shippingVo.setPageNum(1);
//        shippingVo.setPageSize(10);
//        //根据user_id获取shippingList
//       List<Shipping> shippingList=  iShippingDao.findAllShippings(order.getUser_id(), shippingVo.getPageSize(), shippingVo.getPages());
//        shippingVo.setList(shippingList);
//         shippingVo.setNavigatepageNums(1);
//         shippingVo.setStartRow(1);
//         //shipping总数
//      Integer count=  iShippingDao.countShippings(order.getUser_id());
//
//         shippingVo.setTotal(count);
//          orderVo.setShippingVo(shippingVo);

        //根据order_no 查询orderItem
        List<OrderItem> orderItemList = iOrderDao.findOrderItemsByOrderNo(order.getOrder_no());
        List<OrderItemVo> orderItemVoList = new ArrayList<>();


        for (OrderItem orderItem : orderItemList) {
            OrderItemVo orderItemVo = new OrderItemVo();
            orderItemVo = accembleOrderItemVo(orderItem);
            orderItemVoList.add(orderItemVo);

        }
        orderVo.setOrderItemVoList(orderItemVoList);

        return orderVo;

    }


    //将shipping转化成shippingVo

    private VOShipping accessbleShippingVo(Shipping shipping) {

        VOShipping voShipping = new VOShipping();
        voShipping.setReceiverName(shipping.getReceiver_name());
        voShipping.setReceiverAddress(shipping.getReceiver_adress());
        voShipping.setReceiverCity(shipping.getReceiver_city());
        voShipping.setReceiverDistrict(shipping.getReceiver_district());
        voShipping.setReceiverMobile(shipping.getReceiver_mobile());
        voShipping.setReceiverPhone(shipping.getReceiver_phone());
        voShipping.setReceiverZip(shipping.getReceiver_zip());
        voShipping.setReceiverProvince(shipping.getReceiver_province());


        return voShipping;
    }


    //将orderItem转化为OrderItemVo

    public OrderItemVo accembleOrderItemVo(OrderItem orderItem) {
        OrderItemVo orderItemVo = new OrderItemVo();

        orderItemVo.setCreate_time(DateUtil.dateChange(orderItem.getCreate_time()));
        orderItemVo.setProduct_name(orderItem.getProduct_name());
        orderItemVo.setProduct_id(orderItem.getProduct_id());
        orderItemVo.setProduct_image(orderItem.getProduct_image());
        orderItemVo.setQuantity(orderItem.getQuantity());
        orderItemVo.setTotal_price(orderItem.getTotal_price());
        orderItemVo.setOrder_no(orderItem.getOrder_no());
        orderItemVo.setCurrent_unit_price(orderItem.getCurrent_unit_price());
        return orderItemVo;
    }


    //扣除库存
    public void reduceStock(List<Cart> cartList) {
        for (Cart cart : cartList) {
            com.neuedu.pojo.Product product = iProduct.findProductById(cart.getProduct_id());
            product.setStock(product.getStock() - cart.getQuantity());
            iProduct.updateProduct(product);
        }


    }


    //创建订单的实体类
    private Order getOrder(Integer user_id, Integer shippingId) {
        Order order = new Order();
        order.setUser_id(user_id);
        order.setShipping_id(shippingId);
        order.setOrder_no(generateOrderNo());
        order.setPostage(0);
        order.setPayment_type(Const.PAYPLATFORM.ALIPAY.getStatus());
        order.setStatus(Const.alipay.WAIT_BUYER_PAY.getStatus());

        return order;
    }


    /**
     * 生成订单号
     */

    private Long generateOrderNo() {
        Long order_no = System.currentTimeMillis() + new Random().nextInt(100);


        return order_no;
    }

    /**
     * 计算总价
     */
    private BigDecimal getPayMent(List<OrderItem> orderItemList) {
        BigDecimal totalPrice = new BigDecimal(0);

        for (OrderItem orderItem : orderItemList) {
            totalPrice = BigdecimalUtil.add(totalPrice, orderItem.getTotal_price());
        }

        return totalPrice;

    }


    //将list<cart>转化为list<orderItem

    private ServerResponse getListOrderItem(Integer user_id, List<Cart> cartList) {
        //检验购物车是否有商品
        if (cartList == null || cartList.size() <= 0) {
            return ServerResponse.createServerResponse(ResponseCode.CART_IS_NULL.getCode(), ResponseCode.CART_IS_NULL.getMsg());
        }


        //遍历  进行转换

        List<OrderItem> orderItemList = new ArrayList<>();


        for (Cart cart : cartList) {
            OrderItem orderItem = new OrderItem();
            orderItem.setUser_id(user_id);
            orderItem.setProduct_id(cart.getProduct_id());

            //根据product_id查询product_name;
            com.neuedu.pojo.Product product = iProduct.findProductById(cart.getProduct_id());

            //判定商品是否存在或者是否在售
            if (product == null || product.getStatus() != 1) {

                return ServerResponse.createServerResponse(ResponseCode.PRODUCT_OFFLINE.getCode(), ResponseCode.PRODUCT_OFFLINE.getMsg());
            }

            //判断商品的库存是否充足
            if (product.getStock() < cart.getQuantity()) {

                return ServerResponse.createServerResponse(ResponseCode.PRODUCT_SHORT.getCode(), ResponseCode.PRODUCT_SHORT.getMsg());
            }

            orderItem.setProduct_name(product.getName());
            orderItem.setProduct_image(product.getMain_imag());
            orderItem.setCurrent_unit_price(product.getPrice());
            orderItem.setQuantity(cart.getQuantity());
            orderItem.setTotal_price(BigdecimalUtil.multi(product.getPrice(), new BigDecimal(cart.getQuantity())));


            orderItemList.add(orderItem);


        }

        return ServerResponse.createServerResponse(ResponseCode.CHANGE_SUCCESS.getCode(), orderItemList, ResponseCode.CHANGE_SUCCESS.getMsg());
    }


    // 支付
    @Override
    public ServerResponse findOrderByUserIdAndOrderNo(Long order_no, Integer user_id) {


        //先根据用户id和订单号查询订单
        Order order = iOrderDao.findOrderByUserIdAndOrderNo(user_id, order_no);

        if (order == null) {
            return ServerResponse.createServerResponse(ResponseCode.FIND_FAILD.getCode(), ResponseCode.FIND_FAILD.getMsg());
        }


        //进行支付


        // (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
        // 需保证商户系统端不能重复，建议通过数据库sequence生成，
        String outTradeNo = String.valueOf(order.getOrder_no());

        // (必填) 订单标题，粗略描述用户的支付目的。如“xxx品牌xxx门店当面付扫码消费”
        String subject = "订单号：" + order.getOrder_no() + "消费：" + order.getPayment() + "元";

        // (必填) 订单总金额，单位为元，不能超过1亿元
        // 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
        String totalAmount = String.valueOf(order.getPayment());

        // (可选) 订单不可打折金额，可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段
        // 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】
        String undiscountableAmount = "0";

        // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
        // 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
        String sellerId = "";

        // 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品2件共15.00元"
        String body = "购买商品3件共20.00元";

        // 商户操作员编号，添加此参数可以为商户操作员做销售统计
        String operatorId = "test_operator_id";

        // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
        String storeId = "test_store_id";

        // 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
        ExtendParams extendParams = new ExtendParams();
        extendParams.setSysServiceProviderId("2088100200300400500");

        // 支付超时，定义为120分钟
        String timeoutExpress = "120m";


        //根据订单编号查询订单明细
        List<OrderItem> itemList = iOrderDao.findOrderItemsByOrderNo(order_no);

        System.out.println(itemList + "========we空===========");


        // 商品明细列表，需填写购买商品详细信息，
        List<GoodsDetail> goodsDetailList = new ArrayList<GoodsDetail>();


        for (OrderItem orderItem : itemList) {
            System.out.println(orderItem.getTotal_price() + "=====orderItem.getTotal_price()=====");
            GoodsDetail goods1 = GoodsDetail.newInstance(String.valueOf(orderItem.getProduct_id()), orderItem.getProduct_name(),
                    BigdecimalUtil.multi(orderItem.getTotal_price(), new BigDecimal(100)).longValue(), orderItem.getQuantity());
            // 创建好一个商品后添加至商品明细列表
            goodsDetailList.add(goods1);


        }


        // 创建一个商品信息，参数含义分别为商品id（使用国标）、名称、单价（单位为分）、数量，如果需要添加商品类别，详见GoodsDetail
        GoodsDetail goods1 = GoodsDetail.newInstance("goods_id001", "xxx小面包", 1000, 1);
        // 创建好一个商品后添加至商品明细列表
        goodsDetailList.add(goods1);

        // 继续创建并添加第一条商品信息，用户购买的产品为“黑人牙刷”，单价为5.00元，购买了两件
        GoodsDetail goods2 = GoodsDetail.newInstance("goods_id002", "xxx牙刷", 500, 2);
        goodsDetailList.add(goods2);

        // 创建扫码支付请求builder，设置请求参数
        AlipayTradePrecreateRequestBuilder builder = new AlipayTradePrecreateRequestBuilder()
                .setSubject(subject).setTotalAmount(totalAmount).setOutTradeNo(outTradeNo)
                .setUndiscountableAmount(undiscountableAmount).setSellerId(sellerId).setBody(body)
                .setOperatorId(operatorId).setStoreId(storeId).setExtendParams(extendParams)
                .setTimeoutExpress(timeoutExpress)
                .setNotifyUrl("http://znaqzv.natappfree.cc/order/callback.do")//支付宝服务器主动通知商户服务器里指定的页面http路径,根据需要设置
                .setGoodsDetailList(goodsDetailList);

        AlipayF2FPrecreateResult result = tradeService.tradePrecreate(builder);
        switch (result.getTradeStatus()) {
            case SUCCESS:
                log.info("支付宝预下单成功: )");

                AlipayTradePrecreateResponse response = result.getResponse();
                dumpResponse(response);

                // 需要修改为运行机器上的路径
                String filePath = String.format("/G:/kuwo/qr-%s.png",
                        response.getOutTradeNo());
                log.info("filePath:" + filePath);
                ZxingUtils.getQRCodeImge(response.getQrCode(), 256, filePath);

                return ServerResponse.createServerResponse(ResponseCode.PAY_SUCCESSFUL.getCode(), String.format("http://localhost:8080/uploadpic/qr-%s.png",
                        response.getOutTradeNo()), ResponseCode.PAY_SUCCESSFUL.getMsg());


            case FAILED:
                log.error("支付宝预下单失败!!!");
                return ServerResponse.createServerResponse(ResponseCode.PAY_FAILD.getCode(), ResponseCode.PAY_FAILD.getMsg());

            case UNKNOWN:
                log.error("系统异常，预下单状态未知!!!");
                return ServerResponse.createServerResponse(ResponseCode.PAY_FAILD.getCode(), ResponseCode.PAY_FAILD.getMsg());

            default:
                log.error("不支持的交易状态，交易返回异常!!!");
                return ServerResponse.createServerResponse(ResponseCode.PAY_FAILD.getCode(), ResponseCode.PAY_FAILD.getMsg());
        }


    }

    @Override
    public String alipaycallBack(Map<String, String> map) {

        //查询订单   获取订单信息
        String order_no = map.get("out_trade_no");
        String status = map.get("trade_status");
        String trade_no = map.get("trade_no");
        Order order = iOrderDao.findOrderByOrderNo(Long.parseLong(order_no));
        System.out.println(order + "=======order== service=========");

        if (order != null) {//支付成功！
            if (status.equals(Const.alipay.TRADE_SUCCESS.getMsg())) {
                //修改订单状态
                int result = iOrderDao.updateOrderStatus(Long.valueOf(order_no), 20);
                System.out.println("修改订单状态的结果是：" + result);
            }

        }

        //将支付信息保存

        PayInfo payInfo = new PayInfo();
        payInfo.setUser_id(order.getUser_id());
        payInfo.setOrder_no(Long.valueOf(order_no));
        payInfo.setPay_platform(Const.PAYPLATFORM.ALIPAY.getStatus());
        payInfo.setPlatform_status(status);
        payInfo.setPlatform_number(trade_no);
        //添加支付信息
        int result = iOrderDao.addPayInfo(payInfo);
        System.out.println(result + "=======添加支付信息的结果是==========：");


        return "success";
    }


    private static Log log = LogFactory.getLog(Main.class);

    // 支付宝当面付2.0服务
    private static AlipayTradeService tradeService;

    // 支付宝当面付2.0服务（集成了交易保障接口逻辑）
    private static AlipayTradeService tradeWithHBService;

    // 支付宝交易保障接口服务，供测试接口api使用，请先阅读readme.txt
    private static AlipayMonitorService monitorService;

    static {
        /** 一定要在创建AlipayTradeService之前调用Configs.init()设置默认参数
         *  Configs会读取classpath下的zfbinfo.properties文件配置信息，如果找不到该文件则确认该文件是否在classpath目录
         */
        Configs.init("zfbinfo.properties");

        /** 使用Configs提供的默认参数
         *  AlipayTradeService可以使用单例或者为静态成员对象，不需要反复new
         */
        tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();

        // 支付宝当面付2.0服务（集成了交易保障接口逻辑）
        tradeWithHBService = new AlipayTradeWithHBServiceImpl.ClientBuilder().build();

        /** 如果需要在程序中覆盖Configs提供的默认参数, 可以使用ClientBuilder类的setXXX方法修改默认参数 否则使用代码中的默认设置 */
        monitorService = new AlipayMonitorServiceImpl.ClientBuilder()
                .setGatewayUrl("http://mcloudmonitor.com/gateway.do").setCharset("GBK")
                .setFormat("json").build();
    }

    // 简单打印应答
    private void dumpResponse(AlipayResponse response) {
        if (response != null) {
            log.info(String.format("code:%s, msg:%s", response.getCode(), response.getMsg()));
            if (StringUtils.isNotEmpty(response.getSubCode())) {
                log.info(String.format("subCode:%s, subMsg:%s", response.getSubCode(),
                        response.getSubMsg()));
            }
            log.info("body:" + response.getBody());
        }
    }

    public static void main(String[] args) {
        Main main = new Main();

        // 系统商商测试交易保障接口api
        //        main.test_monitor_sys();

        // POS厂商测试交易保障接口api
        //        main.test_monitor_pos();

        // 测试交易保障接口调度
        //        main.test_monitor_schedule_logic();

        // 测试当面付2.0支付（使用未集成交易保障接口的当面付2.0服务）
        //        main.test_trade_pay(tradeService);

        // 测试查询当面付2.0交易
        //        main.test_trade_query();

        // 测试当面付2.0退货
        //        main.test_trade_refund();

        // 测试当面付2.0生成支付二维码
        main.test_trade_precreate();
    }

    // 测试系统商交易保障调度
    public void test_monitor_schedule_logic() {
        // 启动交易保障线程
        DemoHbRunner demoRunner = new DemoHbRunner(monitorService);
        demoRunner.setDelay(5); // 设置启动后延迟5秒开始调度，不设置则默认3秒
        demoRunner.setDuration(10); // 设置间隔10秒进行调度，不设置则默认15 * 60秒
        demoRunner.schedule();

        // 启动当面付，此处每隔5秒调用一次支付接口，并且当随机数为0时交易保障线程退出
        while (Math.random() != 0) {
            test_trade_pay(tradeWithHBService);
            Utils.sleep(5 * 1000);
        }

        // 满足退出条件后可以调用shutdown优雅安全退出
        demoRunner.shutdown();
    }

    // 系统商的调用样例，填写了所有系统商商需要填写的字段
    public void test_monitor_sys() {
        // 系统商使用的交易信息格式，json字符串类型
        List<SysTradeInfo> sysTradeInfoList = new ArrayList<SysTradeInfo>();
        sysTradeInfoList.add(SysTradeInfo.newInstance("00000001", 5.2, HbStatus.S));
        sysTradeInfoList.add(SysTradeInfo.newInstance("00000002", 4.4, HbStatus.F));
        sysTradeInfoList.add(SysTradeInfo.newInstance("00000003", 11.3, HbStatus.P));
        sysTradeInfoList.add(SysTradeInfo.newInstance("00000004", 3.2, HbStatus.X));
        sysTradeInfoList.add(SysTradeInfo.newInstance("00000005", 4.1, HbStatus.X));

        // 填写异常信息，如果有的话
        List<ExceptionInfo> exceptionInfoList = new ArrayList<ExceptionInfo>();
        exceptionInfoList.add(ExceptionInfo.HE_SCANER);
        //        exceptionInfoList.add(ExceptionInfo.HE_PRINTER);
        //        exceptionInfoList.add(ExceptionInfo.HE_OTHER);

        // 填写扩展参数，如果有的话
        Map<String, Object> extendInfo = new HashMap<String, Object>();
        //        extendInfo.put("SHOP_ID", "BJ_ZZ_001");
        //        extendInfo.put("TERMINAL_ID", "1234");

        String appAuthToken = "应用授权令牌";//根据真实值填写

        AlipayHeartbeatSynRequestBuilder builder = new AlipayHeartbeatSynRequestBuilder()
                .setAppAuthToken(appAuthToken).setProduct(Product.FP).setType(Type.CR)
                .setEquipmentId("cr1000001").setEquipmentStatus(EquipStatus.NORMAL)
                .setTime(Utils.toDate(new Date())).setStoreId("store10001").setMac("0a:00:27:00:00:00")
                .setNetworkType("LAN").setProviderId("2088911212323549") // 设置系统商pid
                .setSysTradeInfoList(sysTradeInfoList) // 系统商同步trade_info信息
                //                .setExceptionInfoList(exceptionInfoList)  // 填写异常信息，如果有的话
                .setExtendInfo(extendInfo) // 填写扩展信息，如果有的话
                ;

        MonitorHeartbeatSynResponse response = monitorService.heartbeatSyn(builder);
        dumpResponse(response);
    }

    // POS厂商的调用样例，填写了所有pos厂商需要填写的字段
    public void test_monitor_pos() {
        // POS厂商使用的交易信息格式，字符串类型
        List<PosTradeInfo> posTradeInfoList = new ArrayList<PosTradeInfo>();
        posTradeInfoList.add(PosTradeInfo.newInstance(HbStatus.S, "1324", 7));
        posTradeInfoList.add(PosTradeInfo.newInstance(HbStatus.X, "1326", 15));
        posTradeInfoList.add(PosTradeInfo.newInstance(HbStatus.S, "1401", 8));
        posTradeInfoList.add(PosTradeInfo.newInstance(HbStatus.F, "1405", 3));

        // 填写异常信息，如果有的话
        List<ExceptionInfo> exceptionInfoList = new ArrayList<ExceptionInfo>();
        exceptionInfoList.add(ExceptionInfo.HE_PRINTER);

        // 填写扩展参数，如果有的话
        Map<String, Object> extendInfo = new HashMap<String, Object>();
        //        extendInfo.put("SHOP_ID", "BJ_ZZ_001");
        //        extendInfo.put("TERMINAL_ID", "1234");

        AlipayHeartbeatSynRequestBuilder builder = new AlipayHeartbeatSynRequestBuilder()
                .setProduct(Product.FP)
                .setType(Type.SOFT_POS)
                .setEquipmentId("soft100001")
                .setEquipmentStatus(EquipStatus.NORMAL)
                .setTime("2015-09-28 11:14:49")
                .setManufacturerPid("2088000000000009")
                // 填写机具商的支付宝pid
                .setStoreId("store200001").setEquipmentPosition("31.2433190000,121.5090750000")
                .setBbsPosition("2869719733-065|2896507033-091").setNetworkStatus("gggbbbgggnnn")
                .setNetworkType("3G").setBattery("98").setWifiMac("0a:00:27:00:00:00")
                .setWifiName("test_wifi_name").setIp("192.168.1.188")
                .setPosTradeInfoList(posTradeInfoList) // POS厂商同步trade_info信息
                //                .setExceptionInfoList(exceptionInfoList) // 填写异常信息，如果有的话
                .setExtendInfo(extendInfo) // 填写扩展信息，如果有的话
                ;

        MonitorHeartbeatSynResponse response = monitorService.heartbeatSyn(builder);
        dumpResponse(response);
    }

    // 测试当面付2.0支付
    public void test_trade_pay(AlipayTradeService service) {
        // (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
        // 需保证商户系统端不能重复，建议通过数据库sequence生成，
        String outTradeNo = "tradepay" + System.currentTimeMillis()
                + (long) (Math.random() * 10000000L);

        // (必填) 订单标题，粗略描述用户的支付目的。如“xxx品牌xxx门店消费”
        String subject = "xxx品牌xxx门店当面付消费";

        // (必填) 订单总金额，单位为元，不能超过1亿元
        // 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
        String totalAmount = "0.01";

        // (必填) 付款条码，用户支付宝钱包手机app点击“付款”产生的付款条码
        String authCode = "用户自己的支付宝付款码"; // 条码示例，286648048691290423
        // (可选，根据需要决定是否使用) 订单可打折金额，可以配合商家平台配置折扣活动，如果订单部分商品参与打折，可以将部分商品总价填写至此字段，默认全部商品可打折
        // 如果该值未传入,但传入了【订单总金额】,【不可打折金额】 则该值默认为【订单总金额】- 【不可打折金额】
        //        String discountableAmount = "1.00"; //

        // (可选) 订单不可打折金额，可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段
        // 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】
        String undiscountableAmount = "0.0";

        // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
        // 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
        String sellerId = "";

        // 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品3件共20.00元"
        String body = "购买商品3件共20.00元";

        // 商户操作员编号，添加此参数可以为商户操作员做销售统计
        String operatorId = "test_operator_id";

        // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
        String storeId = "test_store_id";

        // 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
        String providerId = "2088100200300400500";
        ExtendParams extendParams = new ExtendParams();
        extendParams.setSysServiceProviderId(providerId);

        // 支付超时，线下扫码交易定义为5分钟
        String timeoutExpress = "5m";

        // 商品明细列表，需填写购买商品详细信息，
        List<GoodsDetail> goodsDetailList = new ArrayList<GoodsDetail>();
        // 创建一个商品信息，参数含义分别为商品id（使用国标）、名称、单价（单位为分）、数量，如果需要添加商品类别，详见GoodsDetail
        GoodsDetail goods1 = GoodsDetail.newInstance("goods_id001", "xxx面包", 1000, 1);
        // 创建好一个商品后添加至商品明细列表
        goodsDetailList.add(goods1);

        // 继续创建并添加第一条商品信息，用户购买的产品为“黑人牙刷”，单价为5.00元，购买了两件
        GoodsDetail goods2 = GoodsDetail.newInstance("goods_id002", "xxx牙刷", 500, 2);
        goodsDetailList.add(goods2);

        String appAuthToken = "应用授权令牌";//根据真实值填写

        // 创建条码支付请求builder，设置请求参数
        AlipayTradePayRequestBuilder builder = new AlipayTradePayRequestBuilder()
                //            .setAppAuthToken(appAuthToken)
                .setOutTradeNo(outTradeNo).setSubject(subject).setAuthCode(authCode)
                .setTotalAmount(totalAmount).setStoreId(storeId)
                .setUndiscountableAmount(undiscountableAmount).setBody(body).setOperatorId(operatorId)
                .setExtendParams(extendParams).setSellerId(sellerId)
                .setGoodsDetailList(goodsDetailList).setTimeoutExpress(timeoutExpress);

        // 调用tradePay方法获取当面付应答
        AlipayF2FPayResult result = service.tradePay(builder);
        switch (result.getTradeStatus()) {
            case SUCCESS:
                log.info("支付宝支付成功: )");
                break;

            case FAILED:
                log.error("支付宝支付失败!!!");
                break;

            case UNKNOWN:
                log.error("系统异常，订单状态未知!!!");
                break;

            default:
                log.error("不支持的交易状态，交易返回异常!!!");
                break;
        }
    }

    // 测试当面付2.0查询订单
    public void test_trade_query() {
        // (必填) 商户订单号，通过此商户订单号查询当面付的交易状态
        String outTradeNo = "tradepay14817938139942440181";

        // 创建查询请求builder，设置请求参数
        AlipayTradeQueryRequestBuilder builder = new AlipayTradeQueryRequestBuilder()
                .setOutTradeNo(outTradeNo);

        AlipayF2FQueryResult result = tradeService.queryTradeResult(builder);
        switch (result.getTradeStatus()) {
            case SUCCESS:
                log.info("查询返回该订单支付成功: )");

                AlipayTradeQueryResponse response = result.getResponse();
                dumpResponse(response);

                log.info(response.getTradeStatus());
                if (Utils.isListNotEmpty(response.getFundBillList())) {
                    for (TradeFundBill bill : response.getFundBillList()) {
                        log.info(bill.getFundChannel() + ":" + bill.getAmount());
                    }
                }
                break;

            case FAILED:
                log.error("查询返回该订单支付失败或被关闭!!!");
                break;

            case UNKNOWN:
                log.error("系统异常，订单支付状态未知!!!");
                break;

            default:
                log.error("不支持的交易状态，交易返回异常!!!");
                break;
        }
    }

    // 测试当面付2.0退款
    public void test_trade_refund() {
        // (必填) 外部订单号，需要退款交易的商户外部订单号
        String outTradeNo = "tradepay14817938139942440181";

        // (必填) 退款金额，该金额必须小于等于订单的支付金额，单位为元
        String refundAmount = "0.01";

        // (可选，需要支持重复退货时必填) 商户退款请求号，相同支付宝交易号下的不同退款请求号对应同一笔交易的不同退款申请，
        // 对于相同支付宝交易号下多笔相同商户退款请求号的退款交易，支付宝只会进行一次退款
        String outRequestNo = "";

        // (必填) 退款原因，可以说明用户退款原因，方便为商家后台提供统计
        String refundReason = "正常退款，用户买多了";

        // (必填) 商户门店编号，退款情况下可以为商家后台提供退款权限判定和统计等作用，详询支付宝技术支持
        String storeId = "test_store_id";

        // 创建退款请求builder，设置请求参数
        AlipayTradeRefundRequestBuilder builder = new AlipayTradeRefundRequestBuilder()
                .setOutTradeNo(outTradeNo).setRefundAmount(refundAmount).setRefundReason(refundReason)
                .setOutRequestNo(outRequestNo).setStoreId(storeId);

        AlipayF2FRefundResult result = tradeService.tradeRefund(builder);
        switch (result.getTradeStatus()) {
            case SUCCESS:
                log.info("支付宝退款成功: )");
                break;

            case FAILED:
                log.error("支付宝退款失败!!!");
                break;

            case UNKNOWN:
                log.error("系统异常，订单退款状态未知!!!");
                break;

            default:
                log.error("不支持的交易状态，交易返回异常!!!");
                break;
        }
    }

//    // 测试当面付2.0生成支付二维码
//    public void test_trade_precreate() {
//
//    }

}
