package com.chaco.chao.webfilter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chaco.chao.apolloconfig.ConfigUtil;
import com.chaco.chao.constants.ApolloConstants;
import com.chaco.chao.tools.finance.common.lock.RedisUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 防止重复提交过滤器
 * author:zhaopeiyan001
 * Date:2020-03-16 18:57
 */
public class RequestHandlerInterceptor extends HandlerInterceptorAdapter {

    private static Logger logger = LogManager.getLogger(RequestHandlerInterceptor.class);
    private static final String preKey = "duplicate:request:";
    @Resource
    private RedisUtils redisUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        logger.info("RequestHandlerInterceptor begin, uri:{}", request.getRequestURL());
        List<String> uriMappingList = JSONArray.parseArray(ConfigUtil.getConfigByKey(ApolloConstants.ROOT_LOGGER, "[]"), String.class);
        try {
            for (String confStr : uriMappingList) {
                JSONObject jsonObject = JSON.parseObject(confStr);
                String uri = jsonObject.getString("uri");
                if (uri.equals(request.getRequestURI())) {
                    Integer timeLimit = jsonObject.getInteger("timeLimit");
                    Integer countLimit = jsonObject.getInteger("countLimit");
                    String key = preKey + request.getRequestURI();
                    if (null != timeLimit) {
                        Long count = redisUtils.incr(key);
                        if (Long.valueOf(1).equals(count)) {
                            logger.info("request success,url:" + request.getRequestURL() + ",totalCount:" + count + "limitCount:" + countLimit);
                            redisUtils.expire(key, timeLimit);
                            return true;
                        }
                        if (count > countLimit) {
                            logger.info("request fail,url:" + request.getRequestURL() + ",totalCount:" + count + "limitCount:" + countLimit);
                            return false;
                        }
                    }
                }
            }
            return true;
        } catch (Exception e) {
            logger.info("RequestHandlerInterceptor error:", e.getMessage());
            return true;
        }
    }
}

