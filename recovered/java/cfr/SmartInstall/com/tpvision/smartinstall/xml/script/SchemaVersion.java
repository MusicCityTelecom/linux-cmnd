/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.xml.script;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="SchemaVersion")
@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(propOrder={"majorVerNo", "minorVerNo"})
public class SchemaVersion {
    @XmlAttribute(name="MajorVerNo", required=false)
    protected String majorVerNo;
    @XmlAttribute(name="MinorVerNo", required=false)
    protected String minorVerNo;

    public String getMajorVerNo() {
        return this.majorVerNo;
    }

    public void setMajorVerNo(String majorVerNo) {
        this.majorVerNo = majorVerNo;
    }

    public String getMinorVerNo() {
        return this.minorVerNo;
    }

    public void setMinorVerNo(String minorVerNo) {
        this.minorVerNo = minorVerNo;
    }
}

