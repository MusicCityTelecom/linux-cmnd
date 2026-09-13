/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.throttle.AuthenticationThrottlingExecutionPlan
 *  org.apereo.cas.web.support.ThrottledSubmissionHandlerInterceptor
 *  org.springframework.scheduling.annotation.Scheduled
 */
package org.apereo.cas.web.support;

import java.util.List;
import lombok.Generated;
import org.apereo.cas.throttle.AuthenticationThrottlingExecutionPlan;
import org.apereo.cas.web.support.ThrottledSubmissionHandlerInterceptor;
import org.springframework.scheduling.annotation.Scheduled;

public class InMemoryThrottledSubmissionCleaner
implements Runnable {
    private final AuthenticationThrottlingExecutionPlan authenticationThrottlingExecutionPlan;

    @Override
    @Scheduled(initialDelayString="${cas.authn.throttle.schedule.start-delay:PT10S}", fixedDelayString="${cas.authn.throttle.schedule.repeat-interval:PT15S}")
    public void run() {
        List handlers = this.authenticationThrottlingExecutionPlan.getAuthenticationThrottleInterceptors();
        handlers.stream().filter(handler -> handler instanceof ThrottledSubmissionHandlerInterceptor).map(handler -> (ThrottledSubmissionHandlerInterceptor)handler).forEach(ThrottledSubmissionHandlerInterceptor::release);
    }

    @Generated
    public InMemoryThrottledSubmissionCleaner(AuthenticationThrottlingExecutionPlan authenticationThrottlingExecutionPlan) {
        this.authenticationThrottlingExecutionPlan = authenticationThrottlingExecutionPlan;
    }
}

