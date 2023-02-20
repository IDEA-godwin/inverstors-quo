package com.sample.investorsquo;

import com.sample.investorsquo.config.configProperties.KeycloakProperties;
import org.keycloak.adapters.springboot.KeycloakAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(value = {KeycloakProperties.class})
public class InvestorsQuoApplication {

    public static void main(String[] args) {
        SpringApplication.run(InvestorsQuoApplication.class, args);
    }
}
