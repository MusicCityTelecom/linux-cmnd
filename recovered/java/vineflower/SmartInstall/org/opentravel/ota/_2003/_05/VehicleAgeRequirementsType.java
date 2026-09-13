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
@XmlType(name = "VehicleAgeRequirementsType", propOrder = "age")
public class VehicleAgeRequirementsType {
   @XmlElement(name = "Age")
   protected VehicleAgeRequirementsType.Age age;

   public VehicleAgeRequirementsType.Age getAge() {
      return this.age;
   }

   public void setAge(VehicleAgeRequirementsType.Age value) {
      this.age = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = {"ageSurcharge", "ageInfos", "vehicles"})
   public static class Age {
      @XmlElement(name = "AgeSurcharge")
      protected List<VehicleAgeRequirementsType.Age.AgeSurcharge> ageSurcharge;
      @XmlElement(name = "AgeInfos")
      protected VehicleAgeRequirementsType.Age.AgeInfos ageInfos;
      @XmlElement(name = "Vehicles", required = true)
      protected VehicleAgeRequirementsType.Age.Vehicles vehicles;
      @XmlAttribute(name = "MinimumAge")
      @XmlSchemaType(name = "positiveInteger")
      protected BigInteger minimumAge;
      @XmlAttribute(name = "MaximumAge")
      @XmlSchemaType(name = "positiveInteger")
      protected BigInteger maximumAge;

      public List<VehicleAgeRequirementsType.Age.AgeSurcharge> getAgeSurcharge() {
         if (this.ageSurcharge == null) {
            this.ageSurcharge = new ArrayList<>();
         }

         return this.ageSurcharge;
      }

      public VehicleAgeRequirementsType.Age.AgeInfos getAgeInfos() {
         return this.ageInfos;
      }

      public void setAgeInfos(VehicleAgeRequirementsType.Age.AgeInfos value) {
         this.ageInfos = value;
      }

      public VehicleAgeRequirementsType.Age.Vehicles getVehicles() {
         return this.vehicles;
      }

      public void setVehicles(VehicleAgeRequirementsType.Age.Vehicles value) {
         this.vehicles = value;
      }

      public BigInteger getMinimumAge() {
         return this.minimumAge;
      }

      public void setMinimumAge(BigInteger value) {
         this.minimumAge = value;
      }

      public BigInteger getMaximumAge() {
         return this.maximumAge;
      }

      public void setMaximumAge(BigInteger value) {
         this.maximumAge = value;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = "ageInfo")
      public static class AgeInfos {
         @XmlElement(name = "AgeInfo", required = true)
         protected List<VehicleAgeRequirementsType.Age.AgeInfos.AgeInfo> ageInfo;

         public List<VehicleAgeRequirementsType.Age.AgeInfos.AgeInfo> getAgeInfo() {
            if (this.ageInfo == null) {
               this.ageInfo = new ArrayList<>();
            }

            return this.ageInfo;
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "")
         public static class AgeInfo extends FormattedTextType {
            @XmlAttribute(name = "Type", required = true)
            protected LocationDetailRequirementAgeInfoType type;

            public LocationDetailRequirementAgeInfoType getType() {
               return this.type;
            }

            public void setType(LocationDetailRequirementAgeInfoType value) {
               this.type = value;
            }
         }
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class AgeSurcharge {
         @XmlAttribute(name = "Age")
         protected String age;
         @XmlAttribute(name = "ChargeType")
         protected String chargeType;
         @XmlAttribute(name = "Amount")
         protected BigDecimal amount;
         @XmlAttribute(name = "CurrencyCode")
         protected String currencyCode;
         @XmlAttribute(name = "DecimalPlaces")
         @XmlSchemaType(name = "nonNegativeInteger")
         protected BigInteger decimalPlaces;

         public String getAge() {
            return this.age;
         }

         public void setAge(String value) {
            this.age = value;
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
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = "vehicle")
      public static class Vehicles {
         @XmlElement(name = "Vehicle", required = true)
         protected List<VehicleAgeRequirementsType.Age.Vehicles.Vehicle> vehicle;

         public List<VehicleAgeRequirementsType.Age.Vehicles.Vehicle> getVehicle() {
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
