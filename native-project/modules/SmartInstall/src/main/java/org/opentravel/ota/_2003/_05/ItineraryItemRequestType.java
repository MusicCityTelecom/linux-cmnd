package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ItineraryItemRequestType", propOrder = {"accommodation", "flight", "rentalCar"})
public class ItineraryItemRequestType {
   @XmlElement(name = "Accommodation")
   protected AccommodationSegmentRequestType accommodation;
   @XmlElement(name = "Flight")
   protected PkgFlightSegmentType flight;
   @XmlElement(name = "RentalCar")
   protected ItineraryItemRequestType.RentalCar rentalCar;
   @XmlAttribute(name = "RPH")
   protected String rph;

   public AccommodationSegmentRequestType getAccommodation() {
      return this.accommodation;
   }

   public void setAccommodation(AccommodationSegmentRequestType value) {
      this.accommodation = value;
   }

   public PkgFlightSegmentType getFlight() {
      return this.flight;
   }

   public void setFlight(PkgFlightSegmentType value) {
      this.flight = value;
   }

   public ItineraryItemRequestType.RentalCar getRentalCar() {
      return this.rentalCar;
   }

   public void setRentalCar(ItineraryItemRequestType.RentalCar value) {
      this.rentalCar = value;
   }

   public String getRPH() {
      return this.rph;
   }

   public void setRPH(String value) {
      this.rph = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class RentalCar extends VehicleRentalCoreType {
      @XmlAttribute(name = "RPH")
      protected String rph;
      @XmlAttribute(name = "Name")
      protected String name;
      @XmlAttribute(name = "Code")
      protected String code;

      public String getRPH() {
         return this.rph;
      }

      public void setRPH(String value) {
         this.rph = value;
      }

      public String getName() {
         return this.name;
      }

      public void setName(String value) {
         this.name = value;
      }

      public String getCode() {
         return this.code;
      }

      public void setCode(String value) {
         this.code = value;
      }
   }
}
