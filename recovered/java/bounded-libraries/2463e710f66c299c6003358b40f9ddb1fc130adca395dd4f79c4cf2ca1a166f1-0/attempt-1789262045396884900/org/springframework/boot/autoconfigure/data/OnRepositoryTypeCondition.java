/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.annotation.ConditionContext
 *  org.springframework.core.env.Environment
 *  org.springframework.core.type.AnnotatedTypeMetadata
 */
package org.springframework.boot.autoconfigure.data;

import java.util.Locale;
import java.util.Map;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.boot.autoconfigure.data.ConditionalOnRepositoryType;
import org.springframework.boot.autoconfigure.data.RepositoryType;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

class OnRepositoryTypeCondition
extends SpringBootCondition {
    OnRepositoryTypeCondition() {
    }

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Map attributes = metadata.getAnnotationAttributes(ConditionalOnRepositoryType.class.getName(), true);
        RepositoryType configuredType = this.getTypeProperty(context.getEnvironment(), (String)attributes.get("store"));
        RepositoryType requiredType = (RepositoryType)((Object)attributes.get("type"));
        ConditionMessage.Builder message = ConditionMessage.forCondition(ConditionalOnRepositoryType.class, new Object[0]);
        if (configuredType == requiredType || configuredType == RepositoryType.AUTO) {
            return ConditionOutcome.match(message.because("configured type of '" + configuredType.name() + "' matched required type"));
        }
        return ConditionOutcome.noMatch(message.because("configured type (" + configuredType.name() + ") did not match required type (" + requiredType.name() + ")"));
    }

    private RepositoryType getTypeProperty(Environment environment, String store) {
        return RepositoryType.valueOf(environment.getProperty(String.format("spring.data.%s.repositories.type", store), "auto").toUpperCase(Locale.ENGLISH));
    }
}

