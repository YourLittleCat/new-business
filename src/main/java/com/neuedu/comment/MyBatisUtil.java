package com.neuedu.comment;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

public class MyBatisUtil {

    private static SqlSessionFactory sqlSessionFactory=null;



    static {

        Reader reader=null;

        //根据路径加载配置文件
        String configile="mybatis-config.xml";

        try {

            reader=  Resources.getResourceAsReader(configile);
            sqlSessionFactory=new SqlSessionFactoryBuilder().build(reader);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    //生成一个sqlSession
    public static SqlSession getSqlSession(){


        return  sqlSessionFactory.openSession();

    }


    //关闭sqlSession
    public static  void   close(SqlSession sqlSession){
        sqlSession.close();


    }

}
