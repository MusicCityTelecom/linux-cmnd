/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.jdbc;

import java.util.function.Supplier;

public class UnsupportedDataSourcePropertyException
extends RuntimeException {
    UnsupportedDataSourcePropertyException(String message) {
        super(message);
    }

    static void throwIf(boolean test, Supplier<String> message) {
        if (test) {
            throw new UnsupportedDataSourcePropertyException(message.get());
        }
    }
}

