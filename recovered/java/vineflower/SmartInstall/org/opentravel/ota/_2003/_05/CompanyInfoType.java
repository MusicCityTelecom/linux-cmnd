package org.opentravel.ota._2003._05;

import java.math.BigInteger;
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

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "CompanyInfoType",
   propOrder = {
         "companyName",
         "addressInfo",
         "telephoneInfo",
         "email",
         "url",
         "businessLocale",
         "paymentForm",
         "contactPerson",
         "travelArranger",
         "loyaltyProgram",
         "tripPurpose"
   }
)
public class CompanyInfoType {
   @XmlElement(name = "CompanyName")
   protected List<CompanyNameType> companyName;
   @XmlElement(name = "AddressInfo")
   protected List<CompanyInfoType.AddressInfo> addressInfo;
   @XmlElement(name = "TelephoneInfo")
   protected List<CompanyInfoType.TelephoneInfo> telephoneInfo;
   @XmlElement(name = "Email")
   protected List<CompanyInfoType.Email> email;
   @XmlElement(name = "URL")
   protected List<URLType> url;
   @XmlElement(name = "BusinessLocale")
   protected List<AddressType> businessLocale;
   @XmlElement(name = "PaymentForm")
   protected List<CompanyInfoType.PaymentForm> paymentForm;
   @XmlElement(name = "ContactPerson")
   protected List<ContactPersonType> contactPerson;
   @XmlElement(name = "TravelArranger")
   protected List<TravelArrangerType> travelArranger;
   @XmlElement(name = "LoyaltyProgram")
   protected List<LoyaltyProgramType> loyaltyProgram;
   @XmlElement(name = "TripPurpose")
   protected List<CompanyInfoType.TripPurpose> tripPurpose;
   @XmlAttribute(name = "CurrencyCode")
   protected String currencyCode;
   @XmlAttribute(name = "DecimalPlaces")
   @XmlSchemaType(name = "nonNegativeInteger")
   protected BigInteger decimalPlaces;

   public List<CompanyNameType> getCompanyName() {
      if (this.companyName == null) {
         this.companyName = new ArrayList<>();
      }

      return this.companyName;
   }

   public List<CompanyInfoType.AddressInfo> getAddressInfo() {
      if (this.addressInfo == null) {
         this.addressInfo = new ArrayList<>();
      }

      return this.addressInfo;
   }

   public List<CompanyInfoType.TelephoneInfo> getTelephoneInfo() {
      if (this.telephoneInfo == null) {
         this.telephoneInfo = new ArrayList<>();
      }

      return this.telephoneInfo;
   }

   public List<CompanyInfoType.Email> getEmail() {
      if (this.email == null) {
         this.email = new ArrayList<>();
      }

      return this.email;
   }

   public List<URLType> getURL() {
      if (this.url == null) {
         this.url = new ArrayList<>();
      }

      return this.url;
   }

   public List<AddressType> getBusinessLocale() {
      if (this.businessLocale == null) {
         this.businessLocale = new ArrayList<>();
      }

      return this.businessLocale;
   }

   public List<CompanyInfoType.PaymentForm> getPaymentForm() {
      if (this.paymentForm == null) {
         this.paymentForm = new ArrayList<>();
      }

      return this.paymentForm;
   }

   public List<ContactPersonType> getContactPerson() {
      if (this.contactPerson == null) {
         this.contactPerson = new ArrayList<>();
      }

      return this.contactPerson;
   }

   public List<TravelArrangerType> getTravelArranger() {
      if (this.travelArranger == null) {
         this.travelArranger = new ArrayList<>();
      }

      return this.travelArranger;
   }

   public List<LoyaltyProgramType> getLoyaltyProgram() {
      if (this.loyaltyProgram == null) {
         this.loyaltyProgram = new ArrayList<>();
      }

      return this.loyaltyProgram;
   }

   public List<CompanyInfoType.TripPurpose> getTripPurpose() {
      if (this.tripPurpose == null) {
         this.tripPurpose = new ArrayList<>();
      }

      return this.tripPurpose;
   }

   public String getCurrencyCode() {
      return this.currencyCode;
   }

   public void setCurrencyCode(String value) {
      this.currencyCode = value;
   }

   public BigInteger getDecimalPlaces() {
      return this.decimalPlaces;
   }

   public void setDecimalPlaces(BigInteger value) {
      this.decimalPlaces = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class AddressInfo extends AddressInfoType {
      @XmlAttribute(name = "TransferAction")
      protected TransferActionType transferAction;

      public TransferActionType getTransferAction() {
         return this.transferAction;
      }

      public void setTransferAction(TransferActionType value) {
         this.transferAction = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class Email extends EmailType {
      @XmlAttribute(name = "TransferAction")
      protected TransferActionType transferAction;

      public TransferActionType getTransferAction() {
         return this.transferAction;
      }

      public void setTransferAction(TransferActionType value) {
         this.transferAction = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class PaymentForm extends PaymentFormType {
      @XmlAttribute(name = "TransferAction")
      protected TransferActionType transferAction;

      public TransferActionType getTransferAction() {
         return this.transferAction;
      }

      public void setTransferAction(TransferActionType value) {
         this.transferAction = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class TelephoneInfo {
      @XmlAttribute(name = "TransferAction")
      protected TransferActionType transferAction;
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

      public TransferActionType getTransferAction() {
         return this.transferAction;
      }

      public void setTransferAction(TransferActionType value) {
         this.transferAction = value;
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

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class TripPurpose {
      @XmlAttribute(name = "Code")
      protected String code;
      @XmlAttribute(name = "Description")
      protected String description;

      public String getCode() {
         return this.code;
      }

      public void setCode(String value) {
         this.code = value;
      }

      public String getDescription() {
         return this.description;
      }

      public void setDescription(String value) {
         this.description = value;
      }
   }
}
