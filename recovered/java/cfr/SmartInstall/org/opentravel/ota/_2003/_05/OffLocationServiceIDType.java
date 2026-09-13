/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="OffLocationServiceID_Type")
@XmlEnum
public enum OffLocationServiceIDType {
    CUST_PICK_UP("CustPickUp"),
    VEH_DELIVERY("VehDelivery"),
    CUST_DROP_OFF("CustDropOff"),
    VEH_COLLECTION("VehCollection"),
    EXCHANGE("Exchange"),
    REPAIR_LOCATION("RepairLocation");

    private final String value;

    private OffLocationServiceIDType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static OffLocationServiceIDType fromValue(String v) {
        for (OffLocationServiceIDType c : OffLocationServiceIDType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

