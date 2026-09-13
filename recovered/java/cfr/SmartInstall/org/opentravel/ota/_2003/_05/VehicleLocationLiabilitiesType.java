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
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.DeductibleType;
import org.opentravel.ota._2003._05.FormattedTextType;
import org.opentravel.ota._2003._05.IncludeExcludeType;
import org.opentravel.ota._2003._05.TPAExtensionsType;
import org.opentravel.ota._2003._05.VehicleChargeType;
import org.opentravel.ota._2003._05.VehicleType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="VehicleLocationLiabilitiesType", propOrder={"coverages", "info", "tpaExtensions"})
public class VehicleLocationLiabilitiesType {
    @XmlElement(name="Coverages")
    protected Coverages coverages;
    @XmlElement(name="Info")
    protected FormattedTextType info;
    @XmlElement(name="TPA_Extensions")
    protected TPAExtensionsType tpaExtensions;

    public Coverages getCoverages() {
        return this.coverages;
    }

    public void setCoverages(Coverages value) {
        this.coverages = value;
    }

    public FormattedTextType getInfo() {
        return this.info;
    }

    public void setInfo(FormattedTextType value) {
        this.info = value;
    }

    public TPAExtensionsType getTPAExtensions() {
        return this.tpaExtensions;
    }

    public void setTPAExtensions(TPAExtensionsType value) {
        this.tpaExtensions = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"coverage"})
    public static class Coverages {
        @XmlElement(name="Coverage")
        protected List<Coverage> coverage;

        public List<Coverage> getCoverage() {
            if (this.coverage == null) {
                this.coverage = new ArrayList<Coverage>();
            }
            return this.coverage;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="", propOrder={"coverageInfo", "coverageFees"})
        public static class Coverage {
            @XmlElement(name="CoverageInfo")
            protected FormattedTextType coverageInfo;
            @XmlElement(name="CoverageFees")
            protected CoverageFees coverageFees;
            @XmlAttribute(name="Type", required=true)
            protected String type;
            @XmlAttribute(name="RequiredInd")
            protected Boolean requiredInd;

            public FormattedTextType getCoverageInfo() {
                return this.coverageInfo;
            }

            public void setCoverageInfo(FormattedTextType value) {
                this.coverageInfo = value;
            }

            public CoverageFees getCoverageFees() {
                return this.coverageFees;
            }

            public void setCoverageFees(CoverageFees value) {
                this.coverageFees = value;
            }

            public String getType() {
                return this.type;
            }

            public void setType(String value) {
                this.type = value;
            }

            public Boolean isRequiredInd() {
                return this.requiredInd;
            }

            public void setRequiredInd(Boolean value) {
                this.requiredInd = value;
            }

            @XmlAccessorType(value=XmlAccessType.FIELD)
            @XmlType(name="", propOrder={"coverageFee"})
            public static class CoverageFees {
                @XmlElement(name="CoverageFee", required=true)
                protected List<CoverageFee> coverageFee;

                public List<CoverageFee> getCoverageFee() {
                    if (this.coverageFee == null) {
                        this.coverageFee = new ArrayList<CoverageFee>();
                    }
                    return this.coverageFee;
                }

                @XmlAccessorType(value=XmlAccessType.FIELD)
                @XmlType(name="", propOrder={"charge", "vehicles", "deductible"})
                public static class CoverageFee {
                    @XmlElement(name="Charge", required=true)
                    protected VehicleChargeType charge;
                    @XmlElement(name="Vehicles")
                    protected Vehicles vehicles;
                    @XmlElement(name="Deductible")
                    protected DeductibleType deductible;

                    public VehicleChargeType getCharge() {
                        return this.charge;
                    }

                    public void setCharge(VehicleChargeType value) {
                        this.charge = value;
                    }

                    public Vehicles getVehicles() {
                        return this.vehicles;
                    }

                    public void setVehicles(Vehicles value) {
                        this.vehicles = value;
                    }

                    public DeductibleType getDeductible() {
                        return this.deductible;
                    }

                    public void setDeductible(DeductibleType value) {
                        this.deductible = value;
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
                        extends VehicleType {
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
                }
            }
        }
    }
}

