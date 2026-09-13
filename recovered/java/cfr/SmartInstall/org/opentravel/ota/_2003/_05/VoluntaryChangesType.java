/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="VoluntaryChangesType", propOrder={"penalty"})
public class VoluntaryChangesType {
    @XmlElement(name="Penalty")
    protected Penalty penalty;
    @XmlAttribute(name="VolChangeInd")
    protected Boolean volChangeInd;

    public Penalty getPenalty() {
        return this.penalty;
    }

    public void setPenalty(Penalty value) {
        this.penalty = value;
    }

    public Boolean isVolChangeInd() {
        return this.volChangeInd;
    }

    public void setVolChangeInd(Boolean value) {
        this.volChangeInd = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class Penalty {
        @XmlAttribute(name="PenaltyType")
        protected String penaltyType;
        @XmlAttribute(name="DepartureStatus")
        protected String departureStatus;
        @XmlAttribute(name="Percent")
        protected BigDecimal percent;
        @XmlAttribute(name="Amount")
        protected BigDecimal amount;
        @XmlAttribute(name="CurrencyCode")
        protected String currencyCode;
        @XmlAttribute(name="DecimalPlaces")
        @XmlSchemaType(name="nonNegativeInteger")
        protected BigInteger decimalPlaces;

        public String getPenaltyType() {
            return this.penaltyType;
        }

        public void setPenaltyType(String value) {
            this.penaltyType = value;
        }

        public String getDepartureStatus() {
            return this.departureStatus;
        }

        public void setDepartureStatus(String value) {
            this.departureStatus = value;
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
    }
}

