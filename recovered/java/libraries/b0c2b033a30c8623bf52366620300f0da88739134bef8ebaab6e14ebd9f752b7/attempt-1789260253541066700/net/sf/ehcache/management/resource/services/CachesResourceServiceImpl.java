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
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import net.sf.ehcache.management.resource.CacheEntity;
import net.sf.ehcache.management.service.CacheService;
import net.sf.ehcache.management.service.EntityResourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terracotta.management.ServiceExecutionException;
import org.terracotta.management.ServiceLocator;
import org.terracotta.management.resource.exceptions.ResourceRuntimeException;
import org.terracotta.management.resource.services.validator.RequestValidator;

@Path(value="/agents/cacheManagers/caches")
public final class CachesResourceServiceImpl {
    public static final String ATTR_QUERY_KEY = "show";
    private static final Logger LOG = LoggerFactory.getLogger(CachesResourceServiceImpl.class);
    private final EntityResourceFactory entityResourceFactory = ServiceLocator.locate(EntityResourceFactory.class);
    private final CacheService cacheSvc;
    private final RequestValidator validator = ServiceLocator.locate(RequestValidator.class);

    public CachesResourceServiceImpl() {
        this.cacheSvc = ServiceLocator.locate(CacheService.class);
    }

    @GET
    @Produces(value={"application/json"})
    public Collection<CacheEntity> getCaches(@Context UriInfo info) {
        LOG.debug(String.format("Invoking CachesResourceServiceImpl.getCaches: %s", info.getRequestUri()));
        this.validator.validateSafe(info);
        String cacheManagerNames = info.getPathSegments().get(1).getMatrixParameters().getFirst("names");
        HashSet<String> cmNames = cacheManagerNames == null ? null : new HashSet<String>(Arrays.asList(cacheManagerNames.split(",")));
        String cacheNames = info.getPathSegments().get(2).getMatrixParameters().getFirst("names");
        HashSet<String> cNames = cacheNames == null ? null : new HashSet<String>(Arrays.asList(cacheNames.split(",")));
        MultivaluedMap<String, String> qParams = info.getQueryParameters();
        List attrs = (List)qParams.get(ATTR_QUERY_KEY);
        HashSet<String> cAttrs = attrs == null || attrs.isEmpty() ? null : new HashSet<String>(attrs);
        try {
            return this.entityResourceFactory.createCacheEntities(cmNames, cNames, cAttrs);
        }
        catch (ServiceExecutionException e) {
            throw new ResourceRuntimeException("Failed to get caches", e, Response.Status.BAD_REQUEST.getStatusCode());
        }
    }

    @PUT
    @Consumes(value={"application/json"})
    public void createOrUpdateCache(@Context UriInfo info, CacheEntity resource) {
        LOG.debug(String.format("Invoking CachesResourceServiceImpl.createOrUpdateCache: %s", info.getRequestUri()));
        this.validator.validate(info);
        String cacheManagerName = info.getPathSegments().get(1).getMatrixParameters().getFirst("names");
        String cacheName = info.getPathSegments().get(2).getMatrixParameters().getFirst("names");
        try {
            this.cacheSvc.createOrUpdateCache(cacheManagerName, cacheName, resource);
        }
        catch (ServiceExecutionException e) {
            throw new ResourceRuntimeException("Failed to create or update cache", e, Response.Status.BAD_REQUEST.getStatusCode());
        }
    }
}

