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
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.Duration;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RateRulesType", propOrder = {"advanceBooking", "pickupReturnRules", "rateGuarantee", "paymentRules", "cancelPenaltyInfo", "rateDeposit"})
public class RateRulesType {
   @XmlElement(name = "AdvanceBooking")
   protected RateRulesType.AdvanceBooking advanceBooking;
   @XmlElement(name = "PickupReturnRules")
   protected List<RateRulesType.PickupReturnRules> pickupReturnRules;
   @XmlElement(name = "RateGuarantee")
   protected RateRulesType.RateGuarantee rateGuarantee;
   @XmlElement(name = "PaymentRules")
   protected RateRulesType.PaymentRules paymentRules;
   @XmlElement(name = "CancelPenaltyInfo")
   protected List<RateRulesType.CancelPenaltyInfo> cancelPenaltyInfo;
   @XmlElement(name = "RateDeposit")
   protected RateRulesType.RateDeposit rateDeposit;
   @XmlAttribute(name = "MinimumKeep")
   protected Duration minimumKeep;
   @XmlAttribute(name = "MaximumKeep")
   protected Duration maximumKeep;
   @XmlAttribute(name = "MaximumRental")
   protected Duration maximumRental;

   public RateRulesType.AdvanceBooking getAdvanceBooking() {
      return this.advanceBooking;
   }

   public void setAdvanceBooking(RateRulesType.AdvanceBooking value) {
      this.advanceBooking = value;
   }

   public List<RateRulesType.PickupReturnRules> getPickupReturnRules() {
      if (this.pickupReturnRules == null) {
         this.pickupReturnRules = new ArrayList<>();
      }

      return this.pickupReturnRules;
   }

   public RateRulesType.RateGuarantee getRateGuarantee() {
      return this.rateGuarantee;
   }

   public void setRateGuarantee(RateRulesType.RateGuarantee value) {
      this.rateGuarantee = value;
   }

   public RateRulesType.PaymentRules getPaymentRules() {
      return this.paymentRules;
   }

   public void setPaymentRules(RateRulesType.PaymentRules value) {
      this.paymentRules = value;
   }

   public List<RateRulesType.CancelPenaltyInfo> getCancelPenaltyInfo() {
      if (this.cancelPenaltyInfo == null) {
         this.cancelPenaltyInfo = new ArrayList<>();
      }

      return this.cancelPenaltyInfo;
   }

   public RateRulesType.RateDeposit getRateDeposit() {
      return this.rateDeposit;
   }

   public void setRateDeposit(RateRulesType.RateDeposit value) {
      this.rateDeposit = value;
   }

   public Duration getMinimumKeep() {
      return this.minimumKeep;
   }

   public void setMinimumKeep(Duration value) {
      this.minimumKeep = value;
   }

   public Duration getMaximumKeep() {
      return this.maximumKeep;
   }

   public void setMaximumKeep(Duration value) {
      this.maximumKeep = value;
   }

   public Duration getMaximumRental() {
      return this.maximumRental;
   }

   public void setMaximumRental(Duration value) {
      this.maximumRental = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class AdvanceBooking {
      @XmlAttribute(name = "RequiredInd")
      protected Boolean requiredInd;
      @XmlAttribute(name = "AbsoluteDeadline")
      protected String absoluteDeadline;
      @XmlAttribute(name = "OffsetTimeUnit")
      protected TimeUnitType offsetTimeUnit;
      @XmlAttribute(name = "OffsetUnitMultiplier")
      protected Integer offsetUnitMultiplier;
      @XmlAttribute(name = "OffsetDropTime")
      @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
      protected String offsetDropTime;

      public Boolean isRequiredInd() {
         return this.requiredInd;
      }

      public void setRequiredInd(Boolean value) {
         this.requiredInd = value;
      }

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
   @XmlType(name = "", propOrder = {"deadline", "penaltyFee", "description"})
   public static class CancelPenaltyInfo {
      @XmlElement(name = "Deadline")
      protected List<RateRulesType.CancelPenaltyInfo.Deadline> deadline;
      @XmlElement(name = "PenaltyFee")
      protected RateRulesType.CancelPenaltyInfo.PenaltyFee penaltyFee;
      @XmlElement(name = "Description")
      protected FormattedTextTextType description;
      @XmlAttribute(name = "GuaranteeRequiredInd")
      protected Boolean guaranteeRequiredInd;
      @XmlAttribute(name = "ModifyPenaltyInd")
      protected Boolean modifyPenaltyInd;

      public List<RateRulesType.CancelPenaltyInfo.Deadline> getDeadline() {
         if (this.deadline == null) {
            this.deadline = new ArrayList<>();
         }

         return this.deadline;
      }

      public RateRulesType.CancelPenaltyInfo.PenaltyFee getPenaltyFee() {
         return this.penaltyFee;
      }

      public void setPenaltyFee(RateRulesType.CancelPenaltyInfo.PenaltyFee value) {
         this.penaltyFee = value;
      }

      public FormattedTextTextType getDescription() {
         return this.description;
      }

      public void setDescription(FormattedTextTextType value) {
         this.description = value;
      }

      public Boolean isGuaranteeRequiredInd() {
         return this.guaranteeRequiredInd;
      }

      public void setGuaranteeRequiredInd(Boolean value) {
         this.guaranteeRequiredInd = value;
      }

      public Boolean isModifyPenaltyInd() {
         return this.modifyPenaltyInd;
      }

      public void setModifyPenaltyInd(Boolean value) {
         this.modifyPenaltyInd = value;
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
      public static class PenaltyFee {
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

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "acceptablePayments")
   public static class PaymentRules extends PaymentRulesType {
      @XmlElement(name = "AcceptablePayments")
      protected List<RateRulesType.PaymentRules.AcceptablePayments> acceptablePayments;

      public List<RateRulesType.PaymentRules.AcceptablePayments> getAcceptablePayments() {
         if (this.acceptablePayments == null) {
            this.acceptablePayments = new ArrayList<>();
         }

         return this.acceptablePayments;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = "acceptablePayment")
      public static class AcceptablePayments {
         @XmlElement(name = "AcceptablePayment", required = true)
         protected List<RateRulesType.PaymentRules.AcceptablePayments.AcceptablePayment> acceptablePayment;
         @XmlAttribute(name = "PaymentTypeCode")
         protected String paymentTypeCode;

         public List<RateRulesType.PaymentRules.AcceptablePayments.AcceptablePayment> getAcceptablePayment() {
            if (this.acceptablePayment == null) {
               this.acceptablePayment = new ArrayList<>();
            }

            return this.acceptablePayment;
         }

         public String getPaymentTypeCode() {
            return this.paymentTypeCode;
         }

         public void setPaymentTypeCode(String value) {
            this.paymentTypeCode = value;
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "")
         public static class AcceptablePayment {
            @XmlAttribute(name = "CreditCardCode")
            protected String creditCardCode;

            public String getCreditCardCode() {
               return this.creditCardCode;
            }

            public void setCreditCardCode(String value) {
               this.creditCardCode = value;
            }
         }
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = {"earliestPickup", "latestPickup", "latestReturn", "earliestReturn"})
   public static class PickupReturnRules {
      @XmlElement(name = "EarliestPickup")
      protected RateRulesType.PickupReturnRules.EarliestPickup earliestPickup;
      @XmlElement(name = "LatestPickup")
      protected RateRulesType.PickupReturnRules.LatestPickup latestPickup;
      @XmlElement(name = "LatestReturn")
      protected RateRulesType.PickupReturnRules.LatestReturn latestReturn;
      @XmlElement(name = "EarliestReturn")
      protected RateRulesType.PickupReturnRules.EarliestReturn earliestReturn;
      @XmlAttribute(name = "DayOfWeek")
      protected DayOfWeekType dayOfWeek;
      @XmlAttribute(name = "MinimumKeep")
      protected Duration minimumKeep;
      @XmlAttribute(name = "MaximumKeep")
      protected Duration maximumKeep;
      @XmlAttribute(name = "MaximumRental")
      protected Duration maximumRental;
      @XmlAttribute(name = "OvernightInd")
      protected Boolean overnightInd;
      @XmlAttribute(name = "ReturnAllowedInd")
      protected Boolean returnAllowedInd;

      public RateRulesType.PickupReturnRules.EarliestPickup getEarliestPickup() {
         return this.earliestPickup;
      }

      public void setEarliestPickup(RateRulesType.PickupReturnRules.EarliestPickup value) {
         this.earliestPickup = value;
      }

      public RateRulesType.PickupReturnRules.LatestPickup getLatestPickup() {
         return this.latestPickup;
      }

      public void setLatestPickup(RateRulesType.PickupReturnRules.LatestPickup value) {
         this.latestPickup = value;
      }

      public RateRulesType.PickupReturnRules.LatestReturn getLatestReturn() {
         return this.latestReturn;
      }

      public void setLatestReturn(RateRulesType.PickupReturnRules.LatestReturn value) {
         this.latestReturn = value;
      }

      public RateRulesType.PickupReturnRules.EarliestReturn getEarliestReturn() {
         return this.earliestReturn;
      }

      public void setEarliestReturn(RateRulesType.PickupReturnRules.EarliestReturn value) {
         this.earliestReturn = value;
      }

      public DayOfWeekType getDayOfWeek() {
         return this.dayOfWeek;
      }

      public void setDayOfWeek(DayOfWeekType value) {
         this.dayOfWeek = value;
      }

      public Duration getMinimumKeep() {
         return this.minimumKeep;
      }

      public void setMinimumKeep(Duration value) {
         this.minimumKeep = value;
      }

      public Duration getMaximumKeep() {
         return this.maximumKeep;
      }

      public void setMaximumKeep(Duration value) {
         this.maximumKeep = value;
      }

      public Duration getMaximumRental() {
         return this.maximumRental;
      }

      public void setMaximumRental(Duration value) {
         this.maximumRental = value;
      }

      public Boolean isOvernightInd() {
         return this.overnightInd;
      }

      public void setOvernightInd(Boolean value) {
         this.overnightInd = value;
      }

      public Boolean isReturnAllowedInd() {
         return this.returnAllowedInd;
      }

      public void setReturnAllowedInd(Boolean value) {
         this.returnAllowedInd = value;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class EarliestPickup {
         @XmlAttribute(name = "DayOfWeek")
         protected DayOfWeekType dayOfWeek;
         @XmlAttribute(name = "Time")
         protected String time;

         public DayOfWeekType getDayOfWeek() {
            return this.dayOfWeek;
         }

         public void setDayOfWeek(DayOfWeekType value) {
            this.dayOfWeek = value;
         }

         public String getTime() {
            return this.time;
         }

         public void setTime(String value) {
            this.time = value;
         }
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class EarliestReturn {
         @XmlAttribute(name = "DayOfWeek")
         protected DayOfWeekType dayOfWeek;
         @XmlAttribute(name = "Time")
         protected String time;

         public DayOfWeekType getDayOfWeek() {
            return this.dayOfWeek;
         }

         public void setDayOfWeek(DayOfWeekType value) {
            this.dayOfWeek = value;
         }

         public String getTime() {
            return this.time;
         }

         public void setTime(String value) {
            this.time = value;
         }
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class LatestPickup {
         @XmlAttribute(name = "DayOfWeek")
         protected DayOfWeekType dayOfWeek;
         @XmlAttribute(name = "Time")
         protected String time;

         public DayOfWeekType getDayOfWeek() {
            return this.dayOfWeek;
         }

         public void setDayOfWeek(DayOfWeekType value) {
            this.dayOfWeek = value;
         }

         public String getTime() {
            return this.time;
         }

         public void setTime(String value) {
            this.time = value;
         }
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class LatestReturn {
         @XmlAttribute(name = "DayOfWeek")
         protected DayOfWeekType dayOfWeek;
         @XmlAttribute(name = "Time")
         protected String time;

         public DayOfWeekType getDayOfWeek() {
            return this.dayOfWeek;
         }

         public void setDayOfWeek(DayOfWeekType value) {
            this.dayOfWeek = value;
         }

         public String getTime() {
            return this.time;
         }

         public void setTime(String value) {
            this.time = value;
         }
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class RateDeposit {
      @XmlAttribute(name = "DepositRequiredInd")
      protected Boolean depositRequiredInd;
      @XmlAttribute(name = "Start")
      protected String start;
      @XmlAttribute(name = "Duration")
      protected String duration;
      @XmlAttribute(name = "End")
      protected String end;
      @XmlAttribute(name = "AbsoluteDeadline")
      protected String absoluteDeadline;
      @XmlAttribute(name = "OffsetTimeUnit")
      protected TimeUnitType offsetTimeUnit;
      @XmlAttribute(name = "OffsetUnitMultiplier")
      protected Integer offsetUnitMultiplier;
      @XmlAttribute(name = "OffsetDropTime")
      @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
      protected String offsetDropTime;

      public Boolean isDepositRequiredInd() {
         return this.depositRequiredInd;
      }

      public void setDepositRequiredInd(Boolean value) {
         this.depositRequiredInd = value;
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
   @XmlType(name = "", propOrder = "description")
   public static class RateGuarantee {
      @XmlElement(name = "Description")
      protected FormattedTextTextType description;
      @XmlAttribute(name = "AbsoluteDeadline")
      protected String absoluteDeadline;
      @XmlAttribute(name = "OffsetTimeUnit")
      protected TimeUnitType offsetTimeUnit;
      @XmlAttribute(name = "OffsetUnitMultiplier")
      protected Integer offsetUnitMultiplier;
      @XmlAttribute(name = "OffsetDropTime")
      @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
      protected String offsetDropTime;
      @XmlAttribute(name = "Start")
      protected String start;
      @XmlAttribute(name = "Duration")
      protected String duration;
      @XmlAttribute(name = "End")
      protected String end;

      public FormattedTextTextType getDescription() {
         return this.description;
      }

      public void setDescription(FormattedTextTextType value) {
         this.description = value;
      }

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
   }
}
