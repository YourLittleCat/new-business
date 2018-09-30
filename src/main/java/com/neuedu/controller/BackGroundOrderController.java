package com.neuedu.controller;


import com.neuedu.comment.Const;
import com.neuedu.comment.ResponseCode;
import com.neuedu.comment.ServerResponse;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IOrderService;
import com.neuedu.vo.OrderPageModel;
import com.neuedu.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/manage/order")
public class BackGroundOrderController {

    @Autowired
    IOrderService iOrderService;

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

        if (userInfo.getRole() != 0) {


            return ServerResponse.createServerResponse(ResponseCode.OUT_OF_AUTHORITY.getCode(), ResponseCode.OUT_OF_AUTHORITY.getMsg());
        }


        return iOrderService.allorderlist(userInfo.getId(), pageSize, pageNum);
    }


    //后台根据订单号模糊查询订单--->返回一个orderPageModel


    @RequestMapping("/search.do")
    public ServerResponse<OrderPageModel> findListByOrderNo(Long order_no, HttpSession session,
                                                            @RequestParam(defaultValue = "1") Integer pageNum,
                                                            @RequestParam(defaultValue = "10") Integer pageSize) {
        //是否登录的验证
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        //是否登录
        if (userInfo == null) {
            //    throw BusinessException.creatBusinessException(session, "未登录或者登录已过期，请重新登录！", "3秒后将自动跳转到登录页面...", "login.jsp");
            return ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }

        return iOrderService.findListByOrderNo(userInfo.getId(), order_no, pageSize, pageNum);

    }


    //后台订单详情


    @RequestMapping("/detail.do")
    public ServerResponse<OrderVo> detail(Long order_no, HttpSession session) {
               //是否登录的验证
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        //是否登录
        if (userInfo == null) {
            //    throw BusinessException.creatBusinessException(session, "未登录或者登录已过期，请重新登录！", "3秒后将自动跳转到登录页面...", "login.jsp");
            return ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }

        if (userInfo.getRole() != 0) {

            return ServerResponse.createServerResponse(ResponseCode.OUT_OF_AUTHORITY.getCode(), ResponseCode.OUT_OF_AUTHORITY.getMsg());
        }


        return iOrderService.detail(userInfo.getId(), order_no);
    }
}
