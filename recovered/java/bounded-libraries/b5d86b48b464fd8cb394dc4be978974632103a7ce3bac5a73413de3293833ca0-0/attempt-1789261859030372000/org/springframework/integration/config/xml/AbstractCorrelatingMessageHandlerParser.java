/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeanMetadataElement
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.xml.ParserContext
 *  org.springframework.util.xml.DomUtils
 */
package org.springframework.integration.config.xml;

import org.springframework.beans.BeanMetadataElement;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.config.xml.AbstractConsumerEndpointParser;
import org.springframework.integration.config.xml.IntegrationNamespaceUtils;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

public abstract class AbstractCorrelatingMessageHandlerParser
extends AbstractConsumerEndpointParser {
    private static final String CORRELATION_STRATEGY_REF_ATTRIBUTE = "correlation-strategy";
    private static final String CORRELATION_STRATEGY_METHOD_ATTRIBUTE = "correlation-strategy-method";
    private static final String CORRELATION_STRATEGY_EXPRESSION_ATTRIBUTE = "correlation-strategy-expression";
    private static final String CORRELATION_STRATEGY_PROPERTY = "correlationStrategy";
    private static final String RELEASE_STRATEGY_REF_ATTRIBUTE = "release-strategy";
    private static final String RELEASE_STRATEGY_METHOD_ATTRIBUTE = "release-strategy-method";
    private static final String RELEASE_STRATEGY_EXPRESSION_ATTRIBUTE = "release-strategy-expression";
    private static final String RELEASE_STRATEGY_PROPERTY = "releaseStrategy";
    private static final String MESSAGE_STORE_ATTRIBUTE = "message-store";
    private static final String DISCARD_CHANNEL_ATTRIBUTE = "discard-channel";
    private static final String SEND_TIMEOUT_ATTRIBUTE = "send-timeout";
    private static final String SEND_PARTIAL_RESULT_ON_EXPIRY_ATTRIBUTE = "send-partial-result-on-expiry";
    private static final String EXPIRE_GROUPS_UPON_TIMEOUT = "expire-groups-upon-timeout";
    private static final String RELEASE_LOCK = "release-lock-before-send";

    protected void doParse(BeanDefinitionBuilder builder, Element element, BeanMetadataElement processor, ParserContext parserContext) {
        IntegrationNamespaceUtils.injectPropertyWithAdapter(CORRELATION_STRATEGY_REF_ATTRIBUTE, CORRELATION_STRATEGY_METHOD_ATTRIBUTE, CORRELATION_STRATEGY_EXPRESSION_ATTRIBUTE, CORRELATION_STRATEGY_PROPERTY, "CorrelationStrategy", element, builder, processor, parserContext);
        IntegrationNamespaceUtils.injectPropertyWithAdapter(RELEASE_STRATEGY_REF_ATTRIBUTE, RELEASE_STRATEGY_METHOD_ATTRIBUTE, RELEASE_STRATEGY_EXPRESSION_ATTRIBUTE, RELEASE_STRATEGY_PROPERTY, "ReleaseStrategy", element, builder, processor, parserContext);
        IntegrationNamespaceUtils.setReferenceIfAttributeDefined(builder, element, MESSAGE_STORE_ATTRIBUTE);
        IntegrationNamespaceUtils.setReferenceIfAttributeDefined(builder, element, "scheduler", "taskScheduler");
        IntegrationNamespaceUtils.setReferenceIfAttributeDefined(builder, element, "lock-registry");
        IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element, DISCARD_CHANNEL_ATTRIBUTE, "discardChannelName");
        IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element, SEND_TIMEOUT_ATTRIBUTE);
        IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element, SEND_PARTIAL_RESULT_ON_EXPIRY_ATTRIBUTE);
        IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element, "empty-group-min-timeout", "minimumTimeoutForEmptyGroups");
        IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element, "pop-sequence");
        BeanDefinition expressionDef = IntegrationNamespaceUtils.createExpressionDefinitionFromValueOrExpression("group-timeout", "group-timeout-expression", parserContext, element, false);
        builder.addPropertyValue("groupTimeoutExpression", (Object)expressionDef);
        Element txElement = DomUtils.getChildElementByTagName((Element)element, (String)"expire-transactional");
        Element adviceChainElement = DomUtils.getChildElementByTagName((Element)element, (String)"expire-advice-chain");
        IntegrationNamespaceUtils.configureAndSetAdviceChainIfPresent(adviceChainElement, txElement, (BeanDefinition)builder.getRawBeanDefinition(), parserContext, "forceReleaseAdviceChain");
        IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element, EXPIRE_GROUPS_UPON_TIMEOUT);
        IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element, RELEASE_LOCK);
        IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element, "expire-timeout");
        IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element, "expire-duration", "expireDurationMillis");
        IntegrationNamespaceUtils.setReferenceIfAttributeDefined(builder, element, "group-condition-supplier");
    }
}

