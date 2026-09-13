/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeanMetadataElement
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.config.BeanDefinitionHolder
 *  org.springframework.beans.factory.config.ConstructorArgumentValues
 *  org.springframework.beans.factory.config.ConstructorArgumentValues$ValueHolder
 *  org.springframework.beans.factory.config.RuntimeBeanReference
 *  org.springframework.beans.factory.config.TypedStringValue
 *  org.springframework.beans.factory.parsing.BeanComponentDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.support.BeanDefinitionRegistry
 *  org.springframework.beans.factory.support.ManagedList
 *  org.springframework.beans.factory.support.ManagedMap
 *  org.springframework.beans.factory.support.RootBeanDefinition
 *  org.springframework.beans.factory.xml.BeanDefinitionParserDelegate
 *  org.springframework.beans.factory.xml.ParserContext
 *  org.springframework.core.Conventions
 *  org.springframework.expression.common.LiteralExpression
 *  org.springframework.lang.Nullable
 *  org.springframework.transaction.interceptor.DefaultTransactionAttribute
 *  org.springframework.transaction.interceptor.MatchAlwaysTransactionAttributeSource
 *  org.springframework.transaction.interceptor.TransactionInterceptor
 *  org.springframework.util.Assert
 *  org.springframework.util.CollectionUtils
 *  org.springframework.util.StringUtils
 *  org.springframework.util.xml.DomUtils
 */
package org.springframework.integration.config.xml;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.beans.BeanMetadataElement;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParserDelegate;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.core.Conventions;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.channel.FixedSubscriberChannel;
import org.springframework.integration.config.ExpressionFactoryBean;
import org.springframework.integration.config.FixedSubscriberChannelBeanFactoryPostProcessor;
import org.springframework.integration.config.IntegrationConfigUtils;
import org.springframework.integration.transaction.TransactionHandleMessageAdvice;
import org.springframework.lang.Nullable;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.MatchAlwaysTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public abstract class IntegrationNamespaceUtils {
    public static final String REF_ATTRIBUTE = "ref";
    public static final String METHOD_ATTRIBUTE = "method";
    public static final String ORDER = "order";
    public static final String EXPRESSION_ATTRIBUTE = "expression";
    public static final String REQUEST_HANDLER_ADVICE_CHAIN = "request-handler-advice-chain";
    public static final String AUTO_STARTUP = "auto-startup";
    public static final String PHASE = "phase";
    public static final String ROLE = "role";

    public static void setValueIfAttributeDefined(BeanDefinitionBuilder builder, Element element, String attributeName, String propertyName) {
        IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element, attributeName, propertyName, false);
    }

    public static void setValueIfAttributeDefined(BeanDefinitionBuilder builder, Element element, String attributeName) {
        IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element, attributeName, false);
    }

    public static void setValueIfAttributeDefined(BeanDefinitionBuilder builder, Element element, String attributeName, String propertyName, boolean emptyStringAllowed) {
        String attributeValue = element.getAttribute(attributeName);
        if (StringUtils.hasText((String)attributeValue) || emptyStringAllowed && element.hasAttribute(attributeName)) {
            builder.addPropertyValue(propertyName, (Object)new TypedStringValue(attributeValue));
        }
    }

    public static void setValueIfAttributeDefined(BeanDefinitionBuilder builder, Element element, String attributeName, boolean emptyStringAllowed) {
        IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element, attributeName, Conventions.attributeNameToPropertyName((String)attributeName), emptyStringAllowed);
    }

    public static void setReferenceIfAttributeDefined(BeanDefinitionBuilder builder, Element element, String attributeName, String propertyName) {
        IntegrationNamespaceUtils.setReferenceIfAttributeDefined(builder, element, attributeName, propertyName, false);
    }

    public static void setReferenceIfAttributeDefined(BeanDefinitionBuilder builder, Element element, String attributeName, String propertyName, boolean emptyStringAllowed) {
        if (element.hasAttribute(attributeName)) {
            String attributeValue = element.getAttribute(attributeName);
            if (StringUtils.hasText((String)attributeValue)) {
                builder.addPropertyReference(propertyName, attributeValue);
            } else if (emptyStringAllowed) {
                builder.addPropertyValue(propertyName, null);
            }
        }
    }

    public static void setReferenceIfAttributeDefined(BeanDefinitionBuilder builder, Element element, String attributeName) {
        IntegrationNamespaceUtils.setReferenceIfAttributeDefined(builder, element, attributeName, false);
    }

    public static void setReferenceIfAttributeDefined(BeanDefinitionBuilder builder, Element element, String attributeName, boolean emptyStringAllowed) {
        IntegrationNamespaceUtils.setReferenceIfAttributeDefined(builder, element, attributeName, Conventions.attributeNameToPropertyName((String)attributeName), emptyStringAllowed);
    }

    public static String createElementDescription(Element element) {
        String elementId = "'" + element.getNodeName() + "'";
        String id = element.getAttribute("id");
        if (StringUtils.hasText((String)id)) {
            elementId = elementId + " with id='" + id + "'";
        }
        return elementId;
    }

    public static void configurePollerMetadata(Element pollerElement, BeanDefinitionBuilder targetBuilder, ParserContext parserContext) {
        if (pollerElement.hasAttribute(REF_ATTRIBUTE)) {
            int numberOfAttributes = pollerElement.getAttributes().getLength();
            if (!(numberOfAttributes == 1 || numberOfAttributes == 2 && pollerElement.hasAttribute("default") && pollerElement.getAttribute("default").equals("false"))) {
                parserContext.getReaderContext().error("A 'poller' element that provides a 'ref' must have no other attributes.", (Object)pollerElement);
            }
            if (pollerElement.getChildNodes().getLength() != 0) {
                parserContext.getReaderContext().error("A 'poller' element that provides a 'ref' must have no child elements.", (Object)pollerElement);
            }
            targetBuilder.addPropertyReference("pollerMetadata", pollerElement.getAttribute(REF_ATTRIBUTE));
        } else {
            BeanDefinition beanDefinition = parserContext.getDelegate().parseCustomElement(pollerElement, (BeanDefinition)targetBuilder.getBeanDefinition());
            if (beanDefinition == null) {
                parserContext.getReaderContext().error("BeanDefinition must not be null", (Object)pollerElement);
            }
            targetBuilder.addPropertyValue("pollerMetadata", (Object)beanDefinition);
        }
    }

    public static String getTextFromAttributeOrNestedElement(Element element, String name, ParserContext parserContext) {
        String attr = element.getAttribute(name);
        Element childElement = DomUtils.getChildElementByTagName((Element)element, (String)name);
        if (StringUtils.hasText((String)attr) && childElement != null) {
            parserContext.getReaderContext().error("Either an attribute or a child element can be specified for " + name + " but not both", (Object)element);
            return null;
        }
        if (!StringUtils.hasText((String)attr) && childElement == null) {
            return null;
        }
        return StringUtils.hasText((String)attr) ? attr : childElement.getTextContent();
    }

    public static BeanComponentDefinition parseInnerHandlerDefinition(Element element, ParserContext parserContext) {
        String ref;
        List childElements = DomUtils.getChildElementsByTagName((Element)element, (String)"bean");
        BeanComponentDefinition innerComponentDefinition = null;
        if (childElements.size() == 1) {
            Element beanElement = (Element)childElements.get(0);
            BeanDefinitionParserDelegate delegate = parserContext.getDelegate();
            BeanDefinitionHolder bdHolder = delegate.parseBeanDefinitionElement(beanElement);
            bdHolder = delegate.decorateBeanDefinitionIfRequired(beanElement, bdHolder);
            BeanDefinition inDef = bdHolder.getBeanDefinition();
            innerComponentDefinition = new BeanComponentDefinition(inDef, bdHolder.getBeanName());
        }
        if (StringUtils.hasText((String)(ref = element.getAttribute(REF_ATTRIBUTE))) && innerComponentDefinition != null) {
            parserContext.getReaderContext().error("Ambiguous definition. Inner bean " + innerComponentDefinition.getBeanDefinition().getBeanClassName() + " declaration and \"ref\" " + ref + " are not allowed together on element " + IntegrationNamespaceUtils.createElementDescription(element) + ".", parserContext.extractSource((Object)element));
        }
        return innerComponentDefinition;
    }

    public static void configureHeaderMapper(Element element, BeanDefinitionBuilder rootBuilder, ParserContext parserContext, Class<?> headerMapperClass, String replyHeaderValue) {
        IntegrationNamespaceUtils.configureHeaderMapper(element, rootBuilder, parserContext, BeanDefinitionBuilder.genericBeanDefinition(headerMapperClass), replyHeaderValue);
    }

    public static void configureHeaderMapper(Element element, BeanDefinitionBuilder rootBuilder, ParserContext parserContext, BeanDefinitionBuilder headerMapperBuilder, @Nullable String replyHeaderValueArg) {
        String defaultMappedReplyHeadersAttributeName = "mapped-reply-headers";
        String replyHeaderValue = replyHeaderValueArg;
        if (!StringUtils.hasText((String)replyHeaderValue)) {
            replyHeaderValue = defaultMappedReplyHeadersAttributeName;
        }
        boolean hasHeaderMapper = element.hasAttribute("header-mapper");
        boolean hasMappedRequestHeaders = element.hasAttribute("mapped-request-headers");
        boolean hasMappedReplyHeaders = element.hasAttribute(replyHeaderValue);
        if (hasHeaderMapper && (hasMappedRequestHeaders || hasMappedReplyHeaders)) {
            parserContext.getReaderContext().error("The 'header-mapper' attribute is mutually exclusive with 'mapped-request-headers' or 'mapped-reply-headers'. You can only use one or the others", (Object)element);
        }
        IntegrationNamespaceUtils.setReferenceIfAttributeDefined(rootBuilder, element, "header-mapper");
        if (hasMappedRequestHeaders || hasMappedReplyHeaders) {
            if (hasMappedRequestHeaders) {
                headerMapperBuilder.addPropertyValue("requestHeaderNames", (Object)element.getAttribute("mapped-request-headers"));
            }
            if (hasMappedReplyHeaders) {
                headerMapperBuilder.addPropertyValue("replyHeaderNames", (Object)element.getAttribute(replyHeaderValue));
            }
            rootBuilder.addPropertyValue("headerMapper", (Object)headerMapperBuilder.getBeanDefinition());
        }
    }

    public static BeanDefinition configureTransactionAttributes(Element txElement) {
        return IntegrationNamespaceUtils.configureTransactionAttributes(txElement, false);
    }

    public static BeanDefinition configureTransactionAttributes(Element txElement, boolean handleMessageAdvice) {
        BeanDefinition txDefinition = IntegrationNamespaceUtils.configureTransactionDefinition(txElement);
        BeanDefinitionBuilder attributeSourceBuilder = BeanDefinitionBuilder.genericBeanDefinition(MatchAlwaysTransactionAttributeSource.class);
        attributeSourceBuilder.addPropertyValue("transactionAttribute", (Object)txDefinition);
        BeanDefinitionBuilder txInterceptorBuilder = BeanDefinitionBuilder.genericBeanDefinition(handleMessageAdvice ? TransactionHandleMessageAdvice.class : TransactionInterceptor.class);
        txInterceptorBuilder.addPropertyReference("transactionManager", txElement.getAttribute("transaction-manager"));
        txInterceptorBuilder.addPropertyValue("transactionAttributeSource", (Object)attributeSourceBuilder.getBeanDefinition());
        return txInterceptorBuilder.getBeanDefinition();
    }

    public static BeanDefinition configureTransactionDefinition(Element txElement) {
        BeanDefinitionBuilder txDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(DefaultTransactionAttribute.class);
        txDefinitionBuilder.addPropertyValue("propagationBehaviorName", (Object)("PROPAGATION_" + txElement.getAttribute("propagation")));
        txDefinitionBuilder.addPropertyValue("isolationLevelName", (Object)("ISOLATION_" + txElement.getAttribute("isolation")));
        txDefinitionBuilder.addPropertyValue("timeout", (Object)txElement.getAttribute("timeout"));
        txDefinitionBuilder.addPropertyValue("readOnly", (Object)txElement.getAttribute("read-only"));
        return txDefinitionBuilder.getBeanDefinition();
    }

    public static String[] generateAlias(Element element) {
        String[] handlerAlias = null;
        String id = element.getAttribute("id");
        if (StringUtils.hasText((String)id)) {
            handlerAlias = new String[]{id + ".handler"};
        }
        return handlerAlias;
    }

    public static void configureAndSetAdviceChainIfPresent(Element adviceChainElement, Element txElement, BeanDefinition parentBeanDefinition, ParserContext parserContext) {
        IntegrationNamespaceUtils.configureAndSetAdviceChainIfPresent(adviceChainElement, txElement, false, parentBeanDefinition, parserContext);
    }

    public static void configureAndSetAdviceChainIfPresent(Element adviceChainElement, Element txElement, boolean handleMessageAdvice, BeanDefinition parentBeanDefinition, ParserContext parserContext) {
        IntegrationNamespaceUtils.configureAndSetAdviceChainIfPresent(adviceChainElement, txElement, handleMessageAdvice, parentBeanDefinition, parserContext, "adviceChain");
    }

    public static void configureAndSetAdviceChainIfPresent(Element adviceChainElement, Element txElement, BeanDefinition parentBeanDefinition, ParserContext parserContext, String propertyName) {
        IntegrationNamespaceUtils.configureAndSetAdviceChainIfPresent(adviceChainElement, txElement, false, parentBeanDefinition, parserContext, propertyName);
    }

    public static void configureAndSetAdviceChainIfPresent(Element adviceChainElement, Element txElement, boolean handleMessageAdvice, BeanDefinition parentBeanDefinition, ParserContext parserContext, String propertyName) {
        ManagedList adviceChain = IntegrationNamespaceUtils.configureAdviceChain(adviceChainElement, txElement, handleMessageAdvice, parentBeanDefinition, parserContext);
        if (!CollectionUtils.isEmpty((Collection)adviceChain)) {
            parentBeanDefinition.getPropertyValues().add(propertyName, (Object)adviceChain);
        }
    }

    public static ManagedList configureAdviceChain(Element adviceChainElement, Element txElement, BeanDefinition parentBeanDefinition, ParserContext parserContext) {
        return IntegrationNamespaceUtils.configureAdviceChain(adviceChainElement, txElement, false, parentBeanDefinition, parserContext);
    }

    public static ManagedList configureAdviceChain(Element adviceChainElement, Element txElement, boolean handleMessageAdvice, BeanDefinition parentBeanDefinition, ParserContext parserContext) {
        ManagedList adviceChain = new ManagedList();
        if (txElement != null) {
            adviceChain.add((Object)IntegrationNamespaceUtils.configureTransactionAttributes(txElement, handleMessageAdvice));
        }
        if (adviceChainElement != null) {
            NodeList childNodes = adviceChainElement.getChildNodes();
            for (int i = 0; i < childNodes.getLength(); ++i) {
                Node child = childNodes.item(i);
                if (child.getNodeType() != 1) continue;
                Element childElement = (Element)child;
                String localName = child.getLocalName();
                if ("bean".equals(localName)) {
                    BeanDefinitionHolder holder = parserContext.getDelegate().parseBeanDefinitionElement(childElement, parentBeanDefinition);
                    parserContext.registerBeanComponent(new BeanComponentDefinition(holder));
                    adviceChain.add((Object)new RuntimeBeanReference(holder.getBeanName()));
                    continue;
                }
                if (REF_ATTRIBUTE.equals(localName)) {
                    String ref = childElement.getAttribute("bean");
                    adviceChain.add((Object)new RuntimeBeanReference(ref));
                    continue;
                }
                BeanDefinition customBeanDefinition = parserContext.getDelegate().parseCustomElement(childElement, parentBeanDefinition);
                if (customBeanDefinition == null) {
                    parserContext.getReaderContext().error("failed to parse custom element '" + localName + "'", (Object)childElement);
                }
                adviceChain.add((Object)customBeanDefinition);
            }
        }
        return adviceChain;
    }

    public static BeanDefinition createExpressionDefinitionFromValueOrExpression(String valueElementName, String expressionElementName, ParserContext parserContext, Element element, boolean oneRequired) {
        BeanDefinition expressionDef;
        Assert.hasText((String)valueElementName, (String)"'valueElementName' must not be empty");
        Assert.hasText((String)expressionElementName, (String)"'expressionElementName' must not be empty");
        String valueElementValue = element.getAttribute(valueElementName);
        String expressionElementValue = element.getAttribute(expressionElementName);
        boolean hasAttributeValue = StringUtils.hasText((String)valueElementValue);
        boolean hasAttributeExpression = StringUtils.hasText((String)expressionElementValue);
        if (hasAttributeValue && hasAttributeExpression) {
            parserContext.getReaderContext().error("Only one of '" + valueElementName + "' or '" + expressionElementName + "' is allowed", (Object)element);
        }
        if (oneRequired && !hasAttributeValue && !hasAttributeExpression) {
            parserContext.getReaderContext().error("One of '" + valueElementName + "' or '" + expressionElementName + "' is required", (Object)element);
        }
        if (hasAttributeValue) {
            expressionDef = new RootBeanDefinition(LiteralExpression.class);
            expressionDef.getConstructorArgumentValues().addGenericArgumentValue((Object)valueElementValue);
        } else {
            expressionDef = IntegrationNamespaceUtils.createExpressionDefIfAttributeDefined(expressionElementName, element);
        }
        return expressionDef;
    }

    public static BeanDefinition createExpressionDefIfAttributeDefined(String expressionElementName, Element element) {
        Assert.hasText((String)expressionElementName, (String)"'expressionElementName' must no be empty");
        String expressionElementValue = element.getAttribute(expressionElementName);
        if (StringUtils.hasText((String)expressionElementValue)) {
            BeanDefinitionBuilder expressionDefBuilder = BeanDefinitionBuilder.genericBeanDefinition(ExpressionFactoryBean.class);
            expressionDefBuilder.addConstructorArgValue((Object)expressionElementValue);
            return expressionDefBuilder.getRawBeanDefinition();
        }
        return null;
    }

    public static String createDirectChannel(Element element, ParserContext parserContext) {
        String channelId = element.getAttribute("id");
        if (!StringUtils.hasText((String)channelId)) {
            parserContext.getReaderContext().error("The channel-adapter's 'id' attribute is required when no 'channel' reference has been provided, because that 'id' would be used for the created channel.", (Object)element);
        }
        IntegrationConfigUtils.autoCreateDirectChannel(channelId, parserContext.getRegistry());
        return channelId;
    }

    public static void checkAndConfigureFixedSubscriberChannel(Element element, ParserContext parserContext, String channelName, String handlerBeanName) {
        BeanDefinitionRegistry registry = parserContext.getRegistry();
        if (registry.containsBeanDefinition(channelName)) {
            BeanDefinition inputChannelDefinition = registry.getBeanDefinition(channelName);
            if (FixedSubscriberChannel.class.getName().equals(inputChannelDefinition.getBeanClassName())) {
                ConstructorArgumentValues constructorArgumentValues = inputChannelDefinition.getConstructorArgumentValues();
                if (constructorArgumentValues.isEmpty()) {
                    constructorArgumentValues.addGenericArgumentValue((Object)new RuntimeBeanReference(handlerBeanName));
                } else {
                    parserContext.getReaderContext().error("Only one subscriber is allowed for a FixedSubscriberChannel.", (Object)element);
                }
            }
        } else {
            ManagedMap candidates;
            BeanDefinition bfppd;
            if (!registry.containsBeanDefinition("fixedSubscriberChannelBeanFactoryPostProcessor")) {
                bfppd = new RootBeanDefinition(FixedSubscriberChannelBeanFactoryPostProcessor.class);
                registry.registerBeanDefinition("fixedSubscriberChannelBeanFactoryPostProcessor", bfppd);
            } else {
                bfppd = registry.getBeanDefinition("fixedSubscriberChannelBeanFactoryPostProcessor");
            }
            ConstructorArgumentValues.ValueHolder argumentValue = bfppd.getConstructorArgumentValues().getArgumentValue(0, Map.class);
            if (argumentValue == null) {
                candidates = new ManagedMap();
                bfppd.getConstructorArgumentValues().addIndexedArgumentValue(0, (Object)candidates);
            } else {
                candidates = (ManagedMap)argumentValue.getValue();
            }
            candidates.put((Object)handlerBeanName, (Object)channelName);
        }
    }

    public static void injectPropertyWithAdapter(String beanRefAttribute, String methodRefAttribute, String expressionAttribute, String beanProperty, String adapterClass, Element element, BeanDefinitionBuilder builder, BeanMetadataElement processor, ParserContext parserContext) {
        BeanMetadataElement adapter = IntegrationNamespaceUtils.constructAdapter(beanRefAttribute, methodRefAttribute, expressionAttribute, adapterClass, element, processor, parserContext);
        if (adapter != null) {
            builder.addPropertyValue(beanProperty, (Object)adapter);
        }
    }

    public static void injectConstructorWithAdapter(String beanRefAttribute, String methodRefAttribute, String expressionAttribute, String adapterClass, Element element, BeanDefinitionBuilder builder, BeanMetadataElement processor, ParserContext parserContext) {
        BeanMetadataElement adapter = IntegrationNamespaceUtils.constructAdapter(beanRefAttribute, methodRefAttribute, expressionAttribute, adapterClass, element, processor, parserContext);
        if (adapter != null) {
            builder.addConstructorArgValue((Object)adapter);
        }
    }

    private static BeanMetadataElement constructAdapter(String beanRefAttribute, String methodRefAttribute, String expressionAttribute, String adapterClass, Element element, BeanMetadataElement processor, ParserContext parserContext) {
        String beanRef = element.getAttribute(beanRefAttribute);
        String beanMethod = element.getAttribute(methodRefAttribute);
        String expression = element.getAttribute(expressionAttribute);
        boolean hasBeanRef = StringUtils.hasText((String)beanRef);
        boolean hasExpression = StringUtils.hasText((String)expression);
        if (hasBeanRef && hasExpression) {
            parserContext.getReaderContext().error("Exactly one of the '" + beanRefAttribute + "' or '" + expressionAttribute + "' attribute is allowed.", (Object)element);
        }
        BeanMetadataElement adapter = null;
        if (hasBeanRef) {
            adapter = IntegrationNamespaceUtils.createAdapter((BeanMetadataElement)new RuntimeBeanReference(beanRef), beanMethod, adapterClass);
        } else if (hasExpression) {
            BeanDefinitionBuilder adapterBuilder = BeanDefinitionBuilder.genericBeanDefinition((String)("org.springframework.integration.aggregator.ExpressionEvaluating" + adapterClass));
            adapterBuilder.addConstructorArgValue((Object)expression);
            adapter = adapterBuilder.getBeanDefinition();
        } else if (processor != null) {
            adapter = IntegrationNamespaceUtils.createAdapter(processor, beanMethod, adapterClass);
        }
        return adapter;
    }

    private static BeanMetadataElement createAdapter(BeanMetadataElement ref, String method, String unqualifiedClassName) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition((String)("org.springframework.integration.config." + unqualifiedClassName + "FactoryBean"));
        builder.addPropertyValue("target", (Object)ref);
        if (StringUtils.hasText((String)method)) {
            builder.addPropertyValue("methodName", (Object)method);
        }
        return builder.getBeanDefinition();
    }
}

