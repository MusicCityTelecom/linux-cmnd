/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.geo;

import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.SpringResourceProperties;
import org.apereo.cas.configuration.model.support.geo.googlemaps.GoogleMapsProperties;
import org.apereo.cas.configuration.model.support.geo.ip.IPGeoLocationProperties;
import org.apereo.cas.configuration.model.support.geo.maxmind.MaxmindProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-geolocation")
public class GeoLocationProperties
implements Serializable {
    private static final long serialVersionUID = 7529478582792969209L;
    @NestedConfigurationProperty
    private IPGeoLocationProperties ipGeoLocation = new IPGeoLocationProperties();
    @NestedConfigurationProperty
    private MaxmindProperties maxmind = new MaxmindProperties();
    @NestedConfigurationProperty
    private GoogleMapsProperties googleMaps = new GoogleMapsProperties();
    @NestedConfigurationProperty
    private SpringResourceProperties groovy = new SpringResourceProperties();

    @Generated
    public IPGeoLocationProperties getIpGeoLocation() {
        return this.ipGeoLocation;
    }

    @Generated
    public MaxmindProperties getMaxmind() {
        return this.maxmind;
    }

    @Generated
    public GoogleMapsProperties getGoogleMaps() {
        return this.googleMaps;
    }

    @Generated
    public SpringResourceProperties getGroovy() {
        return this.groovy;
    }

    @Generated
    public GeoLocationProperties setIpGeoLocation(IPGeoLocationProperties ipGeoLocation) {
        this.ipGeoLocation = ipGeoLocation;
        return this;
    }

    @Generated
    public GeoLocationProperties setMaxmind(MaxmindProperties maxmind) {
        this.maxmind = maxmind;
        return this;
    }

    @Generated
    public GeoLocationProperties setGoogleMaps(GoogleMapsProperties googleMaps) {
        this.googleMaps = googleMaps;
        return this;
    }

    @Generated
    public GeoLocationProperties setGroovy(SpringResourceProperties groovy) {
        this.groovy = groovy;
        return this;
    }
}

