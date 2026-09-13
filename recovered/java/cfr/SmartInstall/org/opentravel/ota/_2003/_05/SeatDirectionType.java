/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="SeatDirectionType")
@XmlEnum
public enum SeatDirectionType {
    FACING("Facing"),
    BACK("Back"),
    AIRLINE("Airline"),
    LATERAL("Lateral"),
    UNKNOWN("Unknown");

    private final String value;

    private SeatDirectionType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static SeatDirectionType fromValue(String v) {
        for (SeatDirectionType c : SeatDirectionType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

