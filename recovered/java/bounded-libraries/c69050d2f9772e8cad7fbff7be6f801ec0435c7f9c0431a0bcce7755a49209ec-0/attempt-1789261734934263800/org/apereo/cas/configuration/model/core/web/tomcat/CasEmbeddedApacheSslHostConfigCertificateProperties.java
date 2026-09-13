/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.core.web.tomcat;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-webapp-tomcat")
@JsonFilter(value="CasEmbeddedApacheSslHostConfigCertificateProperties")
public class CasEmbeddedApacheSslHostConfigCertificateProperties
implements Serializable {
    private static final long serialVersionUID = -5412170529081298822L;
    @RequiredProperty
    private String certificateFile;
    @RequiredProperty
    private String certificateKeyFile;
    @RequiredProperty
    private String certificateKeyPassword;
    @RequiredProperty
    private String certificateChainFile;
    private String type = "UNDEFINED";

    @Generated
    public String getCertificateFile() {
        return this.certificateFile;
    }

    @Generated
    public String getCertificateKeyFile() {
        return this.certificateKeyFile;
    }

    @Generated
    public String getCertificateKeyPassword() {
        return this.certificateKeyPassword;
    }

    @Generated
    public String getCertificateChainFile() {
        return this.certificateChainFile;
    }

    @Generated
    public String getType() {
        return this.type;
    }

    @Generated
    public CasEmbeddedApacheSslHostConfigCertificateProperties setCertificateFile(String certificateFile) {
        this.certificateFile = certificateFile;
        return this;
    }

    @Generated
    public CasEmbeddedApacheSslHostConfigCertificateProperties setCertificateKeyFile(String certificateKeyFile) {
        this.certificateKeyFile = certificateKeyFile;
        return this;
    }

    @Generated
    public CasEmbeddedApacheSslHostConfigCertificateProperties setCertificateKeyPassword(String certificateKeyPassword) {
        this.certificateKeyPassword = certificateKeyPassword;
        return this;
    }

    @Generated
    public CasEmbeddedApacheSslHostConfigCertificateProperties setCertificateChainFile(String certificateChainFile) {
        this.certificateChainFile = certificateChainFile;
        return this;
    }

    @Generated
    public CasEmbeddedApacheSslHostConfigCertificateProperties setType(String type) {
        this.type = type;
        return this;
    }
}

