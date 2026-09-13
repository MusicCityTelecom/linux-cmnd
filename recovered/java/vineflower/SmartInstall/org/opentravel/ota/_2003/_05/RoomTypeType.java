package org.opentravel.ota._2003._05;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.htng._2011b.HTNGBasicOrSuiteRoomType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RoomTypeType", propOrder = {"roomDescription", "additionalDetails", "amenities", "occupancy", "tpaExtensions"})
@XmlSeeAlso(HTNGBasicOrSuiteRoomType.class)
public class RoomTypeType {
   @XmlElement(name = "RoomDescription")
   protected ParagraphType roomDescription;
   @XmlElement(name = "AdditionalDetails")
   protected AdditionalDetailsType additionalDetails;
   @XmlElement(name = "Amenities")
   protected RoomTypeType.Amenities amenities;
   @XmlElement(name = "Occupancy")
   protected List<RoomTypeType.Occupancy> occupancy;
   @XmlElement(name = "TPA_Extensions")
   protected TPAExtensionsType tpaExtensions;
   @XmlAttribute(name = "NumberOfUnits")
   protected BigInteger numberOfUnits;
   @XmlAttribute(name = "IsRoom")
   protected Boolean isRoom;
   @XmlAttribute(name = "IsConverted")
   protected Boolean isConverted;
   @XmlAttribute(name = "IsAlternate")
   protected Boolean isAlternate;
   @XmlAttribute(name = "ReqdGuaranteeType")
   protected String reqdGuaranteeType;
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

   public ParagraphType getRoomDescription() {
      return this.roomDescription;
   }

   public void setRoomDescription(ParagraphType value) {
      this.roomDescription = value;
   }

   public AdditionalDetailsType getAdditionalDetails() {
      return this.additionalDetails;
   }

   public void setAdditionalDetails(AdditionalDetailsType value) {
      this.additionalDetails = value;
   }

   public RoomTypeType.Amenities getAmenities() {
      return this.amenities;
   }

   public void setAmenities(RoomTypeType.Amenities value) {
      this.amenities = value;
   }

   public List<RoomTypeType.Occupancy> getOccupancy() {
      if (this.occupancy == null) {
         this.occupancy = new ArrayList<>();
      }

      return this.occupancy;
   }

   public TPAExtensionsType getTPAExtensions() {
      return this.tpaExtensions;
   }

   public void setTPAExtensions(TPAExtensionsType value) {
      this.tpaExtensions = value;
   }

   public BigInteger getNumberOfUnits() {
      return this.numberOfUnits;
   }

   public void setNumberOfUnits(BigInteger value) {
      this.numberOfUnits = value;
   }

   public Boolean isIsRoom() {
      return this.isRoom;
   }

   public void setIsRoom(Boolean value) {
      this.isRoom = value;
   }

   public Boolean isIsConverted() {
      return this.isConverted;
   }

   public void setIsConverted(Boolean value) {
      this.isConverted = value;
   }

   public Boolean isIsAlternate() {
      return this.isAlternate;
   }

   public void setIsAlternate(Boolean value) {
      this.isAlternate = value;
   }

   public String getReqdGuaranteeType() {
      return this.reqdGuaranteeType;
   }

   public void setReqdGuaranteeType(String value) {
      this.reqdGuaranteeType = value;
   }

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

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "amenity")
   public static class Amenities {
      @XmlElement(name = "Amenity")
      protected List<RoomAmenityPrefType> amenity;

      public List<RoomAmenityPrefType> getAmenity() {
         if (this.amenity == null) {
            this.amenity = new ArrayList<>();
         }

         return this.amenity;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class Occupancy {
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
      @XmlAttribute(name = "MinOccupancy")
      protected Integer minOccupancy;
      @XmlAttribute(name = "MaxOccupancy")
      protected Integer maxOccupancy;

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
   }
}
