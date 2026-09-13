/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot;

public interface SpringApplicationShutdownHandlers {
    public void add(Runnable var1);

    public void remove(Runnable var1);
}

