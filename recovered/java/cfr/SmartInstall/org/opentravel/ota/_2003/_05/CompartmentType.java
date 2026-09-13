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
import org.opentravel.ota._2003._05.CompartmentTypeEnum;
import org.opentravel.ota._2003._05.RailAccommDetailType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="CompartmentType", propOrder={"value"})
@XmlSeeAlso(value={RailAccommDetailType.Compartment.class})
public class CompartmentType {
    @XmlValue
    protected CompartmentTypeEnum value;
    @XmlAttribute(name="extension")
    protected String extension;

    public CompartmentTypeEnum getValue() {
        return this.value;
    }

    public void setValue(CompartmentTypeEnum value) {
        this.value = value;
    }

    public String getExtension() {
        return this.extension;
    }

    public void setExtension(String value) {
        this.extension = value;
    }
}

