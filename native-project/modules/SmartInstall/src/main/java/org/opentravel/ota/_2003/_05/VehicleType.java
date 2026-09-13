package org.opentravel.ota._2003._05;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VehicleType", propOrder = {"vehMakeModel", "pictureURL", "vehIdentity"})
@XmlSeeAlso(
   {
         VehicleLocationLiabilitiesType.Coverages.Coverage.CoverageFees.CoverageFee.Vehicles.Vehicle.class,
         VehicleLocationVehiclesType.Vehicle.class,
         VehicleRentalTransactionType.Vehicle.class
   }
)
public class VehicleType extends VehicleCoreType {
   @XmlElement(name = "VehMakeModel")
   protected VehicleType.VehMakeModel vehMakeModel;
   @XmlElement(name = "PictureURL")
   @XmlSchemaType(name = "anyURI")
   protected String pictureURL;
   @XmlElement(name = "VehIdentity")
   protected VehicleType.VehIdentity vehIdentity;
   @XmlAttribute(name = "PassengerQuantity")
   protected String passengerQuantity;
   @XmlAttribute(name = "BaggageQuantity")
   protected BigInteger baggageQuantity;
   @XmlAttribute(name = "VendorCarType")
   protected String vendorCarType;
   @XmlAttribute(name = "OdometerUnitOfMeasure")
   protected DistanceUnitNameType odometerUnitOfMeasure;
   @XmlAttribute(name = "Description")
   protected String description;
   @XmlAttribute(name = "Start")
   protected String start;
   @XmlAttribute(name = "Duration")
   protected String duration;
   @XmlAttribute(name = "End")
   protected String end;
   @XmlAttribute(name = "UnitOfMeasureQuantity")
   protected BigDecimal unitOfMeasureQuantity;
   @XmlAttribute(name = "UnitOfMeasure")
   protected String unitOfMeasure;
   @XmlAttribute(name = "UnitOfMeasureCode")
   protected String unitOfMeasureCode;
   @XmlAttribute(name = "Code")
   protected String code;
   @XmlAttribute(name = "CodeContext")
   protected String codeContext;

   public VehicleType.VehMakeModel getVehMakeModel() {
      return this.vehMakeModel;
   }

   public void setVehMakeModel(VehicleType.VehMakeModel value) {
      this.vehMakeModel = value;
   }

   public String getPictureURL() {
      return this.pictureURL;
   }

   public void setPictureURL(String value) {
      this.pictureURL = value;
   }

   public VehicleType.VehIdentity getVehIdentity() {
      return this.vehIdentity;
   }

   public void setVehIdentity(VehicleType.VehIdentity value) {
      this.vehIdentity = value;
   }

   public String getPassengerQuantity() {
      return this.passengerQuantity;
   }

   public void setPassengerQuantity(String value) {
      this.passengerQuantity = value;
   }

   public BigInteger getBaggageQuantity() {
      return this.baggageQuantity;
   }

   public void setBaggageQuantity(BigInteger value) {
      this.baggageQuantity = value;
   }

   public String getVendorCarType() {
      return this.vendorCarType;
   }

   public void setVendorCarType(String value) {
      this.vendorCarType = value;
   }

   public DistanceUnitNameType getOdometerUnitOfMeasure() {
      return this.odometerUnitOfMeasure;
   }

   public void setOdometerUnitOfMeasure(DistanceUnitNameType value) {
      this.odometerUnitOfMeasure = value;
   }

   public String getDescription() {
      return this.description;
   }

   public void setDescription(String value) {
      this.description = value;
   }

   public String getStart() {
      return this.start;
   }

   public void setStart(String value) {
      this.start = value;
   }

   public String getDuration() {
      return this.duration;
   }

   public void setDuration(String value) {
      this.duration = value;
   }

   public String getEnd() {
      return this.end;
   }

   public void setEnd(String value) {
      this.end = value;
   }

   public BigDecimal getUnitOfMeasureQuantity() {
      return this.unitOfMeasureQuantity;
   }

   public void setUnitOfMeasureQuantity(BigDecimal value) {
      this.unitOfMeasureQuantity = value;
   }

   public String getUnitOfMeasure() {
      return this.unitOfMeasure;
   }

   public void setUnitOfMeasure(String value) {
      this.unitOfMeasure = value;
   }

   public String getUnitOfMeasureCode() {
      return this.unitOfMeasureCode;
   }

   public void setUnitOfMeasureCode(String value) {
      this.unitOfMeasureCode = value;
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

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class VehIdentity {
      @XmlAttribute(name = "VehicleAssetNumber")
      protected String vehicleAssetNumber;
      @XmlAttribute(name = "LicensePlateNumber")
      protected String licensePlateNumber;
      @XmlAttribute(name = "StateProvCode")
      protected String stateProvCode;
      @XmlAttribute(name = "CountryCode")
      protected String countryCode;
      @XmlAttribute(name = "VehicleID_Number")
      protected String vehicleIDNumber;
      @XmlAttribute(name = "VehicleColor")
      protected String vehicleColor;

      public String getVehicleAssetNumber() {
         return this.vehicleAssetNumber;
      }

      public void setVehicleAssetNumber(String value) {
         this.vehicleAssetNumber = value;
      }

      public String getLicensePlateNumber() {
         return this.licensePlateNumber;
      }

      public void setLicensePlateNumber(String value) {
         this.licensePlateNumber = value;
      }

      public String getStateProvCode() {
         return this.stateProvCode;
      }

      public void setStateProvCode(String value) {
         this.stateProvCode = value;
      }

      public String getCountryCode() {
         return this.countryCode;
      }

      public void setCountryCode(String value) {
         this.countryCode = value;
      }

      public String getVehicleIDNumber() {
         return this.vehicleIDNumber;
      }

      public void setVehicleIDNumber(String value) {
         this.vehicleIDNumber = value;
      }

      public String getVehicleColor() {
         return this.vehicleColor;
      }

      public void setVehicleColor(String value) {
         this.vehicleColor = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class VehMakeModel {
      @XmlAttribute(name = "ModelYear")
      @XmlSchemaType(name = "gYear")
      protected XMLGregorianCalendar modelYear;
      @XmlAttribute(name = "Name", required = true)
      protected String name;
      @XmlAttribute(name = "Code")
      protected String code;

      public XMLGregorianCalendar getModelYear() {
         return this.modelYear;
      }

      public void setModelYear(XMLGregorianCalendar value) {
         this.modelYear = value;
      }

      public String getName() {
         return this.name;
      }

      public void setName(String value) {
         this.name = value;
      }

      public String getCode() {
         return this.code;
      }

      public void setCode(String value) {
         this.code = value;
      }
   }
}
