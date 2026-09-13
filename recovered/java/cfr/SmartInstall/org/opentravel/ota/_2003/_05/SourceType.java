/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.CompanyNameType;
import org.opentravel.ota._2003._05.UniqueIDType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="SourceType", propOrder={"requestorID", "position", "bookingChannel"})
public class SourceType {
    @XmlElement(name="RequestorID")
    protected RequestorID requestorID;
    @XmlElement(name="Position")
    protected Position position;
    @XmlElement(name="BookingChannel")
    protected BookingChannel bookingChannel;
    @XmlAttribute(name="AgentSine")
    protected String agentSine;
    @XmlAttribute(name="PseudoCityCode")
    protected String pseudoCityCode;
    @XmlAttribute(name="ISOCountry")
    protected String isoCountry;
    @XmlAttribute(name="ISOCurrency")
    protected String isoCurrency;
    @XmlAttribute(name="AgentDutyCode")
    protected String agentDutyCode;
    @XmlAttribute(name="AirlineVendorID")
    protected String airlineVendorID;
    @XmlAttribute(name="AirportCode")
    protected String airportCode;
    @XmlAttribute(name="FirstDepartPoint")
    protected String firstDepartPoint;
    @XmlAttribute(name="ERSP_UserID")
    protected String erspUserID;
    @XmlAttribute(name="TerminalID")
    protected String terminalID;

    public RequestorID getRequestorID() {
        return this.requestorID;
    }

    public void setRequestorID(RequestorID value) {
        this.requestorID = value;
    }

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position value) {
        this.position = value;
    }

    public BookingChannel getBookingChannel() {
        return this.bookingChannel;
    }

    public void setBookingChannel(BookingChannel value) {
        this.bookingChannel = value;
    }

    public String getAgentSine() {
        return this.agentSine;
    }

    public void setAgentSine(String value) {
        this.agentSine = value;
    }

    public String getPseudoCityCode() {
        return this.pseudoCityCode;
    }

    public void setPseudoCityCode(String value) {
        this.pseudoCityCode = value;
    }

    public String getISOCountry() {
        return this.isoCountry;
    }

    public void setISOCountry(String value) {
        this.isoCountry = value;
    }

    public String getISOCurrency() {
        return this.isoCurrency;
    }

    public void setISOCurrency(String value) {
        this.isoCurrency = value;
    }

    public String getAgentDutyCode() {
        return this.agentDutyCode;
    }

    public void setAgentDutyCode(String value) {
        this.agentDutyCode = value;
    }

    public String getAirlineVendorID() {
        return this.airlineVendorID;
    }

    public void setAirlineVendorID(String value) {
        this.airlineVendorID = value;
    }

    public String getAirportCode() {
        return this.airportCode;
    }

    public void setAirportCode(String value) {
        this.airportCode = value;
    }

    public String getFirstDepartPoint() {
        return this.firstDepartPoint;
    }

    public void setFirstDepartPoint(String value) {
        this.firstDepartPoint = value;
    }

    public String getERSPUserID() {
        return this.erspUserID;
    }

    public void setERSPUserID(String value) {
        this.erspUserID = value;
    }

    public String getTerminalID() {
        return this.terminalID;
    }

    public void setTerminalID(String value) {
        this.terminalID = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class RequestorID
    extends UniqueIDType {
        @XmlAttribute(name="MessagePassword")
        protected String messagePassword;

        public String getMessagePassword() {
            return this.messagePassword;
        }

        public void setMessagePassword(String value) {
            this.messagePassword = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class Position {
        @XmlAttribute(name="Latitude")
        protected String latitude;
        @XmlAttribute(name="Longitude")
        protected String longitude;
        @XmlAttribute(name="Altitude")
        protected String altitude;
        @XmlAttribute(name="AltitudeUnitOfMeasureCode")
        protected String altitudeUnitOfMeasureCode;
        @XmlAttribute(name="PositionAccuracy")
        protected String positionAccuracy;

        public String getLatitude() {
            return this.latitude;
        }

        public void setLatitude(String value) {
            this.latitude = value;
        }

        public String getLongitude() {
            return this.longitude;
        }

        public void setLongitude(String value) {
            this.longitude = value;
        }

        public String getAltitude() {
            return this.altitude;
        }

        public void setAltitude(String value) {
            this.altitude = value;
        }

        public String getAltitudeUnitOfMeasureCode() {
            return this.altitudeUnitOfMeasureCode;
        }

        public void setAltitudeUnitOfMeasureCode(String value) {
            this.altitudeUnitOfMeasureCode = value;
        }

        public String getPositionAccuracy() {
            return this.positionAccuracy;
        }

        public void setPositionAccuracy(String value) {
            this.positionAccuracy = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"companyName"})
    public static class BookingChannel {
        @XmlElement(name="CompanyName")
        protected CompanyNameType companyName;
        @XmlAttribute(name="Type", required=true)
        protected String type;
        @XmlAttribute(name="Primary")
        protected Boolean primary;

        public CompanyNameType getCompanyName() {
            return this.companyName;
        }

        public void setCompanyName(CompanyNameType value) {
            this.companyName = value;
        }

        public String getType() {
            return this.type;
        }

        public void setType(String value) {
            this.type = value;
        }

        public Boolean isPrimary() {
            return this.primary;
        }

        public void setPrimary(Boolean value) {
            this.primary = value;
        }
    }
}

