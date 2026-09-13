package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SpecificFlightInfoType", propOrder = {"flightNumber", "airline", "bookingClassPref"})
public class SpecificFlightInfoType {
   @XmlElement(name = "FlightNumber")
   protected String flightNumber;
   @XmlElement(name = "Airline")
   protected CompanyNameType airline;
   @XmlElement(name = "BookingClassPref")
   protected List<SpecificFlightInfoType.BookingClassPref> bookingClassPref;

   public String getFlightNumber() {
      return this.flightNumber;
   }

   public void setFlightNumber(String value) {
      this.flightNumber = value;
   }

   public CompanyNameType getAirline() {
      return this.airline;
   }

   public void setAirline(CompanyNameType value) {
      this.airline = value;
   }

   public List<SpecificFlightInfoType.BookingClassPref> getBookingClassPref() {
      if (this.bookingClassPref == null) {
         this.bookingClassPref = new ArrayList<>();
      }

      return this.bookingClassPref;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class BookingClassPref {
      @XmlAttribute(name = "ResBookDesigCode", required = true)
      protected String resBookDesigCode;
      @XmlAttribute(name = "ResBookDesigCodeType")
      @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
      protected String resBookDesigCodeType;
      @XmlAttribute(name = "PreferLevel")
      protected PreferLevelType preferLevel;

      public String getResBookDesigCode() {
         return this.resBookDesigCode;
      }

      public void setResBookDesigCode(String value) {
         this.resBookDesigCode = value;
      }

      public String getResBookDesigCodeType() {
         return this.resBookDesigCodeType;
      }

      public void setResBookDesigCodeType(String value) {
         this.resBookDesigCodeType = value;
      }

      public PreferLevelType getPreferLevel() {
         return this.preferLevel;
      }

      public void setPreferLevel(PreferLevelType value) {
         this.preferLevel = value;
      }
   }
}
