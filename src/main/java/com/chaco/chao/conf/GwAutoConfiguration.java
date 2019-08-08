package com.chaco.chao.conf;

import com.chaco.chao.prop.GwProperties;
import com.chaco.chao.service.GwService;
import com.chaco.chao.service.impl.GwServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * author:zhaopeiyan001
 * Date:2019-08-08 17:25
 */
@Configuration
@ConditionalOnClass(GwService.class)
@EnableConfigurationProperties(GwProperties.class)
public class GwAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public GwService gwService() {
        return new GwServiceImpl();
    }
}
