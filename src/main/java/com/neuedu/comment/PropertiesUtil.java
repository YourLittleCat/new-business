package com.neuedu.comment;

import org.springframework.web.servlet.resource.ResourceTransformer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

    private static Properties properties = new Properties();

    static {


        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("jdbc.properties");

        try {
            properties.load(inputStream);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperties(String key) {
        return properties.getProperty(key);


    }


}
