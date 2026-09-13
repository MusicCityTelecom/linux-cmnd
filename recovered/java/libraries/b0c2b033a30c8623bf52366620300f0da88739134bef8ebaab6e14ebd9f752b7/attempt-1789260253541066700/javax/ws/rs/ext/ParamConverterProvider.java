/*
 * Decompiled with CFR 0.152.
 */
package javax.ws.rs.ext;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.ws.rs.ext.ParamConverter;

public interface ParamConverterProvider {
    public <T> ParamConverter<T> getConverter(Class<T> var1, Type var2, Annotation[] var3);
}

