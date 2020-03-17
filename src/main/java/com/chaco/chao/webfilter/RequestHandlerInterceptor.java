package com.chaco.chao.webfilter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chaco.chao.tools.finance.common.lock.RedisUtils;
import org.apache.catalina.Group;
import org.apache.catalina.Role;
import org.apache.catalina.User;
import org.apache.catalina.UserDatabase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
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
        List<String> uriMappingList = JSONArray.parseArray(ConfigUtil.getConfigByKey(ApolloConstants.UNSYNC_EXPORT_DUPLICATE_SUBMIT_CONFIG, "[]"), String.class);
        try {
//            User user = SessionUserUtil.getCurrentSessionUser();
            User user = new User() {
                @Override
                public String getFullName() {
                    return null;
                }

                @Override
                public void setFullName(String s) {

                }

                @Override
                public Iterator<Group> getGroups() {
                    return null;
                }

                @Override
                public String getPassword() {
                    return null;
                }

                @Override
                public void setPassword(String s) {

                }

                @Override
                public Iterator<Role> getRoles() {
                    return null;
                }

                @Override
                public UserDatabase getUserDatabase() {
                    return null;
                }

                @Override
                public String getUsername() {
                    return null;
                }

                @Override
                public void setUsername(String s) {

                }

                @Override
                public void addGroup(Group group) {

                }

                @Override
                public void addRole(Role role) {

                }

                @Override
                public boolean isInGroup(Group group) {
                    return false;
                }

                @Override
                public boolean isInRole(Role role) {
                    return false;
                }

                @Override
                public void removeGroup(Group group) {

                }

                @Override
                public void removeGroups() {

                }

                @Override
                public void removeRole(Role role) {

                }

                @Override
                public void removeRoles() {

                }

                @Override
                public String getName() {
                    return null;
                }
            }
            for (String confStr : uriMappingList) {
                JSONObject jsonObject = JSON.parseObject(confStr);
                String uri = jsonObject.getString("uri");
                if (uri.equals(request.getRequestURI())) {
                    Integer timeLimit = jsonObject.getInteger("timeLimit");
                    Integer countLimit = jsonObject.getInteger("countLimit");
                    String key = preKey + request.getRequestURI() + user.getId();
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

