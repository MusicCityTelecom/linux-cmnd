/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.context.properties.PropertyMapper
 *  org.springframework.retry.RetryPolicy
 *  org.springframework.retry.backoff.BackOffPolicy
 *  org.springframework.retry.backoff.ExponentialBackOffPolicy
 *  org.springframework.retry.policy.SimpleRetryPolicy
 *  org.springframework.retry.support.RetryTemplate
 */
package org.springframework.boot.autoconfigure.amqp;

import java.time.Duration;
import java.util.List;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.autoconfigure.amqp.RabbitRetryTemplateCustomizer;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.backoff.BackOffPolicy;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

class RetryTemplateFactory {
    private final List<RabbitRetryTemplateCustomizer> customizers;

    RetryTemplateFactory(List<RabbitRetryTemplateCustomizer> customizers) {
        this.customizers = customizers;
    }

    RetryTemplate createRetryTemplate(RabbitProperties.Retry properties, RabbitRetryTemplateCustomizer.Target target) {
        PropertyMapper map = PropertyMapper.get();
        RetryTemplate template = new RetryTemplate();
        SimpleRetryPolicy policy = new SimpleRetryPolicy();
        map.from(properties::getMaxAttempts).to(arg_0 -> ((SimpleRetryPolicy)policy).setMaxAttempts(arg_0));
        template.setRetryPolicy((RetryPolicy)policy);
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        map.from(properties::getInitialInterval).whenNonNull().as(Duration::toMillis).to(arg_0 -> ((ExponentialBackOffPolicy)backOffPolicy).setInitialInterval(arg_0));
        map.from(properties::getMultiplier).to(arg_0 -> ((ExponentialBackOffPolicy)backOffPolicy).setMultiplier(arg_0));
        map.from(properties::getMaxInterval).whenNonNull().as(Duration::toMillis).to(arg_0 -> ((ExponentialBackOffPolicy)backOffPolicy).setMaxInterval(arg_0));
        template.setBackOffPolicy((BackOffPolicy)backOffPolicy);
        if (this.customizers != null) {
            for (RabbitRetryTemplateCustomizer customizer : this.customizers) {
                customizer.customize(target, template);
            }
        }
        return template;
    }
}

