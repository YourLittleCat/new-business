package com.neuedu.controller.frontController;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.demo.trade.config.Configs;
import com.neuedu.comment.Const;
import com.neuedu.comment.ResponseCode;
import com.neuedu.comment.ServerResponse;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IOrderService;
import com.neuedu.vo.OrderPageModel;
import com.neuedu.vo.OrderProductVo;
import com.neuedu.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    IOrderService iOrderService;

    //支付接口
    @RequestMapping("/pay.do")
    public ServerResponse pay(Long order_no, HttpSession session) {

        //是否登录的验证
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        //是否登录
        if (userInfo == null) {
            //    throw BusinessException.creatBusinessException(session, "未登录或者登录已过期，请重新登录！", "3秒后将自动跳转到登录页面...", "login.jsp");
            return ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }

        if (order_no == null || order_no.equals("")) {
            return ServerResponse.createServerResponse(ResponseCode.ORDER_NUMBER_NOT_NULL.getCode(), ResponseCode.ORDER_NUMBER_NOT_NULL.getMsg());

        }

        return iOrderService.findOrderByUserIdAndOrderNo(order_no, userInfo.getId());

    }

    //支付完的回调
    @RequestMapping("/callback.do")
    public String alipay_callback(HttpServletRequest request) {

        //将所有的返回的信息都封装在map中  需要对map进行遍历将封装的信息取出
        Map<String, String[]> map = request.getParameterMap();

        Map<String, String> param = new HashMap<>();


        //遍历map
        for (Iterator<String> iterator = map.keySet().iterator(); iterator.hasNext(); ) {
            //将遍历的赋值给key
            String key = iterator.next();
            //此时的key是一个长字符串 通过map将其取出  写进数组
            String[] keys = map.get(key);

            //new一个string类型的参数接收值
            String value = "";
            //对数组进行遍历   将keys的值取出来之后再放到另外一个map集合中
            for (int i = 0; i < keys.length; i++) {

                value = (i == keys.length - 1 ? value + keys[i] : value + keys[i] + ",");

            }
            //再放到map集合中
            param.put(key, value);


        }

        //进行支付宝的验签操作

        try {
            param.remove("sign_type");

            boolean result = AlipaySignature.rsaCheckV2(param, Configs.getAlipayPublicKey(), "utf-8", Configs.getSignType());
            System.out.println(result + "=========result==========");

            //如果返回值为true才说明是支付宝请求的

            if (result) {

                return iOrderService.alipaycallBack(param);

            } else {

                return "faild";
            }


        } catch (AlipayApiException e) {
            e.printStackTrace();
        }


        return "faild";

    }


    //创建订单接口

    @RequestMapping("/creatOrder.do")
    public ServerResponse creatOrder(HttpSession session, Integer shippingId) {
        //是否登录的验证
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        //是否登录
        if (userInfo == null) {
            //    throw BusinessException.creatBusinessException(session, "未登录或者登录已过期，请重新登录！", "3秒后将自动跳转到登录页面...", "login.jsp");
            return ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }

        return iOrderService.creatOrder(userInfo.getId(), shippingId);

    }

    //查询订单状态
    @RequestMapping("/orderStatus.do")
    public ServerResponse orderStatus(HttpSession session, Long order_no) {

        //是否登录的验证
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        //是否登录
        if (userInfo == null) {
            //    throw BusinessException.creatBusinessException(session, "未登录或者登录已过期，请重新登录！", "3秒后将自动跳转到登录页面...", "login.jsp");
            return ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }


        return iOrderService.orderStatus(userInfo.getId(), order_no);
    }


    //取消订单
    @RequestMapping("/cancleOrder")
    public ServerResponse cancleOrder(HttpSession session, Long order_no) {
        //是否登录的验证
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        //是否登录
        if (userInfo == null) {
            //    throw BusinessException.creatBusinessException(session, "未登录或者登录已过期，请重新登录！", "3秒后将自动跳转到登录页面...", "login.jsp");
            return ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }

        return iOrderService.cancleOrder(userInfo.getId(), order_no);

    }


    //获取订单中商品的详情
    @RequestMapping("/get_order_cart_product.do")
    public ServerResponse<OrderProductVo> getOrderProductDetail(HttpSession session) {
        //是否登录的验证
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        //是否登录
        if (userInfo == null) {
            //    throw BusinessException.creatBusinessException(session, "未登录或者登录已过期，请重新登录！", "3秒后将自动跳转到登录页面...", "login.jsp");
            return ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }

        return iOrderService.getOrderProductDetail(userInfo.getId());

    }

    //根据order_no 查找订单详情

    @RequestMapping("/detail")
    public ServerResponse<OrderVo> detail(HttpSession session, Long order_no) {
        //是否登录的验证
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        //是否登录
        if (userInfo == null) {
            //    throw BusinessException.creatBusinessException(session, "未登录或者登录已过期，请重新登录！", "3秒后将自动跳转到登录页面...", "login.jsp");
            return ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }


        return iOrderService.frontDetail(userInfo.getId(), order_no);

    }


    //发货---》修改状态、修改发货时间


    @RequestMapping("/send_goods.do")
    public ServerResponse sendGoods(HttpSession session, Long order_no) {


        //是否登录的验证
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        //是否登录
        if (userInfo == null) {
            //    throw BusinessException.creatBusinessException(session, "未登录或者登录已过期，请重新登录！", "3秒后将自动跳转到登录页面...", "login.jsp");
            return ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }

        //是否有修改的权限

        if (userInfo.getRole() != 0) {
            return ServerResponse.createServerResponse(ResponseCode.OUT_OF_AUTHORITY.getCode(), ResponseCode.OUT_OF_AUTHORITY.getMsg());

        }

        return iOrderService.sendGoods(userInfo.getId(), order_no);


    }

    //前台orderlist查询
    @RequestMapping("/list.do")
    public ServerResponse<OrderPageModel> orderlist(@RequestParam(defaultValue = "10") Integer pageSize,
                                                    @RequestParam(defaultValue = "1") Integer pageNum,
                                                    HttpSession session) {
        //是否登录的验证
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        //是否登录
        if (userInfo == null) {
            //    throw BusinessException.creatBusinessException(session, "未登录或者登录已过期，请重新登录！", "3秒后将自动跳转到登录页面...", "login.jsp");
            return ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }

        return iOrderService.orderlist(userInfo.getId(), pageSize, pageNum);


    }





}
