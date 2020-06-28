package com.redissonlock.lock.common.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: JAVA8 stream流常用方法
 * @author: wangyz
 * @date: 2020/6/24 16:18
 */
public class StreamUtils {

    /**
     * 字符串to字符串List
     * @param str
     * @return
     */
    public static  List<String> strToStrList(String str){
        List<String> nameList= Arrays.stream(str.split(","))
                .collect(Collectors.toList());
        return nameList;
    }

    /**
     * 字符串toInteger List
     * @param str
     * @return
     */
    public static List<Integer> strToIntList(String str) {
        List<Integer> fileIdList = Arrays.stream(str.split(","))
                .map(id -> Integer.parseInt(id))
                .collect(Collectors.toList());
        return fileIdList;
    }


}