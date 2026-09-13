package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "VehicleRetrieveResRQAdditionalInfoType",
   propOrder = {"pickUpLocation", "returnLocation", "telephone", "vendor", "vehPref", "email", "remark", "searchDateRange", "tpaExtensions"}
)
@XmlSeeAlso(OTAVehRetResRQ.VehRetResRQInfo.class)
public class VehicleRetrieveResRQAdditionalInfoType {
   @XmlElement(name = "PickUpLocation")
   protected LocationType pickUpLocation;
   @XmlElement(name = "ReturnLocation")
   protected LocationType returnLocation;
   @XmlElement(name = "Telephone")
   protected VehicleRetrieveResRQAdditionalInfoType.Telephone telephone;
   @XmlElement(name = "Vendor")
   protected CompanyNameType vendor;
   @XmlElement(name = "VehPref")
   protected VehiclePrefType vehPref;
   @XmlElement(name = "Email")
   protected EmailType email;
   @XmlElement(name = "Remark")
   protected List<ParagraphType> remark;
   @XmlElement(name = "SearchDateRange")
   protected List<VehicleRetrieveResRQAdditionalInfoType.SearchDateRange> searchDateRange;
   @XmlElement(name = "TPA_Extensions")
   protected TPAExtensionsType tpaExtensions;
   @XmlAttribute(name = "PickUpDateTime")
   @XmlSchemaType(name = "dateTime")
   protected XMLGregorianCalendar pickUpDateTime;

   public LocationType getPickUpLocation() {
      return this.pickUpLocation;
   }

   public void setPickUpLocation(LocationType value) {
      this.pickUpLocation = value;
   }

   public LocationType getReturnLocation() {
      return this.returnLocation;
   }

   public void setReturnLocation(LocationType value) {
      this.returnLocation = value;
   }

   public VehicleRetrieveResRQAdditionalInfoType.Telephone getTelephone() {
      return this.telephone;
   }

   public void setTelephone(VehicleRetrieveResRQAdditionalInfoType.Telephone value) {
      this.telephone = value;
   }

   public CompanyNameType getVendor() {
      return this.vendor;
   }

   public void setVendor(CompanyNameType value) {
      this.vendor = value;
   }

   public VehiclePrefType getVehPref() {
      return this.vehPref;
   }

   public void setVehPref(VehiclePrefType value) {
      this.vehPref = value;
   }

   public EmailType getEmail() {
      return this.email;
   }

   public void setEmail(EmailType value) {
      this.email = value;
   }

   public List<ParagraphType> getRemark() {
      if (this.remark == null) {
         this.remark = new ArrayList<>();
      }

      return this.remark;
   }

   public List<VehicleRetrieveResRQAdditionalInfoType.SearchDateRange> getSearchDateRange() {
      if (this.searchDateRange == null) {
         this.searchDateRange = new ArrayList<>();
      }

      return this.searchDateRange;
   }

   public TPAExtensionsType getTPAExtensions() {
      return this.tpaExtensions;
   }

   public void setTPAExtensions(TPAExtensionsType value) {
      this.tpaExtensions = value;
   }

   public XMLGregorianCalendar getPickUpDateTime() {
      return this.pickUpDateTime;
   }

   public void setPickUpDateTime(XMLGregorianCalendar value) {
      this.pickUpDateTime = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class SearchDateRange {
      @XmlAttribute(name = "SearchQualifier")
      @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
      protected String searchQualifier;
      @XmlAttribute(name = "Start")
      protected String start;
      @XmlAttribute(name = "Duration")
      protected String duration;
      @XmlAttribute(name = "End")
      protected String end;

      public String getSearchQualifier() {
         return this.searchQualifier;
      }

      public void setSearchQualifier(String value) {
         this.searchQualifier = value;
      }

      public String getStart() {
         return this.start;
      }

      public void setStart(String value) {
         this.start = value;
      }

      public String getDuration() {
         return this.duration;
      }

      public void setDuration(String value) {
         this.duration = value;
      }

      public String getEnd() {
         return this.end;
      }

      public void setEnd(String value) {
         this.end = value;
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
