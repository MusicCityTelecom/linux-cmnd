package org.opentravel.ota._2003._05;

import java.math.BigDecimal;
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
   name = "AuthorizationType",
   propOrder = {"checkAuthorization", "creditCardAuthorization", "accountAuthorization", "driversLicenseAuthorization", "bookingReferenceID"}
)
public class AuthorizationType {
   @XmlElement(name = "CheckAuthorization")
   protected AuthorizationType.CheckAuthorization checkAuthorization;
   @XmlElement(name = "CreditCardAuthorization")
   protected AuthorizationType.CreditCardAuthorization creditCardAuthorization;
   @XmlElement(name = "AccountAuthorization")
   protected AuthorizationType.AccountAuthorization accountAuthorization;
   @XmlElement(name = "DriversLicenseAuthorization")
   protected DocumentType driversLicenseAuthorization;
   @XmlElement(name = "BookingReferenceID")
   protected AuthorizationType.BookingReferenceID bookingReferenceID;
   @XmlAttribute(name = "PrincipalCompanyCode")
   protected String principalCompanyCode;
   @XmlAttribute(name = "RefNumber")
   protected String refNumber;

   public AuthorizationType.CheckAuthorization getCheckAuthorization() {
      return this.checkAuthorization;
   }

   public void setCheckAuthorization(AuthorizationType.CheckAuthorization value) {
      this.checkAuthorization = value;
   }

   public AuthorizationType.CreditCardAuthorization getCreditCardAuthorization() {
      return this.creditCardAuthorization;
   }

   public void setCreditCardAuthorization(AuthorizationType.CreditCardAuthorization value) {
      this.creditCardAuthorization = value;
   }

   public AuthorizationType.AccountAuthorization getAccountAuthorization() {
      return this.accountAuthorization;
   }

   public void setAccountAuthorization(AuthorizationType.AccountAuthorization value) {
      this.accountAuthorization = value;
   }

   public DocumentType getDriversLicenseAuthorization() {
      return this.driversLicenseAuthorization;
   }

   public void setDriversLicenseAuthorization(DocumentType value) {
      this.driversLicenseAuthorization = value;
   }

   public AuthorizationType.BookingReferenceID getBookingReferenceID() {
      return this.bookingReferenceID;
   }

   public void setBookingReferenceID(AuthorizationType.BookingReferenceID value) {
      this.bookingReferenceID = value;
   }

   public String getPrincipalCompanyCode() {
      return this.principalCompanyCode;
   }

   public void setPrincipalCompanyCode(String value) {
      this.principalCompanyCode = value;
   }

   public String getRefNumber() {
      return this.refNumber;
   }

   public void setRefNumber(String value) {
      this.refNumber = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "accountInfo")
   public static class AccountAuthorization {
      @XmlElement(name = "AccountInfo")
      protected AuthorizationType.AccountAuthorization.AccountInfo accountInfo;
      @XmlAttribute(name = "NonISO_CurrencyCode")
      protected String nonISOCurrencyCode;
      @XmlAttribute(name = "Amount")
      protected BigDecimal amount;
      @XmlAttribute(name = "CurrencyCode")
      protected String currencyCode;
      @XmlAttribute(name = "DecimalPlaces")
      @XmlSchemaType(name = "nonNegativeInteger")
      protected BigInteger decimalPlaces;

      public AuthorizationType.AccountAuthorization.AccountInfo getAccountInfo() {
         return this.accountInfo;
      }

      public void setAccountInfo(AuthorizationType.AccountAuthorization.AccountInfo value) {
         this.accountInfo = value;
      }

      public String getNonISOCurrencyCode() {
         return this.nonISOCurrencyCode;
      }

      public void setNonISOCurrencyCode(String value) {
         this.nonISOCurrencyCode = value;
      }

      public BigDecimal getAmount() {
         return this.amount;
      }

      public void setAmount(BigDecimal value) {
         this.amount = value;
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
      public static class AccountInfo {
         @XmlAttribute(name = "AccountName")
         protected String accountName;
         @XmlAttribute(name = "CompanyName")
         protected String companyName;
         @XmlAttribute(name = "AccountID")
         protected String accountID;
         @XmlAttribute(name = "Password")
         protected String password;
         @XmlAttribute(name = "Code")
         protected String code;

         public String getAccountName() {
            return this.accountName;
         }

         public void setAccountName(String value) {
            this.accountName = value;
         }

         public String getCompanyName() {
            return this.companyName;
         }

         public void setCompanyName(String value) {
            this.companyName = value;
         }

         public String getAccountID() {
            return this.accountID;
         }

         public void setAccountID(String value) {
            this.accountID = value;
         }

         public String getPassword() {
            return this.password;
         }

         public void setPassword(String value) {
            this.password = value;
         }

         public String getCode() {
            return this.code;
         }

         public void setCode(String value) {
            this.code = value;
         }
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class BookingReferenceID extends UniqueIDType {
      @XmlAttribute(name = "IgnoreReservationInd")
      protected Boolean ignoreReservationInd;

      public Boolean isIgnoreReservationInd() {
         return this.ignoreReservationInd;
      }

      public void setIgnoreReservationInd(Boolean value) {
         this.ignoreReservationInd = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "bankAcct")
   public static class CheckAuthorization {
      @XmlElement(name = "BankAcct", required = true)
      protected BankAcctType bankAcct;
      @XmlAttribute(name = "Amount")
      protected BigDecimal amount;
      @XmlAttribute(name = "CurrencyCode")
      protected String currencyCode;
      @XmlAttribute(name = "DecimalPlaces")
      @XmlSchemaType(name = "nonNegativeInteger")
      protected BigInteger decimalPlaces;

      public BankAcctType getBankAcct() {
         return this.bankAcct;
      }

      public void setBankAcct(BankAcctType value) {
         this.bankAcct = value;
      }

      public BigDecimal getAmount() {
         return this.amount;
      }

      public void setAmount(BigDecimal value) {
         this.amount = value;
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
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = {"creditCard", "id"})
   public static class CreditCardAuthorization {
      @XmlElement(name = "CreditCard", required = true)
      protected PaymentCardType creditCard;
      @XmlElement(name = "ID")
      protected List<UniqueIDType> id;
      @XmlAttribute(name = "SourceType")
      @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
      protected String sourceType;
      @XmlAttribute(name = "ExtendedPaymentInd")
      protected Boolean extendedPaymentInd;
      @XmlAttribute(name = "ExtendedPaymentQuantity")
      protected Integer extendedPaymentQuantity;
      @XmlAttribute(name = "ExtendedPaymentFrequency")
      protected TimeUnitType extendedPaymentFrequency;
      @XmlAttribute(name = "AuthorizationCode")
      protected String authorizationCode;
      @XmlAttribute(name = "ReversalIndicator")
      protected Boolean reversalIndicator;
      @XmlAttribute(name = "CardPresentInd")
      protected Boolean cardPresentInd;
      @XmlAttribute(name = "E_CommerceCode")
      protected String eCommerceCode;
      @XmlAttribute(name = "AuthTransactionID")
      protected String authTransactionID;
      @XmlAttribute(name = "AuthVerificationValue")
      protected String authVerificationValue;
      @XmlAttribute(name = "Amount")
      protected BigDecimal amount;
      @XmlAttribute(name = "CurrencyCode")
      protected String currencyCode;
      @XmlAttribute(name = "DecimalPlaces")
      @XmlSchemaType(name = "nonNegativeInteger")
      protected BigInteger decimalPlaces;

      public PaymentCardType getCreditCard() {
         return this.creditCard;
      }

      public void setCreditCard(PaymentCardType value) {
         this.creditCard = value;
      }

      public List<UniqueIDType> getID() {
         if (this.id == null) {
            this.id = new ArrayList<>();
         }

         return this.id;
      }

      public String getSourceType() {
         return this.sourceType;
      }

      public void setSourceType(String value) {
         this.sourceType = value;
      }

      public Boolean isExtendedPaymentInd() {
         return this.extendedPaymentInd;
      }

      public void setExtendedPaymentInd(Boolean value) {
         this.extendedPaymentInd = value;
      }

      public Integer getExtendedPaymentQuantity() {
         return this.extendedPaymentQuantity;
      }

      public void setExtendedPaymentQuantity(Integer value) {
         this.extendedPaymentQuantity = value;
      }

      public TimeUnitType getExtendedPaymentFrequency() {
         return this.extendedPaymentFrequency;
      }

      public void setExtendedPaymentFrequency(TimeUnitType value) {
         this.extendedPaymentFrequency = value;
      }

      public String getAuthorizationCode() {
         return this.authorizationCode;
      }

      public void setAuthorizationCode(String value) {
         this.authorizationCode = value;
      }

      public Boolean isReversalIndicator() {
         return this.reversalIndicator;
      }

      public void setReversalIndicator(Boolean value) {
         this.reversalIndicator = value;
      }

      public Boolean isCardPresentInd() {
         return this.cardPresentInd;
      }

      public void setCardPresentInd(Boolean value) {
         this.cardPresentInd = value;
      }

      public String getECommerceCode() {
         return this.eCommerceCode;
      }

      public void setECommerceCode(String value) {
         this.eCommerceCode = value;
      }

      public String getAuthTransactionID() {
         return this.authTransactionID;
      }

      public void setAuthTransactionID(String value) {
         this.authTransactionID = value;
      }

      public String getAuthVerificationValue() {
         return this.authVerificationValue;
      }

      public void setAuthVerificationValue(String value) {
         this.authVerificationValue = value;
      }

      public BigDecimal getAmount() {
         return this.amount;
      }

      public void setAmount(BigDecimal value) {
         this.amount = value;
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
   }
}
