/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="PurposeType")
@XmlEnum
public enum PurposeType {
    SELL("Sell"),
    NET("Net"),
    BASE("Base"),
    REFUND("Refund"),
    ADDITIONAL("Additional");

    private final String value;

    private PurposeType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static PurposeType fromValue(String v) {
        for (PurposeType c : PurposeType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

