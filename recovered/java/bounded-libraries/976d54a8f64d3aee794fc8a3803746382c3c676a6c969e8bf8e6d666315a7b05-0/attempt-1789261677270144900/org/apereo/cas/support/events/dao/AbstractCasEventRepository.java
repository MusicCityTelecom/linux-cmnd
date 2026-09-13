/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.support.events.CasEventRepository
 *  org.apereo.cas.support.events.CasEventRepositoryFilter
 *  org.apereo.cas.support.events.dao.CasEvent
 *  org.apereo.cas.util.DateTimeUtils
 *  org.springframework.boot.actuate.audit.AuditEvent
 *  org.springframework.boot.actuate.audit.listener.AuditApplicationEvent
 *  org.springframework.context.ApplicationEvent
 *  org.springframework.context.ApplicationEventPublisher
 *  org.springframework.context.ApplicationEventPublisherAware
 */
package org.apereo.cas.support.events.dao;

import java.time.ZonedDateTime;
import java.util.stream.Stream;
import lombok.Generated;
import org.apereo.cas.support.events.CasEventRepository;
import org.apereo.cas.support.events.CasEventRepositoryFilter;
import org.apereo.cas.support.events.dao.CasEvent;
import org.apereo.cas.util.DateTimeUtils;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.listener.AuditApplicationEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

public abstract class AbstractCasEventRepository
implements CasEventRepository,
ApplicationEventPublisherAware {
    protected static final String TYPE_PARAM = "type";
    protected static final String CREATION_TIME_PARAM = "creationTime";
    protected static final String PRINCIPAL_ID_PARAM = "principalId";
    private final CasEventRepositoryFilter eventRepositoryFilter;
    private ApplicationEventPublisher applicationEventPublisher;

    private static ZonedDateTime convertEventCreationTime(CasEvent event) {
        return DateTimeUtils.convertToZonedDateTime((String)event.getCreationTime());
    }

    public void save(CasEvent event) throws Exception {
        if (this.getEventRepositoryFilter().shouldSaveEvent(event)) {
            this.saveInternal(event);
            if (this.applicationEventPublisher != null) {
                AuditEvent auditEvent = new AuditEvent(event.getPrincipalId(), event.getType(), event.getProperties());
                this.applicationEventPublisher.publishEvent((ApplicationEvent)new AuditApplicationEvent(auditEvent));
            }
        }
    }

    public Stream<? extends CasEvent> load(ZonedDateTime dateTime) {
        return this.load().filter(e -> {
            ZonedDateTime dt = AbstractCasEventRepository.convertEventCreationTime(e);
            return dt.isEqual(dateTime) || dt.isAfter(dateTime);
        });
    }

    public Stream<? extends CasEvent> getEventsOfTypeForPrincipal(String type, String principal) {
        return this.getEventsForPrincipal(principal).filter(event -> event.getType().equals(type));
    }

    public Stream<? extends CasEvent> getEventsOfTypeForPrincipal(String type, String principal, ZonedDateTime dateTime) {
        return this.getEventsOfTypeForPrincipal(type, principal).filter(e -> {
            ZonedDateTime dt = AbstractCasEventRepository.convertEventCreationTime(e);
            return dt.isEqual(dateTime) || dt.isAfter(dateTime);
        });
    }

    public Stream<? extends CasEvent> getEventsOfType(String type) {
        return this.load().filter(event -> event.getType().equals(type));
    }

    public Stream<? extends CasEvent> getEventsOfType(String type, ZonedDateTime dateTime) {
        return this.getEventsOfType(type).filter(e -> {
            ZonedDateTime dt = AbstractCasEventRepository.convertEventCreationTime(e);
            return dt.isEqual(dt) || dt.isAfter(dt);
        });
    }

    public Stream<? extends CasEvent> getEventsForPrincipal(String id) {
        return this.load().filter(e -> e.getPrincipalId().equalsIgnoreCase(id));
    }

    public Stream<? extends CasEvent> getEventsForPrincipal(String id, ZonedDateTime dateTime) {
        return this.getEventsForPrincipal(id).filter(e -> {
            ZonedDateTime dt = AbstractCasEventRepository.convertEventCreationTime(e);
            return dt.isEqual(dateTime) || dt.isAfter(dateTime);
        });
    }

    public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
        this.applicationEventPublisher = publisher;
    }

    public abstract CasEvent saveInternal(CasEvent var1) throws Exception;

    @Generated
    protected AbstractCasEventRepository(CasEventRepositoryFilter eventRepositoryFilter) {
        this.eventRepositoryFilter = eventRepositoryFilter;
    }

    @Generated
    public CasEventRepositoryFilter getEventRepositoryFilter() {
        return this.eventRepositoryFilter;
    }

    @Generated
    public ApplicationEventPublisher getApplicationEventPublisher() {
        return this.applicationEventPublisher;
    }
}

