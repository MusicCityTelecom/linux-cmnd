/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="DisplayOrderType")
@XmlEnum
public enum DisplayOrderType {
    BY_DEPARTURE_TIME("ByDepartureTime"),
    BY_ARRIVAL_TIME("ByArrivalTime"),
    BY_JOURNEY_TIME("ByJourneyTime"),
    BY_PRICE_HIGH_TO_LOW("ByPriceHighToLow"),
    BY_PRICE_LOW_TO_HIGH("ByPriceLowToHigh");

    private final String value;

    private DisplayOrderType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static DisplayOrderType fromValue(String v) {
        for (DisplayOrderType c : DisplayOrderType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

