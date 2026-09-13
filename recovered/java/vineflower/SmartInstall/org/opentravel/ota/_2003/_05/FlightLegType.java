package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FlightLegType", propOrder = {"departureAirport", "arrivalAirport"})
public class FlightLegType {
   @XmlElement(name = "DepartureAirport")
   protected FlightLegType.DepartureAirport departureAirport;
   @XmlElement(name = "ArrivalAirport")
   protected FlightLegType.ArrivalAirport arrivalAirport;
   @XmlAttribute(name = "FlightNumber")
   protected String flightNumber;
   @XmlAttribute(name = "ResBookDesigCode")
   protected String resBookDesigCode;
   @XmlAttribute(name = "Date")
   @XmlSchemaType(name = "dateTime")
   protected XMLGregorianCalendar date;

   public FlightLegType.DepartureAirport getDepartureAirport() {
      return this.departureAirport;
   }

   public void setDepartureAirport(FlightLegType.DepartureAirport value) {
      this.departureAirport = value;
   }

   public FlightLegType.ArrivalAirport getArrivalAirport() {
      return this.arrivalAirport;
   }

   public void setArrivalAirport(FlightLegType.ArrivalAirport value) {
      this.arrivalAirport = value;
   }

   public String getFlightNumber() {
      return this.flightNumber;
   }

   public void setFlightNumber(String value) {
      this.flightNumber = value;
   }

   public String getResBookDesigCode() {
      return this.resBookDesigCode;
   }

   public void setResBookDesigCode(String value) {
      this.resBookDesigCode = value;
   }

   public XMLGregorianCalendar getDate() {
      return this.date;
   }

   public void setDate(XMLGregorianCalendar value) {
      this.date = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class ArrivalAirport {
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

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class DepartureAirport {
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
