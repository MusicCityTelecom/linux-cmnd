/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.convert.ConversionException
 *  org.springframework.core.convert.ConversionService
 *  org.springframework.core.convert.support.DefaultConversionService
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 */
package org.springframework.messaging.converter;

import org.springframework.core.convert.ConversionException;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.messaging.converter.SimpleMessageConverter;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

public class GenericMessageConverter
extends SimpleMessageConverter {
    private final ConversionService conversionService;

    public GenericMessageConverter() {
        this.conversionService = DefaultConversionService.getSharedInstance();
    }

    public GenericMessageConverter(ConversionService conversionService) {
        Assert.notNull((Object)conversionService, (String)"ConversionService must not be null");
        this.conversionService = conversionService;
    }

    @Override
    @Nullable
    public Object fromMessage(Message<?> message, Class<?> targetClass) {
        Object payload = message.getPayload();
        if (this.conversionService.canConvert(payload.getClass(), targetClass)) {
            try {
                return this.conversionService.convert(payload, targetClass);
            }
            catch (ConversionException ex) {
                throw new MessageConversionException(message, "Failed to convert message payload '" + payload + "' to '" + targetClass.getName() + "'", ex);
            }
        }
        return ClassUtils.isAssignableValue(targetClass, payload) ? payload : null;
    }
}

