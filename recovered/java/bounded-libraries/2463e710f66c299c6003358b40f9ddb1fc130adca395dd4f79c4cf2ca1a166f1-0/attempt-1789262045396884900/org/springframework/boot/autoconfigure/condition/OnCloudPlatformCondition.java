/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.cloud.CloudPlatform
 *  org.springframework.context.annotation.ConditionContext
 *  org.springframework.core.env.Environment
 *  org.springframework.core.type.AnnotatedTypeMetadata
 */
package org.springframework.boot.autoconfigure.condition;

import java.util.Map;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.ConditionalOnCloudPlatform;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.boot.cloud.CloudPlatform;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

class OnCloudPlatformCondition
extends SpringBootCondition {
    OnCloudPlatformCondition() {
    }

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Map attributes = metadata.getAnnotationAttributes(ConditionalOnCloudPlatform.class.getName());
        CloudPlatform cloudPlatform = (CloudPlatform)attributes.get("value");
        return this.getMatchOutcome(context.getEnvironment(), cloudPlatform);
    }

    private ConditionOutcome getMatchOutcome(Environment environment, CloudPlatform cloudPlatform) {
        String name = cloudPlatform.name();
        ConditionMessage.Builder message = ConditionMessage.forCondition(ConditionalOnCloudPlatform.class, new Object[0]);
        if (cloudPlatform.isActive(environment)) {
            return ConditionOutcome.match(message.foundExactly(name));
        }
        return ConditionOutcome.noMatch(message.didNotFind(name).atAll());
    }
}

