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
import org.opentravel.ota._2003._05.CategoryOptionType;
import org.opentravel.ota._2003._05.FreeTextType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="PriceInfoType", propOrder={"priceDescription"})
@XmlSeeAlso(value={CategoryOptionType.PriceInfos.PriceInfo.class})
public class PriceInfoType {
    @XmlElement(name="PriceDescription")
    protected FreeTextType priceDescription;
    @XmlAttribute(name="AgeQualifyingCode")
    protected String ageQualifyingCode;
    @XmlAttribute(name="BreakdownType")
    protected String breakdownType;
    @XmlAttribute(name="ChargeTypeCode")
    protected String chargeTypeCode;
    @XmlAttribute(name="Amount")
    protected BigDecimal amount;
    @XmlAttribute(name="CurrencyCode")
    protected String currencyCode;
    @XmlAttribute(name="DecimalPlaces")
    @XmlSchemaType(name="nonNegativeInteger")
    protected BigInteger decimalPlaces;

    public FreeTextType getPriceDescription() {
        return this.priceDescription;
    }

    public void setPriceDescription(FreeTextType value) {
        this.priceDescription = value;
    }

    public String getAgeQualifyingCode() {
        return this.ageQualifyingCode;
    }

    public void setAgeQualifyingCode(String value) {
        this.ageQualifyingCode = value;
    }

    public String getBreakdownType() {
        return this.breakdownType;
    }

    public void setBreakdownType(String value) {
        this.breakdownType = value;
    }

    public String getChargeTypeCode() {
        return this.chargeTypeCode;
    }

    public void setChargeTypeCode(String value) {
        this.chargeTypeCode = value;
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

