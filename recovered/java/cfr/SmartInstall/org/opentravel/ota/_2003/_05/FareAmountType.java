/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="FareAmountType")
@XmlEnum
public enum FareAmountType {
    NOADC("NOADC"),
    BULK("Bulk"),
    IT("IT");

    private final String value;

    private FareAmountType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static FareAmountType fromValue(String v) {
        for (FareAmountType c : FareAmountType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

