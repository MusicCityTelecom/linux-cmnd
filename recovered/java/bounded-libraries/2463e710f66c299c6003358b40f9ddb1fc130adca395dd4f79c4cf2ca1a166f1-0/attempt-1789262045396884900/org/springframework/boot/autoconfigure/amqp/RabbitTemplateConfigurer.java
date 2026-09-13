/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.amqp.rabbit.connection.ConnectionFactory
 *  org.springframework.amqp.rabbit.core.RabbitTemplate
 *  org.springframework.amqp.support.converter.MessageConverter
 *  org.springframework.boot.context.properties.PropertyMapper
 *  org.springframework.util.Assert
 */
package org.springframework.boot.autoconfigure.amqp;

import java.time.Duration;
import java.util.List;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.autoconfigure.amqp.RabbitRetryTemplateCustomizer;
import org.springframework.boot.autoconfigure.amqp.RetryTemplateFactory;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.util.Assert;

public class RabbitTemplateConfigurer {
    private MessageConverter messageConverter;
    private List<RabbitRetryTemplateCustomizer> retryTemplateCustomizers;
    private RabbitProperties rabbitProperties;

    @Deprecated
    public RabbitTemplateConfigurer() {
    }

    public RabbitTemplateConfigurer(RabbitProperties rabbitProperties) {
        Assert.notNull((Object)rabbitProperties, (String)"RabbitProperties must not be null");
        this.rabbitProperties = rabbitProperties;
    }

    public void setMessageConverter(MessageConverter messageConverter) {
        this.messageConverter = messageConverter;
    }

    public void setRetryTemplateCustomizers(List<RabbitRetryTemplateCustomizer> retryTemplateCustomizers) {
        this.retryTemplateCustomizers = retryTemplateCustomizers;
    }

    @Deprecated
    protected void setRabbitProperties(RabbitProperties rabbitProperties) {
        this.rabbitProperties = rabbitProperties;
    }

    protected final RabbitProperties getRabbitProperties() {
        return this.rabbitProperties;
    }

    public void configure(RabbitTemplate template, ConnectionFactory connectionFactory) {
        PropertyMapper map = PropertyMapper.get();
        template.setConnectionFactory(connectionFactory);
        if (this.messageConverter != null) {
            template.setMessageConverter(this.messageConverter);
        }
        template.setMandatory(this.determineMandatoryFlag());
        RabbitProperties.Template templateProperties = this.rabbitProperties.getTemplate();
        if (templateProperties.getRetry().isEnabled()) {
            template.setRetryTemplate(new RetryTemplateFactory(this.retryTemplateCustomizers).createRetryTemplate(templateProperties.getRetry(), RabbitRetryTemplateCustomizer.Target.SENDER));
        }
        map.from(templateProperties::getReceiveTimeout).whenNonNull().as(Duration::toMillis).to(arg_0 -> ((RabbitTemplate)template).setReceiveTimeout(arg_0));
        map.from(templateProperties::getReplyTimeout).whenNonNull().as(Duration::toMillis).to(arg_0 -> ((RabbitTemplate)template).setReplyTimeout(arg_0));
        map.from(templateProperties::getExchange).to(arg_0 -> ((RabbitTemplate)template).setExchange(arg_0));
        map.from(templateProperties::getRoutingKey).to(arg_0 -> ((RabbitTemplate)template).setRoutingKey(arg_0));
        map.from(templateProperties::getDefaultReceiveQueue).whenNonNull().to(arg_0 -> ((RabbitTemplate)template).setDefaultReceiveQueue(arg_0));
    }

    private boolean determineMandatoryFlag() {
        Boolean mandatory = this.rabbitProperties.getTemplate().getMandatory();
        return mandatory != null ? mandatory.booleanValue() : this.rabbitProperties.isPublisherReturns();
    }
}

