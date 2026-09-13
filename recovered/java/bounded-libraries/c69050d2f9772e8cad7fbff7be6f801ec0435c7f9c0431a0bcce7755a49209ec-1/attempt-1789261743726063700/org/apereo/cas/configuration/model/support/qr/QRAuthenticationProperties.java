/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.qr;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.qr.JsonQRAuthenticationProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-qr-authentication")
@JsonFilter(value="QRAuthenticationProperties")
public class QRAuthenticationProperties
implements Serializable {
    private static final long serialVersionUID = 8726382874579042117L;
    private List<String> allowedOrigins = new ArrayList<String>();
    @NestedConfigurationProperty
    private JsonQRAuthenticationProperties json = new JsonQRAuthenticationProperties();

    @Generated
    public List<String> getAllowedOrigins() {
        return this.allowedOrigins;
    }

    @Generated
    public JsonQRAuthenticationProperties getJson() {
        return this.json;
    }

    @Generated
    public QRAuthenticationProperties setAllowedOrigins(List<String> allowedOrigins) {
        this.allowedOrigins = allowedOrigins;
        return this;
    }

    @Generated
    public QRAuthenticationProperties setJson(JsonQRAuthenticationProperties json) {
        this.json = json;
        return this;
    }
}

