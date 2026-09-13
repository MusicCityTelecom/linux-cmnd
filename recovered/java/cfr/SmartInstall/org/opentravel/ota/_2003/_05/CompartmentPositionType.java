/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="CompartmentPositionType")
@XmlEnum
public enum CompartmentPositionType {
    CLOSE_TO_RESTAURANT_CAR("CloseToRestaurantCar"),
    CLOSE_TO_EXIT("CloseToExit"),
    CLOSE_TO_TOILET("CloseToToilet"),
    MIDDLE_OF_CAR("MiddleOfCar");

    private final String value;

    private CompartmentPositionType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static CompartmentPositionType fromValue(String v) {
        for (CompartmentPositionType c : CompartmentPositionType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

