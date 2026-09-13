/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 */
package org.springframework.boot.actuate.audit;

import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.AuditEventRepository;
import org.springframework.util.Assert;

public class InMemoryAuditEventRepository
implements AuditEventRepository {
    private static final int DEFAULT_CAPACITY = 1000;
    private final Object monitor = new Object();
    private AuditEvent[] events;
    private volatile int tail = -1;

    public InMemoryAuditEventRepository() {
        this(1000);
    }

    public InMemoryAuditEventRepository(int capacity) {
        this.events = new AuditEvent[capacity];
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setCapacity(int capacity) {
        Object object = this.monitor;
        synchronized (object) {
            this.events = new AuditEvent[capacity];
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void add(AuditEvent event) {
        Assert.notNull((Object)event, (String)"AuditEvent must not be null");
        Object object = this.monitor;
        synchronized (object) {
            this.tail = (this.tail + 1) % this.events.length;
            this.events[this.tail] = event;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<AuditEvent> find(String principal, Instant after, String type) {
        LinkedList<AuditEvent> events = new LinkedList<AuditEvent>();
        Object object = this.monitor;
        synchronized (object) {
            for (int i = 0; i < this.events.length; ++i) {
                AuditEvent event = this.resolveTailEvent(i);
                if (event == null || !this.isMatch(principal, after, type, event)) continue;
                events.addFirst(event);
            }
        }
        return events;
    }

    private boolean isMatch(String principal, Instant after, String type, AuditEvent event) {
        boolean match = true;
        match = match && (principal == null || event.getPrincipal().equals(principal));
        match = match && (after == null || event.getTimestamp().isAfter(after));
        match = match && (type == null || event.getType().equals(type));
        return match;
    }

    private AuditEvent resolveTailEvent(int offset) {
        int index = (this.tail + this.events.length - offset) % this.events.length;
        return this.events[index];
    }
}

