/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.configuration.CasConfigurationProperties
 *  org.apereo.cas.web.support.ThrottledSubmission
 *  org.apereo.cas.web.support.ThrottledSubmissionsStore
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.throttle;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;
import lombok.Generated;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.web.support.ThrottledSubmission;
import org.apereo.cas.web.support.ThrottledSubmissionsStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseMappableThrottledSubmissionsStore<T extends ThrottledSubmission>
implements ThrottledSubmissionsStore<T> {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseMappableThrottledSubmissionsStore.class);
    private static final double SUBMISSION_RATE_DIVIDEND = 1000.0;
    protected final Map<String, T> backingMap;
    protected final CasConfigurationProperties casProperties;

    public void put(T submission) {
        this.backingMap.put(submission.getKey(), submission);
    }

    public boolean contains(String key) {
        return this.backingMap.containsKey(key);
    }

    public T get(String key) {
        return (T)((ThrottledSubmission)this.backingMap.get(key));
    }

    public Stream<T> entries() {
        return this.backingMap.values().stream();
    }

    public void removeIf(Predicate<T> condition) {
        this.backingMap.entrySet().removeIf((? super E entry) -> condition.test((ThrottledSubmission)entry.getValue()));
    }

    public void remove(String key) {
        this.backingMap.remove(key);
    }

    public boolean exceedsThreshold(String key, double thresholdRate) {
        T submissionEntry = this.get(key);
        LOGGER.debug("Last throttling date time for key [{}] is [{}]", (Object)key, submissionEntry);
        return submissionEntry != null && BaseMappableThrottledSubmissionsStore.submissionRate(ZonedDateTime.now(ZoneOffset.UTC), submissionEntry.getValue()) > thresholdRate;
    }

    public void release(double thresholdRate) {
        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
        this.removeIf(entry -> BaseMappableThrottledSubmissionsStore.submissionRate(now, entry.getValue()) < thresholdRate);
    }

    private static double submissionRate(ZonedDateTime a, ZonedDateTime b) {
        double rate = 1000.0 / (double)(a.toInstant().toEpochMilli() - b.toInstant().toEpochMilli());
        LOGGER.debug("Submitting rate for [{}] and [{}] is [{}]", new Object[]{a, b, rate});
        return rate;
    }

    @Generated
    public BaseMappableThrottledSubmissionsStore(Map<String, T> backingMap, CasConfigurationProperties casProperties) {
        this.backingMap = backingMap;
        this.casProperties = casProperties;
    }
}

