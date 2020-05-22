package com.hrkj.scalp.util;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 全局参数定义
 */
@Repository("cacheInfo")
public class CacheInfo {

    /**
     * 储存配置信息
     */
    public static Map<String,String> commonInfo=new ConcurrentHashMap<>();;


    /**
     * 通过key直接获取value
     * @param key
     * @return
     * @throws Exception
     */
    public static String getCommonToKey(String key) throws Exception{
        String value="";
        if(commonInfo.containsKey(key)){    //当key存在时
            value=commonInfo.get(key);
        }else{
            throw new Exception("该key值不存在");
        }
        return value;
    }
}
