/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="PricingSourceType")
@XmlEnum
public enum PricingSourceType {
    PUBLISHED("Published"),
    PRIVATE("Private"),
    BOTH("Both");

    private final String value;

    private PricingSourceType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static PricingSourceType fromValue(String v) {
        for (PricingSourceType c : PricingSourceType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

