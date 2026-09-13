/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 */
package org.springframework.boot.actuate.audit;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.AuditEventRepository;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

@Endpoint(id="auditevents")
public class AuditEventsEndpoint {
    private final AuditEventRepository auditEventRepository;

    public AuditEventsEndpoint(AuditEventRepository auditEventRepository) {
        Assert.notNull((Object)auditEventRepository, (String)"AuditEventRepository must not be null");
        this.auditEventRepository = auditEventRepository;
    }

    @ReadOperation
    public AuditEventsDescriptor events(@Nullable String principal, @Nullable OffsetDateTime after, @Nullable String type) {
        List<AuditEvent> events = this.auditEventRepository.find(principal, this.getInstant(after), type);
        return new AuditEventsDescriptor(events);
    }

    private Instant getInstant(OffsetDateTime offsetDateTime) {
        return offsetDateTime != null ? offsetDateTime.toInstant() : null;
    }

    public static final class AuditEventsDescriptor {
        private final List<AuditEvent> events;

        private AuditEventsDescriptor(List<AuditEvent> events) {
            this.events = events;
        }

        public List<AuditEvent> getEvents() {
            return this.events;
        }
    }
}

