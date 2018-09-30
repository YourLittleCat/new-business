package com.neuedu.ioc;

import com.neuedu.pojo.Category;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {


   @Bean
    public Category category1(){

        Category category=new Category();
        category.setName("花花不是花花");
        return category;
    }
}
