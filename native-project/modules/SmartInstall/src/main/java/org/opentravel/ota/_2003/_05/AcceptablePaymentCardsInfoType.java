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
@XmlType(name = "AcceptablePaymentCardsInfoType", propOrder = {"acceptablePaymentCards", "info"})
public class AcceptablePaymentCardsInfoType {
   @XmlElement(name = "AcceptablePaymentCards")
   protected AcceptablePaymentCardsInfoType.AcceptablePaymentCards acceptablePaymentCards;
   @XmlElement(name = "Info")
   protected FormattedTextType info;

   public AcceptablePaymentCardsInfoType.AcceptablePaymentCards getAcceptablePaymentCards() {
      return this.acceptablePaymentCards;
   }

   public void setAcceptablePaymentCards(AcceptablePaymentCardsInfoType.AcceptablePaymentCards value) {
      this.acceptablePaymentCards = value;
   }

   public FormattedTextType getInfo() {
      return this.info;
   }

   public void setInfo(FormattedTextType value) {
      this.info = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "acceptablePaymentCard")
   public static class AcceptablePaymentCards {
      @XmlElement(name = "AcceptablePaymentCard", required = true)
      protected List<AcceptablePaymentCardsInfoType.AcceptablePaymentCards.AcceptablePaymentCard> acceptablePaymentCard;

      public List<AcceptablePaymentCardsInfoType.AcceptablePaymentCards.AcceptablePaymentCard> getAcceptablePaymentCard() {
         if (this.acceptablePaymentCard == null) {
            this.acceptablePaymentCard = new ArrayList<>();
         }

         return this.acceptablePaymentCard;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class AcceptablePaymentCard {
         @XmlAttribute(name = "CardType")
         protected String cardType;
         @XmlAttribute(name = "CardName")
         protected String cardName;
         @XmlAttribute(name = "UsagePercentage")
         protected BigDecimal usagePercentage;
         @XmlAttribute(name = "UsageAmount")
         protected BigDecimal usageAmount;
         @XmlAttribute(name = "CurrencyCode")
         protected String currencyCode;
         @XmlAttribute(name = "DecimalPlaces")
         @XmlSchemaType(name = "nonNegativeInteger")
         protected BigInteger decimalPlaces;

         public String getCardType() {
            return this.cardType;
         }

         public void setCardType(String value) {
            this.cardType = value;
         }

         public String getCardName() {
            return this.cardName;
         }

         public void setCardName(String value) {
            this.cardName = value;
         }

         public BigDecimal getUsagePercentage() {
            return this.usagePercentage;
         }

         public void setUsagePercentage(BigDecimal value) {
            this.usagePercentage = value;
         }

         public BigDecimal getUsageAmount() {
            return this.usageAmount;
         }

         public void setUsageAmount(BigDecimal value) {
            this.usageAmount = value;
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
}
