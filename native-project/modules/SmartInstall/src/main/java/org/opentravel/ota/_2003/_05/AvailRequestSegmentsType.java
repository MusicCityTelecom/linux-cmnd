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
@XmlType(name = "AvailRequestSegmentsType", propOrder = "availRequestSegment")
@XmlSeeAlso(OTAHotelAvailRQ.AvailRequestSegments.class)
public class AvailRequestSegmentsType {
   @XmlElement(name = "AvailRequestSegment", required = true)
   protected List<AvailRequestSegmentsType.AvailRequestSegment> availRequestSegment;

   public List<AvailRequestSegmentsType.AvailRequestSegment> getAvailRequestSegment() {
      if (this.availRequestSegment == null) {
         this.availRequestSegment = new ArrayList<>();
      }

      return this.availRequestSegment;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(
      name = "",
      propOrder = {"stayDateRange", "rateRange", "ratePlanCandidates", "profiles", "roomStayCandidates", "hotelSearchCriteria", "tpaExtensions"}
   )
   public static class AvailRequestSegment {
      @XmlElement(name = "StayDateRange")
      protected DateTimeSpanType stayDateRange;
      @XmlElement(name = "RateRange")
      protected List<AvailRequestSegmentsType.AvailRequestSegment.RateRange> rateRange;
      @XmlElement(name = "RatePlanCandidates")
      protected RatePlanCandidatesType ratePlanCandidates;
      @XmlElement(name = "Profiles")
      protected ProfilesType profiles;
      @XmlElement(name = "RoomStayCandidates")
      protected AvailRequestSegmentsType.AvailRequestSegment.RoomStayCandidates roomStayCandidates;
      @XmlElement(name = "HotelSearchCriteria")
      protected AvailRequestSegmentsType.AvailRequestSegment.HotelSearchCriteria hotelSearchCriteria;
      @XmlElement(name = "TPA_Extensions")
      protected TPAExtensionsType tpaExtensions;
      @XmlAttribute(name = "AvailReqType")
      protected String availReqType;
      @XmlAttribute(name = "MoreDataEchoToken")
      protected String moreDataEchoToken;
      @XmlAttribute(name = "InfoSource")
      protected String infoSource;
      @XmlAttribute(name = "ResponseType")
      protected String responseType;

      public DateTimeSpanType getStayDateRange() {
         return this.stayDateRange;
      }

      public void setStayDateRange(DateTimeSpanType value) {
         this.stayDateRange = value;
      }

      public List<AvailRequestSegmentsType.AvailRequestSegment.RateRange> getRateRange() {
         if (this.rateRange == null) {
            this.rateRange = new ArrayList<>();
         }

         return this.rateRange;
      }

      public RatePlanCandidatesType getRatePlanCandidates() {
         return this.ratePlanCandidates;
      }

      public void setRatePlanCandidates(RatePlanCandidatesType value) {
         this.ratePlanCandidates = value;
      }

      public ProfilesType getProfiles() {
         return this.profiles;
      }

      public void setProfiles(ProfilesType value) {
         this.profiles = value;
      }

      public AvailRequestSegmentsType.AvailRequestSegment.RoomStayCandidates getRoomStayCandidates() {
         return this.roomStayCandidates;
      }

      public void setRoomStayCandidates(AvailRequestSegmentsType.AvailRequestSegment.RoomStayCandidates value) {
         this.roomStayCandidates = value;
      }

      public AvailRequestSegmentsType.AvailRequestSegment.HotelSearchCriteria getHotelSearchCriteria() {
         return this.hotelSearchCriteria;
      }

      public void setHotelSearchCriteria(AvailRequestSegmentsType.AvailRequestSegment.HotelSearchCriteria value) {
         this.hotelSearchCriteria = value;
      }

      public TPAExtensionsType getTPAExtensions() {
         return this.tpaExtensions;
      }

      public void setTPAExtensions(TPAExtensionsType value) {
         this.tpaExtensions = value;
      }

      public String getAvailReqType() {
         return this.availReqType;
      }

      public void setAvailReqType(String value) {
         this.availReqType = value;
      }

      public String getMoreDataEchoToken() {
         return this.moreDataEchoToken;
      }

      public void setMoreDataEchoToken(String value) {
         this.moreDataEchoToken = value;
      }

      public String getInfoSource() {
         return this.infoSource;
      }

      public void setInfoSource(String value) {
         this.infoSource = value;
      }

      public String getResponseType() {
         return this.responseType;
      }

      public void setResponseType(String value) {
         this.responseType = value;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class HotelSearchCriteria extends HotelSearchCriteriaType {
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class RateRange {
         @XmlAttribute(name = "RoomStayCandidateRPH")
         protected String roomStayCandidateRPH;
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
      @XmlType(name = "", propOrder = "roomStayCandidate")
      public static class RoomStayCandidates {
         @XmlElement(name = "RoomStayCandidate", required = true)
         protected List<AvailRequestSegmentsType.AvailRequestSegment.RoomStayCandidates.RoomStayCandidate> roomStayCandidate;

         public List<AvailRequestSegmentsType.AvailRequestSegment.RoomStayCandidates.RoomStayCandidate> getRoomStayCandidate() {
            if (this.roomStayCandidate == null) {
               this.roomStayCandidate = new ArrayList<>();
            }

            return this.roomStayCandidate;
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "")
         public static class RoomStayCandidate extends RoomStayCandidateType {
            @XmlAttribute(name = "IsAlternate")
            protected Boolean isAlternate;

            public Boolean isIsAlternate() {
               return this.isAlternate;
            }

            public void setIsAlternate(Boolean value) {
               this.isAlternate = value;
            }
         }
      }
   }
}
