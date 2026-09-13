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
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PropertyValueMatchType", propOrder = {"searchValueMatch", "amenities", "rateRange"})
public class PropertyValueMatchType extends BasicPropertyInfoType {
   @XmlElement(name = "SearchValueMatch")
   protected List<PropertyValueMatchType.SearchValueMatch> searchValueMatch;
   @XmlElement(name = "Amenities")
   protected PropertyValueMatchType.Amenities amenities;
   @XmlElement(name = "RateRange")
   protected PropertyValueMatchType.RateRange rateRange;
   @XmlAttribute(name = "MoreDataEchoToken")
   protected String moreDataEchoToken;
   @XmlAttribute(name = "SameCountryInd")
   protected Boolean sameCountryInd;
   @XmlAttribute(name = "AvailabilityStatus")
   protected RateIndicatorType availabilityStatus;

   public List<PropertyValueMatchType.SearchValueMatch> getSearchValueMatch() {
      if (this.searchValueMatch == null) {
         this.searchValueMatch = new ArrayList<>();
      }

      return this.searchValueMatch;
   }

   public PropertyValueMatchType.Amenities getAmenities() {
      return this.amenities;
   }

   public void setAmenities(PropertyValueMatchType.Amenities value) {
      this.amenities = value;
   }

   public PropertyValueMatchType.RateRange getRateRange() {
      return this.rateRange;
   }

   public void setRateRange(PropertyValueMatchType.RateRange value) {
      this.rateRange = value;
   }

   public String getMoreDataEchoToken() {
      return this.moreDataEchoToken;
   }

   public void setMoreDataEchoToken(String value) {
      this.moreDataEchoToken = value;
   }

   public Boolean isSameCountryInd() {
      return this.sameCountryInd;
   }

   public void setSameCountryInd(Boolean value) {
      this.sameCountryInd = value;
   }

   public RateIndicatorType getAvailabilityStatus() {
      return this.availabilityStatus;
   }

   public void setAvailabilityStatus(RateIndicatorType value) {
      this.availabilityStatus = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "amenity")
   public static class Amenities {
      @XmlElement(name = "Amenity", required = true)
      protected List<PropertyValueMatchType.Amenities.Amenity> amenity;

      public List<PropertyValueMatchType.Amenities.Amenity> getAmenity() {
         if (this.amenity == null) {
            this.amenity = new ArrayList<>();
         }

         return this.amenity;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class Amenity extends RoomAmenityPrefType {
         @XmlAttribute(name = "PropertyAmenityType")
         protected String propertyAmenityType;

         public String getPropertyAmenityType() {
            return this.propertyAmenityType;
         }

         public void setPropertyAmenityType(String value) {
            this.propertyAmenityType = value;
         }
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class RateRange {
      @XmlAttribute(name = "InfoSource")
      protected String infoSource;
      @XmlAttribute(name = "TaxRate")
      protected BigDecimal taxRate;
      @XmlAttribute(name = "RateInfoNotAvailableInd")
      protected Boolean rateInfoNotAvailableInd;
      @XmlAttribute(name = "MinRate")
      protected BigDecimal minRate;
      @XmlAttribute(name = "MaxRate")
      protected BigDecimal maxRate;
      @XmlAttribute(name = "FixedRate")
      protected BigDecimal fixedRate;
      @XmlAttribute(name = "RateTimeUnit")
      protected TimeUnitType rateTimeUnit;
      @XmlAttribute(name = "CurrencyCode")
      protected String currencyCode;
      @XmlAttribute(name = "DecimalPlaces")
      @XmlSchemaType(name = "nonNegativeInteger")
      protected BigInteger decimalPlaces;

      public String getInfoSource() {
         return this.infoSource;
      }

      public void setInfoSource(String value) {
         this.infoSource = value;
      }

      public BigDecimal getTaxRate() {
         return this.taxRate;
      }

      public void setTaxRate(BigDecimal value) {
         this.taxRate = value;
      }

      public Boolean isRateInfoNotAvailableInd() {
         return this.rateInfoNotAvailableInd;
      }

      public void setRateInfoNotAvailableInd(Boolean value) {
         this.rateInfoNotAvailableInd = value;
      }

      public BigDecimal getMinRate() {
         return this.minRate;
      }

      public void setMinRate(BigDecimal value) {
         this.minRate = value;
      }

      public BigDecimal getMaxRate() {
         return this.maxRate;
      }

      public void setMaxRate(BigDecimal value) {
         this.maxRate = value;
      }

      public BigDecimal getFixedRate() {
         return this.fixedRate;
      }

      public void setFixedRate(BigDecimal value) {
         this.fixedRate = value;
      }

      public TimeUnitType getRateTimeUnit() {
         return this.rateTimeUnit;
      }

      public void setRateTimeUnit(TimeUnitType value) {
         this.rateTimeUnit = value;
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
   public static class SearchValueMatch {
      @XmlValue
      protected String value;
      @XmlAttribute(name = "Match", required = true)
      protected boolean match;
      @XmlAttribute(name = "Relevance")
      protected BigDecimal relevance;

      public String getValue() {
         return this.value;
      }

      public void setValue(String value) {
         this.value = value;
      }

      public boolean isMatch() {
         return this.match;
      }

      public void setMatch(boolean value) {
         this.match = value;
      }

      public BigDecimal getRelevance() {
         return this.relevance;
      }

      public void setRelevance(BigDecimal value) {
         this.relevance = value;
      }
   }
}
