/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

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
import org.opentravel.ota._2003._05.OTANotifReportRQ;
import org.opentravel.ota._2003._05.RateUploadType;
import org.opentravel.ota._2003._05.StatusApplicationControlType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="RateAmountMessageType", propOrder={"statusApplicationControl", "rates"})
@XmlSeeAlso(value={OTANotifReportRQ.NotifDetails.HotelNotifReport.RateAmountMessages.RateAmountMessage.class})
public class RateAmountMessageType {
    @XmlElement(name="StatusApplicationControl")
    protected StatusApplicationControlType statusApplicationControl;
    @XmlElement(name="Rates")
    protected Rates rates;
    @XmlAttribute(name="LocatorID")
    @XmlSchemaType(name="positiveInteger")
    protected BigInteger locatorID;

    public StatusApplicationControlType getStatusApplicationControl() {
        return this.statusApplicationControl;
    }

    public void setStatusApplicationControl(StatusApplicationControlType value) {
        this.statusApplicationControl = value;
    }

    public Rates getRates() {
        return this.rates;
    }

    public void setRates(Rates value) {
        this.rates = value;
    }

    public BigInteger getLocatorID() {
        return this.locatorID;
    }

    public void setLocatorID(BigInteger value) {
        this.locatorID = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"rate"})
    public static class Rates {
        @XmlElement(name="Rate", required=true)
        protected List<Rate> rate;

        public List<Rate> getRate() {
            if (this.rate == null) {
                this.rate = new ArrayList<Rate>();
            }
            return this.rate;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="")
        public static class Rate
        extends RateUploadType {
            @XmlAttribute(name="RateChangeIndicator")
            protected Boolean rateChangeIndicator;

            public Boolean isRateChangeIndicator() {
                return this.rateChangeIndicator;
            }

            public void setRateChangeIndicator(Boolean value) {
                this.rateChangeIndicator = value;
            }
        }
    }
}

