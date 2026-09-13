/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="CompartmentTypeEnum")
@XmlEnum
public enum CompartmentTypeEnum {
    NOT_SIGNIFICANT("NotSignificant"),
    FAMILY("Family"),
    QUITE("Quite"),
    CONFERENCE("Conference"),
    COMPARTMENT_WITHOUT_ANIMALS("CompartmentWithoutAnimals"),
    COMPLETE("Complete"),
    VIDEO("Video"),
    PRAM("Pram"),
    WOMAN_AND_CHILD("WomanAndChild"),
    EASY_ACCESS("EasyAccess"),
    T_2("T2"),
    T_3("T3"),
    T_4("T4"),
    T_6("T6"),
    C_2("C2"),
    C_4("C4"),
    C_5("C5"),
    C_6("C6"),
    SINGLE("Single"),
    DOUBLE("Double"),
    SINGLE_SUITE("SingleSuite"),
    DOUBLE_SUITE("DoubleSuite"),
    SPECIAL("Special"),
    OTHER("Other_");

    private final String value;

    private CompartmentTypeEnum(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static CompartmentTypeEnum fromValue(String v) {
        for (CompartmentTypeEnum c : CompartmentTypeEnum.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

