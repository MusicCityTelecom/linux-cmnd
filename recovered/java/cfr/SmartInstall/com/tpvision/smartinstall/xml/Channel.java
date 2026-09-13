/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="")
@XmlRootElement(name="channel")
public class Channel {
    @XmlAttribute(name="fileName", required=true)
    protected String fileName;
    @XmlAttribute(name="folderName", required=true)
    @XmlSchemaType(name="anySimpleType")
    protected String folderName;
    @XmlAttribute(name="isMust", required=true)
    protected boolean isMust;
    @XmlAttribute(name="xsdVer", required=true)
    protected String xsdVer;

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String value) {
        this.fileName = value;
    }

    public String getFolderName() {
        return this.folderName;
    }

    public void setFolderName(String value) {
        this.folderName = value;
    }

    public boolean isIsMust() {
        return this.isMust;
    }

    public void setIsMust(boolean value) {
        this.isMust = value;
    }

    public String getXsdVer() {
        return this.xsdVer;
    }

    public void setXsdVer(String value) {
        this.xsdVer = value;
    }
}

