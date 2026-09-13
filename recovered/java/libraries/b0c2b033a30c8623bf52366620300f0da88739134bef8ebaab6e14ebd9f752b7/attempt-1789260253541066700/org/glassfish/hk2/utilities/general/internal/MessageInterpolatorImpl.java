/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.utilities.general.internal;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;
import java.util.WeakHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.validation.MessageInterpolator;
import javax.validation.Payload;
import javax.validation.metadata.ConstraintDescriptor;

public class MessageInterpolatorImpl
implements MessageInterpolator {
    public static final String DEFAULT_VALIDATION_MESSAGES = "org.hibernate.validator.ValidationMessages";
    public static final String USER_VALIDATION_MESSAGES = "ValidationMessages";
    private static final Pattern MESSAGE_PARAMETER_PATTERN = Pattern.compile("(\\{[^\\}]+?\\})");
    private final Locale defaultLocale = Locale.getDefault();
    private final Map<LocalisedMessage, String> resolvedMessages = new WeakHashMap<LocalisedMessage, String>();
    private final boolean cacheMessages = true;

    @Override
    public String interpolate(String message, MessageInterpolator.Context context) {
        return this.interpolate(message, context, this.defaultLocale);
    }

    @Override
    public String interpolate(String message, MessageInterpolator.Context context, Locale locale) {
        Map<String, Object> annotationParameters = context.getConstraintDescriptor().getAttributes();
        LocalisedMessage localisedMessage = new LocalisedMessage(message, locale);
        String resolvedMessage = null;
        resolvedMessage = this.resolvedMessages.get(localisedMessage);
        if (resolvedMessage == null) {
            ContextResourceBundle userResourceBundle = new ContextResourceBundle(context, locale);
            ClassLoader cl = System.getSecurityManager() != null ? AccessController.doPrivileged(new PrivilegedAction<ClassLoader>(){

                @Override
                public ClassLoader run() {
                    return MessageInterpolator.class.getClassLoader();
                }
            }) : MessageInterpolator.class.getClassLoader();
            ResourceBundle defaultResourceBundle = ResourceBundle.getBundle(DEFAULT_VALIDATION_MESSAGES, locale, cl);
            resolvedMessage = message;
            boolean evaluatedDefaultBundleOnce = false;
            while (true) {
                String userBundleResolvedMessage = this.replaceVariables(resolvedMessage, userResourceBundle, locale, true);
                if (evaluatedDefaultBundleOnce && !this.hasReplacementTakenPlace(userBundleResolvedMessage, resolvedMessage)) break;
                resolvedMessage = this.replaceVariables(userBundleResolvedMessage, defaultResourceBundle, locale, false);
                evaluatedDefaultBundleOnce = true;
                this.resolvedMessages.put(localisedMessage, resolvedMessage);
            }
        }
        resolvedMessage = this.replaceAnnotationAttributes(resolvedMessage, annotationParameters);
        resolvedMessage = resolvedMessage.replace("\\{", "{");
        resolvedMessage = resolvedMessage.replace("\\}", "}");
        resolvedMessage = resolvedMessage.replace("\\\\", "\\");
        return resolvedMessage;
    }

    private boolean hasReplacementTakenPlace(String origMessage, String newMessage) {
        return !origMessage.equals(newMessage);
    }

    private String replaceVariables(String message, ResourceBundle bundle, Locale locale, boolean recurse) {
        Matcher matcher = MESSAGE_PARAMETER_PATTERN.matcher(message);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String parameter = matcher.group(1);
            String resolvedParameterValue = this.resolveParameter(parameter, bundle, locale, recurse);
            matcher.appendReplacement(sb, this.escapeMetaCharacters(resolvedParameterValue));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    private String replaceAnnotationAttributes(String message, Map<String, Object> annotationParameters) {
        Matcher matcher = MESSAGE_PARAMETER_PATTERN.matcher(message);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String parameter = matcher.group(1);
            Object variable = annotationParameters.get(this.removeCurlyBrace(parameter));
            String resolvedParameterValue = variable != null ? this.escapeMetaCharacters(variable.toString()) : parameter;
            matcher.appendReplacement(sb, resolvedParameterValue);
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    private String resolveParameter(String parameterName, ResourceBundle bundle, Locale locale, boolean recurse) {
        String parameterValue;
        try {
            if (bundle != null) {
                parameterValue = bundle.getString(this.removeCurlyBrace(parameterName));
                if (recurse) {
                    parameterValue = this.replaceVariables(parameterValue, bundle, locale, recurse);
                }
            } else {
                parameterValue = parameterName;
            }
        }
        catch (MissingResourceException e) {
            parameterValue = parameterName;
        }
        return parameterValue;
    }

    private String removeCurlyBrace(String parameter) {
        return parameter.substring(1, parameter.length() - 1);
    }

    private String escapeMetaCharacters(String s) {
        String escapedString = s.replace("\\", "\\\\");
        escapedString = escapedString.replace("$", "\\$");
        return escapedString;
    }

    private static class LocalisedMessage {
        private final String message;
        private final Locale locale;

        LocalisedMessage(String message, Locale locale) {
            this.message = message;
            this.locale = locale;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            LocalisedMessage that = (LocalisedMessage)o;
            if (this.locale != null ? !this.locale.equals(that.locale) : that.locale != null) {
                return false;
            }
            return !(this.message != null ? !this.message.equals(that.message) : that.message != null);
        }

        public int hashCode() {
            int result = this.message != null ? this.message.hashCode() : 0;
            result = 31 * result + (this.locale != null ? this.locale.hashCode() : 0);
            return result;
        }
    }

    private static class ContextResourceBundle
    extends ResourceBundle {
        ResourceBundle contextBundle;
        ResourceBundle userBundle;

        ContextResourceBundle(MessageInterpolator.Context context, Locale locale) {
            ConstraintDescriptor<?> descriptor = context.getConstraintDescriptor();
            Set<Class<Payload>> payload = descriptor.getPayload();
            if (!payload.isEmpty()) {
                final Class<Payload> payloadClass = payload.iterator().next();
                String baseName = payloadClass.getPackage().getName() + ".LocalStrings";
                ClassLoader cl = System.getSecurityManager() != null ? AccessController.doPrivileged(new PrivilegedAction<ClassLoader>(){

                    @Override
                    public ClassLoader run() {
                        return payloadClass.getClassLoader();
                    }
                }) : payloadClass.getClassLoader();
                try {
                    this.contextBundle = ResourceBundle.getBundle(baseName, locale, cl);
                }
                catch (MissingResourceException mre) {
                    this.contextBundle = null;
                }
            }
            try {
                ClassLoader cl = System.getSecurityManager() == null ? Thread.currentThread().getContextClassLoader() : AccessController.doPrivileged(new PrivilegedAction<ClassLoader>(){

                    @Override
                    public ClassLoader run() {
                        return Thread.currentThread().getContextClassLoader();
                    }
                });
                this.userBundle = ResourceBundle.getBundle(MessageInterpolatorImpl.USER_VALIDATION_MESSAGES, locale, cl);
            }
            catch (MissingResourceException mre) {
                this.userBundle = null;
            }
            if (this.userBundle != null) {
                this.setParent(this.userBundle);
            }
        }

        @Override
        protected Object handleGetObject(String key) {
            if (this.contextBundle != null) {
                return this.contextBundle.getObject(key);
            }
            return null;
        }

        @Override
        public Enumeration<String> getKeys() {
            TreeSet<String> keys = new TreeSet<String>();
            if (this.contextBundle != null) {
                keys.addAll(Collections.list(this.contextBundle.getKeys()));
            }
            if (this.userBundle != null) {
                keys.addAll(Collections.list(this.userBundle.getKeys()));
            }
            return Collections.enumeration(keys);
        }
    }
}

