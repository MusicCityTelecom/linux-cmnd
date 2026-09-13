/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.logout.LogoutExecutionPlan
 *  org.apereo.cas.logout.LogoutPostProcessor
 *  org.apereo.cas.logout.LogoutRedirectionStrategy
 *  org.apereo.cas.logout.slo.SingleLogoutServiceMessageHandler
 *  org.apereo.cas.util.spring.beans.BeanSupplier
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.core.annotation.AnnotationAwareOrderComparator
 */
package org.apereo.cas.logout;

import java.util.ArrayList;
import java.util.List;
import lombok.Generated;
import org.apereo.cas.logout.LogoutExecutionPlan;
import org.apereo.cas.logout.LogoutPostProcessor;
import org.apereo.cas.logout.LogoutRedirectionStrategy;
import org.apereo.cas.logout.slo.SingleLogoutServiceMessageHandler;
import org.apereo.cas.util.spring.beans.BeanSupplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

public class DefaultLogoutExecutionPlan
implements LogoutExecutionPlan {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultLogoutExecutionPlan.class);
    private final List<LogoutRedirectionStrategy> logoutRedirectionStrategies = new ArrayList<LogoutRedirectionStrategy>(0);
    private final List<LogoutPostProcessor> logoutPostProcessors = new ArrayList<LogoutPostProcessor>(0);
    private final List<SingleLogoutServiceMessageHandler> singleLogoutServiceMessageHandlers = new ArrayList<SingleLogoutServiceMessageHandler>(0);

    public void registerLogoutPostProcessor(LogoutPostProcessor handler) {
        if (BeanSupplier.isNotProxy((Object)handler)) {
            LOGGER.debug("Registering logout handler [{}]", (Object)handler.getName());
            this.logoutPostProcessors.add(handler);
            AnnotationAwareOrderComparator.sort(this.logoutPostProcessors);
        }
    }

    public void registerSingleLogoutServiceMessageHandler(SingleLogoutServiceMessageHandler handler) {
        if (BeanSupplier.isNotProxy((Object)handler)) {
            LOGGER.trace("Registering single logout service message handler [{}]", (Object)handler.getName());
            this.singleLogoutServiceMessageHandlers.add(handler);
            AnnotationAwareOrderComparator.sort(this.singleLogoutServiceMessageHandlers);
        }
    }

    public void registerLogoutRedirectionStrategy(LogoutRedirectionStrategy strategy) {
        if (BeanSupplier.isNotProxy((Object)strategy)) {
            LOGGER.trace("Registering logout redirection strategy [{}]", (Object)strategy.getName());
            this.logoutRedirectionStrategies.add(strategy);
            AnnotationAwareOrderComparator.sort(this.logoutRedirectionStrategies);
        }
    }

    @Generated
    public List<LogoutRedirectionStrategy> getLogoutRedirectionStrategies() {
        return this.logoutRedirectionStrategies;
    }

    @Generated
    public List<LogoutPostProcessor> getLogoutPostProcessors() {
        return this.logoutPostProcessors;
    }

    @Generated
    public List<SingleLogoutServiceMessageHandler> getSingleLogoutServiceMessageHandlers() {
        return this.singleLogoutServiceMessageHandlers;
    }
}

