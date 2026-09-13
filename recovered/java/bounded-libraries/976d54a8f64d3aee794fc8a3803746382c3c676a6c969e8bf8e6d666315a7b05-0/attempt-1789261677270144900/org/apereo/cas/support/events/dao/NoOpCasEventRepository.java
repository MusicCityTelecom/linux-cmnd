/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.support.events.CasEventRepository
 *  org.apereo.cas.support.events.CasEventRepositoryFilter
 *  org.apereo.cas.support.events.dao.CasEvent
 */
package org.apereo.cas.support.events.dao;

import java.util.stream.Stream;
import org.apereo.cas.support.events.CasEventRepository;
import org.apereo.cas.support.events.CasEventRepositoryFilter;
import org.apereo.cas.support.events.dao.AbstractCasEventRepository;
import org.apereo.cas.support.events.dao.CasEvent;

public class NoOpCasEventRepository
extends AbstractCasEventRepository {
    public static final CasEventRepository INSTANCE = new NoOpCasEventRepository();

    public NoOpCasEventRepository() {
        this(CasEventRepositoryFilter.noOp());
    }

    public NoOpCasEventRepository(CasEventRepositoryFilter eventRepositoryFilter) {
        super(eventRepositoryFilter);
    }

    @Override
    public CasEvent saveInternal(CasEvent event) {
        return event;
    }

    public Stream<? extends CasEvent> load() {
        return Stream.empty();
    }
}

