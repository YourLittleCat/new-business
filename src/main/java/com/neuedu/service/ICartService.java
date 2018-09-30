package com.neuedu.service;

import com.neuedu.comment.ServerResponse;
import com.neuedu.vo.CartProductVo;
import com.neuedu.vo.CartVo;

import javax.xml.ws.Service;
import java.util.List;

public interface ICartService {

    //增加商品到购物车
    public ServerResponse<CartVo> addProductTocart(Integer userId,Integer productId,Integer count);


    //查看用户的购物车


    public ServerResponse<CartVo> checkCart(Integer userId);


    //移除购物车中的商品

    public  ServerResponse<CartVo> deleteProduct(Integer userId,Integer productId);


    //修改购物车商品的数量


    public  ServerResponse<CartVo> updateProductQuantity(Integer user_id,Integer count ,Integer product_Id);

    //设置商品选中

    public  ServerResponse<CartVo> checkedProduct(Integer user_id,Integer product_id);


    //设置商品未选中

    public  ServerResponse<CartVo> unCheckedProduct(Integer user_id,Integer product_id);

    //查询所有的商品数量之和


    public  ServerResponse<Integer> addProductNum(Integer user_id);


    //设置全选
    public  ServerResponse<CartVo> allCheck(Integer user_id);

   //取消全选
   public  ServerResponse<CartVo> allUnCheck(Integer user_id);

}
