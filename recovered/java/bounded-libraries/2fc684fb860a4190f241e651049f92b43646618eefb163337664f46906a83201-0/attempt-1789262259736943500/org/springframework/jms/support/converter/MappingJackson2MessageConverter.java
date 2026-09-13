/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonView
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.JavaType
 *  com.fasterxml.jackson.databind.MapperFeature
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.ObjectWriter
 *  javax.jms.BytesMessage
 *  javax.jms.JMSException
 *  javax.jms.Message
 *  javax.jms.Session
 *  javax.jms.TextMessage
 *  org.springframework.beans.factory.BeanClassLoaderAware
 *  org.springframework.core.MethodParameter
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 */
package org.springframework.jms.support.converter;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.core.MethodParameter;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.jms.support.converter.SmartMessageConverter;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

public class MappingJackson2MessageConverter
implements SmartMessageConverter,
BeanClassLoaderAware {
    public static final String DEFAULT_ENCODING = "UTF-8";
    private ObjectMapper objectMapper;
    private MessageType targetType = MessageType.BYTES;
    @Nullable
    private String encoding;
    @Nullable
    private String encodingPropertyName;
    @Nullable
    private String typeIdPropertyName;
    private Map<String, Class<?>> idClassMappings = new HashMap();
    private Map<Class<?>, String> classIdMappings = new HashMap();
    @Nullable
    private ClassLoader beanClassLoader;

    public MappingJackson2MessageConverter() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        Assert.notNull((Object)objectMapper, (String)"ObjectMapper must not be null");
        this.objectMapper = objectMapper;
    }

    public void setTargetType(MessageType targetType) {
        Assert.notNull((Object)((Object)targetType), (String)"MessageType must not be null");
        this.targetType = targetType;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public void setEncodingPropertyName(String encodingPropertyName) {
        this.encodingPropertyName = encodingPropertyName;
    }

    public void setTypeIdPropertyName(String typeIdPropertyName) {
        this.typeIdPropertyName = typeIdPropertyName;
    }

    public void setTypeIdMappings(Map<String, Class<?>> typeIdMappings) {
        this.idClassMappings = new HashMap();
        typeIdMappings.forEach((id, clazz) -> {
            this.idClassMappings.put((String)id, (Class<?>)clazz);
            this.classIdMappings.put((Class<?>)clazz, (String)id);
        });
    }

    public void setBeanClassLoader(ClassLoader classLoader) {
        this.beanClassLoader = classLoader;
    }

    @Override
    public Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {
        TextMessage message;
        try {
            switch (this.targetType) {
                case TEXT: {
                    message = this.mapToTextMessage(object, session, this.objectMapper.writer());
                    break;
                }
                case BYTES: {
                    message = this.mapToBytesMessage(object, session, this.objectMapper.writer());
                    break;
                }
                default: {
                    message = this.mapToMessage(object, session, this.objectMapper.writer(), this.targetType);
                    break;
                }
            }
        }
        catch (IOException ex) {
            throw new MessageConversionException("Could not map JSON object [" + object + "]", ex);
        }
        this.setTypeIdOnMessage(object, (Message)message);
        return message;
    }

    @Override
    public Message toMessage(Object object, Session session, @Nullable Object conversionHint) throws JMSException, MessageConversionException {
        return this.toMessage(object, session, this.getSerializationView(conversionHint));
    }

    public Message toMessage(Object object, Session session, @Nullable Class<?> jsonView) throws JMSException, MessageConversionException {
        if (jsonView != null) {
            return this.toMessage(object, session, this.objectMapper.writerWithView(jsonView));
        }
        return this.toMessage(object, session, this.objectMapper.writer());
    }

    @Override
    public Object fromMessage(Message message) throws JMSException, MessageConversionException {
        try {
            JavaType targetJavaType = this.getJavaTypeForMessage(message);
            return this.convertToObject(message, targetJavaType);
        }
        catch (IOException ex) {
            throw new MessageConversionException("Failed to convert JSON message content", ex);
        }
    }

    protected Message toMessage(Object object, Session session, ObjectWriter objectWriter) throws JMSException, MessageConversionException {
        TextMessage message;
        try {
            switch (this.targetType) {
                case TEXT: {
                    message = this.mapToTextMessage(object, session, objectWriter);
                    break;
                }
                case BYTES: {
                    message = this.mapToBytesMessage(object, session, objectWriter);
                    break;
                }
                default: {
                    message = this.mapToMessage(object, session, objectWriter, this.targetType);
                    break;
                }
            }
        }
        catch (IOException ex) {
            throw new MessageConversionException("Could not map JSON object [" + object + "]", ex);
        }
        this.setTypeIdOnMessage(object, (Message)message);
        return message;
    }

    protected TextMessage mapToTextMessage(Object object, Session session, ObjectWriter objectWriter) throws JMSException, IOException {
        StringWriter writer = new StringWriter(1024);
        objectWriter.writeValue((Writer)writer, object);
        return session.createTextMessage(writer.toString());
    }

    protected BytesMessage mapToBytesMessage(Object object, Session session, ObjectWriter objectWriter) throws JMSException, IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
        if (this.encoding != null) {
            OutputStreamWriter writer = new OutputStreamWriter((OutputStream)bos, this.encoding);
            objectWriter.writeValue((Writer)writer, object);
        } else {
            objectWriter.writeValue((OutputStream)bos, object);
        }
        BytesMessage message = session.createBytesMessage();
        message.writeBytes(bos.toByteArray());
        if (this.encodingPropertyName != null) {
            message.setStringProperty(this.encodingPropertyName, this.encoding != null ? this.encoding : DEFAULT_ENCODING);
        }
        return message;
    }

    protected Message mapToMessage(Object object, Session session, ObjectWriter objectWriter, MessageType targetType) throws JMSException, IOException {
        throw new IllegalArgumentException("Unsupported message type [" + (Object)((Object)targetType) + "]. MappingJackson2MessageConverter by default only supports TextMessages and BytesMessages.");
    }

    protected void setTypeIdOnMessage(Object object, Message message) throws JMSException {
        if (this.typeIdPropertyName != null) {
            String typeId = this.classIdMappings.get(object.getClass());
            if (typeId == null) {
                typeId = object.getClass().getName();
            }
            message.setStringProperty(this.typeIdPropertyName, typeId);
        }
    }

    private Object convertToObject(Message message, JavaType targetJavaType) throws JMSException, IOException {
        if (message instanceof TextMessage) {
            return this.convertFromTextMessage((TextMessage)message, targetJavaType);
        }
        if (message instanceof BytesMessage) {
            return this.convertFromBytesMessage((BytesMessage)message, targetJavaType);
        }
        return this.convertFromMessage(message, targetJavaType);
    }

    protected Object convertFromTextMessage(TextMessage message, JavaType targetJavaType) throws JMSException, IOException {
        String body = message.getText();
        return this.objectMapper.readValue(body, targetJavaType);
    }

    protected Object convertFromBytesMessage(BytesMessage message, JavaType targetJavaType) throws JMSException, IOException {
        String encoding = this.encoding;
        if (this.encodingPropertyName != null && message.propertyExists(this.encodingPropertyName)) {
            encoding = message.getStringProperty(this.encodingPropertyName);
        }
        byte[] bytes = new byte[(int)message.getBodyLength()];
        message.readBytes(bytes);
        if (encoding != null) {
            try {
                String body = new String(bytes, encoding);
                return this.objectMapper.readValue(body, targetJavaType);
            }
            catch (UnsupportedEncodingException ex) {
                throw new MessageConversionException("Cannot convert bytes to String", ex);
            }
        }
        return this.objectMapper.readValue(bytes, targetJavaType);
    }

    protected Object convertFromMessage(Message message, JavaType targetJavaType) throws JMSException, IOException {
        throw new IllegalArgumentException("Unsupported message type [" + message.getClass() + "]. MappingJacksonMessageConverter by default only supports TextMessages and BytesMessages.");
    }

    protected JavaType getJavaTypeForMessage(Message message) throws JMSException {
        String typeId = message.getStringProperty(this.typeIdPropertyName);
        if (typeId == null) {
            throw new MessageConversionException("Could not find type id property [" + this.typeIdPropertyName + "] on message [" + message.getJMSMessageID() + "] from destination [" + message.getJMSDestination() + "]");
        }
        Class<?> mappedClass = this.idClassMappings.get(typeId);
        if (mappedClass != null) {
            return this.objectMapper.constructType(mappedClass);
        }
        try {
            Class typeClass = ClassUtils.forName((String)typeId, (ClassLoader)this.beanClassLoader);
            return this.objectMapper.constructType((Type)typeClass);
        }
        catch (Throwable ex) {
            throw new MessageConversionException("Failed to resolve type id [" + typeId + "]", ex);
        }
    }

    @Nullable
    protected Class<?> getSerializationView(@Nullable Object conversionHint) {
        if (conversionHint instanceof MethodParameter) {
            MethodParameter methodParam = (MethodParameter)conversionHint;
            JsonView annotation = (JsonView)methodParam.getParameterAnnotation(JsonView.class);
            if (annotation == null && (annotation = (JsonView)methodParam.getMethodAnnotation(JsonView.class)) == null) {
                return null;
            }
            return this.extractViewClass(annotation, conversionHint);
        }
        if (conversionHint instanceof JsonView) {
            return this.extractViewClass((JsonView)conversionHint, conversionHint);
        }
        if (conversionHint instanceof Class) {
            return (Class)conversionHint;
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
}

