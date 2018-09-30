package com.neuedu.comment;

import com.google.gson.Gson;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ServerResponse<T> {
    private int status;
    private T data;
    private String msg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private ServerResponse(int status) {
        this.status = status;

    }

    private ServerResponse(T data){

        this.data=data;
    }

    private ServerResponse(int status, String msg) {
        this.status = status;
        this.msg = msg;

    }

    private ServerResponse(int status, T data) {
        this.status = status;
        this.data = data;
        this.msg = msg;
    }




    private ServerResponse(int status, T data,String msg) {
        this.status = status;
        this.data = data;
        this.msg=msg;
    }

     //创建响应对象

    public static  <T> ServerResponse<T> createServerResponse(int status,String msg){

    return new  ServerResponse<T>(status,msg);

    }




    public  static <T> ServerResponse<T> createServerResponse(int status,T data,String msg){

        return new  ServerResponse<T>(status,data,msg);

    }

    public static  <T> ServerResponse<T> createServerResponse(int status){

        return new  ServerResponse<T>(status);

    }



    public static void convertToJson(ServerResponse serverResponse, HttpServletResponse response){
        Gson gson=new Gson();
          String json=   gson.toJson(serverResponse);
        try {
            PrintWriter pt=response.getWriter();
            pt.print(json);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
