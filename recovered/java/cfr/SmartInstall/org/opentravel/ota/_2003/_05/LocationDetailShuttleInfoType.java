/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="LocationDetailShuttleInfoType")
@XmlEnum
public enum LocationDetailShuttleInfoType {
    TRANSPORTATION("Transportation"),
    FREQUENCY("Frequency"),
    PICKUP_INFO("PickupInfo"),
    DISTANCE("Distance"),
    ELAPSED_TIME("ElapsedTime"),
    FEE("Fee"),
    MISCELLANEOUS("Miscellaneous"),
    HOURS("Hours");

    private final String value;

    private LocationDetailShuttleInfoType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static LocationDetailShuttleInfoType fromValue(String v) {
        for (LocationDetailShuttleInfoType c : LocationDetailShuttleInfoType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

