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
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ViewershipsType", propOrder = "viewership")
public class ViewershipsType {
   @XmlElement(name = "Viewership", required = true)
   protected List<ViewershipsType.Viewership> viewership;

   public List<ViewershipsType.Viewership> getViewership() {
      if (this.viewership == null) {
         this.viewership = new ArrayList<>();
      }

      return this.viewership;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(
      name = "",
      propOrder = {"viewershipCodes", "systemCodes", "profileTypes", "profileRefs", "profiles", "locationCodes", "bookingChannelCodes", "distributorTypes"}
   )
   public static class Viewership {
      @XmlElement(name = "ViewershipCodes")
      protected ViewershipsType.Viewership.ViewershipCodes viewershipCodes;
      @XmlElement(name = "SystemCodes")
      protected ViewershipsType.Viewership.SystemCodes systemCodes;
      @XmlElement(name = "ProfileTypes")
      protected ViewershipsType.Viewership.ProfileTypes profileTypes;
      @XmlElement(name = "ProfileRefs")
      protected ViewershipsType.Viewership.ProfileRefs profileRefs;
      @XmlElement(name = "Profiles")
      protected ViewershipsType.Viewership.Profiles profiles;
      @XmlElement(name = "LocationCodes")
      protected ViewershipsType.Viewership.LocationCodes locationCodes;
      @XmlElement(name = "BookingChannelCodes")
      protected ViewershipsType.Viewership.BookingChannelCodes bookingChannelCodes;
      @XmlElement(name = "DistributorTypes")
      protected ViewershipsType.Viewership.DistributorTypes distributorTypes;
      @XmlAttribute(name = "ViewershipRPH")
      protected String viewershipRPH;
      @XmlAttribute(name = "ViewOnly")
      protected Boolean viewOnly;

      public ViewershipsType.Viewership.ViewershipCodes getViewershipCodes() {
         return this.viewershipCodes;
      }

      public void setViewershipCodes(ViewershipsType.Viewership.ViewershipCodes value) {
         this.viewershipCodes = value;
      }

      public ViewershipsType.Viewership.SystemCodes getSystemCodes() {
         return this.systemCodes;
      }

      public void setSystemCodes(ViewershipsType.Viewership.SystemCodes value) {
         this.systemCodes = value;
      }

      public ViewershipsType.Viewership.ProfileTypes getProfileTypes() {
         return this.profileTypes;
      }

      public void setProfileTypes(ViewershipsType.Viewership.ProfileTypes value) {
         this.profileTypes = value;
      }

      public ViewershipsType.Viewership.ProfileRefs getProfileRefs() {
         return this.profileRefs;
      }

      public void setProfileRefs(ViewershipsType.Viewership.ProfileRefs value) {
         this.profileRefs = value;
      }

      public ViewershipsType.Viewership.Profiles getProfiles() {
         return this.profiles;
      }

      public void setProfiles(ViewershipsType.Viewership.Profiles value) {
         this.profiles = value;
      }

      public ViewershipsType.Viewership.LocationCodes getLocationCodes() {
         return this.locationCodes;
      }

      public void setLocationCodes(ViewershipsType.Viewership.LocationCodes value) {
         this.locationCodes = value;
      }

      public ViewershipsType.Viewership.BookingChannelCodes getBookingChannelCodes() {
         return this.bookingChannelCodes;
      }

      public void setBookingChannelCodes(ViewershipsType.Viewership.BookingChannelCodes value) {
         this.bookingChannelCodes = value;
      }

      public ViewershipsType.Viewership.DistributorTypes getDistributorTypes() {
         return this.distributorTypes;
      }

      public void setDistributorTypes(ViewershipsType.Viewership.DistributorTypes value) {
         this.distributorTypes = value;
      }

      public String getViewershipRPH() {
         return this.viewershipRPH;
      }

      public void setViewershipRPH(String value) {
         this.viewershipRPH = value;
      }

      public Boolean isViewOnly() {
         return this.viewOnly;
      }

      public void setViewOnly(Boolean value) {
         this.viewOnly = value;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = "bookingChannelCode")
      public static class BookingChannelCodes {
         @XmlElement(name = "BookingChannelCode", required = true)
         protected List<ViewershipsType.Viewership.BookingChannelCodes.BookingChannelCode> bookingChannelCode;
         @XmlAttribute(name = "ChannelCodesInclusive")
         protected Boolean channelCodesInclusive;

         public List<ViewershipsType.Viewership.BookingChannelCodes.BookingChannelCode> getBookingChannelCode() {
            if (this.bookingChannelCode == null) {
               this.bookingChannelCode = new ArrayList<>();
            }

            return this.bookingChannelCode;
         }

         public Boolean isChannelCodesInclusive() {
            return this.channelCodesInclusive;
         }

         public void setChannelCodesInclusive(Boolean value) {
            this.channelCodesInclusive = value;
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "", propOrder = "value")
         public static class BookingChannelCode {
            @XmlValue
            protected String value;
            @XmlAttribute(name = "RestrictedDisplayIndicator")
            protected Boolean restrictedDisplayIndicator;
            @XmlAttribute(name = "Sort")
            @XmlSchemaType(name = "nonNegativeInteger")
            protected BigInteger sort;

            public String getValue() {
               return this.value;
            }

            public void setValue(String value) {
               this.value = value;
            }

            public Boolean isRestrictedDisplayIndicator() {
               return this.restrictedDisplayIndicator;
            }

            public void setRestrictedDisplayIndicator(Boolean value) {
               this.restrictedDisplayIndicator = value;
            }

            public BigInteger getSort() {
               return this.sort;
            }

            public void setSort(BigInteger value) {
               this.sort = value;
            }
         }
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = "distributorType")
      public static class DistributorTypes {
         @XmlElement(name = "DistributorType", required = true)
         protected List<ViewershipsType.Viewership.DistributorTypes.DistributorType> distributorType;

         public List<ViewershipsType.Viewership.DistributorTypes.DistributorType> getDistributorType() {
            if (this.distributorType == null) {
               this.distributorType = new ArrayList<>();
            }

            return this.distributorType;
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "", propOrder = "value")
         public static class DistributorType {
            @XmlValue
            protected String value;
            @XmlAttribute(name = "DistributorCode")
            protected String distributorCode;
            @XmlAttribute(name = "DistributorTypeCode")
            protected String distributorTypeCode;

            public String getValue() {
               return this.value;
            }

            public void setValue(String value) {
               this.value = value;
            }

            public String getDistributorCode() {
               return this.distributorCode;
            }

            public void setDistributorCode(String value) {
               this.distributorCode = value;
            }

            public String getDistributorTypeCode() {
               return this.distributorTypeCode;
            }

            public void setDistributorTypeCode(String value) {
               this.distributorTypeCode = value;
            }
         }
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = "locationCode")
      public static class LocationCodes {
         @XmlElement(name = "LocationCode", required = true)
         protected List<ViewershipsType.Viewership.LocationCodes.LocationCode> locationCode;
         @XmlAttribute(name = "LocationCodesInclusive")
         protected Boolean locationCodesInclusive;

         public List<ViewershipsType.Viewership.LocationCodes.LocationCode> getLocationCode() {
            if (this.locationCode == null) {
               this.locationCode = new ArrayList<>();
            }

            return this.locationCode;
         }

         public Boolean isLocationCodesInclusive() {
            return this.locationCodesInclusive;
         }

         public void setLocationCodesInclusive(Boolean value) {
            this.locationCodesInclusive = value;
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "")
         public static class LocationCode {
            @XmlAttribute(name = "CityCode")
            protected String cityCode;
            @XmlAttribute(name = "StateProvinceCode")
            protected String stateProvinceCode;
            @XmlAttribute(name = "CountryCode")
            protected String countryCode;

            public String getCityCode() {
               return this.cityCode;
            }

            public void setCityCode(String value) {
               this.cityCode = value;
            }

            public String getStateProvinceCode() {
               return this.stateProvinceCode;
            }

            public void setStateProvinceCode(String value) {
               this.stateProvinceCode = value;
            }

            public String getCountryCode() {
               return this.countryCode;
            }

            public void setCountryCode(String value) {
               this.countryCode = value;
            }
         }
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = "profileRef")
      public static class ProfileRefs {
         @XmlElement(name = "ProfileRef", required = true)
         protected List<UniqueIDType> profileRef;

         public List<UniqueIDType> getProfileRef() {
            if (this.profileRef == null) {
               this.profileRef = new ArrayList<>();
            }

            return this.profileRef;
         }
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = "profileType")
      public static class ProfileTypes {
         @XmlElement(name = "ProfileType", required = true)
         protected List<ViewershipsType.Viewership.ProfileTypes.ProfileType> profileType;

         public List<ViewershipsType.Viewership.ProfileTypes.ProfileType> getProfileType() {
            if (this.profileType == null) {
               this.profileType = new ArrayList<>();
            }

            return this.profileType;
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "")
         public static class ProfileType {
            @XmlAttribute(name = "ProfileType")
            protected String profileType;

            public String getProfileType() {
               return this.profileType;
            }

            public void setProfileType(String value) {
               this.profileType = value;
            }
         }
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = "profile")
      public static class Profiles {
         @XmlElement(name = "Profile", required = true)
         protected List<ProfileType> profile;

         public List<ProfileType> getProfile() {
            if (this.profile == null) {
               this.profile = new ArrayList<>();
            }

            return this.profile;
         }
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = "systemCode")
      public static class SystemCodes {
         @XmlElement(name = "SystemCode", required = true)
         protected List<ViewershipsType.Viewership.SystemCodes.SystemCode> systemCode;
         @XmlAttribute(name = "SystemCodesInclusive")
         protected Boolean systemCodesInclusive;

         public List<ViewershipsType.Viewership.SystemCodes.SystemCode> getSystemCode() {
            if (this.systemCode == null) {
               this.systemCode = new ArrayList<>();
            }

            return this.systemCode;
         }

         public Boolean isSystemCodesInclusive() {
            return this.systemCodesInclusive;
         }

         public void setSystemCodesInclusive(Boolean value) {
            this.systemCodesInclusive = value;
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "", propOrder = "value")
         public static class SystemCode {
            @XmlValue
            protected String value;
            @XmlAttribute(name = "RestrictedDisplayIndicator")
            protected Boolean restrictedDisplayIndicator;
            @XmlAttribute(name = "Sort")
            @XmlSchemaType(name = "nonNegativeInteger")
            protected BigInteger sort;

            public String getValue() {
               return this.value;
            }

            public void setValue(String value) {
               this.value = value;
            }

            public Boolean isRestrictedDisplayIndicator() {
               return this.restrictedDisplayIndicator;
            }

            public void setRestrictedDisplayIndicator(Boolean value) {
               this.restrictedDisplayIndicator = value;
            }

            public BigInteger getSort() {
               return this.sort;
            }

            public void setSort(BigInteger value) {
               this.sort = value;
            }
         }
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = "viewershipCode")
      public static class ViewershipCodes {
         @XmlElement(name = "ViewershipCode", required = true)
         protected String viewershipCode;

         public String getViewershipCode() {
            return this.viewershipCode;
         }

         public void setViewershipCode(String value) {
            this.viewershipCode = value;
         }
      }
   }
}
