/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.management.resource;

import java.util.HashMap;
import java.util.Map;
import net.sf.ehcache.management.resource.AbstractCacheEntity;

public class CacheEntity
extends AbstractCacheEntity {
    private Map<String, Object> attributes = new HashMap<String, Object>();

    public Map<String, Object> getAttributes() {
        return this.attributes;
    }
}

