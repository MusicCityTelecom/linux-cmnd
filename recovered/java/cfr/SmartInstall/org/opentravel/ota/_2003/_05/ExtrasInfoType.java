/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

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
import org.opentravel.ota._2003._05.ExtrasCoreType;
import org.opentravel.ota._2003._05.PeriodPriceType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ExtrasInfoType", propOrder={"criteria", "periods", "additionalInfoPrompt", "parentExtras", "extraLocationInfo"})
public class ExtrasInfoType
extends ExtrasCoreType {
    @XmlElement(name="Criteria")
    protected Criteria criteria;
    @XmlElement(name="Periods")
    protected Periods periods;
    @XmlElement(name="AdditionalInfoPrompt")
    protected List<AdditionalInfoPrompt> additionalInfoPrompt;
    @XmlElement(name="ParentExtras")
    protected ParentExtras parentExtras;
    @XmlElement(name="ExtraLocationInfo")
    protected List<ExtraLocationInfo> extraLocationInfo;
    @XmlAttribute(name="ApplyTo")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    protected String applyTo;
    @XmlAttribute(name="SelectionType")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    protected String selectionType;
    @XmlAttribute(name="RuleCode")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    protected String ruleCode;

    public Criteria getCriteria() {
        return this.criteria;
    }

    public void setCriteria(Criteria value) {
        this.criteria = value;
    }

    public Periods getPeriods() {
        return this.periods;
    }

    public void setPeriods(Periods value) {
        this.periods = value;
    }

    public List<AdditionalInfoPrompt> getAdditionalInfoPrompt() {
        if (this.additionalInfoPrompt == null) {
            this.additionalInfoPrompt = new ArrayList<AdditionalInfoPrompt>();
        }
        return this.additionalInfoPrompt;
    }

    public ParentExtras getParentExtras() {
        return this.parentExtras;
    }

    public void setParentExtras(ParentExtras value) {
        this.parentExtras = value;
    }

    public List<ExtraLocationInfo> getExtraLocationInfo() {
        if (this.extraLocationInfo == null) {
            this.extraLocationInfo = new ArrayList<ExtraLocationInfo>();
        }
        return this.extraLocationInfo;
    }

    public String getApplyTo() {
        return this.applyTo;
    }

    public void setApplyTo(String value) {
        this.applyTo = value;
    }

    public String getSelectionType() {
        return this.selectionType;
    }

    public void setSelectionType(String value) {
        this.selectionType = value;
    }

    public String getRuleCode() {
        return this.ruleCode;
    }

    public void setRuleCode(String value) {
        this.ruleCode = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"period"})
    public static class Periods {
        @XmlElement(name="Period", required=true)
        protected List<PeriodPriceType> period;

        public List<PeriodPriceType> getPeriod() {
            if (this.period == null) {
                this.period = new ArrayList<PeriodPriceType>();
            }
            return this.period;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class ParentExtras {
        @XmlAttribute(name="ListOfParentRPH")
        protected List<String> listOfParentRPH;
        @XmlAttribute(name="SelectionType")
        @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
        protected String selectionType;
        @XmlAttribute(name="RuleCode")
        @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
        protected String ruleCode;

        public List<String> getListOfParentRPH() {
            if (this.listOfParentRPH == null) {
                this.listOfParentRPH = new ArrayList<String>();
            }
            return this.listOfParentRPH;
        }

        public String getSelectionType() {
            return this.selectionType;
        }

        public void setSelectionType(String value) {
            this.selectionType = value;
        }

        public String getRuleCode() {
            return this.ruleCode;
        }

        public void setRuleCode(String value) {
            this.ruleCode = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class ExtraLocationInfo {
        @XmlAttribute(name="Location")
        protected String location;
        @XmlAttribute(name="Type")
        @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
        protected String type;

        public String getLocation() {
            return this.location;
        }

        public void setLocation(String value) {
            this.location = value;
        }

        public String getType() {
            return this.type;
        }

        public void setType(String value) {
            this.type = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class Criteria {
        @XmlAttribute(name="MinimumAge")
        @XmlSchemaType(name="positiveInteger")
        protected BigInteger minimumAge;
        @XmlAttribute(name="MaximumAge")
        @XmlSchemaType(name="positiveInteger")
        protected BigInteger maximumAge;
        @XmlAttribute(name="YearsExperience")
        @XmlSchemaType(name="positiveInteger")
        protected BigInteger yearsExperience;
        @XmlAttribute(name="DateRequiredInd")
        protected Boolean dateRequiredInd;
        @XmlAttribute(name="DurationRequiredInd")
        protected Boolean durationRequiredInd;
        @XmlAttribute(name="StockControlledInd")
        protected Boolean stockControlledInd;
        @XmlAttribute(name="MaximumOccupancy")
        protected Integer maximumOccupancy;

        public BigInteger getMinimumAge() {
            return this.minimumAge;
        }

        public void setMinimumAge(BigInteger value) {
            this.minimumAge = value;
        }

        public BigInteger getMaximumAge() {
            return this.maximumAge;
        }

        public void setMaximumAge(BigInteger value) {
            this.maximumAge = value;
        }

        public BigInteger getYearsExperience() {
            return this.yearsExperience;
        }

        public void setYearsExperience(BigInteger value) {
            this.yearsExperience = value;
        }

        public Boolean isDateRequiredInd() {
            return this.dateRequiredInd;
        }

        public void setDateRequiredInd(Boolean value) {
            this.dateRequiredInd = value;
        }

        public Boolean isDurationRequiredInd() {
            return this.durationRequiredInd;
        }

        public void setDurationRequiredInd(Boolean value) {
            this.durationRequiredInd = value;
        }

        public Boolean isStockControlledInd() {
            return this.stockControlledInd;
        }

        public void setStockControlledInd(Boolean value) {
            this.stockControlledInd = value;
        }

        public Integer getMaximumOccupancy() {
            return this.maximumOccupancy;
        }

        public void setMaximumOccupancy(Integer value) {
            this.maximumOccupancy = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class AdditionalInfoPrompt {
        @XmlAttribute(name="AdditionalInfoText")
        protected String additionalInfoText;
        @XmlAttribute(name="LineNumber")
        protected Integer lineNumber;

        public String getAdditionalInfoText() {
            return this.additionalInfoText;
        }

        public void setAdditionalInfoText(String value) {
            this.additionalInfoText = value;
        }

        public Integer getLineNumber() {
            return this.lineNumber;
        }

        public void setLineNumber(Integer value) {
            this.lineNumber = value;
        }
    }
}

