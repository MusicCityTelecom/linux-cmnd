/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.saml.shibboleth;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-shibboleth")
@JsonFilter(value="ShibbolethIdPProperties")
public class ShibbolethIdPProperties
implements Serializable {
    private static final long serialVersionUID = 1741075420882227768L;
    private String serverUrl = "localhost";

    @Generated
    public String getServerUrl() {
        return this.serverUrl;
    }

    @Generated
    public ShibbolethIdPProperties setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
        return this;
    }
}

