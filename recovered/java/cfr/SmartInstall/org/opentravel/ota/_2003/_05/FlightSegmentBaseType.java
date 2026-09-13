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
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import org.opentravel.ota._2003._05.EquipmentType;
import org.opentravel.ota._2003._05.FlightSegmentType;
import org.opentravel.ota._2003._05.OperatingAirlineType;
import org.opentravel.ota._2003._05.PkgAirSegmentType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="FlightSegmentBaseType", propOrder={"departureAirport", "arrivalAirport", "operatingAirline", "equipment"})
@XmlSeeAlso(value={FlightSegmentType.class, PkgAirSegmentType.class})
public class FlightSegmentBaseType {
    @XmlElement(name="DepartureAirport")
    protected DepartureAirport departureAirport;
    @XmlElement(name="ArrivalAirport")
    protected ArrivalAirport arrivalAirport;
    @XmlElement(name="OperatingAirline")
    protected OperatingAirlineType operatingAirline;
    @XmlElement(name="Equipment")
    protected List<EquipmentType> equipment;
    @XmlAttribute(name="DepartureDateTime")
    @XmlSchemaType(name="dateTime")
    protected XMLGregorianCalendar departureDateTime;
    @XmlAttribute(name="ArrivalDateTime")
    @XmlSchemaType(name="dateTime")
    protected XMLGregorianCalendar arrivalDateTime;
    @XmlAttribute(name="StopQuantity")
    @XmlSchemaType(name="nonNegativeInteger")
    protected BigInteger stopQuantity;
    @XmlAttribute(name="RPH")
    protected String rph;
    @XmlAttribute(name="InfoSource")
    protected String infoSource;

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

    public OperatingAirlineType getOperatingAirline() {
        return this.operatingAirline;
    }

    public void setOperatingAirline(OperatingAirlineType value) {
        this.operatingAirline = value;
    }

    public List<EquipmentType> getEquipment() {
        if (this.equipment == null) {
            this.equipment = new ArrayList<EquipmentType>();
        }
        return this.equipment;
    }

    public XMLGregorianCalendar getDepartureDateTime() {
        return this.departureDateTime;
    }

    public void setDepartureDateTime(XMLGregorianCalendar value) {
        this.departureDateTime = value;
    }

    public XMLGregorianCalendar getArrivalDateTime() {
        return this.arrivalDateTime;
    }

    public void setArrivalDateTime(XMLGregorianCalendar value) {
        this.arrivalDateTime = value;
    }

    public BigInteger getStopQuantity() {
        return this.stopQuantity;
    }

    public void setStopQuantity(BigInteger value) {
        this.stopQuantity = value;
    }

    public String getRPH() {
        return this.rph;
    }

    public void setRPH(String value) {
        this.rph = value;
    }

    public String getInfoSource() {
        return this.infoSource;
    }

    public void setInfoSource(String value) {
        this.infoSource = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class DepartureAirport {
        @XmlAttribute(name="LocationCode")
        protected String locationCode;
        @XmlAttribute(name="CodeContext")
        protected String codeContext;
        @XmlAttribute(name="Terminal")
        protected String terminal;
        @XmlAttribute(name="Gate")
        protected String gate;

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

        public String getTerminal() {
            return this.terminal;
        }

        public void setTerminal(String value) {
            this.terminal = value;
        }

        public String getGate() {
            return this.gate;
        }

        public void setGate(String value) {
            this.gate = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class ArrivalAirport {
        @XmlAttribute(name="LocationCode")
        protected String locationCode;
        @XmlAttribute(name="CodeContext")
        protected String codeContext;
        @XmlAttribute(name="Terminal")
        protected String terminal;
        @XmlAttribute(name="Gate")
        protected String gate;

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

        public String getTerminal() {
            return this.terminal;
        }

        public void setTerminal(String value) {
            this.terminal = value;
        }

        public String getGate() {
            return this.gate;
        }

        public void setGate(String value) {
            this.gate = value;
        }
    }
}

