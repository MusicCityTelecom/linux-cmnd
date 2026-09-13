package org.opentravel.ota._2003._05;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AirFeeType", propOrder = "value")
public class AirFeeType {
   @XmlValue
   protected String value;
   @XmlAttribute(name = "FeeCode", required = true)
   protected String feeCode;
   @XmlAttribute(name = "TaxPercentage")
   protected BigDecimal taxPercentage;
   @XmlAttribute(name = "Operation")
   protected ActionType operation;
   @XmlAttribute(name = "FeeTransactionType")
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   protected String feeTransactionType;
   @XmlAttribute(name = "RPH")
   protected String rph;
   @XmlAttribute(name = "Amount")
   protected BigDecimal amount;
   @XmlAttribute(name = "CurrencyCode")
   protected String currencyCode;
   @XmlAttribute(name = "DecimalPlaces")
   @XmlSchemaType(name = "nonNegativeInteger")
   protected BigInteger decimalPlaces;

   public String getValue() {
      return this.value;
   }

   public void setValue(String value) {
      this.value = value;
   }

   public String getFeeCode() {
      return this.feeCode;
   }

   public void setFeeCode(String value) {
      this.feeCode = value;
   }

   public BigDecimal getTaxPercentage() {
      return this.taxPercentage;
   }

   public void setTaxPercentage(BigDecimal value) {
      this.taxPercentage = value;
   }

   public ActionType getOperation() {
      return this.operation;
   }

   public void setOperation(ActionType value) {
      this.operation = value;
   }

   public String getFeeTransactionType() {
      return this.feeTransactionType;
   }

   public void setFeeTransactionType(String value) {
      this.feeTransactionType = value;
   }

   public String getRPH() {
      return this.rph;
   }

   public void setRPH(String value) {
      this.rph = value;
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
