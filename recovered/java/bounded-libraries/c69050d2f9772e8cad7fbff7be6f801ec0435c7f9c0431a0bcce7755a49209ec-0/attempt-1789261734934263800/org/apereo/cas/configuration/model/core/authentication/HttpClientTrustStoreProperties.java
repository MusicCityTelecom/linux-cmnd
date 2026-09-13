/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.springframework.core.io.Resource
 */
package org.apereo.cas.configuration.model.core.authentication;

import java.io.Serializable;
import java.security.KeyStore;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.core.io.Resource;

@RequiresModule(name="cas-server-core-authentication", automated=true)
public class HttpClientTrustStoreProperties
implements Serializable {
    private static final long serialVersionUID = -1357168622083627654L;
    private transient Resource file;
    private String psw = "changeit";
    private String type = KeyStore.getDefaultType();

    @Generated
    public Resource getFile() {
        return this.file;
    }

    @Generated
    public String getPsw() {
        return this.psw;
    }

    @Generated
    public String getType() {
        return this.type;
    }

    @Generated
    public HttpClientTrustStoreProperties setFile(Resource file) {
        this.file = file;
        return this;
    }

    @Generated
    public HttpClientTrustStoreProperties setPsw(String psw) {
        this.psw = psw;
        return this;
    }

    @Generated
    public HttpClientTrustStoreProperties setType(String type) {
        this.type = type;
        return this;
    }
}

