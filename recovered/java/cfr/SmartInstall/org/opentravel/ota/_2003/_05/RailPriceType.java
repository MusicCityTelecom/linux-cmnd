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
import org.opentravel.ota._2003._05.RailPriceBreakdownType;
import org.opentravel.ota._2003._05.TPAExtensionsType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="RailPriceType", propOrder={"price", "priceBreakdown", "tpaExtensions"})
public class RailPriceType {
    @XmlElement(name="Price")
    protected Price price;
    @XmlElement(name="PriceBreakdown", required=true)
    protected RailPriceBreakdownType priceBreakdown;
    @XmlElement(name="TPA_Extensions")
    protected TPAExtensionsType tpaExtensions;

    public Price getPrice() {
        return this.price;
    }

    public void setPrice(Price value) {
        this.price = value;
    }

    public RailPriceBreakdownType getPriceBreakdown() {
        return this.priceBreakdown;
    }

    public void setPriceBreakdown(RailPriceBreakdownType value) {
        this.priceBreakdown = value;
    }

    public TPAExtensionsType getTPAExtensions() {
        return this.tpaExtensions;
    }

    public void setTPAExtensions(TPAExtensionsType value) {
        this.tpaExtensions = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class Price {
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
}

