/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.core.GenericTypeResolver
 *  org.springframework.core.MethodParameter
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 *  org.springframework.util.MimeType
 */
package org.springframework.messaging.converter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.GenericTypeResolver;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.converter.ContentTypeResolver;
import org.springframework.messaging.converter.DefaultContentTypeResolver;
import org.springframework.messaging.converter.SmartMessageConverter;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.util.Assert;
import org.springframework.util.MimeType;

public abstract class AbstractMessageConverter
implements SmartMessageConverter {
    protected final Log logger = LogFactory.getLog(this.getClass());
    private final List<MimeType> supportedMimeTypes = new ArrayList<MimeType>(4);
    @Nullable
    private ContentTypeResolver contentTypeResolver = new DefaultContentTypeResolver();
    private boolean strictContentTypeMatch = false;
    private Class<?> serializedPayloadClass = byte[].class;

    protected AbstractMessageConverter(MimeType supportedMimeType) {
        this(Collections.singletonList(supportedMimeType));
    }

    protected AbstractMessageConverter(MimeType ... supportedMimeTypes) {
        this(Arrays.asList(supportedMimeTypes));
    }

    protected AbstractMessageConverter(Collection<MimeType> supportedMimeTypes) {
        this.supportedMimeTypes.addAll(supportedMimeTypes);
    }

    public List<MimeType> getSupportedMimeTypes() {
        return Collections.unmodifiableList(this.supportedMimeTypes);
    }

    protected void addSupportedMimeTypes(MimeType ... supportedMimeTypes) {
        this.supportedMimeTypes.addAll(Arrays.asList(supportedMimeTypes));
    }

    public void setContentTypeResolver(@Nullable ContentTypeResolver resolver) {
        this.contentTypeResolver = resolver;
    }

    @Nullable
    public ContentTypeResolver getContentTypeResolver() {
        return this.contentTypeResolver;
    }

    public void setStrictContentTypeMatch(boolean strictContentTypeMatch) {
        if (strictContentTypeMatch) {
            Assert.notEmpty(this.getSupportedMimeTypes(), (String)"Strict match requires non-empty list of supported mime types");
            Assert.notNull((Object)this.getContentTypeResolver(), (String)"Strict match requires ContentTypeResolver");
        }
        this.strictContentTypeMatch = strictContentTypeMatch;
    }

    public boolean isStrictContentTypeMatch() {
        return this.strictContentTypeMatch;
    }

    public void setSerializedPayloadClass(Class<?> payloadClass) {
        Assert.isTrue((byte[].class == payloadClass || String.class == payloadClass ? 1 : 0) != 0, () -> "Payload class must be byte[] or String: " + payloadClass);
        this.serializedPayloadClass = payloadClass;
    }

    public Class<?> getSerializedPayloadClass() {
        return this.serializedPayloadClass;
    }

    @Override
    @Nullable
    public final Object fromMessage(Message<?> message, Class<?> targetClass) {
        return this.fromMessage(message, targetClass, null);
    }

    @Override
    @Nullable
    public final Object fromMessage(Message<?> message, Class<?> targetClass, @Nullable Object conversionHint) {
        if (!this.canConvertFrom(message, targetClass)) {
            return null;
        }
        return this.convertFromInternal(message, targetClass, conversionHint);
    }

    @Override
    @Nullable
    public final Message<?> toMessage(Object payload, @Nullable MessageHeaders headers) {
        return this.toMessage(payload, headers, null);
    }

    @Override
    @Nullable
    public final Message<?> toMessage(Object payload, @Nullable MessageHeaders headers, @Nullable Object conversionHint) {
        MessageHeaderAccessor accessor;
        if (!this.canConvertTo(payload, headers)) {
            return null;
        }
        Object payloadToUse = this.convertToInternal(payload, headers, conversionHint);
        if (payloadToUse == null) {
            return null;
        }
        MimeType mimeType = this.getDefaultContentType(payloadToUse);
        if (headers != null && (accessor = MessageHeaderAccessor.getAccessor(headers, MessageHeaderAccessor.class)) != null && accessor.isMutable()) {
            if (mimeType != null) {
                accessor.setHeaderIfAbsent("contentType", mimeType);
            }
            return MessageBuilder.createMessage(payloadToUse, accessor.getMessageHeaders());
        }
        MessageBuilder<Object> builder = MessageBuilder.withPayload(payloadToUse);
        if (headers != null) {
            builder.copyHeaders(headers);
        }
        if (mimeType != null) {
            builder.setHeaderIfAbsent("contentType", mimeType);
        }
        return builder.build();
    }

    protected boolean canConvertFrom(Message<?> message, Class<?> targetClass) {
        return this.supports(targetClass) && this.supportsMimeType(message.getHeaders());
    }

    protected boolean canConvertTo(Object payload, @Nullable MessageHeaders headers) {
        return this.supports(payload.getClass()) && this.supportsMimeType(headers);
    }

    protected boolean supportsMimeType(@Nullable MessageHeaders headers) {
        if (this.getSupportedMimeTypes().isEmpty()) {
            return true;
        }
        MimeType mimeType = this.getMimeType(headers);
        if (mimeType == null) {
            return !this.isStrictContentTypeMatch();
        }
        for (MimeType current : this.getSupportedMimeTypes()) {
            if (!current.getType().equals(mimeType.getType()) || !current.getSubtype().equals(mimeType.getSubtype())) continue;
            return true;
        }
        return false;
    }

    @Nullable
    protected MimeType getMimeType(@Nullable MessageHeaders headers) {
        return headers != null && this.contentTypeResolver != null ? this.contentTypeResolver.resolve(headers) : null;
    }

    @Nullable
    protected MimeType getDefaultContentType(Object payload) {
        List<MimeType> mimeTypes = this.getSupportedMimeTypes();
        return !mimeTypes.isEmpty() ? mimeTypes.get(0) : null;
    }

    protected abstract boolean supports(Class<?> var1);

    @Nullable
    protected Object convertFromInternal(Message<?> message, Class<?> targetClass, @Nullable Object conversionHint) {
        return null;
    }

    @Nullable
    protected Object convertToInternal(Object payload, @Nullable MessageHeaders headers, @Nullable Object conversionHint) {
        return null;
    }

    static Type getResolvedType(Class<?> targetClass, @Nullable Object conversionHint) {
        if (conversionHint instanceof MethodParameter) {
            MethodParameter param = (MethodParameter)conversionHint;
            if (Message.class.isAssignableFrom((param = param.nestedIfOptional()).getParameterType())) {
                param = param.nested();
            }
            Type genericParameterType = param.getNestedGenericParameterType();
            Class contextClass = param.getContainingClass();
            return GenericTypeResolver.resolveType((Type)genericParameterType, (Class)contextClass);
        }
        return targetClass;
    }
}

