/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.saml.idp.metadata;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-saml-idp")
@JsonFilter(value="MDQSamlMetadataProperties")
public class MDQSamlMetadataProperties
implements Serializable {
    private static final long serialVersionUID = -1311568960413770598L;
    private String basicAuthnUsername;
    private String basicAuthnPassword;
    private String supportedContentType = "text/xml";

    @Generated
    public String getBasicAuthnUsername() {
        return this.basicAuthnUsername;
    }

    @Generated
    public String getBasicAuthnPassword() {
        return this.basicAuthnPassword;
    }

    @Generated
    public String getSupportedContentType() {
        return this.supportedContentType;
    }

    @Generated
    public MDQSamlMetadataProperties setBasicAuthnUsername(String basicAuthnUsername) {
        this.basicAuthnUsername = basicAuthnUsername;
        return this;
    }

    @Generated
    public MDQSamlMetadataProperties setBasicAuthnPassword(String basicAuthnPassword) {
        this.basicAuthnPassword = basicAuthnPassword;
        return this;
    }

    @Generated
    public MDQSamlMetadataProperties setSupportedContentType(String supportedContentType) {
        this.supportedContentType = supportedContentType;
        return this;
    }
}

