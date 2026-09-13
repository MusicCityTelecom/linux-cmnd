/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

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
import org.opentravel.ota._2003._05.EmailType;
import org.opentravel.ota._2003._05.HotelRoomListType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="DirectBillType", propOrder={"companyName", "address", "email", "telephone"})
@XmlSeeAlso(value={HotelRoomListType.MasterAccount.class})
public class DirectBillType {
    @XmlElement(name="CompanyName")
    protected CompanyName companyName;
    @XmlElement(name="Address")
    protected AddressInfoType address;
    @XmlElement(name="Email")
    protected EmailType email;
    @XmlElement(name="Telephone")
    protected Telephone telephone;
    @XmlAttribute(name="DirectBill_ID")
    protected String directBillID;
    @XmlAttribute(name="BillingNumber")
    protected String billingNumber;
    @XmlAttribute(name="ShareSynchInd")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    protected String shareSynchInd;
    @XmlAttribute(name="ShareMarketInd")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    protected String shareMarketInd;

    public CompanyName getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(CompanyName value) {
        this.companyName = value;
    }

    public AddressInfoType getAddress() {
        return this.address;
    }

    public void setAddress(AddressInfoType value) {
        this.address = value;
    }

    public EmailType getEmail() {
        return this.email;
    }

    public void setEmail(EmailType value) {
        this.email = value;
    }

    public Telephone getTelephone() {
        return this.telephone;
    }

    public void setTelephone(Telephone value) {
        this.telephone = value;
    }

    public String getDirectBillID() {
        return this.directBillID;
    }

    public void setDirectBillID(String value) {
        this.directBillID = value;
    }

    public String getBillingNumber() {
        return this.billingNumber;
    }

    public void setBillingNumber(String value) {
        this.billingNumber = value;
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

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class CompanyName
    extends CompanyNameType {
        @XmlAttribute(name="ContactName")
        protected String contactName;

        public String getContactName() {
            return this.contactName;
        }

        public void setContactName(String value) {
            this.contactName = value;
        }
    }
}

