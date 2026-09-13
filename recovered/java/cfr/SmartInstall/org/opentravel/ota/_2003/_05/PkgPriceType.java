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
import org.opentravel.ota._2003._05.PricingType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="PkgPriceType")
public class PkgPriceType {
    @XmlAttribute(name="PriceBasis")
    protected PricingType priceBasis;
    @XmlAttribute(name="Age")
    protected Integer age;
    @XmlAttribute(name="Code")
    protected String code;
    @XmlAttribute(name="CodeContext")
    protected String codeContext;
    @XmlAttribute(name="URI")
    @XmlSchemaType(name="anyURI")
    protected String uri;
    @XmlAttribute(name="Quantity")
    @XmlSchemaType(name="nonNegativeInteger")
    protected BigInteger quantity;
    @XmlAttribute(name="Amount")
    protected BigDecimal amount;
    @XmlAttribute(name="CurrencyCode")
    protected String currencyCode;
    @XmlAttribute(name="DecimalPlaces")
    @XmlSchemaType(name="nonNegativeInteger")
    protected BigInteger decimalPlaces;

    public PricingType getPriceBasis() {
        return this.priceBasis;
    }

    public void setPriceBasis(PricingType value) {
        this.priceBasis = value;
    }

    public Integer getAge() {
        return this.age;
    }

    public void setAge(Integer value) {
        this.age = value;
    }

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

    public String getURI() {
        return this.uri;
    }

    public void setURI(String value) {
        this.uri = value;
    }

    public BigInteger getQuantity() {
        return this.quantity;
    }

    public void setQuantity(BigInteger value) {
        this.quantity = value;
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

