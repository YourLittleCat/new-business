package com.neuedu.Dao;

import com.neuedu.pojo.Category;
import com.neuedu.pojo.Product;
import com.neuedu.pojo.UserInfo;

import java.util.List;

public interface ICategory {

    public Category findProductByCategoryId(int categoryId);

    //获取分类平级的子节点  （也就是下一级的分类）
    public List<Category>findCategorySubByCategoryId(int category_id);

    public int  addCategory(int parent_id,String category_name);

    //根据品类名进行查找品类  返回一个品类

    public int findCategoryByName(String categoryName);


    //根据categoryId和newcategoryName进行修改categoryName

    public  int updateCategoryNameById(Integer categoryId,String newCategoryName);

    //根据id查找商品类名
    public Category findCategoryNameById(Integer caetgoryId);

}
