/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.cosmosdb;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Generated;
import org.apereo.cas.configuration.support.DurationCapable;
import org.apereo.cas.configuration.support.ExpressionLanguageCapable;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-cosmosdb-core")
@JsonFilter(value="BaseCosmosDbProperties")
public abstract class BaseCosmosDbProperties
implements Serializable {
    private static final long serialVersionUID = 2528153816791719898L;
    @RequiredProperty
    @ExpressionLanguageCapable
    private String uri;
    @RequiredProperty
    @ExpressionLanguageCapable
    private String key;
    private String consistencyLevel = "SESSION";
    private boolean endpointDiscoveryEnabled = true;
    @RequiredProperty
    private String database;
    private int databaseThroughput = 4000;
    private boolean allowTelemetry;
    private boolean createContainer;
    private List<String> preferredRegions = new ArrayList<String>();
    private String userAgentSuffix;
    private int maxRetryAttemptsOnThrottledRequests = 4;
    @DurationCapable
    private String maxRetryWaitTime = "PT10S";
    private String indexingMode = "NONE";

    @Generated
    public String getUri() {
        return this.uri;
    }

    @Generated
    public String getKey() {
        return this.key;
    }

    @Generated
    public String getConsistencyLevel() {
        return this.consistencyLevel;
    }

    @Generated
    public boolean isEndpointDiscoveryEnabled() {
        return this.endpointDiscoveryEnabled;
    }

    @Generated
    public String getDatabase() {
        return this.database;
    }

    @Generated
    public int getDatabaseThroughput() {
        return this.databaseThroughput;
    }

    @Generated
    public boolean isAllowTelemetry() {
        return this.allowTelemetry;
    }

    @Generated
    public boolean isCreateContainer() {
        return this.createContainer;
    }

    @Generated
    public List<String> getPreferredRegions() {
        return this.preferredRegions;
    }

    @Generated
    public String getUserAgentSuffix() {
        return this.userAgentSuffix;
    }

    @Generated
    public int getMaxRetryAttemptsOnThrottledRequests() {
        return this.maxRetryAttemptsOnThrottledRequests;
    }

    @Generated
    public String getMaxRetryWaitTime() {
        return this.maxRetryWaitTime;
    }

    @Generated
    public String getIndexingMode() {
        return this.indexingMode;
    }

    @Generated
    public BaseCosmosDbProperties setUri(String uri) {
        this.uri = uri;
        return this;
    }

    @Generated
    public BaseCosmosDbProperties setKey(String key) {
        this.key = key;
        return this;
    }

    @Generated
    public BaseCosmosDbProperties setConsistencyLevel(String consistencyLevel) {
        this.consistencyLevel = consistencyLevel;
        return this;
    }

    @Generated
    public BaseCosmosDbProperties setEndpointDiscoveryEnabled(boolean endpointDiscoveryEnabled) {
        this.endpointDiscoveryEnabled = endpointDiscoveryEnabled;
        return this;
    }

    @Generated
    public BaseCosmosDbProperties setDatabase(String database) {
        this.database = database;
        return this;
    }

    @Generated
    public BaseCosmosDbProperties setDatabaseThroughput(int databaseThroughput) {
        this.databaseThroughput = databaseThroughput;
        return this;
    }

    @Generated
    public BaseCosmosDbProperties setAllowTelemetry(boolean allowTelemetry) {
        this.allowTelemetry = allowTelemetry;
        return this;
    }

    @Generated
    public BaseCosmosDbProperties setCreateContainer(boolean createContainer) {
        this.createContainer = createContainer;
        return this;
    }

    @Generated
    public BaseCosmosDbProperties setPreferredRegions(List<String> preferredRegions) {
        this.preferredRegions = preferredRegions;
        return this;
    }

    @Generated
    public BaseCosmosDbProperties setUserAgentSuffix(String userAgentSuffix) {
        this.userAgentSuffix = userAgentSuffix;
        return this;
    }

    @Generated
    public BaseCosmosDbProperties setMaxRetryAttemptsOnThrottledRequests(int maxRetryAttemptsOnThrottledRequests) {
        this.maxRetryAttemptsOnThrottledRequests = maxRetryAttemptsOnThrottledRequests;
        return this;
    }

    @Generated
    public BaseCosmosDbProperties setMaxRetryWaitTime(String maxRetryWaitTime) {
        this.maxRetryWaitTime = maxRetryWaitTime;
        return this;
    }

    @Generated
    public BaseCosmosDbProperties setIndexingMode(String indexingMode) {
        this.indexingMode = indexingMode;
        return this;
    }
}

