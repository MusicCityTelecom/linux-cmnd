/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.logout;

import java.util.ArrayList;
import java.util.Collection;
import org.apereo.cas.logout.LogoutPostProcessor;
import org.apereo.cas.logout.LogoutRedirectionStrategy;
import org.apereo.cas.logout.slo.SingleLogoutServiceMessageHandler;

public interface LogoutExecutionPlan {
    public static final String BEAN_NAME = "logoutExecutionPlan";

    default public void registerLogoutPostProcessor(LogoutPostProcessor handler) {
    }

    default public void registerSingleLogoutServiceMessageHandler(SingleLogoutServiceMessageHandler handler) {
    }

    default public void registerLogoutRedirectionStrategy(LogoutRedirectionStrategy strategy) {
    }

    default public Collection<LogoutPostProcessor> getLogoutPostProcessors() {
        return new ArrayList<LogoutPostProcessor>(0);
    }

    default public Collection<LogoutRedirectionStrategy> getLogoutRedirectionStrategies() {
        return new ArrayList<LogoutRedirectionStrategy>(0);
    }

    default public Collection<SingleLogoutServiceMessageHandler> getSingleLogoutServiceMessageHandlers() {
        return new ArrayList<SingleLogoutServiceMessageHandler>(0);
    }
}

