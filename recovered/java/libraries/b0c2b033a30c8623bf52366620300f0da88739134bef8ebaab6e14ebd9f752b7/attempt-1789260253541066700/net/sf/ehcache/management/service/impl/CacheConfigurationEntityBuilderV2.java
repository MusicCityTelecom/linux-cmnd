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
import net.sf.ehcache.management.resource.CacheConfigEntityV2;
import net.sf.ehcache.management.sampled.CacheManagerSampler;

final class CacheConfigurationEntityBuilderV2 {
    private final Map<String, CacheManagerSampler> samplersByCName = new HashMap<String, CacheManagerSampler>();

    static CacheConfigurationEntityBuilderV2 createWith(CacheManagerSampler sampler, String cacheName) {
        return new CacheConfigurationEntityBuilderV2(sampler, cacheName);
    }

    private CacheConfigurationEntityBuilderV2(CacheManagerSampler sampler, String cacheName) {
        this.addSampler(sampler, cacheName);
    }

    CacheConfigurationEntityBuilderV2 add(CacheManagerSampler sampler, String cacheName) {
        this.addSampler(sampler, cacheName);
        return this;
    }

    Collection<CacheConfigEntityV2> build() {
        ArrayList<CacheConfigEntityV2> cces = new ArrayList<CacheConfigEntityV2>();
        for (Map.Entry<String, CacheManagerSampler> entry : this.samplersByCName.entrySet()) {
            CacheConfigEntityV2 cce = new CacheConfigEntityV2();
            String cacheName = entry.getKey();
            cce.setCacheName(cacheName);
            cce.setAgentId("embedded");
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

