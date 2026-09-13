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
@XmlType(
   name = "HotelSearchCriterionType",
   propOrder = {
         "hotelAmenity",
         "roomAmenity",
         "hotelFeature",
         "award",
         "recreation",
         "service",
         "transportation",
         "stayDateRange",
         "rateRange",
         "ratePlanCandidates",
         "profiles",
         "roomStayCandidates",
         "acceptedPayments",
         "media",
         "hotelMeetingFacility",
         "mealPlan",
         "rebatePrograms",
         "tpaExtensions"
   }
)
@XmlSeeAlso(HotelSearchCriteriaType.Criterion.class)
public class HotelSearchCriterionType extends ItemSearchCriterionType {
   @XmlElement(name = "HotelAmenity")
   protected List<HotelSearchCriterionType.HotelAmenity> hotelAmenity;
   @XmlElement(name = "RoomAmenity")
   protected List<RoomAmenityPrefType> roomAmenity;
   @XmlElement(name = "HotelFeature")
   protected List<HotelSearchCriterionType.HotelFeature> hotelFeature;
   @XmlElement(name = "Award")
   protected List<HotelSearchCriterionType.Award> award;
   @XmlElement(name = "Recreation")
   protected List<HotelSearchCriterionType.Recreation> recreation;
   @XmlElement(name = "Service")
   protected List<HotelSearchCriterionType.Service> service;
   @XmlElement(name = "Transportation")
   protected List<HotelSearchCriterionType.Transportation> transportation;
   @XmlElement(name = "StayDateRange")
   protected DateTimeSpanType stayDateRange;
   @XmlElement(name = "RateRange")
   protected List<HotelSearchCriterionType.RateRange> rateRange;
   @XmlElement(name = "RatePlanCandidates")
   protected HotelSearchCriterionType.RatePlanCandidates ratePlanCandidates;
   @XmlElement(name = "Profiles")
   protected ProfilesType profiles;
   @XmlElement(name = "RoomStayCandidates")
   protected HotelSearchCriterionType.RoomStayCandidates roomStayCandidates;
   @XmlElement(name = "AcceptedPayments")
   protected AcceptedPaymentsType acceptedPayments;
   @XmlElement(name = "Media")
   protected List<HotelSearchCriterionType.Media> media;
   @XmlElement(name = "HotelMeetingFacility")
   protected HotelSearchCriterionType.HotelMeetingFacility hotelMeetingFacility;
   @XmlElement(name = "MealPlan")
   protected HotelSearchCriterionType.MealPlan mealPlan;
   @XmlElement(name = "RebatePrograms")
   protected HotelSearchCriterionType.RebatePrograms rebatePrograms;
   @XmlElement(name = "TPA_Extensions")
   protected TPAExtensionsType tpaExtensions;

   public List<HotelSearchCriterionType.HotelAmenity> getHotelAmenity() {
      if (this.hotelAmenity == null) {
         this.hotelAmenity = new ArrayList<>();
      }

      return this.hotelAmenity;
   }

   public List<RoomAmenityPrefType> getRoomAmenity() {
      if (this.roomAmenity == null) {
         this.roomAmenity = new ArrayList<>();
      }

      return this.roomAmenity;
   }

   public List<HotelSearchCriterionType.HotelFeature> getHotelFeature() {
      if (this.hotelFeature == null) {
         this.hotelFeature = new ArrayList<>();
      }

      return this.hotelFeature;
   }

   public List<HotelSearchCriterionType.Award> getAward() {
      if (this.award == null) {
         this.award = new ArrayList<>();
      }

      return this.award;
   }

   public List<HotelSearchCriterionType.Recreation> getRecreation() {
      if (this.recreation == null) {
         this.recreation = new ArrayList<>();
      }

      return this.recreation;
   }

   public List<HotelSearchCriterionType.Service> getService() {
      if (this.service == null) {
         this.service = new ArrayList<>();
      }

      return this.service;
   }

   public List<HotelSearchCriterionType.Transportation> getTransportation() {
      if (this.transportation == null) {
         this.transportation = new ArrayList<>();
      }

      return this.transportation;
   }

   public DateTimeSpanType getStayDateRange() {
      return this.stayDateRange;
   }

   public void setStayDateRange(DateTimeSpanType value) {
      this.stayDateRange = value;
   }

   public List<HotelSearchCriterionType.RateRange> getRateRange() {
      if (this.rateRange == null) {
         this.rateRange = new ArrayList<>();
      }

      return this.rateRange;
   }

   public HotelSearchCriterionType.RatePlanCandidates getRatePlanCandidates() {
      return this.ratePlanCandidates;
   }

   public void setRatePlanCandidates(HotelSearchCriterionType.RatePlanCandidates value) {
      this.ratePlanCandidates = value;
   }

   public ProfilesType getProfiles() {
      return this.profiles;
   }

   public void setProfiles(ProfilesType value) {
      this.profiles = value;
   }

   public HotelSearchCriterionType.RoomStayCandidates getRoomStayCandidates() {
      return this.roomStayCandidates;
   }

   public void setRoomStayCandidates(HotelSearchCriterionType.RoomStayCandidates value) {
      this.roomStayCandidates = value;
   }

   public AcceptedPaymentsType getAcceptedPayments() {
      return this.acceptedPayments;
   }

   public void setAcceptedPayments(AcceptedPaymentsType value) {
      this.acceptedPayments = value;
   }

   public List<HotelSearchCriterionType.Media> getMedia() {
      if (this.media == null) {
         this.media = new ArrayList<>();
      }

      return this.media;
   }

   public HotelSearchCriterionType.HotelMeetingFacility getHotelMeetingFacility() {
      return this.hotelMeetingFacility;
   }

   public void setHotelMeetingFacility(HotelSearchCriterionType.HotelMeetingFacility value) {
      this.hotelMeetingFacility = value;
   }

   public HotelSearchCriterionType.MealPlan getMealPlan() {
      return this.mealPlan;
   }

   public void setMealPlan(HotelSearchCriterionType.MealPlan value) {
      this.mealPlan = value;
   }

   public HotelSearchCriterionType.RebatePrograms getRebatePrograms() {
      return this.rebatePrograms;
   }

   public void setRebatePrograms(HotelSearchCriterionType.RebatePrograms value) {
      this.rebatePrograms = value;
   }

   public TPAExtensionsType getTPAExtensions() {
      return this.tpaExtensions;
   }

   public void setTPAExtensions(TPAExtensionsType value) {
      this.tpaExtensions = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class Award {
      @XmlAttribute(name = "Provider")
      protected String provider;
      @XmlAttribute(name = "Rating")
      protected String rating;

      public String getProvider() {
         return this.provider;
      }

      public void setProvider(String value) {
         this.provider = value;
      }

      public String getRating() {
         return this.rating;
      }

      public void setRating(String value) {
         this.rating = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class HotelAmenity {
      @XmlAttribute(name = "Code")
      protected String code;
      @XmlAttribute(name = "ComplimentaryInd")
      protected Boolean complimentaryInd;

      public String getCode() {
         return this.code;
      }

      public void setCode(String value) {
         this.code = value;
      }

      public Boolean isComplimentaryInd() {
         return this.complimentaryInd;
      }

      public void setComplimentaryInd(Boolean value) {
         this.complimentaryInd = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class HotelFeature {
      @XmlAttribute(name = "SecurityFeatureCode")
      protected String securityFeatureCode;
      @XmlAttribute(name = "AccessibilityCode")
      protected String accessibilityCode;

      public String getSecurityFeatureCode() {
         return this.securityFeatureCode;
      }

      public void setSecurityFeatureCode(String value) {
         this.securityFeatureCode = value;
      }

      public String getAccessibilityCode() {
         return this.accessibilityCode;
      }

      public void setAccessibilityCode(String value) {
         this.accessibilityCode = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class HotelMeetingFacility {
      @XmlAttribute(name = "MeetingRoomCount")
      @XmlSchemaType(name = "nonNegativeInteger")
      protected BigInteger meetingRoomCount;
      @XmlAttribute(name = "LargestSeatingCapacity")
      @XmlSchemaType(name = "nonNegativeInteger")
      protected BigInteger largestSeatingCapacity;
      @XmlAttribute(name = "LargestRoomSpace")
      @XmlSchemaType(name = "nonNegativeInteger")
      protected BigInteger largestRoomSpace;
      @XmlAttribute(name = "UnitOfMeasureCode")
      protected String unitOfMeasureCode;
      @XmlAttribute(name = "MeetingRoomCode")
      protected String meetingRoomCode;

      public BigInteger getMeetingRoomCount() {
         return this.meetingRoomCount;
      }

      public void setMeetingRoomCount(BigInteger value) {
         this.meetingRoomCount = value;
      }

      public BigInteger getLargestSeatingCapacity() {
         return this.largestSeatingCapacity;
      }

      public void setLargestSeatingCapacity(BigInteger value) {
         this.largestSeatingCapacity = value;
      }

      public BigInteger getLargestRoomSpace() {
         return this.largestRoomSpace;
      }

      public void setLargestRoomSpace(BigInteger value) {
         this.largestRoomSpace = value;
      }

      public String getUnitOfMeasureCode() {
         return this.unitOfMeasureCode;
      }

      public void setUnitOfMeasureCode(String value) {
         this.unitOfMeasureCode = value;
      }

      public String getMeetingRoomCode() {
         return this.meetingRoomCode;
      }

      public void setMeetingRoomCode(String value) {
         this.meetingRoomCode = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class MealPlan {
      @XmlAttribute(name = "Code")
      protected List<String> code;

      public List<String> getCode() {
         if (this.code == null) {
            this.code = new ArrayList<>();
         }

         return this.code;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class Media {
      @XmlAttribute(name = "ContentCode")
      protected String contentCode;

      public String getContentCode() {
         return this.contentCode;
      }

      public void setContentCode(String value) {
         this.contentCode = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class RatePlanCandidates extends RatePlanCandidatesType {
      @XmlAttribute(name = "TaxesIncludedInd")
      protected Boolean taxesIncludedInd;

      public Boolean isTaxesIncludedInd() {
         return this.taxesIncludedInd;
      }

      public void setTaxesIncludedInd(Boolean value) {
         this.taxesIncludedInd = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class RateRange {
      @XmlAttribute(name = "RoomStayCandidateRPH")
      protected String roomStayCandidateRPH;
      @XmlAttribute(name = "RateMode")
      protected String rateMode;
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

      public String getRoomStayCandidateRPH() {
         return this.roomStayCandidateRPH;
      }

      public void setRoomStayCandidateRPH(String value) {
         this.roomStayCandidateRPH = value;
      }

      public String getRateMode() {
         return this.rateMode;
      }

      public void setRateMode(String value) {
         this.rateMode = value;
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
   @XmlType(name = "", propOrder = "rebateProgram")
   public static class RebatePrograms {
      @XmlElement(name = "RebateProgram", required = true)
      protected List<RebateType> rebateProgram;

      public List<RebateType> getRebateProgram() {
         if (this.rebateProgram == null) {
            this.rebateProgram = new ArrayList<>();
         }

         return this.rebateProgram;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class Recreation {
      @XmlAttribute(name = "Code")
      protected String code;

      public String getCode() {
         return this.code;
      }

      public void setCode(String value) {
         this.code = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "roomStayCandidate")
   public static class RoomStayCandidates {
      @XmlElement(name = "RoomStayCandidate", required = true)
      protected List<RoomStayCandidateType> roomStayCandidate;

      public List<RoomStayCandidateType> getRoomStayCandidate() {
         if (this.roomStayCandidate == null) {
            this.roomStayCandidate = new ArrayList<>();
         }

         return this.roomStayCandidate;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class Service {
      @XmlAttribute(name = "BusinessServiceCode")
      protected String businessServiceCode;
      @XmlAttribute(name = "ServiceInventoryCode")
      protected String serviceInventoryCode;
      @XmlAttribute(name = "Quantity")
      protected Integer quantity;

      public String getBusinessServiceCode() {
         return this.businessServiceCode;
      }

      public void setBusinessServiceCode(String value) {
         this.businessServiceCode = value;
      }

      public String getServiceInventoryCode() {
         return this.serviceInventoryCode;
      }

      public void setServiceInventoryCode(String value) {
         this.serviceInventoryCode = value;
      }

      public Integer getQuantity() {
         return this.quantity;
      }

      public void setQuantity(Integer value) {
         this.quantity = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class Transportation {
      @XmlAttribute(name = "Code")
      protected String code;

      public String getCode() {
         return this.code;
      }

      public void setCode(String value) {
         this.code = value;
      }
   }
}
