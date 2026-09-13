package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VehicleLocationDetailsType", propOrder = {"address", "telephone", "additionalInfo"})
public class VehicleLocationDetailsType {
   @XmlElement(name = "Address")
   protected List<AddressInfoType> address;
   @XmlElement(name = "Telephone")
   protected List<VehicleLocationDetailsType.Telephone> telephone;
   @XmlElement(name = "AdditionalInfo")
   protected VehicleLocationAdditionalDetailsType additionalInfo;
   @XmlAttribute(name = "AtAirport")
   protected Boolean atAirport;
   @XmlAttribute(name = "Code")
   protected String code;
   @XmlAttribute(name = "Name")
   protected String name;
   @XmlAttribute(name = "CodeContext")
   protected String codeContext;
   @XmlAttribute(name = "ExtendedLocationCode")
   protected String extendedLocationCode;
   @XmlAttribute(name = "AssocAirportLocList")
   protected List<String> assocAirportLocList;

   public List<AddressInfoType> getAddress() {
      if (this.address == null) {
         this.address = new ArrayList<>();
      }

      return this.address;
   }

   public List<VehicleLocationDetailsType.Telephone> getTelephone() {
      if (this.telephone == null) {
         this.telephone = new ArrayList<>();
      }

      return this.telephone;
   }

   public VehicleLocationAdditionalDetailsType getAdditionalInfo() {
      return this.additionalInfo;
   }

   public void setAdditionalInfo(VehicleLocationAdditionalDetailsType value) {
      this.additionalInfo = value;
   }

   public Boolean isAtAirport() {
      return this.atAirport;
   }

   public void setAtAirport(Boolean value) {
      this.atAirport = value;
   }

   public String getCode() {
      return this.code;
   }

   public void setCode(String value) {
      this.code = value;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String value) {
      this.name = value;
   }

   public String getCodeContext() {
      return this.codeContext;
   }

   public void setCodeContext(String value) {
      this.codeContext = value;
   }

   public String getExtendedLocationCode() {
      return this.extendedLocationCode;
   }

   public void setExtendedLocationCode(String value) {
      this.extendedLocationCode = value;
   }

   public List<String> getAssocAirportLocList() {
      if (this.assocAirportLocList == null) {
         this.assocAirportLocList = new ArrayList<>();
      }

      return this.assocAirportLocList;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class Telephone {
      @XmlAttribute(name = "RPH")
      protected String rph;
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
      @XmlAttribute(name = "DefaultInd")
      protected Boolean defaultInd;

      public String getRPH() {
         return this.rph;
      }

      public void setRPH(String value) {
         this.rph = value;
      }

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

      public Boolean isDefaultInd() {
         return this.defaultInd;
      }

      public void setDefaultInd(Boolean value) {
         this.defaultInd = value;
      }
   }
}
