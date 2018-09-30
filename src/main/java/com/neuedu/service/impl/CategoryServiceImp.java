package com.neuedu.service.impl;

import com.neuedu.Dao.ICategory;
import com.neuedu.Dao.impl.CategoryDaoImp;
import com.neuedu.pojo.Category;
import com.neuedu.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Set;
@Service
public class CategoryServiceImp implements ICategoryService {
  @Autowired
 ICategory category;

 /*   public CategoryServiceImp() {
        ApplicationContext Context = new ClassPathXmlApplicationContext("spring.xml");
        category = (ICategory) Context.getBean("categoryDaoImp");
        ICategory iCategory = new CategoryDaoImp();
        CategoryDaoImp category1 = (CategoryDaoImp) Context.getBean("categoryDaoImp");

//        System.out.println("11111111111111"+category);
//        System.out.println( "======="+category1.getCategory());

        //    System.out.println(category1.getCategory());

    }*/


    @Override
    public List<Category> findCategorySubByCategoryId(int category_id) {

        return category.findCategorySubByCategoryId(category_id);
    }


    @Override
    public int addCategory(int parent_Id, String categoryName) {


        return category.addCategory(parent_Id, categoryName);
    }

    @Override
    public int findCategoryByName(String categoryName) {


        return category.findCategoryByName(categoryName);
    }

    @Override
    public int updateCategoryNameById(Integer categoryId, String newCategoryName) {
        return category.updateCategoryNameById(categoryId, newCategoryName);
    }

    @Override
    public Category findCategoryById(Integer categoryId) {

        return category.findCategoryNameById(categoryId);
    }

    @Override
    public Set<Integer> findAllChildByCategoryId(Set<Integer>list, Integer categoryId) {
      Category category1=   category.findCategoryNameById(categoryId);
   //     System.out.println(category1+"====service里面的category1========");
      if (category1!=null){
          list.add(categoryId);

      }
       //查询当前的节点的所有子节点
  //      System.out.println(list+"=======service里面的list11111===============");
        List<Category>categoryList=  category.findCategorySubByCategoryId(categoryId);

     if (categoryList!=null&&categoryList.size()>0){
         for (Category category2:categoryList){
            findAllChildByCategoryId(list,category2.getId() );
         }
     }

  //      System.out.println(list+"=======service里面的list22222===============");

        return list;
    }


}
