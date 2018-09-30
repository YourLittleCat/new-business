package com.neuedu.vo;

import com.neuedu.pojo.Category;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CartProductVo implements Serializable{

    private Integer productId ;//'商品id',
    private Integer stock;
    private String       productName    ;// '商品名称',
    private String       subtitle    ;// 商品副标题
    private String       main_imag    ;// 产品主图，url相对地址
    private String       sub_imags    ;// 图片地址，json格式
    private BigDecimal price;//价格，单位-元保留两位小数
    private String     limitQuantity      ;// 库存数量
    private Integer     status      ;// 商品状态，1-在售 2-下架 3-删除
    private Integer cartId;     //    `id` INT(11) NOT NULL auto_increment,
    private Integer quantity;             //            `quantity` int(11) DEFAULT NULL COMMENT'数量',
    private Integer checked;              //            `checked` INT(11) DEFAULT NULL COMMENT'是否选择，1-已勾选，0-未勾选',
    private  BigDecimal totalprice;

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getLimitQuantity() {
        return limitQuantity;
    }

    public void setLimitQuantity(String limitQuantity) {
        this.limitQuantity = limitQuantity;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getMain_imag() {
        return main_imag;
    }

    public void setMain_imag(String main_imag) {
        this.main_imag = main_imag;
    }

    public String getSub_imags() {
        return sub_imags;
    }

    public void setSub_imags(String sub_imags) {
        this.sub_imags = sub_imags;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }



    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getChecked() {
        return checked;
    }

    public void setChecked(Integer checked) {
        this.checked = checked;
    }

    public BigDecimal getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(BigDecimal totalprice) {
        this.totalprice = totalprice;
    }






    public CartProductVo() {

    }
}
