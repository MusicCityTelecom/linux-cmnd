/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.core.authentication;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-core-authentication", automated=true)
@JsonFilter(value="PrincipalAttributesCoreProperties")
public class PrincipalAttributesCoreProperties
implements Serializable {
    private static final long serialVersionUID = -4525569588579072890L;
    private int expirationTime = 30;
    private String expirationTimeUnit = TimeUnit.MINUTES.name();
    private int maximumCacheSize = 10000;
    private boolean recoverExceptions = true;
    private MergingStrategyTypes merger = MergingStrategyTypes.REPLACE;
    private AggregationStrategyTypes aggregation = AggregationStrategyTypes.MERGE;
    private boolean requireAllRepositorySources;
    private Set<String> defaultAttributesToRelease = new HashSet<String>(0);

    @Generated
    public int getExpirationTime() {
        return this.expirationTime;
    }

    @Generated
    public String getExpirationTimeUnit() {
        return this.expirationTimeUnit;
    }

    @Generated
    public int getMaximumCacheSize() {
        return this.maximumCacheSize;
    }

    @Generated
    public boolean isRecoverExceptions() {
        return this.recoverExceptions;
    }

    @Generated
    public MergingStrategyTypes getMerger() {
        return this.merger;
    }

    @Generated
    public AggregationStrategyTypes getAggregation() {
        return this.aggregation;
    }

    @Generated
    public boolean isRequireAllRepositorySources() {
        return this.requireAllRepositorySources;
    }

    @Generated
    public Set<String> getDefaultAttributesToRelease() {
        return this.defaultAttributesToRelease;
    }

    @Generated
    public PrincipalAttributesCoreProperties setExpirationTime(int expirationTime) {
        this.expirationTime = expirationTime;
        return this;
    }

    @Generated
    public PrincipalAttributesCoreProperties setExpirationTimeUnit(String expirationTimeUnit) {
        this.expirationTimeUnit = expirationTimeUnit;
        return this;
    }

    @Generated
    public PrincipalAttributesCoreProperties setMaximumCacheSize(int maximumCacheSize) {
        this.maximumCacheSize = maximumCacheSize;
        return this;
    }

    @Generated
    public PrincipalAttributesCoreProperties setRecoverExceptions(boolean recoverExceptions) {
        this.recoverExceptions = recoverExceptions;
        return this;
    }

    @Generated
    public PrincipalAttributesCoreProperties setMerger(MergingStrategyTypes merger) {
        this.merger = merger;
        return this;
    }

    @Generated
    public PrincipalAttributesCoreProperties setAggregation(AggregationStrategyTypes aggregation) {
        this.aggregation = aggregation;
        return this;
    }

    @Generated
    public PrincipalAttributesCoreProperties setRequireAllRepositorySources(boolean requireAllRepositorySources) {
        this.requireAllRepositorySources = requireAllRepositorySources;
        return this;
    }

    @Generated
    public PrincipalAttributesCoreProperties setDefaultAttributesToRelease(Set<String> defaultAttributesToRelease) {
        this.defaultAttributesToRelease = defaultAttributesToRelease;
        return this;
    }

    public static enum MergingStrategyTypes {
        REPLACE,
        ADD,
        SOURCE,
        DESTINATION,
        MULTIVALUED;

    }

    public static enum AggregationStrategyTypes {
        MERGE,
        CASCADE;

    }
}

