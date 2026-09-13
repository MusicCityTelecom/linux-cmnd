/*
 * Decompiled with CFR 0.152.
 */
package org.htng._2011b;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="HTNG_ProfileMessageStatusType")
@XmlEnum
public enum HTNGProfileMessageStatusType {
    NEW("New"),
    VIEWED("Viewed"),
    DELETED("Deleted");

    private final String value;

    private HTNGProfileMessageStatusType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static HTNGProfileMessageStatusType fromValue(String v) {
        for (HTNGProfileMessageStatusType c : HTNGProfileMessageStatusType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

