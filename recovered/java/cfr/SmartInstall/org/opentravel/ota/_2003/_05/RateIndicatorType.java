/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="RateIndicatorType")
@XmlEnum
public enum RateIndicatorType {
    CHANGE_DURING_STAY("ChangeDuringStay"),
    MULTIPLE_NIGHTS("MultipleNights"),
    EXCLUSIVE("Exclusive"),
    ON_REQUEST("OnRequest"),
    LIMITED_AVAILABILITY("LimitedAvailability"),
    AVAILABLE_FOR_SALE("AvailableForSale"),
    CLOSED_OUT("ClosedOut"),
    OTHER_AVAILABLE("OtherAvailable"),
    UNABLE_TO_PROCESS("UnableToProcess"),
    NO_AVAILABILITY("NoAvailability"),
    ROOM_TYPE_CLOSED("RoomTypeClosed"),
    RATE_PLAN_CLOSED("RatePlanClosed"),
    LOS_RESTRICTED("LOS_Restricted"),
    RESTRICTED("Restricted"),
    DOES_NOT_EXIST("DoesNotExist");

    private final String value;

    private RateIndicatorType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static RateIndicatorType fromValue(String v) {
        for (RateIndicatorType c : RateIndicatorType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

