/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.couchbase.authentication;

import lombok.Generated;
import org.apereo.cas.configuration.model.support.couchbase.BaseCouchbaseProperties;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-couchbase-authentication")
public class CouchbasePrincipalAttributesProperties
extends BaseCouchbaseProperties {
    private static final long serialVersionUID = -6573755681498251678L;
    private int order;
    @RequiredProperty
    private String usernameAttribute = "username";
    private String id;

    @Generated
    public int getOrder() {
        return this.order;
    }

    @Generated
    public String getUsernameAttribute() {
        return this.usernameAttribute;
    }

    @Generated
    public String getId() {
        return this.id;
    }

    @Generated
    public CouchbasePrincipalAttributesProperties setOrder(int order) {
        this.order = order;
        return this;
    }

    @Generated
    public CouchbasePrincipalAttributesProperties setUsernameAttribute(String usernameAttribute) {
        this.usernameAttribute = usernameAttribute;
        return this;
    }

    @Generated
    public CouchbasePrincipalAttributesProperties setId(String id) {
        this.id = id;
        return this;
    }
}

