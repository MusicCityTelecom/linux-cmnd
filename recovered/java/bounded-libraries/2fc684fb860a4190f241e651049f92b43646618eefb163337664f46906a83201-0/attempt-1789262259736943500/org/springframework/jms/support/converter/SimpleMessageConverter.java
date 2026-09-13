/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.BytesMessage
 *  javax.jms.JMSException
 *  javax.jms.MapMessage
 *  javax.jms.Message
 *  javax.jms.ObjectMessage
 *  javax.jms.Session
 *  javax.jms.TextMessage
 *  org.springframework.util.ObjectUtils
 */
package org.springframework.jms.support.converter;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.util.ObjectUtils;

public class SimpleMessageConverter
implements MessageConverter {
    @Override
    public Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {
        if (object instanceof Message) {
            return (Message)object;
        }
        if (object instanceof String) {
            return this.createMessageForString((String)object, session);
        }
        if (object instanceof byte[]) {
            return this.createMessageForByteArray((byte[])object, session);
        }
        if (object instanceof Map) {
            return this.createMessageForMap((Map)object, session);
        }
        if (object instanceof Serializable) {
            return this.createMessageForSerializable((Serializable)object, session);
        }
        throw new MessageConversionException("Cannot convert object of type [" + ObjectUtils.nullSafeClassName((Object)object) + "] to JMS message. Supported message payloads are: String, byte array, Map<String,?>, Serializable object.");
    }

    @Override
    public Object fromMessage(Message message) throws JMSException, MessageConversionException {
        if (message instanceof TextMessage) {
            return this.extractStringFromMessage((TextMessage)message);
        }
        if (message instanceof BytesMessage) {
            return this.extractByteArrayFromMessage((BytesMessage)message);
        }
        if (message instanceof MapMessage) {
            return this.extractMapFromMessage((MapMessage)message);
        }
        if (message instanceof ObjectMessage) {
            return this.extractSerializableFromMessage((ObjectMessage)message);
        }
        return message;
    }

    protected TextMessage createMessageForString(String text, Session session) throws JMSException {
        return session.createTextMessage(text);
    }

    protected BytesMessage createMessageForByteArray(byte[] bytes, Session session) throws JMSException {
        BytesMessage message = session.createBytesMessage();
        message.writeBytes(bytes);
        return message;
    }

    protected MapMessage createMessageForMap(Map<?, ?> map, Session session) throws JMSException {
        MapMessage message = session.createMapMessage();
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            Object key = entry.getKey();
            if (!(key instanceof String)) {
                throw new MessageConversionException("Cannot convert non-String key of type [" + ObjectUtils.nullSafeClassName(key) + "] to JMS MapMessage entry");
            }
            message.setObject((String)key, entry.getValue());
        }
        return message;
    }

    protected ObjectMessage createMessageForSerializable(Serializable object, Session session) throws JMSException {
        return session.createObjectMessage(object);
    }

    protected String extractStringFromMessage(TextMessage message) throws JMSException {
        return message.getText();
    }

    protected byte[] extractByteArrayFromMessage(BytesMessage message) throws JMSException {
        byte[] bytes = new byte[(int)message.getBodyLength()];
        message.readBytes(bytes);
        return bytes;
    }

    protected Map<String, Object> extractMapFromMessage(MapMessage message) throws JMSException {
        HashMap<String, Object> map = new HashMap<String, Object>();
        Enumeration en = message.getMapNames();
        while (en.hasMoreElements()) {
            String key = (String)en.nextElement();
            map.put(key, message.getObject(key));
        }
        return map;
    }

    protected Serializable extractSerializableFromMessage(ObjectMessage message) throws JMSException {
        return message.getObject();
    }
}

