package com.lianjia.finance.commons.util;

import org.springframework.context.annotation.Configuration;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;

/**
 * 获取apollo配置中心配置信息
 * @author zhangshihong
 * @date 2019年07月23日
 */
@Configuration
@EnableApolloConfig
public class ConfigUtil {

    private static final String nameSpace = "application";

    /**
     * 根据Namespace和key获取配置中心下配置信息
     * <p>获取不到返回null</p>
     * @param key
     * @param namespace
     * @return
     * @author zhangshihong
     * @date 2019年07月23日
     */
    public static String getSpaceConfigByKey(String key, String namespace) {
        Config config = ConfigService.getConfig(namespace);
        return config.getProperty(key, null);
    }

    /**
     * 根据key获取配置中心application下配置信息
     * <p>获取不到返回null</p>
     * @param key
     * @return
     * @author zhangshihong
     * @date 2019年07月23日
     */
    public static String getConfigByKey(String key) {
        Config config = ConfigService.getConfig(nameSpace);
        return config.getProperty(key, null);
    }
    /**
     * 根据key获取配置中心application下配置信息
     * <p>获取不到返回默认值</p>
     * @param key
     * @return
     * @author zhangshihong
     * @date 2019年07月23日
     */
    public static String getConfigByKey(String key, String defaultVal) {
        Config config = ConfigService.getConfig(nameSpace);
        return config.getProperty(key, defaultVal);
    }

    /**
     * 根据key获取配置中心application下配置信息
     * <p>获取的配置为key下json的item</p>
     * @param key
     * @param item
     * @return
     * @author zhangshihong
     * @date 2019年07月23日
     */
    public static String getConfigByItem(String key,String item) {
        Config config = ConfigService.getConfig(nameSpace);
        String values = config.getProperty(key, null);
        if(values!=null) {
            JSONObject obj = JSON.parseObject(values);
            return obj.getString(item);
        }
        return null;
    }
}
