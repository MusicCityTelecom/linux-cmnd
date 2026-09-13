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
@XmlType(
   name = "VehicleReservationRQCoreType",
   propOrder = {
         "uniqueID",
         "vehRentalCore",
         "customer",
         "vendorPref",
         "vehPref",
         "driverType",
         "rateQualifier",
         "fees",
         "vehicleCharges",
         "specialEquipPrefs",
         "rateDistance",
         "totalCharge",
         "queue",
         "tpaExtensions"
   }
)
public class VehicleReservationRQCoreType {
   @XmlElement(name = "UniqueID")
   protected List<UniqueIDType> uniqueID;
   @XmlElement(name = "VehRentalCore")
   protected VehicleRentalCoreType vehRentalCore;
   @XmlElement(name = "Customer")
   protected CustomerPrimaryAdditionalType customer;
   @XmlElement(name = "VendorPref")
   protected CompanyNamePrefType vendorPref;
   @XmlElement(name = "VehPref")
   protected VehiclePrefType vehPref;
   @XmlElement(name = "DriverType")
   protected List<VehicleReservationRQCoreType.DriverType> driverType;
   @XmlElement(name = "RateQualifier")
   protected RateQualifierType rateQualifier;
   @XmlElement(name = "Fees")
   protected VehicleReservationRQCoreType.Fees fees;
   @XmlElement(name = "VehicleCharges")
   protected VehicleReservationRQCoreType.VehicleCharges vehicleCharges;
   @XmlElement(name = "SpecialEquipPrefs")
   protected VehicleReservationRQCoreType.SpecialEquipPrefs specialEquipPrefs;
   @XmlElement(name = "RateDistance")
   protected List<VehicleReservationRQCoreType.RateDistance> rateDistance;
   @XmlElement(name = "TotalCharge")
   protected VehicleReservationRQCoreType.TotalCharge totalCharge;
   @XmlElement(name = "Queue")
   protected VehicleReservationRQCoreType.Queue queue;
   @XmlElement(name = "TPA_Extensions")
   protected TPAExtensionsType tpaExtensions;
   @XmlAttribute(name = "Status")
   protected String status;

   public List<UniqueIDType> getUniqueID() {
      if (this.uniqueID == null) {
         this.uniqueID = new ArrayList<>();
      }

      return this.uniqueID;
   }

   public VehicleRentalCoreType getVehRentalCore() {
      return this.vehRentalCore;
   }

   public void setVehRentalCore(VehicleRentalCoreType value) {
      this.vehRentalCore = value;
   }

   public CustomerPrimaryAdditionalType getCustomer() {
      return this.customer;
   }

   public void setCustomer(CustomerPrimaryAdditionalType value) {
      this.customer = value;
   }

   public CompanyNamePrefType getVendorPref() {
      return this.vendorPref;
   }

   public void setVendorPref(CompanyNamePrefType value) {
      this.vendorPref = value;
   }

   public VehiclePrefType getVehPref() {
      return this.vehPref;
   }

   public void setVehPref(VehiclePrefType value) {
      this.vehPref = value;
   }

   public List<VehicleReservationRQCoreType.DriverType> getDriverType() {
      if (this.driverType == null) {
         this.driverType = new ArrayList<>();
      }

      return this.driverType;
   }

   public RateQualifierType getRateQualifier() {
      return this.rateQualifier;
   }

   public void setRateQualifier(RateQualifierType value) {
      this.rateQualifier = value;
   }

   public VehicleReservationRQCoreType.Fees getFees() {
      return this.fees;
   }

   public void setFees(VehicleReservationRQCoreType.Fees value) {
      this.fees = value;
   }

   public VehicleReservationRQCoreType.VehicleCharges getVehicleCharges() {
      return this.vehicleCharges;
   }

   public void setVehicleCharges(VehicleReservationRQCoreType.VehicleCharges value) {
      this.vehicleCharges = value;
   }

   public VehicleReservationRQCoreType.SpecialEquipPrefs getSpecialEquipPrefs() {
      return this.specialEquipPrefs;
   }

   public void setSpecialEquipPrefs(VehicleReservationRQCoreType.SpecialEquipPrefs value) {
      this.specialEquipPrefs = value;
   }

   public List<VehicleReservationRQCoreType.RateDistance> getRateDistance() {
      if (this.rateDistance == null) {
         this.rateDistance = new ArrayList<>();
      }

      return this.rateDistance;
   }

   public VehicleReservationRQCoreType.TotalCharge getTotalCharge() {
      return this.totalCharge;
   }

   public void setTotalCharge(VehicleReservationRQCoreType.TotalCharge value) {
      this.totalCharge = value;
   }

   public VehicleReservationRQCoreType.Queue getQueue() {
      return this.queue;
   }

   public void setQueue(VehicleReservationRQCoreType.Queue value) {
      this.queue = value;
   }

   public TPAExtensionsType getTPAExtensions() {
      return this.tpaExtensions;
   }

   public void setTPAExtensions(TPAExtensionsType value) {
      this.tpaExtensions = value;
   }

   public String getStatus() {
      return this.status;
   }

   public void setStatus(String value) {
      this.status = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class DriverType {
      @XmlAttribute(name = "Age")
      protected Integer age;
      @XmlAttribute(name = "Code")
      protected String code;
      @XmlAttribute(name = "CodeContext")
      protected String codeContext;
      @XmlAttribute(name = "URI")
      @XmlSchemaType(name = "anyURI")
      protected String uri;
      @XmlAttribute(name = "Quantity")
      @XmlSchemaType(name = "nonNegativeInteger")
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

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "fee")
   public static class Fees {
      @XmlElement(name = "Fee", required = true)
      protected List<VehicleChargePurposeType> fee;

      public List<VehicleChargePurposeType> getFee() {
         if (this.fee == null) {
            this.fee = new ArrayList<>();
         }

         return this.fee;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class Queue {
      @XmlAttribute(name = "PseudoCityCode")
      protected String pseudoCityCode;
      @XmlAttribute(name = "QueueNumber")
      protected String queueNumber;
      @XmlAttribute(name = "QueueCategory")
      protected String queueCategory;
      @XmlAttribute(name = "SystemCode")
      protected String systemCode;
      @XmlAttribute(name = "QueueID")
      protected String queueID;

      public String getPseudoCityCode() {
         return this.pseudoCityCode;
      }

      public void setPseudoCityCode(String value) {
         this.pseudoCityCode = value;
      }

      public String getQueueNumber() {
         return this.queueNumber;
      }

      public void setQueueNumber(String value) {
         this.queueNumber = value;
      }

      public String getQueueCategory() {
         return this.queueCategory;
      }

      public void setQueueCategory(String value) {
         this.queueCategory = value;
      }

      public String getSystemCode() {
         return this.systemCode;
      }

      public void setSystemCode(String value) {
         this.systemCode = value;
      }

      public String getQueueID() {
         return this.queueID;
      }

      public void setQueueID(String value) {
         this.queueID = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class RateDistance {
      @XmlAttribute(name = "Unlimited", required = true)
      protected boolean unlimited;
      @XmlAttribute(name = "Quantity")
      @XmlSchemaType(name = "nonNegativeInteger")
      protected BigInteger quantity;
      @XmlAttribute(name = "DistUnitName")
      protected DistanceUnitNameType distUnitName;
      @XmlAttribute(name = "VehiclePeriodUnitName")
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

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = {"specialEquipPref", "charge"})
   public static class SpecialEquipPrefs {
      @XmlElement(name = "SpecialEquipPref", required = true)
      protected List<VehicleReservationRQCoreType.SpecialEquipPrefs.SpecialEquipPref> specialEquipPref;
      @XmlElement(name = "Charge")
      protected VehicleChargeType charge;

      public List<VehicleReservationRQCoreType.SpecialEquipPrefs.SpecialEquipPref> getSpecialEquipPref() {
         if (this.specialEquipPref == null) {
            this.specialEquipPref = new ArrayList<>();
         }

         return this.specialEquipPref;
      }

      public VehicleChargeType getCharge() {
         return this.charge;
      }

      public void setCharge(VehicleChargeType value) {
         this.charge = value;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class SpecialEquipPref {
         @XmlAttribute(name = "Action")
         protected ActionType action;
         @XmlAttribute(name = "EquipType", required = true)
         protected String equipType;
         @XmlAttribute(name = "Quantity")
         @XmlSchemaType(name = "positiveInteger")
         protected BigInteger quantity;
         @XmlAttribute(name = "PreferLevel")
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

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class TotalCharge {
      @XmlAttribute(name = "RateTotalAmount")
      protected BigDecimal rateTotalAmount;
      @XmlAttribute(name = "EstimatedTotalAmount")
      protected BigDecimal estimatedTotalAmount;
      @XmlAttribute(name = "CurrencyCode")
      protected String currencyCode;
      @XmlAttribute(name = "DecimalPlaces")
      @XmlSchemaType(name = "nonNegativeInteger")
      protected BigInteger decimalPlaces;

      public BigDecimal getRateTotalAmount() {
         return this.rateTotalAmount;
      }

      public void setRateTotalAmount(BigDecimal value) {
         this.rateTotalAmount = value;
      }

      public BigDecimal getEstimatedTotalAmount() {
         return this.estimatedTotalAmount;
      }

      public void setEstimatedTotalAmount(BigDecimal value) {
         this.estimatedTotalAmount = value;
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
   @XmlType(name = "", propOrder = "vehicleCharge")
   public static class VehicleCharges {
      @XmlElement(name = "VehicleCharge", required = true)
      protected List<VehicleChargePurposeType> vehicleCharge;

      public List<VehicleChargePurposeType> getVehicleCharge() {
         if (this.vehicleCharge == null) {
            this.vehicleCharge = new ArrayList<>();
         }

         return this.vehicleCharge;
      }
   }
}
