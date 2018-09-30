package com.neuedu.pojo;

import java.io.Serializable;
import java.util.Date;

public class Shipping implements Serializable{

    private  Integer id;
    private  Integer user_id;
    private  String receiver_name;
    private  String receiver_phone;
    private  String receiver_mobile;
    private  String receiver_province;
    private  String receiver_city;
    private  String receiver_district;
    private  String receiver_adress;
    private  String receiver_zip;
    private Date create_time;
    private Date update_time;

    public String getReceiver_zip() {
        return receiver_zip;
    }

    public void setReceiver_zip(String receiver_zip) {
        this.receiver_zip = receiver_zip;
    }

    public Shipping(Integer id, Integer user_id, String receiver_name, String receiver_phone, String receiver_mobile, String receiver_province, String receiver_city, String receiver_district, String receiver_adress, Date create_time, Date update_time,String receiver_zip) {
        this.id = id;
        this.user_id = user_id;
        this.receiver_name = receiver_name;
        this.receiver_phone = receiver_phone;
        this.receiver_mobile = receiver_mobile;
        this.receiver_province = receiver_province;
        this.receiver_city = receiver_city;
        this.receiver_district = receiver_district;
        this.receiver_adress = receiver_adress;
        this.receiver_zip=receiver_zip;
        this.create_time = create_time;
        this.update_time = update_time;
    }

    public Shipping() {

    }

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

    public String getReceiver_name() {
        return receiver_name;
    }

    public void setReceiver_name(String receiver_name) {
        this.receiver_name = receiver_name;
    }

    public String getReceiver_phone() {
        return receiver_phone;
    }

    public void setReceiver_phone(String receiver_phone) {
        this.receiver_phone = receiver_phone;
    }

    public String getReceiver_mobile() {
        return receiver_mobile;
    }

    public void setReceiver_mobile(String receiver_mobile) {
        this.receiver_mobile = receiver_mobile;
    }

    public String getReceiver_province() {
        return receiver_province;
    }

    public void setReceiver_province(String receiver_province) {
        this.receiver_province = receiver_province;
    }

    public String getReceiver_city() {
        return receiver_city;
    }

    public void setReceiver_city(String receiver_city) {
        this.receiver_city = receiver_city;
    }

    public String getReceiver_district() {
        return receiver_district;
    }

    public void setReceiver_district(String receiver_district) {
        this.receiver_district = receiver_district;
    }

    public String getReceiver_adress() {
        return receiver_adress;
    }

    public void setReceiver_adress(String receiver_adress) {
        this.receiver_adress = receiver_adress;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    @Override
    public String toString() {
        return "shipping{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", receiver_name='" + receiver_name + '\'' +
                ", receiver_phone='" + receiver_phone + '\'' +
                ", receiver_mobile='" + receiver_mobile + '\'' +
                ", receiver_province='" + receiver_province + '\'' +
                ", receiver_city='" + receiver_city + '\'' +
                ", receiver_district='" + receiver_district + '\'' +
                ", receiver_adress='" + receiver_adress + '\'' +
                ", create_time=" + create_time +
                ", update_time=" + update_time +
                '}';
    }







}
