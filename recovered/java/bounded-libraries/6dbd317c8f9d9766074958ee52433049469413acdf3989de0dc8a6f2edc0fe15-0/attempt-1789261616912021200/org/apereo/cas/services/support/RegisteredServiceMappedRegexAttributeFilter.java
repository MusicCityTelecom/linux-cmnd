/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  lombok.Generated
 *  org.apereo.cas.services.RegisteredServiceAttributeFilter
 *  org.apereo.cas.util.CollectionUtils
 *  org.apereo.cas.util.RegexUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.services.support;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.Generated;
import org.apereo.cas.services.RegisteredServiceAttributeFilter;
import org.apereo.cas.util.CollectionUtils;
import org.apereo.cas.util.RegexUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonInclude(value=JsonInclude.Include.NON_DEFAULT)
public class RegisteredServiceMappedRegexAttributeFilter
implements RegisteredServiceAttributeFilter {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(RegisteredServiceMappedRegexAttributeFilter.class);
    private static final long serialVersionUID = 852145306984610128L;
    private Map<String, Object> patterns;
    private boolean excludeUnmappedAttributes;
    private boolean caseInsensitive = true;
    private boolean completeMatch;
    private int order;

    public RegisteredServiceMappedRegexAttributeFilter(Map<String, Object> patterns) {
        this.patterns = patterns;
    }

    public Map<String, List<Object>> filter(Map<String, List<Object>> givenAttributes) {
        HashMap<String, List<Object>> attributesToRelease = new HashMap<String, List<Object>>();
        givenAttributes.entrySet().stream().filter(this.filterProvidedGivenAttributes()).forEach(entry -> {
            String attributeName = (String)entry.getKey();
            if (this.patterns.containsKey(attributeName)) {
                Set attributeValues = CollectionUtils.toCollection(entry.getValue());
                LOGGER.debug("Found attribute [{}] in pattern definitions with value(s) [{}]", (Object)attributeName, (Object)attributeValues);
                Collection<Pattern> attributePatterns = this.createPatternForMappedAttribute(attributeName);
                attributePatterns.forEach(pattern -> {
                    LOGGER.debug("Found attribute [{}] in the pattern definitions. Processing pattern [{}]", (Object)attributeName, (Object)pattern.pattern());
                    List<Object> filteredValues = this.filterAttributeValuesByPattern(attributeValues, (Pattern)pattern);
                    LOGGER.debug("Filtered attribute values for [{}] are [{}]", (Object)attributeName, filteredValues);
                    if (filteredValues.isEmpty()) {
                        LOGGER.debug("Attribute [{}] has no values remaining and shall be excluded", (Object)attributeName);
                    } else {
                        this.collectAttributeWithFilteredValues(attributesToRelease, attributeName, filteredValues);
                    }
                });
            } else {
                this.handleUnmappedAttribute(attributesToRelease, (String)entry.getKey(), entry.getValue());
            }
        });
        LOGGER.debug("Received [{}] attributes. Filtered and released [{}]", (Object)givenAttributes.size(), (Object)attributesToRelease.size());
        return attributesToRelease;
    }

    protected void handleUnmappedAttribute(Map<String, List<Object>> attributesToRelease, String attributeName, Object attributeValue) {
        LOGGER.debug("Found attribute [{}] that is not defined in pattern definitions", (Object)attributeName);
        if (this.excludeUnmappedAttributes) {
            LOGGER.debug("Excluding attribute [{}] given unmatched attributes are to be excluded", (Object)attributeName);
        } else {
            LOGGER.debug("Added unmatched attribute [{}] with value(s) [{}]", (Object)attributeName, attributeValue);
            attributesToRelease.put(attributeName, (List)CollectionUtils.toCollection((Object)attributeValue, ArrayList.class));
        }
    }

    protected Collection<Pattern> createPatternForMappedAttribute(String attributeName) {
        String matchingPattern = this.patterns.get(attributeName).toString();
        Pattern pattern = RegexUtils.createPattern((String)matchingPattern, (int)(this.caseInsensitive ? 2 : 0));
        LOGGER.debug("Created pattern for mapped attribute filter [{}]", (Object)pattern.pattern());
        return CollectionUtils.wrap((Object)pattern);
    }

    protected void collectAttributeWithFilteredValues(Map<String, List<Object>> attributesToRelease, String attributeName, List<Object> filteredValues) {
        attributesToRelease.put(attributeName, filteredValues);
    }

    protected Predicate<Map.Entry<String, List<Object>>> filterProvidedGivenAttributes() {
        return entry -> {
            String attributeName = (String)entry.getKey();
            List attributeValue = (List)entry.getValue();
            LOGGER.debug("Received attribute [{}] with value(s) [{}]", (Object)attributeName, (Object)attributeValue);
            return attributeValue != null;
        };
    }

    protected List<Object> filterAttributeValuesByPattern(Set<Object> attributeValues, Pattern pattern) {
        return attributeValues.stream().filter((? super T v) -> RegexUtils.matches((Pattern)pattern, (String)v.toString(), (boolean)this.completeMatch)).collect(Collectors.toList());
    }

    @Generated
    public Map<String, Object> getPatterns() {
        return this.patterns;
    }

    @Generated
    public boolean isExcludeUnmappedAttributes() {
        return this.excludeUnmappedAttributes;
    }

    @Generated
    public boolean isCaseInsensitive() {
        return this.caseInsensitive;
    }

    @Generated
    public boolean isCompleteMatch() {
        return this.completeMatch;
    }

    @Generated
    public int getOrder() {
        return this.order;
    }

    @Generated
    public void setPatterns(Map<String, Object> patterns) {
        this.patterns = patterns;
    }

    @Generated
    public void setExcludeUnmappedAttributes(boolean excludeUnmappedAttributes) {
        this.excludeUnmappedAttributes = excludeUnmappedAttributes;
    }

    @Generated
    public void setCaseInsensitive(boolean caseInsensitive) {
        this.caseInsensitive = caseInsensitive;
    }

    @Generated
    public void setCompleteMatch(boolean completeMatch) {
        this.completeMatch = completeMatch;
    }

    @Generated
    public void setOrder(int order) {
        this.order = order;
    }

    @Generated
    public RegisteredServiceMappedRegexAttributeFilter() {
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RegisteredServiceMappedRegexAttributeFilter)) {
            return false;
        }
        RegisteredServiceMappedRegexAttributeFilter other = (RegisteredServiceMappedRegexAttributeFilter)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.excludeUnmappedAttributes != other.excludeUnmappedAttributes) {
            return false;
        }
        if (this.caseInsensitive != other.caseInsensitive) {
            return false;
        }
        if (this.completeMatch != other.completeMatch) {
            return false;
        }
        if (this.order != other.order) {
            return false;
        }
        Map<String, Object> this$patterns = this.patterns;
        Map<String, Object> other$patterns = other.patterns;
        return !(this$patterns == null ? other$patterns != null : !((Object)this$patterns).equals(other$patterns));
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof RegisteredServiceMappedRegexAttributeFilter;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + (this.excludeUnmappedAttributes ? 79 : 97);
        result = result * 59 + (this.caseInsensitive ? 79 : 97);
        result = result * 59 + (this.completeMatch ? 79 : 97);
        result = result * 59 + this.order;
        Map<String, Object> $patterns = this.patterns;
        result = result * 59 + ($patterns == null ? 43 : ((Object)$patterns).hashCode());
        return result;
    }
}

