/*
 * Decompiled with CFR 0.152.
 */
package javax.ws.rs.ext;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

public interface MessageBodyReader<T> {
    public boolean isReadable(Class<?> var1, Type var2, Annotation[] var3, MediaType var4);

    public T readFrom(Class<T> var1, Type var2, Annotation[] var3, MediaType var4, MultivaluedMap<String, String> var5, InputStream var6) throws IOException, WebApplicationException;
}

