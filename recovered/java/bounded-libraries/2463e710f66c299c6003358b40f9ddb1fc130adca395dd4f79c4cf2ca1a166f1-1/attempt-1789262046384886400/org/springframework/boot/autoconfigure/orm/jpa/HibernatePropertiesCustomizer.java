/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.autoconfigure.orm.jpa;

import java.util.Map;

@FunctionalInterface
public interface HibernatePropertiesCustomizer {
    public void customize(Map<String, Object> var1);
}

