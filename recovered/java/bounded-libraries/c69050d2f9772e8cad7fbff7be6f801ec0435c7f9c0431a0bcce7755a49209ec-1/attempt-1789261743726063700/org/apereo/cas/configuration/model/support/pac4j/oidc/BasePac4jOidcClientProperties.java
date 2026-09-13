/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.pac4j.oidc;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.pac4j.Pac4jIdentifiableClientProperties;
import org.apereo.cas.configuration.support.DurationCapable;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-pac4j-webflow")
@JsonFilter(value="BasePac4jOidcClientProperties")
public abstract class BasePac4jOidcClientProperties
extends Pac4jIdentifiableClientProperties {
    private static final long serialVersionUID = 3359382317533639638L;
    @RequiredProperty
    private String discoveryUri;
    private String logoutUrl;
    private boolean useNonce;
    private boolean disablePkce;
    private String scope;
    private String preferredJwsAlgorithm;
    @DurationCapable
    private String maxClockSkew = "PT5S";
    private Map<String, String> customParams = new HashMap<String, String>(0);
    private String responseMode;
    private String responseType;
    @DurationCapable
    private String connectTimeout = "PT5S";
    @DurationCapable
    private String readTimeout = "PT5S";
    private boolean expireSessionWithToken;
    @DurationCapable
    private String tokenExpirationAdvance;
    private List<String> mappedClaims = new ArrayList<String>();
    private boolean allowUnsignedIdTokens;
    private boolean includeAccessTokenClaims;

    @Generated
    public String getDiscoveryUri() {
        return this.discoveryUri;
    }

    @Generated
    public String getLogoutUrl() {
        return this.logoutUrl;
    }

    @Generated
    public boolean isUseNonce() {
        return this.useNonce;
    }

    @Generated
    public boolean isDisablePkce() {
        return this.disablePkce;
    }

    @Generated
    public String getScope() {
        return this.scope;
    }

    @Generated
    public String getPreferredJwsAlgorithm() {
        return this.preferredJwsAlgorithm;
    }

    @Generated
    public String getMaxClockSkew() {
        return this.maxClockSkew;
    }

    @Generated
    public Map<String, String> getCustomParams() {
        return this.customParams;
    }

    @Generated
    public String getResponseMode() {
        return this.responseMode;
    }

    @Generated
    public String getResponseType() {
        return this.responseType;
    }

    @Generated
    public String getConnectTimeout() {
        return this.connectTimeout;
    }

    @Generated
    public String getReadTimeout() {
        return this.readTimeout;
    }

    @Generated
    public boolean isExpireSessionWithToken() {
        return this.expireSessionWithToken;
    }

    @Generated
    public String getTokenExpirationAdvance() {
        return this.tokenExpirationAdvance;
    }

    @Generated
    public List<String> getMappedClaims() {
        return this.mappedClaims;
    }

    @Generated
    public boolean isAllowUnsignedIdTokens() {
        return this.allowUnsignedIdTokens;
    }

    @Generated
    public boolean isIncludeAccessTokenClaims() {
        return this.includeAccessTokenClaims;
    }

    @Generated
    public BasePac4jOidcClientProperties setDiscoveryUri(String discoveryUri) {
        this.discoveryUri = discoveryUri;
        return this;
    }

    @Generated
    public BasePac4jOidcClientProperties setLogoutUrl(String logoutUrl) {
        this.logoutUrl = logoutUrl;
        return this;
    }

    @Generated
    public BasePac4jOidcClientProperties setUseNonce(boolean useNonce) {
        this.useNonce = useNonce;
        return this;
    }

    @Generated
    public BasePac4jOidcClientProperties setDisablePkce(boolean disablePkce) {
        this.disablePkce = disablePkce;
        return this;
    }

    @Generated
    public BasePac4jOidcClientProperties setScope(String scope) {
        this.scope = scope;
        return this;
    }

    @Generated
    public BasePac4jOidcClientProperties setPreferredJwsAlgorithm(String preferredJwsAlgorithm) {
        this.preferredJwsAlgorithm = preferredJwsAlgorithm;
        return this;
    }

    @Generated
    public BasePac4jOidcClientProperties setMaxClockSkew(String maxClockSkew) {
        this.maxClockSkew = maxClockSkew;
        return this;
    }

    @Generated
    public BasePac4jOidcClientProperties setCustomParams(Map<String, String> customParams) {
        this.customParams = customParams;
        return this;
    }

    @Generated
    public BasePac4jOidcClientProperties setResponseMode(String responseMode) {
        this.responseMode = responseMode;
        return this;
    }

    @Generated
    public BasePac4jOidcClientProperties setResponseType(String responseType) {
        this.responseType = responseType;
        return this;
    }

    @Generated
    public BasePac4jOidcClientProperties setConnectTimeout(String connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    @Generated
    public BasePac4jOidcClientProperties setReadTimeout(String readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }

    @Generated
    public BasePac4jOidcClientProperties setExpireSessionWithToken(boolean expireSessionWithToken) {
        this.expireSessionWithToken = expireSessionWithToken;
        return this;
    }

    @Generated
    public BasePac4jOidcClientProperties setTokenExpirationAdvance(String tokenExpirationAdvance) {
        this.tokenExpirationAdvance = tokenExpirationAdvance;
        return this;
    }

    @Generated
    public BasePac4jOidcClientProperties setMappedClaims(List<String> mappedClaims) {
        this.mappedClaims = mappedClaims;
        return this;
    }

    @Generated
    public BasePac4jOidcClientProperties setAllowUnsignedIdTokens(boolean allowUnsignedIdTokens) {
        this.allowUnsignedIdTokens = allowUnsignedIdTokens;
        return this;
    }

    @Generated
    public BasePac4jOidcClientProperties setIncludeAccessTokenClaims(boolean includeAccessTokenClaims) {
        this.includeAccessTokenClaims = includeAccessTokenClaims;
        return this;
    }
}

