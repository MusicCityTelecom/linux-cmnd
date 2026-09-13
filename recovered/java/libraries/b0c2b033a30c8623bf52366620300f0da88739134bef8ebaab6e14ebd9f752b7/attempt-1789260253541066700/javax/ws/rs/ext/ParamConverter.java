/*
 * Decompiled with CFR 0.152.
 */
package javax.ws.rs.ext;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public interface ParamConverter<T> {
    public T fromString(String var1);

    public String toString(T var1);

    @Target(value={ElementType.TYPE})
    @Retention(value=RetentionPolicy.RUNTIME)
    @Documented
    public static @interface Lazy {
    }
}

