/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="PricingType")
@XmlEnum
public enum PricingType {
    PER_STAY("Per stay"),
    PER_PERSON("Per person"),
    PER_NIGHT("Per night"),
    PER_PERSON_PER_NIGHT("Per person per night"),
    PER_USE("Per use");

    private final String value;

    private PricingType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static PricingType fromValue(String v) {
        for (PricingType c : PricingType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

