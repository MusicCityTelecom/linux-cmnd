/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import org.opentravel.ota._2003._05.CompanyNameType;
import org.opentravel.ota._2003._05.LocationType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="VehicleArrivalDetailsType", propOrder={"arrivalLocation", "marketingCompany", "operatingCompany"})
public class VehicleArrivalDetailsType {
    @XmlElement(name="ArrivalLocation")
    protected LocationType arrivalLocation;
    @XmlElement(name="MarketingCompany")
    protected CompanyNameType marketingCompany;
    @XmlElement(name="OperatingCompany")
    protected CompanyNameType operatingCompany;
    @XmlAttribute(name="TransportationCode")
    protected String transportationCode;
    @XmlAttribute(name="Number")
    protected String number;
    @XmlAttribute(name="ArrivalDateTime")
    @XmlSchemaType(name="dateTime")
    protected XMLGregorianCalendar arrivalDateTime;

    public LocationType getArrivalLocation() {
        return this.arrivalLocation;
    }

    public void setArrivalLocation(LocationType value) {
        this.arrivalLocation = value;
    }

    public CompanyNameType getMarketingCompany() {
        return this.marketingCompany;
    }

    public void setMarketingCompany(CompanyNameType value) {
        this.marketingCompany = value;
    }

    public CompanyNameType getOperatingCompany() {
        return this.operatingCompany;
    }

    public void setOperatingCompany(CompanyNameType value) {
        this.operatingCompany = value;
    }

    public String getTransportationCode() {
        return this.transportationCode;
    }

    public void setTransportationCode(String value) {
        this.transportationCode = value;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String value) {
        this.number = value;
    }

    public XMLGregorianCalendar getArrivalDateTime() {
        return this.arrivalDateTime;
    }

    public void setArrivalDateTime(XMLGregorianCalendar value) {
        this.arrivalDateTime = value;
    }
}

