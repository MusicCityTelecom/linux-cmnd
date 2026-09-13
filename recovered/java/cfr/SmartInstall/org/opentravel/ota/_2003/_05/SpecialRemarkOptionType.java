/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="SpecialRemarkOptionType")
@XmlEnum
public enum SpecialRemarkOptionType {
    ITINERARY("Itinerary"),
    INVOICE("Invoice"),
    ENDORSEMENT("Endorsement"),
    SAVE("Save"),
    CONFIDENTIAL("Confidential"),
    FREE("Free"),
    GRMS("GRMS"),
    SPLIT("Split");

    private final String value;

    private SpecialRemarkOptionType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static SpecialRemarkOptionType fromValue(String v) {
        for (SpecialRemarkOptionType c : SpecialRemarkOptionType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

