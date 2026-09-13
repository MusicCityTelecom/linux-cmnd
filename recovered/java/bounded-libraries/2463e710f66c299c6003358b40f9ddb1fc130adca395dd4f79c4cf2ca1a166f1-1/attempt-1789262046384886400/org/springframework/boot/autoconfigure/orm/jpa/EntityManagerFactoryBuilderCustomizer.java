/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
 */
package org.springframework.boot.autoconfigure.orm.jpa;

import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;

@FunctionalInterface
public interface EntityManagerFactoryBuilderCustomizer {
    public void customize(EntityManagerFactoryBuilder var1);
}

