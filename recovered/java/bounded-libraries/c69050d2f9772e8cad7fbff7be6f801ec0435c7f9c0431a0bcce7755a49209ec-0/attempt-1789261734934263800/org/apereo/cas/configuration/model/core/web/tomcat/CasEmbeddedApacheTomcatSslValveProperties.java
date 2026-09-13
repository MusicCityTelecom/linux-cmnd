/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.core.web.tomcat;

import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-webapp-tomcat")
public class CasEmbeddedApacheTomcatSslValveProperties
implements Serializable {
    private static final long serialVersionUID = 3164446071136700242L;
    @RequiredProperty
    private boolean enabled;
    private String sslClientCertHeader = "ssl_client_cert";
    private String sslCipherHeader = "ssl_cipher";
    private String sslSessionIdHeader = "ssl_session_id";
    private String sslCipherUserKeySizeHeader = "ssl_cipher_usekeysize";

    @Generated
    public boolean isEnabled() {
        return this.enabled;
    }

    @Generated
    public String getSslClientCertHeader() {
        return this.sslClientCertHeader;
    }

    @Generated
    public String getSslCipherHeader() {
        return this.sslCipherHeader;
    }

    @Generated
    public String getSslSessionIdHeader() {
        return this.sslSessionIdHeader;
    }

    @Generated
    public String getSslCipherUserKeySizeHeader() {
        return this.sslCipherUserKeySizeHeader;
    }

    @Generated
    public CasEmbeddedApacheTomcatSslValveProperties setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatSslValveProperties setSslClientCertHeader(String sslClientCertHeader) {
        this.sslClientCertHeader = sslClientCertHeader;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatSslValveProperties setSslCipherHeader(String sslCipherHeader) {
        this.sslCipherHeader = sslCipherHeader;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatSslValveProperties setSslSessionIdHeader(String sslSessionIdHeader) {
        this.sslSessionIdHeader = sslSessionIdHeader;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatSslValveProperties setSslCipherUserKeySizeHeader(String sslCipherUserKeySizeHeader) {
        this.sslCipherUserKeySizeHeader = sslCipherUserKeySizeHeader;
        return this;
    }
}

