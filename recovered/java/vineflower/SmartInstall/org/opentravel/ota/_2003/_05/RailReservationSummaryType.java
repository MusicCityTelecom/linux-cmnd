package org.opentravel.ota._2003._05;

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
@XmlType(name = "RailReservationSummaryType", propOrder = {"odInfo", "passengerInfo", "tpaExtensions"})
public class RailReservationSummaryType {
   @XmlElement(name = "ODInfo", required = true)
   protected List<RailReservationSummaryType.ODInfo> odInfo;
   @XmlElement(name = "PassengerInfo", required = true)
   protected List<RailPassengerCategoryDetailType> passengerInfo;
   @XmlElement(name = "TPA_Extensions")
   protected TPAExtensionsType tpaExtensions;
   @XmlAttribute(name = "BookingReferenceID")
   protected String bookingReferenceID;
   @XmlAttribute(name = "DateBooked")
   @XmlSchemaType(name = "dateTime")
   protected XMLGregorianCalendar dateBooked;
   @XmlAttribute(name = "Status")
   protected TransactionStatusType status;

   public List<RailReservationSummaryType.ODInfo> getODInfo() {
      if (this.odInfo == null) {
         this.odInfo = new ArrayList<>();
      }

      return this.odInfo;
   }

   public List<RailPassengerCategoryDetailType> getPassengerInfo() {
      if (this.passengerInfo == null) {
         this.passengerInfo = new ArrayList<>();
      }

      return this.passengerInfo;
   }

   public TPAExtensionsType getTPAExtensions() {
      return this.tpaExtensions;
   }

   public void setTPAExtensions(TPAExtensionsType value) {
      this.tpaExtensions = value;
   }

   public String getBookingReferenceID() {
      return this.bookingReferenceID;
   }

   public void setBookingReferenceID(String value) {
      this.bookingReferenceID = value;
   }

   public XMLGregorianCalendar getDateBooked() {
      return this.dateBooked;
   }

   public void setDateBooked(XMLGregorianCalendar value) {
      this.dateBooked = value;
   }

   public TransactionStatusType getStatus() {
      return this.status;
   }

   public void setStatus(TransactionStatusType value) {
      this.status = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = {"originLocation", "destinationLocation", "trainSegment"})
   public static class ODInfo {
      @XmlElement(name = "OriginLocation", required = true)
      protected LocationType originLocation;
      @XmlElement(name = "DestinationLocation", required = true)
      protected LocationType destinationLocation;
      @XmlElement(name = "TrainSegment", required = true)
      protected List<RailReservationSummaryType.ODInfo.TrainSegment> trainSegment;

      public LocationType getOriginLocation() {
         return this.originLocation;
      }

      public void setOriginLocation(LocationType value) {
         this.originLocation = value;
      }

      public LocationType getDestinationLocation() {
         return this.destinationLocation;
      }

      public void setDestinationLocation(LocationType value) {
         this.destinationLocation = value;
      }

      public List<RailReservationSummaryType.ODInfo.TrainSegment> getTrainSegment() {
         if (this.trainSegment == null) {
            this.trainSegment = new ArrayList<>();
         }

         return this.trainSegment;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = {"departureStation", "arrivalStation", "departureDateTime", "arrivalDateTime", "trainIdentification"})
      public static class TrainSegment {
         @XmlElement(name = "DepartureStation", required = true)
         protected LocationType departureStation;
         @XmlElement(name = "ArrivalStation", required = true)
         protected LocationType arrivalStation;
         @XmlElement(name = "DepartureDateTime", required = true)
         @XmlSchemaType(name = "dateTime")
         protected XMLGregorianCalendar departureDateTime;
         @XmlElement(name = "ArrivalDateTime", required = true)
         @XmlSchemaType(name = "dateTime")
         protected XMLGregorianCalendar arrivalDateTime;
         @XmlElement(name = "TrainIdentification", required = true)
         protected TrainIdentificationType trainIdentification;

         public LocationType getDepartureStation() {
            return this.departureStation;
         }

         public void setDepartureStation(LocationType value) {
            this.departureStation = value;
         }

         public LocationType getArrivalStation() {
            return this.arrivalStation;
         }

         public void setArrivalStation(LocationType value) {
            this.arrivalStation = value;
         }

         public XMLGregorianCalendar getDepartureDateTime() {
            return this.departureDateTime;
         }

         public void setDepartureDateTime(XMLGregorianCalendar value) {
            this.departureDateTime = value;
         }

         public XMLGregorianCalendar getArrivalDateTime() {
            return this.arrivalDateTime;
         }

         public void setArrivalDateTime(XMLGregorianCalendar value) {
            this.arrivalDateTime = value;
         }

         public TrainIdentificationType getTrainIdentification() {
            return this.trainIdentification;
         }

         public void setTrainIdentification(TrainIdentificationType value) {
            this.trainIdentification = value;
         }
      }
   }
}
