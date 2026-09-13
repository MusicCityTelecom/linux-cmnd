/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="VehicleTransmissionType")
@XmlEnum
public enum VehicleTransmissionType {
    AUTOMATIC("Automatic"),
    MANUAL("Manual");

    private final String value;

    private VehicleTransmissionType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static VehicleTransmissionType fromValue(String v) {
        for (VehicleTransmissionType c : VehicleTransmissionType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

