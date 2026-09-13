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
@XmlType(
   name = "HotelPrefType",
   propOrder = {
         "loyaltyPref",
         "paymentFormPref",
         "hotelChainPref",
         "propertyNamePref",
         "propertyLocationPref",
         "propertyTypePref",
         "propertyClassPref",
         "propertyAmenityPref",
         "roomAmenityPref",
         "roomLocationPref",
         "bedTypePref",
         "foodSrvcPref",
         "mediaEntertainPref",
         "petInfoPref",
         "mealPref",
         "recreationSrvcPref",
         "businessSrvcPref",
         "personalSrvcPref",
         "securityFeaturePref",
         "physChallFeaturePref",
         "specRequestPref",
         "tpaExtensions"
   }
)
public class HotelPrefType {
   @XmlElement(name = "LoyaltyPref")
   protected List<LoyaltyPrefType> loyaltyPref;
   @XmlElement(name = "PaymentFormPref")
   protected List<PaymentFormPrefType> paymentFormPref;
   @XmlElement(name = "HotelChainPref")
   protected List<CompanyNamePrefType> hotelChainPref;
   @XmlElement(name = "PropertyNamePref")
   protected List<PropertyNamePrefType> propertyNamePref;
   @XmlElement(name = "PropertyLocationPref")
   protected List<PropertyLocationPrefType> propertyLocationPref;
   @XmlElement(name = "PropertyTypePref")
   protected List<PropertyTypePrefType> propertyTypePref;
   @XmlElement(name = "PropertyClassPref")
   protected List<PropertyClassPrefType> propertyClassPref;
   @XmlElement(name = "PropertyAmenityPref")
   protected List<PropertyAmenityPrefType> propertyAmenityPref;
   @XmlElement(name = "RoomAmenityPref")
   protected List<RoomAmenityPrefType> roomAmenityPref;
   @XmlElement(name = "RoomLocationPref")
   protected List<RoomLocationPrefType> roomLocationPref;
   @XmlElement(name = "BedTypePref")
   protected List<BedTypePrefType> bedTypePref;
   @XmlElement(name = "FoodSrvcPref")
   protected List<FoodSrvcPrefType> foodSrvcPref;
   @XmlElement(name = "MediaEntertainPref")
   protected List<MediaEntertainPrefType> mediaEntertainPref;
   @XmlElement(name = "PetInfoPref")
   protected List<PetInfoPrefType> petInfoPref;
   @XmlElement(name = "MealPref")
   protected List<MealPrefType> mealPref;
   @XmlElement(name = "RecreationSrvcPref")
   protected List<RecreationSrvcPrefType> recreationSrvcPref;
   @XmlElement(name = "BusinessSrvcPref")
   protected List<BusinessSrvcPrefType> businessSrvcPref;
   @XmlElement(name = "PersonalSrvcPref")
   protected List<PersonalSrvcPrefType> personalSrvcPref;
   @XmlElement(name = "SecurityFeaturePref")
   protected List<SecurityFeaturePrefType> securityFeaturePref;
   @XmlElement(name = "PhysChallFeaturePref")
   protected List<PhysChallFeaturePrefType> physChallFeaturePref;
   @XmlElement(name = "SpecRequestPref")
   protected List<SpecRequestPrefType> specRequestPref;
   @XmlElement(name = "TPA_Extensions")
   protected TPAExtensionsType tpaExtensions;
   @XmlAttribute(name = "RatePlanCode")
   protected String ratePlanCode;
   @XmlAttribute(name = "HotelGuestType")
   protected String hotelGuestType;
   @XmlAttribute(name = "PreferLevel")
   protected PreferLevelType preferLevel;
   @XmlAttribute(name = "ShareSynchInd")
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   protected String shareSynchInd;
   @XmlAttribute(name = "ShareMarketInd")
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   protected String shareMarketInd;
   @XmlAttribute(name = "SmokingAllowed")
   protected Boolean smokingAllowed;

   public List<LoyaltyPrefType> getLoyaltyPref() {
      if (this.loyaltyPref == null) {
         this.loyaltyPref = new ArrayList<>();
      }

      return this.loyaltyPref;
   }

   public List<PaymentFormPrefType> getPaymentFormPref() {
      if (this.paymentFormPref == null) {
         this.paymentFormPref = new ArrayList<>();
      }

      return this.paymentFormPref;
   }

   public List<CompanyNamePrefType> getHotelChainPref() {
      if (this.hotelChainPref == null) {
         this.hotelChainPref = new ArrayList<>();
      }

      return this.hotelChainPref;
   }

   public List<PropertyNamePrefType> getPropertyNamePref() {
      if (this.propertyNamePref == null) {
         this.propertyNamePref = new ArrayList<>();
      }

      return this.propertyNamePref;
   }

   public List<PropertyLocationPrefType> getPropertyLocationPref() {
      if (this.propertyLocationPref == null) {
         this.propertyLocationPref = new ArrayList<>();
      }

      return this.propertyLocationPref;
   }

   public List<PropertyTypePrefType> getPropertyTypePref() {
      if (this.propertyTypePref == null) {
         this.propertyTypePref = new ArrayList<>();
      }

      return this.propertyTypePref;
   }

   public List<PropertyClassPrefType> getPropertyClassPref() {
      if (this.propertyClassPref == null) {
         this.propertyClassPref = new ArrayList<>();
      }

      return this.propertyClassPref;
   }

   public List<PropertyAmenityPrefType> getPropertyAmenityPref() {
      if (this.propertyAmenityPref == null) {
         this.propertyAmenityPref = new ArrayList<>();
      }

      return this.propertyAmenityPref;
   }

   public List<RoomAmenityPrefType> getRoomAmenityPref() {
      if (this.roomAmenityPref == null) {
         this.roomAmenityPref = new ArrayList<>();
      }

      return this.roomAmenityPref;
   }

   public List<RoomLocationPrefType> getRoomLocationPref() {
      if (this.roomLocationPref == null) {
         this.roomLocationPref = new ArrayList<>();
      }

      return this.roomLocationPref;
   }

   public List<BedTypePrefType> getBedTypePref() {
      if (this.bedTypePref == null) {
         this.bedTypePref = new ArrayList<>();
      }

      return this.bedTypePref;
   }

   public List<FoodSrvcPrefType> getFoodSrvcPref() {
      if (this.foodSrvcPref == null) {
         this.foodSrvcPref = new ArrayList<>();
      }

      return this.foodSrvcPref;
   }

   public List<MediaEntertainPrefType> getMediaEntertainPref() {
      if (this.mediaEntertainPref == null) {
         this.mediaEntertainPref = new ArrayList<>();
      }

      return this.mediaEntertainPref;
   }

   public List<PetInfoPrefType> getPetInfoPref() {
      if (this.petInfoPref == null) {
         this.petInfoPref = new ArrayList<>();
      }

      return this.petInfoPref;
   }

   public List<MealPrefType> getMealPref() {
      if (this.mealPref == null) {
         this.mealPref = new ArrayList<>();
      }

      return this.mealPref;
   }

   public List<RecreationSrvcPrefType> getRecreationSrvcPref() {
      if (this.recreationSrvcPref == null) {
         this.recreationSrvcPref = new ArrayList<>();
      }

      return this.recreationSrvcPref;
   }

   public List<BusinessSrvcPrefType> getBusinessSrvcPref() {
      if (this.businessSrvcPref == null) {
         this.businessSrvcPref = new ArrayList<>();
      }

      return this.businessSrvcPref;
   }

   public List<PersonalSrvcPrefType> getPersonalSrvcPref() {
      if (this.personalSrvcPref == null) {
         this.personalSrvcPref = new ArrayList<>();
      }

      return this.personalSrvcPref;
   }

   public List<SecurityFeaturePrefType> getSecurityFeaturePref() {
      if (this.securityFeaturePref == null) {
         this.securityFeaturePref = new ArrayList<>();
      }

      return this.securityFeaturePref;
   }

   public List<PhysChallFeaturePrefType> getPhysChallFeaturePref() {
      if (this.physChallFeaturePref == null) {
         this.physChallFeaturePref = new ArrayList<>();
      }

      return this.physChallFeaturePref;
   }

   public List<SpecRequestPrefType> getSpecRequestPref() {
      if (this.specRequestPref == null) {
         this.specRequestPref = new ArrayList<>();
      }

      return this.specRequestPref;
   }

   public TPAExtensionsType getTPAExtensions() {
      return this.tpaExtensions;
   }

   public void setTPAExtensions(TPAExtensionsType value) {
      this.tpaExtensions = value;
   }

   public String getRatePlanCode() {
      return this.ratePlanCode;
   }

   public void setRatePlanCode(String value) {
      this.ratePlanCode = value;
   }

   public String getHotelGuestType() {
      return this.hotelGuestType;
   }

   public void setHotelGuestType(String value) {
      this.hotelGuestType = value;
   }

   public PreferLevelType getPreferLevel() {
      return this.preferLevel;
   }

   public void setPreferLevel(PreferLevelType value) {
      this.preferLevel = value;
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

   public Boolean isSmokingAllowed() {
      return this.smokingAllowed;
   }

   public void setSmokingAllowed(Boolean value) {
      this.smokingAllowed = value;
   }
}
