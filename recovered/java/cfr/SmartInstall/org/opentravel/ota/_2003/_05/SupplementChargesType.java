/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.FeeType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="SupplementChargesType", propOrder={"charge"})
public class SupplementChargesType {
    @XmlElement(name="Charge", required=true)
    protected List<FeeType> charge;
    @XmlAttribute(name="CurrencyCode")
    protected String currencyCode;
    @XmlAttribute(name="DecimalPlaces")
    @XmlSchemaType(name="nonNegativeInteger")
    protected BigInteger decimalPlaces;

    public List<FeeType> getCharge() {
        if (this.charge == null) {
            this.charge = new ArrayList<FeeType>();
        }
        return this.charge;
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

