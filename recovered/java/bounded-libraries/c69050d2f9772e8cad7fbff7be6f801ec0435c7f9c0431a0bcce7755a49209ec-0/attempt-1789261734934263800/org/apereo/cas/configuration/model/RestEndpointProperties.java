/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Generated;
import org.apereo.cas.configuration.model.BaseRestEndpointProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-core-util", automated=true)
@JsonFilter(value="RestEndpointProperties")
public class RestEndpointProperties
extends BaseRestEndpointProperties {
    private static final long serialVersionUID = 2687020856160473089L;
    private String method = "GET";

    @Generated
    public String getMethod() {
        return this.method;
    }

    @Generated
    public RestEndpointProperties setMethod(String method) {
        this.method = method;
        return this;
    }
}

