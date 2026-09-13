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
import javax.xml.datatype.XMLGregorianCalendar;
import org.opentravel.ota._2003._05.BookedTrainSegmentType;
import org.opentravel.ota._2003._05.CompanyNameType;
import org.opentravel.ota._2003._05.LocationType;
import org.opentravel.ota._2003._05.MonetaryRuleType;
import org.opentravel.ota._2003._05.RailChargesType;
import org.opentravel.ota._2003._05.RailPassengerCategoryDetailType;
import org.opentravel.ota._2003._05.TPAExtensionsType;
import org.opentravel.ota._2003._05.UniqueIDType;
import org.opentravel.ota._2003._05.VendorMessagesType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="RailReservationType", propOrder={"uniqueID", "itinerary", "passengerInfo", "paymentRules", "fulfillment", "tpaExtensions"})
public class RailReservationType {
    @XmlElement(name="UniqueID", required=true)
    protected UniqueIDType uniqueID;
    @XmlElement(name="Itinerary", required=true)
    protected Itinerary itinerary;
    @XmlElement(name="PassengerInfo")
    protected List<RailPassengerCategoryDetailType> passengerInfo;
    @XmlElement(name="PaymentRules")
    protected PaymentRules paymentRules;
    @XmlElement(name="Fulfillment")
    protected CompanyNameType fulfillment;
    @XmlElement(name="TPA_Extensions")
    protected TPAExtensionsType tpaExtensions;
    @XmlAttribute(name="LastHoldDate")
    @XmlSchemaType(name="date")
    protected XMLGregorianCalendar lastHoldDate;

    public UniqueIDType getUniqueID() {
        return this.uniqueID;
    }

    public void setUniqueID(UniqueIDType value) {
        this.uniqueID = value;
    }

    public Itinerary getItinerary() {
        return this.itinerary;
    }

    public void setItinerary(Itinerary value) {
        this.itinerary = value;
    }

    public List<RailPassengerCategoryDetailType> getPassengerInfo() {
        if (this.passengerInfo == null) {
            this.passengerInfo = new ArrayList<RailPassengerCategoryDetailType>();
        }
        return this.passengerInfo;
    }

    public PaymentRules getPaymentRules() {
        return this.paymentRules;
    }

    public void setPaymentRules(PaymentRules value) {
        this.paymentRules = value;
    }

    public CompanyNameType getFulfillment() {
        return this.fulfillment;
    }

    public void setFulfillment(CompanyNameType value) {
        this.fulfillment = value;
    }

    public TPAExtensionsType getTPAExtensions() {
        return this.tpaExtensions;
    }

    public void setTPAExtensions(TPAExtensionsType value) {
        this.tpaExtensions = value;
    }

    public XMLGregorianCalendar getLastHoldDate() {
        return this.lastHoldDate;
    }

    public void setLastHoldDate(XMLGregorianCalendar value) {
        this.lastHoldDate = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"paymentRule"})
    public static class PaymentRules {
        @XmlElement(name="PaymentRule", required=true)
        protected List<MonetaryRuleType> paymentRule;

        public List<MonetaryRuleType> getPaymentRule() {
            if (this.paymentRule == null) {
                this.paymentRule = new ArrayList<MonetaryRuleType>();
            }
            return this.paymentRule;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"originAndDestination", "railCharges", "vendorMessages"})
    public static class Itinerary {
        @XmlElement(name="OriginAndDestination", required=true)
        protected List<OriginAndDestination> originAndDestination;
        @XmlElement(name="RailCharges")
        protected RailChargesType railCharges;
        @XmlElement(name="VendorMessages")
        protected VendorMessagesType vendorMessages;

        public List<OriginAndDestination> getOriginAndDestination() {
            if (this.originAndDestination == null) {
                this.originAndDestination = new ArrayList<OriginAndDestination>();
            }
            return this.originAndDestination;
        }

        public RailChargesType getRailCharges() {
            return this.railCharges;
        }

        public void setRailCharges(RailChargesType value) {
            this.railCharges = value;
        }

        public VendorMessagesType getVendorMessages() {
            return this.vendorMessages;
        }

        public void setVendorMessages(VendorMessagesType value) {
            this.vendorMessages = value;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="", propOrder={"originLocation", "destinationLocation", "trainSegment"})
        public static class OriginAndDestination {
            @XmlElement(name="OriginLocation", required=true)
            protected LocationType originLocation;
            @XmlElement(name="DestinationLocation", required=true)
            protected LocationType destinationLocation;
            @XmlElement(name="TrainSegment", required=true)
            protected List<BookedTrainSegmentType> trainSegment;

            public LocationType getOriginLocation() {
                return this.originLocation;
            }

            public void setOriginLocation(LocationType value) {
                this.originLocation = value;
            }

            public LocationType getDestinationLocation() {
                return this.destinationLocation;
            }

            public void setDestinationLocation(LocationType value) {
                this.destinationLocation = value;
            }

            public List<BookedTrainSegmentType> getTrainSegment() {
                if (this.trainSegment == null) {
                    this.trainSegment = new ArrayList<BookedTrainSegmentType>();
                }
                return this.trainSegment;
            }
        }
    }
}

