package org.opentravel.ota._2003._05;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NoShowFeeType", propOrder = {"deadline", "gracePeriod", "feeAmount", "description"})
public class NoShowFeeType {
   @XmlElement(name = "Deadline")
   protected NoShowFeeType.Deadline deadline;
   @XmlElement(name = "GracePeriod")
   protected NoShowFeeType.GracePeriod gracePeriod;
   @XmlElement(name = "FeeAmount")
   protected NoShowFeeType.FeeAmount feeAmount;
   @XmlElement(name = "Description")
   protected FormattedTextTextType description;

   public NoShowFeeType.Deadline getDeadline() {
      return this.deadline;
   }

   public void setDeadline(NoShowFeeType.Deadline value) {
      this.deadline = value;
   }

   public NoShowFeeType.GracePeriod getGracePeriod() {
      return this.gracePeriod;
   }

   public void setGracePeriod(NoShowFeeType.GracePeriod value) {
      this.gracePeriod = value;
   }

   public NoShowFeeType.FeeAmount getFeeAmount() {
      return this.feeAmount;
   }

   public void setFeeAmount(NoShowFeeType.FeeAmount value) {
      this.feeAmount = value;
   }

   public FormattedTextTextType getDescription() {
      return this.description;
   }

   public void setDescription(FormattedTextTextType value) {
      this.description = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class Deadline {
      @XmlAttribute(name = "AbsoluteDeadline")
      protected String absoluteDeadline;
      @XmlAttribute(name = "OffsetTimeUnit")
      protected TimeUnitType offsetTimeUnit;
      @XmlAttribute(name = "OffsetUnitMultiplier")
      protected Integer offsetUnitMultiplier;
      @XmlAttribute(name = "OffsetDropTime")
      @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
      protected String offsetDropTime;

      public String getAbsoluteDeadline() {
         return this.absoluteDeadline;
      }

      public void setAbsoluteDeadline(String value) {
         this.absoluteDeadline = value;
      }

      public TimeUnitType getOffsetTimeUnit() {
         return this.offsetTimeUnit;
      }

      public void setOffsetTimeUnit(TimeUnitType value) {
         this.offsetTimeUnit = value;
      }

      public Integer getOffsetUnitMultiplier() {
         return this.offsetUnitMultiplier;
      }

      public void setOffsetUnitMultiplier(Integer value) {
         this.offsetUnitMultiplier = value;
      }

      public String getOffsetDropTime() {
         return this.offsetDropTime;
      }

      public void setOffsetDropTime(String value) {
         this.offsetDropTime = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class FeeAmount {
      @XmlAttribute(name = "RateConvertedInd")
      protected Boolean rateConvertedInd;
      @XmlAttribute(name = "GuaranteeReqInd")
      protected Boolean guaranteeReqInd;
      @XmlAttribute(name = "EmailRequiredInd")
      protected Boolean emailRequiredInd;
      @XmlAttribute(name = "Amount")
      protected BigDecimal amount;
      @XmlAttribute(name = "CurrencyCode")
      protected String currencyCode;
      @XmlAttribute(name = "DecimalPlaces")
      @XmlSchemaType(name = "nonNegativeInteger")
      protected BigInteger decimalPlaces;

      public Boolean isRateConvertedInd() {
         return this.rateConvertedInd;
      }

      public void setRateConvertedInd(Boolean value) {
         this.rateConvertedInd = value;
      }

      public Boolean isGuaranteeReqInd() {
         return this.guaranteeReqInd;
      }

      public void setGuaranteeReqInd(Boolean value) {
         this.guaranteeReqInd = value;
      }

      public Boolean isEmailRequiredInd() {
         return this.emailRequiredInd;
      }

      public void setEmailRequiredInd(Boolean value) {
         this.emailRequiredInd = value;
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

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class GracePeriod {
      @XmlAttribute(name = "AbsoluteDeadline")
      protected String absoluteDeadline;
      @XmlAttribute(name = "OffsetTimeUnit")
      protected TimeUnitType offsetTimeUnit;
      @XmlAttribute(name = "OffsetUnitMultiplier")
      protected Integer offsetUnitMultiplier;
      @XmlAttribute(name = "OffsetDropTime")
      @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
      protected String offsetDropTime;

      public String getAbsoluteDeadline() {
         return this.absoluteDeadline;
      }

      public void setAbsoluteDeadline(String value) {
         this.absoluteDeadline = value;
      }

      public TimeUnitType getOffsetTimeUnit() {
         return this.offsetTimeUnit;
      }

      public void setOffsetTimeUnit(TimeUnitType value) {
         this.offsetTimeUnit = value;
      }

      public Integer getOffsetUnitMultiplier() {
         return this.offsetUnitMultiplier;
      }

      public void setOffsetUnitMultiplier(Integer value) {
         this.offsetUnitMultiplier = value;
      }

      public String getOffsetDropTime() {
         return this.offsetDropTime;
      }

      public void setOffsetDropTime(String value) {
         this.offsetDropTime = value;
      }
   }
}
