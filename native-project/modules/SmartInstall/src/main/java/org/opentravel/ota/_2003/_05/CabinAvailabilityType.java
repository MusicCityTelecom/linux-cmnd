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
@XmlType(name = "CabinAvailabilityType", propOrder = {"meal", "baggageAllowance", "entertainment", "flightLoadInfo"})
@XmlSeeAlso(MarketingCabinType.class)
public class CabinAvailabilityType {
   @XmlElement(name = "Meal")
   protected List<CabinAvailabilityType.Meal> meal;
   @XmlElement(name = "BaggageAllowance")
   protected CabinAvailabilityType.BaggageAllowance baggageAllowance;
   @XmlElement(name = "Entertainment")
   protected List<CabinAvailabilityType.Entertainment> entertainment;
   @XmlElement(name = "FlightLoadInfo")
   protected CabinAvailabilityType.FlightLoadInfo flightLoadInfo;
   @XmlAttribute(name = "CabinType", required = true)
   protected CabinType cabinType;
   @XmlAttribute(name = "CabinCapacity")
   @XmlSchemaType(name = "positiveInteger")
   protected BigInteger cabinCapacity;

   public List<CabinAvailabilityType.Meal> getMeal() {
      if (this.meal == null) {
         this.meal = new ArrayList<>();
      }

      return this.meal;
   }

   public CabinAvailabilityType.BaggageAllowance getBaggageAllowance() {
      return this.baggageAllowance;
   }

   public void setBaggageAllowance(CabinAvailabilityType.BaggageAllowance value) {
      this.baggageAllowance = value;
   }

   public List<CabinAvailabilityType.Entertainment> getEntertainment() {
      if (this.entertainment == null) {
         this.entertainment = new ArrayList<>();
      }

      return this.entertainment;
   }

   public CabinAvailabilityType.FlightLoadInfo getFlightLoadInfo() {
      return this.flightLoadInfo;
   }

   public void setFlightLoadInfo(CabinAvailabilityType.FlightLoadInfo value) {
      this.flightLoadInfo = value;
   }

   public CabinType getCabinType() {
      return this.cabinType;
   }

   public void setCabinType(CabinType value) {
      this.cabinType = value;
   }

   public BigInteger getCabinCapacity() {
      return this.cabinCapacity;
   }

   public void setCabinCapacity(BigInteger value) {
      this.cabinCapacity = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class BaggageAllowance {
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

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class Entertainment {
      @XmlAttribute(name = "Code")
      protected String code;

      public String getCode() {
         return this.code;
      }

      public void setCode(String value) {
         this.code = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class FlightLoadInfo {
      @XmlAttribute(name = "AuthorizedSeatQty")
      @XmlSchemaType(name = "positiveInteger")
      protected BigInteger authorizedSeatQty;
      @XmlAttribute(name = "NRSA_StandbyPaxQty")
      @XmlSchemaType(name = "positiveInteger")
      protected BigInteger nrsaStandbyPaxQty;
      @XmlAttribute(name = "RevenuePaxQty")
      @XmlSchemaType(name = "positiveInteger")
      protected BigInteger revenuePaxQty;

      public BigInteger getAuthorizedSeatQty() {
         return this.authorizedSeatQty;
      }

      public void setAuthorizedSeatQty(BigInteger value) {
         this.authorizedSeatQty = value;
      }

      public BigInteger getNRSAStandbyPaxQty() {
         return this.nrsaStandbyPaxQty;
      }

      public void setNRSAStandbyPaxQty(BigInteger value) {
         this.nrsaStandbyPaxQty = value;
      }

      public BigInteger getRevenuePaxQty() {
         return this.revenuePaxQty;
      }

      public void setRevenuePaxQty(BigInteger value) {
         this.revenuePaxQty = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class Meal {
      @XmlAttribute(name = "MealCode", required = true)
      protected String mealCode;

      public String getMealCode() {
         return this.mealCode;
      }

      public void setMealCode(String value) {
         this.mealCode = value;
      }
   }
}
