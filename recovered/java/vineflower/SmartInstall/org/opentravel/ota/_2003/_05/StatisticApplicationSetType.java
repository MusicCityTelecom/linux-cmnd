package org.opentravel.ota._2003._05;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StatisticApplicationSetType", propOrder = {"statisticCodes", "revenueCategorySummaries", "countCategorySummaries", "reportSummaries"})
public class StatisticApplicationSetType {
   @XmlElement(name = "StatisticCodes")
   protected StatisticApplicationSetType.StatisticCodes statisticCodes;
   @XmlElement(name = "RevenueCategorySummaries")
   protected StatisticApplicationSetType.RevenueCategorySummaries revenueCategorySummaries;
   @XmlElement(name = "CountCategorySummaries")
   protected StatisticApplicationSetType.CountCategorySummaries countCategorySummaries;
   @XmlElement(name = "ReportSummaries")
   protected StatisticApplicationSetType.ReportSummaries reportSummaries;
   @XmlAttribute(name = "Start")
   protected String start;
   @XmlAttribute(name = "Duration")
   protected String duration;
   @XmlAttribute(name = "End")
   protected String end;

   public StatisticApplicationSetType.StatisticCodes getStatisticCodes() {
      return this.statisticCodes;
   }

   public void setStatisticCodes(StatisticApplicationSetType.StatisticCodes value) {
      this.statisticCodes = value;
   }

   public StatisticApplicationSetType.RevenueCategorySummaries getRevenueCategorySummaries() {
      return this.revenueCategorySummaries;
   }

   public void setRevenueCategorySummaries(StatisticApplicationSetType.RevenueCategorySummaries value) {
      this.revenueCategorySummaries = value;
   }

   public StatisticApplicationSetType.CountCategorySummaries getCountCategorySummaries() {
      return this.countCategorySummaries;
   }

   public void setCountCategorySummaries(StatisticApplicationSetType.CountCategorySummaries value) {
      this.countCategorySummaries = value;
   }

   public StatisticApplicationSetType.ReportSummaries getReportSummaries() {
      return this.reportSummaries;
   }

   public void setReportSummaries(StatisticApplicationSetType.ReportSummaries value) {
      this.reportSummaries = value;
   }

   public String getStart() {
      return this.start;
   }

   public void setStart(String value) {
      this.start = value;
   }

   public String getDuration() {
      return this.duration;
   }

   public void setDuration(String value) {
      this.duration = value;
   }

   public String getEnd() {
      return this.end;
   }

   public void setEnd(String value) {
      this.end = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "countCategorySummary")
   public static class CountCategorySummaries {
      @XmlElement(name = "CountCategorySummary", required = true)
      protected List<StatisticApplicationSetType.CountCategorySummaries.CountCategorySummary> countCategorySummary;

      public List<StatisticApplicationSetType.CountCategorySummaries.CountCategorySummary> getCountCategorySummary() {
         if (this.countCategorySummary == null) {
            this.countCategorySummary = new ArrayList<>();
         }

         return this.countCategorySummary;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class CountCategorySummary {
         @XmlAttribute(name = "SummaryCount")
         @XmlSchemaType(name = "nonNegativeInteger")
         protected BigInteger summaryCount;
         @XmlAttribute(name = "CountCategoryCode")
         protected String countCategoryCode;

         public BigInteger getSummaryCount() {
            return this.summaryCount;
         }

         public void setSummaryCount(BigInteger value) {
            this.summaryCount = value;
         }

         public String getCountCategoryCode() {
            return this.countCategoryCode;
         }

         public void setCountCategoryCode(String value) {
            this.countCategoryCode = value;
         }
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "reportSummary")
   public static class ReportSummaries {
      @XmlElement(name = "ReportSummary", required = true)
      protected List<ParagraphType> reportSummary;

      public List<ParagraphType> getReportSummary() {
         if (this.reportSummary == null) {
            this.reportSummary = new ArrayList<>();
         }

         return this.reportSummary;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "revenueCategorySummary")
   public static class RevenueCategorySummaries {
      @XmlElement(name = "RevenueCategorySummary", required = true)
      protected List<StatisticApplicationSetType.RevenueCategorySummaries.RevenueCategorySummary> revenueCategorySummary;

      public List<StatisticApplicationSetType.RevenueCategorySummaries.RevenueCategorySummary> getRevenueCategorySummary() {
         if (this.revenueCategorySummary == null) {
            this.revenueCategorySummary = new ArrayList<>();
         }

         return this.revenueCategorySummary;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class RevenueCategorySummary {
         @XmlAttribute(name = "RevenueCategoryCode")
         protected String revenueCategoryCode;
         @XmlAttribute(name = "Amount")
         protected BigDecimal amount;
         @XmlAttribute(name = "CurrencyCode")
         protected String currencyCode;
         @XmlAttribute(name = "DecimalPlaces")
         @XmlSchemaType(name = "nonNegativeInteger")
         protected BigInteger decimalPlaces;

         public String getRevenueCategoryCode() {
            return this.revenueCategoryCode;
         }

         public void setRevenueCategoryCode(String value) {
            this.revenueCategoryCode = value;
         }

         public BigDecimal getAmount() {
            return this.amount;
         }

         public void setAmount(BigDecimal value) {
            this.amount = value;
         }

         public String getCurrencyCode() {
            return this.currencyCode;
         }

         public void setCurrencyCode(String value) {
            this.currencyCode = value;
         }

         public BigInteger getDecimalPlaces() {
            return this.decimalPlaces;
         }

         public void setDecimalPlaces(BigInteger value) {
            this.decimalPlaces = value;
         }
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "statisticCode")
   public static class StatisticCodes {
      @XmlElement(name = "StatisticCode", required = true)
      protected List<StatisticApplicationSetType.StatisticCodes.StatisticCode> statisticCode;

      public List<StatisticApplicationSetType.StatisticCodes.StatisticCode> getStatisticCode() {
         if (this.statisticCode == null) {
            this.statisticCode = new ArrayList<>();
         }

         return this.statisticCode;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class StatisticCode {
         @XmlAttribute(name = "StatCode")
         protected String statCode;
         @XmlAttribute(name = "StatCategoryCode")
         protected String statCategoryCode;

         public String getStatCode() {
            return this.statCode;
         }

         public void setStatCode(String value) {
            this.statCode = value;
         }

         public String getStatCategoryCode() {
            return this.statCategoryCode;
         }

         public void setStatCategoryCode(String value) {
            this.statCategoryCode = value;
         }
      }
   }
}
