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
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;
import org.opentravel.ota._2003._05.AirInfoType;
import org.opentravel.ota._2003._05.AmenityOptionType;
import org.opentravel.ota._2003._05.ContactPersonType;
import org.opentravel.ota._2003._05.CruisePackageType;
import org.opentravel.ota._2003._05.CruiseProfileType;
import org.opentravel.ota._2003._05.DocumentHandlingType;
import org.opentravel.ota._2003._05.DocumentType;
import org.opentravel.ota._2003._05.GuestTransportationType;
import org.opentravel.ota._2003._05.PreferLevelType;
import org.opentravel.ota._2003._05.RelatedTravelerType;
import org.opentravel.ota._2003._05.SpecialServiceType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="CruiseGuestDetailType", propOrder={"selectedFareCode", "contactInfo", "guestTransportation", "loyaltyInfo", "linkedTraveler", "travelDocument", "selectedDining", "selectedInsurance", "selectedOptions", "selectedPackages", "selectedSpecialServices", "airAccommodations", "cruiseDocument", "profiles"})
public class CruiseGuestDetailType {
    @XmlElement(name="SelectedFareCode")
    protected List<SelectedFareCode> selectedFareCode;
    @XmlElement(name="ContactInfo")
    protected List<ContactInfo> contactInfo;
    @XmlElement(name="GuestTransportation")
    protected List<GuestTransportationType> guestTransportation;
    @XmlElement(name="LoyaltyInfo")
    protected List<LoyaltyInfo> loyaltyInfo;
    @XmlElement(name="LinkedTraveler")
    protected List<LinkedTraveler> linkedTraveler;
    @XmlElement(name="TravelDocument")
    protected List<DocumentType> travelDocument;
    @XmlElement(name="SelectedDining")
    protected List<SelectedDining> selectedDining;
    @XmlElement(name="SelectedInsurance")
    protected List<SelectedInsurance> selectedInsurance;
    @XmlElement(name="SelectedOptions")
    protected List<AmenityOptionType> selectedOptions;
    @XmlElement(name="SelectedPackages")
    protected SelectedPackages selectedPackages;
    @XmlElement(name="SelectedSpecialServices")
    protected SelectedSpecialServices selectedSpecialServices;
    @XmlElement(name="AirAccommodations")
    protected AirAccommodations airAccommodations;
    @XmlElement(name="CruiseDocument")
    protected List<DocumentHandlingType> cruiseDocument;
    @XmlElement(name="Profiles")
    protected List<CruiseProfileType> profiles;
    @XmlAttribute(name="GuestExistsIndicator")
    protected Boolean guestExistsIndicator;
    @XmlAttribute(name="RepeatGuestIndicator")
    protected Boolean repeatGuestIndicator;

    public List<SelectedFareCode> getSelectedFareCode() {
        if (this.selectedFareCode == null) {
            this.selectedFareCode = new ArrayList<SelectedFareCode>();
        }
        return this.selectedFareCode;
    }

    public List<ContactInfo> getContactInfo() {
        if (this.contactInfo == null) {
            this.contactInfo = new ArrayList<ContactInfo>();
        }
        return this.contactInfo;
    }

    public List<GuestTransportationType> getGuestTransportation() {
        if (this.guestTransportation == null) {
            this.guestTransportation = new ArrayList<GuestTransportationType>();
        }
        return this.guestTransportation;
    }

    public List<LoyaltyInfo> getLoyaltyInfo() {
        if (this.loyaltyInfo == null) {
            this.loyaltyInfo = new ArrayList<LoyaltyInfo>();
        }
        return this.loyaltyInfo;
    }

    public List<LinkedTraveler> getLinkedTraveler() {
        if (this.linkedTraveler == null) {
            this.linkedTraveler = new ArrayList<LinkedTraveler>();
        }
        return this.linkedTraveler;
    }

    public List<DocumentType> getTravelDocument() {
        if (this.travelDocument == null) {
            this.travelDocument = new ArrayList<DocumentType>();
        }
        return this.travelDocument;
    }

    public List<SelectedDining> getSelectedDining() {
        if (this.selectedDining == null) {
            this.selectedDining = new ArrayList<SelectedDining>();
        }
        return this.selectedDining;
    }

    public List<SelectedInsurance> getSelectedInsurance() {
        if (this.selectedInsurance == null) {
            this.selectedInsurance = new ArrayList<SelectedInsurance>();
        }
        return this.selectedInsurance;
    }

    public List<AmenityOptionType> getSelectedOptions() {
        if (this.selectedOptions == null) {
            this.selectedOptions = new ArrayList<AmenityOptionType>();
        }
        return this.selectedOptions;
    }

    public SelectedPackages getSelectedPackages() {
        return this.selectedPackages;
    }

    public void setSelectedPackages(SelectedPackages value) {
        this.selectedPackages = value;
    }

    public SelectedSpecialServices getSelectedSpecialServices() {
        return this.selectedSpecialServices;
    }

    public void setSelectedSpecialServices(SelectedSpecialServices value) {
        this.selectedSpecialServices = value;
    }

    public AirAccommodations getAirAccommodations() {
        return this.airAccommodations;
    }

    public void setAirAccommodations(AirAccommodations value) {
        this.airAccommodations = value;
    }

    public List<DocumentHandlingType> getCruiseDocument() {
        if (this.cruiseDocument == null) {
            this.cruiseDocument = new ArrayList<DocumentHandlingType>();
        }
        return this.cruiseDocument;
    }

    public List<CruiseProfileType> getProfiles() {
        if (this.profiles == null) {
            this.profiles = new ArrayList<CruiseProfileType>();
        }
        return this.profiles;
    }

    public Boolean isGuestExistsIndicator() {
        return this.guestExistsIndicator;
    }

    public void setGuestExistsIndicator(Boolean value) {
        this.guestExistsIndicator = value;
    }

    public Boolean isRepeatGuestIndicator() {
        return this.repeatGuestIndicator;
    }

    public void setRepeatGuestIndicator(Boolean value) {
        this.repeatGuestIndicator = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"selectedSpecialService"})
    public static class SelectedSpecialServices {
        @XmlElement(name="SelectedSpecialService")
        protected List<SpecialServiceType> selectedSpecialService;

        public List<SpecialServiceType> getSelectedSpecialService() {
            if (this.selectedSpecialService == null) {
                this.selectedSpecialService = new ArrayList<SpecialServiceType>();
            }
            return this.selectedSpecialService;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"selectedPackage"})
    public static class SelectedPackages {
        @XmlElement(name="SelectedPackage")
        protected List<SelectedPackage> selectedPackage;

        public List<SelectedPackage> getSelectedPackage() {
            if (this.selectedPackage == null) {
                this.selectedPackage = new ArrayList<SelectedPackage>();
            }
            return this.selectedPackage;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="", propOrder={"airInfo"})
        public static class SelectedPackage
        extends CruisePackageType {
            @XmlElement(name="AirInfo")
            protected AirInfoType airInfo;

            public AirInfoType getAirInfo() {
                return this.airInfo;
            }

            public void setAirInfo(AirInfoType value) {
                this.airInfo = value;
            }
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class SelectedInsurance {
        @XmlAttribute(name="InsuranceCode")
        protected String insuranceCode;
        @XmlAttribute(name="SelectedOptionIndicator")
        protected Boolean selectedOptionIndicator;
        @XmlAttribute(name="DefaultIndicator")
        protected Boolean defaultIndicator;
        @XmlAttribute(name="Status")
        protected String status;

        public String getInsuranceCode() {
            return this.insuranceCode;
        }

        public void setInsuranceCode(String value) {
            this.insuranceCode = value;
        }

        public Boolean isSelectedOptionIndicator() {
            return this.selectedOptionIndicator;
        }

        public void setSelectedOptionIndicator(Boolean value) {
            this.selectedOptionIndicator = value;
        }

        public Boolean isDefaultIndicator() {
            return this.defaultIndicator;
        }

        public void setDefaultIndicator(Boolean value) {
            this.defaultIndicator = value;
        }

        public String getStatus() {
            return this.status;
        }

        public void setStatus(String value) {
            this.status = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class SelectedFareCode {
        @XmlAttribute(name="FareCode")
        protected String fareCode;
        @XmlAttribute(name="GroupCode")
        protected String groupCode;

        public String getFareCode() {
            return this.fareCode;
        }

        public void setFareCode(String value) {
            this.fareCode = value;
        }

        public String getGroupCode() {
            return this.groupCode;
        }

        public void setGroupCode(String value) {
            this.groupCode = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class SelectedDining {
        @XmlAttribute(name="SmokingCode")
        protected String smokingCode;
        @XmlAttribute(name="DiningRoom")
        protected String diningRoom;
        @XmlAttribute(name="TableSize")
        protected String tableSize;
        @XmlAttribute(name="AgeCode")
        protected String ageCode;
        @XmlAttribute(name="Language")
        @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
        @XmlSchemaType(name="language")
        protected String language;
        @XmlAttribute(name="Sitting", required=true)
        protected String sitting;
        @XmlAttribute(name="Status")
        protected String status;
        @XmlAttribute(name="Preference")
        protected PreferLevelType preference;

        public String getSmokingCode() {
            return this.smokingCode;
        }

        public void setSmokingCode(String value) {
            this.smokingCode = value;
        }

        public String getDiningRoom() {
            return this.diningRoom;
        }

        public void setDiningRoom(String value) {
            this.diningRoom = value;
        }

        public String getTableSize() {
            return this.tableSize;
        }

        public void setTableSize(String value) {
            this.tableSize = value;
        }

        public String getAgeCode() {
            return this.ageCode;
        }

        public void setAgeCode(String value) {
            this.ageCode = value;
        }

        public String getLanguage() {
            return this.language;
        }

        public void setLanguage(String value) {
            this.language = value;
        }

        public String getSitting() {
            return this.sitting;
        }

        public void setSitting(String value) {
            this.sitting = value;
        }

        public String getStatus() {
            return this.status;
        }

        public void setStatus(String value) {
            this.status = value;
        }

        public PreferLevelType getPreference() {
            return this.preference;
        }

        public void setPreference(PreferLevelType value) {
            this.preference = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class LoyaltyInfo {
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

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class LinkedTraveler
    extends RelatedTravelerType {
        @XmlAttribute(name="LinkTypeCode")
        protected String linkTypeCode;

        public String getLinkTypeCode() {
            return this.linkTypeCode;
        }

        public void setLinkTypeCode(String value) {
            this.linkTypeCode = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class ContactInfo
    extends ContactPersonType {
        @XmlAttribute(name="GuestRefNumber")
        protected String guestRefNumber;
        @XmlAttribute(name="Age")
        protected Integer age;
        @XmlAttribute(name="Nationality")
        protected String nationality;
        @XmlAttribute(name="GuestOccupation")
        protected String guestOccupation;
        @XmlAttribute(name="BirthDate")
        @XmlSchemaType(name="date")
        protected XMLGregorianCalendar birthDate;
        @XmlAttribute(name="Gender")
        @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
        protected String gender;
        @XmlAttribute(name="LoyaltyMembershipID")
        protected String loyaltyMembershipID;
        @XmlAttribute(name="LoyalLevel")
        protected String loyalLevel;
        @XmlAttribute(name="LoyalLevelCode")
        protected Integer loyalLevelCode;

        public String getGuestRefNumber() {
            return this.guestRefNumber;
        }

        public void setGuestRefNumber(String value) {
            this.guestRefNumber = value;
        }

        public Integer getAge() {
            return this.age;
        }

        public void setAge(Integer value) {
            this.age = value;
        }

        public String getNationality() {
            return this.nationality;
        }

        public void setNationality(String value) {
            this.nationality = value;
        }

        public String getGuestOccupation() {
            return this.guestOccupation;
        }

        public void setGuestOccupation(String value) {
            this.guestOccupation = value;
        }

        public XMLGregorianCalendar getBirthDate() {
            return this.birthDate;
        }

        public void setBirthDate(XMLGregorianCalendar value) {
            this.birthDate = value;
        }

        public String getGender() {
            return this.gender;
        }

        public void setGender(String value) {
            this.gender = value;
        }

        public String getLoyaltyMembershipID() {
            return this.loyaltyMembershipID;
        }

        public void setLoyaltyMembershipID(String value) {
            this.loyaltyMembershipID = value;
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
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"airAccommodation"})
    public static class AirAccommodations {
        @XmlElement(name="AirAccommodation")
        protected List<AirAccommodation> airAccommodation;

        public List<AirAccommodation> getAirAccommodation() {
            if (this.airAccommodation == null) {
                this.airAccommodation = new ArrayList<AirAccommodation>();
            }
            return this.airAccommodation;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="")
        public static class AirAccommodation
        extends AirInfoType {
            @XmlAttribute(name="Comment")
            protected String comment;
            @XmlAttribute(name="AirAccommodationType")
            protected String airAccommodationType;

            public String getComment() {
                return this.comment;
            }

            public void setComment(String value) {
                this.comment = value;
            }

            public String getAirAccommodationType() {
                return this.airAccommodationType;
            }

            public void setAirAccommodationType(String value) {
                this.airAccommodationType = value;
            }
        }
    }
}

