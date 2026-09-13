package org.opentravel.ota._2003._05;

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
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "ItemSearchCriterionType",
   propOrder = {"position", "address", "telephone", "refPoint", "codeRef", "hotelRef", "radius", "mapArea", "additionalContents"}
)
@XmlSeeAlso(HotelSearchCriterionType.class)
public class ItemSearchCriterionType {
   @XmlElement(name = "Position")
   protected ItemSearchCriterionType.Position position;
   @XmlElement(name = "Address")
   protected ItemSearchCriterionType.Address address;
   @XmlElement(name = "Telephone")
   protected ItemSearchCriterionType.Telephone telephone;
   @XmlElement(name = "RefPoint")
   protected List<ItemSearchCriterionType.RefPoint> refPoint;
   @XmlElement(name = "CodeRef")
   protected ItemSearchCriterionType.CodeRef codeRef;
   @XmlElement(name = "HotelRef")
   protected List<ItemSearchCriterionType.HotelRef> hotelRef;
   @XmlElement(name = "Radius")
   protected ItemSearchCriterionType.Radius radius;
   @XmlElement(name = "MapArea")
   protected ItemSearchCriterionType.MapArea mapArea;
   @XmlElement(name = "AdditionalContents")
   protected ItemSearchCriterionType.AdditionalContents additionalContents;
   @XmlAttribute(name = "ExactMatch")
   protected Boolean exactMatch;
   @XmlAttribute(name = "ImportanceType")
   protected String importanceType;
   @XmlAttribute(name = "Ranking")
   protected BigInteger ranking;

   public ItemSearchCriterionType.Position getPosition() {
      return this.position;
   }

   public void setPosition(ItemSearchCriterionType.Position value) {
      this.position = value;
   }

   public ItemSearchCriterionType.Address getAddress() {
      return this.address;
   }

   public void setAddress(ItemSearchCriterionType.Address value) {
      this.address = value;
   }

   public ItemSearchCriterionType.Telephone getTelephone() {
      return this.telephone;
   }

   public void setTelephone(ItemSearchCriterionType.Telephone value) {
      this.telephone = value;
   }

   public List<ItemSearchCriterionType.RefPoint> getRefPoint() {
      if (this.refPoint == null) {
         this.refPoint = new ArrayList<>();
      }

      return this.refPoint;
   }

   public ItemSearchCriterionType.CodeRef getCodeRef() {
      return this.codeRef;
   }

   public void setCodeRef(ItemSearchCriterionType.CodeRef value) {
      this.codeRef = value;
   }

   public List<ItemSearchCriterionType.HotelRef> getHotelRef() {
      if (this.hotelRef == null) {
         this.hotelRef = new ArrayList<>();
      }

      return this.hotelRef;
   }

   public ItemSearchCriterionType.Radius getRadius() {
      return this.radius;
   }

   public void setRadius(ItemSearchCriterionType.Radius value) {
      this.radius = value;
   }

   public ItemSearchCriterionType.MapArea getMapArea() {
      return this.mapArea;
   }

   public void setMapArea(ItemSearchCriterionType.MapArea value) {
      this.mapArea = value;
   }

   public ItemSearchCriterionType.AdditionalContents getAdditionalContents() {
      return this.additionalContents;
   }

   public void setAdditionalContents(ItemSearchCriterionType.AdditionalContents value) {
      this.additionalContents = value;
   }

   public Boolean isExactMatch() {
      return this.exactMatch;
   }

   public void setExactMatch(Boolean value) {
      this.exactMatch = value;
   }

   public String getImportanceType() {
      return this.importanceType;
   }

   public void setImportanceType(String value) {
      this.importanceType = value;
   }

   public BigInteger getRanking() {
      return this.ranking;
   }

   public void setRanking(BigInteger value) {
      this.ranking = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "additionalContent")
   public static class AdditionalContents {
      @XmlElement(name = "AdditionalContent", required = true)
      protected List<ItemSearchCriterionType.AdditionalContents.AdditionalContent> additionalContent;

      public List<ItemSearchCriterionType.AdditionalContents.AdditionalContent> getAdditionalContent() {
         if (this.additionalContent == null) {
            this.additionalContent = new ArrayList<>();
         }

         return this.additionalContent;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class AdditionalContent {
         @XmlAttribute(name = "ContentGroupCode", required = true)
         protected String contentGroupCode;
         @XmlAttribute(name = "CodeDetail")
         protected String codeDetail;
         @XmlAttribute(name = "Removal")
         protected Boolean removal;

         public String getContentGroupCode() {
            return this.contentGroupCode;
         }

         public void setContentGroupCode(String value) {
            this.contentGroupCode = value;
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
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class Address extends AddressType {
      @XmlAttribute(name = "SameCountryInd")
      protected Boolean sameCountryInd;
      @XmlAttribute(name = "AddressSearchScope")
      @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
      protected String addressSearchScope;

      public Boolean isSameCountryInd() {
         return this.sameCountryInd;
      }

      public void setSameCountryInd(Boolean value) {
         this.sameCountryInd = value;
      }

      public String getAddressSearchScope() {
         return this.addressSearchScope;
      }

      public void setAddressSearchScope(String value) {
         this.addressSearchScope = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class CodeRef extends LocationType {
      @XmlAttribute(name = "VicinityCode")
      protected String vicinityCode;

      public String getVicinityCode() {
         return this.vicinityCode;
      }

      public void setVicinityCode(String value) {
         this.vicinityCode = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class HotelRef {
      @XmlAttribute(name = "SegmentCategoryCode")
      protected String segmentCategoryCode;
      @XmlAttribute(name = "PropertyClassCode")
      protected String propertyClassCode;
      @XmlAttribute(name = "ArchitecturalStyleCode")
      protected String architecturalStyleCode;
      @XmlAttribute(name = "SupplierIntegrationLevel")
      @XmlSchemaType(name = "nonNegativeInteger")
      protected BigInteger supplierIntegrationLevel;
      @XmlAttribute(name = "LocationCategoryCode")
      protected String locationCategoryCode;
      @XmlAttribute(name = "ExtendedCitySearchIndicator")
      protected Boolean extendedCitySearchIndicator;
      @XmlAttribute(name = "ChainCode")
      protected String chainCode;
      @XmlAttribute(name = "BrandCode")
      protected String brandCode;
      @XmlAttribute(name = "HotelCode")
      protected String hotelCode;
      @XmlAttribute(name = "HotelCityCode")
      protected String hotelCityCode;
      @XmlAttribute(name = "HotelName")
      protected String hotelName;
      @XmlAttribute(name = "HotelCodeContext")
      protected String hotelCodeContext;
      @XmlAttribute(name = "ChainName")
      protected String chainName;
      @XmlAttribute(name = "BrandName")
      protected String brandName;
      @XmlAttribute(name = "AreaID")
      protected String areaID;

      public String getSegmentCategoryCode() {
         return this.segmentCategoryCode;
      }

      public void setSegmentCategoryCode(String value) {
         this.segmentCategoryCode = value;
      }

      public String getPropertyClassCode() {
         return this.propertyClassCode;
      }

      public void setPropertyClassCode(String value) {
         this.propertyClassCode = value;
      }

      public String getArchitecturalStyleCode() {
         return this.architecturalStyleCode;
      }

      public void setArchitecturalStyleCode(String value) {
         this.architecturalStyleCode = value;
      }

      public BigInteger getSupplierIntegrationLevel() {
         return this.supplierIntegrationLevel;
      }

      public void setSupplierIntegrationLevel(BigInteger value) {
         this.supplierIntegrationLevel = value;
      }

      public String getLocationCategoryCode() {
         return this.locationCategoryCode;
      }

      public void setLocationCategoryCode(String value) {
         this.locationCategoryCode = value;
      }

      public Boolean isExtendedCitySearchIndicator() {
         return this.extendedCitySearchIndicator;
      }

      public void setExtendedCitySearchIndicator(Boolean value) {
         this.extendedCitySearchIndicator = value;
      }

      public String getChainCode() {
         return this.chainCode;
      }

      public void setChainCode(String value) {
         this.chainCode = value;
      }

      public String getBrandCode() {
         return this.brandCode;
      }

      public void setBrandCode(String value) {
         this.brandCode = value;
      }

      public String getHotelCode() {
         return this.hotelCode;
      }

      public void setHotelCode(String value) {
         this.hotelCode = value;
      }

      public String getHotelCityCode() {
         return this.hotelCityCode;
      }

      public void setHotelCityCode(String value) {
         this.hotelCityCode = value;
      }

      public String getHotelName() {
         return this.hotelName;
      }

      public void setHotelName(String value) {
         this.hotelName = value;
      }

      public String getHotelCodeContext() {
         return this.hotelCodeContext;
      }

      public void setHotelCodeContext(String value) {
         this.hotelCodeContext = value;
      }

      public String getChainName() {
         return this.chainName;
      }

      public void setChainName(String value) {
         this.chainName = value;
      }

      public String getBrandName() {
         return this.brandName;
      }

      public void setBrandName(String value) {
         this.brandName = value;
      }

      public String getAreaID() {
         return this.areaID;
      }

      public void setAreaID(String value) {
         this.areaID = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class MapArea {
      @XmlAttribute(name = "NorthLatitude")
      protected String northLatitude;
      @XmlAttribute(name = "SouthLatitude")
      protected String southLatitude;
      @XmlAttribute(name = "EastLongitude")
      protected String eastLongitude;
      @XmlAttribute(name = "WestLongitude")
      protected String westLongitude;

      public String getNorthLatitude() {
         return this.northLatitude;
      }

      public void setNorthLatitude(String value) {
         this.northLatitude = value;
      }

      public String getSouthLatitude() {
         return this.southLatitude;
      }

      public void setSouthLatitude(String value) {
         this.southLatitude = value;
      }

      public String getEastLongitude() {
         return this.eastLongitude;
      }

      public void setEastLongitude(String value) {
         this.eastLongitude = value;
      }

      public String getWestLongitude() {
         return this.westLongitude;
      }

      public void setWestLongitude(String value) {
         this.westLongitude = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class Position {
      @XmlAttribute(name = "Latitude")
      protected String latitude;
      @XmlAttribute(name = "Longitude")
      protected String longitude;
      @XmlAttribute(name = "Altitude")
      protected String altitude;
      @XmlAttribute(name = "AltitudeUnitOfMeasureCode")
      protected String altitudeUnitOfMeasureCode;
      @XmlAttribute(name = "PositionAccuracy")
      protected String positionAccuracy;

      public String getLatitude() {
         return this.latitude;
      }

      public void setLatitude(String value) {
         this.latitude = value;
      }

      public String getLongitude() {
         return this.longitude;
      }

      public void setLongitude(String value) {
         this.longitude = value;
      }

      public String getAltitude() {
         return this.altitude;
      }

      public void setAltitude(String value) {
         this.altitude = value;
      }

      public String getAltitudeUnitOfMeasureCode() {
         return this.altitudeUnitOfMeasureCode;
      }

      public void setAltitudeUnitOfMeasureCode(String value) {
         this.altitudeUnitOfMeasureCode = value;
      }

      public String getPositionAccuracy() {
         return this.positionAccuracy;
      }

      public void setPositionAccuracy(String value) {
         this.positionAccuracy = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class Radius {
      @XmlAttribute(name = "Distance")
      protected String distance;
      @XmlAttribute(name = "DistanceMeasure")
      protected String distanceMeasure;
      @XmlAttribute(name = "Direction")
      protected String direction;
      @XmlAttribute(name = "DistanceMax")
      protected String distanceMax;
      @XmlAttribute(name = "UnitOfMeasureCode")
      protected String unitOfMeasureCode;

      public String getDistance() {
         return this.distance;
      }

      public void setDistance(String value) {
         this.distance = value;
      }

      public String getDistanceMeasure() {
         return this.distanceMeasure;
      }

      public void setDistanceMeasure(String value) {
         this.distanceMeasure = value;
      }

      public String getDirection() {
         return this.direction;
      }

      public void setDirection(String value) {
         this.direction = value;
      }

      public String getDistanceMax() {
         return this.distanceMax;
      }

      public void setDistanceMax(String value) {
         this.distanceMax = value;
      }

      public String getUnitOfMeasureCode() {
         return this.unitOfMeasureCode;
      }

      public void setUnitOfMeasureCode(String value) {
         this.unitOfMeasureCode = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "value")
   public static class RefPoint {
      @XmlValue
      protected String value;
      @XmlAttribute(name = "StateProv")
      protected String stateProv;
      @XmlAttribute(name = "CountryCode")
      protected String countryCode;
      @XmlAttribute(name = "RefPointType")
      protected String refPointType;
      @XmlAttribute(name = "Name")
      protected String name;
      @XmlAttribute(name = "CityName")
      protected String cityName;
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

      public String getValue() {
         return this.value;
      }

      public void setValue(String value) {
         this.value = value;
      }

      public String getStateProv() {
         return this.stateProv;
      }

      public void setStateProv(String value) {
         this.stateProv = value;
      }

      public String getCountryCode() {
         return this.countryCode;
      }

      public void setCountryCode(String value) {
         this.countryCode = value;
      }

      public String getRefPointType() {
         return this.refPointType;
      }

      public void setRefPointType(String value) {
         this.refPointType = value;
      }

      public String getName() {
         return this.name;
      }

      public void setName(String value) {
         this.name = value;
      }

      public String getCityName() {
         return this.cityName;
      }

      public void setCityName(String value) {
         this.cityName = value;
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
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class Telephone {
      @XmlAttribute(name = "FormattedInd")
      protected Boolean formattedInd;
      @XmlAttribute(name = "ShareSynchInd")
      @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
      protected String shareSynchInd;
      @XmlAttribute(name = "ShareMarketInd")
      @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
      protected String shareMarketInd;
      @XmlAttribute(name = "PhoneLocationType")
      protected String phoneLocationType;
      @XmlAttribute(name = "PhoneTechType")
      protected String phoneTechType;
      @XmlAttribute(name = "PhoneUseType")
      protected String phoneUseType;
      @XmlAttribute(name = "CountryAccessCode")
      protected String countryAccessCode;
      @XmlAttribute(name = "AreaCityCode")
      protected String areaCityCode;
      @XmlAttribute(name = "PhoneNumber", required = true)
      protected String phoneNumber;
      @XmlAttribute(name = "Extension")
      protected String extension;
      @XmlAttribute(name = "PIN")
      protected String pin;
      @XmlAttribute(name = "Remark")
      protected String remark;

      public Boolean isFormattedInd() {
         return this.formattedInd;
      }

      public void setFormattedInd(Boolean value) {
         this.formattedInd = value;
      }

      public String getShareSynchInd() {
         return this.shareSynchInd;
      }

      public void setShareSynchInd(String value) {
         this.shareSynchInd = value;
      }

      public String getShareMarketInd() {
         return this.shareMarketInd;
      }

      public void setShareMarketInd(String value) {
         this.shareMarketInd = value;
      }

      public String getPhoneLocationType() {
         return this.phoneLocationType;
      }

      public void setPhoneLocationType(String value) {
         this.phoneLocationType = value;
      }

      public String getPhoneTechType() {
         return this.phoneTechType;
      }

      public void setPhoneTechType(String value) {
         this.phoneTechType = value;
      }

      public String getPhoneUseType() {
         return this.phoneUseType;
      }

      public void setPhoneUseType(String value) {
         this.phoneUseType = value;
      }

      public String getCountryAccessCode() {
         return this.countryAccessCode;
      }

      public void setCountryAccessCode(String value) {
         this.countryAccessCode = value;
      }

      public String getAreaCityCode() {
         return this.areaCityCode;
      }

      public void setAreaCityCode(String value) {
         this.areaCityCode = value;
      }

      public String getPhoneNumber() {
         return this.phoneNumber;
      }

      public void setPhoneNumber(String value) {
         this.phoneNumber = value;
      }

      public String getExtension() {
         return this.extension;
      }

      public void setExtension(String value) {
         this.extension = value;
      }

      public String getPIN() {
         return this.pin;
      }

      public void setPIN(String value) {
         this.pin = value;
      }

      public String getRemark() {
         return this.remark;
      }

      public void setRemark(String value) {
         this.remark = value;
      }
   }
}
