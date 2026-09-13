/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.ehcache.config.ManagementRESTServiceConfiguration
 */
package com.terracotta.management;

import com.terracotta.management.ApplicationEhCacheService;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import net.sf.ehcache.config.ManagementRESTServiceConfiguration;
import net.sf.ehcache.management.resource.services.CacheConfigsResourceServiceImpl;
import net.sf.ehcache.management.resource.services.CacheManagerConfigsResourceServiceImpl;
import net.sf.ehcache.management.resource.services.CacheManagersResourceServiceImpl;
import net.sf.ehcache.management.resource.services.CacheStatisticSamplesResourceServiceImpl;
import net.sf.ehcache.management.resource.services.CachesResourceServiceImpl;
import net.sf.ehcache.management.resource.services.ElementsResourceServiceImpl;
import net.sf.ehcache.management.resource.services.QueryResourceServiceImpl;
import net.sf.ehcache.management.resource.services.validator.impl.EmbeddedEhcacheRequestValidator;
import net.sf.ehcache.management.service.CacheManagerService;
import net.sf.ehcache.management.service.CacheService;
import net.sf.ehcache.management.service.EntityResourceFactory;
import net.sf.ehcache.management.service.ManagementServerLifecycle;
import net.sf.ehcache.management.service.SamplerRepositoryService;
import net.sf.ehcache.management.service.impl.DfltSamplerRepositoryService;
import net.sf.ehcache.management.service.impl.RemoteAgentEndpointImpl;
import org.terracotta.management.application.DefaultApplication;
import org.terracotta.management.resource.services.AgentService;
import org.terracotta.management.resource.services.validator.RequestValidator;

public class ApplicationEhCacheV1
extends DefaultApplication
implements ApplicationEhCacheService {
    @Override
    public Set<Class<?>> getRestResourceClasses() {
        HashSet s = new HashSet(super.getClasses());
        s.add(ElementsResourceServiceImpl.class);
        s.add(CacheStatisticSamplesResourceServiceImpl.class);
        s.add(CachesResourceServiceImpl.class);
        s.add(CacheManagersResourceServiceImpl.class);
        s.add(CacheManagerConfigsResourceServiceImpl.class);
        s.add(CacheConfigsResourceServiceImpl.class);
        s.add(QueryResourceServiceImpl.class);
        return s;
    }

    @Override
    public Map<Class<?>, Object> getServiceClasses(ManagementRESTServiceConfiguration configuration, RemoteAgentEndpointImpl agentEndpointImpl) {
        DfltSamplerRepositoryService samplerRepoSvc = new DfltSamplerRepositoryService(configuration, agentEndpointImpl);
        HashMap serviceClasses = new HashMap();
        serviceClasses.put(RequestValidator.class, new EmbeddedEhcacheRequestValidator());
        serviceClasses.put(CacheManagerService.class, samplerRepoSvc);
        serviceClasses.put(CacheService.class, samplerRepoSvc);
        serviceClasses.put(EntityResourceFactory.class, samplerRepoSvc);
        serviceClasses.put(SamplerRepositoryService.class, samplerRepoSvc);
        serviceClasses.put(AgentService.class, samplerRepoSvc);
        return serviceClasses;
    }

    @Override
    public Class<? extends ManagementServerLifecycle> getManagementServerLifecyle() {
        return SamplerRepositoryService.class;
    }
}

