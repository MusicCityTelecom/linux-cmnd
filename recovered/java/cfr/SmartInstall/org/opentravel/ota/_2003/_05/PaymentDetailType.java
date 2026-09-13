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
import org.opentravel.ota._2003._05.CommissionType;
import org.opentravel.ota._2003._05.CruiseGuestInfoType;
import org.opentravel.ota._2003._05.FulfillmentType;
import org.opentravel.ota._2003._05.PaymentFormType;
import org.opentravel.ota._2003._05.VehicleReservationRQAdditionalInfoType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="PaymentDetailType", propOrder={"paymentAmount", "commission"})
@XmlSeeAlso(value={FulfillmentType.PaymentDetails.PaymentDetail.class, VehicleReservationRQAdditionalInfoType.RentalPaymentPref.class, CruiseGuestInfoType.PaymentOptions.PaymentOption.class})
public class PaymentDetailType
extends PaymentFormType {
    @XmlElement(name="PaymentAmount")
    protected List<PaymentAmount> paymentAmount;
    @XmlElement(name="Commission")
    protected CommissionType commission;
    @XmlAttribute(name="PaymentType")
    protected String paymentType;
    @XmlAttribute(name="SplitPaymentInd")
    protected Boolean splitPaymentInd;
    @XmlAttribute(name="AuthorizedDays")
    protected Integer authorizedDays;
    @XmlAttribute(name="PrimaryPaymentInd")
    protected Boolean primaryPaymentInd;

    public List<PaymentAmount> getPaymentAmount() {
        if (this.paymentAmount == null) {
            this.paymentAmount = new ArrayList<PaymentAmount>();
        }
        return this.paymentAmount;
    }

    public CommissionType getCommission() {
        return this.commission;
    }

    public void setCommission(CommissionType value) {
        this.commission = value;
    }

    public String getPaymentType() {
        return this.paymentType;
    }

    public void setPaymentType(String value) {
        this.paymentType = value;
    }

    public Boolean isSplitPaymentInd() {
        return this.splitPaymentInd;
    }

    public void setSplitPaymentInd(Boolean value) {
        this.splitPaymentInd = value;
    }

    public Integer getAuthorizedDays() {
        return this.authorizedDays;
    }

    public void setAuthorizedDays(Integer value) {
        this.authorizedDays = value;
    }

    public Boolean isPrimaryPaymentInd() {
        return this.primaryPaymentInd;
    }

    public void setPrimaryPaymentInd(Boolean value) {
        this.primaryPaymentInd = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class PaymentAmount {
        @XmlAttribute(name="ApprovalCode")
        protected String approvalCode;
        @XmlAttribute(name="RefundCalcMethod")
        @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
        protected String refundCalcMethod;
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

        public String getRefundCalcMethod() {
            return this.refundCalcMethod;
        }

        public void setRefundCalcMethod(String value) {
            this.refundCalcMethod = value;
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

