/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="CoverageTextType")
@XmlEnum
public enum CoverageTextType {
    SUPPLEMENT("Supplement"),
    DESCRIPTION("Description"),
    LIMITS("Limits");

    private final String value;

    private CoverageTextType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static CoverageTextType fromValue(String v) {
        for (CoverageTextType c : CoverageTextType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

