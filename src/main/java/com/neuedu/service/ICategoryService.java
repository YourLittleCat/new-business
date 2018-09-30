package com.neuedu.service;

import com.neuedu.pojo.Category;

import java.util.List;
import java.util.Set;

public interface ICategoryService {
       //查找子节点
    public List<Category> findCategorySubByCategoryId(int category_id);

    //增加节点

    public int addCategory(int parent_Id,String categoryName);


    //根据节点名查找节点

    public int findCategoryByName(String categoryName);

    public  int updateCategoryNameById(Integer categoryId,String newCategoryName);

    public  Category findCategoryById(Integer categoryId);

    public Set <Integer> findAllChildByCategoryId(Set<Integer> list,Integer categoryId);


}
