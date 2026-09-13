/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.ehcache.config.ManagementRESTServiceConfiguration
 */
package net.sf.ehcache.management;

import com.terracotta.management.ApplicationEhCacheService;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import net.sf.ehcache.config.ManagementRESTServiceConfiguration;
import net.sf.ehcache.management.AbstractManagementServer;
import net.sf.ehcache.management.service.ManagementServerLifecycle;
import net.sf.ehcache.management.service.impl.RemoteAgentEndpointImpl;
import org.terracotta.management.ServiceLocator;
import org.terracotta.management.embedded.FilterDetail;
import org.terracotta.management.embedded.NoIaFilter;
import org.terracotta.management.embedded.StandaloneServer;
import org.terracotta.management.resource.services.LicenseService;
import org.terracotta.management.resource.services.LicenseServiceImpl;

public final class ManagementServerImpl
extends AbstractManagementServer {
    public void initialize(ManagementRESTServiceConfiguration configuration) {
        configuration.setNeedClientAuth(false);
        configuration.setSecurityServiceLocation(null);
        configuration.setSslEnabled(false);
        configuration.setSecurityServiceTimeout(0);
        String host = configuration.getHost();
        int port = configuration.getPort();
        this.loadEmbeddedAgentServiceLocator(configuration);
        ServiceLoader<ApplicationEhCacheService> loaders = this.applicationEhCacheServiceLoader();
        for (ApplicationEhCacheService applicationEhCacheService : loaders) {
            Class<ManagementServerLifecycle> clazz = applicationEhCacheService.getManagementServerLifecyle();
            this.managementServerLifecycles.add(ServiceLocator.locate(clazz));
        }
        List<FilterDetail> filterDetails = Collections.singletonList(new FilterDetail(new NoIaFilter(), "/*"));
        this.standaloneServer = new StandaloneServer(filterDetails, null, "com.terracotta.management.ApplicationEhCache", host, port, null, false);
    }

    public void registerClusterRemoteEndpoint(String clientUUID) {
        this.remoteAgentEndpointImpl.registerMBean(clientUUID);
    }

    public void unregisterClusterRemoteEndpoint(String clientUUID) {
        this.remoteAgentEndpointImpl.unregisterMBean(clientUUID);
    }

    private <T> void loadEmbeddedAgentServiceLocator(ManagementRESTServiceConfiguration configuration) {
        this.remoteAgentEndpointImpl = new RemoteAgentEndpointImpl();
        ServiceLocator locator = new ServiceLocator();
        LicenseServiceImpl licenseService = new LicenseServiceImpl(false);
        ServiceLoader<ApplicationEhCacheService> loader = this.applicationEhCacheServiceLoader();
        for (ApplicationEhCacheService applicationEhCacheService : loader) {
            Map<Class<?>, Object> serviceClasses = applicationEhCacheService.getServiceClasses(configuration, this.remoteAgentEndpointImpl);
            for (Map.Entry<Class<?>, Object> entry : serviceClasses.entrySet()) {
                locator.loadService(entry.getKey(), entry.getValue());
            }
        }
        locator.loadService(LicenseService.class, licenseService);
        locator.loadService(ManagementRESTServiceConfiguration.class, configuration);
        ServiceLocator.load(locator);
    }
}

