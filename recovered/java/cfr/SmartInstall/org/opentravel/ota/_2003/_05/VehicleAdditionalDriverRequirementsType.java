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
import org.opentravel.ota._2003._05.FormattedTextType;
import org.opentravel.ota._2003._05.IncludeExcludeType;
import org.opentravel.ota._2003._05.LocationDetailRequirementAddlDriverInfoType;
import org.opentravel.ota._2003._05.VehicleCoreType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="VehicleAdditionalDriverRequirementsType", propOrder={"addlDriverInfos"})
public class VehicleAdditionalDriverRequirementsType {
    @XmlElement(name="AddlDriverInfos")
    protected AddlDriverInfos addlDriverInfos;

    public AddlDriverInfos getAddlDriverInfos() {
        return this.addlDriverInfos;
    }

    public void setAddlDriverInfos(AddlDriverInfos value) {
        this.addlDriverInfos = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"addlDriverInfo", "vehicles"})
    public static class AddlDriverInfos {
        @XmlElement(name="AddlDriverInfo", required=true)
        protected List<AddlDriverInfo> addlDriverInfo;
        @XmlElement(name="Vehicles")
        protected Vehicles vehicles;
        @XmlAttribute(name="ChargeType")
        protected String chargeType;
        @XmlAttribute(name="Amount")
        protected BigDecimal amount;
        @XmlAttribute(name="CurrencyCode")
        protected String currencyCode;
        @XmlAttribute(name="DecimalPlaces")
        @XmlSchemaType(name="nonNegativeInteger")
        protected BigInteger decimalPlaces;
        @XmlAttribute(name="Start")
        protected String start;
        @XmlAttribute(name="Duration")
        protected String duration;
        @XmlAttribute(name="End")
        protected String end;

        public List<AddlDriverInfo> getAddlDriverInfo() {
            if (this.addlDriverInfo == null) {
                this.addlDriverInfo = new ArrayList<AddlDriverInfo>();
            }
            return this.addlDriverInfo;
        }

        public Vehicles getVehicles() {
            return this.vehicles;
        }

        public void setVehicles(Vehicles value) {
            this.vehicles = value;
        }

        public String getChargeType() {
            return this.chargeType;
        }

        public void setChargeType(String value) {
            this.chargeType = value;
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
        @XmlType(name="", propOrder={"vehicle"})
        public static class Vehicles {
            @XmlElement(name="Vehicle", required=true)
            protected List<Vehicle> vehicle;

            public List<Vehicle> getVehicle() {
                if (this.vehicle == null) {
                    this.vehicle = new ArrayList<Vehicle>();
                }
                return this.vehicle;
            }

            @XmlAccessorType(value=XmlAccessType.FIELD)
            @XmlType(name="")
            public static class Vehicle
            extends VehicleCoreType {
                @XmlAttribute(name="IncludeExclude")
                protected IncludeExcludeType includeExclude;

                public IncludeExcludeType getIncludeExclude() {
                    return this.includeExclude;
                }

                public void setIncludeExclude(IncludeExcludeType value) {
                    this.includeExclude = value;
                }
            }
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="")
        public static class AddlDriverInfo
        extends FormattedTextType {
            @XmlAttribute(name="Type", required=true)
            protected LocationDetailRequirementAddlDriverInfoType type;

            public LocationDetailRequirementAddlDriverInfoType getType() {
                return this.type;
            }

            public void setType(LocationDetailRequirementAddlDriverInfoType value) {
                this.type = value;
            }
        }
    }
}

