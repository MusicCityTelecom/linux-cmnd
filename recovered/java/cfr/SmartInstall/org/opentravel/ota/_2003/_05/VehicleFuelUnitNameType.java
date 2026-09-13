/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="VehicleFuelUnitNameType")
@XmlEnum
public enum VehicleFuelUnitNameType {
    GALLON("Gallon"),
    LITER("Liter");

    private final String value;

    private VehicleFuelUnitNameType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static VehicleFuelUnitNameType fromValue(String v) {
        for (VehicleFuelUnitNameType c : VehicleFuelUnitNameType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

