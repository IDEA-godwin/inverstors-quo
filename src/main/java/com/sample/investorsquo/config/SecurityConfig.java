package com.sample.investorsquo.config;

import com.sample.investorsquo.config.handlers.CustomAccessDeniedExceptionHandler;
import com.sample.investorsquo.config.handlers.CustomAuthenticationEntryPointHandler;
import com.sample.investorsquo.config.handlers.KeycloakLogoutHandler;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final KeycloakLogoutHandler keycloakLogoutHandler;
    private final CustomAccessDeniedExceptionHandler accessDeniedExceptionHandler;
    private final CustomAuthenticationEntryPointHandler authenticationEntryPointHandler;

    @Bean
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeHttpRequests(authorizeReq -> authorizeReq
                    .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/docs/**", "/rest").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
                    .anyRequest().authenticated()
            )
            .oauth2Login().and()
            .logout(logout -> logout
                    .addLogoutHandler(keycloakLogoutHandler)
                    .logoutSuccessUrl("/"))
            .exceptionHandling(exception -> exception
                    .accessDeniedHandler(accessDeniedExceptionHandler)
                    .authenticationEntryPoint(authenticationEntryPointHandler));

        http.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

}
