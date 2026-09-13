/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import org.opentravel.ota._2003._05.CompanyNameType;
import org.opentravel.ota._2003._05.EmployeeInfoType;
import org.opentravel.ota._2003._05.FreeTextType;
import org.opentravel.ota._2003._05.LoyaltyProgramType;
import org.opentravel.ota._2003._05.OfficeLocationType;
import org.opentravel.ota._2003._05.TravelArrangerType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="EmployerType", propOrder={"companyName", "relatedEmployer", "employeeInfo", "internalRefNmbr", "travelArranger", "loyaltyProgram"})
public class EmployerType {
    @XmlElement(name="CompanyName")
    protected CompanyNameType companyName;
    @XmlElement(name="RelatedEmployer")
    protected List<CompanyNameType> relatedEmployer;
    @XmlElement(name="EmployeeInfo")
    protected List<EmployeeInfoType> employeeInfo;
    @XmlElement(name="InternalRefNmbr")
    protected List<FreeTextType> internalRefNmbr;
    @XmlElement(name="TravelArranger")
    protected List<TravelArrangerType> travelArranger;
    @XmlElement(name="LoyaltyProgram")
    protected List<LoyaltyProgramType> loyaltyProgram;
    @XmlAttribute(name="EffectiveDate")
    @XmlSchemaType(name="date")
    protected XMLGregorianCalendar effectiveDate;
    @XmlAttribute(name="ExpireDate")
    @XmlSchemaType(name="date")
    protected XMLGregorianCalendar expireDate;
    @XmlAttribute(name="ExpireDateExclusiveIndicator")
    protected Boolean expireDateExclusiveIndicator;
    @XmlAttribute(name="DefaultInd")
    protected Boolean defaultInd;
    @XmlAttribute(name="OfficeType")
    protected OfficeLocationType officeType;

    public CompanyNameType getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(CompanyNameType value) {
        this.companyName = value;
    }

    public List<CompanyNameType> getRelatedEmployer() {
        if (this.relatedEmployer == null) {
            this.relatedEmployer = new ArrayList<CompanyNameType>();
        }
        return this.relatedEmployer;
    }

    public List<EmployeeInfoType> getEmployeeInfo() {
        if (this.employeeInfo == null) {
            this.employeeInfo = new ArrayList<EmployeeInfoType>();
        }
        return this.employeeInfo;
    }

    public List<FreeTextType> getInternalRefNmbr() {
        if (this.internalRefNmbr == null) {
            this.internalRefNmbr = new ArrayList<FreeTextType>();
        }
        return this.internalRefNmbr;
    }

    public List<TravelArrangerType> getTravelArranger() {
        if (this.travelArranger == null) {
            this.travelArranger = new ArrayList<TravelArrangerType>();
        }
        return this.travelArranger;
    }

    public List<LoyaltyProgramType> getLoyaltyProgram() {
        if (this.loyaltyProgram == null) {
            this.loyaltyProgram = new ArrayList<LoyaltyProgramType>();
        }
        return this.loyaltyProgram;
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

    public Boolean isDefaultInd() {
        return this.defaultInd;
    }

    public void setDefaultInd(Boolean value) {
        this.defaultInd = value;
    }

    public OfficeLocationType getOfficeType() {
        return this.officeType;
    }

    public void setOfficeType(OfficeLocationType value) {
        this.officeType = value;
    }
}

