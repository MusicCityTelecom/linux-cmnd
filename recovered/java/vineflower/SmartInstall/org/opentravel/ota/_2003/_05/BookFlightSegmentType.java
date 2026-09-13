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

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BookFlightSegmentType", propOrder = {"marriageGrp", "bookingClassAvails", "comment", "stopLocation"})
@XmlSeeAlso({OriginDestinationOptionType.FlightSegment.class, PTCFareBreakdownType.PricingUnit.FareComponent.FlightLeg.class})
public class BookFlightSegmentType extends FlightSegmentType {
   @XmlElement(name = "MarriageGrp")
   protected String marriageGrp;
   @XmlElement(name = "BookingClassAvails")
   protected List<BookFlightSegmentType.BookingClassAvails> bookingClassAvails;
   @XmlElement(name = "Comment")
   protected List<FreeTextType> comment;
   @XmlElement(name = "StopLocation")
   protected List<BookFlightSegmentType.StopLocation> stopLocation;
   @XmlAttribute(name = "ResBookDesigCode")
   protected String resBookDesigCode;
   @XmlAttribute(name = "NumberInParty")
   @XmlSchemaType(name = "positiveInteger")
   protected BigInteger numberInParty;
   @XmlAttribute(name = "Status")
   protected String status;
   @XmlAttribute(name = "E_TicketEligibility")
   protected String eTicketEligibility;
   @XmlAttribute(name = "MealCode")
   protected String mealCode;
   @XmlAttribute(name = "DepartureDay")
   protected DayOfWeekType departureDay;
   @XmlAttribute(name = "StopoverInd")
   protected Boolean stopoverInd;
   @XmlAttribute(name = "LineNumber")
   protected Integer lineNumber;
   @XmlAttribute(name = "ConnectionType")
   protected String connectionType;
   @XmlAttribute(name = "ParticipationLevelCode")
   protected String participationLevelCode;
   @XmlAttribute(name = "Distance")
   @XmlSchemaType(name = "nonNegativeInteger")
   protected BigInteger distance;
   @XmlAttribute(name = "DateChangeNbr")
   protected String dateChangeNbr;
   @XmlAttribute(name = "ValidConnectionInd")
   protected Boolean validConnectionInd;

   public String getMarriageGrp() {
      return this.marriageGrp;
   }

   public void setMarriageGrp(String value) {
      this.marriageGrp = value;
   }

   public List<BookFlightSegmentType.BookingClassAvails> getBookingClassAvails() {
      if (this.bookingClassAvails == null) {
         this.bookingClassAvails = new ArrayList<>();
      }

      return this.bookingClassAvails;
   }

   public List<FreeTextType> getComment() {
      if (this.comment == null) {
         this.comment = new ArrayList<>();
      }

      return this.comment;
   }

   public List<BookFlightSegmentType.StopLocation> getStopLocation() {
      if (this.stopLocation == null) {
         this.stopLocation = new ArrayList<>();
      }

      return this.stopLocation;
   }

   public String getResBookDesigCode() {
      return this.resBookDesigCode;
   }

   public void setResBookDesigCode(String value) {
      this.resBookDesigCode = value;
   }

   public BigInteger getNumberInParty() {
      return this.numberInParty;
   }

   public void setNumberInParty(BigInteger value) {
      this.numberInParty = value;
   }

   public String getStatus() {
      return this.status;
   }

   public void setStatus(String value) {
      this.status = value;
   }

   public String getETicketEligibility() {
      return this.eTicketEligibility;
   }

   public void setETicketEligibility(String value) {
      this.eTicketEligibility = value;
   }

   public String getMealCode() {
      return this.mealCode;
   }

   public void setMealCode(String value) {
      this.mealCode = value;
   }

   public DayOfWeekType getDepartureDay() {
      return this.departureDay;
   }

   public void setDepartureDay(DayOfWeekType value) {
      this.departureDay = value;
   }

   public Boolean isStopoverInd() {
      return this.stopoverInd;
   }

   public void setStopoverInd(Boolean value) {
      this.stopoverInd = value;
   }

   public Integer getLineNumber() {
      return this.lineNumber;
   }

   public void setLineNumber(Integer value) {
      this.lineNumber = value;
   }

   public String getConnectionType() {
      return this.connectionType;
   }

   public void setConnectionType(String value) {
      this.connectionType = value;
   }

   public String getParticipationLevelCode() {
      return this.participationLevelCode;
   }

   public void setParticipationLevelCode(String value) {
      this.participationLevelCode = value;
   }

   public BigInteger getDistance() {
      return this.distance;
   }

   public void setDistance(BigInteger value) {
      this.distance = value;
   }

   public String getDateChangeNbr() {
      return this.dateChangeNbr;
   }

   public void setDateChangeNbr(String value) {
      this.dateChangeNbr = value;
   }

   public Boolean isValidConnectionInd() {
      return this.validConnectionInd;
   }

   public void setValidConnectionInd(Boolean value) {
      this.validConnectionInd = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "bookingClassAvail")
   public static class BookingClassAvails {
      @XmlElement(name = "BookingClassAvail", required = true)
      protected List<BookFlightSegmentType.BookingClassAvails.BookingClassAvail> bookingClassAvail;
      @XmlAttribute(name = "CabinType")
      protected CabinType cabinType;

      public List<BookFlightSegmentType.BookingClassAvails.BookingClassAvail> getBookingClassAvail() {
         if (this.bookingClassAvail == null) {
            this.bookingClassAvail = new ArrayList<>();
         }

         return this.bookingClassAvail;
      }

      public CabinType getCabinType() {
         return this.cabinType;
      }

      public void setCabinType(CabinType value) {
         this.cabinType = value;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class BookingClassAvail {
         @XmlAttribute(name = "RPH")
         protected String rph;
         @XmlAttribute(name = "ResBookDesigCode")
         protected String resBookDesigCode;
         @XmlAttribute(name = "ResBookDesigQuantity")
         protected String resBookDesigQuantity;
         @XmlAttribute(name = "ResBookDesigStatusCode")
         protected String resBookDesigStatusCode;

         public String getRPH() {
            return this.rph;
         }

         public void setRPH(String value) {
            this.rph = value;
         }

         public String getResBookDesigCode() {
            return this.resBookDesigCode;
         }

         public void setResBookDesigCode(String value) {
            this.resBookDesigCode = value;
         }

         public String getResBookDesigQuantity() {
            return this.resBookDesigQuantity;
         }

         public void setResBookDesigQuantity(String value) {
            this.resBookDesigQuantity = value;
         }

         public String getResBookDesigStatusCode() {
            return this.resBookDesigStatusCode;
         }

         public void setResBookDesigStatusCode(String value) {
            this.resBookDesigStatusCode = value;
         }
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class StopLocation {
      @XmlAttribute(name = "LocationCode")
      protected String locationCode;
      @XmlAttribute(name = "CodeContext")
      protected String codeContext;

      public String getLocationCode() {
         return this.locationCode;
      }

      public void setLocationCode(String value) {
         this.locationCode = value;
      }

      public String getCodeContext() {
         return this.codeContext;
      }

      public void setCodeContext(String value) {
         this.codeContext = value;
      }
   }
}
