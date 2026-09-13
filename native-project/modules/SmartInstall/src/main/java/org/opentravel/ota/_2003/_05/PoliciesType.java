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
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PoliciesType", propOrder = "policy")
@XmlSeeAlso(HotelDescriptiveContentType.Policies.class)
public class PoliciesType {
   @XmlElement(name = "Policy", required = true)
   protected List<PoliciesType.Policy> policy;

   public List<PoliciesType.Policy> getPolicy() {
      if (this.policy == null) {
         this.policy = new ArrayList<>();
      }

      return this.policy;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(
      name = "",
      propOrder = {
            "cancelPolicy",
            "guaranteePaymentPolicy",
            "policyInfoCodes",
            "checkoutCharges",
            "policyInfo",
            "taxPolicies",
            "petsPolicies",
            "stayRequirements",
            "commissionPolicy",
            "feePolicies",
            "ratePolicies"
      }
   )
   public static class Policy {
      @XmlElement(name = "CancelPolicy")
      protected CancelPenaltiesType cancelPolicy;
      @XmlElement(name = "GuaranteePaymentPolicy")
      protected PoliciesType.Policy.GuaranteePaymentPolicy guaranteePaymentPolicy;
      @XmlElement(name = "PolicyInfoCodes")
      protected PoliciesType.Policy.PolicyInfoCodes policyInfoCodes;
      @XmlElement(name = "CheckoutCharges")
      protected PoliciesType.Policy.CheckoutCharges checkoutCharges;
      @XmlElement(name = "PolicyInfo")
      protected PoliciesType.Policy.PolicyInfo policyInfo;
      @XmlElement(name = "TaxPolicies")
      protected PoliciesType.Policy.TaxPolicies taxPolicies;
      @XmlElement(name = "PetsPolicies")
      protected PoliciesType.Policy.PetsPolicies petsPolicies;
      @XmlElement(name = "StayRequirements")
      protected PoliciesType.Policy.StayRequirements stayRequirements;
      @XmlElement(name = "CommissionPolicy")
      protected PoliciesType.Policy.CommissionPolicy commissionPolicy;
      @XmlElement(name = "FeePolicies")
      protected PoliciesType.Policy.FeePolicies feePolicies;
      @XmlElement(name = "RatePolicies")
      protected PoliciesType.Policy.RatePolicies ratePolicies;
      @XmlAttribute(name = "DefaultValidBookingMinOffset")
      @XmlSchemaType(name = "nonNegativeInteger")
      protected BigInteger defaultValidBookingMinOffset;
      @XmlAttribute(name = "Code")
      protected String code;
      @XmlAttribute(name = "LastUpdated")
      @XmlSchemaType(name = "dateTime")
      protected XMLGregorianCalendar lastUpdated;
      @XmlAttribute(name = "Start")
      protected String start;
      @XmlAttribute(name = "Duration")
      protected String duration;
      @XmlAttribute(name = "End")
      protected String end;
      @XmlAttribute(name = "Mon")
      protected Boolean mon;
      @XmlAttribute(name = "Tue")
      protected Boolean tue;
      @XmlAttribute(name = "Weds")
      protected Boolean weds;
      @XmlAttribute(name = "Thur")
      protected Boolean thur;
      @XmlAttribute(name = "Fri")
      protected Boolean fri;
      @XmlAttribute(name = "Sat")
      protected Boolean sat;
      @XmlAttribute(name = "Sun")
      protected Boolean sun;
      @XmlAttribute(name = "CodeDetail")
      protected String codeDetail;
      @XmlAttribute(name = "Removal")
      protected Boolean removal;

      public CancelPenaltiesType getCancelPolicy() {
         return this.cancelPolicy;
      }

      public void setCancelPolicy(CancelPenaltiesType value) {
         this.cancelPolicy = value;
      }

      public PoliciesType.Policy.GuaranteePaymentPolicy getGuaranteePaymentPolicy() {
         return this.guaranteePaymentPolicy;
      }

      public void setGuaranteePaymentPolicy(PoliciesType.Policy.GuaranteePaymentPolicy value) {
         this.guaranteePaymentPolicy = value;
      }

      public PoliciesType.Policy.PolicyInfoCodes getPolicyInfoCodes() {
         return this.policyInfoCodes;
      }

      public void setPolicyInfoCodes(PoliciesType.Policy.PolicyInfoCodes value) {
         this.policyInfoCodes = value;
      }

      public PoliciesType.Policy.CheckoutCharges getCheckoutCharges() {
         return this.checkoutCharges;
      }

      public void setCheckoutCharges(PoliciesType.Policy.CheckoutCharges value) {
         this.checkoutCharges = value;
      }

      public PoliciesType.Policy.PolicyInfo getPolicyInfo() {
         return this.policyInfo;
      }

      public void setPolicyInfo(PoliciesType.Policy.PolicyInfo value) {
         this.policyInfo = value;
      }

      public PoliciesType.Policy.TaxPolicies getTaxPolicies() {
         return this.taxPolicies;
      }

      public void setTaxPolicies(PoliciesType.Policy.TaxPolicies value) {
         this.taxPolicies = value;
      }

      public PoliciesType.Policy.PetsPolicies getPetsPolicies() {
         return this.petsPolicies;
      }

      public void setPetsPolicies(PoliciesType.Policy.PetsPolicies value) {
         this.petsPolicies = value;
      }

      public PoliciesType.Policy.StayRequirements getStayRequirements() {
         return this.stayRequirements;
      }

      public void setStayRequirements(PoliciesType.Policy.StayRequirements value) {
         this.stayRequirements = value;
      }

      public PoliciesType.Policy.CommissionPolicy getCommissionPolicy() {
         return this.commissionPolicy;
      }

      public void setCommissionPolicy(PoliciesType.Policy.CommissionPolicy value) {
         this.commissionPolicy = value;
      }

      public PoliciesType.Policy.FeePolicies getFeePolicies() {
         return this.feePolicies;
      }

      public void setFeePolicies(PoliciesType.Policy.FeePolicies value) {
         this.feePolicies = value;
      }

      public PoliciesType.Policy.RatePolicies getRatePolicies() {
         return this.ratePolicies;
      }

      public void setRatePolicies(PoliciesType.Policy.RatePolicies value) {
         this.ratePolicies = value;
      }

      public BigInteger getDefaultValidBookingMinOffset() {
         return this.defaultValidBookingMinOffset;
      }

      public void setDefaultValidBookingMinOffset(BigInteger value) {
         this.defaultValidBookingMinOffset = value;
      }

      public String getCode() {
         return this.code;
      }

      public void setCode(String value) {
         this.code = value;
      }

      public XMLGregorianCalendar getLastUpdated() {
         return this.lastUpdated;
      }

      public void setLastUpdated(XMLGregorianCalendar value) {
         this.lastUpdated = value;
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

      public Boolean isMon() {
         return this.mon;
      }

      public void setMon(Boolean value) {
         this.mon = value;
      }

      public Boolean isTue() {
         return this.tue;
      }

      public void setTue(Boolean value) {
         this.tue = value;
      }

      public Boolean isWeds() {
         return this.weds;
      }

      public void setWeds(Boolean value) {
         this.weds = value;
      }

      public Boolean isThur() {
         return this.thur;
      }

      public void setThur(Boolean value) {
         this.thur = value;
      }

      public Boolean isFri() {
         return this.fri;
      }

      public void setFri(Boolean value) {
         this.fri = value;
      }

      public Boolean isSat() {
         return this.sat;
      }

      public void setSat(Boolean value) {
         this.sat = value;
      }

      public Boolean isSun() {
         return this.sun;
      }

      public void setSun(Boolean value) {
         this.sun = value;
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

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = "checkoutCharge")
      public static class CheckoutCharges {
         @XmlElement(name = "CheckoutCharge", required = true)
         protected List<PoliciesType.Policy.CheckoutCharges.CheckoutCharge> checkoutCharge;

         public List<PoliciesType.Policy.CheckoutCharges.CheckoutCharge> getCheckoutCharge() {
            if (this.checkoutCharge == null) {
               this.checkoutCharge = new ArrayList<>();
            }

            return this.checkoutCharge;
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "", propOrder = "description")
         public static class CheckoutCharge {
            @XmlElement(name = "Description")
            protected List<ParagraphType> description;
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

            public List<ParagraphType> getDescription() {
               if (this.description == null) {
                  this.description = new ArrayList<>();
               }

               return this.description;
            }

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
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = "paymentCompany")
      public static class CommissionPolicy extends FeeType {
         @XmlElement(name = "PaymentCompany")
         protected List<PoliciesType.Policy.CommissionPolicy.PaymentCompany> paymentCompany;
         @XmlAttribute(name = "CommissionApplicability")
         @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
         protected String commissionApplicability;

         public List<PoliciesType.Policy.CommissionPolicy.PaymentCompany> getPaymentCompany() {
            if (this.paymentCompany == null) {
               this.paymentCompany = new ArrayList<>();
            }

            return this.paymentCompany;
         }

         public String getCommissionApplicability() {
            return this.commissionApplicability;
         }

         public void setCommissionApplicability(String value) {
            this.commissionApplicability = value;
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "")
         public static class PaymentCompany {
            @XmlAttribute(name = "Name")
            protected String name;

            public String getName() {
               return this.name;
            }

            public void setName(String value) {
               this.name = value;
            }
         }
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = "feePolicy")
      public static class FeePolicies {
         @XmlElement(name = "FeePolicy", required = true)
         protected List<FeeType> feePolicy;

         public List<FeeType> getFeePolicy() {
            if (this.feePolicy == null) {
               this.feePolicy = new ArrayList<>();
            }

            return this.feePolicy;
         }
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class GuaranteePaymentPolicy extends RequiredPaymentsType {
         @XmlAttribute(name = "Removal")
         protected Boolean removal;

         public Boolean isRemoval() {
            return this.removal;
         }

         public void setRemoval(Boolean value) {
            this.removal = value;
         }
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = "petsPolicy")
      public static class PetsPolicies {
         @XmlElement(name = "PetsPolicy")
         protected List<PoliciesType.Policy.PetsPolicies.PetsPolicy> petsPolicy;
         @XmlAttribute(name = "PetsAllowedCode")
         protected String petsAllowedCode;

         public List<PoliciesType.Policy.PetsPolicies.PetsPolicy> getPetsPolicy() {
            if (this.petsPolicy == null) {
               this.petsPolicy = new ArrayList<>();
            }

            return this.petsPolicy;
         }

         public String getPetsAllowedCode() {
            return this.petsAllowedCode;
         }

         public void setPetsAllowedCode(String value) {
            this.petsAllowedCode = value;
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "", propOrder = "description")
         public static class PetsPolicy {
            @XmlElement(name = "Description")
            protected List<ParagraphType> description;
            @XmlAttribute(name = "PetsPolicyCode")
            protected String petsPolicyCode;
            @XmlAttribute(name = "MaxPetQuantity")
            protected Integer maxPetQuantity;
            @XmlAttribute(name = "RefundableDeposit")
            protected BigDecimal refundableDeposit;
            @XmlAttribute(name = "NonRefundableFee")
            protected BigDecimal nonRefundableFee;
            @XmlAttribute(name = "ChargeCode")
            protected String chargeCode;
            @XmlAttribute(name = "RestrictionInd")
            protected Boolean restrictionInd;
            @XmlAttribute(name = "MinUnitOfMeasureQuantity")
            protected BigDecimal minUnitOfMeasureQuantity;
            @XmlAttribute(name = "CurrencyCode")
            protected String currencyCode;
            @XmlAttribute(name = "DecimalPlaces")
            @XmlSchemaType(name = "nonNegativeInteger")
            protected BigInteger decimalPlaces;
            @XmlAttribute(name = "UnitOfMeasureQuantity")
            protected BigDecimal unitOfMeasureQuantity;
            @XmlAttribute(name = "UnitOfMeasure")
            protected String unitOfMeasure;
            @XmlAttribute(name = "UnitOfMeasureCode")
            protected String unitOfMeasureCode;

            public List<ParagraphType> getDescription() {
               if (this.description == null) {
                  this.description = new ArrayList<>();
               }

               return this.description;
            }

            public String getPetsPolicyCode() {
               return this.petsPolicyCode;
            }

            public void setPetsPolicyCode(String value) {
               this.petsPolicyCode = value;
            }

            public Integer getMaxPetQuantity() {
               return this.maxPetQuantity;
            }

            public void setMaxPetQuantity(Integer value) {
               this.maxPetQuantity = value;
            }

            public BigDecimal getRefundableDeposit() {
               return this.refundableDeposit;
            }

            public void setRefundableDeposit(BigDecimal value) {
               this.refundableDeposit = value;
            }

            public BigDecimal getNonRefundableFee() {
               return this.nonRefundableFee;
            }

            public void setNonRefundableFee(BigDecimal value) {
               this.nonRefundableFee = value;
            }

            public String getChargeCode() {
               return this.chargeCode;
            }

            public void setChargeCode(String value) {
               this.chargeCode = value;
            }

            public Boolean isRestrictionInd() {
               return this.restrictionInd;
            }

            public void setRestrictionInd(Boolean value) {
               this.restrictionInd = value;
            }

            public BigDecimal getMinUnitOfMeasureQuantity() {
               return this.minUnitOfMeasureQuantity;
            }

            public void setMinUnitOfMeasureQuantity(BigDecimal value) {
               this.minUnitOfMeasureQuantity = value;
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

            public BigDecimal getUnitOfMeasureQuantity() {
               return this.unitOfMeasureQuantity;
            }

            public void setUnitOfMeasureQuantity(BigDecimal value) {
               this.unitOfMeasureQuantity = value;
            }

            public String getUnitOfMeasure() {
               return this.unitOfMeasure;
            }

            public void setUnitOfMeasure(String value) {
               this.unitOfMeasure = value;
            }

            public String getUnitOfMeasureCode() {
               return this.unitOfMeasureCode;
            }

            public void setUnitOfMeasureCode(String value) {
               this.unitOfMeasureCode = value;
            }
         }
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = "description")
      public static class PolicyInfo {
         @XmlElement(name = "Description")
         protected ParagraphType description;
         @XmlAttribute(name = "CheckInTime")
         protected String checkInTime;
         @XmlAttribute(name = "CheckOutTime")
         protected String checkOutTime;
         @XmlAttribute(name = "MinGuestAge")
         @XmlSchemaType(name = "nonNegativeInteger")
         protected BigInteger minGuestAge;
         @XmlAttribute(name = "MinRecommendedGuestAge")
         @XmlSchemaType(name = "nonNegativeInteger")
         protected BigInteger minRecommendedGuestAge;
         @XmlAttribute(name = "UsualStayFreeCutoffAge")
         @XmlSchemaType(name = "nonNegativeInteger")
         protected BigInteger usualStayFreeCutoffAge;
         @XmlAttribute(name = "UsualStayFreeChildPerAdult")
         @XmlSchemaType(name = "nonNegativeInteger")
         protected BigInteger usualStayFreeChildPerAdult;
         @XmlAttribute(name = "TotalGuestCount")
         @XmlSchemaType(name = "nonNegativeInteger")
         protected BigInteger totalGuestCount;
         @XmlAttribute(name = "DefaultTaxServiceInclusive")
         protected Boolean defaultTaxServiceInclusive;
         @XmlAttribute(name = "KidsStayFree")
         protected Boolean kidsStayFree;
         @XmlAttribute(name = "MaxChildAge")
         protected Integer maxChildAge;
         @XmlAttribute(name = "InternetGuaranteeRequiredInd")
         protected Boolean internetGuaranteeRequiredInd;
         @XmlAttribute(name = "AcceptedGuestType")
         @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
         protected String acceptedGuestType;

         public ParagraphType getDescription() {
            return this.description;
         }

         public void setDescription(ParagraphType value) {
            this.description = value;
         }

         public String getCheckInTime() {
            return this.checkInTime;
         }

         public void setCheckInTime(String value) {
            this.checkInTime = value;
         }

         public String getCheckOutTime() {
            return this.checkOutTime;
         }

         public void setCheckOutTime(String value) {
            this.checkOutTime = value;
         }

         public BigInteger getMinGuestAge() {
            return this.minGuestAge;
         }

         public void setMinGuestAge(BigInteger value) {
            this.minGuestAge = value;
         }

         public BigInteger getMinRecommendedGuestAge() {
            return this.minRecommendedGuestAge;
         }

         public void setMinRecommendedGuestAge(BigInteger value) {
            this.minRecommendedGuestAge = value;
         }

         public BigInteger getUsualStayFreeCutoffAge() {
            return this.usualStayFreeCutoffAge;
         }

         public void setUsualStayFreeCutoffAge(BigInteger value) {
            this.usualStayFreeCutoffAge = value;
         }

         public BigInteger getUsualStayFreeChildPerAdult() {
            return this.usualStayFreeChildPerAdult;
         }

         public void setUsualStayFreeChildPerAdult(BigInteger value) {
            this.usualStayFreeChildPerAdult = value;
         }

         public BigInteger getTotalGuestCount() {
            return this.totalGuestCount;
         }

         public void setTotalGuestCount(BigInteger value) {
            this.totalGuestCount = value;
         }

         public Boolean isDefaultTaxServiceInclusive() {
            return this.defaultTaxServiceInclusive;
         }

         public void setDefaultTaxServiceInclusive(Boolean value) {
            this.defaultTaxServiceInclusive = value;
         }

         public Boolean isKidsStayFree() {
            return this.kidsStayFree;
         }

         public void setKidsStayFree(Boolean value) {
            this.kidsStayFree = value;
         }

         public Integer getMaxChildAge() {
            return this.maxChildAge;
         }

         public void setMaxChildAge(Integer value) {
            this.maxChildAge = value;
         }

         public Boolean isInternetGuaranteeRequiredInd() {
            return this.internetGuaranteeRequiredInd;
         }

         public void setInternetGuaranteeRequiredInd(Boolean value) {
            this.internetGuaranteeRequiredInd = value;
         }

         public String getAcceptedGuestType() {
            return this.acceptedGuestType;
         }

         public void setAcceptedGuestType(String value) {
            this.acceptedGuestType = value;
         }
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = "policyInfoCode")
      public static class PolicyInfoCodes {
         @XmlElement(name = "PolicyInfoCode", required = true)
         protected List<PoliciesType.Policy.PolicyInfoCodes.PolicyInfoCode> policyInfoCode;

         public List<PoliciesType.Policy.PolicyInfoCodes.PolicyInfoCode> getPolicyInfoCode() {
            if (this.policyInfoCode == null) {
               this.policyInfoCode = new ArrayList<>();
            }

            return this.policyInfoCode;
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "", propOrder = "description")
         public static class PolicyInfoCode {
            @XmlElement(name = "Description")
            protected List<ParagraphType> description;
            @XmlAttribute(name = "Name")
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            protected String name;
            @XmlAttribute(name = "ExistsCode")
            protected String existsCode;
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
            @XmlAttribute(name = "CodeDetail")
            protected String codeDetail;
            @XmlAttribute(name = "Removal")
            protected Boolean removal;

            public List<ParagraphType> getDescription() {
               if (this.description == null) {
                  this.description = new ArrayList<>();
               }

               return this.description;
            }

            public String getName() {
               return this.name;
            }

            public void setName(String value) {
               this.name = value;
            }

            public String getExistsCode() {
               return this.existsCode;
            }

            public void setExistsCode(String value) {
               this.existsCode = value;
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
         }
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = "ratePolicy")
      public static class RatePolicies {
         @XmlElement(name = "RatePolicy", required = true)
         protected List<PoliciesType.Policy.RatePolicies.RatePolicy> ratePolicy;

         public List<PoliciesType.Policy.RatePolicies.RatePolicy> getRatePolicy() {
            if (this.ratePolicy == null) {
               this.ratePolicy = new ArrayList<>();
            }

            return this.ratePolicy;
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "", propOrder = {"type", "description"})
         public static class RatePolicy {
            @XmlElement(name = "Type")
            protected PoliciesType.Policy.RatePolicies.RatePolicy.Type type;
            @XmlElement(name = "Description")
            protected List<ParagraphType> description;
            @XmlAttribute(name = "SubjectToChangeInd")
            protected Boolean subjectToChangeInd;
            @XmlAttribute(name = "ID_RequiredInd")
            protected Boolean idRequiredInd;
            @XmlAttribute(name = "GuestType")
            protected List<String> guestType;
            @XmlAttribute(name = "MinRoomNightCommitment")
            @XmlSchemaType(name = "positiveInteger")
            protected BigInteger minRoomNightCommitment;
            @XmlAttribute(name = "RateOfferType")
            protected String rateOfferType;

            public PoliciesType.Policy.RatePolicies.RatePolicy.Type getType() {
               return this.type;
            }

            public void setType(PoliciesType.Policy.RatePolicies.RatePolicy.Type value) {
               this.type = value;
            }

            public List<ParagraphType> getDescription() {
               if (this.description == null) {
                  this.description = new ArrayList<>();
               }

               return this.description;
            }

            public Boolean isSubjectToChangeInd() {
               return this.subjectToChangeInd;
            }

            public void setSubjectToChangeInd(Boolean value) {
               this.subjectToChangeInd = value;
            }

            public Boolean isIDRequiredInd() {
               return this.idRequiredInd;
            }

            public void setIDRequiredInd(Boolean value) {
               this.idRequiredInd = value;
            }

            public List<String> getGuestType() {
               if (this.guestType == null) {
                  this.guestType = new ArrayList<>();
               }

               return this.guestType;
            }

            public BigInteger getMinRoomNightCommitment() {
               return this.minRoomNightCommitment;
            }

            public void setMinRoomNightCommitment(BigInteger value) {
               this.minRoomNightCommitment = value;
            }

            public String getRateOfferType() {
               return this.rateOfferType;
            }

            public void setRateOfferType(String value) {
               this.rateOfferType = value;
            }

            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = "value")
            public static class Type {
               @XmlValue
               protected List<String> value;
               @XmlAttribute(name = "Extension")
               protected String extension;

               public List<String> getValue() {
                  if (this.value == null) {
                     this.value = new ArrayList<>();
                  }

                  return this.value;
               }

               public String getExtension() {
                  return this.extension;
               }

               public void setExtension(String value) {
                  this.extension = value;
               }
            }
         }
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = "stayRequirement")
      public static class StayRequirements {
         @XmlElement(name = "StayRequirement", required = true)
         protected List<PoliciesType.Policy.StayRequirements.StayRequirement> stayRequirement;

         public List<PoliciesType.Policy.StayRequirements.StayRequirement> getStayRequirement() {
            if (this.stayRequirement == null) {
               this.stayRequirement = new ArrayList<>();
            }

            return this.stayRequirement;
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "", propOrder = "description")
         public static class StayRequirement {
            @XmlElement(name = "Description")
            protected ParagraphType description;
            @XmlAttribute(name = "MinLOS")
            @XmlSchemaType(name = "nonNegativeInteger")
            protected BigInteger minLOS;
            @XmlAttribute(name = "MaxLOS")
            @XmlSchemaType(name = "nonNegativeInteger")
            protected BigInteger maxLOS;
            @XmlAttribute(name = "StayContext")
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            protected String stayContext;
            @XmlAttribute(name = "Mon")
            protected Boolean mon;
            @XmlAttribute(name = "Tue")
            protected Boolean tue;
            @XmlAttribute(name = "Weds")
            protected Boolean weds;
            @XmlAttribute(name = "Thur")
            protected Boolean thur;
            @XmlAttribute(name = "Fri")
            protected Boolean fri;
            @XmlAttribute(name = "Sat")
            protected Boolean sat;
            @XmlAttribute(name = "Sun")
            protected Boolean sun;

            public ParagraphType getDescription() {
               return this.description;
            }

            public void setDescription(ParagraphType value) {
               this.description = value;
            }

            public BigInteger getMinLOS() {
               return this.minLOS;
            }

            public void setMinLOS(BigInteger value) {
               this.minLOS = value;
            }

            public BigInteger getMaxLOS() {
               return this.maxLOS;
            }

            public void setMaxLOS(BigInteger value) {
               this.maxLOS = value;
            }

            public String getStayContext() {
               return this.stayContext;
            }

            public void setStayContext(String value) {
               this.stayContext = value;
            }

            public Boolean isMon() {
               return this.mon;
            }

            public void setMon(Boolean value) {
               this.mon = value;
            }

            public Boolean isTue() {
               return this.tue;
            }

            public void setTue(Boolean value) {
               this.tue = value;
            }

            public Boolean isWeds() {
               return this.weds;
            }

            public void setWeds(Boolean value) {
               this.weds = value;
            }

            public Boolean isThur() {
               return this.thur;
            }

            public void setThur(Boolean value) {
               this.thur = value;
            }

            public Boolean isFri() {
               return this.fri;
            }

            public void setFri(Boolean value) {
               this.fri = value;
            }

            public Boolean isSat() {
               return this.sat;
            }

            public void setSat(Boolean value) {
               this.sat = value;
            }

            public Boolean isSun() {
               return this.sun;
            }

            public void setSun(Boolean value) {
               this.sun = value;
            }
         }
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = "taxPolicy")
      public static class TaxPolicies {
         @XmlElement(name = "TaxPolicy")
         protected List<PoliciesType.Policy.TaxPolicies.TaxPolicy> taxPolicy;

         public List<PoliciesType.Policy.TaxPolicies.TaxPolicy> getTaxPolicy() {
            if (this.taxPolicy == null) {
               this.taxPolicy = new ArrayList<>();
            }

            return this.taxPolicy;
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "")
         public static class TaxPolicy extends TaxType {
            @XmlAttribute(name = "NightsForTaxExemptionQuantity")
            @XmlSchemaType(name = "nonNegativeInteger")
            protected BigInteger nightsForTaxExemptionQuantity;
            @XmlAttribute(name = "TaxableNightsQuantity")
            @XmlSchemaType(name = "nonNegativeInteger")
            protected BigInteger taxableNightsQuantity;

            public BigInteger getNightsForTaxExemptionQuantity() {
               return this.nightsForTaxExemptionQuantity;
            }

            public void setNightsForTaxExemptionQuantity(BigInteger value) {
               this.nightsForTaxExemptionQuantity = value;
            }

            public BigInteger getTaxableNightsQuantity() {
               return this.taxableNightsQuantity;
            }

            public void setTaxableNightsQuantity(BigInteger value) {
               this.taxableNightsQuantity = value;
            }
         }
      }
   }
}
