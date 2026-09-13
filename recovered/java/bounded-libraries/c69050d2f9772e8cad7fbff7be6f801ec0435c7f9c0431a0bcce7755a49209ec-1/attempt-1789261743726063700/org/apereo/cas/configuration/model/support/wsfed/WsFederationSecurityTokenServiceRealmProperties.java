/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.wsfed;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-ws-sts")
@JsonFilter(value="WsFederationSecurityTokenServiceRealmProperties")
public class WsFederationSecurityTokenServiceRealmProperties
implements Serializable {
    private static final long serialVersionUID = -2209230334376432934L;
    @RequiredProperty
    private String keystoreFile;
    @RequiredProperty
    private String keystorePassword;
    private String keystoreAlias;
    @RequiredProperty
    private String keyPassword;
    @RequiredProperty
    private String issuer = "CAS";

    @Generated
    public String getKeystoreFile() {
        return this.keystoreFile;
    }

    @Generated
    public String getKeystorePassword() {
        return this.keystorePassword;
    }

    @Generated
    public String getKeystoreAlias() {
        return this.keystoreAlias;
    }

    @Generated
    public String getKeyPassword() {
        return this.keyPassword;
    }

    @Generated
    public String getIssuer() {
        return this.issuer;
    }

    @Generated
    public WsFederationSecurityTokenServiceRealmProperties setKeystoreFile(String keystoreFile) {
        this.keystoreFile = keystoreFile;
        return this;
    }

    @Generated
    public WsFederationSecurityTokenServiceRealmProperties setKeystorePassword(String keystorePassword) {
        this.keystorePassword = keystorePassword;
        return this;
    }

    @Generated
    public WsFederationSecurityTokenServiceRealmProperties setKeystoreAlias(String keystoreAlias) {
        this.keystoreAlias = keystoreAlias;
        return this;
    }

    @Generated
    public WsFederationSecurityTokenServiceRealmProperties setKeyPassword(String keyPassword) {
        this.keyPassword = keyPassword;
        return this;
    }

    @Generated
    public WsFederationSecurityTokenServiceRealmProperties setIssuer(String issuer) {
        this.issuer = issuer;
        return this;
    }
}

