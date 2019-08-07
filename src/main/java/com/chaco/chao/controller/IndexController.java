package com.chaco.chao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * author:zhaopeiyan001
 * Date:2019-08-07 14:22
 */
@Controller
public class IndexController {

    @RequestMapping(value = "/index/top", method = RequestMethod.GET)
    @ResponseBody
    public String indexTop() {
        return "index top";
    }
}
