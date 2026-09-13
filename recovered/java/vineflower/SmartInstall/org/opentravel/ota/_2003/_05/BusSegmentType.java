package org.opentravel.ota._2003._05;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BusSegmentType", propOrder = {"departureStation", "arrivalStation", "marketingCompany", "operatingCompany", "equipment", "busInfo"})
public class BusSegmentType {
   @XmlElement(name = "DepartureStation", required = true)
   protected StationDetailsType departureStation;
   @XmlElement(name = "ArrivalStation", required = true)
   protected StationDetailsType arrivalStation;
   @XmlElement(name = "MarketingCompany", required = true)
   protected CompanyNameType marketingCompany;
   @XmlElement(name = "OperatingCompany")
   protected CompanyNameType operatingCompany;
   @XmlElement(name = "Equipment")
   protected BusSegmentType.Equipment equipment;
   @XmlElement(name = "BusInfo")
   protected BusInfoType busInfo;
   @XmlAttribute(name = "DepartureDateTime")
   @XmlSchemaType(name = "dateTime")
   protected XMLGregorianCalendar departureDateTime;
   @XmlAttribute(name = "ArrivalDateTime")
   @XmlSchemaType(name = "dateTime")
   protected XMLGregorianCalendar arrivalDateTime;
   @XmlAttribute(name = "StopQuantity")
   @XmlSchemaType(name = "nonNegativeInteger")
   protected BigInteger stopQuantity;
   @XmlAttribute(name = "JourneyDuration")
   protected Duration journeyDuration;
   @XmlAttribute(name = "CrossBorderInd")
   protected Boolean crossBorderInd;

   public StationDetailsType getDepartureStation() {
      return this.departureStation;
   }

   public void setDepartureStation(StationDetailsType value) {
      this.departureStation = value;
   }

   public StationDetailsType getArrivalStation() {
      return this.arrivalStation;
   }

   public void setArrivalStation(StationDetailsType value) {
      this.arrivalStation = value;
   }

   public CompanyNameType getMarketingCompany() {
      return this.marketingCompany;
   }

   public void setMarketingCompany(CompanyNameType value) {
      this.marketingCompany = value;
   }

   public CompanyNameType getOperatingCompany() {
      return this.operatingCompany;
   }

   public void setOperatingCompany(CompanyNameType value) {
      this.operatingCompany = value;
   }

   public BusSegmentType.Equipment getEquipment() {
      return this.equipment;
   }

   public void setEquipment(BusSegmentType.Equipment value) {
      this.equipment = value;
   }

   public BusInfoType getBusInfo() {
      return this.busInfo;
   }

   public void setBusInfo(BusInfoType value) {
      this.busInfo = value;
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

   public BigInteger getStopQuantity() {
      return this.stopQuantity;
   }

   public void setStopQuantity(BigInteger value) {
      this.stopQuantity = value;
   }

   public Duration getJourneyDuration() {
      return this.journeyDuration;
   }

   public void setJourneyDuration(Duration value) {
      this.journeyDuration = value;
   }

   public Boolean isCrossBorderInd() {
      return this.crossBorderInd;
   }

   public void setCrossBorderInd(Boolean value) {
      this.crossBorderInd = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class Equipment {
      @XmlAttribute(name = "Code", required = true)
      protected String code;
      @XmlAttribute(name = "CodeContext")
      protected String codeContext;
      @XmlAttribute(name = "Quantity")
      @XmlSchemaType(name = "nonNegativeInteger")
      protected BigInteger quantity;

      public String getCode() {
         return this.code;
      }

      public void setCode(String value) {
         this.code = value;
      }

      public String getCodeContext() {
         return this.codeContext;
      }

      public void setCodeContext(String value) {
         this.codeContext = value;
      }

      public BigInteger getQuantity() {
         return this.quantity;
      }

      public void setQuantity(BigInteger value) {
         this.quantity = value;
      }
   }
}
