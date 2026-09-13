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
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.opentravel.ota._2003._05.RequiredPaymentsType;
import org.opentravel.ota._2003._05.TaxesType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="AmountPercentType", propOrder={"taxes"})
@XmlSeeAlso(value={RequiredPaymentsType.GuaranteePayment.AmountPercent.class})
public class AmountPercentType {
    @XmlElement(name="Taxes")
    protected TaxesType taxes;
    @XmlAttribute(name="TaxInclusive")
    protected Boolean taxInclusive;
    @XmlAttribute(name="FeesInclusive")
    protected Boolean feesInclusive;
    @XmlAttribute(name="NmbrOfNights")
    @XmlSchemaType(name="nonNegativeInteger")
    protected BigInteger nmbrOfNights;
    @XmlAttribute(name="BasisType")
    protected String basisType;
    @XmlAttribute(name="Percent")
    protected BigDecimal percent;
    @XmlAttribute(name="ApplyAs")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    protected String applyAs;
    @XmlAttribute(name="Amount")
    protected BigDecimal amount;
    @XmlAttribute(name="CurrencyCode")
    protected String currencyCode;
    @XmlAttribute(name="DecimalPlaces")
    @XmlSchemaType(name="nonNegativeInteger")
    protected BigInteger decimalPlaces;

    public TaxesType getTaxes() {
        return this.taxes;
    }

    public void setTaxes(TaxesType value) {
        this.taxes = value;
    }

    public Boolean isTaxInclusive() {
        return this.taxInclusive;
    }

    public void setTaxInclusive(Boolean value) {
        this.taxInclusive = value;
    }

    public Boolean isFeesInclusive() {
        return this.feesInclusive;
    }

    public void setFeesInclusive(Boolean value) {
        this.feesInclusive = value;
    }

    public BigInteger getNmbrOfNights() {
        return this.nmbrOfNights;
    }

    public void setNmbrOfNights(BigInteger value) {
        this.nmbrOfNights = value;
    }

    public String getBasisType() {
        return this.basisType;
    }

    public void setBasisType(String value) {
        this.basisType = value;
    }

    public BigDecimal getPercent() {
        return this.percent;
    }

    public void setPercent(BigDecimal value) {
        this.percent = value;
    }

    public String getApplyAs() {
        return this.applyAs;
    }

    public void setApplyAs(String value) {
        this.applyAs = value;
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

