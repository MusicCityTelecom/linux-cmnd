/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot;

import java.util.function.Supplier;

public interface BootstrapContext {
    public <T> T get(Class<T> var1) throws IllegalStateException;

    public <T> T getOrElse(Class<T> var1, T var2);

    public <T> T getOrElseSupply(Class<T> var1, Supplier<T> var2);

    public <T, X extends Throwable> T getOrElseThrow(Class<T> var1, Supplier<? extends X> var2) throws X;

    public <T> boolean isRegistered(Class<T> var1);
}

