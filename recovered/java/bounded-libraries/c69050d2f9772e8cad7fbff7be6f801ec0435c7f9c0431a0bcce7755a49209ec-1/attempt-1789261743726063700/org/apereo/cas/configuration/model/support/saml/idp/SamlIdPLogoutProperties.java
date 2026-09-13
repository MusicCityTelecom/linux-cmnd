/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.saml.idp;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-saml-idp")
@JsonFilter(value="SamlIdPLogoutProperties")
public class SamlIdPLogoutProperties
implements Serializable {
    private static final long serialVersionUID = -4608824149569614549L;
    private String logoutResponseBinding;
    private boolean signLogoutResponse;
    private boolean sendLogoutResponse = true;
    private boolean forceSignedLogoutRequests = true;
    private boolean singleLogoutCallbacksDisabled;

    @Generated
    public String getLogoutResponseBinding() {
        return this.logoutResponseBinding;
    }

    @Generated
    public boolean isSignLogoutResponse() {
        return this.signLogoutResponse;
    }

    @Generated
    public boolean isSendLogoutResponse() {
        return this.sendLogoutResponse;
    }

    @Generated
    public boolean isForceSignedLogoutRequests() {
        return this.forceSignedLogoutRequests;
    }

    @Generated
    public boolean isSingleLogoutCallbacksDisabled() {
        return this.singleLogoutCallbacksDisabled;
    }

    @Generated
    public SamlIdPLogoutProperties setLogoutResponseBinding(String logoutResponseBinding) {
        this.logoutResponseBinding = logoutResponseBinding;
        return this;
    }

    @Generated
    public SamlIdPLogoutProperties setSignLogoutResponse(boolean signLogoutResponse) {
        this.signLogoutResponse = signLogoutResponse;
        return this;
    }

    @Generated
    public SamlIdPLogoutProperties setSendLogoutResponse(boolean sendLogoutResponse) {
        this.sendLogoutResponse = sendLogoutResponse;
        return this;
    }

    @Generated
    public SamlIdPLogoutProperties setForceSignedLogoutRequests(boolean forceSignedLogoutRequests) {
        this.forceSignedLogoutRequests = forceSignedLogoutRequests;
        return this;
    }

    @Generated
    public SamlIdPLogoutProperties setSingleLogoutCallbacksDisabled(boolean singleLogoutCallbacksDisabled) {
        this.singleLogoutCallbacksDisabled = singleLogoutCallbacksDisabled;
        return this;
    }
}

