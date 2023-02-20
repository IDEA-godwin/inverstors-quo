package com.sample.investorsquo.config.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;

import java.security.Principal;

public class SecurityUtil {

    public static String bearerSubject() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return getAuthenticationPrincipal(authentication);
    }

    static String getAuthenticationPrincipal(Authentication authentication) {

        Object principalObject = authentication.getPrincipal();

        if (principalObject instanceof Principal principal) {
            return principal.getName();
        } else if (principalObject instanceof Jwt jwt) {
            return jwt.getSubject();
        } else {
            return principalObject.toString();
        }
    }
}
