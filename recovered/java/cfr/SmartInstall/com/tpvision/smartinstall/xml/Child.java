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
@XmlRootElement(name="child")
public class Child {
    @XmlAttribute(name="folderPath", required=true)
    @XmlSchemaType(name="anySimpleType")
    protected String folderPath;
    @XmlAttribute(name="id", required=true)
    @XmlSchemaType(name="anySimpleType")
    protected String id;

    public String getFolderPath() {
        return this.folderPath;
    }

    public void setFolderPath(String value) {
        this.folderPath = value;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String value) {
        this.id = value;
    }
}

