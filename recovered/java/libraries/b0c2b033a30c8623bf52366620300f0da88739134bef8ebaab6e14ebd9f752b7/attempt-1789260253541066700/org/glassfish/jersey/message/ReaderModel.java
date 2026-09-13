/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.MessageBodyReader;
import org.glassfish.jersey.message.AbstractEntityProviderModel;
import org.glassfish.jersey.message.internal.MessageBodyFactory;

public final class ReaderModel
extends AbstractEntityProviderModel<MessageBodyReader> {
    public ReaderModel(MessageBodyReader provider, List<MediaType> types, Boolean custom) {
        super(provider, types, custom, MessageBodyReader.class);
    }

    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return MessageBodyFactory.isReadable((MessageBodyReader)super.provider(), type, genericType, annotations, mediaType);
    }
}

