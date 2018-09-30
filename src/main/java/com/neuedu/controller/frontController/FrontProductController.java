package com.neuedu.controller.frontController;

import com.neuedu.Dao.IProduct;
import com.neuedu.comment.ServerResponse;
import com.neuedu.pojo.Product;
import com.neuedu.service.IProductService;
import com.neuedu.vo.PageModel;
import com.neuedu.vo.ProductVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class FrontProductController {
    @Autowired
    IProductService productService;

    //前台根据productId进行商品详情查询
        @RequestMapping("/findProductDetail")
    public ServerResponse<ProductVo> findProductDetail(Integer productId) {


      return   productService.findProductDetailByProductId(productId);

    }


 //根据商品名模糊查询或者根据用户品类id进行查询
   @RequestMapping("/search")
    public  ServerResponse<PageModel<ProductVo>> searchProduct(@RequestParam(required = false) Integer categoryId,
                                                                @RequestParam(required = false) String productName,
                                                               @RequestParam(defaultValue = "1") Integer pageNo,
                                                               @RequestParam(defaultValue = "10") Integer pageSize,
                                                               @RequestParam(required = false)  String orderBy    ){


        return productService.searchProduct(categoryId, productName, pageNo, pageSize,orderBy);


   }



}
