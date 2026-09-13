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

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RailFareType", propOrder = {"basicFare", "termAndCondition"})
@XmlSeeAlso(RailPriceBreakdownType.class)
public class RailFareType {
   @XmlElement(name = "BasicFare", required = true)
   protected RailFareType.BasicFare basicFare;
   @XmlElement(name = "TermAndCondition")
   protected List<RailFareType.TermAndCondition> termAndCondition;

   public RailFareType.BasicFare getBasicFare() {
      return this.basicFare;
   }

   public void setBasicFare(RailFareType.BasicFare value) {
      this.basicFare = value;
   }

   public List<RailFareType.TermAndCondition> getTermAndCondition() {
      if (this.termAndCondition == null) {
         this.termAndCondition = new ArrayList<>();
      }

      return this.termAndCondition;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class BasicFare {
      @XmlAttribute(name = "FareBasisCode")
      protected String fareBasisCode;
      @XmlAttribute(name = "Amount")
      protected BigDecimal amount;
      @XmlAttribute(name = "CurrencyCode")
      protected String currencyCode;
      @XmlAttribute(name = "DecimalPlaces")
      @XmlSchemaType(name = "nonNegativeInteger")
      protected BigInteger decimalPlaces;

      public String getFareBasisCode() {
         return this.fareBasisCode;
      }

      public void setFareBasisCode(String value) {
         this.fareBasisCode = value;
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

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = {"fareRule", "effectiveDates", "description"})
   public static class TermAndCondition {
      @XmlElement(name = "FareRule")
      protected RailFareType.TermAndCondition.FareRule fareRule;
      @XmlElement(name = "EffectiveDates")
      protected RailFareType.TermAndCondition.EffectiveDates effectiveDates;
      @XmlElement(name = "Description")
      protected FreeTextType description;

      public RailFareType.TermAndCondition.FareRule getFareRule() {
         return this.fareRule;
      }

      public void setFareRule(RailFareType.TermAndCondition.FareRule value) {
         this.fareRule = value;
      }

      public RailFareType.TermAndCondition.EffectiveDates getEffectiveDates() {
         return this.effectiveDates;
      }

      public void setEffectiveDates(RailFareType.TermAndCondition.EffectiveDates value) {
         this.effectiveDates = value;
      }

      public FreeTextType getDescription() {
         return this.description;
      }

      public void setDescription(FreeTextType value) {
         this.description = value;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class EffectiveDates {
         @XmlAttribute(name = "Start")
         protected String start;
         @XmlAttribute(name = "Duration")
         protected String duration;
         @XmlAttribute(name = "End")
         protected String end;

         public String getStart() {
            return this.start;
         }

         public void setStart(String value) {
            this.start = value;
         }

         public String getDuration() {
            return this.duration;
         }

         public void setDuration(String value) {
            this.duration = value;
         }

         public String getEnd() {
            return this.end;
         }

         public void setEnd(String value) {
            this.end = value;
         }
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class FareRule {
         @XmlAttribute(name = "Code")
         protected String code;
         @XmlAttribute(name = "CodeContext")
         protected String codeContext;

         public String getCode() {
            return this.code;
         }

         public void setCode(String value) {
            this.code = value;
         }

         public String getCodeContext() {
            return this.codeContext;
         }

         public void setCodeContext(String value) {
            this.codeContext = value;
         }
      }
   }
}
