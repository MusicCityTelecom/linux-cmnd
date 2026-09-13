/*
 * Decompiled with CFR 0.152.
 */
package javax.ws.rs.container;

import java.lang.reflect.Method;

public interface ResourceInfo {
    public Method getResourceMethod();

    public Class<?> getResourceClass();
}

