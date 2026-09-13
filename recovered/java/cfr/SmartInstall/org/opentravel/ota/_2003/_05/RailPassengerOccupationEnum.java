/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="RailPassengerOccupationEnum")
@XmlEnum
public enum RailPassengerOccupationEnum {
    NOT_SIGNIFICANT("NotSignificant"),
    RAIL_EMPLOYEE("RailEmployee"),
    GOVERNMENT_EMPLOYEE("GovernmentEmployee"),
    FARMER("Farmer"),
    MILITARY("Military"),
    JOURNALIST("Journalist"),
    STUDENT("Student"),
    VIP("VIP"),
    OTHER("Other_");

    private final String value;

    private RailPassengerOccupationEnum(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static RailPassengerOccupationEnum fromValue(String v) {
        for (RailPassengerOccupationEnum c : RailPassengerOccupationEnum.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

