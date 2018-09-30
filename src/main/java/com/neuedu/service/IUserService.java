package com.neuedu.service;

import com.neuedu.comment.ServerResponse;
import com.neuedu.pojo.UserInfo;

import javax.servlet.http.HttpSession;

public interface IUserService {

    //登录操作
    public UserInfo login(String username, String password);
    public int updateTokenById(int user_id,String token );
    public UserInfo findUserByToken(String token);
    public int register(HttpSession session, UserInfo userInfo);
    public String  findQuestionByUsername(HttpSession session,String username);
    //判断答案是否正确
    public ServerResponse<String> checkAnswer(HttpSession session, String username, String question, String answer);
    //进行密码修改
    public ServerResponse<Integer> updatePassword(HttpSession session,String username,String newpassword,String token);

    //登录状态下进行密码修改操作
    public ServerResponse<Integer> updatePasswordOnLine(String oldPassword,String newPassword,HttpSession session);


}
