/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 */
package org.apereo.cas.configuration.model.support.mfa.gauth;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.model.support.couchdb.BaseCouchDbProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-gauth-couchdb")
@JsonFilter(value="CouchDbGoogleAuthenticatorMultifactorProperties")
public class CouchDbGoogleAuthenticatorMultifactorProperties
extends BaseCouchDbProperties {
    private static final long serialVersionUID = -6260683393319585262L;

    public CouchDbGoogleAuthenticatorMultifactorProperties() {
        this.setDbName("gauth_multifactor");
    }
}

