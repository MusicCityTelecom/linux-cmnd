package org.opentravel.ota._2003._05;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VehicleRentalDetailsType", propOrder = {"fuelLevelDetails", "odometerReading", "conditionReport"})
public class VehicleRentalDetailsType {
   @XmlElement(name = "FuelLevelDetails")
   protected VehicleRentalDetailsType.FuelLevelDetails fuelLevelDetails;
   @XmlElement(name = "OdometerReading")
   protected VehicleRentalDetailsType.OdometerReading odometerReading;
   @XmlElement(name = "ConditionReport")
   protected List<VehicleRentalDetailsType.ConditionReport> conditionReport;
   @XmlAttribute(name = "ParkingLocation")
   protected String parkingLocation;

   public VehicleRentalDetailsType.FuelLevelDetails getFuelLevelDetails() {
      return this.fuelLevelDetails;
   }

   public void setFuelLevelDetails(VehicleRentalDetailsType.FuelLevelDetails value) {
      this.fuelLevelDetails = value;
   }

   public VehicleRentalDetailsType.OdometerReading getOdometerReading() {
      return this.odometerReading;
   }

   public void setOdometerReading(VehicleRentalDetailsType.OdometerReading value) {
      this.odometerReading = value;
   }

   public List<VehicleRentalDetailsType.ConditionReport> getConditionReport() {
      if (this.conditionReport == null) {
         this.conditionReport = new ArrayList<>();
      }

      return this.conditionReport;
   }

   public String getParkingLocation() {
      return this.parkingLocation;
   }

   public void setParkingLocation(String value) {
      this.parkingLocation = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class ConditionReport extends FormattedTextTextType {
      @XmlAttribute(name = "Condition")
      @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
      protected String condition;

      public String getCondition() {
         return this.condition;
      }

      public void setCondition(String value) {
         this.condition = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class FuelLevelDetails {
      @XmlAttribute(name = "FuelLevelValue")
      @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
      protected String fuelLevelValue;
      @XmlAttribute(name = "UnitOfMeasureQuantity")
      protected BigDecimal unitOfMeasureQuantity;
      @XmlAttribute(name = "UnitOfMeasure")
      protected String unitOfMeasure;
      @XmlAttribute(name = "UnitOfMeasureCode")
      protected String unitOfMeasureCode;

      public String getFuelLevelValue() {
         return this.fuelLevelValue;
      }

      public void setFuelLevelValue(String value) {
         this.fuelLevelValue = value;
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
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class OdometerReading {
      @XmlAttribute(name = "UnitOfMeasureQuantity")
      protected BigDecimal unitOfMeasureQuantity;
      @XmlAttribute(name = "UnitOfMeasure")
      protected String unitOfMeasure;
      @XmlAttribute(name = "UnitOfMeasureCode")
      protected String unitOfMeasureCode;

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
   }
}
