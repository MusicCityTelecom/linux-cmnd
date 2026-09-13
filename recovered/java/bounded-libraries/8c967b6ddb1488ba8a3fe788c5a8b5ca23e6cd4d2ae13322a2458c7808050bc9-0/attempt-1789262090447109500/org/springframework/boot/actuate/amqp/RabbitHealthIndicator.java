/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.amqp.rabbit.core.RabbitTemplate
 *  org.springframework.util.Assert
 */
package org.springframework.boot.actuate.amqp;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.util.Assert;

public class RabbitHealthIndicator
extends AbstractHealthIndicator {
    private final RabbitTemplate rabbitTemplate;

    public RabbitHealthIndicator(RabbitTemplate rabbitTemplate) {
        super("Rabbit health check failed");
        Assert.notNull((Object)rabbitTemplate, (String)"RabbitTemplate must not be null");
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        builder.up().withDetail("version", this.getVersion());
    }

    private String getVersion() {
        return (String)this.rabbitTemplate.execute(channel -> channel.getConnection().getServerProperties().get("version").toString());
    }
}

