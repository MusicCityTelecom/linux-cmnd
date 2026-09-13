/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.VehicleChargeType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="VehicleChargePurposeType")
public class VehicleChargePurposeType
extends VehicleChargeType {
    @XmlAttribute(name="Purpose", required=true)
    protected String purpose;
    @XmlAttribute(name="RequiredInd")
    protected Boolean requiredInd;

    public String getPurpose() {
        return this.purpose;
    }

    public void setPurpose(String value) {
        this.purpose = value;
    }

    public Boolean isRequiredInd() {
        return this.requiredInd;
    }

    public void setRequiredInd(Boolean value) {
        this.requiredInd = value;
    }
}

