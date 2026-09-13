package org.opentravel.ota._2003._05;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RevenueCategoryType", propOrder = {"summaryAmount", "revenueDetails"})
public class RevenueCategoryType {
   @XmlElement(name = "SummaryAmount")
   protected RevenueCategoryType.SummaryAmount summaryAmount;
   @XmlElement(name = "RevenueDetails")
   protected RevenueDetailsType revenueDetails;
   @XmlAttribute(name = "RevenueCategoryCode")
   protected String revenueCategoryCode;

   public RevenueCategoryType.SummaryAmount getSummaryAmount() {
      return this.summaryAmount;
   }

   public void setSummaryAmount(RevenueCategoryType.SummaryAmount value) {
      this.summaryAmount = value;
   }

   public RevenueDetailsType getRevenueDetails() {
      return this.revenueDetails;
   }

   public void setRevenueDetails(RevenueDetailsType value) {
      this.revenueDetails = value;
   }

   public String getRevenueCategoryCode() {
      return this.revenueCategoryCode;
   }

   public void setRevenueCategoryCode(String value) {
      this.revenueCategoryCode = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class SummaryAmount {
      @XmlAttribute(name = "Amount")
      protected BigDecimal amount;
      @XmlAttribute(name = "CurrencyCode")
      protected String currencyCode;
      @XmlAttribute(name = "DecimalPlaces")
      @XmlSchemaType(name = "nonNegativeInteger")
      protected BigInteger decimalPlaces;

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
