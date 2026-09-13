/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.context.properties;

import org.springframework.boot.context.properties.bind.BindHandler;

@FunctionalInterface
public interface ConfigurationPropertiesBindHandlerAdvisor {
    public BindHandler apply(BindHandler var1);
}

