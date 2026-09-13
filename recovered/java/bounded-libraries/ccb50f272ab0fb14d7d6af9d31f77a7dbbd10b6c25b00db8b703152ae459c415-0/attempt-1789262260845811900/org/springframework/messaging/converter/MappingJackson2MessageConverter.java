/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonView
 *  com.fasterxml.jackson.core.JsonEncoding
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.JavaType
 *  com.fasterxml.jackson.databind.JsonMappingException
 *  com.fasterxml.jackson.databind.MapperFeature
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.SerializationFeature
 *  org.springframework.core.MethodParameter
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.MimeType
 */
package org.springframework.messaging.converter;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.concurrent.atomic.AtomicReference;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.converter.AbstractMessageConverter;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.MimeType;

public class MappingJackson2MessageConverter
extends AbstractMessageConverter {
    private ObjectMapper objectMapper = this.initObjectMapper();
    @Nullable
    private Boolean prettyPrint;

    public MappingJackson2MessageConverter() {
        super(new MimeType("application", "json"));
    }

    public MappingJackson2MessageConverter(MimeType ... supportedMimeTypes) {
        super(supportedMimeTypes);
    }

    private ObjectMapper initObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        Assert.notNull((Object)objectMapper, (String)"ObjectMapper must not be null");
        this.objectMapper = objectMapper;
        this.configurePrettyPrint();
    }

    public ObjectMapper getObjectMapper() {
        return this.objectMapper;
    }

    public void setPrettyPrint(boolean prettyPrint) {
        this.prettyPrint = prettyPrint;
        this.configurePrettyPrint();
    }

    private void configurePrettyPrint() {
        if (this.prettyPrint != null) {
            this.objectMapper.configure(SerializationFeature.INDENT_OUTPUT, this.prettyPrint.booleanValue());
        }
    }

    @Override
    protected boolean canConvertFrom(Message<?> message, @Nullable Class<?> targetClass) {
        AtomicReference causeRef;
        if (targetClass == null || !this.supportsMimeType(message.getHeaders())) {
            return false;
        }
        JavaType javaType = this.objectMapper.constructType(targetClass);
        if (this.objectMapper.canDeserialize(javaType, causeRef = new AtomicReference())) {
            return true;
        }
        this.logWarningIfNecessary((Type)javaType, (Throwable)causeRef.get());
        return false;
    }

    @Override
    protected boolean canConvertTo(Object payload, @Nullable MessageHeaders headers) {
        if (!this.supportsMimeType(headers)) {
            return false;
        }
        AtomicReference causeRef = new AtomicReference();
        if (this.objectMapper.canSerialize(payload.getClass(), causeRef)) {
            return true;
        }
        this.logWarningIfNecessary(payload.getClass(), (Throwable)causeRef.get());
        return false;
    }

    protected void logWarningIfNecessary(Type type, @Nullable Throwable cause) {
        boolean debugLevel;
        if (cause == null) {
            return;
        }
        boolean bl = debugLevel = cause instanceof JsonMappingException && cause.getMessage().startsWith("Cannot find");
        if (debugLevel ? this.logger.isDebugEnabled() : this.logger.isWarnEnabled()) {
            String msg = "Failed to evaluate Jackson " + (type instanceof JavaType ? "de" : "") + "serialization for type [" + type + "]";
            if (debugLevel) {
                this.logger.debug((Object)msg, cause);
            } else if (this.logger.isDebugEnabled()) {
                this.logger.warn((Object)msg, cause);
            } else {
                this.logger.warn((Object)(msg + ": " + cause));
            }
        }
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Nullable
    protected Object convertFromInternal(Message<?> message, Class<?> targetClass, @Nullable Object conversionHint) {
        JavaType javaType = this.objectMapper.constructType(MappingJackson2MessageConverter.getResolvedType(targetClass, conversionHint));
        Object payload = message.getPayload();
        Class<?> view = this.getSerializationView(conversionHint);
        try {
            if (ClassUtils.isAssignableValue(targetClass, payload)) {
                return payload;
            }
            if (payload instanceof byte[]) {
                if (view != null) {
                    return this.objectMapper.readerWithView(view).forType(javaType).readValue((byte[])payload);
                }
                return this.objectMapper.readValue((byte[])payload, javaType);
            }
            if (view != null) {
                return this.objectMapper.readerWithView(view).forType(javaType).readValue(payload.toString());
            }
            return this.objectMapper.readValue(payload.toString(), javaType);
        }
        catch (IOException ex) {
            throw new MessageConversionException(message, "Could not read JSON: " + ex.getMessage(), ex);
        }
    }

    @Override
    @Nullable
    protected Object convertToInternal(Object payload, @Nullable MessageHeaders headers, @Nullable Object conversionHint) {
        block19: {
            try {
                Class<?> view = this.getSerializationView(conversionHint);
                if (byte[].class == this.getSerializedPayloadClass()) {
                    ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
                    JsonEncoding encoding = this.getJsonEncoding(this.getMimeType(headers));
                    try (JsonGenerator generator = this.objectMapper.getFactory().createGenerator((OutputStream)out, encoding);){
                        if (view != null) {
                            this.objectMapper.writerWithView(view).writeValue(generator, payload);
                        } else {
                            this.objectMapper.writeValue(generator, payload);
                        }
                        payload = out.toByteArray();
                        break block19;
                    }
                }
                StringWriter writer = new StringWriter(1024);
                if (view != null) {
                    this.objectMapper.writerWithView(view).writeValue((Writer)writer, payload);
                } else {
                    this.objectMapper.writeValue((Writer)writer, payload);
                }
                payload = ((Object)writer).toString();
            }
            catch (IOException ex) {
                throw new MessageConversionException("Could not write JSON: " + ex.getMessage(), (Throwable)ex);
            }
        }
        return payload;
    }

    @Nullable
    protected Class<?> getSerializationView(@Nullable Object conversionHint) {
        if (conversionHint instanceof MethodParameter) {
            JsonView annotation;
            MethodParameter param = (MethodParameter)conversionHint;
            JsonView jsonView = annotation = param.getParameterIndex() >= 0 ? (JsonView)param.getParameterAnnotation(JsonView.class) : (JsonView)param.getMethodAnnotation(JsonView.class);
            if (annotation != null) {
                return this.extractViewClass(annotation, conversionHint);
            }
        } else {
            if (conversionHint instanceof JsonView) {
                return this.extractViewClass((JsonView)conversionHint, conversionHint);
            }
            if (conversionHint instanceof Class) {
                return (Class)conversionHint;
            }
        }
        return null;
    }

    private Class<?> extractViewClass(JsonView annotation, Object conversionHint) {
        Class[] classes = annotation.value();
        if (classes.length != 1) {
            throw new IllegalArgumentException("@JsonView only supported for handler methods with exactly 1 class argument: " + conversionHint);
        }
        return classes[0];
    }

    protected JsonEncoding getJsonEncoding(@Nullable MimeType contentType) {
        if (contentType != null && contentType.getCharset() != null) {
            Charset charset = contentType.getCharset();
            for (JsonEncoding encoding : JsonEncoding.values()) {
                if (!charset.name().equals(encoding.getJavaName())) continue;
                return encoding;
            }
        }
        return JsonEncoding.UTF8;
    }
}

