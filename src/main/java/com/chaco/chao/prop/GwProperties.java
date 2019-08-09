package com.chaco.chao.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * author:zhaopeiyan001
 * Date:2019-08-08 17:20
 */
@Component
@ConfigurationProperties(prefix = "spring.gwname")
@Data
public class GwProperties {
    private String name = "chaco";
}
