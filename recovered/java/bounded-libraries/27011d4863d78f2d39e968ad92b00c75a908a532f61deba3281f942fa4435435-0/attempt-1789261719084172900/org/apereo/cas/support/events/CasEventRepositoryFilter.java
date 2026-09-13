/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.support.events;

import org.apereo.cas.support.events.dao.CasEvent;

public interface CasEventRepositoryFilter {
    public static CasEventRepositoryFilter noOp() {
        return new NoOpCasEventRepositoryFilter();
    }

    default public boolean shouldSaveEvent(CasEvent event) {
        return true;
    }

    public static class NoOpCasEventRepositoryFilter
    implements CasEventRepositoryFilter {
    }
}

