/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.syncope;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.util.HashMap;
import java.util.Map;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.authentication.AttributeRepositoryStates;
import org.apereo.cas.configuration.model.support.syncope.AbstractSyncopeProperties;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-syncope-authentication")
@JsonFilter(value="SyncopePrincipalAttributesProperties")
public class SyncopePrincipalAttributesProperties
extends AbstractSyncopeProperties {
    private static final long serialVersionUID = 98257222402164L;
    @RequiredProperty
    protected String searchFilter;
    private String id;
    private int order;
    private AttributeRepositoryStates state = AttributeRepositoryStates.ACTIVE;
    @RequiredProperty
    private String basicAuthUsername;
    @RequiredProperty
    private String basicAuthPassword;
    private Map<String, String> headers = new HashMap<String, String>();

    @Generated
    public String getSearchFilter() {
        return this.searchFilter;
    }

    @Generated
    public String getId() {
        return this.id;
    }

    @Generated
    public int getOrder() {
        return this.order;
    }

    @Generated
    public AttributeRepositoryStates getState() {
        return this.state;
    }

    @Generated
    public String getBasicAuthUsername() {
        return this.basicAuthUsername;
    }

    @Generated
    public String getBasicAuthPassword() {
        return this.basicAuthPassword;
    }

    @Generated
    public Map<String, String> getHeaders() {
        return this.headers;
    }

    @Generated
    public SyncopePrincipalAttributesProperties setSearchFilter(String searchFilter) {
        this.searchFilter = searchFilter;
        return this;
    }

    @Generated
    public SyncopePrincipalAttributesProperties setId(String id) {
        this.id = id;
        return this;
    }

    @Generated
    public SyncopePrincipalAttributesProperties setOrder(int order) {
        this.order = order;
        return this;
    }

    @Generated
    public SyncopePrincipalAttributesProperties setState(AttributeRepositoryStates state) {
        this.state = state;
        return this;
    }

    @Generated
    public SyncopePrincipalAttributesProperties setBasicAuthUsername(String basicAuthUsername) {
        this.basicAuthUsername = basicAuthUsername;
        return this;
    }

    @Generated
    public SyncopePrincipalAttributesProperties setBasicAuthPassword(String basicAuthPassword) {
        this.basicAuthPassword = basicAuthPassword;
        return this;
    }

    @Generated
    public SyncopePrincipalAttributesProperties setHeaders(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }
}

