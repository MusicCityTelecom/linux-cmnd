/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="PkgPersonalInsuranceCode")
@XmlEnum
public enum PkgPersonalInsuranceCode {
    SKI("Ski"),
    WORLDWIDE("Worldwide"),
    EUROPE("Europe");

    private final String value;

    private PkgPersonalInsuranceCode(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static PkgPersonalInsuranceCode fromValue(String v) {
        for (PkgPersonalInsuranceCode c : PkgPersonalInsuranceCode.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

