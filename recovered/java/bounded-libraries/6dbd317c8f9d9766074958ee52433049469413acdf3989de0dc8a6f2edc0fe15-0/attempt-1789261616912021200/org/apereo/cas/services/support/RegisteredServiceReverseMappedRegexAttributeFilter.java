/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  lombok.Generated
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.services.support;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.Generated;
import org.apereo.cas.services.support.RegisteredServiceMappedRegexAttributeFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonInclude(value=JsonInclude.Include.NON_DEFAULT)
public class RegisteredServiceReverseMappedRegexAttributeFilter
extends RegisteredServiceMappedRegexAttributeFilter {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(RegisteredServiceReverseMappedRegexAttributeFilter.class);
    private static final long serialVersionUID = 852145306984610128L;

    @Override
    protected List<Object> filterAttributeValuesByPattern(Set<Object> attributeValues, Pattern pattern) {
        return attributeValues.stream().filter((? super T v) -> {
            LOGGER.debug("Matching attribute value [{}] against pattern [{}]", v, (Object)pattern.pattern());
            Matcher matcher = pattern.matcher(v.toString());
            if (this.isCompleteMatch()) {
                return !matcher.matches();
            }
            return !matcher.find();
        }).collect(Collectors.toList());
    }
}

