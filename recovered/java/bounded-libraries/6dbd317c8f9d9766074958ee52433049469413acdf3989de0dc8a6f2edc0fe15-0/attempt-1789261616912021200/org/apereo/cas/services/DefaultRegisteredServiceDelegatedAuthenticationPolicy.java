/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  lombok.Generated
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.services.RegisteredServiceDelegatedAuthenticationPolicy
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Collection;
import java.util.LinkedHashSet;
import lombok.Generated;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.RegisteredServiceDelegatedAuthenticationPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonInclude(value=JsonInclude.Include.NON_DEFAULT)
public class DefaultRegisteredServiceDelegatedAuthenticationPolicy
implements RegisteredServiceDelegatedAuthenticationPolicy {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultRegisteredServiceDelegatedAuthenticationPolicy.class);
    private static final long serialVersionUID = -784106970642770923L;
    private Collection<String> allowedProviders = new LinkedHashSet<String>(0);
    private boolean permitUndefined = true;
    private boolean exclusive;
    private String selectionStrategy;

    @JsonIgnore
    public boolean isProviderAllowed(String provider, RegisteredService registeredService) {
        if (this.getAllowedProviders() == null || this.getAllowedProviders().isEmpty()) {
            LOGGER.warn("Registered service [{}] does not define any authorized/supported delegated authentication providers. It is STRONGLY recommended that you authorize and assign providers to the service definition. While just a warning for now, this behavior will be enforced by CAS in future versions.", (Object)registeredService.getName());
            return this.permitUndefined;
        }
        return this.getAllowedProviders().contains(provider);
    }

    @Generated
    public String toString() {
        return "DefaultRegisteredServiceDelegatedAuthenticationPolicy(allowedProviders=" + this.allowedProviders + ", permitUndefined=" + this.permitUndefined + ", exclusive=" + this.exclusive + ", selectionStrategy=" + this.selectionStrategy + ")";
    }

    @Generated
    public Collection<String> getAllowedProviders() {
        return this.allowedProviders;
    }

    @Generated
    public boolean isPermitUndefined() {
        return this.permitUndefined;
    }

    @Generated
    public boolean isExclusive() {
        return this.exclusive;
    }

    @Generated
    public String getSelectionStrategy() {
        return this.selectionStrategy;
    }

    @Generated
    public DefaultRegisteredServiceDelegatedAuthenticationPolicy setAllowedProviders(Collection<String> allowedProviders) {
        this.allowedProviders = allowedProviders;
        return this;
    }

    @Generated
    public DefaultRegisteredServiceDelegatedAuthenticationPolicy setPermitUndefined(boolean permitUndefined) {
        this.permitUndefined = permitUndefined;
        return this;
    }

    @Generated
    public DefaultRegisteredServiceDelegatedAuthenticationPolicy setExclusive(boolean exclusive) {
        this.exclusive = exclusive;
        return this;
    }

    @Generated
    public DefaultRegisteredServiceDelegatedAuthenticationPolicy setSelectionStrategy(String selectionStrategy) {
        this.selectionStrategy = selectionStrategy;
        return this;
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof DefaultRegisteredServiceDelegatedAuthenticationPolicy)) {
            return false;
        }
        DefaultRegisteredServiceDelegatedAuthenticationPolicy other = (DefaultRegisteredServiceDelegatedAuthenticationPolicy)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.permitUndefined != other.permitUndefined) {
            return false;
        }
        if (this.exclusive != other.exclusive) {
            return false;
        }
        Collection<String> this$allowedProviders = this.allowedProviders;
        Collection<String> other$allowedProviders = other.allowedProviders;
        if (this$allowedProviders == null ? other$allowedProviders != null : !((Object)this$allowedProviders).equals(other$allowedProviders)) {
            return false;
        }
        String this$selectionStrategy = this.selectionStrategy;
        String other$selectionStrategy = other.selectionStrategy;
        return !(this$selectionStrategy == null ? other$selectionStrategy != null : !this$selectionStrategy.equals(other$selectionStrategy));
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof DefaultRegisteredServiceDelegatedAuthenticationPolicy;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + (this.permitUndefined ? 79 : 97);
        result = result * 59 + (this.exclusive ? 79 : 97);
        Collection<String> $allowedProviders = this.allowedProviders;
        result = result * 59 + ($allowedProviders == null ? 43 : ((Object)$allowedProviders).hashCode());
        String $selectionStrategy = this.selectionStrategy;
        result = result * 59 + ($selectionStrategy == null ? 43 : $selectionStrategy.hashCode());
        return result;
    }

    @Generated
    public DefaultRegisteredServiceDelegatedAuthenticationPolicy(Collection<String> allowedProviders, boolean permitUndefined, boolean exclusive, String selectionStrategy) {
        this.allowedProviders = allowedProviders;
        this.permitUndefined = permitUndefined;
        this.exclusive = exclusive;
        this.selectionStrategy = selectionStrategy;
    }

    @Generated
    public DefaultRegisteredServiceDelegatedAuthenticationPolicy() {
    }
}

