/*
 * Decompiled with CFR 0.152.
 */
package com.terracotta.management;

import com.terracotta.management.ApplicationEhCacheService;
import java.util.HashSet;
import java.util.ServiceLoader;
import java.util.Set;
import javax.ws.rs.core.Application;

public class ApplicationEhCache
extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        HashSet restResourcesClasses = new HashSet();
        ServiceLoader<ApplicationEhCacheService> loader = ServiceLoader.load(ApplicationEhCacheService.class, ApplicationEhCacheService.class.getClassLoader());
        for (ApplicationEhCacheService applicationEhCacheService : loader) {
            restResourcesClasses.addAll(applicationEhCacheService.getRestResourceClasses());
        }
        return restResourcesClasses;
    }
}

