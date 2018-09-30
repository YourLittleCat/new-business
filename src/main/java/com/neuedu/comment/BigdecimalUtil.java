package com.neuedu.comment;

import java.io.Serializable;
import java.math.BigDecimal;

public class BigdecimalUtil implements Serializable {


    //加法

    public static BigDecimal add(BigDecimal big1, BigDecimal big2) {


        return big1.add(big2);
    }


    //减法

    public  static  BigDecimal sub(BigDecimal big1, BigDecimal big2){


        return  big1.subtract(big2);
    }


    //乘法

    public static BigDecimal multi(BigDecimal big1, BigDecimal big2) {


        return big1.multiply(big2);
    }


    //除法

    public  static  BigDecimal div(BigDecimal big1, BigDecimal big2){


        return  big1.divide(big2);
    }




}
