/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.configurationmetadata;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class ValueProvider
implements Serializable {
    private String name;
    private final Map<String, Object> parameters = new LinkedHashMap<String, Object>();

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Object> getParameters() {
        return this.parameters;
    }

    public String toString() {
        return "ValueProvider{name='" + this.name + ", parameters=" + this.parameters + '}';
    }
}

