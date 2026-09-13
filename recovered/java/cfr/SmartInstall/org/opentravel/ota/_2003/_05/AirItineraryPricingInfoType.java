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
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.opentravel.ota._2003._05.ActionType;
import org.opentravel.ota._2003._05.BookingPriceInfoType;
import org.opentravel.ota._2003._05.FareInfoType;
import org.opentravel.ota._2003._05.FareType;
import org.opentravel.ota._2003._05.PTCFareBreakdownType;
import org.opentravel.ota._2003._05.PriceRequestInformationType;
import org.opentravel.ota._2003._05.PricedItineraryType;
import org.opentravel.ota._2003._05.PricingSourceType;
import org.opentravel.ota._2003._05.TPAExtensionsType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="AirItineraryPricingInfoType", propOrder={"itinTotalFare", "ptcFareBreakdowns", "fareInfos", "priceRequestInformation"})
@XmlSeeAlso(value={BookingPriceInfoType.class, PricedItineraryType.AirItineraryPricingInfo.class})
public class AirItineraryPricingInfoType {
    @XmlElement(name="ItinTotalFare")
    protected List<ItinTotalFare> itinTotalFare;
    @XmlElement(name="PTC_FareBreakdowns")
    protected PTCFareBreakdowns ptcFareBreakdowns;
    @XmlElement(name="FareInfos")
    protected FareInfos fareInfos;
    @XmlElement(name="PriceRequestInformation")
    protected PriceRequestInformationType priceRequestInformation;
    @XmlAttribute(name="PricingSource")
    protected PricingSourceType pricingSource;
    @XmlAttribute(name="ValidatingAirlineCode")
    protected String validatingAirlineCode;
    @XmlAttribute(name="QuoteID")
    protected String quoteID;

    public List<ItinTotalFare> getItinTotalFare() {
        if (this.itinTotalFare == null) {
            this.itinTotalFare = new ArrayList<ItinTotalFare>();
        }
        return this.itinTotalFare;
    }

    public PTCFareBreakdowns getPTCFareBreakdowns() {
        return this.ptcFareBreakdowns;
    }

    public void setPTCFareBreakdowns(PTCFareBreakdowns value) {
        this.ptcFareBreakdowns = value;
    }

    public FareInfos getFareInfos() {
        return this.fareInfos;
    }

    public void setFareInfos(FareInfos value) {
        this.fareInfos = value;
    }

    public PriceRequestInformationType getPriceRequestInformation() {
        return this.priceRequestInformation;
    }

    public void setPriceRequestInformation(PriceRequestInformationType value) {
        this.priceRequestInformation = value;
    }

    public PricingSourceType getPricingSource() {
        return this.pricingSource;
    }

    public void setPricingSource(PricingSourceType value) {
        this.pricingSource = value;
    }

    public String getValidatingAirlineCode() {
        return this.validatingAirlineCode;
    }

    public void setValidatingAirlineCode(String value) {
        this.validatingAirlineCode = value;
    }

    public String getQuoteID() {
        return this.quoteID;
    }

    public void setQuoteID(String value) {
        this.quoteID = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"ptcFareBreakdown"})
    public static class PTCFareBreakdowns {
        @XmlElement(name="PTC_FareBreakdown", required=true)
        protected List<PTCFareBreakdownType> ptcFareBreakdown;

        public List<PTCFareBreakdownType> getPTCFareBreakdown() {
            if (this.ptcFareBreakdown == null) {
                this.ptcFareBreakdown = new ArrayList<PTCFareBreakdownType>();
            }
            return this.ptcFareBreakdown;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class ItinTotalFare
    extends FareType {
        @XmlAttribute(name="Usage")
        @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
        protected String usage;

        public String getUsage() {
            return this.usage;
        }

        public void setUsage(String value) {
            this.usage = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"fareInfo"})
    public static class FareInfos {
        @XmlElement(name="FareInfo", required=true)
        protected List<FareInfo> fareInfo;

        public List<FareInfo> getFareInfo() {
            if (this.fareInfo == null) {
                this.fareInfo = new ArrayList<FareInfo>();
            }
            return this.fareInfo;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="", propOrder={"tpaExtensions"})
        public static class FareInfo
        extends FareInfoType {
            @XmlElement(name="TPA_Extensions")
            protected TPAExtensionsType tpaExtensions;
            @XmlAttribute(name="Operation")
            protected ActionType operation;
            @XmlAttribute(name="RPH")
            protected String rph;

            public TPAExtensionsType getTPAExtensions() {
                return this.tpaExtensions;
            }

            public void setTPAExtensions(TPAExtensionsType value) {
                this.tpaExtensions = value;
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
        }
    }
}

