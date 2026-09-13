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
@JsonFilter(value="GrouperPrincipalAttributesProperties")
public class GrouperPrincipalAttributesProperties
implements Serializable {
    private static final long serialVersionUID = 7139471665871712818L;
    private int order;
    private AttributeRepositoryStates state = AttributeRepositoryStates.ACTIVE;
    private String id;
    private String subjectType = "SUBJECT_ID";
    private String usernameAttribute = "username";
    private Map<String, String> parameters = new HashMap<String, String>();

    @Generated
    public int getOrder() {
        return this.order;
    }

    @Generated
    public AttributeRepositoryStates getState() {
        return this.state;
    }

    @Generated
    public String getId() {
        return this.id;
    }

    @Generated
    public String getSubjectType() {
        return this.subjectType;
    }

    @Generated
    public String getUsernameAttribute() {
        return this.usernameAttribute;
    }

    @Generated
    public Map<String, String> getParameters() {
        return this.parameters;
    }

    @Generated
    public GrouperPrincipalAttributesProperties setOrder(int order) {
        this.order = order;
        return this;
    }

    @Generated
    public GrouperPrincipalAttributesProperties setState(AttributeRepositoryStates state) {
        this.state = state;
        return this;
    }

    @Generated
    public GrouperPrincipalAttributesProperties setId(String id) {
        this.id = id;
        return this;
    }

    @Generated
    public GrouperPrincipalAttributesProperties setSubjectType(String subjectType) {
        this.subjectType = subjectType;
        return this;
    }

    @Generated
    public GrouperPrincipalAttributesProperties setUsernameAttribute(String usernameAttribute) {
        this.usernameAttribute = usernameAttribute;
        return this;
    }

    @Generated
    public GrouperPrincipalAttributesProperties setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
        return this;
    }
}

