/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.annotation.ConditionContext
 *  org.springframework.core.io.Resource
 *  org.springframework.core.type.AnnotatedTypeMetadata
 */
package org.springframework.boot.autoconfigure.condition;

import java.util.ArrayList;
import java.util.Arrays;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotatedTypeMetadata;

public abstract class ResourceCondition
extends SpringBootCondition {
    private final String name;
    private final String property;
    private final String[] resourceLocations;

    protected ResourceCondition(String name, String property, String ... resourceLocations) {
        this.name = name;
        this.property = property;
        this.resourceLocations = resourceLocations;
    }

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        if (context.getEnvironment().containsProperty(this.property)) {
            return ConditionOutcome.match(this.startConditionMessage().foundExactly("property " + this.property));
        }
        return this.getResourceOutcome(context, metadata);
    }

    protected ConditionOutcome getResourceOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        ConditionMessage message;
        ArrayList<String> found = new ArrayList<String>();
        for (String location : this.resourceLocations) {
            Resource resource = context.getResourceLoader().getResource(location);
            if (resource == null || !resource.exists()) continue;
            found.add(location);
        }
        if (found.isEmpty()) {
            message = this.startConditionMessage().didNotFind("resource", "resources").items(ConditionMessage.Style.QUOTE, Arrays.asList(this.resourceLocations));
            return ConditionOutcome.noMatch(message);
        }
        message = this.startConditionMessage().found("resource", "resources").items(ConditionMessage.Style.QUOTE, found);
        return ConditionOutcome.match(message);
    }

    protected final ConditionMessage.Builder startConditionMessage() {
        return ConditionMessage.forCondition("ResourceCondition", "(" + this.name + ")");
    }
}

