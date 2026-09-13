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
import org.opentravel.ota._2003._05.AirTripType;
import org.opentravel.ota._2003._05.CabinAvailType;
import org.opentravel.ota._2003._05.DayOfWeekType;
import org.opentravel.ota._2003._05.PkgFlightSegmentBaseType;
import org.opentravel.ota._2003._05.TicketType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="PkgFlightSegmentType", propOrder={"cabinAvailability"})
public class PkgFlightSegmentType
extends PkgFlightSegmentBaseType {
    @XmlElement(name="CabinAvailability")
    protected List<CabinAvailType> cabinAvailability;
    @XmlAttribute(name="Type")
    protected String type;
    @XmlAttribute(name="DepartureDay")
    protected DayOfWeekType departureDay;
    @XmlAttribute(name="TravelCode")
    protected String travelCode;
    @XmlAttribute(name="Duration")
    protected Duration duration;
    @XmlAttribute(name="DirectionInd")
    protected AirTripType directionInd;
    @XmlAttribute(name="CheckInDate")
    @XmlSchemaType(name="dateTime")
    protected XMLGregorianCalendar checkInDate;
    @XmlAttribute(name="CheckInDesk")
    protected String checkInDesk;
    @XmlAttribute(name="TOD_Indicator")
    protected Boolean todIndicator;
    @XmlAttribute(name="TicketType")
    protected TicketType ticketType;

    public List<CabinAvailType> getCabinAvailability() {
        if (this.cabinAvailability == null) {
            this.cabinAvailability = new ArrayList<CabinAvailType>();
        }
        return this.cabinAvailability;
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

    public AirTripType getDirectionInd() {
        return this.directionInd;
    }

    public void setDirectionInd(AirTripType value) {
        this.directionInd = value;
    }

    public XMLGregorianCalendar getCheckInDate() {
        return this.checkInDate;
    }

    public void setCheckInDate(XMLGregorianCalendar value) {
        this.checkInDate = value;
    }

    public String getCheckInDesk() {
        return this.checkInDesk;
    }

    public void setCheckInDesk(String value) {
        this.checkInDesk = value;
    }

    public Boolean isTODIndicator() {
        return this.todIndicator;
    }

    public void setTODIndicator(Boolean value) {
        this.todIndicator = value;
    }

    public TicketType getTicketType() {
        return this.ticketType;
    }

    public void setTicketType(TicketType value) {
        this.ticketType = value;
    }
}

