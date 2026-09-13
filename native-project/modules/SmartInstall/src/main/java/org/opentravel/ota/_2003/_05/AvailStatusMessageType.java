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
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.Duration;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "AvailStatusMessageType",
   propOrder = {"statusApplicationControl", "lengthsOfStay", "bestAvailableRates", "hurdleRate", "delta", "uniqueID", "restrictionStatus"}
)
@XmlSeeAlso(OTANotifReportRQ.NotifDetails.HotelNotifReport.AvailStatusMessages.AvailStatusMessage.class)
public class AvailStatusMessageType {
   @XmlElement(name = "StatusApplicationControl")
   protected StatusApplicationControlType statusApplicationControl;
   @XmlElement(name = "LengthsOfStay")
   protected LengthsOfStayType lengthsOfStay;
   @XmlElement(name = "BestAvailableRates")
   protected AvailStatusMessageType.BestAvailableRates bestAvailableRates;
   @XmlElement(name = "HurdleRate")
   protected AvailStatusMessageType.HurdleRate hurdleRate;
   @XmlElement(name = "Delta")
   protected AvailStatusMessageType.Delta delta;
   @XmlElement(name = "UniqueID")
   protected UniqueIDType uniqueID;
   @XmlElement(name = "RestrictionStatus")
   protected AvailStatusMessageType.RestrictionStatus restrictionStatus;
   @XmlAttribute(name = "Override")
   protected Boolean override;
   @XmlAttribute(name = "BookingLimitMessageType")
   protected String bookingLimitMessageType;
   @XmlAttribute(name = "BookingLimit")
   @XmlSchemaType(name = "nonNegativeInteger")
   protected BigInteger bookingLimit;
   @XmlAttribute(name = "LocatorID")
   @XmlSchemaType(name = "positiveInteger")
   protected BigInteger locatorID;
   @XmlAttribute(name = "BookingThreshold")
   protected BigInteger bookingThreshold;
   @XmlAttribute(name = "RoomGender")
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   protected String roomGender;
   @XmlAttribute(name = "SharedRoomInd")
   protected Boolean sharedRoomInd;

   public StatusApplicationControlType getStatusApplicationControl() {
      return this.statusApplicationControl;
   }

   public void setStatusApplicationControl(StatusApplicationControlType value) {
      this.statusApplicationControl = value;
   }

   public LengthsOfStayType getLengthsOfStay() {
      return this.lengthsOfStay;
   }

   public void setLengthsOfStay(LengthsOfStayType value) {
      this.lengthsOfStay = value;
   }

   public AvailStatusMessageType.BestAvailableRates getBestAvailableRates() {
      return this.bestAvailableRates;
   }

   public void setBestAvailableRates(AvailStatusMessageType.BestAvailableRates value) {
      this.bestAvailableRates = value;
   }

   public AvailStatusMessageType.HurdleRate getHurdleRate() {
      return this.hurdleRate;
   }

   public void setHurdleRate(AvailStatusMessageType.HurdleRate value) {
      this.hurdleRate = value;
   }

   public AvailStatusMessageType.Delta getDelta() {
      return this.delta;
   }

   public void setDelta(AvailStatusMessageType.Delta value) {
      this.delta = value;
   }

   public UniqueIDType getUniqueID() {
      return this.uniqueID;
   }

   public void setUniqueID(UniqueIDType value) {
      this.uniqueID = value;
   }

   public AvailStatusMessageType.RestrictionStatus getRestrictionStatus() {
      return this.restrictionStatus;
   }

   public void setRestrictionStatus(AvailStatusMessageType.RestrictionStatus value) {
      this.restrictionStatus = value;
   }

   public Boolean isOverride() {
      return this.override;
   }

   public void setOverride(Boolean value) {
      this.override = value;
   }

   public String getBookingLimitMessageType() {
      return this.bookingLimitMessageType;
   }

   public void setBookingLimitMessageType(String value) {
      this.bookingLimitMessageType = value;
   }

   public BigInteger getBookingLimit() {
      return this.bookingLimit;
   }

   public void setBookingLimit(BigInteger value) {
      this.bookingLimit = value;
   }

   public BigInteger getLocatorID() {
      return this.locatorID;
   }

   public void setLocatorID(BigInteger value) {
      this.locatorID = value;
   }

   public BigInteger getBookingThreshold() {
      return this.bookingThreshold;
   }

   public void setBookingThreshold(BigInteger value) {
      this.bookingThreshold = value;
   }

   public String getRoomGender() {
      return this.roomGender;
   }

   public void setRoomGender(String value) {
      this.roomGender = value;
   }

   public Boolean isSharedRoomInd() {
      return this.sharedRoomInd;
   }

   public void setSharedRoomInd(Boolean value) {
      this.sharedRoomInd = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "bestAvailableRate")
   public static class BestAvailableRates {
      @XmlElement(name = "BestAvailableRate", required = true)
      protected List<AvailStatusMessageType.BestAvailableRates.BestAvailableRate> bestAvailableRate;

      public List<AvailStatusMessageType.BestAvailableRates.BestAvailableRate> getBestAvailableRate() {
         if (this.bestAvailableRate == null) {
            this.bestAvailableRate = new ArrayList<>();
         }

         return this.bestAvailableRate;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class BestAvailableRate {
         @XmlAttribute(name = "LengthOfStayTime")
         protected BigInteger lengthOfStayTime;
         @XmlAttribute(name = "LengthOfStayTimeUnit")
         protected TimeUnitType lengthOfStayTimeUnit;
         @XmlAttribute(name = "RatePlanCode")
         protected String ratePlanCode;
         @XmlAttribute(name = "TaxInclusive")
         protected Boolean taxInclusive;
         @XmlAttribute(name = "Amount")
         protected BigDecimal amount;
         @XmlAttribute(name = "CurrencyCode")
         protected String currencyCode;
         @XmlAttribute(name = "DecimalPlaces")
         @XmlSchemaType(name = "nonNegativeInteger")
         protected BigInteger decimalPlaces;

         public BigInteger getLengthOfStayTime() {
            return this.lengthOfStayTime;
         }

         public void setLengthOfStayTime(BigInteger value) {
            this.lengthOfStayTime = value;
         }

         public TimeUnitType getLengthOfStayTimeUnit() {
            return this.lengthOfStayTimeUnit;
         }

         public void setLengthOfStayTimeUnit(TimeUnitType value) {
            this.lengthOfStayTimeUnit = value;
         }

         public String getRatePlanCode() {
            return this.ratePlanCode;
         }

         public void setRatePlanCode(String value) {
            this.ratePlanCode = value;
         }

         public Boolean isTaxInclusive() {
            return this.taxInclusive;
         }

         public void setTaxInclusive(Boolean value) {
            this.taxInclusive = value;
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
   public static class Delta {
      @XmlAttribute(name = "Ceiling")
      @XmlSchemaType(name = "positiveInteger")
      protected BigInteger ceiling;
      @XmlAttribute(name = "MaxSold")
      @XmlSchemaType(name = "positiveInteger")
      protected BigInteger maxSold;
      @XmlAttribute(name = "Amount")
      protected BigDecimal amount;
      @XmlAttribute(name = "CurrencyCode")
      protected String currencyCode;
      @XmlAttribute(name = "DecimalPlaces")
      @XmlSchemaType(name = "nonNegativeInteger")
      protected BigInteger decimalPlaces;

      public BigInteger getCeiling() {
         return this.ceiling;
      }

      public void setCeiling(BigInteger value) {
         this.ceiling = value;
      }

      public BigInteger getMaxSold() {
         return this.maxSold;
      }

      public void setMaxSold(BigInteger value) {
         this.maxSold = value;
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
   public static class HurdleRate {
      @XmlAttribute(name = "Amount")
      protected BigDecimal amount;
      @XmlAttribute(name = "CurrencyCode")
      protected String currencyCode;
      @XmlAttribute(name = "DecimalPlaces")
      @XmlSchemaType(name = "nonNegativeInteger")
      protected BigInteger decimalPlaces;

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
   public static class RestrictionStatus {
      @XmlAttribute(name = "MaxAdvancedBookingOffset")
      protected Duration maxAdvancedBookingOffset;
      @XmlAttribute(name = "MinAdvancedBookingOffset")
      protected Duration minAdvancedBookingOffset;
      @XmlAttribute(name = "Restriction")
      protected List<String> restriction;
      @XmlAttribute(name = "Status")
      protected List<String> status;
      @XmlAttribute(name = "SellThroughOpenIndicator")
      protected Boolean sellThroughOpenIndicator;

      public Duration getMaxAdvancedBookingOffset() {
         return this.maxAdvancedBookingOffset;
      }

      public void setMaxAdvancedBookingOffset(Duration value) {
         this.maxAdvancedBookingOffset = value;
      }

      public Duration getMinAdvancedBookingOffset() {
         return this.minAdvancedBookingOffset;
      }

      public void setMinAdvancedBookingOffset(Duration value) {
         this.minAdvancedBookingOffset = value;
      }

      public List<String> getRestriction() {
         if (this.restriction == null) {
            this.restriction = new ArrayList<>();
         }

         return this.restriction;
      }

      public List<String> getStatus() {
         if (this.status == null) {
            this.status = new ArrayList<>();
         }

         return this.status;
      }

      public Boolean isSellThroughOpenIndicator() {
         return this.sellThroughOpenIndicator;
      }

      public void setSellThroughOpenIndicator(Boolean value) {
         this.sellThroughOpenIndicator = value;
      }
   }
}
