/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.geo.googlemaps;

import lombok.Generated;
import org.apereo.cas.configuration.model.support.geo.BaseGeoLocationProperties;
import org.apereo.cas.configuration.support.DurationCapable;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-geolocation-googlemaps")
public class GoogleMapsProperties
extends BaseGeoLocationProperties {
    private static final long serialVersionUID = 4661113818711911462L;
    @RequiredProperty
    private String apiKey;
    @RequiredProperty
    private String clientId;
    @RequiredProperty
    private String clientSecret;
    @DurationCapable
    private String connectTimeout = "PT3S";
    private boolean googleAppsEngine;

    @Generated
    public String getApiKey() {
        return this.apiKey;
    }

    @Generated
    public String getClientId() {
        return this.clientId;
    }

    @Generated
    public String getClientSecret() {
        return this.clientSecret;
    }

    @Generated
    public String getConnectTimeout() {
        return this.connectTimeout;
    }

    @Generated
    public boolean isGoogleAppsEngine() {
        return this.googleAppsEngine;
    }

    @Generated
    public GoogleMapsProperties setApiKey(String apiKey) {
        this.apiKey = apiKey;
        return this;
    }

    @Generated
    public GoogleMapsProperties setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    @Generated
    public GoogleMapsProperties setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
        return this;
    }

    @Generated
    public GoogleMapsProperties setConnectTimeout(String connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    @Generated
    public GoogleMapsProperties setGoogleAppsEngine(boolean googleAppsEngine) {
        this.googleAppsEngine = googleAppsEngine;
        return this;
    }
}

