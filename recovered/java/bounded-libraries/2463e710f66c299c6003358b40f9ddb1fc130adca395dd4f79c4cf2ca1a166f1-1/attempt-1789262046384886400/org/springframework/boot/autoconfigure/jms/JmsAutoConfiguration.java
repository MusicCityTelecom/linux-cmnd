/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.ConnectionFactory
 *  javax.jms.Message
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.boot.context.properties.PropertyMapper
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.context.annotation.Import
 *  org.springframework.jms.core.JmsMessageOperations
 *  org.springframework.jms.core.JmsMessagingTemplate
 *  org.springframework.jms.core.JmsOperations
 *  org.springframework.jms.core.JmsTemplate
 *  org.springframework.jms.support.converter.MessageConverter
 *  org.springframework.jms.support.destination.DestinationResolver
 */
package org.springframework.boot.autoconfigure.jms;

import java.time.Duration;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.jms.JmsAnnotationDrivenConfiguration;
import org.springframework.boot.autoconfigure.jms.JmsProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jms.core.JmsMessageOperations;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.destination.DestinationResolver;

@AutoConfiguration
@ConditionalOnClass(value={Message.class, JmsTemplate.class})
@ConditionalOnBean(value={ConnectionFactory.class})
@EnableConfigurationProperties(value={JmsProperties.class})
@Import(value={JmsAnnotationDrivenConfiguration.class})
public class JmsAutoConfiguration {

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnClass(value={JmsMessagingTemplate.class})
    @Import(value={JmsTemplateConfiguration.class})
    protected static class MessagingTemplateConfiguration {
        protected MessagingTemplateConfiguration() {
        }

        @Bean
        @ConditionalOnMissingBean(value={JmsMessageOperations.class})
        @ConditionalOnSingleCandidate(value=JmsTemplate.class)
        public JmsMessagingTemplate jmsMessagingTemplate(JmsProperties properties, JmsTemplate jmsTemplate) {
            JmsMessagingTemplate messagingTemplate = new JmsMessagingTemplate(jmsTemplate);
            this.mapTemplateProperties(properties.getTemplate(), messagingTemplate);
            return messagingTemplate;
        }

        private void mapTemplateProperties(JmsProperties.Template properties, JmsMessagingTemplate messagingTemplate) {
            PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
            map.from(properties::getDefaultDestination).to(arg_0 -> ((JmsMessagingTemplate)messagingTemplate).setDefaultDestinationName(arg_0));
        }
    }

    @Configuration(proxyBeanMethods=false)
    protected static class JmsTemplateConfiguration {
        private final JmsProperties properties;
        private final ObjectProvider<DestinationResolver> destinationResolver;
        private final ObjectProvider<MessageConverter> messageConverter;

        public JmsTemplateConfiguration(JmsProperties properties, ObjectProvider<DestinationResolver> destinationResolver, ObjectProvider<MessageConverter> messageConverter) {
            this.properties = properties;
            this.destinationResolver = destinationResolver;
            this.messageConverter = messageConverter;
        }

        @Bean
        @ConditionalOnMissingBean(value={JmsOperations.class})
        @ConditionalOnSingleCandidate(value=ConnectionFactory.class)
        public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory) {
            PropertyMapper map = PropertyMapper.get();
            JmsTemplate template = new JmsTemplate(connectionFactory);
            template.setPubSubDomain(this.properties.isPubSubDomain());
            map.from(() -> this.destinationResolver.getIfUnique()).whenNonNull().to(arg_0 -> ((JmsTemplate)template).setDestinationResolver(arg_0));
            map.from(() -> this.messageConverter.getIfUnique()).whenNonNull().to(arg_0 -> ((JmsTemplate)template).setMessageConverter(arg_0));
            this.mapTemplateProperties(this.properties.getTemplate(), template);
            return template;
        }

        private void mapTemplateProperties(JmsProperties.Template properties, JmsTemplate template) {
            PropertyMapper map = PropertyMapper.get();
            map.from(properties::getDefaultDestination).whenNonNull().to(arg_0 -> ((JmsTemplate)template).setDefaultDestinationName(arg_0));
            map.from(properties::getDeliveryDelay).whenNonNull().as(Duration::toMillis).to(arg_0 -> ((JmsTemplate)template).setDeliveryDelay(arg_0));
            map.from(properties::determineQosEnabled).to(arg_0 -> ((JmsTemplate)template).setExplicitQosEnabled(arg_0));
            map.from(properties::getDeliveryMode).whenNonNull().as(JmsProperties.DeliveryMode::getValue).to(arg_0 -> ((JmsTemplate)template).setDeliveryMode(arg_0));
            map.from(properties::getPriority).whenNonNull().to(arg_0 -> ((JmsTemplate)template).setPriority(arg_0));
            map.from(properties::getTimeToLive).whenNonNull().as(Duration::toMillis).to(arg_0 -> ((JmsTemplate)template).setTimeToLive(arg_0));
            map.from(properties::getReceiveTimeout).whenNonNull().as(Duration::toMillis).to(arg_0 -> ((JmsTemplate)template).setReceiveTimeout(arg_0));
        }
    }
}

