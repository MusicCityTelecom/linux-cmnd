/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
 */
package org.springframework.boot.autoconfigure.jackson;

import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@FunctionalInterface
public interface Jackson2ObjectMapperBuilderCustomizer {
    public void customize(Jackson2ObjectMapperBuilder var1);
}

