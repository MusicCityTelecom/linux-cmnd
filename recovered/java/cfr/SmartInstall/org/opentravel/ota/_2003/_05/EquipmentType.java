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
import org.opentravel.ota._2003._05.EquipmentTypePref;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="EquipmentType", propOrder={"value"})
@XmlSeeAlso(value={EquipmentTypePref.class})
public class EquipmentType {
    @XmlValue
    protected String value;
    @XmlAttribute(name="AirEquipType")
    protected String airEquipType;
    @XmlAttribute(name="ChangeofGauge")
    protected Boolean changeofGauge;

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getAirEquipType() {
        return this.airEquipType;
    }

    public void setAirEquipType(String value) {
        this.airEquipType = value;
    }

    public Boolean isChangeofGauge() {
        return this.changeofGauge;
    }

    public void setChangeofGauge(Boolean value) {
        this.changeofGauge = value;
    }
}

