package com.chaco.chao.tools.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chaco.tools.httpexecutor.HttpRetryExecutors;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * author:zhaopeiyan001
 * Date:2019-03-20 14:30
 */
@Component
public class HttpUtils {

    private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    /**
     * post请求
     *
     * @param url    请求地址
     * @param params 请求参数 参数中的value将会调用toString()方法将其转换为String类型
     */
    public JSONObject postWithJson(String url, String params) {
        try {
            HttpHeaders headers = new HttpHeaders();
            MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
            headers.setContentType(type);
            headers.add("Accept", MediaType.APPLICATION_JSON.toString());
            HttpEntity<String> stringHttpEntity = new HttpEntity<>(params, headers);
            RestTemplate client = new RestTemplate();
            logger.info("postWithJson请求URL:{},请求参数:{}", url, params);
            return client.postForEntity(url, stringHttpEntity, JSONObject.class).getBody();
        } catch (Exception e) {
            logger.error("postWithJson请求接口异常!url:{}, params:{}, e={}", url, JSON.toJSONString(params), e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Resource
    private HttpRetryExecutors commonRetryExecutors;

    // 接受返回json格式
    public JSONObject postWithJsonRetry(String url, String params, String sign) {

        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        //添加签名
        if (!StringUtils.isBlank(sign)) {
            headers.add("signature", sign);
        }
        try {
            HttpEntity<String> stringHttpEntity = new HttpEntity<>(params, headers);
            logger.info("postWithJsonRetry请求URL:{},请求headers:{} 请求body:{}", url, JSON.toJSONString(headers), params);
            return commonRetryExecutors.executor(
                    (RestTemplate r) -> r.postForEntity(url, stringHttpEntity, JSONObject.class).getBody(), true);
        } catch (Exception e) {
            logger.error("postWithJsonRetry请求接口异常!URL:{},请求headers:{},请求body:{}, e={}", url,JSON.toJSONString(headers), params, e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
