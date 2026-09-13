/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="AirTripDirectionType")
@XmlEnum
public enum AirTripDirectionType {
    OUTBOUND("Outbound"),
    RETURN("Return"),
    ALL("All");

    private final String value;

    private AirTripDirectionType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static AirTripDirectionType fromValue(String v) {
        for (AirTripDirectionType c : AirTripDirectionType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

