/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.sql.init.DatabaseInitializationMode
 *  org.springframework.context.annotation.ConditionContext
 *  org.springframework.core.env.Environment
 *  org.springframework.core.type.AnnotatedTypeMetadata
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.autoconfigure.sql.init;

import java.util.Locale;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.boot.sql.init.DatabaseInitializationMode;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.StringUtils;

public class OnDatabaseInitializationCondition
extends SpringBootCondition {
    private final String name;
    private final String[] propertyNames;

    public OnDatabaseInitializationCondition(String name, String ... propertyNames) {
        this.name = name;
        this.propertyNames = propertyNames;
    }

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Environment environment = context.getEnvironment();
        String propertyName = this.getConfiguredProperty(environment);
        DatabaseInitializationMode mode = this.getDatabaseInitializationMode(environment, propertyName);
        boolean match = this.match(mode);
        String messagePrefix = propertyName != null ? propertyName : "default value";
        return new ConditionOutcome(match, ConditionMessage.forCondition(this.name + "Database Initialization", new Object[0]).because(messagePrefix + " is " + mode));
    }

    private boolean match(DatabaseInitializationMode mode) {
        return !mode.equals((Object)DatabaseInitializationMode.NEVER);
    }

    private DatabaseInitializationMode getDatabaseInitializationMode(Environment environment, String propertyName) {
        String candidate;
        if (StringUtils.hasText((String)propertyName) && StringUtils.hasText((String)(candidate = environment.getProperty(propertyName, "embedded").toUpperCase(Locale.ENGLISH)))) {
            return DatabaseInitializationMode.valueOf((String)candidate);
        }
        return DatabaseInitializationMode.EMBEDDED;
    }

    private String getConfiguredProperty(Environment environment) {
        for (String propertyName : this.propertyNames) {
            if (!environment.containsProperty(propertyName)) continue;
            return propertyName;
        }
        return null;
    }
}

