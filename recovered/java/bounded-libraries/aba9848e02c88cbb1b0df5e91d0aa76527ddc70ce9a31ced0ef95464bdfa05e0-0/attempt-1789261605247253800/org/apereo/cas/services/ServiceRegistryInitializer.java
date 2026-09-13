/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.InitializingBean
 */
package org.apereo.cas.services;

import org.springframework.beans.factory.InitializingBean;

@FunctionalInterface
public interface ServiceRegistryInitializer
extends InitializingBean {
    public void initialize();

    default public void afterPropertiesSet() throws Exception {
        this.initialize();
    }
}

