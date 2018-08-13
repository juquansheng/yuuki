package com.malaxiaoyugan.yuukicore.framework.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
@Data
public class AppProperties {

    public Boolean enableKaptcha;

    public boolean getEnableKaptcha() {
        return null == enableKaptcha ? false : enableKaptcha;
    }

}
