/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot;

@FunctionalInterface
public interface ExitCodeExceptionMapper {
    public int getExitCode(Throwable var1);
}

