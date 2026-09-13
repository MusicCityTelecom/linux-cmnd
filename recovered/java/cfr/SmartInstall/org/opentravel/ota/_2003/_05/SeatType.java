/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="SeatType")
@XmlEnum
public enum SeatType {
    WINDOW("Window"),
    AISLE("Aisle"),
    TABLE("Table"),
    MIDDLE("Middle"),
    INDIVIDUAL("Individual");

    private final String value;

    private SeatType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static SeatType fromValue(String v) {
        for (SeatType c : SeatType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

