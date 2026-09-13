/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.core.authentication;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Generated;
import org.apereo.cas.configuration.model.SpringResourceProperties;
import org.apereo.cas.configuration.model.core.authentication.AttributeRepositoryStates;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-person-directory", automated=true)
@JsonFilter(value="GroovyPrincipalAttributesProperties")
public class GroovyPrincipalAttributesProperties
extends SpringResourceProperties {
    private static final long serialVersionUID = 7901595963842506684L;
    private boolean caseInsensitive;
    private int order;
    private String id;
    private AttributeRepositoryStates state = AttributeRepositoryStates.ACTIVE;

    @Generated
    public boolean isCaseInsensitive() {
        return this.caseInsensitive;
    }

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
    public GroovyPrincipalAttributesProperties setCaseInsensitive(boolean caseInsensitive) {
        this.caseInsensitive = caseInsensitive;
        return this;
    }

    @Generated
    public GroovyPrincipalAttributesProperties setOrder(int order) {
        this.order = order;
        return this;
    }

    @Generated
    public GroovyPrincipalAttributesProperties setId(String id) {
        this.id = id;
        return this;
    }

    @Generated
    public GroovyPrincipalAttributesProperties setState(AttributeRepositoryStates state) {
        this.state = state;
        return this;
    }
}

