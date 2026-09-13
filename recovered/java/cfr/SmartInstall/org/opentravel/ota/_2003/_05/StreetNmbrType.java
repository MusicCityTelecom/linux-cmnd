/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import org.opentravel.ota._2003._05.AddressType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="StreetNmbrType", propOrder={"value"})
@XmlSeeAlso(value={AddressType.StreetNmbr.class})
public class StreetNmbrType {
    @XmlValue
    protected String value;
    @XmlAttribute(name="PO_Box")
    protected String poBox;

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPOBox() {
        return this.poBox;
    }

    public void setPOBox(String value) {
        this.poBox = value;
    }
}

