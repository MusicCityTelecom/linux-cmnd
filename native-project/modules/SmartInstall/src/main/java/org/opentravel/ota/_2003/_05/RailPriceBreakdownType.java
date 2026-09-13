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
@XmlType(name = "RailPriceBreakdownType", propOrder = {"accommodationAdjustment", "fareQualifierAdjustment", "discountClass", "ancillaryCharge", "fee", "tax"})
public class RailPriceBreakdownType extends RailFareType {
   @XmlElement(name = "AccommodationAdjustment")
   protected RailPriceBreakdownType.AccommodationAdjustment accommodationAdjustment;
   @XmlElement(name = "FareQualifierAdjustment")
   protected List<CodeAmountType> fareQualifierAdjustment;
   @XmlElement(name = "DiscountClass")
   protected List<RailPriceBreakdownType.DiscountClass> discountClass;
   @XmlElement(name = "AncillaryCharge")
   protected List<CodeAmountType> ancillaryCharge;
   @XmlElement(name = "Fee")
   protected List<CodeAmountType> fee;
   @XmlElement(name = "Tax")
   protected List<CodeAmountType> tax;

   public RailPriceBreakdownType.AccommodationAdjustment getAccommodationAdjustment() {
      return this.accommodationAdjustment;
   }

   public void setAccommodationAdjustment(RailPriceBreakdownType.AccommodationAdjustment value) {
      this.accommodationAdjustment = value;
   }

   public List<CodeAmountType> getFareQualifierAdjustment() {
      if (this.fareQualifierAdjustment == null) {
         this.fareQualifierAdjustment = new ArrayList<>();
      }

      return this.fareQualifierAdjustment;
   }

   public List<RailPriceBreakdownType.DiscountClass> getDiscountClass() {
      if (this.discountClass == null) {
         this.discountClass = new ArrayList<>();
      }

      return this.discountClass;
   }

   public List<CodeAmountType> getAncillaryCharge() {
      if (this.ancillaryCharge == null) {
         this.ancillaryCharge = new ArrayList<>();
      }

      return this.ancillaryCharge;
   }

   public List<CodeAmountType> getFee() {
      if (this.fee == null) {
         this.fee = new ArrayList<>();
      }

      return this.fee;
   }

   public List<CodeAmountType> getTax() {
      if (this.tax == null) {
         this.tax = new ArrayList<>();
      }

      return this.tax;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class AccommodationAdjustment extends CodeAmountType {
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class DiscountClass {
      @XmlAttribute(name = "Percent")
      protected BigDecimal percent;
      @XmlAttribute(name = "ID")
      protected String id;
      @XmlAttribute(name = "Description")
      protected String description;
      @XmlAttribute(name = "Amount")
      protected BigDecimal amount;
      @XmlAttribute(name = "CurrencyCode")
      protected String currencyCode;
      @XmlAttribute(name = "DecimalPlaces")
      @XmlSchemaType(name = "nonNegativeInteger")
      protected BigInteger decimalPlaces;

      public BigDecimal getPercent() {
         return this.percent;
      }

      public void setPercent(BigDecimal value) {
         this.percent = value;
      }

      public String getID() {
         return this.id;
      }

      public void setID(String value) {
         this.id = value;
      }

      public String getDescription() {
         return this.description;
      }

      public void setDescription(String value) {
         this.description = value;
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
