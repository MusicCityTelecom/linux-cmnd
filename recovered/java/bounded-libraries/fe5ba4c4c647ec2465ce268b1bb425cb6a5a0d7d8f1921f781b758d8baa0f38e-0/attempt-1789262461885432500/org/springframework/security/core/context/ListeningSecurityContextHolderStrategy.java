/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 */
package org.springframework.security.core.context;

import java.util.Arrays;
import java.util.Collection;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextChangedEvent;
import org.springframework.security.core.context.SecurityContextChangedListener;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.context.ThreadLocalSecurityContextHolderStrategy;
import org.springframework.util.Assert;

public final class ListeningSecurityContextHolderStrategy
implements SecurityContextHolderStrategy {
    private final Collection<SecurityContextChangedListener> listeners;
    private final SecurityContextHolderStrategy delegate;

    public ListeningSecurityContextHolderStrategy(Collection<SecurityContextChangedListener> listeners) {
        this((SecurityContextHolderStrategy)new ThreadLocalSecurityContextHolderStrategy(), listeners);
    }

    public ListeningSecurityContextHolderStrategy(SecurityContextChangedListener ... listeners) {
        this((SecurityContextHolderStrategy)new ThreadLocalSecurityContextHolderStrategy(), listeners);
    }

    public ListeningSecurityContextHolderStrategy(SecurityContextHolderStrategy delegate, Collection<SecurityContextChangedListener> listeners) {
        Assert.notNull((Object)delegate, (String)"securityContextHolderStrategy cannot be null");
        Assert.notNull(listeners, (String)"securityContextChangedListeners cannot be null");
        Assert.notEmpty(listeners, (String)"securityContextChangedListeners cannot be empty");
        Assert.noNullElements(listeners, (String)"securityContextChangedListeners cannot contain null elements");
        this.delegate = delegate;
        this.listeners = listeners;
    }

    public ListeningSecurityContextHolderStrategy(SecurityContextHolderStrategy delegate, SecurityContextChangedListener ... listeners) {
        Assert.notNull((Object)delegate, (String)"securityContextHolderStrategy cannot be null");
        Assert.notNull((Object)listeners, (String)"securityContextChangedListeners cannot be null");
        Assert.notEmpty((Object[])listeners, (String)"securityContextChangedListeners cannot be empty");
        Assert.noNullElements((Object[])listeners, (String)"securityContextChangedListeners cannot contain null elements");
        this.delegate = delegate;
        this.listeners = Arrays.asList(listeners);
    }

    @Override
    public void clearContext() {
        SecurityContext from = this.getContext();
        this.delegate.clearContext();
        this.publish(from, null);
    }

    @Override
    public SecurityContext getContext() {
        return this.delegate.getContext();
    }

    @Override
    public void setContext(SecurityContext context) {
        SecurityContext from = this.getContext();
        this.delegate.setContext(context);
        this.publish(from, context);
    }

    @Override
    public SecurityContext createEmptyContext() {
        return this.delegate.createEmptyContext();
    }

    private void publish(SecurityContext previous, SecurityContext current) {
        if (previous == current) {
            return;
        }
        SecurityContextChangedEvent event = new SecurityContextChangedEvent(previous, current);
        for (SecurityContextChangedListener listener : this.listeners) {
            listener.securityContextChanged(event);
        }
    }
}

