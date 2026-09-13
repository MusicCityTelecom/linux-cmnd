/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.handler;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.integration.IntegrationPattern;
import org.springframework.integration.IntegrationPatternType;
import org.springframework.integration.context.IntegrationObjectSupport;
import org.springframework.integration.context.Orderable;
import org.springframework.integration.support.management.IntegrationManagedResource;
import org.springframework.integration.support.management.IntegrationManagement;
import org.springframework.integration.support.management.TrackableComponent;
import org.springframework.integration.support.management.metrics.MeterFacade;
import org.springframework.integration.support.management.metrics.MetricsCaptor;
import org.springframework.integration.support.management.metrics.TimerFacade;

@IntegrationManagedResource
public abstract class MessageHandlerSupport
extends IntegrationObjectSupport
implements TrackableComponent,
Orderable,
IntegrationManagement,
IntegrationPattern {
    private final IntegrationManagement.ManagementOverrides managementOverrides = new IntegrationManagement.ManagementOverrides();
    private final Set<TimerFacade> timers = ConcurrentHashMap.newKeySet();
    private boolean shouldTrack = false;
    private boolean loggingEnabled = true;
    private MetricsCaptor metricsCaptor;
    private int order = Integer.MAX_VALUE;
    private String managedName;
    private String managedType;
    private TimerFacade successTimer;

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
    public void registerMetricsCaptor(MetricsCaptor metricsCaptorToRegister) {
        this.metricsCaptor = metricsCaptorToRegister;
    }

    protected MetricsCaptor getMetricsCaptor() {
        return this.metricsCaptor;
    }

    @Override
    public void setOrder(int order) {
        this.order = order;
    }

    public int getOrder() {
        return this.order;
    }

    @Override
    public String getComponentType() {
        return "message-handler";
    }

    @Override
    public void setShouldTrack(boolean shouldTrack) {
        this.shouldTrack = shouldTrack;
    }

    protected boolean shouldTrack() {
        return this.shouldTrack;
    }

    @Override
    public IntegrationManagement.ManagementOverrides getOverrides() {
        return this.managementOverrides;
    }

    @Override
    public IntegrationPatternType getIntegrationPatternType() {
        return IntegrationPatternType.outbound_channel_adapter;
    }

    protected TimerFacade sendTimer() {
        if (this.successTimer == null) {
            this.successTimer = this.buildSendTimer(true, "none");
        }
        return this.successTimer;
    }

    protected TimerFacade buildSendTimer(boolean success, String exception) {
        TimerFacade timer = this.metricsCaptor.timerBuilder("spring.integration.send").tag("type", "handler").tag("name", this.getComponentName() == null ? "unknown" : this.getComponentName()).tag("result", success ? "success" : "failure").tag("exception", exception).description("Send processing time").build();
        this.timers.add(timer);
        return timer;
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
    public void setManagedType(String managedType) {
        this.managedType = managedType;
    }

    @Override
    public String getManagedType() {
        return this.managedType;
    }

    @Override
    public void destroy() {
        this.timers.forEach(MeterFacade::remove);
        this.timers.clear();
    }
}

