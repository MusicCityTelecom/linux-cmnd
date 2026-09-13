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
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.opentravel.ota._2003._05.AddressInfoType;
import org.opentravel.ota._2003._05.CompanyNameType;
import org.opentravel.ota._2003._05.CruiseGuestDetailType;
import org.opentravel.ota._2003._05.EmailType;
import org.opentravel.ota._2003._05.EmployeeInfoType;
import org.opentravel.ota._2003._05.HotelRoomListType;
import org.opentravel.ota._2003._05.PersonNameType;
import org.opentravel.ota._2003._05.RecipientInfosType;
import org.opentravel.ota._2003._05.URLType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ContactPersonType", propOrder={"personName", "telephone", "address", "email", "url", "companyName", "employeeInfo"})
@XmlSeeAlso(value={RecipientInfosType.RecipientInfo.class, HotelRoomListType.Guests.Guest.class, HotelRoomListType.MasterContact.class, CruiseGuestDetailType.ContactInfo.class})
public class ContactPersonType {
    @XmlElement(name="PersonName")
    protected PersonNameType personName;
    @XmlElement(name="Telephone")
    protected List<Telephone> telephone;
    @XmlElement(name="Address")
    protected List<AddressInfoType> address;
    @XmlElement(name="Email")
    protected List<EmailType> email;
    @XmlElement(name="URL")
    protected List<URLType> url;
    @XmlElement(name="CompanyName")
    protected List<CompanyNameType> companyName;
    @XmlElement(name="EmployeeInfo")
    protected List<EmployeeInfoType> employeeInfo;
    @XmlAttribute(name="ContactType")
    protected String contactType;
    @XmlAttribute(name="Relation")
    protected String relation;
    @XmlAttribute(name="EmergencyFlag")
    protected Boolean emergencyFlag;
    @XmlAttribute(name="RPH")
    protected String rph;
    @XmlAttribute(name="CommunicationMethodCode")
    protected String communicationMethodCode;
    @XmlAttribute(name="DocumentDistribMethodCode")
    protected String documentDistribMethodCode;
    @XmlAttribute(name="DefaultInd")
    protected Boolean defaultInd;
    @XmlAttribute(name="ShareSynchInd")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    protected String shareSynchInd;
    @XmlAttribute(name="ShareMarketInd")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    protected String shareMarketInd;

    public PersonNameType getPersonName() {
        return this.personName;
    }

    public void setPersonName(PersonNameType value) {
        this.personName = value;
    }

    public List<Telephone> getTelephone() {
        if (this.telephone == null) {
            this.telephone = new ArrayList<Telephone>();
        }
        return this.telephone;
    }

    public List<AddressInfoType> getAddress() {
        if (this.address == null) {
            this.address = new ArrayList<AddressInfoType>();
        }
        return this.address;
    }

    public List<EmailType> getEmail() {
        if (this.email == null) {
            this.email = new ArrayList<EmailType>();
        }
        return this.email;
    }

    public List<URLType> getURL() {
        if (this.url == null) {
            this.url = new ArrayList<URLType>();
        }
        return this.url;
    }

    public List<CompanyNameType> getCompanyName() {
        if (this.companyName == null) {
            this.companyName = new ArrayList<CompanyNameType>();
        }
        return this.companyName;
    }

    public List<EmployeeInfoType> getEmployeeInfo() {
        if (this.employeeInfo == null) {
            this.employeeInfo = new ArrayList<EmployeeInfoType>();
        }
        return this.employeeInfo;
    }

    public String getContactType() {
        return this.contactType;
    }

    public void setContactType(String value) {
        this.contactType = value;
    }

    public String getRelation() {
        return this.relation;
    }

    public void setRelation(String value) {
        this.relation = value;
    }

    public Boolean isEmergencyFlag() {
        return this.emergencyFlag;
    }

    public void setEmergencyFlag(Boolean value) {
        this.emergencyFlag = value;
    }

    public String getRPH() {
        return this.rph;
    }

    public void setRPH(String value) {
        this.rph = value;
    }

    public String getCommunicationMethodCode() {
        return this.communicationMethodCode;
    }

    public void setCommunicationMethodCode(String value) {
        this.communicationMethodCode = value;
    }

    public String getDocumentDistribMethodCode() {
        return this.documentDistribMethodCode;
    }

    public void setDocumentDistribMethodCode(String value) {
        this.documentDistribMethodCode = value;
    }

    public Boolean isDefaultInd() {
        return this.defaultInd;
    }

    public void setDefaultInd(Boolean value) {
        this.defaultInd = value;
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

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class Telephone {
        @XmlAttribute(name="RPH")
        protected String rph;
        @XmlAttribute(name="FormattedInd")
        protected Boolean formattedInd;
        @XmlAttribute(name="ShareSynchInd")
        @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
        protected String shareSynchInd;
        @XmlAttribute(name="ShareMarketInd")
        @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
        protected String shareMarketInd;
        @XmlAttribute(name="PhoneLocationType")
        protected String phoneLocationType;
        @XmlAttribute(name="PhoneTechType")
        protected String phoneTechType;
        @XmlAttribute(name="PhoneUseType")
        protected String phoneUseType;
        @XmlAttribute(name="CountryAccessCode")
        protected String countryAccessCode;
        @XmlAttribute(name="AreaCityCode")
        protected String areaCityCode;
        @XmlAttribute(name="PhoneNumber", required=true)
        protected String phoneNumber;
        @XmlAttribute(name="Extension")
        protected String extension;
        @XmlAttribute(name="PIN")
        protected String pin;
        @XmlAttribute(name="Remark")
        protected String remark;
        @XmlAttribute(name="DefaultInd")
        protected Boolean defaultInd;

        public String getRPH() {
            return this.rph;
        }

        public void setRPH(String value) {
            this.rph = value;
        }

        public Boolean isFormattedInd() {
            return this.formattedInd;
        }

        public void setFormattedInd(Boolean value) {
            this.formattedInd = value;
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

        public String getPhoneLocationType() {
            return this.phoneLocationType;
        }

        public void setPhoneLocationType(String value) {
            this.phoneLocationType = value;
        }

        public String getPhoneTechType() {
            return this.phoneTechType;
        }

        public void setPhoneTechType(String value) {
            this.phoneTechType = value;
        }

        public String getPhoneUseType() {
            return this.phoneUseType;
        }

        public void setPhoneUseType(String value) {
            this.phoneUseType = value;
        }

        public String getCountryAccessCode() {
            return this.countryAccessCode;
        }

        public void setCountryAccessCode(String value) {
            this.countryAccessCode = value;
        }

        public String getAreaCityCode() {
            return this.areaCityCode;
        }

        public void setAreaCityCode(String value) {
            this.areaCityCode = value;
        }

        public String getPhoneNumber() {
            return this.phoneNumber;
        }

        public void setPhoneNumber(String value) {
            this.phoneNumber = value;
        }

        public String getExtension() {
            return this.extension;
        }

        public void setExtension(String value) {
            this.extension = value;
        }

        public String getPIN() {
            return this.pin;
        }

        public void setPIN(String value) {
            this.pin = value;
        }

        public String getRemark() {
            return this.remark;
        }

        public void setRemark(String value) {
            this.remark = value;
        }

        public Boolean isDefaultInd() {
            return this.defaultInd;
        }

        public void setDefaultInd(Boolean value) {
            this.defaultInd = value;
        }
    }
}

