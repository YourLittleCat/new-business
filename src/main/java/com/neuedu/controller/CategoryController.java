package com.neuedu.controller;

import com.neuedu.Dao.ICategory;
import com.neuedu.comment.Const;
import com.neuedu.comment.ResponseCode;
import com.neuedu.comment.ServerResponse;
import com.neuedu.pojo.Category;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.ICategoryService;
import com.neuedu.service.impl.CategoryServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/manage/category")
public class CategoryController {
    @Autowired
    ICategory iCategory;
    @Autowired
    ICategoryService iCategoryService;

    @RequestMapping("/findCategory")
    public ServerResponse<List<Category>> findCategorySubByCategoryId(@RequestParam("categoryId") Integer categoryId,HttpSession session) {
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        //是否登录
        if (userInfo == null) {
            //    throw BusinessException.creatBusinessException(session, "未登录或者登录已过期，请重新登录！", "3秒后将自动跳转到登录页面...", "login.jsp");
            return ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }

        //是否有权限
        if (userInfo.getRole()!=0){
            return  ServerResponse.createServerResponse(ResponseCode.OUT_OF_AUTHORITY.getCode(),ResponseCode.OUT_OF_AUTHORITY.getMsg());

        }



        if (categoryId == null) {
            return ServerResponse.createServerResponse(ResponseCode.CATEGORY_ID_NOT_NULL.getCode(), ResponseCode.CATEGORY_ID_NOT_NULL.getMsg());

        }


        List<Category> list = new ArrayList<>();

        list = iCategory.findCategorySubByCategoryId(categoryId);

        return ServerResponse.createServerResponse(ResponseCode.CATEGORY_HAVE_FIND.getCode(), list, ResponseCode.CATEGORY_HAVE_FIND.getMsg());

    }

    @RequestMapping("/add")
    public ServerResponse<String> addCategory(@RequestParam("categoryName") String categoryName, @RequestParam("parentId") Integer parentId, HttpSession session) {
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);


        //验证是否登录
        if (userInfo == null) {
            //    throw BusinessException.creatBusinessException(session, "未登录或者登录已过期，请重新登录！", "3秒后将自动跳转到登录页面...", "login.jsp");
            return ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }
        System.out.println(userInfo+"===============userInfo==================");
        // 验证是否有权限
        if (userInfo.getRole()!=0){
          return  ServerResponse.createServerResponse(ResponseCode.OUT_OF_AUTHORITY.getCode(),ResponseCode.OUT_OF_AUTHORITY.getMsg());

        }




        ServerResponse serverResponse = null;
        if (parentId == null) {
            return serverResponse = ServerResponse.createServerResponse(ResponseCode.PARENTID_NOT_NULL.getCode(), ResponseCode.PARENTID_NOT_NULL.getMsg());
        }

        if (categoryName.equals("") || categoryName == null) {
            return serverResponse = ServerResponse.createServerResponse(ResponseCode.CATEGORY_NOT_NULL.getCode(), ResponseCode.CATEGORY_NOT_NULL.getMsg());

        }



        int num = iCategoryService.findCategoryByName(categoryName);
        System.out.println(num+"=======num==========");

        if (num > 0) {
            return serverResponse = ServerResponse.createServerResponse(ResponseCode.CATEGORY_EXIST.getCode(), ResponseCode.CATEGORY_EXIST.getMsg());


        } else {
            int result = iCategoryService.addCategory(parentId, categoryName);
            System.out.println(result);
            if (result <= 0) {

                return serverResponse = ServerResponse.createServerResponse(ResponseCode.CATEGORY_ADD_FAILD.getCode(), ResponseCode.CATEGORY_ADD_FAILD.getMsg());

            } else {

                return serverResponse = ServerResponse.createServerResponse(ResponseCode.CATEGORY_ADD_SUCCESS.getCode(), ResponseCode.CATEGORY_ADD_SUCCESS.getMsg());

            }
        }


    }

    @RequestMapping("/updateCategoryName")
    public ServerResponse<Category> updateCategoryNameById(Integer categoryId, String newCategoryName,HttpSession session) {
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
       //是否登录
        if (userInfo == null) {
            //    throw BusinessException.creatBusinessException(session, "未登录或者登录已过期，请重新登录！", "3秒后将自动跳转到登录页面...", "login.jsp");
            return ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }
       //是否有权限
        if (userInfo.getRole()!=0){
            return  ServerResponse.createServerResponse(ResponseCode.OUT_OF_AUTHORITY.getCode(),ResponseCode.OUT_OF_AUTHORITY.getMsg());

        }


        if (categoryId == null) {
            return ServerResponse.createServerResponse(ResponseCode.CATEGORY_ID_NOT_NULL.getCode(), ResponseCode.CATEGORY_ID_NOT_NULL.getMsg());

        }
        if (newCategoryName == null || newCategoryName.equals("")) {
            return ServerResponse.createServerResponse(ResponseCode.NEW_CATEGORY_NOT_NULL.getCode(), ResponseCode.NEW_CATEGORY_NOT_NULL.getMsg());

        }
             int result= iCategory.updateCategoryNameById(categoryId, newCategoryName);

             Category category= iCategory.findCategoryNameById(categoryId);


        if (result>0){
            return ServerResponse.createServerResponse(ResponseCode.UPDATE_NEW_CATEGORY_SUCCESS.getCode(), category,ResponseCode.UPDATE_NEW_CATEGORY_SUCCESS.getMsg());

        }else {
            return ServerResponse.createServerResponse(ResponseCode.UPDATE_NEW_CATEGORT_FAILD.getCode(),ResponseCode.UPDATE_NEW_CATEGORT_FAILD.getMsg());

        }

    }

    @RequestMapping("/findAll")
    public ServerResponse<Set<Integer>> findAllChildByCAtegoryId(Integer categoryId ,HttpSession session){
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);

        if (categoryId == null) {
            return ServerResponse.createServerResponse(ResponseCode.CATEGORY_ID_NOT_NULL.getCode(), ResponseCode.CATEGORY_ID_NOT_NULL.getMsg());

        }

        //验证是否登录
        if (userInfo == null) {
            //    throw BusinessException.creatBusinessException(session, "未登录或者登录已过期，请重新登录！", "3秒后将自动跳转到登录页面...", "login.jsp");
            return ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }

        // 验证是否有权限
        if (userInfo.getRole()!=0){
            return  ServerResponse.createServerResponse(ResponseCode.OUT_OF_AUTHORITY.getCode(),ResponseCode.OUT_OF_AUTHORITY.getMsg());

        }

        System.out.println(userInfo+"====categoryController里面的userInfo======");
        Set<Integer> set=new HashSet<>();


           set=iCategoryService.findAllChildByCategoryId(set,categoryId );

        System.out.println(set+"====set的值======");
        if (set==null&&set.size()<=0){
            return  ServerResponse.createServerResponse(ResponseCode.ALL_CATEGORY_CHILD_WITHOUT_FIND.getCode(),ResponseCode.ALL_CATEGORY_CHILD_WITHOUT_FIND.getMsg());

        }else {

            return ServerResponse.createServerResponse(ResponseCode.ALL_CATEGORY_CHILD_HAVE_FIND.getCode(),set,ResponseCode.ALL_CATEGORY_CHILD_HAVE_FIND.getMsg());



        }





    }


}
