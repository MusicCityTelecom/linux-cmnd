/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.SailingBaseType;
import org.opentravel.ota._2003._05.SailingCategoryInfoType;
import org.opentravel.ota._2003._05.SailingType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="SailingInfoType", propOrder={"selectedSailing", "inclusivePackageOption", "currency"})
@XmlSeeAlso(value={SailingCategoryInfoType.class, SailingType.class})
public class SailingInfoType {
    @XmlElement(name="SelectedSailing")
    protected SelectedSailing selectedSailing;
    @XmlElement(name="InclusivePackageOption")
    protected InclusivePackageOption inclusivePackageOption;
    @XmlElement(name="Currency")
    protected Currency currency;

    public SelectedSailing getSelectedSailing() {
        return this.selectedSailing;
    }

    public void setSelectedSailing(SelectedSailing value) {
        this.selectedSailing = value;
    }

    public InclusivePackageOption getInclusivePackageOption() {
        return this.inclusivePackageOption;
    }

    public void setInclusivePackageOption(InclusivePackageOption value) {
        this.inclusivePackageOption = value;
    }

    public Currency getCurrency() {
        return this.currency;
    }

    public void setCurrency(Currency value) {
        this.currency = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class SelectedSailing
    extends SailingBaseType {
        @XmlAttribute(name="VoyageID")
        protected String voyageID;
        @XmlAttribute(name="Status")
        protected String status;
        @XmlAttribute(name="PortsOfCallQuantity")
        protected Integer portsOfCallQuantity;
        @XmlAttribute(name="Start")
        protected String start;
        @XmlAttribute(name="Duration")
        protected String duration;
        @XmlAttribute(name="End")
        protected String end;

        public String getVoyageID() {
            return this.voyageID;
        }

        public void setVoyageID(String value) {
            this.voyageID = value;
        }

        public String getStatus() {
            return this.status;
        }

        public void setStatus(String value) {
            this.status = value;
        }

        public Integer getPortsOfCallQuantity() {
            return this.portsOfCallQuantity;
        }

        public void setPortsOfCallQuantity(Integer value) {
            this.portsOfCallQuantity = value;
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
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class InclusivePackageOption {
        @XmlAttribute(name="CruisePackageCode")
        protected String cruisePackageCode;
        @XmlAttribute(name="InclusiveIndicator")
        protected Boolean inclusiveIndicator;
        @XmlAttribute(name="Start")
        protected String start;
        @XmlAttribute(name="Duration")
        protected String duration;
        @XmlAttribute(name="End")
        protected String end;

        public String getCruisePackageCode() {
            return this.cruisePackageCode;
        }

        public void setCruisePackageCode(String value) {
            this.cruisePackageCode = value;
        }

        public Boolean isInclusiveIndicator() {
            return this.inclusiveIndicator;
        }

        public void setInclusiveIndicator(Boolean value) {
            this.inclusiveIndicator = value;
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
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class Currency {
        @XmlAttribute(name="CurrencyCode")
        protected String currencyCode;
        @XmlAttribute(name="DecimalPlaces")
        @XmlSchemaType(name="nonNegativeInteger")
        protected BigInteger decimalPlaces;

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

