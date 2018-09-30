package com.neuedu.comment;

public enum ResponseCode {


    //需要登录之后才能进行操作
    PRODUCT_ADD_SUCCESS(0,"商品添加成功！"),
    CATEGORY_ADD_SUCCESS(0,"商品类名添加成功！"),
    PRODUCT_UPDATE_SUCCESS(0,"商品信息更新成功！"),
    FIND_SUCCESS(0,"查询成功！"),
    DELETE_SUCCESS(0,"购物车商品移除成功！"),
    ADD_SUCCESS(0,"添加成功！"),
    DO_SUCCESS(0,"操作成功！"),
    CHANGE_SUCCESS(0,"转换成功！"),
    PAY_SUCCESSFUL(0,"支付成功！"),
    CATEGORY_HAVE_FIND(0,"商品类名已查到！"),
    UPDATE_NEW_CATEGORY_SUCCESS(0,"新商品类名修改成功！"),
    ALL_CATEGORY_CHILD_HAVE_FIND(0,"该id下的所有子商品类名已查到！"),
    UPLOAD_SUCCESS(0,"上传成功！"),
    DELETE_SHIPPING_SUCCESS(0,"删除成功！"),
    ADD_TO_CART_SUCCESS(0,"成功添加到购物车！"),
    ADD_ADRESS_SUCCUSS(0,"添加地址成功！"),

    NEED_LOGIN(1,"请先登录！"),
    PRODUCT_NOT_NULL(2,"商品不能为空！"),
    ORDER_NUMBER_NOT_NULL(2,"订单号不能为空！"),
    CATEGORY_ID_NOT_NULL(2,"商品类名Id不能为空！"),
    CATEGORYID_OR_PROAUCTNAME_NOT_NULL(2,"品类id和商品名称不能都为空！"),
    PRODUCT_ID_NOT_NULL(2,"商品id不能为空！"),
    UPDATE_SUCCESS(3,"更新成功！"),
    PRODUCT_STATUS_NOT_NULL(2,"商品状态不能为空！"),
    CATEGORY_NOT_NULL(2,"商品类名不能为空！"),
    SHIPPING_ID(2,"shippingId不能为空！"),
    PARAMETER_NOT_NULL(2,"参数不能为空！"),
    NEW_CATEGORY_NOT_NULL(2,"新商品类名不能为空！"),
    PARENTID_NOT_NULL(2,"父类ID不能为空！"),
    CATEGORY_ADD_FAILD(3,"商品类名添加失败！"),
    ADD_FAILD(3,"添加失败！"),
    UPDATE_FAILD(3,"更新失败！"),
    DO_FAILD(3,"操作失败！"),
    CHANGE_FALID(0,"转换失败！"),
    DELETE_SHIPPING_FAILD(0,"删除失败！"),
    ADD_ADRESS_FAILD(3,"添加地址失败！"),
    PAY_FAILD(3,"支付失败！"),
    DELETE_FAILD(3,"购物车商品移除失败！"),
    PRODUCT_UPDATE_FAILD(3,"商品信息更新失败！"),
    CATEGORY_EXIST(3,"商品类名已存在！"),
    FIND_FAILD(3,"查询失败！"),
    UPLOAD_FAILD(3,"上传失败！"),
    ADD_TO_CART_FAILD(3,"添加到购物车失败！"),
    UPDATE_CART_FAILD(3,"购物车中不存在此商品，更新数量失败！"),
    PRODUCT_ADD_FAILD(3,"商品添加失败！"),
    UPDATE_NEW_CATEGORT_FAILD(3,"修改商品类名失败！"),
    OUT_OF_AUTHORITY(4,"没有权限进行此操作！"),
    ALL_CATEGORY_CHILD_WITHOUT_FIND(3,"该id下的所有子商品类名查询失败！"),
    OUT_OF_RANGE(4,"超出查询范围！"),
    PRODUCT_OFFLINE(5,"查询的商品已下架或者不存在"),
    CART_IS_NULL(6,"购物车为空！"),
    ORDER_ENMPTY(6,"订单列表为空！"),
    PRODUCT_ENOUGH("库存充足"),
    ORDER_NOT_EXIT(7,"订单不存在"),
    ORDER_COUND_NOT_CANCLE(8,"订单已付款，不可取消！"),
    PRODUCT_SHORT("库存不足"),
    CART_WITHOUT_PRODUCT(9,"购物车中没有商品");


 private String msg;
    private int code;





    private  ResponseCode (int code ,String msg){
         this.code=code;
         this.msg=msg;


    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }


    private  ResponseCode (String msg){
        this.msg=msg;
    }







}
