/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.AddressType;
import org.opentravel.ota._2003._05.AddressesType;
import org.opentravel.ota._2003._05.CompanyInfoType;
import org.opentravel.ota._2003._05.CustomerType;
import org.opentravel.ota._2003._05.RequiredPaymentsType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="AddressInfoType")
@XmlSeeAlso(value={CustomerType.Address.class, CompanyInfoType.AddressInfo.class, AddressesType.Address.class, RequiredPaymentsType.GuaranteePayment.Address.class})
public class AddressInfoType
extends AddressType {
    @XmlAttribute(name="UseType")
    protected String useType;
    @XmlAttribute(name="RPH")
    protected String rph;
    @XmlAttribute(name="DefaultInd")
    protected Boolean defaultInd;

    public String getUseType() {
        return this.useType;
    }

    public void setUseType(String value) {
        this.useType = value;
    }

    public String getRPH() {
        return this.rph;
    }

    public void setRPH(String value) {
        this.rph = value;
    }

    public Boolean isDefaultInd() {
        return this.defaultInd;
    }

    public void setDefaultInd(Boolean value) {
        this.defaultInd = value;
    }
}

