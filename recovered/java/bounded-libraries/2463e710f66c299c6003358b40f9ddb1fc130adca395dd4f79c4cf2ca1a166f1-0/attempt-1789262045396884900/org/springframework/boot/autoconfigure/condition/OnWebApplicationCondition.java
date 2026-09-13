/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.web.reactive.context.ConfigurableReactiveWebEnvironment
 *  org.springframework.boot.web.reactive.context.ReactiveWebApplicationContext
 *  org.springframework.context.annotation.ConditionContext
 *  org.springframework.core.annotation.Order
 *  org.springframework.core.type.AnnotatedTypeMetadata
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.ObjectUtils
 *  org.springframework.web.context.ConfigurableWebEnvironment
 *  org.springframework.web.context.WebApplicationContext
 */
package org.springframework.boot.autoconfigure.condition;

import java.util.Map;
import org.springframework.boot.autoconfigure.AutoConfigurationMetadata;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.FilteringSpringBootCondition;
import org.springframework.boot.web.reactive.context.ConfigurableReactiveWebEnvironment;
import org.springframework.boot.web.reactive.context.ReactiveWebApplicationContext;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.annotation.Order;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.ClassUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.ConfigurableWebEnvironment;
import org.springframework.web.context.WebApplicationContext;

@Order(value=-2147483628)
class OnWebApplicationCondition
extends FilteringSpringBootCondition {
    private static final String SERVLET_WEB_APPLICATION_CLASS = "org.springframework.web.context.support.GenericWebApplicationContext";
    private static final String REACTIVE_WEB_APPLICATION_CLASS = "org.springframework.web.reactive.HandlerResult";

    OnWebApplicationCondition() {
    }

    @Override
    protected ConditionOutcome[] getOutcomes(String[] autoConfigurationClasses, AutoConfigurationMetadata autoConfigurationMetadata) {
        ConditionOutcome[] outcomes = new ConditionOutcome[autoConfigurationClasses.length];
        for (int i = 0; i < outcomes.length; ++i) {
            String autoConfigurationClass = autoConfigurationClasses[i];
            if (autoConfigurationClass == null) continue;
            outcomes[i] = this.getOutcome(autoConfigurationMetadata.get(autoConfigurationClass, "ConditionalOnWebApplication"));
        }
        return outcomes;
    }

    private ConditionOutcome getOutcome(String type) {
        if (type == null) {
            return null;
        }
        ConditionMessage.Builder message = ConditionMessage.forCondition(ConditionalOnWebApplication.class, new Object[0]);
        if (ConditionalOnWebApplication.Type.SERVLET.name().equals(type) && !FilteringSpringBootCondition.ClassNameFilter.isPresent(SERVLET_WEB_APPLICATION_CLASS, this.getBeanClassLoader())) {
            return ConditionOutcome.noMatch(message.didNotFind("servlet web application classes").atAll());
        }
        if (ConditionalOnWebApplication.Type.REACTIVE.name().equals(type) && !FilteringSpringBootCondition.ClassNameFilter.isPresent(REACTIVE_WEB_APPLICATION_CLASS, this.getBeanClassLoader())) {
            return ConditionOutcome.noMatch(message.didNotFind("reactive web application classes").atAll());
        }
        if (!FilteringSpringBootCondition.ClassNameFilter.isPresent(SERVLET_WEB_APPLICATION_CLASS, this.getBeanClassLoader()) && !ClassUtils.isPresent((String)REACTIVE_WEB_APPLICATION_CLASS, (ClassLoader)this.getBeanClassLoader())) {
            return ConditionOutcome.noMatch(message.didNotFind("reactive or servlet web application classes").atAll());
        }
        return null;
    }

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        boolean required = metadata.isAnnotated(ConditionalOnWebApplication.class.getName());
        ConditionOutcome outcome = this.isWebApplication(context, metadata, required);
        if (required && !outcome.isMatch()) {
            return ConditionOutcome.noMatch(outcome.getConditionMessage());
        }
        if (!required && outcome.isMatch()) {
            return ConditionOutcome.noMatch(outcome.getConditionMessage());
        }
        return ConditionOutcome.match(outcome.getConditionMessage());
    }

    private ConditionOutcome isWebApplication(ConditionContext context, AnnotatedTypeMetadata metadata, boolean required) {
        switch (this.deduceType(metadata)) {
            case SERVLET: {
                return this.isServletWebApplication(context);
            }
            case REACTIVE: {
                return this.isReactiveWebApplication(context);
            }
        }
        return this.isAnyWebApplication(context, required);
    }

    private ConditionOutcome isAnyWebApplication(ConditionContext context, boolean required) {
        ConditionMessage.Builder message = ConditionMessage.forCondition(ConditionalOnWebApplication.class, required ? "(required)" : "");
        ConditionOutcome servletOutcome = this.isServletWebApplication(context);
        if (servletOutcome.isMatch() && required) {
            return new ConditionOutcome(servletOutcome.isMatch(), message.because(servletOutcome.getMessage()));
        }
        ConditionOutcome reactiveOutcome = this.isReactiveWebApplication(context);
        if (reactiveOutcome.isMatch() && required) {
            return new ConditionOutcome(reactiveOutcome.isMatch(), message.because(reactiveOutcome.getMessage()));
        }
        return new ConditionOutcome(servletOutcome.isMatch() || reactiveOutcome.isMatch(), message.because(servletOutcome.getMessage()).append("and").append(reactiveOutcome.getMessage()));
    }

    private ConditionOutcome isServletWebApplication(ConditionContext context) {
        Object[] scopes;
        ConditionMessage.Builder message = ConditionMessage.forCondition("", new Object[0]);
        if (!FilteringSpringBootCondition.ClassNameFilter.isPresent(SERVLET_WEB_APPLICATION_CLASS, context.getClassLoader())) {
            return ConditionOutcome.noMatch(message.didNotFind("servlet web application classes").atAll());
        }
        if (context.getBeanFactory() != null && ObjectUtils.containsElement((Object[])(scopes = context.getBeanFactory().getRegisteredScopeNames()), (Object)"session")) {
            return ConditionOutcome.match(message.foundExactly("'session' scope"));
        }
        if (context.getEnvironment() instanceof ConfigurableWebEnvironment) {
            return ConditionOutcome.match(message.foundExactly("ConfigurableWebEnvironment"));
        }
        if (context.getResourceLoader() instanceof WebApplicationContext) {
            return ConditionOutcome.match(message.foundExactly("WebApplicationContext"));
        }
        return ConditionOutcome.noMatch(message.because("not a servlet web application"));
    }

    private ConditionOutcome isReactiveWebApplication(ConditionContext context) {
        ConditionMessage.Builder message = ConditionMessage.forCondition("", new Object[0]);
        if (!FilteringSpringBootCondition.ClassNameFilter.isPresent(REACTIVE_WEB_APPLICATION_CLASS, context.getClassLoader())) {
            return ConditionOutcome.noMatch(message.didNotFind("reactive web application classes").atAll());
        }
        if (context.getEnvironment() instanceof ConfigurableReactiveWebEnvironment) {
            return ConditionOutcome.match(message.foundExactly("ConfigurableReactiveWebEnvironment"));
        }
        if (context.getResourceLoader() instanceof ReactiveWebApplicationContext) {
            return ConditionOutcome.match(message.foundExactly("ReactiveWebApplicationContext"));
        }
        return ConditionOutcome.noMatch(message.because("not a reactive web application"));
    }

    private ConditionalOnWebApplication.Type deduceType(AnnotatedTypeMetadata metadata) {
        Map attributes = metadata.getAnnotationAttributes(ConditionalOnWebApplication.class.getName());
        if (attributes != null) {
            return (ConditionalOnWebApplication.Type)((Object)attributes.get("type"));
        }
        return ConditionalOnWebApplication.Type.ANY;
    }
}

