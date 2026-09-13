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
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VehicleChargeType", propOrder = {"taxAmounts", "minMax", "calculation"})
@XmlSeeAlso(
   {
         VehicleChargePurposeType.class,
         VehicleLocationAdditionalFeesType.Fees.Fee.class,
         VehicleLocationAdditionalFeesType.Surcharges.Surcharge.class,
         VehicleLocationAdditionalFeesType.MiscellaneousCharges.MiscellaneousCharge.class
   }
)
public class VehicleChargeType {
   @XmlElement(name = "TaxAmounts")
   protected VehicleChargeType.TaxAmounts taxAmounts;
   @XmlElement(name = "MinMax")
   protected VehicleChargeType.MinMax minMax;
   @XmlElement(name = "Calculation")
   protected List<VehicleChargeType.Calculation> calculation;
   @XmlAttribute(name = "TaxInclusive")
   protected Boolean taxInclusive;
   @XmlAttribute(name = "Description")
   protected String description;
   @XmlAttribute(name = "GuaranteedInd")
   protected Boolean guaranteedInd;
   @XmlAttribute(name = "IncludedInRate")
   protected Boolean includedInRate;
   @XmlAttribute(name = "IncludedInEstTotalInd")
   protected Boolean includedInEstTotalInd;
   @XmlAttribute(name = "RateConvertInd")
   protected Boolean rateConvertInd;
   @XmlAttribute(name = "Amount")
   protected BigDecimal amount;
   @XmlAttribute(name = "CurrencyCode")
   protected String currencyCode;
   @XmlAttribute(name = "DecimalPlaces")
   @XmlSchemaType(name = "nonNegativeInteger")
   protected BigInteger decimalPlaces;

   public VehicleChargeType.TaxAmounts getTaxAmounts() {
      return this.taxAmounts;
   }

   public void setTaxAmounts(VehicleChargeType.TaxAmounts value) {
      this.taxAmounts = value;
   }

   public VehicleChargeType.MinMax getMinMax() {
      return this.minMax;
   }

   public void setMinMax(VehicleChargeType.MinMax value) {
      this.minMax = value;
   }

   public List<VehicleChargeType.Calculation> getCalculation() {
      if (this.calculation == null) {
         this.calculation = new ArrayList<>();
      }

      return this.calculation;
   }

   public Boolean isTaxInclusive() {
      return this.taxInclusive;
   }

   public void setTaxInclusive(Boolean value) {
      this.taxInclusive = value;
   }

   public String getDescription() {
      return this.description;
   }

   public void setDescription(String value) {
      this.description = value;
   }

   public Boolean isGuaranteedInd() {
      return this.guaranteedInd;
   }

   public void setGuaranteedInd(Boolean value) {
      this.guaranteedInd = value;
   }

   public Boolean isIncludedInRate() {
      return this.includedInRate;
   }

   public void setIncludedInRate(Boolean value) {
      this.includedInRate = value;
   }

   public Boolean isIncludedInEstTotalInd() {
      return this.includedInEstTotalInd;
   }

   public void setIncludedInEstTotalInd(Boolean value) {
      this.includedInEstTotalInd = value;
   }

   public Boolean isRateConvertInd() {
      return this.rateConvertInd;
   }

   public void setRateConvertInd(Boolean value) {
      this.rateConvertInd = value;
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

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class Calculation {
      @XmlAttribute(name = "UnitCharge")
      protected BigDecimal unitCharge;
      @XmlAttribute(name = "UnitName")
      protected String unitName;
      @XmlAttribute(name = "Quantity")
      protected Integer quantity;
      @XmlAttribute(name = "Percentage")
      protected BigDecimal percentage;
      @XmlAttribute(name = "Applicability")
      @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
      protected String applicability;
      @XmlAttribute(name = "MaxQuantity")
      protected Integer maxQuantity;
      @XmlAttribute(name = "Total")
      protected BigDecimal total;

      public BigDecimal getUnitCharge() {
         return this.unitCharge;
      }

      public void setUnitCharge(BigDecimal value) {
         this.unitCharge = value;
      }

      public String getUnitName() {
         return this.unitName;
      }

      public void setUnitName(String value) {
         this.unitName = value;
      }

      public Integer getQuantity() {
         return this.quantity;
      }

      public void setQuantity(Integer value) {
         this.quantity = value;
      }

      public BigDecimal getPercentage() {
         return this.percentage;
      }

      public void setPercentage(BigDecimal value) {
         this.percentage = value;
      }

      public String getApplicability() {
         return this.applicability;
      }

      public void setApplicability(String value) {
         this.applicability = value;
      }

      public Integer getMaxQuantity() {
         return this.maxQuantity;
      }

      public void setMaxQuantity(Integer value) {
         this.maxQuantity = value;
      }

      public BigDecimal getTotal() {
         return this.total;
      }

      public void setTotal(BigDecimal value) {
         this.total = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class MinMax {
      @XmlAttribute(name = "MaxCharge")
      protected BigDecimal maxCharge;
      @XmlAttribute(name = "MinCharge")
      protected BigDecimal minCharge;
      @XmlAttribute(name = "MaxChargeDays")
      protected BigInteger maxChargeDays;

      public BigDecimal getMaxCharge() {
         return this.maxCharge;
      }

      public void setMaxCharge(BigDecimal value) {
         this.maxCharge = value;
      }

      public BigDecimal getMinCharge() {
         return this.minCharge;
      }

      public void setMinCharge(BigDecimal value) {
         this.minCharge = value;
      }

      public BigInteger getMaxChargeDays() {
         return this.maxChargeDays;
      }

      public void setMaxChargeDays(BigInteger value) {
         this.maxChargeDays = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "taxAmount")
   public static class TaxAmounts {
      @XmlElement(name = "TaxAmount", required = true)
      protected List<VehicleChargeType.TaxAmounts.TaxAmount> taxAmount;

      public List<VehicleChargeType.TaxAmounts.TaxAmount> getTaxAmount() {
         if (this.taxAmount == null) {
            this.taxAmount = new ArrayList<>();
         }

         return this.taxAmount;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class TaxAmount {
         @XmlAttribute(name = "Total", required = true)
         protected BigDecimal total;
         @XmlAttribute(name = "CurrencyCode", required = true)
         protected String currencyCode;
         @XmlAttribute(name = "TaxCode")
         protected String taxCode;
         @XmlAttribute(name = "Percentage")
         protected BigDecimal percentage;
         @XmlAttribute(name = "Description")
         protected String description;

         public BigDecimal getTotal() {
            return this.total;
         }

         public void setTotal(BigDecimal value) {
            this.total = value;
         }

         public String getCurrencyCode() {
            return this.currencyCode;
         }

         public void setCurrencyCode(String value) {
            this.currencyCode = value;
         }

         public String getTaxCode() {
            return this.taxCode;
         }

         public void setTaxCode(String value) {
            this.taxCode = value;
         }

         public BigDecimal getPercentage() {
            return this.percentage;
         }

         public void setPercentage(BigDecimal value) {
            this.percentage = value;
         }

         public String getDescription() {
            return this.description;
         }

         public void setDescription(String value) {
            this.description = value;
         }
      }
   }
}
