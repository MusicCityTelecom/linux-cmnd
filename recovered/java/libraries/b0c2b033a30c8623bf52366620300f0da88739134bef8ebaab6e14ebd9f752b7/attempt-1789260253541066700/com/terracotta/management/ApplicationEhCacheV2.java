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
import net.sf.ehcache.management.resource.services.CacheConfigsResourceServiceImplV2;
import net.sf.ehcache.management.resource.services.CacheManagerConfigsResourceServiceImplV2;
import net.sf.ehcache.management.resource.services.CacheManagersResourceServiceImplV2;
import net.sf.ehcache.management.resource.services.CacheStatisticSamplesResourceServiceImplV2;
import net.sf.ehcache.management.resource.services.CachesResourceServiceImplV2;
import net.sf.ehcache.management.resource.services.ElementsResourceServiceImplV2;
import net.sf.ehcache.management.resource.services.QueryResourceServiceImplV2;
import net.sf.ehcache.management.resource.services.validator.impl.EmbeddedEhcacheRequestValidator;
import net.sf.ehcache.management.service.CacheManagerServiceV2;
import net.sf.ehcache.management.service.CacheServiceV2;
import net.sf.ehcache.management.service.EntityResourceFactoryV2;
import net.sf.ehcache.management.service.ManagementServerLifecycle;
import net.sf.ehcache.management.service.SamplerRepositoryServiceV2;
import net.sf.ehcache.management.service.impl.DfltSamplerRepositoryServiceV2;
import net.sf.ehcache.management.service.impl.RemoteAgentEndpointImpl;
import org.terracotta.management.application.DefaultApplicationV2;
import org.terracotta.management.resource.services.AgentServiceV2;
import org.terracotta.management.resource.services.events.EventServiceV2;
import org.terracotta.management.resource.services.validator.RequestValidator;

public class ApplicationEhCacheV2
extends DefaultApplicationV2
implements ApplicationEhCacheService {
    @Override
    public Set<Class<?>> getRestResourceClasses() {
        HashSet s = new HashSet(super.getClasses());
        s.add(ElementsResourceServiceImplV2.class);
        s.add(CacheStatisticSamplesResourceServiceImplV2.class);
        s.add(CachesResourceServiceImplV2.class);
        s.add(CacheManagersResourceServiceImplV2.class);
        s.add(CacheManagerConfigsResourceServiceImplV2.class);
        s.add(CacheConfigsResourceServiceImplV2.class);
        s.add(QueryResourceServiceImplV2.class);
        return s;
    }

    @Override
    public Map<Class<?>, Object> getServiceClasses(ManagementRESTServiceConfiguration configuration, RemoteAgentEndpointImpl agentEndpointImpl) {
        DfltSamplerRepositoryServiceV2 samplerRepoSvc = new DfltSamplerRepositoryServiceV2(configuration, agentEndpointImpl);
        HashMap serviceClasses = new HashMap();
        serviceClasses.put(RequestValidator.class, new EmbeddedEhcacheRequestValidator());
        serviceClasses.put(CacheManagerServiceV2.class, samplerRepoSvc);
        serviceClasses.put(CacheServiceV2.class, samplerRepoSvc);
        serviceClasses.put(EntityResourceFactoryV2.class, samplerRepoSvc);
        serviceClasses.put(SamplerRepositoryServiceV2.class, samplerRepoSvc);
        serviceClasses.put(AgentServiceV2.class, samplerRepoSvc);
        serviceClasses.put(EventServiceV2.class, samplerRepoSvc);
        return serviceClasses;
    }

    @Override
    public Class<? extends ManagementServerLifecycle> getManagementServerLifecyle() {
        return SamplerRepositoryServiceV2.class;
    }
}

