/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  lombok.Generated
 *  org.apache.commons.lang3.ObjectUtils
 *  org.apereo.cas.services.CasModelRegisteredService
 *  org.apereo.cas.services.RegisteredServiceProxyGrantingTicketExpirationPolicy
 *  org.apereo.cas.services.RegisteredServiceProxyPolicy
 *  org.apereo.cas.services.RegisteredServiceProxyTicketExpirationPolicy
 *  org.apereo.cas.services.RegisteredServiceServiceTicketExpirationPolicy
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.util.LinkedHashSet;
import java.util.Set;
import lombok.Generated;
import org.apache.commons.lang3.ObjectUtils;
import org.apereo.cas.services.BaseWebBasedRegisteredService;
import org.apereo.cas.services.CasModelRegisteredService;
import org.apereo.cas.services.RefuseRegisteredServiceProxyPolicy;
import org.apereo.cas.services.RegisteredServiceProxyGrantingTicketExpirationPolicy;
import org.apereo.cas.services.RegisteredServiceProxyPolicy;
import org.apereo.cas.services.RegisteredServiceProxyTicketExpirationPolicy;
import org.apereo.cas.services.RegisteredServiceServiceTicketExpirationPolicy;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public class CasRegisteredService
extends BaseWebBasedRegisteredService
implements CasModelRegisteredService {
    public static final String FRIENDLY_NAME = "CAS Client";
    private static final long serialVersionUID = -2416680749378661897L;
    private RegisteredServiceProxyPolicy proxyPolicy = new RefuseRegisteredServiceProxyPolicy();
    private RegisteredServiceProxyTicketExpirationPolicy proxyTicketExpirationPolicy;
    private RegisteredServiceProxyGrantingTicketExpirationPolicy proxyGrantingTicketExpirationPolicy;
    private RegisteredServiceServiceTicketExpirationPolicy serviceTicketExpirationPolicy;
    private String redirectUrl;
    private Set<String> supportedProtocols = new LinkedHashSet<String>(0);

    @JsonIgnore
    public String getFriendlyName() {
        return FRIENDLY_NAME;
    }

    public void initialize() {
        super.initialize();
        this.proxyPolicy = (RegisteredServiceProxyPolicy)ObjectUtils.defaultIfNull((Object)this.proxyPolicy, (Object)new RefuseRegisteredServiceProxyPolicy());
    }

    @Override
    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof CasRegisteredService)) {
            return false;
        }
        CasRegisteredService other = (CasRegisteredService)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        RegisteredServiceProxyPolicy this$proxyPolicy = this.proxyPolicy;
        RegisteredServiceProxyPolicy other$proxyPolicy = other.proxyPolicy;
        if (this$proxyPolicy == null ? other$proxyPolicy != null : !this$proxyPolicy.equals(other$proxyPolicy)) {
            return false;
        }
        RegisteredServiceProxyTicketExpirationPolicy this$proxyTicketExpirationPolicy = this.proxyTicketExpirationPolicy;
        RegisteredServiceProxyTicketExpirationPolicy other$proxyTicketExpirationPolicy = other.proxyTicketExpirationPolicy;
        if (this$proxyTicketExpirationPolicy == null ? other$proxyTicketExpirationPolicy != null : !this$proxyTicketExpirationPolicy.equals(other$proxyTicketExpirationPolicy)) {
            return false;
        }
        RegisteredServiceProxyGrantingTicketExpirationPolicy this$proxyGrantingTicketExpirationPolicy = this.proxyGrantingTicketExpirationPolicy;
        RegisteredServiceProxyGrantingTicketExpirationPolicy other$proxyGrantingTicketExpirationPolicy = other.proxyGrantingTicketExpirationPolicy;
        if (this$proxyGrantingTicketExpirationPolicy == null ? other$proxyGrantingTicketExpirationPolicy != null : !this$proxyGrantingTicketExpirationPolicy.equals(other$proxyGrantingTicketExpirationPolicy)) {
            return false;
        }
        RegisteredServiceServiceTicketExpirationPolicy this$serviceTicketExpirationPolicy = this.serviceTicketExpirationPolicy;
        RegisteredServiceServiceTicketExpirationPolicy other$serviceTicketExpirationPolicy = other.serviceTicketExpirationPolicy;
        if (this$serviceTicketExpirationPolicy == null ? other$serviceTicketExpirationPolicy != null : !this$serviceTicketExpirationPolicy.equals(other$serviceTicketExpirationPolicy)) {
            return false;
        }
        String this$redirectUrl = this.redirectUrl;
        String other$redirectUrl = other.redirectUrl;
        if (this$redirectUrl == null ? other$redirectUrl != null : !this$redirectUrl.equals(other$redirectUrl)) {
            return false;
        }
        Set<String> this$supportedProtocols = this.supportedProtocols;
        Set<String> other$supportedProtocols = other.supportedProtocols;
        return !(this$supportedProtocols == null ? other$supportedProtocols != null : !((Object)this$supportedProtocols).equals(other$supportedProtocols));
    }

    @Override
    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof CasRegisteredService;
    }

    @Override
    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        RegisteredServiceProxyPolicy $proxyPolicy = this.proxyPolicy;
        result = result * 59 + ($proxyPolicy == null ? 43 : $proxyPolicy.hashCode());
        RegisteredServiceProxyTicketExpirationPolicy $proxyTicketExpirationPolicy = this.proxyTicketExpirationPolicy;
        result = result * 59 + ($proxyTicketExpirationPolicy == null ? 43 : $proxyTicketExpirationPolicy.hashCode());
        RegisteredServiceProxyGrantingTicketExpirationPolicy $proxyGrantingTicketExpirationPolicy = this.proxyGrantingTicketExpirationPolicy;
        result = result * 59 + ($proxyGrantingTicketExpirationPolicy == null ? 43 : $proxyGrantingTicketExpirationPolicy.hashCode());
        RegisteredServiceServiceTicketExpirationPolicy $serviceTicketExpirationPolicy = this.serviceTicketExpirationPolicy;
        result = result * 59 + ($serviceTicketExpirationPolicy == null ? 43 : $serviceTicketExpirationPolicy.hashCode());
        String $redirectUrl = this.redirectUrl;
        result = result * 59 + ($redirectUrl == null ? 43 : $redirectUrl.hashCode());
        Set<String> $supportedProtocols = this.supportedProtocols;
        result = result * 59 + ($supportedProtocols == null ? 43 : ((Object)$supportedProtocols).hashCode());
        return result;
    }

    @Generated
    public RegisteredServiceProxyPolicy getProxyPolicy() {
        return this.proxyPolicy;
    }

    @Generated
    public RegisteredServiceProxyTicketExpirationPolicy getProxyTicketExpirationPolicy() {
        return this.proxyTicketExpirationPolicy;
    }

    @Generated
    public RegisteredServiceProxyGrantingTicketExpirationPolicy getProxyGrantingTicketExpirationPolicy() {
        return this.proxyGrantingTicketExpirationPolicy;
    }

    @Generated
    public RegisteredServiceServiceTicketExpirationPolicy getServiceTicketExpirationPolicy() {
        return this.serviceTicketExpirationPolicy;
    }

    @Generated
    public String getRedirectUrl() {
        return this.redirectUrl;
    }

    @Generated
    public Set<String> getSupportedProtocols() {
        return this.supportedProtocols;
    }

    @Generated
    public void setProxyPolicy(RegisteredServiceProxyPolicy proxyPolicy) {
        this.proxyPolicy = proxyPolicy;
    }

    @Generated
    public void setProxyTicketExpirationPolicy(RegisteredServiceProxyTicketExpirationPolicy proxyTicketExpirationPolicy) {
        this.proxyTicketExpirationPolicy = proxyTicketExpirationPolicy;
    }

    @Generated
    public void setProxyGrantingTicketExpirationPolicy(RegisteredServiceProxyGrantingTicketExpirationPolicy proxyGrantingTicketExpirationPolicy) {
        this.proxyGrantingTicketExpirationPolicy = proxyGrantingTicketExpirationPolicy;
    }

    @Generated
    public void setServiceTicketExpirationPolicy(RegisteredServiceServiceTicketExpirationPolicy serviceTicketExpirationPolicy) {
        this.serviceTicketExpirationPolicy = serviceTicketExpirationPolicy;
    }

    @Generated
    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    @Generated
    public void setSupportedProtocols(Set<String> supportedProtocols) {
        this.supportedProtocols = supportedProtocols;
    }
}

