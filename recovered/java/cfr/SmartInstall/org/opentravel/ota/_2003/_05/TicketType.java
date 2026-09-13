/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="TicketType")
@XmlEnum
public enum TicketType {
    E_TICKET("eTicket"),
    PAPER("Paper"),
    MCO("MCO");

    private final String value;

    private TicketType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static TicketType fromValue(String v) {
        for (TicketType c : TicketType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

