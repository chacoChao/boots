package com.chaco.chao.jdkTest;

import java.util.concurrent.ConcurrentHashMap;

/**
 * author:zhaopeiyan001
 * Date:2020-04-20 11:54
 */
public class mapTest {

    public static void main(String[] args) {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key1", "value3");
        String key1 = map.get("key1");
        String key2 = map.get("key3");
        System.out.println("========================");
    }
}
