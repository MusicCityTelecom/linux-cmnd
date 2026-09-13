/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot;

@FunctionalInterface
public interface SpringBootExceptionReporter {
    public boolean reportException(Throwable var1);
}

