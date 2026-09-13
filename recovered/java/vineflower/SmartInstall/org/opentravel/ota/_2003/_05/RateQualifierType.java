package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RateQualifierType", propOrder = {"promoDesc", "rateComments"})
@XmlSeeAlso(VehicleRentalRateType.RateQualifier.class)
public class RateQualifierType {
   @XmlElement(name = "PromoDesc")
   protected String promoDesc;
   @XmlElement(name = "RateComments")
   protected RateQualifierType.RateComments rateComments;
   @XmlAttribute(name = "ArriveByFlight")
   protected Boolean arriveByFlight;
   @XmlAttribute(name = "RateAuthorizationCode")
   protected String rateAuthorizationCode;
   @XmlAttribute(name = "VendorRateID")
   protected String vendorRateID;
   @XmlAttribute(name = "TravelPurpose")
   protected String travelPurpose;
   @XmlAttribute(name = "RateCategory")
   protected String rateCategory;
   @XmlAttribute(name = "CorpDiscountNmbr")
   protected String corpDiscountNmbr;
   @XmlAttribute(name = "RateQualifier")
   protected String rateQualifier;
   @XmlAttribute(name = "RatePeriod")
   protected RatePeriodSimpleType ratePeriod;
   @XmlAttribute(name = "GuaranteedInd")
   protected Boolean guaranteedInd;
   @XmlAttribute(name = "PromotionCode")
   protected String promotionCode;
   @XmlAttribute(name = "PromotionVendorCode")
   protected List<String> promotionVendorCode;

   public String getPromoDesc() {
      return this.promoDesc;
   }

   public void setPromoDesc(String value) {
      this.promoDesc = value;
   }

   public RateQualifierType.RateComments getRateComments() {
      return this.rateComments;
   }

   public void setRateComments(RateQualifierType.RateComments value) {
      this.rateComments = value;
   }

   public Boolean isArriveByFlight() {
      return this.arriveByFlight;
   }

   public void setArriveByFlight(Boolean value) {
      this.arriveByFlight = value;
   }

   public String getRateAuthorizationCode() {
      return this.rateAuthorizationCode;
   }

   public void setRateAuthorizationCode(String value) {
      this.rateAuthorizationCode = value;
   }

   public String getVendorRateID() {
      return this.vendorRateID;
   }

   public void setVendorRateID(String value) {
      this.vendorRateID = value;
   }

   public String getTravelPurpose() {
      return this.travelPurpose;
   }

   public void setTravelPurpose(String value) {
      this.travelPurpose = value;
   }

   public String getRateCategory() {
      return this.rateCategory;
   }

   public void setRateCategory(String value) {
      this.rateCategory = value;
   }

   public String getCorpDiscountNmbr() {
      return this.corpDiscountNmbr;
   }

   public void setCorpDiscountNmbr(String value) {
      this.corpDiscountNmbr = value;
   }

   public String getRateQualifier() {
      return this.rateQualifier;
   }

   public void setRateQualifier(String value) {
      this.rateQualifier = value;
   }

   public RatePeriodSimpleType getRatePeriod() {
      return this.ratePeriod;
   }

   public void setRatePeriod(RatePeriodSimpleType value) {
      this.ratePeriod = value;
   }

   public Boolean isGuaranteedInd() {
      return this.guaranteedInd;
   }

   public void setGuaranteedInd(Boolean value) {
      this.guaranteedInd = value;
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

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "rateComment")
   public static class RateComments {
      @XmlElement(name = "RateComment", required = true)
      protected List<RateQualifierType.RateComments.RateComment> rateComment;

      public List<RateQualifierType.RateComments.RateComment> getRateComment() {
         if (this.rateComment == null) {
            this.rateComment = new ArrayList<>();
         }

         return this.rateComment;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class RateComment extends FormattedTextTextType {
         @XmlAttribute(name = "Name")
         protected String name;

         public String getName() {
            return this.name;
         }

         public void setName(String value) {
            this.name = value;
         }
      }
   }
}
