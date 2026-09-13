package org.opentravel.ota._2003._05;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AirTaxType", propOrder = "value")
@XmlSeeAlso(EMDType.Taxes.Tax.class)
public class AirTaxType {
   @XmlValue
   protected String value;
   @XmlAttribute(name = "TaxCode")
   protected String taxCode;
   @XmlAttribute(name = "TaxCountry")
   protected String taxCountry;
   @XmlAttribute(name = "TaxName")
   protected String taxName;
   @XmlAttribute(name = "TaxExemptInd")
   protected Boolean taxExemptInd;
   @XmlAttribute(name = "Operation")
   protected ActionType operation;
   @XmlAttribute(name = "TaxTransactionType")
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   protected String taxTransactionType;
   @XmlAttribute(name = "RPH")
   protected String rph;
   @XmlAttribute(name = "RefundableInd")
   protected Boolean refundableInd;
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

   public String getTaxCode() {
      return this.taxCode;
   }

   public void setTaxCode(String value) {
      this.taxCode = value;
   }

   public String getTaxCountry() {
      return this.taxCountry;
   }

   public void setTaxCountry(String value) {
      this.taxCountry = value;
   }

   public String getTaxName() {
      return this.taxName;
   }

   public void setTaxName(String value) {
      this.taxName = value;
   }

   public Boolean isTaxExemptInd() {
      return this.taxExemptInd;
   }

   public void setTaxExemptInd(Boolean value) {
      this.taxExemptInd = value;
   }

   public ActionType getOperation() {
      return this.operation;
   }

   public void setOperation(ActionType value) {
      this.operation = value;
   }

   public String getTaxTransactionType() {
      return this.taxTransactionType;
   }

   public void setTaxTransactionType(String value) {
      this.taxTransactionType = value;
   }

   public String getRPH() {
      return this.rph;
   }

   public void setRPH(String value) {
      this.rph = value;
   }

   public Boolean isRefundableInd() {
      return this.refundableInd;
   }

   public void setRefundableInd(Boolean value) {
      this.refundableInd = value;
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
