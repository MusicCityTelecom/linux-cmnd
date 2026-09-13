/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="FlightTypeType")
@XmlEnum
public enum FlightTypeType {
    NONSTOP("Nonstop"),
    DIRECT("Direct"),
    CONNECTION("Connection"),
    SINGLE_CONNECTION("SingleConnection"),
    DOUBLE_CONNECTION("DoubleConnection"),
    ONE_STOP_ONLY("OneStopOnly");

    private final String value;

    private FlightTypeType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static FlightTypeType fromValue(String v) {
        for (FlightTypeType c : FlightTypeType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

