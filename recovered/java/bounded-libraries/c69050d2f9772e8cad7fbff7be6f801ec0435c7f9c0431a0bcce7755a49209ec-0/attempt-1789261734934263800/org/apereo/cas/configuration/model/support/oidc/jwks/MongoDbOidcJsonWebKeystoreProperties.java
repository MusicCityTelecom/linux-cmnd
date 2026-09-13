/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 */
package org.apereo.cas.configuration.model.support.oidc.jwks;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.model.support.mongo.SingleCollectionMongoDbProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-oidc")
@JsonFilter(value="MongoDbOidcJsonWebKeystoreProperties")
public class MongoDbOidcJsonWebKeystoreProperties
extends SingleCollectionMongoDbProperties {
    private static final long serialVersionUID = -8392367146283877576L;
}

