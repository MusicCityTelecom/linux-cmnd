/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.core.events;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.jpa.AbstractJpaProperties;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-events-jpa")
@JsonFilter(value="JpaEventsProperties")
public class JpaEventsProperties
extends AbstractJpaProperties {
    private static final long serialVersionUID = 7647381223153797806L;
    @RequiredProperty
    private boolean enabled = true;

    @Generated
    public boolean isEnabled() {
        return this.enabled;
    }

    @Generated
    public JpaEventsProperties setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }
}

