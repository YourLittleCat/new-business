package com.neuedu.comment;

public class Const {


    public static final String CURRENTUSER = "user";
    public static final String USERNAME_COOKIE = "username";
    public static final String PASSWORD_COOKIE = "password";
    public static final String AUTOLIGINTOKEN = "aotu_login_token";
    public static final String EXCEPTION = "ex";
    public static final String TOKENPREFIX = "token_";


    public enum alipay {

        WAIT_BUYER_PAY(10, "WAIT_BUYER_PAY"),
        TRADE_SUCCESS(20, "TRADE_SUCCESS"),
        TRADE_FINISHED(50, "TRADE_FINISHED"),
        TRADE_CLOSED(60, "TRADE_CLOSED");


        private String msg;
        private Integer status;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }


        alipay(Integer status, String msg) {
            this.msg = msg;
            this.status = status;
        }
    }


    //支付平台

    public enum PAYPLATFORM {

        ALIPAY(1, "alipay"),
        WECHAT(2, "wechat");
        private Integer status;
        private String msg;

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        PAYPLATFORM(Integer status, String msg) {
            this.status = status;
            this.msg = msg;
        }
    }


    public enum PAYONLINEOROFFLINE {

        PAY_ONLINE(1, "在线支付"),
        PAY_OFFLINE(2, "线下支付");

        private Integer code;
        private String msg;

        PAYONLINEOROFFLINE(Integer code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

    public enum ORDERSTSTUS {

        ORDER_CANCELLED(0, "订单已取消"),
        ORDER_NO_PAY(10, "订单未付款"),
        ORDER_PAY(20, "订单已付款"),
        ORDER_CLOSED(60, "订单已关闭"),
        ORDER_SEND(40, "订单已发货"),
        ORDER_SUCCESS(50, "订单交易成功"),;

        private Integer code;
        private String msg;

        ORDERSTSTUS(Integer code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }


        public static ORDERSTSTUS codeOf(Integer status) {

            for (ORDERSTSTUS orderststus : ORDERSTSTUS.values()) {
                if (status == orderststus.code) {


                    return orderststus;
                }
            }

            return null;

        }


    }


}
