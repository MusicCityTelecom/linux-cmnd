package org.opentravel.ota._2003._05;

import java.math.BigDecimal;
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
@XmlType(name = "CruiseGuestInfoType", propOrder = {"reservationID", "guestDetails", "linkedBookings", "paymentOptions", "cancellationPenalty"})
public class CruiseGuestInfoType {
   @XmlElement(name = "ReservationID")
   protected List<ReservationIDType> reservationID;
   @XmlElement(name = "GuestDetails", required = true)
   protected CruiseGuestInfoType.GuestDetails guestDetails;
   @XmlElement(name = "LinkedBookings")
   protected CruiseGuestInfoType.LinkedBookings linkedBookings;
   @XmlElement(name = "PaymentOptions")
   protected CruiseGuestInfoType.PaymentOptions paymentOptions;
   @XmlElement(name = "CancellationPenalty")
   protected CruiseGuestInfoType.CancellationPenalty cancellationPenalty;

   public List<ReservationIDType> getReservationID() {
      if (this.reservationID == null) {
         this.reservationID = new ArrayList<>();
      }

      return this.reservationID;
   }

   public CruiseGuestInfoType.GuestDetails getGuestDetails() {
      return this.guestDetails;
   }

   public void setGuestDetails(CruiseGuestInfoType.GuestDetails value) {
      this.guestDetails = value;
   }

   public CruiseGuestInfoType.LinkedBookings getLinkedBookings() {
      return this.linkedBookings;
   }

   public void setLinkedBookings(CruiseGuestInfoType.LinkedBookings value) {
      this.linkedBookings = value;
   }

   public CruiseGuestInfoType.PaymentOptions getPaymentOptions() {
      return this.paymentOptions;
   }

   public void setPaymentOptions(CruiseGuestInfoType.PaymentOptions value) {
      this.paymentOptions = value;
   }

   public CruiseGuestInfoType.CancellationPenalty getCancellationPenalty() {
      return this.cancellationPenalty;
   }

   public void setCancellationPenalty(CruiseGuestInfoType.CancellationPenalty value) {
      this.cancellationPenalty = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class CancellationPenalty {
      @XmlAttribute(name = "Amount")
      protected BigDecimal amount;

      public BigDecimal getAmount() {
         return this.amount;
      }

      public void setAmount(BigDecimal value) {
         this.amount = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "guestDetail")
   public static class GuestDetails {
      @XmlElement(name = "GuestDetail", required = true)
      protected List<CruiseGuestDetailType> guestDetail;

      public List<CruiseGuestDetailType> getGuestDetail() {
         if (this.guestDetail == null) {
            this.guestDetail = new ArrayList<>();
         }

         return this.guestDetail;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "linkedBooking")
   public static class LinkedBookings {
      @XmlElement(name = "LinkedBooking", required = true)
      protected List<CruiseGuestInfoType.LinkedBookings.LinkedBooking> linkedBooking;

      public List<CruiseGuestInfoType.LinkedBookings.LinkedBooking> getLinkedBooking() {
         if (this.linkedBooking == null) {
            this.linkedBooking = new ArrayList<>();
         }

         return this.linkedBooking;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class LinkedBooking extends RelatedTravelerType {
         @XmlAttribute(name = "LinkTypeCode")
         protected List<String> linkTypeCode;

         public List<String> getLinkTypeCode() {
            if (this.linkTypeCode == null) {
               this.linkTypeCode = new ArrayList<>();
            }

            return this.linkTypeCode;
         }
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "paymentOption")
   public static class PaymentOptions {
      @XmlElement(name = "PaymentOption", required = true)
      protected List<CruiseGuestInfoType.PaymentOptions.PaymentOption> paymentOption;

      public List<CruiseGuestInfoType.PaymentOptions.PaymentOption> getPaymentOption() {
         if (this.paymentOption == null) {
            this.paymentOption = new ArrayList<>();
         }

         return this.paymentOption;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class PaymentOption extends PaymentDetailType {
         @XmlAttribute(name = "ExtendedIndicator")
         protected Boolean extendedIndicator;
         @XmlAttribute(name = "PaymentPurpose")
         @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
         protected String paymentPurpose;
         @XmlAttribute(name = "ExtendedDepositDate")
         protected String extendedDepositDate;
         @XmlAttribute(name = "ReferenceNumber")
         protected String referenceNumber;

         public Boolean isExtendedIndicator() {
            return this.extendedIndicator;
         }

         public void setExtendedIndicator(Boolean value) {
            this.extendedIndicator = value;
         }

         public String getPaymentPurpose() {
            return this.paymentPurpose;
         }

         public void setPaymentPurpose(String value) {
            this.paymentPurpose = value;
         }

         public String getExtendedDepositDate() {
            return this.extendedDepositDate;
         }

         public void setExtendedDepositDate(String value) {
            this.extendedDepositDate = value;
         }

         public String getReferenceNumber() {
            return this.referenceNumber;
         }

         public void setReferenceNumber(String value) {
            this.referenceNumber = value;
         }
      }
   }
}
