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
@XmlType(name = "SearchQualifierType", propOrder = {"reservationID", "residency", "priceRange", "status", "dining"})
@XmlSeeAlso(SailingSearchQualifierType.class)
public class SearchQualifierType {
   @XmlElement(name = "ReservationID")
   protected UniqueIDType reservationID;
   @XmlElement(name = "Residency")
   protected SearchQualifierType.Residency residency;
   @XmlElement(name = "PriceRange")
   protected SearchQualifierType.PriceRange priceRange;
   @XmlElement(name = "Status")
   protected List<SearchQualifierType.Status> status;
   @XmlElement(name = "Dining")
   protected SearchQualifierType.Dining dining;
   @XmlAttribute(name = "CabinNumber")
   protected String cabinNumber;
   @XmlAttribute(name = "HeldIndicator")
   protected Boolean heldIndicator;
   @XmlAttribute(name = "MaxOccupancy")
   protected Integer maxOccupancy;
   @XmlAttribute(name = "CategoryLocation")
   protected CategoryLocationType categoryLocation;
   @XmlAttribute(name = "SortOrderCode")
   protected String sortOrderCode;
   @XmlAttribute(name = "LoyaltyMembershipID")
   protected String loyaltyMembershipID;
   @XmlAttribute(name = "LoyalLevel")
   protected String loyalLevel;
   @XmlAttribute(name = "LoyalLevelCode")
   protected Integer loyalLevelCode;
   @XmlAttribute(name = "BerthedCategoryCode")
   protected String berthedCategoryCode;
   @XmlAttribute(name = "PricedCategoryCode")
   protected String pricedCategoryCode;
   @XmlAttribute(name = "FareCode")
   protected String fareCode;
   @XmlAttribute(name = "GroupCode")
   protected String groupCode;

   public UniqueIDType getReservationID() {
      return this.reservationID;
   }

   public void setReservationID(UniqueIDType value) {
      this.reservationID = value;
   }

   public SearchQualifierType.Residency getResidency() {
      return this.residency;
   }

   public void setResidency(SearchQualifierType.Residency value) {
      this.residency = value;
   }

   public SearchQualifierType.PriceRange getPriceRange() {
      return this.priceRange;
   }

   public void setPriceRange(SearchQualifierType.PriceRange value) {
      this.priceRange = value;
   }

   public List<SearchQualifierType.Status> getStatus() {
      if (this.status == null) {
         this.status = new ArrayList<>();
      }

      return this.status;
   }

   public SearchQualifierType.Dining getDining() {
      return this.dining;
   }

   public void setDining(SearchQualifierType.Dining value) {
      this.dining = value;
   }

   public String getCabinNumber() {
      return this.cabinNumber;
   }

   public void setCabinNumber(String value) {
      this.cabinNumber = value;
   }

   public Boolean isHeldIndicator() {
      return this.heldIndicator;
   }

   public void setHeldIndicator(Boolean value) {
      this.heldIndicator = value;
   }

   public Integer getMaxOccupancy() {
      return this.maxOccupancy;
   }

   public void setMaxOccupancy(Integer value) {
      this.maxOccupancy = value;
   }

   public CategoryLocationType getCategoryLocation() {
      return this.categoryLocation;
   }

   public void setCategoryLocation(CategoryLocationType value) {
      this.categoryLocation = value;
   }

   public String getSortOrderCode() {
      return this.sortOrderCode;
   }

   public void setSortOrderCode(String value) {
      this.sortOrderCode = value;
   }

   public String getLoyaltyMembershipID() {
      return this.loyaltyMembershipID;
   }

   public void setLoyaltyMembershipID(String value) {
      this.loyaltyMembershipID = value;
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

   public String getBerthedCategoryCode() {
      return this.berthedCategoryCode;
   }

   public void setBerthedCategoryCode(String value) {
      this.berthedCategoryCode = value;
   }

   public String getPricedCategoryCode() {
      return this.pricedCategoryCode;
   }

   public void setPricedCategoryCode(String value) {
      this.pricedCategoryCode = value;
   }

   public String getFareCode() {
      return this.fareCode;
   }

   public void setFareCode(String value) {
      this.fareCode = value;
   }

   public String getGroupCode() {
      return this.groupCode;
   }

   public void setGroupCode(String value) {
      this.groupCode = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class Dining {
      @XmlAttribute(name = "Sitting")
      protected String sitting;

      public String getSitting() {
         return this.sitting;
      }

      public void setSitting(String value) {
         this.sitting = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class PriceRange {
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
   @XmlType(name = "")
   public static class Residency {
      @XmlAttribute(name = "StateProvCode")
      protected String stateProvCode;
      @XmlAttribute(name = "CountryCode")
      protected String countryCode;

      public String getStateProvCode() {
         return this.stateProvCode;
      }

      public void setStateProvCode(String value) {
         this.stateProvCode = value;
      }

      public String getCountryCode() {
         return this.countryCode;
      }

      public void setCountryCode(String value) {
         this.countryCode = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class Status {
      @XmlAttribute(name = "Status")
      protected String status;

      public String getStatus() {
         return this.status;
      }

      public void setStatus(String value) {
         this.status = value;
      }
   }
}
