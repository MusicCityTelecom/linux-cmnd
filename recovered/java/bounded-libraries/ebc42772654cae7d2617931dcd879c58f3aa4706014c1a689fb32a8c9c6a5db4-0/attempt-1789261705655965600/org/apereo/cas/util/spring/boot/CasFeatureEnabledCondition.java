/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.configuration.features.CasFeatureModule$FeatureCatalog
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.boot.autoconfigure.condition.ConditionOutcome
 *  org.springframework.boot.autoconfigure.condition.SpringBootCondition
 *  org.springframework.context.annotation.ConditionContext
 *  org.springframework.core.annotation.AnnotationAttributes
 *  org.springframework.core.type.AnnotatedTypeMetadata
 */
package org.apereo.cas.util.spring.boot;

import java.util.Arrays;
import java.util.Map;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.configuration.features.CasFeatureModule;
import org.apereo.cas.util.spring.boot.ConditionalOnFeatureEnabled;
import org.apereo.cas.util.spring.boot.ConditionalOnFeaturesEnabled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class CasFeatureEnabledCondition
extends SpringBootCondition {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(CasFeatureEnabledCondition.class);

    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Map attributes = metadata.getAnnotationAttributes(ConditionalOnFeatureEnabled.class.getName());
        if (attributes == null) {
            AnnotationAttributes[] conditions = (AnnotationAttributes[])metadata.getAnnotationAttributes(ConditionalOnFeaturesEnabled.class.getName()).get("value");
            StringBuilder builder = new StringBuilder();
            boolean match = Arrays.stream(conditions).allMatch(annotation -> {
                CasFeatureModule.FeatureCatalog feature = (CasFeatureModule.FeatureCatalog)annotation.get((Object)"feature");
                String module = annotation.getString("module");
                boolean enabledByDefault = annotation.getBoolean("enabledByDefault");
                ConditionOutcome conditionOutcome = CasFeatureEnabledCondition.getConditionOutcome(context, feature, module, enabledByDefault);
                builder.append(conditionOutcome.getMessage()).append(' ');
                return conditionOutcome.isMatch();
            });
            return match ? ConditionOutcome.match((String)builder.toString()) : ConditionOutcome.noMatch((String)builder.toString());
        }
        return CasFeatureEnabledCondition.evaluateFeatureCondition(context, attributes);
    }

    private static ConditionOutcome evaluateFeatureCondition(ConditionContext context, Map<String, Object> attributes) {
        CasFeatureModule.FeatureCatalog feature = (CasFeatureModule.FeatureCatalog)attributes.get("feature");
        String module = (String)attributes.get("module");
        boolean enabledByDefault = (Boolean)attributes.get("enabledByDefault");
        return CasFeatureEnabledCondition.getConditionOutcome(context, feature, module, enabledByDefault);
    }

    private static ConditionOutcome getConditionOutcome(ConditionContext context, CasFeatureModule.FeatureCatalog feature, String module, boolean enabledByDefault) {
        String property = feature.toProperty(module);
        LOGGER.trace("Checking for feature module capability via [{}]", (Object)property);
        if (!context.getEnvironment().containsProperty(property) && !enabledByDefault) {
            String message = "CAS feature " + property + " is disabled by default and must be explicitly enabled.";
            LOGGER.trace(message);
            return ConditionOutcome.noMatch((String)message);
        }
        String propertyValue = context.getEnvironment().getProperty(property);
        if (StringUtils.equalsIgnoreCase((CharSequence)propertyValue, (CharSequence)"false")) {
            String message = "CAS feature " + property + " is set to false.";
            LOGGER.trace(message);
            return ConditionOutcome.noMatch((String)message);
        }
        String message = "CAS feature " + property + " is set to true.";
        LOGGER.trace(message);
        feature.register(module);
        return ConditionOutcome.match((String)message);
    }
}

