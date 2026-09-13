/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.wsfed;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-ws-idp")
@JsonFilter(value="WsFederationIdentityProviderProperties")
public class WsFederationIdentityProviderProperties
implements Serializable {
    private static final long serialVersionUID = 5190493517277610788L;
    @RequiredProperty
    private String realm = "urn:org:apereo:cas:ws:idp:realm-CAS";
    @RequiredProperty
    private String realmName = "CAS";

    @Generated
    public String getRealm() {
        return this.realm;
    }

    @Generated
    public String getRealmName() {
        return this.realmName;
    }

    @Generated
    public WsFederationIdentityProviderProperties setRealm(String realm) {
        this.realm = realm;
        return this;
    }

    @Generated
    public WsFederationIdentityProviderProperties setRealmName(String realmName) {
        this.realmName = realmName;
        return this;
    }
}

