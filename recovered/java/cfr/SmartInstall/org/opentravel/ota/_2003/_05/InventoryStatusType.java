/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="InventoryStatusType")
@XmlEnum
public enum InventoryStatusType {
    AVAILABLE("Available"),
    UNAVAILABLE("Unavailable"),
    ON_REQUEST("OnRequest"),
    CONFIRMED("Confirmed"),
    ALL("All"),
    WAITLIST("Waitlist"),
    SUPPLIER_BOOKED("SupplierBooked");

    private final String value;

    private InventoryStatusType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static InventoryStatusType fromValue(String v) {
        for (InventoryStatusType c : InventoryStatusType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

