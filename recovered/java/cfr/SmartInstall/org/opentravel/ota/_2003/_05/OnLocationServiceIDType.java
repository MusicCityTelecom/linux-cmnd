/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="OnLocationServiceID_Type")
@XmlEnum
public enum OnLocationServiceIDType {
    COMPUTER_DRIVING_DIRECTIONS("ComputerDrivingDirections"),
    EXPRESS_RETURN_SERVICE("ExpressReturnService"),
    SPECIAL_NEEDS("SpecialNeeds"),
    FREQUENT_RENTER("FrequentRenter"),
    MISCELLANEOUS("Miscellaneous");

    private final String value;

    private OnLocationServiceIDType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static OnLocationServiceIDType fromValue(String v) {
        for (OnLocationServiceIDType c : OnLocationServiceIDType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

