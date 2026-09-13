/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="TimeUnitType")
@XmlEnum
public enum TimeUnitType {
    YEAR("Year"),
    MONTH("Month"),
    WEEK("Week"),
    DAY("Day"),
    HOUR("Hour"),
    SECOND("Second"),
    FULL_DURATION("FullDuration"),
    MINUTE("Minute");

    private final String value;

    private TimeUnitType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static TimeUnitType fromValue(String v) {
        for (TimeUnitType c : TimeUnitType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

