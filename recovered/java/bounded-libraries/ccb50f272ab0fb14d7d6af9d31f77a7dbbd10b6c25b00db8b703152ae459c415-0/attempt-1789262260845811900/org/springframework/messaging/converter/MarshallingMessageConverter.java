/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.TypeMismatchException
 *  org.springframework.lang.Nullable
 *  org.springframework.oxm.Marshaller
 *  org.springframework.oxm.Unmarshaller
 *  org.springframework.util.Assert
 *  org.springframework.util.MimeType
 */
package org.springframework.messaging.converter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.springframework.beans.TypeMismatchException;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.converter.AbstractMessageConverter;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.util.Assert;
import org.springframework.util.MimeType;

public class MarshallingMessageConverter
extends AbstractMessageConverter {
    @Nullable
    private Marshaller marshaller;
    @Nullable
    private Unmarshaller unmarshaller;

    public MarshallingMessageConverter() {
        this(new MimeType("application", "xml"), new MimeType("text", "xml"), new MimeType("application", "*+xml"));
    }

    public MarshallingMessageConverter(MimeType ... supportedMimeTypes) {
        super(supportedMimeTypes);
    }

    public MarshallingMessageConverter(Marshaller marshaller) {
        this();
        Assert.notNull((Object)marshaller, (String)"Marshaller must not be null");
        this.marshaller = marshaller;
        if (marshaller instanceof Unmarshaller) {
            this.unmarshaller = (Unmarshaller)marshaller;
        }
    }

    public void setMarshaller(@Nullable Marshaller marshaller) {
        this.marshaller = marshaller;
    }

    @Nullable
    public Marshaller getMarshaller() {
        return this.marshaller;
    }

    public void setUnmarshaller(@Nullable Unmarshaller unmarshaller) {
        this.unmarshaller = unmarshaller;
    }

    @Nullable
    public Unmarshaller getUnmarshaller() {
        return this.unmarshaller;
    }

    @Override
    protected boolean canConvertFrom(Message<?> message, Class<?> targetClass) {
        return this.supportsMimeType(message.getHeaders()) && this.unmarshaller != null && this.unmarshaller.supports(targetClass);
    }

    @Override
    protected boolean canConvertTo(Object payload, @Nullable MessageHeaders headers) {
        return this.supportsMimeType(headers) && this.marshaller != null && this.marshaller.supports(payload.getClass());
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Nullable
    protected Object convertFromInternal(Message<?> message, Class<?> targetClass, @Nullable Object conversionHint) {
        Assert.notNull((Object)this.unmarshaller, (String)"Property 'unmarshaller' is required");
        try {
            Source source = this.getSource(message.getPayload());
            Object result = this.unmarshaller.unmarshal(source);
            if (!targetClass.isInstance(result)) {
                throw new TypeMismatchException(result, targetClass);
            }
            return result;
        }
        catch (Exception ex) {
            throw new MessageConversionException(message, "Could not unmarshal XML: " + ex.getMessage(), ex);
        }
    }

    private Source getSource(Object payload) {
        if (payload instanceof byte[]) {
            return new StreamSource(new ByteArrayInputStream((byte[])payload));
        }
        return new StreamSource(new StringReader(payload.toString()));
    }

    @Override
    @Nullable
    protected Object convertToInternal(Object payload, @Nullable MessageHeaders headers, @Nullable Object conversionHint) {
        Assert.notNull((Object)this.marshaller, (String)"Property 'marshaller' is required");
        try {
            if (byte[].class == this.getSerializedPayloadClass()) {
                ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
                StreamResult result = new StreamResult(out);
                this.marshaller.marshal(payload, (Result)result);
                payload = out.toByteArray();
            } else {
                StringWriter writer = new StringWriter(1024);
                StreamResult result = new StreamResult(writer);
                this.marshaller.marshal(payload, (Result)result);
                payload = ((Object)writer).toString();
            }
        }
        catch (Throwable ex) {
            throw new MessageConversionException("Could not marshal XML: " + ex.getMessage(), ex);
        }
        return payload;
    }
}

