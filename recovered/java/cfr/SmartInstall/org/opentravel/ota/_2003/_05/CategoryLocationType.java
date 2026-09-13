/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="CategoryLocationType")
@XmlEnum
public enum CategoryLocationType {
    INSIDE("Inside"),
    OUTSIDE("Outside"),
    BOTH("Both");

    private final String value;

    private CategoryLocationType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static CategoryLocationType fromValue(String v) {
        for (CategoryLocationType c : CategoryLocationType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

