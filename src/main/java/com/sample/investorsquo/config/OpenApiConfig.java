package com.sample.investorsquo.config;

import com.sample.investorsquo.config.configProperties.KeycloakProperties;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition
public class OpenApiConfig {

    private static final String OAUTH_SCHEME_NAME = "oAuth";
    private static final String PROTOCOL_URL_FORMAT = "%s/realms/%s/protocol/openid-connect";

    @Bean
    public OpenAPI customOpenAPI(KeycloakProperties keycloakProperties) {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes(OAUTH_SCHEME_NAME, createApiSecurityScheme(keycloakProperties)))
                .info(new Info().title("Investors Quo Application API").version("0.0.1")
                        .contact(contact())
                        .description(
                                "The Investors Quo RESTful service using springdoc-openapi and OpenAPI 3."))
                .addSecurityItem(new SecurityRequirement()
                        .addList(OAUTH_SCHEME_NAME));
    }

    private Contact contact() {
        Contact contact = new Contact();
        contact.setName("Godwin Igwurube");
        contact.setEmail("godwinaquinas@gmail.com");
        return contact;
    }

    private SecurityScheme createApiSecurityScheme(KeycloakProperties keycloakProperties) {

        OAuthFlows flows = createOAuthFlows(keycloakProperties);

        return new SecurityScheme()
                .type(SecurityScheme.Type.OAUTH2)
                .flows(flows);
    }

    private OAuthFlows createOAuthFlows(KeycloakProperties properties) {
        OAuthFlow flow = createAuthorizationCodeFlow(properties);

        return new OAuthFlows()
                .authorizationCode(flow);
    }

    private OAuthFlow createAuthorizationCodeFlow(KeycloakProperties properties) {
        var protocolUrl = String.format(PROTOCOL_URL_FORMAT, properties.realmServerUrl(), properties.realm());

        return new OAuthFlow()
                .authorizationUrl(protocolUrl + "/auth")
                .tokenUrl(protocolUrl + "/token")
                .scopes(new Scopes().addString("openid", ""));
    }
}
