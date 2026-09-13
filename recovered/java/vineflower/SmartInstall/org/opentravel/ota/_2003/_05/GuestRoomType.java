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
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GuestRoomType", propOrder = {"quantities", "occupancy", "room", "amenities", "roomLevelFees", "additionalGuestAmount", "description"})
public class GuestRoomType {
   @XmlElement(name = "Quantities")
   protected GuestRoomType.Quantities quantities;
   @XmlElement(name = "Occupancy")
   protected List<GuestRoomType.Occupancy> occupancy;
   @XmlElement(name = "Room")
   protected GuestRoomType.Room room;
   @XmlElement(name = "Amenities")
   protected GuestRoomType.Amenities amenities;
   @XmlElement(name = "RoomLevelFees")
   protected GuestRoomType.RoomLevelFees roomLevelFees;
   @XmlElement(name = "AdditionalGuestAmount")
   protected List<AdditionalGuestAmountType> additionalGuestAmount;
   @XmlElement(name = "Description")
   protected ParagraphType description;

   public GuestRoomType.Quantities getQuantities() {
      return this.quantities;
   }

   public void setQuantities(GuestRoomType.Quantities value) {
      this.quantities = value;
   }

   public List<GuestRoomType.Occupancy> getOccupancy() {
      if (this.occupancy == null) {
         this.occupancy = new ArrayList<>();
      }

      return this.occupancy;
   }

   public GuestRoomType.Room getRoom() {
      return this.room;
   }

   public void setRoom(GuestRoomType.Room value) {
      this.room = value;
   }

   public GuestRoomType.Amenities getAmenities() {
      return this.amenities;
   }

   public void setAmenities(GuestRoomType.Amenities value) {
      this.amenities = value;
   }

   public GuestRoomType.RoomLevelFees getRoomLevelFees() {
      return this.roomLevelFees;
   }

   public void setRoomLevelFees(GuestRoomType.RoomLevelFees value) {
      this.roomLevelFees = value;
   }

   public List<AdditionalGuestAmountType> getAdditionalGuestAmount() {
      if (this.additionalGuestAmount == null) {
         this.additionalGuestAmount = new ArrayList<>();
      }

      return this.additionalGuestAmount;
   }

   public ParagraphType getDescription() {
      return this.description;
   }

   public void setDescription(ParagraphType value) {
      this.description = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "amenity")
   public static class Amenities {
      @XmlElement(name = "Amenity", required = true)
      protected List<GuestRoomType.Amenities.Amenity> amenity;

      public List<GuestRoomType.Amenities.Amenity> getAmenity() {
         if (this.amenity == null) {
            this.amenity = new ArrayList<>();
         }

         return this.amenity;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class Amenity {
         @XmlAttribute(name = "AmenityCode")
         protected String amenityCode;

         public String getAmenityCode() {
            return this.amenityCode;
         }

         public void setAmenityCode(String value) {
            this.amenityCode = value;
         }
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class Occupancy {
      @XmlAttribute(name = "MinOccupancy")
      protected Integer minOccupancy;
      @XmlAttribute(name = "MaxOccupancy")
      protected Integer maxOccupancy;
      @XmlAttribute(name = "AgeQualifyingCode")
      protected String ageQualifyingCode;
      @XmlAttribute(name = "MinAge")
      protected Integer minAge;
      @XmlAttribute(name = "MaxAge")
      protected Integer maxAge;
      @XmlAttribute(name = "AgeTimeUnit")
      protected TimeUnitType ageTimeUnit;
      @XmlAttribute(name = "AgeBucket")
      protected String ageBucket;

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

      public String getAgeQualifyingCode() {
         return this.ageQualifyingCode;
      }

      public void setAgeQualifyingCode(String value) {
         this.ageQualifyingCode = value;
      }

      public Integer getMinAge() {
         return this.minAge;
      }

      public void setMinAge(Integer value) {
         this.minAge = value;
      }

      public Integer getMaxAge() {
         return this.maxAge;
      }

      public void setMaxAge(Integer value) {
         this.maxAge = value;
      }

      public TimeUnitType getAgeTimeUnit() {
         return this.ageTimeUnit;
      }

      public void setAgeTimeUnit(TimeUnitType value) {
         this.ageTimeUnit = value;
      }

      public String getAgeBucket() {
         return this.ageBucket;
      }

      public void setAgeBucket(String value) {
         this.ageBucket = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class Quantities {
      @XmlAttribute(name = "MaxRollaways")
      @XmlSchemaType(name = "nonNegativeInteger")
      protected BigInteger maxRollaways;
      @XmlAttribute(name = "StandardNumBeds")
      @XmlSchemaType(name = "nonNegativeInteger")
      protected BigInteger standardNumBeds;
      @XmlAttribute(name = "MaximumAdditionalGuests")
      @XmlSchemaType(name = "nonNegativeInteger")
      protected BigInteger maximumAdditionalGuests;
      @XmlAttribute(name = "MinBillableGuests")
      @XmlSchemaType(name = "nonNegativeInteger")
      protected BigInteger minBillableGuests;

      public BigInteger getMaxRollaways() {
         return this.maxRollaways;
      }

      public void setMaxRollaways(BigInteger value) {
         this.maxRollaways = value;
      }

      public BigInteger getStandardNumBeds() {
         return this.standardNumBeds;
      }

      public void setStandardNumBeds(BigInteger value) {
         this.standardNumBeds = value;
      }

      public BigInteger getMaximumAdditionalGuests() {
         return this.maximumAdditionalGuests;
      }

      public void setMaximumAdditionalGuests(BigInteger value) {
         this.maximumAdditionalGuests = value;
      }

      public BigInteger getMinBillableGuests() {
         return this.minBillableGuests;
      }

      public void setMinBillableGuests(BigInteger value) {
         this.minBillableGuests = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class Room {
      @XmlAttribute(name = "RoomType")
      protected String roomType;
      @XmlAttribute(name = "RoomTypeCode")
      protected String roomTypeCode;
      @XmlAttribute(name = "RoomCategory")
      protected String roomCategory;
      @XmlAttribute(name = "RoomID")
      protected String roomID;
      @XmlAttribute(name = "Floor")
      protected Integer floor;
      @XmlAttribute(name = "InvBlockCode")
      protected String invBlockCode;
      @XmlAttribute(name = "RoomLocationCode")
      protected String roomLocationCode;
      @XmlAttribute(name = "RoomViewCode")
      protected String roomViewCode;
      @XmlAttribute(name = "BedTypeCode")
      protected List<String> bedTypeCode;
      @XmlAttribute(name = "NonSmoking")
      protected Boolean nonSmoking;
      @XmlAttribute(name = "Configuration")
      protected String configuration;
      @XmlAttribute(name = "SizeMeasurement")
      protected String sizeMeasurement;
      @XmlAttribute(name = "Quantity")
      protected Integer quantity;
      @XmlAttribute(name = "Composite")
      protected Boolean composite;
      @XmlAttribute(name = "RoomClassificationCode")
      protected String roomClassificationCode;
      @XmlAttribute(name = "RoomArchitectureCode")
      protected String roomArchitectureCode;
      @XmlAttribute(name = "RoomGender")
      @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
      protected String roomGender;
      @XmlAttribute(name = "SharedRoomInd")
      protected Boolean sharedRoomInd;
      @XmlAttribute(name = "PromotionCode")
      protected String promotionCode;
      @XmlAttribute(name = "PromotionVendorCode")
      protected List<String> promotionVendorCode;

      public String getRoomType() {
         return this.roomType;
      }

      public void setRoomType(String value) {
         this.roomType = value;
      }

      public String getRoomTypeCode() {
         return this.roomTypeCode;
      }

      public void setRoomTypeCode(String value) {
         this.roomTypeCode = value;
      }

      public String getRoomCategory() {
         return this.roomCategory;
      }

      public void setRoomCategory(String value) {
         this.roomCategory = value;
      }

      public String getRoomID() {
         return this.roomID;
      }

      public void setRoomID(String value) {
         this.roomID = value;
      }

      public Integer getFloor() {
         return this.floor;
      }

      public void setFloor(Integer value) {
         this.floor = value;
      }

      public String getInvBlockCode() {
         return this.invBlockCode;
      }

      public void setInvBlockCode(String value) {
         this.invBlockCode = value;
      }

      public String getRoomLocationCode() {
         return this.roomLocationCode;
      }

      public void setRoomLocationCode(String value) {
         this.roomLocationCode = value;
      }

      public String getRoomViewCode() {
         return this.roomViewCode;
      }

      public void setRoomViewCode(String value) {
         this.roomViewCode = value;
      }

      public List<String> getBedTypeCode() {
         if (this.bedTypeCode == null) {
            this.bedTypeCode = new ArrayList<>();
         }

         return this.bedTypeCode;
      }

      public Boolean isNonSmoking() {
         return this.nonSmoking;
      }

      public void setNonSmoking(Boolean value) {
         this.nonSmoking = value;
      }

      public String getConfiguration() {
         return this.configuration;
      }

      public void setConfiguration(String value) {
         this.configuration = value;
      }

      public String getSizeMeasurement() {
         return this.sizeMeasurement;
      }

      public void setSizeMeasurement(String value) {
         this.sizeMeasurement = value;
      }

      public Integer getQuantity() {
         return this.quantity;
      }

      public void setQuantity(Integer value) {
         this.quantity = value;
      }

      public Boolean isComposite() {
         return this.composite;
      }

      public void setComposite(Boolean value) {
         this.composite = value;
      }

      public String getRoomClassificationCode() {
         return this.roomClassificationCode;
      }

      public void setRoomClassificationCode(String value) {
         this.roomClassificationCode = value;
      }

      public String getRoomArchitectureCode() {
         return this.roomArchitectureCode;
      }

      public void setRoomArchitectureCode(String value) {
         this.roomArchitectureCode = value;
      }

      public String getRoomGender() {
         return this.roomGender;
      }

      public void setRoomGender(String value) {
         this.roomGender = value;
      }

      public Boolean isSharedRoomInd() {
         return this.sharedRoomInd;
      }

      public void setSharedRoomInd(Boolean value) {
         this.sharedRoomInd = value;
      }

      public String getPromotionCode() {
         return this.promotionCode;
      }

      public void setPromotionCode(String value) {
         this.promotionCode = value;
      }

      public List<String> getPromotionVendorCode() {
         if (this.promotionVendorCode == null) {
            this.promotionVendorCode = new ArrayList<>();
         }

         return this.promotionVendorCode;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class RoomLevelFees extends FeesType {
      @XmlAttribute(name = "URI")
      @XmlSchemaType(name = "anyURI")
      protected String uri;
      @XmlAttribute(name = "Quantity")
      @XmlSchemaType(name = "nonNegativeInteger")
      protected BigInteger quantity;
      @XmlAttribute(name = "Code")
      protected String code;
      @XmlAttribute(name = "CodeContext")
      protected String codeContext;

      public String getURI() {
         return this.uri;
      }

      public void setURI(String value) {
         this.uri = value;
      }

      public BigInteger getQuantity() {
         return this.quantity;
      }

      public void setQuantity(BigInteger value) {
         this.quantity = value;
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
   }
}
