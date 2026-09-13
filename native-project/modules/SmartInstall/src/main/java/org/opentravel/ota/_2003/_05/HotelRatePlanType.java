package org.opentravel.ota._2003._05;

import java.math.BigDecimal;
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
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "HotelRatePlanType",
   propOrder = {
         "destinationSystemsCode",
         "bookingRules",
         "rates",
         "supplements",
         "offers",
         "ratePlanShoulders",
         "sellableProducts",
         "ratePlanLevelFee",
         "commission",
         "description",
         "uniqueID",
         "hotelRef"
   }
)
public class HotelRatePlanType {
   @XmlElement(name = "DestinationSystemsCode")
   protected HotelRatePlanType.DestinationSystemsCode destinationSystemsCode;
   @XmlElement(name = "BookingRules")
   protected HotelRatePlanType.BookingRules bookingRules;
   @XmlElement(name = "Rates")
   protected HotelRatePlanType.Rates rates;
   @XmlElement(name = "Supplements")
   protected HotelRatePlanType.Supplements supplements;
   @XmlElement(name = "Offers")
   protected HotelRatePlanType.Offers offers;
   @XmlElement(name = "RatePlanShoulders")
   protected HotelRatePlanType.RatePlanShoulders ratePlanShoulders;
   @XmlElement(name = "SellableProducts")
   protected SellableProductsType sellableProducts;
   @XmlElement(name = "RatePlanLevelFee")
   protected HotelRatePlanType.RatePlanLevelFee ratePlanLevelFee;
   @XmlElement(name = "Commission")
   protected CommissionType commission;
   @XmlElement(name = "Description")
   protected List<ParagraphType> description;
   @XmlElement(name = "UniqueID")
   protected UniqueIDType uniqueID;
   @XmlElement(name = "HotelRef")
   protected HotelRatePlanType.HotelRef hotelRef;
   @XmlAttribute(name = "RatePlanNotifType")
   protected String ratePlanNotifType;
   @XmlAttribute(name = "RatePlanStatusType")
   protected String ratePlanStatusType;
   @XmlAttribute(name = "RatePlanNotifScopeType")
   protected String ratePlanNotifScopeType;
   @XmlAttribute(name = "IsCommissionable")
   protected Boolean isCommissionable;
   @XmlAttribute(name = "RateReturn")
   protected Boolean rateReturn;
   @XmlAttribute(name = "YieldableIndicator")
   protected Boolean yieldableIndicator;
   @XmlAttribute(name = "MarketCode")
   protected String marketCode;
   @XmlAttribute(name = "YieldDeltaAmount")
   protected BigDecimal yieldDeltaAmount;
   @XmlAttribute(name = "InventoryAllocatedInd")
   protected Boolean inventoryAllocatedInd;
   @XmlAttribute(name = "RestrictedDisplayIndicator")
   protected Boolean restrictedDisplayIndicator;
   @XmlAttribute(name = "EarliestStartIndicator")
   protected Boolean earliestStartIndicator;
   @XmlAttribute(name = "LatestEndIndicator")
   protected Boolean latestEndIndicator;
   @XmlAttribute(name = "ExtraNightRatePlanCode")
   protected String extraNightRatePlanCode;
   @XmlAttribute(name = "ChargeTypeCode")
   protected String chargeTypeCode;
   @XmlAttribute(name = "RatePlanCodeType")
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   protected String ratePlanCodeType;
   @XmlAttribute(name = "Start")
   protected String start;
   @XmlAttribute(name = "Duration")
   protected String duration;
   @XmlAttribute(name = "End")
   protected String end;
   @XmlAttribute(name = "BaseRatePlanCode")
   protected String baseRatePlanCode;
   @XmlAttribute(name = "AdjustedAmount")
   protected BigDecimal adjustedAmount;
   @XmlAttribute(name = "AdjustedPercentage")
   protected BigDecimal adjustedPercentage;
   @XmlAttribute(name = "FloorAmount")
   protected BigDecimal floorAmount;
   @XmlAttribute(name = "CeilingAmount")
   protected BigDecimal ceilingAmount;
   @XmlAttribute(name = "AdjustUpIndicator")
   protected Boolean adjustUpIndicator;
   @XmlAttribute(name = "CurrencyCode")
   protected String currencyCode;
   @XmlAttribute(name = "DecimalPlaces")
   @XmlSchemaType(name = "nonNegativeInteger")
   protected BigInteger decimalPlaces;
   @XmlAttribute(name = "RatePlanType")
   protected String ratePlanType;
   @XmlAttribute(name = "RatePlanCode")
   protected String ratePlanCode;
   @XmlAttribute(name = "RatePlanID")
   protected String ratePlanID;
   @XmlAttribute(name = "RatePlanQualifier")
   protected Boolean ratePlanQualifier;
   @XmlAttribute(name = "RatePlanCategory")
   protected String ratePlanCategory;
   @XmlAttribute(name = "PromotionCode")
   protected String promotionCode;
   @XmlAttribute(name = "PromotionVendorCode")
   protected List<String> promotionVendorCode;

   public HotelRatePlanType.DestinationSystemsCode getDestinationSystemsCode() {
      return this.destinationSystemsCode;
   }

   public void setDestinationSystemsCode(HotelRatePlanType.DestinationSystemsCode value) {
      this.destinationSystemsCode = value;
   }

   public HotelRatePlanType.BookingRules getBookingRules() {
      return this.bookingRules;
   }

   public void setBookingRules(HotelRatePlanType.BookingRules value) {
      this.bookingRules = value;
   }

   public HotelRatePlanType.Rates getRates() {
      return this.rates;
   }

   public void setRates(HotelRatePlanType.Rates value) {
      this.rates = value;
   }

   public HotelRatePlanType.Supplements getSupplements() {
      return this.supplements;
   }

   public void setSupplements(HotelRatePlanType.Supplements value) {
      this.supplements = value;
   }

   public HotelRatePlanType.Offers getOffers() {
      return this.offers;
   }

   public void setOffers(HotelRatePlanType.Offers value) {
      this.offers = value;
   }

   public HotelRatePlanType.RatePlanShoulders getRatePlanShoulders() {
      return this.ratePlanShoulders;
   }

   public void setRatePlanShoulders(HotelRatePlanType.RatePlanShoulders value) {
      this.ratePlanShoulders = value;
   }

   public SellableProductsType getSellableProducts() {
      return this.sellableProducts;
   }

   public void setSellableProducts(SellableProductsType value) {
      this.sellableProducts = value;
   }

   public HotelRatePlanType.RatePlanLevelFee getRatePlanLevelFee() {
      return this.ratePlanLevelFee;
   }

   public void setRatePlanLevelFee(HotelRatePlanType.RatePlanLevelFee value) {
      this.ratePlanLevelFee = value;
   }

   public CommissionType getCommission() {
      return this.commission;
   }

   public void setCommission(CommissionType value) {
      this.commission = value;
   }

   public List<ParagraphType> getDescription() {
      if (this.description == null) {
         this.description = new ArrayList<>();
      }

      return this.description;
   }

   public UniqueIDType getUniqueID() {
      return this.uniqueID;
   }

   public void setUniqueID(UniqueIDType value) {
      this.uniqueID = value;
   }

   public HotelRatePlanType.HotelRef getHotelRef() {
      return this.hotelRef;
   }

   public void setHotelRef(HotelRatePlanType.HotelRef value) {
      this.hotelRef = value;
   }

   public String getRatePlanNotifType() {
      return this.ratePlanNotifType;
   }

   public void setRatePlanNotifType(String value) {
      this.ratePlanNotifType = value;
   }

   public String getRatePlanStatusType() {
      return this.ratePlanStatusType;
   }

   public void setRatePlanStatusType(String value) {
      this.ratePlanStatusType = value;
   }

   public String getRatePlanNotifScopeType() {
      return this.ratePlanNotifScopeType;
   }

   public void setRatePlanNotifScopeType(String value) {
      this.ratePlanNotifScopeType = value;
   }

   public Boolean isIsCommissionable() {
      return this.isCommissionable;
   }

   public void setIsCommissionable(Boolean value) {
      this.isCommissionable = value;
   }

   public Boolean isRateReturn() {
      return this.rateReturn;
   }

   public void setRateReturn(Boolean value) {
      this.rateReturn = value;
   }

   public Boolean isYieldableIndicator() {
      return this.yieldableIndicator;
   }

   public void setYieldableIndicator(Boolean value) {
      this.yieldableIndicator = value;
   }

   public String getMarketCode() {
      return this.marketCode;
   }

   public void setMarketCode(String value) {
      this.marketCode = value;
   }

   public BigDecimal getYieldDeltaAmount() {
      return this.yieldDeltaAmount;
   }

   public void setYieldDeltaAmount(BigDecimal value) {
      this.yieldDeltaAmount = value;
   }

   public Boolean isInventoryAllocatedInd() {
      return this.inventoryAllocatedInd;
   }

   public void setInventoryAllocatedInd(Boolean value) {
      this.inventoryAllocatedInd = value;
   }

   public Boolean isRestrictedDisplayIndicator() {
      return this.restrictedDisplayIndicator;
   }

   public void setRestrictedDisplayIndicator(Boolean value) {
      this.restrictedDisplayIndicator = value;
   }

   public Boolean isEarliestStartIndicator() {
      return this.earliestStartIndicator;
   }

   public void setEarliestStartIndicator(Boolean value) {
      this.earliestStartIndicator = value;
   }

   public Boolean isLatestEndIndicator() {
      return this.latestEndIndicator;
   }

   public void setLatestEndIndicator(Boolean value) {
      this.latestEndIndicator = value;
   }

   public String getExtraNightRatePlanCode() {
      return this.extraNightRatePlanCode;
   }

   public void setExtraNightRatePlanCode(String value) {
      this.extraNightRatePlanCode = value;
   }

   public String getChargeTypeCode() {
      return this.chargeTypeCode;
   }

   public void setChargeTypeCode(String value) {
      this.chargeTypeCode = value;
   }

   public String getRatePlanCodeType() {
      return this.ratePlanCodeType;
   }

   public void setRatePlanCodeType(String value) {
      this.ratePlanCodeType = value;
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

   public String getBaseRatePlanCode() {
      return this.baseRatePlanCode;
   }

   public void setBaseRatePlanCode(String value) {
      this.baseRatePlanCode = value;
   }

   public BigDecimal getAdjustedAmount() {
      return this.adjustedAmount;
   }

   public void setAdjustedAmount(BigDecimal value) {
      this.adjustedAmount = value;
   }

   public BigDecimal getAdjustedPercentage() {
      return this.adjustedPercentage;
   }

   public void setAdjustedPercentage(BigDecimal value) {
      this.adjustedPercentage = value;
   }

   public BigDecimal getFloorAmount() {
      return this.floorAmount;
   }

   public void setFloorAmount(BigDecimal value) {
      this.floorAmount = value;
   }

   public BigDecimal getCeilingAmount() {
      return this.ceilingAmount;
   }

   public void setCeilingAmount(BigDecimal value) {
      this.ceilingAmount = value;
   }

   public Boolean isAdjustUpIndicator() {
      return this.adjustUpIndicator;
   }

   public void setAdjustUpIndicator(Boolean value) {
      this.adjustUpIndicator = value;
   }

   public String getCurrencyCode() {
      return this.currencyCode;
   }

   public void setCurrencyCode(String value) {
      this.currencyCode = value;
   }

   public BigInteger getDecimalPlaces() {
      return this.decimalPlaces;
   }

   public void setDecimalPlaces(BigInteger value) {
      this.decimalPlaces = value;
   }

   public String getRatePlanType() {
      return this.ratePlanType;
   }

   public void setRatePlanType(String value) {
      this.ratePlanType = value;
   }

   public String getRatePlanCode() {
      return this.ratePlanCode;
   }

   public void setRatePlanCode(String value) {
      this.ratePlanCode = value;
   }

   public String getRatePlanID() {
      return this.ratePlanID;
   }

   public void setRatePlanID(String value) {
      this.ratePlanID = value;
   }

   public Boolean isRatePlanQualifier() {
      return this.ratePlanQualifier;
   }

   public void setRatePlanQualifier(Boolean value) {
      this.ratePlanQualifier = value;
   }

   public String getRatePlanCategory() {
      return this.ratePlanCategory;
   }

   public void setRatePlanCategory(String value) {
      this.ratePlanCategory = value;
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
   @XmlType(name = "", propOrder = "inventoryInfo")
   public static class BookingRules extends BookingRulesType {
      @XmlElement(name = "InventoryInfo")
      protected HotelRatePlanType.BookingRules.InventoryInfo inventoryInfo;

      public HotelRatePlanType.BookingRules.InventoryInfo getInventoryInfo() {
         return this.inventoryInfo;
      }

      public void setInventoryInfo(HotelRatePlanType.BookingRules.InventoryInfo value) {
         this.inventoryInfo = value;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class InventoryInfo {
         @XmlAttribute(name = "InvCodeApplication")
         @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
         protected String invCodeApplication;
         @XmlAttribute(name = "InvCode")
         protected String invCode;
         @XmlAttribute(name = "InvType")
         protected String invType;
         @XmlAttribute(name = "InvTypeCode")
         protected String invTypeCode;
         @XmlAttribute(name = "IsRoom")
         protected Boolean isRoom;

         public String getInvCodeApplication() {
            return this.invCodeApplication;
         }

         public void setInvCodeApplication(String value) {
            this.invCodeApplication = value;
         }

         public String getInvCode() {
            return this.invCode;
         }

         public void setInvCode(String value) {
            this.invCode = value;
         }

         public String getInvType() {
            return this.invType;
         }

         public void setInvType(String value) {
            this.invType = value;
         }

         public String getInvTypeCode() {
            return this.invTypeCode;
         }

         public void setInvTypeCode(String value) {
            this.invTypeCode = value;
         }

         public Boolean isIsRoom() {
            return this.isRoom;
         }

         public void setIsRoom(Boolean value) {
            this.isRoom = value;
         }
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "destinationSystemCode")
   public static class DestinationSystemsCode {
      @XmlElement(name = "DestinationSystemCode", required = true)
      protected List<HotelRatePlanType.DestinationSystemsCode.DestinationSystemCode> destinationSystemCode;

      public List<HotelRatePlanType.DestinationSystemsCode.DestinationSystemCode> getDestinationSystemCode() {
         if (this.destinationSystemCode == null) {
            this.destinationSystemCode = new ArrayList<>();
         }

         return this.destinationSystemCode;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = "value")
      public static class DestinationSystemCode {
         @XmlValue
         protected String value;
         @XmlAttribute(name = "ChainRateLevelCrossRef")
         protected String chainRateLevelCrossRef;
         @XmlAttribute(name = "ChainRateCodeCrossRef")
         protected String chainRateCodeCrossRef;
         @XmlAttribute(name = "LengthOfStayRuleID")
         protected String lengthOfStayRuleID;
         @XmlAttribute(name = "POS_RuleID")
         protected String posRuleID;
         @XmlAttribute(name = "RateAccessID")
         protected String rateAccessID;
         @XmlAttribute(name = "GuaranteeDepositRuleID")
         protected String guaranteeDepositRuleID;
         @XmlAttribute(name = "CancelRuleID")
         protected String cancelRuleID;

         public String getValue() {
            return this.value;
         }

         public void setValue(String value) {
            this.value = value;
         }

         public String getChainRateLevelCrossRef() {
            return this.chainRateLevelCrossRef;
         }

         public void setChainRateLevelCrossRef(String value) {
            this.chainRateLevelCrossRef = value;
         }

         public String getChainRateCodeCrossRef() {
            return this.chainRateCodeCrossRef;
         }

         public void setChainRateCodeCrossRef(String value) {
            this.chainRateCodeCrossRef = value;
         }

         public String getLengthOfStayRuleID() {
            return this.lengthOfStayRuleID;
         }

         public void setLengthOfStayRuleID(String value) {
            this.lengthOfStayRuleID = value;
         }

         public String getPOSRuleID() {
            return this.posRuleID;
         }

         public void setPOSRuleID(String value) {
            this.posRuleID = value;
         }

         public String getRateAccessID() {
            return this.rateAccessID;
         }

         public void setRateAccessID(String value) {
            this.rateAccessID = value;
         }

         public String getGuaranteeDepositRuleID() {
            return this.guaranteeDepositRuleID;
         }

         public void setGuaranteeDepositRuleID(String value) {
            this.guaranteeDepositRuleID = value;
         }

         public String getCancelRuleID() {
            return this.cancelRuleID;
         }

         public void setCancelRuleID(String value) {
            this.cancelRuleID = value;
         }
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class HotelRef {
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
   @XmlType(name = "", propOrder = "offer")
   public static class Offers {
      @XmlElement(name = "Offer", required = true)
      protected List<HotelRatePlanType.Offers.Offer> offer;

      public List<HotelRatePlanType.Offers.Offer> getOffer() {
         if (this.offer == null) {
            this.offer = new ArrayList<>();
         }

         return this.offer;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class Offer extends OfferType {
         @XmlAttribute(name = "SupplementRPH")
         protected List<String> supplementRPH;

         public List<String> getSupplementRPH() {
            if (this.supplementRPH == null) {
               this.supplementRPH = new ArrayList<>();
            }

            return this.supplementRPH;
         }
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "fee")
   public static class RatePlanLevelFee {
      @XmlElement(name = "Fee", required = true)
      protected List<HotelRatePlanType.RatePlanLevelFee.Fee> fee;
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

      public List<HotelRatePlanType.RatePlanLevelFee.Fee> getFee() {
         if (this.fee == null) {
            this.fee = new ArrayList<>();
         }

         return this.fee;
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

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class Fee extends FeeType {
         @XmlAttribute(name = "MealPlanCode")
         protected String mealPlanCode;

         public String getMealPlanCode() {
            return this.mealPlanCode;
         }

         public void setMealPlanCode(String value) {
            this.mealPlanCode = value;
         }
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "ratePlanShoulder")
   public static class RatePlanShoulders {
      @XmlElement(name = "RatePlanShoulder", required = true)
      protected List<HotelRatePlanType.RatePlanShoulders.RatePlanShoulder> ratePlanShoulder;

      public List<HotelRatePlanType.RatePlanShoulders.RatePlanShoulder> getRatePlanShoulder() {
         if (this.ratePlanShoulder == null) {
            this.ratePlanShoulder = new ArrayList<>();
         }

         return this.ratePlanShoulder;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = "sellableProducts")
      public static class RatePlanShoulder {
         @XmlElement(name = "SellableProducts")
         protected SellableProductsType sellableProducts;
         @XmlAttribute(name = "ShoulderRPH")
         protected String shoulderRPH;
         @XmlAttribute(name = "PreShoulderSellLimit")
         @XmlSchemaType(name = "nonNegativeInteger")
         protected BigInteger preShoulderSellLimit;
         @XmlAttribute(name = "PostShoulderSellLimit")
         @XmlSchemaType(name = "nonNegativeInteger")
         protected BigInteger postShoulderSellLimit;
         @XmlAttribute(name = "PreShoulderStartDate")
         protected String preShoulderStartDate;
         @XmlAttribute(name = "PostShoulderEndDate")
         protected String postShoulderEndDate;

         public SellableProductsType getSellableProducts() {
            return this.sellableProducts;
         }

         public void setSellableProducts(SellableProductsType value) {
            this.sellableProducts = value;
         }

         public String getShoulderRPH() {
            return this.shoulderRPH;
         }

         public void setShoulderRPH(String value) {
            this.shoulderRPH = value;
         }

         public BigInteger getPreShoulderSellLimit() {
            return this.preShoulderSellLimit;
         }

         public void setPreShoulderSellLimit(BigInteger value) {
            this.preShoulderSellLimit = value;
         }

         public BigInteger getPostShoulderSellLimit() {
            return this.postShoulderSellLimit;
         }

         public void setPostShoulderSellLimit(BigInteger value) {
            this.postShoulderSellLimit = value;
         }

         public String getPreShoulderStartDate() {
            return this.preShoulderStartDate;
         }

         public void setPreShoulderStartDate(String value) {
            this.preShoulderStartDate = value;
         }

         public String getPostShoulderEndDate() {
            return this.postShoulderEndDate;
         }

         public void setPostShoulderEndDate(String value) {
            this.postShoulderEndDate = value;
         }
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "rate")
   public static class Rates {
      @XmlElement(name = "Rate", required = true)
      protected List<HotelRatePlanType.Rates.Rate> rate;

      public List<HotelRatePlanType.Rates.Rate> getRate() {
         if (this.rate == null) {
            this.rate = new ArrayList<>();
         }

         return this.rate;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class Rate extends RateUploadType {
         @XmlAttribute(name = "Status")
         protected List<String> status;
         @XmlAttribute(name = "ExtraNightIndicator")
         protected Boolean extraNightIndicator;
         @XmlAttribute(name = "InvCodeApplication")
         @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
         protected String invCodeApplication;
         @XmlAttribute(name = "InvCode")
         protected String invCode;
         @XmlAttribute(name = "InvType")
         protected String invType;
         @XmlAttribute(name = "InvTypeCode")
         protected String invTypeCode;
         @XmlAttribute(name = "IsRoom")
         protected Boolean isRoom;
         @XmlAttribute(name = "BaseRatePlanCode")
         protected String baseRatePlanCode;
         @XmlAttribute(name = "AdjustedAmount")
         protected BigDecimal adjustedAmount;
         @XmlAttribute(name = "AdjustedPercentage")
         protected BigDecimal adjustedPercentage;
         @XmlAttribute(name = "FloorAmount")
         protected BigDecimal floorAmount;
         @XmlAttribute(name = "CeilingAmount")
         protected BigDecimal ceilingAmount;
         @XmlAttribute(name = "AdjustUpIndicator")
         protected Boolean adjustUpIndicator;

         public List<String> getStatus() {
            if (this.status == null) {
               this.status = new ArrayList<>();
            }

            return this.status;
         }

         public Boolean isExtraNightIndicator() {
            return this.extraNightIndicator;
         }

         public void setExtraNightIndicator(Boolean value) {
            this.extraNightIndicator = value;
         }

         public String getInvCodeApplication() {
            return this.invCodeApplication;
         }

         public void setInvCodeApplication(String value) {
            this.invCodeApplication = value;
         }

         public String getInvCode() {
            return this.invCode;
         }

         public void setInvCode(String value) {
            this.invCode = value;
         }

         public String getInvType() {
            return this.invType;
         }

         public void setInvType(String value) {
            this.invType = value;
         }

         public String getInvTypeCode() {
            return this.invTypeCode;
         }

         public void setInvTypeCode(String value) {
            this.invTypeCode = value;
         }

         public Boolean isIsRoom() {
            return this.isRoom;
         }

         public void setIsRoom(Boolean value) {
            this.isRoom = value;
         }

         public String getBaseRatePlanCode() {
            return this.baseRatePlanCode;
         }

         public void setBaseRatePlanCode(String value) {
            this.baseRatePlanCode = value;
         }

         public BigDecimal getAdjustedAmount() {
            return this.adjustedAmount;
         }

         public void setAdjustedAmount(BigDecimal value) {
            this.adjustedAmount = value;
         }

         public BigDecimal getAdjustedPercentage() {
            return this.adjustedPercentage;
         }

         public void setAdjustedPercentage(BigDecimal value) {
            this.adjustedPercentage = value;
         }

         public BigDecimal getFloorAmount() {
            return this.floorAmount;
         }

         public void setFloorAmount(BigDecimal value) {
            this.floorAmount = value;
         }

         public BigDecimal getCeilingAmount() {
            return this.ceilingAmount;
         }

         public void setCeilingAmount(BigDecimal value) {
            this.ceilingAmount = value;
         }

         public Boolean isAdjustUpIndicator() {
            return this.adjustUpIndicator;
         }

         public void setAdjustUpIndicator(Boolean value) {
            this.adjustUpIndicator = value;
         }
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "supplement")
   public static class Supplements {
      @XmlElement(name = "Supplement", required = true)
      protected List<HotelRatePlanType.Supplements.Supplement> supplement;

      public List<HotelRatePlanType.Supplements.Supplement> getSupplement() {
         if (this.supplement == null) {
            this.supplement = new ArrayList<>();
         }

         return this.supplement;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = {"roomCompanions", "prerequisiteInventory", "description"})
      public static class Supplement {
         @XmlElement(name = "RoomCompanions")
         protected List<HotelRatePlanType.Supplements.Supplement.RoomCompanions> roomCompanions;
         @XmlElement(name = "PrerequisiteInventory")
         protected List<HotelRatePlanType.Supplements.Supplement.PrerequisiteInventory> prerequisiteInventory;
         @XmlElement(name = "Description")
         protected List<ParagraphType> description;
         @XmlAttribute(name = "SupplementType")
         @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
         protected String supplementType;
         @XmlAttribute(name = "ChargeTypeCode")
         protected String chargeTypeCode;
         @XmlAttribute(name = "InvCode")
         protected String invCode;
         @XmlAttribute(name = "InvType")
         protected String invType;
         @XmlAttribute(name = "AdditionalGuestNumber")
         protected Integer additionalGuestNumber;
         @XmlAttribute(name = "RPH")
         protected String rph;
         @XmlAttribute(name = "Percent")
         protected BigDecimal percent;
         @XmlAttribute(name = "AddToBasicRateIndicator")
         protected Boolean addToBasicRateIndicator;
         @XmlAttribute(name = "SingleUseIndicator")
         protected Boolean singleUseIndicator;
         @XmlAttribute(name = "MandatoryIndicator")
         protected Boolean mandatoryIndicator;
         @XmlAttribute(name = "AgeQualifyingCode")
         protected String ageQualifyingCode;
         @XmlAttribute(name = "MinAge")
         protected Integer minAge;
         @XmlAttribute(name = "MaxAge")
         protected Integer maxAge;
         @XmlAttribute(name = "AgeTimeUnit")
         protected TimeUnitType ageTimeUnit;
         @XmlAttribute(name = "AgeBucket")
         protected String ageBucket;
         @XmlAttribute(name = "Amount")
         protected BigDecimal amount;
         @XmlAttribute(name = "CurrencyCode")
         protected String currencyCode;
         @XmlAttribute(name = "DecimalPlaces")
         @XmlSchemaType(name = "nonNegativeInteger")
         protected BigInteger decimalPlaces;
         @XmlAttribute(name = "Start")
         protected String start;
         @XmlAttribute(name = "Duration")
         protected String duration;
         @XmlAttribute(name = "End")
         protected String end;

         public List<HotelRatePlanType.Supplements.Supplement.RoomCompanions> getRoomCompanions() {
            if (this.roomCompanions == null) {
               this.roomCompanions = new ArrayList<>();
            }

            return this.roomCompanions;
         }

         public List<HotelRatePlanType.Supplements.Supplement.PrerequisiteInventory> getPrerequisiteInventory() {
            if (this.prerequisiteInventory == null) {
               this.prerequisiteInventory = new ArrayList<>();
            }

            return this.prerequisiteInventory;
         }

         public List<ParagraphType> getDescription() {
            if (this.description == null) {
               this.description = new ArrayList<>();
            }

            return this.description;
         }

         public String getSupplementType() {
            return this.supplementType;
         }

         public void setSupplementType(String value) {
            this.supplementType = value;
         }

         public String getChargeTypeCode() {
            return this.chargeTypeCode;
         }

         public void setChargeTypeCode(String value) {
            this.chargeTypeCode = value;
         }

         public String getInvCode() {
            return this.invCode;
         }

         public void setInvCode(String value) {
            this.invCode = value;
         }

         public String getInvType() {
            return this.invType;
         }

         public void setInvType(String value) {
            this.invType = value;
         }

         public Integer getAdditionalGuestNumber() {
            return this.additionalGuestNumber;
         }

         public void setAdditionalGuestNumber(Integer value) {
            this.additionalGuestNumber = value;
         }

         public String getRPH() {
            return this.rph;
         }

         public void setRPH(String value) {
            this.rph = value;
         }

         public BigDecimal getPercent() {
            return this.percent;
         }

         public void setPercent(BigDecimal value) {
            this.percent = value;
         }

         public Boolean isAddToBasicRateIndicator() {
            return this.addToBasicRateIndicator;
         }

         public void setAddToBasicRateIndicator(Boolean value) {
            this.addToBasicRateIndicator = value;
         }

         public Boolean isSingleUseIndicator() {
            return this.singleUseIndicator;
         }

         public void setSingleUseIndicator(Boolean value) {
            this.singleUseIndicator = value;
         }

         public Boolean isMandatoryIndicator() {
            return this.mandatoryIndicator;
         }

         public void setMandatoryIndicator(Boolean value) {
            this.mandatoryIndicator = value;
         }

         public String getAgeQualifyingCode() {
            return this.ageQualifyingCode;
         }

         public void setAgeQualifyingCode(String value) {
            this.ageQualifyingCode = value;
         }

         public Integer getMinAge() {
            return this.minAge;
         }

         public void setMinAge(Integer value) {
            this.minAge = value;
         }

         public Integer getMaxAge() {
            return this.maxAge;
         }

         public void setMaxAge(Integer value) {
            this.maxAge = value;
         }

         public TimeUnitType getAgeTimeUnit() {
            return this.ageTimeUnit;
         }

         public void setAgeTimeUnit(TimeUnitType value) {
            this.ageTimeUnit = value;
         }

         public String getAgeBucket() {
            return this.ageBucket;
         }

         public void setAgeBucket(String value) {
            this.ageBucket = value;
         }

         public BigDecimal getAmount() {
            return this.amount;
         }

         public void setAmount(BigDecimal value) {
            this.amount = value;
         }

         public String getCurrencyCode() {
            return this.currencyCode;
         }

         public void setCurrencyCode(String value) {
            this.currencyCode = value;
         }

         public BigInteger getDecimalPlaces() {
            return this.decimalPlaces;
         }

         public void setDecimalPlaces(BigInteger value) {
            this.decimalPlaces = value;
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

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "")
         public static class PrerequisiteInventory {
            @XmlAttribute(name = "InvCode")
            protected String invCode;
            @XmlAttribute(name = "InvType")
            protected String invType;

            public String getInvCode() {
               return this.invCode;
            }

            public void setInvCode(String value) {
               this.invCode = value;
            }

            public String getInvType() {
               return this.invType;
            }

            public void setInvType(String value) {
               this.invType = value;
            }
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "")
         public static class RoomCompanions {
            @XmlAttribute(name = "MinCompanions")
            @XmlSchemaType(name = "nonNegativeInteger")
            protected BigInteger minCompanions;
            @XmlAttribute(name = "MaxCompanions")
            @XmlSchemaType(name = "nonNegativeInteger")
            protected BigInteger maxCompanions;
            @XmlAttribute(name = "AgeQualifyingCode")
            protected String ageQualifyingCode;
            @XmlAttribute(name = "MinAge")
            protected Integer minAge;
            @XmlAttribute(name = "MaxAge")
            protected Integer maxAge;
            @XmlAttribute(name = "AgeTimeUnit")
            protected TimeUnitType ageTimeUnit;
            @XmlAttribute(name = "AgeBucket")
            protected String ageBucket;

            public BigInteger getMinCompanions() {
               return this.minCompanions;
            }

            public void setMinCompanions(BigInteger value) {
               this.minCompanions = value;
            }

            public BigInteger getMaxCompanions() {
               return this.maxCompanions;
            }

            public void setMaxCompanions(BigInteger value) {
               this.maxCompanions = value;
            }

            public String getAgeQualifyingCode() {
               return this.ageQualifyingCode;
            }

            public void setAgeQualifyingCode(String value) {
               this.ageQualifyingCode = value;
            }

            public Integer getMinAge() {
               return this.minAge;
            }

            public void setMinAge(Integer value) {
               this.minAge = value;
            }

            public Integer getMaxAge() {
               return this.maxAge;
            }

            public void setMaxAge(Integer value) {
               this.maxAge = value;
            }

            public TimeUnitType getAgeTimeUnit() {
               return this.ageTimeUnit;
            }

            public void setAgeTimeUnit(TimeUnitType value) {
               this.ageTimeUnit = value;
            }

            public String getAgeBucket() {
               return this.ageBucket;
            }

            public void setAgeBucket(String value) {
               this.ageBucket = value;
            }
         }
      }
   }
}
