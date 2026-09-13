/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.aws;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.DurationCapable;
import org.apereo.cas.configuration.support.ExpressionLanguageCapable;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-aws")
@JsonFilter(value="BaseAmazonWebServicesProperties")
public abstract class BaseAmazonWebServicesProperties
implements Serializable {
    private static final long serialVersionUID = 6426637051495147084L;
    @RequiredProperty
    @ExpressionLanguageCapable
    private String credentialAccessKey;
    @RequiredProperty
    @ExpressionLanguageCapable
    private String credentialSecretKey;
    @RequiredProperty
    private String region;
    private String profileName;
    private String profilePath;
    @RequiredProperty
    private String endpoint;
    private int maxConnections = 10;
    @DurationCapable
    private String connectionTimeout = "5000";
    @DurationCapable
    private String socketTimeout = "5000";
    @DurationCapable
    private String clientExecutionTimeout = "10000";
    private boolean useReaper;
    private String proxyHost;
    private String proxyPassword;
    private String proxyUsername;
    private String retryMode = "STANDARD";
    private String localAddress;

    @Generated
    public String getCredentialAccessKey() {
        return this.credentialAccessKey;
    }

    @Generated
    public String getCredentialSecretKey() {
        return this.credentialSecretKey;
    }

    @Generated
    public String getRegion() {
        return this.region;
    }

    @Generated
    public String getProfileName() {
        return this.profileName;
    }

    @Generated
    public String getProfilePath() {
        return this.profilePath;
    }

    @Generated
    public String getEndpoint() {
        return this.endpoint;
    }

    @Generated
    public int getMaxConnections() {
        return this.maxConnections;
    }

    @Generated
    public String getConnectionTimeout() {
        return this.connectionTimeout;
    }

    @Generated
    public String getSocketTimeout() {
        return this.socketTimeout;
    }

    @Generated
    public String getClientExecutionTimeout() {
        return this.clientExecutionTimeout;
    }

    @Generated
    public boolean isUseReaper() {
        return this.useReaper;
    }

    @Generated
    public String getProxyHost() {
        return this.proxyHost;
    }

    @Generated
    public String getProxyPassword() {
        return this.proxyPassword;
    }

    @Generated
    public String getProxyUsername() {
        return this.proxyUsername;
    }

    @Generated
    public String getRetryMode() {
        return this.retryMode;
    }

    @Generated
    public String getLocalAddress() {
        return this.localAddress;
    }

    @Generated
    public BaseAmazonWebServicesProperties setCredentialAccessKey(String credentialAccessKey) {
        this.credentialAccessKey = credentialAccessKey;
        return this;
    }

    @Generated
    public BaseAmazonWebServicesProperties setCredentialSecretKey(String credentialSecretKey) {
        this.credentialSecretKey = credentialSecretKey;
        return this;
    }

    @Generated
    public BaseAmazonWebServicesProperties setRegion(String region) {
        this.region = region;
        return this;
    }

    @Generated
    public BaseAmazonWebServicesProperties setProfileName(String profileName) {
        this.profileName = profileName;
        return this;
    }

    @Generated
    public BaseAmazonWebServicesProperties setProfilePath(String profilePath) {
        this.profilePath = profilePath;
        return this;
    }

    @Generated
    public BaseAmazonWebServicesProperties setEndpoint(String endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    @Generated
    public BaseAmazonWebServicesProperties setMaxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
        return this;
    }

    @Generated
    public BaseAmazonWebServicesProperties setConnectionTimeout(String connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
        return this;
    }

    @Generated
    public BaseAmazonWebServicesProperties setSocketTimeout(String socketTimeout) {
        this.socketTimeout = socketTimeout;
        return this;
    }

    @Generated
    public BaseAmazonWebServicesProperties setClientExecutionTimeout(String clientExecutionTimeout) {
        this.clientExecutionTimeout = clientExecutionTimeout;
        return this;
    }

    @Generated
    public BaseAmazonWebServicesProperties setUseReaper(boolean useReaper) {
        this.useReaper = useReaper;
        return this;
    }

    @Generated
    public BaseAmazonWebServicesProperties setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
        return this;
    }

    @Generated
    public BaseAmazonWebServicesProperties setProxyPassword(String proxyPassword) {
        this.proxyPassword = proxyPassword;
        return this;
    }

    @Generated
    public BaseAmazonWebServicesProperties setProxyUsername(String proxyUsername) {
        this.proxyUsername = proxyUsername;
        return this;
    }

    @Generated
    public BaseAmazonWebServicesProperties setRetryMode(String retryMode) {
        this.retryMode = retryMode;
        return this;
    }

    @Generated
    public BaseAmazonWebServicesProperties setLocalAddress(String localAddress) {
        this.localAddress = localAddress;
        return this;
    }
}

