package com.neuedu.Dao;

import com.neuedu.pojo.Cart;
import com.neuedu.pojo.Product;
import sun.font.CompositeGlyphMapper;

import java.util.List;

public interface ICartDao {

    //根据userId和productId来查询购物车


    public Cart findCartByUserIdAndProductId(Integer user_id,Integer product_id);


    //将购物车添加到表中

    public Integer addProductToCart(Cart cart);



//更新购物车

    public  Integer updateCart(Cart cart);

 //根据user_Id查询cart


    public List<Cart> findCartsByUserId(Integer user_id);


    //根据用户Id查询  是否全选  如果全选  返回0


    public Integer isCheckAll(Integer user_id);


    //移除购物车中的商品

    public Integer deleteProduct(Cart cart);

    //选中商品

    public Integer checkedProduct(Integer user_id,Integer product_id);


    //未选中商品

    public Integer unCheckedProduct(Integer user_id,Integer product_id);


    //根据用户id查询用户选中的购物车中的商品

    public  List<Cart> findCartsCheckedByUserId(Integer user_id);

    //移除cart中已经添加到订单中的cart

    public Integer deleteCart( List<Cart> list,Integer user_id);


}
