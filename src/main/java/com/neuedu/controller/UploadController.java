package com.neuedu.controller;

import com.neuedu.comment.ServerResponse;
import com.neuedu.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
public class UploadController {

   @RequestMapping(value = "/upload",method = RequestMethod.GET)
   public String upload(){

       return  "upload";
   }
   @Autowired
   IProductService productService;



   @RequestMapping(value = "/upload",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> upload(MultipartFile upload ){

       return productService.upload(upload);


   }



//    @RequestMapping(value = "/upload" ,method = RequestMethod.POST)
//    public String upload(@RequestParam("uploadFile") MultipartFile uploadFile){
//
//     //开始上传文件
//          if (uploadFile!=null){
//              //step1:获取到它的原文件的原始名称
//              String filename=  uploadFile.getOriginalFilename();
//  //            System.out.println("================="+filename);
//
//              if (filename!=null&&!filename.equals("")){
//                  //step2：获取到原文件的名字的扩展名
//                  int index=   filename.lastIndexOf('.');
//                String preName=  filename.substring(index);
//
//               //step3:生成新的名称
//               String uuName=UUID.randomUUID().toString();
//               String newFileName=uuName+preName;
//               //step4:创建文件存放的路径和姓名
//                String filePath="G:\\kuwo";
////                  System.out.println("==================================111成功");
//                  File file=new File(filePath,newFileName);
// //                 System.out.println("==================================222成功");
//                  try {
//                      //将数据写入磁盘
//                      uploadFile.transferTo(file);
// //                     System.out.println("==================================成功");
//                  } catch (IOException e) {
//                      e.printStackTrace();
//                  }
//
//              }
//
//
//          }
//
//
//
//
//        return  "upload";
//    }

}
