/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="TransferActionType")
@XmlEnum
public enum TransferActionType {
    AUTOMATIC("Automatic"),
    MANDATORY("Mandatory"),
    SELECTABLE("Selectable");

    private final String value;

    private TransferActionType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static TransferActionType fromValue(String v) {
        for (TransferActionType c : TransferActionType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

