/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="LocationDetailRequirementInfoType")
@XmlEnum
public enum LocationDetailRequirementInfoType {
    ONE_WAY_RENTAL("OneWayRental"),
    GEOGRAPHIC("Geographic"),
    DROP_OFF("DropOff"),
    LICENSE("License"),
    INSURANCE("Insurance"),
    ELIGIBILITY("Eligibility"),
    MISCELLANEOUS("Miscellaneous");

    private final String value;

    private LocationDetailRequirementInfoType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static LocationDetailRequirementInfoType fromValue(String v) {
        for (LocationDetailRequirementInfoType c : LocationDetailRequirementInfoType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

