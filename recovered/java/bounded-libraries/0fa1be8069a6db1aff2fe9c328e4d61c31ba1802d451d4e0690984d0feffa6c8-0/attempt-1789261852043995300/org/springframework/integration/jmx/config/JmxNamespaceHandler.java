/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.xml.BeanDefinitionParser
 *  org.springframework.integration.config.xml.AbstractIntegrationNamespaceHandler
 */
package org.springframework.integration.jmx.config;

import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.integration.config.xml.AbstractIntegrationNamespaceHandler;
import org.springframework.integration.jmx.config.AttributePollingChannelAdapterParser;
import org.springframework.integration.jmx.config.MBeanExporterParser;
import org.springframework.integration.jmx.config.MBeanTreePollingChannelAdapterParser;
import org.springframework.integration.jmx.config.NotificationListeningChannelAdapterParser;
import org.springframework.integration.jmx.config.NotificationPublishingChannelAdapterParser;
import org.springframework.integration.jmx.config.OperationInvokingChannelAdapterParser;
import org.springframework.integration.jmx.config.OperationInvokingOutboundGatewayParser;

public class JmxNamespaceHandler
extends AbstractIntegrationNamespaceHandler {
    public void init() {
        this.registerBeanDefinitionParser("operation-invoking-channel-adapter", (BeanDefinitionParser)new OperationInvokingChannelAdapterParser());
        this.registerBeanDefinitionParser("operation-invoking-outbound-gateway", (BeanDefinitionParser)new OperationInvokingOutboundGatewayParser());
        this.registerBeanDefinitionParser("attribute-polling-channel-adapter", (BeanDefinitionParser)new AttributePollingChannelAdapterParser());
        this.registerBeanDefinitionParser("tree-polling-channel-adapter", (BeanDefinitionParser)new MBeanTreePollingChannelAdapterParser());
        this.registerBeanDefinitionParser("notification-listening-channel-adapter", (BeanDefinitionParser)new NotificationListeningChannelAdapterParser());
        this.registerBeanDefinitionParser("notification-publishing-channel-adapter", (BeanDefinitionParser)new NotificationPublishingChannelAdapterParser());
        this.registerBeanDefinitionParser("mbean-export", (BeanDefinitionParser)new MBeanExporterParser());
    }
}

