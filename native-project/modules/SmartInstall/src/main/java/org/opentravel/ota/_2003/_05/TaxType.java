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
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TaxType", propOrder = "taxDescription")
@XmlSeeAlso(PoliciesType.Policy.TaxPolicies.TaxPolicy.class)
public class TaxType {
   @XmlElement(name = "TaxDescription")
   protected List<ParagraphType> taxDescription;
   @XmlAttribute(name = "Type")
   protected AmountDeterminationType type;
   @XmlAttribute(name = "Code")
   protected String code;
   @XmlAttribute(name = "Percent")
   protected BigDecimal percent;
   @XmlAttribute(name = "Amount")
   protected BigDecimal amount;
   @XmlAttribute(name = "CurrencyCode")
   protected String currencyCode;
   @XmlAttribute(name = "DecimalPlaces")
   @XmlSchemaType(name = "nonNegativeInteger")
   protected BigInteger decimalPlaces;
   @XmlAttribute(name = "ChargeUnit")
   protected String chargeUnit;
   @XmlAttribute(name = "ChargeFrequency")
   protected String chargeFrequency;
   @XmlAttribute(name = "ChargeUnitExempt")
   @XmlSchemaType(name = "positiveInteger")
   protected BigInteger chargeUnitExempt;
   @XmlAttribute(name = "ChargeFrequencyExempt")
   @XmlSchemaType(name = "positiveInteger")
   protected BigInteger chargeFrequencyExempt;
   @XmlAttribute(name = "MaxChargeUnitApplies")
   @XmlSchemaType(name = "positiveInteger")
   protected BigInteger maxChargeUnitApplies;
   @XmlAttribute(name = "MaxChargeFrequencyApplies")
   @XmlSchemaType(name = "positiveInteger")
   protected BigInteger maxChargeFrequencyApplies;
   @XmlAttribute(name = "EffectiveDate")
   @XmlSchemaType(name = "date")
   protected XMLGregorianCalendar effectiveDate;
   @XmlAttribute(name = "ExpireDate")
   @XmlSchemaType(name = "date")
   protected XMLGregorianCalendar expireDate;
   @XmlAttribute(name = "ExpireDateExclusiveIndicator")
   protected Boolean expireDateExclusiveIndicator;

   public List<ParagraphType> getTaxDescription() {
      if (this.taxDescription == null) {
         this.taxDescription = new ArrayList<>();
      }

      return this.taxDescription;
   }

   public AmountDeterminationType getType() {
      return this.type;
   }

   public void setType(AmountDeterminationType value) {
      this.type = value;
   }

   public String getCode() {
      return this.code;
   }

   public void setCode(String value) {
      this.code = value;
   }

   public BigDecimal getPercent() {
      return this.percent;
   }

   public void setPercent(BigDecimal value) {
      this.percent = value;
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

   public String getChargeUnit() {
      return this.chargeUnit;
   }

   public void setChargeUnit(String value) {
      this.chargeUnit = value;
   }

   public String getChargeFrequency() {
      return this.chargeFrequency;
   }

   public void setChargeFrequency(String value) {
      this.chargeFrequency = value;
   }

   public BigInteger getChargeUnitExempt() {
      return this.chargeUnitExempt;
   }

   public void setChargeUnitExempt(BigInteger value) {
      this.chargeUnitExempt = value;
   }

   public BigInteger getChargeFrequencyExempt() {
      return this.chargeFrequencyExempt;
   }

   public void setChargeFrequencyExempt(BigInteger value) {
      this.chargeFrequencyExempt = value;
   }

   public BigInteger getMaxChargeUnitApplies() {
      return this.maxChargeUnitApplies;
   }

   public void setMaxChargeUnitApplies(BigInteger value) {
      this.maxChargeUnitApplies = value;
   }

   public BigInteger getMaxChargeFrequencyApplies() {
      return this.maxChargeFrequencyApplies;
   }

   public void setMaxChargeFrequencyApplies(BigInteger value) {
      this.maxChargeFrequencyApplies = value;
   }

   public XMLGregorianCalendar getEffectiveDate() {
      return this.effectiveDate;
   }

   public void setEffectiveDate(XMLGregorianCalendar value) {
      this.effectiveDate = value;
   }

   public XMLGregorianCalendar getExpireDate() {
      return this.expireDate;
   }

   public void setExpireDate(XMLGregorianCalendar value) {
      this.expireDate = value;
   }

   public Boolean isExpireDateExclusiveIndicator() {
      return this.expireDateExclusiveIndicator;
   }

   public void setExpireDateExclusiveIndicator(Boolean value) {
      this.expireDateExclusiveIndicator = value;
   }
}
