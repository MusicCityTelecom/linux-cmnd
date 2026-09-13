/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="GlobalIndicatorType")
@XmlEnum
public enum GlobalIndicatorType {
    AP,
    AT,
    CT,
    DO,
    EH,
    FE,
    PA,
    PN,
    PO,
    RU,
    RW,
    SA,
    TS,
    WH;


    public String value() {
        return this.name();
    }

    public static GlobalIndicatorType fromValue(String v) {
        return GlobalIndicatorType.valueOf(v);
    }
}

