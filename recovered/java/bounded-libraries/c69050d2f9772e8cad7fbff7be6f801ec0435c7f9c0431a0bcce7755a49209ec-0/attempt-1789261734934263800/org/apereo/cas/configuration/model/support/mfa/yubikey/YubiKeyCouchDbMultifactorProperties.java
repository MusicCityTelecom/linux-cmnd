/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.configuration.model.support.mfa.yubikey;

import org.apereo.cas.configuration.model.support.couchdb.BaseCouchDbProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-yubikey-couchdb")
public class YubiKeyCouchDbMultifactorProperties
extends BaseCouchDbProperties {
    private static final long serialVersionUID = 3757390989294642185L;

    public YubiKeyCouchDbMultifactorProperties() {
        this.setDbName("yubikey");
    }
}

