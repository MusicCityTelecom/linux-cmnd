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
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.opentravel.ota._2003._05.ActionType;
import org.opentravel.ota._2003._05.AddressType;
import org.opentravel.ota._2003._05.EmailType;
import org.opentravel.ota._2003._05.PersonNameType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="RailPersonInfoType", propOrder={"personName", "telephone", "email", "address"})
public class RailPersonInfoType {
    @XmlElement(name="PersonName", required=true)
    protected PersonNameType personName;
    @XmlElement(name="Telephone")
    protected List<Telephone> telephone;
    @XmlElement(name="Email")
    protected List<Email> email;
    @XmlElement(name="Address")
    protected List<Address> address;

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

    public List<Email> getEmail() {
        if (this.email == null) {
            this.email = new ArrayList<Email>();
        }
        return this.email;
    }

    public List<Address> getAddress() {
        if (this.address == null) {
            this.address = new ArrayList<Address>();
        }
        return this.address;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class Telephone {
        @XmlAttribute(name="Operation")
        protected ActionType operation;
        @XmlAttribute(name="LocationCode")
        protected String locationCode;
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

        public ActionType getOperation() {
            return this.operation;
        }

        public void setOperation(ActionType value) {
            this.operation = value;
        }

        public String getLocationCode() {
            return this.locationCode;
        }

        public void setLocationCode(String value) {
            this.locationCode = value;
        }

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
    public static class Email
    extends EmailType {
        @XmlAttribute(name="Operation")
        protected ActionType operation;

        public ActionType getOperation() {
            return this.operation;
        }

        public void setOperation(ActionType value) {
            this.operation = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class Address
    extends AddressType {
        @XmlAttribute(name="Operation")
        protected ActionType operation;

        public ActionType getOperation() {
            return this.operation;
        }

        public void setOperation(ActionType value) {
            this.operation = value;
        }
    }
}

