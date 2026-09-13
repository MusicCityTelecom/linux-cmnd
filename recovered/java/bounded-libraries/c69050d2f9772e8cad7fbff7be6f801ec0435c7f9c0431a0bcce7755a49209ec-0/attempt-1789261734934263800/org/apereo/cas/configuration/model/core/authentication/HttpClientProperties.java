/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.core.authentication;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.authentication.HttpClientTrustStoreProperties;
import org.apereo.cas.configuration.support.DurationCapable;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-core-authentication", automated=true)
@JsonFilter(value="HttpClientProperties")
public class HttpClientProperties
implements Serializable {
    private static final long serialVersionUID = -7494946569869245770L;
    @DurationCapable
    private String connectionTimeout = "PT5S";
    @DurationCapable
    private String readTimeout = "PT5S";
    @DurationCapable
    private String socketTimeout = "PT5S";
    @DurationCapable
    private String asyncTimeout = "PT5S";
    private String hostNameVerifier = "default";
    @NestedConfigurationProperty
    private HttpClientTrustStoreProperties truststore = new HttpClientTrustStoreProperties();
    private boolean allowLocalUrls;
    private String authorityValidationRegex;
    private String proxyHost;
    private int proxyPort;
    private boolean authorityValidationRegExCaseSensitive = true;
    private Map<String, String> defaultHeaders = new HashMap<String, String>();

    @Generated
    public String getConnectionTimeout() {
        return this.connectionTimeout;
    }

    @Generated
    public String getReadTimeout() {
        return this.readTimeout;
    }

    @Generated
    public String getSocketTimeout() {
        return this.socketTimeout;
    }

    @Generated
    public String getAsyncTimeout() {
        return this.asyncTimeout;
    }

    @Generated
    public String getHostNameVerifier() {
        return this.hostNameVerifier;
    }

    @Generated
    public HttpClientTrustStoreProperties getTruststore() {
        return this.truststore;
    }

    @Generated
    public boolean isAllowLocalUrls() {
        return this.allowLocalUrls;
    }

    @Generated
    public String getAuthorityValidationRegex() {
        return this.authorityValidationRegex;
    }

    @Generated
    public String getProxyHost() {
        return this.proxyHost;
    }

    @Generated
    public int getProxyPort() {
        return this.proxyPort;
    }

    @Generated
    public boolean isAuthorityValidationRegExCaseSensitive() {
        return this.authorityValidationRegExCaseSensitive;
    }

    @Generated
    public Map<String, String> getDefaultHeaders() {
        return this.defaultHeaders;
    }

    @Generated
    public HttpClientProperties setConnectionTimeout(String connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
        return this;
    }

    @Generated
    public HttpClientProperties setReadTimeout(String readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }

    @Generated
    public HttpClientProperties setSocketTimeout(String socketTimeout) {
        this.socketTimeout = socketTimeout;
        return this;
    }

    @Generated
    public HttpClientProperties setAsyncTimeout(String asyncTimeout) {
        this.asyncTimeout = asyncTimeout;
        return this;
    }

    @Generated
    public HttpClientProperties setHostNameVerifier(String hostNameVerifier) {
        this.hostNameVerifier = hostNameVerifier;
        return this;
    }

    @Generated
    public HttpClientProperties setTruststore(HttpClientTrustStoreProperties truststore) {
        this.truststore = truststore;
        return this;
    }

    @Generated
    public HttpClientProperties setAllowLocalUrls(boolean allowLocalUrls) {
        this.allowLocalUrls = allowLocalUrls;
        return this;
    }

    @Generated
    public HttpClientProperties setAuthorityValidationRegex(String authorityValidationRegex) {
        this.authorityValidationRegex = authorityValidationRegex;
        return this;
    }

    @Generated
    public HttpClientProperties setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
        return this;
    }

    @Generated
    public HttpClientProperties setProxyPort(int proxyPort) {
        this.proxyPort = proxyPort;
        return this;
    }

    @Generated
    public HttpClientProperties setAuthorityValidationRegExCaseSensitive(boolean authorityValidationRegExCaseSensitive) {
        this.authorityValidationRegExCaseSensitive = authorityValidationRegExCaseSensitive;
        return this;
    }

    @Generated
    public HttpClientProperties setDefaultHeaders(Map<String, String> defaultHeaders) {
        this.defaultHeaders = defaultHeaders;
        return this;
    }
}

