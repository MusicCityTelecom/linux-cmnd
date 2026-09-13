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
@XmlType(name = "GuaranteeType", propOrder = {"guaranteesAccepted", "deadline", "comments", "guaranteeDescription"})
@XmlSeeAlso(BookingRulesType.BookingRule.AcceptableGuarantees.AcceptableGuarantee.class)
public class GuaranteeType {
   @XmlElement(name = "GuaranteesAccepted")
   protected GuaranteeType.GuaranteesAccepted guaranteesAccepted;
   @XmlElement(name = "Deadline")
   protected GuaranteeType.Deadline deadline;
   @XmlElement(name = "Comments")
   protected CommentType comments;
   @XmlElement(name = "GuaranteeDescription")
   protected List<ParagraphType> guaranteeDescription;
   @XmlAttribute(name = "RetributionType")
   protected String retributionType;
   @XmlAttribute(name = "GuaranteeCode")
   protected String guaranteeCode;
   @XmlAttribute(name = "GuaranteeType")
   protected String guaranteeType;
   @XmlAttribute(name = "HoldTime")
   @XmlSchemaType(name = "time")
   protected XMLGregorianCalendar holdTime;

   public GuaranteeType.GuaranteesAccepted getGuaranteesAccepted() {
      return this.guaranteesAccepted;
   }

   public void setGuaranteesAccepted(GuaranteeType.GuaranteesAccepted value) {
      this.guaranteesAccepted = value;
   }

   public GuaranteeType.Deadline getDeadline() {
      return this.deadline;
   }

   public void setDeadline(GuaranteeType.Deadline value) {
      this.deadline = value;
   }

   public CommentType getComments() {
      return this.comments;
   }

   public void setComments(CommentType value) {
      this.comments = value;
   }

   public List<ParagraphType> getGuaranteeDescription() {
      if (this.guaranteeDescription == null) {
         this.guaranteeDescription = new ArrayList<>();
      }

      return this.guaranteeDescription;
   }

   public String getRetributionType() {
      return this.retributionType;
   }

   public void setRetributionType(String value) {
      this.retributionType = value;
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

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class Deadline {
      @XmlAttribute(name = "AbsoluteDeadline")
      protected String absoluteDeadline;
      @XmlAttribute(name = "OffsetTimeUnit")
      protected TimeUnitType offsetTimeUnit;
      @XmlAttribute(name = "OffsetUnitMultiplier")
      protected Integer offsetUnitMultiplier;
      @XmlAttribute(name = "OffsetDropTime")
      @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
      protected String offsetDropTime;

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

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "guaranteeAccepted")
   public static class GuaranteesAccepted {
      @XmlElement(name = "GuaranteeAccepted", required = true)
      protected List<GuaranteeType.GuaranteesAccepted.GuaranteeAccepted> guaranteeAccepted;

      public List<GuaranteeType.GuaranteesAccepted.GuaranteeAccepted> getGuaranteeAccepted() {
         if (this.guaranteeAccepted == null) {
            this.guaranteeAccepted = new ArrayList<>();
         }

         return this.guaranteeAccepted;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class GuaranteeAccepted extends PaymentFormType {
         @XmlAttribute(name = "Default")
         protected Boolean _default;
         @XmlAttribute(name = "NoCardHolderInfoReqInd")
         protected Boolean noCardHolderInfoReqInd;
         @XmlAttribute(name = "NameReqInd")
         protected Boolean nameReqInd;
         @XmlAttribute(name = "AddressReqInd")
         protected Boolean addressReqInd;
         @XmlAttribute(name = "PhoneReqInd")
         protected Boolean phoneReqInd;
         @XmlAttribute(name = "InterbankNbrReqInd")
         protected Boolean interbankNbrReqInd;
         @XmlAttribute(name = "BookingSourceAllowedInd")
         protected Boolean bookingSourceAllowedInd;
         @XmlAttribute(name = "CorpDiscountNbrAllowedInd")
         protected Boolean corpDiscountNbrAllowedInd;

         public Boolean isDefault() {
            return this._default;
         }

         public void setDefault(Boolean value) {
            this._default = value;
         }

         public Boolean isNoCardHolderInfoReqInd() {
            return this.noCardHolderInfoReqInd;
         }

         public void setNoCardHolderInfoReqInd(Boolean value) {
            this.noCardHolderInfoReqInd = value;
         }

         public Boolean isNameReqInd() {
            return this.nameReqInd;
         }

         public void setNameReqInd(Boolean value) {
            this.nameReqInd = value;
         }

         public Boolean isAddressReqInd() {
            return this.addressReqInd;
         }

         public void setAddressReqInd(Boolean value) {
            this.addressReqInd = value;
         }

         public Boolean isPhoneReqInd() {
            return this.phoneReqInd;
         }

         public void setPhoneReqInd(Boolean value) {
            this.phoneReqInd = value;
         }

         public Boolean isInterbankNbrReqInd() {
            return this.interbankNbrReqInd;
         }

         public void setInterbankNbrReqInd(Boolean value) {
            this.interbankNbrReqInd = value;
         }

         public Boolean isBookingSourceAllowedInd() {
            return this.bookingSourceAllowedInd;
         }

         public void setBookingSourceAllowedInd(Boolean value) {
            this.bookingSourceAllowedInd = value;
         }

         public Boolean isCorpDiscountNbrAllowedInd() {
            return this.corpDiscountNbrAllowedInd;
         }

         public void setCorpDiscountNbrAllowedInd(Boolean value) {
            this.corpDiscountNbrAllowedInd = value;
         }
      }
   }
}
