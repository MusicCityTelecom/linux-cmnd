/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.memcached;

import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-memcached-core")
public class BaseMemcachedProperties
implements Serializable {
    private static final long serialVersionUID = 514520518053691666L;
    private boolean shouldOptimize;
    private boolean daemon = true;
    private long maxReconnectDelay = -1L;
    private boolean useNagleAlgorithm;
    private long shutdownTimeoutSeconds = -1L;
    private int timeoutExceptionThreshold = 2;
    private long opTimeout = -1L;
    private TranscoderTypes transcoder = TranscoderTypes.KRYO;
    private int transcoderCompressionThreshold = 16384;
    @RequiredProperty
    private String servers = "localhost:11211";
    private String failureMode = "Redistribute";
    private String locatorType = "ARRAY_MOD";
    private String hashAlgorithm = "FNV1_64_HASH";
    private String protocol = "TEXT";
    private int maxTotal = 20;
    private int maxIdle = 8;
    private int minIdle;
    private boolean kryoAutoReset;
    private boolean kryoObjectsByReference;
    private boolean kryoRegistrationRequired = true;

    @Generated
    public boolean isShouldOptimize() {
        return this.shouldOptimize;
    }

    @Generated
    public boolean isDaemon() {
        return this.daemon;
    }

    @Generated
    public long getMaxReconnectDelay() {
        return this.maxReconnectDelay;
    }

    @Generated
    public boolean isUseNagleAlgorithm() {
        return this.useNagleAlgorithm;
    }

    @Generated
    public long getShutdownTimeoutSeconds() {
        return this.shutdownTimeoutSeconds;
    }

    @Generated
    public int getTimeoutExceptionThreshold() {
        return this.timeoutExceptionThreshold;
    }

    @Generated
    public long getOpTimeout() {
        return this.opTimeout;
    }

    @Generated
    public TranscoderTypes getTranscoder() {
        return this.transcoder;
    }

    @Generated
    public int getTranscoderCompressionThreshold() {
        return this.transcoderCompressionThreshold;
    }

    @Generated
    public String getServers() {
        return this.servers;
    }

    @Generated
    public String getFailureMode() {
        return this.failureMode;
    }

    @Generated
    public String getLocatorType() {
        return this.locatorType;
    }

    @Generated
    public String getHashAlgorithm() {
        return this.hashAlgorithm;
    }

    @Generated
    public String getProtocol() {
        return this.protocol;
    }

    @Generated
    public int getMaxTotal() {
        return this.maxTotal;
    }

    @Generated
    public int getMaxIdle() {
        return this.maxIdle;
    }

    @Generated
    public int getMinIdle() {
        return this.minIdle;
    }

    @Generated
    public boolean isKryoAutoReset() {
        return this.kryoAutoReset;
    }

    @Generated
    public boolean isKryoObjectsByReference() {
        return this.kryoObjectsByReference;
    }

    @Generated
    public boolean isKryoRegistrationRequired() {
        return this.kryoRegistrationRequired;
    }

    @Generated
    public BaseMemcachedProperties setShouldOptimize(boolean shouldOptimize) {
        this.shouldOptimize = shouldOptimize;
        return this;
    }

    @Generated
    public BaseMemcachedProperties setDaemon(boolean daemon) {
        this.daemon = daemon;
        return this;
    }

    @Generated
    public BaseMemcachedProperties setMaxReconnectDelay(long maxReconnectDelay) {
        this.maxReconnectDelay = maxReconnectDelay;
        return this;
    }

    @Generated
    public BaseMemcachedProperties setUseNagleAlgorithm(boolean useNagleAlgorithm) {
        this.useNagleAlgorithm = useNagleAlgorithm;
        return this;
    }

    @Generated
    public BaseMemcachedProperties setShutdownTimeoutSeconds(long shutdownTimeoutSeconds) {
        this.shutdownTimeoutSeconds = shutdownTimeoutSeconds;
        return this;
    }

    @Generated
    public BaseMemcachedProperties setTimeoutExceptionThreshold(int timeoutExceptionThreshold) {
        this.timeoutExceptionThreshold = timeoutExceptionThreshold;
        return this;
    }

    @Generated
    public BaseMemcachedProperties setOpTimeout(long opTimeout) {
        this.opTimeout = opTimeout;
        return this;
    }

    @Generated
    public BaseMemcachedProperties setTranscoder(TranscoderTypes transcoder) {
        this.transcoder = transcoder;
        return this;
    }

    @Generated
    public BaseMemcachedProperties setTranscoderCompressionThreshold(int transcoderCompressionThreshold) {
        this.transcoderCompressionThreshold = transcoderCompressionThreshold;
        return this;
    }

    @Generated
    public BaseMemcachedProperties setServers(String servers) {
        this.servers = servers;
        return this;
    }

    @Generated
    public BaseMemcachedProperties setFailureMode(String failureMode) {
        this.failureMode = failureMode;
        return this;
    }

    @Generated
    public BaseMemcachedProperties setLocatorType(String locatorType) {
        this.locatorType = locatorType;
        return this;
    }

    @Generated
    public BaseMemcachedProperties setHashAlgorithm(String hashAlgorithm) {
        this.hashAlgorithm = hashAlgorithm;
        return this;
    }

    @Generated
    public BaseMemcachedProperties setProtocol(String protocol) {
        this.protocol = protocol;
        return this;
    }

    @Generated
    public BaseMemcachedProperties setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
        return this;
    }

    @Generated
    public BaseMemcachedProperties setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
        return this;
    }

    @Generated
    public BaseMemcachedProperties setMinIdle(int minIdle) {
        this.minIdle = minIdle;
        return this;
    }

    @Generated
    public BaseMemcachedProperties setKryoAutoReset(boolean kryoAutoReset) {
        this.kryoAutoReset = kryoAutoReset;
        return this;
    }

    @Generated
    public BaseMemcachedProperties setKryoObjectsByReference(boolean kryoObjectsByReference) {
        this.kryoObjectsByReference = kryoObjectsByReference;
        return this;
    }

    @Generated
    public BaseMemcachedProperties setKryoRegistrationRequired(boolean kryoRegistrationRequired) {
        this.kryoRegistrationRequired = kryoRegistrationRequired;
        return this;
    }

    public static enum TranscoderTypes {
        KRYO,
        SERIAL,
        WHALIN,
        WHALINV1;

    }
}

