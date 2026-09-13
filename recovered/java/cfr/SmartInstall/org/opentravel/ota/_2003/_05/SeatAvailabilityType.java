/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.ChargesType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="SeatAvailabilityType", propOrder={"charges"})
public class SeatAvailabilityType {
    @XmlElement(name="Charges")
    protected ChargesType charges;
    @XmlAttribute(name="URI")
    @XmlSchemaType(name="anyURI")
    protected String uri;
    @XmlAttribute(name="Quantity")
    @XmlSchemaType(name="nonNegativeInteger")
    protected BigInteger quantity;
    @XmlAttribute(name="Code")
    protected String code;
    @XmlAttribute(name="CodeContext")
    protected String codeContext;

    public ChargesType getCharges() {
        return this.charges;
    }

    public void setCharges(ChargesType value) {
        this.charges = value;
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
}

