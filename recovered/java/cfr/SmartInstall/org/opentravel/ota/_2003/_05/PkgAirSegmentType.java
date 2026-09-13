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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import org.opentravel.ota._2003._05.ChargesType;
import org.opentravel.ota._2003._05.DayOfWeekType;
import org.opentravel.ota._2003._05.FlightSegmentBaseType;
import org.opentravel.ota._2003._05.SeatAvailabilityType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="PkgAirSegmentType", propOrder={"supplementCharges", "availableSeats", "passengers"})
public class PkgAirSegmentType
extends FlightSegmentBaseType {
    @XmlElement(name="SupplementCharges")
    protected List<ChargesType> supplementCharges;
    @XmlElement(name="AvailableSeats")
    protected AvailableSeats availableSeats;
    @XmlElement(name="Passengers")
    protected Passengers passengers;
    @XmlAttribute(name="CheckInDate")
    @XmlSchemaType(name="dateTime")
    protected XMLGregorianCalendar checkInDate;
    @XmlAttribute(name="Type")
    protected String type;
    @XmlAttribute(name="DepartureDay")
    protected DayOfWeekType departureDay;
    @XmlAttribute(name="TravelCode")
    protected String travelCode;
    @XmlAttribute(name="Duration")
    protected Duration duration;

    public List<ChargesType> getSupplementCharges() {
        if (this.supplementCharges == null) {
            this.supplementCharges = new ArrayList<ChargesType>();
        }
        return this.supplementCharges;
    }

    public AvailableSeats getAvailableSeats() {
        return this.availableSeats;
    }

    public void setAvailableSeats(AvailableSeats value) {
        this.availableSeats = value;
    }

    public Passengers getPassengers() {
        return this.passengers;
    }

    public void setPassengers(Passengers value) {
        this.passengers = value;
    }

    public XMLGregorianCalendar getCheckInDate() {
        return this.checkInDate;
    }

    public void setCheckInDate(XMLGregorianCalendar value) {
        this.checkInDate = value;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String value) {
        this.type = value;
    }

    public DayOfWeekType getDepartureDay() {
        return this.departureDay;
    }

    public void setDepartureDay(DayOfWeekType value) {
        this.departureDay = value;
    }

    public String getTravelCode() {
        return this.travelCode;
    }

    public void setTravelCode(String value) {
        this.travelCode = value;
    }

    public Duration getDuration() {
        return this.duration;
    }

    public void setDuration(Duration value) {
        this.duration = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class Passengers {
        @XmlAttribute(name="PassengerRPH")
        protected List<String> passengerRPH;

        public List<String> getPassengerRPH() {
            if (this.passengerRPH == null) {
                this.passengerRPH = new ArrayList<String>();
            }
            return this.passengerRPH;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"seatAvailability"})
    public static class AvailableSeats {
        @XmlElement(name="SeatAvailability", required=true)
        protected List<SeatAvailabilityType> seatAvailability;

        public List<SeatAvailabilityType> getSeatAvailability() {
            if (this.seatAvailability == null) {
                this.seatAvailability = new ArrayList<SeatAvailabilityType>();
            }
            return this.seatAvailability;
        }
    }
}

