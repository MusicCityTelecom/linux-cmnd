/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="DestinationLevelType")
@XmlEnum
public enum DestinationLevelType {
    CONTINENT("Continent"),
    COUNTRY("Country"),
    STATE("State"),
    AREA("Area"),
    SUB_AREA("SubArea"),
    RESORT("Resort"),
    DISTRICT("District"),
    REGION("Region");

    private final String value;

    private DestinationLevelType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static DestinationLevelType fromValue(String v) {
        for (DestinationLevelType c : DestinationLevelType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

