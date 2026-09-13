/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  lombok.Generated
 *  org.apereo.cas.services.RegisteredServiceAttributeFilter
 *  org.apereo.cas.util.RegexUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.services.support;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.Generated;
import org.apereo.cas.services.RegisteredServiceAttributeFilter;
import org.apereo.cas.util.RegexUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonInclude(value=JsonInclude.Include.NON_DEFAULT)
public class RegisteredServiceRegexAttributeFilter
implements RegisteredServiceAttributeFilter {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(RegisteredServiceRegexAttributeFilter.class);
    private static final long serialVersionUID = 403015306984610128L;
    @JsonIgnore
    private Pattern compiledPattern;
    private String pattern;
    private int order;

    @JsonCreator
    public RegisteredServiceRegexAttributeFilter(@JsonProperty(value="pattern") String regex) {
        this.compiledPattern = RegexUtils.createPattern((String)regex);
        this.pattern = regex;
    }

    public Map<String, List<Object>> filter(Map<String, List<Object>> givenAttributes) {
        HashMap<String, List<Object>> attributesToRelease = new HashMap<String, List<Object>>();
        givenAttributes.entrySet().stream().filter((? super T entry) -> {
            String attributeName = (String)entry.getKey();
            List attributeValue = (List)entry.getValue();
            LOGGER.debug("Received attribute [{}] with value [{}]", (Object)attributeName, (Object)attributeValue);
            return attributeValue != null;
        }).forEach(entry -> {
            String attributeName = (String)entry.getKey();
            List attributeValue = (List)entry.getValue();
            LOGGER.trace("Attribute value [{}] is a collection", (Object)attributeValue);
            List filteredAttributes = this.filterAttributes(attributeValue, attributeName);
            if (!filteredAttributes.isEmpty()) {
                attributesToRelease.put(attributeName, filteredAttributes);
            }
        });
        LOGGER.debug("Received [{}] attributes. Filtered and released [{}]", (Object)givenAttributes.size(), (Object)attributesToRelease.size());
        return attributesToRelease;
    }

    private List filterAttributes(List<Object> valuesToFilter, String attributeName) {
        return valuesToFilter.stream().filter(this::patternMatchesAttributeValue).peek(attributeValue -> this.logReleasedAttributeEntry(attributeName, attributeValue)).collect(Collectors.toList());
    }

    private boolean patternMatchesAttributeValue(Object value) {
        String matcher = value.toString();
        LOGGER.trace("Compiling a pattern matcher for [{}]", (Object)matcher);
        return this.compiledPattern.matcher(matcher).matches();
    }

    private void logReleasedAttributeEntry(String attributeName, Object attributeValue) {
        LOGGER.debug("The attribute value [{}] for attribute name [{}] matches the pattern [{}]. Releasing attribute...", new Object[]{attributeValue, attributeName, this.compiledPattern.pattern()});
    }

    @Generated
    public String toString() {
        return "RegisteredServiceRegexAttributeFilter(compiledPattern=" + this.compiledPattern + ", pattern=" + this.pattern + ", order=" + this.order + ")";
    }

    @JsonIgnore
    @Generated
    public void setCompiledPattern(Pattern compiledPattern) {
        this.compiledPattern = compiledPattern;
    }

    @Generated
    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    @Generated
    public void setOrder(int order) {
        this.order = order;
    }

    @Generated
    public RegisteredServiceRegexAttributeFilter() {
    }

    @Generated
    public Pattern getCompiledPattern() {
        return this.compiledPattern;
    }

    @Generated
    public String getPattern() {
        return this.pattern;
    }

    @Generated
    public int getOrder() {
        return this.order;
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RegisteredServiceRegexAttributeFilter)) {
            return false;
        }
        RegisteredServiceRegexAttributeFilter other = (RegisteredServiceRegexAttributeFilter)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.order != other.order) {
            return false;
        }
        String this$pattern = this.pattern;
        String other$pattern = other.pattern;
        return !(this$pattern == null ? other$pattern != null : !this$pattern.equals(other$pattern));
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof RegisteredServiceRegexAttributeFilter;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.order;
        String $pattern = this.pattern;
        result = result * 59 + ($pattern == null ? 43 : $pattern.hashCode());
        return result;
    }
}

