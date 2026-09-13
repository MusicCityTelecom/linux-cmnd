/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 */
package org.apereo.cas.configuration.model.support.mfa.u2f;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.model.support.couchdb.BaseAsynchronousCouchDbProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-u2f-couchdb")
@JsonFilter(value="U2FCouchDbMultifactorAuthenticationProperties")
public class U2FCouchDbMultifactorAuthenticationProperties
extends BaseAsynchronousCouchDbProperties {
    private static final long serialVersionUID = 2751957521987245445L;

    public U2FCouchDbMultifactorAuthenticationProperties() {
        this.setDbName("u2f_multifactor");
    }
}

