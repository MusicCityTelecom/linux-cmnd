package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VehicleCoreType", propOrder = {"vehType", "vehClass"})
@XmlSeeAlso(
   {
         VehicleAdditionalDriverRequirementsType.AddlDriverInfos.Vehicles.Vehicle.class,
         VehicleAgeRequirementsType.Age.Vehicles.Vehicle.class,
         VehiclePrefType.class,
         VehicleType.class
   }
)
public class VehicleCoreType {
   @XmlElement(name = "VehType")
   protected VehicleCoreType.VehType vehType;
   @XmlElement(name = "VehClass")
   protected VehicleCoreType.VehClass vehClass;
   @XmlAttribute(name = "AirConditionInd")
   protected Boolean airConditionInd;
   @XmlAttribute(name = "TransmissionType")
   protected VehicleTransmissionType transmissionType;
   @XmlAttribute(name = "FuelType")
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   protected String fuelType;
   @XmlAttribute(name = "DriveType")
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   protected String driveType;

   public VehicleCoreType.VehType getVehType() {
      return this.vehType;
   }

   public void setVehType(VehicleCoreType.VehType value) {
      this.vehType = value;
   }

   public VehicleCoreType.VehClass getVehClass() {
      return this.vehClass;
   }

   public void setVehClass(VehicleCoreType.VehClass value) {
      this.vehClass = value;
   }

   public Boolean isAirConditionInd() {
      return this.airConditionInd;
   }

   public void setAirConditionInd(Boolean value) {
      this.airConditionInd = value;
   }

   public VehicleTransmissionType getTransmissionType() {
      return this.transmissionType;
   }

   public void setTransmissionType(VehicleTransmissionType value) {
      this.transmissionType = value;
   }

   public String getFuelType() {
      return this.fuelType;
   }

   public void setFuelType(String value) {
      this.fuelType = value;
   }

   public String getDriveType() {
      return this.driveType;
   }

   public void setDriveType(String value) {
      this.driveType = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class VehClass {
      @XmlAttribute(name = "Size", required = true)
      protected String size;

      public String getSize() {
         return this.size;
      }

      public void setSize(String value) {
         this.size = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class VehType {
      @XmlAttribute(name = "VehicleCategory", required = true)
      protected String vehicleCategory;
      @XmlAttribute(name = "DoorCount")
      protected String doorCount;

      public String getVehicleCategory() {
         return this.vehicleCategory;
      }

      public void setVehicleCategory(String value) {
         this.vehicleCategory = value;
      }

      public String getDoorCount() {
         return this.doorCount;
      }

      public void setDoorCount(String value) {
         this.doorCount = value;
      }
   }
}
