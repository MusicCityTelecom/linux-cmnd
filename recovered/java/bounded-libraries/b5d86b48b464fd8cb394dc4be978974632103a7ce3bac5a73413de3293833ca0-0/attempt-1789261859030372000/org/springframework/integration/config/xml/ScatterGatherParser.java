/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.config.RuntimeBeanReference
 *  org.springframework.beans.factory.support.AbstractBeanDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.support.RootBeanDefinition
 *  org.springframework.beans.factory.xml.ParserContext
 *  org.springframework.util.StringUtils
 *  org.springframework.util.xml.DomUtils
 */
package org.springframework.integration.config.xml;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.config.xml.AbstractConsumerEndpointParser;
import org.springframework.integration.config.xml.AggregatorParser;
import org.springframework.integration.config.xml.IntegrationNamespaceUtils;
import org.springframework.integration.config.xml.RecipientListRouterParser;
import org.springframework.integration.router.RecipientListRouter;
import org.springframework.integration.scattergather.ScatterGatherHandler;
import org.springframework.util.StringUtils;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

public class ScatterGatherParser
extends AbstractConsumerEndpointParser {
    private static final RecipientListRouterParser SCATTERER_PARSER = new RecipientListRouterParser();
    private static final AggregatorParser GATHERER_PARSER = new AggregatorParser();
    private static final DocumentBuilderFactory DOCUMENT_BUILDER_FACTORY = DocumentBuilderFactory.newInstance();

    @Override
    protected BeanDefinitionBuilder parseHandler(Element element, ParserContext parserContext) {
        boolean hasScatterer;
        String scatterChannel = element.getAttribute("scatter-channel");
        boolean hasScatterChannel = StringUtils.hasText((String)scatterChannel);
        Element scatterer = DomUtils.getChildElementByTagName((Element)element, (String)"scatterer");
        boolean bl = hasScatterer = scatterer != null;
        if (hasScatterChannel & hasScatterer) {
            parserContext.getReaderContext().error("'scatter-channel' attribute and 'scatterer' sub-element are mutually exclusive", (Object)element);
        }
        if (!hasScatterChannel & !hasScatterer) {
            parserContext.getReaderContext().error("The 'scatter-channel' attribute or 'scatterer' sub-element must be specified", (Object)element);
        }
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(ScatterGatherHandler.class);
        AbstractBeanDefinition scatterGatherDefinition = builder.getRawBeanDefinition();
        String id = this.resolveId(element, scatterGatherDefinition, parserContext);
        this.scatter(parserContext, scatterChannel, hasScatterChannel, scatterer, hasScatterer, builder, scatterGatherDefinition, id);
        this.gather(element, parserContext, builder, scatterGatherDefinition, id);
        IntegrationNamespaceUtils.setReferenceIfAttributeDefined(builder, element, "gather-channel");
        IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element, "gather-timeout");
        IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element, "requires-reply");
        return builder;
    }

    private void scatter(ParserContext parserContext, String scatterChannel, boolean hasScatterChannel, Element scatterer, boolean hasScatterer, BeanDefinitionBuilder builder, AbstractBeanDefinition scatterGatherDefinition, String id) {
        if (hasScatterChannel) {
            builder.addConstructorArgReference(scatterChannel);
        } else {
            Object scattererDefinition = null;
            scattererDefinition = !hasScatterer ? new RootBeanDefinition(RecipientListRouter.class) : SCATTERER_PARSER.parse(scatterer, new ParserContext(parserContext.getReaderContext(), parserContext.getDelegate(), (BeanDefinition)scatterGatherDefinition));
            String scattererId = id + ".scatterer";
            if (hasScatterer && scatterer.hasAttribute("id")) {
                scattererId = scatterer.getAttribute("id");
            }
            parserContext.getRegistry().registerBeanDefinition(scattererId, (BeanDefinition)scattererDefinition);
            builder.addConstructorArgValue((Object)new RuntimeBeanReference(scattererId));
        }
    }

    private void gather(Element element, ParserContext parserContext, BeanDefinitionBuilder builder, AbstractBeanDefinition scatterGatherDefinition, String id) {
        Element gatherer = DomUtils.getChildElementByTagName((Element)element, (String)"gatherer");
        BeanDefinition gathererDefinition = null;
        if (gatherer == null) {
            try {
                gatherer = DOCUMENT_BUILDER_FACTORY.newDocumentBuilder().newDocument().createElement("aggregator");
            }
            catch (ParserConfigurationException e) {
                parserContext.getReaderContext().error(e.getMessage(), (Object)element);
            }
        }
        gathererDefinition = GATHERER_PARSER.parse(gatherer, new ParserContext(parserContext.getReaderContext(), parserContext.getDelegate(), (BeanDefinition)scatterGatherDefinition));
        String gathererId = id + ".gatherer";
        if (gatherer != null && gatherer.hasAttribute("id")) {
            gathererId = gatherer.getAttribute("id");
        }
        parserContext.getRegistry().registerBeanDefinition(gathererId, gathererDefinition);
        builder.addConstructorArgValue((Object)new RuntimeBeanReference(gathererId));
    }
}

