/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.dsl;

import java.util.Map;

@FunctionalInterface
public interface ComponentsRegistration {
    public Map<Object, String> getComponentsToRegister();
}

