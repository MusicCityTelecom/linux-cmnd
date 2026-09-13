/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.bucket4j;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.DurationCapable;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-bucket4j-core")
@JsonFilter(value="Bucket4jBandwidthLimitProperties")
public class Bucket4jBandwidthLimitProperties
implements Serializable {
    private static final long serialVersionUID = -4208702997065904970L;
    private long initialTokens;
    private long capacity = 120L;
    private long refillCount = 10L;
    @DurationCapable
    private String duration = "PT60S";
    @DurationCapable
    private String refillDuration = "PT30S";
    private BandwidthRefillStrategies refillStrategy = BandwidthRefillStrategies.GREEDY;

    @Generated
    public long getInitialTokens() {
        return this.initialTokens;
    }

    @Generated
    public long getCapacity() {
        return this.capacity;
    }

    @Generated
    public long getRefillCount() {
        return this.refillCount;
    }

    @Generated
    public String getDuration() {
        return this.duration;
    }

    @Generated
    public String getRefillDuration() {
        return this.refillDuration;
    }

    @Generated
    public BandwidthRefillStrategies getRefillStrategy() {
        return this.refillStrategy;
    }

    @Generated
    public Bucket4jBandwidthLimitProperties setInitialTokens(long initialTokens) {
        this.initialTokens = initialTokens;
        return this;
    }

    @Generated
    public Bucket4jBandwidthLimitProperties setCapacity(long capacity) {
        this.capacity = capacity;
        return this;
    }

    @Generated
    public Bucket4jBandwidthLimitProperties setRefillCount(long refillCount) {
        this.refillCount = refillCount;
        return this;
    }

    @Generated
    public Bucket4jBandwidthLimitProperties setDuration(String duration) {
        this.duration = duration;
        return this;
    }

    @Generated
    public Bucket4jBandwidthLimitProperties setRefillDuration(String refillDuration) {
        this.refillDuration = refillDuration;
        return this;
    }

    @Generated
    public Bucket4jBandwidthLimitProperties setRefillStrategy(BandwidthRefillStrategies refillStrategy) {
        this.refillStrategy = refillStrategy;
        return this;
    }

    public static enum BandwidthRefillStrategies {
        GREEDY,
        INTERVALLY;

    }
}

