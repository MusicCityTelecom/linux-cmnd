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
import net.sf.ehcache.management.resource.CacheManagerConfigEntity;
import net.sf.ehcache.management.sampled.CacheManagerSampler;

final class CacheManagerConfigurationEntityBuilder {
    private final List<CacheManagerSampler> cmSamplers = new ArrayList<CacheManagerSampler>();

    static CacheManagerConfigurationEntityBuilder createWith(CacheManagerSampler sampler) {
        return new CacheManagerConfigurationEntityBuilder(sampler);
    }

    private CacheManagerConfigurationEntityBuilder(CacheManagerSampler sampler) {
        this.addSampler(sampler);
    }

    CacheManagerConfigurationEntityBuilder add(CacheManagerSampler sampler) {
        this.addSampler(sampler);
        return this;
    }

    Collection<CacheManagerConfigEntity> build() {
        ArrayList<CacheManagerConfigEntity> cmces = new ArrayList<CacheManagerConfigEntity>(this.cmSamplers.size());
        for (CacheManagerSampler sampler : this.cmSamplers) {
            CacheManagerConfigEntity cmce = new CacheManagerConfigEntity();
            cmce.setCacheManagerName(sampler.getName());
            cmce.setAgentId("embedded");
            cmce.setVersion(this.getClass().getPackage().getImplementationVersion());
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

