/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.core.web.tomcat;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.File;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.web.tomcat.CasEmbeddedApacheSslHostConfigProperties;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-webapp-tomcat")
@JsonFilter(value="CasEmbeddedApacheTomcatApachePortableRuntimeProperties")
public class CasEmbeddedApacheTomcatApachePortableRuntimeProperties
implements Serializable {
    private static final long serialVersionUID = 8229851352067677264L;
    @RequiredProperty
    private boolean enabled;
    private String sslProtocol;
    private int sslVerifyDepth = 10;
    private String sslVerifyClient = "require";
    private File sslCaRevocationFile;
    private File sslCertificateChainFile;
    private String sslCipherSuite;
    private boolean sslDisableCompression;
    private boolean sslHonorCipherOrder;
    private String sslPassword;
    private File sslCaCertificateFile;
    private File sslCertificateKeyFile;
    private File sslCertificateFile;
    @NestedConfigurationProperty
    private CasEmbeddedApacheSslHostConfigProperties sslHostConfig = new CasEmbeddedApacheSslHostConfigProperties();

    @Generated
    public boolean isEnabled() {
        return this.enabled;
    }

    @Generated
    public String getSslProtocol() {
        return this.sslProtocol;
    }

    @Generated
    public int getSslVerifyDepth() {
        return this.sslVerifyDepth;
    }

    @Generated
    public String getSslVerifyClient() {
        return this.sslVerifyClient;
    }

    @Generated
    public File getSslCaRevocationFile() {
        return this.sslCaRevocationFile;
    }

    @Generated
    public File getSslCertificateChainFile() {
        return this.sslCertificateChainFile;
    }

    @Generated
    public String getSslCipherSuite() {
        return this.sslCipherSuite;
    }

    @Generated
    public boolean isSslDisableCompression() {
        return this.sslDisableCompression;
    }

    @Generated
    public boolean isSslHonorCipherOrder() {
        return this.sslHonorCipherOrder;
    }

    @Generated
    public String getSslPassword() {
        return this.sslPassword;
    }

    @Generated
    public File getSslCaCertificateFile() {
        return this.sslCaCertificateFile;
    }

    @Generated
    public File getSslCertificateKeyFile() {
        return this.sslCertificateKeyFile;
    }

    @Generated
    public File getSslCertificateFile() {
        return this.sslCertificateFile;
    }

    @Generated
    public CasEmbeddedApacheSslHostConfigProperties getSslHostConfig() {
        return this.sslHostConfig;
    }

    @Generated
    public CasEmbeddedApacheTomcatApachePortableRuntimeProperties setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatApachePortableRuntimeProperties setSslProtocol(String sslProtocol) {
        this.sslProtocol = sslProtocol;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatApachePortableRuntimeProperties setSslVerifyDepth(int sslVerifyDepth) {
        this.sslVerifyDepth = sslVerifyDepth;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatApachePortableRuntimeProperties setSslVerifyClient(String sslVerifyClient) {
        this.sslVerifyClient = sslVerifyClient;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatApachePortableRuntimeProperties setSslCaRevocationFile(File sslCaRevocationFile) {
        this.sslCaRevocationFile = sslCaRevocationFile;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatApachePortableRuntimeProperties setSslCertificateChainFile(File sslCertificateChainFile) {
        this.sslCertificateChainFile = sslCertificateChainFile;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatApachePortableRuntimeProperties setSslCipherSuite(String sslCipherSuite) {
        this.sslCipherSuite = sslCipherSuite;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatApachePortableRuntimeProperties setSslDisableCompression(boolean sslDisableCompression) {
        this.sslDisableCompression = sslDisableCompression;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatApachePortableRuntimeProperties setSslHonorCipherOrder(boolean sslHonorCipherOrder) {
        this.sslHonorCipherOrder = sslHonorCipherOrder;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatApachePortableRuntimeProperties setSslPassword(String sslPassword) {
        this.sslPassword = sslPassword;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatApachePortableRuntimeProperties setSslCaCertificateFile(File sslCaCertificateFile) {
        this.sslCaCertificateFile = sslCaCertificateFile;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatApachePortableRuntimeProperties setSslCertificateKeyFile(File sslCertificateKeyFile) {
        this.sslCertificateKeyFile = sslCertificateKeyFile;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatApachePortableRuntimeProperties setSslCertificateFile(File sslCertificateFile) {
        this.sslCertificateFile = sslCertificateFile;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatApachePortableRuntimeProperties setSslHostConfig(CasEmbeddedApacheSslHostConfigProperties sslHostConfig) {
        this.sslHostConfig = sslHostConfig;
        return this;
    }
}

