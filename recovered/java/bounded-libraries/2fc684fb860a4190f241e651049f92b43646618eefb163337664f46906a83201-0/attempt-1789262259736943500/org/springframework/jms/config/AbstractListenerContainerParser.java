/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.MutablePropertyValues
 *  org.springframework.beans.PropertyValue
 *  org.springframework.beans.PropertyValues
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.config.RuntimeBeanReference
 *  org.springframework.beans.factory.parsing.BeanComponentDefinition
 *  org.springframework.beans.factory.parsing.CompositeComponentDefinition
 *  org.springframework.beans.factory.support.RootBeanDefinition
 *  org.springframework.beans.factory.xml.BeanDefinitionParser
 *  org.springframework.beans.factory.xml.ParserContext
 *  org.springframework.lang.Nullable
 *  org.springframework.util.StringUtils
 */
package org.springframework.jms.config;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.parsing.CompositeComponentDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

abstract class AbstractListenerContainerParser
implements BeanDefinitionParser {
    protected static final String FACTORY_ID_ATTRIBUTE = "factory-id";
    protected static final String LISTENER_ELEMENT = "listener";
    protected static final String ID_ATTRIBUTE = "id";
    protected static final String DESTINATION_ATTRIBUTE = "destination";
    protected static final String SUBSCRIPTION_ATTRIBUTE = "subscription";
    protected static final String SELECTOR_ATTRIBUTE = "selector";
    protected static final String REF_ATTRIBUTE = "ref";
    protected static final String METHOD_ATTRIBUTE = "method";
    protected static final String DESTINATION_RESOLVER_ATTRIBUTE = "destination-resolver";
    protected static final String MESSAGE_CONVERTER_ATTRIBUTE = "message-converter";
    protected static final String RESPONSE_DESTINATION_ATTRIBUTE = "response-destination";
    protected static final String DESTINATION_TYPE_ATTRIBUTE = "destination-type";
    protected static final String DESTINATION_TYPE_QUEUE = "queue";
    protected static final String DESTINATION_TYPE_TOPIC = "topic";
    protected static final String DESTINATION_TYPE_DURABLE_TOPIC = "durableTopic";
    protected static final String DESTINATION_TYPE_SHARED_TOPIC = "sharedTopic";
    protected static final String DESTINATION_TYPE_SHARED_DURABLE_TOPIC = "sharedDurableTopic";
    protected static final String RESPONSE_DESTINATION_TYPE_ATTRIBUTE = "response-destination-type";
    protected static final String CLIENT_ID_ATTRIBUTE = "client-id";
    protected static final String ACKNOWLEDGE_ATTRIBUTE = "acknowledge";
    protected static final String ACKNOWLEDGE_AUTO = "auto";
    protected static final String ACKNOWLEDGE_CLIENT = "client";
    protected static final String ACKNOWLEDGE_DUPS_OK = "dups-ok";
    protected static final String ACKNOWLEDGE_TRANSACTED = "transacted";
    protected static final String TRANSACTION_MANAGER_ATTRIBUTE = "transaction-manager";
    protected static final String CONCURRENCY_ATTRIBUTE = "concurrency";
    protected static final String PHASE_ATTRIBUTE = "phase";
    protected static final String PREFETCH_ATTRIBUTE = "prefetch";

    AbstractListenerContainerParser() {
    }

    @Nullable
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        RootBeanDefinition beanDefinition;
        CompositeComponentDefinition compositeDef = new CompositeComponentDefinition(element.getTagName(), parserContext.extractSource((Object)element));
        parserContext.pushContainingComponent(compositeDef);
        MutablePropertyValues commonProperties = this.parseCommonContainerProperties(element, parserContext);
        MutablePropertyValues specificProperties = this.parseSpecificContainerProperties(element, parserContext);
        String factoryId = element.getAttribute(FACTORY_ID_ATTRIBUTE);
        if (StringUtils.hasText((String)factoryId) && (beanDefinition = this.createContainerFactory(factoryId, element, parserContext, (PropertyValues)commonProperties, (PropertyValues)specificProperties)) != null) {
            beanDefinition.setSource(parserContext.extractSource((Object)element));
            parserContext.registerBeanComponent(new BeanComponentDefinition((BeanDefinition)beanDefinition, factoryId));
        }
        NodeList childNodes = element.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); ++i) {
            String localName;
            Node child = childNodes.item(i);
            if (child.getNodeType() != 1 || !LISTENER_ELEMENT.equals(localName = parserContext.getDelegate().getLocalName(child))) continue;
            this.parseListener(element, (Element)child, parserContext, commonProperties, (PropertyValues)specificProperties);
        }
        parserContext.popAndRegisterContainingComponent();
        return null;
    }

    private void parseListener(Element containerEle, Element listenerEle, ParserContext parserContext, MutablePropertyValues commonContainerProperties, PropertyValues specificContainerProperties) {
        String containerBeanName;
        PropertyValue messageConverterPv;
        RootBeanDefinition listenerDef = new RootBeanDefinition();
        listenerDef.setSource(parserContext.extractSource((Object)listenerEle));
        listenerDef.setBeanClassName("org.springframework.jms.listener.adapter.MessageListenerAdapter");
        String ref = listenerEle.getAttribute(REF_ATTRIBUTE);
        if (!StringUtils.hasText((String)ref)) {
            parserContext.getReaderContext().error("Listener 'ref' attribute contains empty value.", (Object)listenerEle);
        } else {
            listenerDef.getPropertyValues().add("delegate", (Object)new RuntimeBeanReference(ref));
        }
        if (listenerEle.hasAttribute(METHOD_ATTRIBUTE)) {
            String method = listenerEle.getAttribute(METHOD_ATTRIBUTE);
            if (!StringUtils.hasText((String)method)) {
                parserContext.getReaderContext().error("Listener 'method' attribute contains empty value.", (Object)listenerEle);
            }
            listenerDef.getPropertyValues().add("defaultListenerMethod", (Object)method);
        }
        if ((messageConverterPv = commonContainerProperties.getPropertyValue("messageConverter")) != null) {
            listenerDef.getPropertyValues().addPropertyValue(messageConverterPv);
        }
        RootBeanDefinition containerDef = this.createContainer(containerEle, listenerEle, parserContext, (PropertyValues)commonContainerProperties, specificContainerProperties);
        containerDef.getPropertyValues().add("messageListener", (Object)listenerDef);
        if (listenerEle.hasAttribute(RESPONSE_DESTINATION_ATTRIBUTE)) {
            String responseDestination = listenerEle.getAttribute(RESPONSE_DESTINATION_ATTRIBUTE);
            Boolean pubSubDomain = (Boolean)commonContainerProperties.get("replyPubSubDomain");
            if (pubSubDomain == null) {
                pubSubDomain = false;
            }
            listenerDef.getPropertyValues().add(pubSubDomain != false ? "defaultResponseTopicName" : "defaultResponseQueueName", (Object)responseDestination);
            PropertyValue destinationResolver = containerDef.getPropertyValues().getPropertyValue("destinationResolver");
            if (destinationResolver != null) {
                listenerDef.getPropertyValues().addPropertyValue(destinationResolver);
            }
        }
        if (!StringUtils.hasText((String)(containerBeanName = listenerEle.getAttribute(ID_ATTRIBUTE)))) {
            containerBeanName = parserContext.getReaderContext().generateBeanName((BeanDefinition)containerDef);
        }
        parserContext.registerBeanComponent(new BeanComponentDefinition((BeanDefinition)containerDef, containerBeanName));
    }

    protected void parseListenerConfiguration(Element ele, ParserContext parserContext, MutablePropertyValues configValues) {
        String destination = ele.getAttribute(DESTINATION_ATTRIBUTE);
        if (!StringUtils.hasText((String)destination)) {
            parserContext.getReaderContext().error("Listener 'destination' attribute contains empty value.", (Object)ele);
        }
        configValues.add("destinationName", (Object)destination);
        if (ele.hasAttribute(SUBSCRIPTION_ATTRIBUTE)) {
            String subscription = ele.getAttribute(SUBSCRIPTION_ATTRIBUTE);
            if (!StringUtils.hasText((String)subscription)) {
                parserContext.getReaderContext().error("Listener 'subscription' attribute contains empty value.", (Object)ele);
            }
            configValues.add("subscriptionName", (Object)subscription);
        }
        if (ele.hasAttribute(SELECTOR_ATTRIBUTE)) {
            String selector = ele.getAttribute(SELECTOR_ATTRIBUTE);
            if (!StringUtils.hasText((String)selector)) {
                parserContext.getReaderContext().error("Listener 'selector' attribute contains empty value.", (Object)ele);
            }
            configValues.add("messageSelector", (Object)selector);
        }
        if (ele.hasAttribute(CONCURRENCY_ATTRIBUTE)) {
            String concurrency = ele.getAttribute(CONCURRENCY_ATTRIBUTE);
            if (!StringUtils.hasText((String)concurrency)) {
                parserContext.getReaderContext().error("Listener 'concurrency' attribute contains empty value.", (Object)ele);
            }
            configValues.add(CONCURRENCY_ATTRIBUTE, (Object)concurrency);
        }
    }

    protected MutablePropertyValues parseCommonContainerProperties(Element containerEle, ParserContext parserContext) {
        MutablePropertyValues properties = new MutablePropertyValues();
        String destinationType = containerEle.getAttribute(DESTINATION_TYPE_ATTRIBUTE);
        boolean pubSubDomain = false;
        boolean subscriptionDurable = false;
        boolean subscriptionShared = false;
        if (DESTINATION_TYPE_SHARED_DURABLE_TOPIC.equals(destinationType)) {
            pubSubDomain = true;
            subscriptionDurable = true;
            subscriptionShared = true;
        } else if (DESTINATION_TYPE_SHARED_TOPIC.equals(destinationType)) {
            pubSubDomain = true;
            subscriptionShared = true;
        } else if (DESTINATION_TYPE_DURABLE_TOPIC.equals(destinationType)) {
            pubSubDomain = true;
            subscriptionDurable = true;
        } else if (DESTINATION_TYPE_TOPIC.equals(destinationType)) {
            pubSubDomain = true;
        } else if (StringUtils.hasLength((String)destinationType) && !DESTINATION_TYPE_QUEUE.equals(destinationType)) {
            parserContext.getReaderContext().error("Invalid listener container 'destination-type': only \"queue\", \"topic\", \"durableTopic\", \"sharedTopic\", \"sharedDurableTopic\" supported.", (Object)containerEle);
        }
        properties.add("pubSubDomain", (Object)pubSubDomain);
        properties.add("subscriptionDurable", (Object)subscriptionDurable);
        properties.add("subscriptionShared", (Object)subscriptionShared);
        boolean replyPubSubDomain = false;
        String replyDestinationType = containerEle.getAttribute(RESPONSE_DESTINATION_TYPE_ATTRIBUTE);
        if (!StringUtils.hasText((String)replyDestinationType)) {
            replyPubSubDomain = pubSubDomain;
        } else if (DESTINATION_TYPE_TOPIC.equals(replyDestinationType)) {
            replyPubSubDomain = true;
        } else if (!DESTINATION_TYPE_QUEUE.equals(replyDestinationType)) {
            parserContext.getReaderContext().error("Invalid listener container 'response-destination-type': only \"queue\", \"topic\" supported.", (Object)containerEle);
        }
        properties.add("replyPubSubDomain", (Object)replyPubSubDomain);
        if (containerEle.hasAttribute(CLIENT_ID_ATTRIBUTE)) {
            String clientId = containerEle.getAttribute(CLIENT_ID_ATTRIBUTE);
            if (!StringUtils.hasText((String)clientId)) {
                parserContext.getReaderContext().error("Listener 'client-id' attribute contains empty value.", (Object)containerEle);
            }
            properties.add("clientId", (Object)clientId);
        }
        if (containerEle.hasAttribute(MESSAGE_CONVERTER_ATTRIBUTE)) {
            String messageConverter = containerEle.getAttribute(MESSAGE_CONVERTER_ATTRIBUTE);
            if (!StringUtils.hasText((String)messageConverter)) {
                parserContext.getReaderContext().error("listener container 'message-converter' attribute contains empty value.", (Object)containerEle);
            } else {
                properties.add("messageConverter", (Object)new RuntimeBeanReference(messageConverter));
            }
        }
        return properties;
    }

    protected abstract MutablePropertyValues parseSpecificContainerProperties(Element var1, ParserContext var2);

    @Nullable
    protected abstract RootBeanDefinition createContainerFactory(String var1, Element var2, ParserContext var3, PropertyValues var4, PropertyValues var5);

    protected abstract RootBeanDefinition createContainer(Element var1, Element var2, ParserContext var3, PropertyValues var4, PropertyValues var5);

    @Nullable
    protected Integer parseAcknowledgeMode(Element ele, ParserContext parserContext) {
        String acknowledge = ele.getAttribute(ACKNOWLEDGE_ATTRIBUTE);
        if (StringUtils.hasText((String)acknowledge)) {
            int acknowledgeMode = 1;
            if (ACKNOWLEDGE_TRANSACTED.equals(acknowledge)) {
                acknowledgeMode = 0;
            } else if (ACKNOWLEDGE_DUPS_OK.equals(acknowledge)) {
                acknowledgeMode = 3;
            } else if (ACKNOWLEDGE_CLIENT.equals(acknowledge)) {
                acknowledgeMode = 2;
            } else if (!ACKNOWLEDGE_AUTO.equals(acknowledge)) {
                parserContext.getReaderContext().error("Invalid listener container 'acknowledge' setting [" + acknowledge + "]: only \"auto\", \"client\", \"dups-ok\" and \"transacted\" supported.", (Object)ele);
            }
            return acknowledgeMode;
        }
        return null;
    }
}

