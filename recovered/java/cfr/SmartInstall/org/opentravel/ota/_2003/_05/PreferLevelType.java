/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="PreferLevelType")
@XmlEnum
public enum PreferLevelType {
    ONLY("Only"),
    UNACCEPTABLE("Unacceptable"),
    PREFERRED("Preferred"),
    REQUIRED("Required"),
    NO_PREFERENCE("NoPreference");

    private final String value;

    private PreferLevelType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static PreferLevelType fromValue(String v) {
        for (PreferLevelType c : PreferLevelType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

