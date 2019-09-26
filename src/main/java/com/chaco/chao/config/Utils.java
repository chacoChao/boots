package com.chaco.chao.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author caoyixiong
 * @Date: 2018/12/13
 * @Copyright (c) 2015, lianjia.com All Rights Reserved
 */
@Slf4j
public class Utils {
    public static String subString(String data, int end) {
        if (StringUtils.isEmpty(data)) {
//            return Constants.BLACK_STRING;
            return "";
        }
        if (data.length() <= end) {
            return data;
        }
        return data.substring(0, end);
    }

    public static String getEmptyIfNull(String s){
        if(null == s){
            return "";
        }
        return s;
    }


    //金额是否合法，不能多于两位小数
    public static Boolean isLegalAmount(BigDecimal amount){
        if(amount == null){
            return false;
        }
        Pattern pattern=Pattern.compile("^-?(([1-9][0-9]*)|(([0]\\.\\d{1,2}|[1-9][0-9]*\\.\\d{1,2})))$"); // 判断小数点后2位的数字的正则表达式
        Matcher match=pattern.matcher(amount.toPlainString());
        if(match.matches()) {
            return true;
        }
        log.info("amount={}不合法", amount);
        return false;
    }

}
