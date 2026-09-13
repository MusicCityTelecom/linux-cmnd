/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.DisposableBean
 *  org.springframework.jmx.export.annotation.ManagedAttribute
 *  org.springframework.lang.Nullable
 */
package org.springframework.integration.support.management;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.integration.support.context.NamedComponent;
import org.springframework.integration.support.management.metrics.MetricsCaptor;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.lang.Nullable;

public interface IntegrationManagement
extends NamedComponent,
DisposableBean {
    public static final String METER_PREFIX = "spring.integration.";
    public static final String SEND_TIMER_NAME = "spring.integration.send";
    public static final String RECEIVE_COUNTER_NAME = "spring.integration.receive";

    @ManagedAttribute(description="Use to disable debug logging during normal message flow")
    default public void setLoggingEnabled(boolean enabled) {
    }

    @ManagedAttribute
    default public boolean isLoggingEnabled() {
        return true;
    }

    default public void setManagedName(String managedName) {
    }

    default public String getManagedName() {
        return null;
    }

    default public void setManagedType(String managedType) {
    }

    default public String getManagedType() {
        return null;
    }

    @Nullable
    default public ManagementOverrides getOverrides() {
        return null;
    }

    default public void registerMetricsCaptor(MetricsCaptor captor) {
    }

    default public void destroy() {
    }

    default public <T> T getThisAs() {
        return (T)this;
    }

    public static class ManagementOverrides {
        public boolean loggingConfigured;
    }
}

