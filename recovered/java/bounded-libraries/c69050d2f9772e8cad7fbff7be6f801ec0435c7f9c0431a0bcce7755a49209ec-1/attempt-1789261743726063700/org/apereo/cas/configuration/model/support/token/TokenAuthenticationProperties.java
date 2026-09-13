/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.token;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.authentication.AuthenticationHandlerStates;
import org.apereo.cas.configuration.model.core.authentication.PrincipalTransformationProperties;
import org.apereo.cas.configuration.model.core.util.EncryptionOptionalSigningOptionalJwtCryptographyProperties;
import org.apereo.cas.configuration.model.core.web.flow.WebflowAutoConfigurationProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-token-webflow")
@JsonFilter(value="TokenAuthenticationProperties")
public class TokenAuthenticationProperties
implements Serializable {
    private static final long serialVersionUID = 6016124091895278265L;
    @NestedConfigurationProperty
    private PrincipalTransformationProperties principalTransformation = new PrincipalTransformationProperties();
    @NestedConfigurationProperty
    private EncryptionOptionalSigningOptionalJwtCryptographyProperties crypto = new EncryptionOptionalSigningOptionalJwtCryptographyProperties();
    @NestedConfigurationProperty
    private WebflowAutoConfigurationProperties webflow = new WebflowAutoConfigurationProperties().setOrder(100);
    private String name;
    private AuthenticationHandlerStates state = AuthenticationHandlerStates.ACTIVE;

    public TokenAuthenticationProperties() {
        this.crypto.getEncryption().setKeySize(256);
        this.crypto.getSigning().setKeySize(512);
    }

    @Generated
    public PrincipalTransformationProperties getPrincipalTransformation() {
        return this.principalTransformation;
    }

    @Generated
    public EncryptionOptionalSigningOptionalJwtCryptographyProperties getCrypto() {
        return this.crypto;
    }

    @Generated
    public WebflowAutoConfigurationProperties getWebflow() {
        return this.webflow;
    }

    @Generated
    public String getName() {
        return this.name;
    }

    @Generated
    public AuthenticationHandlerStates getState() {
        return this.state;
    }

    @Generated
    public TokenAuthenticationProperties setPrincipalTransformation(PrincipalTransformationProperties principalTransformation) {
        this.principalTransformation = principalTransformation;
        return this;
    }

    @Generated
    public TokenAuthenticationProperties setCrypto(EncryptionOptionalSigningOptionalJwtCryptographyProperties crypto) {
        this.crypto = crypto;
        return this;
    }

    @Generated
    public TokenAuthenticationProperties setWebflow(WebflowAutoConfigurationProperties webflow) {
        this.webflow = webflow;
        return this;
    }

    @Generated
    public TokenAuthenticationProperties setName(String name) {
        this.name = name;
        return this;
    }

    @Generated
    public TokenAuthenticationProperties setState(AuthenticationHandlerStates state) {
        this.state = state;
        return this;
    }
}

