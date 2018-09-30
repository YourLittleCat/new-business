package com.neuedu.controller.frontController;


import com.neuedu.comment.Const;
import com.neuedu.comment.ResponseCode;
import com.neuedu.comment.ServerResponse;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.ICartService;
import com.neuedu.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    ICartService iCartService;


    //添加商品到购物车

    @RequestMapping("/add")
    public ServerResponse<CartVo> addProductToCart(Integer productId, Integer count, HttpSession session) {

        //是否登录的验证
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        //是否登录
        if (userInfo == null) {
            //    throw BusinessException.creatBusinessException(session, "未登录或者登录已过期，请重新登录！", "3秒后将自动跳转到登录页面...", "login.jsp");
            return ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }


        return iCartService.addProductTocart(userInfo.getId(), productId, count);


    }


    //查看用户的购物车

    @RequestMapping("/checkCart")
    public ServerResponse<CartVo> checkCart(HttpSession session) {
        //是否登录的验证
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        //是否登录
        if (userInfo == null) {
            //    throw BusinessException.creatBusinessException(session, "未登录或者登录已过期，请重新登录！", "3秒后将自动跳转到登录页面...", "login.jsp");
            return ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }


        return iCartService.checkCart(userInfo.getId());

    }


    //移除购物车的物品

    @RequestMapping("/delete_product")
    public ServerResponse<CartVo> deleteProduct(HttpSession session, Integer productId) {
        //是否登录的验证
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        //是否登录
        if (userInfo == null) {
            //    throw BusinessException.creatBusinessException(session, "未登录或者登录已过期，请重新登录！", "3秒后将自动跳转到登录页面...", "login.jsp");
            return ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }

        return iCartService.deleteProduct(userInfo.getId(), productId);


    }


    //更新购物车商品的数量
        @RequestMapping("/updateQuantity")
    public ServerResponse<CartVo> updateProductQuantity(HttpSession session, Integer productId, Integer count) {
        //是否登录的验证
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        //是否登录
        if (userInfo == null) {
            //    throw BusinessException.creatBusinessException(session, "未登录或者登录已过期，请重新登录！", "3秒后将自动跳转到登录页面...", "login.jsp");
            return ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }

        return iCartService.updateProductQuantity(userInfo.getId(), count, productId);

    }


    //选中某个商品
    @RequestMapping("/checked")
public  ServerResponse<CartVo> checkProduct(HttpSession session,Integer productId){
    //是否登录的验证
    UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
    //是否登录
    if (userInfo == null) {
        //    throw BusinessException.creatBusinessException(session, "未登录或者登录已过期，请重新登录！", "3秒后将自动跳转到登录页面...", "login.jsp");
        return ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
    }

    return  iCartService.checkedProduct(userInfo.getId(), productId );



}


    @RequestMapping("/unChecked")
    public  ServerResponse<CartVo> unCheckProduct(HttpSession session,Integer productId){
        //是否登录的验证
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        //是否登录
        if (userInfo == null) {
            //    throw BusinessException.creatBusinessException(session, "未登录或者登录已过期，请重新登录！", "3秒后将自动跳转到登录页面...", "login.jsp");
            return ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }

        return  iCartService.unCheckedProduct(userInfo.getId(), productId );



    }

    //查询所有商品的数量
    @RequestMapping("/productNum")
    public  ServerResponse<Integer> addProductNumber(HttpSession session){
        //是否登录的验证
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        //是否登录
        if (userInfo == null) {
            //    throw BusinessException.creatBusinessException(session, "未登录或者登录已过期，请重新登录！", "3秒后将自动跳转到登录页面...", "login.jsp");
            return ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }

        return iCartService.addProductNum(userInfo.getId());

    }

    //全选
    @RequestMapping("/allCheck")
    public ServerResponse<CartVo> allCheck(HttpSession session){
        //是否登录的验证
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        //是否登录
        if (userInfo == null) {
            //    throw BusinessException.creatBusinessException(session, "未登录或者登录已过期，请重新登录！", "3秒后将自动跳转到登录页面...", "login.jsp");
            return ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }


       return iCartService.allCheck(userInfo.getId());


    }


    //全不选
    @RequestMapping("/allUnCheck")
    public ServerResponse<CartVo> allUnCheck(HttpSession session){
        //是否登录的验证
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        //是否登录
        if (userInfo == null) {
            //    throw BusinessException.creatBusinessException(session, "未登录或者登录已过期，请重新登录！", "3秒后将自动跳转到登录页面...", "login.jsp");
            return ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }


        return iCartService.allUnCheck(userInfo.getId());


    }



}
