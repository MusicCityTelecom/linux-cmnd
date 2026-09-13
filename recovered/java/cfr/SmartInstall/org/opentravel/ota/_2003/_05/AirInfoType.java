/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.CabinType;
import org.opentravel.ota._2003._05.CruiseGuestDetailType;
import org.opentravel.ota._2003._05.LocationType;
import org.opentravel.ota._2003._05.OperatingAirlineType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="AirInfoType", propOrder={"departureCity", "arrivalCity", "airline"})
@XmlSeeAlso(value={CruiseGuestDetailType.AirAccommodations.AirAccommodation.class})
public class AirInfoType {
    @XmlElement(name="DepartureCity")
    protected LocationType departureCity;
    @XmlElement(name="ArrivalCity")
    protected LocationType arrivalCity;
    @XmlElement(name="Airline")
    protected OperatingAirlineType airline;
    @XmlAttribute(name="DepartureDateTime")
    protected String departureDateTime;
    @XmlAttribute(name="ArrivalDateTime")
    protected String arrivalDateTime;
    @XmlAttribute(name="AirlineCabinClass")
    protected CabinType airlineCabinClass;

    public LocationType getDepartureCity() {
        return this.departureCity;
    }

    public void setDepartureCity(LocationType value) {
        this.departureCity = value;
    }

    public LocationType getArrivalCity() {
        return this.arrivalCity;
    }

    public void setArrivalCity(LocationType value) {
        this.arrivalCity = value;
    }

    public OperatingAirlineType getAirline() {
        return this.airline;
    }

    public void setAirline(OperatingAirlineType value) {
        this.airline = value;
    }

    public String getDepartureDateTime() {
        return this.departureDateTime;
    }

    public void setDepartureDateTime(String value) {
        this.departureDateTime = value;
    }

    public String getArrivalDateTime() {
        return this.arrivalDateTime;
    }

    public void setArrivalDateTime(String value) {
        this.arrivalDateTime = value;
    }

    public CabinType getAirlineCabinClass() {
        return this.airlineCabinClass;
    }

    public void setAirlineCabinClass(CabinType value) {
        this.airlineCabinClass = value;
    }
}

