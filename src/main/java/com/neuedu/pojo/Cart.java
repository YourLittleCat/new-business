package com.neuedu.pojo;

import java.util.Date;

public class Cart {

    private Integer id;     //    `id` INT(11) NOT NULL auto_increment,
    private Integer user_id;            //`user_id` INT(11) NOT NULL,
    private Integer product_id;  //`product_id` INT(11) DEFAULT NULL COMMENT'商品id',
    private Integer quantity;             //            `quantity` int(11) DEFAULT NULL COMMENT'数量',
    private Integer checked;              //            `checked` INT(11) DEFAULT NULL COMMENT'是否选择，1-已勾选，0-未勾选',
    private Date creat_time;              //            `creat_time` datetime DEFAULT NULL COMMENT'创建时间',
    private Date update_time;  //            `update_time` datetime DEFAULT NULL COMMENT'更新时间',


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
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

    public Date getCreat_time() {
        return creat_time;
    }

    public void setCreat_time(Date creat_time) {
        this.creat_time = creat_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }


    public Cart(Integer id, Integer user_id, Integer product_id, Integer quantity, Integer checked, Date creat_time, Date update_time) {
        this.id = id;
        this.user_id = user_id;
        this.product_id = product_id;
        this.quantity = quantity;
        this.checked = checked;
        this.creat_time = creat_time;
        this.update_time = update_time;
    }

    public Cart( Integer user_id, Integer product_id, Integer quantity, Integer checked) {

        this.user_id = user_id;
        this.product_id = product_id;
        this.quantity = quantity;
        this.checked = checked;

    }

    public Cart() {

    }

}
