/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.ehcache.CacheException
 *  net.sf.ehcache.CacheManager
 *  net.sf.ehcache.Ehcache
 *  net.sf.ehcache.Status
 *  net.sf.ehcache.config.CacheConfiguration
 *  net.sf.ehcache.config.CacheConfigurationListener
 *  net.sf.ehcache.config.ManagementRESTServiceConfiguration
 *  net.sf.ehcache.event.CacheManagerEventListener
 *  net.sf.ehcache.management.sampled.CacheManagerSampler
 *  net.sf.ehcache.management.sampled.CacheManagerSamplerImpl
 *  net.sf.ehcache.management.sampled.CacheSampler
 *  net.sf.ehcache.management.sampled.CacheSamplerImpl
 *  net.sf.ehcache.terracotta.ClusteredInstanceFactory
 */
package net.sf.ehcache.management.service.impl;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.ClusteredInstanceFactoryAccessor;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Status;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.CacheConfigurationListener;
import net.sf.ehcache.config.ManagementRESTServiceConfiguration;
import net.sf.ehcache.event.CacheManagerEventListener;
import net.sf.ehcache.management.resource.CacheConfigEntityV2;
import net.sf.ehcache.management.resource.CacheEntityV2;
import net.sf.ehcache.management.resource.CacheManagerConfigEntityV2;
import net.sf.ehcache.management.resource.CacheManagerEntityV2;
import net.sf.ehcache.management.resource.CacheStatisticSampleEntityV2;
import net.sf.ehcache.management.resource.QueryResultsEntityV2;
import net.sf.ehcache.management.sampled.CacheManagerSampler;
import net.sf.ehcache.management.sampled.CacheManagerSamplerImpl;
import net.sf.ehcache.management.sampled.CacheSampler;
import net.sf.ehcache.management.sampled.CacheSamplerImpl;
import net.sf.ehcache.management.service.CacheManagerServiceV2;
import net.sf.ehcache.management.service.CacheServiceV2;
import net.sf.ehcache.management.service.EntityResourceFactoryV2;
import net.sf.ehcache.management.service.SamplerRepositoryServiceV2;
import net.sf.ehcache.management.service.impl.CacheConfigurationEntityBuilderV2;
import net.sf.ehcache.management.service.impl.CacheEntityBuilderV2;
import net.sf.ehcache.management.service.impl.CacheManagerConfigurationEntityBuilderV2;
import net.sf.ehcache.management.service.impl.CacheManagerEntityBuilderV2;
import net.sf.ehcache.management.service.impl.CacheStatisticSampleEntityBuilderV2;
import net.sf.ehcache.management.service.impl.RemoteAgentEndpointImpl;
import net.sf.ehcache.terracotta.ClusteredInstanceFactory;
import org.terracotta.management.ServiceExecutionException;
import org.terracotta.management.ServiceLocator;
import org.terracotta.management.resource.AgentEntityV2;
import org.terracotta.management.resource.AgentMetadataEntityV2;
import org.terracotta.management.resource.ResponseEntityV2;
import org.terracotta.management.resource.events.EventEntityV2;
import org.terracotta.management.resource.exceptions.ExceptionUtils;
import org.terracotta.management.resource.services.AgentServiceV2;
import org.terracotta.management.resource.services.LicenseService;
import org.terracotta.management.resource.services.Utils;
import org.terracotta.management.resource.services.events.EventServiceV2;

public class DfltSamplerRepositoryServiceV2
implements SamplerRepositoryServiceV2,
EntityResourceFactoryV2,
CacheManagerServiceV2,
CacheServiceV2,
AgentServiceV2,
EventServiceV2 {
    public static final String AGENCY = "Ehcache";
    final Set<EventServiceV2.EventListener> listeners = new CopyOnWriteArraySet<EventServiceV2.EventListener>();
    private final Map<String, SamplerRepoEntry> cacheManagerSamplerRepo = new HashMap<String, SamplerRepoEntry>();
    private final ReadWriteLock cacheManagerSamplerRepoLock = new ReentrantReadWriteLock();
    protected final ManagementRESTServiceConfiguration configuration;
    private final Map<String, PropertyChangeListener> configurationChangeListenerMap = new HashMap<String, PropertyChangeListener>();
    private final RemoteAgentEndpointImpl remoteAgentEndpoint;

    public DfltSamplerRepositoryServiceV2(ManagementRESTServiceConfiguration configuration, RemoteAgentEndpointImpl remoteAgentEndpoint) {
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
        String[] cacheNames;
        String name = cacheManager.getName();
        this.cacheManagerSamplerRepoLock.writeLock().lock();
        try {
            if (!this.cacheManagerSamplerRepo.containsKey(name)) {
                SamplerRepoEntry entry = new SamplerRepoEntry(cacheManager);
                cacheManager.setCacheManagerEventListener((CacheManagerEventListener)entry);
                this.cacheManagerSamplerRepo.put(name, entry);
            }
        }
        finally {
            this.cacheManagerSamplerRepoLock.writeLock().unlock();
        }
        ArrayList cacheEntities = new ArrayList();
        for (String cacheName : cacheNames = cacheManager.getCacheNames()) {
            HashMap<String, Object> cacheAttributes = new HashMap<String, Object>();
            cacheAttributes.put("version", this.getClass().getPackage().getImplementationVersion());
            cacheAttributes.put("agentId", "embedded");
            cacheAttributes.put("name", cacheName);
            Collection<CacheEntityV2> createCacheEntities = this.createCacheEntities(Collections.singleton(name), Collections.singleton(cacheName), null).getEntities();
            if (createCacheEntities != null && !createCacheEntities.isEmpty()) {
                cacheAttributes.put("attributes", createCacheEntities.iterator().next().getAttributes());
            }
            cacheEntities.add(cacheAttributes);
        }
        EventEntityV2 eventEntityV2 = new EventEntityV2();
        ResponseEntityV2<CacheManagerEntityV2> responseEntity = this.createCacheManagerEntities(Collections.singleton(name), null);
        CacheManagerEntityV2 cacheManagerEntity = responseEntity.getEntities().iterator().next();
        eventEntityV2.setAgentId("embedded");
        eventEntityV2.setType("EHCACHE.CACHEMANAGER.ADDED");
        eventEntityV2.getRootRepresentables().put("attributes", cacheManagerEntity.getAttributes());
        eventEntityV2.getRootRepresentables().put("caches", cacheEntities);
        eventEntityV2.getRootRepresentables().put("cacheManagerName", name);
        cacheManager.sendManagementEvent((Serializable)eventEntityV2, eventEntityV2.getType());
        for (EventServiceV2.EventListener eventListener : this.listeners) {
            eventListener.onEvent(eventEntityV2);
        }
        ConfigurationPropertyChangeListener pcl = new ConfigurationPropertyChangeListener(cacheManager);
        cacheManager.getConfiguration().addPropertyChangeListener((PropertyChangeListener)pcl);
        this.configurationChangeListenerMap.put(cacheManager.getName(), pcl);
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
        EventEntityV2 eventEntityV2 = new EventEntityV2();
        eventEntityV2.setAgentId("embedded");
        eventEntityV2.getRootRepresentables().put("cacheManagerName", cacheManager.getName());
        eventEntityV2.getRootRepresentables().put("ClusterUUID", cacheManager.getClusterUUID());
        eventEntityV2.setType("EHCACHE.CACHEMANAGER.REMOVED");
        cacheManager.sendManagementEvent((Serializable)eventEntityV2, eventEntityV2.getType());
        for (EventServiceV2.EventListener eventListener : this.listeners) {
            eventListener.onEvent(eventEntityV2);
        }
        String cacheManagerName = cacheManager.getName();
        cacheManager.getConfiguration().removePropertyChangeListener(this.configurationChangeListenerMap.get(cacheManagerName));
        this.configurationChangeListenerMap.remove(cacheManagerName);
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
    public ResponseEntityV2<CacheManagerEntityV2> createCacheManagerEntities(Set<String> cacheManagerNames, Set<String> attributes) {
        Collection<Object> entities;
        ResponseEntityV2<CacheManagerEntityV2> responseEntityV2 = new ResponseEntityV2<CacheManagerEntityV2>();
        String requestClusterUUID = this.remoteAgentEndpoint.getRequestClusterUUID();
        CacheManagerEntityBuilderV2 builder = null;
        this.cacheManagerSamplerRepoLock.readLock().lock();
        try {
            if (cacheManagerNames == null) {
                for (SamplerRepoEntry entry : this.cacheManagerSamplerRepo.values()) {
                    if (!entry.isConnectedToCluster(requestClusterUUID)) continue;
                    builder = builder == null ? CacheManagerEntityBuilderV2.createWith(entry.getCacheManagerSampler()) : builder.add(entry.getCacheManagerSampler());
                }
            } else {
                for (String cmName : cacheManagerNames) {
                    SamplerRepoEntry entry = this.cacheManagerSamplerRepo.get(cmName);
                    if (entry == null || !entry.isConnectedToCluster(requestClusterUUID)) continue;
                    builder = builder == null ? CacheManagerEntityBuilderV2.createWith(entry.getCacheManagerSampler()) : builder.add(entry.getCacheManagerSampler());
                }
            }
            entities = builder == null ? new HashSet(0) : (attributes == null ? builder.build() : builder.add(attributes).build());
        }
        finally {
            this.cacheManagerSamplerRepoLock.readLock().unlock();
        }
        responseEntityV2.getEntities().addAll(entities);
        return responseEntityV2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ResponseEntityV2<CacheManagerConfigEntityV2> createCacheManagerConfigEntities(Set<String> cacheManagerNames) {
        Collection<CacheManagerConfigEntityV2> entities;
        ResponseEntityV2<CacheManagerConfigEntityV2> responseEntityV2 = new ResponseEntityV2<CacheManagerConfigEntityV2>();
        CacheManagerConfigurationEntityBuilderV2 builder = null;
        String requestClusterUUID = this.remoteAgentEndpoint.getRequestClusterUUID();
        this.cacheManagerSamplerRepoLock.readLock().lock();
        try {
            if (cacheManagerNames == null) {
                for (SamplerRepoEntry entry : this.cacheManagerSamplerRepo.values()) {
                    if (!entry.isConnectedToCluster(requestClusterUUID)) continue;
                    builder = builder == null ? CacheManagerConfigurationEntityBuilderV2.createWith(entry.getCacheManagerSampler()) : builder.add(entry.getCacheManagerSampler());
                }
            } else {
                for (String cmName : cacheManagerNames) {
                    SamplerRepoEntry entry = this.cacheManagerSamplerRepo.get(cmName);
                    if (entry == null || !entry.isConnectedToCluster(requestClusterUUID)) continue;
                    builder = builder == null ? CacheManagerConfigurationEntityBuilderV2.createWith(entry.getCacheManagerSampler()) : builder.add(entry.getCacheManagerSampler());
                }
            }
            entities = builder == null ? new HashSet<CacheManagerConfigEntityV2>(0) : builder.build();
        }
        finally {
            this.cacheManagerSamplerRepoLock.readLock().unlock();
        }
        responseEntityV2.getEntities().addAll(entities);
        return responseEntityV2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ResponseEntityV2<CacheEntityV2> createCacheEntities(Set<String> cacheManagerNames, Set<String> cacheNames, Set<String> attributes) {
        Collection<Object> entities;
        ResponseEntityV2<CacheEntityV2> responseEntityV2 = new ResponseEntityV2<CacheEntityV2>();
        CacheEntityBuilderV2 builder = null;
        String requestClusterUUID = this.remoteAgentEndpoint.getRequestClusterUUID();
        this.cacheManagerSamplerRepoLock.readLock().lock();
        ArrayList<Object> disabledSamplerRepoEntries = new ArrayList<Object>();
        try {
            if (cacheManagerNames == null) {
                for (Map.Entry entry : this.cacheManagerSamplerRepo.entrySet()) {
                    if (!((SamplerRepoEntry)entry.getValue()).isConnectedToCluster(requestClusterUUID)) continue;
                    DfltSamplerRepositoryServiceV2.enableNonStopFor((SamplerRepoEntry)entry.getValue(), false);
                    disabledSamplerRepoEntries.add(entry.getValue());
                    for (CacheSampler sampler : ((SamplerRepoEntry)entry.getValue()).getComprehensiveCacheSamplers(cacheNames)) {
                        builder = builder == null ? CacheEntityBuilderV2.createWith(sampler, (String)entry.getKey()) : builder.add(sampler, (String)entry.getKey());
                    }
                }
            } else {
                for (String string : cacheManagerNames) {
                    SamplerRepoEntry entry = this.cacheManagerSamplerRepo.get(string);
                    if (entry == null || !entry.isConnectedToCluster(requestClusterUUID)) continue;
                    DfltSamplerRepositoryServiceV2.enableNonStopFor(entry, false);
                    disabledSamplerRepoEntries.add(entry);
                    for (CacheSampler sampler : entry.getComprehensiveCacheSamplers(cacheNames)) {
                        builder = builder == null ? CacheEntityBuilderV2.createWith(sampler, string) : builder.add(sampler, string);
                    }
                }
            }
            entities = builder == null ? new HashSet(0) : (attributes == null ? builder.build() : builder.add(attributes).build());
        }
        finally {
            for (SamplerRepoEntry samplerRepoEntry : disabledSamplerRepoEntries) {
                DfltSamplerRepositoryServiceV2.enableNonStopFor(samplerRepoEntry, true);
            }
            this.cacheManagerSamplerRepoLock.readLock().unlock();
        }
        responseEntityV2.getEntities().addAll(entities);
        return responseEntityV2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ResponseEntityV2<CacheConfigEntityV2> createCacheConfigEntities(Set<String> cacheManagerNames, Set<String> cacheNames) {
        Collection<CacheConfigEntityV2> entities;
        CacheConfigurationEntityBuilderV2 builder = null;
        ResponseEntityV2<CacheConfigEntityV2> responseEntityV2 = new ResponseEntityV2<CacheConfigEntityV2>();
        String requestClusterUUID = this.remoteAgentEndpoint.getRequestClusterUUID();
        this.cacheManagerSamplerRepoLock.readLock().lock();
        try {
            if (cacheManagerNames == null) {
                for (Map.Entry<String, SamplerRepoEntry> entry : this.cacheManagerSamplerRepo.entrySet()) {
                    for (CacheSampler sampler : entry.getValue().getComprehensiveCacheSamplers(cacheNames)) {
                        if (!entry.getValue().isConnectedToCluster(requestClusterUUID)) continue;
                        builder = builder == null ? CacheConfigurationEntityBuilderV2.createWith(entry.getValue().getCacheManagerSampler(), sampler.getCacheName()) : builder.add(entry.getValue().getCacheManagerSampler(), sampler.getCacheName());
                    }
                }
            } else {
                for (String cmName : cacheManagerNames) {
                    SamplerRepoEntry entry = this.cacheManagerSamplerRepo.get(cmName);
                    if (entry == null || !entry.isConnectedToCluster(requestClusterUUID)) continue;
                    for (CacheSampler sampler : entry.getComprehensiveCacheSamplers(cacheNames)) {
                        builder = builder == null ? CacheConfigurationEntityBuilderV2.createWith(entry.getCacheManagerSampler(), sampler.getCacheName()) : builder.add(entry.getCacheManagerSampler(), sampler.getCacheName());
                    }
                }
            }
            entities = builder == null ? new HashSet<CacheConfigEntityV2>(0) : builder.build();
        }
        finally {
            this.cacheManagerSamplerRepoLock.readLock().unlock();
        }
        responseEntityV2.getEntities().addAll(entities);
        return responseEntityV2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ResponseEntityV2<CacheStatisticSampleEntityV2> createCacheStatisticSampleEntity(Set<String> cacheManagerNames, Set<String> cacheNames, Set<String> sampleNames) {
        CacheStatisticSampleEntityBuilderV2 builder = CacheStatisticSampleEntityBuilderV2.createWith(sampleNames);
        ResponseEntityV2 responseEntityV2 = new ResponseEntityV2();
        String requestClusterUUID = this.remoteAgentEndpoint.getRequestClusterUUID();
        this.cacheManagerSamplerRepoLock.readLock().lock();
        ArrayList<Object> disabledSamplerRepoEntries = new ArrayList<Object>();
        try {
            if (cacheManagerNames == null) {
                for (Map.Entry entry : this.cacheManagerSamplerRepo.entrySet()) {
                    if (!((SamplerRepoEntry)entry.getValue()).isConnectedToCluster(requestClusterUUID)) continue;
                    DfltSamplerRepositoryServiceV2.enableNonStopFor((SamplerRepoEntry)entry.getValue(), false);
                    disabledSamplerRepoEntries.add(entry.getValue());
                    for (CacheSampler sampler : ((SamplerRepoEntry)entry.getValue()).getComprehensiveCacheSamplers(cacheNames)) {
                        builder.add(sampler, (String)entry.getKey());
                    }
                }
            } else {
                for (String string : cacheManagerNames) {
                    SamplerRepoEntry samplerRepoEntry = this.cacheManagerSamplerRepo.get(string);
                    if (samplerRepoEntry == null || !samplerRepoEntry.isConnectedToCluster(requestClusterUUID)) continue;
                    DfltSamplerRepositoryServiceV2.enableNonStopFor(samplerRepoEntry, false);
                    disabledSamplerRepoEntries.add(samplerRepoEntry);
                    for (CacheSampler sampler : samplerRepoEntry.getComprehensiveCacheSamplers(cacheNames)) {
                        builder.add(sampler, string);
                    }
                }
            }
            responseEntityV2.getEntities().addAll(builder.build());
            ResponseEntityV2 responseEntityV22 = responseEntityV2;
            return responseEntityV22;
        }
        finally {
            for (SamplerRepoEntry samplerRepoEntry : disabledSamplerRepoEntries) {
                DfltSamplerRepositoryServiceV2.enableNonStopFor(samplerRepoEntry, true);
            }
            this.cacheManagerSamplerRepoLock.readLock().unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void createOrUpdateCache(String cacheManagerName, String cacheName, CacheEntityV2 resource) throws ServiceExecutionException {
        block4: {
            this.cacheManagerSamplerRepoLock.readLock().lock();
            SamplerRepoEntry entry = this.cacheManagerSamplerRepo.get(cacheManagerName);
            try {
                DfltSamplerRepositoryServiceV2.enableNonStopFor(entry, false);
                if (entry != null) {
                    entry.updateCache(cacheName, resource);
                    break block4;
                }
                throw new ServiceExecutionException("CacheManager not found !");
            }
            finally {
                DfltSamplerRepositoryServiceV2.enableNonStopFor(entry, true);
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
            DfltSamplerRepositoryServiceV2.enableNonStopFor(entry, false);
            if (entry != null) {
                entry.clearCache(cacheName);
            }
        }
        finally {
            DfltSamplerRepositoryServiceV2.enableNonStopFor(entry, true);
            this.cacheManagerSamplerRepoLock.readLock().unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void updateCacheManager(String cacheManagerName, CacheManagerEntityV2 resource) throws ServiceExecutionException {
        block7: {
            this.cacheManagerSamplerRepoLock.writeLock().lock();
            try {
                SamplerRepoEntry entry = this.cacheManagerSamplerRepo.get(cacheManagerName);
                if (entry != null) {
                    Object enabledAttr;
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
                    if ((enabledAttr = resource.getAttributes().get("Enabled")) != null) {
                        cms.setEnabled(Boolean.valueOf(enabledAttr.toString()).booleanValue());
                    }
                    break block7;
                }
                throw new ServiceExecutionException("CacheManager not found !");
            }
            finally {
                this.cacheManagerSamplerRepoLock.writeLock().unlock();
            }
        }
    }

    @Override
    public ResponseEntityV2<QueryResultsEntityV2> executeQuery(String cacheManagerName, String queryString) throws ServiceExecutionException {
        this.cacheManagerSamplerRepoLock.writeLock().lock();
        ResponseEntityV2<QueryResultsEntityV2> responseEntityV2 = new ResponseEntityV2<QueryResultsEntityV2>();
        try {
            SamplerRepoEntry entry = this.cacheManagerSamplerRepo.get(cacheManagerName);
            if (entry != null) {
                try {
                    DfltSamplerRepositoryServiceV2.enableNonStopFor(entry, false);
                    CacheManagerSampler cms = entry.getCacheManagerSampler();
                    responseEntityV2.getEntities().addAll(this.buildQueryResultsEntity(cacheManagerName, cms.executeQuery(queryString)));
                    ResponseEntityV2<QueryResultsEntityV2> responseEntityV22 = responseEntityV2;
                    return responseEntityV22;
                }
                catch (Exception e) {
                    Throwable t = ExceptionUtils.getRootCause(e);
                    throw new ServiceExecutionException(t.getMessage());
                }
                finally {
                    DfltSamplerRepositoryServiceV2.enableNonStopFor(entry, true);
                }
            }
            throw new ServiceExecutionException("CacheManager not found !");
        }
        finally {
            this.cacheManagerSamplerRepoLock.writeLock().unlock();
        }
    }

    private Collection<QueryResultsEntityV2> buildQueryResultsEntity(String cacheManagerName, Object[][] data) {
        QueryResultsEntityV2 qre = new QueryResultsEntityV2();
        qre.setAgentId("embedded");
        qre.setName(cacheManagerName);
        qre.setData(data);
        return Collections.singleton(qre);
    }

    private void checkForInvalidAttributes(String cacheManagerName, CacheManagerEntityV2 resource) throws ServiceExecutionException {
        boolean invalidAttributesFound = false;
        StringBuilder errorMessage = new StringBuilder("You are not allowed to update those attributes : ");
        if (resource.getName() != null && !resource.getName().equals(cacheManagerName)) {
            errorMessage.append("name ");
            invalidAttributesFound = true;
        }
        for (Map.Entry<String, Object> attribute : resource.getAttributes().entrySet()) {
            String key = attribute.getKey();
            if (key.equals("MaxBytesLocalDiskAsString") || key.equals("MaxBytesLocalHeapAsString") || key.equals("Enabled")) continue;
            errorMessage.append(key).append(" ");
            invalidAttributesFound = true;
        }
        if (invalidAttributesFound) {
            errorMessage.append(". Only ").append("MaxBytesLocalDiskAsString").append(", ").append("MaxBytesLocalHeapAsString").append(" and ").append("Enabled").append(" can be updated for a CacheManager.");
            throw new ServiceExecutionException(errorMessage.toString());
        }
    }

    private static void checkForInvalidAttributes(String cacheName, CacheEntityV2 resource) throws ServiceExecutionException {
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
    public ResponseEntityV2<AgentEntityV2> getAgents(Set<String> ids) throws ServiceExecutionException {
        ResponseEntityV2<AgentEntityV2> agentEntityCollectionV2 = new ResponseEntityV2<AgentEntityV2>();
        if (ids.isEmpty()) {
            agentEntityCollectionV2.getEntities().add(this.buildAgentEntity());
            return agentEntityCollectionV2;
        }
        for (String id : ids) {
            if (!id.equals("embedded")) {
                throw new ServiceExecutionException("Unknown agent ID : " + id);
            }
            agentEntityCollectionV2.getEntities().add(this.buildAgentEntity());
        }
        return agentEntityCollectionV2;
    }

    private AgentEntityV2 buildAgentEntity() {
        AgentEntityV2 e = new AgentEntityV2();
        e.setAgentId("embedded");
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
    public ResponseEntityV2<AgentMetadataEntityV2> getAgentsMetadata(Set<String> ids) throws ServiceExecutionException {
        ResponseEntityV2<AgentMetadataEntityV2> agentEntityCollectionV2 = new ResponseEntityV2<AgentMetadataEntityV2>();
        if (ids.isEmpty()) {
            agentEntityCollectionV2.getEntities().addAll(Collections.singleton(this.buildAgentMetadata()));
            return agentEntityCollectionV2;
        }
        ArrayList<AgentMetadataEntityV2> result = new ArrayList<AgentMetadataEntityV2>();
        for (String id : ids) {
            if (!id.equals("embedded")) {
                throw new ServiceExecutionException("Unknown agent ID : " + id);
            }
            result.add(this.buildAgentMetadata());
        }
        agentEntityCollectionV2.getEntities().addAll(result);
        return agentEntityCollectionV2;
    }

    private AgentMetadataEntityV2 buildAgentMetadata() {
        AgentMetadataEntityV2 ame = new AgentMetadataEntityV2();
        ame.setAgentId("embedded");
        ame.setAgencyOf(AGENCY);
        ame.setProductVersion(this.getClass().getPackage().getImplementationVersion());
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
        return false;
    }

    private void sendCacheEvent(Object newValue, String key, Ehcache cache) {
        this.sendCacheEvent(Collections.singletonMap(key, newValue), cache);
    }

    private void sendCacheEvent(Map<String, Object> attributes, Ehcache cache) {
        CacheManager cacheManager = cache.getCacheManager();
        EventEntityV2 evenEntityV2 = new EventEntityV2();
        evenEntityV2.setAgentId("embedded");
        evenEntityV2.setType("EHCACHE.CACHE.UPDATED");
        evenEntityV2.getRootRepresentables().put("attributes", attributes);
        evenEntityV2.getRootRepresentables().put("cacheManagerName", cacheManager.getName());
        evenEntityV2.getRootRepresentables().put("cacheName", cache.getName());
        cacheManager.sendManagementEvent((Serializable)evenEntityV2, evenEntityV2.getType());
        for (EventServiceV2.EventListener eventListener : this.listeners) {
            eventListener.onEvent(evenEntityV2);
        }
    }

    private void sendCacheManagerEvent(Map<String, Object> attributes, CacheManager cacheManager) {
        EventEntityV2 evenEntityV2 = new EventEntityV2();
        evenEntityV2.setAgentId("embedded");
        evenEntityV2.setType("EHCACHE.CACHEMANAGER.UPDATED");
        evenEntityV2.getRootRepresentables().put("attributes", attributes);
        evenEntityV2.getRootRepresentables().put("cacheManagerName", cacheManager.getName());
        cacheManager.sendManagementEvent((Serializable)evenEntityV2, evenEntityV2.getType());
        for (EventServiceV2.EventListener eventListener : this.listeners) {
            eventListener.onEvent(evenEntityV2);
        }
    }

    @Override
    public void registerEventListener(EventServiceV2.EventListener listener, boolean localOnly) {
        this.listeners.add(listener);
    }

    @Override
    public void unregisterEventListener(EventServiceV2.EventListener listener) {
        this.listeners.remove(listener);
    }

    class SamplerCacheConfigurationListener
    implements CacheConfigurationListener {
        private final Ehcache cache;

        public SamplerCacheConfigurationListener(Ehcache ehcache) {
            this.cache = ehcache;
        }

        public void timeToIdleChanged(long oldTimeToIdle, long newTimeToIdle) {
            String key = "TimeToIdleSeconds";
            DfltSamplerRepositoryServiceV2.this.sendCacheEvent(newTimeToIdle, key, this.cache);
        }

        public void timeToLiveChanged(long oldTimeToLive, long newTimeToLive) {
            String key = "TimeToLiveSeconds";
            DfltSamplerRepositoryServiceV2.this.sendCacheEvent(newTimeToLive, key, this.cache);
        }

        public void diskCapacityChanged(int oldCapacity, int newCapacity) {
            String key = "MaxEntriesLocalDisk";
            DfltSamplerRepositoryServiceV2.this.sendCacheEvent(newCapacity, key, this.cache);
        }

        public void memoryCapacityChanged(int oldCapacity, int newCapacity) {
            String key = "MaxEntriesLocalHeap";
            DfltSamplerRepositoryServiceV2.this.sendCacheEvent(newCapacity, key, this.cache);
        }

        public void loggingChanged(boolean oldValue, boolean newValue) {
            String key = "LoggingEnabled";
            DfltSamplerRepositoryServiceV2.this.sendCacheEvent(newValue, key, this.cache);
        }

        public void registered(CacheConfiguration config) {
        }

        public void deregistered(CacheConfiguration config) {
        }

        public void maxBytesLocalHeapChanged(long oldValue, long newValue) {
            String key = "MaxBytesLocalHeap";
            DfltSamplerRepositoryServiceV2.this.sendCacheEvent(newValue, key, this.cache);
        }

        public void maxBytesLocalDiskChanged(long oldValue, long newValue) {
            String key = "MaxBytesLocalDisk";
            DfltSamplerRepositoryServiceV2.this.sendCacheEvent(newValue, key, this.cache);
        }

        public void maxEntriesInCacheChanged(long oldValue, long newValue) {
            String key = "MaxEntriesInCache";
            DfltSamplerRepositoryServiceV2.this.sendCacheEvent(newValue, key, this.cache);
        }
    }

    class PropertyChangeListenerImplementation
    implements PropertyChangeListener {
        private final Ehcache cache;

        public PropertyChangeListenerImplementation(Ehcache cache) {
            this.cache = cache;
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            String propName = evt.getPropertyName();
            Object propVal = evt.getNewValue();
            if ("Disabled".equals(propName)) {
                propName = "Enabled";
                propVal = (Boolean)propVal != Boolean.TRUE;
            }
            DfltSamplerRepositoryServiceV2.this.sendCacheEvent(propVal, propName, this.cache);
        }
    }

    private final class SamplerRepoEntry
    implements CacheManagerEventListener {
        private static final String ENABLED_ATTR = "Enabled";
        private static final String BULK_LOAD_ENABLED = "NodeBulkLoadEnabled";
        private static final String MAX_ELEMENTS_ON_DISK = "MaxElementsOnDisk";
        private static final String MAX_BYTES_LOCAL_DISK = "MaxBytesLocalDisk";
        private static final String MAX_BYTES_LOCAL_DISK_STRING = "MaxBytesLocalDiskAsString";
        private static final String MAX_BYTES_LOCAL_HEAP = "MaxBytesLocalHeap";
        private static final String MAX_BYTES_LOCAL_HEAP_STRING = "MaxBytesLocalHeapAsString";
        private static final String LOGGING_ENABLED = "LoggingEnabled";
        private static final String TIME_TO_IDLE_SECONDS = "TimeToIdleSeconds";
        private static final String TIME_TO_LIVE_SEC = "TimeToLiveSeconds";
        private static final String MAX_ENTRIES_LOCAL_HEAP = "MaxEntriesLocalHeap";
        private static final String MAX_ENTRIES_IN_CACHE = "MaxEntriesInCache";
        private CacheManager cacheManager;
        private CacheManagerSampler cacheManagerSampler;
        private Map<String, CacheSampler> cacheSamplersByName;
        private volatile Status status = Status.STATUS_UNINITIALISED;
        private final ReadWriteLock cacheSamplerMapLock = new ReentrantReadWriteLock();
        private final Map<String, PropertyChangeListenerImplementation> propertyChangeListeners = new ConcurrentHashMap<String, PropertyChangeListenerImplementation>();
        private final Map<String, SamplerCacheConfigurationListener> samplerCacheConfigurationListeners = new ConcurrentHashMap<String, SamplerCacheConfigurationListener>();

        public SamplerRepoEntry(CacheManager cacheManager) {
            if (cacheManager == null) {
                throw new IllegalArgumentException("cacheManager == null");
            }
            this.cacheManagerSampler = new CacheManagerSamplerImpl(cacheManager);
            this.cacheManager = cacheManager;
            String[] cNames = cacheManager.getCacheNames();
            this.cacheSamplersByName = new HashMap<String, CacheSampler>(cNames.length);
            for (String cName : cNames) {
                Ehcache ehcache = cacheManager.getEhcache(cName);
                this.cacheSamplersByName.put(cName, (CacheSampler)new CacheSamplerImpl(ehcache));
                PropertyChangeListenerImplementation propertyChangeListener = new PropertyChangeListenerImplementation(ehcache);
                SamplerCacheConfigurationListener samplerCacheConfigurationListener = new SamplerCacheConfigurationListener(ehcache);
                this.propertyChangeListeners.put(cName, propertyChangeListener);
                this.samplerCacheConfigurationListeners.put(cName, samplerCacheConfigurationListener);
                ehcache.addPropertyChangeListener((PropertyChangeListener)propertyChangeListener);
                ehcache.getCacheConfiguration().addConfigurationListener((CacheConfigurationListener)samplerCacheConfigurationListener);
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
        public void updateCache(String cacheSamplerName, CacheEntityV2 cacheResource) throws ServiceExecutionException {
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
                            DfltSamplerRepositoryServiceV2.checkForInvalidAttributes(cacheSamplerName, cacheResource);
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
                            if ((ttiAttr = cacheResource.getAttributes().get(TIME_TO_IDLE_SECONDS)) != null) {
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

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public void notifyCacheAdded(String cacheName) {
            this.cacheSamplerMapLock.writeLock().lock();
            try {
                Ehcache ehcache = this.cacheManager.getEhcache(cacheName);
                if (ehcache != null) {
                    this.cacheSamplersByName.put(cacheName, (CacheSampler)new CacheSamplerImpl(ehcache));
                    HashMap<String, Object> cacheAttributes = new HashMap<String, Object>();
                    cacheAttributes.put("version", this.getClass().getPackage().getImplementationVersion());
                    cacheAttributes.put("name", ehcache.getName());
                    cacheAttributes.put("cacheManagerName", this.cacheManager.getName());
                    Collection<CacheEntityV2> createCacheEntities = DfltSamplerRepositoryServiceV2.this.createCacheEntities(Collections.singleton(this.cacheManager.getName()), Collections.singleton(ehcache.getName()), null).getEntities();
                    if (createCacheEntities != null && !createCacheEntities.isEmpty()) {
                        CacheEntityV2 next = createCacheEntities.iterator().next();
                        cacheAttributes.put("attributes", next.getAttributes());
                    }
                    EventEntityV2 evenEntityV2 = new EventEntityV2();
                    evenEntityV2.setAgentId("embedded");
                    evenEntityV2.setType("EHCACHE.CACHE.ADDED");
                    evenEntityV2.getRootRepresentables().put("cache", cacheAttributes);
                    this.cacheManager.sendManagementEvent((Serializable)evenEntityV2, evenEntityV2.getType());
                    for (EventServiceV2.EventListener eventListener : DfltSamplerRepositoryServiceV2.this.listeners) {
                        eventListener.onEvent(evenEntityV2);
                    }
                    PropertyChangeListenerImplementation propertyChangeListener = new PropertyChangeListenerImplementation(ehcache);
                    SamplerCacheConfigurationListener samplerCacheConfigurationListener = new SamplerCacheConfigurationListener(ehcache);
                    this.propertyChangeListeners.put(cacheName, propertyChangeListener);
                    this.samplerCacheConfigurationListeners.put(cacheName, samplerCacheConfigurationListener);
                    ehcache.addPropertyChangeListener((PropertyChangeListener)propertyChangeListener);
                    ehcache.getCacheConfiguration().addConfigurationListener((CacheConfigurationListener)samplerCacheConfigurationListener);
                }
            }
            finally {
                this.cacheSamplerMapLock.writeLock().unlock();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public void notifyCacheRemoved(String cacheName) {
            this.cacheSamplerMapLock.writeLock().lock();
            try {
                this.propertyChangeListeners.remove(cacheName);
                this.samplerCacheConfigurationListeners.remove(cacheName);
                this.cacheSamplersByName.remove(cacheName);
                HashMap<String, String> cacheAttributes = new HashMap<String, String>();
                cacheAttributes.put("version", this.getClass().getPackage().getImplementationVersion());
                cacheAttributes.put("name", cacheName);
                cacheAttributes.put("cacheManagerName", this.cacheManager.getName());
                EventEntityV2 evenEntityV2 = new EventEntityV2();
                evenEntityV2.setAgentId("embedded");
                evenEntityV2.setType("EHCACHE.CACHE.REMOVED");
                evenEntityV2.getRootRepresentables().put("cache", cacheAttributes);
                this.cacheManager.sendManagementEvent((Serializable)evenEntityV2, evenEntityV2.getType());
                for (EventServiceV2.EventListener eventListener : DfltSamplerRepositoryServiceV2.this.listeners) {
                    eventListener.onEvent(evenEntityV2);
                }
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

    class ConfigurationPropertyChangeListener
    implements PropertyChangeListener {
        private CacheManager cacheManager;

        ConfigurationPropertyChangeListener(CacheManager cacheManager) {
            this.cacheManager = cacheManager;
        }

        @Override
        public void propertyChange(PropertyChangeEvent pce) {
            String propName = pce.getPropertyName();
            if (propName.equals("maxBytesLocalHeap")) {
                HashMap<String, Object> attrs = new HashMap<String, Object>();
                attrs.put("MaxBytesLocalHeap", pce.getNewValue());
                attrs.put("MaxBytesLocalHeapAsString", this.cacheManager.getConfiguration().getMaxBytesLocalHeapAsString());
                DfltSamplerRepositoryServiceV2.this.sendCacheManagerEvent(attrs, this.cacheManager);
            } else if (propName.equals("maxBytesLocalDisk")) {
                HashMap<String, Object> attrs = new HashMap<String, Object>();
                attrs.put("MaxBytesLocalDisk", pce.getNewValue());
                attrs.put("MaxBytesLocalDiskAsString", this.cacheManager.getConfiguration().getMaxBytesLocalDiskAsString());
                DfltSamplerRepositoryServiceV2.this.sendCacheManagerEvent(attrs, this.cacheManager);
            }
        }
    }
}

