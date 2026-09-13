/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.configuration.model.support.consent;

import org.apereo.cas.configuration.model.support.couchdb.BaseCouchDbProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-consent-couchdb")
public class CouchDbConsentProperties
extends BaseCouchDbProperties {
    private static final long serialVersionUID = 8184753250455916462L;

    public CouchDbConsentProperties() {
        this.setDbName("consent");
    }
}

