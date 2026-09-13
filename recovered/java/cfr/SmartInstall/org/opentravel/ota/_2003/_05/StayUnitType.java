/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="StayUnitType")
@XmlEnum
public enum StayUnitType {
    MINUTES("Minutes"),
    HOURS("Hours"),
    DAYS("Days"),
    MONTHS("Months"),
    MON("MON"),
    TUES("TUES"),
    WED("WED"),
    THU("THU"),
    FRI("FRI"),
    SAT("SAT"),
    SUN("SUN");

    private final String value;

    private StayUnitType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static StayUnitType fromValue(String v) {
        for (StayUnitType c : StayUnitType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

