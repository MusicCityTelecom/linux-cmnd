/*
 * Decompiled with CFR 0.152.
 */
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
import org.opentravel.ota._2003._05.AmountDeterminationType;
import org.opentravel.ota._2003._05.CancelPenaltiesType;
import org.opentravel.ota._2003._05.DayOfWeekType;
import org.opentravel.ota._2003._05.FeesType;
import org.opentravel.ota._2003._05.GuaranteeType;
import org.opentravel.ota._2003._05.HotelAdditionalChargesType;
import org.opentravel.ota._2003._05.HotelRatePlanType;
import org.opentravel.ota._2003._05.InvBlockRoomType;
import org.opentravel.ota._2003._05.ParagraphType;
import org.opentravel.ota._2003._05.RateAmountMessageType;
import org.opentravel.ota._2003._05.RequiredPaymentsType;
import org.opentravel.ota._2003._05.TaxesType;
import org.opentravel.ota._2003._05.TimeUnitType;
import org.opentravel.ota._2003._05.TotalType;
import org.opentravel.ota._2003._05.UniqueIDType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="RateUploadType", propOrder={"baseByGuestAmts", "additionalGuestAmounts", "fees", "guaranteePolicies", "cancelPolicies", "paymentPolicies", "rateDescription", "uniqueID", "mealsIncluded", "additionalCharges"})
@XmlSeeAlso(value={InvBlockRoomType.RatePlans.RatePlan.class, RateAmountMessageType.Rates.Rate.class, HotelRatePlanType.Rates.Rate.class})
public class RateUploadType {
    @XmlElement(name="BaseByGuestAmts")
    protected BaseByGuestAmts baseByGuestAmts;
    @XmlElement(name="AdditionalGuestAmounts")
    protected AdditionalGuestAmounts additionalGuestAmounts;
    @XmlElement(name="Fees")
    protected FeesType fees;
    @XmlElement(name="GuaranteePolicies")
    protected GuaranteePolicies guaranteePolicies;
    @XmlElement(name="CancelPolicies")
    protected CancelPenaltiesType cancelPolicies;
    @XmlElement(name="PaymentPolicies")
    protected RequiredPaymentsType paymentPolicies;
    @XmlElement(name="RateDescription")
    protected ParagraphType rateDescription;
    @XmlElement(name="UniqueID")
    protected UniqueIDType uniqueID;
    @XmlElement(name="MealsIncluded")
    protected MealsIncluded mealsIncluded;
    @XmlElement(name="AdditionalCharges")
    protected HotelAdditionalChargesType additionalCharges;
    @XmlAttribute(name="NumberOfUnits")
    protected BigInteger numberOfUnits;
    @XmlAttribute(name="RateTimeUnit")
    protected TimeUnitType rateTimeUnit;
    @XmlAttribute(name="UnitMultiplier")
    protected Integer unitMultiplier;
    @XmlAttribute(name="MinGuestApplicable")
    protected Integer minGuestApplicable;
    @XmlAttribute(name="MaxGuestApplicable")
    protected Integer maxGuestApplicable;
    @XmlAttribute(name="MinLOS")
    protected String minLOS;
    @XmlAttribute(name="MaxLOS")
    protected String maxLOS;
    @XmlAttribute(name="StayOverDate")
    protected DayOfWeekType stayOverDate;
    @XmlAttribute(name="RateTier")
    protected String rateTier;
    @XmlAttribute(name="Start")
    protected String start;
    @XmlAttribute(name="Duration")
    protected String duration;
    @XmlAttribute(name="End")
    protected String end;
    @XmlAttribute(name="Mon")
    protected Boolean mon;
    @XmlAttribute(name="Tue")
    protected Boolean tue;
    @XmlAttribute(name="Weds")
    protected Boolean weds;
    @XmlAttribute(name="Thur")
    protected Boolean thur;
    @XmlAttribute(name="Fri")
    protected Boolean fri;
    @XmlAttribute(name="Sat")
    protected Boolean sat;
    @XmlAttribute(name="Sun")
    protected Boolean sun;
    @XmlAttribute(name="CurrencyCode")
    protected String currencyCode;
    @XmlAttribute(name="DecimalPlaces")
    @XmlSchemaType(name="nonNegativeInteger")
    protected BigInteger decimalPlaces;
    @XmlAttribute(name="AgeQualifyingCode")
    protected String ageQualifyingCode;
    @XmlAttribute(name="MinAge")
    protected Integer minAge;
    @XmlAttribute(name="MaxAge")
    protected Integer maxAge;
    @XmlAttribute(name="AgeTimeUnit")
    protected TimeUnitType ageTimeUnit;
    @XmlAttribute(name="AgeBucket")
    protected String ageBucket;

    public BaseByGuestAmts getBaseByGuestAmts() {
        return this.baseByGuestAmts;
    }

    public void setBaseByGuestAmts(BaseByGuestAmts value) {
        this.baseByGuestAmts = value;
    }

    public AdditionalGuestAmounts getAdditionalGuestAmounts() {
        return this.additionalGuestAmounts;
    }

    public void setAdditionalGuestAmounts(AdditionalGuestAmounts value) {
        this.additionalGuestAmounts = value;
    }

    public FeesType getFees() {
        return this.fees;
    }

    public void setFees(FeesType value) {
        this.fees = value;
    }

    public GuaranteePolicies getGuaranteePolicies() {
        return this.guaranteePolicies;
    }

    public void setGuaranteePolicies(GuaranteePolicies value) {
        this.guaranteePolicies = value;
    }

    public CancelPenaltiesType getCancelPolicies() {
        return this.cancelPolicies;
    }

    public void setCancelPolicies(CancelPenaltiesType value) {
        this.cancelPolicies = value;
    }

    public RequiredPaymentsType getPaymentPolicies() {
        return this.paymentPolicies;
    }

    public void setPaymentPolicies(RequiredPaymentsType value) {
        this.paymentPolicies = value;
    }

    public ParagraphType getRateDescription() {
        return this.rateDescription;
    }

    public void setRateDescription(ParagraphType value) {
        this.rateDescription = value;
    }

    public UniqueIDType getUniqueID() {
        return this.uniqueID;
    }

    public void setUniqueID(UniqueIDType value) {
        this.uniqueID = value;
    }

    public MealsIncluded getMealsIncluded() {
        return this.mealsIncluded;
    }

    public void setMealsIncluded(MealsIncluded value) {
        this.mealsIncluded = value;
    }

    public HotelAdditionalChargesType getAdditionalCharges() {
        return this.additionalCharges;
    }

    public void setAdditionalCharges(HotelAdditionalChargesType value) {
        this.additionalCharges = value;
    }

    public BigInteger getNumberOfUnits() {
        return this.numberOfUnits;
    }

    public void setNumberOfUnits(BigInteger value) {
        this.numberOfUnits = value;
    }

    public TimeUnitType getRateTimeUnit() {
        return this.rateTimeUnit;
    }

    public void setRateTimeUnit(TimeUnitType value) {
        this.rateTimeUnit = value;
    }

    public Integer getUnitMultiplier() {
        return this.unitMultiplier;
    }

    public void setUnitMultiplier(Integer value) {
        this.unitMultiplier = value;
    }

    public Integer getMinGuestApplicable() {
        return this.minGuestApplicable;
    }

    public void setMinGuestApplicable(Integer value) {
        this.minGuestApplicable = value;
    }

    public Integer getMaxGuestApplicable() {
        return this.maxGuestApplicable;
    }

    public void setMaxGuestApplicable(Integer value) {
        this.maxGuestApplicable = value;
    }

    public String getMinLOS() {
        return this.minLOS;
    }

    public void setMinLOS(String value) {
        this.minLOS = value;
    }

    public String getMaxLOS() {
        return this.maxLOS;
    }

    public void setMaxLOS(String value) {
        this.maxLOS = value;
    }

    public DayOfWeekType getStayOverDate() {
        return this.stayOverDate;
    }

    public void setStayOverDate(DayOfWeekType value) {
        this.stayOverDate = value;
    }

    public String getRateTier() {
        return this.rateTier;
    }

    public void setRateTier(String value) {
        this.rateTier = value;
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

    public String getAgeQualifyingCode() {
        return this.ageQualifyingCode;
    }

    public void setAgeQualifyingCode(String value) {
        this.ageQualifyingCode = value;
    }

    public Integer getMinAge() {
        return this.minAge;
    }

    public void setMinAge(Integer value) {
        this.minAge = value;
    }

    public Integer getMaxAge() {
        return this.maxAge;
    }

    public void setMaxAge(Integer value) {
        this.maxAge = value;
    }

    public TimeUnitType getAgeTimeUnit() {
        return this.ageTimeUnit;
    }

    public void setAgeTimeUnit(TimeUnitType value) {
        this.ageTimeUnit = value;
    }

    public String getAgeBucket() {
        return this.ageBucket;
    }

    public void setAgeBucket(String value) {
        this.ageBucket = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class MealsIncluded {
        @XmlAttribute(name="Breakfast")
        protected Boolean breakfast;
        @XmlAttribute(name="Lunch")
        protected Boolean lunch;
        @XmlAttribute(name="Dinner")
        protected Boolean dinner;
        @XmlAttribute(name="MealPlanIndicator")
        protected Boolean mealPlanIndicator;
        @XmlAttribute(name="MealPlanCodes")
        protected List<String> mealPlanCodes;

        public Boolean isBreakfast() {
            return this.breakfast;
        }

        public void setBreakfast(Boolean value) {
            this.breakfast = value;
        }

        public Boolean isLunch() {
            return this.lunch;
        }

        public void setLunch(Boolean value) {
            this.lunch = value;
        }

        public Boolean isDinner() {
            return this.dinner;
        }

        public void setDinner(Boolean value) {
            this.dinner = value;
        }

        public Boolean isMealPlanIndicator() {
            return this.mealPlanIndicator;
        }

        public void setMealPlanIndicator(Boolean value) {
            this.mealPlanIndicator = value;
        }

        public List<String> getMealPlanCodes() {
            if (this.mealPlanCodes == null) {
                this.mealPlanCodes = new ArrayList<String>();
            }
            return this.mealPlanCodes;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"guaranteePolicy"})
    public static class GuaranteePolicies {
        @XmlElement(name="GuaranteePolicy", required=true)
        protected List<GuaranteeType> guaranteePolicy;

        public List<GuaranteeType> getGuaranteePolicy() {
            if (this.guaranteePolicy == null) {
                this.guaranteePolicy = new ArrayList<GuaranteeType>();
            }
            return this.guaranteePolicy;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"baseByGuestAmt"})
    public static class BaseByGuestAmts {
        @XmlElement(name="BaseByGuestAmt", required=true)
        protected List<BaseByGuestAmt> baseByGuestAmt;

        public List<BaseByGuestAmt> getBaseByGuestAmt() {
            if (this.baseByGuestAmt == null) {
                this.baseByGuestAmt = new ArrayList<BaseByGuestAmt>();
            }
            return this.baseByGuestAmt;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="", propOrder={"numberOfGuestsDescription"})
        public static class BaseByGuestAmt
        extends TotalType {
            @XmlElement(name="NumberOfGuestsDescription")
            protected List<ParagraphType> numberOfGuestsDescription;
            @XmlAttribute(name="Code")
            protected String code;
            @XmlAttribute(name="NumberOfGuests")
            protected Integer numberOfGuests;
            @XmlAttribute(name="AgeQualifyingCode")
            protected String ageQualifyingCode;
            @XmlAttribute(name="MinAge")
            protected Integer minAge;
            @XmlAttribute(name="MaxAge")
            protected Integer maxAge;
            @XmlAttribute(name="AgeTimeUnit")
            protected TimeUnitType ageTimeUnit;
            @XmlAttribute(name="AgeBucket")
            protected String ageBucket;

            public List<ParagraphType> getNumberOfGuestsDescription() {
                if (this.numberOfGuestsDescription == null) {
                    this.numberOfGuestsDescription = new ArrayList<ParagraphType>();
                }
                return this.numberOfGuestsDescription;
            }

            public String getCode() {
                return this.code;
            }

            public void setCode(String value) {
                this.code = value;
            }

            public Integer getNumberOfGuests() {
                return this.numberOfGuests;
            }

            public void setNumberOfGuests(Integer value) {
                this.numberOfGuests = value;
            }

            public String getAgeQualifyingCode() {
                return this.ageQualifyingCode;
            }

            public void setAgeQualifyingCode(String value) {
                this.ageQualifyingCode = value;
            }

            public Integer getMinAge() {
                return this.minAge;
            }

            public void setMinAge(Integer value) {
                this.minAge = value;
            }

            public Integer getMaxAge() {
                return this.maxAge;
            }

            public void setMaxAge(Integer value) {
                this.maxAge = value;
            }

            public TimeUnitType getAgeTimeUnit() {
                return this.ageTimeUnit;
            }

            public void setAgeTimeUnit(TimeUnitType value) {
                this.ageTimeUnit = value;
            }

            public String getAgeBucket() {
                return this.ageBucket;
            }

            public void setAgeBucket(String value) {
                this.ageBucket = value;
            }
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"additionalGuestAmount"})
    public static class AdditionalGuestAmounts {
        @XmlElement(name="AdditionalGuestAmount", required=true)
        protected List<AdditionalGuestAmount> additionalGuestAmount;

        public List<AdditionalGuestAmount> getAdditionalGuestAmount() {
            if (this.additionalGuestAmount == null) {
                this.additionalGuestAmount = new ArrayList<AdditionalGuestAmount>();
            }
            return this.additionalGuestAmount;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="", propOrder={"taxes", "addlGuestAmtDescription"})
        public static class AdditionalGuestAmount {
            @XmlElement(name="Taxes")
            protected TaxesType taxes;
            @XmlElement(name="AddlGuestAmtDescription")
            protected List<ParagraphType> addlGuestAmtDescription;
            @XmlAttribute(name="TaxInclusive")
            protected Boolean taxInclusive;
            @XmlAttribute(name="MaxAdditionalGuests")
            protected Integer maxAdditionalGuests;
            @XmlAttribute(name="Type")
            protected AmountDeterminationType type;
            @XmlAttribute(name="Code")
            protected String code;
            @XmlAttribute(name="Percent")
            protected BigDecimal percent;
            @XmlAttribute(name="Amount")
            protected BigDecimal amount;
            @XmlAttribute(name="CurrencyCode")
            protected String currencyCode;
            @XmlAttribute(name="DecimalPlaces")
            @XmlSchemaType(name="nonNegativeInteger")
            protected BigInteger decimalPlaces;
            @XmlAttribute(name="AgeQualifyingCode")
            protected String ageQualifyingCode;
            @XmlAttribute(name="MinAge")
            protected Integer minAge;
            @XmlAttribute(name="MaxAge")
            protected Integer maxAge;
            @XmlAttribute(name="AgeTimeUnit")
            protected TimeUnitType ageTimeUnit;
            @XmlAttribute(name="AgeBucket")
            protected String ageBucket;

            public TaxesType getTaxes() {
                return this.taxes;
            }

            public void setTaxes(TaxesType value) {
                this.taxes = value;
            }

            public List<ParagraphType> getAddlGuestAmtDescription() {
                if (this.addlGuestAmtDescription == null) {
                    this.addlGuestAmtDescription = new ArrayList<ParagraphType>();
                }
                return this.addlGuestAmtDescription;
            }

            public Boolean isTaxInclusive() {
                return this.taxInclusive;
            }

            public void setTaxInclusive(Boolean value) {
                this.taxInclusive = value;
            }

            public Integer getMaxAdditionalGuests() {
                return this.maxAdditionalGuests;
            }

            public void setMaxAdditionalGuests(Integer value) {
                this.maxAdditionalGuests = value;
            }

            public AmountDeterminationType getType() {
                return this.type;
            }

            public void setType(AmountDeterminationType value) {
                this.type = value;
            }

            public String getCode() {
                return this.code;
            }

            public void setCode(String value) {
                this.code = value;
            }

            public BigDecimal getPercent() {
                return this.percent;
            }

            public void setPercent(BigDecimal value) {
                this.percent = value;
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

            public String getAgeQualifyingCode() {
                return this.ageQualifyingCode;
            }

            public void setAgeQualifyingCode(String value) {
                this.ageQualifyingCode = value;
            }

            public Integer getMinAge() {
                return this.minAge;
            }

            public void setMinAge(Integer value) {
                this.minAge = value;
            }

            public Integer getMaxAge() {
                return this.maxAge;
            }

            public void setMaxAge(Integer value) {
                this.maxAge = value;
            }

            public TimeUnitType getAgeTimeUnit() {
                return this.ageTimeUnit;
            }

            public void setAgeTimeUnit(TimeUnitType value) {
                this.ageTimeUnit = value;
            }

            public String getAgeBucket() {
                return this.ageBucket;
            }

            public void setAgeBucket(String value) {
                this.ageBucket = value;
            }
        }
    }
}

