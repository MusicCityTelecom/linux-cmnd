/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.configuration.model.support.consent;

import org.apereo.cas.configuration.model.support.mongo.SingleCollectionMongoDbProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-consent-mongo")
public class MongoDbConsentProperties
extends SingleCollectionMongoDbProperties {
    private static final long serialVersionUID = -1918436901491275547L;

    public MongoDbConsentProperties() {
        this.setCollection("MongoDbCasConsentRepository");
    }
}

