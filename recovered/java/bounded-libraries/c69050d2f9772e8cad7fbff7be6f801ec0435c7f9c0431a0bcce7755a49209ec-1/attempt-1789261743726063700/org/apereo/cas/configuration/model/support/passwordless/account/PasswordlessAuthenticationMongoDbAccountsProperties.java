/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 */
package org.apereo.cas.configuration.model.support.passwordless.account;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.model.support.mongo.SingleCollectionMongoDbProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-passwordless-mongo")
@JsonFilter(value="PasswordlessAuthenticationMongoDbAccountsProperties")
public class PasswordlessAuthenticationMongoDbAccountsProperties
extends SingleCollectionMongoDbProperties {
    private static final long serialVersionUID = -6304734732383722585L;
}

