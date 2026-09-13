/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.core.events;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-core-events", automated=true)
@JsonFilter(value="CoreEventsProperties")
public class CoreEventsProperties
implements Serializable {
    private static final long serialVersionUID = 2734523424737956370L;
    private boolean enabled = true;
    private boolean trackGeolocation;
    private boolean trackConfigurationModifications;

    @Generated
    public boolean isEnabled() {
        return this.enabled;
    }

    @Generated
    public boolean isTrackGeolocation() {
        return this.trackGeolocation;
    }

    @Generated
    public boolean isTrackConfigurationModifications() {
        return this.trackConfigurationModifications;
    }

    @Generated
    public CoreEventsProperties setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    @Generated
    public CoreEventsProperties setTrackGeolocation(boolean trackGeolocation) {
        this.trackGeolocation = trackGeolocation;
        return this;
    }

    @Generated
    public CoreEventsProperties setTrackConfigurationModifications(boolean trackConfigurationModifications) {
        this.trackConfigurationModifications = trackConfigurationModifications;
        return this;
    }
}

