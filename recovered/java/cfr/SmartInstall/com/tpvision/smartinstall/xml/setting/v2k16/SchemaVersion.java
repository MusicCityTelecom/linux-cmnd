/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.xml.setting.v2k16;

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
    protected String majorVerNo;
    @XmlAttribute(name="MinorVerNo", required=true)
    protected String minorVerNo;

    public String getMajorVerNo() {
        return this.majorVerNo;
    }

    public void setMajorVerNo(String value) {
        this.majorVerNo = value;
    }

    public String getMinorVerNo() {
        return this.minorVerNo;
    }

    public void setMinorVerNo(String value) {
        this.minorVerNo = value;
    }
}

