/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="DayOfWeekType")
@XmlEnum
public enum DayOfWeekType {
    MON("Mon"),
    TUE("Tue"),
    WED("Wed"),
    THU("Thu"),
    FRI("Fri"),
    SAT("Sat"),
    SUN("Sun");

    private final String value;

    private DayOfWeekType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static DayOfWeekType fromValue(String v) {
        for (DayOfWeekType c : DayOfWeekType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

