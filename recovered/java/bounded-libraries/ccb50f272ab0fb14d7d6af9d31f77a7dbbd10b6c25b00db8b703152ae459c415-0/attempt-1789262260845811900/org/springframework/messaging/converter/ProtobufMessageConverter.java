/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.protobuf.ExtensionRegistry
 *  com.google.protobuf.ExtensionRegistryLite
 *  com.google.protobuf.Message
 *  com.google.protobuf.Message$Builder
 *  com.google.protobuf.MessageOrBuilder
 *  com.google.protobuf.util.JsonFormat
 *  com.google.protobuf.util.JsonFormat$Parser
 *  com.google.protobuf.util.JsonFormat$Printer
 *  org.springframework.lang.Nullable
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.ConcurrentReferenceHashMap
 *  org.springframework.util.MimeType
 *  org.springframework.util.MimeTypeUtils
 */
package org.springframework.messaging.converter;

import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.Message;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.util.JsonFormat;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.converter.AbstractMessageConverter;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.util.ClassUtils;
import org.springframework.util.ConcurrentReferenceHashMap;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;

public class ProtobufMessageConverter
extends AbstractMessageConverter {
    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    public static final MimeType PROTOBUF = new MimeType("application", "x-protobuf", DEFAULT_CHARSET);
    private static final Map<Class<?>, Method> methodCache = new ConcurrentReferenceHashMap();
    final ExtensionRegistry extensionRegistry;
    @Nullable
    private final ProtobufFormatSupport protobufFormatSupport;

    public ProtobufMessageConverter() {
        this((ProtobufFormatSupport)null, (ExtensionRegistry)null);
    }

    public ProtobufMessageConverter(ExtensionRegistry extensionRegistry) {
        this(null, extensionRegistry);
    }

    ProtobufMessageConverter(@Nullable ProtobufFormatSupport formatSupport, @Nullable ExtensionRegistry extensionRegistry) {
        super(PROTOBUF, MimeTypeUtils.TEXT_PLAIN);
        this.protobufFormatSupport = formatSupport != null ? formatSupport : (ClassUtils.isPresent((String)"com.google.protobuf.util.JsonFormat", (ClassLoader)this.getClass().getClassLoader()) ? new ProtobufJavaUtilSupport(null, null) : null);
        if (this.protobufFormatSupport != null) {
            this.addSupportedMimeTypes(this.protobufFormatSupport.supportedMediaTypes());
        }
        this.extensionRegistry = extensionRegistry == null ? ExtensionRegistry.newInstance() : extensionRegistry;
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return com.google.protobuf.Message.class.isAssignableFrom(clazz);
    }

    @Override
    protected boolean canConvertTo(Object payload, @Nullable MessageHeaders headers) {
        MimeType contentType = this.getMimeType(headers);
        return super.canConvertTo(payload, headers) || this.protobufFormatSupport != null && this.protobufFormatSupport.supportsWriteOnly(contentType);
    }

    @Override
    protected Object convertFromInternal(Message<?> message, Class<?> targetClass, @Nullable Object conversionHint) {
        Charset charset;
        MimeType contentType = this.getMimeType(message.getHeaders());
        Object payload = message.getPayload();
        if (contentType == null) {
            contentType = PROTOBUF;
        }
        if ((charset = contentType.getCharset()) == null) {
            charset = DEFAULT_CHARSET;
        }
        Message.Builder builder = this.getMessageBuilder(targetClass);
        try {
            if (PROTOBUF.isCompatibleWith(contentType)) {
                builder.mergeFrom((byte[])payload, (ExtensionRegistryLite)this.extensionRegistry);
            } else if (this.protobufFormatSupport != null) {
                this.protobufFormatSupport.merge(message, charset, contentType, this.extensionRegistry, builder);
            }
        }
        catch (IOException ex) {
            throw new MessageConversionException(message, "Could not read proto message" + ex.getMessage(), ex);
        }
        return builder.build();
    }

    @Override
    protected Object convertToInternal(Object payload, @Nullable MessageHeaders headers, @Nullable Object conversionHint) {
        Charset charset;
        com.google.protobuf.Message message = (com.google.protobuf.Message)payload;
        MimeType contentType = this.getMimeType(headers);
        if (contentType == null) {
            contentType = PROTOBUF;
        }
        if ((charset = contentType.getCharset()) == null) {
            charset = DEFAULT_CHARSET;
        }
        try {
            if (PROTOBUF.isCompatibleWith(contentType)) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                message.writeTo((OutputStream)byteArrayOutputStream);
                payload = byteArrayOutputStream.toByteArray();
            } else if (this.protobufFormatSupport != null) {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                this.protobufFormatSupport.print(message, outputStream, contentType, charset);
                payload = outputStream.toString(charset.name());
            }
        }
        catch (IOException ex) {
            throw new MessageConversionException("Failed to print Protobuf message: " + ex.getMessage(), (Throwable)ex);
        }
        return payload;
    }

    private Message.Builder getMessageBuilder(Class<?> clazz) {
        try {
            Method method = methodCache.get(clazz);
            if (method == null) {
                method = clazz.getMethod("newBuilder", new Class[0]);
                methodCache.put(clazz, method);
            }
            return (Message.Builder)method.invoke(clazz, new Object[0]);
        }
        catch (Exception ex) {
            throw new MessageConversionException("Invalid Protobuf Message type: no invocable newBuilder() method on " + clazz, (Throwable)ex);
        }
    }

    static class ProtobufJavaUtilSupport
    implements ProtobufFormatSupport {
        private final JsonFormat.Parser parser;
        private final JsonFormat.Printer printer;

        public ProtobufJavaUtilSupport(@Nullable JsonFormat.Parser parser, @Nullable JsonFormat.Printer printer) {
            this.parser = parser != null ? parser : JsonFormat.parser();
            this.printer = printer != null ? printer : JsonFormat.printer();
        }

        @Override
        public MimeType[] supportedMediaTypes() {
            return new MimeType[]{MimeTypeUtils.APPLICATION_JSON};
        }

        @Override
        public boolean supportsWriteOnly(@Nullable MimeType mimeType) {
            return false;
        }

        @Override
        public void merge(Message<?> message, Charset charset, MimeType contentType, ExtensionRegistry extensionRegistry, Message.Builder builder) throws IOException, MessageConversionException {
            if (!contentType.isCompatibleWith(MimeTypeUtils.APPLICATION_JSON)) {
                throw new MessageConversionException("protobuf-java-util does not support parsing " + contentType);
            }
            this.parser.merge(message.getPayload().toString(), builder);
        }

        @Override
        public void print(com.google.protobuf.Message message, OutputStream output, MimeType contentType, Charset charset) throws IOException, MessageConversionException {
            if (!contentType.isCompatibleWith(MimeTypeUtils.APPLICATION_JSON)) {
                throw new MessageConversionException("protobuf-java-util does not support printing " + contentType);
            }
            OutputStreamWriter writer = new OutputStreamWriter(output, charset);
            this.printer.appendTo((MessageOrBuilder)message, (Appendable)writer);
            writer.flush();
        }
    }

    static interface ProtobufFormatSupport {
        public MimeType[] supportedMediaTypes();

        public boolean supportsWriteOnly(@Nullable MimeType var1);

        public void merge(Message<?> var1, Charset var2, MimeType var3, ExtensionRegistry var4, Message.Builder var5) throws IOException, MessageConversionException;

        public void print(com.google.protobuf.Message var1, OutputStream var2, MimeType var3, Charset var4) throws IOException, MessageConversionException;
    }
}

