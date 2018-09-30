package com.neuedu.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class CartVo implements Serializable{
    private BigDecimal totalPrice;
    private boolean isAllCheck;
    private List<CartProductVo> cartProductVoList;

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean isAllCheck() {
        return isAllCheck;
    }

    public void setAllCheck(boolean allCheck) {
        isAllCheck = allCheck;
    }

    public List<CartProductVo> getCartProductVoList() {
        return cartProductVoList;
    }

    public void setCartProductVoList(List<CartProductVo> cartProductVoList) {
        this.cartProductVoList = cartProductVoList;
    }

    public CartVo(BigDecimal totalPrice, boolean isAllCheck, List<CartProductVo> cartProductVoList) {
        this.totalPrice = totalPrice;
        this.isAllCheck = isAllCheck;
        this.cartProductVoList = cartProductVoList;
    }

    public CartVo() {

    }


}
