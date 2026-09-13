/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.core.ticket;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.ticket.HardTimeoutTicketExpirationPolicyProperties;
import org.apereo.cas.configuration.model.core.ticket.PrimaryTicketExpirationPolicyProperties;
import org.apereo.cas.configuration.model.core.ticket.RememberMeAuthenticationProperties;
import org.apereo.cas.configuration.model.core.ticket.ThrottledTimeoutTicketExpirationPolicyProperties;
import org.apereo.cas.configuration.model.core.ticket.TicketGrantingTicketCoreProperties;
import org.apereo.cas.configuration.model.core.ticket.TimeoutTicketExpirationPolicyProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-core-tickets", automated=true)
@JsonFilter(value="TicketGrantingTicketProperties")
public class TicketGrantingTicketProperties
implements Serializable {
    private static final long serialVersionUID = 2349079252583399336L;
    @NestedConfigurationProperty
    private PrimaryTicketExpirationPolicyProperties primary = new PrimaryTicketExpirationPolicyProperties();
    @NestedConfigurationProperty
    private TicketGrantingTicketCoreProperties core = new TicketGrantingTicketCoreProperties();
    @NestedConfigurationProperty
    private HardTimeoutTicketExpirationPolicyProperties hardTimeout = new HardTimeoutTicketExpirationPolicyProperties();
    @NestedConfigurationProperty
    private ThrottledTimeoutTicketExpirationPolicyProperties throttledTimeout = new ThrottledTimeoutTicketExpirationPolicyProperties();
    @NestedConfigurationProperty
    private TimeoutTicketExpirationPolicyProperties timeout = new TimeoutTicketExpirationPolicyProperties();
    @NestedConfigurationProperty
    private RememberMeAuthenticationProperties rememberMe = new RememberMeAuthenticationProperties();

    @Generated
    public PrimaryTicketExpirationPolicyProperties getPrimary() {
        return this.primary;
    }

    @Generated
    public TicketGrantingTicketCoreProperties getCore() {
        return this.core;
    }

    @Generated
    public HardTimeoutTicketExpirationPolicyProperties getHardTimeout() {
        return this.hardTimeout;
    }

    @Generated
    public ThrottledTimeoutTicketExpirationPolicyProperties getThrottledTimeout() {
        return this.throttledTimeout;
    }

    @Generated
    public TimeoutTicketExpirationPolicyProperties getTimeout() {
        return this.timeout;
    }

    @Generated
    public RememberMeAuthenticationProperties getRememberMe() {
        return this.rememberMe;
    }

    @Generated
    public TicketGrantingTicketProperties setPrimary(PrimaryTicketExpirationPolicyProperties primary) {
        this.primary = primary;
        return this;
    }

    @Generated
    public TicketGrantingTicketProperties setCore(TicketGrantingTicketCoreProperties core) {
        this.core = core;
        return this;
    }

    @Generated
    public TicketGrantingTicketProperties setHardTimeout(HardTimeoutTicketExpirationPolicyProperties hardTimeout) {
        this.hardTimeout = hardTimeout;
        return this;
    }

    @Generated
    public TicketGrantingTicketProperties setThrottledTimeout(ThrottledTimeoutTicketExpirationPolicyProperties throttledTimeout) {
        this.throttledTimeout = throttledTimeout;
        return this;
    }

    @Generated
    public TicketGrantingTicketProperties setTimeout(TimeoutTicketExpirationPolicyProperties timeout) {
        this.timeout = timeout;
        return this;
    }

    @Generated
    public TicketGrantingTicketProperties setRememberMe(RememberMeAuthenticationProperties rememberMe) {
        this.rememberMe = rememberMe;
        return this;
    }
}

