/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  lombok.Generated
 *  org.apache.commons.lang3.tuple.Pair
 *  org.apereo.cas.configuration.support.ExpressionLanguageCapable
 *  org.apereo.cas.services.RegisteredServiceAttributeReleasePolicyContext
 *  org.apereo.cas.util.CollectionUtils
 *  org.apereo.cas.util.spring.SpringExpressionLanguageValueResolver
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import lombok.Generated;
import org.apache.commons.lang3.tuple.Pair;
import org.apereo.cas.configuration.support.ExpressionLanguageCapable;
import org.apereo.cas.services.AbstractRegisteredServiceAttributeReleasePolicy;
import org.apereo.cas.services.RegisteredServiceAttributeReleasePolicyContext;
import org.apereo.cas.util.CollectionUtils;
import org.apereo.cas.util.spring.SpringExpressionLanguageValueResolver;

@JsonInclude(value=JsonInclude.Include.NON_DEFAULT)
public class ReturnStaticAttributeReleasePolicy
extends AbstractRegisteredServiceAttributeReleasePolicy {
    private static final long serialVersionUID = 1239257723778012771L;
    @JsonProperty(value="allowedAttributes")
    @ExpressionLanguageCapable
    private Map<String, List<Object>> allowedAttributes = new TreeMap<String, List<Object>>();

    @Override
    public Map<String, List<Object>> getAttributesInternal(RegisteredServiceAttributeReleasePolicyContext context, Map<String, List<Object>> resolvedAttributes) {
        return this.allowedAttributes.entrySet().stream().map(entry -> {
            List values = ((List)entry.getValue()).stream().map(value -> SpringExpressionLanguageValueResolver.getInstance().resolve(value.toString())).collect(Collectors.toList());
            return Pair.of((Object)((String)entry.getKey()), values);
        }).collect(Collectors.toMap(Pair::getKey, entry -> (List)CollectionUtils.toCollection((Object)entry.getValue(), ArrayList.class)));
    }

    @Override
    @Generated
    public String toString() {
        return "ReturnStaticAttributeReleasePolicy(super=" + super.toString() + ", allowedAttributes=" + this.allowedAttributes + ")";
    }

    @Override
    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ReturnStaticAttributeReleasePolicy)) {
            return false;
        }
        ReturnStaticAttributeReleasePolicy other = (ReturnStaticAttributeReleasePolicy)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Map<String, List<Object>> this$allowedAttributes = this.allowedAttributes;
        Map<String, List<Object>> other$allowedAttributes = other.allowedAttributes;
        return !(this$allowedAttributes == null ? other$allowedAttributes != null : !((Object)this$allowedAttributes).equals(other$allowedAttributes));
    }

    @Override
    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof ReturnStaticAttributeReleasePolicy;
    }

    @Override
    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        Map<String, List<Object>> $allowedAttributes = this.allowedAttributes;
        result = result * 59 + ($allowedAttributes == null ? 43 : ((Object)$allowedAttributes).hashCode());
        return result;
    }

    @Generated
    public Map<String, List<Object>> getAllowedAttributes() {
        return this.allowedAttributes;
    }

    @JsonProperty(value="allowedAttributes")
    @Generated
    public void setAllowedAttributes(Map<String, List<Object>> allowedAttributes) {
        this.allowedAttributes = allowedAttributes;
    }

    @Generated
    public ReturnStaticAttributeReleasePolicy() {
    }
}

