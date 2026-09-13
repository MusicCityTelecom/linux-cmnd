/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import org.opentravel.ota._2003._05.UniqueIDType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ReservationID_Type")
public class ReservationIDType
extends UniqueIDType {
    @XmlAttribute(name="StatusCode")
    protected String statusCode;
    @XmlAttribute(name="LastModifyDateTime")
    @XmlSchemaType(name="dateTime")
    protected XMLGregorianCalendar lastModifyDateTime;
    @XmlAttribute(name="BookedDate")
    protected String bookedDate;
    @XmlAttribute(name="OfferDate")
    protected String offerDate;
    @XmlAttribute(name="SyncDateTime")
    @XmlSchemaType(name="dateTime")
    protected XMLGregorianCalendar syncDateTime;

    public String getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(String value) {
        this.statusCode = value;
    }

    public XMLGregorianCalendar getLastModifyDateTime() {
        return this.lastModifyDateTime;
    }

    public void setLastModifyDateTime(XMLGregorianCalendar value) {
        this.lastModifyDateTime = value;
    }

    public String getBookedDate() {
        return this.bookedDate;
    }

    public void setBookedDate(String value) {
        this.bookedDate = value;
    }

    public String getOfferDate() {
        return this.offerDate;
    }

    public void setOfferDate(String value) {
        this.offerDate = value;
    }

    public XMLGregorianCalendar getSyncDateTime() {
        return this.syncDateTime;
    }

    public void setSyncDateTime(XMLGregorianCalendar value) {
        this.syncDateTime = value;
    }
}

