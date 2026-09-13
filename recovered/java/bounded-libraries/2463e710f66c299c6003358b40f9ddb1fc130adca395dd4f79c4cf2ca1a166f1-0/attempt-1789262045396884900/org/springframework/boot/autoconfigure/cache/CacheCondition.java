/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.context.properties.bind.BindException
 *  org.springframework.boot.context.properties.bind.BindResult
 *  org.springframework.boot.context.properties.bind.Binder
 *  org.springframework.context.annotation.ConditionContext
 *  org.springframework.core.env.Environment
 *  org.springframework.core.type.AnnotatedTypeMetadata
 *  org.springframework.core.type.AnnotationMetadata
 *  org.springframework.core.type.ClassMetadata
 */
package org.springframework.boot.autoconfigure.cache;

import org.springframework.boot.autoconfigure.cache.CacheConfigurations;
import org.springframework.boot.autoconfigure.cache.CacheType;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.boot.context.properties.bind.BindException;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;

class CacheCondition
extends SpringBootCondition {
    CacheCondition() {
    }

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String sourceClass = "";
        if (metadata instanceof ClassMetadata) {
            sourceClass = ((ClassMetadata)metadata).getClassName();
        }
        ConditionMessage.Builder message = ConditionMessage.forCondition("Cache", sourceClass);
        Environment environment = context.getEnvironment();
        try {
            BindResult specified = Binder.get((Environment)environment).bind("spring.cache.type", CacheType.class);
            if (!specified.isBound()) {
                return ConditionOutcome.match(message.because("automatic cache type"));
            }
            CacheType required = CacheConfigurations.getType(((AnnotationMetadata)metadata).getClassName());
            if (specified.get() == required) {
                return ConditionOutcome.match(message.because(specified.get() + " cache type"));
            }
        }
        catch (BindException bindException) {
            // empty catch block
        }
        return ConditionOutcome.noMatch(message.because("unknown cache type"));
    }
}

