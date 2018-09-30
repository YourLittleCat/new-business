package com.neuedu.controller;

import com.google.gson.Gson;
import com.neuedu.comment.Const;
import com.neuedu.comment.IpUtils;
import com.neuedu.comment.MD5Utils;
import com.neuedu.exception.BusinessException;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IUserService;
import com.neuedu.service.impl.UserServiceImp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
//    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
//        doGet(request, response);
//    }
//
//    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
//        response.setContentType("text/html");
//
//        String operation = request.getParameter("operation");
//        if (operation == null || operation.equals("")) {
//
//            throw BusinessException.creatBusinessException(request.getSession(), "operation参数不能少", "3秒后跳转到登录页面...", "login.jsp");
//
//        }
//        if (operation.equals("1")) {//登录
//            login(request, response);
//
//        } else if (operation.equals("2")) {//注册
//            register(request, response);
//
//        } else if (operation.equals("3")) {//通过username查询问题
//            findQuestionByUsername(request, response);
//
//
//        } else if (operation.equals("4")) {//校验答案
//            checkAswer(request, response);
//
//
//        } else if (operation.equals("5")) {//重置密码
//            updatePassword(request, response);
//
//        }else if (operation.equals("6")) {//登录状态下重置密码
//            updatePassWord(request, response);
//
//        }else if (operation.equals("7")) {//获取用户信息
//            getInfo(request, response);
//
//        }else if (operation.equals("8")) {//退出登录
//            logOUt(request, response);
//
//        }
//
//
//    }
//    public static  void logOUt(HttpServletRequest request,HttpServletResponse response){
//        //验证是否登录
//        HttpSession session=request.getSession();
//        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
//
//        if (userInfo!=null){
//            session.removeAttribute(Const.CURRENTUSER);
//        }
//
//        IUserService userService=new UserServiceImp();
//
//        if (userInfo!=null){
//            userService.updateTokenById(userInfo.getId(), "");
//
//        }
//
//
//    }
//
//
//
//    public static void getInfo(HttpServletRequest request,HttpServletResponse response){
//        HttpSession session=   request.getSession();
//        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
//        if (userInfo==null) {
//            throw BusinessException.creatBusinessException(session, "登录过期或未登录！", "3秒后将自动跳转到登录页面...", "login.jsp");
//
//        }
//        System.out.println(userInfo);
//
//    }
//
//
//
//
//
//    public static  void updatePassWord(HttpServletRequest request,HttpServletResponse response){
//        String newPassword=request.getParameter("newPassword");
//        String oldPassword=request.getParameter("oldPassword");
//        HttpSession session=request.getSession();
//        IUserService userService=new UserServiceImp();
//        if (newPassword == null || newPassword.equals("")) {
//            throw BusinessException.creatBusinessException(session, "新密码不能为空！", "3秒后将自动跳转到输入新密码页面...", "newpassword.jsp");
//
//        }
//        if (oldPassword == null || oldPassword.equals("")) {
//            throw BusinessException.creatBusinessException(session, "旧密码不能为空！", "3秒后将自动跳转到输入新密码页面...", "newpassword.jsp");
//
//        }
//
//        int result=   userService.updatePassword(oldPassword, newPassword,session );
//        if (result<=0){
//            throw BusinessException.creatBusinessException(session, "修改失败，请重新修改！", "3秒后将自动跳转到输入新密码页面...", "newpassword.jsp");
//
//        }
//        if (result>0){
//            throw BusinessException.creatBusinessException(session, "修改成功，请重新登录！", "3秒后将自动跳转到登录页面...", "login.jsp");
//
//
//
//        }
//    }
//
//
//
//    public static void updatePassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        HttpSession session = request.getSession();
//        String username = request.getParameter("username");
//        String newpassword = request.getParameter("newpassword");
//        String token = request.getParameter("token");
//        if (username == null || username.equals("")) {
//            throw BusinessException.creatBusinessException(session, "用户名不能为空！", "3秒后将自动跳转到找回密码页面...", "newpassword.jsp");
//
//        }
//        if (newpassword == null || newpassword.equals("")) {
//            throw BusinessException.creatBusinessException(session, "新密码不能为空！", "3秒后将自动跳转到找回密码页面...", "newpassword.jsp");
//
//        }
//        if (token == null || token.equals("")) {
//            throw BusinessException.creatBusinessException(session, "token不能为空！", "3秒后将自动跳转到找回密码页面...", "newpassword.jsp");
//
//        }
//
//        IUserService userService = new UserServiceImp();
//        int result = userService.updatePassword(session, username, newpassword, token);
//        if (result > 0) {
//            request.getRequestDispatcher("login.jsp").forward(request, response);
//        } else {
//
//            throw BusinessException.creatBusinessException(session, "密码修改失败，请重新修改！", "3秒后将自动跳转到找回密码页面...", "newpassword.jsp");
//
//        }
//
//    }
//
//
//    public static void checkAswer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        HttpSession session = request.getSession();
//        String username = request.getParameter("username");
//        String question = request.getParameter("question");
//        String answer = request.getParameter("answer");
//        if (username == null || username.equals("")) {
//            throw BusinessException.creatBusinessException(session, "用户名不能为空！", "3秒后将跳转到输入答案页面...", "answer.jsp");
//
//        }
//        if (question == null || username.equals("")) {
//            throw BusinessException.creatBusinessException(session, "问题不能为空！", "3秒后将跳转到输入答案页面...", "answer.jsp");
//
//        }
//        if (answer == null || username.equals("")) {
//            throw BusinessException.creatBusinessException(session, "问题答案不能为空！", "3秒后将跳转到输入答案页面...", "answer.jsp");
//
//        }
//
//        IUserService userService = new UserServiceImp();
//        String token = userService.checkAnswer(session, username, question, answer);
//        session.setAttribute("forgot_token", token);
//        request.getRequestDispatcher("newpassword.jsp").forward(request, response);
////        System.out.println(token);
//
//    }
//
//
//    public static void findQuestionByUsername(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        HttpSession session = request.getSession();
//
//        String username = request.getParameter("username");
//        if (username == null || username.equals("")) {
//            throw BusinessException.creatBusinessException(session, "账号不能为空，请重新找回密码！", "3秒后将自动跳转到找回密码页面...", "findpwd.jsp");
//        }
//
//        IUserService userService = new UserServiceImp();
//        String question = userService.findQuestionByUsername(session, username);
//        session.setAttribute("question", question);
//        request.getRequestDispatcher("answer.jsp").forward(request, response);
//
//
//    }
//
//
//    public static void register(HttpServletRequest request, HttpServletResponse response) {
//        String username = request.getParameter("username");
//        String email = request.getParameter("email");
//        String password = request.getParameter("password");
//        String question = request.getParameter("question");
//        String answer = request.getParameter("answer");
//        String phone = request.getParameter("phone");
//        String role = request.getParameter("lever");
//        HttpSession session = request.getSession();
//        if (username == null || username.equals("")) {
//            throw BusinessException.creatBusinessException(session, "用户名不能为空！", "3秒后将跳转到注册页面...", "register.jsp");
//
//        }
//        if (email == null || username.equals("")) {
//            throw BusinessException.creatBusinessException(session, "邮箱不能为空！", "3秒后将跳转到注册页面...", "register.jsp");
//
//        }
//        if (password == null || username.equals("")) {
//            throw BusinessException.creatBusinessException(session, "密码不能为空！", "3秒后将跳转到注册页面...", "register.jsp");
//
//        }
//        if (question == null || username.equals("")) {
//            throw BusinessException.creatBusinessException(session, "问题不能为空！", "3秒后将跳转到注册页面...", "register.jsp");
//
//        }
//        if (phone == null || username.equals("")) {
//            throw BusinessException.creatBusinessException(session, "电话号码不能为空！", "3秒后将跳转到注册页面...", "register.jsp");
//
//        }
//        if (answer == null || username.equals("")) {
//            throw BusinessException.creatBusinessException(session, "问题答案不能为空！", "3秒后将跳转到注册页面...", "register.jsp");
//
//        }
//
//        UserInfo userInfo = new UserInfo();
//        userInfo.setUsername(username);
//        userInfo.setPassword(password);
//        userInfo.setQuestion(question);
//        userInfo.setPhone(phone);
//        userInfo.setEmail(email);
//        userInfo.setAnswer(answer);
//        userInfo.setRole(0);
//
//        IUserService userService = new UserServiceImp();
//        int result = userService.register(session, userInfo);
//        if (result > 0) {
//            throw BusinessException.creatBusinessException(session, "恭喜，注册成功！", "3秒后将跳转到登录页面...", "login.jsp");
//
//        } else {
//
//            throw BusinessException.creatBusinessException(session, "很遗憾注册失败，请重新注册！", "3秒后将跳转到注册页面...", "register.jsp");
//
//        }
//
//
//    }
//
//    public static void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//        response.setContentType("text/html;charset=UTF-8");
//        HttpSession session = request.getSession();
//        String username = request.getParameter("username");
//        String password = request.getParameter("password");
//
////          StringBuffer buffer=new StringBuffer();
////          buffer.append("{");
////          buffer.append("\"id\":34");
////          buffer.append(":");
////          buffer.append("34");
////          buffer.append("}");
////        PrintWriter pt=response.getWriter();
////        pt.print(buffer);
//
//
//
//
//
//
//
//        if (username == null || username.equals("") || password == null || password.equals("")) {
////            BusinessException businessException = new BusinessException();
////            businessException.setMsg("账号密码不能为空！");
////            businessException.setWarn("3秒后将自动跳转到登录页面...");
////            businessException.setUrl("login.jsp");
////            session.setAttribute(Const.EXCEPTION, businessException);
//            throw BusinessException.creatBusinessException(session, "账号密码不能为空！", "3秒后将自动跳转到登录页面...", "login.jsp");
//
//        }
//        IUserService userService = new UserServiceImp();
//        UserInfo userInfo = userService.login(username, password);
//        if (userInfo != null) {//查询成功
////               Cookie username_cookie=new Cookie(Const.USERNAME_COOKIE,username);
////               Cookie password_cookie=new Cookie(Const.PASSWORD_COOKIE,password);
////               username_cookie.setMaxAge(3600*24*7);
////               password_cookie.setMaxAge(3600*24*7);
////               response.addCookie(username_cookie);
////               response.addCookie(password_cookie);
//            String ip = IpUtils.getRemoteAddress(request);
//
//            String mac = IpUtils.getMACAddress(ip);
//            String token = MD5Utils.GetMD5Code(mac);
//
//            Cookie cookie = new Cookie(Const.AUTOLIGINTOKEN, token);
//            cookie.setMaxAge(3600 * 24 * 7);
//            response.addCookie(cookie);
//            userService.updateTokenById(userInfo.getId(), token);
//
//
//   //         session.setAttribute("user", userInfo);
//            //       session.setAttribute(Const.AUTOLIGINTOKEN, token);
//
//                request.getRequestDispatcher("/manage/home.jsp").forward(request, response);
//
//
//        } else {//用户不存在   查询失败
//
//            System.out.println("账号密码错误");
//
//        }
//
//
////
////        //用于json测试
////        Gson gson=new Gson();
////        String json=   gson.toJson(userInfo);
////        //    DaoUserInfo user=    gson.fromJson(json,DaoUserInfo.class );
////        response.setHeader("Access-Control-Allow-Origin", "*");
////        PrintWriter ps=response.getWriter();
////        ps.print(json);
//    }
//


}
