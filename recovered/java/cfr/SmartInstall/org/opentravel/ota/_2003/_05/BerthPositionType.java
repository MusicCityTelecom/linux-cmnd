/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="BerthPositionType")
@XmlEnum
public enum BerthPositionType {
    UPPER("Upper"),
    MIDDLE("Middle"),
    LOWER("Lower");

    private final String value;

    private BerthPositionType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static BerthPositionType fromValue(String v) {
        for (BerthPositionType c : BerthPositionType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

