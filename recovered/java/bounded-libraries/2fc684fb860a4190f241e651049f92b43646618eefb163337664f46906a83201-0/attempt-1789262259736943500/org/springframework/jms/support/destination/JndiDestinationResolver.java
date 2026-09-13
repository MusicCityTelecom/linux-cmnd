/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.Destination
 *  javax.jms.JMSException
 *  javax.jms.Queue
 *  javax.jms.Session
 *  javax.jms.Topic
 *  org.springframework.jndi.JndiLocatorSupport
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 */
package org.springframework.jms.support.destination;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.Topic;
import javax.naming.NamingException;
import org.springframework.jms.support.destination.CachingDestinationResolver;
import org.springframework.jms.support.destination.DestinationResolutionException;
import org.springframework.jms.support.destination.DestinationResolver;
import org.springframework.jms.support.destination.DynamicDestinationResolver;
import org.springframework.jndi.JndiLocatorSupport;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

public class JndiDestinationResolver
extends JndiLocatorSupport
implements CachingDestinationResolver {
    private boolean cache = true;
    private boolean fallbackToDynamicDestination = false;
    private DestinationResolver dynamicDestinationResolver = new DynamicDestinationResolver();
    private final Map<String, Destination> destinationCache = new ConcurrentHashMap<String, Destination>(16);

    public void setCache(boolean cache) {
        this.cache = cache;
    }

    public void setFallbackToDynamicDestination(boolean fallbackToDynamicDestination) {
        this.fallbackToDynamicDestination = fallbackToDynamicDestination;
    }

    public void setDynamicDestinationResolver(DestinationResolver dynamicDestinationResolver) {
        this.dynamicDestinationResolver = dynamicDestinationResolver;
    }

    @Override
    public Destination resolveDestinationName(@Nullable Session session, String destinationName, boolean pubSubDomain) throws JMSException {
        Assert.notNull((Object)destinationName, (String)"Destination name must not be null");
        Destination dest = this.destinationCache.get(destinationName);
        if (dest != null) {
            this.validateDestination(dest, destinationName, pubSubDomain);
        } else {
            try {
                dest = (Destination)this.lookup(destinationName, Destination.class);
                this.validateDestination(dest, destinationName, pubSubDomain);
            }
            catch (NamingException ex) {
                if (this.logger.isDebugEnabled()) {
                    this.logger.debug((Object)("Destination [" + destinationName + "] not found in JNDI"), (Throwable)ex);
                }
                if (this.fallbackToDynamicDestination) {
                    dest = this.dynamicDestinationResolver.resolveDestinationName(session, destinationName, pubSubDomain);
                }
                throw new DestinationResolutionException("Destination [" + destinationName + "] not found in JNDI", ex);
            }
            if (this.cache) {
                this.destinationCache.put(destinationName, dest);
            }
        }
        return dest;
    }

    protected void validateDestination(Destination destination, String destinationName, boolean pubSubDomain) {
        Class<Queue> targetClass = Queue.class;
        if (pubSubDomain) {
            targetClass = Topic.class;
        }
        if (!targetClass.isInstance(destination)) {
            throw new DestinationResolutionException("Destination [" + destinationName + "] is not of expected type [" + targetClass.getName() + "]");
        }
    }

    @Override
    public void removeFromCache(String destinationName) {
        this.destinationCache.remove(destinationName);
    }

    @Override
    public void clearCache() {
        this.destinationCache.clear();
    }
}

