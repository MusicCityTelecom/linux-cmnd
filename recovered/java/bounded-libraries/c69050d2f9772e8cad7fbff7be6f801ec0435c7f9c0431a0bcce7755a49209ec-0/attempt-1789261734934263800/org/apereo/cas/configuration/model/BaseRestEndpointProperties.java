/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-core-util", automated=true)
public class BaseRestEndpointProperties
implements Serializable {
    private static final long serialVersionUID = 2687020856160473089L;
    @RequiredProperty
    private String url;
    private String basicAuthUsername;
    private String basicAuthPassword;
    private Map<String, String> headers = new HashMap<String, String>();

    @Generated
    public String getUrl() {
        return this.url;
    }

    @Generated
    public String getBasicAuthUsername() {
        return this.basicAuthUsername;
    }

    @Generated
    public String getBasicAuthPassword() {
        return this.basicAuthPassword;
    }

    @Generated
    public Map<String, String> getHeaders() {
        return this.headers;
    }

    @Generated
    public BaseRestEndpointProperties setUrl(String url) {
        this.url = url;
        return this;
    }

    @Generated
    public BaseRestEndpointProperties setBasicAuthUsername(String basicAuthUsername) {
        this.basicAuthUsername = basicAuthUsername;
        return this;
    }

    @Generated
    public BaseRestEndpointProperties setBasicAuthPassword(String basicAuthPassword) {
        this.basicAuthPassword = basicAuthPassword;
        return this;
    }

    @Generated
    public BaseRestEndpointProperties setHeaders(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }
}

