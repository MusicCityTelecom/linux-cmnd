/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.CoverageType;
import org.opentravel.ota._2003._05.DeductibleType;
import org.opentravel.ota._2003._05.VehicleChargeType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="CoveragePricedType", propOrder={"coverage", "charge", "deductible"})
public class CoveragePricedType {
    @XmlElement(name="Coverage", required=true)
    protected CoverageType coverage;
    @XmlElement(name="Charge", required=true)
    protected VehicleChargeType charge;
    @XmlElement(name="Deductible")
    protected DeductibleType deductible;
    @XmlAttribute(name="Required")
    protected Boolean required;

    public CoverageType getCoverage() {
        return this.coverage;
    }

    public void setCoverage(CoverageType value) {
        this.coverage = value;
    }

    public VehicleChargeType getCharge() {
        return this.charge;
    }

    public void setCharge(VehicleChargeType value) {
        this.charge = value;
    }

    public DeductibleType getDeductible() {
        return this.deductible;
    }

    public void setDeductible(DeductibleType value) {
        this.deductible = value;
    }

    public Boolean isRequired() {
        return this.required;
    }

    public void setRequired(Boolean value) {
        this.required = value;
    }
}

