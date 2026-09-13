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
import org.apereo.cas.configuration.support.ExpressionLanguageCapable;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-oidc")
@JsonFilter(value="FileSystemOidcJsonWebKeystoreProperties")
public class FileSystemOidcJsonWebKeystoreProperties
implements Serializable {
    private static final long serialVersionUID = 1659099897056632658L;
    @RequiredProperty
    @ExpressionLanguageCapable
    private String jwksFile = "file:/etc/cas/config/keystore.jwks";
    private boolean watcherEnabled = true;

    @Generated
    public String getJwksFile() {
        return this.jwksFile;
    }

    @Generated
    public boolean isWatcherEnabled() {
        return this.watcherEnabled;
    }

    @Generated
    public FileSystemOidcJsonWebKeystoreProperties setJwksFile(String jwksFile) {
        this.jwksFile = jwksFile;
        return this;
    }

    @Generated
    public FileSystemOidcJsonWebKeystoreProperties setWatcherEnabled(boolean watcherEnabled) {
        this.watcherEnabled = watcherEnabled;
        return this;
    }
}

