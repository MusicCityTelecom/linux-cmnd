/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.util.scripting;

import java.util.Map;

public interface ExecutableCompiledGroovyScript
extends AutoCloseable {
    public <T> T execute(Object[] var1, Class<T> var2);

    public void execute(Object[] var1);

    public <T> T execute(Object[] var1, Class<T> var2, boolean var3);

    public <T> T execute(String var1, Class<T> var2, Object ... var3);

    default public void setBinding(Map<String, Object> args) {
    }

    @Override
    default public void close() {
    }
}

