package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FulfillmentType", propOrder = {"paymentDetails", "deliveryAddress", "name", "receipt", "paymentText"})
public class FulfillmentType {
   @XmlElement(name = "PaymentDetails")
   protected FulfillmentType.PaymentDetails paymentDetails;
   @XmlElement(name = "DeliveryAddress")
   protected AddressType deliveryAddress;
   @XmlElement(name = "Name")
   protected PersonNameType name;
   @XmlElement(name = "Receipt")
   protected FulfillmentType.Receipt receipt;
   @XmlElement(name = "PaymentText")
   protected List<FulfillmentType.PaymentText> paymentText;

   public FulfillmentType.PaymentDetails getPaymentDetails() {
      return this.paymentDetails;
   }

   public void setPaymentDetails(FulfillmentType.PaymentDetails value) {
      this.paymentDetails = value;
   }

   public AddressType getDeliveryAddress() {
      return this.deliveryAddress;
   }

   public void setDeliveryAddress(AddressType value) {
      this.deliveryAddress = value;
   }

   public PersonNameType getName() {
      return this.name;
   }

   public void setName(PersonNameType value) {
      this.name = value;
   }

   public FulfillmentType.Receipt getReceipt() {
      return this.receipt;
   }

   public void setReceipt(FulfillmentType.Receipt value) {
      this.receipt = value;
   }

   public List<FulfillmentType.PaymentText> getPaymentText() {
      if (this.paymentText == null) {
         this.paymentText = new ArrayList<>();
      }

      return this.paymentText;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "paymentDetail")
   public static class PaymentDetails {
      @XmlElement(name = "PaymentDetail", required = true)
      protected List<FulfillmentType.PaymentDetails.PaymentDetail> paymentDetail;

      public List<FulfillmentType.PaymentDetails.PaymentDetail> getPaymentDetail() {
         if (this.paymentDetail == null) {
            this.paymentDetail = new ArrayList<>();
         }

         return this.paymentDetail;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class PaymentDetail extends PaymentDetailType {
         @XmlAttribute(name = "Operation")
         protected ActionType operation;

         public ActionType getOperation() {
            return this.operation;
         }

         public void setOperation(ActionType value) {
            this.operation = value;
         }
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class PaymentText extends FormattedTextTextType {
      @XmlAttribute(name = "Name")
      protected String name;

      public String getName() {
         return this.name;
      }

      public void setName(String value) {
         this.name = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class Receipt {
      @XmlAttribute(name = "DistribType")
      protected String distribType;

      public String getDistribType() {
         return this.distribType;
      }

      public void setDistribType(String value) {
         this.distribType = value;
      }
   }
}
