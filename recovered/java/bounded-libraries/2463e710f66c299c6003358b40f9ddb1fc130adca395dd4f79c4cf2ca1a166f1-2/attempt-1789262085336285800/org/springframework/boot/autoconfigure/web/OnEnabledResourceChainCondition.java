/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.annotation.ConditionContext
 *  org.springframework.core.env.ConfigurableEnvironment
 *  org.springframework.core.type.AnnotatedTypeMetadata
 *  org.springframework.util.ClassUtils
 */
package org.springframework.boot.autoconfigure.web;

import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.boot.autoconfigure.web.ConditionalOnEnabledResourceChain;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.ClassUtils;

class OnEnabledResourceChainCondition
extends SpringBootCondition {
    private static final String WEBJAR_ASSET_LOCATOR = "org.webjars.WebJarAssetLocator";

    OnEnabledResourceChainCondition() {
    }

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        ConfigurableEnvironment environment = (ConfigurableEnvironment)context.getEnvironment();
        boolean fixed = this.getEnabledProperty(environment, "strategy.fixed.", false);
        boolean content = this.getEnabledProperty(environment, "strategy.content.", false);
        Boolean chain = this.getEnabledProperty(environment, "", null);
        Boolean match = WebProperties.Resources.Chain.getEnabled(fixed, content, chain);
        ConditionMessage.Builder message = ConditionMessage.forCondition(ConditionalOnEnabledResourceChain.class, new Object[0]);
        if (match == null) {
            if (ClassUtils.isPresent((String)WEBJAR_ASSET_LOCATOR, (ClassLoader)this.getClass().getClassLoader())) {
                return ConditionOutcome.match(message.found("class").items(WEBJAR_ASSET_LOCATOR));
            }
            return ConditionOutcome.noMatch(message.didNotFind("class").items(WEBJAR_ASSET_LOCATOR));
        }
        if (match.booleanValue()) {
            return ConditionOutcome.match(message.because("enabled"));
        }
        return ConditionOutcome.noMatch(message.because("disabled"));
    }

    private Boolean getEnabledProperty(ConfigurableEnvironment environment, String key, Boolean defaultValue) {
        String name = "spring.web.resources.chain." + key + "enabled";
        return (Boolean)environment.getProperty(name, Boolean.class, (Object)defaultValue);
    }
}

