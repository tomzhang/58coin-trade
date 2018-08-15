package com.coin58.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author coin58 - 2018/3/26.
 */
@ConfigurationProperties("coin58")
@Data
@Component
public class Coin58Config {
    private String apiKey;
    private String secretKey;
}
