/*
 * Decompiled with CFR 0.152.
 */
package org.htng._2011b;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="HTNG_EventFilterType")
@XmlEnum
public enum HTNGEventFilterType {
    APPLIED("Applied"),
    AVAILABLE("Available"),
    REQUESTED("Requested");

    private final String value;

    private HTNGEventFilterType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static HTNGEventFilterType fromValue(String v) {
        for (HTNGEventFilterType c : HTNGEventFilterType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

