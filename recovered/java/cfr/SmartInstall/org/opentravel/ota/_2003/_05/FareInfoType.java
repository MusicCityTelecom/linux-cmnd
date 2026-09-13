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
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;
import org.opentravel.ota._2003._05.ActionType;
import org.opentravel.ota._2003._05.AirItineraryPricingInfoType;
import org.opentravel.ota._2003._05.AirTripType;
import org.opentravel.ota._2003._05.CompanyNameType;
import org.opentravel.ota._2003._05.FareStatusType;
import org.opentravel.ota._2003._05.GlobalIndicatorType;
import org.opentravel.ota._2003._05.LocationType;
import org.opentravel.ota._2003._05.PTCFareBreakdownType;
import org.opentravel.ota._2003._05.RuleInfoType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="FareInfoType", propOrder={"departureDate", "fareReference", "ruleInfo", "filingAirline", "marketingAirline", "departureAirport", "arrivalAirport", "date", "fareInfo", "discountPricing", "city", "airport"})
@XmlSeeAlso(value={AirItineraryPricingInfoType.FareInfos.FareInfo.class, PTCFareBreakdownType.FareInfo.class})
public class FareInfoType {
    @XmlElement(name="DepartureDate")
    @XmlSchemaType(name="dateTime")
    protected XMLGregorianCalendar departureDate;
    @XmlElement(name="FareReference")
    protected List<FareReference> fareReference;
    @XmlElement(name="RuleInfo")
    protected RuleInfo ruleInfo;
    @XmlElement(name="FilingAirline")
    protected CompanyNameType filingAirline;
    @XmlElement(name="MarketingAirline")
    protected List<CompanyNameType> marketingAirline;
    @XmlElement(name="DepartureAirport")
    protected LocationType departureAirport;
    @XmlElement(name="ArrivalAirport")
    protected LocationType arrivalAirport;
    @XmlElement(name="Date")
    protected List<Date> date;
    @XmlElement(name="FareInfo")
    protected List<FareInfo> fareInfo;
    @XmlElement(name="DiscountPricing")
    protected DiscountPricing discountPricing;
    @XmlElement(name="City")
    protected List<City> city;
    @XmlElement(name="Airport")
    protected List<Airport> airport;
    @XmlAttribute(name="CurrencyCode")
    protected String currencyCode;
    @XmlAttribute(name="TariffNumber")
    protected String tariffNumber;
    @XmlAttribute(name="RuleNumber")
    protected String ruleNumber;
    @XmlAttribute(name="RoutingNumber")
    protected Integer routingNumber;
    @XmlAttribute(name="NbrOfCities")
    protected Integer nbrOfCities;
    @XmlAttribute(name="NegotiatedFare")
    protected Boolean negotiatedFare;
    @XmlAttribute(name="NegotiatedFareCode")
    protected String negotiatedFareCode;

    public XMLGregorianCalendar getDepartureDate() {
        return this.departureDate;
    }

    public void setDepartureDate(XMLGregorianCalendar value) {
        this.departureDate = value;
    }

    public List<FareReference> getFareReference() {
        if (this.fareReference == null) {
            this.fareReference = new ArrayList<FareReference>();
        }
        return this.fareReference;
    }

    public RuleInfo getRuleInfo() {
        return this.ruleInfo;
    }

    public void setRuleInfo(RuleInfo value) {
        this.ruleInfo = value;
    }

    public CompanyNameType getFilingAirline() {
        return this.filingAirline;
    }

    public void setFilingAirline(CompanyNameType value) {
        this.filingAirline = value;
    }

    public List<CompanyNameType> getMarketingAirline() {
        if (this.marketingAirline == null) {
            this.marketingAirline = new ArrayList<CompanyNameType>();
        }
        return this.marketingAirline;
    }

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

    public List<Date> getDate() {
        if (this.date == null) {
            this.date = new ArrayList<Date>();
        }
        return this.date;
    }

    public List<FareInfo> getFareInfo() {
        if (this.fareInfo == null) {
            this.fareInfo = new ArrayList<FareInfo>();
        }
        return this.fareInfo;
    }

    public DiscountPricing getDiscountPricing() {
        return this.discountPricing;
    }

    public void setDiscountPricing(DiscountPricing value) {
        this.discountPricing = value;
    }

    public List<City> getCity() {
        if (this.city == null) {
            this.city = new ArrayList<City>();
        }
        return this.city;
    }

    public List<Airport> getAirport() {
        if (this.airport == null) {
            this.airport = new ArrayList<Airport>();
        }
        return this.airport;
    }

    public String getCurrencyCode() {
        return this.currencyCode;
    }

    public void setCurrencyCode(String value) {
        this.currencyCode = value;
    }

    public String getTariffNumber() {
        return this.tariffNumber;
    }

    public void setTariffNumber(String value) {
        this.tariffNumber = value;
    }

    public String getRuleNumber() {
        return this.ruleNumber;
    }

    public void setRuleNumber(String value) {
        this.ruleNumber = value;
    }

    public Integer getRoutingNumber() {
        return this.routingNumber;
    }

    public void setRoutingNumber(Integer value) {
        this.routingNumber = value;
    }

    public Integer getNbrOfCities() {
        return this.nbrOfCities;
    }

    public void setNbrOfCities(Integer value) {
        this.nbrOfCities = value;
    }

    public Boolean isNegotiatedFare() {
        return this.negotiatedFare;
    }

    public void setNegotiatedFare(Boolean value) {
        this.negotiatedFare = value;
    }

    public String getNegotiatedFareCode() {
        return this.negotiatedFareCode;
    }

    public void setNegotiatedFareCode(String value) {
        this.negotiatedFareCode = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class RuleInfo
    extends RuleInfoType {
        @XmlAttribute(name="TripType")
        protected AirTripType tripType;
        @XmlAttribute(name="MoneySaverInd")
        protected Boolean moneySaverInd;

        public AirTripType getTripType() {
            return this.tripType;
        }

        public void setTripType(AirTripType value) {
            this.tripType = value;
        }

        public Boolean isMoneySaverInd() {
            return this.moneySaverInd;
        }

        public void setMoneySaverInd(Boolean value) {
            this.moneySaverInd = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"value"})
    public static class FareReference {
        @XmlValue
        protected String value;
        @XmlAttribute(name="ResBookDesigCode")
        protected String resBookDesigCode;
        @XmlAttribute(name="TicketDesignatorCode")
        protected String ticketDesignatorCode;
        @XmlAttribute(name="AccountCode")
        protected String accountCode;

        public String getValue() {
            return this.value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getResBookDesigCode() {
            return this.resBookDesigCode;
        }

        public void setResBookDesigCode(String value) {
            this.resBookDesigCode = value;
        }

        public String getTicketDesignatorCode() {
            return this.ticketDesignatorCode;
        }

        public void setTicketDesignatorCode(String value) {
            this.ticketDesignatorCode = value;
        }

        public String getAccountCode() {
            return this.accountCode;
        }

        public void setAccountCode(String value) {
            this.accountCode = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"date", "fare", "ptc"})
    public static class FareInfo {
        @XmlElement(name="Date")
        protected List<Date> date;
        @XmlElement(name="Fare")
        protected Fare fare;
        @XmlElement(name="PTC")
        protected List<PTC> ptc;
        @XmlAttribute(name="FareBasisCode")
        protected String fareBasisCode;
        @XmlAttribute(name="GlobalIndicatorCode")
        protected GlobalIndicatorType globalIndicatorCode;
        @XmlAttribute(name="MaximumPermittedMileage")
        protected BigInteger maximumPermittedMileage;
        @XmlAttribute(name="TripType")
        protected AirTripType tripType;
        @XmlAttribute(name="FareType")
        protected String fareType;
        @XmlAttribute(name="FareStatus")
        protected FareStatusType fareStatus;
        @XmlAttribute(name="Operation")
        protected ActionType operation;
        @XmlAttribute(name="RPH")
        protected String rph;

        public List<Date> getDate() {
            if (this.date == null) {
                this.date = new ArrayList<Date>();
            }
            return this.date;
        }

        public Fare getFare() {
            return this.fare;
        }

        public void setFare(Fare value) {
            this.fare = value;
        }

        public List<PTC> getPTC() {
            if (this.ptc == null) {
                this.ptc = new ArrayList<PTC>();
            }
            return this.ptc;
        }

        public String getFareBasisCode() {
            return this.fareBasisCode;
        }

        public void setFareBasisCode(String value) {
            this.fareBasisCode = value;
        }

        public GlobalIndicatorType getGlobalIndicatorCode() {
            return this.globalIndicatorCode;
        }

        public void setGlobalIndicatorCode(GlobalIndicatorType value) {
            this.globalIndicatorCode = value;
        }

        public BigInteger getMaximumPermittedMileage() {
            return this.maximumPermittedMileage;
        }

        public void setMaximumPermittedMileage(BigInteger value) {
            this.maximumPermittedMileage = value;
        }

        public AirTripType getTripType() {
            return this.tripType;
        }

        public void setTripType(AirTripType value) {
            this.tripType = value;
        }

        public String getFareType() {
            return this.fareType;
        }

        public void setFareType(String value) {
            this.fareType = value;
        }

        public FareStatusType getFareStatus() {
            return this.fareStatus;
        }

        public void setFareStatus(FareStatusType value) {
            this.fareStatus = value;
        }

        public ActionType getOperation() {
            return this.operation;
        }

        public void setOperation(ActionType value) {
            this.operation = value;
        }

        public String getRPH() {
            return this.rph;
        }

        public void setRPH(String value) {
            this.rph = value;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="")
        public static class PTC {
            @XmlAttribute(name="PassengerTypeCode")
            protected String passengerTypeCode;

            public String getPassengerTypeCode() {
                return this.passengerTypeCode;
            }

            public void setPassengerTypeCode(String value) {
                this.passengerTypeCode = value;
            }
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="")
        public static class Fare {
            @XmlAttribute(name="BaseAmount")
            protected BigDecimal baseAmount;
            @XmlAttribute(name="BaseNUC_Amount")
            protected BigDecimal baseNUCAmount;
            @XmlAttribute(name="TaxAmount")
            protected BigDecimal taxAmount;
            @XmlAttribute(name="TotalFare")
            protected BigDecimal totalFare;
            @XmlAttribute(name="FareDescription")
            protected String fareDescription;

            public BigDecimal getBaseAmount() {
                return this.baseAmount;
            }

            public void setBaseAmount(BigDecimal value) {
                this.baseAmount = value;
            }

            public BigDecimal getBaseNUCAmount() {
                return this.baseNUCAmount;
            }

            public void setBaseNUCAmount(BigDecimal value) {
                this.baseNUCAmount = value;
            }

            public BigDecimal getTaxAmount() {
                return this.taxAmount;
            }

            public void setTaxAmount(BigDecimal value) {
                this.taxAmount = value;
            }

            public BigDecimal getTotalFare() {
                return this.totalFare;
            }

            public void setTotalFare(BigDecimal value) {
                this.totalFare = value;
            }

            public String getFareDescription() {
                return this.fareDescription;
            }

            public void setFareDescription(String value) {
                this.fareDescription = value;
            }
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="")
        public static class Date {
            @XmlAttribute(name="Date")
            protected String date;
            @XmlAttribute(name="Type")
            @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
            protected String type;

            public String getDate() {
                return this.date;
            }

            public void setDate(String value) {
                this.date = value;
            }

            public String getType() {
                return this.type;
            }

            public void setType(String value) {
                this.type = value;
            }
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class DiscountPricing {
        @XmlAttribute(name="Purpose")
        @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
        protected String purpose;
        @XmlAttribute(name="Type")
        @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
        protected String type;
        @XmlAttribute(name="Usage")
        @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
        protected String usage;
        @XmlAttribute(name="Discount")
        protected String discount;
        @XmlAttribute(name="TicketDesignatorCode")
        protected String ticketDesignatorCode;
        @XmlAttribute(name="Text")
        protected String text;

        public String getPurpose() {
            return this.purpose;
        }

        public void setPurpose(String value) {
            this.purpose = value;
        }

        public String getType() {
            return this.type;
        }

        public void setType(String value) {
            this.type = value;
        }

        public String getUsage() {
            return this.usage;
        }

        public void setUsage(String value) {
            this.usage = value;
        }

        public String getDiscount() {
            return this.discount;
        }

        public void setDiscount(String value) {
            this.discount = value;
        }

        public String getTicketDesignatorCode() {
            return this.ticketDesignatorCode;
        }

        public void setTicketDesignatorCode(String value) {
            this.ticketDesignatorCode = value;
        }

        public String getText() {
            return this.text;
        }

        public void setText(String value) {
            this.text = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class Date {
        @XmlAttribute(name="Date")
        protected String date;
        @XmlAttribute(name="Type")
        @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
        protected String type;

        public String getDate() {
            return this.date;
        }

        public void setDate(String value) {
            this.date = value;
        }

        public String getType() {
            return this.type;
        }

        public void setType(String value) {
            this.type = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class City {
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
    public static class Airport {
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

