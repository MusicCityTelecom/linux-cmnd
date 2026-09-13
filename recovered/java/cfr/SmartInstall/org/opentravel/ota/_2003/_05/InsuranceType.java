/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;
import org.opentravel.ota._2003._05.CompanyNameType;
import org.opentravel.ota._2003._05.PersonNameType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="InsuranceType", propOrder={"insuredName", "insuranceCompany", "underwriter"})
public class InsuranceType {
    @XmlElement(name="InsuredName")
    protected PersonNameType insuredName;
    @XmlElement(name="InsuranceCompany")
    protected CompanyNameType insuranceCompany;
    @XmlElement(name="Underwriter")
    protected CompanyNameType underwriter;
    @XmlAttribute(name="InsuranceType")
    protected String insuranceType;
    @XmlAttribute(name="PolicyNumber", required=true)
    protected String policyNumber;
    @XmlAttribute(name="RPH")
    protected String rph;
    @XmlAttribute(name="EffectiveDate")
    @XmlSchemaType(name="date")
    protected XMLGregorianCalendar effectiveDate;
    @XmlAttribute(name="ExpireDate")
    @XmlSchemaType(name="date")
    protected XMLGregorianCalendar expireDate;
    @XmlAttribute(name="ExpireDateExclusiveIndicator")
    protected Boolean expireDateExclusiveIndicator;
    @XmlAttribute(name="ShareSynchInd")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    protected String shareSynchInd;
    @XmlAttribute(name="ShareMarketInd")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    protected String shareMarketInd;

    public PersonNameType getInsuredName() {
        return this.insuredName;
    }

    public void setInsuredName(PersonNameType value) {
        this.insuredName = value;
    }

    public CompanyNameType getInsuranceCompany() {
        return this.insuranceCompany;
    }

    public void setInsuranceCompany(CompanyNameType value) {
        this.insuranceCompany = value;
    }

    public CompanyNameType getUnderwriter() {
        return this.underwriter;
    }

    public void setUnderwriter(CompanyNameType value) {
        this.underwriter = value;
    }

    public String getInsuranceType() {
        return this.insuranceType;
    }

    public void setInsuranceType(String value) {
        this.insuranceType = value;
    }

    public String getPolicyNumber() {
        return this.policyNumber;
    }

    public void setPolicyNumber(String value) {
        this.policyNumber = value;
    }

    public String getRPH() {
        return this.rph;
    }

    public void setRPH(String value) {
        this.rph = value;
    }

    public XMLGregorianCalendar getEffectiveDate() {
        return this.effectiveDate;
    }

    public void setEffectiveDate(XMLGregorianCalendar value) {
        this.effectiveDate = value;
    }

    public XMLGregorianCalendar getExpireDate() {
        return this.expireDate;
    }

    public void setExpireDate(XMLGregorianCalendar value) {
        this.expireDate = value;
    }

    public Boolean isExpireDateExclusiveIndicator() {
        return this.expireDateExclusiveIndicator;
    }

    public void setExpireDateExclusiveIndicator(Boolean value) {
        this.expireDateExclusiveIndicator = value;
    }

    public String getShareSynchInd() {
        return this.shareSynchInd;
    }

    public void setShareSynchInd(String value) {
        this.shareSynchInd = value;
    }

    public String getShareMarketInd() {
        return this.shareMarketInd;
    }

    public void setShareMarketInd(String value) {
        this.shareMarketInd = value;
    }
}

