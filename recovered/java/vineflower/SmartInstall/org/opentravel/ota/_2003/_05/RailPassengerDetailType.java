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

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RailPassengerDetailType", propOrder = {"identification", "profileRef", "telephone", "email", "address", "custLoyalty"})
public class RailPassengerDetailType {
   @XmlElement(name = "Identification", required = true)
   protected PersonNameType identification;
   @XmlElement(name = "ProfileRef")
   protected RailPassengerDetailType.ProfileRef profileRef;
   @XmlElement(name = "Telephone")
   protected List<RailPassengerDetailType.Telephone> telephone;
   @XmlElement(name = "Email")
   protected List<RailPassengerDetailType.Email> email;
   @XmlElement(name = "Address")
   protected List<RailPassengerDetailType.Address> address;
   @XmlElement(name = "CustLoyalty")
   protected List<RailPassengerDetailType.CustLoyalty> custLoyalty;
   @XmlAttribute(name = "BirthDate")
   @XmlSchemaType(name = "date")
   protected XMLGregorianCalendar birthDate;

   public PersonNameType getIdentification() {
      return this.identification;
   }

   public void setIdentification(PersonNameType value) {
      this.identification = value;
   }

   public RailPassengerDetailType.ProfileRef getProfileRef() {
      return this.profileRef;
   }

   public void setProfileRef(RailPassengerDetailType.ProfileRef value) {
      this.profileRef = value;
   }

   public List<RailPassengerDetailType.Telephone> getTelephone() {
      if (this.telephone == null) {
         this.telephone = new ArrayList<>();
      }

      return this.telephone;
   }

   public List<RailPassengerDetailType.Email> getEmail() {
      if (this.email == null) {
         this.email = new ArrayList<>();
      }

      return this.email;
   }

   public List<RailPassengerDetailType.Address> getAddress() {
      if (this.address == null) {
         this.address = new ArrayList<>();
      }

      return this.address;
   }

   public List<RailPassengerDetailType.CustLoyalty> getCustLoyalty() {
      if (this.custLoyalty == null) {
         this.custLoyalty = new ArrayList<>();
      }

      return this.custLoyalty;
   }

   public XMLGregorianCalendar getBirthDate() {
      return this.birthDate;
   }

   public void setBirthDate(XMLGregorianCalendar value) {
      this.birthDate = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class Address extends AddressType {
      @XmlAttribute(name = "Operation")
      protected ActionType operation;

      public ActionType getOperation() {
         return this.operation;
      }

      public void setOperation(ActionType value) {
         this.operation = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class CustLoyalty {
      @XmlAttribute(name = "Operation")
      protected ActionType operation;
      @XmlAttribute(name = "ProgramID")
      protected String programID;
      @XmlAttribute(name = "MembershipID")
      protected String membershipID;
      @XmlAttribute(name = "TravelSector")
      protected String travelSector;
      @XmlAttribute(name = "RPH")
      protected String rph;
      @XmlAttribute(name = "VendorCode")
      protected List<String> vendorCode;
      @XmlAttribute(name = "PrimaryLoyaltyIndicator")
      protected Boolean primaryLoyaltyIndicator;
      @XmlAttribute(name = "AllianceLoyaltyLevelName")
      protected String allianceLoyaltyLevelName;
      @XmlAttribute(name = "CustomerType")
      protected String customerType;
      @XmlAttribute(name = "CustomerValue")
      protected String customerValue;
      @XmlAttribute(name = "Password")
      protected String password;
      @XmlAttribute(name = "ShareSynchInd")
      @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
      protected String shareSynchInd;
      @XmlAttribute(name = "ShareMarketInd")
      @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
      protected String shareMarketInd;
      @XmlAttribute(name = "EffectiveDate")
      @XmlSchemaType(name = "date")
      protected XMLGregorianCalendar effectiveDate;
      @XmlAttribute(name = "ExpireDate")
      @XmlSchemaType(name = "date")
      protected XMLGregorianCalendar expireDate;
      @XmlAttribute(name = "ExpireDateExclusiveIndicator")
      protected Boolean expireDateExclusiveIndicator;
      @XmlAttribute(name = "SingleVendorInd")
      @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
      protected String singleVendorInd;
      @XmlAttribute(name = "LoyalLevel")
      protected String loyalLevel;
      @XmlAttribute(name = "LoyalLevelCode")
      protected Integer loyalLevelCode;
      @XmlAttribute(name = "SignupDate")
      @XmlSchemaType(name = "date")
      protected XMLGregorianCalendar signupDate;

      public ActionType getOperation() {
         return this.operation;
      }

      public void setOperation(ActionType value) {
         this.operation = value;
      }

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
            this.vendorCode = new ArrayList<>();
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

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class Email extends EmailType {
      @XmlAttribute(name = "Operation")
      protected ActionType operation;

      public ActionType getOperation() {
         return this.operation;
      }

      public void setOperation(ActionType value) {
         this.operation = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "uniqueID")
   public static class ProfileRef {
      @XmlElement(name = "UniqueID", required = true)
      protected UniqueIDType uniqueID;

      public UniqueIDType getUniqueID() {
         return this.uniqueID;
      }

      public void setUniqueID(UniqueIDType value) {
         this.uniqueID = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class Telephone {
      @XmlAttribute(name = "Operation")
      protected ActionType operation;
      @XmlAttribute(name = "LocationCode")
      protected String locationCode;
      @XmlAttribute(name = "RPH")
      protected String rph;
      @XmlAttribute(name = "FormattedInd")
      protected Boolean formattedInd;
      @XmlAttribute(name = "ShareSynchInd")
      @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
      protected String shareSynchInd;
      @XmlAttribute(name = "ShareMarketInd")
      @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
      protected String shareMarketInd;
      @XmlAttribute(name = "PhoneLocationType")
      protected String phoneLocationType;
      @XmlAttribute(name = "PhoneTechType")
      protected String phoneTechType;
      @XmlAttribute(name = "PhoneUseType")
      protected String phoneUseType;
      @XmlAttribute(name = "CountryAccessCode")
      protected String countryAccessCode;
      @XmlAttribute(name = "AreaCityCode")
      protected String areaCityCode;
      @XmlAttribute(name = "PhoneNumber", required = true)
      protected String phoneNumber;
      @XmlAttribute(name = "Extension")
      protected String extension;
      @XmlAttribute(name = "PIN")
      protected String pin;
      @XmlAttribute(name = "Remark")
      protected String remark;
      @XmlAttribute(name = "DefaultInd")
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
}
