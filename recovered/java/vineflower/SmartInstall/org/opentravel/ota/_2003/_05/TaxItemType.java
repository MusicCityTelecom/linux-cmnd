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
@XmlType(name = "TaxItemType", propOrder = "taxAmount")
public class TaxItemType {
   @XmlElement(name = "TaxAmount", required = true)
   protected List<TaxItemType.TaxAmount> taxAmount;
   @XmlAttribute(name = "Description")
   protected String description;
   @XmlAttribute(name = "Code")
   protected String code;
   @XmlAttribute(name = "Rate")
   protected BigDecimal rate;

   public List<TaxItemType.TaxAmount> getTaxAmount() {
      if (this.taxAmount == null) {
         this.taxAmount = new ArrayList<>();
      }

      return this.taxAmount;
   }

   public String getDescription() {
      return this.description;
   }

   public void setDescription(String value) {
      this.description = value;
   }

   public String getCode() {
      return this.code;
   }

   public void setCode(String value) {
      this.code = value;
   }

   public BigDecimal getRate() {
      return this.rate;
   }

   public void setRate(BigDecimal value) {
      this.rate = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class TaxAmount {
      @XmlAttribute(name = "Amount")
      protected BigDecimal amount;
      @XmlAttribute(name = "CurrencyCode")
      protected String currencyCode;
      @XmlAttribute(name = "DecimalPlaces")
      @XmlSchemaType(name = "nonNegativeInteger")
      protected BigInteger decimalPlaces;

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
