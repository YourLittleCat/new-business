package com.neuedu.service.impl;

import com.neuedu.Dao.IShippingDao;
import com.neuedu.Dao.impl.ShippingDaoImp;
import com.neuedu.comment.ResponseCode;
import com.neuedu.comment.ServerResponse;
import com.neuedu.pojo.Shipping;
import com.neuedu.service.IShippingService;
import com.neuedu.vo.ShippingVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class ShippingServiceImp implements IShippingService {
    @Autowired
    IShippingDao shippingDao;


    //添加地址
    @Override
    public ServerResponse<Shipping> addShipping(Shipping shipping) {

        int result = shippingDao.addShipping(shipping);
        if (result > 0) {
            Shipping shipping1 =new Shipping();
            shipping1.setId(shipping.getId());

            System.out.println(shipping.getId()+"===========================");
            return ServerResponse.createServerResponse(ResponseCode.ADD_SUCCESS.getCode(),shipping1,ResponseCode.ADD_SUCCESS.getMsg());

        } else {

            return ServerResponse.createServerResponse(ResponseCode.ADD_FAILD.getCode(), ResponseCode.ADD_FAILD.getMsg());

        }
    }

    @Override
    public ServerResponse<Integer> deleteShipping(Integer user_id, Integer shippingId) {
        int result = shippingDao.deleteShipping(user_id, shippingId);

        if (result > 0) {

            return ServerResponse.createServerResponse(ResponseCode.DELETE_SHIPPING_SUCCESS.getCode(), ResponseCode.DELETE_SHIPPING_SUCCESS.getMsg());
        } else {

            return ServerResponse.createServerResponse(ResponseCode.DELETE_SHIPPING_FAILD.getCode(), ResponseCode.DELETE_SHIPPING_FAILD.getMsg());

        }
    }

    @Override
    public ServerResponse<Integer> updateShipping(Integer id, Integer userId, String receiverName, String receiverPhone, String receiverMobile, String receiverProvince, String receiverCity, String receiverAdress, String receiverZip) {
        Shipping shipping = new Shipping();
        shipping.setId(id);
        shipping.setUser_id(userId);
        shipping.setReceiver_name(receiverName);
        shipping.setReceiver_phone(receiverPhone);
        shipping.setReceiver_province(receiverProvince);
        shipping.setReceiver_mobile(receiverMobile);
        shipping.setReceiver_city(receiverCity);
        shipping.setReceiver_adress(receiverAdress);
        shipping.setReceiver_zip(receiverZip);

        int result = shippingDao.updateShipping(shipping);

        if (result > 0) {

            return ServerResponse.createServerResponse(ResponseCode.UPDATE_SUCCESS.getCode(), ResponseCode.UPDATE_SUCCESS.getMsg());
        } else {

            return ServerResponse.createServerResponse(ResponseCode.UPDATE_FAILD.getCode(), ResponseCode.UPDATE_FAILD.getMsg());

        }


    }

    @Override
    public ServerResponse<Shipping> findShippingByShippingId(Integer shippingId, Integer user_id) {
        Shipping shipping = shippingDao.findShippingByShippingId(user_id, shippingId);

        if (shipping != null) {

            return ServerResponse.createServerResponse(ResponseCode.FIND_SUCCESS.getCode(), shipping, ResponseCode.FIND_SUCCESS.getMsg());
        } else {


            return ServerResponse.createServerResponse(ResponseCode.FIND_FAILD.getCode(), ResponseCode.FIND_FAILD.getMsg());
        }
    }


    //根据user_id查找shipping  并返回一个shipping的VO

    /*
     * pageNo  查询第几页
     *
     * pageSize  每页的查询的数量
     * */
    @Override
    public ServerResponse<ShippingVo> findAllShippings(Integer user_id, Integer pageNum, Integer pageSize) {
        ShippingVo shippingVo = new ShippingVo();
        List<Shipping> shippingList = shippingDao.findAllShippings(user_id, pageSize, pageNum);
        if (shippingList != null && shippingList.size() > 0) {
            shippingVo.setPageNum(pageNum);
            shippingVo.setPageSize(pageSize);
            shippingVo.setSize(shippingList.size());
            shippingVo.setTotal(shippingList.size());
            shippingVo.setPages(shippingList.size() / pageSize + 1);
            shippingVo.setList(shippingList);
            //如果查询的页面等于1  那就是首页   true   否则则false
            if (pageNum == 1) {
                shippingVo.setFirstPage(true);

            } else {
                shippingVo.setFirstPage(false);
            }

            //如果查询的页面等于总的页数  那么就是末页  true  否则是false
            if (pageNum==shippingVo.getPages()){
                shippingVo.setLastPage(true);
            }else {
                shippingVo.setLastPage(false);
            }

            shippingVo.setFirstPage(1);
            shippingVo.setLastPage(shippingVo.getPages());



            return ServerResponse.createServerResponse(ResponseCode.FIND_SUCCESS.getCode(), shippingVo, ResponseCode.FIND_SUCCESS.getMsg());
        }


        return ServerResponse.createServerResponse(ResponseCode.FIND_FAILD.getCode(), ResponseCode.FIND_FAILD.getMsg());
    }



}



