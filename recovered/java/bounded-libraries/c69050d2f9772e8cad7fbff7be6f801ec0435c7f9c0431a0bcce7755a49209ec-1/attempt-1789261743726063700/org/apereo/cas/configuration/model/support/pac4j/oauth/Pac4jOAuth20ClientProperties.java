/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.pac4j.oauth;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.Generated;
import org.apereo.cas.configuration.features.CasFeatureModule;
import org.apereo.cas.configuration.model.support.pac4j.Pac4jBaseClientProperties;
import org.apereo.cas.configuration.model.support.pac4j.Pac4jIdentifiableClientProperties;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-pac4j-webflow")
@JsonFilter(value="Pac4jOAuth20ClientProperties")
public class Pac4jOAuth20ClientProperties
extends Pac4jIdentifiableClientProperties
implements CasFeatureModule {
    private static final long serialVersionUID = -1240711580664148382L;
    @RequiredProperty
    private String authUrl;
    @RequiredProperty
    private String tokenUrl;
    @RequiredProperty
    private String profileUrl;
    private boolean withState;
    private String scope;
    private String profilePath;
    private String profileVerb = "POST";
    private String responseType = "code";
    private Map<String, String> profileAttrs = new LinkedHashMap<String, String>(1);
    private Map<String, String> customParams = new LinkedHashMap<String, String>(1);
    private String clientAuthenticationMethod;

    public Pac4jOAuth20ClientProperties() {
        this.setCallbackUrlType(Pac4jBaseClientProperties.CallbackUrlTypes.PATH_PARAMETER);
    }

    @Generated
    public String getAuthUrl() {
        return this.authUrl;
    }

    @Generated
    public String getTokenUrl() {
        return this.tokenUrl;
    }

    @Generated
    public String getProfileUrl() {
        return this.profileUrl;
    }

    @Generated
    public boolean isWithState() {
        return this.withState;
    }

    @Generated
    public String getScope() {
        return this.scope;
    }

    @Generated
    public String getProfilePath() {
        return this.profilePath;
    }

    @Generated
    public String getProfileVerb() {
        return this.profileVerb;
    }

    @Generated
    public String getResponseType() {
        return this.responseType;
    }

    @Generated
    public Map<String, String> getProfileAttrs() {
        return this.profileAttrs;
    }

    @Generated
    public Map<String, String> getCustomParams() {
        return this.customParams;
    }

    @Generated
    public String getClientAuthenticationMethod() {
        return this.clientAuthenticationMethod;
    }

    @Generated
    public Pac4jOAuth20ClientProperties setAuthUrl(String authUrl) {
        this.authUrl = authUrl;
        return this;
    }

    @Generated
    public Pac4jOAuth20ClientProperties setTokenUrl(String tokenUrl) {
        this.tokenUrl = tokenUrl;
        return this;
    }

    @Generated
    public Pac4jOAuth20ClientProperties setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
        return this;
    }

    @Generated
    public Pac4jOAuth20ClientProperties setWithState(boolean withState) {
        this.withState = withState;
        return this;
    }

    @Generated
    public Pac4jOAuth20ClientProperties setScope(String scope) {
        this.scope = scope;
        return this;
    }

    @Generated
    public Pac4jOAuth20ClientProperties setProfilePath(String profilePath) {
        this.profilePath = profilePath;
        return this;
    }

    @Generated
    public Pac4jOAuth20ClientProperties setProfileVerb(String profileVerb) {
        this.profileVerb = profileVerb;
        return this;
    }

    @Generated
    public Pac4jOAuth20ClientProperties setResponseType(String responseType) {
        this.responseType = responseType;
        return this;
    }

    @Generated
    public Pac4jOAuth20ClientProperties setProfileAttrs(Map<String, String> profileAttrs) {
        this.profileAttrs = profileAttrs;
        return this;
    }

    @Generated
    public Pac4jOAuth20ClientProperties setCustomParams(Map<String, String> customParams) {
        this.customParams = customParams;
        return this;
    }

    @Generated
    public Pac4jOAuth20ClientProperties setClientAuthenticationMethod(String clientAuthenticationMethod) {
        this.clientAuthenticationMethod = clientAuthenticationMethod;
        return this;
    }
}

