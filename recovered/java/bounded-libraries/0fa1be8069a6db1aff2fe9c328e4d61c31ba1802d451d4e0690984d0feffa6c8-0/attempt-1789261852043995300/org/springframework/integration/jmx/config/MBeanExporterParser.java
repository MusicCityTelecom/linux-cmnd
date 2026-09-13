/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.BeanDefinitionStoreException
 *  org.springframework.beans.factory.config.RuntimeBeanReference
 *  org.springframework.beans.factory.support.AbstractBeanDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser
 *  org.springframework.beans.factory.xml.ParserContext
 *  org.springframework.integration.config.xml.IntegrationNamespaceUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.jmx.config;

import java.util.UUID;
import javax.management.MBeanServerFactory;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.config.xml.IntegrationNamespaceUtils;
import org.springframework.integration.monitor.IntegrationMBeanExporter;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

public class MBeanExporterParser
extends AbstractSingleBeanDefinitionParser {
    private static final String MBEAN_EXPORTER_NAME = "mbeanExporter";

    protected boolean shouldGenerateIdAsFallback() {
        return true;
    }

    protected String getBeanClassName(Element element) {
        return IntegrationMBeanExporter.class.getName();
    }

    protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
        Object mbeanServer = this.getMBeanServer(element);
        builder.getRawBeanDefinition().setSource(parserContext.extractSource((Object)element));
        IntegrationNamespaceUtils.setValueIfAttributeDefined((BeanDefinitionBuilder)builder, (Element)element, (String)"default-domain");
        IntegrationNamespaceUtils.setReferenceIfAttributeDefined((BeanDefinitionBuilder)builder, (Element)element, (String)"object-name-static-properties");
        IntegrationNamespaceUtils.setValueIfAttributeDefined((BeanDefinitionBuilder)builder, (Element)element, (String)"managed-components", (String)"componentNamePatterns");
        IntegrationNamespaceUtils.setReferenceIfAttributeDefined((BeanDefinitionBuilder)builder, (Element)element, (String)"object-naming-strategy", (String)"namingStrategy");
        builder.addPropertyValue("server", mbeanServer);
    }

    private Object getMBeanServer(Element element) {
        String mbeanServer = element.getAttribute("server");
        if (StringUtils.hasText((String)mbeanServer)) {
            return new RuntimeBeanReference(mbeanServer);
        }
        return MBeanServerFactory.createMBeanServer();
    }

    protected String resolveId(Element element, AbstractBeanDefinition definition, ParserContext parserContext) throws BeanDefinitionStoreException {
        String id = super.resolveId(element, definition, parserContext);
        if (MBEAN_EXPORTER_NAME.equals(id)) {
            parserContext.getReaderContext().error("Illegal bean id for <jmx:mbean-export/>: mbeanExporter (clashes with <context:mbean-export/> default).  Please choose another bean id.", (Object)definition);
        }
        if (id.matches(IntegrationMBeanExporter.class.getName() + "#[0-9]+")) {
            id = id + "#" + UUID.randomUUID();
        }
        return id;
    }
}

