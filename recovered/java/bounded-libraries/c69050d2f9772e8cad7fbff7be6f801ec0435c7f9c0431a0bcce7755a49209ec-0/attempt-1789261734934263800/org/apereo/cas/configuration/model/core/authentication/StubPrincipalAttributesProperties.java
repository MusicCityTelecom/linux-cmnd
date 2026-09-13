/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.core.authentication;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.authentication.AttributeRepositoryStates;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-person-directory", automated=true)
@JsonFilter(value="StubPrincipalAttributesProperties")
public class StubPrincipalAttributesProperties
implements Serializable {
    private static final long serialVersionUID = 7017508256487553063L;
    private Map<String, String> attributes = new HashMap<String, String>(0);
    private String id;
    private int order = Integer.MAX_VALUE;
    private AttributeRepositoryStates state = AttributeRepositoryStates.ACTIVE;

    @Generated
    public Map<String, String> getAttributes() {
        return this.attributes;
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
    public StubPrincipalAttributesProperties setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
        return this;
    }

    @Generated
    public StubPrincipalAttributesProperties setId(String id) {
        this.id = id;
        return this;
    }

    @Generated
    public StubPrincipalAttributesProperties setOrder(int order) {
        this.order = order;
        return this;
    }

    @Generated
    public StubPrincipalAttributesProperties setState(AttributeRepositoryStates state) {
        this.state = state;
        return this;
    }
}

