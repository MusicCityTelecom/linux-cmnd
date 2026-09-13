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
import org.opentravel.ota._2003._05.AirFeeType;
import org.opentravel.ota._2003._05.AirItineraryPricingInfoType;
import org.opentravel.ota._2003._05.AirTaxType;
import org.opentravel.ota._2003._05.PTCFareBreakdownType;
import org.opentravel.ota._2003._05.TPAExtensionsType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="FareType", propOrder={"baseFare", "equivFare", "taxes", "fees", "totalFare", "fareConstruction", "unstructuredFareCalc", "fareBaggageAllowance", "tourCode", "remark", "originalIssueInfo", "exchangeInfo", "discounts", "tpaExtensions"})
@XmlSeeAlso(value={AirItineraryPricingInfoType.ItinTotalFare.class, PTCFareBreakdownType.PassengerFare.class})
public class FareType {
    @XmlElement(name="BaseFare")
    protected BaseFare baseFare;
    @XmlElement(name="EquivFare")
    protected List<EquivFare> equivFare;
    @XmlElement(name="Taxes")
    protected Taxes taxes;
    @XmlElement(name="Fees")
    protected Fees fees;
    @XmlElement(name="TotalFare")
    protected TotalFare totalFare;
    @XmlElement(name="FareConstruction")
    protected FareConstruction fareConstruction;
    @XmlElement(name="UnstructuredFareCalc")
    protected UnstructuredFareCalc unstructuredFareCalc;
    @XmlElement(name="FareBaggageAllowance")
    protected List<FareBaggageAllowance> fareBaggageAllowance;
    @XmlElement(name="TourCode")
    protected TourCode tourCode;
    @XmlElement(name="Remark")
    protected List<Remark> remark;
    @XmlElement(name="OriginalIssueInfo")
    protected OriginalIssueInfo originalIssueInfo;
    @XmlElement(name="ExchangeInfo")
    protected ExchangeInfo exchangeInfo;
    @XmlElement(name="Discounts")
    protected Discounts discounts;
    @XmlElement(name="TPA_Extensions")
    protected TPAExtensionsType tpaExtensions;
    @XmlAttribute(name="TicketDesignatorCode")
    protected String ticketDesignatorCode;
    @XmlAttribute(name="TotalNbrTrips")
    protected Integer totalNbrTrips;
    @XmlAttribute(name="TotalNbrPTC")
    protected Integer totalNbrPTC;
    @XmlAttribute(name="NegotiatedFare")
    protected Boolean negotiatedFare;
    @XmlAttribute(name="NegotiatedFareCode")
    protected String negotiatedFareCode;

    public BaseFare getBaseFare() {
        return this.baseFare;
    }

    public void setBaseFare(BaseFare value) {
        this.baseFare = value;
    }

    public List<EquivFare> getEquivFare() {
        if (this.equivFare == null) {
            this.equivFare = new ArrayList<EquivFare>();
        }
        return this.equivFare;
    }

    public Taxes getTaxes() {
        return this.taxes;
    }

    public void setTaxes(Taxes value) {
        this.taxes = value;
    }

    public Fees getFees() {
        return this.fees;
    }

    public void setFees(Fees value) {
        this.fees = value;
    }

    public TotalFare getTotalFare() {
        return this.totalFare;
    }

    public void setTotalFare(TotalFare value) {
        this.totalFare = value;
    }

    public FareConstruction getFareConstruction() {
        return this.fareConstruction;
    }

    public void setFareConstruction(FareConstruction value) {
        this.fareConstruction = value;
    }

    public UnstructuredFareCalc getUnstructuredFareCalc() {
        return this.unstructuredFareCalc;
    }

    public void setUnstructuredFareCalc(UnstructuredFareCalc value) {
        this.unstructuredFareCalc = value;
    }

    public List<FareBaggageAllowance> getFareBaggageAllowance() {
        if (this.fareBaggageAllowance == null) {
            this.fareBaggageAllowance = new ArrayList<FareBaggageAllowance>();
        }
        return this.fareBaggageAllowance;
    }

    public TourCode getTourCode() {
        return this.tourCode;
    }

    public void setTourCode(TourCode value) {
        this.tourCode = value;
    }

    public List<Remark> getRemark() {
        if (this.remark == null) {
            this.remark = new ArrayList<Remark>();
        }
        return this.remark;
    }

    public OriginalIssueInfo getOriginalIssueInfo() {
        return this.originalIssueInfo;
    }

    public void setOriginalIssueInfo(OriginalIssueInfo value) {
        this.originalIssueInfo = value;
    }

    public ExchangeInfo getExchangeInfo() {
        return this.exchangeInfo;
    }

    public void setExchangeInfo(ExchangeInfo value) {
        this.exchangeInfo = value;
    }

    public Discounts getDiscounts() {
        return this.discounts;
    }

    public void setDiscounts(Discounts value) {
        this.discounts = value;
    }

    public TPAExtensionsType getTPAExtensions() {
        return this.tpaExtensions;
    }

    public void setTPAExtensions(TPAExtensionsType value) {
        this.tpaExtensions = value;
    }

    public String getTicketDesignatorCode() {
        return this.ticketDesignatorCode;
    }

    public void setTicketDesignatorCode(String value) {
        this.ticketDesignatorCode = value;
    }

    public Integer getTotalNbrTrips() {
        return this.totalNbrTrips;
    }

    public void setTotalNbrTrips(Integer value) {
        this.totalNbrTrips = value;
    }

    public Integer getTotalNbrPTC() {
        return this.totalNbrPTC;
    }

    public void setTotalNbrPTC(Integer value) {
        this.totalNbrPTC = value;
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
    @XmlType(name="", propOrder={"value"})
    public static class UnstructuredFareCalc {
        @XmlValue
        protected String value;
        @XmlAttribute(name="FareCalcMode")
        protected String fareCalcMode;
        @XmlAttribute(name="Operation")
        protected ActionType operation;

        public String getValue() {
            return this.value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getFareCalcMode() {
            return this.fareCalcMode;
        }

        public void setFareCalcMode(String value) {
            this.fareCalcMode = value;
        }

        public ActionType getOperation() {
            return this.operation;
        }

        public void setOperation(ActionType value) {
            this.operation = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"value"})
    public static class TourCode {
        @XmlValue
        protected String value;
        @XmlAttribute(name="Operation")
        protected ActionType operation;

        public String getValue() {
            return this.value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public ActionType getOperation() {
            return this.operation;
        }

        public void setOperation(ActionType value) {
            this.operation = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class TotalFare {
        @XmlAttribute(name="Operation")
        protected ActionType operation;
        @XmlAttribute(name="Amount")
        protected BigDecimal amount;
        @XmlAttribute(name="CurrencyCode")
        protected String currencyCode;
        @XmlAttribute(name="DecimalPlaces")
        @XmlSchemaType(name="nonNegativeInteger")
        protected BigInteger decimalPlaces;

        public ActionType getOperation() {
            return this.operation;
        }

        public void setOperation(ActionType value) {
            this.operation = value;
        }

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
    @XmlType(name="", propOrder={"tax"})
    public static class Taxes {
        @XmlElement(name="Tax", required=true)
        protected List<AirTaxType> tax;
        @XmlAttribute(name="Amount")
        protected BigDecimal amount;

        public List<AirTaxType> getTax() {
            if (this.tax == null) {
                this.tax = new ArrayList<AirTaxType>();
            }
            return this.tax;
        }

        public BigDecimal getAmount() {
            return this.amount;
        }

        public void setAmount(BigDecimal value) {
            this.amount = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"value"})
    public static class Remark {
        @XmlValue
        protected String value;

        public String getValue() {
            return this.value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class OriginalIssueInfo {
        @XmlAttribute(name="TicketDocumentNbr")
        protected String ticketDocumentNbr;
        @XmlAttribute(name="IssuingAgentID")
        protected String issuingAgentID;
        @XmlAttribute(name="DateOfIssue")
        @XmlSchemaType(name="date")
        protected XMLGregorianCalendar dateOfIssue;
        @XmlAttribute(name="LocationCode")
        protected String locationCode;
        @XmlAttribute(name="IssuingAirlineCode")
        protected String issuingAirlineCode;

        public String getTicketDocumentNbr() {
            return this.ticketDocumentNbr;
        }

        public void setTicketDocumentNbr(String value) {
            this.ticketDocumentNbr = value;
        }

        public String getIssuingAgentID() {
            return this.issuingAgentID;
        }

        public void setIssuingAgentID(String value) {
            this.issuingAgentID = value;
        }

        public XMLGregorianCalendar getDateOfIssue() {
            return this.dateOfIssue;
        }

        public void setDateOfIssue(XMLGregorianCalendar value) {
            this.dateOfIssue = value;
        }

        public String getLocationCode() {
            return this.locationCode;
        }

        public void setLocationCode(String value) {
            this.locationCode = value;
        }

        public String getIssuingAirlineCode() {
            return this.issuingAirlineCode;
        }

        public void setIssuingAirlineCode(String value) {
            this.issuingAirlineCode = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"fee"})
    public static class Fees {
        @XmlElement(name="Fee", required=true)
        protected List<AirFeeType> fee;
        @XmlAttribute(name="Amount")
        protected BigDecimal amount;
        @XmlAttribute(name="CurrencyCode")
        protected String currencyCode;
        @XmlAttribute(name="DecimalPlaces")
        @XmlSchemaType(name="nonNegativeInteger")
        protected BigInteger decimalPlaces;

        public List<AirFeeType> getFee() {
            if (this.fee == null) {
                this.fee = new ArrayList<AirFeeType>();
            }
            return this.fee;
        }

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
    public static class FareConstruction {
        @XmlAttribute(name="FormattedIndicator")
        protected Boolean formattedIndicator;
        @XmlAttribute(name="Language")
        @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
        @XmlSchemaType(name="language")
        protected String language;
        @XmlAttribute(name="OriginCityCode")
        protected String originCityCode;
        @XmlAttribute(name="OriginCodeContext")
        protected String originCodeContext;
        @XmlAttribute(name="DestinationCityCode")
        protected String destinationCityCode;
        @XmlAttribute(name="DestinationCodeContext")
        protected String destinationCodeContext;
        @XmlAttribute(name="Operation")
        protected ActionType operation;

        public Boolean isFormattedIndicator() {
            return this.formattedIndicator;
        }

        public void setFormattedIndicator(Boolean value) {
            this.formattedIndicator = value;
        }

        public String getLanguage() {
            return this.language;
        }

        public void setLanguage(String value) {
            this.language = value;
        }

        public String getOriginCityCode() {
            return this.originCityCode;
        }

        public void setOriginCityCode(String value) {
            this.originCityCode = value;
        }

        public String getOriginCodeContext() {
            return this.originCodeContext;
        }

        public void setOriginCodeContext(String value) {
            this.originCodeContext = value;
        }

        public String getDestinationCityCode() {
            return this.destinationCityCode;
        }

        public void setDestinationCityCode(String value) {
            this.destinationCityCode = value;
        }

        public String getDestinationCodeContext() {
            return this.destinationCodeContext;
        }

        public void setDestinationCodeContext(String value) {
            this.destinationCodeContext = value;
        }

        public ActionType getOperation() {
            return this.operation;
        }

        public void setOperation(ActionType value) {
            this.operation = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class FareBaggageAllowance {
        @XmlAttribute(name="FlightSegmentRPH")
        protected String flightSegmentRPH;
        @XmlAttribute(name="Operation")
        protected ActionType operation;
        @XmlAttribute(name="UnitOfMeasureQuantity")
        protected BigDecimal unitOfMeasureQuantity;
        @XmlAttribute(name="UnitOfMeasure")
        protected String unitOfMeasure;
        @XmlAttribute(name="UnitOfMeasureCode")
        protected String unitOfMeasureCode;

        public String getFlightSegmentRPH() {
            return this.flightSegmentRPH;
        }

        public void setFlightSegmentRPH(String value) {
            this.flightSegmentRPH = value;
        }

        public ActionType getOperation() {
            return this.operation;
        }

        public void setOperation(ActionType value) {
            this.operation = value;
        }

        public BigDecimal getUnitOfMeasureQuantity() {
            return this.unitOfMeasureQuantity;
        }

        public void setUnitOfMeasureQuantity(BigDecimal value) {
            this.unitOfMeasureQuantity = value;
        }

        public String getUnitOfMeasure() {
            return this.unitOfMeasure;
        }

        public void setUnitOfMeasure(String value) {
            this.unitOfMeasure = value;
        }

        public String getUnitOfMeasureCode() {
            return this.unitOfMeasureCode;
        }

        public void setUnitOfMeasureCode(String value) {
            this.unitOfMeasureCode = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"couponInfo", "originalOriginDestination"})
    public static class ExchangeInfo {
        @XmlElement(name="CouponInfo")
        protected List<CouponInfo> couponInfo;
        @XmlElement(name="OriginalOriginDestination")
        protected OriginalOriginDestination originalOriginDestination;
        @XmlAttribute(name="TicketDocumentNbr")
        protected String ticketDocumentNbr;

        public List<CouponInfo> getCouponInfo() {
            if (this.couponInfo == null) {
                this.couponInfo = new ArrayList<CouponInfo>();
            }
            return this.couponInfo;
        }

        public OriginalOriginDestination getOriginalOriginDestination() {
            return this.originalOriginDestination;
        }

        public void setOriginalOriginDestination(OriginalOriginDestination value) {
            this.originalOriginDestination = value;
        }

        public String getTicketDocumentNbr() {
            return this.ticketDocumentNbr;
        }

        public void setTicketDocumentNbr(String value) {
            this.ticketDocumentNbr = value;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="")
        public static class OriginalOriginDestination {
            @XmlAttribute(name="OriginCityCode")
            protected String originCityCode;
            @XmlAttribute(name="DestinationCityCode")
            protected String destinationCityCode;

            public String getOriginCityCode() {
                return this.originCityCode;
            }

            public void setOriginCityCode(String value) {
                this.originCityCode = value;
            }

            public String getDestinationCityCode() {
                return this.destinationCityCode;
            }

            public void setDestinationCityCode(String value) {
                this.destinationCityCode = value;
            }
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="")
        public static class CouponInfo {
            @XmlAttribute(name="Number")
            protected Integer number;

            public Integer getNumber() {
                return this.number;
            }

            public void setNumber(Integer value) {
                this.number = value;
            }
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class EquivFare {
        @XmlAttribute(name="Operation")
        protected ActionType operation;
        @XmlAttribute(name="Amount")
        protected BigDecimal amount;
        @XmlAttribute(name="CurrencyCode")
        protected String currencyCode;
        @XmlAttribute(name="DecimalPlaces")
        @XmlSchemaType(name="nonNegativeInteger")
        protected BigInteger decimalPlaces;

        public ActionType getOperation() {
            return this.operation;
        }

        public void setOperation(ActionType value) {
            this.operation = value;
        }

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
    @XmlType(name="", propOrder={"discount"})
    public static class Discounts {
        @XmlElement(name="Discount", required=true)
        protected List<Discount> discount;

        public List<Discount> getDiscount() {
            if (this.discount == null) {
                this.discount = new ArrayList<Discount>();
            }
            return this.discount;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="")
        public static class Discount {
            @XmlAttribute(name="Percent")
            protected BigDecimal percent;
            @XmlAttribute(name="ID")
            protected String id;
            @XmlAttribute(name="Description")
            protected String description;
            @XmlAttribute(name="Amount")
            protected BigDecimal amount;
            @XmlAttribute(name="CurrencyCode")
            protected String currencyCode;
            @XmlAttribute(name="DecimalPlaces")
            @XmlSchemaType(name="nonNegativeInteger")
            protected BigInteger decimalPlaces;

            public BigDecimal getPercent() {
                return this.percent;
            }

            public void setPercent(BigDecimal value) {
                this.percent = value;
            }

            public String getID() {
                return this.id;
            }

            public void setID(String value) {
                this.id = value;
            }

            public String getDescription() {
                return this.description;
            }

            public void setDescription(String value) {
                this.description = value;
            }

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
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class BaseFare {
        @XmlAttribute(name="Operation")
        protected ActionType operation;
        @XmlAttribute(name="FromCurrency")
        protected String fromCurrency;
        @XmlAttribute(name="ToCurrency")
        protected String toCurrency;
        @XmlAttribute(name="Rate")
        protected BigDecimal rate;
        @XmlAttribute(name="Date")
        @XmlSchemaType(name="date")
        protected XMLGregorianCalendar date;
        @XmlAttribute(name="Amount")
        protected BigDecimal amount;
        @XmlAttribute(name="CurrencyCode")
        protected String currencyCode;
        @XmlAttribute(name="DecimalPlaces")
        @XmlSchemaType(name="nonNegativeInteger")
        protected BigInteger decimalPlaces;

        public ActionType getOperation() {
            return this.operation;
        }

        public void setOperation(ActionType value) {
            this.operation = value;
        }

        public String getFromCurrency() {
            return this.fromCurrency;
        }

        public void setFromCurrency(String value) {
            this.fromCurrency = value;
        }

        public String getToCurrency() {
            return this.toCurrency;
        }

        public void setToCurrency(String value) {
            this.toCurrency = value;
        }

        public BigDecimal getRate() {
            return this.rate;
        }

        public void setRate(BigDecimal value) {
            this.rate = value;
        }

        public XMLGregorianCalendar getDate() {
            return this.date;
        }

        public void setDate(XMLGregorianCalendar value) {
            this.date = value;
        }

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
}

