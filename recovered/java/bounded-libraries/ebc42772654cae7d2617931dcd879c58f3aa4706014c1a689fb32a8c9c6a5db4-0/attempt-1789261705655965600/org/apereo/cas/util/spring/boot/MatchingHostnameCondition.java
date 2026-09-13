/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 *  org.springframework.boot.autoconfigure.condition.ConditionOutcome
 *  org.springframework.boot.autoconfigure.condition.SpringBootCondition
 *  org.springframework.context.annotation.ConditionContext
 *  org.springframework.core.type.AnnotatedTypeMetadata
 */
package org.apereo.cas.util.spring.boot;

import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.util.InetAddressUtils;
import org.apereo.cas.util.RegexUtils;
import org.apereo.cas.util.spring.boot.ConditionalOnMatchingHostname;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class MatchingHostnameCondition
extends SpringBootCondition {
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String name = metadata.getAnnotationAttributes(ConditionalOnMatchingHostname.class.getName()).get("name").toString();
        String hostnameToMatch = context.getEnvironment().getProperty(name);
        if (StringUtils.isBlank((CharSequence)hostnameToMatch)) {
            return ConditionOutcome.match((String)("No hostname set with property: " + name));
        }
        if (RegexUtils.find(hostnameToMatch, InetAddressUtils.getCasServerHostName())) {
            return ConditionOutcome.match((String)("Hostname matches value for " + name));
        }
        return ConditionOutcome.noMatch((String)("Hostname doesn't match value for " + name));
    }
}

