/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.services;

import java.io.File;
import java.util.Collection;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.ServiceRegistry;

public interface ResourceBasedServiceRegistry
extends ServiceRegistry {
    public void update(RegisteredService var1);

    public Collection<RegisteredService> load(File var1);
}

