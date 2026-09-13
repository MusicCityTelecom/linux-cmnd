/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletContext
 *  org.springframework.context.annotation.ConditionContext
 *  org.springframework.core.io.ResourceLoader
 *  org.springframework.core.type.AnnotatedTypeMetadata
 *  org.springframework.web.context.WebApplicationContext
 */
package org.springframework.boot.autoconfigure.condition;

import javax.servlet.ServletContext;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWarDeployment;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.web.context.WebApplicationContext;

class OnWarDeploymentCondition
extends SpringBootCondition {
    OnWarDeploymentCondition() {
    }

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        WebApplicationContext applicationContext;
        ServletContext servletContext;
        ResourceLoader resourceLoader = context.getResourceLoader();
        if (resourceLoader instanceof WebApplicationContext && (servletContext = (applicationContext = (WebApplicationContext)resourceLoader).getServletContext()) != null) {
            return ConditionOutcome.match("Application is deployed as a WAR file.");
        }
        return ConditionOutcome.noMatch(ConditionMessage.forCondition(ConditionalOnWarDeployment.class, new Object[0]).because("the application is not deployed as a WAR file."));
    }
}

