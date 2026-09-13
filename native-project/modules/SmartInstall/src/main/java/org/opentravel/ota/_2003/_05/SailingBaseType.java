package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SailingBaseType", propOrder = {"cruiseLine", "region", "departurePort", "arrivalPort"})
@XmlSeeAlso(SailingInfoType.SelectedSailing.class)
public class SailingBaseType {
   @XmlElement(name = "CruiseLine", required = true)
   protected SailingBaseType.CruiseLine cruiseLine;
   @XmlElement(name = "Region")
   protected SailingBaseType.Region region;
   @XmlElement(name = "DeparturePort")
   protected SailingBaseType.DeparturePort departurePort;
   @XmlElement(name = "ArrivalPort")
   protected SailingBaseType.ArrivalPort arrivalPort;
   @XmlAttribute(name = "ListOfSailingDescriptionCode")
   protected List<String> listOfSailingDescriptionCode;

   public SailingBaseType.CruiseLine getCruiseLine() {
      return this.cruiseLine;
   }

   public void setCruiseLine(SailingBaseType.CruiseLine value) {
      this.cruiseLine = value;
   }

   public SailingBaseType.Region getRegion() {
      return this.region;
   }

   public void setRegion(SailingBaseType.Region value) {
      this.region = value;
   }

   public SailingBaseType.DeparturePort getDeparturePort() {
      return this.departurePort;
   }

   public void setDeparturePort(SailingBaseType.DeparturePort value) {
      this.departurePort = value;
   }

   public SailingBaseType.ArrivalPort getArrivalPort() {
      return this.arrivalPort;
   }

   public void setArrivalPort(SailingBaseType.ArrivalPort value) {
      this.arrivalPort = value;
   }

   public List<String> getListOfSailingDescriptionCode() {
      if (this.listOfSailingDescriptionCode == null) {
         this.listOfSailingDescriptionCode = new ArrayList<>();
      }

      return this.listOfSailingDescriptionCode;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class ArrivalPort extends LocationType {
      @XmlAttribute(name = "DebarkationDateTime")
      @XmlSchemaType(name = "dateTime")
      protected XMLGregorianCalendar debarkationDateTime;

      public XMLGregorianCalendar getDebarkationDateTime() {
         return this.debarkationDateTime;
      }

      public void setDebarkationDateTime(XMLGregorianCalendar value) {
         this.debarkationDateTime = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class CruiseLine {
      @XmlAttribute(name = "VendorCode")
      protected String vendorCode;
      @XmlAttribute(name = "VendorName")
      protected String vendorName;
      @XmlAttribute(name = "ShipCode")
      protected String shipCode;
      @XmlAttribute(name = "ShipName")
      protected String shipName;
      @XmlAttribute(name = "VendorCodeContext")
      protected String vendorCodeContext;

      public String getVendorCode() {
         return this.vendorCode;
      }

      public void setVendorCode(String value) {
         this.vendorCode = value;
      }

      public String getVendorName() {
         return this.vendorName;
      }

      public void setVendorName(String value) {
         this.vendorName = value;
      }

      public String getShipCode() {
         return this.shipCode;
      }

      public void setShipCode(String value) {
         this.shipCode = value;
      }

      public String getShipName() {
         return this.shipName;
      }

      public void setShipName(String value) {
         this.shipName = value;
      }

      public String getVendorCodeContext() {
         return this.vendorCodeContext;
      }

      public void setVendorCodeContext(String value) {
         this.vendorCodeContext = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class DeparturePort extends LocationType {
      @XmlAttribute(name = "EmbarkationTime")
      @XmlSchemaType(name = "dateTime")
      protected XMLGregorianCalendar embarkationTime;

      public XMLGregorianCalendar getEmbarkationTime() {
         return this.embarkationTime;
      }

      public void setEmbarkationTime(XMLGregorianCalendar value) {
         this.embarkationTime = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class Region {
      @XmlAttribute(name = "RegionCode")
      protected String regionCode;
      @XmlAttribute(name = "RegionName")
      protected String regionName;
      @XmlAttribute(name = "SubRegionCode")
      protected String subRegionCode;
      @XmlAttribute(name = "SubRegionName")
      protected String subRegionName;

      public String getRegionCode() {
         return this.regionCode;
      }

      public void setRegionCode(String value) {
         this.regionCode = value;
      }

      public String getRegionName() {
         return this.regionName;
      }

      public void setRegionName(String value) {
         this.regionName = value;
      }

      public String getSubRegionCode() {
         return this.subRegionCode;
      }

      public void setSubRegionCode(String value) {
         this.subRegionCode = value;
      }

      public String getSubRegionName() {
         return this.subRegionName;
      }

      public void setSubRegionName(String value) {
         this.subRegionName = value;
      }
   }
}
