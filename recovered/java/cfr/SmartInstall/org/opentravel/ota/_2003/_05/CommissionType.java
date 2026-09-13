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
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.opentravel.ota._2003._05.ParagraphType;
import org.opentravel.ota._2003._05.UniqueIDType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="CommissionType", propOrder={"uniqueID", "commissionableAmount", "prepaidAmount", "flatCommission", "commissionPayableAmount", "comment"})
public class CommissionType {
    @XmlElement(name="UniqueID")
    protected UniqueIDType uniqueID;
    @XmlElement(name="CommissionableAmount")
    protected CommissionableAmount commissionableAmount;
    @XmlElement(name="PrepaidAmount")
    protected PrepaidAmount prepaidAmount;
    @XmlElement(name="FlatCommission")
    protected FlatCommission flatCommission;
    @XmlElement(name="CommissionPayableAmount")
    protected CommissionPayableAmount commissionPayableAmount;
    @XmlElement(name="Comment")
    protected ParagraphType comment;
    @XmlAttribute(name="StatusType")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    protected String statusType;
    @XmlAttribute(name="Percent")
    protected BigDecimal percent;
    @XmlAttribute(name="ReasonCode")
    protected String reasonCode;
    @XmlAttribute(name="BillToID")
    protected String billToID;
    @XmlAttribute(name="Frequency")
    protected String frequency;
    @XmlAttribute(name="MaxCommissionUnitApplies")
    @XmlSchemaType(name="positiveInteger")
    protected BigInteger maxCommissionUnitApplies;
    @XmlAttribute(name="CapAmount")
    protected BigDecimal capAmount;
    @XmlAttribute(name="CurrencyCode")
    protected String currencyCode;
    @XmlAttribute(name="DecimalPlaces")
    @XmlSchemaType(name="nonNegativeInteger")
    protected BigInteger decimalPlaces;

    public UniqueIDType getUniqueID() {
        return this.uniqueID;
    }

    public void setUniqueID(UniqueIDType value) {
        this.uniqueID = value;
    }

    public CommissionableAmount getCommissionableAmount() {
        return this.commissionableAmount;
    }

    public void setCommissionableAmount(CommissionableAmount value) {
        this.commissionableAmount = value;
    }

    public PrepaidAmount getPrepaidAmount() {
        return this.prepaidAmount;
    }

    public void setPrepaidAmount(PrepaidAmount value) {
        this.prepaidAmount = value;
    }

    public FlatCommission getFlatCommission() {
        return this.flatCommission;
    }

    public void setFlatCommission(FlatCommission value) {
        this.flatCommission = value;
    }

    public CommissionPayableAmount getCommissionPayableAmount() {
        return this.commissionPayableAmount;
    }

    public void setCommissionPayableAmount(CommissionPayableAmount value) {
        this.commissionPayableAmount = value;
    }

    public ParagraphType getComment() {
        return this.comment;
    }

    public void setComment(ParagraphType value) {
        this.comment = value;
    }

    public String getStatusType() {
        return this.statusType;
    }

    public void setStatusType(String value) {
        this.statusType = value;
    }

    public BigDecimal getPercent() {
        return this.percent;
    }

    public void setPercent(BigDecimal value) {
        this.percent = value;
    }

    public String getReasonCode() {
        return this.reasonCode;
    }

    public void setReasonCode(String value) {
        this.reasonCode = value;
    }

    public String getBillToID() {
        return this.billToID;
    }

    public void setBillToID(String value) {
        this.billToID = value;
    }

    public String getFrequency() {
        return this.frequency;
    }

    public void setFrequency(String value) {
        this.frequency = value;
    }

    public BigInteger getMaxCommissionUnitApplies() {
        return this.maxCommissionUnitApplies;
    }

    public void setMaxCommissionUnitApplies(BigInteger value) {
        this.maxCommissionUnitApplies = value;
    }

    public BigDecimal getCapAmount() {
        return this.capAmount;
    }

    public void setCapAmount(BigDecimal value) {
        this.capAmount = value;
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

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class PrepaidAmount {
        @XmlAttribute(name="Amount")
        protected BigDecimal amount;
        @XmlAttribute(name="CurrencyCode")
        protected String currencyCode;
        @XmlAttribute(name="DecimalPlaces")
        @XmlSchemaType(name="nonNegativeInteger")
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

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class FlatCommission {
        @XmlAttribute(name="Amount")
        protected BigDecimal amount;
        @XmlAttribute(name="CurrencyCode")
        protected String currencyCode;
        @XmlAttribute(name="DecimalPlaces")
        @XmlSchemaType(name="nonNegativeInteger")
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

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class CommissionPayableAmount {
        @XmlAttribute(name="Amount")
        protected BigDecimal amount;
        @XmlAttribute(name="CurrencyCode")
        protected String currencyCode;
        @XmlAttribute(name="DecimalPlaces")
        @XmlSchemaType(name="nonNegativeInteger")
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

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class CommissionableAmount {
        @XmlAttribute(name="TaxInclusiveIndicator")
        protected Boolean taxInclusiveIndicator;
        @XmlAttribute(name="Amount")
        protected BigDecimal amount;
        @XmlAttribute(name="CurrencyCode")
        protected String currencyCode;
        @XmlAttribute(name="DecimalPlaces")
        @XmlSchemaType(name="nonNegativeInteger")
        protected BigInteger decimalPlaces;

        public Boolean isTaxInclusiveIndicator() {
            return this.taxInclusiveIndicator;
        }

        public void setTaxInclusiveIndicator(Boolean value) {
            this.taxInclusiveIndicator = value;
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

