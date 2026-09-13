/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="ActionCodeType")
@XmlEnum
public enum ActionCodeType {
    OK("OK"),
    WAITLIST("Waitlist"),
    OTHER("Other"),
    CANCEL("Cancel"),
    NEED("Need");

    private final String value;

    private ActionCodeType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static ActionCodeType fromValue(String v) {
        for (ActionCodeType c : ActionCodeType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

