package com.neuedu.service.impl;

import com.neuedu.Dao.ICartDao;
import com.neuedu.Dao.IProduct;
import com.neuedu.comment.BigdecimalUtil;
import com.neuedu.comment.ResponseCode;
import com.neuedu.comment.ServerResponse;
import com.neuedu.pojo.Cart;
import com.neuedu.pojo.Product;
import com.neuedu.service.ICartService;
import com.neuedu.vo.CartProductVo;
import com.neuedu.vo.CartVo;
import com.neuedu.vo.ProductVo;
import org.apache.log4j.TTCCLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImp implements ICartService {
    @Autowired
    private ICartDao iCartDao;

    @Autowired
    private IProduct iProduct;


    @Override
    public ServerResponse<CartVo> addProductTocart(Integer userId, Integer productId, Integer count) {
        Cart cart = iCartDao.findCartByUserIdAndProductId(userId, productId);
        //如果Cart为空 则默认添加一个购物车
        if (cart == null) {
            Cart cart1 = new Cart();
            cart1.setChecked(1);
            cart1.setProduct_id(productId);
            cart1.setQuantity(count);
            cart1.setUser_id(userId);

            Integer result = iCartDao.addProductToCart(cart1);

            if (result > 0) {
                CartVo cartVo = new CartVo();
                cartVo=   findCartsByUserId(userId);


                return ServerResponse.createServerResponse(ResponseCode.ADD_TO_CART_SUCCESS.getCode(), cartVo,ResponseCode.ADD_TO_CART_SUCCESS.getMsg());
            } else {


                return ServerResponse.createServerResponse(ResponseCode.ADD_TO_CART_FAILD.getCode(), ResponseCode.ADD_TO_CART_FAILD.getMsg());
            }


        }else{

        //如果不为空  则是更新数量
        cart.setQuantity(cart.getQuantity() + count);
        Integer result = iCartDao.updateCart(cart);

        if (result > 0) {


            CartVo cartVo = new CartVo();
           cartVo=   findCartsByUserId(userId);

            return ServerResponse.createServerResponse(ResponseCode.ADD_TO_CART_SUCCESS.getCode(), cartVo,ResponseCode.ADD_TO_CART_SUCCESS.getMsg());
        } else {


            return ServerResponse.createServerResponse(ResponseCode.ADD_TO_CART_FAILD.getCode(), ResponseCode.ADD_TO_CART_FAILD.getMsg());
        }


    }}



    //查看用户的购物车详情

    public  ServerResponse<CartVo> checkCart(Integer userId){

        CartVo cartVo=findCartsByUserId(userId);

        return ServerResponse.createServerResponse(ResponseCode.FIND_SUCCESS.getCode(),cartVo,ResponseCode.FIND_SUCCESS.getMsg());

    }




    //移除购物车中的商品
    @Override
    public ServerResponse<CartVo> deleteProduct(Integer userId, Integer productId) {

        Cart cart=iCartDao.findCartByUserIdAndProductId(userId, productId);
             iCartDao.deleteProduct(cart);
         CartVo cartVo=new CartVo();
            cartVo=  findCartsByUserId(userId);

        return ServerResponse.createServerResponse(ResponseCode.DELETE_SUCCESS.getCode(),cartVo,ResponseCode.DELETE_SUCCESS.getMsg());
    }


    //更新商品的数量

    @Override
    public ServerResponse<CartVo> updateProductQuantity(Integer user_id, Integer count, Integer product_Id) {

      Cart cart=  iCartDao.findCartByUserIdAndProductId(user_id, product_Id);
        if (cart==null){

            return  ServerResponse.createServerResponse(ResponseCode.UPDATE_CART_FAILD.getCode(),ResponseCode.UPDATE_CART_FAILD.getMsg());
        }

        cart.setQuantity(count);


       int result= iCartDao.updateCart(cart);

        if (result > 0) {


            CartVo cartVo = new CartVo();
            cartVo=   findCartsByUserId(user_id);

            return ServerResponse.createServerResponse(ResponseCode.UPDATE_SUCCESS.getCode(), cartVo,ResponseCode.UPDATE_SUCCESS.getMsg());
        } else {


            return ServerResponse.createServerResponse(ResponseCode.UPDATE_FAILD.getCode(), ResponseCode.UPDATE_FAILD.getMsg());
        }



    }


    //选中商品
    @Override
    public ServerResponse<CartVo> checkedProduct(Integer user_id,  Integer product_id) {
       Cart cart=    iCartDao.findCartByUserIdAndProductId(user_id, product_id);
      int result=   iCartDao.checkedProduct(user_id,product_id);


  //      System.out.println(result+"===============================1==========");
         CartVo cartVo= findCartsByUserId(user_id);


        return ServerResponse.createServerResponse(ResponseCode.UPDATE_SUCCESS.getCode(),cartVo,ResponseCode.UPDATE_SUCCESS.getMsg());
    }

    //未选中商品

    @Override
    public ServerResponse<CartVo> unCheckedProduct(Integer user_id, Integer product_id) {
        Cart cart= new Cart();

    int result=    iCartDao.unCheckedProduct(user_id,product_id);
  //      System.out.println(result+"=================================2========");
        CartVo cartVo= findCartsByUserId(user_id);


        return ServerResponse.createServerResponse(ResponseCode.UPDATE_SUCCESS.getCode(),cartVo,ResponseCode.UPDATE_SUCCESS.getMsg());




    }


    //查询所有的商品之和
    @Override
    public ServerResponse<Integer> addProductNum(Integer user_id) {
        List<Cart> cartList= iCartDao.findCartsByUserId(user_id);
        Integer total=0;
              for (Cart cart :cartList){
                  total=total+cart.getQuantity();

              }




        return ServerResponse.createServerResponse(ResponseCode.FIND_SUCCESS.getCode(),total,ResponseCode.FIND_SUCCESS.getMsg());
    }


    //全选
    @Override
    public ServerResponse<CartVo> allCheck(Integer user_id) {
     List<Cart> cartList=   iCartDao.findCartsByUserId(user_id);
        for (Cart cart :cartList){
               iCartDao.checkedProduct(cart.getUser_id(), cart.getProduct_id());
        }
        CartVo cartVo=    findCartsByUserId(user_id);

        return ServerResponse.createServerResponse(ResponseCode.UPDATE_SUCCESS.getCode(),cartVo,ResponseCode.UPDATE_SUCCESS.getMsg());
    }

    @Override
    public ServerResponse<CartVo> allUnCheck(Integer user_id) {
        List<Cart> cartList=   iCartDao.findCartsByUserId(user_id);
        for (Cart cart :cartList){
            iCartDao.unCheckedProduct(cart.getUser_id(), cart.getProduct_id());
        }
        CartVo cartVo=    findCartsByUserId(user_id);

        return ServerResponse.createServerResponse(ResponseCode.UPDATE_SUCCESS.getCode(),cartVo,ResponseCode.UPDATE_SUCCESS.getMsg());
    }


    public CartVo findCartsByUserId(Integer user_id) {
          CartVo cartVo=new CartVo();
          //根据user_id查询其账号下所有的cart并用集合的方式返回
        List<Cart> listcart = iCartDao.findCartsByUserId(user_id);



        List<CartProductVo> productVoList = new ArrayList<>();
          BigDecimal totalPrice=new BigDecimal(0);

        if (listcart != null) {
            for (Cart cart : listcart) {
                Product product = iProduct.findProductById(cart.getProduct_id());
                 /**
                  *  private Integer productId ;//'商品id',
                  private String       productName    ;// '商品名称',
                  private String       subtitle    ;// 商品副标题
                  private String       main_imag    ;// 产品主图，url相对地址
                  private String       sub_imags    ;// 图片地址，json格式
                  private BigDecimal price;//价格，单位-元保留两位小数
                  *
                  * */
                 //为购物车中每一个商品创建一个视图
                CartProductVo cartProductVo=new CartProductVo();


                 cartProductVo.setProductId(cart.getProduct_id());
                 cartProductVo.setPrice(product.getPrice());
                 cartProductVo.setSub_imags(product.getSub_imags());
                 cartProductVo.setMain_imag(product.getMain_imag());
                  cartProductVo.setSubtitle(product.getSubtitle());
                  cartProductVo.setProductName(product.getName());



                 //校验库存

                if(product.getStock()>cart.getQuantity()){
           // 库存充足的情况
                     cartProductVo.setLimitQuantity(ResponseCode.PRODUCT_ENOUGH.getMsg());
                }else {
             //库存不足   更新购物车中商品的数量  改成商品的库存数量

                    cartProductVo.setLimitQuantity(ResponseCode.PRODUCT_SHORT.getMsg());

                    Cart cart1=new Cart();
                    cart1.setUser_id(user_id);
                    cart1.setQuantity(product.getStock());
                    cart1.setProduct_id(product.getId());
                 Integer result=    iCartDao.updateCart(cart1);

                }




                //设置总价
                cartProductVo.setTotalprice(BigdecimalUtil.multi(product.getPrice(),new BigDecimal(cart.getQuantity())));

                if (cart.getChecked()==1){

                    totalPrice= BigdecimalUtil.add(cartProductVo.getTotalprice(),totalPrice );


                }

                cartProductVo.setQuantity(cart.getQuantity());
                cartProductVo.setStatus(product.getStatus());
                cartProductVo.setCartId(cart.getId());
                cartProductVo.setChecked(cart.getChecked());

              productVoList.add(cartProductVo);

            }


        }




        cartVo.setCartProductVoList(productVoList);
         cartVo.setTotalPrice(totalPrice);
         cartVo.setAllCheck(iCartDao.isCheckAll(user_id)==0);


        return cartVo;


    }


}
