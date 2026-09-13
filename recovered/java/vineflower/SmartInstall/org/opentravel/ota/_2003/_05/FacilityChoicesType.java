package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FacilityChoicesType", propOrder = {"availableRooms", "availableMealPlans", "roomPrices"})
public class FacilityChoicesType {
   @XmlElement(name = "AvailableRooms")
   protected FacilityChoicesType.AvailableRooms availableRooms;
   @XmlElement(name = "AvailableMealPlans")
   protected FacilityChoicesType.AvailableMealPlans availableMealPlans;
   @XmlElement(name = "RoomPrices")
   protected List<FacilityChoicesType.RoomPrices> roomPrices;
   @XmlAttribute(name = "MinOccupancy")
   protected Integer minOccupancy;
   @XmlAttribute(name = "MaxOccupancy")
   protected Integer maxOccupancy;

   public FacilityChoicesType.AvailableRooms getAvailableRooms() {
      return this.availableRooms;
   }

   public void setAvailableRooms(FacilityChoicesType.AvailableRooms value) {
      this.availableRooms = value;
   }

   public FacilityChoicesType.AvailableMealPlans getAvailableMealPlans() {
      return this.availableMealPlans;
   }

   public void setAvailableMealPlans(FacilityChoicesType.AvailableMealPlans value) {
      this.availableMealPlans = value;
   }

   public List<FacilityChoicesType.RoomPrices> getRoomPrices() {
      if (this.roomPrices == null) {
         this.roomPrices = new ArrayList<>();
      }

      return this.roomPrices;
   }

   public Integer getMinOccupancy() {
      return this.minOccupancy;
   }

   public void setMinOccupancy(Integer value) {
      this.minOccupancy = value;
   }

   public Integer getMaxOccupancy() {
      return this.maxOccupancy;
   }

   public void setMaxOccupancy(Integer value) {
      this.maxOccupancy = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "mealPlan")
   public static class AvailableMealPlans {
      @XmlElement(name = "MealPlan", required = true)
      protected List<MealPlanType> mealPlan;

      public List<MealPlanType> getMealPlan() {
         if (this.mealPlan == null) {
            this.mealPlan = new ArrayList<>();
         }

         return this.mealPlan;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "room")
   public static class AvailableRooms {
      @XmlElement(name = "Room", required = true)
      protected List<PkgRoomInventoryType> room;

      public List<PkgRoomInventoryType> getRoom() {
         if (this.room == null) {
            this.room = new ArrayList<>();
         }

         return this.room;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "roomPrice")
   public static class RoomPrices {
      @XmlElement(name = "RoomPrice", required = true)
      protected List<RoomPriceType> roomPrice;
      @XmlAttribute(name = "MealPlan")
      protected String mealPlan;

      public List<RoomPriceType> getRoomPrice() {
         if (this.roomPrice == null) {
            this.roomPrice = new ArrayList<>();
         }

         return this.roomPrice;
      }

      public String getMealPlan() {
         return this.mealPlan;
      }

      public void setMealPlan(String value) {
         this.mealPlan = value;
      }
   }
}
