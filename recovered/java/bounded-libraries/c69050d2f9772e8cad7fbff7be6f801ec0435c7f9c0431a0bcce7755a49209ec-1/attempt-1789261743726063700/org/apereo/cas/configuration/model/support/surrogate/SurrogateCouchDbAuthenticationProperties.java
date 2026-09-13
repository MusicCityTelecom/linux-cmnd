/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.surrogate;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.couchdb.BaseCouchDbProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-surrogate-authentication-couchdb")
@JsonFilter(value="SurrogateCouchDbAuthenticationProperties")
public class SurrogateCouchDbAuthenticationProperties
extends BaseCouchDbProperties {
    private static final long serialVersionUID = 8378399979559955402L;
    private boolean profileBased;
    private String surrogatePrincipalsAttribute = "surrogateFor";

    public SurrogateCouchDbAuthenticationProperties() {
        this.setDbName("surrogates");
    }

    @Generated
    public boolean isProfileBased() {
        return this.profileBased;
    }

    @Generated
    public String getSurrogatePrincipalsAttribute() {
        return this.surrogatePrincipalsAttribute;
    }

    @Generated
    public SurrogateCouchDbAuthenticationProperties setProfileBased(boolean profileBased) {
        this.profileBased = profileBased;
        return this;
    }

    @Generated
    public SurrogateCouchDbAuthenticationProperties setSurrogatePrincipalsAttribute(String surrogatePrincipalsAttribute) {
        this.surrogatePrincipalsAttribute = surrogatePrincipalsAttribute;
        return this;
    }
}

