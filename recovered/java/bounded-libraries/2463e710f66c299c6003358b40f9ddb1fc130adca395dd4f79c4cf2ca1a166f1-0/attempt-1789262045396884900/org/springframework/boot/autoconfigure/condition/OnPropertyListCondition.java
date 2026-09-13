/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.context.properties.bind.BindResult
 *  org.springframework.boot.context.properties.bind.Bindable
 *  org.springframework.boot.context.properties.bind.Binder
 *  org.springframework.context.annotation.ConditionContext
 *  org.springframework.core.env.Environment
 *  org.springframework.core.type.AnnotatedTypeMetadata
 */
package org.springframework.boot.autoconfigure.condition;

import java.util.List;
import java.util.function.Supplier;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class OnPropertyListCondition
extends SpringBootCondition {
    private static final Bindable<List<String>> STRING_LIST = Bindable.listOf(String.class);
    private final String propertyName;
    private final Supplier<ConditionMessage.Builder> messageBuilder;

    protected OnPropertyListCondition(String propertyName, Supplier<ConditionMessage.Builder> messageBuilder) {
        this.propertyName = propertyName;
        this.messageBuilder = messageBuilder;
    }

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        BindResult property = Binder.get((Environment)context.getEnvironment()).bind(this.propertyName, STRING_LIST);
        ConditionMessage.Builder messageBuilder = this.messageBuilder.get();
        if (property.isBound()) {
            return ConditionOutcome.match(messageBuilder.found("property").items(this.propertyName));
        }
        return ConditionOutcome.noMatch(messageBuilder.didNotFind("property").items(this.propertyName));
    }
}

