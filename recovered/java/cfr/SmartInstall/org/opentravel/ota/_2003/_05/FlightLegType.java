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

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="FlightLegType", propOrder={"departureAirport", "arrivalAirport"})
public class FlightLegType {
    @XmlElement(name="DepartureAirport")
    protected DepartureAirport departureAirport;
    @XmlElement(name="ArrivalAirport")
    protected ArrivalAirport arrivalAirport;
    @XmlAttribute(name="FlightNumber")
    protected String flightNumber;
    @XmlAttribute(name="ResBookDesigCode")
    protected String resBookDesigCode;
    @XmlAttribute(name="Date")
    @XmlSchemaType(name="dateTime")
    protected XMLGregorianCalendar date;

    public DepartureAirport getDepartureAirport() {
        return this.departureAirport;
    }

    public void setDepartureAirport(DepartureAirport value) {
        this.departureAirport = value;
    }

    public ArrivalAirport getArrivalAirport() {
        return this.arrivalAirport;
    }

    public void setArrivalAirport(ArrivalAirport value) {
        this.arrivalAirport = value;
    }

    public String getFlightNumber() {
        return this.flightNumber;
    }

    public void setFlightNumber(String value) {
        this.flightNumber = value;
    }

    public String getResBookDesigCode() {
        return this.resBookDesigCode;
    }

    public void setResBookDesigCode(String value) {
        this.resBookDesigCode = value;
    }

    public XMLGregorianCalendar getDate() {
        return this.date;
    }

    public void setDate(XMLGregorianCalendar value) {
        this.date = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class DepartureAirport {
        @XmlAttribute(name="LocationCode")
        protected String locationCode;
        @XmlAttribute(name="CodeContext")
        protected String codeContext;

        public String getLocationCode() {
            return this.locationCode;
        }

        public void setLocationCode(String value) {
            this.locationCode = value;
        }

        public String getCodeContext() {
            return this.codeContext;
        }

        public void setCodeContext(String value) {
            this.codeContext = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class ArrivalAirport {
        @XmlAttribute(name="LocationCode")
        protected String locationCode;
        @XmlAttribute(name="CodeContext")
        protected String codeContext;

        public String getLocationCode() {
            return this.locationCode;
        }

        public void setLocationCode(String value) {
            this.locationCode = value;
        }

        public String getCodeContext() {
            return this.codeContext;
        }

        public void setCodeContext(String value) {
            this.codeContext = value;
        }
    }
}

