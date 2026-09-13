/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.ignite;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.util.EncryptionRandomizedSigningJwtCryptographyProperties;
import org.apereo.cas.configuration.support.DurationCapable;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-ignite-ticket-registry")
public class IgniteProperties
implements Serializable {
    private static final long serialVersionUID = -5259465262649559156L;
    @RequiredProperty
    private List<String> igniteAddress = Stream.of("localhost:47500").collect(Collectors.toList());
    private TicketsCache ticketsCache = new TicketsCache();
    private String keyStoreType = "JKS";
    private String keyStoreFilePath;
    private String keyStorePassword;
    private String trustStoreType = "JKS";
    private String protocol = "TLS";
    private String keyAlgorithm = "SunX509";
    private String trustStoreFilePath;
    private String trustStorePassword;
    @DurationCapable
    private String ackTimeout = "PT2S";
    @DurationCapable
    private String joinTimeout = "PT1S";
    private String localAddress;
    @RequiredProperty
    private int localPort = -1;
    @DurationCapable
    private String networkTimeout = "PT5S";
    @DurationCapable
    private String socketTimeout = "PT5S";
    private int threadPriority = 10;
    private long defaultRegionMaxSize = 0x100000000L;
    private boolean defaultPersistenceEnabled;
    private boolean clientMode;
    private boolean forceServerMode;
    @NestedConfigurationProperty
    private EncryptionRandomizedSigningJwtCryptographyProperties crypto = new EncryptionRandomizedSigningJwtCryptographyProperties();

    public IgniteProperties() {
        this.crypto.setEnabled(false);
    }

    @Generated
    public List<String> getIgniteAddress() {
        return this.igniteAddress;
    }

    @Generated
    public TicketsCache getTicketsCache() {
        return this.ticketsCache;
    }

    @Generated
    public String getKeyStoreType() {
        return this.keyStoreType;
    }

    @Generated
    public String getKeyStoreFilePath() {
        return this.keyStoreFilePath;
    }

    @Generated
    public String getKeyStorePassword() {
        return this.keyStorePassword;
    }

    @Generated
    public String getTrustStoreType() {
        return this.trustStoreType;
    }

    @Generated
    public String getProtocol() {
        return this.protocol;
    }

    @Generated
    public String getKeyAlgorithm() {
        return this.keyAlgorithm;
    }

    @Generated
    public String getTrustStoreFilePath() {
        return this.trustStoreFilePath;
    }

    @Generated
    public String getTrustStorePassword() {
        return this.trustStorePassword;
    }

    @Generated
    public String getAckTimeout() {
        return this.ackTimeout;
    }

    @Generated
    public String getJoinTimeout() {
        return this.joinTimeout;
    }

    @Generated
    public String getLocalAddress() {
        return this.localAddress;
    }

    @Generated
    public int getLocalPort() {
        return this.localPort;
    }

    @Generated
    public String getNetworkTimeout() {
        return this.networkTimeout;
    }

    @Generated
    public String getSocketTimeout() {
        return this.socketTimeout;
    }

    @Generated
    public int getThreadPriority() {
        return this.threadPriority;
    }

    @Generated
    public long getDefaultRegionMaxSize() {
        return this.defaultRegionMaxSize;
    }

    @Generated
    public boolean isDefaultPersistenceEnabled() {
        return this.defaultPersistenceEnabled;
    }

    @Generated
    public boolean isClientMode() {
        return this.clientMode;
    }

    @Generated
    public boolean isForceServerMode() {
        return this.forceServerMode;
    }

    @Generated
    public EncryptionRandomizedSigningJwtCryptographyProperties getCrypto() {
        return this.crypto;
    }

    @Generated
    public IgniteProperties setIgniteAddress(List<String> igniteAddress) {
        this.igniteAddress = igniteAddress;
        return this;
    }

    @Generated
    public IgniteProperties setTicketsCache(TicketsCache ticketsCache) {
        this.ticketsCache = ticketsCache;
        return this;
    }

    @Generated
    public IgniteProperties setKeyStoreType(String keyStoreType) {
        this.keyStoreType = keyStoreType;
        return this;
    }

    @Generated
    public IgniteProperties setKeyStoreFilePath(String keyStoreFilePath) {
        this.keyStoreFilePath = keyStoreFilePath;
        return this;
    }

    @Generated
    public IgniteProperties setKeyStorePassword(String keyStorePassword) {
        this.keyStorePassword = keyStorePassword;
        return this;
    }

    @Generated
    public IgniteProperties setTrustStoreType(String trustStoreType) {
        this.trustStoreType = trustStoreType;
        return this;
    }

    @Generated
    public IgniteProperties setProtocol(String protocol) {
        this.protocol = protocol;
        return this;
    }

    @Generated
    public IgniteProperties setKeyAlgorithm(String keyAlgorithm) {
        this.keyAlgorithm = keyAlgorithm;
        return this;
    }

    @Generated
    public IgniteProperties setTrustStoreFilePath(String trustStoreFilePath) {
        this.trustStoreFilePath = trustStoreFilePath;
        return this;
    }

    @Generated
    public IgniteProperties setTrustStorePassword(String trustStorePassword) {
        this.trustStorePassword = trustStorePassword;
        return this;
    }

    @Generated
    public IgniteProperties setAckTimeout(String ackTimeout) {
        this.ackTimeout = ackTimeout;
        return this;
    }

    @Generated
    public IgniteProperties setJoinTimeout(String joinTimeout) {
        this.joinTimeout = joinTimeout;
        return this;
    }

    @Generated
    public IgniteProperties setLocalAddress(String localAddress) {
        this.localAddress = localAddress;
        return this;
    }

    @Generated
    public IgniteProperties setLocalPort(int localPort) {
        this.localPort = localPort;
        return this;
    }

    @Generated
    public IgniteProperties setNetworkTimeout(String networkTimeout) {
        this.networkTimeout = networkTimeout;
        return this;
    }

    @Generated
    public IgniteProperties setSocketTimeout(String socketTimeout) {
        this.socketTimeout = socketTimeout;
        return this;
    }

    @Generated
    public IgniteProperties setThreadPriority(int threadPriority) {
        this.threadPriority = threadPriority;
        return this;
    }

    @Generated
    public IgniteProperties setDefaultRegionMaxSize(long defaultRegionMaxSize) {
        this.defaultRegionMaxSize = defaultRegionMaxSize;
        return this;
    }

    @Generated
    public IgniteProperties setDefaultPersistenceEnabled(boolean defaultPersistenceEnabled) {
        this.defaultPersistenceEnabled = defaultPersistenceEnabled;
        return this;
    }

    @Generated
    public IgniteProperties setClientMode(boolean clientMode) {
        this.clientMode = clientMode;
        return this;
    }

    @Generated
    public IgniteProperties setForceServerMode(boolean forceServerMode) {
        this.forceServerMode = forceServerMode;
        return this;
    }

    @Generated
    public IgniteProperties setCrypto(EncryptionRandomizedSigningJwtCryptographyProperties crypto) {
        this.crypto = crypto;
        return this;
    }

    @RequiresModule(name="cas-server-support-ignite-ticket-registry")
    public static class TicketsCache
    implements Serializable {
        private static final long serialVersionUID = 4715167757542984471L;
        private String cacheMode = "REPLICATED";
        private String atomicityMode = "TRANSACTIONAL";
        private String writeSynchronizationMode = "FULL_SYNC";

        @Generated
        public String getCacheMode() {
            return this.cacheMode;
        }

        @Generated
        public String getAtomicityMode() {
            return this.atomicityMode;
        }

        @Generated
        public String getWriteSynchronizationMode() {
            return this.writeSynchronizationMode;
        }

        @Generated
        public TicketsCache setCacheMode(String cacheMode) {
            this.cacheMode = cacheMode;
            return this;
        }

        @Generated
        public TicketsCache setAtomicityMode(String atomicityMode) {
            this.atomicityMode = atomicityMode;
            return this;
        }

        @Generated
        public TicketsCache setWriteSynchronizationMode(String writeSynchronizationMode) {
            this.writeSynchronizationMode = writeSynchronizationMode;
            return this;
        }
    }
}

