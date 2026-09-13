/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.couchbase;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Generated;
import org.apereo.cas.configuration.support.DurationCapable;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-couchbase-core")
public abstract class BaseCouchbaseProperties
implements Serializable {
    private static final long serialVersionUID = 6550895842866988551L;
    @RequiredProperty
    private List<String> addresses = Stream.of("localhost").collect(Collectors.toList());
    @DurationCapable
    private String connectionTimeout = "PT60S";
    @DurationCapable
    private String idleConnectionTimeout = "PT60S";
    @DurationCapable
    private String searchTimeout = "PT30S";
    @DurationCapable
    private String queryTimeout = "PT30S";
    @DurationCapable
    private String viewTimeout = "PT30S";
    @DurationCapable
    private String kvTimeout = "PT30S";
    @DurationCapable
    private String scanWaitTimeout = "PT30S";
    @RequiredProperty
    private String clusterUsername;
    @RequiredProperty
    private String clusterPassword;
    private int maxHttpConnections = 5;
    private int maxParallelism;
    private long maxNumRequestsInRetry = 32768L;
    @RequiredProperty
    private String bucket = "testbucket";
    private String scanConsistency = "NOT_BOUNDED";

    @Generated
    public List<String> getAddresses() {
        return this.addresses;
    }

    @Generated
    public String getConnectionTimeout() {
        return this.connectionTimeout;
    }

    @Generated
    public String getIdleConnectionTimeout() {
        return this.idleConnectionTimeout;
    }

    @Generated
    public String getSearchTimeout() {
        return this.searchTimeout;
    }

    @Generated
    public String getQueryTimeout() {
        return this.queryTimeout;
    }

    @Generated
    public String getViewTimeout() {
        return this.viewTimeout;
    }

    @Generated
    public String getKvTimeout() {
        return this.kvTimeout;
    }

    @Generated
    public String getScanWaitTimeout() {
        return this.scanWaitTimeout;
    }

    @Generated
    public String getClusterUsername() {
        return this.clusterUsername;
    }

    @Generated
    public String getClusterPassword() {
        return this.clusterPassword;
    }

    @Generated
    public int getMaxHttpConnections() {
        return this.maxHttpConnections;
    }

    @Generated
    public int getMaxParallelism() {
        return this.maxParallelism;
    }

    @Generated
    public long getMaxNumRequestsInRetry() {
        return this.maxNumRequestsInRetry;
    }

    @Generated
    public String getBucket() {
        return this.bucket;
    }

    @Generated
    public String getScanConsistency() {
        return this.scanConsistency;
    }

    @Generated
    public BaseCouchbaseProperties setAddresses(List<String> addresses) {
        this.addresses = addresses;
        return this;
    }

    @Generated
    public BaseCouchbaseProperties setConnectionTimeout(String connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
        return this;
    }

    @Generated
    public BaseCouchbaseProperties setIdleConnectionTimeout(String idleConnectionTimeout) {
        this.idleConnectionTimeout = idleConnectionTimeout;
        return this;
    }

    @Generated
    public BaseCouchbaseProperties setSearchTimeout(String searchTimeout) {
        this.searchTimeout = searchTimeout;
        return this;
    }

    @Generated
    public BaseCouchbaseProperties setQueryTimeout(String queryTimeout) {
        this.queryTimeout = queryTimeout;
        return this;
    }

    @Generated
    public BaseCouchbaseProperties setViewTimeout(String viewTimeout) {
        this.viewTimeout = viewTimeout;
        return this;
    }

    @Generated
    public BaseCouchbaseProperties setKvTimeout(String kvTimeout) {
        this.kvTimeout = kvTimeout;
        return this;
    }

    @Generated
    public BaseCouchbaseProperties setScanWaitTimeout(String scanWaitTimeout) {
        this.scanWaitTimeout = scanWaitTimeout;
        return this;
    }

    @Generated
    public BaseCouchbaseProperties setClusterUsername(String clusterUsername) {
        this.clusterUsername = clusterUsername;
        return this;
    }

    @Generated
    public BaseCouchbaseProperties setClusterPassword(String clusterPassword) {
        this.clusterPassword = clusterPassword;
        return this;
    }

    @Generated
    public BaseCouchbaseProperties setMaxHttpConnections(int maxHttpConnections) {
        this.maxHttpConnections = maxHttpConnections;
        return this;
    }

    @Generated
    public BaseCouchbaseProperties setMaxParallelism(int maxParallelism) {
        this.maxParallelism = maxParallelism;
        return this;
    }

    @Generated
    public BaseCouchbaseProperties setMaxNumRequestsInRetry(long maxNumRequestsInRetry) {
        this.maxNumRequestsInRetry = maxNumRequestsInRetry;
        return this;
    }

    @Generated
    public BaseCouchbaseProperties setBucket(String bucket) {
        this.bucket = bucket;
        return this;
    }

    @Generated
    public BaseCouchbaseProperties setScanConsistency(String scanConsistency) {
        this.scanConsistency = scanConsistency;
        return this;
    }
}

