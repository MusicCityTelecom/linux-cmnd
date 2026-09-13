package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VehicleLocationLiabilitiesType", propOrder = {"coverages", "info", "tpaExtensions"})
public class VehicleLocationLiabilitiesType {
   @XmlElement(name = "Coverages")
   protected VehicleLocationLiabilitiesType.Coverages coverages;
   @XmlElement(name = "Info")
   protected FormattedTextType info;
   @XmlElement(name = "TPA_Extensions")
   protected TPAExtensionsType tpaExtensions;

   public VehicleLocationLiabilitiesType.Coverages getCoverages() {
      return this.coverages;
   }

   public void setCoverages(VehicleLocationLiabilitiesType.Coverages value) {
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

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "coverage")
   public static class Coverages {
      @XmlElement(name = "Coverage")
      protected List<VehicleLocationLiabilitiesType.Coverages.Coverage> coverage;

      public List<VehicleLocationLiabilitiesType.Coverages.Coverage> getCoverage() {
         if (this.coverage == null) {
            this.coverage = new ArrayList<>();
         }

         return this.coverage;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = {"coverageInfo", "coverageFees"})
      public static class Coverage {
         @XmlElement(name = "CoverageInfo")
         protected FormattedTextType coverageInfo;
         @XmlElement(name = "CoverageFees")
         protected VehicleLocationLiabilitiesType.Coverages.Coverage.CoverageFees coverageFees;
         @XmlAttribute(name = "Type", required = true)
         protected String type;
         @XmlAttribute(name = "RequiredInd")
         protected Boolean requiredInd;

         public FormattedTextType getCoverageInfo() {
            return this.coverageInfo;
         }

         public void setCoverageInfo(FormattedTextType value) {
            this.coverageInfo = value;
         }

         public VehicleLocationLiabilitiesType.Coverages.Coverage.CoverageFees getCoverageFees() {
            return this.coverageFees;
         }

         public void setCoverageFees(VehicleLocationLiabilitiesType.Coverages.Coverage.CoverageFees value) {
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

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "", propOrder = "coverageFee")
         public static class CoverageFees {
            @XmlElement(name = "CoverageFee", required = true)
            protected List<VehicleLocationLiabilitiesType.Coverages.Coverage.CoverageFees.CoverageFee> coverageFee;

            public List<VehicleLocationLiabilitiesType.Coverages.Coverage.CoverageFees.CoverageFee> getCoverageFee() {
               if (this.coverageFee == null) {
                  this.coverageFee = new ArrayList<>();
               }

               return this.coverageFee;
            }

            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {"charge", "vehicles", "deductible"})
            public static class CoverageFee {
               @XmlElement(name = "Charge", required = true)
               protected VehicleChargeType charge;
               @XmlElement(name = "Vehicles")
               protected VehicleLocationLiabilitiesType.Coverages.Coverage.CoverageFees.CoverageFee.Vehicles vehicles;
               @XmlElement(name = "Deductible")
               protected DeductibleType deductible;

               public VehicleChargeType getCharge() {
                  return this.charge;
               }

               public void setCharge(VehicleChargeType value) {
                  this.charge = value;
               }

               public VehicleLocationLiabilitiesType.Coverages.Coverage.CoverageFees.CoverageFee.Vehicles getVehicles() {
                  return this.vehicles;
               }

               public void setVehicles(VehicleLocationLiabilitiesType.Coverages.Coverage.CoverageFees.CoverageFee.Vehicles value) {
                  this.vehicles = value;
               }

               public DeductibleType getDeductible() {
                  return this.deductible;
               }

               public void setDeductible(DeductibleType value) {
                  this.deductible = value;
               }

               @XmlAccessorType(XmlAccessType.FIELD)
               @XmlType(name = "", propOrder = "vehicle")
               public static class Vehicles {
                  @XmlElement(name = "Vehicle", required = true)
                  protected List<VehicleLocationLiabilitiesType.Coverages.Coverage.CoverageFees.CoverageFee.Vehicles.Vehicle> vehicle;

                  public List<VehicleLocationLiabilitiesType.Coverages.Coverage.CoverageFees.CoverageFee.Vehicles.Vehicle> getVehicle() {
                     if (this.vehicle == null) {
                        this.vehicle = new ArrayList<>();
                     }

                     return this.vehicle;
                  }

                  @XmlAccessorType(XmlAccessType.FIELD)
                  @XmlType(name = "")
                  public static class Vehicle extends VehicleType {
                     @XmlAttribute(name = "IncludeExclude")
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
