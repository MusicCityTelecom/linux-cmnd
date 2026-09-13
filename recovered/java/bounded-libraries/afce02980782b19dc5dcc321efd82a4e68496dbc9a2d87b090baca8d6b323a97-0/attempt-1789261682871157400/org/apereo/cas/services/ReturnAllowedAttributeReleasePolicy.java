/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  lombok.Generated
 *  org.apereo.cas.services.RegisteredServiceAttributeReleasePolicyContext
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import lombok.Generated;
import org.apereo.cas.services.AbstractRegisteredServiceAttributeReleasePolicy;
import org.apereo.cas.services.RegisteredServiceAttributeReleasePolicyContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonInclude(value=JsonInclude.Include.NON_DEFAULT)
public class ReturnAllowedAttributeReleasePolicy
extends AbstractRegisteredServiceAttributeReleasePolicy {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(ReturnAllowedAttributeReleasePolicy.class);
    private static final long serialVersionUID = -5771481877391140569L;
    private List<String> allowedAttributes = new ArrayList<String>(0);

    @Override
    public Map<String, List<Object>> getAttributesInternal(RegisteredServiceAttributeReleasePolicyContext context, Map<String, List<Object>> attributes) {
        return this.authorizeReleaseOfAllowedAttributes(context, attributes);
    }

    protected Map<String, List<Object>> authorizeReleaseOfAllowedAttributes(RegisteredServiceAttributeReleasePolicyContext context, Map<String, List<Object>> attributes) {
        TreeMap<String, List<Object>> resolvedAttributes = new TreeMap<String, List<Object>>(String.CASE_INSENSITIVE_ORDER);
        resolvedAttributes.putAll(attributes);
        HashMap<String, List<Object>> attributesToRelease = new HashMap<String, List<Object>>();
        this.getAllowedAttributes().stream().filter(resolvedAttributes::containsKey).forEach(attr -> {
            LOGGER.debug("Found attribute [{}] in the list of allowed attributes", attr);
            attributesToRelease.put((String)attr, (List)resolvedAttributes.get(attr));
        });
        return attributesToRelease;
    }

    @Override
    protected List<String> determineRequestedAttributeDefinitions(RegisteredServiceAttributeReleasePolicyContext context) {
        return this.getAllowedAttributes();
    }

    @Override
    @Generated
    public String toString() {
        return "ReturnAllowedAttributeReleasePolicy(super=" + super.toString() + ", allowedAttributes=" + this.allowedAttributes + ")";
    }

    @Generated
    public List<String> getAllowedAttributes() {
        return this.allowedAttributes;
    }

    @Generated
    public ReturnAllowedAttributeReleasePolicy setAllowedAttributes(List<String> allowedAttributes) {
        this.allowedAttributes = allowedAttributes;
        return this;
    }

    @Override
    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ReturnAllowedAttributeReleasePolicy)) {
            return false;
        }
        ReturnAllowedAttributeReleasePolicy other = (ReturnAllowedAttributeReleasePolicy)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        List<String> this$allowedAttributes = this.allowedAttributes;
        List<String> other$allowedAttributes = other.allowedAttributes;
        return !(this$allowedAttributes == null ? other$allowedAttributes != null : !((Object)this$allowedAttributes).equals(other$allowedAttributes));
    }

    @Override
    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof ReturnAllowedAttributeReleasePolicy;
    }

    @Override
    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        List<String> $allowedAttributes = this.allowedAttributes;
        result = result * 59 + ($allowedAttributes == null ? 43 : ((Object)$allowedAttributes).hashCode());
        return result;
    }

    @Generated
    public ReturnAllowedAttributeReleasePolicy() {
    }

    @Generated
    public ReturnAllowedAttributeReleasePolicy(List<String> allowedAttributes) {
        this.allowedAttributes = allowedAttributes;
    }
}

