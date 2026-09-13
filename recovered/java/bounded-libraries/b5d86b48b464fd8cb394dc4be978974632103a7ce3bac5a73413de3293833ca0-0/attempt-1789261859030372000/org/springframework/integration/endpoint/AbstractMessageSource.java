/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.BeanNameAware
 *  org.springframework.expression.EvaluationContext
 *  org.springframework.expression.Expression
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 *  org.springframework.util.CollectionUtils
 */
package org.springframework.integration.endpoint;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.expression.ExpressionEvalMap;
import org.springframework.integration.support.AbstractIntegrationMessageBuilder;
import org.springframework.integration.support.context.NamedComponent;
import org.springframework.integration.support.management.IntegrationInboundManagement;
import org.springframework.integration.support.management.IntegrationManagedResource;
import org.springframework.integration.support.management.IntegrationManagement;
import org.springframework.integration.support.management.metrics.CounterFacade;
import org.springframework.integration.support.management.metrics.MeterFacade;
import org.springframework.integration.support.management.metrics.MetricsCaptor;
import org.springframework.integration.util.AbstractExpressionEvaluator;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.util.CollectionUtils;

@IntegrationManagedResource
public abstract class AbstractMessageSource<T>
extends AbstractExpressionEvaluator
implements MessageSource<T>,
IntegrationInboundManagement,
NamedComponent,
BeanNameAware {
    private final AtomicLong messageCount = new AtomicLong();
    private final IntegrationManagement.ManagementOverrides managementOverrides = new IntegrationManagement.ManagementOverrides();
    private final Set<MeterFacade> meters = ConcurrentHashMap.newKeySet();
    private Map<String, Expression> headerExpressions;
    private String beanName;
    private String managedType;
    private String managedName;
    private boolean loggingEnabled = true;
    private MetricsCaptor metricsCaptor;
    private CounterFacade receiveCounter;

    public void setHeaderExpressions(@Nullable Map<String, Expression> headerExpressions) {
        if (!CollectionUtils.isEmpty(headerExpressions)) {
            this.headerExpressions = new HashMap<String, Expression>(headerExpressions);
        }
    }

    @Override
    public void registerMetricsCaptor(MetricsCaptor metricsCaptorToSet) {
        this.metricsCaptor = metricsCaptorToSet;
    }

    public void setBeanName(String name) {
        this.beanName = name;
    }

    @Override
    public String getBeanName() {
        return this.beanName;
    }

    @Override
    public void setManagedType(String managedType) {
        this.managedType = managedType;
    }

    @Override
    public String getManagedType() {
        return this.managedType;
    }

    @Override
    public void setManagedName(String managedName) {
        this.managedName = managedName;
    }

    @Override
    public String getManagedName() {
        return this.managedName;
    }

    @Override
    public String getComponentName() {
        return this.beanName;
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
    public IntegrationManagement.ManagementOverrides getOverrides() {
        return this.managementOverrides;
    }

    @Override
    public final Message<T> receive() {
        try {
            return this.buildMessage(this.doReceive());
        }
        catch (RuntimeException ex) {
            if (this.metricsCaptor != null) {
                this.createCounter(false, ex.getClass().getSimpleName()).increment();
            }
            throw ex;
        }
    }

    protected Message<T> buildMessage(Object result) {
        Object message;
        if (result == null) {
            return null;
        }
        Map<String, Object> headers = this.evaluateHeaders();
        if (result instanceof AbstractIntegrationMessageBuilder) {
            if (!CollectionUtils.isEmpty(headers)) {
                ((AbstractIntegrationMessageBuilder)result).copyHeaders(headers);
            }
            message = ((AbstractIntegrationMessageBuilder)result).build();
        } else if (result instanceof Message) {
            message = (Message)result;
            if (!CollectionUtils.isEmpty(headers)) {
                message = this.getMessageBuilderFactory().fromMessage(message).copyHeaders(headers).build();
            }
        } else {
            message = this.getMessageBuilderFactory().withPayload(result).copyHeaders(headers).build();
        }
        if (this.metricsCaptor != null) {
            this.incrementReceiveCounter();
        }
        this.messageCount.incrementAndGet();
        return message;
    }

    private void incrementReceiveCounter() {
        if (this.receiveCounter == null) {
            this.receiveCounter = this.createCounter(true, "none");
        }
        this.receiveCounter.increment();
    }

    private CounterFacade createCounter(boolean success, String exception) {
        CounterFacade counter = this.metricsCaptor.counterBuilder("spring.integration.receive").tag("name", this.getComponentName() == null ? "unknown" : this.getComponentName()).tag("type", "source").tag("result", success ? "success" : "failure").tag("exception", exception).description("Messages received").build();
        this.meters.add(counter);
        return counter;
    }

    @Nullable
    private Map<String, Object> evaluateHeaders() {
        return CollectionUtils.isEmpty(this.headerExpressions) ? null : ExpressionEvalMap.from(this.headerExpressions).usingEvaluationContext((EvaluationContext)this.getEvaluationContext()).build();
    }

    @Nullable
    protected abstract Object doReceive();

    @Override
    public void destroy() {
        this.meters.forEach(MeterFacade::remove);
        this.meters.clear();
    }
}

