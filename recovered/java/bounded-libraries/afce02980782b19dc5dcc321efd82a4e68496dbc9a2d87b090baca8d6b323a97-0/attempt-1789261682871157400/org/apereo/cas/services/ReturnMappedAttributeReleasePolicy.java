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
import java.util.List;
import java.util.Map;
import lombok.Generated;
import org.apereo.cas.services.BaseMappedAttributeReleasePolicy;
import org.apereo.cas.services.RegisteredServiceAttributeReleasePolicyContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonInclude(value=JsonInclude.Include.NON_DEFAULT)
public class ReturnMappedAttributeReleasePolicy
extends BaseMappedAttributeReleasePolicy {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(ReturnMappedAttributeReleasePolicy.class);
    private static final long serialVersionUID = -6249488544306639050L;

    @Override
    public Map<String, List<Object>> getAttributesInternal(RegisteredServiceAttributeReleasePolicyContext context, Map<String, List<Object>> attrs) {
        return this.authorizeMappedAttributes(context, attrs);
    }

    @Override
    public List<String> determineRequestedAttributeDefinitions(RegisteredServiceAttributeReleasePolicyContext context) {
        return new ArrayList<String>(this.getAllowedAttributes().keySet());
    }

    @Override
    @Generated
    public String toString() {
        return "ReturnMappedAttributeReleasePolicy(super=" + super.toString() + ")";
    }

    @Override
    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ReturnMappedAttributeReleasePolicy)) {
            return false;
        }
        ReturnMappedAttributeReleasePolicy other = (ReturnMappedAttributeReleasePolicy)o;
        if (!other.canEqual(this)) {
            return false;
        }
        return super.equals(o);
    }

    @Override
    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof ReturnMappedAttributeReleasePolicy;
    }

    @Override
    @Generated
    public int hashCode() {
        int result = super.hashCode();
        return result;
    }

    @Generated
    public ReturnMappedAttributeReleasePolicy() {
    }
}

