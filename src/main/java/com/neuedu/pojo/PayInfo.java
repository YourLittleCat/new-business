package com.neuedu.pojo;

import java.util.Date;

public class PayInfo {
    private Integer id;
    private  Integer user_id;
    private  Long order_no;
    private   Integer pay_platform;
    private  String platform_status;
    private  String platform_number;
    private Date creat_time;
    private  Date update_time;

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

    public Long getOrder_no() {
        return order_no;
    }

    public void setOrder_no(Long order_no) {
        this.order_no = order_no;
    }

    public Integer getPay_platform() {
        return pay_platform;
    }

    public void setPay_platform(Integer pay_platform) {
        this.pay_platform = pay_platform;
    }

    public String getPlatform_status() {
        return platform_status;
    }

    public void setPlatform_status(String platform_status) {
        this.platform_status = platform_status;
    }

    public String getPlatform_number() {
        return platform_number;
    }

    public void setPlatform_number(String platform_number) {
        this.platform_number = platform_number;
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

    public PayInfo(Integer id, Integer user_id, Long order_no, Integer pay_platform, String platform_status, String platform_number, Date creat_time, Date update_time) {
        this.id = id;
        this.user_id = user_id;
        this.order_no = order_no;
        this.pay_platform = pay_platform;
        this.platform_status = platform_status;
        this.platform_number = platform_number;
        this.creat_time = creat_time;
        this.update_time = update_time;
    }


    public PayInfo() {

    }
}
