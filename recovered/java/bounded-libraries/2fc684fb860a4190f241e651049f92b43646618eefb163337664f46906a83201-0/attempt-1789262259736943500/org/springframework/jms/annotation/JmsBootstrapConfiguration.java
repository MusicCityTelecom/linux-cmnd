/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.context.annotation.Role
 */
package org.springframework.jms.annotation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.jms.annotation.JmsListenerAnnotationBeanPostProcessor;
import org.springframework.jms.config.JmsListenerEndpointRegistry;

@Configuration(proxyBeanMethods=false)
@Role(value=2)
public class JmsBootstrapConfiguration {
    @Bean(name={"org.springframework.jms.config.internalJmsListenerAnnotationProcessor"})
    @Role(value=2)
    public JmsListenerAnnotationBeanPostProcessor jmsListenerAnnotationProcessor() {
        return new JmsListenerAnnotationBeanPostProcessor();
    }

    @Bean(name={"org.springframework.jms.config.internalJmsListenerEndpointRegistry"})
    public JmsListenerEndpointRegistry defaultJmsListenerEndpointRegistry() {
        return new JmsListenerEndpointRegistry();
    }
}

