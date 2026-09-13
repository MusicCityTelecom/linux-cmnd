/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 */
package org.apereo.cas.configuration.model.support.mfa.trusteddevice;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.model.support.couchdb.BaseCouchDbProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-trusted-mfa-couchdb")
@JsonFilter(value="CouchDbTrustedDevicesMultifactorProperties")
public class CouchDbTrustedDevicesMultifactorProperties
extends BaseCouchDbProperties {
    private static final long serialVersionUID = 5887850351177564308L;

    public CouchDbTrustedDevicesMultifactorProperties() {
        this.setDbName("trusted_devices_multifactor");
    }
}

