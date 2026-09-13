/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.ehcache.management.sampled.CacheManagerSampler
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package net.sf.ehcache.management.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import net.sf.ehcache.management.resource.CacheManagerEntityV2;
import net.sf.ehcache.management.sampled.CacheManagerSampler;
import net.sf.ehcache.management.service.AccessorPrefix;
import net.sf.ehcache.management.service.impl.ConstrainableEntityBuilderSupportV2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class CacheManagerEntityBuilderV2
extends ConstrainableEntityBuilderSupportV2<CacheManagerSampler> {
    private static final Logger LOG = LoggerFactory.getLogger(CacheManagerEntityBuilderV2.class);
    private static final String CM_NAME_ACCESSOR = (Object)((Object)AccessorPrefix.get) + "Name";
    private final List<CacheManagerSampler> cmSamplers = new ArrayList<CacheManagerSampler>();

    static CacheManagerEntityBuilderV2 createWith(CacheManagerSampler sampler) {
        return new CacheManagerEntityBuilderV2(sampler);
    }

    CacheManagerEntityBuilderV2(CacheManagerSampler sampler) {
        this.addSampler(sampler);
    }

    CacheManagerEntityBuilderV2 add(CacheManagerSampler sampler) {
        this.addSampler(sampler);
        return this;
    }

    CacheManagerEntityBuilderV2 add(Set<String> constraintAttributes) {
        this.addConstraints(constraintAttributes);
        return this;
    }

    Collection<CacheManagerEntityV2> build() {
        ArrayList<CacheManagerEntityV2> cmes = new ArrayList<CacheManagerEntityV2>(this.cmSamplers.size());
        for (CacheManagerSampler cms : this.cmSamplers) {
            CacheManagerEntityV2 cme = new CacheManagerEntityV2();
            cme.setName(cms.getName());
            cme.setAgentId("embedded");
            if (this.getAttributeConstraints() != null && !this.getAttributeConstraints().isEmpty() && this.getAttributeConstraints().size() < CacheManagerSampler.class.getMethods().length) {
                this.buildAttributeMapByAttribute(CacheManagerSampler.class, cms, cme.getAttributes(), this.getAttributeConstraints(), CM_NAME_ACCESSOR);
            } else {
                this.buildAttributeMapByApi(CacheManagerSampler.class, cms, cme.getAttributes(), this.getAttributeConstraints(), CM_NAME_ACCESSOR);
            }
            cmes.add(cme);
        }
        return cmes;
    }

    @Override
    Logger getLog() {
        return LOG;
    }

    @Override
    protected Set<String> getExcludedAttributeNames(CacheManagerSampler cacheManagerSampler) {
        return Collections.emptySet();
    }

    private void addSampler(CacheManagerSampler sampler) {
        if (sampler == null) {
            throw new IllegalArgumentException("sampler == null");
        }
        this.cmSamplers.add(sampler);
    }
}

