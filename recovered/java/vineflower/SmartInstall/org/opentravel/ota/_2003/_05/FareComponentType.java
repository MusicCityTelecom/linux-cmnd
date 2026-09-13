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
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FareComponentType", propOrder = {"priceableUnit", "totalConstructionAmount"})
@XmlSeeAlso(EMDType.ExchResidualFareComponent.class)
public class FareComponentType {
   @XmlElement(name = "PriceableUnit", required = true)
   protected List<FareComponentType.PriceableUnit> priceableUnit;
   @XmlElement(name = "TotalConstructionAmount", required = true)
   protected List<FareComponentType.TotalConstructionAmount> totalConstructionAmount;
   @XmlAttribute(name = "PriceQuoteDate")
   protected String priceQuoteDate;
   @XmlAttribute(name = "AccountCode")
   protected String accountCode;
   @XmlAttribute(name = "PricingDesignator")
   protected String pricingDesignator;
   @XmlAttribute(name = "ExchangeRate")
   protected BigDecimal exchangeRate;
   @XmlAttribute(name = "Quantity")
   @XmlSchemaType(name = "nonNegativeInteger")
   protected BigInteger quantity;

   public List<FareComponentType.PriceableUnit> getPriceableUnit() {
      if (this.priceableUnit == null) {
         this.priceableUnit = new ArrayList<>();
      }

      return this.priceableUnit;
   }

   public List<FareComponentType.TotalConstructionAmount> getTotalConstructionAmount() {
      if (this.totalConstructionAmount == null) {
         this.totalConstructionAmount = new ArrayList<>();
      }

      return this.totalConstructionAmount;
   }

   public String getPriceQuoteDate() {
      return this.priceQuoteDate;
   }

   public void setPriceQuoteDate(String value) {
      this.priceQuoteDate = value;
   }

   public String getAccountCode() {
      return this.accountCode;
   }

   public void setAccountCode(String value) {
      this.accountCode = value;
   }

   public String getPricingDesignator() {
      return this.pricingDesignator;
   }

   public void setPricingDesignator(String value) {
      this.pricingDesignator = value;
   }

   public BigDecimal getExchangeRate() {
      return this.exchangeRate;
   }

   public void setExchangeRate(BigDecimal value) {
      this.exchangeRate = value;
   }

   public BigInteger getQuantity() {
      return this.quantity;
   }

   public void setQuantity(BigInteger value) {
      this.quantity = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "fareComponentDetail")
   public static class PriceableUnit {
      @XmlElement(name = "FareComponentDetail", required = true)
      protected List<FareComponentType.PriceableUnit.FareComponentDetail> fareComponentDetail;
      @XmlAttribute(name = "Number", required = true)
      protected String number;

      public List<FareComponentType.PriceableUnit.FareComponentDetail> getFareComponentDetail() {
         if (this.fareComponentDetail == null) {
            this.fareComponentDetail = new ArrayList<>();
         }

         return this.fareComponentDetail;
      }

      public String getNumber() {
         return this.number;
      }

      public void setNumber(String value) {
         this.number = value;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = {"couponSequence", "constructionPrinciple", "baseAmount", "ticketDesignator"})
      public static class FareComponentDetail {
         @XmlElement(name = "CouponSequence", required = true)
         protected List<FareComponentType.PriceableUnit.FareComponentDetail.CouponSequence> couponSequence;
         @XmlElement(name = "ConstructionPrinciple")
         protected List<FareComponentType.PriceableUnit.FareComponentDetail.ConstructionPrinciple> constructionPrinciple;
         @XmlElement(name = "BaseAmount", required = true)
         protected List<FareComponentType.PriceableUnit.FareComponentDetail.BaseAmount> baseAmount;
         @XmlElement(name = "TicketDesignator")
         protected List<FareComponentType.PriceableUnit.FareComponentDetail.TicketDesignator> ticketDesignator;
         @XmlAttribute(name = "Number", required = true)
         protected int number;
         @XmlAttribute(name = "TariffNumber")
         protected String tariffNumber;
         @XmlAttribute(name = "RuleNumber")
         protected String ruleNumber;
         @XmlAttribute(name = "WaiverCode")
         protected String waiverCode;
         @XmlAttribute(name = "PassengerTypeCode")
         protected String passengerTypeCode;
         @XmlAttribute(name = "RuleCode")
         protected String ruleCode;
         @XmlAttribute(name = "FareBasisCode")
         protected String fareBasisCode;
         @XmlAttribute(name = "AgreementCode")
         protected String agreementCode;
         @XmlAttribute(name = "CompanyShortName")
         protected String companyShortName;
         @XmlAttribute(name = "TravelSector")
         protected String travelSector;
         @XmlAttribute(name = "Code")
         protected String code;
         @XmlAttribute(name = "CodeContext")
         protected String codeContext;

         public List<FareComponentType.PriceableUnit.FareComponentDetail.CouponSequence> getCouponSequence() {
            if (this.couponSequence == null) {
               this.couponSequence = new ArrayList<>();
            }

            return this.couponSequence;
         }

         public List<FareComponentType.PriceableUnit.FareComponentDetail.ConstructionPrinciple> getConstructionPrinciple() {
            if (this.constructionPrinciple == null) {
               this.constructionPrinciple = new ArrayList<>();
            }

            return this.constructionPrinciple;
         }

         public List<FareComponentType.PriceableUnit.FareComponentDetail.BaseAmount> getBaseAmount() {
            if (this.baseAmount == null) {
               this.baseAmount = new ArrayList<>();
            }

            return this.baseAmount;
         }

         public List<FareComponentType.PriceableUnit.FareComponentDetail.TicketDesignator> getTicketDesignator() {
            if (this.ticketDesignator == null) {
               this.ticketDesignator = new ArrayList<>();
            }

            return this.ticketDesignator;
         }

         public int getNumber() {
            return this.number;
         }

         public void setNumber(int value) {
            this.number = value;
         }

         public String getTariffNumber() {
            return this.tariffNumber;
         }

         public void setTariffNumber(String value) {
            this.tariffNumber = value;
         }

         public String getRuleNumber() {
            return this.ruleNumber;
         }

         public void setRuleNumber(String value) {
            this.ruleNumber = value;
         }

         public String getWaiverCode() {
            return this.waiverCode;
         }

         public void setWaiverCode(String value) {
            this.waiverCode = value;
         }

         public String getPassengerTypeCode() {
            return this.passengerTypeCode;
         }

         public void setPassengerTypeCode(String value) {
            this.passengerTypeCode = value;
         }

         public String getRuleCode() {
            return this.ruleCode;
         }

         public void setRuleCode(String value) {
            this.ruleCode = value;
         }

         public String getFareBasisCode() {
            return this.fareBasisCode;
         }

         public void setFareBasisCode(String value) {
            this.fareBasisCode = value;
         }

         public String getAgreementCode() {
            return this.agreementCode;
         }

         public void setAgreementCode(String value) {
            this.agreementCode = value;
         }

         public String getCompanyShortName() {
            return this.companyShortName;
         }

         public void setCompanyShortName(String value) {
            this.companyShortName = value;
         }

         public String getTravelSector() {
            return this.travelSector;
         }

         public void setTravelSector(String value) {
            this.travelSector = value;
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
         public static class BaseAmount {
            @XmlAttribute(name = "Purpose")
            protected PurposeType purpose;
            @XmlAttribute(name = "Amount")
            protected BigDecimal amount;
            @XmlAttribute(name = "CurrencyCode")
            protected String currencyCode;
            @XmlAttribute(name = "DecimalPlaces")
            @XmlSchemaType(name = "nonNegativeInteger")
            protected BigInteger decimalPlaces;

            public PurposeType getPurpose() {
               return this.purpose;
            }

            public void setPurpose(PurposeType value) {
               this.purpose = value;
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
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "")
         public static class ConstructionPrinciple {
            @XmlAttribute(name = "Code", required = true)
            protected String code;
            @XmlAttribute(name = "Amount")
            protected BigDecimal amount;
            @XmlAttribute(name = "Percent")
            protected BigDecimal percent;
            @XmlAttribute(name = "OriginCityCode")
            protected String originCityCode;
            @XmlAttribute(name = "OriginCodeContext")
            protected String originCodeContext;
            @XmlAttribute(name = "DestinationCityCode")
            protected String destinationCityCode;
            @XmlAttribute(name = "DestinationCodeContext")
            protected String destinationCodeContext;

            public String getCode() {
               return this.code;
            }

            public void setCode(String value) {
               this.code = value;
            }

            public BigDecimal getAmount() {
               return this.amount;
            }

            public void setAmount(BigDecimal value) {
               this.amount = value;
            }

            public BigDecimal getPercent() {
               return this.percent;
            }

            public void setPercent(BigDecimal value) {
               this.percent = value;
            }

            public String getOriginCityCode() {
               return this.originCityCode;
            }

            public void setOriginCityCode(String value) {
               this.originCityCode = value;
            }

            public String getOriginCodeContext() {
               return this.originCodeContext;
            }

            public void setOriginCodeContext(String value) {
               this.originCodeContext = value;
            }

            public String getDestinationCityCode() {
               return this.destinationCityCode;
            }

            public void setDestinationCityCode(String value) {
               this.destinationCityCode = value;
            }

            public String getDestinationCodeContext() {
               return this.destinationCodeContext;
            }

            public void setDestinationCodeContext(String value) {
               this.destinationCodeContext = value;
            }
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "")
         public static class CouponSequence {
            @XmlAttribute(name = "SequenceNbr")
            protected String sequenceNbr;
            @XmlAttribute(name = "CouponItinerarySeqNbr")
            protected Integer couponItinerarySeqNbr;
            @XmlAttribute(name = "StopoverInd")
            protected Boolean stopoverInd;
            @XmlAttribute(name = "ResBookDesigCode")
            protected String resBookDesigCode;
            @XmlAttribute(name = "CompanyShortName")
            protected String companyShortName;
            @XmlAttribute(name = "TravelSector")
            protected String travelSector;
            @XmlAttribute(name = "Code")
            protected String code;
            @XmlAttribute(name = "CodeContext")
            protected String codeContext;
            @XmlAttribute(name = "OriginCityCode")
            protected String originCityCode;
            @XmlAttribute(name = "OriginCodeContext")
            protected String originCodeContext;
            @XmlAttribute(name = "DestinationCityCode")
            protected String destinationCityCode;
            @XmlAttribute(name = "DestinationCodeContext")
            protected String destinationCodeContext;

            public String getSequenceNbr() {
               return this.sequenceNbr;
            }

            public void setSequenceNbr(String value) {
               this.sequenceNbr = value;
            }

            public Integer getCouponItinerarySeqNbr() {
               return this.couponItinerarySeqNbr;
            }

            public void setCouponItinerarySeqNbr(Integer value) {
               this.couponItinerarySeqNbr = value;
            }

            public Boolean isStopoverInd() {
               return this.stopoverInd;
            }

            public void setStopoverInd(Boolean value) {
               this.stopoverInd = value;
            }

            public String getResBookDesigCode() {
               return this.resBookDesigCode;
            }

            public void setResBookDesigCode(String value) {
               this.resBookDesigCode = value;
            }

            public String getCompanyShortName() {
               return this.companyShortName;
            }

            public void setCompanyShortName(String value) {
               this.companyShortName = value;
            }

            public String getTravelSector() {
               return this.travelSector;
            }

            public void setTravelSector(String value) {
               this.travelSector = value;
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

            public String getOriginCityCode() {
               return this.originCityCode;
            }

            public void setOriginCityCode(String value) {
               this.originCityCode = value;
            }

            public String getOriginCodeContext() {
               return this.originCodeContext;
            }

            public void setOriginCodeContext(String value) {
               this.originCodeContext = value;
            }

            public String getDestinationCityCode() {
               return this.destinationCityCode;
            }

            public void setDestinationCityCode(String value) {
               this.destinationCityCode = value;
            }

            public String getDestinationCodeContext() {
               return this.destinationCodeContext;
            }

            public void setDestinationCodeContext(String value) {
               this.destinationCodeContext = value;
            }
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "")
         public static class TicketDesignator {
            @XmlAttribute(name = "TicketDesignatorCode", required = true)
            protected String ticketDesignatorCode;
            @XmlAttribute(name = "TicketDesignatorQualifier")
            protected String ticketDesignatorQualifier;

            public String getTicketDesignatorCode() {
               return this.ticketDesignatorCode;
            }

            public void setTicketDesignatorCode(String value) {
               this.ticketDesignatorCode = value;
            }

            public String getTicketDesignatorQualifier() {
               return this.ticketDesignatorQualifier;
            }

            public void setTicketDesignatorQualifier(String value) {
               this.ticketDesignatorQualifier = value;
            }
         }
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class TotalConstructionAmount {
      @XmlAttribute(name = "Purpose", required = true)
      protected PurposeType purpose;
      @XmlAttribute(name = "Amount")
      protected BigDecimal amount;
      @XmlAttribute(name = "CurrencyCode")
      protected String currencyCode;
      @XmlAttribute(name = "DecimalPlaces")
      @XmlSchemaType(name = "nonNegativeInteger")
      protected BigInteger decimalPlaces;

      public PurposeType getPurpose() {
         return this.purpose;
      }

      public void setPurpose(PurposeType value) {
         this.purpose = value;
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
   }
}
