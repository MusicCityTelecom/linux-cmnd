/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 *  org.reactivestreams.Subscriber
 *  org.reactivestreams.Subscription
 *  org.springframework.beans.factory.BeanNameAware
 *  org.springframework.core.log.LogAccessor
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.PollableChannel
 */
package org.springframework.integration.channel;

import java.util.concurrent.TimeUnit;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.core.log.LogAccessor;
import org.springframework.integration.IntegrationPattern;
import org.springframework.integration.IntegrationPatternType;
import org.springframework.integration.support.management.IntegrationManagedResource;
import org.springframework.integration.support.management.IntegrationManagement;
import org.springframework.integration.support.management.metrics.CounterFacade;
import org.springframework.integration.support.management.metrics.MetricsCaptor;
import org.springframework.integration.support.management.metrics.TimerFacade;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.PollableChannel;

@IntegrationManagedResource
public class NullChannel
implements PollableChannel,
BeanNameAware,
IntegrationManagement,
IntegrationPattern {
    private static final LogAccessor LOG = new LogAccessor(NullChannel.class);
    private final IntegrationManagement.ManagementOverrides managementOverrides = new IntegrationManagement.ManagementOverrides();
    private boolean loggingEnabled = true;
    private String beanName;
    private MetricsCaptor metricsCaptor;
    private TimerFacade successTimer;
    private CounterFacade receiveCounter;

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    @Override
    public boolean isLoggingEnabled() {
        return this.loggingEnabled;
    }

    @Override
    public void setLoggingEnabled(boolean loggingEnabled) {
        this.loggingEnabled = loggingEnabled;
        this.managementOverrides.loggingConfigured = true;
    }

    @Override
    @Nullable
    public String getBeanName() {
        return this.beanName;
    }

    @Override
    @Nullable
    public String getComponentName() {
        return this.beanName;
    }

    @Override
    public String getComponentType() {
        return "null-channel";
    }

    @Override
    public IntegrationPatternType getIntegrationPatternType() {
        return IntegrationPatternType.null_channel;
    }

    @Override
    public void registerMetricsCaptor(MetricsCaptor registry) {
        this.metricsCaptor = registry;
    }

    @Override
    public IntegrationManagement.ManagementOverrides getOverrides() {
        return this.managementOverrides;
    }

    public boolean send(Message<?> message, long timeout) {
        return this.send(message);
    }

    public boolean send(Message<?> message) {
        Object payload;
        if (this.loggingEnabled) {
            LOG.debug(() -> "message sent to null channel: " + message);
        }
        if ((payload = message.getPayload()) instanceof Publisher) {
            ((Publisher)payload).subscribe((Subscriber)new Subscriber<Object>(){

                public void onSubscribe(Subscription subscription) {
                    subscription.request(Long.MAX_VALUE);
                }

                public void onNext(Object value) {
                }

                public void onError(Throwable ex) {
                    LOG.warn(ex, (CharSequence)"An error happened in a reactive stream processing");
                }

                public void onComplete() {
                }
            });
        }
        if (this.metricsCaptor != null) {
            this.sendTimer().record(0L, TimeUnit.MILLISECONDS);
        }
        return true;
    }

    private TimerFacade sendTimer() {
        if (this.successTimer == null) {
            this.successTimer = this.metricsCaptor.timerBuilder("spring.integration.send").tag("type", "channel").tag("name", this.getComponentName() == null ? "nullChannel" : this.getComponentName()).tag("result", "success").tag("exception", "none").description("Subflow process time").build();
        }
        return this.successTimer;
    }

    public Message<?> receive() {
        if (this.loggingEnabled) {
            LOG.debug((CharSequence)"receive called on null channel");
        }
        this.incrementReceiveCounter();
        return null;
    }

    public Message<?> receive(long timeout) {
        return this.receive();
    }

    private void incrementReceiveCounter() {
        if (this.metricsCaptor != null) {
            if (this.receiveCounter == null) {
                this.receiveCounter = this.buildReceiveCounter();
            }
            this.receiveCounter.increment();
        }
    }

    private CounterFacade buildReceiveCounter() {
        return this.metricsCaptor.counterBuilder("spring.integration.receive").tag("name", this.getComponentName() == null ? "unknown" : this.getComponentName()).tag("type", "channel").tag("result", "success").tag("exception", "none").description("Messages received").build();
    }

    public String toString() {
        return this.beanName != null ? this.beanName : super.toString();
    }

    @Override
    public void destroy() {
        if (this.successTimer != null) {
            this.successTimer.remove();
        }
        if (this.receiveCounter != null) {
            this.receiveCounter.remove();
        }
    }
}

