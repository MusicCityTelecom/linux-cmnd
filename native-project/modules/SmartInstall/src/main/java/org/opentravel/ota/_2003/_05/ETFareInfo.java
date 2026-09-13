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
@XmlType(name = "ET_FareInfo", propOrder = {"waiver", "ruleIndicator"})
@XmlSeeAlso(EMDType.FareInfo.class)
public class ETFareInfo {
   @XmlElement(name = "Waiver")
   protected List<ETFareInfo.Waiver> waiver;
   @XmlElement(name = "RuleIndicator")
   protected List<ETFareInfo.RuleIndicator> ruleIndicator;
   @XmlAttribute(name = "NetReportingCode")
   protected String netReportingCode;
   @XmlAttribute(name = "StatisticalCode")
   protected String statisticalCode;
   @XmlAttribute(name = "TourCode")
   protected String tourCode;
   @XmlAttribute(name = "CountryCodeOfIssue")
   protected String countryCodeOfIssue;

   public List<ETFareInfo.Waiver> getWaiver() {
      if (this.waiver == null) {
         this.waiver = new ArrayList<>();
      }

      return this.waiver;
   }

   public List<ETFareInfo.RuleIndicator> getRuleIndicator() {
      if (this.ruleIndicator == null) {
         this.ruleIndicator = new ArrayList<>();
      }

      return this.ruleIndicator;
   }

   public String getNetReportingCode() {
      return this.netReportingCode;
   }

   public void setNetReportingCode(String value) {
      this.netReportingCode = value;
   }

   public String getStatisticalCode() {
      return this.statisticalCode;
   }

   public void setStatisticalCode(String value) {
      this.statisticalCode = value;
   }

   public String getTourCode() {
      return this.tourCode;
   }

   public void setTourCode(String value) {
      this.tourCode = value;
   }

   public String getCountryCodeOfIssue() {
      return this.countryCodeOfIssue;
   }

   public void setCountryCodeOfIssue(String value) {
      this.countryCodeOfIssue = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class RuleIndicator {
      @XmlAttribute(name = "RuleCode", required = true)
      protected String ruleCode;

      public String getRuleCode() {
         return this.ruleCode;
      }

      public void setRuleCode(String value) {
         this.ruleCode = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class Waiver {
      @XmlAttribute(name = "Code")
      protected String code;
      @XmlAttribute(name = "Type")
      @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
      protected String type;

      public String getCode() {
         return this.code;
      }

      public void setCode(String value) {
         this.code = value;
      }

      public String getType() {
         return this.type;
      }

      public void setType(String value) {
         this.type = value;
      }
   }
}
