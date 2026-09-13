/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ch.qos.logback.classic.joran.JoranConfigurator
 *  ch.qos.logback.core.joran.action.Action
 *  ch.qos.logback.core.joran.action.NOPAction
 *  ch.qos.logback.core.joran.spi.ElementSelector
 *  ch.qos.logback.core.joran.spi.RuleStore
 *  org.springframework.core.env.Environment
 */
package org.springframework.boot.logging.logback;

import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.action.Action;
import ch.qos.logback.core.joran.action.NOPAction;
import ch.qos.logback.core.joran.spi.ElementSelector;
import ch.qos.logback.core.joran.spi.RuleStore;
import org.springframework.boot.logging.LoggingInitializationContext;
import org.springframework.boot.logging.logback.SpringProfileAction;
import org.springframework.boot.logging.logback.SpringPropertyAction;
import org.springframework.core.env.Environment;

class SpringBootJoranConfigurator
extends JoranConfigurator {
    private LoggingInitializationContext initializationContext;

    SpringBootJoranConfigurator(LoggingInitializationContext initializationContext) {
        this.initializationContext = initializationContext;
    }

    public void addInstanceRules(RuleStore rs) {
        super.addInstanceRules(rs);
        Environment environment = this.initializationContext.getEnvironment();
        rs.addRule(new ElementSelector("configuration/springProperty"), (Action)new SpringPropertyAction(environment));
        rs.addRule(new ElementSelector("*/springProfile"), (Action)new SpringProfileAction(environment));
        rs.addRule(new ElementSelector("*/springProfile/*"), (Action)new NOPAction());
    }
}

