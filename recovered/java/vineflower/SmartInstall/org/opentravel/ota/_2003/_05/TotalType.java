package org.opentravel.ota._2003._05;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import org.htng._2011b.HTNGExtendedPrice;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TotalType", propOrder = "taxes")
@XmlSeeAlso({HTNGExtendedPrice.class, DiscountType.class, RateUploadType.BaseByGuestAmts.BaseByGuestAmt.class})
public class TotalType {
   @XmlElement(name = "Taxes")
   protected TaxesType taxes;
   @XmlAttribute(name = "AmountBeforeTax")
   protected BigDecimal amountBeforeTax;
   @XmlAttribute(name = "AmountAfterTax")
   protected BigDecimal amountAfterTax;
   @XmlAttribute(name = "AdditionalFeesExcludedIndicator")
   protected Boolean additionalFeesExcludedIndicator;
   @XmlAttribute(name = "Type")
   protected String type;
   @XmlAttribute(name = "ServiceOverrideIndicator")
   protected Boolean serviceOverrideIndicator;
   @XmlAttribute(name = "RateOverrideIndicator")
   protected Boolean rateOverrideIndicator;
   @XmlAttribute(name = "AmountIncludingMarkup")
   protected BigDecimal amountIncludingMarkup;
   @XmlAttribute(name = "CurrencyCode")
   protected String currencyCode;
   @XmlAttribute(name = "DecimalPlaces")
   @XmlSchemaType(name = "nonNegativeInteger")
   protected BigInteger decimalPlaces;

   public TaxesType getTaxes() {
      return this.taxes;
   }

   public void setTaxes(TaxesType value) {
      this.taxes = value;
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

   public Boolean isAdditionalFeesExcludedIndicator() {
      return this.additionalFeesExcludedIndicator;
   }

   public void setAdditionalFeesExcludedIndicator(Boolean value) {
      this.additionalFeesExcludedIndicator = value;
   }

   public String getType() {
      return this.type;
   }

   public void setType(String value) {
      this.type = value;
   }

   public Boolean isServiceOverrideIndicator() {
      return this.serviceOverrideIndicator;
   }

   public void setServiceOverrideIndicator(Boolean value) {
      this.serviceOverrideIndicator = value;
   }

   public Boolean isRateOverrideIndicator() {
      return this.rateOverrideIndicator;
   }

   public void setRateOverrideIndicator(Boolean value) {
      this.rateOverrideIndicator = value;
   }

   public BigDecimal getAmountIncludingMarkup() {
      return this.amountIncludingMarkup;
   }

   public void setAmountIncludingMarkup(BigDecimal value) {
      this.amountIncludingMarkup = value;
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
