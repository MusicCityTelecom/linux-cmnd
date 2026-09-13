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
import org.opentravel.ota._2003._05.ErrorType;
import org.opentravel.ota._2003._05.PaymentFormType;
import org.opentravel.ota._2003._05.UniqueIDType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="PaymentResponseType", propOrder={"paymentAmount", "paymentReferenceID", "error"})
public class PaymentResponseType
extends PaymentFormType {
    @XmlElement(name="PaymentAmount")
    protected PaymentAmount paymentAmount;
    @XmlElement(name="PaymentReferenceID")
    protected UniqueIDType paymentReferenceID;
    @XmlElement(name="Error")
    protected ErrorType error;

    public PaymentAmount getPaymentAmount() {
        return this.paymentAmount;
    }

    public void setPaymentAmount(PaymentAmount value) {
        this.paymentAmount = value;
    }

    public UniqueIDType getPaymentReferenceID() {
        return this.paymentReferenceID;
    }

    public void setPaymentReferenceID(UniqueIDType value) {
        this.paymentReferenceID = value;
    }

    public ErrorType getError() {
        return this.error;
    }

    public void setError(ErrorType value) {
        this.error = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class PaymentAmount {
        @XmlAttribute(name="ApprovalCode")
        protected String approvalCode;
        @XmlAttribute(name="Amount")
        protected BigDecimal amount;
        @XmlAttribute(name="CurrencyCode")
        protected String currencyCode;
        @XmlAttribute(name="DecimalPlaces")
        @XmlSchemaType(name="nonNegativeInteger")
        protected BigInteger decimalPlaces;

        public String getApprovalCode() {
            return this.approvalCode;
        }

        public void setApprovalCode(String value) {
            this.approvalCode = value;
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

