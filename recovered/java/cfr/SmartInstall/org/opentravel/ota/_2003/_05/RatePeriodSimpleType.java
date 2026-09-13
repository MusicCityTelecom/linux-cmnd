/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="RatePeriodSimpleType")
@XmlEnum
public enum RatePeriodSimpleType {
    HOURLY("Hourly"),
    DAILY("Daily"),
    WEEKLY("Weekly"),
    MONTHLY("Monthly"),
    WEEKEND_DAY("WeekendDay"),
    OTHER("Other"),
    PACKAGE("Package"),
    BUNDLE("Bundle"),
    TOTAL("Total");

    private final String value;

    private RatePeriodSimpleType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static RatePeriodSimpleType fromValue(String v) {
        for (RatePeriodSimpleType c : RatePeriodSimpleType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

