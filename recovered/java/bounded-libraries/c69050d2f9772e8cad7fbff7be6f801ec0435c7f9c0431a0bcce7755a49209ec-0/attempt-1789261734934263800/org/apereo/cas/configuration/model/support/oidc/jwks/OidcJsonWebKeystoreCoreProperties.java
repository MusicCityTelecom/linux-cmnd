/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.oidc.jwks;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.DurationCapable;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-oidc")
@JsonFilter(value="OidcJsonWebKeystoreCoreProperties")
public class OidcJsonWebKeystoreCoreProperties
implements Serializable {
    private static final long serialVersionUID = -2696060572027445151L;
    @DurationCapable
    private String jwksCacheExpiration = "PT60M";
    private int jwksKeySize = 2048;
    private String jwksType = "RSA";
    private String jwksKeyId = "cas";

    @Generated
    public String getJwksCacheExpiration() {
        return this.jwksCacheExpiration;
    }

    @Generated
    public int getJwksKeySize() {
        return this.jwksKeySize;
    }

    @Generated
    public String getJwksType() {
        return this.jwksType;
    }

    @Generated
    public String getJwksKeyId() {
        return this.jwksKeyId;
    }

    @Generated
    public OidcJsonWebKeystoreCoreProperties setJwksCacheExpiration(String jwksCacheExpiration) {
        this.jwksCacheExpiration = jwksCacheExpiration;
        return this;
    }

    @Generated
    public OidcJsonWebKeystoreCoreProperties setJwksKeySize(int jwksKeySize) {
        this.jwksKeySize = jwksKeySize;
        return this;
    }

    @Generated
    public OidcJsonWebKeystoreCoreProperties setJwksType(String jwksType) {
        this.jwksType = jwksType;
        return this;
    }

    @Generated
    public OidcJsonWebKeystoreCoreProperties setJwksKeyId(String jwksKeyId) {
        this.jwksKeyId = jwksKeyId;
        return this;
    }
}

