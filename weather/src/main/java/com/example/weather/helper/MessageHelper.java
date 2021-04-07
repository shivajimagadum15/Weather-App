package com.example.weather.helper;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;

public final class MessageHelper {
    private static final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();

    public static String get(String code) {
        Locale locale = setMessageSourceAndGetLocale();

        return messageSource.getMessage(code, null, locale);
    }

    public static String get(String code, String[] translationArgs) {
        Locale locale = setMessageSourceAndGetLocale();

        return messageSource.getMessage(code, translationArgs, locale);
    }

    private static Locale setMessageSourceAndGetLocale() {
        messageSource.setBasename("messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setUseCodeAsDefaultMessage(true);

        return LocaleContextHolder.getLocale();
    }
}
