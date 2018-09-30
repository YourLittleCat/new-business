package com.neuedu.vo;

import com.neuedu.comment.BigdecimalUtil;

import java.math.BigDecimal;
import java.util.List;

public class OrderProductVo {

    private List<OrderItemVo> orderItemVoList;
    private String imageHost;
    private BigDecimal productTotalPrice;

    public List<OrderItemVo> getOrderItemVoList() {
        return orderItemVoList;
    }

    public void setOrderItemVoList(List<OrderItemVo> orderItemVoList) {
        this.orderItemVoList = orderItemVoList;
    }

    public String getImageHost() {
        return imageHost;
    }

    public void setImageHost(String imageHost) {
        this.imageHost = imageHost;
    }

    public BigDecimal getProductTotalPrice() {
        return productTotalPrice;
    }

    public void setProductTotalPrice(BigDecimal productTotalPrice) {
        this.productTotalPrice = productTotalPrice;
    }


    public OrderProductVo(List<OrderItemVo> orderItemVoList, String imageHost, BigDecimal productTotalPrice) {
        this.orderItemVoList = orderItemVoList;
        this.imageHost = imageHost;
        this.productTotalPrice = productTotalPrice;
    }

    public OrderProductVo() {

    }
}
