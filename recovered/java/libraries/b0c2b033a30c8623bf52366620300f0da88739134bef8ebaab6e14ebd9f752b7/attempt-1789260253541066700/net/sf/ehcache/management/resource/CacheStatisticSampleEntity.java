/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.management.resource;

import java.util.Map;
import net.sf.ehcache.management.resource.AbstractCacheEntity;

public class CacheStatisticSampleEntity
extends AbstractCacheEntity {
    private String statName;
    private Map<Long, Long> statValueByTimeMillis;

    public String getStatName() {
        return this.statName;
    }

    public void setStatName(String statName) {
        this.statName = statName;
    }

    public Map<Long, Long> getStatValueByTimeMillis() {
        return this.statValueByTimeMillis;
    }

    public void setStatValueByTimeMillis(Map<Long, Long> statValueByTimeMillis) {
        this.statValueByTimeMillis = statValueByTimeMillis;
    }
}

