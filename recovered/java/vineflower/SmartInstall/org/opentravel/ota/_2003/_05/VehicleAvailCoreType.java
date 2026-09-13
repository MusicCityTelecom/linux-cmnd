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
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "VehicleAvailCoreType",
   propOrder = {
         "vehicle",
         "rentalRate",
         "totalCharge",
         "pricedEquips",
         "fees",
         "reference",
         "vendor",
         "vendorLocation",
         "dropOffLocation",
         "discount",
         "tpaExtensions"
   }
)
public class VehicleAvailCoreType {
   @XmlElement(name = "Vehicle", required = true)
   protected VehicleType vehicle;
   @XmlElement(name = "RentalRate")
   protected List<VehicleRentalRateType> rentalRate;
   @XmlElement(name = "TotalCharge")
   protected List<VehicleAvailCoreType.TotalCharge> totalCharge;
   @XmlElement(name = "PricedEquips")
   protected VehicleAvailCoreType.PricedEquips pricedEquips;
   @XmlElement(name = "Fees")
   protected VehicleAvailCoreType.Fees fees;
   @XmlElement(name = "Reference")
   protected VehicleAvailCoreType.Reference reference;
   @XmlElement(name = "Vendor")
   protected VehicleAvailCoreType.Vendor vendor;
   @XmlElement(name = "VendorLocation")
   protected VehicleAvailCoreType.VendorLocation vendorLocation;
   @XmlElement(name = "DropOffLocation")
   protected VehicleAvailCoreType.DropOffLocation dropOffLocation;
   @XmlElement(name = "Discount")
   protected List<VehicleAvailCoreType.Discount> discount;
   @XmlElement(name = "TPA_Extensions")
   protected TPAExtensionsType tpaExtensions;
   @XmlAttribute(name = "Status", required = true)
   protected InventoryStatusType status;
   @XmlAttribute(name = "IsAlternateInd")
   protected Boolean isAlternateInd;

   public VehicleType getVehicle() {
      return this.vehicle;
   }

   public void setVehicle(VehicleType value) {
      this.vehicle = value;
   }

   public List<VehicleRentalRateType> getRentalRate() {
      if (this.rentalRate == null) {
         this.rentalRate = new ArrayList<>();
      }

      return this.rentalRate;
   }

   public List<VehicleAvailCoreType.TotalCharge> getTotalCharge() {
      if (this.totalCharge == null) {
         this.totalCharge = new ArrayList<>();
      }

      return this.totalCharge;
   }

   public VehicleAvailCoreType.PricedEquips getPricedEquips() {
      return this.pricedEquips;
   }

   public void setPricedEquips(VehicleAvailCoreType.PricedEquips value) {
      this.pricedEquips = value;
   }

   public VehicleAvailCoreType.Fees getFees() {
      return this.fees;
   }

   public void setFees(VehicleAvailCoreType.Fees value) {
      this.fees = value;
   }

   public VehicleAvailCoreType.Reference getReference() {
      return this.reference;
   }

   public void setReference(VehicleAvailCoreType.Reference value) {
      this.reference = value;
   }

   public VehicleAvailCoreType.Vendor getVendor() {
      return this.vendor;
   }

   public void setVendor(VehicleAvailCoreType.Vendor value) {
      this.vendor = value;
   }

   public VehicleAvailCoreType.VendorLocation getVendorLocation() {
      return this.vendorLocation;
   }

   public void setVendorLocation(VehicleAvailCoreType.VendorLocation value) {
      this.vendorLocation = value;
   }

   public VehicleAvailCoreType.DropOffLocation getDropOffLocation() {
      return this.dropOffLocation;
   }

   public void setDropOffLocation(VehicleAvailCoreType.DropOffLocation value) {
      this.dropOffLocation = value;
   }

   public List<VehicleAvailCoreType.Discount> getDiscount() {
      if (this.discount == null) {
         this.discount = new ArrayList<>();
      }

      return this.discount;
   }

   public TPAExtensionsType getTPAExtensions() {
      return this.tpaExtensions;
   }

   public void setTPAExtensions(TPAExtensionsType value) {
      this.tpaExtensions = value;
   }

   public InventoryStatusType getStatus() {
      return this.status;
   }

   public void setStatus(InventoryStatusType value) {
      this.status = value;
   }

   public Boolean isIsAlternateInd() {
      return this.isAlternateInd;
   }

   public void setIsAlternateInd(Boolean value) {
      this.isAlternateInd = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class Discount {
      @XmlAttribute(name = "Percent")
      protected BigDecimal percent;
      @XmlAttribute(name = "ID")
      protected String id;
      @XmlAttribute(name = "Description")
      protected String description;
      @XmlAttribute(name = "Amount")
      protected BigDecimal amount;
      @XmlAttribute(name = "CurrencyCode")
      protected String currencyCode;
      @XmlAttribute(name = "DecimalPlaces")
      @XmlSchemaType(name = "nonNegativeInteger")
      protected BigInteger decimalPlaces;

      public BigDecimal getPercent() {
         return this.percent;
      }

      public void setPercent(BigDecimal value) {
         this.percent = value;
      }

      public String getID() {
         return this.id;
      }

      public void setID(String value) {
         this.id = value;
      }

      public String getDescription() {
         return this.description;
      }

      public void setDescription(String value) {
         this.description = value;
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
   @XmlType(name = "")
   public static class DropOffLocation extends LocationType {
      @XmlAttribute(name = "ExtendedLocationCode")
      protected String extendedLocationCode;
      @XmlAttribute(name = "CounterLocation")
      protected String counterLocation;
      @XmlAttribute(name = "Name")
      protected String name;

      public String getExtendedLocationCode() {
         return this.extendedLocationCode;
      }

      public void setExtendedLocationCode(String value) {
         this.extendedLocationCode = value;
      }

      public String getCounterLocation() {
         return this.counterLocation;
      }

      public void setCounterLocation(String value) {
         this.counterLocation = value;
      }

      public String getName() {
         return this.name;
      }

      public void setName(String value) {
         this.name = value;
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
   public static class Reference extends UniqueIDType {
      @XmlAttribute(name = "DateTime")
      @XmlSchemaType(name = "dateTime")
      protected XMLGregorianCalendar dateTime;

      public XMLGregorianCalendar getDateTime() {
         return this.dateTime;
      }

      public void setDateTime(XMLGregorianCalendar value) {
         this.dateTime = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class TotalCharge {
      @XmlAttribute(name = "RateConvertInd")
      protected Boolean rateConvertInd;
      @XmlAttribute(name = "RateTotalAmount")
      protected BigDecimal rateTotalAmount;
      @XmlAttribute(name = "EstimatedTotalAmount")
      protected BigDecimal estimatedTotalAmount;
      @XmlAttribute(name = "CurrencyCode")
      protected String currencyCode;
      @XmlAttribute(name = "DecimalPlaces")
      @XmlSchemaType(name = "nonNegativeInteger")
      protected BigInteger decimalPlaces;

      public Boolean isRateConvertInd() {
         return this.rateConvertInd;
      }

      public void setRateConvertInd(Boolean value) {
         this.rateConvertInd = value;
      }

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
   @XmlType(name = "")
   public static class Vendor extends CompanyNameType {
      @XmlAttribute(name = "ParticipationLevelCode")
      protected String participationLevelCode;

      public String getParticipationLevelCode() {
         return this.participationLevelCode;
      }

      public void setParticipationLevelCode(String value) {
         this.participationLevelCode = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class VendorLocation extends LocationType {
      @XmlAttribute(name = "ExtendedLocationCode")
      protected String extendedLocationCode;
      @XmlAttribute(name = "CounterLocation")
      protected String counterLocation;
      @XmlAttribute(name = "Name")
      protected String name;
      @XmlAttribute(name = "CounterLocInfo")
      @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
      protected String counterLocInfo;

      public String getExtendedLocationCode() {
         return this.extendedLocationCode;
      }

      public void setExtendedLocationCode(String value) {
         this.extendedLocationCode = value;
      }

      public String getCounterLocation() {
         return this.counterLocation;
      }

      public void setCounterLocation(String value) {
         this.counterLocation = value;
      }

      public String getName() {
         return this.name;
      }

      public void setName(String value) {
         this.name = value;
      }

      public String getCounterLocInfo() {
         return this.counterLocInfo;
      }

      public void setCounterLocInfo(String value) {
         this.counterLocInfo = value;
      }
   }
}
