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
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.opentravel.ota._2003._05.DayOfWeekType;
import org.opentravel.ota._2003._05.DistanceUnitNameType;
import org.opentravel.ota._2003._05.FormattedTextTextType;
import org.opentravel.ota._2003._05.NoShowFeeType;
import org.opentravel.ota._2003._05.RateQualifierType;
import org.opentravel.ota._2003._05.TimeUnitType;
import org.opentravel.ota._2003._05.VehicleChargePurposeType;
import org.opentravel.ota._2003._05.VehiclePeriodUnitNameType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="VehicleRentalRateType", propOrder={"rateDistance", "vehicleCharges", "rateQualifier", "rateRestrictions", "rateGuarantee", "pickupReturnRule", "noShowFeeInfo"})
public class VehicleRentalRateType {
    @XmlElement(name="RateDistance")
    protected List<RateDistance> rateDistance;
    @XmlElement(name="VehicleCharges")
    protected VehicleCharges vehicleCharges;
    @XmlElement(name="RateQualifier")
    protected RateQualifier rateQualifier;
    @XmlElement(name="RateRestrictions")
    protected RateRestrictions rateRestrictions;
    @XmlElement(name="RateGuarantee")
    protected RateGuarantee rateGuarantee;
    @XmlElement(name="PickupReturnRule")
    protected List<PickupReturnRule> pickupReturnRule;
    @XmlElement(name="NoShowFeeInfo")
    protected NoShowFeeType noShowFeeInfo;
    @XmlAttribute(name="QuoteID")
    protected String quoteID;

    public List<RateDistance> getRateDistance() {
        if (this.rateDistance == null) {
            this.rateDistance = new ArrayList<RateDistance>();
        }
        return this.rateDistance;
    }

    public VehicleCharges getVehicleCharges() {
        return this.vehicleCharges;
    }

    public void setVehicleCharges(VehicleCharges value) {
        this.vehicleCharges = value;
    }

    public RateQualifier getRateQualifier() {
        return this.rateQualifier;
    }

    public void setRateQualifier(RateQualifier value) {
        this.rateQualifier = value;
    }

    public RateRestrictions getRateRestrictions() {
        return this.rateRestrictions;
    }

    public void setRateRestrictions(RateRestrictions value) {
        this.rateRestrictions = value;
    }

    public RateGuarantee getRateGuarantee() {
        return this.rateGuarantee;
    }

    public void setRateGuarantee(RateGuarantee value) {
        this.rateGuarantee = value;
    }

    public List<PickupReturnRule> getPickupReturnRule() {
        if (this.pickupReturnRule == null) {
            this.pickupReturnRule = new ArrayList<PickupReturnRule>();
        }
        return this.pickupReturnRule;
    }

    public NoShowFeeType getNoShowFeeInfo() {
        return this.noShowFeeInfo;
    }

    public void setNoShowFeeInfo(NoShowFeeType value) {
        this.noShowFeeInfo = value;
    }

    public String getQuoteID() {
        return this.quoteID;
    }

    public void setQuoteID(String value) {
        this.quoteID = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"vehicleCharge"})
    public static class VehicleCharges {
        @XmlElement(name="VehicleCharge", required=true)
        protected List<VehicleChargePurposeType> vehicleCharge;

        public List<VehicleChargePurposeType> getVehicleCharge() {
            if (this.vehicleCharge == null) {
                this.vehicleCharge = new ArrayList<VehicleChargePurposeType>();
            }
            return this.vehicleCharge;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class RateRestrictions {
        @XmlAttribute(name="ArriveByFlight")
        protected Boolean arriveByFlight;
        @XmlAttribute(name="MinimumDayInd")
        protected Boolean minimumDayInd;
        @XmlAttribute(name="MaximumDayInd")
        protected Boolean maximumDayInd;
        @XmlAttribute(name="AdvancedBookingInd")
        protected Boolean advancedBookingInd;
        @XmlAttribute(name="RestrictedMileageInd")
        protected Boolean restrictedMileageInd;
        @XmlAttribute(name="CorporateRateInd")
        protected Boolean corporateRateInd;
        @XmlAttribute(name="GuaranteeReqInd")
        protected Boolean guaranteeReqInd;
        @XmlAttribute(name="MaximumVehiclesAllowed")
        protected Integer maximumVehiclesAllowed;
        @XmlAttribute(name="OvernightInd")
        protected Boolean overnightInd;
        @XmlAttribute(name="OneWayPolicy")
        @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
        protected String oneWayPolicy;
        @XmlAttribute(name="CancellationPenaltyInd")
        protected Boolean cancellationPenaltyInd;
        @XmlAttribute(name="ModificationPenaltyInd")
        protected Boolean modificationPenaltyInd;
        @XmlAttribute(name="MinimumAge")
        protected Integer minimumAge;
        @XmlAttribute(name="MaximumAge")
        protected Integer maximumAge;
        @XmlAttribute(name="NoShowFeeInd")
        protected Boolean noShowFeeInd;

        public Boolean isArriveByFlight() {
            return this.arriveByFlight;
        }

        public void setArriveByFlight(Boolean value) {
            this.arriveByFlight = value;
        }

        public Boolean isMinimumDayInd() {
            return this.minimumDayInd;
        }

        public void setMinimumDayInd(Boolean value) {
            this.minimumDayInd = value;
        }

        public Boolean isMaximumDayInd() {
            return this.maximumDayInd;
        }

        public void setMaximumDayInd(Boolean value) {
            this.maximumDayInd = value;
        }

        public Boolean isAdvancedBookingInd() {
            return this.advancedBookingInd;
        }

        public void setAdvancedBookingInd(Boolean value) {
            this.advancedBookingInd = value;
        }

        public Boolean isRestrictedMileageInd() {
            return this.restrictedMileageInd;
        }

        public void setRestrictedMileageInd(Boolean value) {
            this.restrictedMileageInd = value;
        }

        public Boolean isCorporateRateInd() {
            return this.corporateRateInd;
        }

        public void setCorporateRateInd(Boolean value) {
            this.corporateRateInd = value;
        }

        public Boolean isGuaranteeReqInd() {
            return this.guaranteeReqInd;
        }

        public void setGuaranteeReqInd(Boolean value) {
            this.guaranteeReqInd = value;
        }

        public Integer getMaximumVehiclesAllowed() {
            return this.maximumVehiclesAllowed;
        }

        public void setMaximumVehiclesAllowed(Integer value) {
            this.maximumVehiclesAllowed = value;
        }

        public Boolean isOvernightInd() {
            return this.overnightInd;
        }

        public void setOvernightInd(Boolean value) {
            this.overnightInd = value;
        }

        public String getOneWayPolicy() {
            return this.oneWayPolicy;
        }

        public void setOneWayPolicy(String value) {
            this.oneWayPolicy = value;
        }

        public Boolean isCancellationPenaltyInd() {
            return this.cancellationPenaltyInd;
        }

        public void setCancellationPenaltyInd(Boolean value) {
            this.cancellationPenaltyInd = value;
        }

        public Boolean isModificationPenaltyInd() {
            return this.modificationPenaltyInd;
        }

        public void setModificationPenaltyInd(Boolean value) {
            this.modificationPenaltyInd = value;
        }

        public Integer getMinimumAge() {
            return this.minimumAge;
        }

        public void setMinimumAge(Integer value) {
            this.minimumAge = value;
        }

        public Integer getMaximumAge() {
            return this.maximumAge;
        }

        public void setMaximumAge(Integer value) {
            this.maximumAge = value;
        }

        public Boolean isNoShowFeeInd() {
            return this.noShowFeeInd;
        }

        public void setNoShowFeeInd(Boolean value) {
            this.noShowFeeInd = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class RateQualifier
    extends RateQualifierType {
        @XmlAttribute(name="TourInfoRPH")
        protected String tourInfoRPH;
        @XmlAttribute(name="CustLoyaltyRPH")
        protected List<String> custLoyaltyRPH;
        @XmlAttribute(name="QuoteID")
        protected String quoteID;

        public String getTourInfoRPH() {
            return this.tourInfoRPH;
        }

        public void setTourInfoRPH(String value) {
            this.tourInfoRPH = value;
        }

        public List<String> getCustLoyaltyRPH() {
            if (this.custLoyaltyRPH == null) {
                this.custLoyaltyRPH = new ArrayList<String>();
            }
            return this.custLoyaltyRPH;
        }

        public String getQuoteID() {
            return this.quoteID;
        }

        public void setQuoteID(String value) {
            this.quoteID = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"description"})
    public static class RateGuarantee {
        @XmlElement(name="Description")
        protected FormattedTextTextType description;
        @XmlAttribute(name="AbsoluteDeadline")
        protected String absoluteDeadline;
        @XmlAttribute(name="OffsetTimeUnit")
        protected TimeUnitType offsetTimeUnit;
        @XmlAttribute(name="OffsetUnitMultiplier")
        protected Integer offsetUnitMultiplier;
        @XmlAttribute(name="OffsetDropTime")
        @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
        protected String offsetDropTime;

        public FormattedTextTextType getDescription() {
            return this.description;
        }

        public void setDescription(FormattedTextTextType value) {
            this.description = value;
        }

        public String getAbsoluteDeadline() {
            return this.absoluteDeadline;
        }

        public void setAbsoluteDeadline(String value) {
            this.absoluteDeadline = value;
        }

        public TimeUnitType getOffsetTimeUnit() {
            return this.offsetTimeUnit;
        }

        public void setOffsetTimeUnit(TimeUnitType value) {
            this.offsetTimeUnit = value;
        }

        public Integer getOffsetUnitMultiplier() {
            return this.offsetUnitMultiplier;
        }

        public void setOffsetUnitMultiplier(Integer value) {
            this.offsetUnitMultiplier = value;
        }

        public String getOffsetDropTime() {
            return this.offsetDropTime;
        }

        public void setOffsetDropTime(String value) {
            this.offsetDropTime = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class RateDistance {
        @XmlAttribute(name="Unlimited", required=true)
        protected boolean unlimited;
        @XmlAttribute(name="Quantity")
        @XmlSchemaType(name="nonNegativeInteger")
        protected BigInteger quantity;
        @XmlAttribute(name="DistUnitName")
        protected DistanceUnitNameType distUnitName;
        @XmlAttribute(name="VehiclePeriodUnitName")
        protected VehiclePeriodUnitNameType vehiclePeriodUnitName;

        public boolean isUnlimited() {
            return this.unlimited;
        }

        public void setUnlimited(boolean value) {
            this.unlimited = value;
        }

        public BigInteger getQuantity() {
            return this.quantity;
        }

        public void setQuantity(BigInteger value) {
            this.quantity = value;
        }

        public DistanceUnitNameType getDistUnitName() {
            return this.distUnitName;
        }

        public void setDistUnitName(DistanceUnitNameType value) {
            this.distUnitName = value;
        }

        public VehiclePeriodUnitNameType getVehiclePeriodUnitName() {
            return this.vehiclePeriodUnitName;
        }

        public void setVehiclePeriodUnitName(VehiclePeriodUnitNameType value) {
            this.vehiclePeriodUnitName = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class PickupReturnRule {
        @XmlAttribute(name="DayOfWeek")
        protected DayOfWeekType dayOfWeek;
        @XmlAttribute(name="Time")
        protected String time;
        @XmlAttribute(name="RuleType")
        @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
        protected String ruleType;

        public DayOfWeekType getDayOfWeek() {
            return this.dayOfWeek;
        }

        public void setDayOfWeek(DayOfWeekType value) {
            this.dayOfWeek = value;
        }

        public String getTime() {
            return this.time;
        }

        public void setTime(String value) {
            this.time = value;
        }

        public String getRuleType() {
            return this.ruleType;
        }

        public void setRuleType(String value) {
            this.ruleType = value;
        }
    }
}

