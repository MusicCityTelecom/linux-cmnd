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
import org.opentravel.ota._2003._05.FormattedTextType;
import org.opentravel.ota._2003._05.TPAExtensionsType;
import org.opentravel.ota._2003._05.VehicleChargeType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="VehicleLocationAdditionalFeesType", propOrder={"taxes", "fees", "surcharges", "miscellaneousCharges", "tpaExtensions"})
public class VehicleLocationAdditionalFeesType {
    @XmlElement(name="Taxes")
    protected Taxes taxes;
    @XmlElement(name="Fees")
    protected Fees fees;
    @XmlElement(name="Surcharges")
    protected Surcharges surcharges;
    @XmlElement(name="MiscellaneousCharges")
    protected MiscellaneousCharges miscellaneousCharges;
    @XmlElement(name="TPA_Extensions")
    protected TPAExtensionsType tpaExtensions;

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

    public Surcharges getSurcharges() {
        return this.surcharges;
    }

    public void setSurcharges(Surcharges value) {
        this.surcharges = value;
    }

    public MiscellaneousCharges getMiscellaneousCharges() {
        return this.miscellaneousCharges;
    }

    public void setMiscellaneousCharges(MiscellaneousCharges value) {
        this.miscellaneousCharges = value;
    }

    public TPAExtensionsType getTPAExtensions() {
        return this.tpaExtensions;
    }

    public void setTPAExtensions(TPAExtensionsType value) {
        this.tpaExtensions = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"tax", "info"})
    public static class Taxes {
        @XmlElement(name="Tax")
        protected List<Tax> tax;
        @XmlElement(name="Info")
        protected FormattedTextType info;
        @XmlAttribute(name="Start")
        protected String start;
        @XmlAttribute(name="Duration")
        protected String duration;
        @XmlAttribute(name="End")
        protected String end;

        public List<Tax> getTax() {
            if (this.tax == null) {
                this.tax = new ArrayList<Tax>();
            }
            return this.tax;
        }

        public FormattedTextType getInfo() {
            return this.info;
        }

        public void setInfo(FormattedTextType value) {
            this.info = value;
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

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="", propOrder={"info"})
        public static class Tax {
            @XmlElement(name="Info")
            protected FormattedTextType info;
            @XmlAttribute(name="TaxCode")
            protected String taxCode;
            @XmlAttribute(name="Percentage")
            protected BigDecimal percentage;

            public FormattedTextType getInfo() {
                return this.info;
            }

            public void setInfo(FormattedTextType value) {
                this.info = value;
            }

            public String getTaxCode() {
                return this.taxCode;
            }

            public void setTaxCode(String value) {
                this.taxCode = value;
            }

            public BigDecimal getPercentage() {
                return this.percentage;
            }

            public void setPercentage(BigDecimal value) {
                this.percentage = value;
            }
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"surcharge", "info"})
    public static class Surcharges {
        @XmlElement(name="Surcharge")
        protected List<Surcharge> surcharge;
        @XmlElement(name="Info")
        protected FormattedTextType info;
        @XmlAttribute(name="Start")
        protected String start;
        @XmlAttribute(name="Duration")
        protected String duration;
        @XmlAttribute(name="End")
        protected String end;

        public List<Surcharge> getSurcharge() {
            if (this.surcharge == null) {
                this.surcharge = new ArrayList<Surcharge>();
            }
            return this.surcharge;
        }

        public FormattedTextType getInfo() {
            return this.info;
        }

        public void setInfo(FormattedTextType value) {
            this.info = value;
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

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="", propOrder={"info"})
        public static class Surcharge
        extends VehicleChargeType {
            @XmlElement(name="Info")
            protected FormattedTextType info;

            public FormattedTextType getInfo() {
                return this.info;
            }

            public void setInfo(FormattedTextType value) {
                this.info = value;
            }
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"miscellaneousCharge", "info"})
    public static class MiscellaneousCharges {
        @XmlElement(name="MiscellaneousCharge")
        protected List<MiscellaneousCharge> miscellaneousCharge;
        @XmlElement(name="Info")
        protected FormattedTextType info;

        public List<MiscellaneousCharge> getMiscellaneousCharge() {
            if (this.miscellaneousCharge == null) {
                this.miscellaneousCharge = new ArrayList<MiscellaneousCharge>();
            }
            return this.miscellaneousCharge;
        }

        public FormattedTextType getInfo() {
            return this.info;
        }

        public void setInfo(FormattedTextType value) {
            this.info = value;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="", propOrder={"info"})
        public static class MiscellaneousCharge
        extends VehicleChargeType {
            @XmlElement(name="Info")
            protected FormattedTextType info;

            public FormattedTextType getInfo() {
                return this.info;
            }

            public void setInfo(FormattedTextType value) {
                this.info = value;
            }
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"fee", "info"})
    public static class Fees {
        @XmlElement(name="Fee")
        protected List<Fee> fee;
        @XmlElement(name="Info")
        protected FormattedTextType info;

        public List<Fee> getFee() {
            if (this.fee == null) {
                this.fee = new ArrayList<Fee>();
            }
            return this.fee;
        }

        public FormattedTextType getInfo() {
            return this.info;
        }

        public void setInfo(FormattedTextType value) {
            this.info = value;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="", propOrder={"info"})
        public static class Fee
        extends VehicleChargeType {
            @XmlElement(name="Info")
            protected FormattedTextType info;

            public FormattedTextType getInfo() {
                return this.info;
            }

            public void setInfo(FormattedTextType value) {
                this.info = value;
            }
        }
    }
}

