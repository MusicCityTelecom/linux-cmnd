/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.xml.ParserContext
 *  org.springframework.util.Assert
 */
package org.springframework.integration.config.xml;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.config.xml.AbstractTransformerParser;
import org.springframework.integration.transformer.ClaimCheckInTransformer;
import org.springframework.util.Assert;
import org.w3c.dom.Element;

public class ClaimCheckInParser
extends AbstractTransformerParser {
    @Override
    protected String getTransformerClassName() {
        return ClaimCheckInTransformer.class.getName();
    }

    @Override
    protected void parseTransformer(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
        String messageStoreRef = element.getAttribute("message-store");
        Assert.hasText((String)messageStoreRef, (String)"The 'message-store' attribute is required.");
        builder.addConstructorArgReference(messageStoreRef);
    }
}

