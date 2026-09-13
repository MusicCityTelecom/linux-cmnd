package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FlightSegmentType", propOrder = "marketingAirline")
@XmlSeeAlso(BookFlightSegmentType.class)
public class FlightSegmentType extends FlightSegmentBaseType {
   @XmlElement(name = "MarketingAirline")
   protected FlightSegmentType.MarketingAirline marketingAirline;
   @XmlAttribute(name = "FlightNumber")
   protected String flightNumber;
   @XmlAttribute(name = "TourOperatorFlightID")
   protected String tourOperatorFlightID;
   @XmlAttribute(name = "GovernmentApprovalInd")
   protected Boolean governmentApprovalInd;
   @XmlAttribute(name = "GovernmentApprovalText")
   protected String governmentApprovalText;

   public FlightSegmentType.MarketingAirline getMarketingAirline() {
      return this.marketingAirline;
   }

   public void setMarketingAirline(FlightSegmentType.MarketingAirline value) {
      this.marketingAirline = value;
   }

   public String getFlightNumber() {
      return this.flightNumber;
   }

   public void setFlightNumber(String value) {
      this.flightNumber = value;
   }

   public String getTourOperatorFlightID() {
      return this.tourOperatorFlightID;
   }

   public void setTourOperatorFlightID(String value) {
      this.tourOperatorFlightID = value;
   }

   public Boolean isGovernmentApprovalInd() {
      return this.governmentApprovalInd;
   }

   public void setGovernmentApprovalInd(Boolean value) {
      this.governmentApprovalInd = value;
   }

   public String getGovernmentApprovalText() {
      return this.governmentApprovalText;
   }

   public void setGovernmentApprovalText(String value) {
      this.governmentApprovalText = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class MarketingAirline extends CompanyNameType {
      @XmlAttribute(name = "SingleVendorInd")
      @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
      protected String singleVendorInd;

      public String getSingleVendorInd() {
         return this.singleVendorInd;
      }

      public void setSingleVendorInd(String value) {
         this.singleVendorInd = value;
      }
   }
}
