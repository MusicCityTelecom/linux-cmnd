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

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PriceRequestInformationType", propOrder = {"negotiatedFareCode", "rebookOption"})
@XmlSeeAlso(TravelerInfoSummaryType.PriceRequestInformation.class)
public class PriceRequestInformationType {
   @XmlElement(name = "NegotiatedFareCode")
   protected List<PriceRequestInformationType.NegotiatedFareCode> negotiatedFareCode;
   @XmlElement(name = "RebookOption")
   protected List<PriceRequestInformationType.RebookOption> rebookOption;
   @XmlAttribute(name = "CabinType")
   protected CabinType cabinType;
   @XmlAttribute(name = "TicketingCountry")
   protected String ticketingCountry;
   @XmlAttribute(name = "OverrideAirlineCode")
   protected String overrideAirlineCode;
   @XmlAttribute(name = "FareQualifier")
   protected String fareQualifier;
   @XmlAttribute(name = "NegotiatedFaresOnly")
   protected Boolean negotiatedFaresOnly;
   @XmlAttribute(name = "CurrencyCode")
   protected String currencyCode;
   @XmlAttribute(name = "PricingSource")
   protected PricingSourceType pricingSource;
   @XmlAttribute(name = "Reprice")
   protected Boolean reprice;
   @XmlAttribute(name = "ValidatingAirlineCode")
   protected String validatingAirlineCode;
   @XmlAttribute(name = "RequestedTicketingDate")
   protected String requestedTicketingDate;
   @XmlAttribute(name = "SaleCountry")
   protected String saleCountry;

   public List<PriceRequestInformationType.NegotiatedFareCode> getNegotiatedFareCode() {
      if (this.negotiatedFareCode == null) {
         this.negotiatedFareCode = new ArrayList<>();
      }

      return this.negotiatedFareCode;
   }

   public List<PriceRequestInformationType.RebookOption> getRebookOption() {
      if (this.rebookOption == null) {
         this.rebookOption = new ArrayList<>();
      }

      return this.rebookOption;
   }

   public CabinType getCabinType() {
      return this.cabinType;
   }

   public void setCabinType(CabinType value) {
      this.cabinType = value;
   }

   public String getTicketingCountry() {
      return this.ticketingCountry;
   }

   public void setTicketingCountry(String value) {
      this.ticketingCountry = value;
   }

   public String getOverrideAirlineCode() {
      return this.overrideAirlineCode;
   }

   public void setOverrideAirlineCode(String value) {
      this.overrideAirlineCode = value;
   }

   public String getFareQualifier() {
      return this.fareQualifier;
   }

   public void setFareQualifier(String value) {
      this.fareQualifier = value;
   }

   public Boolean isNegotiatedFaresOnly() {
      return this.negotiatedFaresOnly;
   }

   public void setNegotiatedFaresOnly(Boolean value) {
      this.negotiatedFaresOnly = value;
   }

   public String getCurrencyCode() {
      return this.currencyCode;
   }

   public void setCurrencyCode(String value) {
      this.currencyCode = value;
   }

   public PricingSourceType getPricingSource() {
      return this.pricingSource;
   }

   public void setPricingSource(PricingSourceType value) {
      this.pricingSource = value;
   }

   public Boolean isReprice() {
      return this.reprice;
   }

   public void setReprice(Boolean value) {
      this.reprice = value;
   }

   public String getValidatingAirlineCode() {
      return this.validatingAirlineCode;
   }

   public void setValidatingAirlineCode(String value) {
      this.validatingAirlineCode = value;
   }

   public String getRequestedTicketingDate() {
      return this.requestedTicketingDate;
   }

   public void setRequestedTicketingDate(String value) {
      this.requestedTicketingDate = value;
   }

   public String getSaleCountry() {
      return this.saleCountry;
   }

   public void setSaleCountry(String value) {
      this.saleCountry = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "value")
   public static class NegotiatedFareCode {
      @XmlValue
      protected String value;
      @XmlAttribute(name = "TicketDesignatorCode")
      protected String ticketDesignatorCode;
      @XmlAttribute(name = "OverrideRuleInd")
      protected Boolean overrideRuleInd;
      @XmlAttribute(name = "SecondaryCode")
      protected String secondaryCode;
      @XmlAttribute(name = "SupplierCode")
      protected String supplierCode;
      @XmlAttribute(name = "URI")
      @XmlSchemaType(name = "anyURI")
      protected String uri;
      @XmlAttribute(name = "Quantity")
      @XmlSchemaType(name = "nonNegativeInteger")
      protected BigInteger quantity;
      @XmlAttribute(name = "Code")
      protected String code;
      @XmlAttribute(name = "CodeContext")
      protected String codeContext;

      public String getValue() {
         return this.value;
      }

      public void setValue(String value) {
         this.value = value;
      }

      public String getTicketDesignatorCode() {
         return this.ticketDesignatorCode;
      }

      public void setTicketDesignatorCode(String value) {
         this.ticketDesignatorCode = value;
      }

      public Boolean isOverrideRuleInd() {
         return this.overrideRuleInd;
      }

      public void setOverrideRuleInd(Boolean value) {
         this.overrideRuleInd = value;
      }

      public String getSecondaryCode() {
         return this.secondaryCode;
      }

      public void setSecondaryCode(String value) {
         this.secondaryCode = value;
      }

      public String getSupplierCode() {
         return this.supplierCode;
      }

      public void setSupplierCode(String value) {
         this.supplierCode = value;
      }

      public String getURI() {
         return this.uri;
      }

      public void setURI(String value) {
         this.uri = value;
      }

      public BigInteger getQuantity() {
         return this.quantity;
      }

      public void setQuantity(BigInteger value) {
         this.quantity = value;
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

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class RebookOption {
      @XmlAttribute(name = "FlightSegmentRPH", required = true)
      protected String flightSegmentRPH;
      @XmlAttribute(name = "ResBookDesigCode", required = true)
      protected String resBookDesigCode;

      public String getFlightSegmentRPH() {
         return this.flightSegmentRPH;
      }

      public void setFlightSegmentRPH(String value) {
         this.flightSegmentRPH = value;
      }

      public String getResBookDesigCode() {
         return this.resBookDesigCode;
      }

      public void setResBookDesigCode(String value) {
         this.resBookDesigCode = value;
      }
   }
}
