package com.sample.investorsquo.config;

import com.sample.investorsquo.config.configProperties.KeycloakProperties;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.ClientResource;
import org.keycloak.admin.client.resource.RealmResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakApiConfig {

    private final KeycloakProperties keycloakProperties;

    public KeycloakApiConfig(KeycloakProperties keycloakProperties) {
        this.keycloakProperties = keycloakProperties;
    }

    @Bean
    public Keycloak keycloak() {
        return Keycloak.getInstance(keycloakProperties.realmServerUrl(),// keycloak address
                "master", // specify Realm master
                keycloakProperties.adminUsername(), // administrator account
                keycloakProperties.password(), // administrator password
                // Specify the client (admin-cli is the built-in client in Master Realm, Direct Access
                // Grants Enabled）
                "admin-cli");
    }

    @Bean
    public RealmResource realmResource(Keycloak keycloak) {
        return keycloak.realm(keycloakProperties.realm());
    }

    @Bean
    public ClientResource clientResource(RealmResource realmResource) {
        return realmResource.clients().get(keycloakProperties.clientId());
    }
}
