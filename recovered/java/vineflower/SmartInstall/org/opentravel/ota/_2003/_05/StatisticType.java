package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StatisticType", propOrder = "statisticApplicationSets")
public class StatisticType {
   @XmlElement(name = "StatisticApplicationSets", required = true)
   protected StatisticType.StatisticApplicationSets statisticApplicationSets;
   @XmlAttribute(name = "FiscalDate")
   protected String fiscalDate;
   @XmlAttribute(name = "ReportCode")
   protected String reportCode;
   @XmlAttribute(name = "ChainCode")
   protected String chainCode;
   @XmlAttribute(name = "BrandCode")
   protected String brandCode;
   @XmlAttribute(name = "HotelCode")
   protected String hotelCode;
   @XmlAttribute(name = "HotelCityCode")
   protected String hotelCityCode;
   @XmlAttribute(name = "HotelName")
   protected String hotelName;
   @XmlAttribute(name = "HotelCodeContext")
   protected String hotelCodeContext;
   @XmlAttribute(name = "ChainName")
   protected String chainName;
   @XmlAttribute(name = "BrandName")
   protected String brandName;
   @XmlAttribute(name = "AreaID")
   protected String areaID;

   public StatisticType.StatisticApplicationSets getStatisticApplicationSets() {
      return this.statisticApplicationSets;
   }

   public void setStatisticApplicationSets(StatisticType.StatisticApplicationSets value) {
      this.statisticApplicationSets = value;
   }

   public String getFiscalDate() {
      return this.fiscalDate;
   }

   public void setFiscalDate(String value) {
      this.fiscalDate = value;
   }

   public String getReportCode() {
      return this.reportCode;
   }

   public void setReportCode(String value) {
      this.reportCode = value;
   }

   public String getChainCode() {
      return this.chainCode;
   }

   public void setChainCode(String value) {
      this.chainCode = value;
   }

   public String getBrandCode() {
      return this.brandCode;
   }

   public void setBrandCode(String value) {
      this.brandCode = value;
   }

   public String getHotelCode() {
      return this.hotelCode;
   }

   public void setHotelCode(String value) {
      this.hotelCode = value;
   }

   public String getHotelCityCode() {
      return this.hotelCityCode;
   }

   public void setHotelCityCode(String value) {
      this.hotelCityCode = value;
   }

   public String getHotelName() {
      return this.hotelName;
   }

   public void setHotelName(String value) {
      this.hotelName = value;
   }

   public String getHotelCodeContext() {
      return this.hotelCodeContext;
   }

   public void setHotelCodeContext(String value) {
      this.hotelCodeContext = value;
   }

   public String getChainName() {
      return this.chainName;
   }

   public void setChainName(String value) {
      this.chainName = value;
   }

   public String getBrandName() {
      return this.brandName;
   }

   public void setBrandName(String value) {
      this.brandName = value;
   }

   public String getAreaID() {
      return this.areaID;
   }

   public void setAreaID(String value) {
      this.areaID = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "statisticApplicationSet")
   public static class StatisticApplicationSets {
      @XmlElement(name = "StatisticApplicationSet", required = true)
      protected List<StatisticApplicationSetType> statisticApplicationSet;

      public List<StatisticApplicationSetType> getStatisticApplicationSet() {
         if (this.statisticApplicationSet == null) {
            this.statisticApplicationSet = new ArrayList<>();
         }

         return this.statisticApplicationSet;
      }
   }
}
