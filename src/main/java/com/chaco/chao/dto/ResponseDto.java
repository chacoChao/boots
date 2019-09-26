package com.chaco.chao.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author caoyixiong
 * @Date: 2018/12/12
 * @Copyright (c) 2015, lianjia.com All Rights Reserved
 */
@Data
public class ResponseDto<T> implements Serializable {

    public static final Integer RESPONSE_CODE_FAIL = 1;
    public static final Integer RESPONSE_CODE_SUCCESS = 0;

    private Integer errorcode;
    private String errmsg;
    private T data;

    public static <T> ResponseDto<T> buildSuccess(T t) {
        ResponseDto<T> response = new ResponseDto<T>();
        response.setErrorcode(RESPONSE_CODE_SUCCESS);
        response.setData(t);
        return response;
    }

    public static ResponseDto buildSuccessMap(Object key, Object value) {
        ResponseDto response = new ResponseDto();
        response.setErrorcode(RESPONSE_CODE_SUCCESS);
        Map<Object, Object> map = new HashMap<>();
        map.put(key, value);
        response.setData(map);
        return response;
    }

    public static <T> ResponseDto<T> buildSuccess() {
        ResponseDto<T> response = new ResponseDto<T>();
        response.setErrorcode(RESPONSE_CODE_SUCCESS);
        return response;
    }
    public static <T> ResponseDto<T> buildFail(String message) {
        return buildFail(RESPONSE_CODE_FAIL, message);
    }

    public static <T> ResponseDto<T> buildFail(Integer code, String message) {
        ResponseDto<T> response = new ResponseDto<T>();
        response.setErrorcode(code);
        response.setErrmsg(message);
        return response;
    }

    public boolean isSuccess() {
        return RESPONSE_CODE_SUCCESS.equals(errorcode);
    }
}
