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
import javax.xml.datatype.Duration;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BookingRulesType", propOrder = "bookingRule")
@XmlSeeAlso(HotelRatePlanType.BookingRules.class)
public class BookingRulesType {
   @XmlElement(name = "BookingRule", required = true)
   protected List<BookingRulesType.BookingRule> bookingRule;

   public List<BookingRulesType.BookingRule> getBookingRule() {
      if (this.bookingRule == null) {
         this.bookingRule = new ArrayList<>();
      }

      return this.bookingRule;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(
      name = "",
      propOrder = {
            "acceptableGuarantees",
            "cancelPenalties",
            "requiredPaymts",
            "lengthsOfStay",
            "dowRestrictions",
            "restrictionStatus",
            "viewerships",
            "addtionalRules",
            "description",
            "uniqueID",
            "checkoutCharge"
      }
   )
   public static class BookingRule {
      @XmlElement(name = "AcceptableGuarantees")
      protected BookingRulesType.BookingRule.AcceptableGuarantees acceptableGuarantees;
      @XmlElement(name = "CancelPenalties")
      protected CancelPenaltiesType cancelPenalties;
      @XmlElement(name = "RequiredPaymts")
      protected RequiredPaymentsType requiredPaymts;
      @XmlElement(name = "LengthsOfStay")
      protected LengthsOfStayType lengthsOfStay;
      @XmlElement(name = "DOW_Restrictions")
      protected DOWRestrictionsType dowRestrictions;
      @XmlElement(name = "RestrictionStatus")
      protected BookingRulesType.BookingRule.RestrictionStatus restrictionStatus;
      @XmlElement(name = "Viewerships")
      protected ViewershipsType viewerships;
      @XmlElement(name = "AddtionalRules")
      protected BookingRulesType.BookingRule.AddtionalRules addtionalRules;
      @XmlElement(name = "Description")
      protected List<ParagraphType> description;
      @XmlElement(name = "UniqueID")
      protected UniqueIDType uniqueID;
      @XmlElement(name = "CheckoutCharge")
      protected List<BookingRulesType.BookingRule.CheckoutCharge> checkoutCharge;
      @XmlAttribute(name = "MaxAdvancedBookingOffset")
      protected Duration maxAdvancedBookingOffset;
      @XmlAttribute(name = "MinAdvancedBookingOffset")
      protected Duration minAdvancedBookingOffset;
      @XmlAttribute(name = "ForceGuaranteeOffset")
      protected Duration forceGuaranteeOffset;
      @XmlAttribute(name = "DepositWaiverOffset")
      protected Duration depositWaiverOffset;
      @XmlAttribute(name = "MinTotalOccupancy")
      @XmlSchemaType(name = "nonNegativeInteger")
      protected BigInteger minTotalOccupancy;
      @XmlAttribute(name = "MaxTotalOccupancy")
      @XmlSchemaType(name = "nonNegativeInteger")
      protected BigInteger maxTotalOccupancy;
      @XmlAttribute(name = "AbsoluteDropTime")
      protected String absoluteDropTime;
      @XmlAttribute(name = "GenerallyBookable")
      protected Boolean generallyBookable;
      @XmlAttribute(name = "PriceViewable")
      protected Boolean priceViewable;
      @XmlAttribute(name = "QualifiedRateYN")
      protected Boolean qualifiedRateYN;
      @XmlAttribute(name = "AddressRequired")
      protected Boolean addressRequired;
      @XmlAttribute(name = "MaxContiguousBookings")
      @XmlSchemaType(name = "nonNegativeInteger")
      protected BigInteger maxContiguousBookings;
      @XmlAttribute(name = "AbsoluteCutoff")
      protected String absoluteCutoff;
      @XmlAttribute(name = "OffsetDuration")
      protected Duration offsetDuration;
      @XmlAttribute(name = "OffsetCalculationMode")
      @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
      protected String offsetCalculationMode;
      @XmlAttribute(name = "URI")
      @XmlSchemaType(name = "anyURI")
      protected String uri;
      @XmlAttribute(name = "Quantity")
      @XmlSchemaType(name = "nonNegativeInteger")
      protected BigInteger quantity;
      @XmlAttribute(name = "Code")
      protected String code;
      @XmlAttribute(name = "CodeContext")
      protected String codeContext;
      @XmlAttribute(name = "Start")
      protected String start;
      @XmlAttribute(name = "Duration")
      protected String duration;
      @XmlAttribute(name = "End")
      protected String end;

      public BookingRulesType.BookingRule.AcceptableGuarantees getAcceptableGuarantees() {
         return this.acceptableGuarantees;
      }

      public void setAcceptableGuarantees(BookingRulesType.BookingRule.AcceptableGuarantees value) {
         this.acceptableGuarantees = value;
      }

      public CancelPenaltiesType getCancelPenalties() {
         return this.cancelPenalties;
      }

      public void setCancelPenalties(CancelPenaltiesType value) {
         this.cancelPenalties = value;
      }

      public RequiredPaymentsType getRequiredPaymts() {
         return this.requiredPaymts;
      }

      public void setRequiredPaymts(RequiredPaymentsType value) {
         this.requiredPaymts = value;
      }

      public LengthsOfStayType getLengthsOfStay() {
         return this.lengthsOfStay;
      }

      public void setLengthsOfStay(LengthsOfStayType value) {
         this.lengthsOfStay = value;
      }

      public DOWRestrictionsType getDOWRestrictions() {
         return this.dowRestrictions;
      }

      public void setDOWRestrictions(DOWRestrictionsType value) {
         this.dowRestrictions = value;
      }

      public BookingRulesType.BookingRule.RestrictionStatus getRestrictionStatus() {
         return this.restrictionStatus;
      }

      public void setRestrictionStatus(BookingRulesType.BookingRule.RestrictionStatus value) {
         this.restrictionStatus = value;
      }

      public ViewershipsType getViewerships() {
         return this.viewerships;
      }

      public void setViewerships(ViewershipsType value) {
         this.viewerships = value;
      }

      public BookingRulesType.BookingRule.AddtionalRules getAddtionalRules() {
         return this.addtionalRules;
      }

      public void setAddtionalRules(BookingRulesType.BookingRule.AddtionalRules value) {
         this.addtionalRules = value;
      }

      public List<ParagraphType> getDescription() {
         if (this.description == null) {
            this.description = new ArrayList<>();
         }

         return this.description;
      }

      public UniqueIDType getUniqueID() {
         return this.uniqueID;
      }

      public void setUniqueID(UniqueIDType value) {
         this.uniqueID = value;
      }

      public List<BookingRulesType.BookingRule.CheckoutCharge> getCheckoutCharge() {
         if (this.checkoutCharge == null) {
            this.checkoutCharge = new ArrayList<>();
         }

         return this.checkoutCharge;
      }

      public Duration getMaxAdvancedBookingOffset() {
         return this.maxAdvancedBookingOffset;
      }

      public void setMaxAdvancedBookingOffset(Duration value) {
         this.maxAdvancedBookingOffset = value;
      }

      public Duration getMinAdvancedBookingOffset() {
         return this.minAdvancedBookingOffset;
      }

      public void setMinAdvancedBookingOffset(Duration value) {
         this.minAdvancedBookingOffset = value;
      }

      public Duration getForceGuaranteeOffset() {
         return this.forceGuaranteeOffset;
      }

      public void setForceGuaranteeOffset(Duration value) {
         this.forceGuaranteeOffset = value;
      }

      public Duration getDepositWaiverOffset() {
         return this.depositWaiverOffset;
      }

      public void setDepositWaiverOffset(Duration value) {
         this.depositWaiverOffset = value;
      }

      public BigInteger getMinTotalOccupancy() {
         return this.minTotalOccupancy;
      }

      public void setMinTotalOccupancy(BigInteger value) {
         this.minTotalOccupancy = value;
      }

      public BigInteger getMaxTotalOccupancy() {
         return this.maxTotalOccupancy;
      }

      public void setMaxTotalOccupancy(BigInteger value) {
         this.maxTotalOccupancy = value;
      }

      public String getAbsoluteDropTime() {
         return this.absoluteDropTime;
      }

      public void setAbsoluteDropTime(String value) {
         this.absoluteDropTime = value;
      }

      public Boolean isGenerallyBookable() {
         return this.generallyBookable;
      }

      public void setGenerallyBookable(Boolean value) {
         this.generallyBookable = value;
      }

      public Boolean isPriceViewable() {
         return this.priceViewable;
      }

      public void setPriceViewable(Boolean value) {
         this.priceViewable = value;
      }

      public Boolean isQualifiedRateYN() {
         return this.qualifiedRateYN;
      }

      public void setQualifiedRateYN(Boolean value) {
         this.qualifiedRateYN = value;
      }

      public Boolean isAddressRequired() {
         return this.addressRequired;
      }

      public void setAddressRequired(Boolean value) {
         this.addressRequired = value;
      }

      public BigInteger getMaxContiguousBookings() {
         return this.maxContiguousBookings;
      }

      public void setMaxContiguousBookings(BigInteger value) {
         this.maxContiguousBookings = value;
      }

      public String getAbsoluteCutoff() {
         return this.absoluteCutoff;
      }

      public void setAbsoluteCutoff(String value) {
         this.absoluteCutoff = value;
      }

      public Duration getOffsetDuration() {
         return this.offsetDuration;
      }

      public void setOffsetDuration(Duration value) {
         this.offsetDuration = value;
      }

      public String getOffsetCalculationMode() {
         return this.offsetCalculationMode;
      }

      public void setOffsetCalculationMode(String value) {
         this.offsetCalculationMode = value;
      }

      public String getURI() {
         return this.uri;
      }

      public void setURI(String value) {
         this.uri = value;
      }

      public BigInteger getQuantity() {
         return this.quantity;
      }

      public void setQuantity(BigInteger value) {
         this.quantity = value;
      }

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
      @XmlType(name = "", propOrder = "acceptableGuarantee")
      public static class AcceptableGuarantees {
         @XmlElement(name = "AcceptableGuarantee", required = true)
         protected List<BookingRulesType.BookingRule.AcceptableGuarantees.AcceptableGuarantee> acceptableGuarantee;

         public List<BookingRulesType.BookingRule.AcceptableGuarantees.AcceptableGuarantee> getAcceptableGuarantee() {
            if (this.acceptableGuarantee == null) {
               this.acceptableGuarantee = new ArrayList<>();
            }

            return this.acceptableGuarantee;
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "")
         public static class AcceptableGuarantee extends GuaranteeType {
            @XmlAttribute(name = "GuaranteePolicyType")
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            protected String guaranteePolicyType;
            @XmlAttribute(name = "PaymentType")
            protected String paymentType;
            @XmlAttribute(name = "UnacceptablePaymentType")
            protected String unacceptablePaymentType;
            @XmlAttribute(name = "CurrencyCode")
            protected String currencyCode;
            @XmlAttribute(name = "DecimalPlaces")
            @XmlSchemaType(name = "nonNegativeInteger")
            protected BigInteger decimalPlaces;

            public String getGuaranteePolicyType() {
               return this.guaranteePolicyType;
            }

            public void setGuaranteePolicyType(String value) {
               this.guaranteePolicyType = value;
            }

            public String getPaymentType() {
               return this.paymentType;
            }

            public void setPaymentType(String value) {
               this.paymentType = value;
            }

            public String getUnacceptablePaymentType() {
               return this.unacceptablePaymentType;
            }

            public void setUnacceptablePaymentType(String value) {
               this.unacceptablePaymentType = value;
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
      @XmlType(name = "", propOrder = "additionalRule")
      public static class AddtionalRules {
         @XmlElement(name = "AdditionalRule", required = true)
         protected List<BookingRulesType.BookingRule.AddtionalRules.AdditionalRule> additionalRule;

         public List<BookingRulesType.BookingRule.AddtionalRules.AdditionalRule> getAdditionalRule() {
            if (this.additionalRule == null) {
               this.additionalRule = new ArrayList<>();
            }

            return this.additionalRule;
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "")
         public static class AdditionalRule {
            @XmlAttribute(name = "AdditionalRule")
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            protected String additionalRule;

            public String getAdditionalRule() {
               return this.additionalRule;
            }

            public void setAdditionalRule(String value) {
               this.additionalRule = value;
            }
         }
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class CheckoutCharge {
         @XmlAttribute(name = "Percent")
         protected BigDecimal percent;
         @XmlAttribute(name = "Type")
         @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
         protected String type;
         @XmlAttribute(name = "NmbrOfNights")
         @XmlSchemaType(name = "nonNegativeInteger")
         protected BigInteger nmbrOfNights;
         @XmlAttribute(name = "ExistsCode")
         protected String existsCode;
         @XmlAttribute(name = "BalanceOfStayInd")
         protected Boolean balanceOfStayInd;
         @XmlAttribute(name = "CodeDetail")
         protected String codeDetail;
         @XmlAttribute(name = "Removal")
         protected Boolean removal;
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

         public String getType() {
            return this.type;
         }

         public void setType(String value) {
            this.type = value;
         }

         public BigInteger getNmbrOfNights() {
            return this.nmbrOfNights;
         }

         public void setNmbrOfNights(BigInteger value) {
            this.nmbrOfNights = value;
         }

         public String getExistsCode() {
            return this.existsCode;
         }

         public void setExistsCode(String value) {
            this.existsCode = value;
         }

         public Boolean isBalanceOfStayInd() {
            return this.balanceOfStayInd;
         }

         public void setBalanceOfStayInd(Boolean value) {
            this.balanceOfStayInd = value;
         }

         public String getCodeDetail() {
            return this.codeDetail;
         }

         public void setCodeDetail(String value) {
            this.codeDetail = value;
         }

         public Boolean isRemoval() {
            return this.removal;
         }

         public void setRemoval(Boolean value) {
            this.removal = value;
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
      public static class RestrictionStatus {
         @XmlAttribute(name = "Restriction")
         protected List<String> restriction;
         @XmlAttribute(name = "Status")
         protected List<String> status;
         @XmlAttribute(name = "SellThroughOpenIndicator")
         protected Boolean sellThroughOpenIndicator;

         public List<String> getRestriction() {
            if (this.restriction == null) {
               this.restriction = new ArrayList<>();
            }

            return this.restriction;
         }

         public List<String> getStatus() {
            if (this.status == null) {
               this.status = new ArrayList<>();
            }

            return this.status;
         }

         public Boolean isSellThroughOpenIndicator() {
            return this.sellThroughOpenIndicator;
         }

         public void setSellThroughOpenIndicator(Boolean value) {
            this.sellThroughOpenIndicator = value;
         }
      }
   }
}
