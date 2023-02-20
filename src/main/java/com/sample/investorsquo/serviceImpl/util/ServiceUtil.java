package com.sample.investorsquo.serviceImpl.util;

import org.springframework.util.StringUtils;

public class ServiceUtil {

    public static String updateField(String old, String change) {
        return StringUtils.hasText(change) ? change : old;
    }
}
