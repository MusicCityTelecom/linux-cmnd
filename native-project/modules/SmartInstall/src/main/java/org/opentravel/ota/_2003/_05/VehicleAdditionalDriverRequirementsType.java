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

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VehicleAdditionalDriverRequirementsType", propOrder = "addlDriverInfos")
public class VehicleAdditionalDriverRequirementsType {
   @XmlElement(name = "AddlDriverInfos")
   protected VehicleAdditionalDriverRequirementsType.AddlDriverInfos addlDriverInfos;

   public VehicleAdditionalDriverRequirementsType.AddlDriverInfos getAddlDriverInfos() {
      return this.addlDriverInfos;
   }

   public void setAddlDriverInfos(VehicleAdditionalDriverRequirementsType.AddlDriverInfos value) {
      this.addlDriverInfos = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = {"addlDriverInfo", "vehicles"})
   public static class AddlDriverInfos {
      @XmlElement(name = "AddlDriverInfo", required = true)
      protected List<VehicleAdditionalDriverRequirementsType.AddlDriverInfos.AddlDriverInfo> addlDriverInfo;
      @XmlElement(name = "Vehicles")
      protected VehicleAdditionalDriverRequirementsType.AddlDriverInfos.Vehicles vehicles;
      @XmlAttribute(name = "ChargeType")
      protected String chargeType;
      @XmlAttribute(name = "Amount")
      protected BigDecimal amount;
      @XmlAttribute(name = "CurrencyCode")
      protected String currencyCode;
      @XmlAttribute(name = "DecimalPlaces")
      @XmlSchemaType(name = "nonNegativeInteger")
      protected BigInteger decimalPlaces;
      @XmlAttribute(name = "Start")
      protected String start;
      @XmlAttribute(name = "Duration")
      protected String duration;
      @XmlAttribute(name = "End")
      protected String end;

      public List<VehicleAdditionalDriverRequirementsType.AddlDriverInfos.AddlDriverInfo> getAddlDriverInfo() {
         if (this.addlDriverInfo == null) {
            this.addlDriverInfo = new ArrayList<>();
         }

         return this.addlDriverInfo;
      }

      public VehicleAdditionalDriverRequirementsType.AddlDriverInfos.Vehicles getVehicles() {
         return this.vehicles;
      }

      public void setVehicles(VehicleAdditionalDriverRequirementsType.AddlDriverInfos.Vehicles value) {
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

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class AddlDriverInfo extends FormattedTextType {
         @XmlAttribute(name = "Type", required = true)
         protected LocationDetailRequirementAddlDriverInfoType type;

         public LocationDetailRequirementAddlDriverInfoType getType() {
            return this.type;
         }

         public void setType(LocationDetailRequirementAddlDriverInfoType value) {
            this.type = value;
         }
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = "vehicle")
      public static class Vehicles {
         @XmlElement(name = "Vehicle", required = true)
         protected List<VehicleAdditionalDriverRequirementsType.AddlDriverInfos.Vehicles.Vehicle> vehicle;

         public List<VehicleAdditionalDriverRequirementsType.AddlDriverInfos.Vehicles.Vehicle> getVehicle() {
            if (this.vehicle == null) {
               this.vehicle = new ArrayList<>();
            }

            return this.vehicle;
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "")
         public static class Vehicle extends VehicleCoreType {
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
