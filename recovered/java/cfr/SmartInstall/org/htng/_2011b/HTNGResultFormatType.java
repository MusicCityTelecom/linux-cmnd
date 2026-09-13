/*
 * Decompiled with CFR 0.152.
 */
package org.htng._2011b;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="HTNG_ResultFormatType")
@XmlEnum
public enum HTNGResultFormatType {
    XML("XML"),
    CSV("CSV"),
    PLAIN_TEXT("PlainText"),
    BASE_64_BINARY("Base64Binary");

    private final String value;

    private HTNGResultFormatType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static HTNGResultFormatType fromValue(String v) {
        for (HTNGResultFormatType c : HTNGResultFormatType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

