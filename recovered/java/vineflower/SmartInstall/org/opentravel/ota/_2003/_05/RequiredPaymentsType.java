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
@XmlType(name = "RequiredPaymentsType", propOrder = "guaranteePayment")
@XmlSeeAlso(PoliciesType.Policy.GuaranteePaymentPolicy.class)
public class RequiredPaymentsType {
   @XmlElement(name = "GuaranteePayment", required = true)
   protected List<RequiredPaymentsType.GuaranteePayment> guaranteePayment;

   public List<RequiredPaymentsType.GuaranteePayment> getGuaranteePayment() {
      if (this.guaranteePayment == null) {
         this.guaranteePayment = new ArrayList<>();
      }

      return this.guaranteePayment;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = {"acceptedPayments", "amountPercent", "deadline", "description", "address", "tpaExtensions"})
   public static class GuaranteePayment {
      @XmlElement(name = "AcceptedPayments")
      protected AcceptedPaymentsType acceptedPayments;
      @XmlElement(name = "AmountPercent")
      protected RequiredPaymentsType.GuaranteePayment.AmountPercent amountPercent;
      @XmlElement(name = "Deadline")
      protected List<RequiredPaymentsType.GuaranteePayment.Deadline> deadline;
      @XmlElement(name = "Description")
      protected List<ParagraphType> description;
      @XmlElement(name = "Address")
      protected List<RequiredPaymentsType.GuaranteePayment.Address> address;
      @XmlElement(name = "TPA_Extensions")
      protected TPAExtensionsType tpaExtensions;
      @XmlAttribute(name = "RetributionType")
      protected String retributionType;
      @XmlAttribute(name = "PaymentCode")
      protected String paymentCode;
      @XmlAttribute(name = "Type")
      protected String type;
      @XmlAttribute(name = "GuaranteeCode")
      protected String guaranteeCode;
      @XmlAttribute(name = "GuaranteeType")
      protected String guaranteeType;
      @XmlAttribute(name = "HoldTime")
      @XmlSchemaType(name = "time")
      protected XMLGregorianCalendar holdTime;
      @XmlAttribute(name = "NoCardHolderInfoReqInd")
      protected Boolean noCardHolderInfoReqInd;
      @XmlAttribute(name = "NameInd")
      protected Boolean nameInd;
      @XmlAttribute(name = "AddressInd")
      protected Boolean addressInd;
      @XmlAttribute(name = "PhoneInd")
      protected Boolean phoneInd;
      @XmlAttribute(name = "InterbankNbrInd")
      protected Boolean interbankNbrInd;
      @XmlAttribute(name = "RoomTypeCode")
      protected String roomTypeCode;
      @XmlAttribute(name = "InfoSource")
      protected String infoSource;
      @XmlAttribute(name = "NonRefundableIndicator")
      protected Boolean nonRefundableIndicator;
      @XmlAttribute(name = "PolicyCode")
      protected String policyCode;
      @XmlAttribute(name = "AgencyNameAddrReqInd")
      protected Boolean agencyNameAddrReqInd;
      @XmlAttribute(name = "CompanyNameAddrReqInd")
      protected Boolean companyNameAddrReqInd;
      @XmlAttribute(name = "Start")
      protected String start;
      @XmlAttribute(name = "Duration")
      protected String duration;
      @XmlAttribute(name = "End")
      protected String end;
      @XmlAttribute(name = "Mon")
      protected Boolean mon;
      @XmlAttribute(name = "Tue")
      protected Boolean tue;
      @XmlAttribute(name = "Weds")
      protected Boolean weds;
      @XmlAttribute(name = "Thur")
      protected Boolean thur;
      @XmlAttribute(name = "Fri")
      protected Boolean fri;
      @XmlAttribute(name = "Sat")
      protected Boolean sat;
      @XmlAttribute(name = "Sun")
      protected Boolean sun;

      public AcceptedPaymentsType getAcceptedPayments() {
         return this.acceptedPayments;
      }

      public void setAcceptedPayments(AcceptedPaymentsType value) {
         this.acceptedPayments = value;
      }

      public RequiredPaymentsType.GuaranteePayment.AmountPercent getAmountPercent() {
         return this.amountPercent;
      }

      public void setAmountPercent(RequiredPaymentsType.GuaranteePayment.AmountPercent value) {
         this.amountPercent = value;
      }

      public List<RequiredPaymentsType.GuaranteePayment.Deadline> getDeadline() {
         if (this.deadline == null) {
            this.deadline = new ArrayList<>();
         }

         return this.deadline;
      }

      public List<ParagraphType> getDescription() {
         if (this.description == null) {
            this.description = new ArrayList<>();
         }

         return this.description;
      }

      public List<RequiredPaymentsType.GuaranteePayment.Address> getAddress() {
         if (this.address == null) {
            this.address = new ArrayList<>();
         }

         return this.address;
      }

      public TPAExtensionsType getTPAExtensions() {
         return this.tpaExtensions;
      }

      public void setTPAExtensions(TPAExtensionsType value) {
         this.tpaExtensions = value;
      }

      public String getRetributionType() {
         return this.retributionType;
      }

      public void setRetributionType(String value) {
         this.retributionType = value;
      }

      public String getPaymentCode() {
         return this.paymentCode;
      }

      public void setPaymentCode(String value) {
         this.paymentCode = value;
      }

      public String getType() {
         return this.type;
      }

      public void setType(String value) {
         this.type = value;
      }

      public String getGuaranteeCode() {
         return this.guaranteeCode;
      }

      public void setGuaranteeCode(String value) {
         this.guaranteeCode = value;
      }

      public String getGuaranteeType() {
         return this.guaranteeType;
      }

      public void setGuaranteeType(String value) {
         this.guaranteeType = value;
      }

      public XMLGregorianCalendar getHoldTime() {
         return this.holdTime;
      }

      public void setHoldTime(XMLGregorianCalendar value) {
         this.holdTime = value;
      }

      public Boolean isNoCardHolderInfoReqInd() {
         return this.noCardHolderInfoReqInd;
      }

      public void setNoCardHolderInfoReqInd(Boolean value) {
         this.noCardHolderInfoReqInd = value;
      }

      public Boolean isNameInd() {
         return this.nameInd;
      }

      public void setNameInd(Boolean value) {
         this.nameInd = value;
      }

      public Boolean isAddressInd() {
         return this.addressInd;
      }

      public void setAddressInd(Boolean value) {
         this.addressInd = value;
      }

      public Boolean isPhoneInd() {
         return this.phoneInd;
      }

      public void setPhoneInd(Boolean value) {
         this.phoneInd = value;
      }

      public Boolean isInterbankNbrInd() {
         return this.interbankNbrInd;
      }

      public void setInterbankNbrInd(Boolean value) {
         this.interbankNbrInd = value;
      }

      public String getRoomTypeCode() {
         return this.roomTypeCode;
      }

      public void setRoomTypeCode(String value) {
         this.roomTypeCode = value;
      }

      public String getInfoSource() {
         return this.infoSource;
      }

      public void setInfoSource(String value) {
         this.infoSource = value;
      }

      public Boolean isNonRefundableIndicator() {
         return this.nonRefundableIndicator;
      }

      public void setNonRefundableIndicator(Boolean value) {
         this.nonRefundableIndicator = value;
      }

      public String getPolicyCode() {
         return this.policyCode;
      }

      public void setPolicyCode(String value) {
         this.policyCode = value;
      }

      public Boolean isAgencyNameAddrReqInd() {
         return this.agencyNameAddrReqInd;
      }

      public void setAgencyNameAddrReqInd(Boolean value) {
         this.agencyNameAddrReqInd = value;
      }

      public Boolean isCompanyNameAddrReqInd() {
         return this.companyNameAddrReqInd;
      }

      public void setCompanyNameAddrReqInd(Boolean value) {
         this.companyNameAddrReqInd = value;
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

      public Boolean isMon() {
         return this.mon;
      }

      public void setMon(Boolean value) {
         this.mon = value;
      }

      public Boolean isTue() {
         return this.tue;
      }

      public void setTue(Boolean value) {
         this.tue = value;
      }

      public Boolean isWeds() {
         return this.weds;
      }

      public void setWeds(Boolean value) {
         this.weds = value;
      }

      public Boolean isThur() {
         return this.thur;
      }

      public void setThur(Boolean value) {
         this.thur = value;
      }

      public Boolean isFri() {
         return this.fri;
      }

      public void setFri(Boolean value) {
         this.fri = value;
      }

      public Boolean isSat() {
         return this.sat;
      }

      public void setSat(Boolean value) {
         this.sat = value;
      }

      public Boolean isSun() {
         return this.sun;
      }

      public void setSun(Boolean value) {
         this.sun = value;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class Address extends AddressInfoType {
         @XmlAttribute(name = "AddresseeName")
         protected String addresseeName;

         public String getAddresseeName() {
            return this.addresseeName;
         }

         public void setAddresseeName(String value) {
            this.addresseeName = value;
         }
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class AmountPercent extends AmountPercentType {
         @XmlAttribute(name = "OverriddenAmountIndicator")
         protected Boolean overriddenAmountIndicator;

         public Boolean isOverriddenAmountIndicator() {
            return this.overriddenAmountIndicator;
         }

         public void setOverriddenAmountIndicator(Boolean value) {
            this.overriddenAmountIndicator = value;
         }
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class Deadline {
         @XmlAttribute(name = "OverrideIndicator")
         protected Boolean overrideIndicator;
         @XmlAttribute(name = "AbsoluteDeadline")
         protected String absoluteDeadline;
         @XmlAttribute(name = "OffsetTimeUnit")
         protected TimeUnitType offsetTimeUnit;
         @XmlAttribute(name = "OffsetUnitMultiplier")
         protected Integer offsetUnitMultiplier;
         @XmlAttribute(name = "OffsetDropTime")
         @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
         protected String offsetDropTime;

         public Boolean isOverrideIndicator() {
            return this.overrideIndicator;
         }

         public void setOverrideIndicator(Boolean value) {
            this.overrideIndicator = value;
         }

         public String getAbsoluteDeadline() {
            return this.absoluteDeadline;
         }

         public void setAbsoluteDeadline(String value) {
            this.absoluteDeadline = value;
         }

         public TimeUnitType getOffsetTimeUnit() {
            return this.offsetTimeUnit;
         }

         public void setOffsetTimeUnit(TimeUnitType value) {
            this.offsetTimeUnit = value;
         }

         public Integer getOffsetUnitMultiplier() {
            return this.offsetUnitMultiplier;
         }

         public void setOffsetUnitMultiplier(Integer value) {
            this.offsetUnitMultiplier = value;
         }

         public String getOffsetDropTime() {
            return this.offsetDropTime;
         }

         public void setOffsetDropTime(String value) {
            this.offsetDropTime = value;
         }
      }
   }
}
