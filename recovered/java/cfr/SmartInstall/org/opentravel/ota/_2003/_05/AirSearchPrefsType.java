/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;
import org.opentravel.ota._2003._05.AdvResTicketingType;
import org.opentravel.ota._2003._05.CabinType;
import org.opentravel.ota._2003._05.CompanyNamePrefType;
import org.opentravel.ota._2003._05.EquipmentTypePref;
import org.opentravel.ota._2003._05.FlightTypeType;
import org.opentravel.ota._2003._05.PreferLevelType;
import org.opentravel.ota._2003._05.StayRestrictionsType;
import org.opentravel.ota._2003._05.TicketDistribPrefType;
import org.opentravel.ota._2003._05.VoluntaryChangesType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="AirSearchPrefsType", propOrder={"vendorPref", "flightTypePref", "fareRestrictPref", "equipPref", "cabinPref", "ticketDistribPref", "bookingSeatPref"})
public class AirSearchPrefsType {
    @XmlElement(name="VendorPref")
    protected List<VendorPref> vendorPref;
    @XmlElement(name="FlightTypePref")
    protected List<FlightTypePref> flightTypePref;
    @XmlElement(name="FareRestrictPref")
    protected List<FareRestrictPref> fareRestrictPref;
    @XmlElement(name="EquipPref")
    protected List<EquipmentTypePref> equipPref;
    @XmlElement(name="CabinPref")
    protected List<CabinPref> cabinPref;
    @XmlElement(name="TicketDistribPref")
    protected List<TicketDistribPref> ticketDistribPref;
    @XmlElement(name="BookingSeatPref")
    protected BookingSeatPref bookingSeatPref;
    @XmlAttribute(name="OnTimeRate")
    protected BigDecimal onTimeRate;
    @XmlAttribute(name="ETicketDesired")
    protected Boolean eTicketDesired;
    @XmlAttribute(name="MaxStopsQuantity")
    protected Integer maxStopsQuantity;
    @XmlAttribute(name="Start")
    protected String start;
    @XmlAttribute(name="Duration")
    protected String duration;
    @XmlAttribute(name="End")
    protected String end;
    @XmlAttribute(name="SmokingAllowed")
    protected Boolean smokingAllowed;

    public List<VendorPref> getVendorPref() {
        if (this.vendorPref == null) {
            this.vendorPref = new ArrayList<VendorPref>();
        }
        return this.vendorPref;
    }

    public List<FlightTypePref> getFlightTypePref() {
        if (this.flightTypePref == null) {
            this.flightTypePref = new ArrayList<FlightTypePref>();
        }
        return this.flightTypePref;
    }

    public List<FareRestrictPref> getFareRestrictPref() {
        if (this.fareRestrictPref == null) {
            this.fareRestrictPref = new ArrayList<FareRestrictPref>();
        }
        return this.fareRestrictPref;
    }

    public List<EquipmentTypePref> getEquipPref() {
        if (this.equipPref == null) {
            this.equipPref = new ArrayList<EquipmentTypePref>();
        }
        return this.equipPref;
    }

    public List<CabinPref> getCabinPref() {
        if (this.cabinPref == null) {
            this.cabinPref = new ArrayList<CabinPref>();
        }
        return this.cabinPref;
    }

    public List<TicketDistribPref> getTicketDistribPref() {
        if (this.ticketDistribPref == null) {
            this.ticketDistribPref = new ArrayList<TicketDistribPref>();
        }
        return this.ticketDistribPref;
    }

    public BookingSeatPref getBookingSeatPref() {
        return this.bookingSeatPref;
    }

    public void setBookingSeatPref(BookingSeatPref value) {
        this.bookingSeatPref = value;
    }

    public BigDecimal getOnTimeRate() {
        return this.onTimeRate;
    }

    public void setOnTimeRate(BigDecimal value) {
        this.onTimeRate = value;
    }

    public Boolean isETicketDesired() {
        return this.eTicketDesired;
    }

    public void setETicketDesired(Boolean value) {
        this.eTicketDesired = value;
    }

    public Integer getMaxStopsQuantity() {
        return this.maxStopsQuantity;
    }

    public void setMaxStopsQuantity(Integer value) {
        this.maxStopsQuantity = value;
    }

    public String getStart() {
        return this.start;
    }

    public void setStart(String value) {
        this.start = value;
    }

    public String getDuration() {
        return this.duration;
    }

    public void setDuration(String value) {
        this.duration = value;
    }

    public String getEnd() {
        return this.end;
    }

    public void setEnd(String value) {
        this.end = value;
    }

    public Boolean isSmokingAllowed() {
        return this.smokingAllowed;
    }

    public void setSmokingAllowed(Boolean value) {
        this.smokingAllowed = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class VendorPref
    extends CompanyNamePrefType {
        @XmlAttribute(name="AllianceAllowedInd")
        protected Boolean allianceAllowedInd;
        @XmlAttribute(name="LoyaltyAllowedInd")
        protected Boolean loyaltyAllowedInd;

        public Boolean isAllianceAllowedInd() {
            return this.allianceAllowedInd;
        }

        public void setAllianceAllowedInd(Boolean value) {
            this.allianceAllowedInd = value;
        }

        public Boolean isLoyaltyAllowedInd() {
            return this.loyaltyAllowedInd;
        }

        public void setLoyaltyAllowedInd(Boolean value) {
            this.loyaltyAllowedInd = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class TicketDistribPref
    extends TicketDistribPrefType {
        @XmlAttribute(name="LastTicketDate")
        @XmlSchemaType(name="dateTime")
        protected XMLGregorianCalendar lastTicketDate;
        @XmlAttribute(name="FirstTicketDate")
        @XmlSchemaType(name="dateTime")
        protected XMLGregorianCalendar firstTicketDate;

        public XMLGregorianCalendar getLastTicketDate() {
            return this.lastTicketDate;
        }

        public void setLastTicketDate(XMLGregorianCalendar value) {
            this.lastTicketDate = value;
        }

        public XMLGregorianCalendar getFirstTicketDate() {
            return this.firstTicketDate;
        }

        public void setFirstTicketDate(XMLGregorianCalendar value) {
            this.firstTicketDate = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class FlightTypePref {
        @XmlAttribute(name="FlightType")
        protected FlightTypeType flightType;
        @XmlAttribute(name="MaxConnections")
        @XmlSchemaType(name="nonNegativeInteger")
        protected BigInteger maxConnections;
        @XmlAttribute(name="NonScheduledFltInfo")
        @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
        protected String nonScheduledFltInfo;
        @XmlAttribute(name="BackhaulIndicator")
        protected Boolean backhaulIndicator;
        @XmlAttribute(name="GroundTransportIndicator")
        protected Boolean groundTransportIndicator;
        @XmlAttribute(name="DirectAndNonStopOnlyInd")
        protected Boolean directAndNonStopOnlyInd;
        @XmlAttribute(name="NonStopsOnlyInd")
        protected Boolean nonStopsOnlyInd;
        @XmlAttribute(name="OnlineConnectionsOnlyInd")
        protected Boolean onlineConnectionsOnlyInd;
        @XmlAttribute(name="RoutingType")
        @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
        protected String routingType;
        @XmlAttribute(name="ExcludeTrainInd")
        protected Boolean excludeTrainInd;
        @XmlAttribute(name="PreferLevel")
        protected PreferLevelType preferLevel;

        public FlightTypeType getFlightType() {
            return this.flightType;
        }

        public void setFlightType(FlightTypeType value) {
            this.flightType = value;
        }

        public BigInteger getMaxConnections() {
            return this.maxConnections;
        }

        public void setMaxConnections(BigInteger value) {
            this.maxConnections = value;
        }

        public String getNonScheduledFltInfo() {
            return this.nonScheduledFltInfo;
        }

        public void setNonScheduledFltInfo(String value) {
            this.nonScheduledFltInfo = value;
        }

        public Boolean isBackhaulIndicator() {
            return this.backhaulIndicator;
        }

        public void setBackhaulIndicator(Boolean value) {
            this.backhaulIndicator = value;
        }

        public Boolean isGroundTransportIndicator() {
            return this.groundTransportIndicator;
        }

        public void setGroundTransportIndicator(Boolean value) {
            this.groundTransportIndicator = value;
        }

        public Boolean isDirectAndNonStopOnlyInd() {
            return this.directAndNonStopOnlyInd;
        }

        public void setDirectAndNonStopOnlyInd(Boolean value) {
            this.directAndNonStopOnlyInd = value;
        }

        public Boolean isNonStopsOnlyInd() {
            return this.nonStopsOnlyInd;
        }

        public void setNonStopsOnlyInd(Boolean value) {
            this.nonStopsOnlyInd = value;
        }

        public Boolean isOnlineConnectionsOnlyInd() {
            return this.onlineConnectionsOnlyInd;
        }

        public void setOnlineConnectionsOnlyInd(Boolean value) {
            this.onlineConnectionsOnlyInd = value;
        }

        public String getRoutingType() {
            return this.routingType;
        }

        public void setRoutingType(String value) {
            this.routingType = value;
        }

        public Boolean isExcludeTrainInd() {
            return this.excludeTrainInd;
        }

        public void setExcludeTrainInd(Boolean value) {
            this.excludeTrainInd = value;
        }

        public PreferLevelType getPreferLevel() {
            return this.preferLevel;
        }

        public void setPreferLevel(PreferLevelType value) {
            this.preferLevel = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"advResTicketing", "stayRestrictions", "voluntaryChanges"})
    public static class FareRestrictPref {
        @XmlElement(name="AdvResTicketing")
        protected AdvResTicketingType advResTicketing;
        @XmlElement(name="StayRestrictions")
        protected StayRestrictionsType stayRestrictions;
        @XmlElement(name="VoluntaryChanges")
        protected VoluntaryChangesType voluntaryChanges;
        @XmlAttribute(name="FareDisplayCurrency")
        protected String fareDisplayCurrency;
        @XmlAttribute(name="CurrencyOverride")
        protected String currencyOverride;
        @XmlAttribute(name="FareRestriction")
        protected String fareRestriction;
        @XmlAttribute(name="Date")
        protected String date;
        @XmlAttribute(name="PreferLevel")
        protected PreferLevelType preferLevel;

        public AdvResTicketingType getAdvResTicketing() {
            return this.advResTicketing;
        }

        public void setAdvResTicketing(AdvResTicketingType value) {
            this.advResTicketing = value;
        }

        public StayRestrictionsType getStayRestrictions() {
            return this.stayRestrictions;
        }

        public void setStayRestrictions(StayRestrictionsType value) {
            this.stayRestrictions = value;
        }

        public VoluntaryChangesType getVoluntaryChanges() {
            return this.voluntaryChanges;
        }

        public void setVoluntaryChanges(VoluntaryChangesType value) {
            this.voluntaryChanges = value;
        }

        public String getFareDisplayCurrency() {
            return this.fareDisplayCurrency;
        }

        public void setFareDisplayCurrency(String value) {
            this.fareDisplayCurrency = value;
        }

        public String getCurrencyOverride() {
            return this.currencyOverride;
        }

        public void setCurrencyOverride(String value) {
            this.currencyOverride = value;
        }

        public String getFareRestriction() {
            return this.fareRestriction;
        }

        public void setFareRestriction(String value) {
            this.fareRestriction = value;
        }

        public String getDate() {
            return this.date;
        }

        public void setDate(String value) {
            this.date = value;
        }

        public PreferLevelType getPreferLevel() {
            return this.preferLevel;
        }

        public void setPreferLevel(PreferLevelType value) {
            this.preferLevel = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class CabinPref {
        @XmlAttribute(name="CabinSubtype")
        protected String cabinSubtype;
        @XmlAttribute(name="Cabin")
        protected CabinType cabin;
        @XmlAttribute(name="PreferLevel")
        protected PreferLevelType preferLevel;

        public String getCabinSubtype() {
            return this.cabinSubtype;
        }

        public void setCabinSubtype(String value) {
            this.cabinSubtype = value;
        }

        public CabinType getCabin() {
            return this.cabin;
        }

        public void setCabin(CabinType value) {
            this.cabin = value;
        }

        public PreferLevelType getPreferLevel() {
            return this.preferLevel;
        }

        public void setPreferLevel(PreferLevelType value) {
            this.preferLevel = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class BookingSeatPref {
        @XmlAttribute(name="SeatsNeeded")
        @XmlSchemaType(name="nonNegativeInteger")
        protected BigInteger seatsNeeded;

        public BigInteger getSeatsNeeded() {
            return this.seatsNeeded;
        }

        public void setSeatsNeeded(BigInteger value) {
            this.seatsNeeded = value;
        }
    }
}

