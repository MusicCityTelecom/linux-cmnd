/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.management.resource;

import java.util.HashMap;
import java.util.Map;
import net.sf.ehcache.management.resource.AbstractCacheEntityV2;

public class CacheEntityV2
extends AbstractCacheEntityV2 {
    private Map<String, Object> attributes = new HashMap<String, Object>();

    public Map<String, Object> getAttributes() {
        return this.attributes;
    }
}

