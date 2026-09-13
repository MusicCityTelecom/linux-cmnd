/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="OfficeLocationType")
@XmlEnum
public enum OfficeLocationType {
    MAIN("Main"),
    FIELD("Field"),
    DIVISION("Division"),
    REGIONAL("Regional"),
    REMOTE("Remote");

    private final String value;

    private OfficeLocationType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static OfficeLocationType fromValue(String v) {
        for (OfficeLocationType c : OfficeLocationType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

