package com.chaco.chao.tools.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;

/**
 * author:zhaopeiyan001
 * Date:2019-01-09 14:17
 */
public @Slf4j
class MockUtils {

    public static void assertAllFieldNotNull(Object object) throws IllegalAccessException {
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            log.info("now checking {}... with class{}", field.getName(), field.getGenericType());
            Assert.notNull(field.get(object));
        }
    }

    public static void fillAllColumn(Object object) throws IllegalAccessException {
        for (Field field : object.getClass().getDeclaredFields()) {
            if (field.getName().equals("id")) {
                continue;
            }
            field.setAccessible(true);
            if (field.getType().toString().endsWith("String")) {
                field.set(object, "1");
            }
            if (field.getType().toString().endsWith("Date")) {
                field.set(object, new Date());
            }
            if (field.getType().toString().endsWith("Integer")) {
                field.set(object, 1);
            }
            if (field.getType().toString().endsWith("Long")) {
                field.set(object, 1L);
            }
            if (field.getType().toString().endsWith("Float")) {
                field.set(object, 1F);
            }
            if (field.getType().toString().endsWith("BigDecimal")) {
                field.set(object, new BigDecimal(1));
            }
        }
    }
}
