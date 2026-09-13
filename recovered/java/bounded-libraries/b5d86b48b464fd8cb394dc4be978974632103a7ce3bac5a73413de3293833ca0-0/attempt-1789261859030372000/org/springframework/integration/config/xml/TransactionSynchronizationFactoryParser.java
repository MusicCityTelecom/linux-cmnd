/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.support.AbstractBeanDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.support.RootBeanDefinition
 *  org.springframework.beans.factory.xml.AbstractBeanDefinitionParser
 *  org.springframework.beans.factory.xml.ParserContext
 *  org.springframework.util.StringUtils
 *  org.springframework.util.xml.DomUtils
 */
package org.springframework.integration.config.xml;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.config.ExpressionFactoryBean;
import org.springframework.integration.transaction.DefaultTransactionSynchronizationFactory;
import org.springframework.integration.transaction.ExpressionEvaluatingTransactionSynchronizationProcessor;
import org.springframework.util.StringUtils;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

public class TransactionSynchronizationFactoryParser
extends AbstractBeanDefinitionParser {
    protected AbstractBeanDefinition parseInternal(Element element, ParserContext parserContext) {
        BeanDefinitionBuilder syncFactoryBuilder = BeanDefinitionBuilder.genericBeanDefinition(DefaultTransactionSynchronizationFactory.class);
        Element beforeCommitElement = DomUtils.getChildElementByTagName((Element)element, (String)"before-commit");
        Element afterCommitElement = DomUtils.getChildElementByTagName((Element)element, (String)"after-commit");
        Element afterRollbackElement = DomUtils.getChildElementByTagName((Element)element, (String)"after-rollback");
        if (this.elementsNotDefined(beforeCommitElement, afterCommitElement, afterRollbackElement)) {
            parserContext.getReaderContext().error("At least one sub-element ('before-commit', 'after-commit' and/or 'after-rollback') must be defined", (Object)element);
        }
        BeanDefinitionBuilder expressionProcessor = BeanDefinitionBuilder.genericBeanDefinition(ExpressionEvaluatingTransactionSynchronizationProcessor.class);
        this.processSubElement(beforeCommitElement, parserContext, expressionProcessor, "beforeCommit");
        this.processSubElement(afterCommitElement, parserContext, expressionProcessor, "afterCommit");
        this.processSubElement(afterRollbackElement, parserContext, expressionProcessor, "afterRollback");
        syncFactoryBuilder.addConstructorArgValue((Object)expressionProcessor.getBeanDefinition());
        return syncFactoryBuilder.getBeanDefinition();
    }

    private void processSubElement(Element element, ParserContext parserContext, BeanDefinitionBuilder expressionProcessor, String elementPrefix) {
        if (element != null) {
            String expression = element.getAttribute("expression");
            String channel = element.getAttribute("channel");
            if (this.attributesNotDefined(expression, channel)) {
                parserContext.getReaderContext().error("At least one attribute ('expression' and/or 'channel') must be defined", (Object)element);
            }
            if (StringUtils.hasText((String)expression)) {
                RootBeanDefinition expressionDef = new RootBeanDefinition(ExpressionFactoryBean.class);
                expressionDef.getConstructorArgumentValues().addGenericArgumentValue((Object)expression);
                expressionProcessor.addPropertyValue(elementPrefix + "Expression", (Object)expressionDef);
            }
            if (StringUtils.hasText((String)channel)) {
                expressionProcessor.addPropertyReference(elementPrefix + "Channel", channel);
            } else {
                expressionProcessor.addPropertyReference(elementPrefix + "Channel", "nullChannel");
            }
        }
    }

    private boolean elementsNotDefined(Element ... elements) {
        for (Element element : elements) {
            if (element == null) continue;
            return false;
        }
        return true;
    }

    private boolean attributesNotDefined(String ... attributes) {
        for (String attribute : attributes) {
            if (!StringUtils.hasText((String)attribute)) continue;
            return false;
        }
        return true;
    }
}

