/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="DeckType")
@XmlEnum
public enum DeckType {
    REGULAR_ONE_LEVEL_ONLY("Regular-OneLevelOnly"),
    LOWER_LEVEL("LowerLevel"),
    UPPER_LEVEL("UpperLevel");

    private final String value;

    private DeckType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static DeckType fromValue(String v) {
        for (DeckType c : DeckType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

