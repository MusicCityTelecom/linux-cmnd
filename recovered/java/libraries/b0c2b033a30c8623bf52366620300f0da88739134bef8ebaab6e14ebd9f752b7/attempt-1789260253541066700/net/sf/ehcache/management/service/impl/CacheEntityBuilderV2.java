/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.ehcache.management.sampled.CacheSampler
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package net.sf.ehcache.management.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import net.sf.ehcache.management.resource.CacheEntityV2;
import net.sf.ehcache.management.sampled.CacheSampler;
import net.sf.ehcache.management.service.AccessorPrefix;
import net.sf.ehcache.management.service.impl.ConstrainableEntityBuilderSupportV2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class CacheEntityBuilderV2
extends ConstrainableEntityBuilderSupportV2<CacheSampler> {
    private static final Logger LOG = LoggerFactory.getLogger(CacheEntityBuilderV2.class);
    private static final String C_NAME_ACCESSOR = (Object)((Object)AccessorPrefix.get) + "CacheName";
    private final Map<String, Set<CacheSampler>> samplersByCMName = new HashMap<String, Set<CacheSampler>>();

    static CacheEntityBuilderV2 createWith(CacheSampler sampler, String cacheManagerName) {
        return new CacheEntityBuilderV2(sampler, cacheManagerName);
    }

    private CacheEntityBuilderV2(CacheSampler sampler, String cacheManagerName) {
        this.addSampler(sampler, cacheManagerName);
    }

    CacheEntityBuilderV2 add(CacheSampler sampler, String cacheManagerName) {
        this.addSampler(sampler, cacheManagerName);
        return this;
    }

    CacheEntityBuilderV2 add(Set<String> constraintAttributes) {
        this.addConstraints(constraintAttributes);
        return this;
    }

    Collection<CacheEntityV2> build() {
        ArrayList<CacheEntityV2> ces = new ArrayList<CacheEntityV2>(this.samplersByCMName.values().size());
        for (Map.Entry<String, Set<CacheSampler>> entry : this.samplersByCMName.entrySet()) {
            for (CacheSampler sampler : entry.getValue()) {
                CacheEntityV2 ce = new CacheEntityV2();
                ce.setCacheManagerName(entry.getKey());
                ce.setName(sampler.getCacheName());
                ce.setAgentId("embedded");
                if (this.getAttributeConstraints() != null && !this.getAttributeConstraints().isEmpty() && this.getAttributeConstraints().size() < CacheSampler.class.getMethods().length) {
                    this.buildAttributeMapByAttribute(CacheSampler.class, sampler, ce.getAttributes(), this.getAttributeConstraints(), C_NAME_ACCESSOR);
                } else {
                    this.buildAttributeMapByApi(CacheSampler.class, sampler, ce.getAttributes(), this.getAttributeConstraints(), C_NAME_ACCESSOR);
                }
                ces.add(ce);
            }
        }
        return ces;
    }

    @Override
    Logger getLog() {
        return LOG;
    }

    @Override
    protected Set<String> getExcludedAttributeNames(CacheSampler sampler) {
        if (sampler.isLocalHeapCountBased()) {
            HashSet<String> excludedNames = new HashSet<String>();
            excludedNames.add("LocalHeapSizeInBytes");
            excludedNames.add("LocalHeapSizeInBytesSample");
            return excludedNames;
        }
        return Collections.emptySet();
    }

    private void addSampler(CacheSampler sampler, String cacheManagerName) {
        if (sampler == null) {
            throw new IllegalArgumentException("sampler == null");
        }
        if (cacheManagerName == null) {
            throw new IllegalArgumentException("cacheManagerName == null");
        }
        Set<CacheSampler> samplers = this.samplersByCMName.get(cacheManagerName);
        if (samplers == null) {
            samplers = new HashSet<CacheSampler>();
            this.samplersByCMName.put(cacheManagerName, samplers);
        }
        samplers.add(sampler);
    }
}

