/*
 * Decompiled with CFR 0.152.
 */
package javax.ws.rs.core;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import javax.ws.rs.RuntimeType;
import javax.ws.rs.core.Feature;

public interface Configuration {
    public RuntimeType getRuntimeType();

    public Map<String, Object> getProperties();

    public Object getProperty(String var1);

    public Collection<String> getPropertyNames();

    public boolean isEnabled(Feature var1);

    public boolean isEnabled(Class<? extends Feature> var1);

    public boolean isRegistered(Object var1);

    public boolean isRegistered(Class<?> var1);

    public Map<Class<?>, Integer> getContracts(Class<?> var1);

    public Set<Class<?>> getClasses();

    public Set<Object> getInstances();
}

