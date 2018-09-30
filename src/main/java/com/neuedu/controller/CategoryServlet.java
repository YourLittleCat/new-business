package com.neuedu.controller;

import com.google.gson.Gson;
import com.mysql.jdbc.StatementInterceptorV2;
import com.neuedu.Dao.ICategory;
import com.neuedu.Dao.impl.CategoryDaoImp;
import com.neuedu.comment.ServerResponse;
import com.neuedu.exception.BusinessException;
import com.neuedu.pojo.Category;
import com.neuedu.service.ICategoryService;
import com.neuedu.service.impl.CategoryServiceImp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;



@WebServlet("/manage/cate.do")
public class CategoryServlet extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");

        String operation = request.getParameter("operation");
        if (operation == null || operation.equals("")) {

//            throw BusinessException.creatBusinessException(request.getSession(), "operation参数不能少", "3秒后跳转到登录页面...", "login.jsp");
            ServerResponse serverResponse = ServerResponse.createServerResponse(1, "operation参数必传！");
            ServerResponse.convertToJson(serverResponse, response);

        }
        if (operation.equals("1")) { //查询子类别
            findCategorySubByCategoryId(request, response);

        }

        if (operation.equals("2")) {
            addCategory(request, response);

        }


    }


    //查询子类别

    public static void findCategorySubByCategoryId(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String categoryid = request.getParameter("category_id");
        if (categoryid == null || categoryid.equals("")) {
            //    BusinessException.creatBusinessException(session,"category的Id不能少！" ,"3秒后跳转到登录页面...", "login.jsp");
            ServerResponse serverResponse = ServerResponse.createServerResponse(1, "category的Id不能少！");

            ServerResponse.convertToJson(serverResponse, response);


        }

        int _categoryid = Integer.parseInt(categoryid);
        ICategory iCategory = new CategoryDaoImp();

        List<Category> list = new ArrayList<>();

        list = iCategory.findCategorySubByCategoryId(_categoryid);

        ServerResponse serverResponse = ServerResponse.createServerResponse(0, list, "数据响应成功！");

        ServerResponse.convertToJson(serverResponse, response);
    }


    public static void addCategory(HttpServletRequest request, HttpServletResponse response) {
        String category_name = request.getParameter("categoryName");
        String id = request.getParameter("parent_id");
        ServerResponse serverResponse = null;
        if (id.equals("") || id == null) {
            serverResponse = ServerResponse.createServerResponse(1, "父类ID不能为空！");
            ServerResponse.convertToJson(serverResponse, response);
        }
        int parent_id = Integer.parseInt(id);
        if (category_name.equals("") || category_name == null) {
            serverResponse = ServerResponse.createServerResponse(1, "categoryName不能为空！");
            ServerResponse.convertToJson(serverResponse, response);
        }
        ICategoryService iCategoryService = new CategoryServiceImp();
        int num = iCategoryService.findCategoryByName(category_name);

        if (num>1) {
            serverResponse = ServerResponse.createServerResponse(1, "品类名已存在,添加失败！");
            ServerResponse.convertToJson(serverResponse, response);

        } else {
            int result = iCategoryService.addCategory(parent_id, category_name);
            System.out.println(result);
            if (result <= 0) {

                serverResponse = ServerResponse.createServerResponse(1, "添加品类失败！");
                ServerResponse.convertToJson(serverResponse, response);
            }else {

                serverResponse = ServerResponse.createServerResponse(0, "添加品类成功！");
                ServerResponse.convertToJson(serverResponse, response);
            }
        }
    }


}
