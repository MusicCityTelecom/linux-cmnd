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
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "FareType",
   propOrder = {
         "baseFare",
         "equivFare",
         "taxes",
         "fees",
         "totalFare",
         "fareConstruction",
         "unstructuredFareCalc",
         "fareBaggageAllowance",
         "tourCode",
         "remark",
         "originalIssueInfo",
         "exchangeInfo",
         "discounts",
         "tpaExtensions"
   }
)
@XmlSeeAlso({AirItineraryPricingInfoType.ItinTotalFare.class, PTCFareBreakdownType.PassengerFare.class})
public class FareType {
   @XmlElement(name = "BaseFare")
   protected FareType.BaseFare baseFare;
   @XmlElement(name = "EquivFare")
   protected List<FareType.EquivFare> equivFare;
   @XmlElement(name = "Taxes")
   protected FareType.Taxes taxes;
   @XmlElement(name = "Fees")
   protected FareType.Fees fees;
   @XmlElement(name = "TotalFare")
   protected FareType.TotalFare totalFare;
   @XmlElement(name = "FareConstruction")
   protected FareType.FareConstruction fareConstruction;
   @XmlElement(name = "UnstructuredFareCalc")
   protected FareType.UnstructuredFareCalc unstructuredFareCalc;
   @XmlElement(name = "FareBaggageAllowance")
   protected List<FareType.FareBaggageAllowance> fareBaggageAllowance;
   @XmlElement(name = "TourCode")
   protected FareType.TourCode tourCode;
   @XmlElement(name = "Remark")
   protected List<FareType.Remark> remark;
   @XmlElement(name = "OriginalIssueInfo")
   protected FareType.OriginalIssueInfo originalIssueInfo;
   @XmlElement(name = "ExchangeInfo")
   protected FareType.ExchangeInfo exchangeInfo;
   @XmlElement(name = "Discounts")
   protected FareType.Discounts discounts;
   @XmlElement(name = "TPA_Extensions")
   protected TPAExtensionsType tpaExtensions;
   @XmlAttribute(name = "TicketDesignatorCode")
   protected String ticketDesignatorCode;
   @XmlAttribute(name = "TotalNbrTrips")
   protected Integer totalNbrTrips;
   @XmlAttribute(name = "TotalNbrPTC")
   protected Integer totalNbrPTC;
   @XmlAttribute(name = "NegotiatedFare")
   protected Boolean negotiatedFare;
   @XmlAttribute(name = "NegotiatedFareCode")
   protected String negotiatedFareCode;

   public FareType.BaseFare getBaseFare() {
      return this.baseFare;
   }

   public void setBaseFare(FareType.BaseFare value) {
      this.baseFare = value;
   }

   public List<FareType.EquivFare> getEquivFare() {
      if (this.equivFare == null) {
         this.equivFare = new ArrayList<>();
      }

      return this.equivFare;
   }

   public FareType.Taxes getTaxes() {
      return this.taxes;
   }

   public void setTaxes(FareType.Taxes value) {
      this.taxes = value;
   }

   public FareType.Fees getFees() {
      return this.fees;
   }

   public void setFees(FareType.Fees value) {
      this.fees = value;
   }

   public FareType.TotalFare getTotalFare() {
      return this.totalFare;
   }

   public void setTotalFare(FareType.TotalFare value) {
      this.totalFare = value;
   }

   public FareType.FareConstruction getFareConstruction() {
      return this.fareConstruction;
   }

   public void setFareConstruction(FareType.FareConstruction value) {
      this.fareConstruction = value;
   }

   public FareType.UnstructuredFareCalc getUnstructuredFareCalc() {
      return this.unstructuredFareCalc;
   }

   public void setUnstructuredFareCalc(FareType.UnstructuredFareCalc value) {
      this.unstructuredFareCalc = value;
   }

   public List<FareType.FareBaggageAllowance> getFareBaggageAllowance() {
      if (this.fareBaggageAllowance == null) {
         this.fareBaggageAllowance = new ArrayList<>();
      }

      return this.fareBaggageAllowance;
   }

   public FareType.TourCode getTourCode() {
      return this.tourCode;
   }

   public void setTourCode(FareType.TourCode value) {
      this.tourCode = value;
   }

   public List<FareType.Remark> getRemark() {
      if (this.remark == null) {
         this.remark = new ArrayList<>();
      }

      return this.remark;
   }

   public FareType.OriginalIssueInfo getOriginalIssueInfo() {
      return this.originalIssueInfo;
   }

   public void setOriginalIssueInfo(FareType.OriginalIssueInfo value) {
      this.originalIssueInfo = value;
   }

   public FareType.ExchangeInfo getExchangeInfo() {
      return this.exchangeInfo;
   }

   public void setExchangeInfo(FareType.ExchangeInfo value) {
      this.exchangeInfo = value;
   }

   public FareType.Discounts getDiscounts() {
      return this.discounts;
   }

   public void setDiscounts(FareType.Discounts value) {
      this.discounts = value;
   }

   public TPAExtensionsType getTPAExtensions() {
      return this.tpaExtensions;
   }

   public void setTPAExtensions(TPAExtensionsType value) {
      this.tpaExtensions = value;
   }

   public String getTicketDesignatorCode() {
      return this.ticketDesignatorCode;
   }

   public void setTicketDesignatorCode(String value) {
      this.ticketDesignatorCode = value;
   }

   public Integer getTotalNbrTrips() {
      return this.totalNbrTrips;
   }

   public void setTotalNbrTrips(Integer value) {
      this.totalNbrTrips = value;
   }

   public Integer getTotalNbrPTC() {
      return this.totalNbrPTC;
   }

   public void setTotalNbrPTC(Integer value) {
      this.totalNbrPTC = value;
   }

   public Boolean isNegotiatedFare() {
      return this.negotiatedFare;
   }

   public void setNegotiatedFare(Boolean value) {
      this.negotiatedFare = value;
   }

   public String getNegotiatedFareCode() {
      return this.negotiatedFareCode;
   }

   public void setNegotiatedFareCode(String value) {
      this.negotiatedFareCode = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class BaseFare {
      @XmlAttribute(name = "Operation")
      protected ActionType operation;
      @XmlAttribute(name = "FromCurrency")
      protected String fromCurrency;
      @XmlAttribute(name = "ToCurrency")
      protected String toCurrency;
      @XmlAttribute(name = "Rate")
      protected BigDecimal rate;
      @XmlAttribute(name = "Date")
      @XmlSchemaType(name = "date")
      protected XMLGregorianCalendar date;
      @XmlAttribute(name = "Amount")
      protected BigDecimal amount;
      @XmlAttribute(name = "CurrencyCode")
      protected String currencyCode;
      @XmlAttribute(name = "DecimalPlaces")
      @XmlSchemaType(name = "nonNegativeInteger")
      protected BigInteger decimalPlaces;

      public ActionType getOperation() {
         return this.operation;
      }

      public void setOperation(ActionType value) {
         this.operation = value;
      }

      public String getFromCurrency() {
         return this.fromCurrency;
      }

      public void setFromCurrency(String value) {
         this.fromCurrency = value;
      }

      public String getToCurrency() {
         return this.toCurrency;
      }

      public void setToCurrency(String value) {
         this.toCurrency = value;
      }

      public BigDecimal getRate() {
         return this.rate;
      }

      public void setRate(BigDecimal value) {
         this.rate = value;
      }

      public XMLGregorianCalendar getDate() {
         return this.date;
      }

      public void setDate(XMLGregorianCalendar value) {
         this.date = value;
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
   @XmlType(name = "", propOrder = "discount")
   public static class Discounts {
      @XmlElement(name = "Discount", required = true)
      protected List<FareType.Discounts.Discount> discount;

      public List<FareType.Discounts.Discount> getDiscount() {
         if (this.discount == null) {
            this.discount = new ArrayList<>();
         }

         return this.discount;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class Discount {
         @XmlAttribute(name = "Percent")
         protected BigDecimal percent;
         @XmlAttribute(name = "ID")
         protected String id;
         @XmlAttribute(name = "Description")
         protected String description;
         @XmlAttribute(name = "Amount")
         protected BigDecimal amount;
         @XmlAttribute(name = "CurrencyCode")
         protected String currencyCode;
         @XmlAttribute(name = "DecimalPlaces")
         @XmlSchemaType(name = "nonNegativeInteger")
         protected BigInteger decimalPlaces;

         public BigDecimal getPercent() {
            return this.percent;
         }

         public void setPercent(BigDecimal value) {
            this.percent = value;
         }

         public String getID() {
            return this.id;
         }

         public void setID(String value) {
            this.id = value;
         }

         public String getDescription() {
            return this.description;
         }

         public void setDescription(String value) {
            this.description = value;
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

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class EquivFare {
      @XmlAttribute(name = "Operation")
      protected ActionType operation;
      @XmlAttribute(name = "Amount")
      protected BigDecimal amount;
      @XmlAttribute(name = "CurrencyCode")
      protected String currencyCode;
      @XmlAttribute(name = "DecimalPlaces")
      @XmlSchemaType(name = "nonNegativeInteger")
      protected BigInteger decimalPlaces;

      public ActionType getOperation() {
         return this.operation;
      }

      public void setOperation(ActionType value) {
         this.operation = value;
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
   @XmlType(name = "", propOrder = {"couponInfo", "originalOriginDestination"})
   public static class ExchangeInfo {
      @XmlElement(name = "CouponInfo")
      protected List<FareType.ExchangeInfo.CouponInfo> couponInfo;
      @XmlElement(name = "OriginalOriginDestination")
      protected FareType.ExchangeInfo.OriginalOriginDestination originalOriginDestination;
      @XmlAttribute(name = "TicketDocumentNbr")
      protected String ticketDocumentNbr;

      public List<FareType.ExchangeInfo.CouponInfo> getCouponInfo() {
         if (this.couponInfo == null) {
            this.couponInfo = new ArrayList<>();
         }

         return this.couponInfo;
      }

      public FareType.ExchangeInfo.OriginalOriginDestination getOriginalOriginDestination() {
         return this.originalOriginDestination;
      }

      public void setOriginalOriginDestination(FareType.ExchangeInfo.OriginalOriginDestination value) {
         this.originalOriginDestination = value;
      }

      public String getTicketDocumentNbr() {
         return this.ticketDocumentNbr;
      }

      public void setTicketDocumentNbr(String value) {
         this.ticketDocumentNbr = value;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class CouponInfo {
         @XmlAttribute(name = "Number")
         protected Integer number;

         public Integer getNumber() {
            return this.number;
         }

         public void setNumber(Integer value) {
            this.number = value;
         }
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class OriginalOriginDestination {
         @XmlAttribute(name = "OriginCityCode")
         protected String originCityCode;
         @XmlAttribute(name = "DestinationCityCode")
         protected String destinationCityCode;

         public String getOriginCityCode() {
            return this.originCityCode;
         }

         public void setOriginCityCode(String value) {
            this.originCityCode = value;
         }

         public String getDestinationCityCode() {
            return this.destinationCityCode;
         }

         public void setDestinationCityCode(String value) {
            this.destinationCityCode = value;
         }
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class FareBaggageAllowance {
      @XmlAttribute(name = "FlightSegmentRPH")
      protected String flightSegmentRPH;
      @XmlAttribute(name = "Operation")
      protected ActionType operation;
      @XmlAttribute(name = "UnitOfMeasureQuantity")
      protected BigDecimal unitOfMeasureQuantity;
      @XmlAttribute(name = "UnitOfMeasure")
      protected String unitOfMeasure;
      @XmlAttribute(name = "UnitOfMeasureCode")
      protected String unitOfMeasureCode;

      public String getFlightSegmentRPH() {
         return this.flightSegmentRPH;
      }

      public void setFlightSegmentRPH(String value) {
         this.flightSegmentRPH = value;
      }

      public ActionType getOperation() {
         return this.operation;
      }

      public void setOperation(ActionType value) {
         this.operation = value;
      }

      public BigDecimal getUnitOfMeasureQuantity() {
         return this.unitOfMeasureQuantity;
      }

      public void setUnitOfMeasureQuantity(BigDecimal value) {
         this.unitOfMeasureQuantity = value;
      }

      public String getUnitOfMeasure() {
         return this.unitOfMeasure;
      }

      public void setUnitOfMeasure(String value) {
         this.unitOfMeasure = value;
      }

      public String getUnitOfMeasureCode() {
         return this.unitOfMeasureCode;
      }

      public void setUnitOfMeasureCode(String value) {
         this.unitOfMeasureCode = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class FareConstruction {
      @XmlAttribute(name = "FormattedIndicator")
      protected Boolean formattedIndicator;
      @XmlAttribute(name = "Language")
      @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
      @XmlSchemaType(name = "language")
      protected String language;
      @XmlAttribute(name = "OriginCityCode")
      protected String originCityCode;
      @XmlAttribute(name = "OriginCodeContext")
      protected String originCodeContext;
      @XmlAttribute(name = "DestinationCityCode")
      protected String destinationCityCode;
      @XmlAttribute(name = "DestinationCodeContext")
      protected String destinationCodeContext;
      @XmlAttribute(name = "Operation")
      protected ActionType operation;

      public Boolean isFormattedIndicator() {
         return this.formattedIndicator;
      }

      public void setFormattedIndicator(Boolean value) {
         this.formattedIndicator = value;
      }

      public String getLanguage() {
         return this.language;
      }

      public void setLanguage(String value) {
         this.language = value;
      }

      public String getOriginCityCode() {
         return this.originCityCode;
      }

      public void setOriginCityCode(String value) {
         this.originCityCode = value;
      }

      public String getOriginCodeContext() {
         return this.originCodeContext;
      }

      public void setOriginCodeContext(String value) {
         this.originCodeContext = value;
      }

      public String getDestinationCityCode() {
         return this.destinationCityCode;
      }

      public void setDestinationCityCode(String value) {
         this.destinationCityCode = value;
      }

      public String getDestinationCodeContext() {
         return this.destinationCodeContext;
      }

      public void setDestinationCodeContext(String value) {
         this.destinationCodeContext = value;
      }

      public ActionType getOperation() {
         return this.operation;
      }

      public void setOperation(ActionType value) {
         this.operation = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "fee")
   public static class Fees {
      @XmlElement(name = "Fee", required = true)
      protected List<AirFeeType> fee;
      @XmlAttribute(name = "Amount")
      protected BigDecimal amount;
      @XmlAttribute(name = "CurrencyCode")
      protected String currencyCode;
      @XmlAttribute(name = "DecimalPlaces")
      @XmlSchemaType(name = "nonNegativeInteger")
      protected BigInteger decimalPlaces;

      public List<AirFeeType> getFee() {
         if (this.fee == null) {
            this.fee = new ArrayList<>();
         }

         return this.fee;
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
   @XmlType(name = "")
   public static class OriginalIssueInfo {
      @XmlAttribute(name = "TicketDocumentNbr")
      protected String ticketDocumentNbr;
      @XmlAttribute(name = "IssuingAgentID")
      protected String issuingAgentID;
      @XmlAttribute(name = "DateOfIssue")
      @XmlSchemaType(name = "date")
      protected XMLGregorianCalendar dateOfIssue;
      @XmlAttribute(name = "LocationCode")
      protected String locationCode;
      @XmlAttribute(name = "IssuingAirlineCode")
      protected String issuingAirlineCode;

      public String getTicketDocumentNbr() {
         return this.ticketDocumentNbr;
      }

      public void setTicketDocumentNbr(String value) {
         this.ticketDocumentNbr = value;
      }

      public String getIssuingAgentID() {
         return this.issuingAgentID;
      }

      public void setIssuingAgentID(String value) {
         this.issuingAgentID = value;
      }

      public XMLGregorianCalendar getDateOfIssue() {
         return this.dateOfIssue;
      }

      public void setDateOfIssue(XMLGregorianCalendar value) {
         this.dateOfIssue = value;
      }

      public String getLocationCode() {
         return this.locationCode;
      }

      public void setLocationCode(String value) {
         this.locationCode = value;
      }

      public String getIssuingAirlineCode() {
         return this.issuingAirlineCode;
      }

      public void setIssuingAirlineCode(String value) {
         this.issuingAirlineCode = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "value")
   public static class Remark {
      @XmlValue
      protected String value;

      public String getValue() {
         return this.value;
      }

      public void setValue(String value) {
         this.value = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "tax")
   public static class Taxes {
      @XmlElement(name = "Tax", required = true)
      protected List<AirTaxType> tax;
      @XmlAttribute(name = "Amount")
      protected BigDecimal amount;

      public List<AirTaxType> getTax() {
         if (this.tax == null) {
            this.tax = new ArrayList<>();
         }

         return this.tax;
      }

      public BigDecimal getAmount() {
         return this.amount;
      }

      public void setAmount(BigDecimal value) {
         this.amount = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class TotalFare {
      @XmlAttribute(name = "Operation")
      protected ActionType operation;
      @XmlAttribute(name = "Amount")
      protected BigDecimal amount;
      @XmlAttribute(name = "CurrencyCode")
      protected String currencyCode;
      @XmlAttribute(name = "DecimalPlaces")
      @XmlSchemaType(name = "nonNegativeInteger")
      protected BigInteger decimalPlaces;

      public ActionType getOperation() {
         return this.operation;
      }

      public void setOperation(ActionType value) {
         this.operation = value;
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
   @XmlType(name = "", propOrder = "value")
   public static class TourCode {
      @XmlValue
      protected String value;
      @XmlAttribute(name = "Operation")
      protected ActionType operation;

      public String getValue() {
         return this.value;
      }

      public void setValue(String value) {
         this.value = value;
      }

      public ActionType getOperation() {
         return this.operation;
      }

      public void setOperation(ActionType value) {
         this.operation = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "value")
   public static class UnstructuredFareCalc {
      @XmlValue
      protected String value;
      @XmlAttribute(name = "FareCalcMode")
      protected String fareCalcMode;
      @XmlAttribute(name = "Operation")
      protected ActionType operation;

      public String getValue() {
         return this.value;
      }

      public void setValue(String value) {
         this.value = value;
      }

      public String getFareCalcMode() {
         return this.fareCalcMode;
      }

      public void setFareCalcMode(String value) {
         this.fareCalcMode = value;
      }

      public ActionType getOperation() {
         return this.operation;
      }

      public void setOperation(ActionType value) {
         this.operation = value;
      }
   }
}
