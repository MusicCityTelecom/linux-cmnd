/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="IncludeExcludeType")
@XmlEnum
public enum IncludeExcludeType {
    INCLUDE("Include"),
    EXCLUDE("Exclude"),
    REQUIRED("Required"),
    ALLOWED("Allowed");

    private final String value;

    private IncludeExcludeType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static IncludeExcludeType fromValue(String v) {
        for (IncludeExcludeType c : IncludeExcludeType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

