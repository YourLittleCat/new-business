package com.neuedu.service.impl;


import com.neuedu.Dao.IUserDao;
import com.neuedu.Dao.impl.UserDaoImp;
import com.neuedu.cache.TokenCache;
import com.neuedu.comment.Const;
import com.neuedu.comment.MD5Utils;
import com.neuedu.comment.ServerResponse;
import com.neuedu.exception.BusinessException;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.UUID;

@Service
public class UserServiceImp implements IUserService {
      @Autowired
    IUserDao userDao = new UserDaoImp();

    @Override
    public UserInfo login(String username, String password) {

   //     System.out.println("serviceImp"+username+"=="+password);
        return userDao.getUser(username, MD5Utils.GetMD5Code(password));
    }

    @Override
    public int updateTokenById(int user_id, String token) {

        return userDao.updateTokenById(user_id, token);
    }

    @Override
    public UserInfo findUserByToken(String token) {
        return userDao.findByToken(token);
    }

    @Override
    public int register(HttpSession session, UserInfo userInfo) {
        //判断账号是否存在
        int result_username = userDao.checkUsername(userInfo.getUsername());
        if (result_username > 0) {//用户名存在
            throw BusinessException.creatBusinessException(session, "用户名已存在，请重新注册！", "3秒后将跳转到注册页面...", "register.jsp");

        }
        //邮箱是否存在
        int result_email = userDao.checkEmail(userInfo.getEmail());
        if (result_email > 0) {//用户名存在
            throw BusinessException.creatBusinessException(session, "邮箱已存在，请重新注册！", "3秒后将跳转到注册页面...", "register.jsp");

        }
        //密码进行加密
        userInfo.setPassword(MD5Utils.GetMD5Code(userInfo.getPassword()));




        //注册

        int result = userDao.register(userInfo);

  //      System.out.println(result);
        return result;
    }

    @Override
    public String findQuestionByUsername(HttpSession session, String username) {
        int result = userDao.checkUsername(username);

        if (username == null || username.equals("")) {
            throw BusinessException.creatBusinessException(session, "账号不能为空，请重新找回密码！", "3秒后将自动跳转到找回密码页面...", "findpwd.jsp");

        }
        if (result == 0 || result < 0) {

            throw BusinessException.creatBusinessException(session, "账号不存在，请重新确认之后再找回密码！", "3秒后将自动跳转到找回密码页面...", "findpwd.jsp");

        }


        return userDao.findQuestionByUsername(username);
    }

    //校验答案
    @Override
    public ServerResponse<String> checkAnswer(HttpSession session, String username, String question, String answer) {
        int count = userDao.checkUsername(username);

        if (count <= 0) {
            //      throw BusinessException.creatBusinessException(session, "账号不存在，请重新确认之后再找回密码！", "3秒后将自动跳转到找回密码页面...", "findpwd.jsp");
            return ServerResponse.createServerResponse(0, "账号不存在！");
        }

        int result = userDao.checkAnswer(answer, question, username);

        if (result > 0) {

            String forget_token = UUID.randomUUID().toString();
            TokenCache.setCacheloader(Const.TOKENPREFIX + username, forget_token);

            return ServerResponse.createServerResponse(0, forget_token, "密保答案校验成功！");
        } else {

            //    throw BusinessException.creatBusinessException(session, "问题回答错误，请重新确认之后再找回密码！", "3秒后将自动跳转到找回密码页面...", "answer.jsp");
            return ServerResponse.createServerResponse(1, "密保问题答案错误,校验失败！");
        }


    }

    @Override
    public ServerResponse<Integer> updatePassword(HttpSession session, String username, String newpassword, String token) {
        //判断用户名是否有效
        int result = userDao.checkUsername(username);
        if (result <= 0) {
          //  throw BusinessException.creatBusinessException(session, "输入账号不存在，请重新确认之后再找回密码！", "3秒后将自动跳转到找回密码页面...", "newpassword.jsp");
            return  ServerResponse.createServerResponse(0,"输入账号不存在，请重新确认之后再找回密码！");
        }
        //判断token是否有效
        String forgot_token = TokenCache.getCacheloader(Const.TOKENPREFIX + username);
 //       System.out.println(forgot_token+"===========");
  //      System.out.println(token+"++++++++++++");
        if (!token.equals(forgot_token)) {
      //      throw BusinessException.creatBusinessException(session, "token无效，请重新确认之后再找回密码！", "3秒后将自动跳转到找回密码页面...", "newpassword.jsp");
        return  ServerResponse.createServerResponse(0,"token无效，请重新确认之后再找回密码！");
        }
        //进行密码修改
        Integer count = userDao.updatePassword(username, MD5Utils.GetMD5Code(newpassword));

        if(count>0){
            return ServerResponse.createServerResponse(1,"修改密码成功");

        }else {


            return ServerResponse.createServerResponse(0,count,"密码修改失败！");
        }




    }

    @Override
    public ServerResponse<Integer> updatePasswordOnLine(String oldPassword, String newPassword, HttpSession session) {
        //判断是否登录
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);

        if (userInfo == null) {
        //    throw BusinessException.creatBusinessException(session, "未登录或者登录已过期，请重新登录！", "3秒后将自动跳转到登录页面...", "login.jsp");
       return ServerResponse.createServerResponse(2,"未登录或者登录已过期，请重新登录！");
        }

        //判断旧密码是否正确
        UserInfo oldUser = userDao.getUser(userInfo.getUsername(), MD5Utils.GetMD5Code(oldPassword));
        if (oldUser == null) {
     //       throw BusinessException.creatBusinessException(session, "旧密码输入错误，请重新输入！", "3秒后将自动跳转到登录页面...", "login.jsp");
   return  ServerResponse.createServerResponse(3,"旧密码输入错误，请重新输入！")    ;
        }

        //进行修改
        int result= userDao.updatePassword(userInfo.getUsername(), MD5Utils.GetMD5Code(newPassword));
          if (result>0){
              return  ServerResponse.createServerResponse(1,"密码修改成功");


          }else {

           return ServerResponse.createServerResponse(0,"密码修改失败");
          }

    }


}
