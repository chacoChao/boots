package com.chaco.chao.tools.utils;

import java.util.UUID;

/**
 * Function:
 * <p>
 * User: chenhongliang001@ke.com
 * Date: 2019-05-09 22:57:00
 */
public class UuidUtil {

    public static String getUuid32(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}
