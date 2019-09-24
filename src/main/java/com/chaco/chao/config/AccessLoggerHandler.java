package com.chaco.chao.config;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public class AccessLoggerHandler extends HandlerInterceptorAdapter {
    private static Logger accessLog = LoggerFactory.getLogger("log.commons.access");

    private ThreadLocal<Long> startTime = new ThreadLocal<>();
    private static ThreadLocal<String> requestId = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        RequestWrapper requestWrapper = new RequestWrapper(request);
        startTime.set(System.currentTimeMillis());
        String id = UUID.randomUUID().toString().replace("-", "").substring(0, 12);
        requestId.set(id);
        accessLog.info("url={} requestId={} access begin, args={}, postBody={}", request.getRequestURL(), id, toJSONString(request.getParameterMap()),requestWrapper.getBody());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
        Long headerId = 0l;
        if(request.getHeader("headerId") != null){
            headerId = Long.parseLong(request.getHeader("headerId")) ;
        }
//      headerId = UserUtils.getLoginheaderId();
        accessLog.info("url={} requestId={} access end, cost time={}, user={}", request.getRequestURL(), requestId.get(),
                System.currentTimeMillis() - startTime.get(), headerId);
        startTime.remove();
        requestId.remove();
    }

    private static String toJSONString(Object object) {
        try {
            return JSONObject.toJSONString(object);
        } catch (Exception e) {
            accessLog.error(String.format("request请求参数序列化%s异常......", object), e);
        }
        return null;
    }

    public static String getRequestId(){
        return requestId.get();
    }

    /**
     * 手动记录用户行为
     * 主要用于记录附加的requestParam
     */
    public static void log(HttpServletRequest request, String resultCode, Object requestParam) {
        Long headerId = 0l;
        if(request.getHeader("headerId") != null){
            headerId = Long.parseLong(request.getHeader("headerId")) ;
        }
//      headerId = UserUtils.getLoginheaderId();
        accessLog.info("url={} access info , user={}, resultCode={}, requestParam={}", request.getRequestURL(), headerId, resultCode, toJSONString(requestParam));
    }
}
