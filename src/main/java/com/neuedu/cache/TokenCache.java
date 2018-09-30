package com.neuedu.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class TokenCache {

    private static LoadingCache<String, String> cacheloader = CacheBuilder
            .newBuilder()
            .initialCapacity(1000)  //初始化设定可存放1000个缓存项
            .maximumSize(10000)     //最大的缓存项是10000
            .refreshAfterWrite(2, TimeUnit.HOURS)  //定时刷新回收   超过两小时没访问该缓存区，则会被系统回收
            .build(new CacheLoader<String, String>() {

                //当从缓存区访问不到这个数据时，会返回"null"的字符串  而不是直接返回空

                @Override
                public String load(String s) throws Exception {
                    return "null";
                }
            });


//              在缓存中放数据
             public static  void setCacheloader(String key,String value){

                         cacheloader.put(key, value);


             }


//            在缓存中取数据

             public static String  getCacheloader(String key){

                 try {
                 return     cacheloader.get(key);
                 } catch (ExecutionException e) {
                     e.printStackTrace();
                 }

                 return null;

             }


}
