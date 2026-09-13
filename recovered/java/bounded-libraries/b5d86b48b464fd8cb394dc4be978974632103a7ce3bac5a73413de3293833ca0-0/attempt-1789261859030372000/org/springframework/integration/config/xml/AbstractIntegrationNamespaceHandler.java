/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.config.BeanDefinitionHolder
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.support.BeanDefinitionReaderUtils
 *  org.springframework.beans.factory.support.BeanDefinitionRegistry
 *  org.springframework.beans.factory.support.ManagedSet
 *  org.springframework.beans.factory.xml.NamespaceHandlerSupport
 *  org.springframework.beans.factory.xml.ParserContext
 */
package org.springframework.integration.config.xml;

import java.util.concurrent.atomic.AtomicBoolean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.ManagedSet;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.config.ChannelInitializer;
import org.springframework.integration.config.IntegrationRegistrar;
import org.springframework.integration.context.IntegrationProperties;
import org.w3c.dom.Element;

public abstract class AbstractIntegrationNamespaceHandler
extends NamespaceHandlerSupport {
    private final AtomicBoolean initialized = new AtomicBoolean();

    public final BeanDefinition parse(Element element, ParserContext parserContext) {
        if (!this.initialized.getAndSet(true)) {
            BeanDefinitionRegistry registry = parserContext.getRegistry();
            new IntegrationRegistrar().registerBeanDefinitions(null, registry);
            AbstractIntegrationNamespaceHandler.registerImplicitChannelCreator(registry);
        }
        return super.parse(element, parserContext);
    }

    private static void registerImplicitChannelCreator(BeanDefinitionRegistry registry) {
        if (!registry.containsBeanDefinition("channelInitializer")) {
            String channelsAutoCreateExpression = IntegrationProperties.getExpressionFor("spring.integration.channels.autoCreate");
            BeanDefinitionBuilder channelDef = BeanDefinitionBuilder.genericBeanDefinition(ChannelInitializer.class).addPropertyValue("autoCreate", (Object)channelsAutoCreateExpression);
            BeanDefinitionHolder channelCreatorHolder = new BeanDefinitionHolder((BeanDefinition)channelDef.getBeanDefinition(), "channelInitializer");
            BeanDefinitionReaderUtils.registerBeanDefinition((BeanDefinitionHolder)channelCreatorHolder, (BeanDefinitionRegistry)registry);
        }
        if (!registry.containsBeanDefinition("$autoCreateChannelCandidates")) {
            BeanDefinitionBuilder channelRegistryBuilder = BeanDefinitionBuilder.genericBeanDefinition(ChannelInitializer.AutoCreateCandidatesCollector.class);
            channelRegistryBuilder.addConstructorArgValue((Object)new ManagedSet());
            channelRegistryBuilder.setRole(2);
            BeanDefinitionHolder channelRegistryHolder = new BeanDefinitionHolder((BeanDefinition)channelRegistryBuilder.getBeanDefinition(), "$autoCreateChannelCandidates");
            BeanDefinitionReaderUtils.registerBeanDefinition((BeanDefinitionHolder)channelRegistryHolder, (BeanDefinitionRegistry)registry);
        }
    }
}

