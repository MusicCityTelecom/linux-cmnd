/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.configuration.model.support.mfa.yubikey;

import org.apereo.cas.configuration.model.support.mongo.SingleCollectionMongoDbProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-yubikey-mongo")
public class YubiKeyMongoDbMultifactorProperties
extends SingleCollectionMongoDbProperties {
    private static final long serialVersionUID = 6876845341227039713L;

    public YubiKeyMongoDbMultifactorProperties() {
        this.setCollection("MongoDbYubiKeyRepository");
    }
}

