/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 */
package org.apereo.cas.configuration.model.support.mfa.webauthn;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.model.support.mongo.SingleCollectionMongoDbProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-webauthn-mongo")
@JsonFilter(value="WebAuthnMongoDbMultifactorProperties")
public class WebAuthnMongoDbMultifactorProperties
extends SingleCollectionMongoDbProperties {
    private static final long serialVersionUID = 6876845341227039713L;

    public WebAuthnMongoDbMultifactorProperties() {
        this.setCollection("MongoDbWebAuthnRepository");
    }
}

