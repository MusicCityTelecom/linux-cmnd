/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.cache.Cache
 *  org.springframework.cache.Cache$ValueWrapper
 *  org.springframework.core.log.LogMessage
 *  org.springframework.util.Assert
 */
package org.springframework.security.cas.authentication;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cache.Cache;
import org.springframework.core.log.LogMessage;
import org.springframework.security.cas.authentication.CasAuthenticationToken;
import org.springframework.security.cas.authentication.StatelessTicketCache;
import org.springframework.util.Assert;

public class SpringCacheBasedTicketCache
implements StatelessTicketCache {
    private static final Log logger = LogFactory.getLog(SpringCacheBasedTicketCache.class);
    private final Cache cache;

    public SpringCacheBasedTicketCache(Cache cache) {
        Assert.notNull((Object)cache, (String)"cache mandatory");
        this.cache = cache;
    }

    @Override
    public CasAuthenticationToken getByTicketId(String serviceTicket) {
        Cache.ValueWrapper element = serviceTicket != null ? this.cache.get((Object)serviceTicket) : null;
        logger.debug((Object)LogMessage.of(() -> "Cache hit: " + (element != null) + "; service ticket: " + serviceTicket));
        return element != null ? (CasAuthenticationToken)element.get() : null;
    }

    @Override
    public void putTicketInCache(CasAuthenticationToken token) {
        String key = token.getCredentials().toString();
        logger.debug((Object)LogMessage.of(() -> "Cache put: " + key));
        this.cache.put((Object)key, (Object)token);
    }

    @Override
    public void removeTicketFromCache(CasAuthenticationToken token) {
        logger.debug((Object)LogMessage.of(() -> "Cache remove: " + token.getCredentials().toString()));
        this.removeTicketFromCache(token.getCredentials().toString());
    }

    @Override
    public void removeTicketFromCache(String serviceTicket) {
        this.cache.evict((Object)serviceTicket);
    }
}

