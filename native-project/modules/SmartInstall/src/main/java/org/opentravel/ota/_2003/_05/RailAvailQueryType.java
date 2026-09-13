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
@XmlType(name = "RailAvailQueryType", propOrder = {"availBaseQueryCriteria", "passengerType", "returnInfo", "railPrefs"})
public class RailAvailQueryType {
   @XmlElement(name = "AvailBaseQueryCriteria", required = true)
   protected List<RailAvailScheduleQueryType> availBaseQueryCriteria;
   @XmlElement(name = "PassengerType")
   protected List<RailAvailQueryType.PassengerType> passengerType;
   @XmlElement(name = "ReturnInfo")
   protected TravelDateTimeType returnInfo;
   @XmlElement(name = "RailPrefs")
   protected RailAvailPrefsType railPrefs;

   public List<RailAvailScheduleQueryType> getAvailBaseQueryCriteria() {
      if (this.availBaseQueryCriteria == null) {
         this.availBaseQueryCriteria = new ArrayList<>();
      }

      return this.availBaseQueryCriteria;
   }

   public List<RailAvailQueryType.PassengerType> getPassengerType() {
      if (this.passengerType == null) {
         this.passengerType = new ArrayList<>();
      }

      return this.passengerType;
   }

   public TravelDateTimeType getReturnInfo() {
      return this.returnInfo;
   }

   public void setReturnInfo(TravelDateTimeType value) {
      this.returnInfo = value;
   }

   public RailAvailPrefsType getRailPrefs() {
      return this.railPrefs;
   }

   public void setRailPrefs(RailAvailPrefsType value) {
      this.railPrefs = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class PassengerType extends RailPassengerCategoryType {
      @XmlAttribute(name = "Quantity")
      @XmlSchemaType(name = "positiveInteger")
      protected BigInteger quantity;

      public BigInteger getQuantity() {
         return this.quantity;
      }

      public void setQuantity(BigInteger value) {
         this.quantity = value;
      }
   }
}
