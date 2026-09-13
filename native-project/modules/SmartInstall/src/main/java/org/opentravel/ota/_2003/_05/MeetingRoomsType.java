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
@XmlType(name = "MeetingRoomsType", propOrder = "meetingRoom")
@XmlSeeAlso(FacilityInfoType.MeetingRooms.class)
public class MeetingRoomsType {
   @XmlElement(name = "MeetingRoom")
   protected List<MeetingRoomsType.MeetingRoom> meetingRoom;
   @XmlAttribute(name = "MeetingRoomCount")
   @XmlSchemaType(name = "nonNegativeInteger")
   protected BigInteger meetingRoomCount;
   @XmlAttribute(name = "SmallestRoomSpace")
   @XmlSchemaType(name = "nonNegativeInteger")
   protected BigInteger smallestRoomSpace;
   @XmlAttribute(name = "LargestRoomSpace")
   @XmlSchemaType(name = "nonNegativeInteger")
   protected BigInteger largestRoomSpace;
   @XmlAttribute(name = "TotalRoomSpace")
   @XmlSchemaType(name = "nonNegativeInteger")
   protected BigInteger totalRoomSpace;
   @XmlAttribute(name = "LargestSeatingCapacity")
   @XmlSchemaType(name = "nonNegativeInteger")
   protected BigInteger largestSeatingCapacity;
   @XmlAttribute(name = "SecondLargestSeatingCapacity")
   @XmlSchemaType(name = "nonNegativeInteger")
   protected BigInteger secondLargestSeatingCapacity;
   @XmlAttribute(name = "SmallestSeatingCapacity")
   @XmlSchemaType(name = "nonNegativeInteger")
   protected BigInteger smallestSeatingCapacity;
   @XmlAttribute(name = "TotalRoomSeatingCapacity")
   @XmlSchemaType(name = "nonNegativeInteger")
   protected BigInteger totalRoomSeatingCapacity;
   @XmlAttribute(name = "LargestRoomHeight")
   @XmlSchemaType(name = "nonNegativeInteger")
   protected BigInteger largestRoomHeight;
   @XmlAttribute(name = "UnitOfMeasureQuantity")
   protected BigDecimal unitOfMeasureQuantity;
   @XmlAttribute(name = "UnitOfMeasure")
   protected String unitOfMeasure;
   @XmlAttribute(name = "UnitOfMeasureCode")
   protected String unitOfMeasureCode;

   public List<MeetingRoomsType.MeetingRoom> getMeetingRoom() {
      if (this.meetingRoom == null) {
         this.meetingRoom = new ArrayList<>();
      }

      return this.meetingRoom;
   }

   public BigInteger getMeetingRoomCount() {
      return this.meetingRoomCount;
   }

   public void setMeetingRoomCount(BigInteger value) {
      this.meetingRoomCount = value;
   }

   public BigInteger getSmallestRoomSpace() {
      return this.smallestRoomSpace;
   }

   public void setSmallestRoomSpace(BigInteger value) {
      this.smallestRoomSpace = value;
   }

   public BigInteger getLargestRoomSpace() {
      return this.largestRoomSpace;
   }

   public void setLargestRoomSpace(BigInteger value) {
      this.largestRoomSpace = value;
   }

   public BigInteger getTotalRoomSpace() {
      return this.totalRoomSpace;
   }

   public void setTotalRoomSpace(BigInteger value) {
      this.totalRoomSpace = value;
   }

   public BigInteger getLargestSeatingCapacity() {
      return this.largestSeatingCapacity;
   }

   public void setLargestSeatingCapacity(BigInteger value) {
      this.largestSeatingCapacity = value;
   }

   public BigInteger getSecondLargestSeatingCapacity() {
      return this.secondLargestSeatingCapacity;
   }

   public void setSecondLargestSeatingCapacity(BigInteger value) {
      this.secondLargestSeatingCapacity = value;
   }

   public BigInteger getSmallestSeatingCapacity() {
      return this.smallestSeatingCapacity;
   }

   public void setSmallestSeatingCapacity(BigInteger value) {
      this.smallestSeatingCapacity = value;
   }

   public BigInteger getTotalRoomSeatingCapacity() {
      return this.totalRoomSeatingCapacity;
   }

   public void setTotalRoomSeatingCapacity(BigInteger value) {
      this.totalRoomSeatingCapacity = value;
   }

   public BigInteger getLargestRoomHeight() {
      return this.largestRoomHeight;
   }

   public void setLargestRoomHeight(BigInteger value) {
      this.largestRoomHeight = value;
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

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = {"codes", "dimension", "availableCapacities", "features", "multimediaDescriptions"})
   public static class MeetingRoom {
      @XmlElement(name = "Codes")
      protected MeetingRoomsType.MeetingRoom.Codes codes;
      @XmlElement(name = "Dimension")
      protected MeetingRoomsType.MeetingRoom.Dimension dimension;
      @XmlElement(name = "AvailableCapacities")
      protected MeetingRoomsType.MeetingRoom.AvailableCapacities availableCapacities;
      @XmlElement(name = "Features")
      protected FeaturesType features;
      @XmlElement(name = "MultimediaDescriptions")
      protected MultimediaDescriptionsType multimediaDescriptions;
      @XmlAttribute(name = "Irregular")
      protected Boolean irregular;
      @XmlAttribute(name = "PropertySystemName")
      protected String propertySystemName;
      @XmlAttribute(name = "RoomName")
      protected String roomName;
      @XmlAttribute(name = "Sort")
      @XmlSchemaType(name = "nonNegativeInteger")
      protected BigInteger sort;
      @XmlAttribute(name = "MeetingRoomCapacity")
      @XmlSchemaType(name = "nonNegativeInteger")
      protected BigInteger meetingRoomCapacity;
      @XmlAttribute(name = "Access")
      protected String access;
      @XmlAttribute(name = "MeetingRoomTypeCode")
      protected String meetingRoomTypeCode;
      @XmlAttribute(name = "MeetingRoomLevel")
      protected String meetingRoomLevel;
      @XmlAttribute(name = "DedicatedIndicator")
      protected Boolean dedicatedIndicator;
      @XmlAttribute(name = "Removal")
      protected Boolean removal;
      @XmlAttribute(name = "ID")
      protected String id;

      public MeetingRoomsType.MeetingRoom.Codes getCodes() {
         return this.codes;
      }

      public void setCodes(MeetingRoomsType.MeetingRoom.Codes value) {
         this.codes = value;
      }

      public MeetingRoomsType.MeetingRoom.Dimension getDimension() {
         return this.dimension;
      }

      public void setDimension(MeetingRoomsType.MeetingRoom.Dimension value) {
         this.dimension = value;
      }

      public MeetingRoomsType.MeetingRoom.AvailableCapacities getAvailableCapacities() {
         return this.availableCapacities;
      }

      public void setAvailableCapacities(MeetingRoomsType.MeetingRoom.AvailableCapacities value) {
         this.availableCapacities = value;
      }

      public FeaturesType getFeatures() {
         return this.features;
      }

      public void setFeatures(FeaturesType value) {
         this.features = value;
      }

      public MultimediaDescriptionsType getMultimediaDescriptions() {
         return this.multimediaDescriptions;
      }

      public void setMultimediaDescriptions(MultimediaDescriptionsType value) {
         this.multimediaDescriptions = value;
      }

      public Boolean isIrregular() {
         return this.irregular;
      }

      public void setIrregular(Boolean value) {
         this.irregular = value;
      }

      public String getPropertySystemName() {
         return this.propertySystemName;
      }

      public void setPropertySystemName(String value) {
         this.propertySystemName = value;
      }

      public String getRoomName() {
         return this.roomName;
      }

      public void setRoomName(String value) {
         this.roomName = value;
      }

      public BigInteger getSort() {
         return this.sort;
      }

      public void setSort(BigInteger value) {
         this.sort = value;
      }

      public BigInteger getMeetingRoomCapacity() {
         return this.meetingRoomCapacity;
      }

      public void setMeetingRoomCapacity(BigInteger value) {
         this.meetingRoomCapacity = value;
      }

      public String getAccess() {
         return this.access;
      }

      public void setAccess(String value) {
         this.access = value;
      }

      public String getMeetingRoomTypeCode() {
         return this.meetingRoomTypeCode;
      }

      public void setMeetingRoomTypeCode(String value) {
         this.meetingRoomTypeCode = value;
      }

      public String getMeetingRoomLevel() {
         return this.meetingRoomLevel;
      }

      public void setMeetingRoomLevel(String value) {
         this.meetingRoomLevel = value;
      }

      public Boolean isDedicatedIndicator() {
         return this.dedicatedIndicator;
      }

      public void setDedicatedIndicator(Boolean value) {
         this.dedicatedIndicator = value;
      }

      public Boolean isRemoval() {
         return this.removal;
      }

      public void setRemoval(Boolean value) {
         this.removal = value;
      }

      public String getID() {
         return this.id;
      }

      public void setID(String value) {
         this.id = value;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = "meetingRoomCapacity")
      public static class AvailableCapacities {
         @XmlElement(name = "MeetingRoomCapacity", required = true)
         protected List<MeetingRoomCapacityType> meetingRoomCapacity;

         public List<MeetingRoomCapacityType> getMeetingRoomCapacity() {
            if (this.meetingRoomCapacity == null) {
               this.meetingRoomCapacity = new ArrayList<>();
            }

            return this.meetingRoomCapacity;
         }
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = "code")
      public static class Codes {
         @XmlElement(name = "Code", required = true)
         protected List<MeetingRoomCodeType> code;

         public List<MeetingRoomCodeType> getCode() {
            if (this.code == null) {
               this.code = new ArrayList<>();
            }

            return this.code;
         }
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class Dimension {
         @XmlAttribute(name = "Area")
         protected BigDecimal area;
         @XmlAttribute(name = "Height")
         protected BigDecimal height;
         @XmlAttribute(name = "Length")
         protected BigDecimal length;
         @XmlAttribute(name = "Width")
         protected BigDecimal width;
         @XmlAttribute(name = "Units")
         protected String units;
         @XmlAttribute(name = "UnitOfMeasureCode")
         protected String unitOfMeasureCode;

         public BigDecimal getArea() {
            return this.area;
         }

         public void setArea(BigDecimal value) {
            this.area = value;
         }

         public BigDecimal getHeight() {
            return this.height;
         }

         public void setHeight(BigDecimal value) {
            this.height = value;
         }

         public BigDecimal getLength() {
            return this.length;
         }

         public void setLength(BigDecimal value) {
            this.length = value;
         }

         public BigDecimal getWidth() {
            return this.width;
         }

         public void setWidth(BigDecimal value) {
            this.width = value;
         }

         public String getUnits() {
            return this.units;
         }

         public void setUnits(String value) {
            this.units = value;
         }

         public String getUnitOfMeasureCode() {
            return this.unitOfMeasureCode;
         }

         public void setUnitOfMeasureCode(String value) {
            this.unitOfMeasureCode = value;
         }
      }
   }
}
