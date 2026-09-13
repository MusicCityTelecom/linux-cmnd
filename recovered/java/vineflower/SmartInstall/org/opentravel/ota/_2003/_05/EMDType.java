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
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "EMD_Type",
   propOrder = {
         "travelerRefNumber",
         "agentID",
         "paymentDetail",
         "originDestination",
         "custLoyalty",
         "endorsement",
         "addReferenceID",
         "baseFare",
         "equivFare",
         "totalFare",
         "taxes",
         "unstructuredFareCalc",
         "fareInfo",
         "ticketDocument",
         "commission",
         "fareComponent",
         "carrierFeeInfo",
         "exchResidualFareComponent",
         "originalIssueInfo",
         "reissuedFlown",
         "responseComment",
         "presentInfo",
         "reasonForIssuance",
         "validatingAirline",
         "taxCouponInformation"
   }
)
public class EMDType {
   @XmlElement(name = "TravelerRefNumber")
   protected EMDType.TravelerRefNumber travelerRefNumber;
   @XmlElement(name = "AgentID")
   protected List<UniqueIDType> agentID;
   @XmlElement(name = "PaymentDetail")
   protected List<PaymentDetailType> paymentDetail;
   @XmlElement(name = "OriginDestination")
   protected EMDType.OriginDestination originDestination;
   @XmlElement(name = "CustLoyalty")
   protected List<EMDType.CustLoyalty> custLoyalty;
   @XmlElement(name = "Endorsement")
   protected EMDType.Endorsement endorsement;
   @XmlElement(name = "AddReferenceID")
   protected List<UniqueIDType> addReferenceID;
   @XmlElement(name = "BaseFare")
   protected List<EMDType.BaseFare> baseFare;
   @XmlElement(name = "EquivFare")
   protected List<EMDType.EquivFare> equivFare;
   @XmlElement(name = "TotalFare")
   protected List<EMDType.TotalFare> totalFare;
   @XmlElement(name = "Taxes")
   protected EMDType.Taxes taxes;
   @XmlElement(name = "UnstructuredFareCalc")
   protected List<EMDType.UnstructuredFareCalc> unstructuredFareCalc;
   @XmlElement(name = "FareInfo")
   protected EMDType.FareInfo fareInfo;
   @XmlElement(name = "TicketDocument", required = true)
   protected List<EMDType.TicketDocument> ticketDocument;
   @XmlElement(name = "Commission")
   protected EMDType.Commission commission;
   @XmlElement(name = "FareComponent")
   protected FareComponentType fareComponent;
   @XmlElement(name = "CarrierFeeInfo")
   protected EMDType.CarrierFeeInfo carrierFeeInfo;
   @XmlElement(name = "ExchResidualFareComponent")
   protected List<EMDType.ExchResidualFareComponent> exchResidualFareComponent;
   @XmlElement(name = "OriginalIssueInfo")
   protected EMDType.OriginalIssueInfo originalIssueInfo;
   @XmlElement(name = "ReissuedFlown")
   protected List<EMDType.ReissuedFlown> reissuedFlown;
   @XmlElement(name = "ResponseComment")
   protected FreeTextType responseComment;
   @XmlElement(name = "PresentInfo")
   protected EMDType.PresentInfo presentInfo;
   @XmlElement(name = "ReasonForIssuance")
   protected EMDType.ReasonForIssuance reasonForIssuance;
   @XmlElement(name = "ValidatingAirline")
   protected EMDType.ValidatingAirline validatingAirline;
   @XmlElement(name = "TaxCouponInformation")
   protected EMDType.TaxCouponInformation taxCouponInformation;
   @XmlAttribute(name = "TotalFltSegQty", required = true)
   protected int totalFltSegQty;
   @XmlAttribute(name = "SpecificData")
   protected String specificData;
   @XmlAttribute(name = "TaxOnCommissionInd")
   protected Boolean taxOnCommissionInd;
   @XmlAttribute(name = "TicketingModeCode")
   protected String ticketingModeCode;
   @XmlAttribute(name = "EMD_Type")
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   protected String emdType;
   @XmlAttribute(name = "QuoteInd")
   protected Boolean quoteInd;
   @XmlAttribute(name = "Operation")
   protected ActionType operation;
   @XmlAttribute(name = "RPH")
   protected String rph;
   @XmlAttribute(name = "Quantity")
   @XmlSchemaType(name = "nonNegativeInteger")
   protected BigInteger quantity;

   public EMDType.TravelerRefNumber getTravelerRefNumber() {
      return this.travelerRefNumber;
   }

   public void setTravelerRefNumber(EMDType.TravelerRefNumber value) {
      this.travelerRefNumber = value;
   }

   public List<UniqueIDType> getAgentID() {
      if (this.agentID == null) {
         this.agentID = new ArrayList<>();
      }

      return this.agentID;
   }

   public List<PaymentDetailType> getPaymentDetail() {
      if (this.paymentDetail == null) {
         this.paymentDetail = new ArrayList<>();
      }

      return this.paymentDetail;
   }

   public EMDType.OriginDestination getOriginDestination() {
      return this.originDestination;
   }

   public void setOriginDestination(EMDType.OriginDestination value) {
      this.originDestination = value;
   }

   public List<EMDType.CustLoyalty> getCustLoyalty() {
      if (this.custLoyalty == null) {
         this.custLoyalty = new ArrayList<>();
      }

      return this.custLoyalty;
   }

   public EMDType.Endorsement getEndorsement() {
      return this.endorsement;
   }

   public void setEndorsement(EMDType.Endorsement value) {
      this.endorsement = value;
   }

   public List<UniqueIDType> getAddReferenceID() {
      if (this.addReferenceID == null) {
         this.addReferenceID = new ArrayList<>();
      }

      return this.addReferenceID;
   }

   public List<EMDType.BaseFare> getBaseFare() {
      if (this.baseFare == null) {
         this.baseFare = new ArrayList<>();
      }

      return this.baseFare;
   }

   public List<EMDType.EquivFare> getEquivFare() {
      if (this.equivFare == null) {
         this.equivFare = new ArrayList<>();
      }

      return this.equivFare;
   }

   public List<EMDType.TotalFare> getTotalFare() {
      if (this.totalFare == null) {
         this.totalFare = new ArrayList<>();
      }

      return this.totalFare;
   }

   public EMDType.Taxes getTaxes() {
      return this.taxes;
   }

   public void setTaxes(EMDType.Taxes value) {
      this.taxes = value;
   }

   public List<EMDType.UnstructuredFareCalc> getUnstructuredFareCalc() {
      if (this.unstructuredFareCalc == null) {
         this.unstructuredFareCalc = new ArrayList<>();
      }

      return this.unstructuredFareCalc;
   }

   public EMDType.FareInfo getFareInfo() {
      return this.fareInfo;
   }

   public void setFareInfo(EMDType.FareInfo value) {
      this.fareInfo = value;
   }

   public List<EMDType.TicketDocument> getTicketDocument() {
      if (this.ticketDocument == null) {
         this.ticketDocument = new ArrayList<>();
      }

      return this.ticketDocument;
   }

   public EMDType.Commission getCommission() {
      return this.commission;
   }

   public void setCommission(EMDType.Commission value) {
      this.commission = value;
   }

   public FareComponentType getFareComponent() {
      return this.fareComponent;
   }

   public void setFareComponent(FareComponentType value) {
      this.fareComponent = value;
   }

   public EMDType.CarrierFeeInfo getCarrierFeeInfo() {
      return this.carrierFeeInfo;
   }

   public void setCarrierFeeInfo(EMDType.CarrierFeeInfo value) {
      this.carrierFeeInfo = value;
   }

   public List<EMDType.ExchResidualFareComponent> getExchResidualFareComponent() {
      if (this.exchResidualFareComponent == null) {
         this.exchResidualFareComponent = new ArrayList<>();
      }

      return this.exchResidualFareComponent;
   }

   public EMDType.OriginalIssueInfo getOriginalIssueInfo() {
      return this.originalIssueInfo;
   }

   public void setOriginalIssueInfo(EMDType.OriginalIssueInfo value) {
      this.originalIssueInfo = value;
   }

   public List<EMDType.ReissuedFlown> getReissuedFlown() {
      if (this.reissuedFlown == null) {
         this.reissuedFlown = new ArrayList<>();
      }

      return this.reissuedFlown;
   }

   public FreeTextType getResponseComment() {
      return this.responseComment;
   }

   public void setResponseComment(FreeTextType value) {
      this.responseComment = value;
   }

   public EMDType.PresentInfo getPresentInfo() {
      return this.presentInfo;
   }

   public void setPresentInfo(EMDType.PresentInfo value) {
      this.presentInfo = value;
   }

   public EMDType.ReasonForIssuance getReasonForIssuance() {
      return this.reasonForIssuance;
   }

   public void setReasonForIssuance(EMDType.ReasonForIssuance value) {
      this.reasonForIssuance = value;
   }

   public EMDType.ValidatingAirline getValidatingAirline() {
      return this.validatingAirline;
   }

   public void setValidatingAirline(EMDType.ValidatingAirline value) {
      this.validatingAirline = value;
   }

   public EMDType.TaxCouponInformation getTaxCouponInformation() {
      return this.taxCouponInformation;
   }

   public void setTaxCouponInformation(EMDType.TaxCouponInformation value) {
      this.taxCouponInformation = value;
   }

   public int getTotalFltSegQty() {
      return this.totalFltSegQty;
   }

   public void setTotalFltSegQty(int value) {
      this.totalFltSegQty = value;
   }

   public String getSpecificData() {
      return this.specificData;
   }

   public void setSpecificData(String value) {
      this.specificData = value;
   }

   public Boolean isTaxOnCommissionInd() {
      return this.taxOnCommissionInd;
   }

   public void setTaxOnCommissionInd(Boolean value) {
      this.taxOnCommissionInd = value;
   }

   public String getTicketingModeCode() {
      return this.ticketingModeCode;
   }

   public void setTicketingModeCode(String value) {
      this.ticketingModeCode = value;
   }

   public String getEMDType() {
      return this.emdType;
   }

   public void setEMDType(String value) {
      this.emdType = value;
   }

   public Boolean isQuoteInd() {
      return this.quoteInd;
   }

   public void setQuoteInd(Boolean value) {
      this.quoteInd = value;
   }

   public ActionType getOperation() {
      return this.operation;
   }

   public void setOperation(ActionType value) {
      this.operation = value;
   }

   public String getRPH() {
      return this.rph;
   }

   public void setRPH(String value) {
      this.rph = value;
   }

   public BigInteger getQuantity() {
      return this.quantity;
   }

   public void setQuantity(BigInteger value) {
      this.quantity = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class BaseFare {
      @XmlAttribute(name = "Purpose")
      protected PurposeType purpose;
      @XmlAttribute(name = "FareAmountType")
      protected FareAmountType fareAmountType;
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

      public FareAmountType getFareAmountType() {
         return this.fareAmountType;
      }

      public void setFareAmountType(FareAmountType value) {
         this.fareAmountType = value;
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
   @XmlType(name = "", propOrder = {"paymentDetail", "carrierFee", "taxes"})
   public static class CarrierFeeInfo {
      @XmlElement(name = "PaymentDetail")
      protected PaymentDetailType paymentDetail;
      @XmlElement(name = "CarrierFee")
      protected List<EMDType.CarrierFeeInfo.CarrierFee> carrierFee;
      @XmlElement(name = "Taxes")
      protected EMDType.CarrierFeeInfo.Taxes taxes;

      public PaymentDetailType getPaymentDetail() {
         return this.paymentDetail;
      }

      public void setPaymentDetail(PaymentDetailType value) {
         this.paymentDetail = value;
      }

      public List<EMDType.CarrierFeeInfo.CarrierFee> getCarrierFee() {
         if (this.carrierFee == null) {
            this.carrierFee = new ArrayList<>();
         }

         return this.carrierFee;
      }

      public EMDType.CarrierFeeInfo.Taxes getTaxes() {
         return this.taxes;
      }

      public void setTaxes(EMDType.CarrierFeeInfo.Taxes value) {
         this.taxes = value;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = "feeAmount")
      public static class CarrierFee {
         @XmlElement(name = "FeeAmount", required = true)
         protected List<EMDType.CarrierFeeInfo.CarrierFee.FeeAmount> feeAmount;
         @XmlAttribute(name = "Type", required = true)
         protected String type;
         @XmlAttribute(name = "Number")
         protected Integer number;
         @XmlAttribute(name = "TariffNumber")
         protected String tariffNumber;
         @XmlAttribute(name = "RuleNumber")
         protected String ruleNumber;
         @XmlAttribute(name = "RuleCode")
         protected String ruleCode;
         @XmlAttribute(name = "FareClassCode")
         protected String fareClassCode;
         @XmlAttribute(name = "ReportingCode")
         protected String reportingCode;
         @XmlAttribute(name = "CompanyShortName")
         protected String companyShortName;
         @XmlAttribute(name = "TravelSector")
         protected String travelSector;
         @XmlAttribute(name = "Code")
         protected String code;
         @XmlAttribute(name = "CodeContext")
         protected String codeContext;

         public List<EMDType.CarrierFeeInfo.CarrierFee.FeeAmount> getFeeAmount() {
            if (this.feeAmount == null) {
               this.feeAmount = new ArrayList<>();
            }

            return this.feeAmount;
         }

         public String getType() {
            return this.type;
         }

         public void setType(String value) {
            this.type = value;
         }

         public Integer getNumber() {
            return this.number;
         }

         public void setNumber(Integer value) {
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

         public String getRuleCode() {
            return this.ruleCode;
         }

         public void setRuleCode(String value) {
            this.ruleCode = value;
         }

         public String getFareClassCode() {
            return this.fareClassCode;
         }

         public void setFareClassCode(String value) {
            this.fareClassCode = value;
         }

         public String getReportingCode() {
            return this.reportingCode;
         }

         public void setReportingCode(String value) {
            this.reportingCode = value;
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
         public static class FeeAmount {
            @XmlAttribute(name = "Type", required = true)
            protected String type;
            @XmlAttribute(name = "Amount", required = true)
            protected BigDecimal amount;
            @XmlAttribute(name = "ApplicationCode", required = true)
            protected List<String> applicationCode;
            @XmlAttribute(name = "OriginCityCode")
            protected String originCityCode;
            @XmlAttribute(name = "OriginCodeContext")
            protected String originCodeContext;
            @XmlAttribute(name = "DestinationCityCode")
            protected String destinationCityCode;
            @XmlAttribute(name = "DestinationCodeContext")
            protected String destinationCodeContext;

            public String getType() {
               return this.type;
            }

            public void setType(String value) {
               this.type = value;
            }

            public BigDecimal getAmount() {
               return this.amount;
            }

            public void setAmount(BigDecimal value) {
               this.amount = value;
            }

            public List<String> getApplicationCode() {
               if (this.applicationCode == null) {
                  this.applicationCode = new ArrayList<>();
               }

               return this.applicationCode;
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
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = "tax")
      public static class Taxes {
         @XmlElement(name = "Tax")
         protected List<AirTaxType> tax;

         public List<AirTaxType> getTax() {
            if (this.tax == null) {
               this.tax = new ArrayList<>();
            }

            return this.tax;
         }
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class Commission {
      @XmlAttribute(name = "Type")
      protected String type;
      @XmlAttribute(name = "Percent")
      protected BigDecimal percent;
      @XmlAttribute(name = "Amount")
      protected BigDecimal amount;
      @XmlAttribute(name = "CurrencyCode")
      protected String currencyCode;
      @XmlAttribute(name = "DecimalPlaces")
      @XmlSchemaType(name = "nonNegativeInteger")
      protected BigInteger decimalPlaces;

      public String getType() {
         return this.type;
      }

      public void setType(String value) {
         this.type = value;
      }

      public BigDecimal getPercent() {
         return this.percent;
      }

      public void setPercent(BigDecimal value) {
         this.percent = value;
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
   public static class CustLoyalty {
      @XmlAttribute(name = "ProgramID")
      protected String programID;
      @XmlAttribute(name = "MembershipID")
      protected String membershipID;
      @XmlAttribute(name = "TravelSector")
      protected String travelSector;
      @XmlAttribute(name = "RPH")
      protected String rph;
      @XmlAttribute(name = "VendorCode")
      protected List<String> vendorCode;
      @XmlAttribute(name = "PrimaryLoyaltyIndicator")
      protected Boolean primaryLoyaltyIndicator;
      @XmlAttribute(name = "AllianceLoyaltyLevelName")
      protected String allianceLoyaltyLevelName;
      @XmlAttribute(name = "CustomerType")
      protected String customerType;
      @XmlAttribute(name = "CustomerValue")
      protected String customerValue;
      @XmlAttribute(name = "Password")
      protected String password;
      @XmlAttribute(name = "ShareSynchInd")
      @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
      protected String shareSynchInd;
      @XmlAttribute(name = "ShareMarketInd")
      @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
      protected String shareMarketInd;
      @XmlAttribute(name = "EffectiveDate")
      @XmlSchemaType(name = "date")
      protected XMLGregorianCalendar effectiveDate;
      @XmlAttribute(name = "ExpireDate")
      @XmlSchemaType(name = "date")
      protected XMLGregorianCalendar expireDate;
      @XmlAttribute(name = "ExpireDateExclusiveIndicator")
      protected Boolean expireDateExclusiveIndicator;
      @XmlAttribute(name = "SingleVendorInd")
      @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
      protected String singleVendorInd;
      @XmlAttribute(name = "LoyalLevel")
      protected String loyalLevel;
      @XmlAttribute(name = "LoyalLevelCode")
      protected Integer loyalLevelCode;
      @XmlAttribute(name = "SignupDate")
      @XmlSchemaType(name = "date")
      protected XMLGregorianCalendar signupDate;

      public String getProgramID() {
         return this.programID;
      }

      public void setProgramID(String value) {
         this.programID = value;
      }

      public String getMembershipID() {
         return this.membershipID;
      }

      public void setMembershipID(String value) {
         this.membershipID = value;
      }

      public String getTravelSector() {
         return this.travelSector;
      }

      public void setTravelSector(String value) {
         this.travelSector = value;
      }

      public String getRPH() {
         return this.rph;
      }

      public void setRPH(String value) {
         this.rph = value;
      }

      public List<String> getVendorCode() {
         if (this.vendorCode == null) {
            this.vendorCode = new ArrayList<>();
         }

         return this.vendorCode;
      }

      public Boolean isPrimaryLoyaltyIndicator() {
         return this.primaryLoyaltyIndicator;
      }

      public void setPrimaryLoyaltyIndicator(Boolean value) {
         this.primaryLoyaltyIndicator = value;
      }

      public String getAllianceLoyaltyLevelName() {
         return this.allianceLoyaltyLevelName;
      }

      public void setAllianceLoyaltyLevelName(String value) {
         this.allianceLoyaltyLevelName = value;
      }

      public String getCustomerType() {
         return this.customerType;
      }

      public void setCustomerType(String value) {
         this.customerType = value;
      }

      public String getCustomerValue() {
         return this.customerValue;
      }

      public void setCustomerValue(String value) {
         this.customerValue = value;
      }

      public String getPassword() {
         return this.password;
      }

      public void setPassword(String value) {
         this.password = value;
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

      public XMLGregorianCalendar getEffectiveDate() {
         return this.effectiveDate;
      }

      public void setEffectiveDate(XMLGregorianCalendar value) {
         this.effectiveDate = value;
      }

      public XMLGregorianCalendar getExpireDate() {
         return this.expireDate;
      }

      public void setExpireDate(XMLGregorianCalendar value) {
         this.expireDate = value;
      }

      public Boolean isExpireDateExclusiveIndicator() {
         return this.expireDateExclusiveIndicator;
      }

      public void setExpireDateExclusiveIndicator(Boolean value) {
         this.expireDateExclusiveIndicator = value;
      }

      public String getSingleVendorInd() {
         return this.singleVendorInd;
      }

      public void setSingleVendorInd(String value) {
         this.singleVendorInd = value;
      }

      public String getLoyalLevel() {
         return this.loyalLevel;
      }

      public void setLoyalLevel(String value) {
         this.loyalLevel = value;
      }

      public Integer getLoyalLevelCode() {
         return this.loyalLevelCode;
      }

      public void setLoyalLevelCode(Integer value) {
         this.loyalLevelCode = value;
      }

      public XMLGregorianCalendar getSignupDate() {
         return this.signupDate;
      }

      public void setSignupDate(XMLGregorianCalendar value) {
         this.signupDate = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class Endorsement {
      @XmlAttribute(name = "Info")
      protected String info;

      public String getInfo() {
         return this.info;
      }

      public void setInfo(String value) {
         this.info = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class EquivFare {
      @XmlAttribute(name = "Purpose")
      protected PurposeType purpose;
      @XmlAttribute(name = "FareAmountType")
      protected FareAmountType fareAmountType;
      @XmlAttribute(name = "BankExchangeRate")
      protected BigDecimal bankExchangeRate;
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

      public FareAmountType getFareAmountType() {
         return this.fareAmountType;
      }

      public void setFareAmountType(FareAmountType value) {
         this.fareAmountType = value;
      }

      public BigDecimal getBankExchangeRate() {
         return this.bankExchangeRate;
      }

      public void setBankExchangeRate(BigDecimal value) {
         this.bankExchangeRate = value;
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
   @XmlType(name = "", propOrder = {"taxes", "totalAmount"})
   public static class ExchResidualFareComponent extends FareComponentType {
      @XmlElement(name = "Taxes")
      protected EMDType.ExchResidualFareComponent.Taxes taxes;
      @XmlElement(name = "TotalAmount")
      protected EMDType.ExchResidualFareComponent.TotalAmount totalAmount;

      public EMDType.ExchResidualFareComponent.Taxes getTaxes() {
         return this.taxes;
      }

      public void setTaxes(EMDType.ExchResidualFareComponent.Taxes value) {
         this.taxes = value;
      }

      public EMDType.ExchResidualFareComponent.TotalAmount getTotalAmount() {
         return this.totalAmount;
      }

      public void setTotalAmount(EMDType.ExchResidualFareComponent.TotalAmount value) {
         this.totalAmount = value;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = "tax")
      public static class Taxes {
         @XmlElement(name = "Tax", required = true)
         protected List<AirTaxType> tax;

         public List<AirTaxType> getTax() {
            if (this.tax == null) {
               this.tax = new ArrayList<>();
            }

            return this.tax;
         }
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class TotalAmount {
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
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "penaltyAmount")
   public static class FareInfo extends ETFareInfo {
      @XmlElement(name = "PenaltyAmount")
      protected List<VoluntaryChangesType> penaltyAmount;
      @XmlAttribute(name = "NonEndorsableInd")
      protected Boolean nonEndorsableInd;
      @XmlAttribute(name = "NonRefundableInd")
      protected Boolean nonRefundableInd;
      @XmlAttribute(name = "PenaltyRestrictionInd")
      protected Boolean penaltyRestrictionInd;
      @XmlAttribute(name = "PresentCreditCardInd")
      protected Boolean presentCreditCardInd;
      @XmlAttribute(name = "AroundTheWorldFareInd")
      protected Boolean aroundTheWorldFareInd;
      @XmlAttribute(name = "NonInterlineableInd")
      protected Boolean nonInterlineableInd;
      @XmlAttribute(name = "NonCommissionableInd")
      protected Boolean nonCommissionableInd;
      @XmlAttribute(name = "NonReissuableNonExchgInd")
      protected Boolean nonReissuableNonExchgInd;
      @XmlAttribute(name = "CompanyShortName")
      protected String companyShortName;
      @XmlAttribute(name = "TravelSector")
      protected String travelSector;
      @XmlAttribute(name = "Code")
      protected String code;
      @XmlAttribute(name = "CodeContext")
      protected String codeContext;

      public List<VoluntaryChangesType> getPenaltyAmount() {
         if (this.penaltyAmount == null) {
            this.penaltyAmount = new ArrayList<>();
         }

         return this.penaltyAmount;
      }

      public Boolean isNonEndorsableInd() {
         return this.nonEndorsableInd;
      }

      public void setNonEndorsableInd(Boolean value) {
         this.nonEndorsableInd = value;
      }

      public Boolean isNonRefundableInd() {
         return this.nonRefundableInd;
      }

      public void setNonRefundableInd(Boolean value) {
         this.nonRefundableInd = value;
      }

      public Boolean isPenaltyRestrictionInd() {
         return this.penaltyRestrictionInd;
      }

      public void setPenaltyRestrictionInd(Boolean value) {
         this.penaltyRestrictionInd = value;
      }

      public Boolean isPresentCreditCardInd() {
         return this.presentCreditCardInd;
      }

      public void setPresentCreditCardInd(Boolean value) {
         this.presentCreditCardInd = value;
      }

      public Boolean isAroundTheWorldFareInd() {
         return this.aroundTheWorldFareInd;
      }

      public void setAroundTheWorldFareInd(Boolean value) {
         this.aroundTheWorldFareInd = value;
      }

      public Boolean isNonInterlineableInd() {
         return this.nonInterlineableInd;
      }

      public void setNonInterlineableInd(Boolean value) {
         this.nonInterlineableInd = value;
      }

      public Boolean isNonCommissionableInd() {
         return this.nonCommissionableInd;
      }

      public void setNonCommissionableInd(Boolean value) {
         this.nonCommissionableInd = value;
      }

      public Boolean isNonReissuableNonExchgInd() {
         return this.nonReissuableNonExchgInd;
      }

      public void setNonReissuableNonExchgInd(Boolean value) {
         this.nonReissuableNonExchgInd = value;
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
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class OriginDestination {
      @XmlAttribute(name = "OriginCityCode")
      protected String originCityCode;
      @XmlAttribute(name = "OriginCodeContext")
      protected String originCodeContext;
      @XmlAttribute(name = "DestinationCityCode")
      protected String destinationCityCode;
      @XmlAttribute(name = "DestinationCodeContext")
      protected String destinationCodeContext;

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
   public static class OriginalIssueInfo {
      @XmlAttribute(name = "Information")
      protected String information;
      @XmlAttribute(name = "TicketDocumentNbr")
      protected String ticketDocumentNbr;
      @XmlAttribute(name = "IssuingAgentID")
      protected String issuingAgentID;
      @XmlAttribute(name = "DateOfIssue")
      @XmlSchemaType(name = "date")
      protected XMLGregorianCalendar dateOfIssue;
      @XmlAttribute(name = "LocationCode")
      protected String locationCode;

      public String getInformation() {
         return this.information;
      }

      public void setInformation(String value) {
         this.information = value;
      }

      public String getTicketDocumentNbr() {
         return this.ticketDocumentNbr;
      }

      public void setTicketDocumentNbr(String value) {
         this.ticketDocumentNbr = value;
      }

      public String getIssuingAgentID() {
         return this.issuingAgentID;
      }

      public void setIssuingAgentID(String value) {
         this.issuingAgentID = value;
      }

      public XMLGregorianCalendar getDateOfIssue() {
         return this.dateOfIssue;
      }

      public void setDateOfIssue(XMLGregorianCalendar value) {
         this.dateOfIssue = value;
      }

      public String getLocationCode() {
         return this.locationCode;
      }

      public void setLocationCode(String value) {
         this.locationCode = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class PresentInfo {
      @XmlAttribute(name = "To")
      protected String to;
      @XmlAttribute(name = "At")
      protected String at;

      public String getTo() {
         return this.to;
      }

      public void setTo(String value) {
         this.to = value;
      }

      public String getAt() {
         return this.at;
      }

      public void setAt(String value) {
         this.at = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class ReasonForIssuance {
      @XmlAttribute(name = "Code")
      protected String code;
      @XmlAttribute(name = "SubCode")
      protected String subCode;
      @XmlAttribute(name = "Description")
      protected String description;

      public String getCode() {
         return this.code;
      }

      public void setCode(String value) {
         this.code = value;
      }

      public String getSubCode() {
         return this.subCode;
      }

      public void setSubCode(String value) {
         this.subCode = value;
      }

      public String getDescription() {
         return this.description;
      }

      public void setDescription(String value) {
         this.description = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "flightSegmentRPH")
   public static class ReissuedFlown {
      @XmlElement(name = "FlightSegmentRPH")
      protected String flightSegmentRPH;
      @XmlAttribute(name = "Number", required = true)
      protected int number;
      @XmlAttribute(name = "CouponItinerarySeqNbr")
      protected Integer couponItinerarySeqNbr;
      @XmlAttribute(name = "FareBasisCode")
      protected String fareBasisCode;
      @XmlAttribute(name = "TicketDocumentNbr", required = true)
      protected String ticketDocumentNbr;
      @XmlAttribute(name = "DateOfIssue")
      @XmlSchemaType(name = "date")
      protected XMLGregorianCalendar dateOfIssue;
      @XmlAttribute(name = "WaiverCode")
      protected String waiverCode;
      @XmlAttribute(name = "TicketDesignatorCode")
      protected String ticketDesignatorCode;

      public String getFlightSegmentRPH() {
         return this.flightSegmentRPH;
      }

      public void setFlightSegmentRPH(String value) {
         this.flightSegmentRPH = value;
      }

      public int getNumber() {
         return this.number;
      }

      public void setNumber(int value) {
         this.number = value;
      }

      public Integer getCouponItinerarySeqNbr() {
         return this.couponItinerarySeqNbr;
      }

      public void setCouponItinerarySeqNbr(Integer value) {
         this.couponItinerarySeqNbr = value;
      }

      public String getFareBasisCode() {
         return this.fareBasisCode;
      }

      public void setFareBasisCode(String value) {
         this.fareBasisCode = value;
      }

      public String getTicketDocumentNbr() {
         return this.ticketDocumentNbr;
      }

      public void setTicketDocumentNbr(String value) {
         this.ticketDocumentNbr = value;
      }

      public XMLGregorianCalendar getDateOfIssue() {
         return this.dateOfIssue;
      }

      public void setDateOfIssue(XMLGregorianCalendar value) {
         this.dateOfIssue = value;
      }

      public String getWaiverCode() {
         return this.waiverCode;
      }

      public void setWaiverCode(String value) {
         this.waiverCode = value;
      }

      public String getTicketDesignatorCode() {
         return this.ticketDesignatorCode;
      }

      public void setTicketDesignatorCode(String value) {
         this.ticketDesignatorCode = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "ticketDocument")
   public static class TaxCouponInformation {
      @XmlElement(name = "TicketDocument", required = true)
      protected List<EMDType.TaxCouponInformation.TicketDocument> ticketDocument;
      @XmlAttribute(name = "BirthDate")
      @XmlSchemaType(name = "date")
      protected XMLGregorianCalendar birthDate;
      @XmlAttribute(name = "JourneyTurnaroundCityCode")
      protected String journeyTurnaroundCityCode;

      public List<EMDType.TaxCouponInformation.TicketDocument> getTicketDocument() {
         if (this.ticketDocument == null) {
            this.ticketDocument = new ArrayList<>();
         }

         return this.ticketDocument;
      }

      public XMLGregorianCalendar getBirthDate() {
         return this.birthDate;
      }

      public void setBirthDate(XMLGregorianCalendar value) {
         this.birthDate = value;
      }

      public String getJourneyTurnaroundCityCode() {
         return this.journeyTurnaroundCityCode;
      }

      public void setJourneyTurnaroundCityCode(String value) {
         this.journeyTurnaroundCityCode = value;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = "couponNumber")
      public static class TicketDocument {
         @XmlElement(name = "CouponNumber", required = true)
         protected List<EMDType.TaxCouponInformation.TicketDocument.CouponNumber> couponNumber;
         @XmlAttribute(name = "TicketDocumentNbr", required = true)
         protected String ticketDocumentNbr;

         public List<EMDType.TaxCouponInformation.TicketDocument.CouponNumber> getCouponNumber() {
            if (this.couponNumber == null) {
               this.couponNumber = new ArrayList<>();
            }

            return this.couponNumber;
         }

         public String getTicketDocumentNbr() {
            return this.ticketDocumentNbr;
         }

         public void setTicketDocumentNbr(String value) {
            this.ticketDocumentNbr = value;
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "", propOrder = {"taxCouponInfo", "tax", "unticketedPointInfo"})
         public static class CouponNumber {
            @XmlElement(name = "TaxCouponInfo")
            protected EMDType.TaxCouponInformation.TicketDocument.CouponNumber.TaxCouponInfo taxCouponInfo;
            @XmlElement(name = "Tax")
            protected List<AirTaxType> tax;
            @XmlElement(name = "UnticketedPointInfo")
            protected List<EMDType.TaxCouponInformation.TicketDocument.CouponNumber.UnticketedPointInfo> unticketedPointInfo;
            @XmlAttribute(name = "Number", required = true)
            protected int number;

            public EMDType.TaxCouponInformation.TicketDocument.CouponNumber.TaxCouponInfo getTaxCouponInfo() {
               return this.taxCouponInfo;
            }

            public void setTaxCouponInfo(EMDType.TaxCouponInformation.TicketDocument.CouponNumber.TaxCouponInfo value) {
               this.taxCouponInfo = value;
            }

            public List<AirTaxType> getTax() {
               if (this.tax == null) {
                  this.tax = new ArrayList<>();
               }

               return this.tax;
            }

            public List<EMDType.TaxCouponInformation.TicketDocument.CouponNumber.UnticketedPointInfo> getUnticketedPointInfo() {
               if (this.unticketedPointInfo == null) {
                  this.unticketedPointInfo = new ArrayList<>();
               }

               return this.unticketedPointInfo;
            }

            public int getNumber() {
               return this.number;
            }

            public void setNumber(int value) {
               this.number = value;
            }

            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "")
            public static class TaxCouponInfo {
               @XmlAttribute(name = "Cabin")
               protected CabinType cabin;
               @XmlAttribute(name = "AirEquipType")
               protected String airEquipType;

               public CabinType getCabin() {
                  return this.cabin;
               }

               public void setCabin(CabinType value) {
                  this.cabin = value;
               }

               public String getAirEquipType() {
                  return this.airEquipType;
               }

               public void setAirEquipType(String value) {
                  this.airEquipType = value;
               }
            }

            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "")
            public static class UnticketedPointInfo {
               @XmlAttribute(name = "CityAirportCode")
               protected String cityAirportCode;
               @XmlAttribute(name = "ArrivalDate")
               protected String arrivalDate;
               @XmlAttribute(name = "DepartureDate")
               protected String departureDate;
               @XmlAttribute(name = "AirEquipType")
               protected String airEquipType;

               public String getCityAirportCode() {
                  return this.cityAirportCode;
               }

               public void setCityAirportCode(String value) {
                  this.cityAirportCode = value;
               }

               public String getArrivalDate() {
                  return this.arrivalDate;
               }

               public void setArrivalDate(String value) {
                  this.arrivalDate = value;
               }

               public String getDepartureDate() {
                  return this.departureDate;
               }

               public void setDepartureDate(String value) {
                  this.departureDate = value;
               }

               public String getAirEquipType() {
                  return this.airEquipType;
               }

               public void setAirEquipType(String value) {
                  this.airEquipType = value;
               }
            }
         }
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "tax")
   public static class Taxes {
      @XmlElement(name = "Tax", required = true)
      protected List<EMDType.Taxes.Tax> tax;

      public List<EMDType.Taxes.Tax> getTax() {
         if (this.tax == null) {
            this.tax = new ArrayList<>();
         }

         return this.tax;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class Tax extends AirTaxType {
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "couponInfo")
   public static class TicketDocument {
      @XmlElement(name = "CouponInfo", required = true)
      protected List<EMDType.TicketDocument.CouponInfo> couponInfo;
      @XmlAttribute(name = "TicketDocumentNbr", required = true)
      protected String ticketDocumentNbr;
      @XmlAttribute(name = "Type")
      @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
      protected String type;
      @XmlAttribute(name = "PrimaryDocInd")
      protected Boolean primaryDocInd;
      @XmlAttribute(name = "InConnectionDocNbr")
      protected String inConnectionDocNbr;
      @XmlAttribute(name = "DateOfIssue")
      @XmlSchemaType(name = "date")
      protected XMLGregorianCalendar dateOfIssue;
      @XmlAttribute(name = "ExchangeTktNbrInd")
      protected Boolean exchangeTktNbrInd;
      @XmlAttribute(name = "CompanyShortName")
      protected String companyShortName;
      @XmlAttribute(name = "TravelSector")
      protected String travelSector;
      @XmlAttribute(name = "Code")
      protected String code;
      @XmlAttribute(name = "CodeContext")
      protected String codeContext;

      public List<EMDType.TicketDocument.CouponInfo> getCouponInfo() {
         if (this.couponInfo == null) {
            this.couponInfo = new ArrayList<>();
         }

         return this.couponInfo;
      }

      public String getTicketDocumentNbr() {
         return this.ticketDocumentNbr;
      }

      public void setTicketDocumentNbr(String value) {
         this.ticketDocumentNbr = value;
      }

      public String getType() {
         return this.type;
      }

      public void setType(String value) {
         this.type = value;
      }

      public Boolean isPrimaryDocInd() {
         return this.primaryDocInd;
      }

      public void setPrimaryDocInd(Boolean value) {
         this.primaryDocInd = value;
      }

      public String getInConnectionDocNbr() {
         return this.inConnectionDocNbr;
      }

      public void setInConnectionDocNbr(String value) {
         this.inConnectionDocNbr = value;
      }

      public XMLGregorianCalendar getDateOfIssue() {
         return this.dateOfIssue;
      }

      public void setDateOfIssue(XMLGregorianCalendar value) {
         this.dateOfIssue = value;
      }

      public Boolean isExchangeTktNbrInd() {
         return this.exchangeTktNbrInd;
      }

      public void setExchangeTktNbrInd(Boolean value) {
         this.exchangeTktNbrInd = value;
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
      @XmlType(
         name = "",
         propOrder = {
               "soldFlightSegmentRPH",
               "checkedInAirlineRPH",
               "flownAirlineSegmentRPH",
               "excessBaggage",
               "presentInfo",
               "reasonForIssuance",
               "validatingAirline",
               "filedFeeInfo"
         }
      )
      public static class CouponInfo {
         @XmlElement(name = "SoldFlightSegmentRPH")
         protected String soldFlightSegmentRPH;
         @XmlElement(name = "CheckedInAirlineRPH")
         protected String checkedInAirlineRPH;
         @XmlElement(name = "FlownAirlineSegmentRPH")
         protected String flownAirlineSegmentRPH;
         @XmlElement(name = "ExcessBaggage")
         protected EMDType.TicketDocument.CouponInfo.ExcessBaggage excessBaggage;
         @XmlElement(name = "PresentInfo")
         protected EMDType.TicketDocument.CouponInfo.PresentInfo presentInfo;
         @XmlElement(name = "ReasonForIssuance")
         protected EMDType.TicketDocument.CouponInfo.ReasonForIssuance reasonForIssuance;
         @XmlElement(name = "ValidatingAirline")
         protected EMDType.TicketDocument.CouponInfo.ValidatingAirline validatingAirline;
         @XmlElement(name = "FiledFeeInfo")
         protected EMDType.TicketDocument.CouponInfo.FiledFeeInfo filedFeeInfo;
         @XmlAttribute(name = "Number")
         protected Integer number;
         @XmlAttribute(name = "InConnectionNbr")
         protected Integer inConnectionNbr;
         @XmlAttribute(name = "CouponReference")
         protected String couponReference;
         @XmlAttribute(name = "FareBasisCode")
         protected String fareBasisCode;
         @XmlAttribute(name = "Start")
         protected String start;
         @XmlAttribute(name = "Duration")
         protected String duration;
         @XmlAttribute(name = "End")
         protected String end;
         @XmlAttribute(name = "Status")
         protected String status;
         @XmlAttribute(name = "CouponItinerarySeqNbr")
         protected Integer couponItinerarySeqNbr;
         @XmlAttribute(name = "InvoluntaryIndCode")
         @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
         protected String involuntaryIndCode;
         @XmlAttribute(name = "SettlementAuthCode")
         protected String settlementAuthCode;
         @XmlAttribute(name = "Value")
         protected BigDecimal value;
         @XmlAttribute(name = "AssociateInd")
         protected Boolean associateInd;
         @XmlAttribute(name = "PromotionalCode")
         protected String promotionalCode;
         @XmlAttribute(name = "Remark")
         protected String remark;
         @XmlAttribute(name = "TaxOnEMD_Ind")
         protected Boolean taxOnEMDInd;
         @XmlAttribute(name = "AssocFareBasisCode")
         protected String assocFareBasisCode;
         @XmlAttribute(name = "ConsumedAtIssuanceInd")
         protected Boolean consumedAtIssuanceInd;
         @XmlAttribute(name = "DateOfService")
         protected String dateOfService;
         @XmlAttribute(name = "UnitOfMeasureQuantity")
         protected BigDecimal unitOfMeasureQuantity;
         @XmlAttribute(name = "UnitOfMeasure")
         protected String unitOfMeasure;
         @XmlAttribute(name = "UnitOfMeasureCode")
         protected String unitOfMeasureCode;

         public String getSoldFlightSegmentRPH() {
            return this.soldFlightSegmentRPH;
         }

         public void setSoldFlightSegmentRPH(String value) {
            this.soldFlightSegmentRPH = value;
         }

         public String getCheckedInAirlineRPH() {
            return this.checkedInAirlineRPH;
         }

         public void setCheckedInAirlineRPH(String value) {
            this.checkedInAirlineRPH = value;
         }

         public String getFlownAirlineSegmentRPH() {
            return this.flownAirlineSegmentRPH;
         }

         public void setFlownAirlineSegmentRPH(String value) {
            this.flownAirlineSegmentRPH = value;
         }

         public EMDType.TicketDocument.CouponInfo.ExcessBaggage getExcessBaggage() {
            return this.excessBaggage;
         }

         public void setExcessBaggage(EMDType.TicketDocument.CouponInfo.ExcessBaggage value) {
            this.excessBaggage = value;
         }

         public EMDType.TicketDocument.CouponInfo.PresentInfo getPresentInfo() {
            return this.presentInfo;
         }

         public void setPresentInfo(EMDType.TicketDocument.CouponInfo.PresentInfo value) {
            this.presentInfo = value;
         }

         public EMDType.TicketDocument.CouponInfo.ReasonForIssuance getReasonForIssuance() {
            return this.reasonForIssuance;
         }

         public void setReasonForIssuance(EMDType.TicketDocument.CouponInfo.ReasonForIssuance value) {
            this.reasonForIssuance = value;
         }

         public EMDType.TicketDocument.CouponInfo.ValidatingAirline getValidatingAirline() {
            return this.validatingAirline;
         }

         public void setValidatingAirline(EMDType.TicketDocument.CouponInfo.ValidatingAirline value) {
            this.validatingAirline = value;
         }

         public EMDType.TicketDocument.CouponInfo.FiledFeeInfo getFiledFeeInfo() {
            return this.filedFeeInfo;
         }

         public void setFiledFeeInfo(EMDType.TicketDocument.CouponInfo.FiledFeeInfo value) {
            this.filedFeeInfo = value;
         }

         public Integer getNumber() {
            return this.number;
         }

         public void setNumber(Integer value) {
            this.number = value;
         }

         public Integer getInConnectionNbr() {
            return this.inConnectionNbr;
         }

         public void setInConnectionNbr(Integer value) {
            this.inConnectionNbr = value;
         }

         public String getCouponReference() {
            return this.couponReference;
         }

         public void setCouponReference(String value) {
            this.couponReference = value;
         }

         public String getFareBasisCode() {
            return this.fareBasisCode;
         }

         public void setFareBasisCode(String value) {
            this.fareBasisCode = value;
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

         public String getStatus() {
            return this.status;
         }

         public void setStatus(String value) {
            this.status = value;
         }

         public Integer getCouponItinerarySeqNbr() {
            return this.couponItinerarySeqNbr;
         }

         public void setCouponItinerarySeqNbr(Integer value) {
            this.couponItinerarySeqNbr = value;
         }

         public String getInvoluntaryIndCode() {
            return this.involuntaryIndCode;
         }

         public void setInvoluntaryIndCode(String value) {
            this.involuntaryIndCode = value;
         }

         public String getSettlementAuthCode() {
            return this.settlementAuthCode;
         }

         public void setSettlementAuthCode(String value) {
            this.settlementAuthCode = value;
         }

         public BigDecimal getValue() {
            return this.value;
         }

         public void setValue(BigDecimal value) {
            this.value = value;
         }

         public Boolean isAssociateInd() {
            return this.associateInd;
         }

         public void setAssociateInd(Boolean value) {
            this.associateInd = value;
         }

         public String getPromotionalCode() {
            return this.promotionalCode;
         }

         public void setPromotionalCode(String value) {
            this.promotionalCode = value;
         }

         public String getRemark() {
            return this.remark;
         }

         public void setRemark(String value) {
            this.remark = value;
         }

         public Boolean isTaxOnEMDInd() {
            return this.taxOnEMDInd;
         }

         public void setTaxOnEMDInd(Boolean value) {
            this.taxOnEMDInd = value;
         }

         public String getAssocFareBasisCode() {
            return this.assocFareBasisCode;
         }

         public void setAssocFareBasisCode(String value) {
            this.assocFareBasisCode = value;
         }

         public Boolean isConsumedAtIssuanceInd() {
            return this.consumedAtIssuanceInd;
         }

         public void setConsumedAtIssuanceInd(Boolean value) {
            this.consumedAtIssuanceInd = value;
         }

         public String getDateOfService() {
            return this.dateOfService;
         }

         public void setDateOfService(String value) {
            this.dateOfService = value;
         }

         public BigDecimal getUnitOfMeasureQuantity() {
            return this.unitOfMeasureQuantity;
         }

         public void setUnitOfMeasureQuantity(BigDecimal value) {
            this.unitOfMeasureQuantity = value;
         }

         public String getUnitOfMeasure() {
            return this.unitOfMeasure;
         }

         public void setUnitOfMeasure(String value) {
            this.unitOfMeasure = value;
         }

         public String getUnitOfMeasureCode() {
            return this.unitOfMeasureCode;
         }

         public void setUnitOfMeasureCode(String value) {
            this.unitOfMeasureCode = value;
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "")
         public static class ExcessBaggage {
            @XmlAttribute(name = "Amount")
            protected BigDecimal amount;
            @XmlAttribute(name = "CurrencyCode")
            protected String currencyCode;
            @XmlAttribute(name = "DecimalPlaces")
            @XmlSchemaType(name = "nonNegativeInteger")
            protected BigInteger decimalPlaces;
            @XmlAttribute(name = "UnitOfMeasureQuantity")
            protected BigDecimal unitOfMeasureQuantity;
            @XmlAttribute(name = "UnitOfMeasure")
            protected String unitOfMeasure;
            @XmlAttribute(name = "UnitOfMeasureCode")
            protected String unitOfMeasureCode;

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

            public BigDecimal getUnitOfMeasureQuantity() {
               return this.unitOfMeasureQuantity;
            }

            public void setUnitOfMeasureQuantity(BigDecimal value) {
               this.unitOfMeasureQuantity = value;
            }

            public String getUnitOfMeasure() {
               return this.unitOfMeasure;
            }

            public void setUnitOfMeasure(String value) {
               this.unitOfMeasure = value;
            }

            public String getUnitOfMeasureCode() {
               return this.unitOfMeasureCode;
            }

            public void setUnitOfMeasureCode(String value) {
               this.unitOfMeasureCode = value;
            }
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "")
         public static class FiledFeeInfo {
            @XmlAttribute(name = "BSR_Rate")
            protected BigDecimal bsrRate;
            @XmlAttribute(name = "Amount")
            protected BigDecimal amount;
            @XmlAttribute(name = "CurrencyCode")
            protected String currencyCode;
            @XmlAttribute(name = "DecimalPlaces")
            @XmlSchemaType(name = "nonNegativeInteger")
            protected BigInteger decimalPlaces;

            public BigDecimal getBSRRate() {
               return this.bsrRate;
            }

            public void setBSRRate(BigDecimal value) {
               this.bsrRate = value;
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
         public static class PresentInfo {
            @XmlAttribute(name = "To")
            protected String to;
            @XmlAttribute(name = "At")
            protected String at;

            public String getTo() {
               return this.to;
            }

            public void setTo(String value) {
               this.to = value;
            }

            public String getAt() {
               return this.at;
            }

            public void setAt(String value) {
               this.at = value;
            }
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "")
         public static class ReasonForIssuance {
            @XmlAttribute(name = "Code")
            protected String code;
            @XmlAttribute(name = "SubCode")
            protected String subCode;
            @XmlAttribute(name = "Description")
            protected String description;

            public String getCode() {
               return this.code;
            }

            public void setCode(String value) {
               this.code = value;
            }

            public String getSubCode() {
               return this.subCode;
            }

            public void setSubCode(String value) {
               this.subCode = value;
            }

            public String getDescription() {
               return this.description;
            }

            public void setDescription(String value) {
               this.description = value;
            }
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "")
         public static class ValidatingAirline {
            @XmlAttribute(name = "CompanyShortName")
            protected String companyShortName;
            @XmlAttribute(name = "TravelSector")
            protected String travelSector;
            @XmlAttribute(name = "Code")
            protected String code;
            @XmlAttribute(name = "CodeContext")
            protected String codeContext;

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
         }
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class TotalFare {
      @XmlAttribute(name = "Purpose")
      protected PurposeType purpose;
      @XmlAttribute(name = "FareAmountType")
      protected FareAmountType fareAmountType;
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

      public FareAmountType getFareAmountType() {
         return this.fareAmountType;
      }

      public void setFareAmountType(FareAmountType value) {
         this.fareAmountType = value;
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
   public static class TravelerRefNumber {
      @XmlAttribute(name = "RPH")
      protected String rph;
      @XmlAttribute(name = "SurnameRefNumber")
      protected String surnameRefNumber;

      public String getRPH() {
         return this.rph;
      }

      public void setRPH(String value) {
         this.rph = value;
      }

      public String getSurnameRefNumber() {
         return this.surnameRefNumber;
      }

      public void setSurnameRefNumber(String value) {
         this.surnameRefNumber = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "value")
   public static class UnstructuredFareCalc {
      @XmlValue
      protected String value;
      @XmlAttribute(name = "FareCalcMode")
      protected String fareCalcMode;
      @XmlAttribute(name = "Operation")
      protected ActionType operation;
      @XmlAttribute(name = "Type")
      protected PurposeType type;
      @XmlAttribute(name = "ReportingCode")
      protected String reportingCode;
      @XmlAttribute(name = "Info", required = true)
      protected String info;

      public String getValue() {
         return this.value;
      }

      public void setValue(String value) {
         this.value = value;
      }

      public String getFareCalcMode() {
         return this.fareCalcMode;
      }

      public void setFareCalcMode(String value) {
         this.fareCalcMode = value;
      }

      public ActionType getOperation() {
         return this.operation;
      }

      public void setOperation(ActionType value) {
         this.operation = value;
      }

      public PurposeType getType() {
         return this.type;
      }

      public void setType(PurposeType value) {
         this.type = value;
      }

      public String getReportingCode() {
         return this.reportingCode;
      }

      public void setReportingCode(String value) {
         this.reportingCode = value;
      }

      public String getInfo() {
         return this.info;
      }

      public void setInfo(String value) {
         this.info = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class ValidatingAirline {
      @XmlAttribute(name = "CompanyShortName")
      protected String companyShortName;
      @XmlAttribute(name = "TravelSector")
      protected String travelSector;
      @XmlAttribute(name = "Code")
      protected String code;
      @XmlAttribute(name = "CodeContext")
      protected String codeContext;

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
   }
}
