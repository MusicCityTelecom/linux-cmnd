package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "VehicleLocationAdditionalDetailsType",
   propOrder = {"vehRentLocInfos", "parkLocation", "counterLocation", "operationSchedules", "shuttle", "oneWayDropLocations", "tpaExtensions"}
)
public class VehicleLocationAdditionalDetailsType {
   @XmlElement(name = "VehRentLocInfos")
   protected VehicleLocationAdditionalDetailsType.VehRentLocInfos vehRentLocInfos;
   @XmlElement(name = "ParkLocation")
   protected VehicleWhereAtFacilityType parkLocation;
   @XmlElement(name = "CounterLocation")
   protected VehicleWhereAtFacilityType counterLocation;
   @XmlElement(name = "OperationSchedules")
   protected OperationSchedulesType operationSchedules;
   @XmlElement(name = "Shuttle")
   protected VehicleLocationAdditionalDetailsType.Shuttle shuttle;
   @XmlElement(name = "OneWayDropLocations")
   protected VehicleLocationAdditionalDetailsType.OneWayDropLocations oneWayDropLocations;
   @XmlElement(name = "TPA_Extensions")
   protected TPAExtensionsType tpaExtensions;

   public VehicleLocationAdditionalDetailsType.VehRentLocInfos getVehRentLocInfos() {
      return this.vehRentLocInfos;
   }

   public void setVehRentLocInfos(VehicleLocationAdditionalDetailsType.VehRentLocInfos value) {
      this.vehRentLocInfos = value;
   }

   public VehicleWhereAtFacilityType getParkLocation() {
      return this.parkLocation;
   }

   public void setParkLocation(VehicleWhereAtFacilityType value) {
      this.parkLocation = value;
   }

   public VehicleWhereAtFacilityType getCounterLocation() {
      return this.counterLocation;
   }

   public void setCounterLocation(VehicleWhereAtFacilityType value) {
      this.counterLocation = value;
   }

   public OperationSchedulesType getOperationSchedules() {
      return this.operationSchedules;
   }

   public void setOperationSchedules(OperationSchedulesType value) {
      this.operationSchedules = value;
   }

   public VehicleLocationAdditionalDetailsType.Shuttle getShuttle() {
      return this.shuttle;
   }

   public void setShuttle(VehicleLocationAdditionalDetailsType.Shuttle value) {
      this.shuttle = value;
   }

   public VehicleLocationAdditionalDetailsType.OneWayDropLocations getOneWayDropLocations() {
      return this.oneWayDropLocations;
   }

   public void setOneWayDropLocations(VehicleLocationAdditionalDetailsType.OneWayDropLocations value) {
      this.oneWayDropLocations = value;
   }

   public TPAExtensionsType getTPAExtensions() {
      return this.tpaExtensions;
   }

   public void setTPAExtensions(TPAExtensionsType value) {
      this.tpaExtensions = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "oneWayDropLocation")
   public static class OneWayDropLocations {
      @XmlElement(name = "OneWayDropLocation", required = true)
      protected List<VehicleLocationAdditionalDetailsType.OneWayDropLocations.OneWayDropLocation> oneWayDropLocation;

      public List<VehicleLocationAdditionalDetailsType.OneWayDropLocations.OneWayDropLocation> getOneWayDropLocation() {
         if (this.oneWayDropLocation == null) {
            this.oneWayDropLocation = new ArrayList<>();
         }

         return this.oneWayDropLocation;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class OneWayDropLocation extends LocationType {
         @XmlAttribute(name = "ExtendedLocationCode")
         protected String extendedLocationCode;

         public String getExtendedLocationCode() {
            return this.extendedLocationCode;
         }

         public void setExtendedLocationCode(String value) {
            this.extendedLocationCode = value;
         }
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = {"shuttleInfos", "operationSchedules"})
   public static class Shuttle {
      @XmlElement(name = "ShuttleInfos")
      protected VehicleLocationAdditionalDetailsType.Shuttle.ShuttleInfos shuttleInfos;
      @XmlElement(name = "OperationSchedules")
      protected OperationSchedulesType operationSchedules;

      public VehicleLocationAdditionalDetailsType.Shuttle.ShuttleInfos getShuttleInfos() {
         return this.shuttleInfos;
      }

      public void setShuttleInfos(VehicleLocationAdditionalDetailsType.Shuttle.ShuttleInfos value) {
         this.shuttleInfos = value;
      }

      public OperationSchedulesType getOperationSchedules() {
         return this.operationSchedules;
      }

      public void setOperationSchedules(OperationSchedulesType value) {
         this.operationSchedules = value;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = "shuttleInfo")
      public static class ShuttleInfos {
         @XmlElement(name = "ShuttleInfo", required = true)
         protected List<VehicleLocationAdditionalDetailsType.Shuttle.ShuttleInfos.ShuttleInfo> shuttleInfo;

         public List<VehicleLocationAdditionalDetailsType.Shuttle.ShuttleInfos.ShuttleInfo> getShuttleInfo() {
            if (this.shuttleInfo == null) {
               this.shuttleInfo = new ArrayList<>();
            }

            return this.shuttleInfo;
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "")
         public static class ShuttleInfo extends FormattedTextType {
            @XmlAttribute(name = "Type", required = true)
            protected LocationDetailShuttleInfoType type;

            public LocationDetailShuttleInfoType getType() {
               return this.type;
            }

            public void setType(LocationDetailShuttleInfoType value) {
               this.type = value;
            }
         }
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "vehRentLocInfo")
   public static class VehRentLocInfos {
      @XmlElement(name = "VehRentLocInfo", required = true)
      protected List<VehicleLocationInformationType> vehRentLocInfo;

      public List<VehicleLocationInformationType> getVehRentLocInfo() {
         if (this.vehRentLocInfo == null) {
            this.vehRentLocInfo = new ArrayList<>();
         }

         return this.vehRentLocInfo;
      }
   }
}
