/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.datatype.XMLGregorianCalendar;
import org.opentravel.ota._2003._05.ActionType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="FareBasisCodeType", propOrder={"value"})
public class FareBasisCodeType {
    @XmlValue
    protected String value;
    @XmlAttribute(name="FlightSegmentRPH")
    protected String flightSegmentRPH;
    @XmlAttribute(name="NotValidBefore")
    @XmlSchemaType(name="date")
    protected XMLGregorianCalendar notValidBefore;
    @XmlAttribute(name="NotValidAfter")
    @XmlSchemaType(name="date")
    protected XMLGregorianCalendar notValidAfter;
    @XmlAttribute(name="Operation")
    protected ActionType operation;

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getFlightSegmentRPH() {
        return this.flightSegmentRPH;
    }

    public void setFlightSegmentRPH(String value) {
        this.flightSegmentRPH = value;
    }

    public XMLGregorianCalendar getNotValidBefore() {
        return this.notValidBefore;
    }

    public void setNotValidBefore(XMLGregorianCalendar value) {
        this.notValidBefore = value;
    }

    public XMLGregorianCalendar getNotValidAfter() {
        return this.notValidAfter;
    }

    public void setNotValidAfter(XMLGregorianCalendar value) {
        this.notValidAfter = value;
    }

    public ActionType getOperation() {
        return this.operation;
    }

    public void setOperation(ActionType value) {
        this.operation = value;
    }
}

