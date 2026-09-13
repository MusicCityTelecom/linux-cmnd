/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.core.rest;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-rest-x509")
@JsonFilter(value="RestX509Properties")
public class RestX509Properties
implements Serializable {
    private static final long serialVersionUID = -1833117478273171342L;
    private boolean headerAuth = true;
    private boolean bodyAuth;
    private boolean tlsClientAuth;

    @Generated
    public boolean isHeaderAuth() {
        return this.headerAuth;
    }

    @Generated
    public boolean isBodyAuth() {
        return this.bodyAuth;
    }

    @Generated
    public boolean isTlsClientAuth() {
        return this.tlsClientAuth;
    }

    @Generated
    public RestX509Properties setHeaderAuth(boolean headerAuth) {
        this.headerAuth = headerAuth;
        return this;
    }

    @Generated
    public RestX509Properties setBodyAuth(boolean bodyAuth) {
        this.bodyAuth = bodyAuth;
        return this;
    }

    @Generated
    public RestX509Properties setTlsClientAuth(boolean tlsClientAuth) {
        this.tlsClientAuth = tlsClientAuth;
        return this;
    }
}

