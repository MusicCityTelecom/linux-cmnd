/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.support.events;

import java.time.ZonedDateTime;
import java.util.stream.Stream;
import org.apereo.cas.support.events.CasEventRepositoryFilter;
import org.apereo.cas.support.events.dao.CasEvent;

public interface CasEventRepository {
    public static final String BEAN_NAME = "casEventRepository";

    default public CasEventRepositoryFilter getEventRepositoryFilter() {
        return CasEventRepositoryFilter.noOp();
    }

    public void save(CasEvent var1) throws Exception;

    public Stream<? extends CasEvent> load();

    public Stream<? extends CasEvent> load(ZonedDateTime var1);

    public Stream<? extends CasEvent> getEventsOfTypeForPrincipal(String var1, String var2);

    public Stream<? extends CasEvent> getEventsOfTypeForPrincipal(String var1, String var2, ZonedDateTime var3);

    public Stream<? extends CasEvent> getEventsOfType(String var1);

    public Stream<? extends CasEvent> getEventsOfType(String var1, ZonedDateTime var2);

    public Stream<? extends CasEvent> getEventsForPrincipal(String var1);

    public Stream<? extends CasEvent> getEventsForPrincipal(String var1, ZonedDateTime var2);
}

