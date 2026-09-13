/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.MessageBodyWriter;
import org.glassfish.jersey.message.AbstractEntityProviderModel;
import org.glassfish.jersey.message.internal.MessageBodyFactory;

public final class WriterModel
extends AbstractEntityProviderModel<MessageBodyWriter> {
    public WriterModel(MessageBodyWriter provider, List<MediaType> types, Boolean custom) {
        super(provider, types, custom, MessageBodyWriter.class);
    }

    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return MessageBodyFactory.isWriteable((MessageBodyWriter)super.provider(), type, genericType, annotations, mediaType);
    }
}

