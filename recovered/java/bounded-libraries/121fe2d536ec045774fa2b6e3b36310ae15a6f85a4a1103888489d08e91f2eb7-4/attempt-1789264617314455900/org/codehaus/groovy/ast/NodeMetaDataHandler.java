/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.ast;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import org.codehaus.groovy.GroovyBugError;
import org.codehaus.groovy.util.ListHashMap;

public interface NodeMetaDataHandler {
    default public <T> T getNodeMetaData(Object key) {
        Map<?, ?> metaDataMap = this.getMetaDataMap();
        if (metaDataMap == null) {
            return null;
        }
        return (T)metaDataMap.get(key);
    }

    default public <T> T getNodeMetaData(Object key, Function<?, ? extends T> valFn) {
        if (key == null) {
            throw new GroovyBugError("Tried to get/set meta data with null key on " + this + ".");
        }
        Map<?, ?> metaDataMap = this.getMetaDataMap();
        if (metaDataMap == null) {
            metaDataMap = new ListHashMap();
            this.setMetaDataMap(metaDataMap);
        }
        return metaDataMap.computeIfAbsent(key, valFn);
    }

    default public void copyNodeMetaData(NodeMetaDataHandler other) {
        Map<?, ?> otherMetaDataMap = other.getMetaDataMap();
        if (otherMetaDataMap == null) {
            return;
        }
        Map<?, ?> metaDataMap = this.getMetaDataMap();
        if (metaDataMap == null) {
            metaDataMap = new ListHashMap();
            this.setMetaDataMap(metaDataMap);
        }
        metaDataMap.putAll(otherMetaDataMap);
    }

    default public void setNodeMetaData(Object key, Object value) {
        Object old = this.putNodeMetaData(key, value);
        if (old != null) {
            throw new GroovyBugError("Tried to overwrite existing meta data " + this + ".");
        }
    }

    default public Object putNodeMetaData(Object key, Object value) {
        if (key == null) {
            throw new GroovyBugError("Tried to set meta data with null key on " + this + ".");
        }
        Map<?, ?> metaDataMap = this.getMetaDataMap();
        if (metaDataMap == null) {
            metaDataMap = new ListHashMap();
            this.setMetaDataMap(metaDataMap);
        }
        return metaDataMap.put(key, value);
    }

    default public void removeNodeMetaData(Object key) {
        if (key == null) {
            throw new GroovyBugError("Tried to remove meta data with null key " + this + ".");
        }
        Map<?, ?> metaDataMap = this.getMetaDataMap();
        if (metaDataMap == null) {
            return;
        }
        metaDataMap.remove(key);
    }

    default public Map<?, ?> getNodeMetaData() {
        Map<?, ?> metaDataMap = this.getMetaDataMap();
        if (metaDataMap == null) {
            return Collections.emptyMap();
        }
        return Collections.unmodifiableMap(metaDataMap);
    }

    public Map<?, ?> getMetaDataMap();

    public void setMetaDataMap(Map<?, ?> var1);
}

