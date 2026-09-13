/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.BytesMessage
 *  javax.jms.JMSException
 *  javax.jms.Message
 *  javax.jms.Session
 *  javax.jms.TextMessage
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.lang.Nullable
 *  org.springframework.oxm.Marshaller
 *  org.springframework.oxm.Unmarshaller
 *  org.springframework.oxm.XmlMappingException
 *  org.springframework.util.Assert
 */
package org.springframework.jms.support.converter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.lang.Nullable;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.XmlMappingException;
import org.springframework.util.Assert;

public class MarshallingMessageConverter
implements MessageConverter,
InitializingBean {
    @Nullable
    private Marshaller marshaller;
    @Nullable
    private Unmarshaller unmarshaller;
    private MessageType targetType = MessageType.BYTES;

    public MarshallingMessageConverter() {
    }

    public MarshallingMessageConverter(Marshaller marshaller) {
        Assert.notNull((Object)marshaller, (String)"Marshaller must not be null");
        if (!(marshaller instanceof Unmarshaller)) {
            throw new IllegalArgumentException("Marshaller [" + marshaller + "] does not implement the Unmarshaller interface. Please set an Unmarshaller explicitly by using the MarshallingMessageConverter(Marshaller, Unmarshaller) constructor.");
        }
        this.marshaller = marshaller;
        this.unmarshaller = (Unmarshaller)marshaller;
    }

    public MarshallingMessageConverter(Marshaller marshaller, Unmarshaller unmarshaller) {
        Assert.notNull((Object)marshaller, (String)"Marshaller must not be null");
        Assert.notNull((Object)unmarshaller, (String)"Unmarshaller must not be null");
        this.marshaller = marshaller;
        this.unmarshaller = unmarshaller;
    }

    public void setMarshaller(Marshaller marshaller) {
        Assert.notNull((Object)marshaller, (String)"Marshaller must not be null");
        this.marshaller = marshaller;
    }

    public void setUnmarshaller(Unmarshaller unmarshaller) {
        Assert.notNull((Object)unmarshaller, (String)"Unmarshaller must not be null");
        this.unmarshaller = unmarshaller;
    }

    public void setTargetType(MessageType targetType) {
        Assert.notNull((Object)((Object)targetType), (String)"MessageType must not be null");
        this.targetType = targetType;
    }

    public void afterPropertiesSet() {
        Assert.notNull((Object)this.marshaller, (String)"Property 'marshaller' is required");
        Assert.notNull((Object)this.unmarshaller, (String)"Property 'unmarshaller' is required");
    }

    @Override
    public Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {
        Assert.state((this.marshaller != null ? 1 : 0) != 0, (String)"No Marshaller set");
        try {
            switch (this.targetType) {
                case TEXT: {
                    return this.marshalToTextMessage(object, session, this.marshaller);
                }
                case BYTES: {
                    return this.marshalToBytesMessage(object, session, this.marshaller);
                }
            }
            return this.marshalToMessage(object, session, this.marshaller, this.targetType);
        }
        catch (IOException | XmlMappingException ex) {
            throw new MessageConversionException("Could not marshal [" + object + "]", ex);
        }
    }

    @Override
    public Object fromMessage(Message message) throws JMSException, MessageConversionException {
        Assert.state((this.unmarshaller != null ? 1 : 0) != 0, (String)"No Unmarshaller set");
        try {
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage)message;
                return this.unmarshalFromTextMessage(textMessage, this.unmarshaller);
            }
            if (message instanceof BytesMessage) {
                BytesMessage bytesMessage = (BytesMessage)message;
                return this.unmarshalFromBytesMessage(bytesMessage, this.unmarshaller);
            }
            return this.unmarshalFromMessage(message, this.unmarshaller);
        }
        catch (IOException ex) {
            throw new MessageConversionException("Could not access message content: " + message, ex);
        }
        catch (XmlMappingException ex) {
            throw new MessageConversionException("Could not unmarshal message: " + message, ex);
        }
    }

    protected TextMessage marshalToTextMessage(Object object, Session session, Marshaller marshaller) throws JMSException, IOException, XmlMappingException {
        StringWriter writer = new StringWriter(1024);
        StreamResult result = new StreamResult(writer);
        marshaller.marshal(object, (Result)result);
        return session.createTextMessage(writer.toString());
    }

    protected BytesMessage marshalToBytesMessage(Object object, Session session, Marshaller marshaller) throws JMSException, IOException, XmlMappingException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
        StreamResult streamResult = new StreamResult(bos);
        marshaller.marshal(object, (Result)streamResult);
        BytesMessage message = session.createBytesMessage();
        message.writeBytes(bos.toByteArray());
        return message;
    }

    protected Message marshalToMessage(Object object, Session session, Marshaller marshaller, MessageType targetType) throws JMSException, IOException, XmlMappingException {
        throw new IllegalArgumentException("Unsupported message type [" + (Object)((Object)targetType) + "]. MarshallingMessageConverter by default only supports TextMessages and BytesMessages.");
    }

    protected Object unmarshalFromTextMessage(TextMessage message, Unmarshaller unmarshaller) throws JMSException, IOException, XmlMappingException {
        StreamSource source = new StreamSource(new StringReader(message.getText()));
        return unmarshaller.unmarshal((Source)source);
    }

    protected Object unmarshalFromBytesMessage(BytesMessage message, Unmarshaller unmarshaller) throws JMSException, IOException, XmlMappingException {
        byte[] bytes = new byte[(int)message.getBodyLength()];
        message.readBytes(bytes);
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        StreamSource source = new StreamSource(bis);
        return unmarshaller.unmarshal((Source)source);
    }

    protected Object unmarshalFromMessage(Message message, Unmarshaller unmarshaller) throws JMSException, IOException, XmlMappingException {
        throw new IllegalArgumentException("Unsupported message type [" + message.getClass() + "]. MarshallingMessageConverter by default only supports TextMessages and BytesMessages.");
    }
}

