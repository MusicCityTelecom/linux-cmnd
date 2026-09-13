/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import org.opentravel.ota._2003._05.RailPassengerOccupationEnum;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="RailPassengerOccupationType", propOrder={"value"})
public class RailPassengerOccupationType {
    @XmlValue
    protected RailPassengerOccupationEnum value;
    @XmlAttribute(name="extension")
    protected String extension;

    public RailPassengerOccupationEnum getValue() {
        return this.value;
    }

    public void setValue(RailPassengerOccupationEnum value) {
        this.value = value;
    }

    public String getExtension() {
        return this.extension;
    }

    public void setExtension(String value) {
        this.extension = value;
    }
}

