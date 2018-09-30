package com.neuedu.filter;



import com.neuedu.comment.Const;
import com.neuedu.comment.ServerResponse;
import com.neuedu.pojo.UserInfo;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/manage/*")
public class CheckoutFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
     //   System.out.println("=========进入过滤器==========");
        HttpServletRequest request=(HttpServletRequest)  req;
        HttpServletResponse response =(HttpServletResponse)resp;
        HttpSession session =request.getSession();
  //      System.out.println(Const.CURRENTUSER+" 花花");
        Object object=   session.getAttribute(Const.CURRENTUSER);
         if (object!=null&&object instanceof UserInfo){
             chain.doFilter(req, resp);
         }else{

      //       response.sendRedirect("http://localhost:8080/login.jsp");


            ServerResponse.convertToJson( ServerResponse.createServerResponse(10,"请先登录以获取权限进行此项操作！" ), response);


         }

    }

    public void init(FilterConfig config) throws ServletException {

    }

}
