/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.mfa;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.mfa.AuthenticationAttributeMultifactorAuthenticationProperties;
import org.apereo.cas.configuration.model.support.mfa.GlobalMultifactorAuthenticationProperties;
import org.apereo.cas.configuration.model.support.mfa.GrouperMultifactorAuthenticationProperties;
import org.apereo.cas.configuration.model.support.mfa.MultifactorAuthenticationHttpTriggerProperties;
import org.apereo.cas.configuration.model.support.mfa.PrincipalAttributeMultifactorAuthenticationProperties;
import org.apereo.cas.configuration.model.support.mfa.RestfulMultifactorAuthenticationProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-core-authentication", automated=true)
@JsonFilter(value="MultifactorAuthenticationTriggersProperties")
public class MultifactorAuthenticationTriggersProperties
implements Serializable {
    private static final long serialVersionUID = 7410521468929733907L;
    @NestedConfigurationProperty
    private MultifactorAuthenticationHttpTriggerProperties http = new MultifactorAuthenticationHttpTriggerProperties();
    @NestedConfigurationProperty
    private RestfulMultifactorAuthenticationProperties rest = new RestfulMultifactorAuthenticationProperties();
    @NestedConfigurationProperty
    private PrincipalAttributeMultifactorAuthenticationProperties principal = new PrincipalAttributeMultifactorAuthenticationProperties();
    @NestedConfigurationProperty
    private AuthenticationAttributeMultifactorAuthenticationProperties authentication = new AuthenticationAttributeMultifactorAuthenticationProperties();
    @NestedConfigurationProperty
    private GrouperMultifactorAuthenticationProperties grouper = new GrouperMultifactorAuthenticationProperties();
    @NestedConfigurationProperty
    private GlobalMultifactorAuthenticationProperties global = new GlobalMultifactorAuthenticationProperties();

    @Generated
    public MultifactorAuthenticationHttpTriggerProperties getHttp() {
        return this.http;
    }

    @Generated
    public RestfulMultifactorAuthenticationProperties getRest() {
        return this.rest;
    }

    @Generated
    public PrincipalAttributeMultifactorAuthenticationProperties getPrincipal() {
        return this.principal;
    }

    @Generated
    public AuthenticationAttributeMultifactorAuthenticationProperties getAuthentication() {
        return this.authentication;
    }

    @Generated
    public GrouperMultifactorAuthenticationProperties getGrouper() {
        return this.grouper;
    }

    @Generated
    public GlobalMultifactorAuthenticationProperties getGlobal() {
        return this.global;
    }

    @Generated
    public MultifactorAuthenticationTriggersProperties setHttp(MultifactorAuthenticationHttpTriggerProperties http) {
        this.http = http;
        return this;
    }

    @Generated
    public MultifactorAuthenticationTriggersProperties setRest(RestfulMultifactorAuthenticationProperties rest) {
        this.rest = rest;
        return this;
    }

    @Generated
    public MultifactorAuthenticationTriggersProperties setPrincipal(PrincipalAttributeMultifactorAuthenticationProperties principal) {
        this.principal = principal;
        return this;
    }

    @Generated
    public MultifactorAuthenticationTriggersProperties setAuthentication(AuthenticationAttributeMultifactorAuthenticationProperties authentication) {
        this.authentication = authentication;
        return this;
    }

    @Generated
    public MultifactorAuthenticationTriggersProperties setGrouper(GrouperMultifactorAuthenticationProperties grouper) {
        this.grouper = grouper;
        return this;
    }

    @Generated
    public MultifactorAuthenticationTriggersProperties setGlobal(GlobalMultifactorAuthenticationProperties global) {
        this.global = global;
        return this;
    }
}

