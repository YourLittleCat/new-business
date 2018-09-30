package com.neuedu.controller.frontController;

import com.neuedu.comment.Const;
import com.neuedu.comment.ResponseCode;
import com.neuedu.comment.ServerResponse;
import com.neuedu.pojo.Shipping;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IShippingService;
import com.neuedu.vo.ShippingVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/shipping")
public class ShippingController {
    @Autowired
    IShippingService shippingService;


    @RequestMapping("/add")
    public ServerResponse<Shipping> add(HttpSession session ,Shipping shipping ) {
        //校验是否登录
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);

        if (userInfo == null) {
            return ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }

        //校验是否为空
        if (shipping.getReceiver_adress() == null || shipping.getReceiver_adress().equals("") || shipping.getReceiver_city() == null || shipping.getReceiver_city().equals("") || shipping.getReceiver_mobile() == null || shipping.getReceiver_mobile().equals("") || shipping.getReceiver_name() == null || shipping.getReceiver_name().equals("") || shipping.getReceiver_phone() == null || shipping.getReceiver_phone().equals("") || shipping.getReceiver_province() == null || shipping.getReceiver_province().equals("") || shipping.getReceiver_zip() == null || shipping.getReceiver_zip().equals("")
                ) {
            return ServerResponse.createServerResponse(ResponseCode.PARAMETER_NOT_NULL.getCode(), ResponseCode.PARAMETER_NOT_NULL.getMsg());

        }
         shipping.setUser_id(userInfo.getId());

        return shippingService.addShipping(shipping);

//userInfo.getId(), shipping.getReceiver_name(), shipping.getReceiver_phone(), shipping.getReceiver_mobile(), shipping.getReceiver_province(), shipping.getReceiver_city(), shipping.getReceiver_adress(), shipping.getReceiver_zip()
    }

    @RequestMapping("/delete")
    public ServerResponse<Integer> delete(HttpSession session, Integer shippingId) {
        //校验是否登录
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);

        if (userInfo == null) {
            return ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }
        if (shippingId == null || shippingId.equals("")) {
            return ServerResponse.createServerResponse(ResponseCode.SHIPPING_ID.getCode(), ResponseCode.SHIPPING_ID.getMsg());
        }

        return shippingService.deleteShipping(userInfo.getId(), shippingId);

    }


    @RequestMapping("/update")
    public ServerResponse<Integer> update(Integer id,@RequestParam(required = false) String receiverName, @RequestParam(required = false) String receiverPhone, @RequestParam(required = false) String receiverMobile, @RequestParam(required = false) String receiverProvince, @RequestParam(required = false) String receiverCity, @RequestParam(required = false) String receiverAdress, @RequestParam(required = false) String receiverZip, HttpSession session) {
        //校验是否登录
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);

        if (userInfo == null) {
            return ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }
        if (id== null||id.equals("")){

            return ServerResponse.createServerResponse(ResponseCode.SHIPPING_ID.getCode(),ResponseCode.SHIPPING_ID.getMsg());
        }



        return shippingService.updateShipping(id,userInfo.getId(), receiverName, receiverPhone, receiverMobile, receiverProvince, receiverCity, receiverAdress, receiverZip);


    }


    //查看选中的地址    根据shippingId查看地址

    @RequestMapping("/findShipping")
    public  ServerResponse<Shipping> checkShippingByShippingId(HttpSession session,Integer shippingId){
        //校验是否登录
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);

        if (userInfo == null) {
            return ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }
        if (shippingId == null || shippingId.equals("")) {
            return ServerResponse.createServerResponse(ResponseCode.SHIPPING_ID.getCode(), ResponseCode.SHIPPING_ID.getMsg());
        }


       return    shippingService.findShippingByShippingId(shippingId, userInfo.getId());


    }


    //根据用户id查找所有的shipping

    @RequestMapping("/findAll")
    public ServerResponse<ShippingVo> findAllShippings(HttpSession session,
                                                       @RequestParam(defaultValue ="1" ) Integer pageNum,
                                                       @RequestParam(defaultValue = "10") Integer pageSize){
        //校验是否登录
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);

        if (userInfo == null) {
            return ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }


        return shippingService.findAllShippings(userInfo.getId(), pageNum, pageSize);

    }



}
