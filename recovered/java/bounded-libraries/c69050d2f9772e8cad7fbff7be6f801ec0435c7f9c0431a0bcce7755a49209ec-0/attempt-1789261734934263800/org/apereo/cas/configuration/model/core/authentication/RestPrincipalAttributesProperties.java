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
import org.apereo.cas.configuration.model.RestEndpointProperties;
import org.apereo.cas.configuration.model.core.authentication.AttributeRepositoryStates;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-person-directory", automated=true)
@JsonFilter(value="RestPrincipalAttributesProperties")
public class RestPrincipalAttributesProperties
extends RestEndpointProperties {
    private static final long serialVersionUID = -30055974448426360L;
    private int order;
    private boolean caseInsensitive;
    private String id;
    private String usernameAttribute = "username";
    private AttributeRepositoryStates state = AttributeRepositoryStates.ACTIVE;

    @Generated
    public int getOrder() {
        return this.order;
    }

    @Generated
    public boolean isCaseInsensitive() {
        return this.caseInsensitive;
    }

    @Generated
    public String getId() {
        return this.id;
    }

    @Generated
    public String getUsernameAttribute() {
        return this.usernameAttribute;
    }

    @Generated
    public AttributeRepositoryStates getState() {
        return this.state;
    }

    @Generated
    public RestPrincipalAttributesProperties setOrder(int order) {
        this.order = order;
        return this;
    }

    @Generated
    public RestPrincipalAttributesProperties setCaseInsensitive(boolean caseInsensitive) {
        this.caseInsensitive = caseInsensitive;
        return this;
    }

    @Generated
    public RestPrincipalAttributesProperties setId(String id) {
        this.id = id;
        return this;
    }

    @Generated
    public RestPrincipalAttributesProperties setUsernameAttribute(String usernameAttribute) {
        this.usernameAttribute = usernameAttribute;
        return this;
    }

    @Generated
    public RestPrincipalAttributesProperties setState(AttributeRepositoryStates state) {
        this.state = state;
        return this;
    }
}

