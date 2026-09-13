/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.inject.hk2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.jersey.internal.guava.Preconditions;
import org.glassfish.jersey.internal.inject.ForeignDescriptor;
import org.glassfish.jersey.internal.util.ExtendedLogger;
import org.glassfish.jersey.internal.util.LazyUid;
import org.glassfish.jersey.process.internal.RequestContext;
import org.glassfish.jersey.process.internal.RequestScope;

public class Hk2RequestScope
extends RequestScope {
    @Override
    public RequestContext createContext() {
        return new Instance();
    }

    public static final class Instance
    implements RequestContext {
        private final ExtendedLogger logger = new ExtendedLogger(Logger.getLogger(Instance.class.getName()), Level.FINEST);
        private final LazyUid id = new LazyUid();
        private final Map<ForeignDescriptor, Object> store = new HashMap<ForeignDescriptor, Object>();
        private final AtomicInteger referenceCounter = new AtomicInteger(1);

        private Instance() {
        }

        @Override
        public Instance getReference() {
            this.referenceCounter.incrementAndGet();
            return this;
        }

        public <T> T get(ForeignDescriptor descriptor) {
            return (T)this.store.get(descriptor);
        }

        public <T> T put(ForeignDescriptor descriptor, T value) {
            Preconditions.checkState(!this.store.containsKey(descriptor), "An instance for the descriptor %s was already seeded in this scope. Old instance: %s New instance: %s", descriptor, this.store.get(descriptor), value);
            return (T)this.store.put(descriptor, value);
        }

        public <T> void remove(ForeignDescriptor descriptor) {
            Object removed = this.store.remove(descriptor);
            if (removed != null) {
                descriptor.dispose(removed);
            }
        }

        public boolean contains(ForeignDescriptor provider) {
            return this.store.containsKey(provider);
        }

        @Override
        public void release() {
            if (this.referenceCounter.decrementAndGet() < 1) {
                try {
                    new HashSet<ForeignDescriptor>(this.store.keySet()).forEach(this::remove);
                }
                catch (Throwable throwable) {
                    this.logger.debugLog("Released scope instance {0}", this);
                    throw throwable;
                }
                this.logger.debugLog("Released scope instance {0}", this);
            }
        }

        public String toString() {
            return "Instance{id=" + this.id + ", referenceCounter=" + this.referenceCounter + ", store size=" + this.store.size() + '}';
        }
    }
}

