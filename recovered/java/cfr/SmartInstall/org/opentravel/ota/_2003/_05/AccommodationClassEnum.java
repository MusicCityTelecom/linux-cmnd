/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="AccommodationClassEnum")
@XmlEnum
public enum AccommodationClassEnum {
    FIRST_CLASS("FirstClass"),
    SECOND_CLASS("SecondClass"),
    PREMIUM("Premium"),
    BUSINESS("Business"),
    LEISURE("Leisure"),
    COACH("Coach"),
    DELUXE("Deluxe"),
    GRAN_CLASSE("GranClasse"),
    SOFT_CLASS("SoftClass"),
    HARD_CLASS("HardClass"),
    SPECIAL_CLASS("SpecialClass"),
    HIGH_GRADE_SOFT_CLASS("HighGradeSoftClass"),
    MIXED_HARD_CLASS("MixedHardClass"),
    MIXED_SOFT_CLASS("MixedSoftClass"),
    SOFT_COMPARTMENT_CLASS("SoftCompartmentClass"),
    HARD_COMPARTMENT_CLASS("HardCompartmentClass"),
    OTHER("Other_");

    private final String value;

    private AccommodationClassEnum(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static AccommodationClassEnum fromValue(String v) {
        for (AccommodationClassEnum c : AccommodationClassEnum.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

