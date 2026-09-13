/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.ehcache.management.sampled.CacheManagerSampler
 */
package net.sf.ehcache.management.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.sf.ehcache.management.resource.CacheManagerConfigEntityV2;
import net.sf.ehcache.management.sampled.CacheManagerSampler;

final class CacheManagerConfigurationEntityBuilderV2 {
    private final List<CacheManagerSampler> cmSamplers = new ArrayList<CacheManagerSampler>();

    static CacheManagerConfigurationEntityBuilderV2 createWith(CacheManagerSampler sampler) {
        return new CacheManagerConfigurationEntityBuilderV2(sampler);
    }

    private CacheManagerConfigurationEntityBuilderV2(CacheManagerSampler sampler) {
        this.addSampler(sampler);
    }

    CacheManagerConfigurationEntityBuilderV2 add(CacheManagerSampler sampler) {
        this.addSampler(sampler);
        return this;
    }

    Collection<CacheManagerConfigEntityV2> build() {
        ArrayList<CacheManagerConfigEntityV2> cmces = new ArrayList<CacheManagerConfigEntityV2>(this.cmSamplers.size());
        for (CacheManagerSampler sampler : this.cmSamplers) {
            CacheManagerConfigEntityV2 cmce = new CacheManagerConfigEntityV2();
            cmce.setCacheManagerName(sampler.getName());
            cmce.setAgentId("embedded");
            cmce.setXml(sampler.generateActiveConfigDeclaration());
            cmces.add(cmce);
        }
        return cmces;
    }

    private void addSampler(CacheManagerSampler sampler) {
        if (sampler == null) {
            throw new IllegalArgumentException("sampler == null");
        }
        this.cmSamplers.add(sampler);
    }
}

