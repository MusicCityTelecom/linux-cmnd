/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.support.AbstractBeanDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.xml.AbstractBeanDefinitionParser
 *  org.springframework.beans.factory.xml.ParserContext
 *  org.springframework.retry.backoff.ExponentialBackOffPolicy
 *  org.springframework.retry.backoff.FixedBackOffPolicy
 *  org.springframework.retry.policy.SimpleRetryPolicy
 *  org.springframework.retry.support.RetryTemplate
 *  org.springframework.util.StringUtils
 *  org.springframework.util.xml.DomUtils
 */
package org.springframework.integration.config.xml;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.config.xml.IntegrationNamespaceUtils;
import org.springframework.integration.handler.advice.ErrorMessageSendingRecoverer;
import org.springframework.integration.handler.advice.RequestHandlerRetryAdvice;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.util.StringUtils;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

public class RetryAdviceParser
extends AbstractBeanDefinitionParser {
    protected AbstractBeanDefinition parseInternal(Element element, ParserContext parserContext) {
        String recoveryChannelAttr;
        String maxAttemptsAttr;
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(RequestHandlerRetryAdvice.class);
        BeanDefinitionBuilder retryTemplateBuilder = BeanDefinitionBuilder.genericBeanDefinition(RetryTemplate.class);
        boolean customTemplate = false;
        Element backOffPolicyEle = DomUtils.getChildElementByTagName((Element)element, (String)"fixed-back-off");
        BeanDefinitionBuilder backOffBuilder = null;
        if (backOffPolicyEle != null) {
            backOffBuilder = BeanDefinitionBuilder.genericBeanDefinition(FixedBackOffPolicy.class);
            IntegrationNamespaceUtils.setValueIfAttributeDefined(backOffBuilder, backOffPolicyEle, "interval", "backOffPeriod");
        } else {
            backOffPolicyEle = DomUtils.getChildElementByTagName((Element)element, (String)"exponential-back-off");
            if (backOffPolicyEle != null) {
                backOffBuilder = BeanDefinitionBuilder.genericBeanDefinition(ExponentialBackOffPolicy.class);
                IntegrationNamespaceUtils.setValueIfAttributeDefined(backOffBuilder, backOffPolicyEle, "initial", "initialInterval");
                IntegrationNamespaceUtils.setValueIfAttributeDefined(backOffBuilder, backOffPolicyEle, "multiplier");
                IntegrationNamespaceUtils.setValueIfAttributeDefined(backOffBuilder, backOffPolicyEle, "maximum", "maxInterval");
            }
        }
        if (backOffBuilder != null) {
            retryTemplateBuilder.addPropertyValue("backOffPolicy", (Object)backOffBuilder.getBeanDefinition());
            customTemplate = true;
        }
        if (StringUtils.hasText((String)(maxAttemptsAttr = element.getAttribute("max-attempts")))) {
            BeanDefinitionBuilder retryPolicyBuilder = BeanDefinitionBuilder.genericBeanDefinition(SimpleRetryPolicy.class);
            IntegrationNamespaceUtils.setValueIfAttributeDefined(retryPolicyBuilder, element, "max-attempts");
            retryTemplateBuilder.addPropertyValue("retryPolicy", (Object)retryPolicyBuilder.getBeanDefinition());
            customTemplate = true;
        }
        if (customTemplate) {
            builder.addPropertyValue("retryTemplate", (Object)retryTemplateBuilder.getBeanDefinition());
        }
        if (StringUtils.hasText((String)(recoveryChannelAttr = element.getAttribute("recovery-channel")))) {
            BeanDefinitionBuilder emsrBuilder = BeanDefinitionBuilder.genericBeanDefinition(ErrorMessageSendingRecoverer.class);
            emsrBuilder.addConstructorArgReference(recoveryChannelAttr);
            IntegrationNamespaceUtils.setValueIfAttributeDefined(emsrBuilder, element, "send-timeout");
            builder.addPropertyValue("recoveryCallback", (Object)emsrBuilder.getBeanDefinition());
        }
        return builder.getBeanDefinition();
    }
}

