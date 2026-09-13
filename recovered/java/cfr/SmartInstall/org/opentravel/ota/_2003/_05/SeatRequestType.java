/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.CompanyNameType;
import org.opentravel.ota._2003._05.LocationType;
import org.opentravel.ota._2003._05.SpecialReqDetailsType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="SeatRequestType", propOrder={"departureAirport", "arrivalAirport", "airline"})
@XmlSeeAlso(value={SpecialReqDetailsType.SeatRequests.SeatRequest.class})
public class SeatRequestType {
    @XmlElement(name="DepartureAirport")
    protected LocationType departureAirport;
    @XmlElement(name="ArrivalAirport")
    protected LocationType arrivalAirport;
    @XmlElement(name="Airline")
    protected CompanyNameType airline;
    @XmlAttribute(name="DepartureDate")
    protected String departureDate;
    @XmlAttribute(name="FlightNumber")
    protected String flightNumber;
    @XmlAttribute(name="Status")
    protected String status;
    @XmlAttribute(name="SeatNumber")
    protected String seatNumber;
    @XmlAttribute(name="SeatPreference")
    protected List<String> seatPreference;
    @XmlAttribute(name="DeckLevel")
    protected String deckLevel;
    @XmlAttribute(name="RowNumber")
    protected Integer rowNumber;
    @XmlAttribute(name="SeatInRow")
    protected String seatInRow;
    @XmlAttribute(name="SmokingAllowed")
    protected Boolean smokingAllowed;

    public LocationType getDepartureAirport() {
        return this.departureAirport;
    }

    public void setDepartureAirport(LocationType value) {
        this.departureAirport = value;
    }

    public LocationType getArrivalAirport() {
        return this.arrivalAirport;
    }

    public void setArrivalAirport(LocationType value) {
        this.arrivalAirport = value;
    }

    public CompanyNameType getAirline() {
        return this.airline;
    }

    public void setAirline(CompanyNameType value) {
        this.airline = value;
    }

    public String getDepartureDate() {
        return this.departureDate;
    }

    public void setDepartureDate(String value) {
        this.departureDate = value;
    }

    public String getFlightNumber() {
        return this.flightNumber;
    }

    public void setFlightNumber(String value) {
        this.flightNumber = value;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String value) {
        this.status = value;
    }

    public String getSeatNumber() {
        return this.seatNumber;
    }

    public void setSeatNumber(String value) {
        this.seatNumber = value;
    }

    public List<String> getSeatPreference() {
        if (this.seatPreference == null) {
            this.seatPreference = new ArrayList<String>();
        }
        return this.seatPreference;
    }

    public String getDeckLevel() {
        return this.deckLevel;
    }

    public void setDeckLevel(String value) {
        this.deckLevel = value;
    }

    public Integer getRowNumber() {
        return this.rowNumber;
    }

    public void setRowNumber(Integer value) {
        this.rowNumber = value;
    }

    public String getSeatInRow() {
        return this.seatInRow;
    }

    public void setSeatInRow(String value) {
        this.seatInRow = value;
    }

    public Boolean isSmokingAllowed() {
        return this.smokingAllowed;
    }

    public void setSmokingAllowed(Boolean value) {
        this.smokingAllowed = value;
    }
}

