package com.neuedu.filter;

import com.neuedu.comment.Const;
import com.neuedu.comment.ServerResponse;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IUserService;
import com.neuedu.service.impl.UserServiceImp;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/manage/*")
public class AutologinFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String token = null;
        HttpSession session = request.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);


        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String cookieName = cookie.getName();

                if (cookieName.equals(Const.AUTOLIGINTOKEN)) {
                    token = cookie.getValue();
                }


            }

        }

        if (token != null) {
            IUserService userService = new UserServiceImp();
           userInfo = userService.findUserByToken(token);
            if (userInfo != null) {

                session.setAttribute(Const.CURRENTUSER, userInfo);
                chain.doFilter(req, resp);
                return ;
            }

        }

   //     response.sendRedirect("http://localhost:8080/login.jsp");

       ServerResponse.convertToJson( ServerResponse.createServerResponse(10,"请先登录以获取权限进行此项操作！" ), response);


    }

    public void init(FilterConfig config) throws ServletException {

    }

}
