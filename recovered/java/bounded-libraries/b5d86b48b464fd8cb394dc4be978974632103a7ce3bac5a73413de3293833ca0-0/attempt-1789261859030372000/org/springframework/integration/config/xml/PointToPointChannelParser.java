/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.config.TypedStringValue
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.xml.ParserContext
 *  org.springframework.util.StringUtils
 *  org.springframework.util.xml.DomUtils
 */
package org.springframework.integration.config.xml;

import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.ExecutorChannel;
import org.springframework.integration.channel.FixedSubscriberChannel;
import org.springframework.integration.channel.PriorityChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.channel.RendezvousChannel;
import org.springframework.integration.config.xml.AbstractChannelParser;
import org.springframework.integration.config.xml.IntegrationNamespaceUtils;
import org.springframework.integration.store.MessageGroupQueue;
import org.springframework.util.StringUtils;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

public class PointToPointChannelParser
extends AbstractChannelParser {
    @Override
    protected BeanDefinitionBuilder buildBeanDefinition(Element element, ParserContext parserContext) {
        BeanDefinitionBuilder builder = null;
        String fixedSubscriberChannel = element.getAttribute("fixed-subscriber");
        boolean isFixedSubscriber = "true".equalsIgnoreCase(fixedSubscriberChannel.trim());
        String channel = element.getAttribute("id");
        Element queueElement = DomUtils.getChildElementByTagName((Element)element, (String)"queue");
        if (queueElement != null) {
            builder = this.queue(element, parserContext, queueElement, channel);
        } else {
            queueElement = DomUtils.getChildElementByTagName((Element)element, (String)"priority-queue");
            if (queueElement != null) {
                builder = this.priorityQueue(element, parserContext, queueElement, channel);
            } else {
                queueElement = DomUtils.getChildElementByTagName((Element)element, (String)"rendezvous-queue");
                if (queueElement != null) {
                    builder = BeanDefinitionBuilder.genericBeanDefinition(RendezvousChannel.class);
                }
            }
        }
        Element dispatcherElement = DomUtils.getChildElementByTagName((Element)element, (String)"dispatcher");
        if (queueElement != null && dispatcherElement != null) {
            parserContext.getReaderContext().error("The 'dispatcher' sub-element and any queue sub-element are mutually exclusive.", (Object)element);
            return null;
        }
        if (queueElement != null) {
            if (isFixedSubscriber) {
                parserContext.getReaderContext().error("The 'fixed-subscriber' attribute is not allowed when a <queue/> child element is present.", (Object)element);
            }
            return builder;
        }
        builder = dispatcherElement == null ? BeanDefinitionBuilder.genericBeanDefinition(isFixedSubscriber ? FixedSubscriberChannel.class : DirectChannel.class) : this.dispatcher(element, parserContext, isFixedSubscriber, dispatcherElement);
        return builder;
    }

    private BeanDefinitionBuilder queue(Element element, ParserContext parserContext, Element queueElement, String channel) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(QueueChannel.class);
        boolean hasStoreRef = this.parseStoreRef(builder, queueElement, channel, false);
        boolean hasQueueRef = this.parseQueueRef(builder, queueElement);
        if (!hasStoreRef || !hasQueueRef) {
            boolean hasCapacity = this.parseQueueCapacity(builder, queueElement);
            if (hasCapacity && hasQueueRef) {
                parserContext.getReaderContext().error("The 'capacity' attribute is not allowed when providing a 'ref' to a custom queue.", (Object)element);
            }
            if (hasCapacity && hasStoreRef) {
                parserContext.getReaderContext().error("The 'capacity' attribute is not allowed when providing a 'message-store' to a custom MessageGroupStore.", (Object)element);
            }
        }
        if (hasStoreRef && hasQueueRef) {
            parserContext.getReaderContext().error("The 'message-store' attribute is not allowed when providing a 'ref' to a custom queue.", (Object)element);
        }
        return builder;
    }

    private BeanDefinitionBuilder priorityQueue(Element element, ParserContext parserContext, Element queueElement, String channel) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(PriorityChannel.class);
        boolean hasCapacity = this.parseQueueCapacity(builder, queueElement);
        String comparatorRef = queueElement.getAttribute("comparator");
        if (StringUtils.hasText((String)comparatorRef)) {
            builder.addConstructorArgReference(comparatorRef);
        }
        if (this.parseStoreRef(builder, queueElement, channel, true)) {
            if (StringUtils.hasText((String)comparatorRef)) {
                parserContext.getReaderContext().error("The 'message-store' attribute is not allowed when providing a 'comparator' to a priority queue.", (Object)element);
            }
            if (hasCapacity) {
                parserContext.getReaderContext().error("The 'capacity' attribute is not allowed when providing a 'message-store' to a custom MessageGroupStore.", (Object)element);
            }
        }
        return builder;
    }

    private BeanDefinitionBuilder dispatcher(Element element, ParserContext parserContext, boolean isFixedSubscriber, Element dispatcherElement) {
        BeanDefinitionBuilder builder;
        String taskExecutor;
        if (isFixedSubscriber) {
            parserContext.getReaderContext().error("The 'fixed-subscriber' attribute is not allowed when a <dispatcher/> child element is present.", (Object)element);
        }
        if (StringUtils.hasText((String)(taskExecutor = dispatcherElement.getAttribute("task-executor")))) {
            builder = BeanDefinitionBuilder.genericBeanDefinition(ExecutorChannel.class);
            builder.addConstructorArgReference(taskExecutor);
        } else {
            builder = BeanDefinitionBuilder.genericBeanDefinition(DirectChannel.class);
        }
        String loadBalancer = dispatcherElement.getAttribute("load-balancer");
        String loadBalancerRef = dispatcherElement.getAttribute("load-balancer-ref");
        if (StringUtils.hasText((String)loadBalancer) && StringUtils.hasText((String)loadBalancerRef)) {
            parserContext.getReaderContext().error("'load-balancer' and 'load-balancer-ref' are mutually exclusive", (Object)element);
        }
        if (StringUtils.hasText((String)loadBalancerRef)) {
            builder.addConstructorArgReference(loadBalancerRef);
        } else if ("none".equals(loadBalancer)) {
            builder.addConstructorArgValue(null);
        }
        IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, dispatcherElement, "failover");
        IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, dispatcherElement, "max-subscribers");
        return builder;
    }

    private boolean parseQueueCapacity(BeanDefinitionBuilder builder, Element queueElement) {
        String capacity = queueElement.getAttribute("capacity");
        if (StringUtils.hasText((String)capacity)) {
            builder.addConstructorArgValue((Object)capacity);
            return true;
        }
        return false;
    }

    private boolean parseQueueRef(BeanDefinitionBuilder builder, Element queueElement) {
        String queueRef = queueElement.getAttribute("ref");
        if (StringUtils.hasText((String)queueRef)) {
            builder.addConstructorArgReference(queueRef);
            return true;
        }
        return false;
    }

    private boolean parseStoreRef(BeanDefinitionBuilder builder, Element queueElement, String channel, boolean priority) {
        String storeRef = queueElement.getAttribute("message-store");
        if (StringUtils.hasText((String)storeRef)) {
            BeanDefinitionBuilder queueBuilder = BeanDefinitionBuilder.genericBeanDefinition(MessageGroupQueue.class);
            queueBuilder.addConstructorArgReference(storeRef);
            queueBuilder.addConstructorArgValue((Object)(new TypedStringValue(storeRef).getValue() + ":" + channel));
            queueBuilder.addPropertyValue("priority", (Object)priority);
            this.parseQueueCapacity(queueBuilder, queueElement);
            builder.addConstructorArgValue((Object)queueBuilder.getBeanDefinition());
            return true;
        }
        return false;
    }
}

