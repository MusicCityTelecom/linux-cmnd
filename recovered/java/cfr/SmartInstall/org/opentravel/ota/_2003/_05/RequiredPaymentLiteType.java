/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.AcceptedPaymentsType;
import org.opentravel.ota._2003._05.AmountPercentType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="RequiredPaymentLiteType", propOrder={"acceptedPayments", "amountPercent"})
public class RequiredPaymentLiteType {
    @XmlElement(name="AcceptedPayments")
    protected AcceptedPaymentsType acceptedPayments;
    @XmlElement(name="AmountPercent")
    protected AmountPercentType amountPercent;
    @XmlAttribute(name="RetributionType")
    protected String retributionType;

    public AcceptedPaymentsType getAcceptedPayments() {
        return this.acceptedPayments;
    }

    public void setAcceptedPayments(AcceptedPaymentsType value) {
        this.acceptedPayments = value;
    }

    public AmountPercentType getAmountPercent() {
        return this.amountPercent;
    }

    public void setAmountPercent(AmountPercentType value) {
        this.amountPercent = value;
    }

    public String getRetributionType() {
        return this.retributionType;
    }

    public void setRetributionType(String value) {
        this.retributionType = value;
    }
}

