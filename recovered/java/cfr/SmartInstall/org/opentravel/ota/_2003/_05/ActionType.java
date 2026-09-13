/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="ActionType")
@XmlEnum
public enum ActionType {
    ADD_UPDATE("Add-Update"),
    CANCEL("Cancel"),
    DELETE("Delete"),
    ADD("Add"),
    REPLACE("Replace");

    private final String value;

    private ActionType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static ActionType fromValue(String v) {
        for (ActionType c : ActionType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

