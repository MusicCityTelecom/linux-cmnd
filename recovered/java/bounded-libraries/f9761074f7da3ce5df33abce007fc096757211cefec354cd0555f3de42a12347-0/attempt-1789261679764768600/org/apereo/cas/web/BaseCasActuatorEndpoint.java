/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.configuration.CasConfigurationProperties
 */
package org.apereo.cas.web;

import lombok.Generated;
import org.apereo.cas.configuration.CasConfigurationProperties;

public abstract class BaseCasActuatorEndpoint {
    protected static final String MEDIA_TYPE_SPRING_BOOT_V2_JSON = "application/vnd.spring-boot.actuator.v2+json";
    protected static final String MEDIA_TYPE_SPRING_BOOT_V3_JSON = "application/vnd.spring-boot.actuator.v3+json";
    protected final CasConfigurationProperties casProperties;

    @Generated
    protected BaseCasActuatorEndpoint(CasConfigurationProperties casProperties) {
        this.casProperties = casProperties;
    }
}

