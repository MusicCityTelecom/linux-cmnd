/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.benmanes.caffeine.cache.Cache
 *  com.github.benmanes.caffeine.cache.Caffeine
 *  com.github.benmanes.caffeine.cache.Expiry
 *  com.github.benmanes.caffeine.cache.RemovalCause
 *  com.github.benmanes.caffeine.cache.RemovalListener
 *  lombok.Generated
 *  org.apereo.cas.logout.LogoutManager
 *  org.apereo.cas.logout.SingleLogoutExecutionRequest
 *  org.apereo.cas.ticket.Ticket
 *  org.apereo.cas.ticket.TicketGrantingTicket
 *  org.apereo.cas.util.crypto.CipherExecutor
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.ObjectProvider
 */
package org.apereo.cas.ticket.registry;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import com.github.benmanes.caffeine.cache.RemovalCause;
import com.github.benmanes.caffeine.cache.RemovalListener;
import java.util.Map;
import lombok.Generated;
import org.apereo.cas.logout.LogoutManager;
import org.apereo.cas.logout.SingleLogoutExecutionRequest;
import org.apereo.cas.ticket.Ticket;
import org.apereo.cas.ticket.TicketGrantingTicket;
import org.apereo.cas.ticket.registry.AbstractMapBasedTicketRegistry;
import org.apereo.cas.util.crypto.CipherExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;

public class CachingTicketRegistry
extends AbstractMapBasedTicketRegistry {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(CachingTicketRegistry.class);
    private static final int INITIAL_CACHE_SIZE = 50;
    private static final long MAX_CACHE_SIZE = 100000000L;
    private final Map<String, Ticket> mapInstance;
    private final Cache<String, Ticket> storage = Caffeine.newBuilder().initialCapacity(50).maximumSize(100000000L).expireAfter((Expiry)new CachedTicketExpirationPolicy()).removalListener((RemovalListener)new CachedTicketRemovalListener()).build();
    private final ObjectProvider<LogoutManager> logoutManager;

    public CachingTicketRegistry(ObjectProvider<LogoutManager> logoutManager) {
        this(CipherExecutor.noOp(), logoutManager);
    }

    public CachingTicketRegistry(CipherExecutor cipherExecutor, ObjectProvider<LogoutManager> logoutManager) {
        super(cipherExecutor);
        this.mapInstance = this.storage.asMap();
        this.logoutManager = logoutManager;
    }

    @Override
    @Generated
    public Map<String, Ticket> getMapInstance() {
        return this.mapInstance;
    }

    @Generated
    public Cache<String, Ticket> getStorage() {
        return this.storage;
    }

    @Generated
    public ObjectProvider<LogoutManager> getLogoutManager() {
        return this.logoutManager;
    }

    public class CachedTicketRemovalListener
    implements RemovalListener<String, Ticket> {
        public void onRemoval(String key, Ticket value, RemovalCause cause) {
            if (cause == RemovalCause.EXPIRED) {
                LOGGER.warn("Received removal notification for ticket [{}] with cause [{}]. Cleaning...", (Object)key, (Object)cause);
                if (value instanceof TicketGrantingTicket) {
                    CachingTicketRegistry.this.logoutManager.ifAvailable(manager -> manager.performLogout(SingleLogoutExecutionRequest.builder().ticketGrantingTicket((TicketGrantingTicket)TicketGrantingTicket.class.cast(value)).build()));
                }
            }
        }
    }

    public static class CachedTicketExpirationPolicy
    implements Expiry<String, Ticket> {
        private static long getExpiration(Ticket value, long currentTime) {
            if (value.isExpired()) {
                LOGGER.debug("Ticket [{}] has expired and shall be evicted from the cache", (Object)value.getId());
                return 0L;
            }
            return currentTime;
        }

        public long expireAfterCreate(String key, Ticket value, long currentTime) {
            return CachedTicketExpirationPolicy.getExpiration(value, currentTime);
        }

        public long expireAfterUpdate(String key, Ticket value, long currentTime, long currentDuration) {
            return CachedTicketExpirationPolicy.getExpiration(value, currentDuration);
        }

        public long expireAfterRead(String key, Ticket value, long currentTime, long currentDuration) {
            return CachedTicketExpirationPolicy.getExpiration(value, currentDuration);
        }
    }
}

