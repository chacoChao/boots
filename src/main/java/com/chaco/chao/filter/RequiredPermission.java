package com.chaco.chao.filter;

import java.lang.annotation.*;

/**
 * author:zhaopeiyan001
 * Date:2019-04-10 15:51
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RequiredPermission {
    String value();
}
