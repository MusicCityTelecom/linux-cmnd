/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.ldap;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.authentication.AttributeRepositoryStates;
import org.apereo.cas.configuration.model.support.ldap.AbstractLdapSearchProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-person-directory", automated=true)
@JsonFilter(value="LdapPrincipalAttributesProperties")
public class LdapPrincipalAttributesProperties
extends AbstractLdapSearchProperties {
    private static final long serialVersionUID = 5760065368731012063L;
    private int order;
    private Map<String, String> attributes = new LinkedHashMap<String, String>();
    private String id;
    private AttributeRepositoryStates state = AttributeRepositoryStates.ACTIVE;
    private boolean useAllQueryAttributes = true;
    private Map<String, String> queryAttributes = new HashMap<String, String>(0);

    @Generated
    public int getOrder() {
        return this.order;
    }

    @Generated
    public Map<String, String> getAttributes() {
        return this.attributes;
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
    public boolean isUseAllQueryAttributes() {
        return this.useAllQueryAttributes;
    }

    @Generated
    public Map<String, String> getQueryAttributes() {
        return this.queryAttributes;
    }

    @Generated
    public LdapPrincipalAttributesProperties setOrder(int order) {
        this.order = order;
        return this;
    }

    @Generated
    public LdapPrincipalAttributesProperties setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
        return this;
    }

    @Generated
    public LdapPrincipalAttributesProperties setId(String id) {
        this.id = id;
        return this;
    }

    @Generated
    public LdapPrincipalAttributesProperties setState(AttributeRepositoryStates state) {
        this.state = state;
        return this;
    }

    @Generated
    public LdapPrincipalAttributesProperties setUseAllQueryAttributes(boolean useAllQueryAttributes) {
        this.useAllQueryAttributes = useAllQueryAttributes;
        return this;
    }

    @Generated
    public LdapPrincipalAttributesProperties setQueryAttributes(Map<String, String> queryAttributes) {
        this.queryAttributes = queryAttributes;
        return this;
    }
}

