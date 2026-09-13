/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="DistanceUnitNameType")
@XmlEnum
public enum DistanceUnitNameType {
    MILE("Mile"),
    KM("Km"),
    BLOCK("Block");

    private final String value;

    private DistanceUnitNameType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static DistanceUnitNameType fromValue(String v) {
        for (DistanceUnitNameType c : DistanceUnitNameType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

