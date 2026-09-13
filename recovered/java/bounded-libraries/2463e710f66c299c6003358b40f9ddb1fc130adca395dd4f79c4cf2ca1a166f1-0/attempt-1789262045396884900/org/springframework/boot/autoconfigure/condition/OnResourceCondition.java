/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.annotation.ConditionContext
 *  org.springframework.core.annotation.Order
 *  org.springframework.core.io.ResourceLoader
 *  org.springframework.core.type.AnnotatedTypeMetadata
 *  org.springframework.util.Assert
 *  org.springframework.util.MultiValueMap
 */
package org.springframework.boot.autoconfigure.condition;

import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;

@Order(value=-2147483628)
class OnResourceCondition
extends SpringBootCondition {
    OnResourceCondition() {
    }

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        MultiValueMap attributes = metadata.getAllAnnotationAttributes(ConditionalOnResource.class.getName(), true);
        ResourceLoader loader = context.getResourceLoader();
        ArrayList<String> locations = new ArrayList<String>();
        this.collectValues(locations, (List)attributes.get((Object)"resources"));
        Assert.isTrue((!locations.isEmpty() ? 1 : 0) != 0, (String)"@ConditionalOnResource annotations must specify at least one resource location");
        ArrayList<String> missing = new ArrayList<String>();
        for (String location : locations) {
            String resource = context.getEnvironment().resolvePlaceholders(location);
            if (loader.getResource(resource).exists()) continue;
            missing.add(location);
        }
        if (!missing.isEmpty()) {
            return ConditionOutcome.noMatch(ConditionMessage.forCondition(ConditionalOnResource.class, new Object[0]).didNotFind("resource", "resources").items(ConditionMessage.Style.QUOTE, missing));
        }
        return ConditionOutcome.match(ConditionMessage.forCondition(ConditionalOnResource.class, new Object[0]).found("location", "locations").items(locations));
    }

    private void collectValues(List<String> names, List<Object> values) {
        for (Object value : values) {
            for (Object item : (Object[])value) {
                names.add((String)item);
            }
        }
    }
}

