/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.xml;

import com.tpvision.smartinstall.xml.Crc;
import com.tpvision.smartinstall.xml.DateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"dateFormat", "crc"})
@XmlRootElement(name="crcFile")
public class CrcFile {
    @XmlElement(required=true)
    protected DateFormat dateFormat;
    @XmlElement(required=true)
    protected List<Crc> crc;
    @XmlAttribute(name="name", required=true)
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    @XmlSchemaType(name="NCName")
    protected String name;

    public DateFormat getDateFormat() {
        return this.dateFormat;
    }

    public void setDateFormat(DateFormat value) {
        this.dateFormat = value;
    }

    public List<Crc> getCrc() {
        if (null == this.crc) {
            this.crc = new ArrayList<Crc>();
        }
        return this.crc;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }
}

