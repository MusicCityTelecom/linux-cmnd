/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.support.ManagedList
 *  org.springframework.beans.factory.support.RootBeanDefinition
 *  org.springframework.beans.factory.xml.ParserContext
 *  org.springframework.util.StringUtils
 *  org.springframework.util.xml.DomUtils
 */
package org.springframework.integration.config.xml;

import java.util.List;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.config.xml.AbstractRouterParser;
import org.springframework.integration.filter.ExpressionEvaluatingSelector;
import org.springframework.integration.router.RecipientListRouter;
import org.springframework.util.StringUtils;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

public class RecipientListRouterParser
extends AbstractRouterParser {
    @Override
    protected BeanDefinition doParseRouter(Element element, ParserContext parserContext) {
        BeanDefinitionBuilder recipientListRouterBuilder = BeanDefinitionBuilder.genericBeanDefinition(RecipientListRouter.class);
        List childElements = DomUtils.getChildElementsByTagName((Element)element, (String)"recipient");
        ManagedList recipientList = new ManagedList();
        for (Element childElement : childElements) {
            BeanDefinitionBuilder recipientBuilder = BeanDefinitionBuilder.genericBeanDefinition(RecipientListRouter.Recipient.class);
            recipientBuilder.addConstructorArgReference(childElement.getAttribute("channel"));
            String expression = childElement.getAttribute("selector-expression");
            if (StringUtils.hasText((String)expression)) {
                RootBeanDefinition selectorDef = new RootBeanDefinition(ExpressionEvaluatingSelector.class);
                selectorDef.getConstructorArgumentValues().addGenericArgumentValue((Object)expression);
                String selectorBeanName = parserContext.getReaderContext().registerWithGeneratedName((BeanDefinition)selectorDef);
                recipientBuilder.addConstructorArgReference(selectorBeanName);
            }
            recipientList.add((Object)recipientBuilder.getBeanDefinition());
        }
        if (recipientList.size() > 0) {
            recipientListRouterBuilder.addPropertyValue("recipients", (Object)recipientList);
        }
        return recipientListRouterBuilder.getBeanDefinition();
    }
}

