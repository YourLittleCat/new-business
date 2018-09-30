package com.neuedu.Dao.impl;

import com.neuedu.Dao.IUserDao;
import com.neuedu.comment.JDBCUtil;
import com.neuedu.comment.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import com.neuedu.pojo.UserInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

public class UserDaoImp implements IUserDao{
    @Override
    public UserInfo getUser(String username, String password) {
        Connection connection = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "select * FROM neuedu_user WHERE username=? AND password=? ";
        try {
            connection = JDBCUtil.getConnection();
            pst = connection.prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, password);
            rs = pst.executeQuery();
            UserInfo user = new UserInfo();

            if (rs.first()) {
                int id = rs.getInt("id");
                String email = rs.getString("email");
                String user_name = rs.getString("username");
                String user_pwd = rs.getString("password");
                String user_question = rs.getString("question");
                String user_answer = rs.getString("answer");
                String user_phone = rs.getString("phone");
                Integer user_role = rs.getInt("role");


                user.setAnswer(user_answer);
                user.setId(id);
                user.setRole(user_role);
                user.setUsername(user_name);
                user.setQuestion(user_question);
                user.setPhone(user_phone);
                user.setPassword(user_pwd);
                user.setEmail(email);

                return user;
            }


//            System.out.println(connection);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(rs, connection, pst);
        }


        return null;
    }

    @Override
    public List<UserInfo> findAll() {
        return null;
    }

    @Override
    public int updateTokenById(int id, String token) {
        Connection connection = null;
        PreparedStatement pst = null;
        String sql = "update neuedu_user set token=? where id=?";
        try {
            connection = JDBCUtil.getConnection();
            pst = connection.prepareStatement(sql);
            pst.setString(1, token);
            pst.setInt(2, id);
            return pst.executeUpdate();


        } catch (Exception e) {
            e.printStackTrace();
        }



        return 0;
    }

    @Override
    public UserInfo findByToken(String token) {
        Connection connection = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "select * FROM neuedu_user WHERE token=? ";
        try {
            connection = JDBCUtil.getConnection();
            pst = connection.prepareCall(sql);
            pst.setString(1, token);

            rs = pst.executeQuery();
            UserInfo user = new UserInfo();

            if (rs.first()) {
                int id = rs.getInt("id");
                String email = rs.getString("email");
                String user_name = rs.getString("username");
                String user_pwd = rs.getString("password");
                String user_question = rs.getString("question");
                String user_answer = rs.getString("answer");
                String user_phone = rs.getString("phone");
                Integer user_level = rs.getInt("role");


                user.setAnswer(user_answer);
                user.setId(id);
                user.setRole(user_level);
                user.setUsername(user_name);
                user.setQuestion(user_question);
                user.setPhone(user_phone);
                user.setPassword(user_pwd);
                user.setEmail(email);

                return user;

            }


//            System.out.println(connection);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(rs, connection, pst);
        }

        return null;
    }

    @Override
    public int checkUsername(String username) {
        Connection connection = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "select count(1) FROM neuedu_user WHERE username=? ";
        try {
            connection = JDBCUtil.getConnection();
            pst = connection.prepareCall(sql);
            pst.setString(1, username);

            rs = pst.executeQuery();
            UserInfo user = new UserInfo();

            if (rs.first()) {
                int count = rs.getInt(1);


                return count;

            }


//            System.out.println(connection);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(rs, connection, pst);
        }


        return 0;
    }

    @Override
    public int checkEmail(String email) {
        Connection connection = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "select count(1) FROM neuedu_user WHERE email=? ";
        try {
            connection = JDBCUtil.getConnection();
            pst = connection.prepareCall(sql);
            pst.setString(1, email);

            rs = pst.executeQuery();
            UserInfo user = new UserInfo();

            if (rs.first()) {
                int count = rs.getInt(1);


                return count;

            }


//            System.out.println(connection);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(rs, connection, pst);
        }

        return 0;
    }

    @Override
    public int register(UserInfo userInfo) {
        Connection connection = null;
        PreparedStatement pst = null;

        String sql = "INSERT INTO neuedu_user (username,password,email,question,answer,phone,role,create_time,update_time) VALUES (?,?,?,?,?,?,?,now(),now())";
        try {
            connection = JDBCUtil.getConnection();
            pst = connection.prepareCall(sql);
            pst.setString(1, userInfo.getUsername());
            pst.setString(2, userInfo.getPassword());
            pst.setString(3, userInfo.getEmail());
            pst.setString(4, userInfo.getQuestion());
            pst.setString(5, userInfo.getAnswer());
            pst.setString(6, userInfo.getPhone());
            pst.setInt(7, userInfo.getRole());

            return pst.executeUpdate();


//            System.out.println(connection);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(connection, pst);
        }




        return 0;
    }

    @Override
    public String findQuestionByUsername(String username) {
        Connection connection = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "select question FROM neuedu_user WHERE username=? ";
        try {
            connection = JDBCUtil.getConnection();
            pst = connection.prepareCall(sql);
            pst.setString(1, username);

            rs = pst.executeQuery();
            UserInfo user = new UserInfo();

            if (rs.first()) {

                return rs.getString(1);


            }


//            System.out.println(connection);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(rs, connection, pst);
        }



        return null;
    }

    @Override
    public int checkAnswer(String answer, String question, String username) {
        Connection connection = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        //   System.out.println(answer+"==>"+question+"==>"+username);
        String sql = "select count(1)  FROM neuedu_user WHERE username=? AND question=? AND answer=? ";
        try {
            connection = JDBCUtil.getConnection();
            pst = connection.prepareCall(sql);
            pst.setString(1, username);
            pst.setString(2, question);
            pst.setString(3, answer);


            rs = pst.executeQuery();


            if (rs.first()) {

                return rs.getInt(1);


            }


//            System.out.println(connection);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(rs, connection, pst);
        }



        return 0;
    }

    @Override
    public int updatePassword(String username, String newpassword) {
        Connection connection = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "UPDATE neuedu_user SET  password=? WHERE  username=? ";
        try {
            connection = JDBCUtil.getConnection();
            pst = connection.prepareCall(sql);
            pst.setString(1, newpassword);
            pst.setString(2,username );


            return  pst.executeUpdate();


//            System.out.println(connection);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close( connection, pst);
        }


        return 0;
    }

    @Override
    public List<UserInfo> findAllByUsername(String username) {
        return null;
    }

    @Override
    public UserInfo findAllByOption(UserInfo userInfo) {
        return null;
    }

    @Override
    public int updateByOption(UserInfo userInfo) {
        return 0;
    }

    @Override

    public UserInfo findUserByUsername(String username) {
     SqlSession sqlSession= MyBatisUtil.getSqlSession();


        return sqlSession.selectOne("com.neuedu.Dao.IUserDao.findUserByUsername", username);
    }
}
