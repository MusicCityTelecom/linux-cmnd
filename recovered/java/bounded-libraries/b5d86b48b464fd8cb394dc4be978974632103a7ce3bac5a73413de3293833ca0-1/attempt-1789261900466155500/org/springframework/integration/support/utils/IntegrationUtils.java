/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.core.convert.ConversionService
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageDeliveryException
 *  org.springframework.messaging.MessageHandlingException
 *  org.springframework.messaging.MessagingException
 *  org.springframework.util.Assert
 */
package org.springframework.integration.support.utils;

import java.io.UnsupportedEncodingException;
import java.util.function.Supplier;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.integration.support.DefaultMessageBuilderFactory;
import org.springframework.integration.support.MessageBuilderFactory;
import org.springframework.integration.support.context.NamedComponent;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.MessageHandlingException;
import org.springframework.messaging.MessagingException;
import org.springframework.util.Assert;

public final class IntegrationUtils {
    private static final Log LOGGER = LogFactory.getLog(IntegrationUtils.class);
    private static final String INTERNAL_COMPONENT_PREFIX = "_org.springframework.integration";
    public static final String INTEGRATION_CONVERSION_SERVICE_BEAN_NAME = "integrationConversionService";
    public static final String INTEGRATION_MESSAGE_BUILDER_FACTORY_BEAN_NAME = "messageBuilderFactory";
    public static final boolean FATAL_WHEN_NO_BEANFACTORY = Boolean.parseBoolean(System.getenv("SI_FATAL_WHEN_NO_BEANFACTORY"));

    private IntegrationUtils() {
    }

    public static ConversionService getConversionService(BeanFactory beanFactory) {
        return IntegrationUtils.getBeanOfType(beanFactory, INTEGRATION_CONVERSION_SERVICE_BEAN_NAME, ConversionService.class);
    }

    public static MessageBuilderFactory getMessageBuilderFactory(@Nullable BeanFactory beanFactory) {
        MessageBuilderFactory messageBuilderFactory = null;
        if (beanFactory != null) {
            try {
                messageBuilderFactory = (MessageBuilderFactory)beanFactory.getBean(INTEGRATION_MESSAGE_BUILDER_FACTORY_BEAN_NAME, MessageBuilderFactory.class);
            }
            catch (Exception e) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug((Object)("No MessageBuilderFactory with name 'messageBuilderFactory' found: " + e.getMessage() + ", using default."));
                }
            }
        } else {
            LOGGER.debug((Object)"No 'beanFactory' supplied; cannot find MessageBuilderFactory, using default.");
            if (FATAL_WHEN_NO_BEANFACTORY) {
                throw new IllegalStateException("All Message creators need a BeanFactory");
            }
        }
        if (messageBuilderFactory == null) {
            messageBuilderFactory = new DefaultMessageBuilderFactory();
        }
        return messageBuilderFactory;
    }

    private static <T> T getBeanOfType(BeanFactory beanFactory, String beanName, Class<T> type) {
        Assert.notNull((Object)beanFactory, (String)"BeanFactory must not be null");
        if (!beanFactory.containsBean(beanName)) {
            return null;
        }
        return (T)beanFactory.getBean(beanName, type);
    }

    public static byte[] stringToBytes(String value, String encoding) {
        try {
            return value != null ? value.getBytes(encoding) : null;
        }
        catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static String bytesToString(byte[] bytes, String encoding) {
        try {
            return bytes == null ? null : new String(bytes, encoding);
        }
        catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static RuntimeException wrapInDeliveryExceptionIfNecessary(Message<?> message, Supplier<String> text, Throwable ex) {
        RuntimeException runtimeException;
        RuntimeException runtimeException2 = runtimeException = ex instanceof RuntimeException ? (RuntimeException)ex : new MessageDeliveryException(message, text.get(), ex);
        if (!(ex instanceof MessagingException) || ((MessagingException)ex).getFailedMessage() == null) {
            runtimeException = new MessageDeliveryException(message, text.get(), ex);
        }
        return runtimeException;
    }

    public static RuntimeException wrapInHandlingExceptionIfNecessary(Message<?> message, Supplier<String> text, Throwable ex) {
        RuntimeException runtimeException;
        RuntimeException runtimeException2 = runtimeException = ex instanceof RuntimeException ? (RuntimeException)ex : new MessageHandlingException(message, text.get(), ex);
        if (!(ex instanceof MessagingException) || ((MessagingException)ex).getFailedMessage() == null) {
            runtimeException = new MessageHandlingException(message, text.get(), ex instanceof IllegalStateException && ex.getCause() != null ? ex.getCause() : ex);
        }
        return runtimeException;
    }

    public static String obtainComponentName(NamedComponent component) {
        String name = component.getComponentName();
        if (name.charAt(0) == '_' && name.startsWith(INTERNAL_COMPONENT_PREFIX)) {
            name = name.substring(INTERNAL_COMPONENT_PREFIX.length() + 1);
        }
        return name;
    }
}

