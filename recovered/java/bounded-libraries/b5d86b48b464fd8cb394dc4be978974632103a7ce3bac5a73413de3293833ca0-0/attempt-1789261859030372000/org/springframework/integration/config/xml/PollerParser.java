/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.BeanDefinitionStoreException
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.support.AbstractBeanDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.support.BeanDefinitionReaderUtils
 *  org.springframework.beans.factory.support.BeanDefinitionRegistry
 *  org.springframework.beans.factory.xml.AbstractBeanDefinitionParser
 *  org.springframework.beans.factory.xml.ParserContext
 *  org.springframework.scheduling.support.CronTrigger
 *  org.springframework.scheduling.support.PeriodicTrigger
 *  org.springframework.util.StringUtils
 *  org.springframework.util.xml.DomUtils
 */
package org.springframework.integration.config.xml;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.channel.MessagePublishingErrorHandler;
import org.springframework.integration.config.xml.IntegrationNamespaceUtils;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.util.StringUtils;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

public class PollerParser
extends AbstractBeanDefinitionParser {
    private static final String MULTIPLE_TRIGGER_DEFINITIONS = "A <poller> cannot specify more than one trigger configuration.";
    private static final String NO_TRIGGER_DEFINITIONS = "A <poller> must have one and only one trigger configuration.";

    protected String resolveId(Element element, AbstractBeanDefinition definition, ParserContext parserContext) throws BeanDefinitionStoreException {
        String id = super.resolveId(element, definition, parserContext);
        if (element.getAttribute("default").equals("true")) {
            if (parserContext.getRegistry().isBeanNameInUse("org.springframework.integration.context.defaultPollerMetadata")) {
                parserContext.getReaderContext().error("Only one default <poller/> element is allowed per context.", (Object)element);
            }
            if (StringUtils.hasText((String)id)) {
                parserContext.getRegistry().registerAlias(id, "org.springframework.integration.context.defaultPollerMetadata");
            } else {
                id = "org.springframework.integration.context.defaultPollerMetadata";
            }
        } else if (!StringUtils.hasText((String)id)) {
            parserContext.getReaderContext().error("The 'id' attribute is required for a top-level poller element unless it is the default poller.", (Object)element);
        }
        return id;
    }

    protected AbstractBeanDefinition parseInternal(Element element, ParserContext parserContext) {
        BeanDefinitionBuilder metadataBuilder = BeanDefinitionBuilder.genericBeanDefinition(PollerMetadata.class);
        if (element.hasAttribute("ref")) {
            parserContext.getReaderContext().error("the 'ref' attribute must not be present on the top-level 'poller' element", (Object)element);
        }
        this.configureTrigger(element, metadataBuilder, parserContext);
        IntegrationNamespaceUtils.setValueIfAttributeDefined(metadataBuilder, element, "max-messages-per-poll");
        IntegrationNamespaceUtils.setValueIfAttributeDefined(metadataBuilder, element, "receive-timeout");
        IntegrationNamespaceUtils.setReferenceIfAttributeDefined(metadataBuilder, element, "task-executor");
        Element txElement = DomUtils.getChildElementByTagName((Element)element, (String)"transactional");
        Element adviceChainElement = DomUtils.getChildElementByTagName((Element)element, (String)"advice-chain");
        IntegrationNamespaceUtils.configureAndSetAdviceChainIfPresent(adviceChainElement, txElement, (BeanDefinition)metadataBuilder.getRawBeanDefinition(), parserContext);
        if (txElement != null) {
            IntegrationNamespaceUtils.setReferenceIfAttributeDefined(metadataBuilder, txElement, "synchronization-factory", "transactionSynchronizationFactory");
        } else if (adviceChainElement != null) {
            IntegrationNamespaceUtils.setReferenceIfAttributeDefined(metadataBuilder, adviceChainElement, "synchronization-factory", "transactionSynchronizationFactory");
        }
        String errorChannel = element.getAttribute("error-channel");
        if (StringUtils.hasText((String)errorChannel)) {
            BeanDefinitionBuilder errorHandler = BeanDefinitionBuilder.genericBeanDefinition(MessagePublishingErrorHandler.class);
            errorHandler.addPropertyReference("defaultErrorChannel", errorChannel);
            metadataBuilder.addPropertyValue("errorHandler", (Object)errorHandler.getBeanDefinition());
        }
        return metadataBuilder.getBeanDefinition();
    }

    private void configureTrigger(Element pollerElement, BeanDefinitionBuilder targetBuilder, ParserContext parserContext) {
        String triggerAttribute = pollerElement.getAttribute("trigger");
        String fixedRateAttribute = pollerElement.getAttribute("fixed-rate");
        String fixedDelayAttribute = pollerElement.getAttribute("fixed-delay");
        String cronAttribute = pollerElement.getAttribute("cron");
        String timeUnit = pollerElement.getAttribute("time-unit");
        ArrayList<String> triggerBeanNames = new ArrayList<String>();
        if (StringUtils.hasText((String)triggerAttribute)) {
            this.trigger(pollerElement, parserContext, triggerAttribute, timeUnit, triggerBeanNames);
        }
        if (StringUtils.hasText((String)fixedRateAttribute)) {
            this.fixedRate(parserContext, fixedRateAttribute, timeUnit, triggerBeanNames);
        }
        if (StringUtils.hasText((String)fixedDelayAttribute)) {
            this.fixedDelay(parserContext, fixedDelayAttribute, timeUnit, triggerBeanNames);
        }
        if (StringUtils.hasText((String)cronAttribute)) {
            this.cron(pollerElement, parserContext, cronAttribute, timeUnit, triggerBeanNames);
        }
        if (triggerBeanNames.isEmpty()) {
            parserContext.getReaderContext().error(NO_TRIGGER_DEFINITIONS, (Object)pollerElement);
        }
        if (triggerBeanNames.size() > 1) {
            parserContext.getReaderContext().error(MULTIPLE_TRIGGER_DEFINITIONS, (Object)pollerElement);
        }
        targetBuilder.addPropertyReference("trigger", (String)triggerBeanNames.get(0));
    }

    private void trigger(Element pollerElement, ParserContext parserContext, String triggerAttribute, String timeUnit, List<String> triggerBeanNames) {
        if (StringUtils.hasText((String)timeUnit)) {
            parserContext.getReaderContext().error("The 'time-unit' attribute cannot be used with a 'trigger' reference.", (Object)pollerElement);
        }
        triggerBeanNames.add(triggerAttribute);
    }

    private void fixedRate(ParserContext parserContext, String fixedRateAttribute, String timeUnit, List<String> triggerBeanNames) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(PeriodicTrigger.class);
        builder.addConstructorArgValue((Object)fixedRateAttribute);
        if (StringUtils.hasText((String)timeUnit)) {
            builder.addConstructorArgValue((Object)timeUnit);
        }
        builder.addPropertyValue("fixedRate", (Object)Boolean.TRUE);
        String triggerBeanName = BeanDefinitionReaderUtils.registerWithGeneratedName((AbstractBeanDefinition)builder.getBeanDefinition(), (BeanDefinitionRegistry)parserContext.getRegistry());
        triggerBeanNames.add(triggerBeanName);
    }

    private void fixedDelay(ParserContext parserContext, String fixedDelayAttribute, String timeUnit, List<String> triggerBeanNames) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(PeriodicTrigger.class);
        builder.addConstructorArgValue((Object)fixedDelayAttribute);
        if (StringUtils.hasText((String)timeUnit)) {
            builder.addConstructorArgValue((Object)timeUnit);
        }
        builder.addPropertyValue("fixedRate", (Object)Boolean.FALSE);
        String triggerBeanName = BeanDefinitionReaderUtils.registerWithGeneratedName((AbstractBeanDefinition)builder.getBeanDefinition(), (BeanDefinitionRegistry)parserContext.getRegistry());
        triggerBeanNames.add(triggerBeanName);
    }

    private void cron(Element pollerElement, ParserContext parserContext, String cronAttribute, String timeUnit, List<String> triggerBeanNames) {
        if (StringUtils.hasText((String)timeUnit)) {
            parserContext.getReaderContext().error("The 'time-unit' attribute cannot be used with a 'cron' trigger.", (Object)pollerElement);
        }
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(CronTrigger.class);
        builder.addConstructorArgValue((Object)cronAttribute);
        String triggerBeanName = BeanDefinitionReaderUtils.registerWithGeneratedName((AbstractBeanDefinition)builder.getBeanDefinition(), (BeanDefinitionRegistry)parserContext.getRegistry());
        triggerBeanNames.add(triggerBeanName);
    }
}

