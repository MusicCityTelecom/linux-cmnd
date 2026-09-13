/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.ehcache.CacheException
 *  net.sf.ehcache.CacheManager
 *  net.sf.ehcache.management.ManagementServer
 */
package net.sf.ehcache.management;

import com.terracotta.management.ApplicationEhCacheService;
import java.util.List;
import java.util.ServiceLoader;
import java.util.concurrent.CopyOnWriteArrayList;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.management.ManagementServer;
import net.sf.ehcache.management.service.ManagementServerLifecycle;
import net.sf.ehcache.management.service.impl.RemoteAgentEndpointImpl;
import org.terracotta.management.ServiceLocator;
import org.terracotta.management.embedded.StandaloneServer;

public abstract class AbstractManagementServer
implements ManagementServer {
    protected RemoteAgentEndpointImpl remoteAgentEndpointImpl;
    protected StandaloneServer standaloneServer;
    protected final List<ManagementServerLifecycle> managementServerLifecycles = new CopyOnWriteArrayList<ManagementServerLifecycle>();

    public void start() {
        try {
            this.standaloneServer.start();
        }
        catch (Exception e) {
            for (ManagementServerLifecycle samplerRepoService : this.managementServerLifecycles) {
                samplerRepoService.dispose();
            }
            ServiceLocator.unload();
            throw new CacheException("error starting management server", (Throwable)e);
        }
    }

    public void stop() {
        try {
            for (ManagementServerLifecycle samplerRepoService : this.managementServerLifecycles) {
                samplerRepoService.dispose();
            }
            this.standaloneServer.stop();
            ServiceLocator.unload();
        }
        catch (Exception e) {
            throw new CacheException("error stopping management server", (Throwable)e);
        }
    }

    public void register(CacheManager managedResource) {
        for (ManagementServerLifecycle samplerRepoService : this.managementServerLifecycles) {
            samplerRepoService.register(managedResource);
        }
    }

    public void unregister(CacheManager managedResource) {
        for (ManagementServerLifecycle samplerRepoService : this.managementServerLifecycles) {
            samplerRepoService.unregister(managedResource);
        }
    }

    public boolean hasRegistered() {
        boolean hasRegistered = true;
        for (ManagementServerLifecycle samplerRepoService : this.managementServerLifecycles) {
            hasRegistered = hasRegistered && samplerRepoService.hasRegistered();
        }
        return hasRegistered;
    }

    protected ServiceLoader<ApplicationEhCacheService> applicationEhCacheServiceLoader() {
        ServiceLoader<ApplicationEhCacheService> sl = ServiceLoader.load(ApplicationEhCacheService.class, this.getClass().getClassLoader());
        if (!sl.iterator().hasNext()) {
            throw new CacheException("ServiceLoader found no ApplicationEhCacheService implementation");
        }
        return sl;
    }
}

