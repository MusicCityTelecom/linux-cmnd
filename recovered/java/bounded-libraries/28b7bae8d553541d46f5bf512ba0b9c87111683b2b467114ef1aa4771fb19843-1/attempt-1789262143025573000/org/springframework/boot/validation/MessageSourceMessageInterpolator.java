/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.validation.MessageInterpolator
 *  javax.validation.MessageInterpolator$Context
 *  org.springframework.context.MessageSource
 *  org.springframework.context.i18n.LocaleContextHolder
 */
package org.springframework.boot.validation;

import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;
import javax.validation.MessageInterpolator;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

class MessageSourceMessageInterpolator
implements MessageInterpolator {
    private static final char PREFIX = '{';
    private static final char SUFFIX = '}';
    private static final char ESCAPE = '\\';
    private final MessageSource messageSource;
    private final MessageInterpolator messageInterpolator;

    MessageSourceMessageInterpolator(MessageSource messageSource, MessageInterpolator messageInterpolator) {
        this.messageSource = messageSource;
        this.messageInterpolator = messageInterpolator;
    }

    public String interpolate(String messageTemplate, MessageInterpolator.Context context) {
        return this.interpolate(messageTemplate, context, LocaleContextHolder.getLocale());
    }

    public String interpolate(String messageTemplate, MessageInterpolator.Context context, Locale locale) {
        String message = this.replaceParameters(messageTemplate, locale);
        return this.messageInterpolator.interpolate(message, context, locale);
    }

    private String replaceParameters(String message, Locale locale) {
        return this.replaceParameters(message, locale, new LinkedHashSet<String>(4));
    }

    private String replaceParameters(String message, Locale locale, Set<String> visitedParameters) {
        StringBuilder buf = new StringBuilder(message);
        int parentheses = 0;
        int startIndex = -1;
        int endIndex = -1;
        for (int i = 0; i < buf.length(); ++i) {
            if (buf.charAt(i) == '\\') {
                ++i;
            } else if (buf.charAt(i) == '{') {
                if (startIndex == -1) {
                    startIndex = i;
                }
                ++parentheses;
            } else if (buf.charAt(i) == '}') {
                if (parentheses > 0) {
                    --parentheses;
                }
                endIndex = i;
            }
            if (parentheses != 0 || startIndex >= endIndex) continue;
            String parameter = buf.substring(startIndex + 1, endIndex);
            if (!visitedParameters.add(parameter)) {
                throw new IllegalArgumentException("Circular reference '{" + String.join((CharSequence)" -> ", visitedParameters) + " -> " + parameter + "}'");
            }
            String value = this.replaceParameter(parameter, locale, visitedParameters);
            if (value != null) {
                buf.replace(startIndex, endIndex + 1, value);
                i = startIndex + value.length() - 1;
            }
            visitedParameters.remove(parameter);
            startIndex = -1;
            endIndex = -1;
        }
        return buf.toString();
    }

    private String replaceParameter(String parameter, Locale locale, Set<String> visitedParameters) {
        String value = this.messageSource.getMessage(parameter = this.replaceParameters(parameter, locale, visitedParameters), null, null, locale);
        return value != null && !this.isUsingCodeAsDefaultMessage(value, parameter) ? this.replaceParameters(value, locale, visitedParameters) : null;
    }

    private boolean isUsingCodeAsDefaultMessage(String value, String parameter) {
        return value.equals(parameter);
    }
}

