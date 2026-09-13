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
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "VehicleSegmentCoreType",
   propOrder = {"confID", "vendor", "vehRentalCore", "vehicle", "rentalRate", "pricedEquips", "fees", "totalCharge", "tpaExtensions"}
)
@XmlSeeAlso(VehicleReservationType.VehSegmentCore.class)
public class VehicleSegmentCoreType {
   @XmlElement(name = "ConfID", required = true)
   protected List<VehicleSegmentCoreType.ConfID> confID;
   @XmlElement(name = "Vendor")
   protected CompanyNameType vendor;
   @XmlElement(name = "VehRentalCore")
   protected VehicleRentalCoreType vehRentalCore;
   @XmlElement(name = "Vehicle")
   protected VehicleType vehicle;
   @XmlElement(name = "RentalRate")
   protected VehicleRentalRateType rentalRate;
   @XmlElement(name = "PricedEquips")
   protected VehicleSegmentCoreType.PricedEquips pricedEquips;
   @XmlElement(name = "Fees")
   protected VehicleSegmentCoreType.Fees fees;
   @XmlElement(name = "TotalCharge")
   protected VehicleSegmentCoreType.TotalCharge totalCharge;
   @XmlElement(name = "TPA_Extensions")
   protected TPAExtensionsType tpaExtensions;
   @XmlAttribute(name = "IndexNumber")
   protected Integer indexNumber;

   public List<VehicleSegmentCoreType.ConfID> getConfID() {
      if (this.confID == null) {
         this.confID = new ArrayList<>();
      }

      return this.confID;
   }

   public CompanyNameType getVendor() {
      return this.vendor;
   }

   public void setVendor(CompanyNameType value) {
      this.vendor = value;
   }

   public VehicleRentalCoreType getVehRentalCore() {
      return this.vehRentalCore;
   }

   public void setVehRentalCore(VehicleRentalCoreType value) {
      this.vehRentalCore = value;
   }

   public VehicleType getVehicle() {
      return this.vehicle;
   }

   public void setVehicle(VehicleType value) {
      this.vehicle = value;
   }

   public VehicleRentalRateType getRentalRate() {
      return this.rentalRate;
   }

   public void setRentalRate(VehicleRentalRateType value) {
      this.rentalRate = value;
   }

   public VehicleSegmentCoreType.PricedEquips getPricedEquips() {
      return this.pricedEquips;
   }

   public void setPricedEquips(VehicleSegmentCoreType.PricedEquips value) {
      this.pricedEquips = value;
   }

   public VehicleSegmentCoreType.Fees getFees() {
      return this.fees;
   }

   public void setFees(VehicleSegmentCoreType.Fees value) {
      this.fees = value;
   }

   public VehicleSegmentCoreType.TotalCharge getTotalCharge() {
      return this.totalCharge;
   }

   public void setTotalCharge(VehicleSegmentCoreType.TotalCharge value) {
      this.totalCharge = value;
   }

   public TPAExtensionsType getTPAExtensions() {
      return this.tpaExtensions;
   }

   public void setTPAExtensions(TPAExtensionsType value) {
      this.tpaExtensions = value;
   }

   public Integer getIndexNumber() {
      return this.indexNumber;
   }

   public void setIndexNumber(Integer value) {
      this.indexNumber = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class ConfID extends UniqueIDType {
      @XmlAttribute(name = "Status")
      protected String status;

      public String getStatus() {
         return this.status;
      }

      public void setStatus(String value) {
         this.status = value;
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
   @XmlType(name = "", propOrder = "pricedEquip")
   public static class PricedEquips {
      @XmlElement(name = "PricedEquip", required = true)
      protected List<VehicleEquipmentPricedType> pricedEquip;

      public List<VehicleEquipmentPricedType> getPricedEquip() {
         if (this.pricedEquip == null) {
            this.pricedEquip = new ArrayList<>();
         }

         return this.pricedEquip;
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
}
