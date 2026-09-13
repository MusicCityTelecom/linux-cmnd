/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.xml.NamespaceHandlerSupport
 */
package org.springframework.jms.config;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;
import org.springframework.jms.config.AnnotationDrivenJmsBeanDefinitionParser;
import org.springframework.jms.config.JcaListenerContainerParser;
import org.springframework.jms.config.JmsListenerContainerParser;

public class JmsNamespaceHandler
extends NamespaceHandlerSupport {
    public void init() {
        this.registerBeanDefinitionParser("listener-container", new JmsListenerContainerParser());
        this.registerBeanDefinitionParser("jca-listener-container", new JcaListenerContainerParser());
        this.registerBeanDefinitionParser("annotation-driven", new AnnotationDrivenJmsBeanDefinitionParser());
    }
}

