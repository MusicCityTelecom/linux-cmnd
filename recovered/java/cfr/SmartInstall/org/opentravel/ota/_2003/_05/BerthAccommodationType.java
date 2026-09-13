/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="BerthAccommodationType")
@XmlEnum
public enum BerthAccommodationType {
    NOT_SIGNIFICANT("NotSignificant"),
    BERTH("Berth"),
    COUCHETTE("Couchette"),
    SLEEPER("Sleeper");

    private final String value;

    private BerthAccommodationType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static BerthAccommodationType fromValue(String v) {
        for (BerthAccommodationType c : BerthAccommodationType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

