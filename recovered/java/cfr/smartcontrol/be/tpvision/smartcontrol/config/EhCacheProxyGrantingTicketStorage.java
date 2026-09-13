/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.config;

import be.tpvision.smartcontrol.messages.config.eh_cache_proxy_granting_ticket_storage.ConstructorMessages;
import java.io.Serializable;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.jasig.cas.client.proxy.ProxyGrantingTicketStorage;
import org.jasig.cas.client.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public class EhCacheProxyGrantingTicketStorage
implements ProxyGrantingTicketStorage {
    private static final Logger logger = LoggerFactory.getLogger(EhCacheProxyGrantingTicketStorage.class);
    private Ehcache ehcache;

    public EhCacheProxyGrantingTicketStorage(Ehcache ehcache) {
        Assert.notNull((Object)ehcache, ConstructorMessages.EH_CACHE_CAN_NOT_BE_NULL);
        this.ehcache = ehcache;
    }

    @Override
    public String retrieve(String proxyGrantingTicketIou) {
        if (CommonUtils.isBlank(proxyGrantingTicketIou)) {
            return null;
        }
        Element element = this.ehcache.get((Serializable)((Object)proxyGrantingTicketIou));
        if (element == null) {
            logger.info("No Proxy Ticket found for [{}].", (Object)proxyGrantingTicketIou);
            return null;
        }
        this.ehcache.remove((Serializable)((Object)proxyGrantingTicketIou));
        String proxyGrantingTicket = (String)element.getObjectValue();
        logger.debug("Returned ProxyGrantingTicket of [{}]", (Object)proxyGrantingTicket);
        return proxyGrantingTicket;
    }

    @Override
    public void save(String proxyGrantingTicketIou, String proxyGrantingTicket) {
        logger.debug("Saving ProxyGrantingTicketIOU and ProxyGrantingTicket combo: [{}, {}]", (Object)proxyGrantingTicketIou, (Object)proxyGrantingTicket);
        Element element = new Element((Serializable)((Object)proxyGrantingTicketIou), (Serializable)((Object)proxyGrantingTicket));
        this.ehcache.put(element);
    }

    @Override
    public void cleanUp() {
        this.ehcache.evictExpiredElements();
    }
}

