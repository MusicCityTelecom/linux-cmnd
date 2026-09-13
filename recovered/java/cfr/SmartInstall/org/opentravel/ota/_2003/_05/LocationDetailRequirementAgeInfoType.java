/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="LocationDetailRequirementAgeInfoType")
@XmlEnum
public enum LocationDetailRequirementAgeInfoType {
    MINIMUM_AGE("MinimumAge"),
    MINIMUM_AGE_EXCEPTIONS("MinimumAgeExceptions"),
    MISCELLANEOUS("Miscellaneous");

    private final String value;

    private LocationDetailRequirementAgeInfoType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static LocationDetailRequirementAgeInfoType fromValue(String v) {
        for (LocationDetailRequirementAgeInfoType c : LocationDetailRequirementAgeInfoType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

