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
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.opentravel.ota._2003._05.UniqueIDType;
import org.opentravel.ota._2003._05.VehicleChargePurposeType;
import org.opentravel.ota._2003._05.VehicleChargeType;
import org.opentravel.ota._2003._05.VehicleEquipmentType;
import org.opentravel.ota._2003._05.VehicleRentalCoreType;
import org.opentravel.ota._2003._05.VehicleRentalDetailsType;
import org.opentravel.ota._2003._05.VehicleRentalRateType;
import org.opentravel.ota._2003._05.VehicleType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="VehicleRentalTransactionType", propOrder={"pickUpReturnDetails", "vehicle", "rentalRate", "pricedEquips", "fees", "totalCharge", "confID", "contractID"})
public class VehicleRentalTransactionType {
    @XmlElement(name="PickUpReturnDetails")
    protected List<PickUpReturnDetails> pickUpReturnDetails;
    @XmlElement(name="Vehicle", required=true)
    protected Vehicle vehicle;
    @XmlElement(name="RentalRate")
    protected VehicleRentalRateType rentalRate;
    @XmlElement(name="PricedEquips")
    protected PricedEquips pricedEquips;
    @XmlElement(name="Fees")
    protected Fees fees;
    @XmlElement(name="TotalCharge")
    protected TotalCharge totalCharge;
    @XmlElement(name="ConfID")
    protected UniqueIDType confID;
    @XmlElement(name="ContractID")
    protected UniqueIDType contractID;

    public List<PickUpReturnDetails> getPickUpReturnDetails() {
        if (this.pickUpReturnDetails == null) {
            this.pickUpReturnDetails = new ArrayList<PickUpReturnDetails>();
        }
        return this.pickUpReturnDetails;
    }

    public Vehicle getVehicle() {
        return this.vehicle;
    }

    public void setVehicle(Vehicle value) {
        this.vehicle = value;
    }

    public VehicleRentalRateType getRentalRate() {
        return this.rentalRate;
    }

    public void setRentalRate(VehicleRentalRateType value) {
        this.rentalRate = value;
    }

    public PricedEquips getPricedEquips() {
        return this.pricedEquips;
    }

    public void setPricedEquips(PricedEquips value) {
        this.pricedEquips = value;
    }

    public Fees getFees() {
        return this.fees;
    }

    public void setFees(Fees value) {
        this.fees = value;
    }

    public TotalCharge getTotalCharge() {
        return this.totalCharge;
    }

    public void setTotalCharge(TotalCharge value) {
        this.totalCharge = value;
    }

    public UniqueIDType getConfID() {
        return this.confID;
    }

    public void setConfID(UniqueIDType value) {
        this.confID = value;
    }

    public UniqueIDType getContractID() {
        return this.contractID;
    }

    public void setContractID(UniqueIDType value) {
        this.contractID = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"vehRentalDetails"})
    public static class Vehicle
    extends VehicleType {
        @XmlElement(name="VehRentalDetails")
        protected List<VehicleRentalDetailsType> vehRentalDetails;

        public List<VehicleRentalDetailsType> getVehRentalDetails() {
            if (this.vehRentalDetails == null) {
                this.vehRentalDetails = new ArrayList<VehicleRentalDetailsType>();
            }
            return this.vehRentalDetails;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class TotalCharge {
        @XmlAttribute(name="RateTotalAmount")
        protected BigDecimal rateTotalAmount;
        @XmlAttribute(name="EstimatedTotalAmount")
        protected BigDecimal estimatedTotalAmount;
        @XmlAttribute(name="CurrencyCode")
        protected String currencyCode;
        @XmlAttribute(name="DecimalPlaces")
        @XmlSchemaType(name="nonNegativeInteger")
        protected BigInteger decimalPlaces;

        public BigDecimal getRateTotalAmount() {
            return this.rateTotalAmount;
        }

        public void setRateTotalAmount(BigDecimal value) {
            this.rateTotalAmount = value;
        }

        public BigDecimal getEstimatedTotalAmount() {
            return this.estimatedTotalAmount;
        }

        public void setEstimatedTotalAmount(BigDecimal value) {
            this.estimatedTotalAmount = value;
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
    @XmlType(name="", propOrder={"pricedEquip"})
    public static class PricedEquips {
        @XmlElement(name="PricedEquip", required=true)
        protected List<PricedEquip> pricedEquip;

        public List<PricedEquip> getPricedEquip() {
            if (this.pricedEquip == null) {
                this.pricedEquip = new ArrayList<PricedEquip>();
            }
            return this.pricedEquip;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="", propOrder={"equipment", "charge"})
        public static class PricedEquip {
            @XmlElement(name="Equipment", required=true)
            protected List<Equipment> equipment;
            @XmlElement(name="Charge")
            protected VehicleChargeType charge;

            public List<Equipment> getEquipment() {
                if (this.equipment == null) {
                    this.equipment = new ArrayList<Equipment>();
                }
                return this.equipment;
            }

            public VehicleChargeType getCharge() {
                return this.charge;
            }

            public void setCharge(VehicleChargeType value) {
                this.charge = value;
            }

            @XmlAccessorType(value=XmlAccessType.FIELD)
            @XmlType(name="")
            public static class Equipment
            extends VehicleEquipmentType {
                @XmlAttribute(name="CheckOutCheckInCode")
                @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
                protected String checkOutCheckInCode;

                public String getCheckOutCheckInCode() {
                    return this.checkOutCheckInCode;
                }

                public void setCheckOutCheckInCode(String value) {
                    this.checkOutCheckInCode = value;
                }
            }
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class PickUpReturnDetails
    extends VehicleRentalCoreType {
        @XmlAttribute(name="ExpectedActualCode")
        @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
        protected String expectedActualCode;

        public String getExpectedActualCode() {
            return this.expectedActualCode;
        }

        public void setExpectedActualCode(String value) {
            this.expectedActualCode = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"fee"})
    public static class Fees {
        @XmlElement(name="Fee", required=true)
        protected List<VehicleChargePurposeType> fee;

        public List<VehicleChargePurposeType> getFee() {
            if (this.fee == null) {
                this.fee = new ArrayList<VehicleChargePurposeType>();
            }
            return this.fee;
        }
    }
}

