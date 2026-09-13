/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.system.JavaVersion
 *  org.springframework.context.annotation.ConditionContext
 *  org.springframework.core.annotation.Order
 *  org.springframework.core.type.AnnotatedTypeMetadata
 */
package org.springframework.boot.autoconfigure.condition;

import java.util.Map;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.ConditionalOnJava;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.boot.system.JavaVersion;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.annotation.Order;
import org.springframework.core.type.AnnotatedTypeMetadata;

@Order(value=-2147483628)
class OnJavaCondition
extends SpringBootCondition {
    private static final JavaVersion JVM_VERSION = JavaVersion.getJavaVersion();

    OnJavaCondition() {
    }

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Map attributes = metadata.getAnnotationAttributes(ConditionalOnJava.class.getName());
        ConditionalOnJava.Range range = (ConditionalOnJava.Range)((Object)attributes.get("range"));
        JavaVersion version = (JavaVersion)attributes.get("value");
        return this.getMatchOutcome(range, JVM_VERSION, version);
    }

    protected ConditionOutcome getMatchOutcome(ConditionalOnJava.Range range, JavaVersion runningVersion, JavaVersion version) {
        boolean match = this.isWithin(runningVersion, range, version);
        String expected = String.format(range != ConditionalOnJava.Range.EQUAL_OR_NEWER ? "(older than %s)" : "(%s or newer)", version);
        ConditionMessage message = ConditionMessage.forCondition(ConditionalOnJava.class, expected).foundExactly(runningVersion);
        return new ConditionOutcome(match, message);
    }

    private boolean isWithin(JavaVersion runningVersion, ConditionalOnJava.Range range, JavaVersion version) {
        if (range == ConditionalOnJava.Range.EQUAL_OR_NEWER) {
            return runningVersion.isEqualOrNewerThan(version);
        }
        if (range == ConditionalOnJava.Range.OLDER_THAN) {
            return runningVersion.isOlderThan(version);
        }
        throw new IllegalStateException("Unknown range " + (Object)((Object)range));
    }
}

