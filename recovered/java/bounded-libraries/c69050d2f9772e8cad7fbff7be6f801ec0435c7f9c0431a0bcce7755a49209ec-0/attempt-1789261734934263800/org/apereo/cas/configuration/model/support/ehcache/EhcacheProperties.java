/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 *  org.springframework.core.io.ClassPathResource
 *  org.springframework.core.io.Resource
 */
package org.apereo.cas.configuration.model.support.ehcache;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.util.EncryptionRandomizedSigningJwtCryptographyProperties;
import org.apereo.cas.configuration.support.DurationCapable;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@RequiresModule(name="cas-server-support-ehcache-ticket-registry")
@Deprecated(since="6.2.0")
public class EhcacheProperties
implements Serializable {
    private static final long serialVersionUID = 7772510035918976450L;
    @RequiredProperty
    @Deprecated(since="6.2.0")
    private boolean enabled = true;
    @Deprecated(since="6.2.0")
    private boolean synchronousWrites;
    @Deprecated(since="6.2.0")
    private boolean loaderAsync = true;
    @Deprecated(since="6.2.0")
    private int maxChunkSize = 5000000;
    @Deprecated(since="6.2.0")
    private int maximumBatchSize = 100;
    @Deprecated(since="6.2.0")
    @DurationCapable
    private String replicationInterval = "PT10S";
    @Deprecated(since="6.2.0")
    private boolean replicatePuts = true;
    @Deprecated(since="6.2.0")
    private boolean replicateUpdatesViaCopy = true;
    @Deprecated(since="6.2.0")
    private boolean replicateRemovals = true;
    @Deprecated(since="6.2.0")
    private boolean replicateUpdates = true;
    @Deprecated(since="6.2.0")
    private boolean replicatePutsViaCopy = true;
    @RequiredProperty
    @Deprecated(since="6.2.0")
    private transient Resource configLocation = new ClassPathResource("ehcache-replicated.xml");
    @Deprecated(since="6.2.0")
    private boolean shared;
    @RequiredProperty
    @Deprecated(since="6.2.0")
    private String cacheManagerName = "ticketRegistryCacheManager";
    @Deprecated(since="6.2.0")
    private int diskExpiryThreadIntervalSeconds;
    @Deprecated(since="6.2.0")
    private boolean eternal;
    @Deprecated(since="6.2.0")
    private int maxElementsInMemory = 10000;
    @Deprecated(since="6.2.0")
    private int maxElementsInCache;
    @Deprecated(since="6.2.0")
    private int maxElementsOnDisk;
    @Deprecated(since="6.2.0")
    private String memoryStoreEvictionPolicy = "LRU";
    @Deprecated(since="6.2.0")
    private String persistence = "NONE";
    @Deprecated(since="6.2.0")
    private final Map<String, String> systemProps = new HashMap<String, String>(0);
    @NestedConfigurationProperty
    @Deprecated(since="6.2.0")
    private EncryptionRandomizedSigningJwtCryptographyProperties crypto = new EncryptionRandomizedSigningJwtCryptographyProperties();

    @Deprecated(since="6.2.0")
    public EhcacheProperties() {
        this.crypto.setEnabled(false);
    }

    @Deprecated
    @Generated
    public boolean isEnabled() {
        return this.enabled;
    }

    @Deprecated
    @Generated
    public boolean isSynchronousWrites() {
        return this.synchronousWrites;
    }

    @Deprecated
    @Generated
    public boolean isLoaderAsync() {
        return this.loaderAsync;
    }

    @Deprecated
    @Generated
    public int getMaxChunkSize() {
        return this.maxChunkSize;
    }

    @Deprecated
    @Generated
    public int getMaximumBatchSize() {
        return this.maximumBatchSize;
    }

    @Deprecated
    @Generated
    public String getReplicationInterval() {
        return this.replicationInterval;
    }

    @Deprecated
    @Generated
    public boolean isReplicatePuts() {
        return this.replicatePuts;
    }

    @Deprecated
    @Generated
    public boolean isReplicateUpdatesViaCopy() {
        return this.replicateUpdatesViaCopy;
    }

    @Deprecated
    @Generated
    public boolean isReplicateRemovals() {
        return this.replicateRemovals;
    }

    @Deprecated
    @Generated
    public boolean isReplicateUpdates() {
        return this.replicateUpdates;
    }

    @Deprecated
    @Generated
    public boolean isReplicatePutsViaCopy() {
        return this.replicatePutsViaCopy;
    }

    @Deprecated
    @Generated
    public Resource getConfigLocation() {
        return this.configLocation;
    }

    @Deprecated
    @Generated
    public boolean isShared() {
        return this.shared;
    }

    @Deprecated
    @Generated
    public String getCacheManagerName() {
        return this.cacheManagerName;
    }

    @Deprecated
    @Generated
    public int getDiskExpiryThreadIntervalSeconds() {
        return this.diskExpiryThreadIntervalSeconds;
    }

    @Deprecated
    @Generated
    public boolean isEternal() {
        return this.eternal;
    }

    @Deprecated
    @Generated
    public int getMaxElementsInMemory() {
        return this.maxElementsInMemory;
    }

    @Deprecated
    @Generated
    public int getMaxElementsInCache() {
        return this.maxElementsInCache;
    }

    @Deprecated
    @Generated
    public int getMaxElementsOnDisk() {
        return this.maxElementsOnDisk;
    }

    @Deprecated
    @Generated
    public String getMemoryStoreEvictionPolicy() {
        return this.memoryStoreEvictionPolicy;
    }

    @Deprecated
    @Generated
    public String getPersistence() {
        return this.persistence;
    }

    @Deprecated
    @Generated
    public Map<String, String> getSystemProps() {
        return this.systemProps;
    }

    @Deprecated
    @Generated
    public EncryptionRandomizedSigningJwtCryptographyProperties getCrypto() {
        return this.crypto;
    }

    @Deprecated
    @Generated
    public EhcacheProperties setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    @Deprecated
    @Generated
    public EhcacheProperties setSynchronousWrites(boolean synchronousWrites) {
        this.synchronousWrites = synchronousWrites;
        return this;
    }

    @Deprecated
    @Generated
    public EhcacheProperties setLoaderAsync(boolean loaderAsync) {
        this.loaderAsync = loaderAsync;
        return this;
    }

    @Deprecated
    @Generated
    public EhcacheProperties setMaxChunkSize(int maxChunkSize) {
        this.maxChunkSize = maxChunkSize;
        return this;
    }

    @Deprecated
    @Generated
    public EhcacheProperties setMaximumBatchSize(int maximumBatchSize) {
        this.maximumBatchSize = maximumBatchSize;
        return this;
    }

    @Deprecated
    @Generated
    public EhcacheProperties setReplicationInterval(String replicationInterval) {
        this.replicationInterval = replicationInterval;
        return this;
    }

    @Deprecated
    @Generated
    public EhcacheProperties setReplicatePuts(boolean replicatePuts) {
        this.replicatePuts = replicatePuts;
        return this;
    }

    @Deprecated
    @Generated
    public EhcacheProperties setReplicateUpdatesViaCopy(boolean replicateUpdatesViaCopy) {
        this.replicateUpdatesViaCopy = replicateUpdatesViaCopy;
        return this;
    }

    @Deprecated
    @Generated
    public EhcacheProperties setReplicateRemovals(boolean replicateRemovals) {
        this.replicateRemovals = replicateRemovals;
        return this;
    }

    @Deprecated
    @Generated
    public EhcacheProperties setReplicateUpdates(boolean replicateUpdates) {
        this.replicateUpdates = replicateUpdates;
        return this;
    }

    @Deprecated
    @Generated
    public EhcacheProperties setReplicatePutsViaCopy(boolean replicatePutsViaCopy) {
        this.replicatePutsViaCopy = replicatePutsViaCopy;
        return this;
    }

    @Deprecated
    @Generated
    public EhcacheProperties setConfigLocation(Resource configLocation) {
        this.configLocation = configLocation;
        return this;
    }

    @Deprecated
    @Generated
    public EhcacheProperties setShared(boolean shared) {
        this.shared = shared;
        return this;
    }

    @Deprecated
    @Generated
    public EhcacheProperties setCacheManagerName(String cacheManagerName) {
        this.cacheManagerName = cacheManagerName;
        return this;
    }

    @Deprecated
    @Generated
    public EhcacheProperties setDiskExpiryThreadIntervalSeconds(int diskExpiryThreadIntervalSeconds) {
        this.diskExpiryThreadIntervalSeconds = diskExpiryThreadIntervalSeconds;
        return this;
    }

    @Deprecated
    @Generated
    public EhcacheProperties setEternal(boolean eternal) {
        this.eternal = eternal;
        return this;
    }

    @Deprecated
    @Generated
    public EhcacheProperties setMaxElementsInMemory(int maxElementsInMemory) {
        this.maxElementsInMemory = maxElementsInMemory;
        return this;
    }

    @Deprecated
    @Generated
    public EhcacheProperties setMaxElementsInCache(int maxElementsInCache) {
        this.maxElementsInCache = maxElementsInCache;
        return this;
    }

    @Deprecated
    @Generated
    public EhcacheProperties setMaxElementsOnDisk(int maxElementsOnDisk) {
        this.maxElementsOnDisk = maxElementsOnDisk;
        return this;
    }

    @Deprecated
    @Generated
    public EhcacheProperties setMemoryStoreEvictionPolicy(String memoryStoreEvictionPolicy) {
        this.memoryStoreEvictionPolicy = memoryStoreEvictionPolicy;
        return this;
    }

    @Deprecated
    @Generated
    public EhcacheProperties setPersistence(String persistence) {
        this.persistence = persistence;
        return this;
    }

    @Deprecated
    @Generated
    public EhcacheProperties setCrypto(EncryptionRandomizedSigningJwtCryptographyProperties crypto) {
        this.crypto = crypto;
        return this;
    }
}

