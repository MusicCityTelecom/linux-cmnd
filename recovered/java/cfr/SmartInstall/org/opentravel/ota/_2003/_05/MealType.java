/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="MealType")
@XmlEnum
public enum MealType {
    AVML,
    BBML,
    BLML,
    CHML,
    DBML,
    FPML,
    GFML,
    HFML,
    HNML,
    KSML,
    LCML,
    LFML,
    LPML,
    LSML,
    MOML,
    NLML,
    ORML,
    PRML,
    RVML,
    SFML,
    SPML,
    VGML,
    VLML,
    RGML;


    public String value() {
        return this.name();
    }

    public static MealType fromValue(String v) {
        return MealType.valueOf(v);
    }
}

