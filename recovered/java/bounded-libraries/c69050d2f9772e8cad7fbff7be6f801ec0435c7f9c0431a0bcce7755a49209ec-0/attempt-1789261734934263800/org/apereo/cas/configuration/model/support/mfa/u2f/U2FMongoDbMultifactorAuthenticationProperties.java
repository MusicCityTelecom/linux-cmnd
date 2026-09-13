/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 */
package org.apereo.cas.configuration.model.support.mfa.u2f;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.model.support.mongo.SingleCollectionMongoDbProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-u2f-mongo")
@JsonFilter(value="U2FMongoDbMultifactorAuthenticationProperties")
public class U2FMongoDbMultifactorAuthenticationProperties
extends SingleCollectionMongoDbProperties {
    private static final long serialVersionUID = -7963843335569634144L;

    public U2FMongoDbMultifactorAuthenticationProperties() {
        this.setCollection("CasMongoDbU2FRepository");
    }
}

