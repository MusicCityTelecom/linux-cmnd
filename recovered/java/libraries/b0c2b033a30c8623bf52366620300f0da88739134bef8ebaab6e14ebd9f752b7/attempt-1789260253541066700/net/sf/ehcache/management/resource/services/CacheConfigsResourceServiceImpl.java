/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package net.sf.ehcache.management.resource.services;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import net.sf.ehcache.management.resource.CacheConfigEntity;
import net.sf.ehcache.management.service.EntityResourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terracotta.management.ServiceExecutionException;
import org.terracotta.management.ServiceLocator;
import org.terracotta.management.resource.exceptions.ResourceRuntimeException;
import org.terracotta.management.resource.services.validator.RequestValidator;

@Path(value="/agents/cacheManagers/caches/configs")
public final class CacheConfigsResourceServiceImpl {
    private static final Logger LOG = LoggerFactory.getLogger(CacheConfigsResourceServiceImpl.class);
    private final EntityResourceFactory entityResourceFactory = ServiceLocator.locate(EntityResourceFactory.class);
    private final RequestValidator validator = ServiceLocator.locate(RequestValidator.class);

    @GET
    @Produces(value={"application/json"})
    public Collection<CacheConfigEntity> getCacheConfigs(@Context UriInfo info) {
        LOG.debug(String.format("Invoking CacheConfigsResourceServiceImpl.getCacheConfigs: %s", info.getRequestUri()));
        this.validator.validateSafe(info);
        String cacheManagerNames = info.getPathSegments().get(1).getMatrixParameters().getFirst("names");
        HashSet<String> cmNames = cacheManagerNames == null ? null : new HashSet<String>(Arrays.asList(cacheManagerNames.split(",")));
        String cacheNames = info.getPathSegments().get(2).getMatrixParameters().getFirst("names");
        HashSet<String> cNames = cacheNames == null ? null : new HashSet<String>(Arrays.asList(cacheNames.split(",")));
        try {
            return this.entityResourceFactory.createCacheConfigEntities(cmNames, cNames);
        }
        catch (ServiceExecutionException see) {
            throw new ResourceRuntimeException("Failed to get cache configs", see, Response.Status.BAD_REQUEST.getStatusCode());
        }
    }
}

