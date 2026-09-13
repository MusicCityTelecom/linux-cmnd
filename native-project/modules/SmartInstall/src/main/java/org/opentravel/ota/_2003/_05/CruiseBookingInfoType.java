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

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CruiseBookingInfoType", propOrder = {"bookingPrices", "paymentSchedule", "guestPrices", "policyInfo"})
public class CruiseBookingInfoType {
   @XmlElement(name = "BookingPrices")
   protected CruiseBookingInfoType.BookingPrices bookingPrices;
   @XmlElement(name = "PaymentSchedule")
   protected CruiseBookingInfoType.PaymentSchedule paymentSchedule;
   @XmlElement(name = "GuestPrices")
   protected CruiseBookingInfoType.GuestPrices guestPrices;
   @XmlElement(name = "PolicyInfo")
   protected List<ParagraphType> policyInfo;

   public CruiseBookingInfoType.BookingPrices getBookingPrices() {
      return this.bookingPrices;
   }

   public void setBookingPrices(CruiseBookingInfoType.BookingPrices value) {
      this.bookingPrices = value;
   }

   public CruiseBookingInfoType.PaymentSchedule getPaymentSchedule() {
      return this.paymentSchedule;
   }

   public void setPaymentSchedule(CruiseBookingInfoType.PaymentSchedule value) {
      this.paymentSchedule = value;
   }

   public CruiseBookingInfoType.GuestPrices getGuestPrices() {
      return this.guestPrices;
   }

   public void setGuestPrices(CruiseBookingInfoType.GuestPrices value) {
      this.guestPrices = value;
   }

   public List<ParagraphType> getPolicyInfo() {
      if (this.policyInfo == null) {
         this.policyInfo = new ArrayList<>();
      }

      return this.policyInfo;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "bookingPrice")
   public static class BookingPrices {
      @XmlElement(name = "BookingPrice", required = true)
      protected List<CruiseBookingInfoType.BookingPrices.BookingPrice> bookingPrice;

      public List<CruiseBookingInfoType.BookingPrices.BookingPrice> getBookingPrice() {
         if (this.bookingPrice == null) {
            this.bookingPrice = new ArrayList<>();
         }

         return this.bookingPrice;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class BookingPrice {
         @XmlAttribute(name = "PriceTypeCode", required = true)
         protected String priceTypeCode;
         @XmlAttribute(name = "Amount")
         protected BigDecimal amount;
         @XmlAttribute(name = "RestrictedIndicator")
         protected Boolean restrictedIndicator;
         @XmlAttribute(name = "CodeDetail")
         protected String codeDetail;
         @XmlAttribute(name = "Percent")
         protected BigDecimal percent;

         public String getPriceTypeCode() {
            return this.priceTypeCode;
         }

         public void setPriceTypeCode(String value) {
            this.priceTypeCode = value;
         }

         public BigDecimal getAmount() {
            return this.amount;
         }

         public void setAmount(BigDecimal value) {
            this.amount = value;
         }

         public Boolean isRestrictedIndicator() {
            return this.restrictedIndicator;
         }

         public void setRestrictedIndicator(Boolean value) {
            this.restrictedIndicator = value;
         }

         public String getCodeDetail() {
            return this.codeDetail;
         }

         public void setCodeDetail(String value) {
            this.codeDetail = value;
         }

         public BigDecimal getPercent() {
            return this.percent;
         }

         public void setPercent(BigDecimal value) {
            this.percent = value;
         }
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "guestPrice")
   public static class GuestPrices {
      @XmlElement(name = "GuestPrice", required = true)
      protected List<CruiseBookingInfoType.GuestPrices.GuestPrice> guestPrice;

      public List<CruiseBookingInfoType.GuestPrices.GuestPrice> getGuestPrice() {
         if (this.guestPrice == null) {
            this.guestPrice = new ArrayList<>();
         }

         return this.guestPrice;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = "priceInfos")
      public static class GuestPrice extends GuestType {
         @XmlElement(name = "PriceInfos", required = true)
         protected CruiseBookingInfoType.GuestPrices.GuestPrice.PriceInfos priceInfos;

         public CruiseBookingInfoType.GuestPrices.GuestPrice.PriceInfos getPriceInfos() {
            return this.priceInfos;
         }

         public void setPriceInfos(CruiseBookingInfoType.GuestPrices.GuestPrice.PriceInfos value) {
            this.priceInfos = value;
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "", propOrder = "priceInfo")
         public static class PriceInfos {
            @XmlElement(name = "PriceInfo", required = true)
            protected List<CruiseBookingInfoType.GuestPrices.GuestPrice.PriceInfos.PriceInfo> priceInfo;

            public List<CruiseBookingInfoType.GuestPrices.GuestPrice.PriceInfos.PriceInfo> getPriceInfo() {
               if (this.priceInfo == null) {
                  this.priceInfo = new ArrayList<>();
               }

               return this.priceInfo;
            }

            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "")
            public static class PriceInfo {
               @XmlAttribute(name = "PriceTypeCode", required = true)
               protected String priceTypeCode;
               @XmlAttribute(name = "Amount")
               protected BigDecimal amount;
               @XmlAttribute(name = "RestrictedIndicator")
               protected Boolean restrictedIndicator;
               @XmlAttribute(name = "CodeDetail")
               protected String codeDetail;
               @XmlAttribute(name = "Percent")
               protected BigDecimal percent;

               public String getPriceTypeCode() {
                  return this.priceTypeCode;
               }

               public void setPriceTypeCode(String value) {
                  this.priceTypeCode = value;
               }

               public BigDecimal getAmount() {
                  return this.amount;
               }

               public void setAmount(BigDecimal value) {
                  this.amount = value;
               }

               public Boolean isRestrictedIndicator() {
                  return this.restrictedIndicator;
               }

               public void setRestrictedIndicator(Boolean value) {
                  this.restrictedIndicator = value;
               }

               public String getCodeDetail() {
                  return this.codeDetail;
               }

               public void setCodeDetail(String value) {
                  this.codeDetail = value;
               }

               public BigDecimal getPercent() {
                  return this.percent;
               }

               public void setPercent(BigDecimal value) {
                  this.percent = value;
               }
            }
         }
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "payment")
   public static class PaymentSchedule {
      @XmlElement(name = "Payment", required = true)
      protected List<CruiseBookingInfoType.PaymentSchedule.Payment> payment;

      public List<CruiseBookingInfoType.PaymentSchedule.Payment> getPayment() {
         if (this.payment == null) {
            this.payment = new ArrayList<>();
         }

         return this.payment;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class Payment {
         @XmlAttribute(name = "PaymentNumber", required = true)
         protected int paymentNumber;
         @XmlAttribute(name = "DueDate", required = true)
         protected String dueDate;
         @XmlAttribute(name = "Amount")
         protected BigDecimal amount;
         @XmlAttribute(name = "CurrencyCode")
         protected String currencyCode;
         @XmlAttribute(name = "DecimalPlaces")
         @XmlSchemaType(name = "nonNegativeInteger")
         protected BigInteger decimalPlaces;

         public int getPaymentNumber() {
            return this.paymentNumber;
         }

         public void setPaymentNumber(int value) {
            this.paymentNumber = value;
         }

         public String getDueDate() {
            return this.dueDate;
         }

         public void setDueDate(String value) {
            this.dueDate = value;
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
}
