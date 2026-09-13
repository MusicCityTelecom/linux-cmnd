/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="CabinType")
@XmlEnum
public enum CabinType {
    COCKPIT("Cockpit"),
    SUITE("Suite"),
    FIRST("First"),
    PREMIUM_BUSINESS("PremiumBusiness"),
    BUSINESS("Business"),
    PREMIUM_ECONOMY("PremiumEconomy"),
    ECONOMY("Economy");

    private final String value;

    private CabinType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static CabinType fromValue(String v) {
        for (CabinType c : CabinType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

