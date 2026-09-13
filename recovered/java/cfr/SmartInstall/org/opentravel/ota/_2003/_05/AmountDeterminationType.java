/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="AmountDeterminationType")
@XmlEnum
public enum AmountDeterminationType {
    INCLUSIVE("Inclusive"),
    EXCLUSIVE("Exclusive"),
    CUMULATIVE("Cumulative");

    private final String value;

    private AmountDeterminationType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static AmountDeterminationType fromValue(String v) {
        for (AmountDeterminationType c : AmountDeterminationType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

