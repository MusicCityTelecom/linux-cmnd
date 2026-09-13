/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.opentravel.ota._2003._05.AddressType;
import org.opentravel.ota._2003._05.PaymentCardType;
import org.opentravel.ota._2003._05.PersonNameType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="DonationType", propOrder={"frontOfficeInfo", "creditCardInfo", "donorInfo"})
public class DonationType {
    @XmlElement(name="FrontOfficeInfo")
    protected FrontOfficeInfo frontOfficeInfo;
    @XmlElement(name="CreditCardInfo", required=true)
    protected CreditCardInfo creditCardInfo;
    @XmlElement(name="DonorInfo")
    protected DonorInfo donorInfo;
    @XmlAttribute(name="Language", required=true)
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    @XmlSchemaType(name="language")
    protected String language;
    @XmlAttribute(name="GDS_ID")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    protected String gdsid;
    @XmlAttribute(name="AskForReceiptInd", required=true)
    protected boolean askForReceiptInd;
    @XmlAttribute(name="CountryCode", required=true)
    protected String countryCode;
    @XmlAttribute(name="StateCode")
    protected String stateCode;

    public FrontOfficeInfo getFrontOfficeInfo() {
        return this.frontOfficeInfo;
    }

    public void setFrontOfficeInfo(FrontOfficeInfo value) {
        this.frontOfficeInfo = value;
    }

    public CreditCardInfo getCreditCardInfo() {
        return this.creditCardInfo;
    }

    public void setCreditCardInfo(CreditCardInfo value) {
        this.creditCardInfo = value;
    }

    public DonorInfo getDonorInfo() {
        return this.donorInfo;
    }

    public void setDonorInfo(DonorInfo value) {
        this.donorInfo = value;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String value) {
        this.language = value;
    }

    public String getGDSID() {
        return this.gdsid;
    }

    public void setGDSID(String value) {
        this.gdsid = value;
    }

    public boolean isAskForReceiptInd() {
        return this.askForReceiptInd;
    }

    public void setAskForReceiptInd(boolean value) {
        this.askForReceiptInd = value;
    }

    public String getCountryCode() {
        return this.countryCode;
    }

    public void setCountryCode(String value) {
        this.countryCode = value;
    }

    public String getStateCode() {
        return this.stateCode;
    }

    public void setStateCode(String value) {
        this.stateCode = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class FrontOfficeInfo {
        @XmlAttribute(name="ProductName", required=true)
        protected String productName;
        @XmlAttribute(name="ProductVersion", required=true)
        protected String productVersion;
        @XmlAttribute(name="OfficeID", required=true)
        protected String officeID;
        @XmlAttribute(name="CorporateID", required=true)
        protected String corporateID;

        public String getProductName() {
            return this.productName;
        }

        public void setProductName(String value) {
            this.productName = value;
        }

        public String getProductVersion() {
            return this.productVersion;
        }

        public void setProductVersion(String value) {
            this.productVersion = value;
        }

        public String getOfficeID() {
            return this.officeID;
        }

        public void setOfficeID(String value) {
            this.officeID = value;
        }

        public String getCorporateID() {
            return this.corporateID;
        }

        public void setCorporateID(String value) {
            this.corporateID = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"name", "contactInfo"})
    public static class DonorInfo {
        @XmlElement(name="Name")
        protected Name name;
        @XmlElement(name="ContactInfo")
        protected ContactInfo contactInfo;

        public Name getName() {
            return this.name;
        }

        public void setName(Name value) {
            this.name = value;
        }

        public ContactInfo getContactInfo() {
            return this.contactInfo;
        }

        public void setContactInfo(ContactInfo value) {
            this.contactInfo = value;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="")
        public static class Name
        extends PersonNameType {
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="")
        public static class ContactInfo
        extends AddressType {
            @XmlAttribute(name="EmailAddress")
            protected String emailAddress;

            public String getEmailAddress() {
                return this.emailAddress;
            }

            public void setEmailAddress(String value) {
                this.emailAddress = value;
            }
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class CreditCardInfo
    extends PaymentCardType {
        @XmlAttribute(name="Currency", required=true)
        protected String currency;
        @XmlAttribute(name="DonationAmount", required=true)
        protected BigDecimal donationAmount;

        public String getCurrency() {
            return this.currency;
        }

        public void setCurrency(String value) {
            this.currency = value;
        }

        public BigDecimal getDonationAmount() {
            return this.donationAmount;
        }

        public void setDonationAmount(BigDecimal value) {
            this.donationAmount = value;
        }
    }
}

