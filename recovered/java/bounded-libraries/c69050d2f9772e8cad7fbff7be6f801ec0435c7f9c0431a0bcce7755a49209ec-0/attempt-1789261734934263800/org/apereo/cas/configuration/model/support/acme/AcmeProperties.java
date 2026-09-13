/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.acme;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Generated;
import org.apereo.cas.configuration.model.SpringResourceProperties;
import org.apereo.cas.configuration.support.DurationCapable;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-acme")
@JsonFilter(value="AcmeProperties")
public class AcmeProperties
implements Serializable {
    private static final long serialVersionUID = -561637865919944706L;
    @RequiredProperty
    private boolean termsOfUseAccepted;
    @NestedConfigurationProperty
    @RequiredProperty
    private SpringResourceProperties userKey = new SpringResourceProperties();
    @NestedConfigurationProperty
    @RequiredProperty
    private SpringResourceProperties domainKey = new SpringResourceProperties();
    @NestedConfigurationProperty
    @RequiredProperty
    private SpringResourceProperties domainCsr = new SpringResourceProperties();
    @NestedConfigurationProperty
    @RequiredProperty
    private SpringResourceProperties domainChain = new SpringResourceProperties();
    private int keySize = 2048;
    @RequiredProperty
    private String serverUrl = "acme://letsencrypt.org/staging";
    @RequiredProperty
    private List<String> domains = new ArrayList<String>();
    private int retryAttempts = 3;
    @DurationCapable
    private String retryInternal = "PT2S";

    @Generated
    public boolean isTermsOfUseAccepted() {
        return this.termsOfUseAccepted;
    }

    @Generated
    public SpringResourceProperties getUserKey() {
        return this.userKey;
    }

    @Generated
    public SpringResourceProperties getDomainKey() {
        return this.domainKey;
    }

    @Generated
    public SpringResourceProperties getDomainCsr() {
        return this.domainCsr;
    }

    @Generated
    public SpringResourceProperties getDomainChain() {
        return this.domainChain;
    }

    @Generated
    public int getKeySize() {
        return this.keySize;
    }

    @Generated
    public String getServerUrl() {
        return this.serverUrl;
    }

    @Generated
    public List<String> getDomains() {
        return this.domains;
    }

    @Generated
    public int getRetryAttempts() {
        return this.retryAttempts;
    }

    @Generated
    public String getRetryInternal() {
        return this.retryInternal;
    }

    @Generated
    public AcmeProperties setTermsOfUseAccepted(boolean termsOfUseAccepted) {
        this.termsOfUseAccepted = termsOfUseAccepted;
        return this;
    }

    @Generated
    public AcmeProperties setUserKey(SpringResourceProperties userKey) {
        this.userKey = userKey;
        return this;
    }

    @Generated
    public AcmeProperties setDomainKey(SpringResourceProperties domainKey) {
        this.domainKey = domainKey;
        return this;
    }

    @Generated
    public AcmeProperties setDomainCsr(SpringResourceProperties domainCsr) {
        this.domainCsr = domainCsr;
        return this;
    }

    @Generated
    public AcmeProperties setDomainChain(SpringResourceProperties domainChain) {
        this.domainChain = domainChain;
        return this;
    }

    @Generated
    public AcmeProperties setKeySize(int keySize) {
        this.keySize = keySize;
        return this;
    }

    @Generated
    public AcmeProperties setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
        return this;
    }

    @Generated
    public AcmeProperties setDomains(List<String> domains) {
        this.domains = domains;
        return this;
    }

    @Generated
    public AcmeProperties setRetryAttempts(int retryAttempts) {
        this.retryAttempts = retryAttempts;
        return this;
    }

    @Generated
    public AcmeProperties setRetryInternal(String retryInternal) {
        this.retryInternal = retryInternal;
        return this;
    }
}

