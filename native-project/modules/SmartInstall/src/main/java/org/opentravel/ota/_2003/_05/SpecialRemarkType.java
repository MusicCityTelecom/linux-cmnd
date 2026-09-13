package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SpecialRemarkType", propOrder = {"travelerRefNumber", "flightRefNumber", "text", "airline", "authorizedViewers"})
@XmlSeeAlso(SpecialReqDetailsType.SpecialRemarks.SpecialRemark.class)
public class SpecialRemarkType {
   @XmlElement(name = "TravelerRefNumber")
   protected List<SpecialRemarkType.TravelerRefNumber> travelerRefNumber;
   @XmlElement(name = "FlightRefNumber")
   protected List<SpecialRemarkType.FlightRefNumber> flightRefNumber;
   @XmlElement(name = "Text")
   protected String text;
   @XmlElement(name = "Airline")
   protected List<CompanyNameType> airline;
   @XmlElement(name = "AuthorizedViewers")
   protected SpecialRemarkType.AuthorizedViewers authorizedViewers;
   @XmlAttribute(name = "RemarkType", required = true)
   protected String remarkType;
   @XmlAttribute(name = "ID")
   protected String id;

   public List<SpecialRemarkType.TravelerRefNumber> getTravelerRefNumber() {
      if (this.travelerRefNumber == null) {
         this.travelerRefNumber = new ArrayList<>();
      }

      return this.travelerRefNumber;
   }

   public List<SpecialRemarkType.FlightRefNumber> getFlightRefNumber() {
      if (this.flightRefNumber == null) {
         this.flightRefNumber = new ArrayList<>();
      }

      return this.flightRefNumber;
   }

   public String getText() {
      return this.text;
   }

   public void setText(String value) {
      this.text = value;
   }

   public List<CompanyNameType> getAirline() {
      if (this.airline == null) {
         this.airline = new ArrayList<>();
      }

      return this.airline;
   }

   public SpecialRemarkType.AuthorizedViewers getAuthorizedViewers() {
      return this.authorizedViewers;
   }

   public void setAuthorizedViewers(SpecialRemarkType.AuthorizedViewers value) {
      this.authorizedViewers = value;
   }

   public String getRemarkType() {
      return this.remarkType;
   }

   public void setRemarkType(String value) {
      this.remarkType = value;
   }

   public String getID() {
      return this.id;
   }

   public void setID(String value) {
      this.id = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "authorizedViewer")
   public static class AuthorizedViewers {
      @XmlElement(name = "AuthorizedViewer", required = true)
      protected List<SpecialRemarkType.AuthorizedViewers.AuthorizedViewer> authorizedViewer;

      public List<SpecialRemarkType.AuthorizedViewers.AuthorizedViewer> getAuthorizedViewer() {
         if (this.authorizedViewer == null) {
            this.authorizedViewer = new ArrayList<>();
         }

         return this.authorizedViewer;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class AuthorizedViewer {
         @XmlAttribute(name = "ViewerCode")
         protected String viewerCode;
         @XmlAttribute(name = "ViewerCarrierCode")
         protected String viewerCarrierCode;

         public String getViewerCode() {
            return this.viewerCode;
         }

         public void setViewerCode(String value) {
            this.viewerCode = value;
         }

         public String getViewerCarrierCode() {
            return this.viewerCarrierCode;
         }

         public void setViewerCarrierCode(String value) {
            this.viewerCarrierCode = value;
         }
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class FlightRefNumber {
      @XmlAttribute(name = "RPH")
      protected String rph;

      public String getRPH() {
         return this.rph;
      }

      public void setRPH(String value) {
         this.rph = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class TravelerRefNumber {
      @XmlAttribute(name = "RangePosition")
      @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
      protected String rangePosition;
      @XmlAttribute(name = "RPH")
      protected String rph;
      @XmlAttribute(name = "SurnameRefNumber")
      protected String surnameRefNumber;

      public String getRangePosition() {
         return this.rangePosition;
      }

      public void setRangePosition(String value) {
         this.rangePosition = value;
      }

      public String getRPH() {
         return this.rph;
      }

      public void setRPH(String value) {
         this.rph = value;
      }

      public String getSurnameRefNumber() {
         return this.surnameRefNumber;
      }

      public void setSurnameRefNumber(String value) {
         this.surnameRefNumber = value;
      }
   }
}
