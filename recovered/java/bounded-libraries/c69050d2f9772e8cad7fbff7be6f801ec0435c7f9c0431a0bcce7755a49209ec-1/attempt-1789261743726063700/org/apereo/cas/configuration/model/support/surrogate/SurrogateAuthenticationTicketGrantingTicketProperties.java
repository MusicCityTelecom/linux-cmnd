/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.surrogate;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-surrogate-webflow")
@JsonFilter(value="SurrogateAuthenticationTicketGrantingTicketProperties")
public class SurrogateAuthenticationTicketGrantingTicketProperties
implements Serializable {
    private static final long serialVersionUID = 2077366413438267330L;
    private long timeToKillInSeconds = 1800L;

    @Generated
    public long getTimeToKillInSeconds() {
        return this.timeToKillInSeconds;
    }

    @Generated
    public SurrogateAuthenticationTicketGrantingTicketProperties setTimeToKillInSeconds(long timeToKillInSeconds) {
        this.timeToKillInSeconds = timeToKillInSeconds;
        return this;
    }
}

