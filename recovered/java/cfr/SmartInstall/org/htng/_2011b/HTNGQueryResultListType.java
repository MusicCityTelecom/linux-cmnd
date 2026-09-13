/*
 * Decompiled with CFR 0.152.
 */
package org.htng._2011b;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="HTNG_QueryResultListType")
@XmlEnum
public enum HTNGQueryResultListType {
    ALL_ASCENDING("All Ascending"),
    ALL_DESCENDING("All Descending"),
    TOP_ASCENDING("Top Ascending"),
    TOP_DESCENDING("Top Descending"),
    BYTES("Bytes");

    private final String value;

    private HTNGQueryResultListType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static HTNGQueryResultListType fromValue(String v) {
        for (HTNGQueryResultListType c : HTNGQueryResultListType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

