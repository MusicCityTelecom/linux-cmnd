/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.sms;

import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-sms-clickatell")
public class ClickatellProperties
implements Serializable {
    private static final long serialVersionUID = -2147844690349952176L;
    @RequiredProperty
    private String token;
    @RequiredProperty
    private String serverUrl = "https://platform.clickatell.com/messages";

    @Generated
    public String getToken() {
        return this.token;
    }

    @Generated
    public String getServerUrl() {
        return this.serverUrl;
    }

    @Generated
    public ClickatellProperties setToken(String token) {
        this.token = token;
        return this;
    }

    @Generated
    public ClickatellProperties setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
        return this;
    }
}

