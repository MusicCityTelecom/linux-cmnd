/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.undertow.Undertow$Builder
 */
package org.springframework.boot.web.embedded.undertow;

import io.undertow.Undertow;

@FunctionalInterface
public interface UndertowBuilderCustomizer {
    public void customize(Undertow.Builder var1);
}

