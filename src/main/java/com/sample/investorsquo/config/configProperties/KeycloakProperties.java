package com.sample.investorsquo.config.configProperties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.keycloak")
public record KeycloakProperties(
    String realm,
    String realmServerUrl,
    String adminUsername,
    String password,
    String clientId
) {

}
