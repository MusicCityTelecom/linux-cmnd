/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.core.rest;

import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.rest.RestRegisteredServicesProperties;
import org.apereo.cas.configuration.model.core.rest.RestX509Properties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-rest")
public class RestProperties
implements Serializable {
    private static final long serialVersionUID = -1833107478273171342L;
    @NestedConfigurationProperty
    private RestRegisteredServicesProperties services = new RestRegisteredServicesProperties();
    @NestedConfigurationProperty
    private RestX509Properties x509 = new RestX509Properties();

    @Generated
    public RestRegisteredServicesProperties getServices() {
        return this.services;
    }

    @Generated
    public RestX509Properties getX509() {
        return this.x509;
    }

    @Generated
    public RestProperties setServices(RestRegisteredServicesProperties services) {
        this.services = services;
        return this;
    }

    @Generated
    public RestProperties setX509(RestX509Properties x509) {
        this.x509 = x509;
        return this;
    }
}

