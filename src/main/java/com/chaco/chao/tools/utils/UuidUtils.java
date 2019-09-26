package com.chaco.chao.tools.utils;

import java.util.Random;
import java.util.UUID;

/**
 * @author zhaoyuguang@lianjia.com on 2017/5/22 上午11:59
 */
public class UuidUtils {

    private static final int MIN_INT_5 = 1679616;
    private static final int MAX_INT_5 = 60466175;

    private static String Random5() {
        return Long.toString(new Random(UUID.randomUUID().hashCode()).nextInt(MAX_INT_5 - MIN_INT_5) + MIN_INT_5, Character.MAX_RADIX);
    }

    private static String Random10() {
        return Random5() + Random5();
    }

    public static String builder() {
        String prefix = Random10();
        String suffix = Long.toString(System.currentTimeMillis(), Character.MAX_RADIX);
        return prefix + "AD" + suffix;
    }
}
