/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.WriterInterceptor;
import org.glassfish.jersey.internal.PropertiesDelegate;
import org.glassfish.jersey.message.ReaderModel;
import org.glassfish.jersey.message.WriterModel;

public interface MessageBodyWorkers {
    public Map<MediaType, List<MessageBodyReader>> getReaders(MediaType var1);

    public Map<MediaType, List<MessageBodyWriter>> getWriters(MediaType var1);

    public String readersToString(Map<MediaType, List<MessageBodyReader>> var1);

    public String writersToString(Map<MediaType, List<MessageBodyWriter>> var1);

    public <T> MessageBodyReader<T> getMessageBodyReader(Class<T> var1, Type var2, Annotation[] var3, MediaType var4);

    public <T> MessageBodyReader<T> getMessageBodyReader(Class<T> var1, Type var2, Annotation[] var3, MediaType var4, PropertiesDelegate var5);

    public <T> MessageBodyWriter<T> getMessageBodyWriter(Class<T> var1, Type var2, Annotation[] var3, MediaType var4);

    public <T> MessageBodyWriter<T> getMessageBodyWriter(Class<T> var1, Type var2, Annotation[] var3, MediaType var4, PropertiesDelegate var5);

    public List<MediaType> getMessageBodyReaderMediaTypes(Class<?> var1, Type var2, Annotation[] var3);

    public List<MediaType> getMessageBodyReaderMediaTypesByType(Class<?> var1);

    public List<MessageBodyReader> getMessageBodyReadersForType(Class<?> var1);

    public List<ReaderModel> getReaderModelsForType(Class<?> var1);

    public List<MediaType> getMessageBodyWriterMediaTypes(Class<?> var1, Type var2, Annotation[] var3);

    public List<MediaType> getMessageBodyWriterMediaTypesByType(Class<?> var1);

    public List<MessageBodyWriter> getMessageBodyWritersForType(Class<?> var1);

    public List<WriterModel> getWritersModelsForType(Class<?> var1);

    public MediaType getMessageBodyWriterMediaType(Class<?> var1, Type var2, Annotation[] var3, List<MediaType> var4);

    public Object readFrom(Class<?> var1, Type var2, Annotation[] var3, MediaType var4, MultivaluedMap<String, String> var5, PropertiesDelegate var6, InputStream var7, Iterable<ReaderInterceptor> var8, boolean var9) throws WebApplicationException, IOException;

    public OutputStream writeTo(Object var1, Class<?> var2, Type var3, Annotation[] var4, MediaType var5, MultivaluedMap<String, Object> var6, PropertiesDelegate var7, OutputStream var8, Iterable<WriterInterceptor> var9) throws IOException, WebApplicationException;
}

