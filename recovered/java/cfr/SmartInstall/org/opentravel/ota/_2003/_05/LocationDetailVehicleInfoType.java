/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="LocationDetailVehicleInfoType")
@XmlEnum
public enum LocationDetailVehicleInfoType {
    GENERAL_INFORMATION("GeneralInformation"),
    DISCLAIMER("Disclaimer"),
    ADVANCED_BOOKING("AdvancedBooking"),
    NON_SMOKING_VEHICLES("NonSmokingVehicles"),
    SPECIALITY_VEHICLES("SpecialityVehicles");

    private final String value;

    private LocationDetailVehicleInfoType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static LocationDetailVehicleInfoType fromValue(String v) {
        for (LocationDetailVehicleInfoType c : LocationDetailVehicleInfoType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

