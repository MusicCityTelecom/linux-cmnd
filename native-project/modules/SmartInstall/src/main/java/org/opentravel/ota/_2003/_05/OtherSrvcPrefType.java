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
@XmlType(name = "OtherSrvcPrefType", propOrder = {"otherSrvcName", "vendorPref", "loyaltyPref", "paymentFormPref", "specRequestPref", "tpaExtensions"})
public class OtherSrvcPrefType {
   @XmlElement(name = "OtherSrvcName", required = true)
   protected String otherSrvcName;
   @XmlElement(name = "VendorPref")
   protected List<CompanyNamePrefType> vendorPref;
   @XmlElement(name = "LoyaltyPref")
   protected List<LoyaltyPrefType> loyaltyPref;
   @XmlElement(name = "PaymentFormPref")
   protected List<PaymentFormPrefType> paymentFormPref;
   @XmlElement(name = "SpecRequestPref")
   protected List<SpecRequestPrefType> specRequestPref;
   @XmlElement(name = "TPA_Extensions")
   protected TPAExtensionsType tpaExtensions;
   @XmlAttribute(name = "TravelPurpose")
   protected String travelPurpose;
   @XmlAttribute(name = "PreferLevel")
   protected PreferLevelType preferLevel;
   @XmlAttribute(name = "ShareSynchInd")
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   protected String shareSynchInd;
   @XmlAttribute(name = "ShareMarketInd")
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   protected String shareMarketInd;

   public String getOtherSrvcName() {
      return this.otherSrvcName;
   }

   public void setOtherSrvcName(String value) {
      this.otherSrvcName = value;
   }

   public List<CompanyNamePrefType> getVendorPref() {
      if (this.vendorPref == null) {
         this.vendorPref = new ArrayList<>();
      }

      return this.vendorPref;
   }

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

   public String getTravelPurpose() {
      return this.travelPurpose;
   }

   public void setTravelPurpose(String value) {
      this.travelPurpose = value;
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
}
