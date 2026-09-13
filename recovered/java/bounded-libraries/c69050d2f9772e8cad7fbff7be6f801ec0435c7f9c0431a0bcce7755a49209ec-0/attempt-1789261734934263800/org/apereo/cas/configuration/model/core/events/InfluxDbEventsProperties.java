/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 */
package org.apereo.cas.configuration.model.core.events;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.model.support.influxdb.InfluxDbProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-events-influxdb")
@JsonFilter(value="InfluxDbEventsProperties")
public class InfluxDbEventsProperties
extends InfluxDbProperties {
    private static final long serialVersionUID = -3918436901491275547L;

    public InfluxDbEventsProperties() {
        this.setDatabase("CasInfluxDbEvents");
    }
}

