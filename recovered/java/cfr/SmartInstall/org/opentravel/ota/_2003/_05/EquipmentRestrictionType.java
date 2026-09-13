/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="EquipmentRestrictionType")
@XmlEnum
public enum EquipmentRestrictionType {
    ONE_WAY_ONLY("OneWayOnly"),
    ROUND_TRIP_ONLY("RoundTripOnly"),
    ANY_RESERVATION("AnyReservation");

    private final String value;

    private EquipmentRestrictionType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static EquipmentRestrictionType fromValue(String v) {
        for (EquipmentRestrictionType c : EquipmentRestrictionType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

