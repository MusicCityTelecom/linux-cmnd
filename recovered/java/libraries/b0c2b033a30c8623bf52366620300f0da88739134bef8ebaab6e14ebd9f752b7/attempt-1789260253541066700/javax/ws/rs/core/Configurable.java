/*
 * Decompiled with CFR 0.152.
 */
package javax.ws.rs.core;

import java.util.Map;
import javax.ws.rs.core.Configuration;

public interface Configurable<C extends Configurable> {
    public Configuration getConfiguration();

    public C property(String var1, Object var2);

    public C register(Class<?> var1);

    public C register(Class<?> var1, int var2);

    public C register(Class<?> var1, Class<?> ... var2);

    public C register(Class<?> var1, Map<Class<?>, Integer> var2);

    public C register(Object var1);

    public C register(Object var1, int var2);

    public C register(Object var1, Class<?> ... var2);

    public C register(Object var1, Map<Class<?>, Integer> var2);
}

