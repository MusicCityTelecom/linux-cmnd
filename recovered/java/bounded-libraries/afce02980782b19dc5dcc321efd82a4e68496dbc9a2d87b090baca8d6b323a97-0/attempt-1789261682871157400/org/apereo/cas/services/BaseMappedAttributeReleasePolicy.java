/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  lombok.Generated
 *  org.apereo.cas.services.RegisteredServiceAttributeReleasePolicyContext
 *  org.apereo.cas.util.CollectionUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import lombok.Generated;
import org.apereo.cas.authentication.AttributeMappingRequest;
import org.apereo.cas.authentication.PrincipalAttributesMapper;
import org.apereo.cas.services.AbstractRegisteredServiceAttributeReleasePolicy;
import org.apereo.cas.services.RegisteredServiceAttributeReleasePolicyContext;
import org.apereo.cas.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonInclude(value=JsonInclude.Include.NON_DEFAULT)
public abstract class BaseMappedAttributeReleasePolicy
extends AbstractRegisteredServiceAttributeReleasePolicy {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseMappedAttributeReleasePolicy.class);
    private static final long serialVersionUID = -6249488544306639050L;
    @JsonProperty(value="allowedAttributes")
    private Map<String, Object> allowedAttributes = new TreeMap<String, Object>();

    public Map<String, Object> getAllowedAttributes() {
        return new TreeMap<String, Object>(this.allowedAttributes);
    }

    protected Map<String, List<Object>> authorizeMappedAttributes(RegisteredServiceAttributeReleasePolicyContext context, Map<String, List<Object>> attributes) {
        TreeMap<String, List<Object>> resolvedAttributes = new TreeMap<String, List<Object>>(String.CASE_INSENSITIVE_ORDER);
        resolvedAttributes.putAll(attributes);
        resolvedAttributes.putAll(context.getReleasingAttributes());
        HashMap<String, List<Object>> attributesToRelease = new HashMap<String, List<Object>>();
        this.getAllowedAttributes().forEach((attributeName, value) -> {
            List mappedAttributes = CollectionUtils.wrap((Object)value);
            LOGGER.trace("Attempting to map allowed attribute name [{}]", attributeName);
            List attributeValue = (List)resolvedAttributes.get(attributeName);
            mappedAttributes.forEach(mapped -> {
                String mappedAttributeName = mapped.toString();
                LOGGER.debug("Mapping attribute [{}] to [{}] with value [{}]", new Object[]{attributeName, mappedAttributeName, attributeValue});
                Object mappingRequest = ((AttributeMappingRequest.AttributeMappingRequestBuilder)((AttributeMappingRequest.AttributeMappingRequestBuilder)((AttributeMappingRequest.AttributeMappingRequestBuilder)((AttributeMappingRequest.AttributeMappingRequestBuilder)AttributeMappingRequest.builder().attributeName((String)attributeName)).mappedAttributeName(mappedAttributeName)).attributeValue(attributeValue)).resolvedAttributes(resolvedAttributes)).build();
                Map<String, List<Object>> mappingResults = PrincipalAttributesMapper.defaultMapper().map((AttributeMappingRequest)mappingRequest);
                attributesToRelease.putAll(mappingResults);
            });
        });
        return attributesToRelease;
    }

    @Override
    @Generated
    public String toString() {
        return "BaseMappedAttributeReleasePolicy(super=" + super.toString() + ", allowedAttributes=" + this.allowedAttributes + ")";
    }

    @JsonProperty(value="allowedAttributes")
    @Generated
    public BaseMappedAttributeReleasePolicy setAllowedAttributes(Map<String, Object> allowedAttributes) {
        this.allowedAttributes = allowedAttributes;
        return this;
    }

    @Override
    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof BaseMappedAttributeReleasePolicy)) {
            return false;
        }
        BaseMappedAttributeReleasePolicy other = (BaseMappedAttributeReleasePolicy)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Map<String, Object> this$allowedAttributes = this.allowedAttributes;
        Map<String, Object> other$allowedAttributes = other.allowedAttributes;
        return !(this$allowedAttributes == null ? other$allowedAttributes != null : !((Object)this$allowedAttributes).equals(other$allowedAttributes));
    }

    @Override
    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof BaseMappedAttributeReleasePolicy;
    }

    @Override
    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        Map<String, Object> $allowedAttributes = this.allowedAttributes;
        result = result * 59 + ($allowedAttributes == null ? 43 : ((Object)$allowedAttributes).hashCode());
        return result;
    }

    @Generated
    protected BaseMappedAttributeReleasePolicy() {
    }
}

