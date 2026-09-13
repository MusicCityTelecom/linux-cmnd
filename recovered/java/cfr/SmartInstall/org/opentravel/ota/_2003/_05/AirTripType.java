/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="AirTripType")
@XmlEnum
public enum AirTripType {
    ONE_WAY("OneWay"),
    ONE_WAY_ONLY("OneWayOnly"),
    RETURN("Return"),
    CIRCLE("Circle"),
    OPEN_JAW("OpenJaw"),
    OTHER("Other"),
    OUTBOUND("Outbound"),
    OUTBOUND_SEASON_ROUNDTRIP("OutboundSeasonRoundtrip"),
    NON_DIRECTIONAL("Non-directional"),
    INBOUND("Inbound"),
    ROUNDTRIP("Roundtrip");

    private final String value;

    private AirTripType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static AirTripType fromValue(String v) {
        for (AirTripType c : AirTripType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

