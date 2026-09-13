/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="SeatAccommodationType")
@XmlEnum
public enum SeatAccommodationType {
    NOT_SIGNIFICANT("NotSignificant"),
    SEAT("Seat"),
    SLEEPERETTE("Sleeperette"),
    NO_SEAT("NoSeat");

    private final String value;

    private SeatAccommodationType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static SeatAccommodationType fromValue(String v) {
        for (SeatAccommodationType c : SeatAccommodationType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

