/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="PMS_ResStatusType")
@XmlEnum
public enum PMSResStatusType {
    RESERVED("Reserved"),
    REQUESTED("Requested"),
    REQUEST_DENIED("Request denied"),
    NO_SHOW("No-show"),
    CANCELLED("Cancelled"),
    IN_HOUSE("In-house"),
    CHECKED_OUT("Checked out"),
    WAITLISTED("Waitlisted");

    private final String value;

    private PMSResStatusType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static PMSResStatusType fromValue(String v) {
        for (PMSResStatusType c : PMSResStatusType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

