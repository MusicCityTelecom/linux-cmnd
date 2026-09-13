/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.ActionType;
import org.opentravel.ota._2003._05.AirItineraryType;
import org.opentravel.ota._2003._05.BookingPriceInfoType;
import org.opentravel.ota._2003._05.EMDType;
import org.opentravel.ota._2003._05.FormattedTextTextType;
import org.opentravel.ota._2003._05.FreeTextType;
import org.opentravel.ota._2003._05.FulfillmentType;
import org.opentravel.ota._2003._05.TicketingInfoType;
import org.opentravel.ota._2003._05.TravelerInfoType;
import org.opentravel.ota._2003._05.UniqueIDType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="AirReservationType", propOrder={"airItinerary", "priceInfo", "travelerInfo", "fulfillment", "ticketing", "queues", "bookingReferenceID", "comment", "pricingOverview", "emdInfo"})
public class AirReservationType {
    @XmlElement(name="AirItinerary")
    protected AirItineraryType airItinerary;
    @XmlElement(name="PriceInfo")
    protected BookingPriceInfoType priceInfo;
    @XmlElement(name="TravelerInfo")
    protected TravelerInfoType travelerInfo;
    @XmlElement(name="Fulfillment")
    protected FulfillmentType fulfillment;
    @XmlElement(name="Ticketing")
    protected List<TicketingInfoType> ticketing;
    @XmlElement(name="Queues")
    protected Queues queues;
    @XmlElement(name="BookingReferenceID")
    protected List<BookingReferenceID> bookingReferenceID;
    @XmlElement(name="Comment")
    protected FormattedTextTextType comment;
    @XmlElement(name="PricingOverview")
    protected PricingOverview pricingOverview;
    @XmlElement(name="EMD_Info")
    protected EMDType emdInfo;
    @XmlAttribute(name="LastModified")
    protected String lastModified;

    public AirItineraryType getAirItinerary() {
        return this.airItinerary;
    }

    public void setAirItinerary(AirItineraryType value) {
        this.airItinerary = value;
    }

    public BookingPriceInfoType getPriceInfo() {
        return this.priceInfo;
    }

    public void setPriceInfo(BookingPriceInfoType value) {
        this.priceInfo = value;
    }

    public TravelerInfoType getTravelerInfo() {
        return this.travelerInfo;
    }

    public void setTravelerInfo(TravelerInfoType value) {
        this.travelerInfo = value;
    }

    public FulfillmentType getFulfillment() {
        return this.fulfillment;
    }

    public void setFulfillment(FulfillmentType value) {
        this.fulfillment = value;
    }

    public List<TicketingInfoType> getTicketing() {
        if (this.ticketing == null) {
            this.ticketing = new ArrayList<TicketingInfoType>();
        }
        return this.ticketing;
    }

    public Queues getQueues() {
        return this.queues;
    }

    public void setQueues(Queues value) {
        this.queues = value;
    }

    public List<BookingReferenceID> getBookingReferenceID() {
        if (this.bookingReferenceID == null) {
            this.bookingReferenceID = new ArrayList<BookingReferenceID>();
        }
        return this.bookingReferenceID;
    }

    public FormattedTextTextType getComment() {
        return this.comment;
    }

    public void setComment(FormattedTextTextType value) {
        this.comment = value;
    }

    public PricingOverview getPricingOverview() {
        return this.pricingOverview;
    }

    public void setPricingOverview(PricingOverview value) {
        this.pricingOverview = value;
    }

    public EMDType getEMDInfo() {
        return this.emdInfo;
    }

    public void setEMDInfo(EMDType value) {
        this.emdInfo = value;
    }

    public String getLastModified() {
        return this.lastModified;
    }

    public void setLastModified(String value) {
        this.lastModified = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"queue"})
    public static class Queues {
        @XmlElement(name="Queue", required=true)
        protected List<Queue> queue;

        public List<Queue> getQueue() {
            if (this.queue == null) {
                this.queue = new ArrayList<Queue>();
            }
            return this.queue;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="")
        public static class Queue {
            @XmlAttribute(name="DateTime")
            protected String dateTime;
            @XmlAttribute(name="Text")
            protected String text;
            @XmlAttribute(name="CarrierCode")
            protected String carrierCode;
            @XmlAttribute(name="Operation")
            protected ActionType operation;
            @XmlAttribute(name="PseudoCityCode")
            protected String pseudoCityCode;
            @XmlAttribute(name="QueueNumber")
            protected String queueNumber;
            @XmlAttribute(name="QueueCategory")
            protected String queueCategory;
            @XmlAttribute(name="SystemCode")
            protected String systemCode;
            @XmlAttribute(name="QueueID")
            protected String queueID;

            public String getDateTime() {
                return this.dateTime;
            }

            public void setDateTime(String value) {
                this.dateTime = value;
            }

            public String getText() {
                return this.text;
            }

            public void setText(String value) {
                this.text = value;
            }

            public String getCarrierCode() {
                return this.carrierCode;
            }

            public void setCarrierCode(String value) {
                this.carrierCode = value;
            }

            public ActionType getOperation() {
                return this.operation;
            }

            public void setOperation(ActionType value) {
                this.operation = value;
            }

            public String getPseudoCityCode() {
                return this.pseudoCityCode;
            }

            public void setPseudoCityCode(String value) {
                this.pseudoCityCode = value;
            }

            public String getQueueNumber() {
                return this.queueNumber;
            }

            public void setQueueNumber(String value) {
                this.queueNumber = value;
            }

            public String getQueueCategory() {
                return this.queueCategory;
            }

            public void setQueueCategory(String value) {
                this.queueCategory = value;
            }

            public String getSystemCode() {
                return this.systemCode;
            }

            public void setSystemCode(String value) {
                this.systemCode = value;
            }

            public String getQueueID() {
                return this.queueID;
            }

            public void setQueueID(String value) {
                this.queueID = value;
            }
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"pricingIndicator", "account", "comment"})
    public static class PricingOverview {
        @XmlElement(name="PricingIndicator")
        protected List<PricingIndicator> pricingIndicator;
        @XmlElement(name="Account")
        protected List<Account> account;
        @XmlElement(name="Comment")
        protected List<FreeTextType> comment;
        @XmlAttribute(name="StatisticalCode")
        protected String statisticalCode;
        @XmlAttribute(name="ValidatingAirlineCode")
        protected String validatingAirlineCode;
        @XmlAttribute(name="DepartureDate")
        protected String departureDate;
        @XmlAttribute(name="PriceType")
        protected String priceType;
        @XmlAttribute(name="NUC_Rate")
        protected BigDecimal nucRate;
        @XmlAttribute(name="ExchangeRate")
        protected BigDecimal exchangeRate;

        public List<PricingIndicator> getPricingIndicator() {
            if (this.pricingIndicator == null) {
                this.pricingIndicator = new ArrayList<PricingIndicator>();
            }
            return this.pricingIndicator;
        }

        public List<Account> getAccount() {
            if (this.account == null) {
                this.account = new ArrayList<Account>();
            }
            return this.account;
        }

        public List<FreeTextType> getComment() {
            if (this.comment == null) {
                this.comment = new ArrayList<FreeTextType>();
            }
            return this.comment;
        }

        public String getStatisticalCode() {
            return this.statisticalCode;
        }

        public void setStatisticalCode(String value) {
            this.statisticalCode = value;
        }

        public String getValidatingAirlineCode() {
            return this.validatingAirlineCode;
        }

        public void setValidatingAirlineCode(String value) {
            this.validatingAirlineCode = value;
        }

        public String getDepartureDate() {
            return this.departureDate;
        }

        public void setDepartureDate(String value) {
            this.departureDate = value;
        }

        public String getPriceType() {
            return this.priceType;
        }

        public void setPriceType(String value) {
            this.priceType = value;
        }

        public BigDecimal getNUCRate() {
            return this.nucRate;
        }

        public void setNUCRate(BigDecimal value) {
            this.nucRate = value;
        }

        public BigDecimal getExchangeRate() {
            return this.exchangeRate;
        }

        public void setExchangeRate(BigDecimal value) {
            this.exchangeRate = value;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="")
        public static class PricingIndicator {
            @XmlAttribute(name="Type", required=true)
            protected String type;
            @XmlAttribute(name="ExcludeInd")
            protected Boolean excludeInd;
            @XmlAttribute(name="Qualifier")
            protected String qualifier;

            public String getType() {
                return this.type;
            }

            public void setType(String value) {
                this.type = value;
            }

            public Boolean isExcludeInd() {
                return this.excludeInd;
            }

            public void setExcludeInd(Boolean value) {
                this.excludeInd = value;
            }

            public String getQualifier() {
                return this.qualifier;
            }

            public void setQualifier(String value) {
                this.qualifier = value;
            }
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="")
        public static class Account {
            @XmlAttribute(name="Code", required=true)
            protected String code;

            public String getCode() {
                return this.code;
            }

            public void setCode(String value) {
                this.code = value;
            }
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class BookingReferenceID
    extends UniqueIDType {
        @XmlAttribute(name="FlightRefNumberRPHList")
        protected List<String> flightRefNumberRPHList;

        public List<String> getFlightRefNumberRPHList() {
            if (this.flightRefNumberRPHList == null) {
                this.flightRefNumberRPHList = new ArrayList<String>();
            }
            return this.flightRefNumberRPHList;
        }
    }
}

