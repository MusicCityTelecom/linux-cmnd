/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="PickUpDropOffType")
@XmlEnum
public enum PickUpDropOffType {
    AIRPORT("Airport"),
    PROPERTY("Property"),
    RESORT("Resort");

    private final String value;

    private PickUpDropOffType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static PickUpDropOffType fromValue(String v) {
        for (PickUpDropOffType c : PickUpDropOffType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

