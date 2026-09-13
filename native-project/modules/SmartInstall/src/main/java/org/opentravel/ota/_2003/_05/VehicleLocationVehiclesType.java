package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VehicleLocationVehiclesType", propOrder = {"vehicleInfos", "vehicle"})
public class VehicleLocationVehiclesType {
   @XmlElement(name = "VehicleInfos")
   protected VehicleLocationVehiclesType.VehicleInfos vehicleInfos;
   @XmlElement(name = "Vehicle")
   protected List<VehicleLocationVehiclesType.Vehicle> vehicle;

   public VehicleLocationVehiclesType.VehicleInfos getVehicleInfos() {
      return this.vehicleInfos;
   }

   public void setVehicleInfos(VehicleLocationVehiclesType.VehicleInfos value) {
      this.vehicleInfos = value;
   }

   public List<VehicleLocationVehiclesType.Vehicle> getVehicle() {
      if (this.vehicle == null) {
         this.vehicle = new ArrayList<>();
      }

      return this.vehicle;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "text")
   public static class Vehicle extends VehicleType {
      @XmlElement(name = "Text")
      protected List<FormattedTextType> text;
      @XmlAttribute(name = "IsConfirmableInd")
      protected Boolean isConfirmableInd;
      @XmlAttribute(name = "DistanceUnit")
      protected DistanceUnitNameType distanceUnit;
      @XmlAttribute(name = "DistancePerFuelUnit")
      protected Integer distancePerFuelUnit;

      public List<FormattedTextType> getText() {
         if (this.text == null) {
            this.text = new ArrayList<>();
         }

         return this.text;
      }

      public Boolean isIsConfirmableInd() {
         return this.isConfirmableInd;
      }

      public void setIsConfirmableInd(Boolean value) {
         this.isConfirmableInd = value;
      }

      public DistanceUnitNameType getDistanceUnit() {
         return this.distanceUnit;
      }

      public void setDistanceUnit(DistanceUnitNameType value) {
         this.distanceUnit = value;
      }

      public Integer getDistancePerFuelUnit() {
         return this.distancePerFuelUnit;
      }

      public void setDistancePerFuelUnit(Integer value) {
         this.distancePerFuelUnit = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "vehicleInfo")
   public static class VehicleInfos {
      @XmlElement(name = "VehicleInfo", required = true)
      protected List<VehicleLocationVehiclesType.VehicleInfos.VehicleInfo> vehicleInfo;

      public List<VehicleLocationVehiclesType.VehicleInfos.VehicleInfo> getVehicleInfo() {
         if (this.vehicleInfo == null) {
            this.vehicleInfo = new ArrayList<>();
         }

         return this.vehicleInfo;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class VehicleInfo extends FormattedTextType {
         @XmlAttribute(name = "Type", required = true)
         protected LocationDetailVehicleInfoType type;

         public LocationDetailVehicleInfoType getType() {
            return this.type;
         }

         public void setType(LocationDetailVehicleInfoType value) {
            this.type = value;
         }
      }
   }
}
