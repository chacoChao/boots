package com.chaco.chao.service.impl;

import com.chaco.chao.prop.GwProperties;
import com.chaco.chao.service.GwService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * author:zhaopeiyan001
 * Date:2019-08-08 17:19
 */
public class GwServiceImpl implements GwService {

    @Autowired
    private GwProperties gwProperties;

    @Override
    public void Hello() {
        String name = gwProperties.getName();
        System.out.println(name);
    }
}
