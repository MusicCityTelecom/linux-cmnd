/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="TravelPurposeEnum")
@XmlEnum
public enum TravelPurposeEnum {
    NOT_SIGNIFICANT("NotSignificant"),
    BUSINESS("Business"),
    PERSONAL("Personal"),
    GROUP("Group"),
    CONFERENCE("Conference"),
    CONSORTIUMS("Consortiums"),
    HOME_VISITING("HomeVisiting"),
    OTHER("Other_");

    private final String value;

    private TravelPurposeEnum(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static TravelPurposeEnum fromValue(String v) {
        for (TravelPurposeEnum c : TravelPurposeEnum.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

