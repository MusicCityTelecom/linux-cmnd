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
@XmlType(name = "TravelerInfoSummaryType", propOrder = {"seatsRequested", "airTravelerAvail", "priceRequestInformation"})
public class TravelerInfoSummaryType {
   @XmlElement(name = "SeatsRequested")
   @XmlSchemaType(name = "nonNegativeInteger")
   protected List<BigInteger> seatsRequested;
   @XmlElement(name = "AirTravelerAvail")
   protected List<TravelerInformationType> airTravelerAvail;
   @XmlElement(name = "PriceRequestInformation")
   protected TravelerInfoSummaryType.PriceRequestInformation priceRequestInformation;

   public List<BigInteger> getSeatsRequested() {
      if (this.seatsRequested == null) {
         this.seatsRequested = new ArrayList<>();
      }

      return this.seatsRequested;
   }

   public List<TravelerInformationType> getAirTravelerAvail() {
      if (this.airTravelerAvail == null) {
         this.airTravelerAvail = new ArrayList<>();
      }

      return this.airTravelerAvail;
   }

   public TravelerInfoSummaryType.PriceRequestInformation getPriceRequestInformation() {
      return this.priceRequestInformation;
   }

   public void setPriceRequestInformation(TravelerInfoSummaryType.PriceRequestInformation value) {
      this.priceRequestInformation = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = {"fareRestrictionPref", "tax", "discountPricing", "segmentOverride", "account", "locationRequirement"})
   public static class PriceRequestInformation extends PriceRequestInformationType {
      @XmlElement(name = "FareRestrictionPref")
      protected List<TravelerInfoSummaryType.PriceRequestInformation.FareRestrictionPref> fareRestrictionPref;
      @XmlElement(name = "Tax")
      protected List<AirTaxType> tax;
      @XmlElement(name = "DiscountPricing")
      protected TravelerInfoSummaryType.PriceRequestInformation.DiscountPricing discountPricing;
      @XmlElement(name = "SegmentOverride")
      protected List<TravelerInfoSummaryType.PriceRequestInformation.SegmentOverride> segmentOverride;
      @XmlElement(name = "Account")
      protected List<TravelerInfoSummaryType.PriceRequestInformation.Account> account;
      @XmlElement(name = "LocationRequirement")
      protected TravelerInfoSummaryType.PriceRequestInformation.LocationRequirement locationRequirement;

      public List<TravelerInfoSummaryType.PriceRequestInformation.FareRestrictionPref> getFareRestrictionPref() {
         if (this.fareRestrictionPref == null) {
            this.fareRestrictionPref = new ArrayList<>();
         }

         return this.fareRestrictionPref;
      }

      public List<AirTaxType> getTax() {
         if (this.tax == null) {
            this.tax = new ArrayList<>();
         }

         return this.tax;
      }

      public TravelerInfoSummaryType.PriceRequestInformation.DiscountPricing getDiscountPricing() {
         return this.discountPricing;
      }

      public void setDiscountPricing(TravelerInfoSummaryType.PriceRequestInformation.DiscountPricing value) {
         this.discountPricing = value;
      }

      public List<TravelerInfoSummaryType.PriceRequestInformation.SegmentOverride> getSegmentOverride() {
         if (this.segmentOverride == null) {
            this.segmentOverride = new ArrayList<>();
         }

         return this.segmentOverride;
      }

      public List<TravelerInfoSummaryType.PriceRequestInformation.Account> getAccount() {
         if (this.account == null) {
            this.account = new ArrayList<>();
         }

         return this.account;
      }

      public TravelerInfoSummaryType.PriceRequestInformation.LocationRequirement getLocationRequirement() {
         return this.locationRequirement;
      }

      public void setLocationRequirement(TravelerInfoSummaryType.PriceRequestInformation.LocationRequirement value) {
         this.locationRequirement = value;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class Account {
         @XmlAttribute(name = "CodeOnlyFaresInd")
         protected Boolean codeOnlyFaresInd;
         @XmlAttribute(name = "Code", required = true)
         protected String code;

         public Boolean isCodeOnlyFaresInd() {
            return this.codeOnlyFaresInd;
         }

         public void setCodeOnlyFaresInd(Boolean value) {
            this.codeOnlyFaresInd = value;
         }

         public String getCode() {
            return this.code;
         }

         public void setCode(String value) {
            this.code = value;
         }
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = "flightReference")
      public static class DiscountPricing {
         @XmlElement(name = "FlightReference")
         protected List<TravelerInfoSummaryType.PriceRequestInformation.DiscountPricing.FlightReference> flightReference;
         @XmlAttribute(name = "Purpose")
         @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
         protected String purpose;
         @XmlAttribute(name = "Type")
         @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
         protected String type;
         @XmlAttribute(name = "Usage")
         @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
         protected String usage;
         @XmlAttribute(name = "Discount")
         protected String discount;
         @XmlAttribute(name = "TicketDesignatorCode")
         protected String ticketDesignatorCode;
         @XmlAttribute(name = "Text")
         protected String text;

         public List<TravelerInfoSummaryType.PriceRequestInformation.DiscountPricing.FlightReference> getFlightReference() {
            if (this.flightReference == null) {
               this.flightReference = new ArrayList<>();
            }

            return this.flightReference;
         }

         public String getPurpose() {
            return this.purpose;
         }

         public void setPurpose(String value) {
            this.purpose = value;
         }

         public String getType() {
            return this.type;
         }

         public void setType(String value) {
            this.type = value;
         }

         public String getUsage() {
            return this.usage;
         }

         public void setUsage(String value) {
            this.usage = value;
         }

         public String getDiscount() {
            return this.discount;
         }

         public void setDiscount(String value) {
            this.discount = value;
         }

         public String getTicketDesignatorCode() {
            return this.ticketDesignatorCode;
         }

         public void setTicketDesignatorCode(String value) {
            this.ticketDesignatorCode = value;
         }

         public String getText() {
            return this.text;
         }

         public void setText(String value) {
            this.text = value;
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "")
         public static class FlightReference {
            @XmlAttribute(name = "FlightRefNumber", required = true)
            protected String flightRefNumber;

            public String getFlightRefNumber() {
               return this.flightRefNumber;
            }

            public void setFlightRefNumber(String value) {
               this.flightRefNumber = value;
            }
         }
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class FareRestrictionPref {
         @XmlAttribute(name = "FareRestriction")
         protected String fareRestriction;
         @XmlAttribute(name = "Date")
         protected String date;
         @XmlAttribute(name = "PreferLevel")
         protected PreferLevelType preferLevel;

         public String getFareRestriction() {
            return this.fareRestriction;
         }

         public void setFareRestriction(String value) {
            this.fareRestriction = value;
         }

         public String getDate() {
            return this.date;
         }

         public void setDate(String value) {
            this.date = value;
         }

         public PreferLevelType getPreferLevel() {
            return this.preferLevel;
         }

         public void setPreferLevel(PreferLevelType value) {
            this.preferLevel = value;
         }
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class LocationRequirement {
         @XmlAttribute(name = "Type")
         @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
         protected String type;
         @XmlAttribute(name = "State")
         protected String state;
         @XmlAttribute(name = "Country")
         protected String country;

         public String getType() {
            return this.type;
         }

         public void setType(String value) {
            this.type = value;
         }

         public String getState() {
            return this.state;
         }

         public void setState(String value) {
            this.state = value;
         }

         public String getCountry() {
            return this.country;
         }

         public void setCountry(String value) {
            this.country = value;
         }
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class SegmentOverride {
         @XmlAttribute(name = "SegmentNumber", required = true)
         protected int segmentNumber;
         @XmlAttribute(name = "SegmentType", required = true)
         @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
         protected String segmentType;

         public int getSegmentNumber() {
            return this.segmentNumber;
         }

         public void setSegmentNumber(int value) {
            this.segmentNumber = value;
         }

         public String getSegmentType() {
            return this.segmentType;
         }

         public void setSegmentType(String value) {
            this.segmentType = value;
         }
      }
   }
}
