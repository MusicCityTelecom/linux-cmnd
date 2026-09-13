package org.opentravel.ota._2003._05;

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
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PaymentFormType", propOrder = {"paymentCard", "bankAcct", "directBill", "voucher", "loyaltyRedemption", "miscChargeOrder", "ticket", "cash"})
@XmlSeeAlso(
   {
         CustomerType.PaymentForm.class,
         CompanyInfoType.PaymentForm.class,
         HotelPaymentFormType.class,
         PaymentDetailType.class,
         PaymentResponseType.class,
         GuaranteeType.GuaranteesAccepted.GuaranteeAccepted.class
   }
)
public class PaymentFormType {
   @XmlElement(name = "PaymentCard")
   protected PaymentCardType paymentCard;
   @XmlElement(name = "BankAcct")
   protected BankAcctType bankAcct;
   @XmlElement(name = "DirectBill")
   protected DirectBillType directBill;
   @XmlElement(name = "Voucher")
   protected PaymentFormType.Voucher voucher;
   @XmlElement(name = "LoyaltyRedemption")
   protected PaymentFormType.LoyaltyRedemption loyaltyRedemption;
   @XmlElement(name = "MiscChargeOrder")
   protected PaymentFormType.MiscChargeOrder miscChargeOrder;
   @XmlElement(name = "Ticket")
   protected PaymentFormType.Ticket ticket;
   @XmlElement(name = "Cash")
   protected PaymentFormType.Cash cash;
   @XmlAttribute(name = "CostCenterID")
   protected String costCenterID;
   @XmlAttribute(name = "RPH")
   protected String rph;
   @XmlAttribute(name = "PaymentTransactionTypeCode")
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   protected String paymentTransactionTypeCode;
   @XmlAttribute(name = "GuaranteeIndicator")
   protected Boolean guaranteeIndicator;
   @XmlAttribute(name = "GuaranteeTypeCode")
   protected String guaranteeTypeCode;
   @XmlAttribute(name = "GuaranteeID")
   protected String guaranteeID;
   @XmlAttribute(name = "Remark")
   protected String remark;
   @XmlAttribute(name = "ShareSynchInd")
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   protected String shareSynchInd;
   @XmlAttribute(name = "ShareMarketInd")
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   protected String shareMarketInd;

   public PaymentCardType getPaymentCard() {
      return this.paymentCard;
   }

   public void setPaymentCard(PaymentCardType value) {
      this.paymentCard = value;
   }

   public BankAcctType getBankAcct() {
      return this.bankAcct;
   }

   public void setBankAcct(BankAcctType value) {
      this.bankAcct = value;
   }

   public DirectBillType getDirectBill() {
      return this.directBill;
   }

   public void setDirectBill(DirectBillType value) {
      this.directBill = value;
   }

   public PaymentFormType.Voucher getVoucher() {
      return this.voucher;
   }

   public void setVoucher(PaymentFormType.Voucher value) {
      this.voucher = value;
   }

   public PaymentFormType.LoyaltyRedemption getLoyaltyRedemption() {
      return this.loyaltyRedemption;
   }

   public void setLoyaltyRedemption(PaymentFormType.LoyaltyRedemption value) {
      this.loyaltyRedemption = value;
   }

   public PaymentFormType.MiscChargeOrder getMiscChargeOrder() {
      return this.miscChargeOrder;
   }

   public void setMiscChargeOrder(PaymentFormType.MiscChargeOrder value) {
      this.miscChargeOrder = value;
   }

   public PaymentFormType.Ticket getTicket() {
      return this.ticket;
   }

   public void setTicket(PaymentFormType.Ticket value) {
      this.ticket = value;
   }

   public PaymentFormType.Cash getCash() {
      return this.cash;
   }

   public void setCash(PaymentFormType.Cash value) {
      this.cash = value;
   }

   public String getCostCenterID() {
      return this.costCenterID;
   }

   public void setCostCenterID(String value) {
      this.costCenterID = value;
   }

   public String getRPH() {
      return this.rph;
   }

   public void setRPH(String value) {
      this.rph = value;
   }

   public String getPaymentTransactionTypeCode() {
      return this.paymentTransactionTypeCode;
   }

   public void setPaymentTransactionTypeCode(String value) {
      this.paymentTransactionTypeCode = value;
   }

   public Boolean isGuaranteeIndicator() {
      return this.guaranteeIndicator;
   }

   public void setGuaranteeIndicator(Boolean value) {
      this.guaranteeIndicator = value;
   }

   public String getGuaranteeTypeCode() {
      return this.guaranteeTypeCode;
   }

   public void setGuaranteeTypeCode(String value) {
      this.guaranteeTypeCode = value;
   }

   public String getGuaranteeID() {
      return this.guaranteeID;
   }

   public void setGuaranteeID(String value) {
      this.guaranteeID = value;
   }

   public String getRemark() {
      return this.remark;
   }

   public void setRemark(String value) {
      this.remark = value;
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
   @XmlType(name = "")
   public static class Cash {
      @XmlAttribute(name = "CashIndicator")
      protected Boolean cashIndicator;

      public Boolean isCashIndicator() {
         return this.cashIndicator;
      }

      public void setCashIndicator(Boolean value) {
         this.cashIndicator = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "loyaltyCertificate")
   public static class LoyaltyRedemption {
      @XmlElement(name = "LoyaltyCertificate")
      protected List<PaymentFormType.LoyaltyRedemption.LoyaltyCertificate> loyaltyCertificate;
      @XmlAttribute(name = "RedemptionQuantity")
      @XmlSchemaType(name = "positiveInteger")
      protected BigInteger redemptionQuantity;
      @XmlAttribute(name = "CertificateNumber")
      protected String certificateNumber;
      @XmlAttribute(name = "MemberNumber")
      protected String memberNumber;
      @XmlAttribute(name = "ProgramName")
      protected String programName;
      @XmlAttribute(name = "PromotionCode")
      protected String promotionCode;
      @XmlAttribute(name = "PromotionVendorCode")
      protected List<String> promotionVendorCode;

      public List<PaymentFormType.LoyaltyRedemption.LoyaltyCertificate> getLoyaltyCertificate() {
         if (this.loyaltyCertificate == null) {
            this.loyaltyCertificate = new ArrayList<>();
         }

         return this.loyaltyCertificate;
      }

      public BigInteger getRedemptionQuantity() {
         return this.redemptionQuantity;
      }

      public void setRedemptionQuantity(BigInteger value) {
         this.redemptionQuantity = value;
      }

      public String getCertificateNumber() {
         return this.certificateNumber;
      }

      public void setCertificateNumber(String value) {
         this.certificateNumber = value;
      }

      public String getMemberNumber() {
         return this.memberNumber;
      }

      public void setMemberNumber(String value) {
         this.memberNumber = value;
      }

      public String getProgramName() {
         return this.programName;
      }

      public void setProgramName(String value) {
         this.programName = value;
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
      @XmlType(name = "")
      public static class LoyaltyCertificate {
         @XmlAttribute(name = "ID_Context")
         protected String idContext;
         @XmlAttribute(name = "NmbrOfNights")
         @XmlSchemaType(name = "nonNegativeInteger")
         protected BigInteger nmbrOfNights;
         @XmlAttribute(name = "Format")
         @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
         protected String format;
         @XmlAttribute(name = "Status")
         protected String status;
         @XmlAttribute(name = "CertificateNumber")
         protected String certificateNumber;
         @XmlAttribute(name = "MemberNumber")
         protected String memberNumber;
         @XmlAttribute(name = "ProgramName")
         protected String programName;
         @XmlAttribute(name = "ID")
         protected String id;
         @XmlAttribute(name = "EffectiveDate")
         @XmlSchemaType(name = "date")
         protected XMLGregorianCalendar effectiveDate;
         @XmlAttribute(name = "ExpireDate")
         @XmlSchemaType(name = "date")
         protected XMLGregorianCalendar expireDate;
         @XmlAttribute(name = "ExpireDateExclusiveIndicator")
         protected Boolean expireDateExclusiveIndicator;

         public String getIDContext() {
            return this.idContext;
         }

         public void setIDContext(String value) {
            this.idContext = value;
         }

         public BigInteger getNmbrOfNights() {
            return this.nmbrOfNights;
         }

         public void setNmbrOfNights(BigInteger value) {
            this.nmbrOfNights = value;
         }

         public String getFormat() {
            return this.format;
         }

         public void setFormat(String value) {
            this.format = value;
         }

         public String getStatus() {
            return this.status;
         }

         public void setStatus(String value) {
            this.status = value;
         }

         public String getCertificateNumber() {
            return this.certificateNumber;
         }

         public void setCertificateNumber(String value) {
            this.certificateNumber = value;
         }

         public String getMemberNumber() {
            return this.memberNumber;
         }

         public void setMemberNumber(String value) {
            this.memberNumber = value;
         }

         public String getProgramName() {
            return this.programName;
         }

         public void setProgramName(String value) {
            this.programName = value;
         }

         public String getID() {
            return this.id;
         }

         public void setID(String value) {
            this.id = value;
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
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class MiscChargeOrder {
      @XmlAttribute(name = "TicketNumber")
      protected String ticketNumber;
      @XmlAttribute(name = "PaperMCO_ExistInd")
      protected Boolean paperMCOExistInd;
      @XmlAttribute(name = "OriginalTicketNumber")
      protected String originalTicketNumber;
      @XmlAttribute(name = "OriginalIssuePlace")
      protected String originalIssuePlace;
      @XmlAttribute(name = "OriginalIssueDate")
      @XmlSchemaType(name = "date")
      protected XMLGregorianCalendar originalIssueDate;
      @XmlAttribute(name = "OriginalIssueIATA")
      protected String originalIssueIATA;
      @XmlAttribute(name = "OriginalPaymentForm")
      protected String originalPaymentForm;
      @XmlAttribute(name = "CheckInhibitorType")
      @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
      protected String checkInhibitorType;
      @XmlAttribute(name = "CouponRPHs")
      protected List<String> couponRPHs;

      public String getTicketNumber() {
         return this.ticketNumber;
      }

      public void setTicketNumber(String value) {
         this.ticketNumber = value;
      }

      public Boolean isPaperMCOExistInd() {
         return this.paperMCOExistInd;
      }

      public void setPaperMCOExistInd(Boolean value) {
         this.paperMCOExistInd = value;
      }

      public String getOriginalTicketNumber() {
         return this.originalTicketNumber;
      }

      public void setOriginalTicketNumber(String value) {
         this.originalTicketNumber = value;
      }

      public String getOriginalIssuePlace() {
         return this.originalIssuePlace;
      }

      public void setOriginalIssuePlace(String value) {
         this.originalIssuePlace = value;
      }

      public XMLGregorianCalendar getOriginalIssueDate() {
         return this.originalIssueDate;
      }

      public void setOriginalIssueDate(XMLGregorianCalendar value) {
         this.originalIssueDate = value;
      }

      public String getOriginalIssueIATA() {
         return this.originalIssueIATA;
      }

      public void setOriginalIssueIATA(String value) {
         this.originalIssueIATA = value;
      }

      public String getOriginalPaymentForm() {
         return this.originalPaymentForm;
      }

      public void setOriginalPaymentForm(String value) {
         this.originalPaymentForm = value;
      }

      public String getCheckInhibitorType() {
         return this.checkInhibitorType;
      }

      public void setCheckInhibitorType(String value) {
         this.checkInhibitorType = value;
      }

      public List<String> getCouponRPHs() {
         if (this.couponRPHs == null) {
            this.couponRPHs = new ArrayList<>();
         }

         return this.couponRPHs;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "conjunctionTicketNbr")
   public static class Ticket {
      @XmlElement(name = "ConjunctionTicketNbr")
      protected List<PaymentFormType.Ticket.ConjunctionTicketNbr> conjunctionTicketNbr;
      @XmlAttribute(name = "TicketNumber")
      protected String ticketNumber;
      @XmlAttribute(name = "ReroutingType")
      @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
      protected String reroutingType;
      @XmlAttribute(name = "ReasonForReroute")
      protected String reasonForReroute;
      @XmlAttribute(name = "OriginalTicketNumber")
      protected String originalTicketNumber;
      @XmlAttribute(name = "OriginalIssuePlace")
      protected String originalIssuePlace;
      @XmlAttribute(name = "OriginalIssueDate")
      @XmlSchemaType(name = "date")
      protected XMLGregorianCalendar originalIssueDate;
      @XmlAttribute(name = "OriginalIssueIATA")
      protected String originalIssueIATA;
      @XmlAttribute(name = "OriginalPaymentForm")
      protected String originalPaymentForm;
      @XmlAttribute(name = "CheckInhibitorType")
      @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
      protected String checkInhibitorType;
      @XmlAttribute(name = "CouponRPHs")
      protected List<String> couponRPHs;

      public List<PaymentFormType.Ticket.ConjunctionTicketNbr> getConjunctionTicketNbr() {
         if (this.conjunctionTicketNbr == null) {
            this.conjunctionTicketNbr = new ArrayList<>();
         }

         return this.conjunctionTicketNbr;
      }

      public String getTicketNumber() {
         return this.ticketNumber;
      }

      public void setTicketNumber(String value) {
         this.ticketNumber = value;
      }

      public String getReroutingType() {
         return this.reroutingType;
      }

      public void setReroutingType(String value) {
         this.reroutingType = value;
      }

      public String getReasonForReroute() {
         return this.reasonForReroute;
      }

      public void setReasonForReroute(String value) {
         this.reasonForReroute = value;
      }

      public String getOriginalTicketNumber() {
         return this.originalTicketNumber;
      }

      public void setOriginalTicketNumber(String value) {
         this.originalTicketNumber = value;
      }

      public String getOriginalIssuePlace() {
         return this.originalIssuePlace;
      }

      public void setOriginalIssuePlace(String value) {
         this.originalIssuePlace = value;
      }

      public XMLGregorianCalendar getOriginalIssueDate() {
         return this.originalIssueDate;
      }

      public void setOriginalIssueDate(XMLGregorianCalendar value) {
         this.originalIssueDate = value;
      }

      public String getOriginalIssueIATA() {
         return this.originalIssueIATA;
      }

      public void setOriginalIssueIATA(String value) {
         this.originalIssueIATA = value;
      }

      public String getOriginalPaymentForm() {
         return this.originalPaymentForm;
      }

      public void setOriginalPaymentForm(String value) {
         this.originalPaymentForm = value;
      }

      public String getCheckInhibitorType() {
         return this.checkInhibitorType;
      }

      public void setCheckInhibitorType(String value) {
         this.checkInhibitorType = value;
      }

      public List<String> getCouponRPHs() {
         if (this.couponRPHs == null) {
            this.couponRPHs = new ArrayList<>();
         }

         return this.couponRPHs;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = "value")
      public static class ConjunctionTicketNbr {
         @XmlValue
         protected String value;
         @XmlAttribute(name = "Coupons")
         protected List<String> coupons;

         public String getValue() {
            return this.value;
         }

         public void setValue(String value) {
            this.value = value;
         }

         public List<String> getCoupons() {
            if (this.coupons == null) {
               this.coupons = new ArrayList<>();
            }

            return this.coupons;
         }
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class Voucher {
      @XmlAttribute(name = "BillingNumber")
      protected String billingNumber;
      @XmlAttribute(name = "SupplierIdentifier")
      protected String supplierIdentifier;
      @XmlAttribute(name = "Identifier")
      protected String identifier;
      @XmlAttribute(name = "ValueType")
      protected String valueType;
      @XmlAttribute(name = "ElectronicIndicator")
      protected Boolean electronicIndicator;
      @XmlAttribute(name = "SeriesCode")
      protected String seriesCode;
      @XmlAttribute(name = "EffectiveDate")
      @XmlSchemaType(name = "date")
      protected XMLGregorianCalendar effectiveDate;
      @XmlAttribute(name = "ExpireDate")
      @XmlSchemaType(name = "date")
      protected XMLGregorianCalendar expireDate;
      @XmlAttribute(name = "ExpireDateExclusiveIndicator")
      protected Boolean expireDateExclusiveIndicator;

      public String getBillingNumber() {
         return this.billingNumber;
      }

      public void setBillingNumber(String value) {
         this.billingNumber = value;
      }

      public String getSupplierIdentifier() {
         return this.supplierIdentifier;
      }

      public void setSupplierIdentifier(String value) {
         this.supplierIdentifier = value;
      }

      public String getIdentifier() {
         return this.identifier;
      }

      public void setIdentifier(String value) {
         this.identifier = value;
      }

      public String getValueType() {
         return this.valueType;
      }

      public void setValueType(String value) {
         this.valueType = value;
      }

      public Boolean isElectronicIndicator() {
         return this.electronicIndicator;
      }

      public void setElectronicIndicator(Boolean value) {
         this.electronicIndicator = value;
      }

      public String getSeriesCode() {
         return this.seriesCode;
      }

      public void setSeriesCode(String value) {
         this.seriesCode = value;
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
   }
}
