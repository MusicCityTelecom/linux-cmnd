/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.xml.setting.v2k16.roomspecific;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="")
@XmlRootElement(name="SchemaVersion")
public class SchemaVersion {
    @XmlAttribute(name="MajorVerNo", required=true)
    protected BigInteger majorVerNo;
    @XmlAttribute(name="MinorVerNo", required=true)
    protected BigInteger minorVerNo;

    public BigInteger getMajorVerNo() {
        return this.majorVerNo;
    }

    public void setMajorVerNo(BigInteger value) {
        this.majorVerNo = value;
    }

    public BigInteger getMinorVerNo() {
        return this.minorVerNo;
    }

    public void setMinorVerNo(BigInteger value) {
        this.minorVerNo = value;
    }
}

