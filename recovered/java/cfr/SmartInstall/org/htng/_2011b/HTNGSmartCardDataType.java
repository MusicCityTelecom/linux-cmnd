/*
 * Decompiled with CFR 0.152.
 */
package org.htng._2011b;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="HTNG_SmartCardDataType", propOrder={"readOnlyData"})
public class HTNGSmartCardDataType {
    @XmlElement(name="ReadOnlyData", required=true)
    protected byte[] readOnlyData;
    @XmlAttribute(name="CardID")
    protected String cardID;
    @XmlAttribute(name="CardType")
    protected String cardType;

    public byte[] getReadOnlyData() {
        return this.readOnlyData;
    }

    public void setReadOnlyData(byte[] value) {
        this.readOnlyData = value;
    }

    public String getCardID() {
        return this.cardID;
    }

    public void setCardID(String value) {
        this.cardID = value;
    }

    public String getCardType() {
        return this.cardType;
    }

    public void setCardType(String value) {
        this.cardType = value;
    }
}

