/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 */
package org.springframework.messaging.converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.converter.SmartMessageConverter;
import org.springframework.util.Assert;

public class CompositeMessageConverter
implements SmartMessageConverter {
    private final List<MessageConverter> converters;

    public CompositeMessageConverter(Collection<MessageConverter> converters) {
        Assert.notEmpty(converters, (String)"Converters must not be empty");
        this.converters = new ArrayList<MessageConverter>(converters);
    }

    @Override
    @Nullable
    public Object fromMessage(Message<?> message, Class<?> targetClass) {
        for (MessageConverter converter : this.getConverters()) {
            Object result = converter.fromMessage(message, targetClass);
            if (result == null) continue;
            return result;
        }
        return null;
    }

    @Override
    @Nullable
    public Object fromMessage(Message<?> message, Class<?> targetClass, @Nullable Object conversionHint) {
        for (MessageConverter converter : this.getConverters()) {
            Object result = converter instanceof SmartMessageConverter ? ((SmartMessageConverter)converter).fromMessage(message, targetClass, conversionHint) : converter.fromMessage(message, targetClass);
            if (result == null) continue;
            return result;
        }
        return null;
    }

    @Override
    @Nullable
    public Message<?> toMessage(Object payload, @Nullable MessageHeaders headers) {
        for (MessageConverter converter : this.getConverters()) {
            Message<?> result = converter.toMessage(payload, headers);
            if (result == null) continue;
            return result;
        }
        return null;
    }

    @Override
    @Nullable
    public Message<?> toMessage(Object payload, @Nullable MessageHeaders headers, @Nullable Object conversionHint) {
        for (MessageConverter converter : this.getConverters()) {
            Message<?> result = converter instanceof SmartMessageConverter ? ((SmartMessageConverter)converter).toMessage(payload, headers, conversionHint) : converter.toMessage(payload, headers);
            if (result == null) continue;
            return result;
        }
        return null;
    }

    public List<MessageConverter> getConverters() {
        return this.converters;
    }

    public String toString() {
        return "CompositeMessageConverter[converters=" + this.getConverters() + "]";
    }
}

