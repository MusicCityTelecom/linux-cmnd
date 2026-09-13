/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="VehiclePeriodUnitNameType")
@XmlEnum
public enum VehiclePeriodUnitNameType {
    RENTAL_PERIOD("RentalPeriod"),
    YEAR("Year"),
    MONTH("Month"),
    WEEK("Week"),
    DAY("Day"),
    HOUR("Hour"),
    WEEKEND("Weekend"),
    EXTRA_MONTH("ExtraMonth"),
    BUNDLE("Bundle"),
    PACKAGE("Package"),
    EXTRA_DAY("ExtraDay"),
    EXTRA_HOUR("ExtraHour"),
    EXTRA_WEEK("ExtraWeek");

    private final String value;

    private VehiclePeriodUnitNameType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static VehiclePeriodUnitNameType fromValue(String v) {
        for (VehiclePeriodUnitNameType c : VehiclePeriodUnitNameType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

