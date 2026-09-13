/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.rabbitmq.client.ConnectionFactory
 *  com.rabbitmq.client.MetricsCollector
 *  com.rabbitmq.client.impl.MicrometerMetricsCollector
 *  io.micrometer.core.instrument.MeterRegistry
 *  io.micrometer.core.instrument.Tag
 *  io.micrometer.core.instrument.binder.MeterBinder
 *  org.springframework.util.Assert
 */
package org.springframework.boot.actuate.metrics.amqp;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MetricsCollector;
import com.rabbitmq.client.impl.MicrometerMetricsCollector;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.binder.MeterBinder;
import java.util.Collections;
import org.springframework.util.Assert;

public class RabbitMetrics
implements MeterBinder {
    private final Iterable<Tag> tags;
    private final ConnectionFactory connectionFactory;

    public RabbitMetrics(ConnectionFactory connectionFactory, Iterable<Tag> tags) {
        Assert.notNull((Object)connectionFactory, (String)"ConnectionFactory must not be null");
        this.connectionFactory = connectionFactory;
        this.tags = tags != null ? tags : Collections.emptyList();
    }

    public void bindTo(MeterRegistry registry) {
        this.connectionFactory.setMetricsCollector((MetricsCollector)new MicrometerMetricsCollector(registry, "rabbitmq", this.tags));
    }
}

