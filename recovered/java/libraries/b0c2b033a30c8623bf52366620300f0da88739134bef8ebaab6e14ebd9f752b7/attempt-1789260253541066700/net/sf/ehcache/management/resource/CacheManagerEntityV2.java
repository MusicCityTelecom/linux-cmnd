/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.management.resource;

import java.util.HashMap;
import java.util.Map;
import org.terracotta.management.resource.AbstractEntityV2;

public class CacheManagerEntityV2
extends AbstractEntityV2 {
    private String name;
    private final Map<String, Object> attributes = new HashMap<String, Object>();

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Object> getAttributes() {
        return this.attributes;
    }
}

