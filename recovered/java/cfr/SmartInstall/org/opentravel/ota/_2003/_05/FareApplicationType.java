/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="FareApplicationType")
@XmlEnum
public enum FareApplicationType {
    ONE_WAY("OneWay"),
    RETURN("Return"),
    HALF_RETURN("HalfReturn"),
    ROUNDTRIP("Roundtrip"),
    ONE_WAY_ONLY("OneWayOnly");

    private final String value;

    private FareApplicationType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static FareApplicationType fromValue(String v) {
        for (FareApplicationType c : FareApplicationType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

