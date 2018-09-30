package com.neuedu.exception;

import com.neuedu.comment.Const;


import javax.servlet.http.HttpSession;

public class BusinessException extends  RuntimeException{

private String msg;
private String warn;
private String url;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getWarn() {
        return warn;
    }

    public void setWarn(String warn) {
        this.warn = warn;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    private BusinessException(String msg, String warn, String url) {
        this.msg = msg;
        this.warn = warn;
        this.url = url;
    }


    private BusinessException() {

    }


    public static  BusinessException creatBusinessException(HttpSession session,String msg,String warn,String url){
        BusinessException businessException=new BusinessException(msg,warn,url);
        session.setAttribute(Const.EXCEPTION,businessException );
      return businessException;
    }
}
