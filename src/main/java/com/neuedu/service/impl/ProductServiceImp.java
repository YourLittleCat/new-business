package com.neuedu.service.impl;

import com.neuedu.Dao.ICategory;
import com.neuedu.Dao.IProduct;
import com.neuedu.comment.DateUtil;
import com.neuedu.comment.PropertiesUtil;
import com.neuedu.comment.ResponseCode;
import com.neuedu.comment.ServerResponse;
import com.neuedu.pojo.Category;
import com.neuedu.pojo.Product;
import com.neuedu.service.ICategoryService;
import com.neuedu.service.IProductService;
import com.neuedu.vo.PageModel;
import com.neuedu.vo.ProductVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class ProductServiceImp implements IProductService {

    @Autowired
    IProduct iProduct;

    @Autowired
    ICategory iCategory;

    @Autowired
    ICategoryService categoryService;

    //更新或者是增加
    @Override
    public ServerResponse<String> addOrUpdateProduct(Product product) {
        if (product == null) {
            return ServerResponse.createServerResponse(ResponseCode.PRODUCT_NOT_NULL.getCode(), ResponseCode.PRODUCT_NOT_NULL.getMsg());
        }
        //手动设置main_img

        String subimgs = product.getSub_imags();
        if (subimgs != null && !subimgs.equals("")) {
            String[] sub_imags = subimgs.split(",");
            System.out.println(sub_imags.length + "=========sub_imags.length=========");


            if (sub_imags != null && sub_imags.length > 0) {
                product.setMain_imag(sub_imags[0]);

            }


        }


        //id不为空，则进行的是更新
        if (product.getId() != null) {

            int result = iProduct.updateProduct(product);

            if (result > 0) {

                return ServerResponse.createServerResponse(ResponseCode.PRODUCT_UPDATE_SUCCESS.getCode(), ResponseCode.PRODUCT_UPDATE_SUCCESS.getMsg());

            } else {


                return ServerResponse.createServerResponse(ResponseCode.PRODUCT_UPDATE_FAILD.getCode(), ResponseCode.PRODUCT_UPDATE_FAILD.getMsg());
            }
        }

        //如果id为null则进行的是增加product
        else {
            int result = iProduct.addProduct(product);

            if (result > 0) {

                return ServerResponse.createServerResponse(ResponseCode.PRODUCT_ADD_SUCCESS.getCode(), ResponseCode.PRODUCT_ADD_SUCCESS.getMsg());

            } else {


                return ServerResponse.createServerResponse(ResponseCode.PRODUCT_ADD_FAILD.getCode(), ResponseCode.PRODUCT_ADD_FAILD.getMsg());
            }


        }


    }


    //上下架
    @Override
    public ServerResponse<String> onLineOrOffLine(Integer id, Integer status) {


        Product product = new Product();
        product.setId(id);
        product.setStatus(status);
        int result = iProduct.updateProduct(product);

        if (result > 0) {

            return ServerResponse.createServerResponse(ResponseCode.PRODUCT_UPDATE_SUCCESS.getCode(), ResponseCode.PRODUCT_UPDATE_SUCCESS.getMsg());

        } else {


            return ServerResponse.createServerResponse(ResponseCode.PRODUCT_UPDATE_FAILD.getCode(), ResponseCode.PRODUCT_UPDATE_FAILD.getMsg());
        }


    }

    //根据商品id进行详细信息的查找

    @Override
    public ServerResponse<ProductVo> findProductById(Integer productId) {
        Product product = iProduct.findProductById(productId);

        ProductVo productVo = assembleProduct(product);
        if (product == null) {

            return ServerResponse.createServerResponse(ResponseCode.FIND_FAILD.getCode(), ResponseCode.FIND_FAILD.getMsg());
        } else {


            return ServerResponse.createServerResponse(ResponseCode.FIND_SUCCESS.getCode(), productVo, ResponseCode.FIND_SUCCESS.getMsg());
        }
    }

    @Override
    public ServerResponse<PageModel<ProductVo>> findProductByPageNo(Integer pageNo, Integer pageSize) {

        List<Product> list = iProduct.findProductByPageNo(pageNo, pageSize);
        List<ProductVo> productVos = new ArrayList<>();

        for (Product product : list) {
            productVos.add(assembleProduct(product));

        }

        long totalRecord = iProduct.findTotalRecord();
        long totalPage = (totalRecord / pageSize == 0 ? totalRecord / pageSize : (totalRecord / pageSize) + 1);
        PageModel<ProductVo> pageModel = new PageModel();
        pageModel.setData(productVos);
        pageModel.setTotalPage(totalPage);

//        if (pageNo>totalPage&&pageNo<1){
//
//            return  ServerResponse.createServerResponse(ResponseCode.OUT_OF_RANGE.getCode(),ResponseCode.OUT_OF_RANGE.getMsg());
//
//        }


        if (pageNo == 1) {
            pageModel.setFirst(true);


        } else {
            pageModel.setFirst(false);


        }

        if (pageNo == totalPage) {
            pageModel.setLast(true);


        } else {
            pageModel.setLast(false);


        }


        return ServerResponse.createServerResponse(ResponseCode.FIND_SUCCESS.getCode(), pageModel, ResponseCode.FIND_SUCCESS.getMsg());
    }

    @Override
    public ServerResponse<PageModel<ProductVo>> findProductByIdOrName(Integer pageNo, Integer pageSize, Integer productId, String productName) {
//        System.out.println(productName+"====2=====productname===========");
//        System.out.println(productId+"======2===productid===========");
//        System.out.println(pageSize+"======2====pageSize===========");
//        System.out.println(pageNo+"=======2===pageNO===========");

        if (productName != null && !productName.equals("")) {
            productName = "%" + productName + "%";
        }
        List<Product> list = iProduct.findProductByIdOrName(productId, productName, pageNo, pageSize);
        List<ProductVo> productVos = new ArrayList<>();
        for (Product product : list) {
            productVos.add(assembleProduct(product));
        }
        long totalRecord = iProduct.findTotalRecord(productId, productName);
        long totalPage = (totalRecord / pageSize == 0 ? totalRecord / pageSize : (totalRecord / pageSize) + 1);
        PageModel<ProductVo> pageModel = new PageModel();
        pageModel.setData(productVos);
        pageModel.setTotalPage(totalPage);
        if (pageNo == 1) {
            pageModel.setFirst(true);
        } else {
            pageModel.setFirst(false);

        }

        if (pageNo == totalPage) {
            pageModel.setLast(true);


        } else {
            pageModel.setLast(false);


        }
        pageModel.setCurrentPage(pageNo);


        return ServerResponse.createServerResponse(ResponseCode.FIND_SUCCESS.getCode(), pageModel, ResponseCode.FIND_SUCCESS.getMsg());
    }

    @Override
    public ServerResponse<String> upload(MultipartFile uploadFile) {
        //开始上传文件
        if (uploadFile != null) {
            //step1:获取到它的原文件的原始名称
            String filename = uploadFile.getOriginalFilename();
            System.out.println("=================" + filename);

            if (filename != null && !filename.equals("")) {
                //step2：获取到原文件的名字的扩展名
                int index = filename.lastIndexOf('.');
                String preName = filename.substring(index);

                //step3:生成新的名称
                String uuName = UUID.randomUUID().toString();
                String newFileName = uuName + preName;
                //step4:创建文件存放的路径和姓名
                String filePath = "G:\\kuwo";
                System.out.println("==================================111成功");
                File file = new File(filePath, newFileName);
                System.out.println("==================================222成功");
                try {
                    //将数据写入磁盘
                    uploadFile.transferTo(file);
                    return ServerResponse.createServerResponse(ResponseCode.UPLOAD_SUCCESS.getCode(), newFileName, ResponseCode.UPLOAD_SUCCESS.getMsg());

                    //                     System.out.println("==================================成功");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }


        }


        return ServerResponse.createServerResponse(ResponseCode.UPLOAD_FAILD.getCode(), ResponseCode.UPLOAD_FAILD.getMsg());
    }

    @Override
    public ServerResponse<ProductVo> findProductDetailByProductId(Integer productId) {
        //非空校验
        if (productId == null && productId.equals("")) {
            return ServerResponse.createServerResponse(ResponseCode.PRODUCT_ID_NOT_NULL.getCode(), ResponseCode.PRODUCT_ID_NOT_NULL.getMsg());
        }

        //进行查询
        Product p = iProduct.findProductById(productId);


        if (p == null) {

            return ServerResponse.createServerResponse(ResponseCode.PRODUCT_OFFLINE.getCode(), ResponseCode.PRODUCT_OFFLINE.getMsg());

        }

        //商品状态校验  下架的商品不能在前台查询到
        if (p.getStatus() == 1) {

            ProductVo productVo = assembleProduct(p);

            return ServerResponse.createServerResponse(ResponseCode.FIND_SUCCESS.getCode(), productVo, ResponseCode.FIND_SUCCESS.getMsg());

        } else {

            //返回信息

            return ServerResponse.createServerResponse(ResponseCode.PRODUCT_OFFLINE.getCode(), ResponseCode.PRODUCT_OFFLINE.getMsg());
        }
    }


    //前台根据商品id，name什么的动态查询商品信息
    @Override
    public ServerResponse<PageModel<ProductVo>> searchProduct(Integer categoryId, String productName, Integer pageNo, Integer pageSize,String orderBy) {
        //先进行数据校验
        //id和货物名称不能都为空


  //      System.out.println("productService=====categoryId===="+categoryId);
        if (categoryId == null && (productName == null || productName.equals(""))) {

            return ServerResponse.createServerResponse(ResponseCode.CATEGORYID_OR_PROAUCTNAME_NOT_NULL.getCode(), ResponseCode.CATEGORYID_OR_PROAUCTNAME_NOT_NULL.getMsg());

        }

        Set<Integer> set = new HashSet<>();

        //如果  id不为空，则根据id查询信息
        if (categoryId != null) {
            Category category = iCategory.findCategoryNameById(categoryId);
            //查询出来的商品类名为空
            if (category == null && (productName == null || productName.equals(""))) {
                PageModel<ProductVo> pageModel = new PageModel<>();
                pageModel.setCurrentPage(0);
                pageModel.setData(null);
                pageModel.setFirst(false);
                pageModel.setLast(false);
                pageModel.setTotalPage(0);


                return ServerResponse.createServerResponse(ResponseCode.PRODUCT_OFFLINE.getCode(), pageModel, ResponseCode.PRODUCT_OFFLINE.getMsg());
            }
            //查询出来的商品类名不为空

            set = categoryService.findAllChildByCategoryId(set, category.getId());

        }

        //进行判断orderBy的值是否为空
        Integer orderby=null;
        if (orderBy!=null&&!orderBy.equals("")){
            String  [] orderByArrr=    orderBy.split("_");
            if (orderByArrr!=null&&orderByArrr.length>1){
                if (orderByArrr[1].equals("desc")){
                    //如果是desc 则降序  如果是 asc或者为null  则是升序
                    orderby=1;

                }else {

                    orderby=0;
                }
            }
        }




        //商品名不为空


        if (productName != null && !productName.equals("")) {
            productName = "%" + productName + "%";
        }
        System.out.println("productService=====productName===="+productName);

        List<Product> productList = iProduct.findProductByCategoryIdProductName(set, productName, pageNo, pageSize, orderby);
        List<ProductVo> productVos = new ArrayList<>();
        for (Product product : productList) {
            productVos.add(assembleProduct(product));
            System.out.println(product);
        }


        long totalRecord = iProduct.findTotalRecord(set, productName);
        long totalPage = (totalRecord / pageSize == 0 ? totalRecord / pageSize : (totalRecord / pageSize) + 1);

        PageModel<ProductVo> pageModel = new PageModel();
        pageModel.setData(productVos);
        pageModel.setTotalPage(totalPage);
        if (pageNo == 1) {
            pageModel.setFirst(true);
        } else {
            pageModel.setFirst(false);

        }

        if (pageNo == totalPage) {
            pageModel.setLast(true);


        } else {
            pageModel.setLast(false);


        }
        pageModel.setCurrentPage(pageNo);


        return ServerResponse.createServerResponse(ResponseCode.FIND_SUCCESS.getCode(),pageModel,ResponseCode.FIND_SUCCESS.getMsg());
    }

    public ProductVo assembleProduct(Product product) {

        ProductVo productVo = new ProductVo();


        productVo.setCategory_id(product.getCategory_id());
        productVo.setCreate_time(DateUtil.dateChange(product.getCreate_time()));
        productVo.setDetail(product.getDetail());
        productVo.setId(product.getId());
        productVo.setImageHost(PropertiesUtil.getProperties("imageHost"));
        productVo.setMain_imag(product.getMain_imag());
        productVo.setName(product.getName());
        productVo.setPrice(product.getPrice());
        productVo.setStatus(product.getStatus());
        productVo.setStock(product.getStock());
        productVo.setUpdate_time(DateUtil.dateChange(product.getUpdate_time()));
        productVo.setSub_imags(product.getSub_imags());
        productVo.setSubtitle(product.getSubtitle());


        return productVo;
    }


}
