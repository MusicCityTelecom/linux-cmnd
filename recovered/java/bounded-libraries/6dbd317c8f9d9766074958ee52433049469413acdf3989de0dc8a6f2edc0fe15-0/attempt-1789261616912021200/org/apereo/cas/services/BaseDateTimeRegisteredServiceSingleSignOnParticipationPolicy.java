/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  lombok.Generated
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.ticket.AuthenticationAwareTicket
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.concurrent.TimeUnit;
import lombok.Generated;
import org.apereo.cas.services.DefaultRegisteredServiceSingleSignOnParticipationPolicy;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.ticket.AuthenticationAwareTicket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public abstract class BaseDateTimeRegisteredServiceSingleSignOnParticipationPolicy
extends DefaultRegisteredServiceSingleSignOnParticipationPolicy {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseDateTimeRegisteredServiceSingleSignOnParticipationPolicy.class);
    private static final long serialVersionUID = -5923946898337761319L;
    private TimeUnit timeUnit = TimeUnit.SECONDS;
    private long timeValue;
    private int order;

    @Override
    public boolean shouldParticipateInSso(RegisteredService registeredService, AuthenticationAwareTicket ticketState) {
        LOGGER.trace("Calculating SSO participation criteria for [{}]", (Object)ticketState);
        if (this.timeValue <= 0L) {
            return true;
        }
        long convertedNano = this.timeUnit.toNanos(this.timeValue);
        ZonedDateTime startingDate = this.determineInitialDateTime(registeredService, ticketState);
        ZonedDateTime endingDate = startingDate.plusNanos(convertedNano);
        ZonedDateTime currentTime = ZonedDateTime.now(ZoneOffset.UTC);
        LOGGER.trace("Starting date/time [{}]. Ending date/time constraint [{}]. Current date/time [{}]", new Object[]{startingDate, endingDate, currentTime});
        if (currentTime.isBefore(endingDate)) {
            LOGGER.debug("Current time [{}] is before [{}] where SSO participation is granted", (Object)currentTime, (Object)endingDate);
            return true;
        }
        LOGGER.debug("Current time [{}] is after [{}] where SSO participation is rejected", (Object)currentTime, (Object)endingDate);
        return false;
    }

    @JsonIgnore
    protected abstract ZonedDateTime determineInitialDateTime(RegisteredService var1, AuthenticationAwareTicket var2);

    @Override
    @Generated
    public String toString() {
        return "BaseDateTimeRegisteredServiceSingleSignOnParticipationPolicy(super=" + super.toString() + ", timeUnit=" + this.timeUnit + ", timeValue=" + this.timeValue + ", order=" + this.order + ")";
    }

    @Generated
    public TimeUnit getTimeUnit() {
        return this.timeUnit;
    }

    @Generated
    public long getTimeValue() {
        return this.timeValue;
    }

    @Generated
    public int getOrder() {
        return this.order;
    }

    @Generated
    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    @Generated
    public void setTimeValue(long timeValue) {
        this.timeValue = timeValue;
    }

    @Generated
    public void setOrder(int order) {
        this.order = order;
    }

    @Generated
    protected BaseDateTimeRegisteredServiceSingleSignOnParticipationPolicy() {
    }

    @Generated
    protected BaseDateTimeRegisteredServiceSingleSignOnParticipationPolicy(TimeUnit timeUnit, long timeValue, int order) {
        this.timeUnit = timeUnit;
        this.timeValue = timeValue;
        this.order = order;
    }

    @Override
    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof BaseDateTimeRegisteredServiceSingleSignOnParticipationPolicy)) {
            return false;
        }
        BaseDateTimeRegisteredServiceSingleSignOnParticipationPolicy other = (BaseDateTimeRegisteredServiceSingleSignOnParticipationPolicy)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        if (this.timeValue != other.timeValue) {
            return false;
        }
        if (this.order != other.order) {
            return false;
        }
        TimeUnit this$timeUnit = this.timeUnit;
        TimeUnit other$timeUnit = other.timeUnit;
        return !(this$timeUnit == null ? other$timeUnit != null : !((Object)((Object)this$timeUnit)).equals((Object)other$timeUnit));
    }

    @Override
    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof BaseDateTimeRegisteredServiceSingleSignOnParticipationPolicy;
    }

    @Override
    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        long $timeValue = this.timeValue;
        result = result * 59 + (int)($timeValue >>> 32 ^ $timeValue);
        result = result * 59 + this.order;
        TimeUnit $timeUnit = this.timeUnit;
        result = result * 59 + ($timeUnit == null ? 43 : ((Object)((Object)$timeUnit)).hashCode());
        return result;
    }
}

