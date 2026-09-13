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
   name = "VehicleReservationRQAdditionalInfoType",
   propOrder = {
         "specialReqPref",
         "coveragePrefs",
         "offLocService",
         "arrivalDetails",
         "rentalPaymentPref",
         "reference",
         "tourInfo",
         "writtenConfInst",
         "remark",
         "tpaExtensions"
   }
)
public class VehicleReservationRQAdditionalInfoType {
   @XmlElement(name = "SpecialReqPref")
   protected List<VehicleSpecialReqPrefType> specialReqPref;
   @XmlElement(name = "CoveragePrefs")
   protected VehicleReservationRQAdditionalInfoType.CoveragePrefs coveragePrefs;
   @XmlElement(name = "OffLocService")
   protected List<OffLocationServiceType> offLocService;
   @XmlElement(name = "ArrivalDetails")
   protected VehicleArrivalDetailsType arrivalDetails;
   @XmlElement(name = "RentalPaymentPref")
   protected List<VehicleReservationRQAdditionalInfoType.RentalPaymentPref> rentalPaymentPref;
   @XmlElement(name = "Reference")
   protected VehicleReservationRQAdditionalInfoType.Reference reference;
   @XmlElement(name = "TourInfo")
   protected VehicleTourInfoType tourInfo;
   @XmlElement(name = "WrittenConfInst")
   protected WrittenConfInstType writtenConfInst;
   @XmlElement(name = "Remark")
   protected List<ParagraphType> remark;
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
   @XmlAttribute(name = "SmokingAllowed")
   protected Boolean smokingAllowed;

   public List<VehicleSpecialReqPrefType> getSpecialReqPref() {
      if (this.specialReqPref == null) {
         this.specialReqPref = new ArrayList<>();
      }

      return this.specialReqPref;
   }

   public VehicleReservationRQAdditionalInfoType.CoveragePrefs getCoveragePrefs() {
      return this.coveragePrefs;
   }

   public void setCoveragePrefs(VehicleReservationRQAdditionalInfoType.CoveragePrefs value) {
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

   public List<VehicleReservationRQAdditionalInfoType.RentalPaymentPref> getRentalPaymentPref() {
      if (this.rentalPaymentPref == null) {
         this.rentalPaymentPref = new ArrayList<>();
      }

      return this.rentalPaymentPref;
   }

   public VehicleReservationRQAdditionalInfoType.Reference getReference() {
      return this.reference;
   }

   public void setReference(VehicleReservationRQAdditionalInfoType.Reference value) {
      this.reference = value;
   }

   public VehicleTourInfoType getTourInfo() {
      return this.tourInfo;
   }

   public void setTourInfo(VehicleTourInfoType value) {
      this.tourInfo = value;
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
      protected List<VehicleReservationRQAdditionalInfoType.CoveragePrefs.CoveragePref> coveragePref;

      public List<VehicleReservationRQAdditionalInfoType.CoveragePrefs.CoveragePref> getCoveragePref() {
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

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class Reference extends UniqueIDType {
      @XmlAttribute(name = "DateTime")
      @XmlSchemaType(name = "dateTime")
      protected XMLGregorianCalendar dateTime;
      @XmlAttribute(name = "Amount")
      protected BigDecimal amount;
      @XmlAttribute(name = "CurrencyCode")
      protected String currencyCode;
      @XmlAttribute(name = "DecimalPlaces")
      @XmlSchemaType(name = "nonNegativeInteger")
      protected BigInteger decimalPlaces;

      public XMLGregorianCalendar getDateTime() {
         return this.dateTime;
      }

      public void setDateTime(XMLGregorianCalendar value) {
         this.dateTime = value;
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
   public static class RentalPaymentPref extends PaymentDetailType {
      @XmlAttribute(name = "Type")
      @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
      protected String type;

      public String getType() {
         return this.type;
      }

      public void setType(String value) {
         this.type = value;
      }
   }
}
