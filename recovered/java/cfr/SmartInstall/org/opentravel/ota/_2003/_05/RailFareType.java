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
import org.opentravel.ota._2003._05.FreeTextType;
import org.opentravel.ota._2003._05.RailPriceBreakdownType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="RailFareType", propOrder={"basicFare", "termAndCondition"})
@XmlSeeAlso(value={RailPriceBreakdownType.class})
public class RailFareType {
    @XmlElement(name="BasicFare", required=true)
    protected BasicFare basicFare;
    @XmlElement(name="TermAndCondition")
    protected List<TermAndCondition> termAndCondition;

    public BasicFare getBasicFare() {
        return this.basicFare;
    }

    public void setBasicFare(BasicFare value) {
        this.basicFare = value;
    }

    public List<TermAndCondition> getTermAndCondition() {
        if (this.termAndCondition == null) {
            this.termAndCondition = new ArrayList<TermAndCondition>();
        }
        return this.termAndCondition;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"fareRule", "effectiveDates", "description"})
    public static class TermAndCondition {
        @XmlElement(name="FareRule")
        protected FareRule fareRule;
        @XmlElement(name="EffectiveDates")
        protected EffectiveDates effectiveDates;
        @XmlElement(name="Description")
        protected FreeTextType description;

        public FareRule getFareRule() {
            return this.fareRule;
        }

        public void setFareRule(FareRule value) {
            this.fareRule = value;
        }

        public EffectiveDates getEffectiveDates() {
            return this.effectiveDates;
        }

        public void setEffectiveDates(EffectiveDates value) {
            this.effectiveDates = value;
        }

        public FreeTextType getDescription() {
            return this.description;
        }

        public void setDescription(FreeTextType value) {
            this.description = value;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="")
        public static class FareRule {
            @XmlAttribute(name="Code")
            protected String code;
            @XmlAttribute(name="CodeContext")
            protected String codeContext;

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
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="")
        public static class EffectiveDates {
            @XmlAttribute(name="Start")
            protected String start;
            @XmlAttribute(name="Duration")
            protected String duration;
            @XmlAttribute(name="End")
            protected String end;

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

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class BasicFare {
        @XmlAttribute(name="FareBasisCode")
        protected String fareBasisCode;
        @XmlAttribute(name="Amount")
        protected BigDecimal amount;
        @XmlAttribute(name="CurrencyCode")
        protected String currencyCode;
        @XmlAttribute(name="DecimalPlaces")
        @XmlSchemaType(name="nonNegativeInteger")
        protected BigInteger decimalPlaces;

        public String getFareBasisCode() {
            return this.fareBasisCode;
        }

        public void setFareBasisCode(String value) {
            this.fareBasisCode = value;
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

