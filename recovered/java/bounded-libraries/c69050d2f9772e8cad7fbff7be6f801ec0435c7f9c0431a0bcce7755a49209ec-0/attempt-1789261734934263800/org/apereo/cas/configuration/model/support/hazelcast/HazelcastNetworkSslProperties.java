/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.hazelcast;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-hazelcast-core")
@JsonFilter(value="HazelcastNetworkSslProperties")
public class HazelcastNetworkSslProperties
implements Serializable {
    private static final long serialVersionUID = -2444780336835699053L;
    private String protocol = "TLS";
    private String keystore;
    private String keystorePassword;
    private String keyStoreType = "JKS";
    private String trustStore;
    private String trustStoreType = "JKS";
    private String trustStorePassword;
    private String mutualAuthentication;
    private String cipherSuites;
    private String trustManagerAlgorithm;
    private String keyManagerAlgorithm;
    private boolean validateIdentity;

    @Generated
    public String getProtocol() {
        return this.protocol;
    }

    @Generated
    public String getKeystore() {
        return this.keystore;
    }

    @Generated
    public String getKeystorePassword() {
        return this.keystorePassword;
    }

    @Generated
    public String getKeyStoreType() {
        return this.keyStoreType;
    }

    @Generated
    public String getTrustStore() {
        return this.trustStore;
    }

    @Generated
    public String getTrustStoreType() {
        return this.trustStoreType;
    }

    @Generated
    public String getTrustStorePassword() {
        return this.trustStorePassword;
    }

    @Generated
    public String getMutualAuthentication() {
        return this.mutualAuthentication;
    }

    @Generated
    public String getCipherSuites() {
        return this.cipherSuites;
    }

    @Generated
    public String getTrustManagerAlgorithm() {
        return this.trustManagerAlgorithm;
    }

    @Generated
    public String getKeyManagerAlgorithm() {
        return this.keyManagerAlgorithm;
    }

    @Generated
    public boolean isValidateIdentity() {
        return this.validateIdentity;
    }

    @Generated
    public HazelcastNetworkSslProperties setProtocol(String protocol) {
        this.protocol = protocol;
        return this;
    }

    @Generated
    public HazelcastNetworkSslProperties setKeystore(String keystore) {
        this.keystore = keystore;
        return this;
    }

    @Generated
    public HazelcastNetworkSslProperties setKeystorePassword(String keystorePassword) {
        this.keystorePassword = keystorePassword;
        return this;
    }

    @Generated
    public HazelcastNetworkSslProperties setKeyStoreType(String keyStoreType) {
        this.keyStoreType = keyStoreType;
        return this;
    }

    @Generated
    public HazelcastNetworkSslProperties setTrustStore(String trustStore) {
        this.trustStore = trustStore;
        return this;
    }

    @Generated
    public HazelcastNetworkSslProperties setTrustStoreType(String trustStoreType) {
        this.trustStoreType = trustStoreType;
        return this;
    }

    @Generated
    public HazelcastNetworkSslProperties setTrustStorePassword(String trustStorePassword) {
        this.trustStorePassword = trustStorePassword;
        return this;
    }

    @Generated
    public HazelcastNetworkSslProperties setMutualAuthentication(String mutualAuthentication) {
        this.mutualAuthentication = mutualAuthentication;
        return this;
    }

    @Generated
    public HazelcastNetworkSslProperties setCipherSuites(String cipherSuites) {
        this.cipherSuites = cipherSuites;
        return this;
    }

    @Generated
    public HazelcastNetworkSslProperties setTrustManagerAlgorithm(String trustManagerAlgorithm) {
        this.trustManagerAlgorithm = trustManagerAlgorithm;
        return this;
    }

    @Generated
    public HazelcastNetworkSslProperties setKeyManagerAlgorithm(String keyManagerAlgorithm) {
        this.keyManagerAlgorithm = keyManagerAlgorithm;
        return this;
    }

    @Generated
    public HazelcastNetworkSslProperties setValidateIdentity(boolean validateIdentity) {
        this.validateIdentity = validateIdentity;
        return this;
    }
}

