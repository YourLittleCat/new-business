package com.neuedu.comment;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static String dateChange(Date date) {


        if (date==null){
            return "";
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String newDate = simpleDateFormat.format(date);

        return newDate;
    }

}
