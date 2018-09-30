package com.neuedu.comment;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

public class JDBCUtil {
    private static Properties p = new Properties();

    static {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("jdbc.properties");



        try {
            p.load(is);
            String driver=p.getProperty("jdbc.driver");
            try {
                Class.forName(driver);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static Connection getConnection() throws Exception {

        return DriverManager.getConnection(p.getProperty("jdbc.url"), p.getProperty("jdbc.username"), p.getProperty("jdbc.password"));
    }



    public static  void close(Object...objects){
        if (objects!=null&&objects.length>0){

            for (Object obj:objects) {
                try {
                    if (obj instanceof Connection){
                        ((Connection) obj).close();
                    }
                    if (obj instanceof PreparedStatement){
                        ((PreparedStatement) obj).close();
                    }
                    if (obj instanceof ResultSet){
                        ((ResultSet) obj).close();
                    }
                }catch (Exception e){

                }


            }

        }


    }

}
