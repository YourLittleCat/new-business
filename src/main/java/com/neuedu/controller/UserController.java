package com.neuedu.controller;

import com.neuedu.comment.Const;
import com.neuedu.comment.IpUtils;
import com.neuedu.comment.MD5Utils;
import com.neuedu.comment.ServerResponse;
import com.neuedu.exception.BusinessException;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IUserService;
import com.neuedu.service.impl.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
public class UserController {

    @Autowired
    IUserService userService;
    @Autowired
    UserInfo userInfo;


    static void forCharset(HttpServletRequest request, HttpServletResponse response) {

        try {
            response.setContentType("text/html;charset=utf-8");
            request.setCharacterEncoding("utf-8");

        } catch (Exception e) {
            System.out.println("编码格式出错");

        }


    }



    @RequestMapping("/logout")
    public ServerResponse<String> logOUt(HttpSession session) {
        //验证是否登录

        userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);

        if (userInfo == null) {

            return ServerResponse.createServerResponse(1, "退出失败，请先登录！");
        }

        if (userInfo != null) {
            session.removeAttribute(Const.CURRENTUSER);
        }

        if (userInfo != null) {
            userService.updateTokenById(userInfo.getId(), "");

        }

        return ServerResponse.createServerResponse(1, "退出成功!");
    }

    @RequestMapping("/getinfo")
    public ServerResponse<UserInfo> getInfo(HttpSession session) {
   //     HttpSession session = request.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo == null) {
            //         throw BusinessException.creatBusinessException(session, "登录过期或未登录！", "3秒后将自动跳转到登录页面...", "login.jsp");
            return ServerResponse.createServerResponse(0, "登录过期或未登录！");
        }
        //      System.out.println(userInfo);
        return ServerResponse.createServerResponse(1, userInfo, "信息获取成功");
    }


    @RequestMapping("/uppwdol")
    public ServerResponse<Integer> updatePassWordOnLine(String newPassword, String oldPassword, HttpSession session) {
//        String newPassword=request.getParameter("newPassword");
//        String oldPassword=request.getParameter("oldPassword");
//        HttpSession session=request.getSession();
        IUserService userService = new UserServiceImp();
        if (newPassword == null || newPassword.equals("")) {
//            throw BusinessException.creatBusinessException(session, "新密码不能为空！", "3秒后将自动跳转到输入新密码页面...", "newpassword.jsp");
            return ServerResponse.createServerResponse(5, "新密码不能为空！");
        }
        if (oldPassword == null || oldPassword.equals("")) {
            //          throw BusinessException.creatBusinessException(session, "旧密码不能为空！", "3秒后将自动跳转到输入新密码页面...", "newpassword.jsp");
            return ServerResponse.createServerResponse(6, "旧密码不能为空！");
        }

//        int result=   userService.updatePasswordOnLine(oldPassword, newPassword,session );
//        if (result<=0){
//            throw BusinessException.creatBusinessException(session, "修改失败，请重新修改！", "3秒后将自动跳转到输入新密码页面...", "newpassword.jsp");
//
//        }
//        if (result>0){
//            throw BusinessException.creatBusinessException(session, "修改成功，请重新登录！", "3秒后将自动跳转到登录页面...", "login.jsp");
//
//        }

        return userService.updatePasswordOnLine(oldPassword, newPassword, session);

    }


    @RequestMapping("/uppwd")
    public ServerResponse<Integer> updatePassword(String username, String newpassword, String token, HttpSession session) throws ServletException, IOException {
//        HttpSession session = request.getSession();
//        String username = request.getParameter("username");
//        String newpassword = request.getParameter("newpassword");
//        String token = request.getParameter("token");
        if (username == null || username.equals("")) {
            //        throw BusinessException.creatBusinessException(session, "用户名不能为空！", "3秒后将自动跳转到找回密码页面...", "newpassword.jsp");
            return ServerResponse.createServerResponse(2, "用户名不能为空!");
        }
        if (newpassword == null || newpassword.equals("")) {
            //     throw BusinessException.creatBusinessException(session, "新密码不能为空！", "3秒后将自动跳转到找回密码页面...", "newpassword.jsp");
            return ServerResponse.createServerResponse(3, "新密码不能为空!");
        }
        if (token == null || token.equals("")) {
            //          throw BusinessException.creatBusinessException(session, "token不能为空！", "3秒后将自动跳转到找回密码页面...", "newpassword.jsp");
            return ServerResponse.createServerResponse(4, "token不能为空！");
        }

        //     IUserService userService = new UserServiceImp();
        ServerResponse serverResponse = userService.updatePassword(session, username, newpassword, token);
        //     int result =(int)serverResponse.getData();
        //      if (result > 0) {
//            request.getRequestDispatcher("login.jsp").forward(request, response);
        //           return ServerResponse.createServerResponse(1,"修改密码成功");

        //     } else {

        //          return  userService.updatePassword(session, username, newpassword, token);
        //      }

        return serverResponse;

    }

    @RequestMapping("/checkAnswer")
    public ServerResponse<String> checkAswer(String username, String question, String answer, HttpSession session) {
//        HttpSession session = request.getSession();
//        String username = request.getParameter("username");
//        String question = request.getParameter("question");
//        String answer = request.getParameter("answer");

        if (username == null || username.equals("")) {
            //    throw BusinessException.creatBusinessException(session, "用户名不能为空！", "3秒后将跳转到输入答案页面...", "answer.jsp");
            return ServerResponse.createServerResponse(0, "用户名不能为空！");
        }
        if (question == null || question.equals("")) {
            //          throw BusinessException.creatBusinessException(session, "问题不能为空！", "3秒后将跳转到输入答案页面...", "answer.jsp");
            return ServerResponse.createServerResponse(0, "密保问题不能为空！");
        }
        if (answer == null || answer.equals("")) {
            //         throw BusinessException.creatBusinessException(session, "问题答案不能为空！", "3秒后将跳转到输入答案页面...", "answer.jsp");
            return ServerResponse.createServerResponse(0, "问题的答案不能为空！");
        }

        ServerResponse serverResponse = userService.checkAnswer(session, username, question, answer);
        String token = (String) serverResponse.getData();
        session.setAttribute("forgot_token", token);
        //      request.getRequestDispatcher("newpassword.jsp").forward(request, response);
//        System.out.println(token);
        return userService.checkAnswer(session, username, question, answer);

    }

    @RequestMapping("/getquestion")
    public ServerResponse<String> findQuestionByUsername(String username, HttpSession session) throws ServletException, IOException {
        //       HttpSession session = request.getSession();

        //       String username = request.getParameter("username");
        if (username == null || username.equals("")) {
            //          throw BusinessException.creatBusinessException(session, "账号不能为空，请重新找回密码！", "3秒后将自动跳转到找回密码页面...", "findpwd.jsp");

            return ServerResponse.createServerResponse(0, "账号不能为空！");

        }


        String question = userService.findQuestionByUsername(session, username);
        session.setAttribute("question", question);
        //      request.getRequestDispatcher("answer.jsp").forward(request, response);

        return ServerResponse.createServerResponse(1, question, "问题查询成功");
    }


    @RequestMapping("/register")
    public ServerResponse<UserInfo> register(String username, String email, String password, String question, String answer,
                                             String phone, Integer role, HttpSession session) {
//        String username = request.getParameter("username");
//        String email = request.getParameter("email");
//        String password = request.getParameter("password");
//        String question = request.getParameter("question");
//        String answer = request.getParameter("answer");
//        String phone = request.getParameter("phone");
//        String role = request.getParameter("lever");

        if (username == null || username.equals("")) {
//            throw BusinessException.creatBusinessException(session, "用户名不能为空！", "3秒后将跳转到注册页面...", "register.jsp");
            return ServerResponse.createServerResponse(2, "用户名不能为空，注册失败");
        }
        if (email == null || username.equals("")) {
            //           throw BusinessException.creatBusinessException(session, "邮箱不能为空！", "3秒后将跳转到注册页面...", "register.jsp");
            return ServerResponse.createServerResponse(3, "邮箱不能为空，注册失败");
        }
        if (password == null || username.equals("")) {
            //        throw BusinessException.creatBusinessException(session, "密码不能为空！", "3秒后将跳转到注册页面...", "register.jsp");
            return ServerResponse.createServerResponse(4, "密码不能为空，注册失败");
        }
        if (question == null || username.equals("")) {
            //         throw BusinessException.creatBusinessException(session, "问题不能为空！", "3秒后将跳转到注册页面...", "register.jsp");
            return ServerResponse.createServerResponse(5, "密保问题不能为空，注册失败");
        }
        if (phone == null || username.equals("")) {
            //           throw BusinessException.creatBusinessException(session, "电话号码不能为空！", "3秒后将跳转到注册页面...", "register.jsp");
            return ServerResponse.createServerResponse(6, "电话号码不能为空，注册失败");
        }
        if (answer == null || username.equals("")) {
            //   throw BusinessException.creatBusinessException(session, "问题答案不能为空！", "3秒后将跳转到注册页面...", "register.jsp");
            return ServerResponse.createServerResponse(7, "问题答案不能为空，注册失败");
        }


        userInfo.setUsername(username);
        userInfo.setPassword(password);
        userInfo.setQuestion(question);
        userInfo.setPhone(phone);
        userInfo.setEmail(email);
        userInfo.setAnswer(answer);
        userInfo.setRole(role);


        int result = userService.register(session, userInfo);
        if (result > 0) {
//            throw BusinessException.creatBusinessException(session, "恭喜，注册成功！", "3秒后将跳转到登录页面...", "login.jsp");
            return ServerResponse.createServerResponse(1, userInfo, "注册成功！");
        } else {

            //        throw BusinessException.creatBusinessException(session, "很遗憾注册失败，请重新注册！", "3秒后将跳转到注册页面...", "register.jsp");
            return ServerResponse.createServerResponse(8, "很遗憾注册失败！");
        }


    }

    @RequestMapping("/login")
    public ServerResponse<UserInfo> login(@RequestParam("username") String username, @RequestParam("password") String password, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException, ServletException {
        response.setContentType("text/html;charset=UTF-8");
//        HttpSession session = request.getSession();
//        String username = request.getParameter("username");
//        String password = request.getParameter("password");

//          StringBuffer buffer=new StringBuffer();
//          buffer.append("{");
//          buffer.append("\"id\":34");
//          buffer.append(":");
//          buffer.append("34");
//          buffer.append("}");
//        PrintWriter pt=response.getWriter();
//        pt.print(buffer);


        if (username == null || username.equals("") || password == null || password.equals("")) {
//            BusinessException businessException = new BusinessException();
//            businessException.setMsg("账号密码不能为空！");
//            businessException.setWarn("3秒后将自动跳转到登录页面...");
//            businessException.setUrl("login.jsp");
//            session.setAttribute(Const.EXCEPTION, businessException);
//            throw BusinessException.creatBusinessException(session, "账号密码不能为空！", "3秒后将自动跳转到登录页面...", "login.jsp");


            return ServerResponse.createServerResponse(0, "账号密码不能为空！");
        }

        userInfo = userService.login(username, password);
        System.out.println("==userInfo==="+userInfo+"=======+++++++");
        if (userInfo != null) {//查询成功
//               Cookie username_cookie=new Cookie(Const.USERNAME_COOKIE,username);
//               Cookie password_cookie=new Cookie(Const.PASSWORD_COOKIE,password);
//               username_cookie.setMaxAge(3600*24*7);
//               password_cookie.setMaxAge(3600*24*7);
//               response.addCookie(username_cookie);
//               response.addCookie(password_cookie);
            String ip = IpUtils.getRemoteAddress(request);

            String mac = IpUtils.getMACAddress(ip);
            String token = MD5Utils.GetMD5Code(mac);

            Cookie cookie = new Cookie(Const.AUTOLIGINTOKEN, token);
            cookie.setMaxAge(3600 * 24 * 7);
            response.addCookie(cookie);
            userService.updateTokenById(userInfo.getId(), token);
            //           System.out.println(token);

            session.setAttribute("user", userInfo);
            //       session.setAttribute(Const.AUTOLIGINTOKEN, token);

            //  request.getRequestDispatcher("/manage/home.jsp").forward(request, response);
            return ServerResponse.createServerResponse(1, userInfo, "登录成功！");

        } else {//用户不存在   查询失败

            return ServerResponse.createServerResponse(0, "账号密码错误！");

        }


//
//        //用于json测试
//        Gson gson=new Gson();
//        String json=   gson.toJson(userInfo);
//        //    DaoUserInfo user=    gson.fromJson(json,DaoUserInfo.class );
//        response.setHeader("Access-Control-Allow-Origin", "*");
//        PrintWriter ps=response.getWriter();
//        ps.print(json);
    }


}
