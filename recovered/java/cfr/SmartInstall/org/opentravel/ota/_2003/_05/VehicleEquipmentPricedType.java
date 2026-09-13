/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.VehicleChargeType;
import org.opentravel.ota._2003._05.VehicleEquipmentType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="VehicleEquipmentPricedType", propOrder={"equipment", "charge"})
public class VehicleEquipmentPricedType {
    @XmlElement(name="Equipment", required=true)
    protected VehicleEquipmentType equipment;
    @XmlElement(name="Charge", required=true)
    protected VehicleChargeType charge;
    @XmlAttribute(name="Required")
    protected Boolean required;

    public VehicleEquipmentType getEquipment() {
        return this.equipment;
    }

    public void setEquipment(VehicleEquipmentType value) {
        this.equipment = value;
    }

    public VehicleChargeType getCharge() {
        return this.charge;
    }

    public void setCharge(VehicleChargeType value) {
        this.charge = value;
    }

    public Boolean isRequired() {
        return this.required;
    }

    public void setRequired(Boolean value) {
        this.required = value;
    }
}

