/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.opentravel.ota._2003._05.ParagraphType;
import org.opentravel.ota._2003._05.PersonNameType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="AmenityOptionType", propOrder={"originator", "message"})
public class AmenityOptionType {
    @XmlElement(name="Originator")
    protected PersonNameType originator;
    @XmlElement(name="Message")
    protected ParagraphType message;
    @XmlAttribute(name="OptionCode", required=true)
    protected String optionCode;
    @XmlAttribute(name="Quantity")
    protected Integer quantity;
    @XmlAttribute(name="DeliveryDate")
    protected String deliveryDate;
    @XmlAttribute(name="DeliveryLocation")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    protected String deliveryLocation;
    @XmlAttribute(name="ModifiableIndicator")
    protected Boolean modifiableIndicator;

    public PersonNameType getOriginator() {
        return this.originator;
    }

    public void setOriginator(PersonNameType value) {
        this.originator = value;
    }

    public ParagraphType getMessage() {
        return this.message;
    }

    public void setMessage(ParagraphType value) {
        this.message = value;
    }

    public String getOptionCode() {
        return this.optionCode;
    }

    public void setOptionCode(String value) {
        this.optionCode = value;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer value) {
        this.quantity = value;
    }

    public String getDeliveryDate() {
        return this.deliveryDate;
    }

    public void setDeliveryDate(String value) {
        this.deliveryDate = value;
    }

    public String getDeliveryLocation() {
        return this.deliveryLocation;
    }

    public void setDeliveryLocation(String value) {
        this.deliveryLocation = value;
    }

    public Boolean isModifiableIndicator() {
        return this.modifiableIndicator;
    }

    public void setModifiableIndicator(Boolean value) {
        this.modifiableIndicator = value;
    }
}

