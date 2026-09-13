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
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;
import org.opentravel.ota._2003._05.AdditionalGuestAmountType;
import org.opentravel.ota._2003._05.CancelPenaltiesType;
import org.opentravel.ota._2003._05.DayOfWeekType;
import org.opentravel.ota._2003._05.DiscountType;
import org.opentravel.ota._2003._05.FeesType;
import org.opentravel.ota._2003._05.HotelAdditionalChargesType;
import org.opentravel.ota._2003._05.ParagraphType;
import org.opentravel.ota._2003._05.RateType;
import org.opentravel.ota._2003._05.RequiredPaymentsType;
import org.opentravel.ota._2003._05.TimeUnitType;
import org.opentravel.ota._2003._05.TotalType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="AmountType", propOrder={"base", "additionalGuestAmounts", "fees", "cancelPolicies", "paymentPolicies", "discount", "total", "rateDescription", "additionalCharges"})
@XmlSeeAlso(value={RateType.Rate.class})
public class AmountType {
    @XmlElement(name="Base")
    protected TotalType base;
    @XmlElement(name="AdditionalGuestAmounts")
    protected AdditionalGuestAmounts additionalGuestAmounts;
    @XmlElement(name="Fees")
    protected FeesType fees;
    @XmlElement(name="CancelPolicies")
    protected CancelPenaltiesType cancelPolicies;
    @XmlElement(name="PaymentPolicies")
    protected RequiredPaymentsType paymentPolicies;
    @XmlElement(name="Discount")
    protected List<Discount> discount;
    @XmlElement(name="Total")
    protected TotalType total;
    @XmlElement(name="RateDescription")
    protected ParagraphType rateDescription;
    @XmlElement(name="AdditionalCharges")
    protected HotelAdditionalChargesType additionalCharges;
    @XmlAttribute(name="GuaranteedInd")
    protected Boolean guaranteedInd;
    @XmlAttribute(name="NumberOfUnits")
    protected BigInteger numberOfUnits;
    @XmlAttribute(name="RateTimeUnit")
    protected TimeUnitType rateTimeUnit;
    @XmlAttribute(name="UnitMultiplier")
    @XmlSchemaType(name="positiveInteger")
    protected BigInteger unitMultiplier;
    @XmlAttribute(name="MinGuestApplicable")
    @XmlSchemaType(name="positiveInteger")
    protected BigInteger minGuestApplicable;
    @XmlAttribute(name="MaxGuestApplicable")
    @XmlSchemaType(name="positiveInteger")
    protected BigInteger maxGuestApplicable;
    @XmlAttribute(name="MinLOS")
    @XmlSchemaType(name="nonNegativeInteger")
    protected BigInteger minLOS;
    @XmlAttribute(name="MaxLOS")
    @XmlSchemaType(name="nonNegativeInteger")
    protected BigInteger maxLOS;
    @XmlAttribute(name="StayOverDate")
    protected DayOfWeekType stayOverDate;
    @XmlAttribute(name="AlternateCurrencyInd")
    protected Boolean alternateCurrencyInd;
    @XmlAttribute(name="ChargeType")
    protected String chargeType;
    @XmlAttribute(name="QuoteID")
    protected String quoteID;
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
    @XmlAttribute(name="EffectiveDate")
    @XmlSchemaType(name="date")
    protected XMLGregorianCalendar effectiveDate;
    @XmlAttribute(name="ExpireDate")
    @XmlSchemaType(name="date")
    protected XMLGregorianCalendar expireDate;
    @XmlAttribute(name="ExpireDateExclusiveIndicator")
    protected Boolean expireDateExclusiveIndicator;

    public TotalType getBase() {
        return this.base;
    }

    public void setBase(TotalType value) {
        this.base = value;
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

    public List<Discount> getDiscount() {
        if (this.discount == null) {
            this.discount = new ArrayList<Discount>();
        }
        return this.discount;
    }

    public TotalType getTotal() {
        return this.total;
    }

    public void setTotal(TotalType value) {
        this.total = value;
    }

    public ParagraphType getRateDescription() {
        return this.rateDescription;
    }

    public void setRateDescription(ParagraphType value) {
        this.rateDescription = value;
    }

    public HotelAdditionalChargesType getAdditionalCharges() {
        return this.additionalCharges;
    }

    public void setAdditionalCharges(HotelAdditionalChargesType value) {
        this.additionalCharges = value;
    }

    public Boolean isGuaranteedInd() {
        return this.guaranteedInd;
    }

    public void setGuaranteedInd(Boolean value) {
        this.guaranteedInd = value;
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

    public BigInteger getUnitMultiplier() {
        return this.unitMultiplier;
    }

    public void setUnitMultiplier(BigInteger value) {
        this.unitMultiplier = value;
    }

    public BigInteger getMinGuestApplicable() {
        return this.minGuestApplicable;
    }

    public void setMinGuestApplicable(BigInteger value) {
        this.minGuestApplicable = value;
    }

    public BigInteger getMaxGuestApplicable() {
        return this.maxGuestApplicable;
    }

    public void setMaxGuestApplicable(BigInteger value) {
        this.maxGuestApplicable = value;
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

    public DayOfWeekType getStayOverDate() {
        return this.stayOverDate;
    }

    public void setStayOverDate(DayOfWeekType value) {
        this.stayOverDate = value;
    }

    public Boolean isAlternateCurrencyInd() {
        return this.alternateCurrencyInd;
    }

    public void setAlternateCurrencyInd(Boolean value) {
        this.alternateCurrencyInd = value;
    }

    public String getChargeType() {
        return this.chargeType;
    }

    public void setChargeType(String value) {
        this.chargeType = value;
    }

    public String getQuoteID() {
        return this.quoteID;
    }

    public void setQuoteID(String value) {
        this.quoteID = value;
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

    public XMLGregorianCalendar getEffectiveDate() {
        return this.effectiveDate;
    }

    public void setEffectiveDate(XMLGregorianCalendar value) {
        this.effectiveDate = value;
    }

    public XMLGregorianCalendar getExpireDate() {
        return this.expireDate;
    }

    public void setExpireDate(XMLGregorianCalendar value) {
        this.expireDate = value;
    }

    public Boolean isExpireDateExclusiveIndicator() {
        return this.expireDateExclusiveIndicator;
    }

    public void setExpireDateExclusiveIndicator(Boolean value) {
        this.expireDateExclusiveIndicator = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class Discount
    extends DiscountType {
        @XmlAttribute(name="AppliesTo")
        @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
        protected String appliesTo;
        @XmlAttribute(name="ItemRPH")
        protected String itemRPH;

        public String getAppliesTo() {
            return this.appliesTo;
        }

        public void setAppliesTo(String value) {
            this.appliesTo = value;
        }

        public String getItemRPH() {
            return this.itemRPH;
        }

        public void setItemRPH(String value) {
            this.itemRPH = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"additionalGuestAmount"})
    public static class AdditionalGuestAmounts {
        @XmlElement(name="AdditionalGuestAmount", required=true)
        protected List<AdditionalGuestAmountType> additionalGuestAmount;
        @XmlAttribute(name="AmountBeforeTax")
        protected BigDecimal amountBeforeTax;
        @XmlAttribute(name="AmountAfterTax")
        protected BigDecimal amountAfterTax;
        @XmlAttribute(name="CurrencyCode")
        protected String currencyCode;
        @XmlAttribute(name="DecimalPlaces")
        @XmlSchemaType(name="nonNegativeInteger")
        protected BigInteger decimalPlaces;

        public List<AdditionalGuestAmountType> getAdditionalGuestAmount() {
            if (this.additionalGuestAmount == null) {
                this.additionalGuestAmount = new ArrayList<AdditionalGuestAmountType>();
            }
            return this.additionalGuestAmount;
        }

        public BigDecimal getAmountBeforeTax() {
            return this.amountBeforeTax;
        }

        public void setAmountBeforeTax(BigDecimal value) {
            this.amountBeforeTax = value;
        }

        public BigDecimal getAmountAfterTax() {
            return this.amountAfterTax;
        }

        public void setAmountAfterTax(BigDecimal value) {
            this.amountAfterTax = value;
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

