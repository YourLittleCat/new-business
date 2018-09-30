package com.neuedu.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class Product {
    private Integer id ;//'商品id',
    private Integer       category_id    ;// 类别id，对应neuedu_category表的主键
    private Category category;        //一对一的关系
    private String       name    ;// '商品名称',
    private String       subtitle    ;// 商品副标题
    private String       main_imag    ;// 产品主图，url相对地址
    private String       sub_imags    ;// 图片地址，json格式
    private String       detail    ;// 商品详情
    private BigDecimal price;//价格，单位-元保留两位小数


    private Integer     stock      ;// 库存数量
    private Integer     status      ;// 商品状态，1-在售 2-下架 3-删除
    private Date create_time ;// '创建时间',
    private Date update_time    ;// '最后一次更新时间',

    public Product( ) {

    }

    public Product(Integer id, Integer category_id, Category category, String name, String subtitle, String main_imag, String sub_imags, String detail, BigDecimal price, Integer stock, Integer status, Date create_time, Date update_time) {
        this.id = id;
        this.category_id = category_id;
        this.category = category;
        this.name = name;
        this.subtitle = subtitle;
        this.main_imag = main_imag;
        this.sub_imags = sub_imags;
        this.detail = detail;
        this.price = price;
        this.stock = stock;
        this.status = status;
        this.create_time = create_time;
        this.update_time = update_time;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
        return "Product{" +
                "id=" + id +
                ", category_id=" + category_id +
                ", category=" + category +
                ", name='" + name + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", main_image='" + main_imag + '\'' +
                ", sub_imags='" + sub_imags + '\'' +
                ", detail='" + detail + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", status=" + status +
                ", create_time=" + create_time +
                ", update_time=" + update_time +
                '}';
    }
}
