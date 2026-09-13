/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="YesNoType")
@XmlEnum
public enum YesNoType {
    YES("Yes"),
    NO("No");

    private final String value;

    private YesNoType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static YesNoType fromValue(String v) {
        for (YesNoType c : YesNoType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

