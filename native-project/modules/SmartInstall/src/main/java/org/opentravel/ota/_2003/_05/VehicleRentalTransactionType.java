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
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "VehicleRentalTransactionType",
   propOrder = {"pickUpReturnDetails", "vehicle", "rentalRate", "pricedEquips", "fees", "totalCharge", "confID", "contractID"}
)
public class VehicleRentalTransactionType {
   @XmlElement(name = "PickUpReturnDetails")
   protected List<VehicleRentalTransactionType.PickUpReturnDetails> pickUpReturnDetails;
   @XmlElement(name = "Vehicle", required = true)
   protected VehicleRentalTransactionType.Vehicle vehicle;
   @XmlElement(name = "RentalRate")
   protected VehicleRentalRateType rentalRate;
   @XmlElement(name = "PricedEquips")
   protected VehicleRentalTransactionType.PricedEquips pricedEquips;
   @XmlElement(name = "Fees")
   protected VehicleRentalTransactionType.Fees fees;
   @XmlElement(name = "TotalCharge")
   protected VehicleRentalTransactionType.TotalCharge totalCharge;
   @XmlElement(name = "ConfID")
   protected UniqueIDType confID;
   @XmlElement(name = "ContractID")
   protected UniqueIDType contractID;

   public List<VehicleRentalTransactionType.PickUpReturnDetails> getPickUpReturnDetails() {
      if (this.pickUpReturnDetails == null) {
         this.pickUpReturnDetails = new ArrayList<>();
      }

      return this.pickUpReturnDetails;
   }

   public VehicleRentalTransactionType.Vehicle getVehicle() {
      return this.vehicle;
   }

   public void setVehicle(VehicleRentalTransactionType.Vehicle value) {
      this.vehicle = value;
   }

   public VehicleRentalRateType getRentalRate() {
      return this.rentalRate;
   }

   public void setRentalRate(VehicleRentalRateType value) {
      this.rentalRate = value;
   }

   public VehicleRentalTransactionType.PricedEquips getPricedEquips() {
      return this.pricedEquips;
   }

   public void setPricedEquips(VehicleRentalTransactionType.PricedEquips value) {
      this.pricedEquips = value;
   }

   public VehicleRentalTransactionType.Fees getFees() {
      return this.fees;
   }

   public void setFees(VehicleRentalTransactionType.Fees value) {
      this.fees = value;
   }

   public VehicleRentalTransactionType.TotalCharge getTotalCharge() {
      return this.totalCharge;
   }

   public void setTotalCharge(VehicleRentalTransactionType.TotalCharge value) {
      this.totalCharge = value;
   }

   public UniqueIDType getConfID() {
      return this.confID;
   }

   public void setConfID(UniqueIDType value) {
      this.confID = value;
   }

   public UniqueIDType getContractID() {
      return this.contractID;
   }

   public void setContractID(UniqueIDType value) {
      this.contractID = value;
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
   public static class PickUpReturnDetails extends VehicleRentalCoreType {
      @XmlAttribute(name = "ExpectedActualCode")
      @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
      protected String expectedActualCode;

      public String getExpectedActualCode() {
         return this.expectedActualCode;
      }

      public void setExpectedActualCode(String value) {
         this.expectedActualCode = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "pricedEquip")
   public static class PricedEquips {
      @XmlElement(name = "PricedEquip", required = true)
      protected List<VehicleRentalTransactionType.PricedEquips.PricedEquip> pricedEquip;

      public List<VehicleRentalTransactionType.PricedEquips.PricedEquip> getPricedEquip() {
         if (this.pricedEquip == null) {
            this.pricedEquip = new ArrayList<>();
         }

         return this.pricedEquip;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = {"equipment", "charge"})
      public static class PricedEquip {
         @XmlElement(name = "Equipment", required = true)
         protected List<VehicleRentalTransactionType.PricedEquips.PricedEquip.Equipment> equipment;
         @XmlElement(name = "Charge")
         protected VehicleChargeType charge;

         public List<VehicleRentalTransactionType.PricedEquips.PricedEquip.Equipment> getEquipment() {
            if (this.equipment == null) {
               this.equipment = new ArrayList<>();
            }

            return this.equipment;
         }

         public VehicleChargeType getCharge() {
            return this.charge;
         }

         public void setCharge(VehicleChargeType value) {
            this.charge = value;
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "")
         public static class Equipment extends VehicleEquipmentType {
            @XmlAttribute(name = "CheckOutCheckInCode")
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            protected String checkOutCheckInCode;

            public String getCheckOutCheckInCode() {
               return this.checkOutCheckInCode;
            }

            public void setCheckOutCheckInCode(String value) {
               this.checkOutCheckInCode = value;
            }
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
   @XmlType(name = "", propOrder = "vehRentalDetails")
   public static class Vehicle extends VehicleType {
      @XmlElement(name = "VehRentalDetails")
      protected List<VehicleRentalDetailsType> vehRentalDetails;

      public List<VehicleRentalDetailsType> getVehRentalDetails() {
         if (this.vehRentalDetails == null) {
            this.vehRentalDetails = new ArrayList<>();
         }

         return this.vehRentalDetails;
      }
   }
}
