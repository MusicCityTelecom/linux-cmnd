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
import org.opentravel.ota._2003._05.ActionType;
import org.opentravel.ota._2003._05.AirTaxType;
import org.opentravel.ota._2003._05.BookFlightSegmentType;
import org.opentravel.ota._2003._05.FareBasisCodeType;
import org.opentravel.ota._2003._05.FareInfoType;
import org.opentravel.ota._2003._05.FareType;
import org.opentravel.ota._2003._05.FreeTextType;
import org.opentravel.ota._2003._05.PassengerTypeQuantityType;
import org.opentravel.ota._2003._05.PricingSourceType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="PTCFareBreakdownType", propOrder={"passengerTypeQuantity", "fareBasisCodes", "passengerFare", "travelerRefNumber", "ticketDesignators", "endorsements", "fareInfo", "pricingUnit"})
public class PTCFareBreakdownType {
    @XmlElement(name="PassengerTypeQuantity", required=true)
    protected PassengerTypeQuantityType passengerTypeQuantity;
    @XmlElement(name="FareBasisCodes")
    protected FareBasisCodes fareBasisCodes;
    @XmlElement(name="PassengerFare")
    protected List<PassengerFare> passengerFare;
    @XmlElement(name="TravelerRefNumber")
    protected List<TravelerRefNumber> travelerRefNumber;
    @XmlElement(name="TicketDesignators")
    protected TicketDesignators ticketDesignators;
    @XmlElement(name="Endorsements")
    protected Endorsements endorsements;
    @XmlElement(name="FareInfo")
    protected List<FareInfo> fareInfo;
    @XmlElement(name="PricingUnit")
    protected List<PricingUnit> pricingUnit;
    @XmlAttribute(name="PricingSource")
    protected PricingSourceType pricingSource;
    @XmlAttribute(name="FlightRefNumberRPHList")
    protected List<String> flightRefNumberRPHList;

    public PassengerTypeQuantityType getPassengerTypeQuantity() {
        return this.passengerTypeQuantity;
    }

    public void setPassengerTypeQuantity(PassengerTypeQuantityType value) {
        this.passengerTypeQuantity = value;
    }

    public FareBasisCodes getFareBasisCodes() {
        return this.fareBasisCodes;
    }

    public void setFareBasisCodes(FareBasisCodes value) {
        this.fareBasisCodes = value;
    }

    public List<PassengerFare> getPassengerFare() {
        if (this.passengerFare == null) {
            this.passengerFare = new ArrayList<PassengerFare>();
        }
        return this.passengerFare;
    }

    public List<TravelerRefNumber> getTravelerRefNumber() {
        if (this.travelerRefNumber == null) {
            this.travelerRefNumber = new ArrayList<TravelerRefNumber>();
        }
        return this.travelerRefNumber;
    }

    public TicketDesignators getTicketDesignators() {
        return this.ticketDesignators;
    }

    public void setTicketDesignators(TicketDesignators value) {
        this.ticketDesignators = value;
    }

    public Endorsements getEndorsements() {
        return this.endorsements;
    }

    public void setEndorsements(Endorsements value) {
        this.endorsements = value;
    }

    public List<FareInfo> getFareInfo() {
        if (this.fareInfo == null) {
            this.fareInfo = new ArrayList<FareInfo>();
        }
        return this.fareInfo;
    }

    public List<PricingUnit> getPricingUnit() {
        if (this.pricingUnit == null) {
            this.pricingUnit = new ArrayList<PricingUnit>();
        }
        return this.pricingUnit;
    }

    public PricingSourceType getPricingSource() {
        return this.pricingSource;
    }

    public void setPricingSource(PricingSourceType value) {
        this.pricingSource = value;
    }

    public List<String> getFlightRefNumberRPHList() {
        if (this.flightRefNumberRPHList == null) {
            this.flightRefNumberRPHList = new ArrayList<String>();
        }
        return this.flightRefNumberRPHList;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class TravelerRefNumber {
        @XmlAttribute(name="RPH")
        protected String rph;
        @XmlAttribute(name="SurnameRefNumber")
        protected String surnameRefNumber;

        public String getRPH() {
            return this.rph;
        }

        public void setRPH(String value) {
            this.rph = value;
        }

        public String getSurnameRefNumber() {
            return this.surnameRefNumber;
        }

        public void setSurnameRefNumber(String value) {
            this.surnameRefNumber = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"ticketDesignator"})
    public static class TicketDesignators {
        @XmlElement(name="TicketDesignator", required=true)
        protected List<TicketDesignator> ticketDesignator;

        public List<TicketDesignator> getTicketDesignator() {
            if (this.ticketDesignator == null) {
                this.ticketDesignator = new ArrayList<TicketDesignator>();
            }
            return this.ticketDesignator;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="")
        public static class TicketDesignator {
            @XmlAttribute(name="FlightRefRPH")
            protected String flightRefRPH;
            @XmlAttribute(name="TicketDesignatorCode")
            protected String ticketDesignatorCode;
            @XmlAttribute(name="TicketDesignatorExtension")
            protected String ticketDesignatorExtension;

            public String getFlightRefRPH() {
                return this.flightRefRPH;
            }

            public void setFlightRefRPH(String value) {
                this.flightRefRPH = value;
            }

            public String getTicketDesignatorCode() {
                return this.ticketDesignatorCode;
            }

            public void setTicketDesignatorCode(String value) {
                this.ticketDesignatorCode = value;
            }

            public String getTicketDesignatorExtension() {
                return this.ticketDesignatorExtension;
            }

            public void setTicketDesignatorExtension(String value) {
                this.ticketDesignatorExtension = value;
            }
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"fareComponent"})
    public static class PricingUnit {
        @XmlElement(name="FareComponent", required=true)
        protected List<FareComponent> fareComponent;
        @XmlAttribute(name="UnitNumber", required=true)
        protected int unitNumber;

        public List<FareComponent> getFareComponent() {
            if (this.fareComponent == null) {
                this.fareComponent = new ArrayList<FareComponent>();
            }
            return this.fareComponent;
        }

        public int getUnitNumber() {
            return this.unitNumber;
        }

        public void setUnitNumber(int value) {
            this.unitNumber = value;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="", propOrder={"flightLeg"})
        public static class FareComponent {
            @XmlElement(name="FlightLeg", required=true)
            protected List<FlightLeg> flightLeg;
            @XmlAttribute(name="Number", required=true)
            protected int number;
            @XmlAttribute(name="Amount")
            protected BigDecimal amount;
            @XmlAttribute(name="CurrencyCode")
            protected String currencyCode;
            @XmlAttribute(name="DecimalPlaces")
            @XmlSchemaType(name="nonNegativeInteger")
            protected BigInteger decimalPlaces;

            public List<FlightLeg> getFlightLeg() {
                if (this.flightLeg == null) {
                    this.flightLeg = new ArrayList<FlightLeg>();
                }
                return this.flightLeg;
            }

            public int getNumber() {
                return this.number;
            }

            public void setNumber(int value) {
                this.number = value;
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

            @XmlAccessorType(value=XmlAccessType.FIELD)
            @XmlType(name="")
            public static class FlightLeg
            extends BookFlightSegmentType {
                @XmlAttribute(name="SurchargeInd")
                protected Boolean surchargeInd;
                @XmlAttribute(name="FareBasisCode")
                protected String fareBasisCode;
                @XmlAttribute(name="UnitOfMeasureQuantity")
                protected BigDecimal unitOfMeasureQuantity;
                @XmlAttribute(name="UnitOfMeasure")
                protected String unitOfMeasure;
                @XmlAttribute(name="UnitOfMeasureCode")
                protected String unitOfMeasureCode;

                public Boolean isSurchargeInd() {
                    return this.surchargeInd;
                }

                public void setSurchargeInd(Boolean value) {
                    this.surchargeInd = value;
                }

                public String getFareBasisCode() {
                    return this.fareBasisCode;
                }

                public void setFareBasisCode(String value) {
                    this.fareBasisCode = value;
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
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"ticketFeeDetail"})
    public static class PassengerFare
    extends FareType {
        @XmlElement(name="TicketFeeDetail")
        protected TicketFeeDetail ticketFeeDetail;
        @XmlAttribute(name="Usage")
        @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
        protected String usage;

        public TicketFeeDetail getTicketFeeDetail() {
            return this.ticketFeeDetail;
        }

        public void setTicketFeeDetail(TicketFeeDetail value) {
            this.ticketFeeDetail = value;
        }

        public String getUsage() {
            return this.usage;
        }

        public void setUsage(String value) {
            this.usage = value;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="", propOrder={"fee", "total"})
        public static class TicketFeeDetail {
            @XmlElement(name="Fee", required=true)
            protected List<Fee> fee;
            @XmlElement(name="Total")
            protected List<Total> total;
            @XmlAttribute(name="CurrencyCode")
            protected String currencyCode;
            @XmlAttribute(name="DecimalPlaces")
            @XmlSchemaType(name="nonNegativeInteger")
            protected BigInteger decimalPlaces;

            public List<Fee> getFee() {
                if (this.fee == null) {
                    this.fee = new ArrayList<Fee>();
                }
                return this.fee;
            }

            public List<Total> getTotal() {
                if (this.total == null) {
                    this.total = new ArrayList<Total>();
                }
                return this.total;
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

            @XmlAccessorType(value=XmlAccessType.FIELD)
            @XmlType(name="")
            public static class Total {
                @XmlAttribute(name="Type", required=true)
                @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
                protected String type;
                @XmlAttribute(name="Amount", required=true)
                protected BigDecimal amount;

                public String getType() {
                    return this.type;
                }

                public void setType(String value) {
                    this.type = value;
                }

                public BigDecimal getAmount() {
                    return this.amount;
                }

                public void setAmount(BigDecimal value) {
                    this.amount = value;
                }
            }

            @XmlAccessorType(value=XmlAccessType.FIELD)
            @XmlType(name="", propOrder={"baseFee", "taxes", "total"})
            public static class Fee {
                @XmlElement(name="BaseFee")
                protected BaseFee baseFee;
                @XmlElement(name="Taxes")
                protected Taxes taxes;
                @XmlElement(name="Total")
                protected Total total;
                @XmlAttribute(name="FeeCode", required=true)
                protected String feeCode;
                @XmlAttribute(name="Description")
                protected String description;

                public BaseFee getBaseFee() {
                    return this.baseFee;
                }

                public void setBaseFee(BaseFee value) {
                    this.baseFee = value;
                }

                public Taxes getTaxes() {
                    return this.taxes;
                }

                public void setTaxes(Taxes value) {
                    this.taxes = value;
                }

                public Total getTotal() {
                    return this.total;
                }

                public void setTotal(Total value) {
                    this.total = value;
                }

                public String getFeeCode() {
                    return this.feeCode;
                }

                public void setFeeCode(String value) {
                    this.feeCode = value;
                }

                public String getDescription() {
                    return this.description;
                }

                public void setDescription(String value) {
                    this.description = value;
                }

                @XmlAccessorType(value=XmlAccessType.FIELD)
                @XmlType(name="")
                public static class Total {
                    @XmlAttribute(name="Amount", required=true)
                    protected BigDecimal amount;

                    public BigDecimal getAmount() {
                        return this.amount;
                    }

                    public void setAmount(BigDecimal value) {
                        this.amount = value;
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
                @XmlType(name="")
                public static class BaseFee {
                    @XmlAttribute(name="Amount", required=true)
                    protected BigDecimal amount;

                    public BigDecimal getAmount() {
                        return this.amount;
                    }

                    public void setAmount(BigDecimal value) {
                        this.amount = value;
                    }
                }
            }
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"passengerFare"})
    public static class FareInfo
    extends FareInfoType {
        @XmlElement(name="PassengerFare", required=true)
        protected FareType passengerFare;

        public FareType getPassengerFare() {
            return this.passengerFare;
        }

        public void setPassengerFare(FareType value) {
            this.passengerFare = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"fareBasisCode"})
    public static class FareBasisCodes {
        @XmlElement(name="FareBasisCode", required=true)
        protected List<FareBasisCodeType> fareBasisCode;

        public List<FareBasisCodeType> getFareBasisCode() {
            if (this.fareBasisCode == null) {
                this.fareBasisCode = new ArrayList<FareBasisCodeType>();
            }
            return this.fareBasisCode;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"endorsement"})
    public static class Endorsements {
        @XmlElement(name="Endorsement", required=true)
        protected List<Endorsement> endorsement;
        @XmlAttribute(name="NonRefundableIndicator")
        protected Boolean nonRefundableIndicator;
        @XmlAttribute(name="NonEndorsableIndicator")
        protected Boolean nonEndorsableIndicator;

        public List<Endorsement> getEndorsement() {
            if (this.endorsement == null) {
                this.endorsement = new ArrayList<Endorsement>();
            }
            return this.endorsement;
        }

        public Boolean isNonRefundableIndicator() {
            return this.nonRefundableIndicator;
        }

        public void setNonRefundableIndicator(Boolean value) {
            this.nonRefundableIndicator = value;
        }

        public Boolean isNonEndorsableIndicator() {
            return this.nonEndorsableIndicator;
        }

        public void setNonEndorsableIndicator(Boolean value) {
            this.nonEndorsableIndicator = value;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="")
        public static class Endorsement
        extends FreeTextType {
            @XmlAttribute(name="Operation")
            protected ActionType operation;

            public ActionType getOperation() {
                return this.operation;
            }

            public void setOperation(ActionType value) {
                this.operation = value;
            }
        }
    }
}

