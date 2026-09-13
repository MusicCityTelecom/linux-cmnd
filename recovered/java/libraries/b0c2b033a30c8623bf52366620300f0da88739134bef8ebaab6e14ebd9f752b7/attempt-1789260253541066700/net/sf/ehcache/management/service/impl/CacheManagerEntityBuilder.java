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
import net.sf.ehcache.management.resource.CacheManagerEntity;
import net.sf.ehcache.management.sampled.CacheManagerSampler;
import net.sf.ehcache.management.service.AccessorPrefix;
import net.sf.ehcache.management.service.impl.ConstrainableEntityBuilderSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class CacheManagerEntityBuilder
extends ConstrainableEntityBuilderSupport<CacheManagerSampler> {
    private static final Logger LOG = LoggerFactory.getLogger(CacheManagerEntityBuilder.class);
    private static final String CM_NAME_ACCESSOR = (Object)((Object)AccessorPrefix.get) + "Name";
    private final List<CacheManagerSampler> cmSamplers = new ArrayList<CacheManagerSampler>();

    static CacheManagerEntityBuilder createWith(CacheManagerSampler sampler) {
        return new CacheManagerEntityBuilder(sampler);
    }

    CacheManagerEntityBuilder(CacheManagerSampler sampler) {
        this.addSampler(sampler);
    }

    CacheManagerEntityBuilder add(CacheManagerSampler sampler) {
        this.addSampler(sampler);
        return this;
    }

    CacheManagerEntityBuilder add(Set<String> constraintAttributes) {
        this.addConstraints(constraintAttributes);
        return this;
    }

    Collection<CacheManagerEntity> build() {
        ArrayList<CacheManagerEntity> cmes = new ArrayList<CacheManagerEntity>(this.cmSamplers.size());
        for (CacheManagerSampler cms : this.cmSamplers) {
            CacheManagerEntity cme = new CacheManagerEntity();
            cme.setName(cms.getName());
            cme.setAgentId("embedded");
            cme.setVersion(this.getClass().getPackage().getImplementationVersion());
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

