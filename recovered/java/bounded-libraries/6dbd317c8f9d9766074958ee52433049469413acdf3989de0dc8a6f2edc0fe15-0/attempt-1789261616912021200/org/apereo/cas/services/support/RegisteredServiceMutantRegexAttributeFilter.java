/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.commons.lang3.tuple.Pair
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apereo.cas.services.support.RegisteredServiceMappedRegexAttributeFilter;
import org.apereo.cas.util.CollectionUtils;
import org.apereo.cas.util.RegexUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonInclude(value=JsonInclude.Include.NON_DEFAULT)
public class RegisteredServiceMutantRegexAttributeFilter
extends RegisteredServiceMappedRegexAttributeFilter {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(RegisteredServiceMutantRegexAttributeFilter.class);
    private static final long serialVersionUID = 543145306984660628L;

    @Override
    public Map<String, List<Object>> filter(Map<String, List<Object>> givenAttributes) {
        HashMap<String, List<Object>> attributesToRelease = new HashMap<String, List<Object>>();
        givenAttributes.entrySet().stream().filter(this.filterProvidedGivenAttributes()).forEach(entry -> {
            String attributeName = (String)entry.getKey();
            if (this.getPatterns().containsKey(attributeName)) {
                Set attributeValues = CollectionUtils.toCollection(entry.getValue());
                LOGGER.trace("Found attribute [{}] in pattern definitions with value(s) [{}]", (Object)attributeName, (Object)attributeValues);
                Collection<Pair<Pattern, String>> patterns = this.createPatternsAndReturnValue(attributeName);
                List<Object> finalValues = patterns.stream().map(patternDefinition -> {
                    Pattern pattern = (Pattern)patternDefinition.getLeft();
                    LOGGER.trace("Found attribute [{}] in the pattern definitions. Processing pattern [{}]", (Object)attributeName, (Object)pattern.pattern());
                    List<Object> filteredValues = this.filterAndMapAttributeValuesByPattern(attributeValues, pattern, (String)patternDefinition.getValue());
                    LOGGER.debug("Filtered attribute values for [{}] are [{}]", (Object)attributeName, filteredValues);
                    return filteredValues;
                }).flatMap(Collection::stream).collect(Collectors.toList());
                if (finalValues.isEmpty()) {
                    LOGGER.trace("Attribute [{}] has no values remaining and shall be excluded", (Object)attributeName);
                } else {
                    this.collectAttributeWithFilteredValues(attributesToRelease, attributeName, finalValues);
                }
            } else {
                this.handleUnmappedAttribute(attributesToRelease, (String)entry.getKey(), entry.getValue());
            }
        });
        LOGGER.debug("Received [{}] attributes. Filtered and released [{}]", (Object)givenAttributes.size(), (Object)attributesToRelease.size());
        return attributesToRelease;
    }

    private Collection<Pair<Pattern, String>> createPatternsAndReturnValue(String attributeName) {
        Object patternDef = this.getPatterns().get(attributeName);
        ArrayList patternAndReturnVal = new ArrayList(CollectionUtils.toCollection((Object)patternDef));
        return patternAndReturnVal.stream().map(this::mapPattern).collect(Collectors.toList());
    }

    private List<Object> filterAndMapAttributeValuesByPattern(Set<Object> attributeValues, Pattern pattern, String returnValue) {
        ArrayList<Object> values = new ArrayList<Object>(attributeValues.size());
        attributeValues.forEach(v -> {
            boolean matches;
            Matcher matcher = pattern.matcher(v.toString());
            boolean bl = matches = this.isCompleteMatch() ? matcher.matches() : matcher.find();
            if (matches) {
                LOGGER.debug("Found a successful match for [{}] while filtering attribute values with [{}]", v, (Object)pattern.pattern());
                if (StringUtils.isNotBlank((CharSequence)returnValue)) {
                    int count = matcher.groupCount();
                    String resultValue = returnValue;
                    for (int i = 1; i <= count; ++i) {
                        resultValue = resultValue.replace("$" + i, matcher.group(i));
                    }
                    LOGGER.debug("Final attribute value after template processing for return is [{}]", (Object)resultValue);
                    values.add(resultValue);
                } else {
                    values.add(v);
                }
            }
        });
        return values;
    }

    private Pair<Pattern, String> mapPattern(Object p) {
        String patternValue = p.toString();
        int index = patternValue.indexOf("->");
        if (index != -1) {
            String patternStr = patternValue.substring(0, index).trim();
            Pattern pattern = RegexUtils.createPattern((String)patternStr, (int)(this.isCaseInsensitive() ? 2 : 0));
            String returnValue = patternValue.substring(index + 2).trim();
            LOGGER.debug("Created attribute filter pattern [{}] with the mapped return value template [{}]", (Object)patternStr, (Object)returnValue);
            return Pair.of((Object)pattern, (Object)returnValue);
        }
        Pattern pattern = RegexUtils.createPattern((String)patternValue.trim(), (int)(this.isCaseInsensitive() ? 2 : 0));
        LOGGER.debug("Created attribute filter pattern [{}] without a mapped return value template", (Object)pattern.pattern());
        return Pair.of((Object)pattern, (Object)"");
    }

    @Generated
    public RegisteredServiceMutantRegexAttributeFilter() {
    }
}

