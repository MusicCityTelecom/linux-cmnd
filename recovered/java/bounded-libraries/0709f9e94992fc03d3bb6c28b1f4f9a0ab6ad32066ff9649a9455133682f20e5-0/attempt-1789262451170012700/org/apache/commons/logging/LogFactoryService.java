/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.logging;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogAdapter;
import org.apache.commons.logging.LogFactory;

@Deprecated
public class LogFactoryService
extends LogFactory {
    private final Map<String, Object> attributes = new ConcurrentHashMap<String, Object>();

    @Override
    public Log getInstance(Class<?> clazz) {
        return this.getInstance(clazz.getName());
    }

    @Override
    public Log getInstance(String name) {
        return LogAdapter.createLog(name);
    }

    public void setAttribute(String name, Object value) {
        if (value != null) {
            this.attributes.put(name, value);
        } else {
            this.attributes.remove(name);
        }
    }

    public void removeAttribute(String name) {
        this.attributes.remove(name);
    }

    public Object getAttribute(String name) {
        return this.attributes.get(name);
    }

    public String[] getAttributeNames() {
        return this.attributes.keySet().toArray(new String[0]);
    }

    public void release() {
    }
}

