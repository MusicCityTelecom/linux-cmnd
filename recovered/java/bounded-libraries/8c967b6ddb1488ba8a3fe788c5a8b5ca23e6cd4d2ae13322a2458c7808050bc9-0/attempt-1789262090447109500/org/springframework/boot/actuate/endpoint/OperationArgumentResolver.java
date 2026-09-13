/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 */
package org.springframework.boot.actuate.endpoint;

import java.util.function.Supplier;
import org.springframework.util.Assert;

public interface OperationArgumentResolver {
    public boolean canResolve(Class<?> var1);

    public <T> T resolve(Class<T> var1);

    public static <T> OperationArgumentResolver of(final Class<T> type, final Supplier<? extends T> supplier) {
        Assert.notNull(type, (String)"Type must not be null");
        Assert.notNull(supplier, (String)"Supplier must not be null");
        return new OperationArgumentResolver(){

            @Override
            public boolean canResolve(Class<?> actualType) {
                return actualType.equals(type);
            }

            public <R> R resolve(Class<R> argumentType) {
                return (R)supplier.get();
            }
        };
    }
}

