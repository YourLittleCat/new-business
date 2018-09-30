package com.neuedu.service;

import com.neuedu.comment.ServerResponse;
import com.neuedu.pojo.Product;
import com.neuedu.vo.PageModel;
import com.neuedu.vo.ProductVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IProductService {


    public ServerResponse<String> addOrUpdateProduct(Product product);


   //商品上下架

    public ServerResponse<String> onLineOrOffLine(Integer id, Integer status);

     //根据id查找商品信息


    public ServerResponse<ProductVo> findProductById(Integer productId);

    //根据分页查询信息  pageNo


    public  ServerResponse<PageModel<ProductVo>> findProductByPageNo(Integer pageNo, Integer pageSize);


    //后台查询商品

    public ServerResponse<PageModel<ProductVo>> findProductByIdOrName(Integer pageNo,Integer pageSize,Integer productId,String productName);


    //图片上传

    public ServerResponse<String> upload(MultipartFile uploadFile);



    //根据productId查询商品详细信息

    public ServerResponse<ProductVo> findProductDetailByProductId(Integer productId);


   //前台根据商品id，name，什么的动态查询信息


    public  ServerResponse<PageModel<ProductVo>> searchProduct(Integer categoryId,String productName,Integer pageNo,Integer pageSize,String orderBy);




}
