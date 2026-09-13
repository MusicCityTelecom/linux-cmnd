/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.annotation.ConditionContext
 *  org.springframework.core.type.AnnotatedTypeMetadata
 *  org.springframework.util.Assert
 */
package org.springframework.boot.autoconfigure.hazelcast;

import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.ResourceCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.Assert;

public abstract class HazelcastConfigResourceCondition
extends ResourceCondition {
    protected static final String HAZELCAST_CONFIG_PROPERTY = "spring.hazelcast.config";
    private final String configSystemProperty;

    protected HazelcastConfigResourceCondition(String configSystemProperty, String ... resourceLocations) {
        super("Hazelcast", HAZELCAST_CONFIG_PROPERTY, resourceLocations);
        Assert.notNull((Object)configSystemProperty, (String)"ConfigSystemProperty must not be null");
        this.configSystemProperty = configSystemProperty;
    }

    @Override
    protected ConditionOutcome getResourceOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        if (System.getProperty(this.configSystemProperty) != null) {
            return ConditionOutcome.match(this.startConditionMessage().because("System property '" + this.configSystemProperty + "' is set."));
        }
        return super.getResourceOutcome(context, metadata);
    }
}

