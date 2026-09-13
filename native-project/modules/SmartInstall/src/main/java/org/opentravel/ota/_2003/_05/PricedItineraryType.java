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
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PricedItineraryType", propOrder = {"airItinerary", "airItineraryPricingInfo", "notes", "ticketingInfo"})
@XmlSeeAlso(PricedItinerariesType.PricedItinerary.class)
public class PricedItineraryType {
   @XmlElement(name = "AirItinerary")
   protected AirItineraryType airItinerary;
   @XmlElement(name = "AirItineraryPricingInfo")
   protected PricedItineraryType.AirItineraryPricingInfo airItineraryPricingInfo;
   @XmlElement(name = "Notes")
   protected List<FreeTextType> notes;
   @XmlElement(name = "TicketingInfo")
   protected PricedItineraryType.TicketingInfo ticketingInfo;
   @XmlAttribute(name = "SequenceNumber")
   @XmlSchemaType(name = "positiveInteger")
   protected BigInteger sequenceNumber;
   @XmlAttribute(name = "PriceType")
   protected String priceType;
   @XmlAttribute(name = "NUC_Rate")
   protected BigDecimal nucRate;
   @XmlAttribute(name = "ExchangeRate")
   protected BigDecimal exchangeRate;

   public AirItineraryType getAirItinerary() {
      return this.airItinerary;
   }

   public void setAirItinerary(AirItineraryType value) {
      this.airItinerary = value;
   }

   public PricedItineraryType.AirItineraryPricingInfo getAirItineraryPricingInfo() {
      return this.airItineraryPricingInfo;
   }

   public void setAirItineraryPricingInfo(PricedItineraryType.AirItineraryPricingInfo value) {
      this.airItineraryPricingInfo = value;
   }

   public List<FreeTextType> getNotes() {
      if (this.notes == null) {
         this.notes = new ArrayList<>();
      }

      return this.notes;
   }

   public PricedItineraryType.TicketingInfo getTicketingInfo() {
      return this.ticketingInfo;
   }

   public void setTicketingInfo(PricedItineraryType.TicketingInfo value) {
      this.ticketingInfo = value;
   }

   public BigInteger getSequenceNumber() {
      return this.sequenceNumber;
   }

   public void setSequenceNumber(BigInteger value) {
      this.sequenceNumber = value;
   }

   public String getPriceType() {
      return this.priceType;
   }

   public void setPriceType(String value) {
      this.priceType = value;
   }

   public BigDecimal getNUCRate() {
      return this.nucRate;
   }

   public void setNUCRate(BigDecimal value) {
      this.nucRate = value;
   }

   public BigDecimal getExchangeRate() {
      return this.exchangeRate;
   }

   public void setExchangeRate(BigDecimal value) {
      this.exchangeRate = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class AirItineraryPricingInfo extends AirItineraryPricingInfoType {
      @XmlAttribute(name = "RepriceRequired")
      protected Boolean repriceRequired;

      public Boolean isRepriceRequired() {
         return this.repriceRequired;
      }

      public void setRepriceRequired(Boolean value) {
         this.repriceRequired = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "deliveryInfo")
   public static class TicketingInfo extends TicketingInfoRSType {
      @XmlElement(name = "DeliveryInfo")
      protected List<PricedItineraryType.TicketingInfo.DeliveryInfo> deliveryInfo;
      @XmlAttribute(name = "PaymentType")
      protected List<String> paymentType;

      public List<PricedItineraryType.TicketingInfo.DeliveryInfo> getDeliveryInfo() {
         if (this.deliveryInfo == null) {
            this.deliveryInfo = new ArrayList<>();
         }

         return this.deliveryInfo;
      }

      public List<String> getPaymentType() {
         if (this.paymentType == null) {
            this.paymentType = new ArrayList<>();
         }

         return this.paymentType;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class DeliveryInfo {
         @XmlAttribute(name = "DistribType")
         protected String distribType;
         @XmlAttribute(name = "Amount")
         protected BigDecimal amount;
         @XmlAttribute(name = "CurrencyCode")
         protected String currencyCode;
         @XmlAttribute(name = "DecimalPlaces")
         @XmlSchemaType(name = "nonNegativeInteger")
         protected BigInteger decimalPlaces;

         public String getDistribType() {
            return this.distribType;
         }

         public void setDistribType(String value) {
            this.distribType = value;
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
