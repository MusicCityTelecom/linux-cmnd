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
import org.opentravel.ota._2003._05.ActionType;
import org.opentravel.ota._2003._05.CompanyNamePrefType;
import org.opentravel.ota._2003._05.PreferLevelType;
import org.opentravel.ota._2003._05.VehiclePrefType;
import org.opentravel.ota._2003._05.VehicleSpecialReqPrefType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="VehicleProfileRentalPrefType", propOrder={"loyaltyPref", "vendorPref", "paymentFormPref", "coveragePref", "specialReqPref", "vehTypePref", "specialEquipPref"})
public class VehicleProfileRentalPrefType {
    @XmlElement(name="LoyaltyPref")
    protected List<LoyaltyPref> loyaltyPref;
    @XmlElement(name="VendorPref")
    protected List<CompanyNamePrefType> vendorPref;
    @XmlElement(name="PaymentFormPref")
    protected List<PaymentFormPref> paymentFormPref;
    @XmlElement(name="CoveragePref")
    protected List<CoveragePref> coveragePref;
    @XmlElement(name="SpecialReqPref")
    protected List<VehicleSpecialReqPrefType> specialReqPref;
    @XmlElement(name="VehTypePref")
    protected List<VehiclePrefType> vehTypePref;
    @XmlElement(name="SpecialEquipPref")
    protected List<SpecialEquipPref> specialEquipPref;
    @XmlAttribute(name="GasPrePay")
    protected Boolean gasPrePay;
    @XmlAttribute(name="PreferLevel")
    protected PreferLevelType preferLevel;
    @XmlAttribute(name="ShareSynchInd")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    protected String shareSynchInd;
    @XmlAttribute(name="ShareMarketInd")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    protected String shareMarketInd;
    @XmlAttribute(name="SmokingAllowed")
    protected Boolean smokingAllowed;

    public List<LoyaltyPref> getLoyaltyPref() {
        if (this.loyaltyPref == null) {
            this.loyaltyPref = new ArrayList<LoyaltyPref>();
        }
        return this.loyaltyPref;
    }

    public List<CompanyNamePrefType> getVendorPref() {
        if (this.vendorPref == null) {
            this.vendorPref = new ArrayList<CompanyNamePrefType>();
        }
        return this.vendorPref;
    }

    public List<PaymentFormPref> getPaymentFormPref() {
        if (this.paymentFormPref == null) {
            this.paymentFormPref = new ArrayList<PaymentFormPref>();
        }
        return this.paymentFormPref;
    }

    public List<CoveragePref> getCoveragePref() {
        if (this.coveragePref == null) {
            this.coveragePref = new ArrayList<CoveragePref>();
        }
        return this.coveragePref;
    }

    public List<VehicleSpecialReqPrefType> getSpecialReqPref() {
        if (this.specialReqPref == null) {
            this.specialReqPref = new ArrayList<VehicleSpecialReqPrefType>();
        }
        return this.specialReqPref;
    }

    public List<VehiclePrefType> getVehTypePref() {
        if (this.vehTypePref == null) {
            this.vehTypePref = new ArrayList<VehiclePrefType>();
        }
        return this.vehTypePref;
    }

    public List<SpecialEquipPref> getSpecialEquipPref() {
        if (this.specialEquipPref == null) {
            this.specialEquipPref = new ArrayList<SpecialEquipPref>();
        }
        return this.specialEquipPref;
    }

    public Boolean isGasPrePay() {
        return this.gasPrePay;
    }

    public void setGasPrePay(Boolean value) {
        this.gasPrePay = value;
    }

    public PreferLevelType getPreferLevel() {
        return this.preferLevel;
    }

    public void setPreferLevel(PreferLevelType value) {
        this.preferLevel = value;
    }

    public String getShareSynchInd() {
        return this.shareSynchInd;
    }

    public void setShareSynchInd(String value) {
        this.shareSynchInd = value;
    }

    public String getShareMarketInd() {
        return this.shareMarketInd;
    }

    public void setShareMarketInd(String value) {
        this.shareMarketInd = value;
    }

    public Boolean isSmokingAllowed() {
        return this.smokingAllowed;
    }

    public void setSmokingAllowed(Boolean value) {
        this.smokingAllowed = value;
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

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class PaymentFormPref {
        @XmlAttribute(name="RPH")
        protected String rph;
        @XmlAttribute(name="PreferLevel")
        protected PreferLevelType preferLevel;

        public String getRPH() {
            return this.rph;
        }

        public void setRPH(String value) {
            this.rph = value;
        }

        public PreferLevelType getPreferLevel() {
            return this.preferLevel;
        }

        public void setPreferLevel(PreferLevelType value) {
            this.preferLevel = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class LoyaltyPref {
        @XmlAttribute(name="RPH")
        protected String rph;
        @XmlAttribute(name="PreferLevel")
        protected PreferLevelType preferLevel;

        public String getRPH() {
            return this.rph;
        }

        public void setRPH(String value) {
            this.rph = value;
        }

        public PreferLevelType getPreferLevel() {
            return this.preferLevel;
        }

        public void setPreferLevel(PreferLevelType value) {
            this.preferLevel = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class CoveragePref {
        @XmlAttribute(name="CoverageType", required=true)
        protected String coverageType;
        @XmlAttribute(name="Code")
        protected String code;
        @XmlAttribute(name="PreferLevel")
        protected PreferLevelType preferLevel;

        public String getCoverageType() {
            return this.coverageType;
        }

        public void setCoverageType(String value) {
            this.coverageType = value;
        }

        public String getCode() {
            return this.code;
        }

        public void setCode(String value) {
            this.code = value;
        }

        public PreferLevelType getPreferLevel() {
            return this.preferLevel;
        }

        public void setPreferLevel(PreferLevelType value) {
            this.preferLevel = value;
        }
    }
}

