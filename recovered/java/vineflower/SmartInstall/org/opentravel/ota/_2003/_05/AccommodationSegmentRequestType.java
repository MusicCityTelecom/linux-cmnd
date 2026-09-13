package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AccommodationSegmentRequestType", propOrder = {"identity", "dateRange", "roomProfiles", "mealPlans"})
public class AccommodationSegmentRequestType {
   @XmlElement(name = "Identity")
   protected PropertyIdentityType identity;
   @XmlElement(name = "DateRange", required = true)
   protected DateTimeSpanType dateRange;
   @XmlElement(name = "RoomProfiles")
   protected AccommodationSegmentRequestType.RoomProfiles roomProfiles;
   @XmlElement(name = "MealPlans")
   protected AccommodationSegmentRequestType.MealPlans mealPlans;
   @XmlAttribute(name = "RPH")
   protected String rph;
   @XmlAttribute(name = "ResortCode")
   protected String resortCode;
   @XmlAttribute(name = "ResortName")
   protected String resortName;
   @XmlAttribute(name = "DestinationCode")
   protected String destinationCode;
   @XmlAttribute(name = "DestinationLevel")
   protected DestinationLevelType destinationLevel;
   @XmlAttribute(name = "DestinationName")
   protected String destinationName;

   public PropertyIdentityType getIdentity() {
      return this.identity;
   }

   public void setIdentity(PropertyIdentityType value) {
      this.identity = value;
   }

   public DateTimeSpanType getDateRange() {
      return this.dateRange;
   }

   public void setDateRange(DateTimeSpanType value) {
      this.dateRange = value;
   }

   public AccommodationSegmentRequestType.RoomProfiles getRoomProfiles() {
      return this.roomProfiles;
   }

   public void setRoomProfiles(AccommodationSegmentRequestType.RoomProfiles value) {
      this.roomProfiles = value;
   }

   public AccommodationSegmentRequestType.MealPlans getMealPlans() {
      return this.mealPlans;
   }

   public void setMealPlans(AccommodationSegmentRequestType.MealPlans value) {
      this.mealPlans = value;
   }

   public String getRPH() {
      return this.rph;
   }

   public void setRPH(String value) {
      this.rph = value;
   }

   public String getResortCode() {
      return this.resortCode;
   }

   public void setResortCode(String value) {
      this.resortCode = value;
   }

   public String getResortName() {
      return this.resortName;
   }

   public void setResortName(String value) {
      this.resortName = value;
   }

   public String getDestinationCode() {
      return this.destinationCode;
   }

   public void setDestinationCode(String value) {
      this.destinationCode = value;
   }

   public DestinationLevelType getDestinationLevel() {
      return this.destinationLevel;
   }

   public void setDestinationLevel(DestinationLevelType value) {
      this.destinationLevel = value;
   }

   public String getDestinationName() {
      return this.destinationName;
   }

   public void setDestinationName(String value) {
      this.destinationName = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "mealPlan")
   public static class MealPlans {
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
   @XmlType(name = "", propOrder = "roomProfile")
   public static class RoomProfiles {
      @XmlElement(name = "RoomProfile", required = true)
      protected List<RoomProfileType> roomProfile;

      public List<RoomProfileType> getRoomProfile() {
         if (this.roomProfile == null) {
            this.roomProfile = new ArrayList<>();
         }

         return this.roomProfile;
      }
   }
}
