package com.neuedu.Dao;


import com.neuedu.pojo.UserInfo;

import java.util.List;

public interface IUserDao {
    //  登录操作
    public UserInfo getUser(String username, String password);

    public List<UserInfo> findAll();

    public int updateTokenById(int user_id, String token);

    public UserInfo findByToken(String token);

    public int checkUsername(String username);

    public int checkEmail(String email);

    public int register(UserInfo userInfo);
     //根据用户名找到问题
    public String findQuestionByUsername(String username);
    //判断答案是否正确
    public int checkAnswer(String answer, String question, String username);
    //修改密码
    public int updatePassword(String username, String newpassword);
   //如果username不为空  则根据username查询 如果username为空  则查询全部
    public List<UserInfo> findAllByUsername(String username);

    //如果用户不存在，就差id，id不存在就查email...
    public  UserInfo findAllByOption(UserInfo userInfo);

    //更新修改信息
    public int updateByOption(UserInfo userInfo);

    public UserInfo findUserByUsername(String username);





}
