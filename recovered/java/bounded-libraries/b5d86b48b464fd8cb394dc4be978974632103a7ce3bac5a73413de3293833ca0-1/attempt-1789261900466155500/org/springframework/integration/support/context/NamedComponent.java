/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.support.context;

public interface NamedComponent {
    public String getComponentName();

    public String getComponentType();

    default public String getBeanName() {
        return this.getComponentName();
    }
}

