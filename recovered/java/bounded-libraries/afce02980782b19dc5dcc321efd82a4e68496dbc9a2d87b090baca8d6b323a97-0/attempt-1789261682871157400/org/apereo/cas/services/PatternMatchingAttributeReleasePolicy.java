/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  lombok.Generated
 *  org.apache.commons.lang3.tuple.Pair
 *  org.apereo.cas.services.RegisteredServiceAttributeReleasePolicyContext
 *  org.apereo.cas.util.RegexUtils
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.Generated;
import org.apache.commons.lang3.tuple.Pair;
import org.apereo.cas.services.AbstractRegisteredServiceAttributeReleasePolicy;
import org.apereo.cas.services.RegisteredServiceAttributeReleasePolicyContext;
import org.apereo.cas.util.RegexUtils;

@JsonInclude(value=JsonInclude.Include.NON_DEFAULT)
public class PatternMatchingAttributeReleasePolicy
extends AbstractRegisteredServiceAttributeReleasePolicy {
    private static final long serialVersionUID = -2168544657991721919L;
    private static final Pattern PATTERN_TRANSFORM_GROUPS = RegexUtils.createPattern((String)"\\$\\{(\\d+)\\}");
    private Map<String, Rule> allowedAttributes = new TreeMap<String, Rule>();

    @JsonCreator
    public PatternMatchingAttributeReleasePolicy(@JsonProperty(value="allowedAttributes") Map<String, Rule> attributes) {
        this.allowedAttributes = attributes;
    }

    @Override
    public Map<String, List<Object>> getAttributesInternal(RegisteredServiceAttributeReleasePolicyContext context, Map<String, List<Object>> attributes) {
        return this.allowedAttributes.entrySet().stream().filter(entry -> attributes.containsKey(entry.getKey())).map(entry -> {
            Rule rule = (Rule)entry.getValue();
            Pattern valuePattern = RegexUtils.createPattern((String)rule.getPattern());
            Matcher transformPattern = PATTERN_TRANSFORM_GROUPS.matcher(rule.getTransform());
            List attributeValues = (List)attributes.get(entry.getKey());
            List transformedValues = attributeValues.stream().map(value -> {
                String transformedValue = rule.getTransform();
                Matcher matcher = valuePattern.matcher(value.toString());
                if (matcher.find()) {
                    while (transformPattern.find()) {
                        int group = Integer.parseInt(transformPattern.group(1));
                        String target = String.format("${%s}", group);
                        transformedValue = transformedValue.replace(target, matcher.group(group));
                    }
                }
                transformPattern.reset();
                return transformedValue;
            }).collect(Collectors.toList());
            return Pair.of((Object)((String)entry.getKey()), transformedValues);
        }).filter(pair -> !((List)pair.getValue()).isEmpty()).collect(Collectors.toMap(Pair::getKey, Pair::getValue));
    }

    @Override
    @Generated
    public String toString() {
        return "PatternMatchingAttributeReleasePolicy(super=" + super.toString() + ", allowedAttributes=" + this.allowedAttributes + ")";
    }

    @Override
    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof PatternMatchingAttributeReleasePolicy)) {
            return false;
        }
        PatternMatchingAttributeReleasePolicy other = (PatternMatchingAttributeReleasePolicy)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Map<String, Rule> this$allowedAttributes = this.allowedAttributes;
        Map<String, Rule> other$allowedAttributes = other.allowedAttributes;
        return !(this$allowedAttributes == null ? other$allowedAttributes != null : !((Object)this$allowedAttributes).equals(other$allowedAttributes));
    }

    @Override
    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof PatternMatchingAttributeReleasePolicy;
    }

    @Override
    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        Map<String, Rule> $allowedAttributes = this.allowedAttributes;
        result = result * 59 + ($allowedAttributes == null ? 43 : ((Object)$allowedAttributes).hashCode());
        return result;
    }

    @Generated
    public Map<String, Rule> getAllowedAttributes() {
        return this.allowedAttributes;
    }

    @Generated
    public PatternMatchingAttributeReleasePolicy setAllowedAttributes(Map<String, Rule> allowedAttributes) {
        this.allowedAttributes = allowedAttributes;
        return this;
    }

    @Generated
    public PatternMatchingAttributeReleasePolicy() {
    }

    @JsonInclude(value=JsonInclude.Include.NON_DEFAULT)
    @JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
    public static class Rule
    implements Serializable {
        private static final long serialVersionUID = 3111910879481087570L;
        private String pattern;
        private String transform;

        @Generated
        public String toString() {
            return "PatternMatchingAttributeReleasePolicy.Rule(pattern=" + this.pattern + ", transform=" + this.transform + ")";
        }

        @Generated
        public String getPattern() {
            return this.pattern;
        }

        @Generated
        public String getTransform() {
            return this.transform;
        }

        @Generated
        public Rule setPattern(String pattern) {
            this.pattern = pattern;
            return this;
        }

        @Generated
        public Rule setTransform(String transform) {
            this.transform = transform;
            return this;
        }

        @Generated
        public Rule() {
        }

        @Generated
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof Rule)) {
                return false;
            }
            Rule other = (Rule)o;
            if (!other.canEqual(this)) {
                return false;
            }
            String this$pattern = this.pattern;
            String other$pattern = other.pattern;
            if (this$pattern == null ? other$pattern != null : !this$pattern.equals(other$pattern)) {
                return false;
            }
            String this$transform = this.transform;
            String other$transform = other.transform;
            return !(this$transform == null ? other$transform != null : !this$transform.equals(other$transform));
        }

        @Generated
        protected boolean canEqual(Object other) {
            return other instanceof Rule;
        }

        @Generated
        public int hashCode() {
            int PRIME = 59;
            int result = 1;
            String $pattern = this.pattern;
            result = result * 59 + ($pattern == null ? 43 : $pattern.hashCode());
            String $transform = this.transform;
            result = result * 59 + ($transform == null ? 43 : $transform.hashCode());
            return result;
        }
    }
}

