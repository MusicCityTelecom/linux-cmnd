/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.OffLocationServiceType;
import org.opentravel.ota._2003._05.VehicleChargeType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="OffLocationServicePricedType", propOrder={"offLocService", "charge"})
public class OffLocationServicePricedType {
    @XmlElement(name="OffLocService", required=true)
    protected OffLocationServiceType offLocService;
    @XmlElement(name="Charge")
    protected VehicleChargeType charge;

    public OffLocationServiceType getOffLocService() {
        return this.offLocService;
    }

    public void setOffLocService(OffLocationServiceType value) {
        this.offLocService = value;
    }

    public VehicleChargeType getCharge() {
        return this.charge;
    }

    public void setCharge(VehicleChargeType value) {
        this.charge = value;
    }
}

