/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  lombok.Generated
 *  org.apereo.cas.services.DefaultRegisteredServiceAcceptableUsagePolicy
 *  org.apereo.cas.services.RegisteredServiceAcceptableUsagePolicy
 *  org.apereo.cas.services.RegisteredServiceSingleSignOnParticipationPolicy
 *  org.apereo.cas.services.RegisteredServiceWebflowInterruptPolicy
 *  org.apereo.cas.services.WebBasedRegisteredService
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Generated;
import org.apereo.cas.services.BaseRegisteredService;
import org.apereo.cas.services.DefaultRegisteredServiceAcceptableUsagePolicy;
import org.apereo.cas.services.DefaultRegisteredServiceWebflowInterruptPolicy;
import org.apereo.cas.services.RegisteredServiceAcceptableUsagePolicy;
import org.apereo.cas.services.RegisteredServiceSingleSignOnParticipationPolicy;
import org.apereo.cas.services.RegisteredServiceWebflowInterruptPolicy;
import org.apereo.cas.services.WebBasedRegisteredService;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public abstract class BaseWebBasedRegisteredService
extends BaseRegisteredService
implements WebBasedRegisteredService {
    private static final long serialVersionUID = 7766178156998290373L;
    private RegisteredServiceAcceptableUsagePolicy acceptableUsagePolicy = new DefaultRegisteredServiceAcceptableUsagePolicy();
    private RegisteredServiceSingleSignOnParticipationPolicy singleSignOnParticipationPolicy;
    private RegisteredServiceWebflowInterruptPolicy webflowInterruptPolicy = new DefaultRegisteredServiceWebflowInterruptPolicy();

    @Generated
    public RegisteredServiceAcceptableUsagePolicy getAcceptableUsagePolicy() {
        return this.acceptableUsagePolicy;
    }

    @Generated
    public RegisteredServiceSingleSignOnParticipationPolicy getSingleSignOnParticipationPolicy() {
        return this.singleSignOnParticipationPolicy;
    }

    @Generated
    public RegisteredServiceWebflowInterruptPolicy getWebflowInterruptPolicy() {
        return this.webflowInterruptPolicy;
    }

    @Generated
    public void setAcceptableUsagePolicy(RegisteredServiceAcceptableUsagePolicy acceptableUsagePolicy) {
        this.acceptableUsagePolicy = acceptableUsagePolicy;
    }

    @Generated
    public void setSingleSignOnParticipationPolicy(RegisteredServiceSingleSignOnParticipationPolicy singleSignOnParticipationPolicy) {
        this.singleSignOnParticipationPolicy = singleSignOnParticipationPolicy;
    }

    @Generated
    public void setWebflowInterruptPolicy(RegisteredServiceWebflowInterruptPolicy webflowInterruptPolicy) {
        this.webflowInterruptPolicy = webflowInterruptPolicy;
    }

    @Override
    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof BaseWebBasedRegisteredService)) {
            return false;
        }
        BaseWebBasedRegisteredService other = (BaseWebBasedRegisteredService)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        RegisteredServiceAcceptableUsagePolicy this$acceptableUsagePolicy = this.acceptableUsagePolicy;
        RegisteredServiceAcceptableUsagePolicy other$acceptableUsagePolicy = other.acceptableUsagePolicy;
        if (this$acceptableUsagePolicy == null ? other$acceptableUsagePolicy != null : !this$acceptableUsagePolicy.equals(other$acceptableUsagePolicy)) {
            return false;
        }
        RegisteredServiceSingleSignOnParticipationPolicy this$singleSignOnParticipationPolicy = this.singleSignOnParticipationPolicy;
        RegisteredServiceSingleSignOnParticipationPolicy other$singleSignOnParticipationPolicy = other.singleSignOnParticipationPolicy;
        if (this$singleSignOnParticipationPolicy == null ? other$singleSignOnParticipationPolicy != null : !this$singleSignOnParticipationPolicy.equals(other$singleSignOnParticipationPolicy)) {
            return false;
        }
        RegisteredServiceWebflowInterruptPolicy this$webflowInterruptPolicy = this.webflowInterruptPolicy;
        RegisteredServiceWebflowInterruptPolicy other$webflowInterruptPolicy = other.webflowInterruptPolicy;
        return !(this$webflowInterruptPolicy == null ? other$webflowInterruptPolicy != null : !this$webflowInterruptPolicy.equals(other$webflowInterruptPolicy));
    }

    @Override
    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof BaseWebBasedRegisteredService;
    }

    @Override
    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        RegisteredServiceAcceptableUsagePolicy $acceptableUsagePolicy = this.acceptableUsagePolicy;
        result = result * 59 + ($acceptableUsagePolicy == null ? 43 : $acceptableUsagePolicy.hashCode());
        RegisteredServiceSingleSignOnParticipationPolicy $singleSignOnParticipationPolicy = this.singleSignOnParticipationPolicy;
        result = result * 59 + ($singleSignOnParticipationPolicy == null ? 43 : $singleSignOnParticipationPolicy.hashCode());
        RegisteredServiceWebflowInterruptPolicy $webflowInterruptPolicy = this.webflowInterruptPolicy;
        result = result * 59 + ($webflowInterruptPolicy == null ? 43 : $webflowInterruptPolicy.hashCode());
        return result;
    }
}

