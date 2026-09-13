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
@XmlType(name = "PreferencesType", propOrder = "prefCollection")
public class PreferencesType {
   @XmlElement(name = "PrefCollection", required = true)
   protected List<PreferencesType.PrefCollection> prefCollection;
   @XmlAttribute(name = "ShareSynchInd")
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   protected String shareSynchInd;
   @XmlAttribute(name = "ShareMarketInd")
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   protected String shareMarketInd;

   public List<PreferencesType.PrefCollection> getPrefCollection() {
      if (this.prefCollection == null) {
         this.prefCollection = new ArrayList<>();
      }

      return this.prefCollection;
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

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = {"commonPref", "vehicleRentalPref", "airlinePref", "hotelPref", "otherSrvcPref", "tpaExtensions"})
   public static class PrefCollection {
      @XmlElement(name = "CommonPref")
      protected List<CommonPrefType> commonPref;
      @XmlElement(name = "VehicleRentalPref")
      protected List<VehicleProfileRentalPrefType> vehicleRentalPref;
      @XmlElement(name = "AirlinePref")
      protected List<AirlinePrefType> airlinePref;
      @XmlElement(name = "HotelPref")
      protected List<HotelPrefType> hotelPref;
      @XmlElement(name = "OtherSrvcPref")
      protected List<OtherSrvcPrefType> otherSrvcPref;
      @XmlElement(name = "TPA_Extensions")
      protected TPAExtensionsType tpaExtensions;
      @XmlAttribute(name = "TravelPurpose")
      protected String travelPurpose;
      @XmlAttribute(name = "ShareSynchInd")
      @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
      protected String shareSynchInd;
      @XmlAttribute(name = "ShareMarketInd")
      @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
      protected String shareMarketInd;

      public List<CommonPrefType> getCommonPref() {
         if (this.commonPref == null) {
            this.commonPref = new ArrayList<>();
         }

         return this.commonPref;
      }

      public List<VehicleProfileRentalPrefType> getVehicleRentalPref() {
         if (this.vehicleRentalPref == null) {
            this.vehicleRentalPref = new ArrayList<>();
         }

         return this.vehicleRentalPref;
      }

      public List<AirlinePrefType> getAirlinePref() {
         if (this.airlinePref == null) {
            this.airlinePref = new ArrayList<>();
         }

         return this.airlinePref;
      }

      public List<HotelPrefType> getHotelPref() {
         if (this.hotelPref == null) {
            this.hotelPref = new ArrayList<>();
         }

         return this.hotelPref;
      }

      public List<OtherSrvcPrefType> getOtherSrvcPref() {
         if (this.otherSrvcPref == null) {
            this.otherSrvcPref = new ArrayList<>();
         }

         return this.otherSrvcPref;
      }

      public TPAExtensionsType getTPAExtensions() {
         return this.tpaExtensions;
      }

      public void setTPAExtensions(TPAExtensionsType value) {
         this.tpaExtensions = value;
      }

      public String getTravelPurpose() {
         return this.travelPurpose;
      }

      public void setTravelPurpose(String value) {
         this.travelPurpose = value;
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
   }
}
