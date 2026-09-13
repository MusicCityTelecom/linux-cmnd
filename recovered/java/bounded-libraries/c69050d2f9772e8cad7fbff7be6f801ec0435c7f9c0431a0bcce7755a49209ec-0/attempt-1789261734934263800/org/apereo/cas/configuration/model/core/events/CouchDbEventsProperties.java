/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 */
package org.apereo.cas.configuration.model.core.events;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.model.support.couchdb.BaseAsynchronousCouchDbProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-events-couchdb")
@JsonFilter(value="CouchDbEventsProperties")
public class CouchDbEventsProperties
extends BaseAsynchronousCouchDbProperties {
    private static final long serialVersionUID = -1587160128953366615L;

    public CouchDbEventsProperties() {
        this.setDbName("events");
    }
}

