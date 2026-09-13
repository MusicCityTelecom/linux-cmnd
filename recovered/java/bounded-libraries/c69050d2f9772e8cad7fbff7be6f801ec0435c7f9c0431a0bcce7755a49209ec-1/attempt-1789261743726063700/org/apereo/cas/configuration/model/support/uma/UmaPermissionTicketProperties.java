/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.uma;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.DurationCapable;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-oauth-uma")
@JsonFilter(value="UmaPermissionTicketProperties")
public class UmaPermissionTicketProperties
implements Serializable {
    private static final long serialVersionUID = 6624128522839644377L;
    @DurationCapable
    private String maxTimeToLiveInSeconds = "PT3M";

    @Generated
    public String getMaxTimeToLiveInSeconds() {
        return this.maxTimeToLiveInSeconds;
    }

    @Generated
    public UmaPermissionTicketProperties setMaxTimeToLiveInSeconds(String maxTimeToLiveInSeconds) {
        this.maxTimeToLiveInSeconds = maxTimeToLiveInSeconds;
        return this;
    }
}

