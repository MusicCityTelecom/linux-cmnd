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

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HotelAdditionalChargesType", propOrder = "additionalCharge")
public class HotelAdditionalChargesType {
   @XmlElement(name = "AdditionalCharge", required = true)
   protected List<HotelAdditionalChargesType.AdditionalCharge> additionalCharge;
   @XmlAttribute(name = "AmountBeforeTax")
   protected BigDecimal amountBeforeTax;
   @XmlAttribute(name = "AmountAfterTax")
   protected BigDecimal amountAfterTax;
   @XmlAttribute(name = "CurrencyCode")
   protected String currencyCode;
   @XmlAttribute(name = "DecimalPlaces")
   @XmlSchemaType(name = "nonNegativeInteger")
   protected BigInteger decimalPlaces;

   public List<HotelAdditionalChargesType.AdditionalCharge> getAdditionalCharge() {
      if (this.additionalCharge == null) {
         this.additionalCharge = new ArrayList<>();
      }

      return this.additionalCharge;
   }

   public BigDecimal getAmountBeforeTax() {
      return this.amountBeforeTax;
   }

   public void setAmountBeforeTax(BigDecimal value) {
      this.amountBeforeTax = value;
   }

   public BigDecimal getAmountAfterTax() {
      return this.amountAfterTax;
   }

   public void setAmountAfterTax(BigDecimal value) {
      this.amountAfterTax = value;
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

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "amount")
   public static class AdditionalCharge {
      @XmlElement(name = "Amount")
      protected TotalType amount;
      @XmlAttribute(name = "RoomAmenityCode")
      protected String roomAmenityCode;
      @XmlAttribute(name = "Quantity")
      @XmlSchemaType(name = "nonNegativeInteger")
      protected BigInteger quantity;

      public TotalType getAmount() {
         return this.amount;
      }

      public void setAmount(TotalType value) {
         this.amount = value;
      }

      public String getRoomAmenityCode() {
         return this.roomAmenityCode;
      }

      public void setRoomAmenityCode(String value) {
         this.roomAmenityCode = value;
      }

      public BigInteger getQuantity() {
         return this.quantity;
      }

      public void setQuantity(BigInteger value) {
         this.quantity = value;
      }
   }
}
