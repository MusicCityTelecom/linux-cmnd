/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.retry.stats;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.springframework.retry.RetryStatistics;
import org.springframework.retry.stats.DefaultRetryStatisticsFactory;
import org.springframework.retry.stats.MutableRetryStatistics;
import org.springframework.retry.stats.RetryStatisticsFactory;
import org.springframework.retry.stats.StatisticsRepository;

public class DefaultStatisticsRepository
implements StatisticsRepository {
    private ConcurrentMap<String, MutableRetryStatistics> map = new ConcurrentHashMap<String, MutableRetryStatistics>();
    private RetryStatisticsFactory factory = new DefaultRetryStatisticsFactory();

    public void setRetryStatisticsFactory(RetryStatisticsFactory factory) {
        this.factory = factory;
    }

    @Override
    public RetryStatistics findOne(String name) {
        return (RetryStatistics)this.map.get(name);
    }

    @Override
    public Iterable<RetryStatistics> findAll() {
        return new ArrayList<RetryStatistics>(this.map.values());
    }

    @Override
    public void addStarted(String name) {
        this.getStatistics(name).incrementStartedCount();
    }

    @Override
    public void addError(String name) {
        this.getStatistics(name).incrementErrorCount();
    }

    @Override
    public void addRecovery(String name) {
        this.getStatistics(name).incrementRecoveryCount();
    }

    @Override
    public void addComplete(String name) {
        this.getStatistics(name).incrementCompleteCount();
    }

    @Override
    public void addAbort(String name) {
        this.getStatistics(name).incrementAbortCount();
    }

    private MutableRetryStatistics getStatistics(String name) {
        if (!this.map.containsKey(name)) {
            this.map.putIfAbsent(name, this.factory.create(name));
        }
        MutableRetryStatistics stats = (MutableRetryStatistics)this.map.get(name);
        return stats;
    }
}

