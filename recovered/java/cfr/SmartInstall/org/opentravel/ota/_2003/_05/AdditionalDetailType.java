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
import org.opentravel.ota._2003._05.ParagraphType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="AdditionalDetailType", propOrder={"detailDescription"})
public class AdditionalDetailType {
    @XmlElement(name="DetailDescription")
    protected ParagraphType detailDescription;
    @XmlAttribute(name="Type")
    protected String type;
    @XmlAttribute(name="Code")
    protected String code;
    @XmlAttribute(name="Amount")
    protected BigDecimal amount;
    @XmlAttribute(name="CurrencyCode")
    protected String currencyCode;
    @XmlAttribute(name="DecimalPlaces")
    @XmlSchemaType(name="nonNegativeInteger")
    protected BigInteger decimalPlaces;

    public ParagraphType getDetailDescription() {
        return this.detailDescription;
    }

    public void setDetailDescription(ParagraphType value) {
        this.detailDescription = value;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String value) {
        this.type = value;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String value) {
        this.code = value;
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

