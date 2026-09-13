/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.core.authentication;

import lombok.Generated;
import org.apereo.cas.configuration.model.SpringResourceProperties;
import org.apereo.cas.configuration.model.core.authentication.AttributeRepositoryStates;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-person-directory", automated=true)
public class JsonPrincipalAttributesProperties
extends SpringResourceProperties {
    private static final long serialVersionUID = -6573755681498251678L;
    private int order;
    private String id;
    private AttributeRepositoryStates state = AttributeRepositoryStates.ACTIVE;

    @Generated
    public int getOrder() {
        return this.order;
    }

    @Generated
    public String getId() {
        return this.id;
    }

    @Generated
    public AttributeRepositoryStates getState() {
        return this.state;
    }

    @Generated
    public JsonPrincipalAttributesProperties setOrder(int order) {
        this.order = order;
        return this;
    }

    @Generated
    public JsonPrincipalAttributesProperties setId(String id) {
        this.id = id;
        return this;
    }

    @Generated
    public JsonPrincipalAttributesProperties setState(AttributeRepositoryStates state) {
        this.state = state;
        return this;
    }
}

