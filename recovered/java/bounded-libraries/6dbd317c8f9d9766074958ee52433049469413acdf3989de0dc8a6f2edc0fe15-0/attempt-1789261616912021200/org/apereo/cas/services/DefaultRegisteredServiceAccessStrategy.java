/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  javax.persistence.PostLoad
 *  lombok.Generated
 *  org.apache.commons.lang3.ObjectUtils
 *  org.apereo.cas.services.RegisteredServiceDelegatedAuthenticationPolicy
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.persistence.PostLoad;
import lombok.Generated;
import org.apache.commons.lang3.ObjectUtils;
import org.apereo.cas.services.BaseRegisteredServiceAccessStrategy;
import org.apereo.cas.services.DefaultRegisteredServiceDelegatedAuthenticationPolicy;
import org.apereo.cas.services.RegisteredServiceDelegatedAuthenticationPolicy;
import org.apereo.cas.services.util.RegisteredServiceAccessStrategyEvaluator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonInclude(value=JsonInclude.Include.NON_DEFAULT)
public class DefaultRegisteredServiceAccessStrategy
extends BaseRegisteredServiceAccessStrategy {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultRegisteredServiceAccessStrategy.class);
    private static final long serialVersionUID = 1245279151345635245L;
    protected int order;
    protected boolean enabled = true;
    protected boolean ssoEnabled = true;
    protected URI unauthorizedRedirectUrl;
    protected RegisteredServiceDelegatedAuthenticationPolicy delegatedAuthenticationPolicy = new DefaultRegisteredServiceDelegatedAuthenticationPolicy();
    protected boolean requireAllAttributes = true;
    protected Map<String, Set<String>> requiredAttributes = new HashMap<String, Set<String>>(0);
    protected Map<String, Set<String>> rejectedAttributes = new HashMap<String, Set<String>>(0);
    protected boolean caseInsensitive;

    public DefaultRegisteredServiceAccessStrategy() {
        this(true, true);
    }

    public DefaultRegisteredServiceAccessStrategy(boolean enabled, boolean ssoEnabled) {
        this.enabled = enabled;
        this.ssoEnabled = ssoEnabled;
    }

    public DefaultRegisteredServiceAccessStrategy(Map<String, Set<String>> requiredAttributes, Map<String, Set<String>> rejectedAttributes) {
        this();
        this.requiredAttributes = (Map)ObjectUtils.defaultIfNull(requiredAttributes, new HashMap(0));
        this.rejectedAttributes = (Map)ObjectUtils.defaultIfNull(rejectedAttributes, new HashMap(0));
    }

    public DefaultRegisteredServiceAccessStrategy(Map<String, Set<String>> requiredAttributes) {
        this();
        this.requiredAttributes = (Map)ObjectUtils.defaultIfNull(requiredAttributes, new HashMap(0));
    }

    @PostLoad
    public void postLoad() {
        this.delegatedAuthenticationPolicy = (RegisteredServiceDelegatedAuthenticationPolicy)ObjectUtils.defaultIfNull((Object)this.delegatedAuthenticationPolicy, (Object)new DefaultRegisteredServiceDelegatedAuthenticationPolicy());
        this.requiredAttributes = (Map)ObjectUtils.defaultIfNull(this.requiredAttributes, new HashMap(0));
        this.rejectedAttributes = (Map)ObjectUtils.defaultIfNull(this.rejectedAttributes, new HashMap(0));
    }

    public Map<String, Set<String>> getRequiredAttributes() {
        return this.requiredAttributes;
    }

    @JsonIgnore
    public boolean isServiceAccessAllowedForSso() {
        if (!this.ssoEnabled) {
            LOGGER.trace("Service is not authorized to participate in SSO.");
            return false;
        }
        return true;
    }

    @JsonIgnore
    public boolean isServiceAccessAllowed() {
        if (!this.enabled) {
            LOGGER.trace("Service is not enabled in service registry.");
            return false;
        }
        return true;
    }

    @JsonIgnore
    public void setServiceAccessAllowed(boolean value) {
        this.enabled = value;
    }

    public boolean doPrincipalAttributesAllowServiceAccess(String principal, Map<String, Object> principalAttributes) {
        return ((RegisteredServiceAccessStrategyEvaluator)((RegisteredServiceAccessStrategyEvaluator.RegisteredServiceAccessStrategyEvaluatorBuilder)((RegisteredServiceAccessStrategyEvaluator.RegisteredServiceAccessStrategyEvaluatorBuilder)((RegisteredServiceAccessStrategyEvaluator.RegisteredServiceAccessStrategyEvaluatorBuilder)((RegisteredServiceAccessStrategyEvaluator.RegisteredServiceAccessStrategyEvaluatorBuilder)RegisteredServiceAccessStrategyEvaluator.builder().caseInsensitive(this.caseInsensitive)).requireAllAttributes(this.requireAllAttributes)).requiredAttributes(this.requiredAttributes)).rejectedAttributes(this.rejectedAttributes)).build()).evaluate(principal, principalAttributes);
    }

    @Override
    @Generated
    public String toString() {
        return "DefaultRegisteredServiceAccessStrategy(order=" + this.order + ", enabled=" + this.enabled + ", ssoEnabled=" + this.ssoEnabled + ", unauthorizedRedirectUrl=" + this.unauthorizedRedirectUrl + ", delegatedAuthenticationPolicy=" + this.delegatedAuthenticationPolicy + ", requireAllAttributes=" + this.requireAllAttributes + ", requiredAttributes=" + this.requiredAttributes + ", rejectedAttributes=" + this.rejectedAttributes + ", caseInsensitive=" + this.caseInsensitive + ")";
    }

    @Generated
    public int getOrder() {
        return this.order;
    }

    @Generated
    public boolean isEnabled() {
        return this.enabled;
    }

    @Generated
    public boolean isSsoEnabled() {
        return this.ssoEnabled;
    }

    @Generated
    public URI getUnauthorizedRedirectUrl() {
        return this.unauthorizedRedirectUrl;
    }

    @Generated
    public RegisteredServiceDelegatedAuthenticationPolicy getDelegatedAuthenticationPolicy() {
        return this.delegatedAuthenticationPolicy;
    }

    @Generated
    public boolean isRequireAllAttributes() {
        return this.requireAllAttributes;
    }

    @Generated
    public Map<String, Set<String>> getRejectedAttributes() {
        return this.rejectedAttributes;
    }

    @Generated
    public boolean isCaseInsensitive() {
        return this.caseInsensitive;
    }

    @Override
    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof DefaultRegisteredServiceAccessStrategy)) {
            return false;
        }
        DefaultRegisteredServiceAccessStrategy other = (DefaultRegisteredServiceAccessStrategy)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        if (this.order != other.order) {
            return false;
        }
        if (this.enabled != other.enabled) {
            return false;
        }
        if (this.ssoEnabled != other.ssoEnabled) {
            return false;
        }
        if (this.requireAllAttributes != other.requireAllAttributes) {
            return false;
        }
        if (this.caseInsensitive != other.caseInsensitive) {
            return false;
        }
        URI this$unauthorizedRedirectUrl = this.unauthorizedRedirectUrl;
        URI other$unauthorizedRedirectUrl = other.unauthorizedRedirectUrl;
        if (this$unauthorizedRedirectUrl == null ? other$unauthorizedRedirectUrl != null : !((Object)this$unauthorizedRedirectUrl).equals(other$unauthorizedRedirectUrl)) {
            return false;
        }
        RegisteredServiceDelegatedAuthenticationPolicy this$delegatedAuthenticationPolicy = this.delegatedAuthenticationPolicy;
        RegisteredServiceDelegatedAuthenticationPolicy other$delegatedAuthenticationPolicy = other.delegatedAuthenticationPolicy;
        if (this$delegatedAuthenticationPolicy == null ? other$delegatedAuthenticationPolicy != null : !this$delegatedAuthenticationPolicy.equals(other$delegatedAuthenticationPolicy)) {
            return false;
        }
        Map<String, Set<String>> this$requiredAttributes = this.requiredAttributes;
        Map<String, Set<String>> other$requiredAttributes = other.requiredAttributes;
        if (this$requiredAttributes == null ? other$requiredAttributes != null : !((Object)this$requiredAttributes).equals(other$requiredAttributes)) {
            return false;
        }
        Map<String, Set<String>> this$rejectedAttributes = this.rejectedAttributes;
        Map<String, Set<String>> other$rejectedAttributes = other.rejectedAttributes;
        return !(this$rejectedAttributes == null ? other$rejectedAttributes != null : !((Object)this$rejectedAttributes).equals(other$rejectedAttributes));
    }

    @Override
    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof DefaultRegisteredServiceAccessStrategy;
    }

    @Override
    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        result = result * 59 + this.order;
        result = result * 59 + (this.enabled ? 79 : 97);
        result = result * 59 + (this.ssoEnabled ? 79 : 97);
        result = result * 59 + (this.requireAllAttributes ? 79 : 97);
        result = result * 59 + (this.caseInsensitive ? 79 : 97);
        URI $unauthorizedRedirectUrl = this.unauthorizedRedirectUrl;
        result = result * 59 + ($unauthorizedRedirectUrl == null ? 43 : ((Object)$unauthorizedRedirectUrl).hashCode());
        RegisteredServiceDelegatedAuthenticationPolicy $delegatedAuthenticationPolicy = this.delegatedAuthenticationPolicy;
        result = result * 59 + ($delegatedAuthenticationPolicy == null ? 43 : $delegatedAuthenticationPolicy.hashCode());
        Map<String, Set<String>> $requiredAttributes = this.requiredAttributes;
        result = result * 59 + ($requiredAttributes == null ? 43 : ((Object)$requiredAttributes).hashCode());
        Map<String, Set<String>> $rejectedAttributes = this.rejectedAttributes;
        result = result * 59 + ($rejectedAttributes == null ? 43 : ((Object)$rejectedAttributes).hashCode());
        return result;
    }

    @Generated
    public DefaultRegisteredServiceAccessStrategy setOrder(int order) {
        this.order = order;
        return this;
    }

    @Generated
    public DefaultRegisteredServiceAccessStrategy setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    @Generated
    public DefaultRegisteredServiceAccessStrategy setSsoEnabled(boolean ssoEnabled) {
        this.ssoEnabled = ssoEnabled;
        return this;
    }

    @Generated
    public DefaultRegisteredServiceAccessStrategy setUnauthorizedRedirectUrl(URI unauthorizedRedirectUrl) {
        this.unauthorizedRedirectUrl = unauthorizedRedirectUrl;
        return this;
    }

    @Generated
    public DefaultRegisteredServiceAccessStrategy setDelegatedAuthenticationPolicy(RegisteredServiceDelegatedAuthenticationPolicy delegatedAuthenticationPolicy) {
        this.delegatedAuthenticationPolicy = delegatedAuthenticationPolicy;
        return this;
    }

    @Generated
    public DefaultRegisteredServiceAccessStrategy setRequireAllAttributes(boolean requireAllAttributes) {
        this.requireAllAttributes = requireAllAttributes;
        return this;
    }

    @Generated
    public DefaultRegisteredServiceAccessStrategy setRequiredAttributes(Map<String, Set<String>> requiredAttributes) {
        this.requiredAttributes = requiredAttributes;
        return this;
    }

    @Generated
    public DefaultRegisteredServiceAccessStrategy setRejectedAttributes(Map<String, Set<String>> rejectedAttributes) {
        this.rejectedAttributes = rejectedAttributes;
        return this;
    }

    @Generated
    public DefaultRegisteredServiceAccessStrategy setCaseInsensitive(boolean caseInsensitive) {
        this.caseInsensitive = caseInsensitive;
        return this;
    }
}

