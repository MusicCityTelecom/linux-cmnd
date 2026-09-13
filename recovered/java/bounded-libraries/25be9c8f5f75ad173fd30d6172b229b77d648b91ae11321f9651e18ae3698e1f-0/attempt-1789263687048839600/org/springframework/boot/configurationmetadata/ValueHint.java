/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.configurationmetadata;

import java.io.Serializable;

public class ValueHint
implements Serializable {
    private Object value;
    private String description;
    private String shortDescription;

    public Object getValue() {
        return this.value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortDescription() {
        return this.shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String toString() {
        return "ValueHint{value=" + this.value + ", description='" + this.description + '\'' + '}';
    }
}

