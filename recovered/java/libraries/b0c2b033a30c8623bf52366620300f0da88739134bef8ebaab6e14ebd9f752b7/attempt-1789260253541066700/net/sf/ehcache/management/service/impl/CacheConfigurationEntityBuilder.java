/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.ehcache.management.sampled.CacheManagerSampler
 */
package net.sf.ehcache.management.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import net.sf.ehcache.management.resource.CacheConfigEntity;
import net.sf.ehcache.management.sampled.CacheManagerSampler;

final class CacheConfigurationEntityBuilder {
    private final Map<String, CacheManagerSampler> samplersByCName = new HashMap<String, CacheManagerSampler>();

    static CacheConfigurationEntityBuilder createWith(CacheManagerSampler sampler, String cacheName) {
        return new CacheConfigurationEntityBuilder(sampler, cacheName);
    }

    private CacheConfigurationEntityBuilder(CacheManagerSampler sampler, String cacheName) {
        this.addSampler(sampler, cacheName);
    }

    CacheConfigurationEntityBuilder add(CacheManagerSampler sampler, String cacheName) {
        this.addSampler(sampler, cacheName);
        return this;
    }

    Collection<CacheConfigEntity> build() {
        ArrayList<CacheConfigEntity> cces = new ArrayList<CacheConfigEntity>();
        for (Map.Entry<String, CacheManagerSampler> entry : this.samplersByCName.entrySet()) {
            CacheConfigEntity cce = new CacheConfigEntity();
            String cacheName = entry.getKey();
            cce.setCacheName(cacheName);
            cce.setAgentId("embedded");
            cce.setVersion(this.getClass().getPackage().getImplementationVersion());
            CacheManagerSampler sampler = entry.getValue();
            cce.setCacheManagerName(sampler.getName());
            cce.setXml(sampler.generateActiveConfigDeclaration(cacheName));
            cces.add(cce);
        }
        return cces;
    }

    private void addSampler(CacheManagerSampler sampler, String cacheName) {
        if (sampler == null) {
            throw new IllegalArgumentException("sampler == null");
        }
        if (cacheName == null) {
            throw new IllegalArgumentException("cacheName == null");
        }
        if (!Arrays.asList(sampler.getCacheNames()).contains(cacheName)) {
            throw new IllegalArgumentException(String.format("Invalid cache name: %s.", cacheName));
        }
        this.samplersByCName.put(cacheName, sampler);
    }
}

