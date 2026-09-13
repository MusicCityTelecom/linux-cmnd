/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="TrainPolicyCodeType")
@XmlEnum
public enum TrainPolicyCodeType {
    MINIMUM("Minimum"),
    MAXIMUM("Maximum");

    private final String value;

    private TrainPolicyCodeType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static TrainPolicyCodeType fromValue(String v) {
        for (TrainPolicyCodeType c : TrainPolicyCodeType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

