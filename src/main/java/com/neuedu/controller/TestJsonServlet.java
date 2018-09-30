package com.neuedu.controller;

import com.google.gson.Gson;
import com.neuedu.pojo.UserInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet( "/TestJsonServlet")
public class TestJsonServlet extends HttpServlet {


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        test(response);
    }




   public  static  void test(HttpServletResponse response){

       Gson gson=new Gson();
       UserInfo userInfo=new UserInfo();
       userInfo.setUsername("花花");
       userInfo.setPhone("123123123");
       userInfo.setRole(1);

         String pe= gson.toJson(userInfo);


       try {
           PrintWriter printWriter=response.getWriter();


           printWriter.print(pe);
       } catch (IOException e) {
           e.printStackTrace();
       }


   }


}
