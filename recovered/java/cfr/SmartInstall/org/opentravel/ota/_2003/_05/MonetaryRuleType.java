/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;
import org.opentravel.ota._2003._05.TimeUnitType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="MonetaryRuleType", propOrder={"value"})
public class MonetaryRuleType {
    @XmlValue
    protected String value;
    @XmlAttribute(name="RuleType", required=true)
    protected String ruleType;
    @XmlAttribute(name="Percent")
    protected BigDecimal percent;
    @XmlAttribute(name="DateTime")
    @XmlSchemaType(name="dateTime")
    protected XMLGregorianCalendar dateTime;
    @XmlAttribute(name="PaymentType")
    protected String paymentType;
    @XmlAttribute(name="RateConvertedInd")
    protected Boolean rateConvertedInd;
    @XmlAttribute(name="AbsoluteDeadline")
    protected String absoluteDeadline;
    @XmlAttribute(name="OffsetTimeUnit")
    protected TimeUnitType offsetTimeUnit;
    @XmlAttribute(name="OffsetUnitMultiplier")
    protected Integer offsetUnitMultiplier;
    @XmlAttribute(name="OffsetDropTime")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    protected String offsetDropTime;
    @XmlAttribute(name="Amount")
    protected BigDecimal amount;
    @XmlAttribute(name="CurrencyCode")
    protected String currencyCode;
    @XmlAttribute(name="DecimalPlaces")
    @XmlSchemaType(name="nonNegativeInteger")
    protected BigInteger decimalPlaces;

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRuleType() {
        return this.ruleType;
    }

    public void setRuleType(String value) {
        this.ruleType = value;
    }

    public BigDecimal getPercent() {
        return this.percent;
    }

    public void setPercent(BigDecimal value) {
        this.percent = value;
    }

    public XMLGregorianCalendar getDateTime() {
        return this.dateTime;
    }

    public void setDateTime(XMLGregorianCalendar value) {
        this.dateTime = value;
    }

    public String getPaymentType() {
        return this.paymentType;
    }

    public void setPaymentType(String value) {
        this.paymentType = value;
    }

    public Boolean isRateConvertedInd() {
        return this.rateConvertedInd;
    }

    public void setRateConvertedInd(Boolean value) {
        this.rateConvertedInd = value;
    }

    public String getAbsoluteDeadline() {
        return this.absoluteDeadline;
    }

    public void setAbsoluteDeadline(String value) {
        this.absoluteDeadline = value;
    }

    public TimeUnitType getOffsetTimeUnit() {
        return this.offsetTimeUnit;
    }

    public void setOffsetTimeUnit(TimeUnitType value) {
        this.offsetTimeUnit = value;
    }

    public Integer getOffsetUnitMultiplier() {
        return this.offsetUnitMultiplier;
    }

    public void setOffsetUnitMultiplier(Integer value) {
        this.offsetUnitMultiplier = value;
    }

    public String getOffsetDropTime() {
        return this.offsetDropTime;
    }

    public void setOffsetDropTime(String value) {
        this.offsetDropTime = value;
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

