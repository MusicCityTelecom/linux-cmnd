package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AddressType", propOrder = {"streetNmbr", "bldgRoom", "addressLine", "cityName", "postalCode", "county", "stateProv", "countryName"})
@XmlSeeAlso(
   {
         DonationType.DonorInfo.ContactInfo.class,
         AirTravelerType.Address.class,
         OffLocationServiceCoreType.Address.class,
         AddressInfoType.class,
         RailPassengerDetailType.Address.class,
         RailPersonInfoType.Address.class,
         ItemSearchCriterionType.Address.class
   }
)
public class AddressType {
   @XmlElement(name = "StreetNmbr")
   protected AddressType.StreetNmbr streetNmbr;
   @XmlElement(name = "BldgRoom")
   protected List<AddressType.BldgRoom> bldgRoom;
   @XmlElement(name = "AddressLine")
   protected List<String> addressLine;
   @XmlElement(name = "CityName")
   protected String cityName;
   @XmlElement(name = "PostalCode")
   protected String postalCode;
   @XmlElement(name = "County")
   protected String county;
   @XmlElement(name = "StateProv")
   protected StateProvType stateProv;
   @XmlElement(name = "CountryName")
   protected CountryNameType countryName;
   @XmlAttribute(name = "Type")
   protected String type;
   @XmlAttribute(name = "Remark")
   protected String remark;
   @XmlAttribute(name = "ShareSynchInd")
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   protected String shareSynchInd;
   @XmlAttribute(name = "ShareMarketInd")
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   protected String shareMarketInd;
   @XmlAttribute(name = "FormattedInd")
   protected Boolean formattedInd;

   public AddressType.StreetNmbr getStreetNmbr() {
      return this.streetNmbr;
   }

   public void setStreetNmbr(AddressType.StreetNmbr value) {
      this.streetNmbr = value;
   }

   public List<AddressType.BldgRoom> getBldgRoom() {
      if (this.bldgRoom == null) {
         this.bldgRoom = new ArrayList<>();
      }

      return this.bldgRoom;
   }

   public List<String> getAddressLine() {
      if (this.addressLine == null) {
         this.addressLine = new ArrayList<>();
      }

      return this.addressLine;
   }

   public String getCityName() {
      return this.cityName;
   }

   public void setCityName(String value) {
      this.cityName = value;
   }

   public String getPostalCode() {
      return this.postalCode;
   }

   public void setPostalCode(String value) {
      this.postalCode = value;
   }

   public String getCounty() {
      return this.county;
   }

   public void setCounty(String value) {
      this.county = value;
   }

   public StateProvType getStateProv() {
      return this.stateProv;
   }

   public void setStateProv(StateProvType value) {
      this.stateProv = value;
   }

   public CountryNameType getCountryName() {
      return this.countryName;
   }

   public void setCountryName(CountryNameType value) {
      this.countryName = value;
   }

   public String getType() {
      return this.type;
   }

   public void setType(String value) {
      this.type = value;
   }

   public String getRemark() {
      return this.remark;
   }

   public void setRemark(String value) {
      this.remark = value;
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

   public Boolean isFormattedInd() {
      return this.formattedInd;
   }

   public void setFormattedInd(Boolean value) {
      this.formattedInd = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "value")
   public static class BldgRoom {
      @XmlValue
      protected String value;
      @XmlAttribute(name = "BldgNameIndicator")
      protected Boolean bldgNameIndicator;

      public String getValue() {
         return this.value;
      }

      public void setValue(String value) {
         this.value = value;
      }

      public Boolean isBldgNameIndicator() {
         return this.bldgNameIndicator;
      }

      public void setBldgNameIndicator(Boolean value) {
         this.bldgNameIndicator = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class StreetNmbr extends StreetNmbrType {
      @XmlAttribute(name = "StreetNmbrSuffix")
      protected String streetNmbrSuffix;
      @XmlAttribute(name = "StreetDirection")
      protected String streetDirection;
      @XmlAttribute(name = "RuralRouteNmbr")
      protected String ruralRouteNmbr;

      public String getStreetNmbrSuffix() {
         return this.streetNmbrSuffix;
      }

      public void setStreetNmbrSuffix(String value) {
         this.streetNmbrSuffix = value;
      }

      public String getStreetDirection() {
         return this.streetDirection;
      }

      public void setStreetDirection(String value) {
         this.streetDirection = value;
      }

      public String getRuralRouteNmbr() {
         return this.ruralRouteNmbr;
      }

      public void setRuralRouteNmbr(String value) {
         this.ruralRouteNmbr = value;
      }
   }
}
