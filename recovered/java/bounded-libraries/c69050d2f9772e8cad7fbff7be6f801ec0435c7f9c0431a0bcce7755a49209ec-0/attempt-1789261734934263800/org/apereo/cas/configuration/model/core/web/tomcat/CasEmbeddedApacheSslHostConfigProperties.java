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
import java.util.ArrayList;
import java.util.List;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.web.tomcat.CasEmbeddedApacheSslHostConfigCertificateProperties;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-webapp-tomcat")
@JsonFilter(value="CasEmbeddedApacheSslHostConfigProperties")
public class CasEmbeddedApacheSslHostConfigProperties
implements Serializable {
    private static final long serialVersionUID = -32143821503580896L;
    @RequiredProperty
    private boolean enabled;
    private boolean revocationEnabled;
    private String caCertificateFile;
    private String certificateVerification = "require";
    private String hostName;
    private String sslProtocol = "TLS";
    private boolean insecureRenegotiation;
    private int certificateVerificationDepth = 10;
    private String protocols = "all";
    private List<CasEmbeddedApacheSslHostConfigCertificateProperties> certificates = new ArrayList<CasEmbeddedApacheSslHostConfigCertificateProperties>();

    @Generated
    public boolean isEnabled() {
        return this.enabled;
    }

    @Generated
    public boolean isRevocationEnabled() {
        return this.revocationEnabled;
    }

    @Generated
    public String getCaCertificateFile() {
        return this.caCertificateFile;
    }

    @Generated
    public String getCertificateVerification() {
        return this.certificateVerification;
    }

    @Generated
    public String getHostName() {
        return this.hostName;
    }

    @Generated
    public String getSslProtocol() {
        return this.sslProtocol;
    }

    @Generated
    public boolean isInsecureRenegotiation() {
        return this.insecureRenegotiation;
    }

    @Generated
    public int getCertificateVerificationDepth() {
        return this.certificateVerificationDepth;
    }

    @Generated
    public String getProtocols() {
        return this.protocols;
    }

    @Generated
    public List<CasEmbeddedApacheSslHostConfigCertificateProperties> getCertificates() {
        return this.certificates;
    }

    @Generated
    public CasEmbeddedApacheSslHostConfigProperties setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    @Generated
    public CasEmbeddedApacheSslHostConfigProperties setRevocationEnabled(boolean revocationEnabled) {
        this.revocationEnabled = revocationEnabled;
        return this;
    }

    @Generated
    public CasEmbeddedApacheSslHostConfigProperties setCaCertificateFile(String caCertificateFile) {
        this.caCertificateFile = caCertificateFile;
        return this;
    }

    @Generated
    public CasEmbeddedApacheSslHostConfigProperties setCertificateVerification(String certificateVerification) {
        this.certificateVerification = certificateVerification;
        return this;
    }

    @Generated
    public CasEmbeddedApacheSslHostConfigProperties setHostName(String hostName) {
        this.hostName = hostName;
        return this;
    }

    @Generated
    public CasEmbeddedApacheSslHostConfigProperties setSslProtocol(String sslProtocol) {
        this.sslProtocol = sslProtocol;
        return this;
    }

    @Generated
    public CasEmbeddedApacheSslHostConfigProperties setInsecureRenegotiation(boolean insecureRenegotiation) {
        this.insecureRenegotiation = insecureRenegotiation;
        return this;
    }

    @Generated
    public CasEmbeddedApacheSslHostConfigProperties setCertificateVerificationDepth(int certificateVerificationDepth) {
        this.certificateVerificationDepth = certificateVerificationDepth;
        return this;
    }

    @Generated
    public CasEmbeddedApacheSslHostConfigProperties setProtocols(String protocols) {
        this.protocols = protocols;
        return this;
    }

    @Generated
    public CasEmbeddedApacheSslHostConfigProperties setCertificates(List<CasEmbeddedApacheSslHostConfigCertificateProperties> certificates) {
        this.certificates = certificates;
        return this;
    }
}

