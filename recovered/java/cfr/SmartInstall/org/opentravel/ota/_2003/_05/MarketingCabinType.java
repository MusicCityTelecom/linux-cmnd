/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.CabinAvailabilityType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="MarketingCabinType")
public class MarketingCabinType
extends CabinAvailabilityType {
    @XmlAttribute(name="Name")
    protected String name;
    @XmlAttribute(name="RPH")
    protected String rph;

    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getRPH() {
        return this.rph;
    }

    public void setRPH(String value) {
        this.rph = value;
    }
}

