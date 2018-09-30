package com.neuedu.Dao.impl;

import com.neuedu.Dao.ICategory;
import com.neuedu.comment.MyBatisUtil;
import com.neuedu.pojo.Category;
import com.neuedu.pojo.Product;
import com.neuedu.pojo.UserInfo;
import org.apache.ibatis.io.Resources;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Repository
//@Scope("singleton")
public class CategoryDaoImp implements ICategory{


    private int categoryId;
    @Autowired
    private SqlSession sqlSession;


    @Resource
    @Qualifier("category1")
    private Category category;


    public CategoryDaoImp (int categoryId){
        this.categoryId=categoryId;
    }



    public CategoryDaoImp (){


    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    //bean的初始化方法
    public void init(){
        System.out.println("=====实现init方法=====");

    }
    //bean的销毁方法
    public void destroy(){
        System.out.println("========实现destroy方法=========");

    }

    @Override
    public Category findProductByCategoryId(int categoryId) {

//        Reader reader ;
//
//        String configfile="mybatis-config.xml";
//
//        SqlSessionFactory sqlSessionFactory=null;
//        SqlSession sqlSession=null;
//
//        try {
//            reader= Resources.getResourceAsReader(configfile);
//            sqlSessionFactory=new SqlSessionFactoryBuilder().build(reader);
//           sqlSession=sqlSessionFactory.openSession();
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        System.out.println("sqlSession=========="+sqlSession);
        return   sqlSession.selectOne("com.neuedu.Dao.Category.findProductByCategoryId", categoryId);
    }

    @Override
    public List<Category> findCategorySubByCategoryId(int category_id) {
//         Reader reader;
//         SqlSessionFactory sqlSessionFactory=null;
//         SqlSession sqlSession=null;
//         String configfile="mybatis-config.xml";
//        try {
////            reader=Resources.getResourceAsReader(configfile);
////            sqlSessionFactory=new SqlSessionFactoryBuilder().build(reader);
//            sqlSession=sqlSessionFactory.openSession();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        return sqlSession.selectList("com.neuedu.Dao.Category.findCategorySubByCategoryId", category_id);
    }

    @Override
    public int addCategory(int parent_id, String category_name) {
//       Reader reader;
//       String configfile="mybatis-config.xml";
//       SqlSessionFactory sqlSessionFactory=null;
//       SqlSession sqlSession=null;
        Map<String,Object>map = new HashMap<>();
        map.put("parent_id",parent_id );
        map.put("category_name",category_name );
   //     System.out.println(category_name);

        try {
//            reader=Resources.getResourceAsReader(configfile);
//             sqlSessionFactory=new SqlSessionFactoryBuilder().build(reader);
//            sqlSession=sqlSessionFactory.openSession();
          int result=  sqlSession.insert("com.neuedu.Dao.Category.addCategory", map);
    //       sqlSession.commit();
      //      System.out.println(result);
            return  result;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public int findCategoryByName(String categoryName) {
     return  sqlSession.selectOne("com.neuedu.Dao.Category.findCategoryByName", categoryName);


    }

    @Override
    public int updateCategoryNameById(Integer categoryId, String newCategoryName) {
        Map<String,Object> map = new HashMap<>();
        map.put("categoryId",categoryId );
        map.put("newCategoryName",newCategoryName );
        return sqlSession.update("com.neuedu.Dao.Category.updateCategoryNameById", map);
    }

    @Override
    public Category findCategoryNameById(Integer caetgoryId) {
        return    sqlSession.selectOne("com.neuedu.Dao.Category.findCategoryNameById", caetgoryId);


    }
}
