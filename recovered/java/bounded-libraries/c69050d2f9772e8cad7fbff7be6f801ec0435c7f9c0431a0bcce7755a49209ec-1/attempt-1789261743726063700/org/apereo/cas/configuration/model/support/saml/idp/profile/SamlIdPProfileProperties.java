/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.saml.idp.profile;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.saml.idp.profile.SamlIdPSLOProfileProperties;
import org.apereo.cas.configuration.model.support.saml.idp.profile.SamlIdPSSOProfileProperties;
import org.apereo.cas.configuration.model.support.saml.idp.profile.SamlIdPSSOSimpleSignProfileProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-saml-idp")
@JsonFilter(value="SamlIdPProfileProperties")
public class SamlIdPProfileProperties
implements Serializable {
    private static final long serialVersionUID = -3218075783676789852L;
    @NestedConfigurationProperty
    private SamlIdPSSOProfileProperties sso = new SamlIdPSSOProfileProperties();
    @NestedConfigurationProperty
    private SamlIdPSSOSimpleSignProfileProperties ssoPostSimpleSign = new SamlIdPSSOSimpleSignProfileProperties();
    @NestedConfigurationProperty
    private SamlIdPSLOProfileProperties slo = new SamlIdPSLOProfileProperties();

    @Generated
    public SamlIdPSSOProfileProperties getSso() {
        return this.sso;
    }

    @Generated
    public SamlIdPSSOSimpleSignProfileProperties getSsoPostSimpleSign() {
        return this.ssoPostSimpleSign;
    }

    @Generated
    public SamlIdPSLOProfileProperties getSlo() {
        return this.slo;
    }

    @Generated
    public SamlIdPProfileProperties setSso(SamlIdPSSOProfileProperties sso) {
        this.sso = sso;
        return this;
    }

    @Generated
    public SamlIdPProfileProperties setSsoPostSimpleSign(SamlIdPSSOSimpleSignProfileProperties ssoPostSimpleSign) {
        this.ssoPostSimpleSign = ssoPostSimpleSign;
        return this;
    }

    @Generated
    public SamlIdPProfileProperties setSlo(SamlIdPSLOProfileProperties slo) {
        this.slo = slo;
        return this;
    }
}

