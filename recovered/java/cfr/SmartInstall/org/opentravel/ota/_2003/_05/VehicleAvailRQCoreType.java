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
import org.opentravel.ota._2003._05.ActionType;
import org.opentravel.ota._2003._05.CompanyNamePrefType;
import org.opentravel.ota._2003._05.InventoryStatusType;
import org.opentravel.ota._2003._05.PreferLevelType;
import org.opentravel.ota._2003._05.RatePeriodSimpleType;
import org.opentravel.ota._2003._05.TPAExtensionsType;
import org.opentravel.ota._2003._05.TimeUnitType;
import org.opentravel.ota._2003._05.VehiclePrefType;
import org.opentravel.ota._2003._05.VehicleRentalCoreType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="VehicleAvailRQCoreType", propOrder={"vehRentalCore", "vendorPrefs", "vehPrefs", "driverType", "rateQualifier", "rateRange", "specialEquipPrefs", "tpaExtensions"})
public class VehicleAvailRQCoreType {
    @XmlElement(name="VehRentalCore", required=true)
    protected VehicleRentalCoreType vehRentalCore;
    @XmlElement(name="VendorPrefs")
    protected VendorPrefs vendorPrefs;
    @XmlElement(name="VehPrefs")
    protected VehPrefs vehPrefs;
    @XmlElement(name="DriverType")
    protected List<DriverType> driverType;
    @XmlElement(name="RateQualifier")
    protected List<RateQualifier> rateQualifier;
    @XmlElement(name="RateRange")
    protected RateRange rateRange;
    @XmlElement(name="SpecialEquipPrefs")
    protected SpecialEquipPrefs specialEquipPrefs;
    @XmlElement(name="TPA_Extensions")
    protected TPAExtensionsType tpaExtensions;
    @XmlAttribute(name="Status")
    protected InventoryStatusType status;
    @XmlAttribute(name="TargetSource")
    protected String targetSource;

    public VehicleRentalCoreType getVehRentalCore() {
        return this.vehRentalCore;
    }

    public void setVehRentalCore(VehicleRentalCoreType value) {
        this.vehRentalCore = value;
    }

    public VendorPrefs getVendorPrefs() {
        return this.vendorPrefs;
    }

    public void setVendorPrefs(VendorPrefs value) {
        this.vendorPrefs = value;
    }

    public VehPrefs getVehPrefs() {
        return this.vehPrefs;
    }

    public void setVehPrefs(VehPrefs value) {
        this.vehPrefs = value;
    }

    public List<DriverType> getDriverType() {
        if (this.driverType == null) {
            this.driverType = new ArrayList<DriverType>();
        }
        return this.driverType;
    }

    public List<RateQualifier> getRateQualifier() {
        if (this.rateQualifier == null) {
            this.rateQualifier = new ArrayList<RateQualifier>();
        }
        return this.rateQualifier;
    }

    public RateRange getRateRange() {
        return this.rateRange;
    }

    public void setRateRange(RateRange value) {
        this.rateRange = value;
    }

    public SpecialEquipPrefs getSpecialEquipPrefs() {
        return this.specialEquipPrefs;
    }

    public void setSpecialEquipPrefs(SpecialEquipPrefs value) {
        this.specialEquipPrefs = value;
    }

    public TPAExtensionsType getTPAExtensions() {
        return this.tpaExtensions;
    }

    public void setTPAExtensions(TPAExtensionsType value) {
        this.tpaExtensions = value;
    }

    public InventoryStatusType getStatus() {
        return this.status;
    }

    public void setStatus(InventoryStatusType value) {
        this.status = value;
    }

    public String getTargetSource() {
        return this.targetSource;
    }

    public void setTargetSource(String value) {
        this.targetSource = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"vendorPref"})
    public static class VendorPrefs {
        @XmlElement(name="VendorPref", required=true)
        protected List<VendorPref> vendorPref;
        @XmlAttribute(name="ParticipationLevelCode")
        protected String participationLevelCode;
        @XmlAttribute(name="LocationCategory")
        protected String locationCategory;

        public List<VendorPref> getVendorPref() {
            if (this.vendorPref == null) {
                this.vendorPref = new ArrayList<VendorPref>();
            }
            return this.vendorPref;
        }

        public String getParticipationLevelCode() {
            return this.participationLevelCode;
        }

        public void setParticipationLevelCode(String value) {
            this.participationLevelCode = value;
        }

        public String getLocationCategory() {
            return this.locationCategory;
        }

        public void setLocationCategory(String value) {
            this.locationCategory = value;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="")
        public static class VendorPref
        extends CompanyNamePrefType {
            @XmlAttribute(name="CorpDiscountNmbr")
            protected String corpDiscountNmbr;

            public String getCorpDiscountNmbr() {
                return this.corpDiscountNmbr;
            }

            public void setCorpDiscountNmbr(String value) {
                this.corpDiscountNmbr = value;
            }
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"vehPref"})
    public static class VehPrefs {
        @XmlElement(name="VehPref", required=true)
        protected List<VehPref> vehPref;

        public List<VehPref> getVehPref() {
            if (this.vehPref == null) {
                this.vehPref = new ArrayList<VehPref>();
            }
            return this.vehPref;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="")
        public static class VehPref
        extends VehiclePrefType {
            @XmlAttribute(name="UpSellInd")
            protected Boolean upSellInd;

            public Boolean isUpSellInd() {
                return this.upSellInd;
            }

            public void setUpSellInd(Boolean value) {
                this.upSellInd = value;
            }
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"specialEquipPref"})
    public static class SpecialEquipPrefs {
        @XmlElement(name="SpecialEquipPref", required=true)
        protected List<SpecialEquipPref> specialEquipPref;

        public List<SpecialEquipPref> getSpecialEquipPref() {
            if (this.specialEquipPref == null) {
                this.specialEquipPref = new ArrayList<SpecialEquipPref>();
            }
            return this.specialEquipPref;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="")
        public static class SpecialEquipPref {
            @XmlAttribute(name="Action")
            protected ActionType action;
            @XmlAttribute(name="EquipType", required=true)
            protected String equipType;
            @XmlAttribute(name="Quantity")
            @XmlSchemaType(name="positiveInteger")
            protected BigInteger quantity;
            @XmlAttribute(name="PreferLevel")
            protected PreferLevelType preferLevel;

            public ActionType getAction() {
                return this.action;
            }

            public void setAction(ActionType value) {
                this.action = value;
            }

            public String getEquipType() {
                return this.equipType;
            }

            public void setEquipType(String value) {
                this.equipType = value;
            }

            public BigInteger getQuantity() {
                return this.quantity;
            }

            public void setQuantity(BigInteger value) {
                this.quantity = value;
            }

            public PreferLevelType getPreferLevel() {
                return this.preferLevel;
            }

            public void setPreferLevel(PreferLevelType value) {
                this.preferLevel = value;
            }
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class RateRange {
        @XmlAttribute(name="MinRate")
        protected BigDecimal minRate;
        @XmlAttribute(name="MaxRate")
        protected BigDecimal maxRate;
        @XmlAttribute(name="FixedRate")
        protected BigDecimal fixedRate;
        @XmlAttribute(name="RateTimeUnit")
        protected TimeUnitType rateTimeUnit;
        @XmlAttribute(name="CurrencyCode")
        protected String currencyCode;
        @XmlAttribute(name="DecimalPlaces")
        @XmlSchemaType(name="nonNegativeInteger")
        protected BigInteger decimalPlaces;

        public BigDecimal getMinRate() {
            return this.minRate;
        }

        public void setMinRate(BigDecimal value) {
            this.minRate = value;
        }

        public BigDecimal getMaxRate() {
            return this.maxRate;
        }

        public void setMaxRate(BigDecimal value) {
            this.maxRate = value;
        }

        public BigDecimal getFixedRate() {
            return this.fixedRate;
        }

        public void setFixedRate(BigDecimal value) {
            this.fixedRate = value;
        }

        public TimeUnitType getRateTimeUnit() {
            return this.rateTimeUnit;
        }

        public void setRateTimeUnit(TimeUnitType value) {
            this.rateTimeUnit = value;
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
    @XmlType(name="")
    public static class RateQualifier {
        @XmlAttribute(name="TravelPurpose")
        protected String travelPurpose;
        @XmlAttribute(name="RateCategory")
        protected String rateCategory;
        @XmlAttribute(name="CorpDiscountNmbr")
        protected String corpDiscountNmbr;
        @XmlAttribute(name="RateQualifier")
        protected String rateQualifier;
        @XmlAttribute(name="RatePeriod")
        protected RatePeriodSimpleType ratePeriod;
        @XmlAttribute(name="GuaranteedInd")
        protected Boolean guaranteedInd;
        @XmlAttribute(name="PromotionCode")
        protected String promotionCode;
        @XmlAttribute(name="PromotionVendorCode")
        protected List<String> promotionVendorCode;

        public String getTravelPurpose() {
            return this.travelPurpose;
        }

        public void setTravelPurpose(String value) {
            this.travelPurpose = value;
        }

        public String getRateCategory() {
            return this.rateCategory;
        }

        public void setRateCategory(String value) {
            this.rateCategory = value;
        }

        public String getCorpDiscountNmbr() {
            return this.corpDiscountNmbr;
        }

        public void setCorpDiscountNmbr(String value) {
            this.corpDiscountNmbr = value;
        }

        public String getRateQualifier() {
            return this.rateQualifier;
        }

        public void setRateQualifier(String value) {
            this.rateQualifier = value;
        }

        public RatePeriodSimpleType getRatePeriod() {
            return this.ratePeriod;
        }

        public void setRatePeriod(RatePeriodSimpleType value) {
            this.ratePeriod = value;
        }

        public Boolean isGuaranteedInd() {
            return this.guaranteedInd;
        }

        public void setGuaranteedInd(Boolean value) {
            this.guaranteedInd = value;
        }

        public String getPromotionCode() {
            return this.promotionCode;
        }

        public void setPromotionCode(String value) {
            this.promotionCode = value;
        }

        public List<String> getPromotionVendorCode() {
            if (this.promotionVendorCode == null) {
                this.promotionVendorCode = new ArrayList<String>();
            }
            return this.promotionVendorCode;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class DriverType {
        @XmlAttribute(name="Age")
        protected Integer age;
        @XmlAttribute(name="Code")
        protected String code;
        @XmlAttribute(name="CodeContext")
        protected String codeContext;
        @XmlAttribute(name="URI")
        @XmlSchemaType(name="anyURI")
        protected String uri;
        @XmlAttribute(name="Quantity")
        @XmlSchemaType(name="nonNegativeInteger")
        protected BigInteger quantity;

        public Integer getAge() {
            return this.age;
        }

        public void setAge(Integer value) {
            this.age = value;
        }

        public String getCode() {
            return this.code;
        }

        public void setCode(String value) {
            this.code = value;
        }

        public String getCodeContext() {
            return this.codeContext;
        }

        public void setCodeContext(String value) {
            this.codeContext = value;
        }

        public String getURI() {
            return this.uri;
        }

        public void setURI(String value) {
            this.uri = value;
        }

        public BigInteger getQuantity() {
            return this.quantity;
        }

        public void setQuantity(BigInteger value) {
            this.quantity = value;
        }
    }
}

