/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.saml.idp;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.saml.idp.SamlIdPAlgorithmsProperties;
import org.apereo.cas.configuration.model.support.saml.idp.SamlIdPCoreProperties;
import org.apereo.cas.configuration.model.support.saml.idp.SamlIdPLogoutProperties;
import org.apereo.cas.configuration.model.support.saml.idp.SamlIdPResponseProperties;
import org.apereo.cas.configuration.model.support.saml.idp.SamlIdPServicesProperties;
import org.apereo.cas.configuration.model.support.saml.idp.SamlIdPTicketProperties;
import org.apereo.cas.configuration.model.support.saml.idp.metadata.SamlIdPMetadataProperties;
import org.apereo.cas.configuration.model.support.saml.idp.profile.SamlIdPProfileProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-saml-idp")
@JsonFilter(value="SamlIdPProperties")
public class SamlIdPProperties
implements Serializable {
    private static final long serialVersionUID = -5848075783676789852L;
    @NestedConfigurationProperty
    private SamlIdPCoreProperties core = new SamlIdPCoreProperties();
    @NestedConfigurationProperty
    private SamlIdPResponseProperties response = new SamlIdPResponseProperties();
    @NestedConfigurationProperty
    private SamlIdPMetadataProperties metadata = new SamlIdPMetadataProperties();
    @NestedConfigurationProperty
    private SamlIdPLogoutProperties logout = new SamlIdPLogoutProperties();
    @NestedConfigurationProperty
    private SamlIdPAlgorithmsProperties algs = new SamlIdPAlgorithmsProperties();
    @NestedConfigurationProperty
    private SamlIdPTicketProperties ticket = new SamlIdPTicketProperties();
    @NestedConfigurationProperty
    private SamlIdPProfileProperties profile = new SamlIdPProfileProperties();
    @NestedConfigurationProperty
    private SamlIdPServicesProperties services = new SamlIdPServicesProperties();

    @Generated
    public SamlIdPCoreProperties getCore() {
        return this.core;
    }

    @Generated
    public SamlIdPResponseProperties getResponse() {
        return this.response;
    }

    @Generated
    public SamlIdPMetadataProperties getMetadata() {
        return this.metadata;
    }

    @Generated
    public SamlIdPLogoutProperties getLogout() {
        return this.logout;
    }

    @Generated
    public SamlIdPAlgorithmsProperties getAlgs() {
        return this.algs;
    }

    @Generated
    public SamlIdPTicketProperties getTicket() {
        return this.ticket;
    }

    @Generated
    public SamlIdPProfileProperties getProfile() {
        return this.profile;
    }

    @Generated
    public SamlIdPServicesProperties getServices() {
        return this.services;
    }

    @Generated
    public SamlIdPProperties setCore(SamlIdPCoreProperties core) {
        this.core = core;
        return this;
    }

    @Generated
    public SamlIdPProperties setResponse(SamlIdPResponseProperties response) {
        this.response = response;
        return this;
    }

    @Generated
    public SamlIdPProperties setMetadata(SamlIdPMetadataProperties metadata) {
        this.metadata = metadata;
        return this;
    }

    @Generated
    public SamlIdPProperties setLogout(SamlIdPLogoutProperties logout) {
        this.logout = logout;
        return this;
    }

    @Generated
    public SamlIdPProperties setAlgs(SamlIdPAlgorithmsProperties algs) {
        this.algs = algs;
        return this;
    }

    @Generated
    public SamlIdPProperties setTicket(SamlIdPTicketProperties ticket) {
        this.ticket = ticket;
        return this;
    }

    @Generated
    public SamlIdPProperties setProfile(SamlIdPProfileProperties profile) {
        this.profile = profile;
        return this;
    }

    @Generated
    public SamlIdPProperties setServices(SamlIdPServicesProperties services) {
        this.services = services;
        return this;
    }
}

