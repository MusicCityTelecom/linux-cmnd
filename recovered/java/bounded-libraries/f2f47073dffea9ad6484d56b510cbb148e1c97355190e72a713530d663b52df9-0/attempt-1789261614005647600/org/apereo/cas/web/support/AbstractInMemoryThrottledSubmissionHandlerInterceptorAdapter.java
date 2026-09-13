/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  lombok.Generated
 *  org.apereo.cas.configuration.support.Beans
 *  org.apereo.cas.web.support.InMemoryThrottledSubmissionHandlerInterceptor
 *  org.apereo.cas.web.support.ThrottledSubmission
 *  org.apereo.cas.web.support.ThrottledSubmissionReceiver
 *  org.apereo.cas.web.support.ThrottledSubmissionsStore
 *  org.apereo.inspektr.common.web.ClientInfoHolder
 *  org.jooq.lambda.Unchecked
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.core.annotation.AnnotationAwareOrderComparator
 */
package org.apereo.cas.web.support;

import java.time.Clock;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import lombok.Generated;
import org.apereo.cas.configuration.support.Beans;
import org.apereo.cas.web.support.AbstractThrottledSubmissionHandlerInterceptorAdapter;
import org.apereo.cas.web.support.InMemoryThrottledSubmissionHandlerInterceptor;
import org.apereo.cas.web.support.ThrottledSubmission;
import org.apereo.cas.web.support.ThrottledSubmissionHandlerConfigurationContext;
import org.apereo.cas.web.support.ThrottledSubmissionReceiver;
import org.apereo.cas.web.support.ThrottledSubmissionsStore;
import org.apereo.inspektr.common.web.ClientInfoHolder;
import org.jooq.lambda.Unchecked;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

public abstract class AbstractInMemoryThrottledSubmissionHandlerInterceptorAdapter
extends AbstractThrottledSubmissionHandlerInterceptorAdapter
implements InMemoryThrottledSubmissionHandlerInterceptor {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractInMemoryThrottledSubmissionHandlerInterceptorAdapter.class);

    protected AbstractInMemoryThrottledSubmissionHandlerInterceptorAdapter(ThrottledSubmissionHandlerConfigurationContext configurationContext) {
        super(configurationContext);
    }

    public void recordSubmissionFailure(HttpServletRequest request) {
        String key = this.constructKey(request);
        LOGGER.debug("Recording submission failure [{}]", (Object)key);
        Duration duration = Beans.newDuration((String)this.getConfigurationContext().getCasProperties().getAuthn().getThrottle().getFailure().getThrottleWindowSeconds());
        ZonedDateTime expiration = ZonedDateTime.now(Clock.systemUTC()).plusSeconds(duration.getSeconds());
        ThrottledSubmission submission = ThrottledSubmission.builder().key(key).username(this.getUsernameParameterFromRequest(request)).clientIpAddress(ClientInfoHolder.getClientInfo().getClientIpAddress()).expiration(expiration).build();
        this.getConfigurationContext().getThrottledSubmissionStore().put(submission);
        ArrayList receivers = new ArrayList(this.getConfigurationContext().getApplicationContext().getBeansOfType(ThrottledSubmissionReceiver.class).values());
        AnnotationAwareOrderComparator.sort(receivers);
        receivers.forEach(Unchecked.consumer(receiver -> receiver.receive(submission)));
    }

    public boolean exceedsThreshold(HttpServletRequest request) {
        String key = this.constructKey(request);
        LOGGER.trace("Throttling threshold key is [{}] with submission threshold [{}]", (Object)key, (Object)this.getThresholdRate());
        ThrottledSubmissionsStore<ThrottledSubmission> store = this.getConfigurationContext().getThrottledSubmissionStore();
        if (store.contains(key)) {
            ThrottledSubmission submission = store.get(key);
            ZonedDateTime now = ZonedDateTime.now(Clock.systemUTC());
            if (now.isBefore(submission.getExpiration())) {
                LOGGER.warn("Throttled submission [{}] remains throttled; submission expires at [{}]", (Object)key, (Object)submission.getExpiration());
                return true;
            }
        }
        return store.exceedsThreshold(key, this.getThresholdRate());
    }

    public Collection getRecords() {
        return this.getConfigurationContext().getThrottledSubmissionStore().entries().map(entry -> entry.getKey() + "<->" + entry.getValue()).collect(Collectors.toList());
    }

    public void release() {
        try {
            LOGGER.info("Beginning audit cleanup...");
            this.getConfigurationContext().getThrottledSubmissionStore().release(this.getThresholdRate());
        }
        finally {
            LOGGER.debug("Done releasing throttled entries.");
        }
    }
}

