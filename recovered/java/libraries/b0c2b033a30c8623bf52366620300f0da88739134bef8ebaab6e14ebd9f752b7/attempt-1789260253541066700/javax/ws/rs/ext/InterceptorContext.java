/*
 * Decompiled with CFR 0.152.
 */
package javax.ws.rs.ext;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collection;
import javax.ws.rs.core.MediaType;

public interface InterceptorContext {
    public Object getProperty(String var1);

    public Collection<String> getPropertyNames();

    public void setProperty(String var1, Object var2);

    public void removeProperty(String var1);

    public Annotation[] getAnnotations();

    public void setAnnotations(Annotation[] var1);

    public Class<?> getType();

    public void setType(Class<?> var1);

    public Type getGenericType();

    public void setGenericType(Type var1);

    public MediaType getMediaType();

    public void setMediaType(MediaType var1);
}

