package org.opentravel.ota._2003._05;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CruiseReservationType", propOrder = {"sailingInfo", "sailingProfile", "reservationInfo", "paymentsDue", "information"})
public class CruiseReservationType {
   @XmlElement(name = "SailingInfo", required = true)
   protected SailingCategoryInfoType sailingInfo;
   @XmlElement(name = "SailingProfile")
   protected List<CruiseProfileType> sailingProfile;
   @XmlElement(name = "ReservationInfo", required = true)
   protected CruiseGuestInfoType reservationInfo;
   @XmlElement(name = "PaymentsDue")
   protected CruiseReservationType.PaymentsDue paymentsDue;
   @XmlElement(name = "Information")
   protected List<ParagraphType> information;

   public SailingCategoryInfoType getSailingInfo() {
      return this.sailingInfo;
   }

   public void setSailingInfo(SailingCategoryInfoType value) {
      this.sailingInfo = value;
   }

   public List<CruiseProfileType> getSailingProfile() {
      if (this.sailingProfile == null) {
         this.sailingProfile = new ArrayList<>();
      }

      return this.sailingProfile;
   }

   public CruiseGuestInfoType getReservationInfo() {
      return this.reservationInfo;
   }

   public void setReservationInfo(CruiseGuestInfoType value) {
      this.reservationInfo = value;
   }

   public CruiseReservationType.PaymentsDue getPaymentsDue() {
      return this.paymentsDue;
   }

   public void setPaymentsDue(CruiseReservationType.PaymentsDue value) {
      this.paymentsDue = value;
   }

   public List<ParagraphType> getInformation() {
      if (this.information == null) {
         this.information = new ArrayList<>();
      }

      return this.information;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "paymentDue")
   public static class PaymentsDue {
      @XmlElement(name = "PaymentDue", required = true)
      protected List<CruiseReservationType.PaymentsDue.PaymentDue> paymentDue;

      public List<CruiseReservationType.PaymentsDue.PaymentDue> getPaymentDue() {
         if (this.paymentDue == null) {
            this.paymentDue = new ArrayList<>();
         }

         return this.paymentDue;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class PaymentDue {
         @XmlAttribute(name = "Amount")
         protected BigDecimal amount;
         @XmlAttribute(name = "PaymentNumber")
         protected Integer paymentNumber;
         @XmlAttribute(name = "DueDate")
         protected String dueDate;

         public BigDecimal getAmount() {
            return this.amount;
         }

         public void setAmount(BigDecimal value) {
            this.amount = value;
         }

         public Integer getPaymentNumber() {
            return this.paymentNumber;
         }

         public void setPaymentNumber(Integer value) {
            this.paymentNumber = value;
         }

         public String getDueDate() {
            return this.dueDate;
         }

         public void setDueDate(String value) {
            this.dueDate = value;
         }
      }
   }
}
