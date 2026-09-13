/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.ehcache.CacheException
 *  net.sf.ehcache.CacheManager
 *  net.sf.ehcache.Ehcache
 *  net.sf.ehcache.Status
 *  net.sf.ehcache.config.ManagementRESTServiceConfiguration
 *  net.sf.ehcache.event.CacheManagerEventListener
 *  net.sf.ehcache.management.sampled.CacheManagerSampler
 *  net.sf.ehcache.management.sampled.CacheManagerSamplerImpl
 *  net.sf.ehcache.management.sampled.CacheSampler
 *  net.sf.ehcache.management.sampled.CacheSamplerImpl
 *  net.sf.ehcache.terracotta.ClusteredInstanceFactory
 */
package net.sf.ehcache.management.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.ClusteredInstanceFactoryAccessor;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Status;
import net.sf.ehcache.config.ManagementRESTServiceConfiguration;
import net.sf.ehcache.event.CacheManagerEventListener;
import net.sf.ehcache.management.resource.CacheConfigEntity;
import net.sf.ehcache.management.resource.CacheEntity;
import net.sf.ehcache.management.resource.CacheManagerConfigEntity;
import net.sf.ehcache.management.resource.CacheManagerEntity;
import net.sf.ehcache.management.resource.CacheStatisticSampleEntity;
import net.sf.ehcache.management.resource.QueryResultsEntity;
import net.sf.ehcache.management.sampled.CacheManagerSampler;
import net.sf.ehcache.management.sampled.CacheManagerSamplerImpl;
import net.sf.ehcache.management.sampled.CacheSampler;
import net.sf.ehcache.management.sampled.CacheSamplerImpl;
import net.sf.ehcache.management.service.CacheManagerService;
import net.sf.ehcache.management.service.CacheService;
import net.sf.ehcache.management.service.EntityResourceFactory;
import net.sf.ehcache.management.service.ManagementServerLifecycle;
import net.sf.ehcache.management.service.impl.CacheConfigurationEntityBuilder;
import net.sf.ehcache.management.service.impl.CacheEntityBuilder;
import net.sf.ehcache.management.service.impl.CacheManagerConfigurationEntityBuilder;
import net.sf.ehcache.management.service.impl.CacheManagerEntityBuilder;
import net.sf.ehcache.management.service.impl.CacheStatisticSampleEntityBuilder;
import net.sf.ehcache.management.service.impl.RemoteAgentEndpointImpl;
import net.sf.ehcache.terracotta.ClusteredInstanceFactory;
import org.terracotta.management.ServiceExecutionException;
import org.terracotta.management.ServiceLocator;
import org.terracotta.management.resource.AgentEntity;
import org.terracotta.management.resource.AgentMetadataEntity;
import org.terracotta.management.resource.exceptions.ExceptionUtils;
import org.terracotta.management.resource.services.AgentService;
import org.terracotta.management.resource.services.LicenseService;
import org.terracotta.management.resource.services.Utils;

public class DfltSamplerRepositoryService
implements ManagementServerLifecycle,
EntityResourceFactory,
CacheManagerService,
CacheService,
AgentService {
    public static final String AGENCY = "Ehcache";
    private final Map<String, SamplerRepoEntry> cacheManagerSamplerRepo = new HashMap<String, SamplerRepoEntry>();
    private final ReadWriteLock cacheManagerSamplerRepoLock = new ReentrantReadWriteLock();
    protected final ManagementRESTServiceConfiguration configuration;
    private final RemoteAgentEndpointImpl remoteAgentEndpoint;

    public DfltSamplerRepositoryService(ManagementRESTServiceConfiguration configuration, RemoteAgentEndpointImpl remoteAgentEndpoint) {
        this.configuration = configuration;
        this.remoteAgentEndpoint = remoteAgentEndpoint;
    }

    private static void enableNonStopFor(SamplerRepoEntry samplerRepoEntry, boolean enable) {
        ClusteredInstanceFactory clusteredInstanceFactory = ClusteredInstanceFactoryAccessor.getClusteredInstanceFactory(samplerRepoEntry.cacheManager);
        if (clusteredInstanceFactory != null) {
            clusteredInstanceFactory.enableNonStopForCurrentThread(enable);
        }
    }

    @Override
    public void dispose() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void register(CacheManager cacheManager) {
        String cmName = cacheManager.getName();
        this.cacheManagerSamplerRepoLock.writeLock().lock();
        try {
            if (!this.cacheManagerSamplerRepo.containsKey(cmName)) {
                SamplerRepoEntry entry = new SamplerRepoEntry(cacheManager);
                cacheManager.setCacheManagerEventListener((CacheManagerEventListener)entry);
                this.cacheManagerSamplerRepo.put(cmName, entry);
            }
        }
        finally {
            this.cacheManagerSamplerRepoLock.writeLock().unlock();
        }
    }

    @Override
    public void unregister(CacheManager cacheManager) {
        this.cacheManagerSamplerRepoLock.writeLock().lock();
        try {
            SamplerRepoEntry entry = this.cacheManagerSamplerRepo.remove(cacheManager.getName());
            entry.destroy();
        }
        finally {
            this.cacheManagerSamplerRepoLock.writeLock().unlock();
        }
    }

    @Override
    public boolean hasRegistered() {
        this.cacheManagerSamplerRepoLock.readLock().lock();
        try {
            boolean bl = !this.cacheManagerSamplerRepo.isEmpty();
            return bl;
        }
        finally {
            this.cacheManagerSamplerRepoLock.readLock().unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Collection<CacheManagerEntity> createCacheManagerEntities(Set<String> cacheManagerNames, Set<String> attributes) {
        Collection<Object> entities;
        String requestClusterUUID = this.remoteAgentEndpoint.getRequestClusterUUID();
        CacheManagerEntityBuilder builder = null;
        this.cacheManagerSamplerRepoLock.readLock().lock();
        try {
            if (cacheManagerNames == null) {
                for (SamplerRepoEntry entry : this.cacheManagerSamplerRepo.values()) {
                    if (!entry.isConnectedToCluster(requestClusterUUID)) continue;
                    builder = builder == null ? CacheManagerEntityBuilder.createWith(entry.getCacheManagerSampler()) : builder.add(entry.getCacheManagerSampler());
                }
            } else {
                for (String cmName : cacheManagerNames) {
                    SamplerRepoEntry entry = this.cacheManagerSamplerRepo.get(cmName);
                    if (entry == null || !entry.isConnectedToCluster(requestClusterUUID)) continue;
                    builder = builder == null ? CacheManagerEntityBuilder.createWith(entry.getCacheManagerSampler()) : builder.add(entry.getCacheManagerSampler());
                }
            }
            entities = builder == null ? new HashSet(0) : (attributes == null ? builder.build() : builder.add(attributes).build());
        }
        finally {
            this.cacheManagerSamplerRepoLock.readLock().unlock();
        }
        return entities;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Collection<CacheManagerConfigEntity> createCacheManagerConfigEntities(Set<String> cacheManagerNames) {
        Collection<CacheManagerConfigEntity> entities;
        CacheManagerConfigurationEntityBuilder builder = null;
        String requestClusterUUID = this.remoteAgentEndpoint.getRequestClusterUUID();
        this.cacheManagerSamplerRepoLock.readLock().lock();
        try {
            if (cacheManagerNames == null) {
                for (SamplerRepoEntry entry : this.cacheManagerSamplerRepo.values()) {
                    if (!entry.isConnectedToCluster(requestClusterUUID)) continue;
                    builder = builder == null ? CacheManagerConfigurationEntityBuilder.createWith(entry.getCacheManagerSampler()) : builder.add(entry.getCacheManagerSampler());
                }
            } else {
                for (String cmName : cacheManagerNames) {
                    SamplerRepoEntry entry = this.cacheManagerSamplerRepo.get(cmName);
                    if (entry == null || !entry.isConnectedToCluster(requestClusterUUID)) continue;
                    builder = builder == null ? CacheManagerConfigurationEntityBuilder.createWith(entry.getCacheManagerSampler()) : builder.add(entry.getCacheManagerSampler());
                }
            }
            entities = builder == null ? new HashSet<CacheManagerConfigEntity>(0) : builder.build();
        }
        finally {
            this.cacheManagerSamplerRepoLock.readLock().unlock();
        }
        return entities;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Collection<CacheEntity> createCacheEntities(Set<String> cacheManagerNames, Set<String> cacheNames, Set<String> attributes) {
        Collection<Object> entities;
        CacheEntityBuilder builder = null;
        String requestClusterUUID = this.remoteAgentEndpoint.getRequestClusterUUID();
        this.cacheManagerSamplerRepoLock.readLock().lock();
        ArrayList<Object> disabledSamplerRepoEntries = new ArrayList<Object>();
        try {
            if (cacheManagerNames == null) {
                for (Map.Entry entry : this.cacheManagerSamplerRepo.entrySet()) {
                    if (!((SamplerRepoEntry)entry.getValue()).isConnectedToCluster(requestClusterUUID)) continue;
                    DfltSamplerRepositoryService.enableNonStopFor((SamplerRepoEntry)entry.getValue(), false);
                    disabledSamplerRepoEntries.add(entry.getValue());
                    for (CacheSampler sampler : ((SamplerRepoEntry)entry.getValue()).getComprehensiveCacheSamplers(cacheNames)) {
                        builder = builder == null ? CacheEntityBuilder.createWith(sampler, (String)entry.getKey()) : builder.add(sampler, (String)entry.getKey());
                    }
                }
            } else {
                for (String string : cacheManagerNames) {
                    SamplerRepoEntry entry = this.cacheManagerSamplerRepo.get(string);
                    if (entry == null || !entry.isConnectedToCluster(requestClusterUUID)) continue;
                    DfltSamplerRepositoryService.enableNonStopFor(entry, false);
                    disabledSamplerRepoEntries.add(entry);
                    for (CacheSampler sampler : entry.getComprehensiveCacheSamplers(cacheNames)) {
                        builder = builder == null ? CacheEntityBuilder.createWith(sampler, string) : builder.add(sampler, string);
                    }
                }
            }
            entities = builder == null ? new HashSet(0) : (attributes == null ? builder.build() : builder.add(attributes).build());
        }
        finally {
            for (SamplerRepoEntry samplerRepoEntry : disabledSamplerRepoEntries) {
                DfltSamplerRepositoryService.enableNonStopFor(samplerRepoEntry, true);
            }
            this.cacheManagerSamplerRepoLock.readLock().unlock();
        }
        return entities;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Collection<CacheConfigEntity> createCacheConfigEntities(Set<String> cacheManagerNames, Set<String> cacheNames) {
        Collection<CacheConfigEntity> entities;
        CacheConfigurationEntityBuilder builder = null;
        String requestClusterUUID = this.remoteAgentEndpoint.getRequestClusterUUID();
        this.cacheManagerSamplerRepoLock.readLock().lock();
        try {
            if (cacheManagerNames == null) {
                for (Map.Entry<String, SamplerRepoEntry> entry : this.cacheManagerSamplerRepo.entrySet()) {
                    for (CacheSampler sampler : entry.getValue().getComprehensiveCacheSamplers(cacheNames)) {
                        if (!entry.getValue().isConnectedToCluster(requestClusterUUID)) continue;
                        builder = builder == null ? CacheConfigurationEntityBuilder.createWith(entry.getValue().getCacheManagerSampler(), sampler.getCacheName()) : builder.add(entry.getValue().getCacheManagerSampler(), sampler.getCacheName());
                    }
                }
            } else {
                for (String cmName : cacheManagerNames) {
                    SamplerRepoEntry entry = this.cacheManagerSamplerRepo.get(cmName);
                    if (entry == null || !entry.isConnectedToCluster(requestClusterUUID)) continue;
                    for (CacheSampler sampler : entry.getComprehensiveCacheSamplers(cacheNames)) {
                        builder = builder == null ? CacheConfigurationEntityBuilder.createWith(entry.getCacheManagerSampler(), sampler.getCacheName()) : builder.add(entry.getCacheManagerSampler(), sampler.getCacheName());
                    }
                }
            }
            entities = builder == null ? new HashSet<CacheConfigEntity>(0) : builder.build();
        }
        finally {
            this.cacheManagerSamplerRepoLock.readLock().unlock();
        }
        return entities;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Collection<CacheStatisticSampleEntity> createCacheStatisticSampleEntity(Set<String> cacheManagerNames, Set<String> cacheNames, Set<String> sampleNames) {
        CacheStatisticSampleEntityBuilder builder = CacheStatisticSampleEntityBuilder.createWith(sampleNames);
        String requestClusterUUID = this.remoteAgentEndpoint.getRequestClusterUUID();
        this.cacheManagerSamplerRepoLock.readLock().lock();
        ArrayList<Object> disabledSamplerRepoEntries = new ArrayList<Object>();
        try {
            if (cacheManagerNames == null) {
                for (Map.Entry entry : this.cacheManagerSamplerRepo.entrySet()) {
                    if (!((SamplerRepoEntry)entry.getValue()).isConnectedToCluster(requestClusterUUID)) continue;
                    DfltSamplerRepositoryService.enableNonStopFor((SamplerRepoEntry)entry.getValue(), false);
                    disabledSamplerRepoEntries.add(entry.getValue());
                    for (CacheSampler sampler : ((SamplerRepoEntry)entry.getValue()).getComprehensiveCacheSamplers(cacheNames)) {
                        builder.add(sampler, (String)entry.getKey());
                    }
                }
            } else {
                for (String string : cacheManagerNames) {
                    SamplerRepoEntry samplerRepoEntry = this.cacheManagerSamplerRepo.get(string);
                    if (samplerRepoEntry == null || !samplerRepoEntry.isConnectedToCluster(requestClusterUUID)) continue;
                    DfltSamplerRepositoryService.enableNonStopFor(samplerRepoEntry, false);
                    disabledSamplerRepoEntries.add(samplerRepoEntry);
                    for (CacheSampler sampler : samplerRepoEntry.getComprehensiveCacheSamplers(cacheNames)) {
                        builder.add(sampler, string);
                    }
                }
            }
            Collection<CacheStatisticSampleEntity> collection = builder.build();
            return collection;
        }
        finally {
            for (SamplerRepoEntry samplerRepoEntry : disabledSamplerRepoEntries) {
                DfltSamplerRepositoryService.enableNonStopFor(samplerRepoEntry, true);
            }
            this.cacheManagerSamplerRepoLock.readLock().unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void createOrUpdateCache(String cacheManagerName, String cacheName, CacheEntity resource) throws ServiceExecutionException {
        block4: {
            this.cacheManagerSamplerRepoLock.readLock().lock();
            try {
                SamplerRepoEntry entry = this.cacheManagerSamplerRepo.get(cacheManagerName);
                if (entry != null) {
                    entry.updateCache(cacheName, resource);
                    break block4;
                }
                throw new ServiceExecutionException("CacheManager not found !");
            }
            finally {
                this.cacheManagerSamplerRepoLock.readLock().unlock();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void clearCache(String cacheManagerName, String cacheName) {
        this.cacheManagerSamplerRepoLock.readLock().lock();
        SamplerRepoEntry entry = this.cacheManagerSamplerRepo.get(cacheManagerName);
        try {
            DfltSamplerRepositoryService.enableNonStopFor(entry, false);
            if (entry != null) {
                entry.clearCache(cacheName);
            }
        }
        finally {
            DfltSamplerRepositoryService.enableNonStopFor(entry, true);
            this.cacheManagerSamplerRepoLock.readLock().unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void updateCacheManager(String cacheManagerName, CacheManagerEntity resource) throws ServiceExecutionException {
        block6: {
            this.cacheManagerSamplerRepoLock.writeLock().lock();
            try {
                SamplerRepoEntry entry = this.cacheManagerSamplerRepo.get(cacheManagerName);
                if (entry != null) {
                    Object mblhsAttr;
                    CacheManagerSampler cms = entry.getCacheManagerSampler();
                    this.checkForInvalidAttributes(cacheManagerName, resource);
                    Object mbldsAttr = resource.getAttributes().get("MaxBytesLocalDiskAsString");
                    if (mbldsAttr != null) {
                        cms.setMaxBytesLocalDiskAsString(mbldsAttr.toString());
                    }
                    if ((mblhsAttr = resource.getAttributes().get("MaxBytesLocalHeapAsString")) != null) {
                        cms.setMaxBytesLocalHeapAsString(mblhsAttr.toString());
                    }
                    break block6;
                }
                throw new ServiceExecutionException("CacheManager not found !");
            }
            finally {
                this.cacheManagerSamplerRepoLock.writeLock().unlock();
            }
        }
    }

    @Override
    public Collection<QueryResultsEntity> executeQuery(String cacheManagerName, String queryString) throws ServiceExecutionException {
        this.cacheManagerSamplerRepoLock.writeLock().lock();
        try {
            SamplerRepoEntry entry = this.cacheManagerSamplerRepo.get(cacheManagerName);
            if (entry != null) {
                try {
                    DfltSamplerRepositoryService.enableNonStopFor(entry, false);
                    CacheManagerSampler cms = entry.getCacheManagerSampler();
                    Collection<QueryResultsEntity> collection = this.buildQueryResultsEntity(cacheManagerName, cms.executeQuery(queryString));
                    return collection;
                }
                catch (Exception e) {
                    Throwable t = ExceptionUtils.getRootCause(e);
                    throw new ServiceExecutionException(t.getMessage());
                }
                finally {
                    DfltSamplerRepositoryService.enableNonStopFor(entry, true);
                }
            }
            throw new ServiceExecutionException("CacheManager not found !");
        }
        finally {
            this.cacheManagerSamplerRepoLock.writeLock().unlock();
        }
    }

    private Collection<QueryResultsEntity> buildQueryResultsEntity(String cacheManagerName, Object[][] data) {
        QueryResultsEntity qre = new QueryResultsEntity();
        qre.setAgentId("embedded");
        qre.setVersion(this.getClass().getPackage().getImplementationVersion());
        qre.setName(cacheManagerName);
        qre.setData(data);
        return Collections.singleton(qre);
    }

    private void checkForInvalidAttributes(String cacheManagerName, CacheManagerEntity resource) throws ServiceExecutionException {
        boolean invalidAttributesFound = false;
        StringBuilder errorMessage = new StringBuilder("You are not allowed to update those attributes : ");
        if (resource.getName() != null && !resource.getName().equals(cacheManagerName)) {
            errorMessage.append("name ");
            invalidAttributesFound = true;
        }
        for (Map.Entry<String, Object> attribute : resource.getAttributes().entrySet()) {
            String key = attribute.getKey();
            if (key.equals("MaxBytesLocalDiskAsString") || key.equals("MaxBytesLocalHeapAsString")) continue;
            errorMessage.append(key).append(" ");
            invalidAttributesFound = true;
        }
        if (invalidAttributesFound) {
            errorMessage.append(". Only ").append("MaxBytesLocalDiskAsString").append(" and ").append("MaxBytesLocalHeapAsString").append(" can be updated for a CacheManager.");
            throw new ServiceExecutionException(errorMessage.toString());
        }
    }

    private static void checkForInvalidAttributes(String cacheName, CacheEntity resource) throws ServiceExecutionException {
        boolean invalidAttributesFound = false;
        StringBuilder errorMessage = new StringBuilder("You are not allowed to update those attributes : ");
        if (resource.getName() != null && !resource.getName().equals(cacheName)) {
            errorMessage.append("name ");
            invalidAttributesFound = true;
        }
        HashSet<String> validAttributes = new HashSet<String>();
        validAttributes.add("Enabled");
        validAttributes.add("NodeBulkLoadEnabled");
        validAttributes.add("MaxElementsOnDisk");
        validAttributes.add("LoggingEnabled");
        validAttributes.add("MaxBytesLocalDiskAsString");
        validAttributes.add("MaxBytesLocalHeapAsString");
        validAttributes.add("MaxEntriesLocalHeap");
        validAttributes.add("MaxEntriesInCache");
        validAttributes.add("TimeToIdleSeconds");
        validAttributes.add("TimeToLiveSeconds");
        for (Map.Entry<String, Object> attribute : resource.getAttributes().entrySet()) {
            String key = attribute.getKey();
            if (validAttributes.contains(key)) continue;
            errorMessage.append(key).append(" ");
            invalidAttributesFound = true;
        }
        if (invalidAttributesFound) {
            errorMessage.append(". Only ");
            for (String validAttribute : validAttributes) {
                errorMessage.append(validAttribute).append(" ");
            }
            errorMessage.append("can be updated for a Cache.");
            throw new ServiceExecutionException(errorMessage.toString());
        }
    }

    @Override
    public Collection<AgentEntity> getAgents(Set<String> ids) throws ServiceExecutionException {
        if (ids.isEmpty()) {
            return Collections.singleton(this.buildAgentEntity());
        }
        ArrayList<AgentEntity> result = new ArrayList<AgentEntity>();
        for (String id : ids) {
            if (!id.equals("embedded")) {
                throw new ServiceExecutionException("Unknown agent ID : " + id);
            }
            result.add(this.buildAgentEntity());
        }
        return result;
    }

    private AgentEntity buildAgentEntity() {
        AgentEntity e = new AgentEntity();
        e.setAgentId("embedded");
        e.setVersion(this.getClass().getPackage().getImplementationVersion());
        e.setAgencyOf(AGENCY);
        StringBuilder sb = new StringBuilder();
        for (String cmName : this.cacheManagerSamplerRepo.keySet()) {
            sb.append(cmName).append(",");
        }
        if (sb.indexOf(",") > -1) {
            sb.deleteCharAt(sb.length() - 1);
        }
        e.getRootRepresentables().put("cacheManagerNames", sb.toString());
        return e;
    }

    @Override
    public Collection<AgentMetadataEntity> getAgentsMetadata(Set<String> ids) throws ServiceExecutionException {
        if (ids.isEmpty()) {
            return Collections.singleton(this.buildAgentMetadata());
        }
        ArrayList<AgentMetadataEntity> result = new ArrayList<AgentMetadataEntity>();
        for (String id : ids) {
            if (!id.equals("embedded")) {
                throw new ServiceExecutionException("Unknown agent ID : " + id);
            }
            result.add(this.buildAgentMetadata());
        }
        return result;
    }

    private AgentMetadataEntity buildAgentMetadata() {
        AgentMetadataEntity ame = new AgentMetadataEntity();
        ame.setAgentId("embedded");
        ame.setAgencyOf(AGENCY);
        ame.setVersion(this.getClass().getPackage().getImplementationVersion());
        ame.setRestAPIVersion("v1");
        ame.setAvailable(true);
        if (this.isTsaBridged()) {
            ame.setSecured(this.isTsaSecured());
            ame.setSslEnabled(this.isTsaSecured());
            ame.setNeedClientAuth(false);
        } else {
            ame.setSecured(Utils.trimToNull(this.configuration.getSecurityServiceLocation()) != null);
            ame.setSslEnabled(Utils.trimToNull(this.configuration.getSecurityServiceLocation()) != null);
            ame.setNeedClientAuth(this.configuration.isNeedClientAuth());
        }
        ame.setLicensed(ServiceLocator.locate(LicenseService.class).isLicensed());
        ame.setSampleHistorySize(this.configuration.getSampleHistorySize());
        ame.setSampleIntervalSeconds(this.configuration.getSampleIntervalSeconds());
        ame.setEnabled(this.configuration.isEnabled());
        return ame;
    }

    protected boolean isTsaBridged() {
        return this.remoteAgentEndpoint.isTsaBridged();
    }

    protected boolean isTsaSecured() {
        return this.remoteAgentEndpoint.isTsaSecured();
    }

    private static final class SamplerRepoEntry
    implements CacheManagerEventListener {
        private static final String ENABLED_ATTR = "Enabled";
        private static final String BULK_LOAD_ENABLED = "NodeBulkLoadEnabled";
        private static final String MAX_ELEMENTS_ON_DISK = "MaxElementsOnDisk";
        private static final String MAX_BYTES_LOCAL_DISK = "MaxBytesLocalDisk";
        private static final String MAX_BYTES_LOCAL_DISK_STRING = "MaxBytesLocalDiskAsString";
        private static final String MAX_BYTES_LOCAL_HEAP = "MaxBytesLocalHeap";
        private static final String MAX_BYTES_LOCAL_HEAP_STRING = "MaxBytesLocalHeapAsString";
        private static final String LOGGING_ENABLED = "LoggingEnabled";
        private static final String TIME_TO_IDLE_SEC = "TimeToIdleSeconds";
        private static final String TIME_TO_LIVE_SEC = "TimeToLiveSeconds";
        private static final String MAX_ENTRIES_LOCAL_HEAP = "MaxEntriesLocalHeap";
        private static final String MAX_ENTRIES_IN_CACHE = "MaxEntriesInCache";
        private CacheManager cacheManager;
        private CacheManagerSampler cacheManagerSampler;
        private Map<String, CacheSampler> cacheSamplersByName;
        private volatile Status status = Status.STATUS_UNINITIALISED;
        private final ReadWriteLock cacheSamplerMapLock = new ReentrantReadWriteLock();

        public SamplerRepoEntry(CacheManager cacheManager) {
            if (cacheManager == null) {
                throw new IllegalArgumentException("cacheManager == null");
            }
            this.cacheManagerSampler = new CacheManagerSamplerImpl(cacheManager);
            this.cacheManager = cacheManager;
            String[] cNames = cacheManager.getCacheNames();
            this.cacheSamplersByName = new HashMap<String, CacheSampler>(cNames.length);
            for (String cName : cNames) {
                this.cacheSamplersByName.put(cName, (CacheSampler)new CacheSamplerImpl(cacheManager.getEhcache(cName)));
            }
        }

        public boolean isConnectedToCluster(String uuid) {
            return uuid == null || this.cacheManager.getClusterUUID().equals("") || this.cacheManager.getClusterUUID().equals(uuid);
        }

        public CacheManagerSampler getCacheManagerSampler() {
            return this.cacheManagerSampler;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public Collection<CacheSampler> getComprehensiveCacheSamplers(Set<String> cacheSamplerNames) {
            HashSet<CacheSampler> samplers = new HashSet<CacheSampler>();
            this.cacheSamplerMapLock.readLock().lock();
            try {
                if (cacheSamplerNames == null) {
                    for (CacheSampler cs : this.cacheSamplersByName.values()) {
                        samplers.add(cs);
                    }
                } else {
                    for (String cName : cacheSamplerNames) {
                        CacheSampler cs = this.cacheSamplersByName.get(cName);
                        if (cs == null) continue;
                        samplers.add(cs);
                    }
                }
            }
            finally {
                this.cacheSamplerMapLock.readLock().unlock();
            }
            return samplers;
        }

        public void clearCache(String cacheSamplerName) {
            this.cacheSamplerMapLock.writeLock().lock();
            try {
                CacheSampler cs = this.cacheSamplersByName.get(cacheSamplerName);
                if (cs != null) {
                    cs.removeAll();
                }
            }
            finally {
                this.cacheSamplerMapLock.writeLock().unlock();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public void updateCache(String cacheSamplerName, CacheEntity cacheResource) throws ServiceExecutionException {
            block16: {
                this.cacheSamplerMapLock.writeLock().lock();
                try {
                    CacheSampler cs = this.cacheSamplersByName.get(cacheSamplerName);
                    if (cs != null) {
                        try {
                            Object ttlAttr;
                            Object ttiAttr;
                            Integer meicAttr;
                            Integer melhAttr;
                            Object mblhsAttr;
                            Object mbldsAttr;
                            Boolean loggingEnabledAttr;
                            Integer maxElementsOnDiskAttr;
                            Boolean enabledBlkLoad;
                            DfltSamplerRepositoryService.checkForInvalidAttributes(cacheSamplerName, cacheResource);
                            Boolean enabledAttr = (Boolean)cacheResource.getAttributes().get(ENABLED_ATTR);
                            if (enabledAttr != null) {
                                cs.setEnabled(enabledAttr.booleanValue());
                            }
                            if ((enabledBlkLoad = (Boolean)cacheResource.getAttributes().get(BULK_LOAD_ENABLED)) != null) {
                                cs.setNodeBulkLoadEnabled(enabledBlkLoad.booleanValue());
                            }
                            if ((maxElementsOnDiskAttr = (Integer)cacheResource.getAttributes().get(MAX_ELEMENTS_ON_DISK)) != null) {
                                cs.setMaxElementsOnDisk(maxElementsOnDiskAttr.intValue());
                            }
                            if ((loggingEnabledAttr = (Boolean)cacheResource.getAttributes().get(LOGGING_ENABLED)) != null) {
                                cs.setLoggingEnabled(loggingEnabledAttr.booleanValue());
                            }
                            if ((mbldsAttr = cacheResource.getAttributes().get(MAX_BYTES_LOCAL_DISK_STRING)) != null) {
                                cs.setMaxBytesLocalDiskAsString(mbldsAttr.toString());
                            }
                            if ((mblhsAttr = cacheResource.getAttributes().get(MAX_BYTES_LOCAL_HEAP_STRING)) != null) {
                                cs.setMaxBytesLocalHeapAsString(mblhsAttr.toString());
                            }
                            if ((melhAttr = (Integer)cacheResource.getAttributes().get(MAX_ENTRIES_LOCAL_HEAP)) != null) {
                                cs.setMaxEntriesLocalHeap((long)melhAttr.intValue());
                            }
                            if ((meicAttr = (Integer)cacheResource.getAttributes().get(MAX_ENTRIES_IN_CACHE)) != null) {
                                cs.setMaxEntriesInCache((long)meicAttr.intValue());
                            }
                            if ((ttiAttr = cacheResource.getAttributes().get(TIME_TO_IDLE_SEC)) != null) {
                                cs.setTimeToIdleSeconds(Long.parseLong(ttiAttr.toString()));
                            }
                            if ((ttlAttr = cacheResource.getAttributes().get(TIME_TO_LIVE_SEC)) != null) {
                                cs.setTimeToLiveSeconds(Long.parseLong(ttlAttr.toString()));
                            }
                            break block16;
                        }
                        catch (RuntimeException e) {
                            throw new ServiceExecutionException(e);
                        }
                    }
                    throw new ServiceExecutionException("Cache not found !");
                }
                finally {
                    this.cacheSamplerMapLock.writeLock().unlock();
                }
            }
        }

        public void init() throws CacheException {
            this.status = Status.STATUS_ALIVE;
        }

        public Status getStatus() {
            return this.status;
        }

        public void dispose() throws CacheException {
            this.cacheSamplerMapLock.writeLock().lock();
            try {
                this.cacheSamplersByName.clear();
                this.cacheSamplersByName = null;
            }
            finally {
                this.cacheSamplerMapLock.writeLock().unlock();
            }
            this.status = Status.STATUS_SHUTDOWN;
        }

        public void notifyCacheAdded(String cacheName) {
            this.cacheSamplerMapLock.writeLock().lock();
            try {
                Ehcache c = this.cacheManager.getEhcache(cacheName);
                if (c != null) {
                    this.cacheSamplersByName.put(cacheName, (CacheSampler)new CacheSamplerImpl(c));
                }
            }
            finally {
                this.cacheSamplerMapLock.writeLock().unlock();
            }
        }

        public void notifyCacheRemoved(String cacheName) {
            this.cacheSamplerMapLock.writeLock().lock();
            try {
                this.cacheSamplersByName.remove(cacheName);
            }
            finally {
                this.cacheSamplerMapLock.writeLock().unlock();
            }
        }

        public void destroy() {
            this.cacheManagerSampler = null;
            this.cacheManager = null;
        }
    }
}

