/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.springframework.core.io.Resource
 */
package org.apereo.cas.configuration.model.support.geo.maxmind;

import lombok.Generated;
import org.apereo.cas.configuration.model.support.geo.BaseGeoLocationProperties;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.core.io.Resource;

@RequiresModule(name="cas-server-support-geolocation-maxmind")
public class MaxmindProperties
extends BaseGeoLocationProperties {
    private static final long serialVersionUID = 7883029275219817797L;
    @RequiredProperty
    private transient Resource cityDatabase;
    @RequiredProperty
    private transient Resource countryDatabase;

    @Generated
    public Resource getCityDatabase() {
        return this.cityDatabase;
    }

    @Generated
    public Resource getCountryDatabase() {
        return this.countryDatabase;
    }

    @Generated
    public MaxmindProperties setCityDatabase(Resource cityDatabase) {
        this.cityDatabase = cityDatabase;
        return this;
    }

    @Generated
    public MaxmindProperties setCountryDatabase(Resource countryDatabase) {
        this.countryDatabase = countryDatabase;
        return this;
    }
}

