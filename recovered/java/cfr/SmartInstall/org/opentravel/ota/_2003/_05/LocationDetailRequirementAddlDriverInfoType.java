/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="LocationDetailRequirementAddlDriverInfoType")
@XmlEnum
public enum LocationDetailRequirementAddlDriverInfoType {
    INCLUDED_AUTHORIZED("IncludedAuthorized"),
    ADDITIONAL_AUTHORIZED("AdditionalAuthorized"),
    FEES("Fees"),
    MISCELLANEOUS("Miscellaneous");

    private final String value;

    private LocationDetailRequirementAddlDriverInfoType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static LocationDetailRequirementAddlDriverInfoType fromValue(String v) {
        for (LocationDetailRequirementAddlDriverInfoType c : LocationDetailRequirementAddlDriverInfoType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

