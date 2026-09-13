/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.http.codec.CodecConfigurer
 */
package org.springframework.boot.web.codec;

import org.springframework.http.codec.CodecConfigurer;

@FunctionalInterface
public interface CodecCustomizer {
    public void customize(CodecConfigurer var1);
}

