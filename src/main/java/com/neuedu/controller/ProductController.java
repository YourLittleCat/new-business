package com.neuedu.controller;

import com.neuedu.comment.Const;
import com.neuedu.comment.ResponseCode;
import com.neuedu.comment.ServerResponse;
import com.neuedu.pojo.Product;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IProductService;
import com.neuedu.vo.PageModel;
import com.neuedu.vo.ProductVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpSession;
import java.security.PublicKey;
import java.util.List;

//@RestController
@RestController
@RequestMapping("/manage/product")
public class ProductController {
    @Autowired
    IProductService productService;

    @RequestMapping("/add")
    public ServerResponse<String> addOrUpdateProduct(Product product, HttpSession session) {
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        //是否登录
        if (userInfo == null) {
            //    throw BusinessException.creatBusinessException(session, "未登录或者登录已过期，请重新登录！", "3秒后将自动跳转到登录页面...", "login.jsp");
            return ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }

        //是否有权限
        if (userInfo.getRole() != 0) {
            return ServerResponse.createServerResponse(ResponseCode.OUT_OF_AUTHORITY.getCode(), ResponseCode.OUT_OF_AUTHORITY.getMsg());

        }

        if (product == null) {

            return ServerResponse.createServerResponse(ResponseCode.PRODUCT_NOT_NULL.getCode(), ResponseCode.PRODUCT_NOT_NULL.getMsg());


        } else {

            return productService.addOrUpdateProduct(product);


        }


    }

    @RequestMapping("/updateStatus")
    public ServerResponse<String> onLineOrOffLine(Integer id, Integer status, HttpSession session) {
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        //是否登录
        if (userInfo == null) {
            //    throw BusinessException.creatBusinessException(session, "未登录或者登录已过期，请重新登录！", "3秒后将自动跳转到登录页面...", "login.jsp");
            return ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }

        //是否有权限
        if (userInfo.getRole() != 0) {
            return ServerResponse.createServerResponse(ResponseCode.OUT_OF_AUTHORITY.getCode(), ResponseCode.OUT_OF_AUTHORITY.getMsg());

        }

        if (id == null) {


            return ServerResponse.createServerResponse(ResponseCode.PRODUCT_ID_NOT_NULL.getCode(), ResponseCode.PRODUCT_ID_NOT_NULL.getMsg());
        }


        if (status == null) {


            return ServerResponse.createServerResponse(ResponseCode.PRODUCT_STATUS_NOT_NULL.getCode(), ResponseCode.PRODUCT_STATUS_NOT_NULL.getMsg());

        }

        return productService.onLineOrOffLine(id, status);


    }


    @RequestMapping("/findProduct")
    public ServerResponse<ProductVo> findProductById(Integer productId, HttpSession session) {

        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        //是否登录
        if (userInfo == null) {
            //    throw BusinessException.creatBusinessException(session, "未登录或者登录已过期，请重新登录！", "3秒后将自动跳转到登录页面...", "login.jsp");
            return ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }

        //是否有权限
        if (userInfo.getRole() != 0) {
            return ServerResponse.createServerResponse(ResponseCode.OUT_OF_AUTHORITY.getCode(), ResponseCode.OUT_OF_AUTHORITY.getMsg());

        }

        if (productId == null) {


            return ServerResponse.createServerResponse(ResponseCode.PRODUCT_ID_NOT_NULL.getCode(), ResponseCode.PRODUCT_ID_NOT_NULL.getMsg());
        }


        return productService.findProductById(productId);

    }


    @RequestMapping("/findProductByPageNo")
    public ServerResponse<PageModel<ProductVo>> findProductByPageNo(@RequestParam(required = false,defaultValue = "1") Integer pageNo, @RequestParam(required = false,defaultValue = "10") Integer pageSize, HttpSession session) {



        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        //是否登录
        if (userInfo == null) {

            return ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }

        //是否有权限
        if (userInfo.getRole() != 0) {
            return ServerResponse.createServerResponse(ResponseCode.OUT_OF_AUTHORITY.getCode(), ResponseCode.OUT_OF_AUTHORITY.getMsg());

        }

      return   productService.findProductByPageNo(pageNo, pageSize);

    }

@RequestMapping("/search")
    public ServerResponse<PageModel<ProductVo>> search(@RequestParam(required = false) Integer productId,
                                                       @RequestParam(required = false)  String productName,
                                                       @RequestParam(defaultValue = "1") Integer pageNo,
                                                       @RequestParam(defaultValue = "10")Integer pageSize,
                                                       HttpSession session){
//    System.out.println(productName+"====1=====productname===========");
//    System.out.println(productId+"======1===productid===========");
//    System.out.println(pageSize+"======1====pageSize===========");
//    System.out.println(pageNo+"=======1===pageNO===========");

        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        //是否登录
        if (userInfo == null) {

            return ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }

        //是否有权限
        if (userInfo.getRole() != 0) {
            return ServerResponse.createServerResponse(ResponseCode.OUT_OF_AUTHORITY.getCode(), ResponseCode.OUT_OF_AUTHORITY.getMsg());

        }


        return productService.findProductByIdOrName(pageNo, pageSize, productId, productName);


    }

//上传图片
    @RequestMapping("/upload")
   public  ServerResponse<String> uploadPic(MultipartFile file,HttpSession session){
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        //是否登录
        if (userInfo == null) {

            return ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMsg());
        }

        //是否有权限
        if (userInfo.getRole() != 0) {
            return ServerResponse.createServerResponse(ResponseCode.OUT_OF_AUTHORITY.getCode(), ResponseCode.OUT_OF_AUTHORITY.getMsg());

        }


        return  productService.upload(file);


    }

//     //localhost:8080/manage/product/add?username=xxx
//    @RequestMapping("/add")
//    public String addproduct(@RequestParam("username") String username) {
//
//        System.out.println("=====xixixi=====");
//        System.out.println(username);
//
//
//
//        return "addproduct";
//
//
//    }
//
//
////通过modelAndView进行反馈
//     @RequestMapping("/show")
//    public ModelAndView findProduct(@RequestParam("productname") String productname,ModelAndView modelAndView){
//         Product product=new Product();
//         product.setName(productname);
//         modelAndView.addObject("product",product );
//         modelAndView.setViewName("showProduct");
//
//     return modelAndView;
//     }
//
//
//     //通过model进行反馈
//    @RequestMapping("/shows")
//    public String findProductByModel(String productname, Model model){
//        Product product =new Product();
//        product.setName(productname);
//
//        model.addAttribute("product", product);
//
//        return  "showProduct";
//
//    }
//
//
//    //放到map中进行反馈
//    @RequestMapping("/showss")
//    public String findProductByMap(String productname, Map<String ,Product> map){
//
//        Product product =new Product();
//        product.setName(productname);
//        map.put("product", product) ;
//        return "showProduct";
//    }
//
//    //通过request进行反馈
//
//    @RequestMapping("/showsss")
// public  String findProductByRequest(String productname, HttpServletRequest request , HttpSession session){
//        Product product=new Product();
//        product.setName(productname);
//        session.setAttribute("product", product);
//        request.setAttribute("productname",productname );
//
//        return  "showProduct";
//
//    }
//
//    @RequestMapping("/showsa")
//    @ResponseBody
//
//    //使json对象转换成字符串格式显示在前端（只要添加@ResponseBody即可）
//    public Product findProduct(String productname){
//        Product product =new Product();
//        product.setName(productname);
//
//        return  product;
//
//    }
//      @RequestMapping(value="/{operation}/{productname}")
//      @ResponseBody
//    public  Product getProduct (@PathVariable("operation") int operation,@PathVariable("productname") String productname){
//      Product product =new Product();
//      product.setName(productname);
//          System.out.println("====operation=====");
//
// return  product;
//    }


}
