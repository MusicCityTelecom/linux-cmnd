/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.core.ticket;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-core-tickets", automated=true)
@JsonFilter(value="RememberMeTicketExpirationPolicyProperties")
public class RememberMeAuthenticationProperties
implements Serializable {
    private static final long serialVersionUID = 1899959269597512610L;
    private boolean enabled;
    private long timeToKillInSeconds = 1209600L;
    private String supportedUserAgents;
    private String supportedIpAddresses;

    @Generated
    public boolean isEnabled() {
        return this.enabled;
    }

    @Generated
    public long getTimeToKillInSeconds() {
        return this.timeToKillInSeconds;
    }

    @Generated
    public String getSupportedUserAgents() {
        return this.supportedUserAgents;
    }

    @Generated
    public String getSupportedIpAddresses() {
        return this.supportedIpAddresses;
    }

    @Generated
    public RememberMeAuthenticationProperties setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    @Generated
    public RememberMeAuthenticationProperties setTimeToKillInSeconds(long timeToKillInSeconds) {
        this.timeToKillInSeconds = timeToKillInSeconds;
        return this;
    }

    @Generated
    public RememberMeAuthenticationProperties setSupportedUserAgents(String supportedUserAgents) {
        this.supportedUserAgents = supportedUserAgents;
        return this;
    }

    @Generated
    public RememberMeAuthenticationProperties setSupportedIpAddresses(String supportedIpAddresses) {
        this.supportedIpAddresses = supportedIpAddresses;
        return this;
    }
}

