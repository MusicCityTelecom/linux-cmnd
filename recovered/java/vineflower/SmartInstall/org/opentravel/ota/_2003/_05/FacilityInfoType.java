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
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FacilityInfoType", propOrder = {"meetingRooms", "guestRooms", "restaurants"})
public class FacilityInfoType {
   @XmlElement(name = "MeetingRooms")
   protected FacilityInfoType.MeetingRooms meetingRooms;
   @XmlElement(name = "GuestRooms")
   protected FacilityInfoType.GuestRooms guestRooms;
   @XmlElement(name = "Restaurants")
   protected RestaurantsType restaurants;
   @XmlAttribute(name = "LastUpdated")
   @XmlSchemaType(name = "dateTime")
   protected XMLGregorianCalendar lastUpdated;

   public FacilityInfoType.MeetingRooms getMeetingRooms() {
      return this.meetingRooms;
   }

   public void setMeetingRooms(FacilityInfoType.MeetingRooms value) {
      this.meetingRooms = value;
   }

   public FacilityInfoType.GuestRooms getGuestRooms() {
      return this.guestRooms;
   }

   public void setGuestRooms(FacilityInfoType.GuestRooms value) {
      this.guestRooms = value;
   }

   public RestaurantsType getRestaurants() {
      return this.restaurants;
   }

   public void setRestaurants(RestaurantsType value) {
      this.restaurants = value;
   }

   public XMLGregorianCalendar getLastUpdated() {
      return this.lastUpdated;
   }

   public void setLastUpdated(XMLGregorianCalendar value) {
      this.lastUpdated = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "guestRoom")
   public static class GuestRooms {
      @XmlElement(name = "GuestRoom")
      protected List<FacilityInfoType.GuestRooms.GuestRoom> guestRoom;
      @XmlAttribute(name = "MaxOccupancy")
      @XmlSchemaType(name = "nonNegativeInteger")
      protected BigInteger maxOccupancy;

      public List<FacilityInfoType.GuestRooms.GuestRoom> getGuestRoom() {
         if (this.guestRoom == null) {
            this.guestRoom = new ArrayList<>();
         }

         return this.guestRoom;
      }

      public BigInteger getMaxOccupancy() {
         return this.maxOccupancy;
      }

      public void setMaxOccupancy(BigInteger value) {
         this.maxOccupancy = value;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = {"typeRoom", "amenities", "features", "multimediaDescriptions", "descriptiveText"})
      public static class GuestRoom {
         @XmlElement(name = "TypeRoom")
         protected List<FacilityInfoType.GuestRooms.GuestRoom.TypeRoom> typeRoom;
         @XmlElement(name = "Amenities")
         protected FacilityInfoType.GuestRooms.GuestRoom.Amenities amenities;
         @XmlElement(name = "Features")
         protected FeaturesType features;
         @XmlElement(name = "MultimediaDescriptions")
         protected MultimediaDescriptionsType multimediaDescriptions;
         @XmlElement(name = "DescriptiveText")
         protected String descriptiveText;
         @XmlAttribute(name = "RoomTypeName")
         protected String roomTypeName;
         @XmlAttribute(name = "Composite")
         protected Boolean composite;
         @XmlAttribute(name = "Quality")
         protected String quality;
         @XmlAttribute(name = "MaxOccupancy")
         @XmlSchemaType(name = "positiveInteger")
         protected BigInteger maxOccupancy;
         @XmlAttribute(name = "MaxAdultOccupancy")
         @XmlSchemaType(name = "nonNegativeInteger")
         protected BigInteger maxAdultOccupancy;
         @XmlAttribute(name = "NonsmokingQuantity")
         @XmlSchemaType(name = "nonNegativeInteger")
         protected BigInteger nonsmokingQuantity;
         @XmlAttribute(name = "MaxChildOccupancy")
         @XmlSchemaType(name = "nonNegativeInteger")
         protected BigInteger maxChildOccupancy;
         @XmlAttribute(name = "Sort")
         @XmlSchemaType(name = "nonNegativeInteger")
         protected BigInteger sort;
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
         @XmlAttribute(name = "ID")
         protected String id;

         public List<FacilityInfoType.GuestRooms.GuestRoom.TypeRoom> getTypeRoom() {
            if (this.typeRoom == null) {
               this.typeRoom = new ArrayList<>();
            }

            return this.typeRoom;
         }

         public FacilityInfoType.GuestRooms.GuestRoom.Amenities getAmenities() {
            return this.amenities;
         }

         public void setAmenities(FacilityInfoType.GuestRooms.GuestRoom.Amenities value) {
            this.amenities = value;
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

         public String getDescriptiveText() {
            return this.descriptiveText;
         }

         public void setDescriptiveText(String value) {
            this.descriptiveText = value;
         }

         public String getRoomTypeName() {
            return this.roomTypeName;
         }

         public void setRoomTypeName(String value) {
            this.roomTypeName = value;
         }

         public Boolean isComposite() {
            return this.composite;
         }

         public void setComposite(Boolean value) {
            this.composite = value;
         }

         public String getQuality() {
            return this.quality;
         }

         public void setQuality(String value) {
            this.quality = value;
         }

         public BigInteger getMaxOccupancy() {
            return this.maxOccupancy;
         }

         public void setMaxOccupancy(BigInteger value) {
            this.maxOccupancy = value;
         }

         public BigInteger getMaxAdultOccupancy() {
            return this.maxAdultOccupancy;
         }

         public void setMaxAdultOccupancy(BigInteger value) {
            this.maxAdultOccupancy = value;
         }

         public BigInteger getNonsmokingQuantity() {
            return this.nonsmokingQuantity;
         }

         public void setNonsmokingQuantity(BigInteger value) {
            this.nonsmokingQuantity = value;
         }

         public BigInteger getMaxChildOccupancy() {
            return this.maxChildOccupancy;
         }

         public void setMaxChildOccupancy(BigInteger value) {
            this.maxChildOccupancy = value;
         }

         public BigInteger getSort() {
            return this.sort;
         }

         public void setSort(BigInteger value) {
            this.sort = value;
         }

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

         public String getID() {
            return this.id;
         }

         public void setID(String value) {
            this.id = value;
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "", propOrder = "amenity")
         public static class Amenities {
            @XmlElement(name = "Amenity", required = true)
            protected List<FacilityInfoType.GuestRooms.GuestRoom.Amenities.Amenity> amenity;

            public List<FacilityInfoType.GuestRooms.GuestRoom.Amenities.Amenity> getAmenity() {
               if (this.amenity == null) {
                  this.amenity = new ArrayList<>();
               }

               return this.amenity;
            }

            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {"operationSchedules", "contactInfo", "multimediaDescriptions", "descriptiveText"})
            public static class Amenity {
               @XmlElement(name = "OperationSchedules")
               protected OperationSchedulesPlusChargeType operationSchedules;
               @XmlElement(name = "ContactInfo")
               protected List<ContactInfoRootType> contactInfo;
               @XmlElement(name = "MultimediaDescriptions")
               protected MultimediaDescriptionsType multimediaDescriptions;
               @XmlElement(name = "DescriptiveText")
               protected String descriptiveText;
               @XmlAttribute(name = "RoomAmenityCode")
               protected String roomAmenityCode;
               @XmlAttribute(name = "IncludedInRateIndicator")
               protected Boolean includedInRateIndicator;
               @XmlAttribute(name = "ExistsCode")
               protected String existsCode;
               @XmlAttribute(name = "Quantity")
               @XmlSchemaType(name = "nonNegativeInteger")
               protected BigInteger quantity;
               @XmlAttribute(name = "CodeDetail")
               protected String codeDetail;
               @XmlAttribute(name = "Removal")
               protected Boolean removal;
               @XmlAttribute(name = "ID")
               protected String id;

               public OperationSchedulesPlusChargeType getOperationSchedules() {
                  return this.operationSchedules;
               }

               public void setOperationSchedules(OperationSchedulesPlusChargeType value) {
                  this.operationSchedules = value;
               }

               public List<ContactInfoRootType> getContactInfo() {
                  if (this.contactInfo == null) {
                     this.contactInfo = new ArrayList<>();
                  }

                  return this.contactInfo;
               }

               public MultimediaDescriptionsType getMultimediaDescriptions() {
                  return this.multimediaDescriptions;
               }

               public void setMultimediaDescriptions(MultimediaDescriptionsType value) {
                  this.multimediaDescriptions = value;
               }

               public String getDescriptiveText() {
                  return this.descriptiveText;
               }

               public void setDescriptiveText(String value) {
                  this.descriptiveText = value;
               }

               public String getRoomAmenityCode() {
                  return this.roomAmenityCode;
               }

               public void setRoomAmenityCode(String value) {
                  this.roomAmenityCode = value;
               }

               public Boolean isIncludedInRateIndicator() {
                  return this.includedInRateIndicator;
               }

               public void setIncludedInRateIndicator(Boolean value) {
                  this.includedInRateIndicator = value;
               }

               public String getExistsCode() {
                  return this.existsCode;
               }

               public void setExistsCode(String value) {
                  this.existsCode = value;
               }

               public BigInteger getQuantity() {
                  return this.quantity;
               }

               public void setQuantity(BigInteger value) {
                  this.quantity = value;
               }

               public String getCodeDetail() {
                  return this.codeDetail;
               }

               public void setCodeDetail(String value) {
                  this.codeDetail = value;
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
            }
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "")
         public static class TypeRoom {
            @XmlAttribute(name = "StandardNumBeds")
            @XmlSchemaType(name = "nonNegativeInteger")
            protected BigInteger standardNumBeds;
            @XmlAttribute(name = "StandardOccupancy")
            @XmlSchemaType(name = "nonNegativeInteger")
            protected BigInteger standardOccupancy;
            @XmlAttribute(name = "MaxRollaways")
            @XmlSchemaType(name = "nonNegativeInteger")
            protected BigInteger maxRollaways;
            @XmlAttribute(name = "Size")
            @XmlSchemaType(name = "nonNegativeInteger")
            protected BigInteger size;
            @XmlAttribute(name = "TypeImplied")
            protected String typeImplied;
            @XmlAttribute(name = "Count")
            @XmlSchemaType(name = "nonNegativeInteger")
            protected BigInteger count;
            @XmlAttribute(name = "Name")
            protected String name;
            @XmlAttribute(name = "MaxCribs")
            @XmlSchemaType(name = "nonNegativeInteger")
            protected BigInteger maxCribs;
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

            public BigInteger getStandardNumBeds() {
               return this.standardNumBeds;
            }

            public void setStandardNumBeds(BigInteger value) {
               this.standardNumBeds = value;
            }

            public BigInteger getStandardOccupancy() {
               return this.standardOccupancy;
            }

            public void setStandardOccupancy(BigInteger value) {
               this.standardOccupancy = value;
            }

            public BigInteger getMaxRollaways() {
               return this.maxRollaways;
            }

            public void setMaxRollaways(BigInteger value) {
               this.maxRollaways = value;
            }

            public BigInteger getSize() {
               return this.size;
            }

            public void setSize(BigInteger value) {
               this.size = value;
            }

            public String getTypeImplied() {
               return this.typeImplied;
            }

            public void setTypeImplied(String value) {
               this.typeImplied = value;
            }

            public BigInteger getCount() {
               return this.count;
            }

            public void setCount(BigInteger value) {
               this.count = value;
            }

            public String getName() {
               return this.name;
            }

            public void setName(String value) {
               this.name = value;
            }

            public BigInteger getMaxCribs() {
               return this.maxCribs;
            }

            public void setMaxCribs(BigInteger value) {
               this.maxCribs = value;
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
         }
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "codes")
   public static class MeetingRooms extends MeetingRoomsType {
      @XmlElement(name = "Codes")
      protected FacilityInfoType.MeetingRooms.Codes codes;

      public FacilityInfoType.MeetingRooms.Codes getCodes() {
         return this.codes;
      }

      public void setCodes(FacilityInfoType.MeetingRooms.Codes value) {
         this.codes = value;
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
   }
}
