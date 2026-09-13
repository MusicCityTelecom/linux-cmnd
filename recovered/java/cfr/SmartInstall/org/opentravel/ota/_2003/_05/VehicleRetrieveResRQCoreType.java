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
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;
import org.opentravel.ota._2003._05.OTAReadRQ;
import org.opentravel.ota._2003._05.OTAVehRetResRQ;
import org.opentravel.ota._2003._05.PersonNameType;
import org.opentravel.ota._2003._05.TPAExtensionsType;
import org.opentravel.ota._2003._05.UniqueIDType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="VehicleRetrieveResRQCoreType", propOrder={"uniqueID", "personName", "custLoyalty", "tpaExtensions"})
@XmlSeeAlso(value={OTAVehRetResRQ.VehRetResRQCore.class, OTAReadRQ.ReadRequests.VehicleReadRequest.class})
public class VehicleRetrieveResRQCoreType {
    @XmlElement(name="UniqueID")
    protected List<UniqueIDType> uniqueID;
    @XmlElement(name="PersonName")
    protected PersonNameType personName;
    @XmlElement(name="CustLoyalty")
    protected CustLoyalty custLoyalty;
    @XmlElement(name="TPA_Extensions")
    protected TPAExtensionsType tpaExtensions;

    public List<UniqueIDType> getUniqueID() {
        if (this.uniqueID == null) {
            this.uniqueID = new ArrayList<UniqueIDType>();
        }
        return this.uniqueID;
    }

    public PersonNameType getPersonName() {
        return this.personName;
    }

    public void setPersonName(PersonNameType value) {
        this.personName = value;
    }

    public CustLoyalty getCustLoyalty() {
        return this.custLoyalty;
    }

    public void setCustLoyalty(CustLoyalty value) {
        this.custLoyalty = value;
    }

    public TPAExtensionsType getTPAExtensions() {
        return this.tpaExtensions;
    }

    public void setTPAExtensions(TPAExtensionsType value) {
        this.tpaExtensions = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class CustLoyalty {
        @XmlAttribute(name="ProgramID")
        protected String programID;
        @XmlAttribute(name="MembershipID")
        protected String membershipID;
        @XmlAttribute(name="TravelSector")
        protected String travelSector;
        @XmlAttribute(name="RPH")
        protected String rph;
        @XmlAttribute(name="VendorCode")
        protected List<String> vendorCode;
        @XmlAttribute(name="PrimaryLoyaltyIndicator")
        protected Boolean primaryLoyaltyIndicator;
        @XmlAttribute(name="AllianceLoyaltyLevelName")
        protected String allianceLoyaltyLevelName;
        @XmlAttribute(name="CustomerType")
        protected String customerType;
        @XmlAttribute(name="CustomerValue")
        protected String customerValue;
        @XmlAttribute(name="Password")
        protected String password;
        @XmlAttribute(name="ShareSynchInd")
        @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
        protected String shareSynchInd;
        @XmlAttribute(name="ShareMarketInd")
        @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
        protected String shareMarketInd;
        @XmlAttribute(name="EffectiveDate")
        @XmlSchemaType(name="date")
        protected XMLGregorianCalendar effectiveDate;
        @XmlAttribute(name="ExpireDate")
        @XmlSchemaType(name="date")
        protected XMLGregorianCalendar expireDate;
        @XmlAttribute(name="ExpireDateExclusiveIndicator")
        protected Boolean expireDateExclusiveIndicator;
        @XmlAttribute(name="SingleVendorInd")
        @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
        protected String singleVendorInd;
        @XmlAttribute(name="LoyalLevel")
        protected String loyalLevel;
        @XmlAttribute(name="LoyalLevelCode")
        protected Integer loyalLevelCode;
        @XmlAttribute(name="SignupDate")
        @XmlSchemaType(name="date")
        protected XMLGregorianCalendar signupDate;

        public String getProgramID() {
            return this.programID;
        }

        public void setProgramID(String value) {
            this.programID = value;
        }

        public String getMembershipID() {
            return this.membershipID;
        }

        public void setMembershipID(String value) {
            this.membershipID = value;
        }

        public String getTravelSector() {
            return this.travelSector;
        }

        public void setTravelSector(String value) {
            this.travelSector = value;
        }

        public String getRPH() {
            return this.rph;
        }

        public void setRPH(String value) {
            this.rph = value;
        }

        public List<String> getVendorCode() {
            if (this.vendorCode == null) {
                this.vendorCode = new ArrayList<String>();
            }
            return this.vendorCode;
        }

        public Boolean isPrimaryLoyaltyIndicator() {
            return this.primaryLoyaltyIndicator;
        }

        public void setPrimaryLoyaltyIndicator(Boolean value) {
            this.primaryLoyaltyIndicator = value;
        }

        public String getAllianceLoyaltyLevelName() {
            return this.allianceLoyaltyLevelName;
        }

        public void setAllianceLoyaltyLevelName(String value) {
            this.allianceLoyaltyLevelName = value;
        }

        public String getCustomerType() {
            return this.customerType;
        }

        public void setCustomerType(String value) {
            this.customerType = value;
        }

        public String getCustomerValue() {
            return this.customerValue;
        }

        public void setCustomerValue(String value) {
            this.customerValue = value;
        }

        public String getPassword() {
            return this.password;
        }

        public void setPassword(String value) {
            this.password = value;
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

        public String getSingleVendorInd() {
            return this.singleVendorInd;
        }

        public void setSingleVendorInd(String value) {
            this.singleVendorInd = value;
        }

        public String getLoyalLevel() {
            return this.loyalLevel;
        }

        public void setLoyalLevel(String value) {
            this.loyalLevel = value;
        }

        public Integer getLoyalLevelCode() {
            return this.loyalLevelCode;
        }

        public void setLoyalLevelCode(Integer value) {
            this.loyalLevelCode = value;
        }

        public XMLGregorianCalendar getSignupDate() {
            return this.signupDate;
        }

        public void setSignupDate(XMLGregorianCalendar value) {
            this.signupDate = value;
        }
    }
}

