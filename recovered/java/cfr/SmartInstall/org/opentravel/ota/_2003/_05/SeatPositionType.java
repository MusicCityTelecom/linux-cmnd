/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="SeatPositionType")
@XmlEnum
public enum SeatPositionType {
    NONE("None"),
    TOGETHER("Together"),
    AISLE("Aisle"),
    CENTER("Center"),
    WINDOW("Window"),
    SPECIFIC("Specific"),
    EXIT("Exit"),
    TABLE("Table"),
    ADJACENT_AISLE("AdjacentAisle"),
    INDIVIDUAL("Individual"),
    MIDDLE("Middle");

    private final String value;

    private SeatPositionType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static SeatPositionType fromValue(String v) {
        for (SeatPositionType c : SeatPositionType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

