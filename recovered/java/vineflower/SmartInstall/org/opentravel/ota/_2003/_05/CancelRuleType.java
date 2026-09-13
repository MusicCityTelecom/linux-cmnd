package org.opentravel.ota._2003._05;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CancelRuleType", propOrder = "paymentCard")
public class CancelRuleType {
   @XmlElement(name = "PaymentCard")
   protected PaymentCardType paymentCard;
   @XmlAttribute(name = "CancelByDate")
   protected String cancelByDate;
   @XmlAttribute(name = "Percent")
   protected BigDecimal percent;
   @XmlAttribute(name = "Type")
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   protected String type;
   @XmlAttribute(name = "Amount")
   protected BigDecimal amount;
   @XmlAttribute(name = "CurrencyCode")
   protected String currencyCode;
   @XmlAttribute(name = "DecimalPlaces")
   @XmlSchemaType(name = "nonNegativeInteger")
   protected BigInteger decimalPlaces;

   public PaymentCardType getPaymentCard() {
      return this.paymentCard;
   }

   public void setPaymentCard(PaymentCardType value) {
      this.paymentCard = value;
   }

   public String getCancelByDate() {
      return this.cancelByDate;
   }

   public void setCancelByDate(String value) {
      this.cancelByDate = value;
   }

   public BigDecimal getPercent() {
      return this.percent;
   }

   public void setPercent(BigDecimal value) {
      this.percent = value;
   }

   public String getType() {
      return this.type;
   }

   public void setType(String value) {
      this.type = value;
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
