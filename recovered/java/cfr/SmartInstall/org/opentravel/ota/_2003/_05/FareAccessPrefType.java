/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="FareAccessPrefType")
@XmlEnum
public enum FareAccessPrefType {
    POINT_TO_POINT("PointToPoint"),
    THROUGH("Through"),
    JOINT("Joint"),
    PRIVATE("Private"),
    NEGOTIATED("Negotiated"),
    NET("Net"),
    HISTORICAL("Historical"),
    SECURATE_AIR("SecurateAir"),
    MONEYSAVER("Moneysaver"),
    MONEYSAVER_ROUNDTRIP("MoneysaverRoundtrip"),
    MONEYSAVER_NO_ONE_WAY("MoneysaverNoOneWay"),
    MONEYSAVER_ONE_WAY_ONLY("MoneysaverOneWayOnly");

    private final String value;

    private FareAccessPrefType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static FareAccessPrefType fromValue(String v) {
        for (FareAccessPrefType c : FareAccessPrefType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

