package org.opentravel.ota._2003._05;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "CustomerType",
   propOrder = {
         "personName",
         "telephone",
         "email",
         "address",
         "url",
         "citizenCountryName",
         "physChallName",
         "petInfo",
         "paymentForm",
         "relatedTraveler",
         "contactPerson",
         "document",
         "custLoyalty",
         "employeeInfo",
         "employerInfo",
         "additionalLanguage",
         "tpaExtensions"
   }
)
@XmlSeeAlso({CustomerPrimaryAdditionalType.Primary.class, CustomerPrimaryAdditionalType.Additional.class})
public class CustomerType {
   @XmlElement(name = "PersonName")
   protected List<PersonNameType> personName;
   @XmlElement(name = "Telephone")
   protected List<CustomerType.Telephone> telephone;
   @XmlElement(name = "Email")
   protected List<CustomerType.Email> email;
   @XmlElement(name = "Address")
   protected List<CustomerType.Address> address;
   @XmlElement(name = "URL")
   protected List<CustomerType.URL> url;
   @XmlElement(name = "CitizenCountryName")
   protected List<CustomerType.CitizenCountryName> citizenCountryName;
   @XmlElement(name = "PhysChallName")
   protected List<CustomerType.PhysChallName> physChallName;
   @XmlElement(name = "PetInfo")
   protected List<String> petInfo;
   @XmlElement(name = "PaymentForm")
   protected List<CustomerType.PaymentForm> paymentForm;
   @XmlElement(name = "RelatedTraveler")
   protected List<RelatedTravelerType> relatedTraveler;
   @XmlElement(name = "ContactPerson")
   protected List<ContactPersonType> contactPerson;
   @XmlElement(name = "Document")
   protected List<DocumentType> document;
   @XmlElement(name = "CustLoyalty")
   protected List<CustomerType.CustLoyalty> custLoyalty;
   @XmlElement(name = "EmployeeInfo")
   protected List<EmployeeInfoType> employeeInfo;
   @XmlElement(name = "EmployerInfo")
   protected CompanyNameType employerInfo;
   @XmlElement(name = "AdditionalLanguage")
   protected List<CustomerType.AdditionalLanguage> additionalLanguage;
   @XmlElement(name = "TPA_Extensions")
   protected TPAExtensionsType tpaExtensions;
   @XmlAttribute(name = "Deceased")
   protected Boolean deceased;
   @XmlAttribute(name = "LockoutType")
   protected String lockoutType;
   @XmlAttribute(name = "VIP_Indicator")
   protected Boolean vipIndicator;
   @XmlAttribute(name = "Text")
   protected String text;
   @XmlAttribute(name = "CustomerValue")
   protected String customerValue;
   @XmlAttribute(name = "MaritalStatus")
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   protected String maritalStatus;
   @XmlAttribute(name = "PreviouslyMarriedIndicator")
   protected Boolean previouslyMarriedIndicator;
   @XmlAttribute(name = "ChildQuantity")
   protected Integer childQuantity;
   @XmlAttribute(name = "Gender")
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   protected String gender;
   @XmlAttribute(name = "BirthDate")
   @XmlSchemaType(name = "date")
   protected XMLGregorianCalendar birthDate;
   @XmlAttribute(name = "Language")
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   @XmlSchemaType(name = "language")
   protected String language;
   @XmlAttribute(name = "CurrencyCode")
   protected String currencyCode;
   @XmlAttribute(name = "DecimalPlaces")
   @XmlSchemaType(name = "nonNegativeInteger")
   protected BigInteger decimalPlaces;

   public List<PersonNameType> getPersonName() {
      if (this.personName == null) {
         this.personName = new ArrayList<>();
      }

      return this.personName;
   }

   public List<CustomerType.Telephone> getTelephone() {
      if (this.telephone == null) {
         this.telephone = new ArrayList<>();
      }

      return this.telephone;
   }

   public List<CustomerType.Email> getEmail() {
      if (this.email == null) {
         this.email = new ArrayList<>();
      }

      return this.email;
   }

   public List<CustomerType.Address> getAddress() {
      if (this.address == null) {
         this.address = new ArrayList<>();
      }

      return this.address;
   }

   public List<CustomerType.URL> getURL() {
      if (this.url == null) {
         this.url = new ArrayList<>();
      }

      return this.url;
   }

   public List<CustomerType.CitizenCountryName> getCitizenCountryName() {
      if (this.citizenCountryName == null) {
         this.citizenCountryName = new ArrayList<>();
      }

      return this.citizenCountryName;
   }

   public List<CustomerType.PhysChallName> getPhysChallName() {
      if (this.physChallName == null) {
         this.physChallName = new ArrayList<>();
      }

      return this.physChallName;
   }

   public List<String> getPetInfo() {
      if (this.petInfo == null) {
         this.petInfo = new ArrayList<>();
      }

      return this.petInfo;
   }

   public List<CustomerType.PaymentForm> getPaymentForm() {
      if (this.paymentForm == null) {
         this.paymentForm = new ArrayList<>();
      }

      return this.paymentForm;
   }

   public List<RelatedTravelerType> getRelatedTraveler() {
      if (this.relatedTraveler == null) {
         this.relatedTraveler = new ArrayList<>();
      }

      return this.relatedTraveler;
   }

   public List<ContactPersonType> getContactPerson() {
      if (this.contactPerson == null) {
         this.contactPerson = new ArrayList<>();
      }

      return this.contactPerson;
   }

   public List<DocumentType> getDocument() {
      if (this.document == null) {
         this.document = new ArrayList<>();
      }

      return this.document;
   }

   public List<CustomerType.CustLoyalty> getCustLoyalty() {
      if (this.custLoyalty == null) {
         this.custLoyalty = new ArrayList<>();
      }

      return this.custLoyalty;
   }

   public List<EmployeeInfoType> getEmployeeInfo() {
      if (this.employeeInfo == null) {
         this.employeeInfo = new ArrayList<>();
      }

      return this.employeeInfo;
   }

   public CompanyNameType getEmployerInfo() {
      return this.employerInfo;
   }

   public void setEmployerInfo(CompanyNameType value) {
      this.employerInfo = value;
   }

   public List<CustomerType.AdditionalLanguage> getAdditionalLanguage() {
      if (this.additionalLanguage == null) {
         this.additionalLanguage = new ArrayList<>();
      }

      return this.additionalLanguage;
   }

   public TPAExtensionsType getTPAExtensions() {
      return this.tpaExtensions;
   }

   public void setTPAExtensions(TPAExtensionsType value) {
      this.tpaExtensions = value;
   }

   public Boolean isDeceased() {
      return this.deceased;
   }

   public void setDeceased(Boolean value) {
      this.deceased = value;
   }

   public String getLockoutType() {
      return this.lockoutType;
   }

   public void setLockoutType(String value) {
      this.lockoutType = value;
   }

   public Boolean isVIPIndicator() {
      return this.vipIndicator;
   }

   public void setVIPIndicator(Boolean value) {
      this.vipIndicator = value;
   }

   public String getText() {
      return this.text;
   }

   public void setText(String value) {
      this.text = value;
   }

   public String getCustomerValue() {
      return this.customerValue;
   }

   public void setCustomerValue(String value) {
      this.customerValue = value;
   }

   public String getMaritalStatus() {
      return this.maritalStatus;
   }

   public void setMaritalStatus(String value) {
      this.maritalStatus = value;
   }

   public Boolean isPreviouslyMarriedIndicator() {
      return this.previouslyMarriedIndicator;
   }

   public void setPreviouslyMarriedIndicator(Boolean value) {
      this.previouslyMarriedIndicator = value;
   }

   public Integer getChildQuantity() {
      return this.childQuantity;
   }

   public void setChildQuantity(Integer value) {
      this.childQuantity = value;
   }

   public String getGender() {
      return this.gender;
   }

   public void setGender(String value) {
      this.gender = value;
   }

   public XMLGregorianCalendar getBirthDate() {
      return this.birthDate;
   }

   public void setBirthDate(XMLGregorianCalendar value) {
      this.birthDate = value;
   }

   public String getLanguage() {
      return this.language;
   }

   public void setLanguage(String value) {
      this.language = value;
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
   public static class AdditionalLanguage {
      @XmlAttribute(name = "Code")
      @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
      @XmlSchemaType(name = "language")
      protected String code;

      public String getCode() {
         return this.code;
      }

      public void setCode(String value) {
         this.code = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = {"companyName", "addresseeName"})
   public static class Address extends AddressInfoType {
      @XmlElement(name = "CompanyName")
      protected CompanyNameType companyName;
      @XmlElement(name = "AddresseeName")
      protected PersonNameType addresseeName;
      @XmlAttribute(name = "ValidationStatus")
      @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
      protected String validationStatus;
      @XmlAttribute(name = "TransferAction")
      protected TransferActionType transferAction;
      @XmlAttribute(name = "ParentCompanyRef")
      protected String parentCompanyRef;
      @XmlAttribute(name = "EffectiveDate")
      @XmlSchemaType(name = "date")
      protected XMLGregorianCalendar effectiveDate;
      @XmlAttribute(name = "ExpireDate")
      @XmlSchemaType(name = "date")
      protected XMLGregorianCalendar expireDate;
      @XmlAttribute(name = "ExpireDateExclusiveIndicator")
      protected Boolean expireDateExclusiveIndicator;

      public CompanyNameType getCompanyName() {
         return this.companyName;
      }

      public void setCompanyName(CompanyNameType value) {
         this.companyName = value;
      }

      public PersonNameType getAddresseeName() {
         return this.addresseeName;
      }

      public void setAddresseeName(PersonNameType value) {
         this.addresseeName = value;
      }

      public String getValidationStatus() {
         return this.validationStatus;
      }

      public void setValidationStatus(String value) {
         this.validationStatus = value;
      }

      public TransferActionType getTransferAction() {
         return this.transferAction;
      }

      public void setTransferAction(TransferActionType value) {
         this.transferAction = value;
      }

      public String getParentCompanyRef() {
         return this.parentCompanyRef;
      }

      public void setParentCompanyRef(String value) {
         this.parentCompanyRef = value;
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
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class CitizenCountryName {
      @XmlAttribute(name = "Code")
      protected String code;
      @XmlAttribute(name = "DefaultInd")
      protected Boolean defaultInd;

      public String getCode() {
         return this.code;
      }

      public void setCode(String value) {
         this.code = value;
      }

      public Boolean isDefaultInd() {
         return this.defaultInd;
      }

      public void setDefaultInd(Boolean value) {
         this.defaultInd = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = {"memberPreferences", "securityInfo", "subAccountBalance"})
   public static class CustLoyalty {
      @XmlElement(name = "MemberPreferences")
      protected CustomerType.CustLoyalty.MemberPreferences memberPreferences;
      @XmlElement(name = "SecurityInfo")
      protected CustomerType.CustLoyalty.SecurityInfo securityInfo;
      @XmlElement(name = "SubAccountBalance")
      protected List<CustomerType.CustLoyalty.SubAccountBalance> subAccountBalance;
      @XmlAttribute(name = "Remark")
      protected String remark;
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

      public CustomerType.CustLoyalty.MemberPreferences getMemberPreferences() {
         return this.memberPreferences;
      }

      public void setMemberPreferences(CustomerType.CustLoyalty.MemberPreferences value) {
         this.memberPreferences = value;
      }

      public CustomerType.CustLoyalty.SecurityInfo getSecurityInfo() {
         return this.securityInfo;
      }

      public void setSecurityInfo(CustomerType.CustLoyalty.SecurityInfo value) {
         this.securityInfo = value;
      }

      public List<CustomerType.CustLoyalty.SubAccountBalance> getSubAccountBalance() {
         if (this.subAccountBalance == null) {
            this.subAccountBalance = new ArrayList<>();
         }

         return this.subAccountBalance;
      }

      public String getRemark() {
         return this.remark;
      }

      public void setRemark(String value) {
         this.remark = value;
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

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = {"additionalReward", "offer"})
      public static class MemberPreferences {
         @XmlElement(name = "AdditionalReward")
         protected List<CustomerType.CustLoyalty.MemberPreferences.AdditionalReward> additionalReward;
         @XmlElement(name = "Offer")
         protected List<CustomerType.CustLoyalty.MemberPreferences.Offer> offer;
         @XmlAttribute(name = "Awareness")
         protected String awareness;
         @XmlAttribute(name = "AwardsPreference")
         protected String awardsPreference;
         @XmlAttribute(name = "PromotionCode")
         protected String promotionCode;
         @XmlAttribute(name = "PromotionVendorCode")
         protected List<String> promotionVendorCode;

         public List<CustomerType.CustLoyalty.MemberPreferences.AdditionalReward> getAdditionalReward() {
            if (this.additionalReward == null) {
               this.additionalReward = new ArrayList<>();
            }

            return this.additionalReward;
         }

         public List<CustomerType.CustLoyalty.MemberPreferences.Offer> getOffer() {
            if (this.offer == null) {
               this.offer = new ArrayList<>();
            }

            return this.offer;
         }

         public String getAwareness() {
            return this.awareness;
         }

         public void setAwareness(String value) {
            this.awareness = value;
         }

         public String getAwardsPreference() {
            return this.awardsPreference;
         }

         public void setAwardsPreference(String value) {
            this.awardsPreference = value;
         }

         public String getPromotionCode() {
            return this.promotionCode;
         }

         public void setPromotionCode(String value) {
            this.promotionCode = value;
         }

         public List<String> getPromotionVendorCode() {
            if (this.promotionVendorCode == null) {
               this.promotionVendorCode = new ArrayList<>();
            }

            return this.promotionVendorCode;
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "", propOrder = {"companyName", "name"})
         public static class AdditionalReward {
            @XmlElement(name = "CompanyName")
            protected CompanyNameType companyName;
            @XmlElement(name = "Name")
            protected PersonNameType name;
            @XmlAttribute(name = "MemberID")
            protected String memberID;

            public CompanyNameType getCompanyName() {
               return this.companyName;
            }

            public void setCompanyName(CompanyNameType value) {
               this.companyName = value;
            }

            public PersonNameType getName() {
               return this.name;
            }

            public void setName(PersonNameType value) {
               this.name = value;
            }

            public String getMemberID() {
               return this.memberID;
            }

            public void setMemberID(String value) {
               this.memberID = value;
            }
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "", propOrder = "communication")
         public static class Offer {
            @XmlElement(name = "Communication")
            protected List<CustomerType.CustLoyalty.MemberPreferences.Offer.Communication> communication;
            @XmlAttribute(name = "Type")
            protected String type;

            public List<CustomerType.CustLoyalty.MemberPreferences.Offer.Communication> getCommunication() {
               if (this.communication == null) {
                  this.communication = new ArrayList<>();
               }

               return this.communication;
            }

            public String getType() {
               return this.type;
            }

            public void setType(String value) {
               this.type = value;
            }

            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "")
            public static class Communication {
               @XmlAttribute(name = "DistribType")
               protected String distribType;

               public String getDistribType() {
                  return this.distribType;
               }

               public void setDistribType(String value) {
                  this.distribType = value;
               }
            }
         }
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = "passwordHint")
      public static class SecurityInfo {
         @XmlElement(name = "PasswordHint")
         protected List<CustomerType.CustLoyalty.SecurityInfo.PasswordHint> passwordHint;
         @XmlAttribute(name = "Username")
         protected String username;
         @XmlAttribute(name = "Password")
         protected String password;

         public List<CustomerType.CustLoyalty.SecurityInfo.PasswordHint> getPasswordHint() {
            if (this.passwordHint == null) {
               this.passwordHint = new ArrayList<>();
            }

            return this.passwordHint;
         }

         public String getUsername() {
            return this.username;
         }

         public void setUsername(String value) {
            this.username = value;
         }

         public String getPassword() {
            return this.password;
         }

         public void setPassword(String value) {
            this.password = value;
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "", propOrder = "value")
         public static class PasswordHint {
            @XmlValue
            protected String value;
            @XmlAttribute(name = "Hint")
            protected String hint;

            public String getValue() {
               return this.value;
            }

            public void setValue(String value) {
               this.value = value;
            }

            public String getHint() {
               return this.hint;
            }

            public void setHint(String value) {
               this.hint = value;
            }
         }
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class SubAccountBalance {
         @XmlAttribute(name = "Type")
         protected String type;
         @XmlAttribute(name = "Balance")
         protected BigInteger balance;

         public String getType() {
            return this.type;
         }

         public void setType(String value) {
            this.type = value;
         }

         public BigInteger getBalance() {
            return this.balance;
         }

         public void setBalance(BigInteger value) {
            this.balance = value;
         }
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class Email extends EmailType {
      @XmlAttribute(name = "TransferAction")
      protected TransferActionType transferAction;
      @XmlAttribute(name = "ParentCompanyRef")
      protected String parentCompanyRef;

      public TransferActionType getTransferAction() {
         return this.transferAction;
      }

      public void setTransferAction(TransferActionType value) {
         this.transferAction = value;
      }

      public String getParentCompanyRef() {
         return this.parentCompanyRef;
      }

      public void setParentCompanyRef(String value) {
         this.parentCompanyRef = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "associatedSupplier")
   public static class PaymentForm extends PaymentFormType {
      @XmlElement(name = "AssociatedSupplier")
      protected CustomerType.PaymentForm.AssociatedSupplier associatedSupplier;
      @XmlAttribute(name = "TransferAction")
      protected TransferActionType transferAction;
      @XmlAttribute(name = "DefaultInd")
      protected Boolean defaultInd;
      @XmlAttribute(name = "ParentCompanyRef")
      protected String parentCompanyRef;

      public CustomerType.PaymentForm.AssociatedSupplier getAssociatedSupplier() {
         return this.associatedSupplier;
      }

      public void setAssociatedSupplier(CustomerType.PaymentForm.AssociatedSupplier value) {
         this.associatedSupplier = value;
      }

      public TransferActionType getTransferAction() {
         return this.transferAction;
      }

      public void setTransferAction(TransferActionType value) {
         this.transferAction = value;
      }

      public Boolean isDefaultInd() {
         return this.defaultInd;
      }

      public void setDefaultInd(Boolean value) {
         this.defaultInd = value;
      }

      public String getParentCompanyRef() {
         return this.parentCompanyRef;
      }

      public void setParentCompanyRef(String value) {
         this.parentCompanyRef = value;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class AssociatedSupplier {
         @XmlAttribute(name = "CompanyShortName")
         protected String companyShortName;
         @XmlAttribute(name = "TravelSector")
         protected String travelSector;
         @XmlAttribute(name = "Code")
         protected String code;
         @XmlAttribute(name = "CodeContext")
         protected String codeContext;

         public String getCompanyShortName() {
            return this.companyShortName;
         }

         public void setCompanyShortName(String value) {
            this.companyShortName = value;
         }

         public String getTravelSector() {
            return this.travelSector;
         }

         public void setTravelSector(String value) {
            this.travelSector = value;
         }

         public String getCode() {
            return this.code;
         }

         public void setCode(String value) {
            this.code = value;
         }

         public String getCodeContext() {
            return this.codeContext;
         }

         public void setCodeContext(String value) {
            this.codeContext = value;
         }
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "value")
   public static class PhysChallName {
      @XmlValue
      protected String value;
      @XmlAttribute(name = "PhysChallInd")
      protected Boolean physChallInd;

      public String getValue() {
         return this.value;
      }

      public void setValue(String value) {
         this.value = value;
      }

      public Boolean isPhysChallInd() {
         return this.physChallInd;
      }

      public void setPhysChallInd(Boolean value) {
         this.physChallInd = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class Telephone {
      @XmlAttribute(name = "TransferAction")
      protected TransferActionType transferAction;
      @XmlAttribute(name = "ParentCompanyRef")
      protected String parentCompanyRef;
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
      @XmlAttribute(name = "EffectiveDate")
      @XmlSchemaType(name = "date")
      protected XMLGregorianCalendar effectiveDate;
      @XmlAttribute(name = "ExpireDate")
      @XmlSchemaType(name = "date")
      protected XMLGregorianCalendar expireDate;
      @XmlAttribute(name = "ExpireDateExclusiveIndicator")
      protected Boolean expireDateExclusiveIndicator;

      public TransferActionType getTransferAction() {
         return this.transferAction;
      }

      public void setTransferAction(TransferActionType value) {
         this.transferAction = value;
      }

      public String getParentCompanyRef() {
         return this.parentCompanyRef;
      }

      public void setParentCompanyRef(String value) {
         this.parentCompanyRef = value;
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
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class URL extends URLType {
      @XmlAttribute(name = "TransferAction")
      protected TransferActionType transferAction;

      public TransferActionType getTransferAction() {
         return this.transferAction;
      }

      public void setTransferAction(TransferActionType value) {
         this.transferAction = value;
      }
   }
}
