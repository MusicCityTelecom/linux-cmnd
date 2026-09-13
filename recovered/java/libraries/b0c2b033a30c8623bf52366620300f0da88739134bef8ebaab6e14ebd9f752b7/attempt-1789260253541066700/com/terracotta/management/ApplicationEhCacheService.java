/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.ehcache.config.ManagementRESTServiceConfiguration
 */
package com.terracotta.management;

import java.util.Map;
import java.util.Set;
import net.sf.ehcache.config.ManagementRESTServiceConfiguration;
import net.sf.ehcache.management.service.ManagementServerLifecycle;
import net.sf.ehcache.management.service.impl.RemoteAgentEndpointImpl;

public interface ApplicationEhCacheService<T> {
    public Set<Class<?>> getRestResourceClasses();

    public Map<Class<?>, Object> getServiceClasses(ManagementRESTServiceConfiguration var1, RemoteAgentEndpointImpl var2);

    public Class<? extends ManagementServerLifecycle> getManagementServerLifecyle();
}

