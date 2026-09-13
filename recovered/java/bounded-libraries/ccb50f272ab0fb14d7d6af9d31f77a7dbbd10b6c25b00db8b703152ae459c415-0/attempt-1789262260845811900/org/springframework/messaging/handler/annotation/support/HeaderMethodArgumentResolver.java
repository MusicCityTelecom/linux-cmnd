/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.beans.factory.config.ConfigurableBeanFactory
 *  org.springframework.core.MethodParameter
 *  org.springframework.core.convert.ConversionService
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 */
package org.springframework.messaging.handler.annotation.support;

import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.core.MethodParameter;
import org.springframework.core.convert.ConversionService;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandlingException;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.support.AbstractNamedValueMethodArgumentResolver;
import org.springframework.util.Assert;

public class HeaderMethodArgumentResolver
extends AbstractNamedValueMethodArgumentResolver {
    private static final Log logger = LogFactory.getLog(HeaderMethodArgumentResolver.class);

    public HeaderMethodArgumentResolver(ConversionService conversionService, @Nullable ConfigurableBeanFactory beanFactory) {
        super(conversionService, beanFactory);
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Header.class);
    }

    @Override
    protected AbstractNamedValueMethodArgumentResolver.NamedValueInfo createNamedValueInfo(MethodParameter parameter) {
        Header annot = (Header)parameter.getParameterAnnotation(Header.class);
        Assert.state((annot != null ? 1 : 0) != 0, (String)"No Header annotation");
        return new HeaderNamedValueInfo(annot);
    }

    @Override
    @Nullable
    protected Object resolveArgumentInternal(MethodParameter parameter, Message<?> message, String name) throws Exception {
        Object headerValue = message.getHeaders().get(name);
        Object nativeHeaderValue = this.getNativeHeaderValue(message, name);
        if (headerValue != null && nativeHeaderValue != null && logger.isDebugEnabled()) {
            logger.debug((Object)("A value was found for '" + name + "', in both the top level header map and also in the nested map for native headers. Using the value from top level map. Use 'nativeHeader.myHeader' to resolve the native header."));
        }
        return headerValue != null ? headerValue : nativeHeaderValue;
    }

    @Nullable
    private Object getNativeHeaderValue(Message<?> message, String name) {
        Map<String, List<String>> nativeHeaders = this.getNativeHeaders(message);
        if (name.startsWith("nativeHeaders.")) {
            name = name.substring("nativeHeaders.".length());
        }
        if (nativeHeaders == null || !nativeHeaders.containsKey(name)) {
            return null;
        }
        List<String> nativeHeaderValues = nativeHeaders.get(name);
        return nativeHeaderValues.size() == 1 ? nativeHeaderValues.get(0) : nativeHeaderValues;
    }

    @Nullable
    private Map<String, List<String>> getNativeHeaders(Message<?> message) {
        return (Map)message.getHeaders().get("nativeHeaders");
    }

    @Override
    protected void handleMissingValue(String headerName, MethodParameter parameter, Message<?> message) {
        throw new MessageHandlingException(message, "Missing header '" + headerName + "' for method parameter type [" + parameter.getParameterType() + "]");
    }

    private static final class HeaderNamedValueInfo
    extends AbstractNamedValueMethodArgumentResolver.NamedValueInfo {
        private HeaderNamedValueInfo(Header annotation) {
            super(annotation.name(), annotation.required(), annotation.defaultValue());
        }
    }
}

