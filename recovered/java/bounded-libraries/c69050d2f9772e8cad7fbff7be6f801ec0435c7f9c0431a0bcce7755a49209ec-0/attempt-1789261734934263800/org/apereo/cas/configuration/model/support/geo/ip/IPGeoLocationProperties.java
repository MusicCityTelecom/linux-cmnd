/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.geo.ip;

import lombok.Generated;
import org.apereo.cas.configuration.model.support.geo.BaseGeoLocationProperties;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-geolocation-ip")
public class IPGeoLocationProperties
extends BaseGeoLocationProperties {
    private static final long serialVersionUID = 1883029275219817797L;
    @RequiredProperty
    private String apiKey;

    @Generated
    public String getApiKey() {
        return this.apiKey;
    }

    @Generated
    public IPGeoLocationProperties setApiKey(String apiKey) {
        this.apiKey = apiKey;
        return this;
    }
}

