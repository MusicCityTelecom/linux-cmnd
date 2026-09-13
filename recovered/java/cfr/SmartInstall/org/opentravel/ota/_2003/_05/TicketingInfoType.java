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
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;
import org.opentravel.ota._2003._05.ActionType;
import org.opentravel.ota._2003._05.FreeTextType;
import org.opentravel.ota._2003._05.TicketType;
import org.opentravel.ota._2003._05.TicketingInfoRSType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="TicketingInfoType", propOrder={"ticketAdvisory", "ticketingVendor", "pricingSystem", "totalFare"})
@XmlSeeAlso(value={TicketingInfoRSType.class})
public class TicketingInfoType {
    @XmlElement(name="TicketAdvisory")
    protected List<TicketAdvisory> ticketAdvisory;
    @XmlElement(name="TicketingVendor")
    protected TicketingVendor ticketingVendor;
    @XmlElement(name="PricingSystem")
    protected PricingSystem pricingSystem;
    @XmlElement(name="TotalFare")
    protected TotalFare totalFare;
    @XmlAttribute(name="TicketTimeLimit")
    @XmlSchemaType(name="dateTime")
    protected XMLGregorianCalendar ticketTimeLimit;
    @XmlAttribute(name="CancelOnExpiryInd")
    protected Boolean cancelOnExpiryInd;
    @XmlAttribute(name="TicketType")
    protected TicketType ticketType;
    @XmlAttribute(name="TicketingStatus")
    protected String ticketingStatus;
    @XmlAttribute(name="FlightSegmentRefNumber")
    protected List<String> flightSegmentRefNumber;
    @XmlAttribute(name="TravelerRefNumber")
    protected List<String> travelerRefNumber;
    @XmlAttribute(name="ReverseTktgSegmentsInd")
    protected Boolean reverseTktgSegmentsInd;
    @XmlAttribute(name="PseudoCityCode")
    protected String pseudoCityCode;
    @XmlAttribute(name="RequestedTicketingDate")
    protected String requestedTicketingDate;
    @XmlAttribute(name="TimeLimitMinutes")
    protected Integer timeLimitMinutes;
    @XmlAttribute(name="BookingChangeType")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    protected String bookingChangeType;
    @XmlAttribute(name="TicketDocumentNbr")
    protected String ticketDocumentNbr;
    @XmlAttribute(name="PassengerTypeCode")
    protected String passengerTypeCode;
    @XmlAttribute(name="Operation")
    protected ActionType operation;
    @XmlAttribute(name="MiscTicketingCode")
    protected List<String> miscTicketingCode;

    public List<TicketAdvisory> getTicketAdvisory() {
        if (this.ticketAdvisory == null) {
            this.ticketAdvisory = new ArrayList<TicketAdvisory>();
        }
        return this.ticketAdvisory;
    }

    public TicketingVendor getTicketingVendor() {
        return this.ticketingVendor;
    }

    public void setTicketingVendor(TicketingVendor value) {
        this.ticketingVendor = value;
    }

    public PricingSystem getPricingSystem() {
        return this.pricingSystem;
    }

    public void setPricingSystem(PricingSystem value) {
        this.pricingSystem = value;
    }

    public TotalFare getTotalFare() {
        return this.totalFare;
    }

    public void setTotalFare(TotalFare value) {
        this.totalFare = value;
    }

    public XMLGregorianCalendar getTicketTimeLimit() {
        return this.ticketTimeLimit;
    }

    public void setTicketTimeLimit(XMLGregorianCalendar value) {
        this.ticketTimeLimit = value;
    }

    public Boolean isCancelOnExpiryInd() {
        return this.cancelOnExpiryInd;
    }

    public void setCancelOnExpiryInd(Boolean value) {
        this.cancelOnExpiryInd = value;
    }

    public TicketType getTicketType() {
        return this.ticketType;
    }

    public void setTicketType(TicketType value) {
        this.ticketType = value;
    }

    public String getTicketingStatus() {
        return this.ticketingStatus;
    }

    public void setTicketingStatus(String value) {
        this.ticketingStatus = value;
    }

    public List<String> getFlightSegmentRefNumber() {
        if (this.flightSegmentRefNumber == null) {
            this.flightSegmentRefNumber = new ArrayList<String>();
        }
        return this.flightSegmentRefNumber;
    }

    public List<String> getTravelerRefNumber() {
        if (this.travelerRefNumber == null) {
            this.travelerRefNumber = new ArrayList<String>();
        }
        return this.travelerRefNumber;
    }

    public Boolean isReverseTktgSegmentsInd() {
        return this.reverseTktgSegmentsInd;
    }

    public void setReverseTktgSegmentsInd(Boolean value) {
        this.reverseTktgSegmentsInd = value;
    }

    public String getPseudoCityCode() {
        return this.pseudoCityCode;
    }

    public void setPseudoCityCode(String value) {
        this.pseudoCityCode = value;
    }

    public String getRequestedTicketingDate() {
        return this.requestedTicketingDate;
    }

    public void setRequestedTicketingDate(String value) {
        this.requestedTicketingDate = value;
    }

    public Integer getTimeLimitMinutes() {
        return this.timeLimitMinutes;
    }

    public void setTimeLimitMinutes(Integer value) {
        this.timeLimitMinutes = value;
    }

    public String getBookingChangeType() {
        return this.bookingChangeType;
    }

    public void setBookingChangeType(String value) {
        this.bookingChangeType = value;
    }

    public String getTicketDocumentNbr() {
        return this.ticketDocumentNbr;
    }

    public void setTicketDocumentNbr(String value) {
        this.ticketDocumentNbr = value;
    }

    public String getPassengerTypeCode() {
        return this.passengerTypeCode;
    }

    public void setPassengerTypeCode(String value) {
        this.passengerTypeCode = value;
    }

    public ActionType getOperation() {
        return this.operation;
    }

    public void setOperation(ActionType value) {
        this.operation = value;
    }

    public List<String> getMiscTicketingCode() {
        if (this.miscTicketingCode == null) {
            this.miscTicketingCode = new ArrayList<String>();
        }
        return this.miscTicketingCode;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class TotalFare {
        @XmlAttribute(name="Amount")
        protected BigDecimal amount;
        @XmlAttribute(name="CurrencyCode")
        protected String currencyCode;
        @XmlAttribute(name="DecimalPlaces")
        @XmlSchemaType(name="nonNegativeInteger")
        protected BigInteger decimalPlaces;

        public BigDecimal getAmount() {
            return this.amount;
        }

        public void setAmount(BigDecimal value) {
            this.amount = value;
        }

        public String getCurrencyCode() {
            return this.currencyCode;
        }

        public void setCurrencyCode(String value) {
            this.currencyCode = value;
        }

        public BigInteger getDecimalPlaces() {
            return this.decimalPlaces;
        }

        public void setDecimalPlaces(BigInteger value) {
            this.decimalPlaces = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class TicketingVendor {
        @XmlAttribute(name="CompanyShortName")
        protected String companyShortName;
        @XmlAttribute(name="TravelSector")
        protected String travelSector;
        @XmlAttribute(name="Code")
        protected String code;
        @XmlAttribute(name="CodeContext")
        protected String codeContext;

        public String getCompanyShortName() {
            return this.companyShortName;
        }

        public void setCompanyShortName(String value) {
            this.companyShortName = value;
        }

        public String getTravelSector() {
            return this.travelSector;
        }

        public void setTravelSector(String value) {
            this.travelSector = value;
        }

        public String getCode() {
            return this.code;
        }

        public void setCode(String value) {
            this.code = value;
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
    public static class TicketAdvisory
    extends FreeTextType {
        @XmlAttribute(name="Operation")
        protected ActionType operation;

        public ActionType getOperation() {
            return this.operation;
        }

        public void setOperation(ActionType value) {
            this.operation = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class PricingSystem {
        @XmlAttribute(name="CompanyShortName")
        protected String companyShortName;
        @XmlAttribute(name="TravelSector")
        protected String travelSector;
        @XmlAttribute(name="Code")
        protected String code;
        @XmlAttribute(name="CodeContext")
        protected String codeContext;

        public String getCompanyShortName() {
            return this.companyShortName;
        }

        public void setCompanyShortName(String value) {
            this.companyShortName = value;
        }

        public String getTravelSector() {
            return this.travelSector;
        }

        public void setTravelSector(String value) {
            this.travelSector = value;
        }

        public String getCode() {
            return this.code;
        }

        public void setCode(String value) {
            this.code = value;
        }

        public String getCodeContext() {
            return this.codeContext;
        }

        public void setCodeContext(String value) {
            this.codeContext = value;
        }
    }
}

