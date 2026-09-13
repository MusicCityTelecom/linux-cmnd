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
   name = "VehicleSegmentAdditionalInfoType",
   propOrder = {
         "paymentRules",
         "rentalPaymentAmount",
         "pricedCoverages",
         "pricedOffLocService",
         "vendorMessages",
         "locationDetails",
         "tourInfo",
         "specialReqPref",
         "arrivalDetails",
         "writtenConfInst",
         "remark",
         "tpaExtensions"
   }
)
public class VehicleSegmentAdditionalInfoType {
   @XmlElement(name = "PaymentRules")
   protected VehicleSegmentAdditionalInfoType.PaymentRules paymentRules;
   @XmlElement(name = "RentalPaymentAmount")
   protected List<PaymentDetailType> rentalPaymentAmount;
   @XmlElement(name = "PricedCoverages")
   protected VehicleSegmentAdditionalInfoType.PricedCoverages pricedCoverages;
   @XmlElement(name = "PricedOffLocService")
   protected List<OffLocationServicePricedType> pricedOffLocService;
   @XmlElement(name = "VendorMessages")
   protected VehicleSegmentAdditionalInfoType.VendorMessages vendorMessages;
   @XmlElement(name = "LocationDetails")
   protected List<VehicleLocationDetailsType> locationDetails;
   @XmlElement(name = "TourInfo")
   protected VehicleTourInfoType tourInfo;
   @XmlElement(name = "SpecialReqPref")
   protected List<VehicleSpecialReqPrefType> specialReqPref;
   @XmlElement(name = "ArrivalDetails")
   protected VehicleArrivalDetailsType arrivalDetails;
   @XmlElement(name = "WrittenConfInst")
   protected WrittenConfInstType writtenConfInst;
   @XmlElement(name = "Remark")
   protected List<ParagraphType> remark;
   @XmlElement(name = "TPA_Extensions")
   protected TPAExtensionsType tpaExtensions;
   @XmlAttribute(name = "WrittenConfInd")
   protected Boolean writtenConfInd;

   public VehicleSegmentAdditionalInfoType.PaymentRules getPaymentRules() {
      return this.paymentRules;
   }

   public void setPaymentRules(VehicleSegmentAdditionalInfoType.PaymentRules value) {
      this.paymentRules = value;
   }

   public List<PaymentDetailType> getRentalPaymentAmount() {
      if (this.rentalPaymentAmount == null) {
         this.rentalPaymentAmount = new ArrayList<>();
      }

      return this.rentalPaymentAmount;
   }

   public VehicleSegmentAdditionalInfoType.PricedCoverages getPricedCoverages() {
      return this.pricedCoverages;
   }

   public void setPricedCoverages(VehicleSegmentAdditionalInfoType.PricedCoverages value) {
      this.pricedCoverages = value;
   }

   public List<OffLocationServicePricedType> getPricedOffLocService() {
      if (this.pricedOffLocService == null) {
         this.pricedOffLocService = new ArrayList<>();
      }

      return this.pricedOffLocService;
   }

   public VehicleSegmentAdditionalInfoType.VendorMessages getVendorMessages() {
      return this.vendorMessages;
   }

   public void setVendorMessages(VehicleSegmentAdditionalInfoType.VendorMessages value) {
      this.vendorMessages = value;
   }

   public List<VehicleLocationDetailsType> getLocationDetails() {
      if (this.locationDetails == null) {
         this.locationDetails = new ArrayList<>();
      }

      return this.locationDetails;
   }

   public VehicleTourInfoType getTourInfo() {
      return this.tourInfo;
   }

   public void setTourInfo(VehicleTourInfoType value) {
      this.tourInfo = value;
   }

   public List<VehicleSpecialReqPrefType> getSpecialReqPref() {
      if (this.specialReqPref == null) {
         this.specialReqPref = new ArrayList<>();
      }

      return this.specialReqPref;
   }

   public VehicleArrivalDetailsType getArrivalDetails() {
      return this.arrivalDetails;
   }

   public void setArrivalDetails(VehicleArrivalDetailsType value) {
      this.arrivalDetails = value;
   }

   public WrittenConfInstType getWrittenConfInst() {
      return this.writtenConfInst;
   }

   public void setWrittenConfInst(WrittenConfInstType value) {
      this.writtenConfInst = value;
   }

   public List<ParagraphType> getRemark() {
      if (this.remark == null) {
         this.remark = new ArrayList<>();
      }

      return this.remark;
   }

   public TPAExtensionsType getTPAExtensions() {
      return this.tpaExtensions;
   }

   public void setTPAExtensions(TPAExtensionsType value) {
      this.tpaExtensions = value;
   }

   public Boolean isWrittenConfInd() {
      return this.writtenConfInd;
   }

   public void setWrittenConfInd(Boolean value) {
      this.writtenConfInd = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "paymentRule")
   public static class PaymentRules {
      @XmlElement(name = "PaymentRule", required = true)
      protected List<MonetaryRuleType> paymentRule;

      public List<MonetaryRuleType> getPaymentRule() {
         if (this.paymentRule == null) {
            this.paymentRule = new ArrayList<>();
         }

         return this.paymentRule;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "pricedCoverage")
   public static class PricedCoverages {
      @XmlElement(name = "PricedCoverage", required = true)
      protected List<CoveragePricedType> pricedCoverage;

      public List<CoveragePricedType> getPricedCoverage() {
         if (this.pricedCoverage == null) {
            this.pricedCoverage = new ArrayList<>();
         }

         return this.pricedCoverage;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "vendorMessage")
   public static class VendorMessages {
      @XmlElement(name = "VendorMessage", required = true)
      protected List<FormattedTextType> vendorMessage;

      public List<FormattedTextType> getVendorMessage() {
         if (this.vendorMessage == null) {
            this.vendorMessage = new ArrayList<>();
         }

         return this.vendorMessage;
      }
   }
}
