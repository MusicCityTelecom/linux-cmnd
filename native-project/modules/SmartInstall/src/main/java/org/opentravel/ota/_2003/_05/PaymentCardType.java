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
import org.htng._2011b.HTNGPaymentCardType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "PaymentCardType",
   propOrder = {"cardHolderName", "cardIssuerName", "address", "telephone", "email", "custLoyalty", "signatureOnFile", "magneticStripe"}
)
@XmlSeeAlso({HTNGPaymentCardType.class, DonationType.CreditCardInfo.class})
public class PaymentCardType {
   @XmlElement(name = "CardHolderName")
   protected String cardHolderName;
   @XmlElement(name = "CardIssuerName")
   protected PaymentCardType.CardIssuerName cardIssuerName;
   @XmlElement(name = "Address")
   protected AddressType address;
   @XmlElement(name = "Telephone")
   protected List<PaymentCardType.Telephone> telephone;
   @XmlElement(name = "Email")
   protected List<EmailType> email;
   @XmlElement(name = "CustLoyalty")
   protected List<PaymentCardType.CustLoyalty> custLoyalty;
   @XmlElement(name = "SignatureOnFile")
   protected PaymentCardType.SignatureOnFile signatureOnFile;
   @XmlElement(name = "MagneticStripe")
   protected PaymentCardType.MagneticStripe magneticStripe;
   @XmlAttribute(name = "CardType")
   protected String cardType;
   @XmlAttribute(name = "CardCode")
   protected String cardCode;
   @XmlAttribute(name = "CardNumber")
   protected String cardNumber;
   @XmlAttribute(name = "SeriesCode")
   protected String seriesCode;
   @XmlAttribute(name = "MaskedCardNumber")
   protected String maskedCardNumber;
   @XmlAttribute(name = "CardHolderRPH")
   protected String cardHolderRPH;
   @XmlAttribute(name = "ExtendPaymentIndicator")
   protected Boolean extendPaymentIndicator;
   @XmlAttribute(name = "CountryOfIssue")
   protected String countryOfIssue;
   @XmlAttribute(name = "ExtendedPaymentQuantity")
   protected Integer extendedPaymentQuantity;
   @XmlAttribute(name = "SignatureOnFileIndicator")
   protected Boolean signatureOnFileIndicator;
   @XmlAttribute(name = "CompanyCardReference")
   protected String companyCardReference;
   @XmlAttribute(name = "Remark")
   protected String remark;
   @XmlAttribute(name = "EncryptionKey")
   protected String encryptionKey;
   @XmlAttribute(name = "ShareSynchInd")
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   protected String shareSynchInd;
   @XmlAttribute(name = "ShareMarketInd")
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   protected String shareMarketInd;
   @XmlAttribute(name = "EffectiveDate")
   protected String effectiveDate;
   @XmlAttribute(name = "ExpireDate")
   protected String expireDate;

   public String getCardHolderName() {
      return this.cardHolderName;
   }

   public void setCardHolderName(String value) {
      this.cardHolderName = value;
   }

   public PaymentCardType.CardIssuerName getCardIssuerName() {
      return this.cardIssuerName;
   }

   public void setCardIssuerName(PaymentCardType.CardIssuerName value) {
      this.cardIssuerName = value;
   }

   public AddressType getAddress() {
      return this.address;
   }

   public void setAddress(AddressType value) {
      this.address = value;
   }

   public List<PaymentCardType.Telephone> getTelephone() {
      if (this.telephone == null) {
         this.telephone = new ArrayList<>();
      }

      return this.telephone;
   }

   public List<EmailType> getEmail() {
      if (this.email == null) {
         this.email = new ArrayList<>();
      }

      return this.email;
   }

   public List<PaymentCardType.CustLoyalty> getCustLoyalty() {
      if (this.custLoyalty == null) {
         this.custLoyalty = new ArrayList<>();
      }

      return this.custLoyalty;
   }

   public PaymentCardType.SignatureOnFile getSignatureOnFile() {
      return this.signatureOnFile;
   }

   public void setSignatureOnFile(PaymentCardType.SignatureOnFile value) {
      this.signatureOnFile = value;
   }

   public PaymentCardType.MagneticStripe getMagneticStripe() {
      return this.magneticStripe;
   }

   public void setMagneticStripe(PaymentCardType.MagneticStripe value) {
      this.magneticStripe = value;
   }

   public String getCardType() {
      return this.cardType;
   }

   public void setCardType(String value) {
      this.cardType = value;
   }

   public String getCardCode() {
      return this.cardCode;
   }

   public void setCardCode(String value) {
      this.cardCode = value;
   }

   public String getCardNumber() {
      return this.cardNumber;
   }

   public void setCardNumber(String value) {
      this.cardNumber = value;
   }

   public String getSeriesCode() {
      return this.seriesCode;
   }

   public void setSeriesCode(String value) {
      this.seriesCode = value;
   }

   public String getMaskedCardNumber() {
      return this.maskedCardNumber;
   }

   public void setMaskedCardNumber(String value) {
      this.maskedCardNumber = value;
   }

   public String getCardHolderRPH() {
      return this.cardHolderRPH;
   }

   public void setCardHolderRPH(String value) {
      this.cardHolderRPH = value;
   }

   public Boolean isExtendPaymentIndicator() {
      return this.extendPaymentIndicator;
   }

   public void setExtendPaymentIndicator(Boolean value) {
      this.extendPaymentIndicator = value;
   }

   public String getCountryOfIssue() {
      return this.countryOfIssue;
   }

   public void setCountryOfIssue(String value) {
      this.countryOfIssue = value;
   }

   public Integer getExtendedPaymentQuantity() {
      return this.extendedPaymentQuantity;
   }

   public void setExtendedPaymentQuantity(Integer value) {
      this.extendedPaymentQuantity = value;
   }

   public Boolean isSignatureOnFileIndicator() {
      return this.signatureOnFileIndicator;
   }

   public void setSignatureOnFileIndicator(Boolean value) {
      this.signatureOnFileIndicator = value;
   }

   public String getCompanyCardReference() {
      return this.companyCardReference;
   }

   public void setCompanyCardReference(String value) {
      this.companyCardReference = value;
   }

   public String getRemark() {
      return this.remark;
   }

   public void setRemark(String value) {
      this.remark = value;
   }

   public String getEncryptionKey() {
      return this.encryptionKey;
   }

   public void setEncryptionKey(String value) {
      this.encryptionKey = value;
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

   public String getEffectiveDate() {
      return this.effectiveDate;
   }

   public void setEffectiveDate(String value) {
      this.effectiveDate = value;
   }

   public String getExpireDate() {
      return this.expireDate;
   }

   public void setExpireDate(String value) {
      this.expireDate = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class CardIssuerName {
      @XmlAttribute(name = "BankID")
      protected String bankID;

      public String getBankID() {
         return this.bankID;
      }

      public void setBankID(String value) {
         this.bankID = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class CustLoyalty {
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
   public static class MagneticStripe {
      @XmlAttribute(name = "Track1")
      protected byte[] track1;
      @XmlAttribute(name = "Track2")
      protected byte[] track2;
      @XmlAttribute(name = "Track3")
      protected byte[] track3;

      public byte[] getTrack1() {
         return this.track1;
      }

      public void setTrack1(byte[] value) {
         this.track1 = value;
      }

      public byte[] getTrack2() {
         return this.track2;
      }

      public void setTrack2(byte[] value) {
         this.track2 = value;
      }

      public byte[] getTrack3() {
         return this.track3;
      }

      public void setTrack3(byte[] value) {
         this.track3 = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class SignatureOnFile {
      @XmlAttribute(name = "SignatureOnFileIndicator")
      protected Boolean signatureOnFileIndicator;
      @XmlAttribute(name = "EffectiveDate")
      @XmlSchemaType(name = "date")
      protected XMLGregorianCalendar effectiveDate;
      @XmlAttribute(name = "ExpireDate")
      @XmlSchemaType(name = "date")
      protected XMLGregorianCalendar expireDate;
      @XmlAttribute(name = "ExpireDateExclusiveIndicator")
      protected Boolean expireDateExclusiveIndicator;

      public Boolean isSignatureOnFileIndicator() {
         return this.signatureOnFileIndicator;
      }

      public void setSignatureOnFileIndicator(Boolean value) {
         this.signatureOnFileIndicator = value;
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
   public static class Telephone {
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
