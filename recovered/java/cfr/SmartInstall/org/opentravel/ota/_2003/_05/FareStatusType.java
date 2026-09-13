/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="FareStatusType")
@XmlEnum
public enum FareStatusType {
    CONSTRUCTED("constructed"),
    PUBLISHED("published"),
    CREATED("created"),
    FARE_BY_RULE("fareByRule"),
    FARE_BY_RULE_PRIVATE("fareByRulePrivate");

    private final String value;

    private FareStatusType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static FareStatusType fromValue(String v) {
        for (FareStatusType c : FareStatusType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

