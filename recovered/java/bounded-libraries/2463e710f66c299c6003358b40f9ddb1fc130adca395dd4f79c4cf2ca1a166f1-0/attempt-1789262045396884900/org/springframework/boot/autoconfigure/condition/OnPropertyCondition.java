/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.annotation.ConditionContext
 *  org.springframework.core.annotation.AnnotationAttributes
 *  org.springframework.core.annotation.MergedAnnotation
 *  org.springframework.core.annotation.MergedAnnotation$Adapt
 *  org.springframework.core.annotation.MergedAnnotationPredicates
 *  org.springframework.core.annotation.Order
 *  org.springframework.core.env.PropertyResolver
 *  org.springframework.core.type.AnnotatedTypeMetadata
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.autoconfigure.condition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotationPredicates;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.PropertyResolver;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Order(value=-2147483608)
class OnPropertyCondition
extends SpringBootCondition {
    OnPropertyCondition() {
    }

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        List allAnnotationAttributes = metadata.getAnnotations().stream(ConditionalOnProperty.class.getName()).filter(MergedAnnotationPredicates.unique(MergedAnnotation::getMetaTypes)).map(rec$ -> ((MergedAnnotation)rec$).asAnnotationAttributes(new MergedAnnotation.Adapt[0])).collect(Collectors.toList());
        ArrayList noMatch = new ArrayList();
        ArrayList<ConditionMessage> match = new ArrayList<ConditionMessage>();
        for (AnnotationAttributes annotationAttributes : allAnnotationAttributes) {
            ConditionOutcome outcome = this.determineOutcome(annotationAttributes, (PropertyResolver)context.getEnvironment());
            (outcome.isMatch() ? match : noMatch).add(outcome.getConditionMessage());
        }
        if (!noMatch.isEmpty()) {
            return ConditionOutcome.noMatch(ConditionMessage.of(noMatch));
        }
        return ConditionOutcome.match(ConditionMessage.of(match));
    }

    private ConditionOutcome determineOutcome(AnnotationAttributes annotationAttributes, PropertyResolver resolver) {
        Spec spec = new Spec(annotationAttributes);
        ArrayList missingProperties = new ArrayList();
        ArrayList nonMatchingProperties = new ArrayList();
        spec.collectProperties(resolver, missingProperties, nonMatchingProperties);
        if (!missingProperties.isEmpty()) {
            return ConditionOutcome.noMatch(ConditionMessage.forCondition(ConditionalOnProperty.class, spec).didNotFind("property", "properties").items(ConditionMessage.Style.QUOTE, missingProperties));
        }
        if (!nonMatchingProperties.isEmpty()) {
            return ConditionOutcome.noMatch(ConditionMessage.forCondition(ConditionalOnProperty.class, spec).found("different value in property", "different value in properties").items(ConditionMessage.Style.QUOTE, nonMatchingProperties));
        }
        return ConditionOutcome.match(ConditionMessage.forCondition(ConditionalOnProperty.class, spec).because("matched"));
    }

    private static class Spec {
        private final String prefix;
        private final String havingValue;
        private final String[] names;
        private final boolean matchIfMissing;

        Spec(AnnotationAttributes annotationAttributes) {
            String prefix = annotationAttributes.getString("prefix").trim();
            if (StringUtils.hasText((String)prefix) && !prefix.endsWith(".")) {
                prefix = prefix + ".";
            }
            this.prefix = prefix;
            this.havingValue = annotationAttributes.getString("havingValue");
            this.names = this.getNames((Map<String, Object>)annotationAttributes);
            this.matchIfMissing = annotationAttributes.getBoolean("matchIfMissing");
        }

        private String[] getNames(Map<String, Object> annotationAttributes) {
            String[] value = (String[])annotationAttributes.get("value");
            String[] name = (String[])annotationAttributes.get("name");
            Assert.state((value.length > 0 || name.length > 0 ? 1 : 0) != 0, (String)"The name or value attribute of @ConditionalOnProperty must be specified");
            Assert.state((value.length == 0 || name.length == 0 ? 1 : 0) != 0, (String)"The name and value attributes of @ConditionalOnProperty are exclusive");
            return value.length > 0 ? value : name;
        }

        private void collectProperties(PropertyResolver resolver, List<String> missing, List<String> nonMatching) {
            for (String name : this.names) {
                String key = this.prefix + name;
                if (resolver.containsProperty(key)) {
                    if (this.isMatch(resolver.getProperty(key), this.havingValue)) continue;
                    nonMatching.add(name);
                    continue;
                }
                if (this.matchIfMissing) continue;
                missing.add(name);
            }
        }

        private boolean isMatch(String value, String requiredValue) {
            if (StringUtils.hasLength((String)requiredValue)) {
                return requiredValue.equalsIgnoreCase(value);
            }
            return !"false".equalsIgnoreCase(value);
        }

        public String toString() {
            StringBuilder result = new StringBuilder();
            result.append("(");
            result.append(this.prefix);
            if (this.names.length == 1) {
                result.append(this.names[0]);
            } else {
                result.append("[");
                result.append(StringUtils.arrayToCommaDelimitedString((Object[])this.names));
                result.append("]");
            }
            if (StringUtils.hasLength((String)this.havingValue)) {
                result.append("=").append(this.havingValue);
            }
            result.append(")");
            return result.toString();
        }
    }
}

