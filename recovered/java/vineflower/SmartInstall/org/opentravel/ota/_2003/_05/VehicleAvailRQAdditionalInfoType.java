package org.opentravel.ota._2003._05;

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
   name = "VehicleAvailRQAdditionalInfoType",
   propOrder = {"customer", "specialReqPref", "coveragePrefs", "offLocService", "arrivalDetails", "tourInfo", "tpaExtensions"}
)
public class VehicleAvailRQAdditionalInfoType {
   @XmlElement(name = "Customer")
   protected CustomerPrimaryAdditionalType customer;
   @XmlElement(name = "SpecialReqPref")
   protected List<VehicleSpecialReqPrefType> specialReqPref;
   @XmlElement(name = "CoveragePrefs")
   protected VehicleAvailRQAdditionalInfoType.CoveragePrefs coveragePrefs;
   @XmlElement(name = "OffLocService")
   protected List<OffLocationServiceType> offLocService;
   @XmlElement(name = "ArrivalDetails")
   protected VehicleArrivalDetailsType arrivalDetails;
   @XmlElement(name = "TourInfo")
   protected VehicleTourInfoType tourInfo;
   @XmlElement(name = "TPA_Extensions")
   protected TPAExtensionsType tpaExtensions;
   @XmlAttribute(name = "LuggageQty")
   @XmlSchemaType(name = "positiveInteger")
   protected BigInteger luggageQty;
   @XmlAttribute(name = "PassengerQty")
   @XmlSchemaType(name = "positiveInteger")
   protected BigInteger passengerQty;
   @XmlAttribute(name = "GasPrePay")
   protected Boolean gasPrePay;
   @XmlAttribute(name = "SingleQuote")
   protected Boolean singleQuote;
   @XmlAttribute(name = "SmokingAllowed")
   protected Boolean smokingAllowed;

   public CustomerPrimaryAdditionalType getCustomer() {
      return this.customer;
   }

   public void setCustomer(CustomerPrimaryAdditionalType value) {
      this.customer = value;
   }

   public List<VehicleSpecialReqPrefType> getSpecialReqPref() {
      if (this.specialReqPref == null) {
         this.specialReqPref = new ArrayList<>();
      }

      return this.specialReqPref;
   }

   public VehicleAvailRQAdditionalInfoType.CoveragePrefs getCoveragePrefs() {
      return this.coveragePrefs;
   }

   public void setCoveragePrefs(VehicleAvailRQAdditionalInfoType.CoveragePrefs value) {
      this.coveragePrefs = value;
   }

   public List<OffLocationServiceType> getOffLocService() {
      if (this.offLocService == null) {
         this.offLocService = new ArrayList<>();
      }

      return this.offLocService;
   }

   public VehicleArrivalDetailsType getArrivalDetails() {
      return this.arrivalDetails;
   }

   public void setArrivalDetails(VehicleArrivalDetailsType value) {
      this.arrivalDetails = value;
   }

   public VehicleTourInfoType getTourInfo() {
      return this.tourInfo;
   }

   public void setTourInfo(VehicleTourInfoType value) {
      this.tourInfo = value;
   }

   public TPAExtensionsType getTPAExtensions() {
      return this.tpaExtensions;
   }

   public void setTPAExtensions(TPAExtensionsType value) {
      this.tpaExtensions = value;
   }

   public BigInteger getLuggageQty() {
      return this.luggageQty;
   }

   public void setLuggageQty(BigInteger value) {
      this.luggageQty = value;
   }

   public BigInteger getPassengerQty() {
      return this.passengerQty;
   }

   public void setPassengerQty(BigInteger value) {
      this.passengerQty = value;
   }

   public Boolean isGasPrePay() {
      return this.gasPrePay;
   }

   public void setGasPrePay(Boolean value) {
      this.gasPrePay = value;
   }

   public Boolean isSingleQuote() {
      return this.singleQuote;
   }

   public void setSingleQuote(Boolean value) {
      this.singleQuote = value;
   }

   public Boolean isSmokingAllowed() {
      return this.smokingAllowed;
   }

   public void setSmokingAllowed(Boolean value) {
      this.smokingAllowed = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "coveragePref")
   public static class CoveragePrefs {
      @XmlElement(name = "CoveragePref", required = true)
      protected List<VehicleAvailRQAdditionalInfoType.CoveragePrefs.CoveragePref> coveragePref;

      public List<VehicleAvailRQAdditionalInfoType.CoveragePrefs.CoveragePref> getCoveragePref() {
         if (this.coveragePref == null) {
            this.coveragePref = new ArrayList<>();
         }

         return this.coveragePref;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class CoveragePref {
         @XmlAttribute(name = "CoverageType", required = true)
         protected String coverageType;
         @XmlAttribute(name = "Code")
         protected String code;
         @XmlAttribute(name = "PreferLevel")
         protected PreferLevelType preferLevel;

         public String getCoverageType() {
            return this.coverageType;
         }

         public void setCoverageType(String value) {
            this.coverageType = value;
         }

         public String getCode() {
            return this.code;
         }

         public void setCode(String value) {
            this.code = value;
         }

         public PreferLevelType getPreferLevel() {
            return this.preferLevel;
         }

         public void setPreferLevel(PreferLevelType value) {
            this.preferLevel = value;
         }
      }
   }
}
