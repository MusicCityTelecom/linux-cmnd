/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.ehcache.management.sampled.CacheSampler
 *  net.sf.ehcache.util.counter.sampled.SampledCounter
 *  net.sf.ehcache.util.counter.sampled.TimeStampedCounterValue
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package net.sf.ehcache.management.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import net.sf.ehcache.management.resource.CacheStatisticSampleEntityV2;
import net.sf.ehcache.management.sampled.CacheSampler;
import net.sf.ehcache.management.service.AccessorPrefix;
import net.sf.ehcache.util.counter.sampled.SampledCounter;
import net.sf.ehcache.util.counter.sampled.TimeStampedCounterValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class CacheStatisticSampleEntityBuilderV2 {
    private static final Logger LOG = LoggerFactory.getLogger(CacheStatisticSampleEntityBuilderV2.class);
    private static final String SAMPLE_SUFFIX = "Sample";
    private final Set<String> sampleNames;
    private final Map<String, Set<CacheSampler>> samplersByCMName = new HashMap<String, Set<CacheSampler>>();

    static CacheStatisticSampleEntityBuilderV2 createWith(Set<String> statisticSampleName) {
        return new CacheStatisticSampleEntityBuilderV2(statisticSampleName);
    }

    private CacheStatisticSampleEntityBuilderV2(Set<String> sampleNames) {
        this.sampleNames = sampleNames;
    }

    CacheStatisticSampleEntityBuilderV2 add(CacheSampler sampler, String cacheManagerName) {
        this.addSampler(sampler, cacheManagerName);
        return this;
    }

    Collection<CacheStatisticSampleEntityV2> build() {
        ArrayList<CacheStatisticSampleEntityV2> csses = new ArrayList<CacheStatisticSampleEntityV2>();
        for (Map.Entry<String, Set<CacheSampler>> entry : this.samplersByCMName.entrySet()) {
            for (CacheSampler sampler : entry.getValue()) {
                if (this.sampleNames == null) {
                    for (Method m : CacheSampler.class.getMethods()) {
                        CacheStatisticSampleEntityV2 csse;
                        if (!AccessorPrefix.isAccessor(m.getName()) || !SampledCounter.class.isAssignableFrom(m.getReturnType()) || (csse = this.makeStatResource(m, sampler, entry.getKey())) == null) continue;
                        csses.add(csse);
                    }
                    continue;
                }
                for (String sampleName : this.sampleNames) {
                    CacheStatisticSampleEntityV2 csse;
                    Method sampleMethod;
                    try {
                        sampleMethod = CacheSampler.class.getMethod((Object)((Object)AccessorPrefix.get) + sampleName + SAMPLE_SUFFIX, new Class[0]);
                    }
                    catch (NoSuchMethodException e) {
                        LOG.warn("A statistic sample with the name '{}' does not exist.", (Object)sampleName);
                        continue;
                    }
                    if (!SampledCounter.class.isAssignableFrom(sampleMethod.getReturnType()) || (csse = this.makeStatResource(sampleMethod, sampler, entry.getKey())) == null) continue;
                    csses.add(csse);
                }
            }
        }
        return csses;
    }

    private CacheStatisticSampleEntityV2 makeStatResource(Method sampleMethod, CacheSampler sampler, String cmName) {
        SampledCounter sCntr;
        try {
            sCntr = (SampledCounter)SampledCounter.class.cast(sampleMethod.invoke(sampler, new Object[0]));
        }
        catch (IllegalAccessException e) {
            LOG.warn("Failed to invoke method '{}' while constructing entity due to access restriction.", (Object)sampleMethod.getName());
            sCntr = null;
        }
        catch (InvocationTargetException e) {
            LOG.warn(String.format("Failed to invoke method %s while constructing entity.", sampleMethod.getName()), (Throwable)e);
            sCntr = null;
        }
        if (sCntr != null) {
            CacheStatisticSampleEntityV2 csse = new CacheStatisticSampleEntityV2();
            csse.setCacheManagerName(cmName);
            csse.setName(sampler.getCacheName());
            csse.setAgentId("embedded");
            csse.setStatName(AccessorPrefix.trimPrefix(sampleMethod.getName()).replace(SAMPLE_SUFFIX, ""));
            TimeStampedCounterValue[] tscvs = this.getExcludedMethodNames(sampler).contains(sampleMethod.getName()) ? new TimeStampedCounterValue[]{} : sCntr.getAllSampleValues();
            TreeMap<Long, Long> statValueByTime = new TreeMap<Long, Long>();
            csse.setStatValueByTimeMillis(statValueByTime);
            for (TimeStampedCounterValue tscv : tscvs) {
                statValueByTime.put(tscv.getTimestamp(), tscv.getCounterValue());
            }
            return csse;
        }
        return null;
    }

    private Set<String> getExcludedMethodNames(CacheSampler sampler) {
        if (sampler.isLocalHeapCountBased()) {
            return Collections.singleton("getLocalHeapSizeInBytesSample");
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

