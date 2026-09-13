/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 *  org.aspectj.lang.JoinPoint
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.i18n.LocaleContextHolder
 */
package org.apereo.inspektr.audit.spi.support;

import java.util.Locale;
import java.util.Objects;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;
import org.apereo.inspektr.audit.spi.support.ReturnValueAsStringResourceResolver;
import org.aspectj.lang.JoinPoint;
import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;

public class MessageBundleAwareResourceResolver
extends ReturnValueAsStringResourceResolver {
    private final ApplicationContext context;

    public MessageBundleAwareResourceResolver(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public String[] resolveFrom(JoinPoint joinPoint, Exception e) {
        String[] resolved = super.resolveFrom(joinPoint, e);
        return this.resolveMessagesFromBundleOrDefault(resolved, e);
    }

    private String[] resolveMessagesFromBundleOrDefault(String[] resolved, Exception e) {
        Locale locale = LocaleContextHolder.getLocale();
        String defaultKey = String.join((CharSequence)"_", StringUtils.splitByCharacterTypeCamelCase((String)e.getClass().getSimpleName())).toUpperCase();
        return (String[])Stream.of(resolved).map(key -> this.toResourceString(this.context.getMessage(key, null, defaultKey, locale))).filter(Objects::nonNull).toArray(String[]::new);
    }
}

