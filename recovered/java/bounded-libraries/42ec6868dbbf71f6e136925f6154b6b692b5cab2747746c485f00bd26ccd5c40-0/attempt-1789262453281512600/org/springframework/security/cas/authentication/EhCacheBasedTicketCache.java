/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.ehcache.Ehcache
 *  net.sf.ehcache.Element
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.core.log.LogMessage
 *  org.springframework.util.Assert
 */
package org.springframework.security.cas.authentication;

import java.io.Serializable;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.log.LogMessage;
import org.springframework.security.cas.authentication.CasAuthenticationToken;
import org.springframework.security.cas.authentication.StatelessTicketCache;
import org.springframework.util.Assert;

@Deprecated
public class EhCacheBasedTicketCache
implements StatelessTicketCache,
InitializingBean {
    private static final Log logger = LogFactory.getLog(EhCacheBasedTicketCache.class);
    private Ehcache cache;

    public void afterPropertiesSet() {
        Assert.notNull((Object)this.cache, (String)"cache mandatory");
    }

    @Override
    public CasAuthenticationToken getByTicketId(String serviceTicket) {
        Element element = this.cache.get((Serializable)((Object)serviceTicket));
        logger.debug((Object)LogMessage.of(() -> "Cache hit: " + (element != null) + "; service ticket: " + serviceTicket));
        return element != null ? (CasAuthenticationToken)element.getValue() : null;
    }

    public Ehcache getCache() {
        return this.cache;
    }

    @Override
    public void putTicketInCache(CasAuthenticationToken token) {
        Element element = new Element((Serializable)((Object)token.getCredentials().toString()), (Serializable)token);
        logger.debug((Object)LogMessage.of(() -> "Cache put: " + element.getKey()));
        this.cache.put(element);
    }

    @Override
    public void removeTicketFromCache(CasAuthenticationToken token) {
        logger.debug((Object)LogMessage.of(() -> "Cache remove: " + token.getCredentials().toString()));
        this.removeTicketFromCache(token.getCredentials().toString());
    }

    @Override
    public void removeTicketFromCache(String serviceTicket) {
        this.cache.remove((Serializable)((Object)serviceTicket));
    }

    public void setCache(Ehcache cache) {
        this.cache = cache;
    }
}

