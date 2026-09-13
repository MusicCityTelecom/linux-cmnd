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
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "RatePlanType",
   propOrder = {
         "guarantee", "cancelPenalties", "ratePlanDescription", "ratePlanInclusions", "commission", "mealsIncluded", "restrictionStatus", "additionalDetails"
   }
)
public class RatePlanType {
   @XmlElement(name = "Guarantee")
   protected List<GuaranteeType> guarantee;
   @XmlElement(name = "CancelPenalties")
   protected CancelPenaltiesType cancelPenalties;
   @XmlElement(name = "RatePlanDescription")
   protected ParagraphType ratePlanDescription;
   @XmlElement(name = "RatePlanInclusions")
   protected RatePlanType.RatePlanInclusions ratePlanInclusions;
   @XmlElement(name = "Commission")
   protected CommissionType commission;
   @XmlElement(name = "MealsIncluded")
   protected RatePlanType.MealsIncluded mealsIncluded;
   @XmlElement(name = "RestrictionStatus")
   protected RatePlanType.RestrictionStatus restrictionStatus;
   @XmlElement(name = "AdditionalDetails")
   protected AdditionalDetailsType additionalDetails;
   @XmlAttribute(name = "BookingCode")
   protected String bookingCode;
   @XmlAttribute(name = "RatePlanCode")
   protected String ratePlanCode;
   @XmlAttribute(name = "RateIndicator")
   protected RateIndicatorType rateIndicator;
   @XmlAttribute(name = "RatePlanType")
   protected String ratePlanType;
   @XmlAttribute(name = "RatePlanID")
   protected String ratePlanID;
   @XmlAttribute(name = "RatePlanName")
   protected String ratePlanName;
   @XmlAttribute(name = "MarketCode")
   protected String marketCode;
   @XmlAttribute(name = "AvailabilityStatus")
   protected RateIndicatorType availabilityStatus;
   @XmlAttribute(name = "ID_RequiredInd")
   protected Boolean idRequiredInd;
   @XmlAttribute(name = "PriceViewableInd")
   protected Boolean priceViewableInd;
   @XmlAttribute(name = "QualificationType")
   protected String qualificationType;
   @XmlAttribute(name = "AvailableQuantity")
   protected BigInteger availableQuantity;
   @XmlAttribute(name = "PrepaidIndicator")
   protected Boolean prepaidIndicator;
   @XmlAttribute(name = "EffectiveDate")
   @XmlSchemaType(name = "date")
   protected XMLGregorianCalendar effectiveDate;
   @XmlAttribute(name = "ExpireDate")
   @XmlSchemaType(name = "date")
   protected XMLGregorianCalendar expireDate;
   @XmlAttribute(name = "ExpireDateExclusiveIndicator")
   protected Boolean expireDateExclusiveIndicator;

   public List<GuaranteeType> getGuarantee() {
      if (this.guarantee == null) {
         this.guarantee = new ArrayList<>();
      }

      return this.guarantee;
   }

   public CancelPenaltiesType getCancelPenalties() {
      return this.cancelPenalties;
   }

   public void setCancelPenalties(CancelPenaltiesType value) {
      this.cancelPenalties = value;
   }

   public ParagraphType getRatePlanDescription() {
      return this.ratePlanDescription;
   }

   public void setRatePlanDescription(ParagraphType value) {
      this.ratePlanDescription = value;
   }

   public RatePlanType.RatePlanInclusions getRatePlanInclusions() {
      return this.ratePlanInclusions;
   }

   public void setRatePlanInclusions(RatePlanType.RatePlanInclusions value) {
      this.ratePlanInclusions = value;
   }

   public CommissionType getCommission() {
      return this.commission;
   }

   public void setCommission(CommissionType value) {
      this.commission = value;
   }

   public RatePlanType.MealsIncluded getMealsIncluded() {
      return this.mealsIncluded;
   }

   public void setMealsIncluded(RatePlanType.MealsIncluded value) {
      this.mealsIncluded = value;
   }

   public RatePlanType.RestrictionStatus getRestrictionStatus() {
      return this.restrictionStatus;
   }

   public void setRestrictionStatus(RatePlanType.RestrictionStatus value) {
      this.restrictionStatus = value;
   }

   public AdditionalDetailsType getAdditionalDetails() {
      return this.additionalDetails;
   }

   public void setAdditionalDetails(AdditionalDetailsType value) {
      this.additionalDetails = value;
   }

   public String getBookingCode() {
      return this.bookingCode;
   }

   public void setBookingCode(String value) {
      this.bookingCode = value;
   }

   public String getRatePlanCode() {
      return this.ratePlanCode;
   }

   public void setRatePlanCode(String value) {
      this.ratePlanCode = value;
   }

   public RateIndicatorType getRateIndicator() {
      return this.rateIndicator;
   }

   public void setRateIndicator(RateIndicatorType value) {
      this.rateIndicator = value;
   }

   public String getRatePlanType() {
      return this.ratePlanType;
   }

   public void setRatePlanType(String value) {
      this.ratePlanType = value;
   }

   public String getRatePlanID() {
      return this.ratePlanID;
   }

   public void setRatePlanID(String value) {
      this.ratePlanID = value;
   }

   public String getRatePlanName() {
      return this.ratePlanName;
   }

   public void setRatePlanName(String value) {
      this.ratePlanName = value;
   }

   public String getMarketCode() {
      return this.marketCode;
   }

   public void setMarketCode(String value) {
      this.marketCode = value;
   }

   public RateIndicatorType getAvailabilityStatus() {
      return this.availabilityStatus;
   }

   public void setAvailabilityStatus(RateIndicatorType value) {
      this.availabilityStatus = value;
   }

   public Boolean isIDRequiredInd() {
      return this.idRequiredInd;
   }

   public void setIDRequiredInd(Boolean value) {
      this.idRequiredInd = value;
   }

   public Boolean isPriceViewableInd() {
      return this.priceViewableInd;
   }

   public void setPriceViewableInd(Boolean value) {
      this.priceViewableInd = value;
   }

   public String getQualificationType() {
      return this.qualificationType;
   }

   public void setQualificationType(String value) {
      this.qualificationType = value;
   }

   public BigInteger getAvailableQuantity() {
      return this.availableQuantity;
   }

   public void setAvailableQuantity(BigInteger value) {
      this.availableQuantity = value;
   }

   public Boolean isPrepaidIndicator() {
      return this.prepaidIndicator;
   }

   public void setPrepaidIndicator(Boolean value) {
      this.prepaidIndicator = value;
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

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class MealsIncluded {
      @XmlAttribute(name = "Breakfast")
      protected Boolean breakfast;
      @XmlAttribute(name = "Lunch")
      protected Boolean lunch;
      @XmlAttribute(name = "Dinner")
      protected Boolean dinner;
      @XmlAttribute(name = "MealPlanIndicator")
      protected Boolean mealPlanIndicator;
      @XmlAttribute(name = "MealPlanCodes")
      protected List<String> mealPlanCodes;

      public Boolean isBreakfast() {
         return this.breakfast;
      }

      public void setBreakfast(Boolean value) {
         this.breakfast = value;
      }

      public Boolean isLunch() {
         return this.lunch;
      }

      public void setLunch(Boolean value) {
         this.lunch = value;
      }

      public Boolean isDinner() {
         return this.dinner;
      }

      public void setDinner(Boolean value) {
         this.dinner = value;
      }

      public Boolean isMealPlanIndicator() {
         return this.mealPlanIndicator;
      }

      public void setMealPlanIndicator(Boolean value) {
         this.mealPlanIndicator = value;
      }

      public List<String> getMealPlanCodes() {
         if (this.mealPlanCodes == null) {
            this.mealPlanCodes = new ArrayList<>();
         }

         return this.mealPlanCodes;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "ratePlanInclusionDesciption")
   public static class RatePlanInclusions {
      @XmlElement(name = "RatePlanInclusionDesciption")
      protected ParagraphType ratePlanInclusionDesciption;
      @XmlAttribute(name = "TaxInclusive")
      protected Boolean taxInclusive;
      @XmlAttribute(name = "ServiceFeeInclusive")
      protected Boolean serviceFeeInclusive;

      public ParagraphType getRatePlanInclusionDesciption() {
         return this.ratePlanInclusionDesciption;
      }

      public void setRatePlanInclusionDesciption(ParagraphType value) {
         this.ratePlanInclusionDesciption = value;
      }

      public Boolean isTaxInclusive() {
         return this.taxInclusive;
      }

      public void setTaxInclusive(Boolean value) {
         this.taxInclusive = value;
      }

      public Boolean isServiceFeeInclusive() {
         return this.serviceFeeInclusive;
      }

      public void setServiceFeeInclusive(Boolean value) {
         this.serviceFeeInclusive = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class RestrictionStatus {
      @XmlAttribute(name = "Restriction")
      protected List<String> restriction;
      @XmlAttribute(name = "Status")
      protected List<String> status;
      @XmlAttribute(name = "SellThroughOpenIndicator")
      protected Boolean sellThroughOpenIndicator;

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
